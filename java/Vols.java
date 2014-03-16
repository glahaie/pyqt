import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/* Vols.java
 * Par Guillaume Lahaie
 * 
 * Classe permettant de faire la gestion d'objets Vol et Passager. Il est possible
 * d'ajouter ou d'enlever des vols, de réserver un siège pour un passager et de
 * canceller la réservation. On peut aussi afficher les détails d'un vol ou les
 * informations de tous les vols. On peut aussi lire les vols d'un fichier et 
 * écrire les vols sur un fichier. Les vols sont enregistrés dans une Map avec
 * une clé String, la méthode pour créer la clé est située dans la classe Vol.
 * 
 * On garde aussi une liste des passagers et des vols où ils ont une réservation
 * dans une map.
 * 
 * Dernière modification: 4 décembre 2011.
 *  
 */

public class Vols {

	protected Map<String, Vol> vols;
	private SortedMap<Passager, Set<String>> passagers;
	private GUI gui;
		
	public Vols(GUI gui) {
		this.gui = gui;
		vols = new HashMap<String, Vol>();
		passagers = new TreeMap<Passager, Set<String>>();
	}
	
	//Véfifie si le vol existe déjà. Sinon, on crée le vol avec une clée unique.
	public boolean addVol(String depart, String destination, Date dateDepart, int rangees, int colonnes) {
		if(depart.equals("") || destination.equals("") || dateDepart == null) {
			gui.infos.append("Il manque une information pour créer le vol.\n");
			return false;
		}
		Vol temp = new Vol(depart, destination, dateDepart, rangees, colonnes);
		for(Map.Entry<String, Vol> entry:vols.entrySet()) {
			if(entry.getValue().hashCode() == temp.hashCode()) {
				gui.infos.append("Le vol existe déjà.\n");
				return false;
			}
		}
		if(vols.containsValue(temp)) {
			gui.infos.append("Le vol existe déjà.\n");
			return false;
		}
		vols.put(temp.creerCle(), temp);
		gui.infos.append("Ajout du vol " + temp.creerCle() + "\n");
		return true;
	}
	
	//Cette méthode est utilisée lors de la lecture d'un fichier.
	public boolean addVol(Vol v) {
		for(Map.Entry<String, Vol> entry:vols.entrySet()) {
			if(entry.getValue().hashCode() == v.hashCode()) {
				return false;
			}
		}
		if(vols.containsValue(v)) {
			//Le vol existe, on retourne false
			return false;
		}
		vols.put(v.creerCle(), v);
		return true;
	}
	
	//On affiche la liste des vols directement dans l'interface graphique. On pourrait remplacer par
	//System.out.println pour la console.
	protected void afficherVols(JTextArea t) {
		t.setText("");
		int nb = 1;
		if(vols.isEmpty()) {
			t.append("Aucun vol créé pour le moment.\n");
		} else {
			for(String s:vols.keySet()) {
				t.append("Vol #" + nb+"\n");
				t.append("Clé: " + s +"\n");
				t.append("Ville de départ: " + vols.get(s).depart+"\n");
				t.append("Ville de destination: " + vols.get(s).destination+"\n");
				t.append("Date de départ: " + vols.get(s).date() + "\n");
				t.append("Heure de départ: " + vols.get(s).heureDep() + "\n");
				t.append("Passagers ayant une réservation: \n");
				for(Map.Entry<Siege, Passager> entry:vols.get(s).sieges.entrySet()) {
					if(entry.getValue() != null) {
						t.append(entry.getKey() + ":" + entry.getValue() + "\n");
					}
				}
				nb++;
				t.append("\n");
			}
		}	
	}
	
	//On ajoute un passager au vol v. On regarde avant si le passager a déjà réservé un siège
	//sur le vol. Si oui, on retourne faux, on ne peut pas avoir plus d'un siège par passager
	//par vol.
	protected boolean ajouterPassager(String nom, String prenom, Vol v, int siege1, String siege2) {
		  if(nom.equals("") || prenom.equals("")) {
			  System.out.println("Il manque des informations dans le nom ou prenom.");
			  return false;
		  }		 
		  //Vérifier si le passager existe déjà
		  Passager temp = new Passager(nom, prenom);
		  Siege temp2 = new Siege(siege1, siege2);
		  		  
		  if(passagers.containsKey(temp)) {
			  //Le passager existe déjà, on ajoute seulement le vol
			  if(passagers.get(temp).contains(v.creerCle())) {
				  //Le passager est déjà sur de vol
				  gui.infos.append("Le passager a déjà une réservation sur ce vol.\n");
				  return false;
			  } else {
				  passagers.get(temp).add(v.creerCle());
			  }
		  } else {
			  //On crée le passager et l'ensemble de vol qui lui est relié
			  passagers.put(temp,  new TreeSet<String>());
			  passagers.get(temp).add(v.creerCle());
		  }
		  v.reserverSiege(temp2, temp);
		  gui.infos.append("Siège Réservé.\n");
		  return true;		  
	}
	
	//On enlève la réservation du siège demandé.
	//Comme on sait déjà quel passager est à quel siège, on ne vérifie pas le nom.
	public boolean enleverPassager(Vol v, Siege siege) {
		Passager temp = v.enleverP(siege);
		passagers.get(temp).remove(v.creerCle());
		gui.infos.append("La réservation ce le siège " + siege + " est annulée.\n");
		return true;
	}

