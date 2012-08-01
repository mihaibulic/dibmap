function HashMap()
{
	this.map = new Array();
	this.keys = new Array();
	
	this.contains = function(key)
	{
		return this.map[key] != null;
	};
	
	// slow
	this.getArray = function()
	{
		var keyValuePairs = new Array();
		
		for(var x = 0; x < this.keys.length; x++)
		{
			keyValuePairs.push(new Array(this.keys[x], this.map[this.keys[x]]));
		}
		
		return keyValuePairs;
	};
	
	this.getSize = function()
	{
		return this.keys.length;
	};
	
	this.get = function(key)
	{
		return this.map[key];
	};
	
	// slow
	this.print = function()
	{
		var output = "";
		for(var x = 0; x < this.keys.length; x++)
		{
			output = output + x + ". " + this.keys[x] + " => " + this.map[this.keys[x]] + "\n";
		}
		
		return output;
	};
	
	this.put = function(key, value)
	{
		if(this.map[key] == null)
		{
			this.keys.push(key);
		}
		this.map[key] = value;
	};
	
	//slow
	this.remove = function(key)
	{
		for(var x = 0; x < this.keys.length; x++)
		{
			if(this.keys[x] === key)
			{
				this.keys.splice(x,1);
			}
		}
		delete this.map[key];
	};
}
