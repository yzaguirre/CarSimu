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
	private String marca, modelo;
	/**Peso en kg**/
	private int peso;
	/**velocidad maxima en km/h**/
	private int velmax;
	/**aceleracion en segundos de 0 a 100 km/h y 0 a 200 km/h, respectivamente**/
	private float t0100, t0200;
	
	/**Numero de cilindros**/
	private int cilindros;
	/**Desplazamiento en litros**/
	private float desplazamiento;
	/**Caballos de fuerza HP**/
	private int hp;
	/**Revoluciones por minuto**/
	private int rpm;
	
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
			int peso, int velmax, float t0100, float t0200, int cilindros,
			float desplazamiento, int hp, int rpm) {
		this(vehiculoImage);
		this.marca = marca;
		this.modelo = modelo;
		this.peso = peso;
		this.velmax = velmax;
		this.t0100 = t0100;
		this.t0200 = t0200;
		this.cilindros = cilindros;
		this.desplazamiento = desplazamiento;
		this.hp = hp;
		this.rpm = rpm;
		this.posicion = posicion;
		this.posicion = new Point(10, 0);
	}
	private BufferedImage vehiculoImage;
	private Point posicion;
	/**
	 * 
	 */
	private Vehiculo(String imagenVehiculo) {
		vehiculoImage = ic.getSprite(imagenVehiculo);
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
