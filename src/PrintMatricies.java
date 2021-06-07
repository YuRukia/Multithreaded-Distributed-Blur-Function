import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrintMatricies 
{
	public void Print(int[][] rngMatrix, ArrayList<Integer[][]> blurMatrix, boolean overwrite) 
	{
		FileWriter output;
		try 
		{
			output = new FileWriter("output.txt", overwrite);

			if(!overwrite) 
			{
				for(int i = 0; i < rngMatrix.length; i++) 
				{
					String outputStr = "[";
					for(int x = 0; x < rngMatrix[0].length; x++) 
					{
						if(x == rngMatrix[0].length - 1) {outputStr += rngMatrix[i][x];}
						else {outputStr += rngMatrix[i][x] + ",";}
					}
					outputStr += "]" + "\n";
					output.write(outputStr);
				}
			}
			
			output.write("\n" + "-------------------------------------------------------------------------" + "\n" + "\n");
			
			for(int i = 0; i < blurMatrix.size(); i++) 
			{
				String outputStr = "[";
				for(int x = 0; x < blurMatrix.get(i)[0].length; x++) 
				{
					if(x == blurMatrix.get(i)[0].length - 1) {outputStr += blurMatrix.get(i)[0][x];}
					else {outputStr += blurMatrix.get(i)[0][x] + ",";}
				}
				outputStr += "]" + "\n";
				output.write(outputStr);
			}
			System.out.println(blurMatrix.size());
			System.out.println("-------------------------------------------------------------------------");
			
			output.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}

	public void Print(Integer[][] threadedStandard, boolean overwrite)
	{
		FileWriter output;
		try 
		{
			output = new FileWriter("output.txt", overwrite);
			
			output.write("\n" + "-------------------------------------------------------------------------" + "\n" + "\n");
			for(int i = 0; i < threadedStandard.length; i++) 
			{
				String outputStr = "[";
				for(int x = 0; x < threadedStandard[0].length; x++) 
				{
					if(x == threadedStandard[0].length - 1) {outputStr += threadedStandard[i][x];}
					else {outputStr += threadedStandard[i][x] + ",";}
				}
				outputStr += "]" + "\n";
				output.write(outputStr);
			}
			System.out.println(threadedStandard.length);
			System.out.println("-------------------------------------------------------------------------");
			
			output.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
