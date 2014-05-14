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
	static {
		dirStats = "img/stats.png";
		biStats = ic.getSprite(dirStats);
		vehiculos = new Vehiculo[]{
				// String vehiculoImage, String marca, String modelo,
				// int peso, int velmax, float t0100, float t0200, int cilindros,
				// float desplazamiento, int hp, int rpm, Point posicion
				new Vehiculo("img/cayenne.jpg", "Porche", "Cayenne 2013", 2105, 230, 7.8f, 17.16f, 6, 3.6f, 296, 6300),
				new Vehiculo("img/nissan.jpg", "Nissan", "350z 2010", 1537, 250, 5.7f, 12.54f, 6, 3.5f, 309, 6800),
				new Vehiculo("img/vw.png", "VW", "Beetle Cabrio", 1530, 221, 7.6f, 16.72f, 4, 2.0f, 197, 5100),
				new Vehiculo("img/peugeot.png", "Peugeot", "207 2011", 1342, 192, 11.2f, 24.64f, 4, 2.0f, 118, 6000)
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
	public void inicializar(String dirFondo, int distanciaPista, int[] carros){
		this.dirFondo = dirFondo;
		this.biFondo = ic.getSprite(dirFondo);
		alVehiculos.clear();
		totalCars = 0;
		for(int i=0; i < 4; i++){
			if (carros[i] == 1) {
				Vehiculo v = vehiculos[i];
				v.setY(ys[totalCars++]);
				alVehiculos.add(v);
			}
		}
	}
	/*public void start (){
		
	}
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
	}
}
