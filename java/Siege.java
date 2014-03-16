/* Siege.java
 * Par Guillaume Lahaie
 * 
 * Cette classe est utilisée pour créer les clés de la sortedMap sieges d'un 
 * objet vol. Comparable est implémenté pour pouvoir obtenir l'ordre voulu.
 *
 *Dernière modification: 6 décembre 2011.
 * 
 */

public class Siege implements Comparable<Siege>{

	private int rangee;
	private String colonne;
	
	//Constructeur
	public Siege(int rangee, String colonne) {
		this.rangee = rangee;
		this.colonne = colonne;
	}

	@Override
	public int compareTo(Siege that) {
		if(this == that)
			return 0;
		int temp = rangee - that.rangee;
		return temp!=0?temp:colonne.compareTo(that.colonne);
	}
	
	public boolean equals(Object that) {
		if(this  == that)
			return true;
		if(!(that instanceof Siege))
			return false;
		Siege temp = (Siege)that;
		return this.colonne.equals(temp.colonne) && this.rangee == temp.rangee ;
	}
	
	public int hashCode() {
		return 37*rangee + colonne.hashCode();
	}
	
	public String toString() {
		return String.format("%d%s", rangee, colonne);
	}
}
	
