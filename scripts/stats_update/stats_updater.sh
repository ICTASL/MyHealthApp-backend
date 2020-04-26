
#!/bin/bash

#python3 -m venv venv
#source venv/bin/activate
#pip install -r requirements.txt

eval $(cat config)

python stats_updater.py