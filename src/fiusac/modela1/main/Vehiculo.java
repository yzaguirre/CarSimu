/**
 * 
 */
package fiusac.modela1.main;

import static fiusac.modela1.utilities.FicheroES.ic;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
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
	private int t0100, t0200;
	
	private BufferedImage vehiculoImage;
	private Point posicion;
	/**
	 * 
	 */
	public Vehiculo(String imagenVehiculo) {
		vehiculoImage = ic.getSprite(imagenVehiculo);
	}

	public void avanzar(){
		
	}
	public int obtenerX(){
        return posicion.x;
    }
    public int obtenerY(){
        return posicion.y;
    }
	@Override
	public void dibujar(Graphics2D g2d) {
		g2d.drawImage(vehiculoImage, posicion.x - vehiculoImage.getWidth()/2, posicion.y - vehiculoImage.getHeight()/2, null);
	}

}
