import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* VolsGUI_E.java
 * Par Guillaume Lahaie
 * 
 * Classe pour un JPanel qui permet de supprimer un vol déjà présent.
 * La liste des vols est mise à jour pour toujours présenter seulement des vols
 * qui existent toujours.
 * 
 * Dernière modification: 4 décembre 2011.
 * 
 */

public class VolsGUI_E extends JPanel {
	
	private JComboBox volsEx;
	private JLabel volsL, infoPane;
	private JButton effacerVol;
	private JPanel panel1, panel2, panel3;
	private Vols vol;
	GUI gui;
	private String[] listeVol;
	
	//Constructeur
	VolsGUI_E(Vols v, GUI gui) {
		this.gui = gui;
		this.setLayout(new GridLayout(5, 1));
		this.vol = v;
		this.setSize(GUI.LARGEUR, 500);
		creerListe();
		creerObjets();
		placerObjets();
	}

	//On créé les objets de l'interface.
	private void creerObjets() {
		
		infoPane = new JLabel("Suppression de vols");
		infoPane.setFont(new Font("diaglog", Font.PLAIN, 24));
		volsL = new JLabel("Liste des vols existant");
		
		effacerVol = new JButton("Effacer un vol");
		effacerVol.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String temp = (String) volsEx.getSelectedItem();
				String mess = "Voulez-vous effacer le vol " + temp + " ?";
				int n = JOptionPane.showConfirmDialog(gui.frame, mess, "Effacer un vol", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					vol.enleverVol(temp);
					gui.switchPanelEfface();
				}
			}
		});
				
		panel1 = new JPanel(new FlowLayout());
		panel2 = new JPanel(new FlowLayout());
		panel3 = new JPanel(new FlowLayout());
	}
	
	//Place les objets graphiques dans le panel.
	private void placerObjets() {

		panel1.add(volsL);
		panel1.add(volsEx);		
		panel2.add(infoPane);
		panel3.add(effacerVol);				
		add(panel2);
		add(panel1);
		add(panel3);
	}
	
	//On crée le combobox de vols existant à partir de leur clée.
	private void creerListe() {
		Set<String> temp =vol.vols.keySet();
		Object[] temp2 = temp.toArray();
		listeVol = new String[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			listeVol[i] = (String)temp2[i];
		}		
		volsEx = new JComboBox(listeVol);
	}	
}
