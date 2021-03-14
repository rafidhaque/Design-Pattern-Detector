package parser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.api.JavacTool;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

import parser.ClassObject.Abstraction;
import parser.Connection.Type;

public class ProjectASTParser {

	// HashMap containing all ClassObjects
	public static HashMap<String, ClassObject> Classes = new HashMap<String, ClassObject>();

	// For NewClass
	public static ClassObject thisclass = new ClassObject();

	/**
	 * C++ getchar() lookalike function for debugging purposes
	 */
	public static void getchar() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\r\nPlease press a key : ");
		String username = null;
		try {
			username = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("You entered : " + username + "\r\n");
	}

	/**
	 * Function to find files within the root file
	 * 
	 * @param path String
	 * @param files ArrayList<File>
	 */
	private static void find_files(String path, ArrayList<File> files) {
		File root = new File(path);
		File[] list = root.listFiles();
		if (list == null)
			return;
		for (File f : list) {
			if (f.isDirectory())
				find_files(f.getAbsolutePath(), files);
			else
				files.add(f.getAbsoluteFile());
		}
	}

	/**
	 * Parse function for gui purposes.
	 * 
	 * @param project defines the input project folder
	 */
	public static void parse(String project) {

		Classes.clear();
		System.setErr(new PrintStream(new ByteArrayOutputStream()));

		// Scanning process
		JavaCompiler compiler = JavacTool.create();
		StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		String projectPath = project;
		ArrayList<File> files = new ArrayList<File>();
		find_files(projectPath, files);

		// Files in folder
		ArrayList<JavaFileObject> units = new ArrayList<JavaFileObject>();
		for (JavaFileObject unit : manager.getJavaFileObjects(files.toArray(new File[files.size()]))) {
			if (unit.getKind() == Kind.SOURCE)
				units.add(unit);
		}
		JavacTask task = (JavacTask) compiler.getTask(null, manager, diagnostics, null, null, units);
		SignatureExtractor tscanner = new SignatureExtractor();
		try {
			tscanner.scan(task.parse(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (ClassObject j : Classes.values()) {
			j.findInherits();
			j.findUses();
			j.findCalls();
			j.findCreates();
			j.findHas();
			j.findReferences();
		}
	}

	/**
	 * Function extending TreeScanner in order to extract the facts we need.
	 * 
	 */
	private static class SignatureExtractor extends TreeScanner<Boolean, Void> {

		public SignatureExtractor() {

		}

		/**
		 * Each time a class is scanned, this function is called.
		 * A new ClassObject is created, filled and saved into the Classes Hashmap.
		 */
		@Override
		public Boolean visitClass(ClassTree node, Void p) {
			ClassTree aclass = node;

			if (Classes.containsKey(aclass.getSimpleName())) {
				Fill_ClassObject(aclass);
			} else {
				if (!GeneralMethods.isPrimitive(aclass.getSimpleName().toString())) {
					Create_ClassObject(aclass);
				} else {
					// System.out.println("Primitive type found: " + aclass.getSimpleName().toString());
				}
			}
			return super.visitClass(node, p);
		}

		/**
		 * Each time a new instance of a class is initialized this function is called.
		 * The newClass is converted to String and saved to its corresponding ClassObject.
		 * A new ClassObject is created(if none with its name exists) and waits to be filled.
		 */
		@Override
		public Boolean visitNewClass(NewClassTree node, Void p) {
			String s;
			s = node.getIdentifier().toString();
			// Adding new instances to the corresponding ClassObject
			Classes.get(thisclass.getName()).addNew_Instance(s);
			if (!Classes.containsKey(s)) {
				Create_ClassObject(s);
			}
			return super.visitNewClass(node, p);
		}

		/**
		 * Each time a new method invocation is encountered, this function is called.
		 * The method invocation is converted to String and saved to its corresponding ClassObject.
		 */
		@Override
		public Boolean visitMethodInvocation(MethodInvocationTree node, Void p) {
			thisclass.addMethodInvocation(node.getMethodSelect().toString());
			return super.visitMethodInvocation(node, p);
		}

		/**
		 * Each time a new variable is encountered, this function is called.
		 * A Variable object is created and saved to its corresponding ClassObject.
		 */
		@Override
		public Boolean visitVariable(VariableTree node, Void p) {
			Variable var = new Variable();
			var.setName(node.getName().toString());
			String s = node.getType().toString();
			var.settype(s);
			// Check for arrays and/or list of classes
			if (s.contains("<")) {
				String s1 = s.substring(s.indexOf("<") + 1, s.indexOf(">"));
				s = s1;
			} else if (s.contains("[]")) {
				String s2 = s.substring(0, s.indexOf("["));
				s = s2;
			}

			// If you encounter a new class, add it in Classes
			if ((!GeneralMethods.isPrimitive(s)) && (!Classes.containsKey(s)) && (!s.contains("[]"))) {
				Create_ClassObject(s);
			}
			if (node.getInitializer() != null)
				var.setinitializer(node.getInitializer().toString());
			for (Object m : node.getModifiers().getFlags().toArray()) {
				var.addmodifier(m.toString());
			}
			thisclass.addMVariable(var);
			return super.visitVariable(node, p);
		}
	}

	/**
	 * Filling a ClassObject that already exists (through return type in a method call inside a class)
	 * 
	 * @param aclass ClassTree
	 */
	public static void Fill_ClassObject(ClassTree aclass) {
		ClassObject newclass = Classes.remove(aclass.getSimpleName().toString());
		create_fill_body(aclass, newclass);
	}

	/**
	 * Creates a ClassObject from a string, when encountering a new class
	 * 
	 * @param s String
	 */
	public static void Create_ClassObject(String s) {
		if (!(Classes.containsKey(s))) {
			// new ClassObject
			ClassObject newclass = new ClassObject();
			// Setting Name
			newclass.setName(s);
			// Setting abstraction as Unknown
			newclass.set_abstraction(Abstraction.Unknown);
			// Adding to Hashmap
			Classes.put(s, newclass);
		}
	}

	/**
	 * Creates a ClassObject from a ClassTree, object provided from the scan procedure.
	 * 
	 * @param aclass ClassTree
	 */
	public static void Create_ClassObject(ClassTree aclass) {
		// new ClassObject
		ClassObject newclass = new ClassObject();
		// Setting Name
		newclass.setName(aclass.getSimpleName().toString());
		create_fill_body(aclass, newclass);
	}

	/**
	 * Main body of Create_ClassObject and Full_ClassObject functions.
	 * 
	 * @param aclass ClassTree
	 * @param newclass ClassObject
	 */
	public static void create_fill_body(ClassTree aclass, ClassObject newclass) {
		newclass.set_abstraction(Abstraction.Normal);
		// Distinguishing Interfaces
		if (aclass.getKind() == Tree.Kind.INTERFACE)
			newclass.set_abstraction(Abstraction.Interface);
		// Setting Extends
		if (aclass.getExtendsClause() != null) {
			newclass.setExtends(aclass.getExtendsClause().toString());
			if (!Classes.containsKey(aclass.getExtendsClause().toString()))
				Create_ClassObject(aclass.getExtendsClause().toString());
		}
		// Setting Implements
		for (Tree t : aclass.getImplementsClause()) {
			newclass.addImplement(t.toString());
			if (!Classes.containsKey(t.toString()))
				Create_ClassObject(t.toString());
		}
		// Setting Modifiers and isAbstract
		for (Object t : aclass.getModifiers().getFlags().toArray()) {
			newclass.addModifier(t.toString());
			if (t.toString().equalsIgnoreCase("abstract"))
				newclass.set_abstraction(Abstraction.Abstract);
		}
		// Setting Methods
		for (Tree t : aclass.getMembers()) {
			if (t.getKind() == Tree.Kind.METHOD) {
				MethodTree method = (MethodTree) t;
				Method newmethod = new Method();
				// Set Method name
				newmethod.setName(method.getName().toString());
				// Set Method return type
				String s;
				if (method.getReturnType() == null)
					s = "Void";
				else
					s = method.getReturnType().toString();
				newmethod.setReturntype(s);

				// Check for arrays and/or list of classes
				if (s.contains("<")) {
					String s1 = s.substring(s.indexOf("<") + 1, s.indexOf(">"));
					s = s1;
				} else if (s.contains("[]")) {
					String s2 = s.substring(0, s.indexOf("["));
					s = s2;
				}

				// If you encounter a new class, add it in Classes
				if (!(GeneralMethods.isPrimitive(s)) && (!Classes.containsKey(s)) && (s != newclass.getName())) {
					Create_ClassObject(s);
				}
				// Set Method Input types
				for (VariableTree m : method.getParameters()) {
					s = m.getType().toString();
					newmethod.addInputtype(s);
					// If you encounter a new class, add it in Classes
					if (!(GeneralMethods.isPrimitive(s)) && (!Classes.containsKey(s)) && (s != newclass.getName())) {
						Create_ClassObject(s);
					}
				}
				// Set Method Modifiers and isAbstract
				newmethod.setisAbstract(false);
				for (Object m : method.getModifiers().getFlags().toArray()) {
					newmethod.addModifier(m.toString());
					if (m.toString().equalsIgnoreCase("abstract"))
						newmethod.setisAbstract(true);
				}
				newclass.addMethod(newmethod);
			}
		}
		// For NewClass
		thisclass = newclass;
		// Adding to Hashmap
		if (Classes.containsKey(newclass.getName())) {
			// Since we checked earlier, the only way a ClassObject with the same name exists,
			// is that it was created during this function(create_fill_body)
			// countClassObjectStringcreated--;
			Classes.remove(newclass.getName());
			Classes.put(newclass.getName(), newclass);
		} else {
			Classes.put(newclass.getName(), newclass);
		}
	}

	/**
	 * Print uses of a specific ClassObject -- Need to use ClassObject.finduses() first
	 * 
	 * @param c ClassObject
	 */
	public static void print_uses(ClassObject c) {
		// Print uses
		for (Connection s : c.getConnections(Type.uses)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " uses " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}

	/**
	 * Print inherits of a specific ClassObject -- Need to use ClassObject.findinherits() first
	 * 
	 * @param c ClassObject
	 */
	public static void print_inherits(ClassObject c) {
		// Print inherits
		for (Connection s : c.getConnections(Type.inherits)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " inherits " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}

	/**
	 * Print has of a specific ClassObject -- Need to use ClassObject.findhas() first
	 * 
	 * @param c ClassObject
	 */
	public static void print_has(ClassObject c) {
		// Print has
		for (Connection s : c.getConnections(Type.has)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " has " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}

	/**
	 * Print references of a specific ClassObject -- Need to use ClassObject.findrefs() first
	 * 
	 * @param c ClassObject
	 */
	public static void print_references(ClassObject c) {
		// Print references
		for (Connection s : c.getConnections(Type.references)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " references " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}

	/**
	 * Print calls of a specific ClassObject -- Need to use ClassObject.findcalls() first
	 * 
	 * @param c ClassObject
	 */
	public static void print_calls(ClassObject c) {
		// Print calls
		for (Connection s : c.getConnections(Type.calls)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " calls " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}

	/**
	 * Print creates of a specific ClassObject -- Need to use ClassObject.findcreates()
	 * 
	 * @param c ClassObject
	 */
	public static void print_creates(ClassObject c) {
		// Print creates
		for (Connection s : c.getConnections(Type.creates)) {
			ClassObject c1 = s.getTo();
			System.out.println(c.get_abstraction() + " " + c.getName() + " creates " + c1.get_abstraction() + " "
					+ c1.getName());
		}
	}
}