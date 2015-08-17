package zoo;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zoo.animals.Animal;
import zoo.ui.ZooMenu;
import zoo.utils.Money;

public final class Zoo {

	public static final Zoo INSTANCE = new Zoo(new Money("5000.00"));
	public static final Class<?>[] SPECIES = concreteAnimalClasses();
	
	private int ticketsHoldByVisitors = 0;
	private Money ticketPrice = new Money("10.00");
	private Money balance = new Money("0.00");
	private Set<Zone> zones = new HashSet<Zone>();

	public Zoo(Money balance) {
		setBalance(balance);
	}

	private void setBalance(Money amount) {
		assert amount != null;
		balance = amount;
	}

	public Money getBalance() {
		return balance;
	}

	public Money getTicketPrice() {
		return ticketPrice;
	}
	
	public void setTicketPrice(Money price) {
		ticketPrice = price;
	}
	
	public Zone addZone(Area area, Class<?> aClass) throws ZooException {
		if (existsZone(aClass)) {
			throw new ZooException("A zone for " 
					+ aClass.getSimpleName().toLowerCase() + "s already exists");
		}
		if (overlaps(area)) {
			throw new ZooException("Zone overlaps another");
		}
		Zone z = new Zone(area, aClass);
		zones.add(z);
		setBalance(balance.minus(Zone.PRICE));
		return z;
	}
	
	public void removeZone(Zone zone) throws ZooException {
		if (!zone.isEmpty())
			throw new ZooException("Zone is not empty!");
		zones.remove(zone);
	}

	private boolean overlaps(Area area) {
		for (Zone zone : zones) 
			if (area.overlaps(zone.getArea()))
				return true;
		return false;
	}

	private boolean existsZone(Class<?> species) {
		for (Zone zone : zones)
			if (zone.isZoneOf(species)) 
				return true;
		return false;
	}

	public Zone getZone(Class<?> species) {
		for (Zone zone : zones)
			if (zone.isZoneOf(species))
				return zone;
		return null;
	}
	
	public Zone[] getZonesOfFamily(Class<?> species) {
		Set<Zone> result = new HashSet<Zone>();
		for (Zone zone : zones)
			if (species.isAssignableFrom(zone.getSpecies()))
				result.add(zone);
		return result.toArray(new Zone[result.size()]);
	}

	public Zone[] getZones() {
		return zones.toArray(new Zone[zones.size()]);
	}

	public Zone[] getNonEmptyZones() {
		List<Zone> nonEmptyZones = new ArrayList<Zone>();
		for (Zone zone : zones)
			if (!zone.isEmpty())
				nonEmptyZones.add(zone);
		return nonEmptyZones.toArray(new Zone[nonEmptyZones.size()]);
	}

	public boolean existNonEmptyZones() {
		for (Zone zone : zones)
			if (!zone.isEmpty())
				return true;
		return false;
	}

	public Class<?>[] getSpeciesWithoutZone() {
		Set<Class<?>> species = new HashSet<Class<?>>(Arrays.asList(Zoo.SPECIES));
		for (Zone zone : getZones())
			species.remove(zone.getSpecies());
		return species.toArray(new Class<?>[species.size()]);
	}
	
	public void buyTickets(int tickets) {
		setBalance(balance.plus(ticketPrice.times(tickets)));
		ticketsHoldByVisitors += tickets;
	}

	public int acceptTickets(int tickets) throws ZooException {
		if (ticketsHoldByVisitors < tickets)
			throw new ZooException("Not enough tickets");
		ticketsHoldByVisitors -= tickets;
		return ticketsHoldByVisitors;
	}
	
	public int getTicketsHoldByVisitors() {
		return ticketsHoldByVisitors;
	}

	public static void main(String[] args) throws ParseException {
		new ZooMenu().run();
		System.out.println("Have a nice day, bye!");
	}

	private static Class<?>[] concreteAnimalClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		URL url = Zoo.class.getResource("/zoo/animals");
		File directory = new File(url.getFile());
		for (File entry : directory.listFiles()) {
			if (entry.isFile()) {
				String name = entry.getName();
				if (name.endsWith(".class")) {
					try {
						Class<?> aClass = Class.forName("zoo.animals." + 
								name.substring(0, name.lastIndexOf('.')));
						if (isConcreteAnimal(aClass))
							classes.add(aClass);
					} catch (ClassNotFoundException e) { // insane!
						e.printStackTrace();
						System.exit(-1);
					}
				}
			}
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}

	public static boolean isConcreteAnimal(Class<?> aClass) {
		return Animal.class.isAssignableFrom(aClass) 
				&& !Modifier.isAbstract(aClass.getModifiers());
	}

	public void acceptDonation(Money donation) {
		setBalance(balance.plus(donation));
	}

}
