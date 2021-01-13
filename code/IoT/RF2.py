# Add your Python code here. E.g.
from microbit import *
from utime import ticks_ms, ticks_diff
import radio

def xor(msg, cle):
    xored = bytes(map(lambda byte: byte ^ cle, msg))
    return xored

def do_envoi(buff):
    uart.write(buff)
    

radio.on()
radio.config()
display.show("R")
buff = b""
last_received = None

while True:
    recv = radio.receive_bytes()

    if recv is not None:
        last_received = ticks_ms()
        recv = xor(recv, 0x42)
        buff += recv[2:]
    elif last_received is not None:
        now = ticks_ms()
        diff = ticks_diff(now, last_received)
        if diff > 500:
            do_envoi(buff)
            buff = b""
            last_received = None
    else:
        pass