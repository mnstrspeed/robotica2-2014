package nl.tomsanders.awt;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class StaticStroke implements Stroke 
{
	private AffineTransform transform;
	private AffineTransform inverse;
	private Stroke stroke;
	
	public StaticStroke(Stroke stroke, Graphics2D g)
	{
		this.stroke = stroke;
		
		try {
			this.transform = new AffineTransform(g.getTransform());
			this.inverse = this.transform.createInverse();
		} 
		catch (NoninvertibleTransformException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Shape createStrokedShape(Shape p) 
	{
		return this.inverse.createTransformedShape(
				this.stroke.createStrokedShape(
						transform.createTransformedShape(p)));
	}

}
