/***********************************************************************
 * Module:  Coordonnees.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Coordonnees
 ***********************************************************************/

import java.util.*;

public class Coordonnees {
	public double longitude;
	public double latitude;

	public Coordonnees(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double newLongitude) {
		this.longitude = newLongitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double newLatitude) {
		this.latitude = newLatitude;
	}

	@Override
	public String toString() {
		return "Coordonnees [longitude=" + this.longitude + ", latitude=" + this.latitude + "]";
	}

}