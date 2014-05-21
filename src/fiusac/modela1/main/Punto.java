package fiusac.modela1.main;
/**
 * Representa un estado de velocidad, desplazamiento y tiempo transcurrido de un vehiculo
 * **/
public class Punto {
	
	public double x, v, t;
	
	/**
	 * @param x desplazamiento
	 * @param v velocidad
	 * @param t tiempo
	 */
	public Punto(double x, double v, double t) {
		this.x = x;
		this.v = v;
		this.t = t;
	}

}
