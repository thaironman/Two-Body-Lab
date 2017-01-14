package tools;

public class Vetor {
	private double modulo;
	private double x;
	private double y;
	
	public Vetor(double x, double y) {
		this.x = x;
		this.y = y;
		modulo = Math.sqrt((x*x)+(y*y));
	}

	public double getModulo() {
		return modulo;
	}

	public void setModulo(double modulo) {
		this.modulo = modulo;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void redefine(double x, double y){
		this.x = x;
		this.y = y;
		modulo = Math.sqrt((x*x)+(y*y));
	}
	
}