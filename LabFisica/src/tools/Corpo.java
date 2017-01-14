package tools;

public class Corpo {
	private double massa;
	public Ponto p;
	public Velocidade v;
	public Aceleracao a;
	
	public Corpo(double massa, Ponto p, Velocidade v, Aceleracao a){
		this.massa = massa;
		this.p = p;
		this.v = v;
		this.a = a;
	}
	
	public double getMassa() {
		return massa;
	}
	public void setMassa(double massa) {
		this.massa = massa;
	}
	
	public void colisao(Corpo c){
		double vx;
		double vy;
		double ux;
		double uy;
		vx = (this.v.getX()*(this.massa-c.massa)+(2*c.massa*c.v.getX()))/(this.massa+c.massa);
		vy = (this.v.getY()*(this.massa-c.massa)+(2*c.massa*c.v.getY()))/(this.massa+c.massa);
		ux = (c.v.getX()*(c.massa-this.massa)+(2*this.massa*this.v.getX()))/(this.massa+c.massa);
		uy = (c.v.getY()*(c.massa-this.massa)+(2*this.massa*this.v.getY()))/(this.massa+c.massa);
		this.v.redefine(vx, vy);
		c.v.redefine(ux, uy);
	}
	
}
