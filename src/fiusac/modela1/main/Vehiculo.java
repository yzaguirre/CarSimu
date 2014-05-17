/**
 * 
 */
package fiusac.modela1.main;

import static fiusac.modela1.utilities.FicheroES.ic;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.text.Position;

import fiusac.modela1.dibujo.Dibujable;

/**
 * @author david
 *
 */
public class Vehiculo implements Dibujable{
	public String marca, modelo;
	/**Peso en kg**/
	private int peso;
	/**velocidad maxima en km/h**/
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
	
	public double vf, t, x, axCTE;
	public double finishTime, velmaxTime;
	public Boolean isDone;
	
	
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
		  this.vf = toMS(200); // conversion a m/s
		  this.t = t0200;
		this.axCTE = calculateAx(this.vf, this.t);
		  System.out.println("vf: " + this.vf + "  axCTE: " + this.axCTE);
		this.cilindros = cilindros;
		this.desplazamiento = desplazamiento;
		this.hp = hp;
		this.rpm = rpm;
		this.posicion = new Point(10, 0);
	}
	public void reset(){
		this.vf = this.x = this.t = 0D;
		isDone = Boolean.FALSE;
	}
	private BufferedImage vehiculoImage;
	private Point posicion;
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
		// 0.5 ax * t ^ 2
		return vf * t;
	}
	/**
	 * @return Velocidad en m/s
	 * */
	public static double calculateVf(double t, double axcte){
		// vx = ax * t
		return axcte * t;
	}
	
	public void traslado(int longitudPista){
		this.posicion.x = (int) this.x * (3000 / longitudPista);
	}
	public void avanzar(){
		
	}
	public boolean isDone(){
		return Boolean.FALSE;
	}
	public void setX(int x){
		this.posicion.x = x;
	}
	public void setY(int y){
		this.posicion.y = y;
	}
	public void setXY(int x, int y){
		this.posicion.setLocation(x, y);
	}
	public int obtenerX(){
        return posicion.x;
    }
    public int obtenerY(){
        return posicion.y;
    }
	@Override
	public void dibujar(Graphics2D g2d) {
		// g2d.drawImage(vehiculoImage, posicion.x - vehiculoImage.getWidth()/2, posicion.y - vehiculoImage.getHeight()/2, 60, 60, null);
		g2d.drawImage(vehiculoImage, posicion.x, posicion.y, 60, 60, null);
	}

}
