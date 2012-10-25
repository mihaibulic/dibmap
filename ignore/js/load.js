var javascripts = new Array();

function Javascript(type, value)
{
	this.type = type; // ext or int
	this.value = value; //either the function or the location of the ext js file
	this.add = function()
	{
		var script = document.createElement("script");
		script.type= 'text/javascript';
		if(this.type === "ext")
		{
		    script.src = value;
		}
		else
		{
			script.onload = value();
		}
		document.body.appendChild(script);
	};
}

function downloadJSAtOnload()
{
	for(var x = 0; x < javascripts.length; x++)
	{
		javascripts[x].add();
	}
}

function runExternalJS(toRun)
{
	javascripts.push(new Javascript("ext", toRun));
}

function runInternalJS(toRun)
{      
	javascripts.push(new Javascript("int", toRun));
}

if (window.addEventListener) window.addEventListener("load", downloadJSAtOnload, false);
else if (window.attachEvent) window.attachEvent("onload", downloadJSAtOnload);
else window.onload = downloadJSAtOnload;
