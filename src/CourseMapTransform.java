
public class CourseMapTransform {

	
	public IntCoordinates2D getMapPixelCoordinates(int course, Coordinates3D kartLocation) {
		
		// Only Luigi Raceway supported currently
		if (course == 1) {
			/* Approximate linear transformation derived from just testing 2 points
			 * manually.
			 */
			int x = (int) (1267 + 0.675347*(kartLocation.getX() + 85.68369));
			int y = (int) (2208 + 0.673726*(kartLocation.getY() + 215.3549));
			return new IntCoordinates2D(x,y);
		}
		
		throw new RuntimeException("Cannot map from kart location to pixels for course " + course);
	}
}
