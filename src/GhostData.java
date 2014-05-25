import java.util.List;


/**
 * Represents a full race worth of MK64 ghost data. Ghost Data is comprised of a list of GhostDataBlock objects,
 * each of which represents a controller state and the duration for which the controller was in the state.
 *
 */
class GhostData {
	private final List<GhostDataBlock> blocks;
	
	public GhostData(List<GhostDataBlock> blocks) {
		this.blocks = blocks;
	}
	
	public List<GhostDataBlock> getBlocks() {
		return this.blocks;
	}
	
	/**
	 * Generate tab-delimited string where each line corresponds to one GhostDataBlock
	 */
	public String toShortTabDelimitedString() {
		StringBuilder str = new StringBuilder();
		str.append("CumulativeFrames\tFrames\tButtons\tX\tY\n");
		int cumulativeFrames = 0;
		for (GhostDataBlock block : blocks) {
			str.append(cumulativeFrames);
			str.append("\t");
			str.append(block.getDurationInFrames());
			str.append("\t");
			str.append(block.getControllerState().toTabDelimitedString());
			str.append("\n");
			cumulativeFrames += block.getDurationInFrames();
		}
		return str.toString();
	}
	
	/**
	 * Generate tab-delimited string where each line corresponds to a single frame.
	 */
	public String toLongTabDelimitedString() {
		StringBuilder str = new StringBuilder();
		str.append("Frame\tButtons\tX\tY\n");
		int frame = 0;
		for (GhostDataBlock block : blocks) {
			String line = block.getControllerState().toTabDelimitedString();
			for (int i = 0; i < block.getDurationInFrames(); i++) {
				str.append(frame);
				str.append("\t");
				str.append(line);
				str.append("\n");
				frame++;
			}
		}
		return str.toString();
	}
	
	public String toBizHawkString() {
		StringBuilder str = new StringBuilder();
		for (GhostDataBlock block : blocks) {
			str.append(block.toBizHawkString());
		}
		return str.toString();
	}
}