package problem;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class Main 
{

	
	public static void knn() throws IOException
	{
		Vector train = Utils.read1D("data/treino.txt");
		Vector test = Utils.read1D("data/teste.txt");
		
		Vector y_true = new Vector();
		Vector y_predicted = new Vector();
		
		for(int i=0; i<test.size(); i++)
		{
			Vector temporal = (Vector) test.get(i);
			int class_label_test = (int) temporal.get(0);
			double[] vector_test = (double[])temporal.get(1);			
			
			Vector distances = new Vector();
			Vector all_predicted = new Vector();
			Vector allsizes = new Vector();
			
			for(int j=0; j<train.size(); j++)
			{
				Vector temporal2 = (Vector) train.get(j);
				int class_label_train = (int) temporal2.get(0);
				double[] vector_train = (double[])temporal2.get(1);
	
				double distanceSimple = DTW.DTWDistance(vector_test, vector_train);
				double distanceBandas = DTW.DTWDistanceBandas(vector_test, vector_train, 100);
				Vector sizes  = new Vector();
				System.out.println(vector_test.length + " " + vector_train.length);
				//if(distanceSimple==distanceBandas)
				//	System.out.println(distanceSimple + " " + distanceBandas);
				
				distances.add(distanceBandas);
				all_predicted.add(class_label_train);
				allsizes.add(sizes);
				
				//System.out.println(distances.indexOf(Collections.min(distances)));	
			}
			int index = distances.indexOf(Collections.min(distances));
			Vector predicted = new Vector();
			predicted.add(distances.get(index));predicted.add(all_predicted.get(index));
			//System.out.println(predicted);
			
			
			
			
			y_true.add(class_label_test);
			y_predicted.add(predicted.get(1));
			
			//System.out.println(i);
			
		}
		System.out.println(Utils.accuracy(y_true, y_predicted));			
	}
	
	
	
	public static void main(String[] args) throws IOException 
	{
		knn();
	
		
	}

}
