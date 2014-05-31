
public class SingleFrameData {
	
	private ControllerState controllerState;
	private Coordinates3D kartCoordinates;
	private int raceTimer;
	private int lap;
	private double speed;
	private boolean isMt;
	
	public SingleFrameData(ControllerState controllerState,
			Coordinates3D kartCoordinates, int raceTimer, int lap,
			double speed, boolean isMt) {
		this.controllerState = controllerState;
		this.kartCoordinates = kartCoordinates;
		this.raceTimer = raceTimer;
		this.lap = lap;
		this.speed = speed;
		this.isMt = isMt;
	}
	public ControllerState getControllerState() {
		return controllerState;
	}
	public Coordinates3D getKartCoordinates() {
		return kartCoordinates;
	}
	public int getRaceTimer() {
		return raceTimer;
	}
	public int getLap() {
		return lap;
	}
	public double getSpeed() {
		return speed;
	}
	public boolean isMt() {
		return isMt;
	}
	
	
	
	

}
