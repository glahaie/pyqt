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


/* PassagerVolGUI.java
 * Par Guillaume Lahaie
 * 
 * Classe permettant de voir tous les passagers d'un certain vol.
 *
 *Dernière modification: 5 décembre 2011.
 * 
 */

public class PassagerVolGUI extends JPanel implements ActionListener{
	
	JComboBox volsEx;
	JTextField nomT, prenomT;
	JLabel info;
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
	PassagerVolGUI(Vols v, GUI g) {
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
		info = new JLabel("Liste des passagers");
		info.setFont(new Font("Dialog", Font.PLAIN, 24));
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
		infoVol.append("Passagers du Vol " + s + "\n");
		for(Map.Entry<Siege, Passager> map:selected.sieges.entrySet()) {
			if(map.getValue() != null) {
				infoVol.append(map.getKey().toString() + ": " + map.getValue().toString()+ "\n");
			}
		}
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
