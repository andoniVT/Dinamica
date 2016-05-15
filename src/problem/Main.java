package problem;

public class Main {

	static int minimun(int a, int b, int c)
	{
		int result = Math.min(a, b);
		result = Math.min(result, c);		
		return result;
	}
	
	static int DTWDistance(int[] array1 , int[] array2)
	{
		int n = array1.length;
		int m = array2.length;
		
		int [][] matrix = new int [n][m] ;
		
	
		
		for(int i=1; i<n; i++)			
		{
			matrix[i][0] = 99999;
		}
		for(int i=1; i<m;i++)
		{
			matrix[0][i] = 99999;
		}
		
		matrix[0][0] = 0;
		
		for(int i=1; i<n;i++)
		{
			for(int j=1; j<m;j++)
			{
				int costo = Math.abs(array1[i]-array2[j]);
				matrix[i][j] = costo + minimun(matrix[i-1][j], matrix[i][j-1], matrix[i-1][j-1]); 
			}
		}
		
		System.out.println(n);
		System.out.println(m);
		
		for(int i=0; i<n; i++)
		{
			for(int j=0;j<m;j++)
			{
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
		return matrix[n-1][m-1];
	}
	
	
	
	public static void main(String[] args) 
	{
		int[] array1 = {1,2,3,4} ;
		int[] array2 = {1,2,3,4,5};
		System.out.println(DTWDistance(array1, array2));

	}

}
