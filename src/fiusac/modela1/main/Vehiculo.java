/**
 * 
 */
package fiusac.modela1.main;

import static fiusac.modela1.utilities.FicheroES.ic;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.text.Position;

import fiusac.modela1.dibujo.Dibujable;

/**
 * @author david
 *
 */
public class Vehiculo implements Dibujable{
	/**velocidad final de 200km/h en m/s**/
	public static final double vel200;
	static {
		vel200 = toMS(200);
	}
	public String marca, modelo;
	/**Peso en kg**/
	private int peso;
	/**velocidad maxima en ms/s**/
	public double velmax;
	/**aceleracion en segundos de 0 a 100 km/h y 0 a 200 km/h, respectivamente**/
	private double t0100, t0200;
	/**Numero de cilindros**/
	private int cilindros;
	/**Desplazamiento en litros**/
	private double desplazamiento;
	/**Caballos de fuerza HP**/
	private int hp;
	/**Revoluciones por minuto**/
	private int rpm;
	/**
	 * vf velocidad final (alcanza velmax si vf >= velmax)
	 * t tiempo transcurrido y finish time
	 * x distancia
	 * axCTE aceleracion constante
	 * **/
	public double vf, t, x, axCTE;
	/**Tiempo
	 * distancia en alcanzar velocidad maxima
	 * distancia en alcanzar velocidad final 200 km/h **/
	public double tvelmax, xvelmax, x200;
	/**True si el vehiculo ha terminado el recorrido o no es participe de simulacion (usuario no lo elegio)**/
	public Boolean isDone;
	/**Lista de puntos a graficar**/
	public ArrayList<Punto> puntosxvt;
	
	/**
	 * @param vehiculoImage
	 * @param marca
	 * @param modelo
	 * @param peso
	 * @param velmax
	 * @param t0100
	 * @param t0200
	 * @param cilindros
	 * @param desplazamiento
	 * @param hp
	 * @param rpm
	 */
	public Vehiculo(String vehiculoImage, String marca, String modelo,
			int peso, double velmax, double t0100, double t0200, int cilindros,
			double desplazamiento, int hp, int rpm) {
		this(vehiculoImage);
		this.marca = marca;
		this.modelo = modelo;
		this.peso = peso;
		this.velmax = toMS(velmax);
		this.t0100 = t0100;
		this.t0200 = t0200;
		  this.vf = vel200; // conversion de km/h a m/s
		  this.t = t0200;
		this.axCTE = calculateAx(this.vf, this.t);
		this.x200 = 0.5D * this.vf * this.t; // precalculado
		  // System.out.println("vel200: " + this.vf + "  axCTE: " + this.axCTE + " x200: " + this.x200);
		this.cilindros = cilindros;
		this.desplazamiento = desplazamiento;
		this.hp = hp;
		this.rpm = rpm;
		this.posicion = new Point(0, 0);
		this.statBox = new Point(0, 0);
		this.puntosxvt = new ArrayList<>(30);
	}
	public void reset(){
		this.vf = this.x = this.t = 0D;
		this.puntosxvt.clear();
		this.isDone = Boolean.FALSE;
	}
	private BufferedImage vehiculoImage;
	/**Posicion del vehiculo y posicion de caja de monitoreo**/
	private Point posicion, statBox;
	/**
	 * @param imagenVehiculo
	 */
	private Vehiculo(String imagenVehiculo) {
		vehiculoImage = ic.getSprite(imagenVehiculo);
		isDone = Boolean.FALSE;
	}
	public static double toMS(double KMH){
		return KMH * 1000D / 3600D;
	}

	public static double calculateAx(double vf, double t){
		return vf / t;
	}
	/*
	 * @return Posicion en metros
	 * */
	public static double calculateX(double t, double vf){
		return vf * t;
	}
	/**
	 * @return Velocidad en m/s
	 * */
	public static double calculateVf(double t, double axcte){
		// vx = ax * t
		return axcte * t;
	}
	/**
	 * @param longitudPista Distancia en metros
	 **/
	public void traslado(int longitudPista){
		this.posicion.x = (int) this.x * (1600 / longitudPista);
	}
	public boolean isDone(){
		return Boolean.FALSE;
	}
	public void setXY(int x, int y){
		this.posicion.setLocation(x, y);
	}
	public void setStatXY(int x, int y){
		this.statBox.setLocation(x, y);
	}
	@Override
	public void dibujar(Graphics2D g2d) {
		// g2d.drawImage(vehiculoImage, posicion.x - vehiculoImage.getWidth()/2, posicion.y - vehiculoImage.getHeight()/2, 60, 60, null);
		g2d.drawImage(vehiculoImage, posicion.x, posicion.y, 60, 60, null);
		// dibujar cuadro de estadisticas
		g2d.drawRect(statBox.x, statBox.y, 210, 80);
		// dibujar variables, especialmente la posicion y velocidad
		// offset x de 10, y de 15
		g2d.drawString(String.format("%s, %s", marca, modelo), statBox.x + 25, statBox.y + 15);
		g2d.drawString(String.format("Distancia: %.5f m", x), statBox.x + 10, statBox.y + 30);
		g2d.drawString(String.format("Tiempo: %.3f s", t), statBox.x + 10, statBox.y + 45);
		g2d.drawString(String.format("Velocidad: %.5f m/s", vf), statBox.x + 10, statBox.y + 60);
		// si ha concluido, dibujar tiempo transcurrido
	}

}
