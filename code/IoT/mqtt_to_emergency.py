import paho.mqtt.client as mqtt
from datetime import datetime
from influxdb_client import InfluxDBClient, Point
import json
import requests


BROKER_ADDRESS = "192.168.68.126"
EMERGENCY_WEBSERVICE_URL = "/new_incendie"


def on_connect(client, userdata, flags, rc):
    print("Connecté au broker.")
    client.subscribe("incendies")
    print("Abonné au topic incendies.")


def on_message(client, userdata, msg):
    msg_string = str(msg.payload, "utf-8")
    dicos_points = json.loads(msg_string)
    now = datetime.utcnow()

    incendies = list(map(lambda i: {"incendie":{"intensite": i["intensite"], "latitude": i["x"], "longitude": i["y"], "dateDebut": now.isoformat()}}))

    for incendie in incendies:
        requests.post(EMERGENCY_WEBSERVICE_URL, json=incendie)



def main():
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    client.connect(BROKER_ADDRESS)
    print(f"Début de la boucle d’événements.")
    client.loop_forever()


if __name__ == "__main__":
    main()