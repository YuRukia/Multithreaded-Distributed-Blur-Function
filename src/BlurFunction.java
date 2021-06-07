import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class BlurFunction extends Thread
{
	private int[][] rngMatrix;
	private int[][] matrixSquare = new int[3][3];
	private int[] squareRow = new int[3];
	private ArrayList<Integer> blurRow = new ArrayList<Integer>();
	private ArrayList<Integer[][]> blurMatrix = new ArrayList<Integer[][]>();
	private int nRows;
	private int nColumns;
	private int rp = 0; //Row Pointer
	private int cp = 0; //Column Pointer
	private int threadDepth = 0;
	private boolean failState = false;
	private double failChance = 0;
	private boolean threaded = false;
	private int id;
	private int threadID;
	private long timer;

	public BlurFunction(int[][] rngMtrx, int tD, boolean thrd, double fC, int ID, int tID, long timr) 
	{
		failChance = fC;
		threaded = thrd;
		rngMatrix = rngMtrx;
		nRows = rngMatrix.length;
		nColumns = rngMatrix[0].length;
		threadDepth = tD - 1;
		id = ID;
		threadID = tID;
		timer = timr;
	}

	public void run() 
	{
		if(threaded) 
		{
			//Simulate task being sent to distributed device
			try {Thread.sleep(200);} 
			catch (InterruptedException e) {e.printStackTrace();}
			//Calculate if thread is going to fail
			Random rand = new Random();
			if (rand.nextDouble() < failChance) {failState = true;}
		}

		//Blur Function
		while (rp < nRows) 
		{
			while (cp < nColumns) 
			{
				int x = 0;
				for(int i = rp; i < rp + 3; i++) 
				{
					int k = 0;
					for(int j = cp; j < cp + 3; j++) 
					{
						if(i > threadDepth && j > rngMatrix[0].length - 1){squareRow[k] = rngMatrix[threadDepth][rngMatrix[0].length - 1];}
						else if (i > threadDepth) {squareRow[k] = rngMatrix[threadDepth][j];}
						else if(j > rngMatrix[0].length - 1) {squareRow[k] = rngMatrix[i][rngMatrix[0].length - 1];}
						else {squareRow[k] = rngMatrix[i][j];}
						k++;
					}
					
					for(int j = 0; j < 3; j++) 
					{
						matrixSquare[x][j] = squareRow[j];
					}
					x++;
				}
				blurRow.add(SquareMatrix(matrixSquare));
				cp++;
			}
			Integer[][] tmpArray = new Integer[1][blurRow.size()];
			for(int i = 0; i < blurRow.size(); i++) 
			{
				tmpArray[0][i] = blurRow.get(i);
			}
			blurRow.clear();
			blurMatrix.add(tmpArray);
			rp++;
			cp = 0;
		}
		
		if(threaded) 
		{
			if(!failState)
			{
				//Simulate sending data back to mainframe
				try {Thread.sleep(200);} 
				catch (InterruptedException e) {e.printStackTrace();}
				
				//If time has exceeded limit do not send back data and commit suicide
				if((System.currentTimeMillis() - timer) < 600)
				{
					transmitFinished();
					transmitResult();
				}
				blurMatrix.clear();
			}
		}
		
		return;
	}
	
	//Save blurred chunk to completed array
	private void transmitResult() 
	{
		int x = id * nRows;
		for(int i = 0; i < nRows; i++)
		{
			ThreadedVer.completedArray[x] = blurMatrix.get(i)[0];
			x++;
		}
	}
	
	//Update global variables
	public void transmitFinished()
	{
		ThreadedVer.completedRows[id] = true;
		ThreadedVer.threadStatus[threadID] = true;
	}
	
	public ArrayList<Integer[][]> getBlurMatrix() {return blurMatrix;}
	
	private static int SquareMatrix(int[][] matrixSquare) 
	{
		int totalSum = 0;
		
		for(int i = 0; i < 3; i++) 
		{
			for(int j = 0; j < 3; j++) 
			{
				totalSum += matrixSquare[i][j];
			}
		}
		return totalSum / 9;
	}	
}
