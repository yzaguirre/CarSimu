/**
 * 
 */
package fiusac.modela1.main;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

/**
 * Contiene tabla de datos resumiendo la simulacion
 */
public class TablaResumen extends JFrame {
	/**
	 * @param title Titulo del JFrame
	 * @param jtTabla Componente Tabla con datos
	 */
	public TablaResumen(String title, JTable jtTabla) {
		super(title);
		setBounds(0, 300, 1100, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JScrollPane jsp = new JScrollPane(jtTabla);
		getContentPane().add(jsp, BorderLayout.CENTER);
	}
	/**
	 * Muestra la ventana
	 **/
	public void mostrar(){
		setVisible(true);
	}
}
