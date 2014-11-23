package nl.tomsanders.robotica2.hub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

@SuppressWarnings("serial")
public class BrickManager extends JFrame 
		implements ListCellRenderer<BrickEndpoint>
{
	public static interface Listener
	{
		public void brickSelected(BrickEndpoint endpoint);
	}
	
	private List<Listener> brickSelectionListeners;
	private TimerTask discoveryTask;

	private JPanel brickListPane;
	private JList<BrickEndpoint> brickList;
	
	private JPanel statusPane;
	private JLabel statusLabel;
	
	public BrickManager() throws IOException
	{
		super("Brick Manager");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(500, 400);
		this.initializeComponents();
		
		this.brickSelectionListeners = new ArrayList<Listener>();
		this.discoveryTask = new TimerTask() {
			@Override
			public void run() 
			{
				discoverBricks();
			}
		};
		new Timer().scheduleAtFixedRate(this.discoveryTask, 0, 5000);
	}
	
	public void addListener(Listener listener)
	{
		this.brickSelectionListeners.add(listener);
	}
	
	public void removeListener(Listener listener) 
	{
		this.brickSelectionListeners.remove(listener);
	}

	private void discoverBricks() 
	{
		ArrayList<BrickEndpoint> endpoints = new ArrayList<>();
		try
		{
			for (BrickInfo info : BrickFinder.discover())
			{
				endpoints.add(new BrickEndpoint(
						info.getName(), InetAddress.getByName(info.getIPAddress())));
			}
			
			if (endpoints.size() > 0)
			{
				this.brickList.setListData(new Vector<BrickEndpoint>(endpoints));
				this.setContentPane(brickListPane);
			}
			else
			{
				this.statusLabel.setText("No bricks discovered");
				this.setContentPane(statusPane);
			}
		}
		catch (IOException e)
		{
			this.statusLabel.setText(e.getMessage());
			this.setContentPane(statusPane);
		}
		
		this.revalidate();
	}

	private void initializeComponents() 
	{
		this.statusPane = new JPanel(new BorderLayout());
		this.statusLabel = new JLabel("Scanning...");
		this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.statusLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.statusPane.add(this.statusLabel);
		
		this.brickListPane = new JPanel(new BorderLayout());
		this.brickList = new JList<BrickEndpoint>();
		this.brickList.setCellRenderer(this);
		this.brickList.addListSelectionListener(this::onBrickSelected);
		this.brickListPane.add(this.brickList);
		
		this.setContentPane(this.statusPane);
	}
	
	public void onBrickSelected(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
		
		BrickEndpoint selected = this.brickList.getSelectedValue();
		
		// Notify listeners
		for (Listener listener : this.brickSelectionListeners)
		{
			if (listener != null)
				listener.brickSelected(selected);
		}
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends BrickEndpoint> list, 
			BrickEndpoint value, int index, boolean isSelected, boolean cellHasFocus) 
	{
		JPanel cell = new JPanel();
		cell.setBackground(Color.WHITE);
		cell.setLayout(new BoxLayout(cell, BoxLayout.PAGE_AXIS));
		
		cell.add(new JLabel(value.getName()));
		cell.add(new JLabel(value.getAddress().toString()));
		
		return cell;
	}
}
