package airdefense;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x,y,width,height;
	protected BufferedImage image;
	protected int xSpeed,ySpeed;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int nx) {
		this.x=nx;
	}
	
	public void setY(int ny) {
		this.y=ny;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int nv) {
		this.width=nv;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int nv) {
		this.height=nv;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public abstract boolean outOfBounds();
	public abstract void nextStep();
	
	public boolean hitted(FlyingObject FO) {
		return (((FO.x >= this.x) && (FO.x < (this.x + this.width))) 
				|| ((FO.x + FO.width > this.x) && (FO.x + FO.width <= (this.x + this.width))))
				&& (((FO.y >= this.y) && (FO.y < (this.y + this.height))) 
						|| ((FO.y + FO.height > this.y) && (FO.y + FO.height <= (this.y + this.height))));
				
	}

}
