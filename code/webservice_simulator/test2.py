from pip._vendor import requests

import web
import json

urls = (
    '/hello/', 'index',
    '/results/', 'get_result'
)



class index:
    def GET(self):
        # How to obtain the name key and then print the value?
        #data = json.loads(web.data())
        #value = data["name"]
        print("Hello !")
        #return "Hello " + value + "!"


class get_result:
    def POST(self):
        print(web.data())
        data = json.loads(web.data())
        print(data)
        value = data["api_dev_key"]
        print(value)
        return "Hello tout est ok for me===><===!"


if __name__ == '__main__':
    app = web.application(urls, globals())
    app.run()
