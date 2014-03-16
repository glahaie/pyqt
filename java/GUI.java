import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/* GUI.java
 * Par Guillaume Lahaie
 * 
 * Gestion de l'ensemble des panels de l'interface pour la gestion des vols d'avion et des passager.
 * Les panels utilisés seront dans d'autres classes.
 *  
 * Dernière modification: 4 décembre 2011
 *  
 */
public class GUI {

	protected JFrame frame;
	final public static int LARGEUR = 1000; //grandeur du frame
	final public static int HAUTEUR = 600;
	private PassagerGUI panelP;
	private VolsGUI volsP;
	private VolsGUI_E volsEffP;
	private PassagerGUI_E panelPE;
	private ListeGUI listeP;
	private ListeVolsGUI listeVolP;
	private PassagerListeGUI listePasP;
	protected JTextArea infos;
	private JScrollPane js;
	protected Vols v; //Classe Vols pour la gestion des vols et passagers.
	private MenuBar bar;
	private JPanel panel;
		
	GUI() {
		v = new Vols(this);
		infos = new JTextArea("Ouverture de la gestion des vols.\n");
		infos.setSize(LARGEUR, 25);
		infos.setEditable(false);
		js = new JScrollPane(infos);
		js.setPreferredSize(new Dimension(LARGEUR, 25));
		panelP = new PassagerGUI(v, this);
		volsP = new VolsGUI(v, this);
		volsEffP = new VolsGUI_E(v, this);
		listeP = new ListeGUI(v, this);
		listeVolP = new ListeVolsGUI(v, this);
		bar = new MenuBar(v, this);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
		
	//Méthodes appelées pour changer le panel de l'interface.
	
	//Changer de panel pour supprimer des vols.
	protected void switchPanelEfface() {
		panel.removeAll();
		volsEffP = new VolsGUI_E(v, this);
		panel.add(volsEffP);
		panel.add(js);
		frame.repaint();
		this.volsEffP.repaint();
		this.frame.setVisible(true);
	}
	
	//Changer de panel pour faire des réservations.
	protected void switchPanelPass() {
		panel.removeAll();
		panelP = new PassagerGUI(v, this);
		panel.add(panelP);
		panel.add(js);
		panel.repaint();
		this.panelP.repaint();
		this.frame.setVisible(true);
	}
	
	//Changer de panel pour supprimer des réservation de siège.
	protected void switchPanelPassEff() {
		panel.removeAll();
		panelPE = new PassagerGUI_E(v, this);
		panel.add(panelPE);
		panel.add(js);
		panel.repaint();
		panelPE.repaint();
		this.frame.setVisible(true);
	}
	
	//Changer de panel pour supprimer créer des vols.
	protected void switchPanelCreer() {
		panel.removeAll();
		volsP = new VolsGUI(v, this);
		panel.add(volsP);
		panel.add(js);
		panel.repaint();
		this.volsP.repaint();
		this.frame.setVisible(true);
	}
	//Changer de panel pour afficher les informations d'un vol particulier.
	protected void switchPanelListe() {
		panel.removeAll();
		listeP = new ListeGUI(v, this);
		panel.add(listeP);
		panel.add(js);
		panel.repaint();
		this.listeP.repaint();
		this.frame.setVisible(true);
	}
	
	//Changer de panel pour afficher la liste de tous les vols.
	protected void switchPanelListeVols() {
		panel.removeAll();
		listeVolP = new ListeVolsGUI(v, this);
		panel.add(listeVolP);
		panel.add(js);
		panel.repaint();
		this.listeVolP.repaint();
		this.frame.setVisible(true);
	}
	
	//Changer de panel pour afficher la liste des passagers d'un vol.
	protected void switchPanelPassListe() {
		panel.removeAll();
		listePasP = new PassagerListeGUI(v, this);
		panel.add(listePasP);
		panel.add(js);
		panel.repaint();
		listePasP.repaint();
		this.frame.setVisible(true);
	}
	
	public static void main(String[] args) {		
		GUI gui = new GUI();
		gui.frame = new JFrame("Gestion de vols");
		gui.frame.setSize(LARGEUR, HAUTEUR);
		gui.frame.setJMenuBar(gui.bar);
		gui.panel.add(gui.volsP);
		gui.panel.add(gui.js);
		gui.frame.getContentPane().add(gui.panel);
		gui.frame.setVisible(true);
		gui.frame.setLocation(100, 100);
		gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
