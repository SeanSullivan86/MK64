import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CourseMapWriter {
	
	private BufferedImage image;
	private IntCoordinates2D lastPointPlotted;
	private Graphics2D graphics;
	
	public CourseMapWriter(int course) {
		if (course != 1) throw new RuntimeException("Unexpected course");
		
		try {
			this.image = ImageIO.read(new File("luigi-raceway.jpg"));
			this.graphics = this.image.createGraphics();
		} catch (IOException e) {
			throw new RuntimeException("Unable to read course map image");
		}
		this.lastPointPlotted = null;
	}
	
	public void plotPoint(IntCoordinates2D point, boolean isShroom) {
		if (lastPointPlotted == null) {
			this.lastPointPlotted = point;
			return;
		}
		if (isShroom) {
			this.graphics.setColor(Color.red);
		} else {
		    this.graphics.setColor(Color.blue);
		}
		this.graphics.drawLine(
				lastPointPlotted.getX(), lastPointPlotted.getY(),
				point.getX(), point.getY());
		this.lastPointPlotted = point;
	}
	
	public void plotMiniTurbo(IntCoordinates2D point) {
		this.graphics.setColor(Color.red);
		this.graphics.fillOval(point.getX()-4, point.getY()-4, 8, 8);
	}

	public void publishMap() {
		image.flush();
		try {
			ImageIO.write(image, "png", new File("courseMap.png"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to write course map image");
		}
	}
}
