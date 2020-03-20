#!/bin/bash

nohup java -jar ./covid19.jar&

if [ $? -eq 0 ]
then
   echo "Started"
fi
