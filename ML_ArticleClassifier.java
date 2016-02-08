import java.io.*;
import java.util.*;
import java.lang.*;


public  class ML_ArticleClassifier
{

	public static  void main(String[] args) throws FileNotFoundException
	{
		

		int p,q,m,n,a,b,c,l,k,t;

		
		
	String files[]={"file_0.txt","file_1.txt","file_2.txt","file_3.txt","file_4.txt"};
	String classes[]={"rec.autos","rec.motorcycles","talk.politics.misc","rec.sport.baseball","rec.sport.hockey","talk.politics.mideast","talk.politics.guns","talk.religion.misc"};
	
	

	HashMap hm[][] = new HashMap [5][8];
	HashSet hs[] = new HashSet[5];
	int arr1[][] = new int[5][8];
	int arr2[][] = new int[5][8];
	
	for( p=0;p<5;p++)
	{
		for(q=0;q<8;q++)
		{
			arr1[p][q] = 0;
			arr2[p][q] = 0;
		}

	}


	for(int x=0;x<5;x++)
	{
		for(int y=0;y<8;y++)
		{
			hm[x][y] = new HashMap<String, Integer>();
			
		}
	}




for(int j=0;j<5;j++)
{


	hs[j] = new HashSet<String>();

			
		

	for(int i=0;i<5;i++)
	{
		
	if(j!=i)
	{

		try
		{
			Scanner s=new Scanner(new FileInputStream(files[i]));
			

		while (s.hasNextLine())
		{
			
			String str[]=s.nextLine().split("\\s+");  
			for(k=0;k<8;k++)
			{
				if(classes[k].equalsIgnoreCase(str[0]))
					break;
			}
			
			arr1[j][k] += str.length-1;
			arr2[j][k] += 1;
			
				

			for(int u=1;u<str.length;u++)
			{
				
				String sw = str[u];
				
				hs[j].add(sw);


				if(hm[j][k].containsKey(sw))
				{
					
					t = new Integer((Integer)hm[j][k].get(sw)).intValue();
					t++;
					hm[j][k].put(sw,Integer.valueOf(t));
				}
				else
				{
					hm[j][k].put(sw,Integer.valueOf(1));
				}
			} 
			
 			

		}

		}
		catch(FileNotFoundException e)
		{
			System.out.println("FILE NOT FOUND");
			
		}





	}
	else
		continue;
	}	
	
}

	double pc[][] = new double[5][8];
	double total[] = {0,0,0,0,0};
	
	for(int y=0;y<5;y++)
	{
		for(int z=0;z<8;z++)
		{
			total[y] += arr2[y][z];
			
		}
		
	}

	for(int y=0;y<5;y++)
	{
		for(int z=0;z<8;z++)
		{
			 pc[y][z] = arr2[y][z]/(double)total[y];
			
		}
		
	}
	

	double  prob[] = new double[8];
	int correct;

	for(int i=0;i<5;i++)
	{
		for(int j=0;j<5;j++)
		{

			correct = 0;

			if(j==i)
			{
				
				try
				{
					Scanner s=new Scanner(new FileInputStream(files[j]));
			    
			    	while (s.hasNextLine())
					{
					
					
					
					for( p=0;p<8;p++)
					{
						prob[p] = 0;
					}





					String str[]=s.nextLine().split("\\s+");   
					for( l=0;l<8;l++)
					{
						if(classes[l].equalsIgnoreCase(str[0]))
						 break;
					}


					for(k=0;k<8;k++)
					{

						for(int e=1;e<str.length;e++)
						{
					
						String sw = str[e];
						
						try
						{a = new Integer((Integer)hm[j][k].get(sw)).intValue();}
						catch(NullPointerException nlp)
						{
							a = 0;
						}
						b = arr1[j][k];
						c = hs[j].size();
						

						double x = (a+1)/(double)(b+c);
						prob[k] += Math.log(x);

	
						}

						prob[k] += Math.log(pc[j][k]);

					}

					double max = prob[0];
					int clss = 0;

					for( p=0;p<8;p++)
					{
						
						if(prob[p]>max)
						{
							max = prob[p];
							clss = p;
							
						}
					}

					

					if(clss==l)
					{
						correct++;
					}





					}
			}

			catch(FileNotFoundException e)
			{
				System.out.println("FILE NOT FOUND");
			
			}



				double percentage;
				percentage = (correct*100)/(double)1446 ;

				System.out.println(" TESTING ON " + files[j] + " ACCURACY IS " + percentage + "%");



			}	
			else
				continue;
		}

	}	


	}


}
