import java.util.ArrayList;
import java.util.List;

/**
 * Performs decompression of an MIO0 data block. The decompression algorithm was ported from
 * http://pastie.org/896531.
 *
 */
public class MIO0Decoder {
	
	private final static byte[] miO0 = new byte[] { 0x4D , 0x49, 0x4F, 0x30 };
	
	/**
	 * Find addresses containing the text "MIO0".
	 */
	public List<Integer> findMIO0BlockAddresses(byte[] array) {
		List<Integer> miO0Addresses = new ArrayList<Integer>();
		for (int i = 0; i+3 < array.length; i++) {
			for (int j = 0; j < 4; j++) {
				if (array[i+j] != miO0[j]) break;
				if (j == 3) {
					miO0Addresses.add(i);
				}
			}
		}
		return miO0Addresses;
	}
	
	/**
	 * Decodes an MIO0 block into uncompressed data.
	 * 
	 * @param array input data
	 * @param baseAddress the address of start of the encoded block. The block starts with the text "MIO0".
	 */
	public byte[] decodeMIO0Block(byte[] array, int baseAddress) {	
		int mapByte = 0x80;
		
		int outputSize = Utils.readInt(array, baseAddress + 4);
		int mapLoc = baseAddress + 16;
		
		int compLoc = baseAddress + Utils.readInt(array, baseAddress + 8);
		int rawLoc = baseAddress + Utils.readInt(array, baseAddress + 12);
		
		int curMapByte = Utils.readByte(array, mapLoc);
		int numBytesOutput = 0;
		int outLoc = 0;
		byte[] output = new byte[outputSize];
		
		while (numBytesOutput < outputSize) {
			if ((curMapByte & mapByte) != 0) {
				output[outLoc] = array[rawLoc];
				outLoc++;
				rawLoc++;
				numBytesOutput++;
			} else {
				int sData = Utils.readShort(array, compLoc);
				int length = (sData >> 12) + 3;
				int dist = (sData & 0xFFF) + 1;

				if (outLoc - dist < 0) {
					throw new RuntimeException("outLoc < dist ?");
				}
				for (int i = 0; i < length; i++) {
					output[outLoc] = output[outLoc-dist];
					outLoc++;
					numBytesOutput++;
					if (numBytesOutput >= outputSize) break;
				}
				compLoc += 2;
			}
			mapByte = mapByte >> 1;
			if (mapByte == 0) {
				mapByte = 0x80;
				mapLoc++;
				curMapByte = Utils.readByte(array, mapLoc);
				
				if (mapLoc > Math.min(compLoc, rawLoc)) {
					throw new RuntimeException("Overflow");
				}
			}
		}
		
		return output;
	}
	

}
