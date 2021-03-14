public class Main {
	public static void main(String[] args){
		ITV tv = new SonyTV();
		LogitechRemoteControl lrc = new LogitechRemoteControl(tv);
		lrc.setChannelKeyboard(100);	
	}
}