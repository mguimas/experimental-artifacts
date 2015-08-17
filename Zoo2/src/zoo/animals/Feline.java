package zoo.animals;

import zoo.Zone;
import zoo.utils.Money;

public abstract class Feline extends Mammal {

	public Feline(Zone zone) {
		super(zone);
	}

	protected abstract Money getDailyTax();

}
