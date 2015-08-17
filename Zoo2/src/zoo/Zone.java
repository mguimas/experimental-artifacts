package zoo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import zoo.animals.Animal;
import zoo.utils.Money;
import zoo.utils.Point;

public final class Zone {
	
	private static final Animal[] NO_ANIMALS = new Animal[0];
	public static final Money PRICE = new Money("1000.00");

	private final Area area;
	private final Class<?> species;
	private Set<Animal> animals;
	
	public Zone(Area area, Class<?> species) {
		assert area != null;
		assert Zoo.isConcreteAnimal(species);
		this.area = area;
		this.species = species;
	}
	
	public Point getEntrance() {
		return area.sw;
	}
	
	public Class<?> getSpecies() {
		return species;
	}
	
	public String getSpeciesName() {
		return species.getSimpleName();
	}
	
	public boolean isZoneOf(Class<?> aClass) {
		return species.equals(aClass);
	}
	
	public Area getArea() {
		return area;
	}

	public boolean isInside(Point p) {
		return area.isInside(p);
	}
	
	public boolean isEmpty() {
		return animals == null;
	}

	public Animal addAnimal() {
		if (animals == null)
			animals = new HashSet<Animal>();
		try {
			Class<?> species = getSpecies();
			Constructor<?> constructor = species.getConstructor(Zone.class);
			Animal animal = (Animal) constructor.newInstance(this);
			animals.add(animal);
			return animal;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean removeAnimal(Animal animal) {
		if (animals != null && animals.remove(animal)) {
			if (animals.isEmpty())
				animals = null;
			return true;
		}
		return false; 
	}
	
	public Animal[] getAnimals() {
		return isEmpty() ? NO_ANIMALS : animals.toArray(new Animal[animals.size()]);
	}

	@Override
	public String toString() {
		return species.getSimpleName() + " zone at " + area;
	}

}
