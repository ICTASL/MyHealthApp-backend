#!/bin/bash

nohup java -jar ./covid19.jar&

if [ $? -eq 0 ]
then
   echo "Started"
fi

sleep 2

tail -150f logs/`ls -alt logs  | grep covid-19 | head -n 1 | awk '{print $9}'`