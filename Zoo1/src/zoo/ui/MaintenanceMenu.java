package zoo.ui;

import zoo.Area;
import zoo.Zone;
import zoo.Zoo;
import zoo.ZooException;
import zoo.animals.Animal;
import zoo.ui.common.ElementSelectionMenu;
import zoo.ui.common.Menu;
import zoo.ui.common.MenuOption;
import zoo.utils.Console;
import zoo.utils.PointFormatException;

class MaintenanceMenu extends Menu {
	
	private static class AddZoneOption extends MenuOption {
		
		private final static String POINT_PROMPT = "%s point? (given as \"(x,y)\" where x and y are integers > 0) > ";
		
		AddZoneOption() {
			super("Add zone");
		}

		@Override
		public void run() {
			Object[] species = Zoo.INSTANCE.getSpeciesWithoutZone();
			if (species.length == 0) {
				System.out.println("No zones left to build!");
				return;
			}
			if (Zoo.INSTANCE.getBalance().lt(Zone.PRICE)) {
				System.out.println("You need " + Zone.PRICE 
						+ " to build a zone!");
				return;
			}
			ElementSelectionMenu menu = new ElementSelectionMenu("Select species", species) {
				@Override
				protected String getLabel(Object elem) {
					return ((Class)elem).getSimpleName();
				}
			};
			menu.run();
			Class aClass = (Class) menu.getSelection(); 
			if (aClass != null) {
				System.out.println("Building zone for " 
						+ aClass.getSimpleName().toLowerCase() + "s ...");
				try {
					Area area = new Area(
							Console.readPoint(String.format(POINT_PROMPT, "SW")),
							Console.readPoint(String.format(POINT_PROMPT, "NE")));
					Zone zone = Zoo.INSTANCE.addZone(area, aClass);
					System.out.println("You built a " + zone);
					Zoo.INSTANCE.setBalance(
							Zoo.INSTANCE.getBalance().minus(Zone.PRICE));
					System.out.println("The new balance is " + Zoo.INSTANCE.getBalance());
				} catch (PointFormatException e) {
					System.out.println("You typed an invalid point!");
				} catch (IllegalArgumentException e) {
					System.out.println("Zone not created: " + e.getMessage());
				} catch (ZooException e) {
					System.out.println("Zone not created: " + e.getMessage());
				}
			}
		}
	}
	
	private static class RemoveZoneOption extends MenuOption {
		RemoveZoneOption() {
			super("Remove zone");
		}

		@Override
		public void run() {
			Zone[] zones = Zoo.INSTANCE.getZones();
			if (zones.length == 0) {
				System.out.println("There are no zones!");
				return;
			}
			ElementSelectionMenu menu = new ZoneSelectionMenu(zones);
			menu.run();
			Zone zone = (Zone) menu.getSelection();
			if (zone != null) {
				String species = zone.getSpeciesName().toLowerCase();
				boolean yes = Console.readYesOrNo("Are you sure you want to remove the " 
						+ species + " zone?");
				if (yes && Zoo.INSTANCE.removeZone(zone)) {
					System.out.println("All " + species + "s were sent to the jungle!");
				}
			}
		}
	}
	
	private static class ListZonesOption extends MenuOption {
		ListZonesOption() {
			super("List zones");
		}

		@Override
		public void run() {
			Zone[] zones = Zoo.INSTANCE.getZones();
			if (zones.length == 0) {
				System.out.println("There are no zones!");
				return;
			}
			for (Zone zone : zones) {
				System.out.println(zone);
				for (Animal animal : zone.getAnimals())
					System.out.println("\t" + animal);
			}
		}
	}
	
	private static class AddAnimalOption extends MenuOption {
		AddAnimalOption() {
			super("Add animal");
		}

		@Override
		public void run() {
			Object[] zones = Zoo.INSTANCE.getZones();
			if (zones.length == 0) {
				System.out.println("You must first create a zone!");
				return;
			}
			ElementSelectionMenu menu = new ElementSelectionMenu("Select species", zones) {
				@Override
				protected String getLabel(Object elem) {
					return ((Zone)elem).getSpeciesName();
				}
			};
			menu.run();
			Zone zone = (Zone) menu.getSelection();
			if (zone != null) {
				Class species = zone.getSpecies();
				try {
					zone.addAnimal((Animal)species.newInstance());
					System.out.println("You added a "
							+ zone.getSpeciesName().toLowerCase());
				} catch (InstantiationException e) {
					exceptionMessage(e, zone);
				} catch (IllegalAccessException e) {
					exceptionMessage(e, zone);
				}
			}
		}
		
		private void exceptionMessage(Exception e, Zone zone) {
			System.out.println("Could not buy " 
					+ zone.getSpeciesName() + ":" + e.getMessage());
		}
	}
	
	private static class FreeAnimalOption extends MenuOption {
		FreeAnimalOption() {
			super("Free animal");
		}

		@Override
		public void run() {
			Zone[] zones = Zoo.INSTANCE.getNonEmptyZones();
			if (zones.length == 0) {
				System.out.println("There are no animals in the zoo!");
				return;
			}
			ElementSelectionMenu menu = new ZoneSelectionMenu(zones);
			menu.run();
			Zone zone = (Zone) menu.getSelection();
			if (zone != null) {
				Object[] animals = zone.getAnimals();
				menu = new ElementSelectionMenu("Select animal", animals);
				menu.run();
				Animal animal = (Animal) menu.getSelection();
				if (animal != null) {
					zone.removeAnimal(animal);
					System.out.println("Congratulations! You released the " 
							+ zone.getSpeciesName().toLowerCase() 
							+ " back into the jungle!");
				}
			}
		}
	}
	
	MaintenanceMenu() {
		super("Maintenance department", 
				new AddZoneOption(), new RemoveZoneOption(), new ListZonesOption(),
				new AddAnimalOption(), new FreeAnimalOption());
	}
}
