package game;

public class BoundingBox {

	public int x1, x2, y1, y2;
	
	public BoundingBox(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public boolean intersects(BoundingBox b){
		return (Math.abs(x1 - b.x1) * 2 < ((x2-x1) + (b.x2-b.x1))) &&
		         (Math.abs(y1 - b.y1) * 2 < ((y2-y1) + (b.y2-b.y1)));
	}
	
}
