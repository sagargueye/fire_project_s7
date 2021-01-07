/***********************************************************************
 * Module:  Caserne.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Caserne
 ***********************************************************************/
import java.util.*;

class Caserne {
	public int idCaserne;
	public Coordonnees coordCaserne;
	public ArrayList<Camion> listCamions = new ArrayList<Camion> ();
	public ArrayList<Pompier> listPompiers = new ArrayList<Pompier> ();
	
	public Caserne(int idCaserne, Coordonnees coordCaserne, ArrayList<Camion> listCamions,	ArrayList<Pompier> listPompiers) {
		this.idCaserne = idCaserne;
		this.coordCaserne = coordCaserne;
		this.listCamions = listCamions;
		this.listPompiers = listPompiers;
	}

	public Caserne(int idCaserne, Coordonnees coordCaserne) {
		this.idCaserne = idCaserne;
		this.coordCaserne = coordCaserne;
		this.listCamions = new ArrayList<Camion> ();
		this.listPompiers = new ArrayList<Pompier> ();
	}

	public void addCamion(Camion c) {
		this.listCamions.add(c);
	}
	
	public void deleteCamion(Camion c) {
		this.listCamions.remove(c);
	}
	
	public void addPompier(Pompier p) {
		this.listPompiers.add(p);
	}
	public void deletePompier(Pompier p) {
		this.listPompiers.remove(p);
	}

	@Override
	public String toString() {
		return "Caserne [idCaserne=" + this.idCaserne + ", coordCaserne=" + this.coordCaserne + ", listCamions=" + this.listCamions
				+ ", listPompiers=" + this.listPompiers + "]";
	}

	public ArrayList<Camion> getListCamions() {
		return this.listCamions;
	}

	public void setListCamions(ArrayList<Camion> listCamions) {
		this.listCamions = listCamions;
	}

	public ArrayList<Pompier> getListPompiers() {
		return this.listPompiers;
	}

	public void setListPompiers(ArrayList<Pompier> listPompiers) {
		this.listPompiers = listPompiers;
	}

	public int getIdCaserne() {
		return this.idCaserne;
	}

	public Coordonnees getCoordCaserne() {
		return this.coordCaserne;
	}	

}
