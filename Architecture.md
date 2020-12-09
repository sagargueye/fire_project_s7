# Architecture des applications
* gérer les interactions entre les Serveur Web, les DataBase, le Simulator et l’EmergencyManagere
* la partie Simulator et la partie Emergency doivent être le plus faiblement couplées
possible

### Architecture similateur

|class incendie 					| |
|---|---|
|Attribut						|methodes|
|---|---|
|coordonnées(GPS)					|set_intensite (int new_intensité)|
|intensité				 		||
|date_incendie||


|class Intervention 						||
|---|---|
|Attribut|methodes|
|---|---|
|incendie						|Detecter_incendie ()
|liste_pompiers						|Detecter_service_urgence ()
|liste_camions
|date_intervention
	
	
|class caserne						||
|---|---|
|Attribut|methodes|
|---|---|
|coordonnées					|add_pompier (Pompier p)
|list camions					|add_camion (Camion C)
|list pompiers					|remove_pompier (Pompier p)
|adress_postal					|remove_camion (Camion C)
|						|set_coordonnees (int x, int y)
	
|class pompier 						||
|---|---|
|Attribut|methodes|
|---|---|
|matricule
|nom
	
|class camion 						||
|---|---|
|Attribut|methodes|
|---|---|
|dimension|
|informations|


**SCHEMA MPD BASE DE DONNES SIMULATEUR**

|Entitees					|attributs			|type attribut|	
|-----|-----|-----|
|incendie					|id				|int|
|						|(x, y)( de 0 à 9)		|point|
|						|intensite			|int|
|						|date_incendie			|datetime|

### Architecture Emergency	
	
|class incendie 			||
|---|---|
|Attribut|methodes|
|---|---|
|coordonnées					|set_intensite (int new_intensité)|
|intensité				 	|
|date_incendie				 	|
	
|class intervation				||
|---|---|
|Attribut|methodes|
|---|---|
|caserne				|Detecter_incendie ()
|incendie				|affecter_caserne ()
|date_intervation			|Detecter_service_urgence () 
	
|class caserne				||
|---|---|
|Attribut|methodes|
|---|---|
|coordonnées			|add_pompier (Pompier p)
|list camions			|add_camions (Camion c)
|list pompiers			|remove_pompier (Pompier p)
|				|remove_camions (Camion c)
|				|set_coordonnees (int x, int y)
	
|class pompier 				||
|---|---|
|Attribut|methodes|
|---|---|
|matricule|
|nom|
	
|class camion 				||
|---|---|
|Attribut|methodes|
|---|---|
|dimension				|
|informations				|	
	
**SCHEMA MPD BASE DE DONNES Emergency**

|Entitees					|attributs				|type attribut|	
|-----|-----|-----|
|incendie|					|id					|int|
| 						|(x, y)				    	|point|
| 						|intensite ( de 0 à 9)			|int|
| 						|date_incendie				|datetime|
|-----|-----|-----|
|caserne|					|id					|int|
| 						|(x, y)				    	|point|
| 						|adresse postal		    		|varchar 255|
|						|telephone				|varchar 10|
|---|---|---|
|pompier|					|id					|int|
| 						|matricule				|varchar 13|
| 						|nom					|varchar 50|
| 						|id_caserne			    	|int|
|---|---|---|
|camion |					|id					    |int|
|   						|Volume en m3			|int|	
| 						|plaque_immatriculation		|varchar 13|
| 						|is_available			|bool|
| 						|id_caserne			    |int|
|---|---|---|
|pompier_intervenant|
| 							|id				    |int|
| 							|id_pompier			    |int|
| 							|id_camion				|int|
| 							|date					|datetime|
|---|---|---|
|intervention |
| 							|id					    |int|
| 							|id_incendie			|int|
| 							|id_camion				|int|			
| 							|duree_intervention	    |int|
| 							|date_intervention		|datetime|
|coordonnees_capteur |
|	|coord_matrice|Point|
|	|coord_latlong|Point|




### Centrale de simulation 

*	Son rôle est de générer des feux dont les coordonnées, l’intensité et la fréquence sont à définir dans le programme 

*	Ces données sont stockés dans une BD de simulation. 

*	Ensuite sont envoyées par ondes radio au Data Center 

### Data center 
* Le Data Center est la partie « métier » des services de gestion d’incendie
* Emergency Manager est une application java qui gère une flotte de services d’urgence 
(casernes, camions, pompiers), détecte les feux (dans la BD) et déploie les ressources nécessaires pour les
éteindre (quel(s) camion(s) de quelle(s) caserne(s) est (sont) affectés à quel(s) feu(x)). 
les interactions se font via Les interfaces de supervision des casernes

### Des données doivent être envoyées dans un serveur de stockage sur le Cloud à des fins
de statistiques et visualisations.==> c'est la deuxieme application java qui gere les statistiques 

