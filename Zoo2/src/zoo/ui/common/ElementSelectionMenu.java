package zoo.ui.common;

public class ElementSelectionMenu extends Menu {

	private final Object[] elements;
	private int selected;
	
	public ElementSelectionMenu(String title, Object... elements) {
		super(title);
		this.elements = elements;
		MenuOption[] options = new MenuOption[elements.length];
		for (int i = 0; i < elements.length; ++i) {
			options[i] = new MenuOption(getLabel(elements[i])) {
				@Override
				public void run() {
					// do nothing
				}
			};
		}
		setOptions(options);
	}

	@Override
	public final void run() {
		selected = prompt();
	}

	@Override
	protected void header() {
		contextBar();
	}

	public final Object getSelection() {
		return selected > 0 ? elements[selected - 1] : null;
	}

	protected String getLabel(Object elem) {
		return elem.toString();
	}

}
