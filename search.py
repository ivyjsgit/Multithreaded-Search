from py4j.java_gateway import JavaGateway
from flask import Flask
gateway=JavaGateway()
app = Flask(__name__)

@app.route("/<query>")
def hello_world(query):
    return gateway.entry_point.search(query)

@app.route("/")
def empty():
    return gateway.entry_point.search("")
app.run(host='0.0.0.0')
