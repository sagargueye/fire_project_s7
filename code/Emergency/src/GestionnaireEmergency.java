/***********************************************************************
 * Module:  Gestionnaire.java
 * Author:  Sagar GUEYE
 * Purpose: Defines the Class Gestionnaire
 ***********************************************************************/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.*;



public final class GestionnaireEmergency {
	public static ArrayList<Incendie> listObjetIncendieCreee= new ArrayList<Incendie>();
	public static ArrayList<Intervention> listObjetInterventionCreee= new ArrayList<Intervention>();
	public static ArrayList<Camion> listObjetCamionCreee= new ArrayList<Camion>();

	
	private static String POSTRequest(String urlString,String data)  {
		String returnValue="";
        //System.out.println(data);
        try {
	        URL url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        //conn.setRequestProperty("userId", "a1bcdefgh");
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setDoOutput(true);
	        OutputStream os = conn.getOutputStream();
	        os.write(data.getBytes());
	        os.flush();
	        os.close();
	
	        int responseCode = conn.getResponseCode();
	        //System.out.println("POST Response Code :  " + responseCode);
	        //System.out.println("POST Response Message : " + conn.getResponseMessage());
	        if (responseCode == HttpURLConnection.HTTP_OK) { //success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    conn.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	
	            while ((inputLine = in .readLine()) != null) {
	                response.append(inputLine);
	            } in .close();
	
	            // print result
	            returnValue=response.toString();
	            //System.out.println(response.toString());
	        } else {
	        	returnValue="POST NOT WORKED";
	            System.out.println("POST NOT WORKED");
	        }
        } catch (Exception e) {
            System.out.println("Exception in NetClientPost:- " + e);
            return "Exception in NetClientPost:- " + e;
        }
		return returnValue;
	}
	
