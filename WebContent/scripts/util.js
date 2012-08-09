function getRequest()
{
	var request = null;
	
	if(window.XMLHttpRequest)
	{
		request = new XMLHttpRequest();
	}
	else if(window.ActiveXObject)
	{	
		try
		{
			request = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(exp)
		{
			alert("AJAX is broken :/ (are you using an old version of IE?)");
		}
	}
	return request;
}

var colors = new Array('red', 'blue', 'green', 'yellow');
var last = -1;
function getButtonClass()
{
	last = (last+1)%colors.length;
	//return colors[last] + " button";
	return "red button";
}

//tooltip stuff
function changeTooltipPosition() 
{
  var tooltipX = event.pageX - 8;
  var tooltipY = event.pageY + 8;
  $('div.tooltip').css({top: tooltipY, left: tooltipX});
}
function showTooltip(msg) 
{
	$('div.tooltip').remove();
	$('<div class="tooltip">' + msg + '</div>').appendTo('body');
	changeTooltipPosition(event);
}
function hideTooltip() 
{
   $('div.tooltip').remove();
}
