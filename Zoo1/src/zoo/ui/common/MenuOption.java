package zoo.ui.common;

public abstract class MenuOption {

	private String title;
	
	public MenuOption(String title) {
		this.title = title;
	}
	
	final String getTitle() {
		return title;
	}
	
	public abstract void run();

}
