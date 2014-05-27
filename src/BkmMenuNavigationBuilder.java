/**
 * Builder class for creating a bizhawk movie (bkm) string. Example usage:
 * 
 * BkmMenuNavigationBuilder builder = new BkmMenuNavigationBuilder();
 * builder.wait(100); // does nothing for 100 frames (all buttons unpressed)
 * builder.press(N64Button.Start, 5); // presses Start for 5 frames
 * builder.build(); // returns the String containing those 105 frames worth of data
 * 
 * Currently only supports the Start, Right, and Down buttons
 *
 */
public class BkmMenuNavigationBuilder {
	
	private static final String blank = "|.|.............. 000, 000|.............. 000, 000|.............. 000, 000|.............. 000, 000|\n";
	private static final String start = "|.|.......S...... 000, 000|.............. 000, 000|.............. 000, 000|.............. 000, 000|\n";
	private static final String right = "|.|..R........... 000, 000|.............. 000, 000|.............. 000, 000|.............. 000, 000|\n";
	private static final String down =  "|.|.D............ 000, 000|.............. 000, 000|.............. 000, 000|.............. 000, 000|\n";
	
	private final StringBuilder builder;
	
	public BkmMenuNavigationBuilder() {
		this.builder = new StringBuilder();
	}
	
	public void wait(int frames) {
		builder.append(nCopies(blank, frames));
	}
	
	public void pressButton(N64Button button, int frames) {
		builder.append(nCopies(buttonToString(button), frames));
	}
	
	public String build() {
		return this.builder.toString();
	}
	
	private String buttonToString(N64Button button) {
		switch(button) {
			case Start : return start;
			case Right : return right;
			case Down : return down;
			default: throw new RuntimeException("Unexpected button : " + button.name());
		}
	}
	
	private String nCopies(String str, int n) {
		StringBuilder x = new StringBuilder();
		for (int i = 0; i < n; i++) {
			x.append(str);
		}
		return x.toString();
	}
	
}
