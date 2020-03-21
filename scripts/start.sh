#!/bin/bash

nohup java -jar ./covid19.jar&

if [ $? -eq 0 ]
then
   echo "Started"
fi

sleep 2

tail -150f logs/`ls -alh logs| head -n 4 | tail -n 1 | awk '{print $9}'`