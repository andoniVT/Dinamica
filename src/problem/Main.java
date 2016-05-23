package problem;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class Main 
{

	
	public static void knn() throws IOException
	{
		//Vector train = Utils.read1D("data/treino.txt");
		//Vector test = Utils.read1D("data/teste.txt");
		Vector train = Utils.read3D("data/treino3D.txt");
		Vector test = Utils.read3D("data/teste3D.txt");
		Vector y_true = new Vector();
		Vector y_predicted = new Vector();
		
		for(int i=0; i<test.size(); i++)
		{
			Vector temporal = (Vector) test.get(i);
			int class_label_test = (int) temporal.get(0);
			//double[] vector_test = (double[])temporal.get(1);			
			double[][] vector_test = (double[][])temporal.get(1);
			Vector distances = new Vector();
			Vector all_predicted = new Vector();
			
			for(int j=0; j<train.size(); j++)
			{
				Vector temporal2 = (Vector) train.get(j);
				int class_label_train = (int) temporal2.get(0);
				//double[] vector_train = (double[])temporal2.get(1);
				double[][] vector_train = (double[][])temporal2.get(1);
				//double distance = DTW.DTWDistance(vector_test, vector_train);
				double distance = DTW.DTWDistance3D_v2(vector_test, vector_train);
				distances.add(distance);
				all_predicted.add(class_label_train);
				
				//System.out.println(distances.indexOf(Collections.min(distances)));	
			}
			int index = distances.indexOf(Collections.min(distances));
			Vector predicted = new Vector();
			predicted.add(distances.get(index));predicted.add(all_predicted.get(index));
			System.out.println(predicted);
			y_true.add(class_label_test);
			y_predicted.add(predicted.get(1));
			
			//System.out.println(i);
			
		}
		System.out.println(Utils.accuracy(y_true, y_predicted));			
	}
	
	
	
	public static void main(String[] args) throws IOException 
	{
		//knn();
	
		
	}

}
