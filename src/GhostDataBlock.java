
/**
 * Represents part of an MK64 ghost where the controller is in a static state. This contains
 * the controller state and the number of consecutive frames the controller was in that state.
 */
class GhostDataBlock {
	private ControllerState controllerState;
	private int durationInFrames;
	
	public GhostDataBlock(ControllerState controllerState, int durationInFrames) {
		this.controllerState = controllerState;
		this.durationInFrames = durationInFrames;
	}
	
	public String toBizHawkString() {
		StringBuilder str = new StringBuilder();
		String bizHawkLine = this.controllerState.toBizHawkString();
		for (int i = 0; i < durationInFrames; i++) {
			str.append(bizHawkLine);
			str.append("\n");
		}
		return str.toString();
	}

	public ControllerState getControllerState() {
		return controllerState;
	}

	public int getDurationInFrames() {
		return durationInFrames;
	}
	
	
	
}