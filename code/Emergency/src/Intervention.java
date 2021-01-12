/***********************************************************************
 * Module:  Intervention.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Intervention
 ***********************************************************************/

import java.time.LocalDateTime;
import java.util.*;

public class Intervention {
	public int idIntervention;
	//public Incendie incendie;
	//public Camion[] listeCamion;
	public LocalDateTime debutIntervention ;//= java.time.LocalDateTime.now();
	public LocalDateTime finIntervention ;//= java.time.LocalDateTime.now();

	public Intervention(int idIntervention,  LocalDateTime debutIntervention) {
		this.idIntervention = idIntervention;
		//this.incendie = incendie;
		//this.listeCamion = listeCamion;
		this.debutIntervention = debutIntervention;
	}
	public Intervention() {
		this.debutIntervention = java.time.LocalDateTime.now();
	}

	public Intervention(int idIntervention,  LocalDateTime debutIntervention, LocalDateTime finIntervention) {
		this.idIntervention = idIntervention;
		this.debutIntervention = debutIntervention;
		this.finIntervention = finIntervention;
	}
/*	
	public void addCamion(Camion c) {
		int l=this.listeCamion.length;
		this.listeCamion[l]=c;
	}
	
	public Camion[] getListeCamion() {
		return listeCamion;
	}
	public void setListeCamion(Camion[] listeCamion) {
		this.listeCamion = listeCamion;
	}

	public Incendie getIncendie() {
		return this.incendie;
	}
*/
	public LocalDateTime getFinIntervention() {
		return this.finIntervention;
	}

	public void setFinIntervention(LocalDateTime finIntervention) {
		this.finIntervention = finIntervention;
	}

	public int getIdIntervention() {
		return this.idIntervention;
	}

	public void setIdIntervention(int id) {
		this.idIntervention=id;
	}


	public LocalDateTime getDebutIntervention() {
		return this.debutIntervention;
	}
	@Override
	public String toString() {
		return "Intervention [idIntervention=" + idIntervention + ", debutIntervention=" + debutIntervention
				+ ", finIntervention=" + finIntervention + "]";
	}


}