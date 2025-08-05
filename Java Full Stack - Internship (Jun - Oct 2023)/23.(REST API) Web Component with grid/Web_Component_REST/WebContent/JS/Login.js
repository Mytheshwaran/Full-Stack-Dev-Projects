let successfulResponse=function(response)
{
	 	alert(response);
	 	location.replace('Home.html');
}
let errorResponse=function(error)
{
	alert("User or Password Wrong");
	console.log(error);
}

$(document).ready(function(){
	$("#form").submit(function(event){
		event.preventDefault();
		var formData={
			name:$("#name").val(),
			password:$("#password").val()
			};
		
		console.log(formData)
		sendRequest(formData);
	});
	
});

function sendRequest(formData){
	$.ajax({
    	         type: $("#form").attr('method'),
   	 	         url: "rest/employee/check",
   	 	         contentType:"application/json",
        	     data: JSON.stringify(formData),
                 success: successfulResponse,
                 error: errorResponse
            });
}
