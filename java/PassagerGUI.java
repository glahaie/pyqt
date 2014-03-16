import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/* PassagerGUI.java
 * Par Guillaume Lahaie
 * 
 *Panel pour la gestion des passagers. On peut ajouter une réservation à un passager.
 *Il faut tout d'abord choisir le vol, et ensuite entrer le nom de la personne et cliquer
 *sur le bouton du siège. Si le siège est gris il est déjà réservé.
 *
 *Dernière modification: 4 décembre 2011.
 * 
 */

public class PassagerGUI extends JPanel implements ActionListener{
	
	JComboBox volsEx;
	JTextField nomT, prenomT;
	JLabel nomL, prenomL, departL, destinationL, info;
	JButton choix;
	JPanel panel1, //Pour les instructions // confirmation
		   panel2, //Depart / destination et les vols
		   panel3, //Nom /prénom
		   panel4, //Pour le choix du siège
		   panel5,
		   panel6;
	String[] listeVol;
	Vols vols;
	GUI gui;
	JTextArea infoVol;
	List<JButton> siegesB;
	JScrollPane js;
	Vol selected;
	
	//Constructeur
	PassagerGUI(Vols v, GUI g) {
		this.vols = v;
		this.gui = g;
		this.setSize(GUI.LARGEUR, 500);
		setLayout(new BorderLayout());
		creerListe();
		creerObjets();
		placerObjets();
	}
	
	//Création des objets graphiques du Panel.
	private void creerObjets() {
		nomT = new JTextField(25);
		prenomT = new JTextField(25);
		choix = new JButton("Choisir ce vol");
		choix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = (String)volsEx.getSelectedItem();
				selected = vols.vols.get(temp);
				System.out.println("Choix de vol: " + temp);
				afficherVol(temp);
			}
		});
		info = new JLabel("Réservation d'un siège");
		info.setFont(new Font("Dialog", Font.PLAIN, 24));
		nomL = new JLabel("Nom de famille");
		prenomL = new JLabel("Prénom");
		infoVol = new JTextArea();
		infoVol.setEditable(false);
		
		panel1 = new JPanel(new BorderLayout());
		panel2 = new JPanel(new FlowLayout());
		panel3 = new JPanel(new FlowLayout());
		panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));	
	}
	
	//On place les objets dans le JPanel.
	private void placerObjets() {
		panel2.add(info);
		panel3.add(volsEx);
		panel3.add(choix);
		panel1.add(panel3, BorderLayout.NORTH);
		panel4.add(infoVol);
		add(panel2, BorderLayout.NORTH);
		add(panel1, BorderLayout.CENTER);	
		panel1.add(panel4, BorderLayout.CENTER);
	}
	
	//On crée le combobox de vols existant à partir de leur clé.
	private void creerListe() {
		Set<String> temp =vols.vols.keySet();
		Object[] temp2 = temp.toArray();
		listeVol = new String[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			listeVol[i] = (String)temp2[i];
		}		
		volsEx = new JComboBox(listeVol);
	}
	
	//Affiche les infos du vol choisi dans un textArea
	private void afficherVol(String s) {
		infoVol.setText("");
		selected = vols.vols.get(s);
		infoVol.append("Vol " + s + "\n");
		infoVol.append("Ville de départ: " +selected.depart + "\n");
		infoVol.append("Ville d'arrivée: " +selected.destination + "\n");
		infoVol.append("Date de départ: " +selected.date() + "\n");
		infoVol.append("Heure de départ: " +selected.heureDep() + "\n");
		afficherSiege();
		repaint();
	}
	
	//Crée un tableau de JButton affichant les sièges d'un vol pour pouvoir réserver le sièges.
	private void afficherSiege() {

		siegesB = new ArrayList<JButton>();
		for(Map.Entry<Siege, Passager> map:selected.sieges.entrySet()) {
			JButton temp = new JButton(map.getKey().toString());
			if(map.getValue() != null) {
				temp.setEnabled(false);
				gui.infos.append("Siege occupé.\n");
			}
			siegesB.add(temp);
		}
		if(panel5 != null)
			panel5.removeAll();
		panel5 = new JPanel();
		panel5.setLayout(new GridLayout(selected.siegeRangees, selected.siegeColonnes));
		for(JButton j:siegesB) {
			j.addActionListener(this);
			panel5.add(j);
		}

		if(js != null) {
			panel4.remove(js);
			panel4.remove(panel6);
		}
		js = new JScrollPane(panel5);
		js.setPreferredSize(new Dimension(500, 300));
		panel4.add(js);
		if(panel6 != null)
			panel6.removeAll();
		panel6 = new JPanel(new FlowLayout());
		panel6.add(nomL);
		panel6.add(nomT);
		panel6.add(prenomL);
		panel6.add(prenomT);
		panel4.add(panel6);
		repaint();
	}
	
	//On trouve le bouton qui a été cliqué et on effectue la réservation sur ce siège.
	public void actionPerformed(ActionEvent e) {
		JButton jb = null;
		String temp = new String();
		for(JButton j:siegesB) {
			if(e.getSource().equals(j)) {
				temp = j.getText();
				jb = j;
			}
		}
		int siege1 = Integer.parseInt(temp.substring(0, temp.length()-1));
		temp = temp.substring(temp.length()-1);
		String nomRes = nomT.getText();
		String prenomRes = prenomT.getText();
	
		boolean b = vols.ajouterPassager(nomRes, prenomRes, selected, siege1, temp);
		
		jb.setEnabled(!b);
		gui.frame.repaint();
			
	}
}
