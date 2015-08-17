package zoo;

import java.util.HashSet;
import java.util.Set;

import zoo.animals.Animal;
import zoo.utils.Money;
import zoo.utils.Point;

public class Zone {
	
	private static final Animal[] EMPTY_ARRAY = new Animal[0];

	public static final Money PRICE = new Money("1000.00");

	private final Area area;

	private Class species;
	
	private Set<Animal> animals;
	
	Zone(Area area, Class species) {
		assert area != null;
		assert Zoo.isConcreteAnimal(species);
		this.area = area;
		this.species = species;
	}
	
	public Class getSpecies() {
		return species;
	}
	
	public String getSpeciesName() {
		return species.getSimpleName();
	}
	
	public boolean isZoneOf(Class aClass) {
		return species.equals(aClass);
	}
	
	public Area getArea() {
		return area;
	}
	
	/**
	 * Checks if a point is inside this zone.
	 * @param p - the point
	 * @return <code>true</code> if the point is inside this zone, otherwise <code>false</code>.
	 */
	public boolean isInside(Point p) {
		return area.isInside(p);
	}
	
	public boolean isEmpty() {
		return animals == null;
	}

	public boolean addAnimal(Animal animal) {
		if (animal.getClass() != species) {
			throw new IllegalArgumentException("Zone not for " 
					+ animal.getClass().getSimpleName());
		}
		if (animals == null) {
			animals = new HashSet<Animal>();
		}
		return animals.add(animal);
	}
	
	public boolean removeAnimal(Animal animal) {
		if (animals != null && animals.remove(animal)) {
			if (animals.isEmpty()) {
				animals = null;
			}
			return true;
		}
		return false; 
	}
	
	public Animal[] getAnimals() {
		return isEmpty() ? EMPTY_ARRAY : animals.toArray(new Animal[animals.size()]);
	}

	@Override
	public String toString() {
		return species.getSimpleName() + " zone at " + area;
	}

}
