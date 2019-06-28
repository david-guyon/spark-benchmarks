#!/usr/bin/python

import os
import sys
from random import seed
from random import randint

desired_size_mb = 50
input_file = "input.txt"

print("Checking if %s already exist" % input_file)
if os.path.exists(input_file):
    print(" - removing %s" % input_file)
    os.remove(input_file)
else:
    print(" - all good")
    
seed(1)

print("\nCreating %s with %dMB of data" % (input_file, desired_size_mb))
with open(input_file, 'w') as f:
    data = ""
    last_size_mb = 0
    while True:
        data += "%d\n" % randint(0, 1000000000)
        current_size_mb = sys.getsizeof(data) / 1024 / 1024
        if int(current_size_mb) > int(last_size_mb):
            print(" - %dMB of data generated" % current_size_mb)
        if current_size_mb >= desired_size_mb:
            print(current_size_mb)
            break
        last_size_mb = current_size_mb
    f.write(data)

