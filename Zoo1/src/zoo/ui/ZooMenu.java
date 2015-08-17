package zoo.ui;

import zoo.Zoo;
import zoo.ZooException;
import zoo.ui.common.Menu;
import zoo.ui.common.MenuOption;
import zoo.utils.Console;

public final class ZooMenu extends Menu {
	
	private static class BuyTicketsOption extends MenuOption {
		BuyTicketsOption() {
			super("Buy tickets");
		}

		@Override
		public void run() {
			try {
				int numTickets = Console.readInteger("How many tickets? ");
				if (numTickets > 0) {
					Zoo.INSTANCE.buyTickets(numTickets);
					System.out.println("Sold " + numTickets + " tickets");
					System.out.println("You now have " + Zoo.INSTANCE.getAvailableTickets() + " tickets");
					System.out.println("The new balance is " + Zoo.INSTANCE.getBalance());
				} else {
					System.out.println("Type a number greater than zero next time :-|");
				}
			} catch (NumberFormatException e) { 
				System.out.println("Type a valid number next time :" + e.getMessage());
			}
		}
	}

	private class VisitZooOption extends MenuOption {
		private VisitZooOption() {
			super("Visit the Zoo");
		}

		@Override
		public void run() {
			if (!Zoo.INSTANCE.existNonEmptyZones()) {
				System.out.println("The Zoo is not open yet!");
				System.out.println("We are creating animal zones and buying animals!");
				System.out.println("Contact the finance department if you wish to support our efforts.");
				System.out.println("Sorry, for any inconvenience!");
				return;
			}
			int available = Zoo.INSTANCE.getAvailableTickets();
			if (available == 0) {
				System.out.println("You don't have any tickets! Please, buy some.");
				return;
			}
			try {
				int numTickets = Console.readInteger("You have " + available 
						+ " tickets. How many visitors are you? (0 to quit) > ");
				if (numTickets > 0) {
					Zoo.INSTANCE.useTickets(numTickets);
					available = Zoo.INSTANCE.getAvailableTickets();
					System.out.println(available == 0 ? "(you have no more tickets)"
						: "(you now have " + available + " tickets)");
					System.out.println("Welcome to the Zoo!");
					new VisitorMenu(ZooMenu.this).run();
				} else if (numTickets < 0) {
					System.out.println("Type a positive number or zero next time :-|");
				}
			} catch (NumberFormatException e) { 
				System.out.println("Type a valid number next time :" + e.getMessage());
			} catch (ZooException e) {
				System.out.println("You only have " + Zoo.INSTANCE.getAvailableTickets() 
						+ " tickets! Try to buy more.");
			}
		}
	}

	public ZooMenu() {
		super("Main");
		setOptions(new BuyTicketsOption(), new VisitZooOption(), 
				new MaintenanceMenu(), new FinanceMenu(), new GymnasiumMenu());
	}

}
