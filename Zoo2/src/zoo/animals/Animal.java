package zoo.animals;

import zoo.Zone;
import zoo.food.Food;
import zoo.utils.Point;

public abstract class Animal {
	
	private static final int MAX_ENERGY = 100;

	private Point location;	
	private Zone zone;
	private int energy;

	public Animal(Zone zone) {
		setZone(zone);
		setLocation(zone.getEntrance());
	}
	
	private void setZone(Zone zone) {
		assert zone != null;
		this.zone = zone;
	}

	public Zone getZone() {
		return zone;
	}

	protected void setLocation(Point location) {
		assert location != null;
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public Class<?> getSpecies() {
		return getClass();
	}

	public int getEnergy() {
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
		return getClass().getSimpleName() + " ["
				+ "location=" + getLocation() + ", "
				+ "energy=" + getEnergy() + "]";
	}

}
