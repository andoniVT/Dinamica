package problem;

public class DTW 
{
	
	static double DTWDistance(double[] array1 , double[] array2)
	{
		int m = array1.length;
		int n = array2.length;
		double[][] distances = new double[n][m];
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<m; j++)
				distances[i][j] =  Math.pow(array1[j] - array2[i], 2);			
		}
		
		double[][] cost = new double[n][m];
		cost[0][0] = distances[0][0];
		
		for(int i=1; i<m; i++)
			cost[0][i] = distances[0][i] + cost[0][i-1];
		
		for(int i=1; i<n;i++)
			cost[i][0] = distances[i][0] + cost[i-1][0];
		
		for(int i=1;i<n;i++)
		{
			for(int j=1;j<m;j++)
				cost[i][j] = Utils.minimun(cost[i-1][j-1] , cost[i-1][j], cost[i][j-1]) + distances[i][j];  
		}							
		return cost[n-1][m-1];
	}
	
	static double DTWDistanceBandas(double[] array1 , double[] array2, double percentage)
	{		
		int m = array1.length;
		int n = array2.length;
		double size_band = percentage*(Math.max(m, n))/100; 
		//double size_band = ((percentage*(Math.min(m, n)-1))/100)+1;
		//System.out.println("size baand" + size_band);
		
		double[][] distances = new double[m][n];
		for(int i=0; i<m; i++)
		{
			for(int j=0; j<n; j++)
				distances[i][j] =  Math.pow(array1[i] - array2[j], 2);			
		}
		
		double[][] cost = new double[m][n];
		cost[0][0] = distances[0][0];
		
		for(int i=1; i<n; i++)
			cost[0][i] = distances[0][i] + cost[0][i-1];
		
		for(int i=1; i<m;i++)
			cost[i][0] = distances[i][0] + cost[i-1][0];
		
		double S = (double) n/m;
		
		for(int i=1;i<m;i++)
		{
			for(int j=1;j<n;j++)
			{				
				if(Math.abs(i-(j/S))>size_band)
					continue;
				
				cost[i][j] = Utils.minimun(cost[i-1][j-1] , cost[i-1][j], cost[i][j-1]) + distances[i][j]; 
			}
		}
		//for(int i=0;i<m;i++)
		//{
			//for(int j=0; j<n; j++)
	//			System.out.print(cost[i][j]+ " ");
	//		System.out.println(" ");
	//	}
		
		
		return cost[m-1][n-1];	
	}
	
	
	static double DTWDistanceBandas2(double[] array1 , double[] array2, double w)
	{		
		int m = array1.length;
		int n = array2.length;
		//double size_band = percentage;
		//w = Math.max(w, Math.abs(m-n));
		
		w = Math.max(m, n)*w/100;
		w = Math.max(w, Math.abs(m-n));
		//double size_band = ((percentage*(Math.min(m, n)-1))/100)+1;
		//System.out.println("size baand" + size_band);
		
		double[][] distances = new double[m][n];
		for(int i=0; i<m; i++)
		{
			for(int j=0; j<n; j++)
				distances[i][j] =  Math.pow(array1[i] - array2[j], 2);			
		}
		
		double[][] cost = new double[m][n];
		cost[0][0] = distances[0][0];
		
		//for(int i=1; i<n; i++)
		//	cost[0][i] = distances[0][i] + cost[0][i-1];
		
		//for(int i=1; i<m;i++)
		//	cost[i][0] = distances[i][0] + cost[i-1][0];
		
		//double S = (double) n/m;
		
		for(int i=1;i<m;i++)
		{
			//for(int j=1;j<n;j++)
			for(int j=(int) Math.max(1,i-w);j<Math.min(n, i+w);j++)
			{												
				cost[i][j] = Utils.minimun(cost[i-1][j-1] , cost[i-1][j], cost[i][j-1]) + distances[i][j]; 
			}
		}
		/*for(int i=0;i<m;i++)
		{
			for(int j=0; j<n; j++)
				System.out.print(cost[i][j]+ " ");
			System.out.println(" ");
		}*/
		
		
		return cost[m-1][n-1];	
	}
	
	static double DTWDistance3D(double[][] array1 , double[][] array2 , boolean toNormalize)
	{
		int m = array1.length;
		int n = array2.length;		
		double[][] distanceMatrix = null;
		if (toNormalize)
		{
			double[][] norm1 = Utils.normalize_dimensions(array1); 
			double[][] norm2 = Utils.normalize_dimensions(array2);
			distanceMatrix = Utils.distance3D(norm1, norm2);
		}
		else		
			distanceMatrix = Utils.distance3D(array1, array2);
							
		double[][] cost = new double[n][m];
		cost[0][0] = distanceMatrix[0][0];
		
		for(int i=1; i<m; i++)
			cost[0][i] = distanceMatrix[0][i] + cost[0][i-1];
		
		for(int i=1; i<n; i++)
			cost[i][0] = distanceMatrix[i][0] + cost[i-1][0];
		
		for(int i=1;i<n;i++)
		{
			for(int j=1;j<m;j++)
				cost[i][j] = Utils.minimun(cost[i-1][j-1] , cost[i-1][j], cost[i][j-1]) + distanceMatrix[i][j];
		}								
		return cost[n-1][m-1];
	}
	
	static double DTWDistance3D_v2(double[][] array1, double[][] array2)
	{
	   double[] array1_x = new double[array1.length], array1_y = new double[array1.length], array1_z = new double[array1.length];
	   double[] array2_x = new double[array2.length], array2_y = new double[array2.length], array2_z = new double[array2.length];
	   
	   for(int i=0;i<3; i++)
	   {
		   for(int j=0;j<array1.length;j++)
		   {
			   if(i==0)
				   array1_x[j] = array1[j][i];
			   if(i==1)
				   array1_y[j] = array1[j][i];
			   if(i==2)
				   array1_z[j] = array1[j][i];			  
		   }
		   for(int j=0;j<array2.length;j++)
		   {
			   if(i==0)
				   array2_x[j] = array2[j][i];
			   if(i==1)
				   array2_y[j] = array2[j][i];
			   if(i==2)
				   array2_z[j] = array2[j][i];			  
		   }
	   }         
		double distanceX =  DTWDistance(array1_x , array2_x);
		double distanceY =  DTWDistance(array1_y , array2_y);
		double distanceZ =  DTWDistance(array1_z , array2_z);		
		return (distanceX+distanceY+distanceZ)/3;	
	}
	
	
	
	
	public static void main(String[] args) 
	{
		//double[] A = {1,2,3,4};
		//double[] B = {1,2,3,4,5};
		//System.out.println(DTWDistance(A, B));
		
		//double[][] a = {{1,2,3},{4,5,6}, {7,8,9}};
		//double[][] b = {{5,0,12}, {6,9,1}};
		/// 1 4 7   - 5  6
		/// 2 5 8   - 0  9
		/// 3 6 9   - 12 1
		//System.out.println(DTWDistance3D(a, b, true));
		//System.out.println(DTWDistance3D(a, b, false));
		//System.out.println(DTWDistance3D_v2(a, b)) ;
		//http://alexminnaar.com/time-series-classification-and-clustering-with-python.html
		
		double[] A = {7,1,5,4,10,15,3,1,6,12,5};
		double[] B = {15,31,2,12,5,9,18,41,3,5,4,6};
		
		//double[] A = {0.29167,0.27778,0.25,0.25,0.23611,0.20833,0.18056,0.16667,0.15278,0.15278,0.16667,0.19444,0.22222,0.26389,0.27778,0.30556,0.36111,0.375,0.375,0.375,0.375,0.40278,0.41667,0.43056,0.44444,0.41667,0.40278,0.34722,0.27778,0.22222,0.18056,0.13889,0.125,0.11111,0.15278,0.16667,0.19444,0.25,0.27778,0.36111,0.41667,0.44444,0.45833,0.47222,0.54167,0.55556,0.56944,0.58333,0.58333,0.59722,0.63889,0.65278,0.65278,0.65278,0.63889,0.63889,0.61111,0.5,0.375,0.33333,0.25,0.20833,0.20833,0.19444,0.22222,0.23611,0.23611,0.23611,0.23611,0.23611,0.26389,0.31944,0.45833,0.41667,0.45833,0.47222,0.47222,0.45833,0.43056,0.40278,0.34722,0.29167,0.26389,0.25,0.16667,0.125,0.083333,0.083333,0.13889,0.19444};
		//double[] B = {0.30556,0.52778, 0.73611}; 
		System.out.println(A.length + " " + B.length);
		
		
		//System.out.println(DTWDistanceBandas(A , B, Math.max(A.length, B.length)));
		///w = (std::max(A.size(), B.size())) * (float) w/100.0;
		System.out.println(DTWDistanceBandas2(A , B, 50));
		System.out.println(DTWDistance(A , B));
	}

}
