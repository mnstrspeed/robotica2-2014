package nl.tomsanders.robotica2.hub.layers;

import java.awt.Color;
import java.awt.Graphics2D;

import nl.tomsanders.robotica2.hub.EnvironmentView;
import robotica2.model.OccupancyMap;

public class OccupancyMapLayer extends EnvironmentView.Layer 
{
	private OccupancyMap map;

	public OccupancyMapLayer(OccupancyMap map)
	{
		this.map = map;
	}
	
	public void setMap(OccupancyMap map)
	{
		this.map = map;
	}
	
	@Override
	public void draw(Graphics2D g, int width, int height) 
	{
		g.setColor(Color.CYAN);
		if (this.map != null)
		{
			drawOccupancyMap(g);
		}
	}

	private void drawOccupancyMap(Graphics2D g) 
	{
		for (int y = 0; y < map.getMap().length; y++)
		{
			for (int x = 0; x < map.getMap()[y].length; x++)
			{
				if (map.getMap()[y][x])
					g.fillRect(x * map.getUnit(), y * map.getUnit(), 
							map.getUnit(), map.getUnit());
			}
		}
	}
	
}
