package fiusac.modela1.graficas;

import javax.swing.JComponent;
import javax.swing.JFrame;
// 
public class PanelGrafico extends JComponent{
	
	
	public static void main(String[] args){
		JFrame jf = new JFrame("Standalone JFrame");
		jf.setBounds(150, 50, 800, 700);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(null);
		// jf.add(new PanelGrafico("img/fondo.png"));
		jf.setVisible(true);
	}
}
