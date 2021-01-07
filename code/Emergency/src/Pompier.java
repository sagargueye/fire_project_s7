/***********************************************************************
 * Module:  Pompier.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Pompier
 ***********************************************************************/
import java.time.LocalDateTime;
import java.util.*;

class Pompier {
	public int idPompier;
	public String matricule;
	public String nom;
	public int contact;
	public Intervention intervention;

	
	public Pompier(int idPompier, String matricule, String nom, int contact) {
		this.idPompier = idPompier;
		this.matricule = matricule;
		this.nom = nom;
		this.contact = contact;
	}

	public Pompier(int idPompier, String matricule, String nom, int contact, Intervention intervention) {
		this.idPompier = idPompier;
		this.matricule = matricule;
		this.nom = nom;
		this.contact = contact;
		this.intervention = intervention;
	}

	public int getIdPompier() {
		return this.idPompier;
	}

	public String getMatricule() {
		return this.matricule;
	}

	public String getNom() {
		return this.nom;
	}

	public int getContact() {
		return this.contact;
	}

	@Override
	public String toString() {
		return "Pompier [idPompier=" + this.idPompier + ", matricule=" + this.matricule + ", nom=" + this.nom + ", contact=" + this.contact
				+ "]";
	}

	public Intervention getIntervention() {
		return this.intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
