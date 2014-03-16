import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/* MenuBar.java
 * Par Guillaume Lahaie
 * 
 * Cette classe gère la barre de menu de l'interface graphique de Vols. La barre permet surtout
 * de changer de panel pour faire différentes opérations.
 *
 *Dernière modification: 4 décembre 2011.
 * 
 */

public class MenuBar extends JMenuBar {

	private JMenu fichier, vols, passager;
	private JMenuItem ouvrir, enregistrer, quitter, creerVol, supVol, resPas, supPas, listeVol, listeVols, listePas;
	private Vols v;
	private GUI gui;

	//Constructeur
	public MenuBar (Vols v, GUI gui) {
		this.v = v;
		this.gui = gui;
		creerMenu();
	}
	
	//Initialise les menus.
	private void creerMenu() {
		fichier = new JMenu("Fichier");	
		ouvrir = new JMenuItem("Lire un fichier vols");
		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});				
		ouvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.readFile();
			}
		});
		enregistrer = new JMenuItem("Enregistrer les vols");
		enregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				v.saveFile();
			}
		});
		fichier.add(ouvrir);
		fichier.add(enregistrer);
		fichier.addSeparator();
		fichier.add(quitter);
		
		vols = new JMenu("Vols");
		passager = new JMenu("Passagers");
		
		creerVol = new JMenuItem("Créer un vol");
		creerVol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelCreer();
			}
		});
		supVol = new JMenuItem("Supprimer un vol");
		supVol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelEfface();
			}
		});
		listeVol = new JMenuItem("Afficher les détails d'un vol");
		listeVol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelListe();
			}
		});
		
		listeVols = new JMenuItem("Afficher tous les vols");
		listeVols.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelListeVols();
			}
		});
		
		resPas = new JMenuItem("Réserver un siège");
		resPas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelPass();
			}
		});
		
		supPas = new JMenuItem("Effacer une réservation");
		supPas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelPassEff();
			}
		});
		listePas = new JMenuItem("Liste des passagers");
		listePas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.switchPanelPassListe();
			}
		});
				
		vols.add(creerVol);
		vols.add(supVol);
		vols.add(listeVol);
		vols.add(listeVols);
		
		passager.add(resPas);
		passager.add(supPas);
		passager.add(listePas);
			
		add(fichier);
		add(vols);
		add(passager);
		
	} //Fin créerMenu

} //Fin MenuBar
