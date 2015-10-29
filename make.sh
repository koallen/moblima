#!/bin/bash

javac -d bin -cp gson.jar:src -Xlint:unchecked src/moblima/Moblima.java src/moblima/entity/*.java src/moblima/control/*.java src/moblima/boundary/*.java
echo "Compilation finished"
