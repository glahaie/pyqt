import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/* ListeVolsGUI.java
 * Par Guillaume Lahaie
 * 
 * Cette classe affiche les informations de tous les vols présents dans la map de vols.
 * Il n'est pas possible d'interagir avec ce panel, il affiche seulement les informations.
 * 
 * 
 * Dernière modification: 1er décembre 2011.
 * 
 * 
 */

public class ListeVolsGUI extends JPanel {
	
	private JLabel infoPane;
	private JPanel panel1;
	private Vols vol;
	GUI gui;
	private JTextArea textArea;
	private JScrollPane js;
	
	//Constructeur
	ListeVolsGUI(Vols v, GUI gui) {
		this.gui = gui;
		this.setLayout(new GridLayout(5, 1));
		this.vol = v;
		this.setSize(GUI.LARGEUR, 400);
		this.setLayout(new BorderLayout());
		creerObjets();
		placerObjets();
		vol.afficherVols(textArea);
	}

	//On créé les objets
	private void creerObjets() {
		
		infoPane = new JLabel("Liste de tous les vols");
		infoPane.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setSize(600, 250);
		
		js = new JScrollPane(textArea);
		js.setSize(600, 250);
				
		panel1 = new JPanel(new FlowLayout());
	}
	
	//On place les objets dans le panel.
	private void placerObjets() {
	
		panel1.add(infoPane);
		add(panel1, BorderLayout.NORTH);
		add(js, BorderLayout.CENTER);
	}	
}
