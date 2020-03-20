#!/bin/bash

kill -9 `cat application.pid`

if [ $? -eq 0 ]
then
   echo "Stopped application"
fi
