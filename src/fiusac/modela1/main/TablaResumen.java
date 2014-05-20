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
 * @author david
 *
 */
public class TablaResumen extends JFrame {
	/**
	 * @param title
	 */
	public TablaResumen(String title, JTable jtTabla) {
		super(title);
		setBounds(0, 300, 1100, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JScrollPane jsp = new JScrollPane(jtTabla);
		getContentPane().add(jsp, BorderLayout.CENTER);
	}
	public void mostrar(){
		setVisible(true);
	}
}
