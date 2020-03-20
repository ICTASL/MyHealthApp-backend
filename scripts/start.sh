#!/bin/bash

nohup java -jar ./@project.artifactId@-@project.version@.jar&

if [ $? -eq 0 ]
then
   echo "Started"
fi

tail -150f logs/`ls -alh logs| head -n 4 | tail -n 1 | awk '{print $9}'`