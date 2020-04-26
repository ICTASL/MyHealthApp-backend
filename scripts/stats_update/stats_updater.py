import requests
import time
import datetime
import os

GET_URL = os.getenv('GET_URL')
PUT_URL = os.getenv('PUT_URL')

PUT_USERNAME = os.getenv('PUT_USERNAME')
PUT_PASSWORD = os.getenv('PUT_PASSWORD')

WAIT_TIME_MINS = 5

def print_log(text):
    now = time.time()
    now_string = datetime.datetime.fromtimestamp(now).strftime('%Y-%m-%d %H:%M:%S')
    print(now_string + ": " + text)

def read_and_publish():

    # get data from gov api
    print_log("call get api")

    result = requests.get(GET_URL)
    json_results = result.json()

    print_log("api results received")


    # prepare payload

    data = json_results['data']

    lk_total_case = data['local_total_cases']
    lk_recovered_case = data['local_recovered']
    lk_total_deaths = data['local_deaths']
    lk_total_suspect = data['local_total_number_of_individuals_in_hospitals']

    put_payload = {
        'lk_total_case': lk_total_case
        , 'lk_recovered_case': lk_recovered_case
        , 'lk_total_deaths': lk_total_deaths
        , 'lk_total_suspect': lk_total_suspect
    }

    print_log("prepared payload for put")


    # publish via PUT

    resp = requests.put(PUT_URL, json=put_payload, auth=(PUT_USERNAME, PUT_PASSWORD))

    print_log("put: " +str(resp.status_code))
    print("...")


starttime=time.time()

while True:
    try:
        read_and_publish()
    except Exception as e:
        print(e)
    
    wait_time_seconds = WAIT_TIME_MINS * 60
    time.sleep(wait_time_seconds - ((time.time() - starttime) % wait_time_seconds))

