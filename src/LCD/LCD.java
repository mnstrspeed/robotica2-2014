import java.util.ArrayList;

import lejos.hardware.lcd.TextLCD;

// topklasse, (C) 2014 kbasten
public class LCD{
	
	private TextLCD lcd;
	
	private LCDBoxer lines;
	
	public LCD(TextLCD lcd){
		this.lcd = lcd;
		this.lines = new LCDBoxer(); // good style, prachtig, schoon
	}
	
	public void add(String l){
		lines.add(l);
		draw();
	}
	
	private void draw(){
		for (int i = 0; i < lines.size(); i++){
			lcd.drawString(lines.get(i), 0, i);
		}
	}
	
	class LCDBoxer extends ArrayList<String> {
		
		public static final int MAX_LINES = 8;
		
		@Override public boolean add(String l){
			while (this.size() > MAX_LINES){
				this.remove(0);
			}
			return super.add(l);
		}
		
	}
	
}