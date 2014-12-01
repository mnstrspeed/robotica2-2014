package go;

import java.util.ArrayList;

import lejos.hardware.Button;

public class Mapper {

	private boolean [][] matrix;
	private ArrayList<Float>results;
	
	public Mapper(ArrayList<Float> results) {
		matrix = new boolean[250][250];
		for(int i = 0; i < 250; i++) {
			for(int j = 0; j < 250; j++) {
				matrix[i][j] = false;
			}
		}
		this.results = results;
	}
	
	private Pair<Integer, Integer> computeCoord(int angle, Float distance){
		System.out.println(angle);
		if(distance.isInfinite()){
			return new Pair(-1, -1);
		} else {
			float x = (float) Math.cos(Math.toRadians(angle));
			float y = (float) Math.sin(Math.toRadians(angle));
			int a = (int) (distance * 100);
			int b = (int) (distance * 100);
			Pair<Integer, Integer> p = new Pair((int)(a * x) + 125, (int) (b * y)); 
			//System.out.println(p + " "+ angle + " " + a + " " + x + " " + y);
		//	Button.waitForAnyPress();
			return p;
		}
	}
	
	private void colourCoord(Pair<Integer,Integer> coord) {
		System.out.println(coord.getFirst() + " " + coord.getSecond());
		if (!(coord.getFirst() < 0) && !(coord.getSecond() < 0)) {
			matrix[coord.getFirst()][coord.getSecond()] = true;
		}

	}
	
	public void enterTheMatrix() {
		for(int i = 0; i < results.size(); i++) {
			int angle = i*5 + 10;
			System.out.println(angle);
			if(i == 0) {
				for(int j = 0; j <= 15; j++) {
					Pair p = computeCoord(j, results.get(i));
					if (!p.getFirst().equals(-1)) {
						colourCoord(p);
					}
				}
			} else {
				for (int k = 1; k <= 5; k++) {
					Pair p = computeCoord(angle+k,results.get(i));
					if (!p.getFirst().equals(-1)) {
						colourCoord(p);
					}
				}
			}
			if (angle == 175) {
				return;
			}
		}
	}
	
	public boolean[][] getMap()
	{
		return matrix;
	}
	
}
