/***********************************************************************
 * Module:  Incendie.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Incendie
 ***********************************************************************/

import java.time.LocalDateTime;
import java.util.*;

public class Incendie {
	public int idIncendie;
	private static int lastidIncendie=1;
	public Coordonnees coordonnes;
	public int intensite;
	public LocalDateTime debutIncendie; //= java.time.LocalDateTime.now();
	public LocalDateTime finIncendie ;//= java.time.LocalDateTime.now();
	public Intervention intervention;

	public Incendie(int idIncendie, Coordonnees coordonnes, int intensite, LocalDateTime debutIncendie ) {
		this.idIncendie=idIncendie;
		this.coordonnes=coordonnes;
		this.intensite=intensite;
		this.debutIncendie=debutIncendie;
	}
	public Incendie(int idIncendie, Intervention intervention, Coordonnees coordonnes, int intensite, LocalDateTime debutIncendie ) {
		this.idIncendie=idIncendie;
		this.coordonnes=coordonnes;
		this.intensite=intensite;
		this.intervention=intervention;
		this.debutIncendie=debutIncendie;
	}

	public Incendie( Coordonnees coordonnes, int intensite, LocalDateTime debutIncendie ) {
		this.idIncendie=this.lastidIncendie;
		this.lastidIncendie++;
		this.coordonnes=coordonnes;
		this.intensite=intensite;
		this.debutIncendie=debutIncendie;
	}


	public LocalDateTime getDebutIncendie() {
		return this.debutIncendie;
	}

	public LocalDateTime getFinIncendie() {
		return this.finIncendie;
	}
	public void setFinIncendie(LocalDateTime finIncendie) {
		this.finIncendie = finIncendie;
	}

	public int getIntensite() {
		return this.intensite;
	}   
	public void setIntensite(int newIntensite) {
		if(newIntensite>=0  || newIntensite<10) {
			this.intensite = newIntensite;
		}
	}

	public Coordonnees getCoordonnes() {
		return this.coordonnes;
	}

	public Intervention getIntervention() {
		return this.intervention;
	}
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}	


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Incendie [idIncendie=" + this.idIncendie + ", coordonnes=" + this.coordonnes + ", intensite=" + this.intensite
				+ ", debutIncendie=" + this.debutIncendie + ", finIncendie=" + this.finIncendie + ", intervention=" + this.intervention
				+ "]";
	}

	public int getIdIncendie() {
		return this.idIncendie;
	}

	public Incendie getIncendieByIntervention(Intervention i) {
		if(this.intervention.equals(i)) {
			return this;
		}
		return null;
	}

}