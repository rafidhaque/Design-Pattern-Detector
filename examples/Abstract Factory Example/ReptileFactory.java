package com.cakes;

import com.cakes.animals.Animal;
import com.cakes.animals.Snake;
import com.cakes.animals.Tyrannosaurus;

public class ReptileFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) {
		if ("snake".equals(type)) {
			return new Snake();
		} else {
			return new Tyrannosaurus();
		}
	}

}