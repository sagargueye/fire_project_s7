/***********************************************************************
 * Module:  Gestionnaire.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Gestionnaire
 ***********************************************************************/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
//import org.json.simple.JSONObject;
import org.json.*;
//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;

public final class GestionnaireEmergency {
	public static ArrayList<Incendie> listObjetIncendieCreee= new ArrayList<Incendie>();
	public static ArrayList<Intervention> listObjetInterventionCreee= new ArrayList<Intervention>();
	public static ArrayList<Camion> listObjetCamionCreee= new ArrayList<Camion>();

	public static Coordonnees genereCoordAleatoir() {
		double minLong= 45.3531152;
		double maxLong= 45.8743837;
		double minLarg= 4.426158699999951;
		double maxLarg= 5.227906;
		double longitude = minLong + (Math.random() * (maxLong - minLong));
		double largitude = minLarg + (Math.random() * (maxLarg - minLarg));
		return new Coordonnees(longitude,largitude);
	}
	
	public static void declencheIncendieChaque5seconde() {
		Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            public void run() {
        		Coordonnees c=genereCoordAleatoir();
        		int intensite=(int) (1 + (Math.random() * (10 - 1)));
        		Incendie i=new Incendie( c, intensite, java.time.LocalDateTime.now() ) ;
        		//System.out.println(listObjetIncendieCreee.toString());
                //System.out.println( i.toString() );
            }
        };
        //minuteur.schedule(tache, 0, 5000);
	}
	
	public static Incendie InListObjectIncendie(ArrayList<Incendie> tableau, int idIncendie) {
		for ( Incendie o: tableau ) {
			if ( o.getIdIncendie()== idIncendie)
			 return o;
		}
		return null;
	}
	public static Camion InListObjectCamion(ArrayList<Camion> tableau, int id) {
		for ( Camion o: tableau ) {
			if ( o.getIdCamion()== id)
			 return o;
		}
		return null;
	}
	public static Intervention InListObjectIntervention(ArrayList<Intervention> tableau, int id) {
		for ( Intervention o: tableau ) {
			if (  o.getIdIntervention()== id )
			 return o;
		}
		return null;
	}

	public static Incendie getIncendieByIntervention (ArrayList<Incendie> listIncendie, Intervention i) {
		for ( Incendie o: listIncendie ) {
			if ( o.getIntervention().equals(i) )
			 return o;
		}
		return null;
	}
	
	public static void afficheListCamion( ArrayList<Camion> listCamion ) {
		System.out.println("Affichage des Camion: ");
		for ( Camion o: listCamion ) {
			System.out.println(o.toString());
		}
	}
	
	public static void afficheListIntervention( ArrayList<Intervention> listIntervention ) {
		System.out.println("Affichage des Intervention: ");
		for ( Intervention o: listIntervention ) {
			System.out.println(o.toString());
		}
	}
	public static void afficheListIncendie( ArrayList<Incendie> listIncendie ) {
		System.out.println("Affichage des incident: ");
		for ( Incendie o: listIncendie ) {
			System.out.println(o.toString());
		}
	}

	public static void getListcamionIntervenantChaque5seconde(String j_string) {
		Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            public void run() {
                System.out.println("coucou 2!");
                
                ArrayList<Incendie> listObjetIncendie= new ArrayList<Incendie>();
            	ArrayList<Intervention> listObjetIntervention= new ArrayList<Intervention>();
            	ArrayList<Camion> listObjetCamion= new ArrayList<Camion>();
            	
                JSONObject json_obj = new JSONObject(j_string);
        		JSONArray json_array_camion = json_obj.getJSONArray("camions");
        		JSONArray json_array_invention = json_obj.getJSONArray("intervention");
        		JSONArray json_array_incendie = json_obj.getJSONArray("incendie");
        		
        		//System.out.println(obj.getString("camions")); 
        		//System.out.println(json_array_camion.toString());
        		
        		//intervention
        		for (int i = 0; i < json_array_invention.length(); i++) {
        		    JSONObject element = json_array_invention.getJSONObject(i);
        		    int id=element.getInt("id");
        		    
        		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		    String datedeb= element.getString("datedeb");
        		    LocalDateTime debutdateTime = LocalDateTime.parse(datedeb, formatter);
        		    String datefin= element.getString("datefin");
        		    LocalDateTime findateTime = null;
        		    if(datefin != null && !datefin.trim().isEmpty()) {
        		    	findateTime = LocalDateTime.parse(datefin, formatter);
        		    }
        		    Intervention intervtion = new Intervention ( id,   debutdateTime);
        		    if(findateTime != null) {
        		    	intervtion.setFinIntervention(findateTime);
        		    }
        		    listObjetIntervention.add(intervtion);
        		}//end listes intervention
        		
        		//afficheListIntervention(listObjetIntervention);
        		
        		//incendie
        		for (int i = 0; i < json_array_incendie.length(); i++) {
        		    JSONObject element = json_array_incendie.getJSONObject(i);
        		    int id=element.getInt("id");
        		    int idIntervention=element.getInt("idintervention");
        		    int intensite=element.getInt("intensite");
        		    
        		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		    String datedeb= element.getString("datedeb");
        		    LocalDateTime debutdateTime = LocalDateTime.parse(datedeb, formatter);
        		    String datefin= element.getString("datefin");
        		    LocalDateTime findateTime = null;
        		    if(datefin!= null && !datefin.trim().isEmpty()) {
        		    	findateTime = LocalDateTime.parse(datefin, formatter);
        		    }
        		    JSONObject coordonnees=element.getJSONObject("coordonnees");
        		    Coordonnees coordonneesIncendie= new Coordonnees(coordonnees.getDouble("longitude"),coordonnees.getDouble("latitude"));
        		    
        		    if( InListObjectIntervention(listObjetIntervention,idIntervention) != null) {
        		    	Intervention intervention = InListObjectIntervention(listObjetIntervention,idIntervention);
        		    	Incendie incdie = new Incendie(id, intervention, coordonneesIncendie, intensite, debutdateTime );
        		    	if(findateTime != null) {
        		    		incdie.setFinIncendie(findateTime);
            		    }
            		    listObjetIncendie.add(incdie);
            		}
        		}//end listes incendie

        		afficheListIncendie(listObjetIncendie);
        		
        		//camions
        		for (int i = 0; i < json_array_camion.length(); i++) {
        		    JSONObject element = json_array_camion.getJSONObject(i);
        		    int idCamion=element.getInt("id");
        		    int idIntervention=element.getInt("idintervention");
        		    String matricule=element.getString("matricule");
        		    double volume=element.getDouble("volume");
        		    
        		    JSONObject coordcamion=element.getJSONObject("coordcamion");
        		    Coordonnees coordonneesCamion= new Coordonnees(coordcamion.getDouble("longitude"),coordcamion.getDouble("latitude"));
        		    
        		    JSONObject coordcaserne=element.getJSONObject("coordcaserne");
        		    Coordonnees coordonneesCaserne= new Coordonnees(coordcaserne.getDouble("longitude"),coordcaserne.getDouble("latitude"));
        		    
        		    //pour chaque camion qui est en intervention, 
        		    //lintensité du feu qui lui est associé, diminue de 1 point
        		    if( InListObjectIntervention(listObjetIntervention,idIntervention) != null) {
        		    	Intervention intervention = InListObjectIntervention(listObjetIntervention,idIntervention);
            		    Camion c = new Camion ( idCamion, intervention, coordonneesCamion, coordonneesCaserne,  matricule,  volume );
            		    listObjetCamion.add(c);
            		    Incendie inc = getIncendieByIntervention(listObjetIncendie, intervention) ;
            		    //si ya toujours lincendie
            		    if(inc != null && inc.getFinIncendie()==null) {
            		    	int position=listObjetIncendie.indexOf(inc);
            		    	inc.setIntensite( inc.getIntensite() - 1 );
            		    	listObjetIncendie.set( position,inc);
            		    }
            		}
        		}//end listes camions
        		
        		//afficheListCamion(listObjetCamion);
        		afficheListIncendie(listObjetIncendie);

            }
        };
        minuteur.schedule(tache, 0, 10000);
	}
	
	public static void main(String[] args) {
		declencheIncendieChaque5seconde();
		String j_string="{\r\n" + 
				"  \"camions\": [\r\n" + 
				"    {\r\n" + 
				"      \"id\": 1,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 9,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 12,\r\n" + 
				"      		\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 12,\r\n" + 
				"			\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 2,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 9,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 12,\r\n" + 
				"      		\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 12,\r\n" + 
				"			\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 3,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 7,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 12,\r\n" + 
				"      		\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 12,\r\n" + 
				"			\"latitude\": 29,\r\n" + 
				"		},\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"intervention\": [\r\n" +
				"    {\r\n" + 
				"      \"id\": 9,\r\n" + 
				"      \"datedeb\": \"2020-12-21 03:50:25\",\r\n" + 
				"      \"datefin\": \"\",\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 7,\r\n" + 
				"      \"datedeb\": \"2020-12-20 03:50:25\",\r\n" + 
				"      \"datefin\": \"2020-12-20 04:50:25\",\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"incendie\": [\r\n" +
				"    {\r\n" + 
				"      \"id\": 3,\r\n" + 
				"      \"intensite\": 5,\r\n" + 
				"      \"idintervention\": 9,\r\n" +  
				"      \"datedeb\": \"2020-12-21 02:50:25\",\r\n" + 
				"      \"datefin\": \"\",\r\n" +  
				"      \"coordonnees\": {\r\n" + 
				"			\"longitude\": 12,\r\n" + 
				"			\"latitude\": 29,\r\n" + 
				"		}\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 1,\r\n" + 
				"      \"intensite\": 6,\r\n" + 
				"      \"idintervention\": 7,\r\n" +  
				"      \"datedeb\": \"2020-12-20 02:50:25\",\r\n" +  
				"      \"datefin\": \"2020-12-20 04:50:25\",\r\n" +  
				"      \"coordonnees\": {\r\n" + 
				"			\"longitude\": 12,\r\n" + 
				"			\"latitude\": 29,\r\n" + 
				"		}\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
		getListcamionIntervenantChaque5seconde(j_string);
        
	}

}
