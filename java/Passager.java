/* Passager.java
 * Par Guillaume Lahaie
 * 
 * Cette classe gère les passagers pour les vols. Elle sert aussi à associer les
 * passagers avec leur liste de réservation.
 * 
 * Dernière modification: 4 décembre 2011.
 * 
 */

public class Passager implements Comparable<Passager>{
	
	private String nom, prenom;
	
	public Passager(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public String toString() {
		return nom +" " + prenom;
	}
	
	public boolean equals(Object that) {
		if(this == that)
			return true;
		if(!(that instanceof Passager))
			return false;
		Passager temp = (Passager)that;
		return this.nom.equals(temp.nom) && this.prenom.equals(temp.prenom);
	}
	
	public int hashCode() {
		return 37*nom.hashCode() + prenom.hashCode();
	}
	
	public int compareTo(Passager that) {
		if(this==that)
			return 0;
		int nomComp = this.nom.compareTo(that.nom);
		return nomComp !=0?nomComp:this.prenom.compareTo(that.prenom);
	}

}
