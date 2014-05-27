
public class SingleFrameData {
	
	private ControllerState controllerState;
	private Coordinates3D kartCoordinates;
	private int raceTimer;
	
	public SingleFrameData(ControllerState controllerState, Coordinates3D kartCoordinates, int raceTimer) {
		this.controllerState = controllerState;
		this.kartCoordinates = kartCoordinates;
		this.raceTimer = raceTimer;
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
	
	
	
	

}
