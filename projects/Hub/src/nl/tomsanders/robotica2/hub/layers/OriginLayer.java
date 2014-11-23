package nl.tomsanders.robotica2.hub.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;

import nl.tomsanders.awt.StaticStroke;
import nl.tomsanders.robotica2.hub.EnvironmentView.Layer;

public class OriginLayer extends Layer {

	@Override
	public void draw(Graphics2D g, int width, int height) 
	{
		g.setStroke(new StaticStroke(new BasicStroke(3f), g));
		g.setColor(Color.BLUE);
		
		try
		{
			AffineTransform inverse = g.getTransform().createInverse();
			
			g.draw(new Line2D.Double(-50 * inverse.getScaleX(), 0, 50 * inverse.getScaleX(), 0));
			g.draw(new Line2D.Double(0, -50 * inverse.getScaleY(), 0, 50 * inverse.getScaleX()));
		}
		catch (NoninvertibleTransformException ex)
		{
			ex.printStackTrace();
		}
	}

}
