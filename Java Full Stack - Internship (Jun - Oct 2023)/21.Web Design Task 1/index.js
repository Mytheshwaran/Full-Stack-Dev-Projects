//This
const person = {
  firstName:"fistName",
  lastName: "lastName",
  display: function() {
    let x = document.getElementById("bind");
    x.innerHTML = this.firstName + " " + this.lastName;
  }
}

//undefind
function loadData(){
	let load=person.display;
	load();
}
/* ALTER
Bind
	person.display.bind(person);
*/

//Bind
$(document).ready(function()
{
		$("#bind").click(function()
		{
			alert("Ready Function Running");
			person.display();
		});
});

//async using timeout
function timeFunction()
{
	setTimeout(myFunction,2000);
}

function myFunction(){
	document.getElementById("details").innerHTML="Hi Mythesh";
}

//promise
async function asyncFunction() 
{
	let newPromise=new Promise(function(resolve){
		resolve("Hiii");
	});
	let result=await newPromise;
	document.getElementById("result").innerHTML=result;
}



