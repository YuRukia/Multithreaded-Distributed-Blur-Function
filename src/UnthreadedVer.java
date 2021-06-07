import java.util.ArrayList;

public class UnthreadedVer
{

	public ArrayList<Integer[][]> run(int[][] rngMtrx, int div, int dep, double failChance) 
	{	
		int[][] rngMatrix = rngMtrx;
		ArrayList<Integer[][]> blurMatrix = new ArrayList<Integer[][]>();
		int division = div;
		int depth = dep;
		int matrixLength = rngMatrix[0].length;
		
		int z = 0;
		//Loop BlurFunction to complete all chunks
		for(int i = 0; i < division; i++) 
		{
			BlurFunction unthreadedBlur;
			int[][] matrixRow = new int[depth][matrixLength];
			for(int x = 0; x < depth; x++) 
			{
				for(int y = 0 ; y < matrixLength; y++) {matrixRow[x][y] = rngMatrix[z + x][y];}
			}
			z += depth;
			unthreadedBlur = new BlurFunction(matrixRow, depth, false, failChance, i, 0, 0);
			unthreadedBlur.run();
			
			ArrayList<Integer[][]> tmpBlurMatrix;
			tmpBlurMatrix = unthreadedBlur.getBlurMatrix();
			for(int x = 0; x < tmpBlurMatrix.size(); x++) 
			{
				blurMatrix.add(tmpBlurMatrix.get(x));
			}
		}
		return blurMatrix;
	}
}
