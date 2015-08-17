package zoo.animals;

import zoo.Zone;
import zoo.utils.Money;

public class Tiger extends Feline {

	public Tiger(Zone zone) {
		super(zone);
	}

	@Override
	protected Money getDailyTax() {
		return new Money("5.00");
	}
	
}
