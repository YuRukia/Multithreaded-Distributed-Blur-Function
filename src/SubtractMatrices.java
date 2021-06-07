import java.util.ArrayList;

public class SubtractMatrices 
{
	public ArrayList<Integer[][]> run(ArrayList<Integer[][]> goldStandard, Integer[][] threadedStandard) 
	{
		ArrayList<Integer[][]> subtractedMatrix = new ArrayList<Integer[][]>();
		ArrayList<Integer> subtractedRow = new ArrayList<Integer>();
		int h = 0;
		
		for(int i = 0; i < goldStandard.size(); i++) 
		{
			for(int x = 0; x < goldStandard.get(i)[0].length; x++) 
			{
				if(goldStandard.get(i)[0][x] - threadedStandard[i][x] != 0) {h++;}
				subtractedRow.add(goldStandard.get(i)[0][x] - threadedStandard[i][x]);
			}
			
			Integer[][] tmpArray = new Integer[1][subtractedRow.size()];
			for(int u = 0; u < subtractedRow.size(); u++) 
			{
				tmpArray[0][u] = subtractedRow.get(u);
			}
			subtractedRow.clear();
			subtractedMatrix.add(tmpArray);
		}
		System.out.println(h);
		return subtractedMatrix;
	}
}
