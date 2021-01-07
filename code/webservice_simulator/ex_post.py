# importing the requests library
import requests
import json

# defining the api-endpoint
API_ENDPOINT = "http://127.0.0.1:8080/new_incendie/"

# your API key here
API_KEY = "XXXXXXXXXXXXXXXXX"

# your source code here
source_code = ''' 
print("Hello, world!") 
a = 1 
b = 2 
print(a + b) 
'''

# data to be sent to api
data = {'api_dev_key': API_KEY,
        'api_option': 'paste',
        'api_paste_code': source_code,
        'api_paste_format': 'python'}

data2 = {
    "intervention": [
        {
            "debut_intervention": "2020-12-21 03:50:25",
            "fin_intervention": ""
        },
        {
            "debut_intervention": "2020-12-21 12:45:25",
            "fin_intervention": ""
        }
    ],
    "incendie": [
        {
            "intensite": 5,
            "id_intervention": 1,
            "debut_incendie": "2020-12-21 02:50:25",
            "fin_incendie": "",
            "longitude": 45.62114824385641,
            "latitude": 5.006238221068346,
        },
        {
            "intensite": 6,
            "id_intervention": 2,
            "debut_incendie": "2020-12-20 12:00:25",
            "fin_incendie": "",
            "longitude": 45.35983091284036,
            "latitude": 5.20419245773059
        }
    ]
}

# sending post request and saving response as response object
r = requests.post(url=API_ENDPOINT, data=json.dumps(data2))

# extracting response text
pastebin_url = r.text
print("The pastebin URL is:%s" % pastebin_url)
