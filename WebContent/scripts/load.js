var toLoad = new Array();

function downloadJSAtOnload()
{
	for(var x = 0; x < toLoad.length; x++)
	{
	    var element = document.createElement("script");
	    element.src = toLoad[x];
	    document.body.appendChild(element);
	}
}
function loadJS(toLoad)
{
	this.toLoad = toLoad;
	
	if (window.addEventListener) window.addEventListener("load", downloadJSAtOnload, false);
	else if (window.attachEvent) window.attachEvent("onload", downloadJSAtOnload);
	else window.onload = downloadJSAtOnload;
}
