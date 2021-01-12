# !/usr/bin/env python
# -*- coding: utf-8 -*-
import copy
import mysql.connector
import sys
import web
import json
import datetime

HOST = "127.0.0.1"
USER = "root"
PASSWORD = "root"
DATABASE = "bd_emergency"

urls = (
    '/get_camions/', 'list_camions',
    '/get_incendie/', 'list_incendie',
    '/get_intervention/', 'list_intervention',
    '/new_incendie/', 'insert_incendie',
    '/update_incendie/', 'update_incendie',
    '/get_incendie_camion_intervention_from_emergency/', 'get_incendie_camion_intervention_from_emergency',
    '/list_new_incendie_for_emergency/', 'list_new_incendie_for_emergency',
    '/camionsNoIntervention/', 'camionsNoIntervention',
    '/newIntervention/', 'newIntervention',
    '/camionsIntervenant/', 'camionsIntervenant',
    '/get_list_camion_intervenant/', 'get_list_camion_intervenant',
    '/get_list_incendie_with_intervention/', 'get_list_incendie_with_intervention',
    '/get_coordcaserne_by_idcamion/?(.*)', 'get_coordcaserne_by_idcamion',
    '/deplacement_camion/', 'deplacement_camion'
)

app = web.application(urls, globals())


def query_db(cursor, query, args=(), one=False):
    cursor.execute(query, args)
    dicts = {}
    dicts2 = {}
    j = 0
    for row in cursor.fetchall():
        # print(row)
        for i, value in enumerate(row):
            # print(i)
            # print(value)
            dicts[cursor.description[i][0]] = value.isoformat() if (
                    isinstance(value, datetime.datetime) and value is not None) else value
        # print(dicts)
        dicts2[j] = copy.copy(dicts)
        j += 1
    return dicts2


def query_select(ma_requete):
    try:
        conn = mysql.connector.connect(host=HOST, user=USER, password=PASSWORD, database=DATABASE)
        cursor = conn.cursor()
        my_query = query_db(cursor, ma_requete)
        json_output = json.dumps(my_query)
        # print(json_output)
        return json_output

    except mysql.connector.errors.InterfaceError as e:
        print("Error %d: %s" % (e.args[0], e.args[1]))
        sys.exit(1)

    finally:
        # On ferme la connexion
        if conn:
            conn.close()


def query_other(ma_requete):
    try:
        conn = mysql.connector.connect(host=HOST, user=USER, password=PASSWORD, database=DATABASE)
        cursor = conn.cursor()
        try:
            cursor.execute(ma_requete)
            conn.commit()
            return "Requete effectuee avec succes"
        except (mysql.connector.Error, mysql.connector.Warning) as e:
            # En cas d'erreur on annule les modifications
            conn.rollback()
            return e

    except mysql.connector.errors.InterfaceError as e:
        print("Error %d: %s" % (e.args[0], e.args[1]))
        sys.exit(1)

    finally:
        # On ferme la connexion
        if conn:
            conn.close()


class list_camions:
    def GET(self):
        return query_select("""SELECT * FROM camion""")


class list_incendie:
    def GET(self):
        return query_select("""SELECT * FROM incendie""")


class list_intervention:
    def GET(self):
        return query_select("""SELECT * FROM intervention""")


class insert_incendie:
    def POST(self):
        sql_insert = "INSERT INTO `incendie`( `intensite`, `longitude`, `latitude`, `debut_incendie`)	VALUES"
        data = json.loads(web.data().decode("utf-8"))
        return_value = " \n "
        for incendie in data["incendie"]:
            values = ""
            if 'intensite' in incendie:
                values += "(" + str(incendie['intensite']) + ","
            else:
                values += "(null,"

            if 'longitude' in incendie:
                values += str(incendie['longitude']) + ","
            else:
                values += "null,"

            if 'latitude' in incendie:
                values += str(incendie['latitude']) + ","
            else:
                values += "null,"

            if 'debutIncendie' in incendie:
                values += "'" + incendie['debutIncendie'] + "'" + ")"
            else:
                values += "null)"
            print(sql_insert + values)
            return_value += str(query_other(str(sql_insert) + str(values))) + " \n "
        print(return_value)
        return return_value


class camionsNoIntervention:
    def GET(self):
        return query_select("""SELECT * FROM camion where id_intervention is null""")


class newIntervention:
    def POST(self):
        sql_insert = "INSERT INTO `intervention`( `debut_intervention`)	VALUES "
        data = json.loads(web.data().decode("utf-8"))
        return_value = ""
        if 'debut_intervention' in data:
            sql_insert += " ('" + data["debut_intervention"] + "')"
            return_value += str(query_other(sql_insert))
            return query_other("""SELECT MAX(id_intervention) FROM intervention""")

        return "error"


class camionsIntervenant:
    def POST(self):
        data = json.loads(web.data().decode("utf-8"))
        return_value = ""
        for camion in data["camion"]:
            if 'id_camion' in camion:
                id_camion = camion["id_camion"]
                if 'id_intervention' in camion:
                    id_intervention = camion["id_intervention"]
                    sql_insert = "UPDATE camion  SET id_intervention =" + str(
                        id_intervention) + " WHERE id_camion=" + str(id_camion)
                    return_value += str(query_other(sql_insert))
        return return_value


class get_list_camion_intervenant:
    def GET(self):
        return query_select("""SELECT * FROM camion where id_intervention is not null""")


class get_list_incendie_with_intervention:
    def GET(self):
        return query_select("""SELECT * FROM incendie where id_intervention is not null""")


class get_coordcaserne_by_idcamion:
    def GET(self, args):
        user_data = web.input()
        id = user_data.id
        return query_select("SELECT *  FROM caserne where id_caserne= " + str(id))


class deplacement_camion:
    def POST(self):
        sql_update = ""
        data = json.loads(web.data().decode("utf-8"))
        print(data)
        return_value = ""
        for camion in data["camions"]:
            values = " UPDATE camion SET "
            if 'longitude' in camion:
                values += " `longitude`=" + str(camion['longitude']) + ","

            if 'latitude' in camion:
                values += " `latitude`=" + str(camion['latitude']) + ","

            if 'id_camion' in camion:
                values += " WHERE id_camion=" + str(camion['id_camion']) + "; "

            sql_update += values
            print(sql_update)
        return_value += str(query_other(sql_update))
        print(return_value)
        return return_value


if __name__ == '__main__':
    print("Démarrage du service Emergency")
    app.run()
