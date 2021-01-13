import paho.mqtt.client as mqtt
from datetime import datetime
from influxdb_client import InfluxDBClient, Point
import json


BROKER_ADDRESS = "192.168.68.126"
INFLUXDB_URL = "http://192.168.68.126:8086"
INFLUXDB_ORG = "CPE"
INFLUXDB_BUCKET = "projet-transversal"
INFLUXDB_TOKEN = "9RiDwt-Z_ovj5h3_8fDjXOZ7rniMRTH7-JwT8groAWfrUwmauTek3PIAWuXd7eC4jICuEmMa-OghPE3gLebLlA=="


def on_connect(client, userdata, flags, rc):
    print("Connecté au broker.")
    client.subscribe("incendies")
    print("Abonné au topic incendies.")


def on_message(client, userdata, msg):
    msg_string = str(msg.payload, "utf-8")
    dicos_points = json.loads(msg_string)
    now = datetime.utcnow()
    to_datapoint = lambda point: Point("incendies").time(now).field("intensite", int(point["intensite"])).tag("x", point["x"]).tag("y", point["y"])
    data_points = list(map(to_datapoint, dicos_points))

    influx_client = InfluxDBClient(INFLUXDB_URL, INFLUXDB_TOKEN, org=INFLUXDB_ORG)
    write_api = influx_client.write_api()
    write_api.write(INFLUXDB_BUCKET, record=data_points)


def main():
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    client.connect(BROKER_ADDRESS)
    print(f"Début de la boucle d’événements.")
    client.loop_forever()


if __name__ == "__main__":
    main()