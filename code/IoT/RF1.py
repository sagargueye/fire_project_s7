# Add your Python code here. E.g.
from microbit import *
from utime import ticks_ms, ticks_diff
import radio


def fletcher_16_sums(data):
   sum_lo = 0
   sum_hi = 0
   for byte in data:
     sum_lo = (sum_lo + byte) % 255
     sum_hi = (sum_lo + sum_hi) % 255
   return (sum_lo, sum_hi)

def fletcher_16(data):
   sum_lo, sum_hi = fletcher_16_sums(data)
   return (sum_hi << 8) | sum_lo

def fletcher_16_check_bytes(data):
   sum_lo, sum_hi = fletcher_16_sums(data)
   check_byte_lo = 255 - ((sum_lo + sum_hi) % 255)
   check_byte_hi = 255 - ((sum_lo + check_byte_lo) % 255)
   return (check_byte_lo, check_byte_hi)


def xor(msg, cle):
    xored = bytes(map(lambda byte: byte ^ cle, msg))
    return xored

def do_envoi(msg):
    TAILLE_CHARGE_UTILE = 30
    TAILLE_MESSAGE_COMPLET = 32

    taille_message = len(msg)
    nb_envois = taille_message // TAILLE_CHARGE_UTILE if taille_message % TAILLE_CHARGE_UTILE == 0 else (taille_message // TAILLE_CHARGE_UTILE) + 1
    num_envoi = 1
    to_send = [num_envoi, nb_envois]
    
    for byte in msg:
        to_send.append(byte)
        if len(to_send) == TAILLE_MESSAGE_COMPLET:
            radio.send_bytes(bytes(xor(to_send, 0x42)))
            num_envoi += 1
            to_send = [num_envoi, nb_envois]
            
    if len(to_send) > 2:
        radio.send_bytes(bytes(xor(to_send, 0x42)))
        

radio.on()
radio.config(data_rate=radio.RATE_2MBIT)
display.show("E")
last_received = None

buff = b""

while True:
    if (uart.any()):
        buff += uart.read()
        last_received = ticks_ms()
    elif last_received is not None:
        now = ticks_ms()
        diff = ticks_diff(now, last_received)
        if diff > 500:
            do_envoi(buff)
            buff = b""
            last_received = None
    else:
        pass

            
        
