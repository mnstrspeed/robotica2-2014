package robotica2.model;

import java.io.Serializable;

public class OccupancyMap implements Serializable
{
	private final int unit;
	private boolean[][] map;
	
	public OccupancyMap(boolean[][] map, int unit)
	{
		this.map = map;
		this.unit = unit;
	}
	
	public boolean[][] getMap()
	{
		return this.map;
	}
	
	public int getUnit()
	{
		return this.unit;
	}
}
