package nl.tomsanders.robotica2.hub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EnvironmentView extends JFrame implements 
		MouseWheelListener, MouseMotionListener, MouseListener
{
	public static abstract class Layer
	{
		public abstract void draw(Graphics2D g, int width, int height);
	}
	
	private List<Layer> layers;
	private AffineTransform transform;
	
	public EnvironmentView()
	{
		super();
		
		this.setTitle("Environment");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200, 800);
		
		this.layers = new ArrayList<Layer>();
		
		this.transform = new AffineTransform();
		this.transform.translate(this.getWidth() / 2, this.getHeight() / 2);
		this.transform.scale(2, 2);
		
		this.getContentPane().add(new JPanel() {
			@Override
			public void paintComponent(Graphics g)
			{	
				Graphics2D g2d = (Graphics2D)g;
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				g2d.setTransform(transform);
				for (Layer layer : layers)
					layer.draw((Graphics2D)g, this.getWidth(), this.getHeight());
			}
		});
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
	}
	
	public void addLayer(Layer layer) 
	{
		this.layers.add(layer);
		this.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		int steps = e.getWheelRotation();
		
		this.transform.scale(1.0 - steps * 0.1, 1.0 - steps * 0.1);
		this.repaint();
	}

	private Point2D previousDragOrigin;
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		try 
		{
			AffineTransform inverse = this.transform.createInverse();
			
			this.transform.translate(
					(e.getX() - this.previousDragOrigin.getX()) * inverse.getScaleX(),
					(e.getY() - this.previousDragOrigin.getY()) * inverse.getScaleY());
			this.previousDragOrigin = e.getPoint();
			this.repaint();
			
		} 
		catch (NoninvertibleTransformException ex) 
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		this.previousDragOrigin = e.getPoint();
	}
	
	@Override public void mouseReleased(MouseEvent e) { }
	@Override public void mouseMoved(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent e) { }
	@Override public void mouseExited(MouseEvent e) { }
	@Override public void mouseClicked(MouseEvent e) {	}
}
