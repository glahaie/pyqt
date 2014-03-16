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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/* PassagerGUI_E.java
 * Par Guillaume Lahaie
 * 
 *Panel pour la gestion des passagers. On peut supprimer la réservation d'un siège d'un passager
 *dans ce panel. On choisit le vol, et ensuite la liste des passagers apparait. On peut choisir
 *par le siège et par le nom du passager.
 *
 *Dernière modification: 4 décembre 2011.
 * 
 */

public class PassagerGUI_E extends JPanel implements ActionListener{
	
	JComboBox volsEx;
	JLabel info;
	JButton choix;
	JPanel panel1, //Pour les instructions // confirmation
		   panel2, //Depart / destination et les vols
		   panel3, //Nom /prénom
		   panel4, //Pour le choix du siège
		   panel5;
	String[] listeVol;
	Vols vols;
	GUI gui;
	JTextArea infoVol;
	List<JButton> siegesB;
	JScrollPane js;
	Vol selected;
	
	//Constructeur
	PassagerGUI_E(Vols v, GUI g) {
		this.vols = v;
		this.gui = g;
		this.setSize(GUI.LARGEUR, 500);
		this.setLayout(new BorderLayout());
		creerListe();
		creerObjets();
		placerObjets();
	}
	
	//Création des objets du JPanel.
	private void creerObjets() {
		choix = new JButton("Choisir ce vol");
		choix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = (String)volsEx.getSelectedItem();
				afficherVol(temp);
			}
		});
		info = new JLabel("Cancellation d'une réservation.");
		info.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		infoVol = new JTextArea();
		infoVol.setEditable(false);
		
		panel1 = new JPanel(new BorderLayout());
		panel2 = new JPanel(new FlowLayout());
		panel3 = new JPanel(new FlowLayout());
		panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		panel5 = new JPanel(new FlowLayout());
		
	}
	
	//Place les objets dans le JPanel.
	private void placerObjets() {
		panel2.add(volsEx);
		panel2.add(choix);
		panel1.add(panel2, BorderLayout.NORTH);
		panel3.add(info);
		panel4.add(infoVol);
		panel1.add(panel4, BorderLayout.CENTER);
		add(panel3, BorderLayout.NORTH);
		add(panel1, BorderLayout.CENTER);		
	}
	
	//On crée la liste des bols existant pour le combo box.
	private void creerListe() {
		Set<String> temp =vols.vols.keySet();
		Object[] temp2 = temp.toArray();
		listeVol = new String[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			listeVol[i] = (String)temp2[i];
		}		
		volsEx = new JComboBox(listeVol);
	}
	
	//Affiche les détails du vol choisi.
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
	
	//Crée un tableau de JButton affichant les sièges réservés du vol pour pouvoir annuler.
	private void afficherSiege() {
		siegesB = new ArrayList<JButton>();
		for(Map.Entry<Siege, Passager> st:selected.sieges.entrySet()) {
			if(st.getValue() != null) {
				JButton temp = new JButton(st.getKey().toString() +" " + st.getValue().toString());
				siegesB.add(temp);
			}
			
		}
		System.out.println("siegesB.size() = " + siegesB.size());
		if(js != null){
			panel4.remove(js);
		}
		panel5.removeAll();
		panel5 = new JPanel();
		panel5.setLayout(new GridLayout(selected.siegeRangees, selected.siegeColonnes));
		for(JButton j:siegesB) {
			j.addActionListener(this);
			panel5.add(j);
		}
		js = new JScrollPane(panel5);
		js.setPreferredSize(new Dimension(500, 300));
		panel4.add(js);
		repaint();
	}
	
	//On trouve le siège cliqué et on annule la réservation.
	public void actionPerformed(ActionEvent e) {
		Passager p;
		String temp = new String();
		JButton button = null;
		for(JButton j:siegesB) {
			if(e.getSource().equals(j)) {
				String[] temp2 = j.getText().split(" ");
				temp = temp2[0];
				button = j;
			}
		}
		int siege1 = Integer.parseInt(temp.substring(0, temp.length()-1));
		temp = temp.substring(temp.length()-1);
		Siege s = new Siege(siege1, temp);
		p = selected.sieges.get(s);
		
		String mess = "Voulez-vous enlever la réservation de " + p.toString() + " pour ce vol?";
		int n = JOptionPane.showConfirmDialog(gui.frame, mess, "Suppression d'une réservation", JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			vols.enleverPassager(selected, s);
			js.remove(button);
		}
		afficherVol((String)volsEx.getSelectedItem());
		gui.frame.repaint();			
	}
}
