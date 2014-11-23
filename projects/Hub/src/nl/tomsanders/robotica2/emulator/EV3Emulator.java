package nl.tomsanders.robotica2.emulator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

public class EV3Emulator 
{	
	public static void main(String[] args)
	{
		final EV3Emulator emulator = new EV3Emulator("EV3 Emulator");
		emulator.start();
		
		JButton button = new JButton("Stop emulator");
		button.addActionListener((e) -> {
			emulator.stop();
			System.exit(0);
		});
		
		JFrame frame = new JFrame("EV3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 100);
		frame.getContentPane().add(button);
		frame.setVisible(true);
	}
	
	private static final int DISCOVERY_PORT = 3016;
	
	private final String name;
	private TimerTask discoveryTask;
	
	public EV3Emulator(String name)
	{
		this.name = name;
	}
	
	public void start()
	{
		try 
		{
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			
			byte[] data = name.getBytes();
			DatagramPacket discoveryPacket = new DatagramPacket(data, data.length,
					InetAddress.getByName("255.255.255.255"), DISCOVERY_PORT);
			
			this.discoveryTask = new DiscoveryTask(socket, discoveryPacket);
			new Timer().scheduleAtFixedRate(this.discoveryTask, 0, 1000);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		if (this.discoveryTask != null)
			this.discoveryTask.cancel();
	}
	
	private class DiscoveryTask extends TimerTask
	{
		private final DatagramSocket socket;
		private final DatagramPacket discoveryPacket;
		
		public DiscoveryTask(DatagramSocket socket, DatagramPacket packet)
		{
			this.socket = socket;
			this.discoveryPacket = packet;
		}
		
		@Override
		public void run() 
		{
			try 
			{
				this.socket.send(this.discoveryPacket);
			} 
			catch (IOException e) 
			{
				throw new RuntimeException(e);
			}
		}
	}
}
