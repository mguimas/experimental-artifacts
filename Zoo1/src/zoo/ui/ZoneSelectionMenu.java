package zoo.ui;

import zoo.Zone;
import zoo.ui.common.ElementSelectionMenu;

class ZoneSelectionMenu extends ElementSelectionMenu {

	ZoneSelectionMenu(Zone[] zones) {
		super("Select zone", (Object[])zones);
	}

	@Override
	protected String getLabel(Object elem) {
		return ((Zone)elem).getSpeciesName() + "s";
	}
	
}
