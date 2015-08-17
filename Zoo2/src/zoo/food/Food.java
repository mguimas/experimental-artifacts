package zoo.food;

public abstract class Food {

	public final int getEnergy() {
		return getKCalPerKg();
	}

	public abstract int getKCalPerKg();

}
