/**
 * 
 */
package fiusac.modela1.main;
import javax.swing.JComponent;
import javax.swing.JFrame;

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
	private int totalCars;
	private static final Vehiculo[] vehiculos;
	private static final int[] ys;
	private ArrayList<Vehiculo> alVehiculos;
	private int longitudPista;
	static {
		dirStats = "img/stats.png";
		biStats = ic.getSprite(dirStats);
		vehiculos = new Vehiculo[]{
				new Vehiculo("img/cayenne.jpg", "Porche", "Cayenne 2013", 2105, 230D, 7.8D, 17.16D, 6, 3.6D, 296, 6300),
				new Vehiculo("img/nissan.jpg", "Nissan", "350z 2010", 1537, 250D, 5.7D, 12.54D, 6, 3.5f, 309, 6800),
				new Vehiculo("img/vw.png", "VW", "Beetle Cabrio", 1530, 221D, 7.6D, 16.72D, 4, 2.0D, 197, 5100),
				new Vehiculo("img/peugeot.png", "Peugeot", "207 2011", 1342, 192D, 11.2D, 24.64D, 4, 2.0D, 118, 6000)
		};
		ys = new int[]{
				40,110,230,310
		};
	}
	public Pista() {
		super.setSize(800, 650);
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
		alVehiculos.clear();
		totalCars = 0;
		for(int i=0; i < 4; i++){
			Vehiculo v = vehiculos[i];
			if (carros[i] == 1) { // usuario ha elegido este carrito
				v.setXY(10, ys[totalCars++]); // reiniciar coordenadas de v
				v.reset();
				alVehiculos.add(v); // incluir v a lista de vehiculos a trabajar
			} else {
				v.isDone = Boolean.TRUE; // autos a no considerar
			}
		}
	}
	public Thread start (){
		Thread t = new Thread(new Runnable() {
			public void run() {
				double epoch0, epoch, epochNow;
				epoch0 = epoch = epochNow = System.currentTimeMillis();
				Vehiculo v1 = vehiculos[0], v2 = vehiculos[1], v3 = vehiculos[2], v4 = vehiculos[3];
				while_v: while (!(v1.isDone && v2.isDone && v3.isDone && v4.isDone)){ // mientras ningun vehiculo ha concluido
					// double tdelta = (epochNow - epoch) / 1000D;
					double t = (epochNow - epoch0) / 1000D;
					// System.out.println("Tiempo transcurrido: " + t);
					for_v: for (Vehiculo v: alVehiculos){
						if (v.isDone) {
							v.finishTime = t;
							continue for_v; //no molestarse con este "v"
						}
						// calcular nuevas variables para Vehiculo "v"
						if (v.vf <= v.velmax) { // calcular su velocidad
							v.vf = Vehiculo.calculateVf(0, t, v.axCTE);
							v.velmaxTime = t;
						}
						v.x = Vehiculo.calculateX(0, 0, t, v.axCTE); // calcular su posicion
						if (v.x > Pista.this.longitudPista) v.isDone = Boolean.TRUE; // alcanzo la longitud de la pista
						v.traslado(Pista.this.longitudPista); // trasladar coordenadas de metros a pixeles
					}
					Pista.this.repaint();
					/*try {
						Thread.sleep(300); // duermase 1 segundo
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					// epoch = epochNow; // comentar para obtener tiempo total transcurrido
					epochNow = System.currentTimeMillis();
				}
				// finaliza hilo
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
		
		BufferedImage biBuff = new BufferedImage(800, 600, BufferedImage.TRANSLUCENT);
		
		Graphics2D g2dBuff = biBuff.createGraphics();
		g2dBuff.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // antialiasing
		
		g2dBuff.drawImage(biFondo, 0, 0, this); // dibujar fondo
		g2dBuff.drawImage(biStats, 0, 400, this); // dibujar fondo
		
		// dibujar carritos
		for(Vehiculo v : alVehiculos){
			v.dibujar(g2dBuff);
		}
		
		g2d.drawImage(biBuff, 0, 0, this); // imprimir el dibujo al JComponent
	}
	public static void main(String[] args){
		JFrame jf = new JFrame("Standalone JFrame");
		jf.setBounds(150, 50, 800, 700);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(null);
		
		Pista p = new Pista();
		p.inicializar("img/fondo.png", 1500, new int[] {1,1,1,1});
		
		jf.add(p);
		jf.setVisible(true);
		// p.start();
		Thread t = p.start();
		t.start();
	}
}
