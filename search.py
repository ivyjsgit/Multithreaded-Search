from py4j.java_gateway import JavaGateway
from flask import Flask, jsonify, render_template, request
gateway=JavaGateway()
app = Flask(__name__)

@app.route("/search/<query>")
def hello_world(query):
    return gateway.entry_point.search(query)

@app.route("/search/")
def empty():
    return gateway.entry_point.search("")
@app.route("/")
def test():
    return render_template('index.html')
app.run(host='0.0.0.0')
