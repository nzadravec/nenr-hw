import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		
		int[] is = new int[] {1,2,3};
		int[] is2 = is;
		is = null;
		System.out.println(Arrays.toString(is));
		System.out.println(Arrays.toString(is2));
		
	}

	public static void main1(String[] args) {
		
		int[][] iss = new int[2][3];
		iss[0][0] = 1;
		iss[1][0] = 2;
		for(int i = 0; i < iss.length; i++) {
			for(int j = 0; j < iss[0].length; j++) {
				System.out.print(iss[i][j]+" ");
			}
			System.out.println();
		}
//		System.out.println(iss.length);
//		System.out.println(iss[0].length);

		iss = f(iss);
		System.out.println();
		
		for(int i = 0; i < iss.length; i++) {
			for(int j = 0; j < iss[0].length; j++) {
				System.out.print(iss[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	
	private static int[][] f(int[][] xss) {
		int[][] yss = new int[xss[0].length][xss.length];
		for(int i = 0; i < yss.length; i++) {
			for(int j = 0; j < yss[0].length; j++) {
				yss[i][j] = xss[j][i];
			}
		}
		
		return yss;
	}
	
}
