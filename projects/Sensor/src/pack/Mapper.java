package pack;

import java.util.ArrayList;

public class Mapper {

	private byte [][] map;
	
	public Mapper(){
		map = new byte[200][200];
	}
	
	public void rawValues(ArrayList<Integer> values) {
		// RConsole.open();
		for(int i = 0; i < values.size(); i++) {
			System.out.println(values.get(i));
		}
		//RConsole.close();
	}
	
}
