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
 * Classe permettant de voir la liste de tous les passagers.
 *
 *Dernière modification: 7 décembre 2011.
 * 
 */

public class PassagerListeGUI extends JPanel {
	
	JLabel info;
	JPanel panel1, //Pour les instructions // confirmation
		   panel2, //Depart / destination et les vols
		   panel3, //Nom /prénom
		   panel4, //Pour le choix du siège
		   panel5,
		   panel6;
	Vols vols;
	GUI gui;
	JTextArea passagers;
	JScrollPane js;
	
	//Constructeur
	PassagerListeGUI(Vols v, GUI g) {
		this.vols = v;
		this.gui = g;
		this.setSize(GUI.LARGEUR, 500);
		setLayout(new BorderLayout());
		creerObjets();
		placerObjets();
		afficherPassagers();
	}
	
	//Création des objets graphiques du Panel.
	private void creerObjets() {
		info = new JLabel("Liste des passagers");
		info.setFont(new Font("Dialog", Font.PLAIN, 24));
		passagers = new JTextArea();
		passagers.setEditable(false);
		js = new JScrollPane(passagers);
		panel1 = new JPanel(new FlowLayout());
	}
	
	//On place les objets dans le JPanel.
	private void placerObjets() {
		panel1.add(info);
		add(panel1, BorderLayout.NORTH);
		add(js, BorderLayout.CENTER);	
	}
	
	private void afficherPassagers() {
		vols.afficherPassagers(passagers);
	}
}