	private static String GETRequest(String urlCalling) {
		String result="";
		try {
            URL url = new URL(urlCalling);//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while (( output=br.readLine() )!= null) {
            	result += output;
                //System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
            return "Exception in NetClientGet:- " + e;
        }
		return result;
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

	public static String getNewIncendies() {
		String result=GETRequest("http://127.0.0.1:8080/list_new_incendie_for_emergency/") ;
		//String result="{\"incendie\":["+reponse+"]}";
		//System.out.println( result );
		return result;
	}
	public static ArrayList<Camion> getCamionNoIntervention(Intervention intervention) {
		String result=GETRequest("http://127.0.0.1:8080/camionsNoIntervention/") ;
		//System.out.println( result );
		ArrayList<Camion> CamionsAvailable= new ArrayList<Camion>();
	    JSONObject json_obj_camions = new JSONObject(result);
		//System.out.println(json_obj_camions);
		//camions
		for (int j = 0; j < json_obj_camions.length(); j++) {
			//System.out.println("*************************");
			JSONObject json_obj2_camions = json_obj_camions.getJSONObject(String.valueOf(j));
			//System.out.println(json_obj2_camions.toString());

		    int id_camion=json_obj2_camions.getInt("id_camion");
		    double volume=json_obj2_camions.getDouble("volume");
		    String type_camion=json_obj2_camions.getString("type_camion");
		    double longitude_camion=json_obj2_camions.getDouble("longitude");
		    double latitude_camion=json_obj2_camions.getDouble("latitude");
		    String immatriculation=json_obj2_camions.getString("immatriculation");
		    int id_caserne=json_obj2_camions.getInt("id_caserne");
		    
		    //System.out.println(id_camion);
		    //System.out.println(volume);
		    //System.out.println(type_camion);
		    //System.out.println(longitude_camion);
		    //System.out.println(latitude_camion);
		    //System.out.println(immatriculation);
		    //System.out.println(id_caserne);
		    Coordonnees coordonneesCamion= new Coordonnees(longitude_camion, latitude_camion);
		    Camion c = new Camion ( id_camion, coordonneesCamion, null,  immatriculation, type_camion, volume,id_caserne,intervention );
		    CamionsAvailable.add(c);
		}
		return CamionsAvailable;
	}
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1,double lon1, double lat2, double lon2) {
	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters
	    distance = Math.pow(distance, 2) ;
	    return Math.sqrt(distance);
	}
	public static void getListcamionIntervenant(String j_string) {
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
	public static ArrayList<Camion>  removeElement(ArrayList<Camion> camions, Camion c) {
		ArrayList<Camion>  result = new ArrayList<Camion> ();
	    for(Camion item : camions)
	        if(!c.equals(item))
	            result.add(item);

	    return result;
	}
	public static ArrayList<Camion> deleteCamionPlusLoin (ArrayList<Camion> camions,Double distance, Camion c){
		Double dMax=distance;
		Camion cMax = null;
		for ( Camion o: camions ) {
			Double odistance= o.getDistanceAssociee();
			if(odistance >= dMax) {
				dMax=odistance;
				cMax=o;
			}
		}
		camions=removeElement(camions,cMax);
		return camions;
	}
	private static String listCamionsToJson(ArrayList<Camion> camionsSelectionnes) {
		String data="{\"camion\": [";
		for ( Camion o: camionsSelectionnes ) {
			data += "{";
			data +="\"id_camion\": "+ o.getIdCamion() +",";
			data +="\"id_intervention\": "+ o.getIntervention().getIdIntervention();			
			data += "},";
		}
		data=removeLastChar(data);
		data += "]}";
		//System.out.println(data);
		return data;		
	}
	public static String removeLastChar(String s) {
	    return (s == null || s.length() == 0)
	      ? null
	      : (s.substring(0, s.length() - 1));
	}
	public static void gestionIntervention(String incendies) {
		System.out.println("------gestion invention---------");
		ArrayList<Camion> CamionsAvailable= new ArrayList<Camion>();
		JSONObject json_obj = new JSONObject(incendies);
		System.out.println(json_obj);

		//incendie
		for (int i = 0; i < json_obj.length(); i++) {
			System.out.println("*************************");
			JSONObject json_obj2 = json_obj.getJSONObject(String.valueOf(i));
			//System.out.println(json_obj2.toString());

		    int id_incendie=json_obj2.getInt("id_incendie");
		    double longitude_incendie=json_obj2.getDouble("longitude");
		    double latitude_incendie=json_obj2.getDouble("latitude");
		    int intensite=json_obj2.getInt("intensite");
		    String debut_incendie_string=json_obj2.getString("debut_incendie");
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 
		    LocalDateTime debut_incendie = LocalDateTime.parse(debut_incendie_string, formatter);
		    
		    //System.out.println(id_incendie);
		    //System.out.println(intensite);
		    //System.out.println(longitude_incendie);
		    //System.out.println(latitude_incendie);
		    //System.out.println(debut_incendie);
		    
		    //on cree lobject intervention
		    Intervention intervention= new Intervention();
		    String result_intervention=POSTRequest("http://127.0.0.1:8080/newIntervention/", "{\"debut_intervention\":\""+intervention.getDebutIntervention()+"\"}");
		    JSONObject json_obj_intervention = new JSONObject(result_intervention);
		    JSONObject json_obj2_intervention = json_obj_intervention.getJSONObject(String.valueOf("0"));
		    int id_intervention=json_obj2_intervention.getInt("MAX(id_intervention)");
			intervention.setIdIntervention(id_intervention);

		    ArrayList<Camion> camions=getCamionNoIntervention(intervention);
		    ArrayList<Camion> camionsSelectionnes= new ArrayList<Camion>();
		    //afficheListCamion(camions);
		    for ( Camion o: camions ) {
		    	Coordonnees coordCamion=o.getCoordCamion();
		    	double latitude_camion=coordCamion.getLatitude();
		    	double longitude_camion=coordCamion.getLongitude();
				//System.out.println(o.getCoordCamion().toString());
				double distance=distance(latitude_incendie, longitude_incendie, latitude_camion, longitude_camion);
				//System.out.println(distance);
				o.setDistanceAssociee(distance);
				camionsSelectionnes.add(o);
				if(camionsSelectionnes.size() > intensite) {
					camionsSelectionnes=deleteCamionPlusLoin(camionsSelectionnes,distance, o);
				}
			}
			afficheListCamion(camionsSelectionnes);
			String result = POSTRequest("http://127.0.0.1:8080/camionsIntervenant/", listCamionsToJson(camionsSelectionnes));
			System.out.println(result);
			
		}
	}
	public static void main(String[] args) {
		Timer minuteur = new Timer();
		TimerTask tache = new TimerTask() {
			public void run() {
				String incendies=getNewIncendies();
				gestionIntervention(incendies);
			}
		};
		minuteur.schedule(tache, 0, 9000);
	}

}
