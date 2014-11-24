package go;

import java.util.ArrayList;

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
		if(distance.isInfinite()){
			return new Pair(-1, -1);
		} else {
			int x = (int) Math.cos(angle);
			int y = (int) Math.sin(angle);
			int a = (int) (distance * 100);
			int b = (int) (distance * 100);
			return new Pair(a * x + 250/2, b * y);
		}
	}
	
	private void colourCoord(Pair<Integer,Integer> coord) {
		matrix[(int)coord.getFirst()][(int)coord.getSecond()] = true;
	}
	
	public void enterTheMatrix() {
		for(int i = 0; i < results.size(); i++) {
			int angle = i*5;
			if(i == 0 || i == 35) {
				for(int j = 0; j < 15; j++) {
					Pair p = computeCoord(angle, results.get(i));
					if (!p.getFirst().equals(-1)) {
						colourCoord(p);
					}
				}
			} else {
				for (int k = 0; k < 5; k++) {
					Pair p = computeCoord(angle+10+k,results.get(i));
					if (!p.getFirst().equals(-1)) {
						colourCoord(p);
					}
				}
			}
		}
	}
	
	private String printRow(int row){
		String r = "";
		for (int j = 0; j < 250; j++) {
			r += matrix[row][j] ? "1" : "0";
		}
		
		return r;
	}
	
	public boolean[][] getMap()
	{
		return matrix;
	}
	
	public void print() {
		for (int i = 0; i < 250; i++) {
			System.out.println(printRow(i));
		}
	}
	
}
