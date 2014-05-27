
public class CourseSelectionBkmBuilder {
	
	/**
	 * Given a course between 1 and 16, generates a Bizhawk movie string from
	 * N64 Power On until the frame where ghost input data from the race
	 * should take over.
	 * 
	 * Navigates through the menus to select time trials and choose the specified
	 * course.
	 */
	public String getBkmString(int course) {
		
		BkmMenuNavigationBuilder builder = new BkmMenuNavigationBuilder();
		
		builder.wait(132);
		builder.pressButton(N64Button.Start, 7);
		builder.wait(140);
		builder.pressButton(N64Button.Start, 6);
		builder.wait(31);
		builder.pressButton(N64Button.Start, 6);
		builder.wait(10);
		builder.pressButton(N64Button.Down, 5); // select time trials instead of gp
		builder.wait(10);
		builder.pressButton(N64Button.Start, 4);
		builder.wait(14);
		builder.pressButton(N64Button.Start, 4);
		builder.wait(16);
		builder.pressButton(N64Button.Start, 5);
		builder.wait(40);
		
		// TODO add character selection option ... currently uses Toad
		builder.pressButton(N64Button.Right, 5);
		builder.wait(10);
		builder.pressButton(N64Button.Right, 5);
		builder.wait(10);
		builder.pressButton(N64Button.Right, 5);
		builder.wait(10);
		
		builder.pressButton(N64Button.Start, 5); // select character
		builder.wait(10);
		builder.pressButton(N64Button.Start, 5); // confirm character
		builder.wait(65);
		
		// choose cup
		int cup = (course-1) / 4;
		for (int i = 0; i < cup; i++) {
			builder.pressButton(N64Button.Right, 5);
			builder.wait(10);
		}
		
		// choose race
		int race = (course-1) % 4;
		for (int i = 0; i < race; i++) {
			builder.pressButton(N64Button.Down, 5);
			builder.wait(10);
		}
		
		builder.pressButton(N64Button.Start, 9); // select race
		builder.wait(11);
		builder.pressButton(N64Button.Start, 10); // confirm race
		builder.wait(2);
		
		return builder.build();
	}

}
