from py4j.java_gateway import JavaGateway
from flask import Flask, jsonify, render_template, request
from flask_cors import CORS

gateway=JavaGateway()
app = Flask(__name__)
#Fixes same site
CORS(app)

@app.route("/search/<query>")
def hello_world(query):
	#Connect to Java program and run EntryPoint.search()
    return gateway.entry_point.search(query)

@app.route("/search/")
def empty():
    return gateway.entry_point.search("")
@app.route("/")
def test():
    return render_template('index.html')
#app.run(host='0.0.0.0')
#app.run(host='10.253.197.28')
app.run()