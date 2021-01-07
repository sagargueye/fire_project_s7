/***********************************************************************
 * Module:  Gestionnaire.java
 * Author:  Utilisateur
 * Purpose: Defines the Class Gestionnaire
 ***********************************************************************/

import java.util.*;

public final class Gestionnaire {
	public java.util.Collection<Intervention> intervention;
	public java.util.Collection<Camion> camion;
	public java.util.Collection<Incendie> incendie;

	public java.util.Collection<Intervention> getIntervention() {
		if (intervention == null)
			intervention = new java.util.HashSet<Intervention>();
		return intervention;
	}

	public java.util.Iterator getIteratorIntervention() {
		if (intervention == null)
			intervention = new java.util.HashSet<Intervention>();
		return intervention.iterator();
	}


	public void setIntervention(java.util.Collection<Intervention> newIntervention) {
		removeAllIntervention();
		for (java.util.Iterator iter = newIntervention.iterator(); iter.hasNext();)
			addIntervention((Intervention)iter.next());
	}


	public void addIntervention(Intervention newIntervention) {
		if (newIntervention == null)
			return;
		if (this.intervention == null)
			this.intervention = new java.util.HashSet<Intervention>();
		if (!this.intervention.contains(newIntervention))
			this.intervention.add(newIntervention);
	}

	public void removeIntervention(Intervention oldIntervention) {
		if (oldIntervention == null)
			return;
		if (this.intervention != null)
			if (this.intervention.contains(oldIntervention))
				this.intervention.remove(oldIntervention);
	}

	public void removeAllIntervention() {
		if (intervention != null)
			intervention.clear();
	}

	public java.util.Collection<Camion> getCamion() {
		if (camion == null)
			camion = new java.util.HashSet<Camion>();
		return camion;
	}

	public java.util.Iterator getIteratorCamion() {
		if (camion == null)
			camion = new java.util.HashSet<Camion>();
		return camion.iterator();
	}


	public void setCamion(java.util.Collection<Camion> newCamion) {
		removeAllCamion();
		for (java.util.Iterator iter = newCamion.iterator(); iter.hasNext();)
			addCamion((Camion)iter.next());
	}


	public void addCamion(Camion newCamion) {
		if (newCamion == null)
			return;
		if (this.camion == null)
			this.camion = new java.util.HashSet<Camion>();
		if (!this.camion.contains(newCamion))
			this.camion.add(newCamion);
	}


	public void removeCamion(Camion oldCamion) {
		if (oldCamion == null)
			return;
		if (this.camion != null)
			if (this.camion.contains(oldCamion))
				this.camion.remove(oldCamion);
	}

	public void removeAllCamion() {
		if (camion != null)
			camion.clear();
	}

	public java.util.Collection<Incendie> getIncendie() {
		if (incendie == null)
			incendie = new java.util.HashSet<Incendie>();
		return incendie;
	}

	public java.util.Iterator getIteratorIncendie() {
		if (incendie == null)
			incendie = new java.util.HashSet<Incendie>();
		return incendie.iterator();
	}


	public void setIncendie(java.util.Collection<Incendie> newIncendie) {
		removeAllIncendie();
		for (java.util.Iterator iter = newIncendie.iterator(); iter.hasNext();)
			addIncendie((Incendie)iter.next());
	}


	public void addIncendie(Incendie newIncendie) {
		if (newIncendie == null)
			return;
		if (this.incendie == null)
			this.incendie = new java.util.HashSet<Incendie>();
		if (!this.incendie.contains(newIncendie))
			this.incendie.add(newIncendie);
	}


	public void removeIncendie(Incendie oldIncendie) {
		if (oldIncendie == null)
			return;
		if (this.incendie != null)
			if (this.incendie.contains(oldIncendie))
				this.incendie.remove(oldIncendie);
	}

	public void removeAllIncendie() {
		if (incendie != null)
			incendie.clear();
	}

}