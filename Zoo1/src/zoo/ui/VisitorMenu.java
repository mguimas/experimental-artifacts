package zoo.ui;

import zoo.Zone;
import zoo.Zoo;
import zoo.animals.Animal;
import zoo.ui.common.Menu;
import zoo.ui.common.MenuOption;

class VisitorMenu extends Menu {

	VisitorMenu(Menu parent) {
		super("Visiting the Zoo", options());
		setParent(parent);
	}

	private static MenuOption[] options() {
		Zone[] zones = Zoo.INSTANCE.getNonEmptyZones();
		MenuOption[] options = new MenuOption[zones.length];
		int num = 0;
		for (final Zone zone : zones) {
			String species = zone.getSpeciesName().toLowerCase();
			options[num++] = new MenuOption("See the " + species + "s") {
				@Override
				public void run() {
					System.out.println(zone);
					for (Animal animal : zone.getAnimals())
						System.out.println("\t" + animal);
				}
			};
		}
		return options;
	}

}
