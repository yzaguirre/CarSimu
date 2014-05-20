/**
 * 
 */
package fiusac.modela1.main;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import fiusac.modela1.graficas.PanelGrafico;
import fiusac.modela1.main.Vehiculo;
import static fiusac.modela1.utilities.FicheroES.ic;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author david
 *
 */
public class Pista extends JComponent {
	private static final BufferedImage biStats;
	private static final String dirStats;
	
	private BufferedImage biFondo;
	private String dirFondo;
	/**Numero de vehiculos a considerar en la simulacion presente**/
	private int totalCars;
	private static final Vehiculo[] vehiculos;
	/**Posicion Y de cada carril para vehiculo**/
	private static final int[] ys;
	/**Posicion X, Y para caja de estadisticas**/
	private static final int[][] xys;
	private ArrayList<Vehiculo> alVehiculos;
	/**Longitud de la pista**/
	private int longitudPista, longitudMedia;
	/**Tiempo transcurrido**/
	private double lapsedTime;
	static {
		dirStats = "img/stats.png";
		biStats = ic.getSprite(dirStats);
		vehiculos = new Vehiculo[]{
				// String vehiculoImage, String marca, String modelo,
				// int peso, double velmax, double t0100, double t0200, int cilindros,
				// double desplazamiento, int hp, int rpm
				new Vehiculo("img/cayenne.jpg", "Porche", "Cayenne 2013", 2105, 230D, 7.8D, 17.16D, 6, 3.6D, 296, 6300),
				new Vehiculo("img/nissan.jpg", "Nissan", "350z 2010", 1537, 250D, 5.7D, 12.54D, 6, 3.5f, 309, 6800),
				new Vehiculo("img/peugeot.png", "Peugeot", "207 2011", 1342, 208D, 8.6D, 18.92D, 4, 2.0D, 118, 6000),
				new Vehiculo("img/vw.png", "VW", "Beetle Cabrio", 1530, 221D, 7.6D, 16.72D, 4, 2.0D, 197, 5100)
		};
		ys = new int[]{
				40,110,230,310
		};
		xys = new int[][]{
				{110,410},{340,410},{110,500},{340,500}
		};
	}
	public Pista() {
		super.setSize(1600, 650);
		alVehiculos = new ArrayList<>(4);
	}
	/**
	 * @param dirFondo
	 * @param distanciaPista
	 * @param carros
	 */
	public void inicializar(String dirFondo, int longitudPista, int[] carros){
		this.dirFondo = dirFondo;
		this.biFondo = ic.getSprite(dirFondo);
		this.longitudPista = longitudPista;
		this.longitudMedia = longitudPista / 2;
		this.lapsedTime = 0D;
		alVehiculos.clear();
		totalCars = 0;
		int [] xypair;
		int carnum = 0;
		for(int i=0; i < 4; i++){
			Vehiculo v = vehiculos[i];
			if (carros[i] == 1) { // usuario ha elegido este carrito
				carnum = totalCars++;
				v.setXY(0, ys[carnum]); // reiniciar coordenadas de v
				xypair = xys[carnum];
				v.setStatXY(xypair[0], xypair[1]);
				v.reset();
				alVehiculos.add(v); // incluir v a lista de vehiculos a considerar
			} else {
				v.isDone = Boolean.TRUE; // autos a no considerar
			}
		}
	}
	public Thread start (){
		Thread t = new Thread(new Runnable() {
			public void run() {
				double t0, tNow;
				t0 = tNow = System.currentTimeMillis();
				int conteo_iteracion = 0;
				Vehiculo v1 = vehiculos[0], v2 = vehiculos[1], v3 = vehiculos[2], v4 = vehiculos[3];
				while (!(v1.isDone && v2.isDone && v3.isDone && v4.isDone)){ // mientras ningun vehiculo ha concluido
					// double tdelta = (epochNow - epoch) / 1000D;
					double t = (tNow - t0) / 1000D;					
					
					
					// System.out.println("Tiempo transcurrido: " + t);
					for_v: for (Vehiculo v: alVehiculos){
						if (v.isDone) {
							continue for_v; //no molestarse con este "v"
						}
						v.t = t; // tiempo actual del vehiculo
						// calcular nuevas variables para Vehiculo "v"
						if (v.vf < v.velmax) { // calcular su velocidad
							v.vf = Vehiculo.calculateVf(t, v.axCTE); // calcular velocidad
							v.x = v.xvelmax = Vehiculo.calculateXconACTE(t, v.axCTE); // calcular su posicion, y guardar posicion al momento de alcanzar velmax
							v.tvelmax = t; // tiempo al momento de alcanzar velocidad maxima
						} else {
							// System.out.println("tiempo " + (t-v.tvelmax) + ", s0 " + v.xvelmax);
							v.x = Vehiculo.calculateX(v.xvelmax,t-v.tvelmax,v.vf); // calcular su posicion							
						}
						/*if (v.marca == "Porche")
							System.out.println(v.vf);*/
						if (v.x > Pista.this.longitudPista) v.isDone = Boolean.TRUE; // alcanzo la longitud de la pista
						if (conteo_iteracion++ % 25 == 0){
							v.puntosxvt.add(new Punto(v.x, v.vf, v.t)); // puntos a graficar
						}
						v.traslado(Pista.this.longitudPista); // trasladar coordenadas de metros a pixeles
					}
					Pista.this.lapsedTime = t; // tiempo total transcurrido
					Pista.this.repaint();
					tNow = System.currentTimeMillis();
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// finaliza hilo
				// mostrar graficas
				PanelGrafico pg = new PanelGrafico("Graficas", Pista.this.alVehiculos, Pista.this.lapsedTime, Pista.this.longitudPista);
				pg.mostrar();
				TablaResumen tr = new TablaResumen("Resumen", Vehiculo.getTableData(alVehiculos, longitudPista));
				tr.mostrar();
			}
		});
		return t;
	}/*
	private void stop (){
		
	}*/

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage biBuff = new BufferedImage(1600, 600, BufferedImage.TRANSLUCENT);
		
		Graphics2D g2dBuff = biBuff.createGraphics();
		g2dBuff.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // antialiasing
		
		g2dBuff.drawImage(biFondo, 0, 0, this); // dibujar fondo
		g2dBuff.drawImage(biStats, 0, 400, this); // dibujar fondo
		// dibujar texto color rojo
		g2dBuff.setColor(Color.RED);
		g2dBuff.drawString("0 m", 10, 210);
		g2dBuff.drawString(String.format("%d m", longitudMedia), 800, 210);
		g2dBuff.drawString(String.format("%d m", longitudPista), 1550, 210);
		g2dBuff.drawString("1", 10, 60);
		g2dBuff.drawString("2", 10, 120);
		g2dBuff.drawString("3", 10, 250);
		g2dBuff.drawString("4", 10, 330);
		// dibujar texto color negro
		g2dBuff.setColor(Color.BLACK);
		// offset x de 10, y de 15
		g2dBuff.drawString("1", 120, 425);
		g2dBuff.drawString("2", 350, 425);
		g2dBuff.drawString("3", 120, 515);
		g2dBuff.drawString("4", 350, 515);
		// dibujar tiempo transcurrido
        g2dBuff.drawString(String.format("Tiempo transcurrido: %.3f s", this.lapsedTime), 600, 410);
		
		// dibujar carritos
		for(Vehiculo v : alVehiculos){
			v.dibujar(g2dBuff);
		}
		
		g2d.drawImage(biBuff, 0, 0, this); // imprimir el dibujo al JComponent
	}
	/*public static void main(String[] args){
		JFrame jf = new JFrame("Standalone JFrame");
		jf.setBounds(150, 50, 1600, 700);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(null);
		
		Pista p = new Pista();
		p.inicializar("img/fondo.png", 500, new int[] {1,1,1,1});
		
		jf.add(p);
		jf.setVisible(true);
		// p.start();
		Thread t = p.start();
		t.start();
	}*/
}
