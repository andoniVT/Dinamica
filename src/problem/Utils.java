package problem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Utils 
{
	
	static double minimun(double a, double b, double c)
	{
		double result = Math.min(a, b);
		result = Math.min(result, c);		
		return result;
	}
	
	public static String readFile(String filename) throws IOException
	{
	    String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){reader.close();}
	    }
	    return content;
	}
	
	public static Vector read1D(String filename) throws IOException
	{
		String content = readFile(filename);
		Vector data1D = new Vector();				
		while(content.indexOf("\n")!=-1)
		{
			String line = content.substring(0, content.indexOf("\n"));
			content = content.substring(content.indexOf("\n")+1);
			Vector pair = new Vector();
			int label = Integer.parseInt(line.substring(0, line.indexOf(" ")));
			String values = line.substring(line.indexOf(" ")+1);
			String[] split = values.split("\\s+");
			double[] vector_values = new double[split.length];
			for(int i=0; i<split.length; i++)			
				vector_values[i] = Double.parseDouble(split[i]) ;
			pair.add(label);
			pair.add(vector_values);
			data1D.add(pair);									
		}
		return data1D;		
	}
	
	public static Vector read3D(String filename) throws IOException
	{
		String content = readFile(filename);
		Vector data3D = new Vector();
		while(content.indexOf("\n")!=-1)
		{
			String line = content.substring(0, content.indexOf("\n"));
			content = content.substring(content.indexOf("\n")+1);
			int label = Integer.parseInt(line.substring(0, line.indexOf(" ")));
			String values = line.substring(line.indexOf(" ")+1);
			String[] split = values.split("\\s+");
			int size = split.length/3;
			double[][] matrix_value = new double[size][3];
			Vector pair = new Vector();
			int index = 0;			
			for(int i=0;i<size;i++)
			{
				for(int j=0;j<3;j++)
				{
					matrix_value[i][j] = Double.parseDouble(split[index]);
					index+=1;
				}
			}					
			pair.add(label);
			pair.add(matrix_value);
			data3D.add(pair);
		}		
		return data3D;
	}
	
	public static double mean(double[] data)
	{
		double result=0;
		for(int i=0; i<data.length;i++)
			result+=data[i];
		return result/data.length;
	}
	
	public static double variance(double[] data)
	{
		double result = 0;
		double average = mean(data);
		for(int i=0; i<data.length; i++)		
			result+= Math.pow(data[i]-average, 2); 		
		return result/data.length;
	}
	
		
	public static double[] normalize(double[] data)
	{
		double[] result = new double[data.length];
		double average = mean(data);
		double deviation = Math.sqrt(variance(data));		
		for(int i=0; i<data.length; i++)
			result[i] = (data[i]-average)/deviation;		
		return result; 
	}
	
	public static double[][] normalize_dimensions(double[][] data)
	{
		double[][] normalize_data = new double[data.length][data[0].length];
		double[] x = new double[data.length];
		double[] y = new double[data.length];
		double[] z = new double[data.length];
		for(int i=0; i<data.length; i++)
		{
			x[i] = data[i][0];
			y[i] = data[i][1];
			z[i] = data[i][2];
		}
		double[] normalized_x = normalize(x);
		double[] normalized_y = normalize(y);
		double[] normalized_z = normalize(z);
		
		for(int i=0; i<data.length; i++)
		{
			normalize_data[i][0] = normalized_x[i];
			normalize_data[i][1] = normalized_y[i];
			normalize_data[i][2] = normalized_z[i]; 
		}				
		return normalize_data;
	}
	
	
	public static double[][] distance3D(double[][] data, double[][] data2)
	{		
		double[][] matrix = new double[data2.length][data.length];		
		for(int i=0; i<data2.length; i++)
		{			
			for(int j=0; j<data.length; j++)
			{
				double distance = Math.pow(data[j][0]-data2[i][0], 2) + Math.pow(data[j][1]-data2[i][1], 2)+
						Math.pow(data[j][2]-data2[i][2], 2);				
				matrix[i][j] = distance;
			}
		}		
		return matrix;
	}
		
	
	public static Vector accuracy(Vector y_true, Vector y_predicted)
	{
		Vector data = new Vector();
		double result = 0;		
		for(int i=0; i<y_true.size();i++)
		{
			if(y_true.get(i)==y_predicted.get(i))
				result+=1;
		}
		data.add(result);
		data.add(result/y_true.size());		
		return data;
	}
	
	public static Vector getInput(String input)
	{
		Vector result = new Vector();
		String a = "1 0";
		String b = "1 1 50.0";
		String c = "3 0";
		String d = "3 1";
		String e = "3 2";
		
		char type = input.charAt(0);
		if(type == '1')
		{		     
		   char method = input.charAt(2);		   
		   if(method=='0')
		   {
			   result.add(true);
			   result.add(0.0);
		   }
		   else
		   {
			   result.add(false);
			   String value = input.substring(4);
			   result.add(Double.parseDouble(value));	
		   }
		}
		else
		{
			String method = String.valueOf(input.charAt(2));			
			result.add(Integer.parseInt(method));
		}
		
		
		return result;
	}
	
	
	public static void main(String[] args) throws IOException 
	{
		
       Vector res = getInput("3 2");
       System.out.println(res.elementAt(0));
       //System.out.println(res.elementAt(1));
		
			
		 
		

	}

}
