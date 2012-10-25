function EmailVerification(form)
{
	this.emailValidationRequest = null;
	this.form = form;
	
	this.makeEmailValidationRequest = function()
	{
		this.emailValidationRequest = getRequest();
		if(this.emailValidationRequest)
		{
			var email = document.getElementById("email").value;
			this.emailValidationRequest.onreadystatechange = this.handleEmailValidationResponse;
			this.emailValidationRequest.open("POST", "/home/EmailValidator", true);
			this.emailValidationRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			this.emailValidationRequest.send("email="+email);
		}
	};
	
	this.handleEmailValidationResponse = function()
	{
		if(this.emailValidationRequest.readyState == 4 && this.emailValidationRequest.status == 200)
		{
			var validEmail;
			if(this.emailValidationRequest.responseText === "used")
			{
				document.getElementById("content").innerHTML = "That email address is already registered";
				validEmail = false;
			}
			else if(this.emailValidationRequest.responseText === "invalid")
			{
				document.getElementById("content").innerHTML = "That email address is invalid";
				validEmail = false;
			}
			else //good
			{
				document.getElementById("content").innerHTML = "Available";
				validEmail = true;
			}
			
			form.setValidity(document.getElementById("email"), validEmail);
		}
	};
}