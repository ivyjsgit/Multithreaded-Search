function getHTML(){
var ourRequest= new XMLHttpRequest();
var query="";
ourRequest.open('GET', 'http://localhost:5000/search/cat');
ourRequest.onload=function(){
  console.log(ourRequest.responseText);
	document.getElementById("results").innerHTML=ourRequest.responseText;
}
ourRequest.send();
}