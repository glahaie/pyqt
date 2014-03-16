import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/* ListeGUI.java
 * Par Guillaume Lahaie
 * 
 * Classe pour un JPanel qui permet de voir les détails d'un vol. On peut choisir
 * le vol avec un combo box et un bouton.
 * 
 * Dernière modification: 4 décembre 2011.
 * 
 */

public class ListeGUI extends JPanel implements ActionListener {
	
	private JComboBox volsEx;
	private JLabel infoPane, volsL;
	private JButton showListe;
	private JPanel panel1, panel2, panel3;
	private Vols vol;
	GUI gui;
	private String[] listeVol;
	private JTextArea textArea;
	private JScrollPane js;
	private Vol selected;
	
	//Constructeur
	ListeGUI(Vols v, GUI gui) {
		this.gui = gui;
		this.setLayout(new GridLayout(4, 1));
		this.vol = v;
		this.setSize(GUI.LARGEUR, 500);
		this.setLayout(new BorderLayout());
		creerListe();
		creerObjets();
		placerObjets();
	}

	//On créé les objets du Panel.
	private void creerObjets() {		
		infoPane = new JLabel("Voir les informations d'un vol");
		infoPane.setFont(new Font("Dialog", Font.PLAIN, 24));
		volsL = new JLabel("Liste des vols existant");
		
		showListe = new JButton("Afficher ce vol");
		showListe.addActionListener(this);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setSize(600, 400);
		
		js = new JScrollPane(textArea);
		js.setSize(500, 400);
				
		panel1 = new JPanel(new FlowLayout());
		panel2 = new JPanel(new FlowLayout());
		panel3 = new JPanel(new BorderLayout());
	}
	
	//Place les objets dans le JPanel.
	private void placerObjets() {
		panel2.add(infoPane);
		panel1.add(volsL);
		panel1.add(volsEx);
		panel1.add(showListe);
		panel3.add(panel1, BorderLayout.NORTH);
		panel3.add(js, BorderLayout.CENTER);
		add(panel2, BorderLayout.NORTH);
		add(panel3, BorderLayout.CENTER);
	}
	
	//On crée le combobox de vols existant à partir de leur clé.
	private void creerListe() {
		Set<String> temp =vol.vols.keySet();
		Object[] temp2 = temp.toArray();
		listeVol = new String[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			listeVol[i] = (String)temp2[i];
		}		
		volsEx = new JComboBox(listeVol);
	}
	
	public void actionPerformed(ActionEvent e) {
		textArea.setText("");
		String s = (String) volsEx.getSelectedItem();
		selected = vol.vols.get(s);
		vol.afficherVol(selected, textArea);
		repaint();
		setVisible(true);
		
	}
	
}
