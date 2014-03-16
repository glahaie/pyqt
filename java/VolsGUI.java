import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;


/* VolsGUI.java
 * Par Guillaume Lahaie
 * 
 * Classe qui crée un JPanel permettant de créer des nouveaux vols.
 * 
 * 
 * Dernière modification: 1er décembre 2011.
 * 
 * 
 */

public class VolsGUI extends JPanel {
	
	final int RANGEES_MIN = 10, RANGEES_MAX = 30, COL_MIN = 2, COL_MAX = 6;
	
	private JComboBox siegesRangC, siegesColC;
	private JTextField departT, destinationT;
	private JLabel siegesRangL, siegesColL, departL, destinationL, heureL, infoPane;
	private JButton creerVol;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
	private SpinnerDateModel model;
	private JSpinner date;
	private JSpinner.DateEditor heure;	
	private Vols vol;
	private GUI gui;
	private String[] siegesRang, siegesCol; //Tableaux contenant les possibilités
											//de sièges et colonnes dans le vol.
	
	//Constructeur
	VolsGUI(Vols v, GUI gui) {
		this.gui = gui;
		this.setLayout(new GridLayout(6, 1));
		this.vol = v;
		this.setSize(GUI.LARGEUR, 400);
		creerSieges();
		creerObjets();
		creerDates();
		placerObjets();
	}

	//On créé les différents objets graphiques du JPanel.
	private void creerObjets() {
		departT = new JTextField(25);
		destinationT = new JTextField(25);
		
		infoPane = new JLabel("Création de vols");
		infoPane.setFont(new Font("Dialog",Font.PLAIN, 24));
		siegesRangL = new JLabel("Rangées");
		siegesColL = new JLabel("Sièges par rangées");
		departL = new JLabel("Départ");
		destinationL = new JLabel("Destination");
		heureL= new JLabel("Date et heure de départ");
		
		creerVol = new JButton("Créer le vol");
		creerVol.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				creerVol();
			}
		});
		
		siegesRangC = new JComboBox(siegesRang);
		siegesColC = new JComboBox(siegesCol);
				
		panel1 = new JPanel(new FlowLayout());
		panel2 = new JPanel(new FlowLayout());
		panel3 = new JPanel(new FlowLayout());
		panel4 = new JPanel(new FlowLayout());
		panel5 = new JPanel(new FlowLayout());
		panel6 = new JPanel(new FlowLayout());
	}
	
	//On place les objets dans le JPanel.
	private void placerObjets() {
		panel1.add(departL);
		panel1.add(departT);
		panel2.add(destinationL);
		panel2.add(destinationT);
		
		panel3.add(heureL);
		panel3.add(date);
		
		panel6.add(siegesRangL);
		panel6.add(siegesRangC);
		panel6.add(siegesColL);
		panel6.add(siegesColC);
		
		panel4.add(infoPane);
		panel5.add(creerVol);
		
		
		add(panel4);
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel6);
		add(panel5);
	}
	
	//Crée un JSpinner spécifique pour la date et l'heure de départ du vol.
	private void creerDates() {
		model = new SpinnerDateModel();
		date = new JSpinner(model);
		heure = new JSpinner.DateEditor(date, "yyyy:MM:dd:HH:mm");
		date.setEditor(heure);
		date.setValue(new Date());
	}
	
	//Crée des tableaux pour toutes les options de rangées de sièges et de colonnes
	//de sièges.
	private void creerSieges() {
		siegesRang = new String[RANGEES_MAX - RANGEES_MIN +1];
		for(int i = RANGEES_MIN; i <= RANGEES_MAX; i++) {
			siegesRang[i - RANGEES_MIN] = String.valueOf(i);
		}
		siegesCol = new String[COL_MAX - COL_MIN +1];
		for (int j = COL_MIN; j <= COL_MAX; j++) {
			siegesCol[j-COL_MIN] = String.valueOf(j);
		}		
	}
	
	//Méthode appellée lorsque l'utilisateur clique sur le bouton pour créer un vol. Vérifie si toutes
	//les informations nécessaires sont entrées, sinon, on annule.
	private void creerVol() {
		String depart, destination;
		Date dateDepart = new Date();
		int rangees, cols;
		
		depart = departT.getText().trim();
		destination  = destinationT.getText().trim();		
		dateDepart = model.getDate();
		
		rangees = Integer.parseInt((String)siegesRangC.getSelectedItem());
		cols = Integer.parseInt((String)siegesColC.getSelectedItem());
		
		vol.addVol(depart, destination, dateDepart, rangees, cols);
		
	}
	
}
