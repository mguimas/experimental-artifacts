package zoo.ui;

import zoo.Zoo;
import zoo.ui.common.Menu;
import zoo.ui.common.MenuOption;
import zoo.utils.Console;
import zoo.utils.Money;

class FinanceMenu extends Menu {

	private static class BalanceOption extends MenuOption {
		public BalanceOption() {
			super("Balance");
		}

		@Override
		public void run() {
			System.out.println("Balance is " + Zoo.INSTANCE.getBalance());
		}
	}
	
	private static class TicketPriceOption extends MenuOption {
		public TicketPriceOption() {
			super("Change ticket price");
		}

		@Override
		public void run() {
			Money price = Zoo.INSTANCE.getTicketPrice();
			System.out.println("Current price is " + price);
			try {
				Money newPrice = Console.readMoney("What will be the new price?");
				if (newPrice.isPlus()) {
					Zoo.INSTANCE.setTicketPrice(newPrice);
					System.out.println("Ticket price is now " + Zoo.INSTANCE.getTicketPrice());
				} else {
					System.out.println("You are playing with me :-(");
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Price not changed");
			}
		}
	}
	
	private static class DonateOption extends MenuOption {
		public DonateOption() {
			super("Make donation");
		}

		@Override
		public void run() {
			Money balance = Zoo.INSTANCE.getBalance();
			System.out.println("Current balance is " + balance);
			try {
				Money donation = Console.readMoney("How much do you want to donate?");
				if (donation.isPlus()) {
					Zoo.INSTANCE.acceptDonation(donation);
					System.out.println("Thanks! The new balance is " + Zoo.INSTANCE.getBalance());
				} else {
					System.out.println("You are playing with me :-(");
				}
			} catch (NumberFormatException e) {
				System.out.println("Type real money!");
			} catch (IllegalArgumentException e) {
				System.out.println("Balance not changed: " + e.getMessage());
			}
		}
	}

	FinanceMenu() {
		super("Finance department", new BalanceOption(), new TicketPriceOption(), 
				new DonateOption());
	}

}
