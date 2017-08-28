package cn.edu.hdu.lab.dao.uml;

public class SDRectangle implements Cloneable {
	@Override
	public Object clone() {   
		SDRectangle o = null;   
        try {   
            o = (SDRectangle) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        } 
        return o;   
    }
	
	private double left,right,top,bottom;
	public SDRectangle(double l,double t, double r, double b) {
		this.left = l;
		this.right = r;
		this.top = t;
		this.bottom = b;
	}
	
	public SDRectangle() {
		this(0,0,0,0);
	}
	public SDRectangle(SDRectangle rec) {
		this(rec.getLeft(), rec.getTop(), rec.getRight(), rec.getBottom());
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getRight() {
		return right;
	}
	public void setRight(double right) {
		this.right = right;
	}
	public double getTop() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public double getBottom() {
		return bottom;
	}
	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	@Override
	public String toString() {
		return "SDRectangle [left=" + left + ", right=" + right + ", top="
				+ top + ", bottom=" + bottom + "]";
	}
	
	
	
}
