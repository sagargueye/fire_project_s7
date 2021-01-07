# !/usr/bin/env python
# -*- coding: utf-8 -*-

import mysql.connector
import sys
import web
import json
import datetime

HOST = "127.0.0.1"
USER = "root"
PASSWORD = "root"
DATABASE = "bd_simulator"

urls = (
    '/get_camions/', 'list_camions',
    '/get_incendie/', 'list_incendie',
    '/new_incendie/', 'insert_incendie',
    '/update_incendie/', 'update_incendie'
)

app = web.application(urls, globals())


def query_db(cursor, query, args=(), one=False):
    cursor.execute(query, args)
    dicts = {}
    for row in cursor.fetchall():
        for i, value in enumerate(row):
            dicts[cursor.description[i][0]] = value.isoformat() if (
                        isinstance(value, datetime.datetime) and value is not None) else value
    return dicts


def query_select(ma_requete):
    try:
        conn = mysql.connector.connect(host=HOST, user=USER, password=PASSWORD, database=DATABASE)
        cursor = conn.cursor()
        my_query = query_db(cursor, ma_requete)
        json_output = json.dumps(my_query)
        print(json_output)
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


class insert_incendie:
    def POST(self):
        sql_insert = "INSERT INTO `incendie`( `intensite`, `longitude`, `latitude`, `debut_incendie`)	VALUES"
        data = json.loads(web.data())
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

            if 'debut_incendie' in incendie:
                values += "'" + incendie['debut_incendie'] + "'" + ")"
            else:
                values += "null)"
            # print(sql_insert + values)
            return_value += str(query_other(str(sql_insert) + str(values))) + " \n "

        return return_value


if __name__ == '__main__':
    print("Démarrage du service simulator")
    app.run()
