/**
 * 
 */
package fiusac.modela1.main;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author david
 *
 */
public class Pista extends JPanel {

	/**Valores adminisibles: 500, 800, 1000, 1200, 1500**/
	private int trayectoria;
	
	private BufferedImage pistaImage;
	/**
	 * 
	 */
	public Pista() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 */
	public Pista(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param isDoubleBuffered
	 */
	public Pista(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public Pista(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}
	/* 
	 * 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}
}
