import java.util.ArrayList;
import java.util.List;

/**
 * Contains logic for reading uncompressed binary MK64 ghost data into a more useful GhostData object.
 * 
 * MK64 Ghost data is stored in the controller pack as a compressed object using MIO0 compression. This
 * class assumes you've already uncompressed the data (for example using the MIO0Decoder class).
 * 
 * The uncompressed binary data is a repeating series of 4 bytes:
 * Byte 1 : Which buttons are being pressed (first 4 bits are A,B,Z,R buttons. 2nd 4 bits not used)
 * Byte 2 : (Number of frames the controller was in this state) minus 1.
 * Byte 3 : Y position of the analog stick (signed integer)
 * Byte 4 : X position of the analog stick (signed integer)
 * 
 * The uncompressed data contains no other metadata, just these 4 bytes repeated. It's possible there is
 * some other data in the controller pack file, above where the MIO0 compressed object starts. One would
 * think that the course and character must be in there.
 *
 */
public class GhostDataReader {

	public GhostData readGhost(byte[] ghostData) {
		int byteLength = ghostData.length;
		int numBlocks = byteLength / 4;
		List<GhostDataBlock> blocks = new ArrayList<GhostDataBlock>();
		for (int i = 0; i < numBlocks; i++) {
			blocks.add(readBlock(ghostData, i*4));
		}
		return new GhostData(blocks);
	}
	
	private GhostDataBlock readBlock(byte[] ghostData, int address) {
		int buttonByte = Utils.readByte(ghostData, address);
		ControllerState controllerState = new ControllerState(
			(buttonByte / 0x80) % 2 == 1, // a
			(buttonByte / 0x40) % 2 == 1, // b
			(buttonByte / 0x20) % 2 == 1, // z
			(buttonByte / 0x10) % 2 == 1, // r
			ghostData[address+3], // x
			ghostData[address+2]); // y
		
		int durationInFrames = Utils.readByte(ghostData, address+1) + 1;
		
		return new GhostDataBlock(controllerState, durationInFrames);
	}
}
