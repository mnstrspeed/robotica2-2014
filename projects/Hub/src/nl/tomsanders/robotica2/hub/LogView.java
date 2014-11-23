package nl.tomsanders.robotica2.hub;

import java.awt.Font;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nl.tomsanders.robotica2.logging.Log;

@SuppressWarnings("serial")
public class LogView extends JFrame implements Observer
{
	private JTextArea textArea;
	
	public LogView()
	{
		super();
		
		this.setTitle("Log");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 800);
		
		Log.getInstance().addObserver(this);
		
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setBorder(BorderFactory.createEmptyBorder());
		this.textArea.setFont(new Font("mono", Font.PLAIN, 14));
		this.updateTextArea();
		this.getContentPane().add(new JScrollPane(textArea));
	}

	@Override
	public void update(Observable o, Object arg)
	{
		this.updateTextArea();
		this.repaint();
	}

	private void updateTextArea()
	{
		StringBuilder builder = new StringBuilder();
		for (Log.Entry entry : Log.getInstance().getEntries())
		{
			builder.append(new Date(entry.time).toString());
			builder.append(": ");
			builder.append(entry.message);
			builder.append("\n");
		}
		this.textArea.setText(builder.toString());
	}
}
