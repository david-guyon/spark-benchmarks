#!/bin/python

import os
import sys
from random import seed
from random import randint

desired_size_mb = 10
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
    last_size_mb = 0
    while True:
        current_size_mb = f.tell() / 1024 / 1024
        if int(current_size_mb) > int(last_size_mb):
            print(" - %s is %dMB" % (input_file, current_size_mb))
        if current_size_mb > desired_size_mb:
            break
        value = randint(0, 1000000)
        f.write("%d\n" % value)
        last_size_mb = current_size_mb