	//Retire le vol correspondant à la clé de la liste des vols.
	protected boolean enleverVol(String cle) {
		if(!vols.containsKey(cle)) {
			gui.infos.append("Impossible de canceller le vol.\n");
			return false;
		} else {
			//Enlever de la liste des passagers.
			Vol temp = vols.get(cle);
			enleverPassagers(cle, temp);			
			vols.remove(cle);
			gui.infos.append("Le vol " + cle + " a été supprimé.\n");
			return true;
		}
	}
	//On enlève un certain vol de tous les passagers.
	private void enleverPassagers(String cle, Vol v) {
		
		List<Passager> temp = new ArrayList<Passager>();
		for(Map.Entry<Siege, Passager> entry:v.sieges.entrySet()) {
			if(entry.getValue() != null) {
				temp.add(entry.getValue());
			}
		}
		//On enlève le vol de la liste des passagers.
		for(Passager pa:temp) {
			passagers.get(pa).remove(cle);
		}
	}
	
	//On affiche les détails du vol v dans le JTextArea textArea.
	protected void afficherVol(Vol v, JTextArea textArea) {	
		textArea.append("Vol: " + v.creerCle() + "\n");
		textArea.append("Ville de départ: " + v.depart + "\n");
		textArea.append("Ville d'arrivée: " + v.destination + "\n");
		textArea.append("Date de départ: " + v.date() + "\n");
		textArea.append("Heure de départ: " + v.heureDep() + "\n");
		for(Map.Entry<Siege, Passager> entry:v.sieges.entrySet()) {
			textArea.append(entry.getKey().toString() + ": ");
			if(entry.getValue() != null)
				textArea.append(entry.getValue().toString());
			textArea.append("\n");
		}
	}
	
	//Affiche tous les passagers et les vols où ils ont une réservation
	//dans le JTextArea donné.
	protected void afficherPassagers(JTextArea t) {
		t.setText("");
		if(passagers.isEmpty())
			t.append("Aucun passager pour le moment.\n");
		else {
			for(Map.Entry<Passager, Set<String>> m:passagers.entrySet()) {
				t.append("Passager: " + m.getKey().toString()+"\n");
				if(!m.getValue().isEmpty()) {
					t.append("Vols: ");
					for(String s:m.getValue()) {
						t.append(s + " ");
					}
					t.append("\n");
				}
			}
		}
	}
	
	//Lit du fichier choisit les vols et réservations enregistrées. Si le vol
	//existe déjà, il n'est pas ajouté.
	protected void readFile() {
		String oneLine, depart, destination, siege2;
		int rangees, colonnes, siege1;
		boolean nouveauVol = false;
		Date d = null;
		Vol temp = null;
		JFileChooser chooser = new JFileChooser();
		FileReader f = null;
		BufferedReader br = null;
		String[] s;
		
		try {
			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				f = new FileReader(chooser.getSelectedFile());
				br = new BufferedReader(f);
			}
		} catch (FileNotFoundException e) {
			gui.infos.append("Impossible d'ouvrir le fichier.\n");
		} catch (NullPointerException e1) {
			gui.infos.append("Aucun fichier choisi.\n");
		}
			
		try {
			while((oneLine = br.readLine()) != null) {
				if(oneLine.trim().equals("Nouveau Vol")) {
					//On commence un nouveau vol
					depart = br.readLine().trim();
					destination = br.readLine().trim();
					d = new SimpleDateFormat("yyyy:MM:dd:HH:mm").parse(br.readLine());
					rangees = Integer.parseInt(br.readLine().trim());
					colonnes = Integer.parseInt(br.readLine().trim());	
					temp = new Vol(depart, destination, d, rangees, colonnes);
					nouveauVol = addVol(temp);					
				}
				while(nouveauVol) {
					//Si le vol a été ajouté, on ajoute les passagers.
					oneLine = br.readLine().trim();
					if(oneLine.equals("Fin vol")) {
						nouveauVol = false;
					} else {
						s = oneLine.split(" ");
						if(s.length > 1) {
							siege1 = Integer.parseInt(s[0].substring(0, s[0].length()-1));
							siege2 = s[0].substring(s[0].length()-1);
							ajouterPassager(s[1], s[2], temp, siege1, siege2);
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			gui.infos.append("Problème lors de la lecture du fichier.\n");
		} catch (NullPointerException e1) {
			gui.infos.append("NullPointerException lors de la lecture du fichier.\n");
		} catch(ParseException e2) {
			gui.infos.append("ParseException lors de la lecture de la date.");
		}
		gui.infos.append("Fichier ouvert et lu.\n");
	}
	
	//Enregistre dans un fichier les informations de tous les vols entrés.
	protected void saveFile() {
		JFileChooser chooser = new JFileChooser();
		FileWriter f = null;
		BufferedWriter b = null;
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				f = new FileWriter(chooser.getSelectedFile());
				b = new BufferedWriter(f);
				for(Map.Entry<String, Vol> map:vols.entrySet()) {
					b.write("Nouveau Vol\n");
					b.write(map.getValue().depart+"\n");
					b.write(map.getValue().destination+"\n");
					b.write(map.getValue().dateSave()+"\n");
					b.write(map.getValue().siegeRangees+"\n");
					b.write(map.getValue().siegeColonnes+"\n");
					for(Map.Entry<Siege, Passager> entry:map.getValue().sieges.entrySet()) {
						if(entry.getValue() == null) {
							b.write(entry.getKey().toString()+"\n");
						} else {
							String temp = String.format("%s %s", entry.getKey().toString(), 
									entry.getValue().toString());
							b.write(temp+"\n");
						}
					}
					b.write("Fin vol\n");
				}
				b.close();
			}catch (IOException e) {
				gui.infos.append("Problème lors de l'écriture du fichier.\n");
			}
		}
		gui.infos.append("Fichier enregistré.\n");
	}	
}
