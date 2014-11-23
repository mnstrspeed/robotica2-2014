package nl.tomsanders.robotica2.hub;

public class OccupancyMap 
{
	private final int unit;
	private int[][] map;
	
	public OccupancyMap(int[][] map, int unit)
	{
		this.map = map;
		this.unit = unit;
	}
	
	public int[][] getMap()
	{
		return this.map;
	}
	
	public int getUnit()
	{
		return this.unit;
	}
}
