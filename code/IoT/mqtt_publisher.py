import paho.mqtt.publish as publish
import serial
import json

BROKER_ADDRESS = "192.168.68.126"
SERIAL_PORT = "COM6"

def handle(line):
    line_str = str(line, "ascii")
    liste_points = list(map(lambda s: s.split(","),map(lambda s: s.strip(")"), line_str.split("("))))[1:]
    dicos_points = list(map(lambda point: {"x": point[0], "y": point[1], "intensite": point[2]}, liste_points))
    json_points = json.dumps(dicos_points)
    publish.single("incendies", json_points, hostname=BROKER_ADDRESS)

def main():
    s = serial.Serial(SERIAL_PORT, 115200, timeout=2)
    line = None

    print("MQTT Publisher en écoute.")

    with s:
        while True:
            while line is None or line == b"":
                line = s.readline()
                if line != b"":
                    handle(line)
                    line = b""

if __name__ == "__main__":
    main()