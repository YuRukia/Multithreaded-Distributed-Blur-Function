import java.util.ArrayList;

public class ThreadedVer 
{
	static Integer[][] completedArray;
	static boolean[] completedRows;
	static boolean[] threadStatus;
	static int[] threadRowID;
	
	public Integer[][] run(int[][] rngMtrx, int div, int dep, double fC) 
	{
		int threadPoolSize = 100;
		completedArray = new Integer[rngMtrx.length][rngMtrx[0].length];
		completedRows = new boolean[div];
		threadStatus = new boolean[threadPoolSize]; //Number of threads in the thread pool
		threadRowID = new int[threadPoolSize];
		long[] threadTimekeeper = new long[threadPoolSize];
		boolean[] taskAssigned = new boolean[div];
		int[][] rngMatrix = rngMtrx;
		ArrayList<int[][]> threadedRows = new ArrayList<int[][]>();
		ArrayList<BlurFunction> blurThreads = new ArrayList<BlurFunction>(); //Thread Pool
		int threadDepth = dep; //Chunks
		int threadDivision = div; //Rows per Chunk
		int matrixLength = rngMatrix[0].length; //1000 column matrix length
		double failChance = fC;
		
		int z = 0;
		//Divide matrix into chunks to be parallelised
		for(int i = 0; i < threadDivision; i++) 
		{
			int[][] threadedRow = new int[threadDepth][matrixLength];
			for(int x = 0; x < threadDepth; x++) 
			{
				for(int y = 0 ; y < matrixLength; y++) {threadedRow[x][y] = rngMatrix[z + x][y];}
			}
			z += threadDepth;
			threadedRows.add(threadedRow);
		}
		
		//Create threadpool
		for(int i = 0; i < threadPoolSize; i++) 
		{
			blurThreads.add(null);
			threadStatus[i] = true;
		}
		
		int progressBar = 0;
		while(boolCount() < completedRows.length) 
		{
			//Assign tasks to thread pool
			if(boolCount() < completedRows.length) 
			{
				for(int i = 0; i < threadedRows.size(); i++) 
				{
					if(!completedRows[i]) 
					{
						if(!taskAssigned[i]) 
						{
							for(int x = 0; x < threadStatus.length; x++) 
							{
								if(threadStatus[x]) 
								{
									threadTimekeeper[x] = System.currentTimeMillis();
									blurThreads.set(x, new BlurFunction(threadedRows.get(i), threadDepth, true, failChance, i, x, threadTimekeeper[x]));
									threadStatus[x] = false;
									threadRowID[x] = i;
									taskAssigned[i] = true;
									blurThreads.get(x).start();
									x = threadStatus.length + 1;
								}
							}
						}
					}
				}
			}
			
			//Kills threads that have been running for too long
			for(int x = 0; x < threadStatus.length; x++) 
			{
				if(!threadStatus[x])
				{
					if((System.currentTimeMillis() - threadTimekeeper[x]) > 600)
					{
						taskAssigned[threadRowID[x]] = false;
						threadStatus[x] = true;
					}
				}
			}
			
			//Updates percentage progress bar
			if(boolCount() > progressBar) 
			{
				progressBar = boolCount();
				System.out.println(progressBar * 100 / threadDivision + "%");
			}
		}
		
		return ThreadedVer.completedArray;
	}
	
	public int boolCount() 
	{
		int result = 0;
		
		for(int i = 0; i < completedRows.length; i++) {if(completedRows[i]) {result++;}}
		
		return result;
	}
}
