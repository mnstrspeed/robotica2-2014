package nl.tomsanders.robotica2.hub;

import java.io.Serializable;

public class OccupancyMap implements Serializable
{
	private static final long serialVersionUID = 9016237103757952488L;
	
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
	
	public void set(int x, int y, int value)
	{
		this.map[y][x] = value;
	}
}
