import java.io.File;
import java.util.List;

/**
 * Takes a single command line argument; a filename containing MK64 ghost data. This file can either be a DEX save
 * file, or controller pack file.
 * 
 * The application tries to identify one or more MK64 ghosts in the input file. For each ghost, the application
 * decompresses the data, then produces 3 output files.
 * 
 * One file is created in the BizHawk movie format.
 * A tab delimited file is created where each line represents 1 frame in the ghost data.
 * Another tab delimited file is created where each line represents 1 controller state in the ghost data, which
 * may have lasted for several frames.
 */
public class GhostReaderApp {

	public static void main(String[] args) {
		if (args == null || args.length != 1 || args[0] == null || args[0].isEmpty()) {
			throw new RuntimeException("Error : Application requires a single argument; the filename containing ghost data");
		}
		
		String fileName = args[0];
		File inputFile = new File(fileName);
		if (!inputFile.exists()) {
			throw new RuntimeException("Input file does not exist : " + inputFile.getAbsolutePath());
		}
		
		byte[] fileData = Utils.readFileToByteArray(fileName);
		MIO0Decoder decoder = new MIO0Decoder();
		GhostDataReader ghostDataReader = new GhostDataReader();
		
		List<Integer> ghostAddresses = decoder.findMIO0BlockAddresses(fileData);
		if (ghostAddresses.isEmpty()) {
			throw new RuntimeException("Error : Found no MK64 ghost data in input file");
		}
		for (int i = 0; i < ghostAddresses.size(); i++) {
			int address = ghostAddresses.get(i);
			System.out.println("Found ghost data at address : " + Integer.toHexString(address));
			byte[] uncompressedGhostData = decoder.decodeMIO0Block(fileData, address);
			GhostData ghostData = ghostDataReader.readGhost(uncompressedGhostData);
			
			Utils.writeStringToFile(
					fileName+"."+i+".shortTabFile.tsv",
					ghostData.toShortTabDelimitedString());
			Utils.writeStringToFile(
					fileName+"."+i+".longTabFile.tsv",
					ghostData.toLongTabDelimitedString());
			Utils.writeStringToFile(
					fileName+"."+i+".bkm",
					ghostData.toBkmMovie());
		}
		
		
	}
}
