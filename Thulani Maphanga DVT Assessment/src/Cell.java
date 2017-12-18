
public class Cell {
	
	private int x,y,z; //cooridnates for the cell
	private String type; //type of the cell
	//constructor
	public Cell()
	{
		
	}
	//getter and setters for the class
	public void setX(int x)
	{
		this.x =x;
	}
	public void setY(int y)
	{
		this.y= y;
	}
	public void setZ(int z)
	{
		this.z= z;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getZ()
	{
		return this.z;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return this.type;
    }
	//toString function to return the string values of the class
    public String toString()
    {
    	return String.valueOf("Type"+"->"+type +" "+"Coordinates"+"->"+"("+x+","+y+","+z+")");
    }

}
