import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;


class Utils {
	/**
	 * Interpret a single byte as an unsigned integer
	 */
	public static int readByte(byte[] array, int firstByte) {
		return (0x000000FF & ((int)array[firstByte]));
	}
	
	/**
	 * Interpret 2 bytes as an unsigned integer
	 */
	public static int readShort(byte[] array, int firstByte) {
		int s =  ((0xFF & ((int)array[firstByte])) << 8) |
				   (0xFF & ((int)array[firstByte+1]));
		return s;
	}

	/**
	 * Interpret 4 bytes as an unsigned integer
	 */
	public static int readInt(byte[] array, int firstByte) {
		return ((0xFF & ((int)array[firstByte])) << 24) |
			   ((0xFF & ((int)array[firstByte+1])) << 16) |
			   ((0xFF & ((int)array[firstByte+2])) << 8) |
					   (0xFF & ((int)array[firstByte+3]));
	}
	
	public static byte[] readFileToByteArray(String filename) {
		try {
			return Files.readAllBytes(Paths.get(filename));
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file : " + filename);
		}
	}
	
	public static void writeStringToFile(String filename, String data) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filename));
			out.print(data);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to file : " + filename);
		}
	}
}