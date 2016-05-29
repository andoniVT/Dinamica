package problem;

import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class KNN 
{
	
	Boolean type;
	double percentage;
	int method;
	Vector train;  
	Vector test; 	
	/*
	 *    1 Dimension 
	 *    True  Simple
	 *    False Bandas  
	 * */
	public KNN(Boolean type, double percentage) throws IOException
	{	    
		this.train = Utils.read1D("data/treino.txt");
		this.test =  Utils.read1D("data/teste.txt");
		this.type = type;
		this.percentage = percentage;
	}
	
	/*
	 * 3 Dimensions
	 * 0 Method1
	 * 1 Method2
	 * 2 Method3
	 * */
	public KNN(int method) throws IOException
	{
		this.train = Utils.read3D("data/treino3D.txt");
		this.test = Utils.read3D("data/teste3D.txt");
		this.method = method;
	}
		
	public void classify()
	{
		Vector y_true = new Vector();
		Vector y_predicted = new Vector();
		Boolean dimension = this.type==null;
		
		for(int i=0; i<this.test.size(); i++)
		{
			Vector temporal = (Vector) test.get(i);
			int class_label_test = (int) temporal.get(0);
			double[][] vector_test3D = null;
			double[] vector_test1D = null;
			if(dimension) // 3D
				vector_test3D = (double[][])temporal.get(1);
			else   // 1D
				vector_test1D = (double[])temporal.get(1);
			
			Vector distances = new Vector();
			Vector all_predicted = new Vector();
			
			for(int j=0; j<train.size(); j++)
			{
				Vector temporal2 = (Vector) train.get(j);
				int class_label_train = (int) temporal2.get(0);
				double[][] vector_train3D = null;
				double[] vector_train1D = null;
				if(dimension) //3D
					vector_train3D = (double[][])temporal2.get(1);
				else  // 1D
					vector_train1D = (double[])temporal2.get(1);
			
				double distance = 0;
				if(dimension) // 3D
				{
					if(this.method==0) /// Method 1
						distance = DTW.DTWDistance3D(vector_test3D, vector_train3D, true);
					if(this.method==1) /// Method 2
						distance = DTW.DTWDistance3D(vector_test3D, vector_train3D, false);
					if(this.method==2) /// Method 3
						distance = DTW.DTWDistance3D_v2(vector_test3D, vector_train3D);
				}
				else  // 1D
				{
					if(this.type) /// DTW Simple 
						distance = DTW.DTWDistance(vector_test1D, vector_train1D);
					else   /// DTW Bandas
						distance = DTW.DTWDistanceBandas(vector_test1D, vector_train1D, this.percentage);
				}				
				distances.add(distance);
				all_predicted.add(class_label_train);								
			}
			
			int index = distances.indexOf(Collections.min(distances));
			Vector predicted = new Vector();
			predicted.add(distances.get(index));predicted.add(all_predicted.get(index));			
			y_true.add(class_label_test);
			y_predicted.add(predicted.get(1));			
		}

		Vector final_results = Utils.accuracy(y_true, y_predicted);
		System.out.println("Data size: "+final_results.elementAt(0));
		System.out.println("Accuracy: "+final_results.elementAt(1));
	}
	 
	
	
	public static void main(String[] args) throws IOException 
	{		
		 /*
		  * KNN test = new KNN(true,0);
		  * KNN test = new KNN(false,0-10-20-100);
		  * KNN test = new KNN(0);
		  * KNN test = new KNN(1);
		  * KNN test = new KNN(2);
		  * 
		  * */
			long time_start, time_end , time;
		 	Scanner reader = new Scanner(System.in);
			System.out.println("Enter an option");
			String input = reader.nextLine();
			Vector res = Utils.getInput(input);					
			if (res.size()==1)
			{
				int option = (int) res.elementAt(0);
				KNN test = new KNN(option);
				time_start = System.currentTimeMillis();
				test.classify();
				time_end = System.currentTimeMillis();
			}
			else
			{
	            boolean option = (boolean) res.elementAt(0);
	            double value = (double) res.elementAt(1);
	            KNN test = new KNN(option,value);
	            time_start = System.currentTimeMillis();
	            test.classify();
	            time_end = System.currentTimeMillis();
			}
			time = TimeUnit.MILLISECONDS.toSeconds(time_end-time_start);									
			System.out.println("Time(s): " + time);
			
			
			
			
			
			
			
			
		 
		 

		 
		 
		 
		
	

	}

}
