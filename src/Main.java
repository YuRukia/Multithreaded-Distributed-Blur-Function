import java.util.ArrayList;
import java.util.Random;

public class Main 
{
	public static void main(String[] args) 
	{	
		PrintMatricies printMatricies = new PrintMatricies();
		int[][] rngMatrix = new int[1000][1000];;
		Random rand = new Random();
		
		for(int i = 0; i < rngMatrix.length; i++) 
		{
			for(int x = 0; x < rngMatrix[0].length; x++) {rngMatrix[i][x] = rand.nextInt(2000);}
		}
		
		//Matrix is sliced into chunks to be worked on
		//Division must be between 1 and 1000
		//Fail state simulation runs faster the less chunks there are
		int depth = rngMatrix.length / 100; // Chunks
		int division = rngMatrix.length / depth; // Rows per Chunk
		double failChance = 0.2; //Percentage chance a thread may fail | 0.2 = 20%
		
		//Unthreaded
		UnthreadedVer unthreaderVer = new UnthreadedVer();
		ArrayList<Integer[][]> goldStandard = unthreaderVer.run(rngMatrix, division, depth, failChance);
		printMatricies.Print(rngMatrix, goldStandard, false);
		
		//Threaded
		ThreadedVer threadedVer = new ThreadedVer();
		Integer[][] threadedStandard = threadedVer.run(rngMatrix, division, depth, failChance);
		printMatricies.Print(threadedStandard, true);
		
		//Subtract Threaded from Unthreaded
		SubtractMatrices subtractMatrices = new SubtractMatrices();
		ArrayList<Integer[][]>subtractedMatrix = subtractMatrices.run(goldStandard, threadedStandard);
		printMatricies.Print(rngMatrix, subtractedMatrix, true);
	}
}
