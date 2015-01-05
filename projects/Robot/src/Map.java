import java.util.LinkedList;
import java.util.List;


public class Map<T extends Object> {
	
	private class Block {
		private Object[] elements;
		
		@SuppressWarnings("unchecked")
		public Block() {
			this.elements = new Object[BLOCK_WIDTH * BLOCK_HEIGHT];
		}
	}
	
	private class Column {
		private int offset;
		
		public Column() {
			this.offset = 0;
		}
		
		public void set(int x, int y, T val) {
			
		}
	}
	
	private static final int BLOCK_WIDTH = 100;
	private static final int BLOCK_HEIGHT = 100;
	
	private List<List<T[]>> blockColumns;
	
	public Map() {
		this.blockColumns = new LinkedList<List<T[]>>();
		
	}
	
	public void get(int x, int y) {
		
	}
	
	public void set(int x, int y, T value) {
		
	}
}
