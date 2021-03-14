package com.cakes;

import com.cakes.animals.Animal;
import com.cakes.animals.Cat;
import com.cakes.animals.Dog;

public class MammalFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) {
		if ("dog".equals(type)) {
			return new Dog();
		} else {
			return new Cat();
		}
	}

}