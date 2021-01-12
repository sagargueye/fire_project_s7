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
import org.json.*;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
//import org.json.JSONArray.*;

//import org.json.simple.parser.ParseException;
//import org.json.simple.parser.JSONParser;

public final class GestionnaireSimulator {
	public static ArrayList<Incendie> listObjetIncendieCreee= new ArrayList<Incendie>();
	public static ArrayList<Intervention> listObjetInterventionCreee= new ArrayList<Intervention>();
	public static ArrayList<Camion> listObjetCamionCreee= new ArrayList<Camion>();
	public static 		String json_teste="{\r\n" + 
			"  \"camions\": [\r\n" + 
			"    {\r\n" + 
			"      \"id\": 1,\r\n" + 
			"      \"volume\": 29,\r\n" + 
			"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
			"      \"idintervention\": 9,\r\n" +  
			"      \"coordcamion\": {\r\n" + 
			"      		\"longitude\": 45.52946204106026,\r\n" + 
			"      		\"latitude\": 4.658654529256415,\r\n" + 
			"		},\r\n" + 
			"      \"coordcaserne\": {\r\n" + 
			"			\"longitude\": 45.7531152,\r\n" + 
			"			\"latitude\": 4.5375623228476325,\r\n" + 
			"		},\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"id\": 2,\r\n" + 
			"      \"volume\": 29,\r\n" + 
			"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
			"      \"idintervention\": 9,\r\n" +  
			"      \"coordcamion\": {\r\n" + 
			"      		\"longitude\": 45.63442137355949,\r\n" + 
			"      		\"latitude\": 4.487364103421158,\r\n" + 
			"		},\r\n" + 
			"      \"coordcaserne\": {\r\n" + 
			"			\"longitude\": 45.49148193558382,\r\n" + 
			"			\"latitude\": 4.714634697349413,\r\n" + 
			"		},\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"id\": 3,\r\n" + 
			"      \"volume\": 29,\r\n" + 
			"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
			"      \"idintervention\": 7,\r\n" +  
			"      \"coordcamion\": {\r\n" + 
			"      		\"longitude\": 45.35333174258658,\r\n" + 
			"      		\"latitude\": 4.984505650563788,\r\n" + 
			"		},\r\n" + 
			"      \"coordcaserne\": {\r\n" + 
			"			\"longitude\": 45.84467390699428,\r\n" + 
			"			\"latitude\": 4.8723810478889575,\r\n" + 
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
			"			\"longitude\": 45.62114824385641,\r\n" + 
			"			\"latitude\": 5.006238221068346,\r\n" + 
			"		}\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"id\": 1,\r\n" + 
			"      \"intensite\": 6,\r\n" + 
			"      \"idintervention\": 7,\r\n" +  
			"      \"datedeb\": \"2020-12-20 02:50:25\",\r\n" +  
			"      \"datefin\": \"2020-12-20 04:50:25\",\r\n" +  
			"      \"coordonnees\": {\r\n" + 
			"			\"longitude\": 45.35983091284036,\r\n" + 
			"			\"latitude\": 5.20419245773059,\r\n" + 
			"		}\r\n" + 
			"    }\r\n" + 
			"  ]\r\n" + 
			"}";	

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
				System.out.println("Déclenche incendie");
				Coordonnees c=genereCoordAleatoir();
				int intensite=(int) (1 + (Math.random() * (10 - 1)));
				Incendie i=new Incendie( c, intensite, java.time.LocalDateTime.now() ) ;
				//System.out.println(listObjetIncendieCreee.toString());
				//System.out.println( i.toString() );
				String data="{\"incendie\": [{" + 
						"\"longitude\": "+ c.getLongitude()+"," +
				        "\"latitude\": "+ c.getLatitude()+"," +
				        "\"intensite\": "+intensite+"," +
				        "\"debutIncendie\": \""+i.getDebutIncendie()+"\"" + 
				        "}]}";
				String result=POSTRequest("http://127.0.0.1:8080/new_incendie/",data ) ;
				System.out.println( result );
			}
		};
		minuteur.schedule(tache, 0, 5000);
	}
	
	public static String removeLastChar(String s) {
	    return (s == null || s.length() == 0)
	      ? null
	      : (s.substring(0, s.length() - 1));
	}

	
	public static Incendie getIncendieByIntervention (ArrayList<Incendie> listIncendie, Intervention i) {
		for ( Incendie o: listIncendie ) {
			if ( o.getIntervention().equals(i) )
				return o;
		}
		return null;
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

	public static void gestionEvolutionIncendie() {
		System.out.println("======debut  gestionEvolutionIncendie!======");
		String j_string_camion = GETRequest("http://127.0.0.1:8080/get_list_camion_intervenant/","");
		String j_string_incendie = GETRequest("http://127.0.0.1:8080/get_list_incendie_with_intervention/","");
		
		ArrayList<Incendie> listObjetIncendie= new ArrayList<Incendie>();
		ArrayList<Intervention> listObjetIntervention= new ArrayList<Intervention>();
		ArrayList<Camion> listObjetCamion= new ArrayList<Camion>();

		JSONObject json_obj = new JSONObject(j_string_incendie);
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

		//afficheListIncendie(listObjetIncendie);

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
			//si les coordonnees du camion est pareilles que celle de lincendie
			if( InListObjectIntervention(listObjetIntervention,idIntervention) != null) {
				Intervention intervention = InListObjectIntervention(listObjetIntervention,idIntervention);
				Camion c = new Camion ( idCamion, intervention, coordonneesCamion, coordonneesCaserne,  matricule,  volume );
				listObjetCamion.add(c);
				Incendie inc = getIncendieByIntervention(listObjetIncendie, intervention) ;
				//si ya toujours lincendie
				if(inc != null && inc.getFinIncendie()==null) {
					if(inc.getCoordonnes().equals(coordonneesCamion)) {//les camions sont sur place
						int position=listObjetIncendie.indexOf(inc);
						inc.setIntensite( inc.getIntensite() - 1 );
						if(inc.getIntensite() == 0) {//fin de lincendie
							inc.setFinIncendie(java.time.LocalDateTime.now());
							intervention.setFinIntervention(java.time.LocalDateTime.now());//fin intervention
							int indexIntervention=listObjetIntervention.indexOf(intervention);
							listObjetIntervention.set(indexIntervention,intervention);
						}
						listObjetIncendie.set(position,inc);
					}

				}
			}
		}//end listes camions

		afficheListCamion(listObjetCamion);
		afficheListIncendie(listObjetIncendie);

		Gson gson = new Gson();
		String jsonc = gson.toJson(listObjetCamion);
		String jsoni = gson.toJson(listObjetIncendie);
		String jsonint = gson.toJson(listObjetIntervention);

		System.out.println(json_teste);
		//System.out.println(jsonc);
		json_obj.put("camions",jsonc);
		json_obj.put("intervention",listObjetIncendie);
		json_obj.put("incendie",listObjetIntervention);

		json_teste=json_obj.toString();
		System.out.println(json_obj.toString());
		
		System.out.println("=======fin  gestionEvolutionIncendie!======");
	}

	public static String getListcamionIntervenantChaque5seconde() {
		String j_string="{\r\n" + 
				"  \"camions\": [\r\n" + 
				"    {\r\n" + 
				"      \"id\": 1,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 9,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 45.52946204106026,\r\n" + 
				"      		\"latitude\": 4.658654529256415,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 45.7531152,\r\n" + 
				"			\"latitude\": 4.5375623228476325,\r\n" + 
				"		},\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 2,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 9,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 45.63442137355949,\r\n" + 
				"      		\"latitude\": 4.487364103421158,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 45.49148193558382,\r\n" + 
				"			\"latitude\": 4.714634697349413,\r\n" + 
				"		},\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 3,\r\n" + 
				"      \"volume\": 29,\r\n" + 
				"      \"matricule\": \"DDCHB347YYD\",\r\n" + 
				"      \"idintervention\": 7,\r\n" +  
				"      \"coordcamion\": {\r\n" + 
				"      		\"longitude\": 45.35333174258658,\r\n" + 
				"      		\"latitude\": 4.984505650563788,\r\n" + 
				"		},\r\n" + 
				"      \"coordcaserne\": {\r\n" + 
				"			\"longitude\": 45.84467390699428,\r\n" + 
				"			\"latitude\": 4.8723810478889575,\r\n" + 
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
				"			\"longitude\": 45.62114824385641,\r\n" + 
				"			\"latitude\": 5.006238221068346,\r\n" + 
				"		}\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"id\": 1,\r\n" + 
				"      \"intensite\": 6,\r\n" + 
				"      \"idintervention\": 7,\r\n" +  
				"      \"datedeb\": \"2020-12-20 02:50:25\",\r\n" +  
				"      \"datefin\": \"2020-12-20 04:50:25\",\r\n" +  
				"      \"coordonnees\": {\r\n" + 
				"			\"longitude\": 45.35983091284036,\r\n" + 
				"			\"latitude\": 5.20419245773059,\r\n" + 
				"		}\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";	
		return j_string;
	}

	public static void gestionDeplacementCamions() {
		System.out.println("GERER LE DECPLACEMENT!!!!!!!!!!!!!!!!!!!!!");
		String j_string_camion = GETRequest("http://127.0.0.1:8080/get_list_camion_intervenant/","");
		String j_string_incendie = GETRequest("http://127.0.0.1:8080/get_list_incendie_with_intervention/","");
		System.out.println(j_string_camion);
		
		ArrayList<Incendie> listObjetIncendie= new ArrayList<Incendie>();
		ArrayList<Intervention> listObjetIntervention= new ArrayList<Intervention>();
		ArrayList<Camion> listObjetCamion= new ArrayList<Camion>();

		//JSONObject json_obj = new JSONObject(j_string_camion);
		//JSONArray json_array_camion = json_obj.getJSONArray("camions");
		//JSONArray json_array_incendie = json_obj.getJSONArray("incendie");
		
		
	    JSONObject json_obj_camions = new JSONObject(j_string_camion);
	    JSONObject json_obj_incendies = new JSONObject(j_string_incendie);
		//System.out.println(json_obj_camions);


		String json_camion_edit="{\"camions\" : [";

		//on boucle sur la liste des camions:
		// pour chaque camions partis en intervention et qui n'as pas encore atteint sa destination,
		// sa longitude et sa latitude tendend vers la longitude et la latitude de l'incendie qui lui est associé
		for (int c = 0; c < json_obj_camions.length(); c++) {
			JSONObject json_obj2_camions = json_obj_camions.getJSONObject(String.valueOf(c));
			//JSONObject element = json_array_camion.getJSONObject(c);
			JSONObject element=json_obj2_camions;
			int idCamion=element.getInt("id_camion");
			int idInterventionFromCamion=element.getInt("id_intervention");
			String matricule=element.getString("immatriculation");
			double volume=element.getDouble("volume");

			double latitude=element.getDouble("latitude");
			double longitude=element.getDouble("longitude");
			Coordonnees coordonneesCamion= new Coordonnees(longitude,latitude);

			String result_caserne= GETRequest("http://127.0.0.1:8080/get_coordcaserne_by_idcamion?id="+String.valueOf(idCamion),"");
			System.out.println(result_caserne);
			JSONObject json_obj_caserne = new JSONObject(result_caserne);
			JSONObject json_obj2_caserne = json_obj_camions.getJSONObject(String.valueOf(0));
			double latitude_caserne=element.getDouble("latitude");
			double longitude_caserne=element.getDouble("longitude");
			Coordonnees coordonneesCaserne= new Coordonnees(longitude_caserne, latitude_caserne);

			//Intervention intervention = InListObjectIntervention(listObjetIntervention,idInterventionFromCamion);// a delete
			//on boucle sur les incendies pour recupere celle lié à ce camion 
			for (int i = 0; i < json_obj_incendies.length(); i++) {
				JSONObject elementIncendie = json_obj_incendies.getJSONObject(String.valueOf(i));
				int idInterventionFromIncendie=elementIncendie.getInt("idintervention");
				//mm id intervention ==> donc c'est notre incendie
				if(idInterventionFromIncendie == idInterventionFromCamion) {
					//à present on peux recuperer ses coordonnées
					JSONObject coordonnees=elementIncendie.getJSONObject("coordonnees");
					Coordonnees coordonneesIncendie= new Coordonnees(coordonnees.getDouble("longitude"),coordonnees.getDouble("latitude"));

					//on verifie si lincendie est toujours dactualité 
					// si oui on gere l'allé des camions sinon on gere leur retour
					String datefin= elementIncendie.getString("datefin");
					if(datefin == null || datefin.trim().isEmpty()) {//ca brule toujours de partout wahouhh!!
						//mm coordonnees => le camion est deja sur place ==> dans ce cas on fait rien
						//coordonnees differente ==> le camion n'est pas encore arrivé à destination (ctd lieux de lincendie)==>traitement
						if (!coordonneesIncendie.equals(coordonneesCamion)) {
							//longitude
							double difLong= coordonneesIncendie.getLongitude() -  coordonneesCamion.getLongitude();
							if(Math.abs(difLong)> 0.1) {
								//on lui rajoute/enleve 0.1
								coordonneesCamion.setLongitude(coordonneesCamion.getLongitude() + ((difLong < 0) ? -0.1 : +0.1) );
							}else {
								coordonneesCamion.setLongitude(coordonneesIncendie.getLongitude());
							}

							//latitude
							double difLat= coordonneesIncendie.getLatitude() -  coordonneesCamion.getLatitude();
							if(Math.abs(difLat)> 0.5) {
								//on lui rajoute/enleve 0.5
								coordonneesCamion.setLatitude(coordonneesCamion.getLatitude() + ((difLat < 0) ? -0.5 : +0.5) );
							}else {
								coordonneesCamion.setLatitude(coordonneesIncendie.getLatitude());
							}
						}
					}//OMG ya plus de feu (^^)
					//Retour des camions
					else {
						//mm coordonnees que la caserne => le camion est deja retourné à la caserne==>ya pas de traitement 
						//coordonnees differente ==> le camion a quitté lincendie et est deja sur la route de retour à la caserne==> traitement
						if (!coordonneesCaserne.equals(coordonneesCamion)) {
							//longitude
							double difLong= coordonneesCaserne.getLongitude() -  coordonneesCamion.getLongitude();
							if(Math.abs(difLong)> 0.1) {
								//on lui rajoute/enleve 0.1
								coordonneesCamion.setLongitude(coordonneesCamion.getLongitude() + ((difLong < 0) ? -0.1 : +0.1) );
							}else {
								coordonneesCamion.setLongitude(coordonneesCaserne.getLongitude());
							}

							//latitude
							double difLat= coordonneesCaserne.getLatitude() -  coordonneesCamion.getLatitude();
							if(Math.abs(difLat)> 0.5) {
								//on lui rajoute/enleve 0.5
								coordonneesCamion.setLatitude(coordonneesCamion.getLatitude() + ((difLat < 0) ? -0.5 : +0.5) );
							}else {
								coordonneesCamion.setLatitude(coordonneesCaserne.getLatitude());
							}
						}
					}
					//Camion camion = new Camion(idCamion, intervention, coordonneesCamion, coordonneesCaserne,  matricule,  volume );
					json_camion_edit +="{" + 
								"id_camion: "+idCamion+"," + 
								"id_intervention: "+idInterventionFromCamion+"," +  
								"longitude: "+coordonneesCamion.getLongitude()+"," + 
								"latitude: "+coordonneesCamion.getLatitude()+"," + 
							"}," ;
					//listObjetCamion.add(camion);

				}
			}
		}
		//json_camion_edit=removeLastChar(json_camion_edit);
		json_camion_edit +="]}";
		System.out.println(json_camion_edit);
		String result=POSTRequest("http://127.0.0.1:8080/deplacement_camion/",json_camion_edit ) ;
		System.out.println( result );
		//json_camion_edit+="],\r\n";
		//afficheListCamion(listObjetCamion); 
		//Gson gson = new Gson();
		//String json = gson.toJson( listObjetCamion);
		//System.out.println(json_teste);
		//System.out.println(json_camion_edit);
		//JSONObject json_edit = new JSONObject(json_camion_edit);
		//json_obj.put("camions",json_camion_edit);
		//json_teste=json_obj.toString();
		//System.out.println(json_edit.toString());
		//System.out.println(json_teste);
		System.out.println("======fin GERER LE DECPLACEMENT=======");
	}
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
	        System.out.println("POST Response Code :  " + responseCode);
	        System.out.println("POST Response Message : " + conn.getResponseMessage());
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
	            System.out.println(response.toString());
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
	private static String GETRequest(String urlCalling, String data) {
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
	public static void main(String[] args) {
		declencheIncendieChaque5seconde();
		Timer minuteur = new Timer();
		TimerTask tache = new TimerTask() {
			public void run() {
				gestionDeplacementCamions();
				//gestionEvolutionIncendie();
			}
		}; minuteur.schedule(tache, 0, 9000);
	}

}
