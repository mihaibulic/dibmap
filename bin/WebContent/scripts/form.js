//this file needs util.js

function handleEmailValidationResponse()
{
	if(emailValidationRequest.readyState == 4 && emailValidationRequest.status == 200)
	{
		var validEmail;
		if(emailValidationRequest.responseText === "used")
		{
			document.getElementById("content").innerHTML = "That email address is already registered";
			validEmail = false;
		}
		else if(emailValidationRequest.responseText === "invalid")
		{
			document.getElementById("content").innerHTML = "That email address is invalid";
			validEmail = false;
		}
		else //good
		{
			document.getElementById("content").innerHTML = "Available";
			validEmail = true;
		}
		
		setValidity(document.getElementById("email"), validEmail);
	}
}

var emailValidationRequest = null;
function makeEmailValidationRequest()
{
	emailValidationRequest = getRequest();
	if(emailValidationRequest)
	{
		var email = document.getElementById("email").value;
		emailValidationRequest.onreadystatechange = handleEmailValidationResponse;
		emailValidationRequest.open("POST", "EmailValidator", true);
		emailValidationRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		emailValidationRequest.send("email="+email);
	}
}

function clearDefault(defValue)
{
	var element = document.getElementById(event.target.id);
	
	if(element.value === defValue)
	{
		element.value = "";
	}
}

function makePassword()
{
	var element = document.getElementById(event.target.id);
	element.value = "";
    element.type = "password";
}

var errors = 0;
var validMap = new Array();
function registerInput(id)
{
	errors++;
	validMap[id] = false;
}

function setValidity(element, isValid)
{
	var wasValid = validMap[element.id]; // green 
	validMap[element.id] = isValid;
	
	if (isValid)
    {
		element.style.borderColor = '#0F0'; // green
    }
    else
	{
    	element.style.borderColor = '#F00';// red
	}
	
	if(!wasValid && isValid) errors--; // fixed an error
	else if(wasValid && !isValid) errors++; // created an error
	
	validateForm();
	
	return isValid;
}

function validateNotBlank()
{
    var element=document.getElementById(event.target.id);
    setValidity(element, element.value !== null && element.value !== "");
}    

function validateEmail()
{
	var pattern = "^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$";
	var element=document.getElementById(event.target.id);

	if(setValidity(element, element.value.match(pattern)))
	{
		document.getElementById("content").innerHTML = "Checking email availability...";
		makeEmailValidationRequest();
	}
}

function validateLength(len)
{
	var element=document.getElementById(event.target.id);
	setValidity(element, element.value.length >= len);
}

function validateForm()
{
	if(errors === 0)
	{
		document.getElementById("register").disabled = "";
		document.getElementById("register").className = getButtonClass();
	}
	else
	{
		document.getElementById("register").disabled = "disabled";
		document.getElementById("register").className = "disabled button";
	}
}
