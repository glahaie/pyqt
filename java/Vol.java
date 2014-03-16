import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

/* Vol.java
 * Par Guillaume Lahaie
 * 
 * Classe contenant les informations d'un Vol. On définit un vol par sa ville de départ, sa destination,
 * son heure de départ et le nombre de sièges qu'il contient. Il est possible de réserver un siège sur le
 * vol et aussi de canceller cette réservation.
 * 
 * Dernière modification: 5 décembre
 * 
 */

public class Vol implements Comparable<Vol> {
	
	private final String[] rangees = {"A", "B", "C", "D", "E", "F"};
	static int compteVol = 1; //Permet de créer une clée unique pour un vol.
	protected String depart, destination;
	protected SortedMap<Siege, Passager> sieges;
	protected Date dateDepart;
	private int volNb;
	protected int siegeRangees, siegeColonnes;
    static DateFormat fd = DateFormat.getDateInstance(DateFormat.MEDIUM,
                                     Locale.FRENCH);
	
    
	public Vol(String depart, String destination, Date dateDepart, int siegeRangees, int siegeColonnes) {
		this.depart = depart;
		this.destination = destination;
		this.dateDepart = dateDepart;
		this.siegeRangees = siegeRangees;
		this.siegeColonnes = siegeColonnes;
		sieges = new TreeMap<Siege, Passager>();
		volNb = compteVol;
		compteVol++;
		creerSieges();
	}
	
	public String toString() {
		return String.format("Vol %s, de %s à %s, date de départ: %s %s,", creerCle(), depart, 
				destination, date(), heureDep());
	}
	
	@Override
	public int compareTo(Vol that) {
		if(this == that)
			return 0;
		int depComp = this.depart.compareTo(that.depart);
		depComp = depComp!=0?depComp:this.destination.compareTo(that.destination);
		return depComp!=0?depComp:this.dateDepart.compareTo(that.dateDepart);
	}
	
	public boolean equals(Object that) {
		if(this==that) {
			return true;
		}
		if(!(that instanceof Vol)) {
			return false;
		}
		Vol temp = (Vol)that;
		boolean b = depart.equals(temp.depart) && destination.equals(temp.destination) 
				&& dateDepart.equals(temp.dateDepart) && siegeRangees == temp.siegeRangees &&
				siegeColonnes == temp.siegeColonnes;
		return b;
	}
	
	public int hashCode() {
		return 31*depart.hashCode() + 37*destination.hashCode() + dateDepart.hashCode();
	}
	
	//Crée une clé unique pour représenter le vol.
	protected String creerCle() {
		//On enlève les caractères différents des lettres, et on capitalise.
		StringBuilder sb = new StringBuilder();
		sb.append(depart.replaceAll("[^\\p{L}\\p{N}]", "").toUpperCase().substring(0,3));
		sb.append(destination.replaceAll("[^\\p{L}\\p{N}]", "").toUpperCase().substring(0,3));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		sb.append(formatter.format(dateDepart));
		sb.append(String.format("%03d",volNb));
		return ""+sb;	
	}
	
	//Réserve le siège s pour le passager p. On a déjà vérifié que le passager
	//n'a pas une réservation sur ce vol.
	protected void reserverSiege(Siege s, Passager p) {
		if(!(sieges.get(s) != null)) {
			sieges.put(s, p);
		}
	}
	
	//On cancel la réservation au siège s.
	protected Passager enleverP(Siege s) {
		return sieges.put(s,  null);
	}
	
	//Retourne un string formatté contenant la date de départ du vol.
	protected String date() {
		return fd.format(dateDepart);
	}
	
	//Retourne un string formatté contenant l'heure de départ du vol.
	protected String heureDep() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(dateDepart);
	}
	
	//Retourne un string pour l'écriture d'un vol dans un fichier.
	protected String dateSave() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
		return formatter.format(dateDepart);
	}
	
	//Crée tous les sièges lors de la création de l'objet.
	private void creerSieges() {
		for(int i = 1; i <= siegeRangees; i++) {
			for(int j = 0; j <siegeColonnes; j++) {
				Siege temp = new Siege(i, rangees[j]);
				sieges.put(temp, null);
			}
		}
	}		
}
