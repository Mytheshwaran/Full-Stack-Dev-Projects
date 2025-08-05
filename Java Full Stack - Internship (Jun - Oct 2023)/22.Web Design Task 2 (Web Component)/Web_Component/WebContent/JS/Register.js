let successfulResponse=function(response)
{
	 	alert(response);
}
let errorResponse=function(error)
{
	alert("Error Occured");
	console.log(error);
}

$(document).ready(function(){
	$("#form").submit(function(event){
		event.preventDefault();
		let phoneNo=[];
		let phoneNo1=$("#p1").val();
		let phoneNo2=$("#p2").val();
		phoneNo.push(phoneNo1);
		if(phoneNo2!="NA")
		{
			phoneNo.push(phoneNo2);
		}
		let formData={
		name: $("#name").val(),
		age: $("#age").val(),
		birth: $("#birth").val(),
		blood:$("#blood-group").val(),
		designation:$("input[name='designation']:checked").val(),
		doorNo:$("#door-no").val(),
		street:$("#street").val(),
		district:$("#district").val(),
		state:$("#state").val(),
		phoneNumbers:phoneNo
		};
		
		sendRequest(formData);
	});
	
});

function sendRequest(formData){
	$.ajax({
    	         type: $("#form").attr('method'),
   	 	         url: "goto",
        	     data: formData,
                 success: successfulResponse,
                 error: errorResponse
            });
}
