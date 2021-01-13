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
   check_byte_hi = 255 - ((sum_lo + sum_hi) % 255)
   check_byte_lo = 255 - ((sum_lo + check_byte_hi) % 255)
   return (check_byte_hi, check_byte_lo)
