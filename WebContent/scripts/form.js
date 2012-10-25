function Form(submitId)
{
	this.submit = document.getElementById(submitId);
	this.errors = 0;
	this.inputs = new Array();

	this.setButton = function(id)
	{
		this.submit = document.getElementById(id);
	};
	
	this.addInput = function(id, tooltipMsg, type)
	{
		this.errors++;
		this.inputs[id] = false;

		var element = document.getElementById(id);
		var defValue = element.value;
		var _this = this;
		
		var click = function()
		{
			switch(type)
			{
				case "Password":
					_this.makePassword(element);
					break;
				default:
					_this.clearDefault(element, defValue);
			}
		};
		element.addEventListener("focusin", click, false);

		if(type.length > 0)
		{
			var validate = function()
			{
				switch(type)
				{
					case "NotBlank":
						_this.validateNotBlank(element);
						break;
					case "Email":
						_this.validateEmail(element);
						break;
					case "Password":
						_this.validatePassword(element);
						break;
				}
				
				_this.resetDefault(element, defValue);
			};
			
			element.addEventListener("focusout", validate, false);
		}
		
		if(tooltipMsg.length > 0)
		{
			element.addEventListener("mouseover", function(){showTooltip(tooltipMsg);}, false);
			element.addEventListener("mousemove", function(){changeTooltipPosition();}, false);
			element.addEventListener("mouseout", function(){hideTooltip();}, false);
		}
	};
	
	this.clearDefault = function(element, defValue)
	{
		if(element.value === defValue)
		{
			element.value = "";
		}
	};
	
	this.resetDefault = function(element, defValue)
	{
		if(element.value === "")
		{
			element.value = defValue;
			element.type = "text";
		}
	};

	this.makePassword = function(element)
	{
		element.value = "";
	    element.type = "password";
	};
	
	this.validateNotBlank = function(element)
	{
	    this.setValidity(element, element.value !== null && element.value !== "");
	};
	
	this.validatePassword = function(element)
	{
		this.setValidity(element, element.value.length >= 6);
	};
	
	this.validateEmail = function(element)
	{
		var pattern = "^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$";

		this.setValidity(element, element.value.match(pattern));
	};
	
	this.setValidity = function(element, isValid)
	{
		var wasValid = this.inputs[element.id]; // green 
		this.inputs[element.id] = isValid;
		
		if (isValid)
	    {
			element.style.borderColor = '#0F0'; // green
	    }
	    else
		{
	    	element.style.borderColor = '#F00';// red
		}
		
		if(!wasValid && isValid) this.errors--; // fixed an error
		else if(wasValid && !isValid) this.errors++; // created an error

		this.validateForm();
		
		return isValid;
	};
	
	this.validateForm = function()
	{
		if(this.errors === 0)
		{
			this.submit.disabled = "";
			this.submit.className = getButtonClass();
		}
		else
		{
			this.submit.disabled = "disabled";
			this.submit.className = "disabled button";
		}
	};
}

