package zoo.ui.common;

import zoo.utils.Console;

public class Menu extends MenuOption {

	private static final String CONTEXT_SEPARATOR = " > ";
	private static final String OPTION_SEPARATOR = " : ";
	private static final int EXIT = 0;
	private Menu parent;
	private MenuOption[] options;
	
	public Menu(String title, MenuOption... options) {
		super(title);
		setOptions(options);
	}

	protected final void setParent(Menu parent) {
		this.parent = parent;
	}
	
	protected final void setOptions(MenuOption... options) {
		this.options = options;
		for (MenuOption option : options) {
			if (option instanceof Menu)
				((Menu)option).setParent(this);
		}
	}
	
	@Override
	public void run() {
		int selected;
		while ((selected = prompt()) != EXIT) {
			options[selected - 1].run();
		}
	}

	/**
	 * @return 0 if the user exited, otherwise the selected option number.
	 */
	protected final int prompt() {
		while (true) {
			header();
			options();
		    try {
		        int selected = Console.readInteger("Option? ");
		        if (selected > 0 && selected <= options.length) return selected;
		        if (selected == 0) return EXIT;
		    } catch (NumberFormatException e) {
		    	System.out.println("Type a valid option number");
		    }
		}
	}

	protected void header() {
		System.out.println();
		contextBar();
	}

	protected final void contextBar() {
		String context = getTitle();
		Menu menu = this;
		while ((menu = menu.parent) != null) {
			context = menu.getTitle() + CONTEXT_SEPARATOR + context;
		}
		System.out.println(context);
	}
	
	private void options() {
		int num = 0;
		for (MenuOption option: options) {
			System.out.println((++num) + OPTION_SEPARATOR + option.getTitle());
		}
		System.out.println(0 + OPTION_SEPARATOR + (parent == null? "Quit" : "Back"));
	}

}
