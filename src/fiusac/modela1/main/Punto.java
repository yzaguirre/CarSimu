package fiusac.modela1.main;

public class Punto {
	/**Puede ser
	 * Velocidad y tiempo
	 * o
	 * Distancia y tiempo
	 * **/
	public double x, v, t;
	
	/**
	 * @param x
	 * @param v
	 * @param t
	 */
	public Punto(double x, double v, double t) {
		this.x = x;
		this.v = v;
		this.t = t;
	}

}
