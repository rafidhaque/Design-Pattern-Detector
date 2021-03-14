package com.cakes;

import com.cakes.animals.Animal;

public abstract class SpeciesFactory {
	public abstract Animal getAnimal(String type);
}