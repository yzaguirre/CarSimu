package fiusac.modela1.main;

import static fiusac.modela1.utilities.FicheroES.ic;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;

/**
 * Representa un vehiculo
 */
public class Vehiculo {
	/**velocidad final de 200km/h en m/s**/
	public static final double vel200;
	static {
		vel200 = toMS(200);
	}
	/**Marca y modelo**/
	public String marca, modelo;
	/**Peso en kg**/
	private int peso;
	/**velocidad maxima en ms/s. Utilizado en simulacion**/
	public double velmax;
	/**aceleracion en segundos de 0 a 100 km/h y 0 a 200 km/h. Utilizado en simulacion**/
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
	 * x desplazamiento
	 * axCTE aceleracion constante
	 * **/
	public double vf, t, x, axCTE;
	/**Tiempo transcurrido al alcanzar velocidad maxima
	 * Desplazamiento al alcanzar velocidad maxima
	 * Desplazamiento al alcanzar velocidad final 200 km/h **/
	public double tvelmax, xvelmax, x200;
	/**True si el vehiculo ha terminado el recorrido o no es considerado en la simulacion (usuario no lo elegio)**/
	public Boolean isDone;
	/**Lista de puntos a graficar**/
	public ArrayList<Punto> puntosxvt;
	/**Imagen de autopista en memoria para el fondo del canvas**/
	private BufferedImage vehiculoImage;
	/**Posicion del vehiculo y posicion de caja de monitoreo**/
	private Point posicion, statBox;
	
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
		this.vehiculoImage = ic.getSprite(vehiculoImage);
		this.isDone = Boolean.FALSE;
		this.marca = marca;
		this.modelo = modelo;
		this.peso = peso;
		this.velmax = toMS(velmax);
		this.t0100 = t0100;
		this.t0200 = t0200;
		  this.vf = vel200;
		  this.t = t0200;
		this.axCTE = calculateAx(this.vf, this.t);
		this.x200 = 0.5D * this.vf * this.t; // precalculado
		this.cilindros = cilindros;
		this.desplazamiento = desplazamiento;
		this.hp = hp;
		this.rpm = rpm;
		this.posicion = new Point(0, 0);
		this.statBox = new Point(0, 0);
		this.puntosxvt = new ArrayList<>(30);
	}
	/**Eliminar valores anteriores**/
	public void reset(){
		this.vf = this.x = this.t = 0D;
		this.puntosxvt.clear();
		this.isDone = Boolean.FALSE;
	}
	/**
	 * Convierte velocidad de km/h a m/s
	 * @param KMH velocidad en km/h 
	 * @return velocidad en m/s
	 * **/
	public static double toMS(double KMH){
		return KMH * 1000D / 3600D;
	}
	/**
	 * Calculo de aceleracion
	 * @param vf velocidad en m/s
	 * @param t tiempo en s
	 * @return aceleracion en m/s^2
	 * */
	public static double calculateAx(double vf, double t){
		return vf / t;
	}
	/*
	 * Calculo de desplazamiento con velocidad constante
	 * @param x0 Desplazamiento inicial en m
	 * @param t Tiempo transcurrido en s
	 * @param vf Velocidad final en m/s
	 * @return Desplazamiento en m
	 * */
	public static double calculateX(double x0, double t, double vf){
		return x0 + vf * t;
	}
	/*
	 * Calculo de desplazamiento con aceleracion constante
	 * @param t Tiempo transcurrido en s
	 * @param acte Aceleracion constante en m/s^2
	 * @return Desplazamiento en m
	 * */
	public static double calculateXconACTE(double t, double acte){
		return 0.5D * acte * Math.pow(t, 2D);
	}
	/**
	 * Calculo de velocidad final en m/s
	 * @param t Tiempo transcurrido en s
	 * @param axcte Aceleracion constante en m/s^2
	 * @return Velocidad final en m/s
	 * */
	public static double calculateVf(double t, double axcte){
		return axcte * t;
	}
	/**
	 * Convierte metros a posicion en pixel
	 * @param longitudPista Distancia de pista en m
	 **/
	public void traslado(int longitudPista){
		this.posicion.x = (int) this.x * 1540 / longitudPista;
	}
	/**
	 * Asigna posicion (x,y) al vehiculo
	 * @param x Posicion x
	 * @param y Posicion y
	 * **/
	public void setXY(int x, int y){
		this.posicion.setLocation(x, y);
	}
	/**
	 * Asigna posicion (x,y) a la cajita de estadisticas
	 * @param x Posicion x
	 * @param y Posicion y
	 * **/
	public void setStatXY(int x, int y){
		this.statBox.setLocation(x, y);
	}
	/**
	 * Crea el componente JTable con datos de los vehiculos considerado en la simulacion
	 * @param vehiculos Lista de vehiculos a extraer datos
	 * @param longitudPista Distancia de pista en m
	 * **/
	public static JTable getTableData(ArrayList<Vehiculo> vehiculos, int longitudPista){
		int size = vehiculos.size();
		String [][] rowData = new String [6][size + 1];
		String [] columnNames = new String[size + 1];
		columnNames[0] = "Datos";
		rowData[0][0] = "Aceleracion constante";
		rowData[1][0] = "Distancia a la que se alcanzaron los 200 km/h";
		rowData[2][0] = "Velocidad final alcanzada";
		rowData[3][0] = "Tiempo en el que se alcanzó la velocidad máxima";
		rowData[4][0] = "Distancia a la que se alcanzó la velocidad máxima";
		rowData[5][0] = String.format("Tiempo en que completa el recorrido (%d m)", longitudPista);
		Iterator<Vehiculo> it = vehiculos.iterator();
		for (int i = 1; i <= size; i ++){ // iterar por vehiculo / columna
			Vehiculo v = it.next();
			columnNames[i] = v.marca;
			// recorrero por fila, de columna i
			rowData[0][i] = String.format("%.5f m/s^2", v.axCTE);
			rowData[1][i] = (longitudPista < v.x200)?"N/A":String.format("%.5f m", v.x200);
			rowData[2][i] = String.format("%.5f m/s", v.vf);
			rowData[3][i] = (v.vf < v.velmax) ? "N/A" : String.format("%.5f s", v.tvelmax); // puede no lograrlo
			rowData[4][i] = (v.vf < v.velmax) ? "N/A" : String.format("%.5f m", v.xvelmax); // puede no lograrlo
			rowData[5][i] = String.format("%.5f s", v.t);
		}
		return new JTable(rowData, columnNames);
	}
	/**
	 * Operacion de dibujo de vehiculo y cajita de estadisticas
	 * @param g2d Objeto Graphics2D a dibujar
	 * **/
	public void dibujar(Graphics2D g2d) {
		g2d.drawImage(vehiculoImage, posicion.x, posicion.y, 60, 60, null);
		// dibujar cuadro de estadisticas
		g2d.drawRect(statBox.x, statBox.y, 210, 80);
		// dibujar variables, especialmente la posicion y velocidad
		// offset x de 10, y de 15
		g2d.drawString(String.format("%s, %s", marca, modelo), statBox.x + 25, statBox.y + 15);
		g2d.drawString(String.format("Distancia: %.5f m", x), statBox.x + 10, statBox.y + 30);
		g2d.drawString(String.format("Tiempo: %.3f s", t), statBox.x + 10, statBox.y + 45);
		g2d.drawString(String.format("Velocidad: %.5f m/s", vf), statBox.x + 10, statBox.y + 60);
	}

}
