
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	private boolean WhiteRedcells; //boolean variable to control the loop
	private final int numberOfCells = 100; //number of cells
    private int numberOfCycles; //integer to keep track of number of cycles
    private int intDistance; //the distance covered for each cycle
	private ArrayList<Cell> cells; //arraylist to store cells
	private Random rand; //random variables to generate random coordinates and random types
	private int start; //index for the starting red blood cell
	private Cell startcell; //first cell(red blood cell)
	private File infile; //file to write to
	private PrintWriter pw; //print writer to write to the text file
	
	
	public Main()
	{
	  init(); //initializing variables
	  initCellType(); //initializing cell types
	  initCoordinates(); //initialzing cell coordinates
	  this.start = getStart(); //getting the  index of the cell to start from
	  this.startcell = cells.get(start); //cell to start from
	  int nextcell = 0; //index of cell to jump to
	  int numberofcells = 100;
	  
	  while(WhiteRedcells == true)
	  {
		  
		  nextcell = getRandomValue(numberofcells); //getting index of the cell to jump to
		  Cell currentcell = cells.get(nextcell); //getting the next cell using the index above(iterating through cells)
		  //distance between two cells(between the previous cell and the next cell)
		  int currentdistance = (int)getDistance(startcell.getX(),currentcell.getX(),startcell.getY(),currentcell.getY(),startcell.getZ(),currentcell.getZ());
		  //checking whether the distance of the cells are within 5000 units
		  if(currentdistance <= 5000)
		  {
			  startcell = currentcell; //setting the new cell to be the new old cell
			  intDistance = intDistance + currentdistance; //incrementing the distance within the current cycle
			  
		  }
	
		  //checking if the cycle is complete
		  //condition for each cycle
		 if(intDistance >= 5000)
		  {
			  //checking if the nano-virus is on a tumorous cell 
			  if(currentcell.getType().equals("Tumorous"))
			  {
				  cells.remove(nextcell); //killing/removing the cell
				  numberofcells = numberofcells -1;  //decrementing the number of cells
				  int newstart = getRandomValue(numberofcells); //index to move to a different cell for the next cycle
				  startcell = cells.get(newstart); //cell for the next cycle 
			  }
			  numberOfCycles = numberOfCycles+1;//incrementing the number of cycles
		      writeToFile(); //function to write to file after each cycle
		      intDistance = 0; //initializing the distance for the next cycle
		  }
		
		  ArrayList<Cell> redbloodcells = new ArrayList<Cell>(); //arraylist for redblood cells
		  ArrayList<Cell> whitebloodcells = new ArrayList<Cell>(); //arraylist for white blood cells
		  //condition for each and every 5 cycles
		  if(numberOfCycles>0 && numberOfCycles%5==0)
		  {	  
			  //populating whiteblood and redblood cells arraylist
			  for(int r=0;r<cells.size();r++)
			  {
				
				  if(cells.get(r).getType().equals("Red Blood Cell"))
				  {
					  redbloodcells.add(cells.get(r));
				  }
				  if(cells.get(r).getType().equals("White Blood Cell"))
				  {
					  whitebloodcells.add(cells.get(r));
				  }
			  }
			  
			  if(redbloodcells.size() != 0)
			  {
				  updateCells("Red Blood Cell"); //updating red blood cells
			  }
			  else
			  {
				  updateCells("White Blood Cell");//updating white blood cells
			  }
			  
			  if(redbloodcells.size() ==0 && whitebloodcells.size()==0)
			  {
				 
				  this.WhiteRedcells = false; //terminate the loop if there's no redblood or whiteblood cells
				  writeToFile();
			  }
			  
		  }
		 
		  
	  }
	  
			
	}
	//function used to terminate the loop if there's no cells left or if there's only tumourous cells
	public void check()
	{
		int c =0;
		for(int r=0;r<cells.size();r++)
		{
			if(cells.get(r).getType() == "Red Blood Cell")
			{
				c++; //increment the integer if there's a red blood cell
			}
			if(cells.get(r).getType() == "White Blood Cell")
			{
				c++; //increment the integer if there's a white blood cell
			}
		}
		//if value remains the same then terminate the loop
		if(c==0)
		{
			this.WhiteRedcells = false;
		}
	}
	//function used to update the cells after every 5 five cycles
	public void updateCells(String type)
	{
		
		int count = 0;  
		int min = 0; //current min value(closest value) to the tumerous cell
		int index = 0; //index for the closest cell to the tumorous cell
		int distance =0; //holds the closest distance to the tumorous cell
		for(int r=0;r<cells.size();r++)
		{
			
			//for each tumorous cell checking the closest red blood and white blood cell
			if(cells.get(r).getType().equals("Tumorous"))
			{
		 			
				for(int j=0;j<cells.size();j++)
				{
					if(cells.get(j).getType().equals(type))
					{
						count = count+1;
						
						if(count==1)
						{   //first distance value to compare with other distances
							min = (int)(getDistance(cells.get(r).getX(),cells.get(j).getX(),cells.get(r).getY(),cells.get(j).getY(), cells.get(r).getZ(), cells.get(j).getZ()));
						}
						else
						{
						    //getting current distance between the red blood or white blood cell
							distance = (int)(getDistance(cells.get(r).getX(),cells.get(j).getX(),cells.get(r).getY(),cells.get(j).getY(), cells.get(r).getZ(), cells.get(j).getZ()));
						}
						if(min>distance)
						{
							min = distance; //reassiging the new closest(minimum) distance
							index = j; //index for the closest distance in the arraylist
						}
					}
				}
				
		
				
			}
			cells.get(index).setType("Tumorous");//changing the cell in the arraylist to tumourous cell
			
			
		}
		
	}
	
	//function to write the app's state for each cycle
	public void writeToFile()
	{
		
		pw.println("Cycle"+" "+numberOfCycles);
		pw.flush();
		pw.println("\n");
		pw.flush();
		
		for(int r=0;r<cells.size();r++)
		{
		
			pw.println(cells.get(r).toString());
			pw.flush();
			pw.println("\n");
			pw.flush();
		}
		
		
	}
	//function to get the first index to start from
	public int getStart()
	{
		int index = 0;
		for(int r=0;r<cells.size();r++)
		{
			index = getRandomValue(99);
			if(cells.get(index).getType().equals("Red Blood Cell"))
			{
				break;
			}
		}
		
		return index;
	}
	//initializing all variables in the class
	public void init()
	{
		this.WhiteRedcells = true;
		this.numberOfCycles = 0;
		this.intDistance = 0;
		this.cells = new ArrayList<Cell>();		
        this.rand = new Random();
		this.start = 0;
		this.infile = new File("data/Appstate");
		
		try {
			this.pw = new PrintWriter(infile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        for(int r=0;r<numberOfCells;r++)
		{
			 cells.add(new Cell());			
		}
	}
	//initializing each cell's type
	public void initCellType()
	{
	 
		String t = "Tumorous";
	    String w = "White Blood Cell";
	    String r = "Red Blood Cell";
	    
	    int inttumerous = 5; //number of tumerous cells
	    int intwhite = 25; //number of white blood cells
	    int intred  = 70; //number of red blood cells
	    
	    int counter = 0;
	    //while loop to generate random types for each cell in the array
	    while(counter<numberOfCells)
	    {
	    	int randomType = getRandomValue(3); //integer used to assign a type to each cell
	    
	    	if(randomType == 0)
	    	{
	    		//ensuring that the percentage of tumoruos cells is five
	    		if(inttumerous != 0)
	    		{
	    			cells.get(counter).setType(t);
	    			counter++;
	    			inttumerous--;
	    		}
	    		
	    	}
	    	if(randomType ==1)
	    	{
	    		//ensuring that the percentage of white blood cells is 25
	    		if(intwhite!=0)
	    		{
	    			cells.get(counter).setType(w);
	    			counter++;
	    			intwhite--;
	    		}
	    		
	    	}
	    	if(randomType==2)
	    	{
	    		//ensuring that the percenatge of red blood cells is 70
	    		if(intred !=0)
	    		{
	    			cells.get(counter).setType(r);
	    			counter++;
	    			intwhite--;
	    		}
	    		
	    	}
	 
	    }

		
	}
	//function to assign random coordiantes for each cell
	public void initCoordinates()
	{
		int counter = 0;
		while(counter<numberOfCells)
		{
			//generating random integer values for each cell's coordiantes
			int x = getRandomValue(1,5000);
			int y = getRandomValue(1,5000);
			int z = getRandomValue(1,5000);
			
		    cells.get(counter).setX(x);
		    cells.get(counter).setY(y);
		    cells.get(counter).setZ(z);
						
			counter++;
		}
	
		
		
	}
	//generating random integer values from [0,max)
	public int getRandomValue(int max)
	{
		return rand.nextInt(max);
	}
	//generating random integer values from [min,max]
    public int getRandomValue(int min,int max)
    {
    	return rand.nextInt((max - min) + 1) + min;
    }
    //generating the distance between two cells
	public double getDistance(int x,int x1,int y,int y1,int z,int z1)
	{
		
		double sum = (Math.pow((x-x1), 2) + Math.pow((y-y1), 2) + Math.pow((z-z1), 2));
		double d = Math.sqrt(sum);
		return d;
	}
	
	public static void main(String[] args) 
	{ 
		Main m = new Main();
	}

}
