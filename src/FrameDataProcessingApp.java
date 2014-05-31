import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FrameDataProcessingApp {

	
	public static void main(String[] args) throws IOException {
		String inputFileName = "lua/testOutput.csv";
		
		BufferedReader in = new BufferedReader(new FileReader(inputFileName));
		
		CourseMapTransform mapTransform = new CourseMapTransform();
		CourseMapWriter mapWriter = new CourseMapWriter(1);
		
		String line = null;
		
		boolean wasPreviousFrameMt = false;
		
		while ( (line = in.readLine()) != null) {
			String[] parts = line.split(",",-1);
			if (parts.length != 6) continue;
			int time = Integer.parseInt(parts[0]);
			double x = Double.parseDouble(parts[1]);
			double y = Double.parseDouble(parts[2]);
			double speed = Double.parseDouble(parts[3]);
			int lap = Integer.parseInt(parts[4]) + 1;
			boolean isMt = Integer.parseInt(parts[5]) % 2 == 1;
			boolean isShroom = Integer.parseInt(parts[5]) >= 32;
			if (time == 0) continue;
			if (lap != 2) continue;
			IntCoordinates2D coords = mapTransform.getMapPixelCoordinates(1,
					new Coordinates3D(x,y,0));
			mapWriter.plotPoint(coords, isShroom);
			if (isMt && !wasPreviousFrameMt) {
				mapWriter.plotMiniTurbo(coords);
			}
			
			wasPreviousFrameMt = isMt;
		}
		mapWriter.publishMap();
	}
}
