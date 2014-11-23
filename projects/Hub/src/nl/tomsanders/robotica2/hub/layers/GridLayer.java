package nl.tomsanders.robotica2.hub.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;

import nl.tomsanders.awt.StaticStroke;
import nl.tomsanders.robotica2.hub.EnvironmentView;

public class GridLayer extends EnvironmentView.Layer
{
	private static final int VIEW_THRESHOLD = 5;
	
	public static final int METER = 1000;
	public static final int DECIMETER = 100;
	public static final int CENTIMETER = 10;
	public static final int MILLIMETER = 1;
	
	private final int scale;
	private final Color color;
	
	public GridLayer(int scale, Color color)
	{
		this.scale = scale;
		this.color = color;
	}
	
	@Override
	public void draw(Graphics2D g, int width, int height) 
	{
		if (scale * g.getTransform().getScaleX() < VIEW_THRESHOLD)
			return;
		
		g.setStroke(new StaticStroke(new BasicStroke(1f), g));
		g.setColor(this.color);

		try 
		{
			AffineTransform inverse = g.getTransform().createInverse();
			Rectangle2D bounds = inverse.createTransformedShape(
					new Rectangle2D.Double(0, 0, width, height)).getBounds2D();
			
			g.setFont(new Font("monospace", 10, Font.PLAIN)
				.deriveFont(10f * (float)inverse.getScaleY()));
			
			// Vertical lines
			for (int x = (int)bounds.getMinX(); x < (int)bounds.getMaxX(); x++)
				if (x % scale == 0)
					g.draw(new Line2D.Double(x, bounds.getMinY(), x, bounds.getMaxY()));
			// Horizontal lines
			for (int y = (int)bounds.getMinY(); y < (int)bounds.getMaxY(); y++)
				if (y % scale == 0)
				{
					g.draw(new Line2D.Double(bounds.getMinX(), y, bounds.getMaxX(), y));
					g.drawString(this.getIndicatorString(y), 
							(int)bounds.getMinX(), (int)y);
				}
		} 
		catch (NoninvertibleTransformException e) 
		{
			throw new RuntimeException(e);
		}
	}

	private String getIndicatorString(int y) 
	{
		return y == 0 ? "0 mm" : Integer.toString(y);
	}
}
