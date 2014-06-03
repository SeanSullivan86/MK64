
public class ConversionRateTest {
	
	public static void main(String[] args) {		
		long ntscIncrementAsLong = 0x3F9111109E88C1B0L; // 0.0166666666
		double ntscIncrement = Double.longBitsToDouble(ntscIncrementAsLong);
		long palIncrementAsLong = 0x3F9485CD701C02FCL; // 0.020041666
		double palIncrement = Double.longBitsToDouble(palIncrementAsLong);

		float ntscTime = 0;
		float palTime = 0;
		int ntscHundredths = 0;
		int palHundredths = 0;
		float oldNtscTime = 0;
		
		for (int i = 0; i < 50000000; i++) {
			oldNtscTime = ntscTime;
			ntscTime = (float) ( ((double) ntscTime) + ntscIncrement );
			palTime = (float) ( ((double) palTime) + palIncrement );
			ntscHundredths = (int) (((float) 100.0) * ntscTime);
			palHundredths = (int) (((float) 100.0) * palTime);
			
			if (ntscTime > 2048 && ntscTime < 2049) {
				System.out.println(ntscIncrement * (1<<11));
				System.out.println((((double) ntscTime) - ((double) oldNtscTime))*(1<<11));
			}
			
			if (i % 2 == 1 && (i/2 + 1) % 10000 == 0) {
				//System.out.println((i/2+1) + "\t" + ntscTime + "\t" + ntscHundredths + "\t" + palTime + "\t" + palHundredths);
			}
		}
	}
	

}
