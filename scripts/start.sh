#!/bin/bash

java -jar ./covid19-1.0.0-SNAPSHOT.jar&

if [ $? -eq 0 ]
then
   echo "Started"
fi
