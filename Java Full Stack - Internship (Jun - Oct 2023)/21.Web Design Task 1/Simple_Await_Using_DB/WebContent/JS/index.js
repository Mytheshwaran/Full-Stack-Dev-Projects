function sendRequest(formData){
	return new Promise((resolve, reject) => {
		$.ajax({
	    	         type: "GET",
	   	 	         url: "goto",
	        	     data: formData,
	            }).done(function(data){
	     			setTimeout(resolve,2000,data);
	    		}).fail(function(error){
					setTimeout(reject,2000,error);
				});
	});
}