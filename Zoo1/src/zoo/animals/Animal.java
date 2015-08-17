package zoo.animals;

import java.util.Date;

import zoo.food.Food;
import zoo.utils.NumberSequence;

public abstract class Animal {
	
	private final static NumberSequence REGISTRY_ID = new NumberSequence();

	private static final int MAX_ENERGY = 100;

	private long registryID;
	
	private Date registryDate;
	
	int energy;
	
	Animal() {
		registryID = REGISTRY_ID.next();
		registryDate = new Date();
	}
	
	public final long getRegistryID() {
		return registryID;
	}
	
	public final Date getRegistryDate() {
		return registryDate;
	}
	
	public final Class getSpecies() {
		return getClass();
	}
	
	public final String getSpeciesName() {
		return getClass().getSimpleName();
	}

	public final int getEnergy() {
		return energy;
	}
	
	/**
	 * Eat food and increase energy till 100%.
	 * 
	 * @param item
	 *            - the food to eat
	 * @return <code>true</code> if the animal eat the food, otherwise
	 *         <code>false</code>.
	 */
	public boolean eat(Food item) {
		energy = Math.max(energy + item.getEnergy(), MAX_ENERGY);
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + getRegistryID() 
				+ ", energy=" + getEnergy() + "]";
	}

}
