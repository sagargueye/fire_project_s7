/***********************************************************************
 * Module:  Camion.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Camion
 ***********************************************************************/

import java.util.*;

public class Camion {
	public int idCamion;
	public Coordonnees coordCamion;
	public Coordonnees coordCaserne;
	public String matricule;
	public double volume;
	public Intervention intervention;


	public Camion(int idCamion, Coordonnees coordCamion, Coordonnees coordCaserne, String matricule, double volume) {
		this.idCamion = idCamion;
		this.coordCamion = coordCamion;
		this.coordCaserne = coordCaserne;
		this.matricule = matricule;
		this.volume = volume;
	}
	
	public Camion(int idCamion,Intervention idinterventin, Coordonnees coordCamion,Coordonnees coordCaserne, String matricule, double volume) {
		this.idCamion = idCamion;
		this.intervention = idinterventin;
		this.coordCamion = coordCamion;
		this.coordCaserne = coordCaserne;
		this.matricule = matricule;
		this.volume = volume;
	}

	public String getMatricule() {
		return matricule;
	}

	public Coordonnees getCoordCaserne() {
		return this.coordCaserne;
	}

	public void setCoordCaserne(Coordonnees newCoordonnees) {
		this.coordCaserne = newCoordonnees;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public int getIdCamion() {
		return this.idCamion;
	}

	public double getVolume() {
		return this.volume;
	}

	public Coordonnees getCoordCamion() {
		return this.coordCamion;
	}

	public void setCoordCamion(Coordonnees coordCamion) {
		this.coordCamion = coordCamion;
	}

	@Override
	public String toString() {
		return "Camion [idCamion=" + idCamion + ", coordCamion=" + coordCamion + ", coordCaserne=" + coordCaserne
				+ ", matricule=" + matricule + ", volume=" + volume + ", intervention=" + intervention + "]";
	}

}