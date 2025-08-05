class Grid extends HTMLElement
{
	constructor(){
		super();
		
		const templateTag=document.createElement("template");
		templateTag.innerHTML='<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ag-grid-community@29.3.2/styles/ag-grid.css"><link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ag-grid-community@29.3.2/styles/ag-theme-balham.css"><div id="myGrid" style="height: 15em; width: 100%;" class="ag-theme-balham"></div>';
		
		const ShadowRoot=this.attachShadow({mode:'open'});
		
		ShadowRoot.appendChild(templateTag.content);
	}
}
customElements.define("ag-grid",Grid);

class Message extends HTMLElement{
	constructor(){
		super();
		const divTag=document.createElement("div");
		divTag.setAttribute("class","message");
		const para=document.createElement("p");
		para.setAttribute("id","pop-up");
		
	
		const shadow=this.attachShadow({mode:"open"});
		divTag.append(para);
		shadow.appendChild(divTag);
	}
}
customElements.define("pop-up-msg",Message);

function createTags(grid)
{
	const innerTable=grid.getElementsByClassName('ag-root-wrapper ag-layout-normal ag-ltr');
	const table=innerTable[0];
	const divTag=document.createElement("div");
	divTag.setAttribute("id","divTag");
	
	const searchBox=document.createElement('input');
	searchBox.setAttribute("oninput","filterData()");
	searchBox.setAttribute("placeholder","Search");
	searchBox.setAttribute("id","filter");
	searchBox.setAttribute("style","width:20%");
	divTag.append(searchBox);
	
	
	const button=document.createElement('button');
    button.innerHTML = 'Remove';
    button.setAttribute("style","background-color:red;float:right;");
    button.setAttribute("onclick","removeRows()");
    divTag.append(button);
    const refreshButton=document.createElement("button");
    refreshButton.innerHTML='Refresh';
    refreshButton.setAttribute("style","background-color:lightblue;float:right;");
    refreshButton.setAttribute("onclick","location.reload()");
    divTag.append(refreshButton);
    
	table.prepend(divTag);
	
	const paginationElement=table.lastElementChild;
	const textBox=document.createElement('input');
	textBox.setAttribute("type","number");
	textBox.setAttribute("min","1");
	textBox.setAttribute("placeholder","Pagination Size");
	textBox.setAttribute("id","pagination-num");
	textBox.setAttribute("oninput","setPageSize()");
	textBox.setAttribute("style","float:left");
	paginationElement.append(textBox);
}


class RemoveButton {
  constructor() {
    this.isNew = true;
  }
  init(params) {
    this.params = params;

    this.eGui = document.createElement('div');

    this.deleteButton = document.createElement('button');
    this.deleteButton.innerHTML = 'Remove';
    this.deleteButton.setAttribute("style","background-color:red");
    this.deleteButton.addEventListener('click', this.onDeleteClick.bind(this));

    this.eGui.appendChild(this.deleteButton);

  }

  getGui() {
    return this.eGui;
  }

  refresh() {
    return false;
  }

  onDeleteClick() {
    const selectedData = [this.params.node.data];
    this.params.api.applyTransaction({ remove: selectedData });
	
	let removedRow=[];
	removedRow.push(selectedData[0].id);
	$.ajax({
    	         type: "DELETE",
   	 	         url: "rest/employee/delete",
   	 	         contentType:"application/json",
   	 	         data:JSON.stringify(removedRow),
                 success: function(response){
					 alert(response);
				 },
                 error:function(error)
				{
					alert("Error Occured");
					console.log(error);
				}
            });
  }


}

$(document).ready(function(){
		retrieveTable();
});
function retrieveTable(){
	$.ajax({
    	         type: "GET",
   	 	         url: "rest/employee/retrieve",
                 success: function(response){
					 displayContent(response);
				 },
                 error:function(error)
				{
					alert("Error Occured");
					console.log(error);
				}
            });
}
var columnDefs = [{
		headerName : 'ID',
		field : 'id',
		sortable:true,
		filter:true
	},{
		headerName : 'NAME',
		field : 'name',
		sortable:true,
		filter:true
	}, {
		headerName : 'AGE',
		field : 'age',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'D.O.B',
		field : 'birth',
		sortable:true
	},{
		headerName : 'BLOOD_GROUP',
		field : 'blood',
		sortable:true,
		filter:true
	},{
		headerName : 'DESIGNATION',
		field : 'designation',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'DOOR_NO',
		field : 'doorNo',
		sortable:true,
		editable: true
	},{
		headerName : 'STREET',
		field : 'street',
		sortable:true,
		editable: true
	},{
		headerName : 'DISTRICT',
		field : 'district',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'STATE',
		field : 'state',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'CONTACT',
		field : 'phoneNumbers',
		sortable:true,
		editable: true
	},{
		headerName : 'REMOVE',
		cellRenderer: RemoveButton
	}];
	
		
	var json_data;
	var data;
	var gridOptions;
	
function displayContent(response){
	data=response.data;
	gridOptions = {
		columnDefs : columnDefs,
		rowData: data,
		rowSelection: 'multiple',
		pagination : true,
		onCellValueChanged:(event)=>{updateChanges(event)},
		onCellClicked:(event)=>{
			message();
			},
	};
	const customTag=document.querySelector('ag-grid');
	const grid =customTag.shadowRoot.querySelector('#myGrid');
	new agGrid.Grid(grid, gridOptions);
	
	createTags(grid);
}


function filterData(){
	const customTag=document.querySelector('ag-grid');
	const grid = customTag.shadowRoot.querySelector('#myGrid');
	const innerTable=grid.getElementsByClassName('ag-root-wrapper ag-layout-normal ag-ltr');
	const table=innerTable[0];
	const searchBox=table.firstChild;
	let text=(searchBox.firstChild).value;
	gridOptions.api.setQuickFilter(text);
}
function removeRows()
{
	let rows=gridOptions.api.getSelectedRows();
	gridOptions.api.applyTransaction({remove:rows});
	
	let data=[];
	rows.forEach(x=>data.push(x.id));
	$.ajax({
    	         type: 'DELETE',
   	 	         url: "rest/employee/delete",
   	 	         contentType:"application/json",
        	     data: JSON.stringify(data),
                 success: function(response)
						{
	 						alert(response);
					},
                 error: function(error)
						{
							alert("Error Occured");
							console.log(error);
					}
            });
	
}


function message()
{
		let customTag=document.querySelector("pop-up-msg");
		let customElement=customTag.shadowRoot.querySelector("#pop-up");
		 if (customTag.style.display == 'none') 
		 {
       		 customTag.setAttribute("style",'display:block');
    	 }
    	  else 
    	  {
        		customTag.setAttribute("style","display:none");
    	}
    	
		let rowData=gridOptions.api.getSelectedRows();
		let message="ID: "+rowData[0].id+
		"<br>NAME: "+rowData[0].name+
		"<br>AGE: "+rowData[0].age+
		"<br>D.O.B: "+rowData[0].birth+
		"<br>BLOOD_GROUP: "+rowData[0].blood+
		"<br>DESIGNATION: "+rowData[0].designation+
		"<br>DOOR_NO: "+rowData[0].doorNo+
		"<br>STREET: "+rowData[0].street+
		"<br>DISTRICT: "+rowData[0].district+
		"<br>STATE: "+rowData[0].state+
		"<br>CONTACT: "+rowData[0].phoneNumbers;
		
		customElement.innerHTML=message;
		
		setTimeout(function(){customTag.setAttribute("style","display:none");},2000)
}


function setPageSize()
{
	const customTag=document.querySelector('ag-grid');
	const grid = customTag.shadowRoot.querySelector('#myGrid');
	const innerTable=grid.getElementsByClassName('ag-root-wrapper ag-layout-normal ag-ltr');
	const paginationElement=innerTable[0].lastElementChild;
	const textBox=paginationElement.lastChild;
	let value=textBox.value;
	gridOptions.api.paginationSetPageSize(value);
}

function updateChanges(event)
{
	let rowData=event.data;
	console.log(rowData)
	$.ajax({
    	         type: "PUT",
   	 	         url: "rest/employee/update",
   	 	         contentType:"application/json",
        	     data: JSON.stringify(rowData),
                 success: function(response)
						{
	 						alert(response);
					},
                 error: function(error)
						{
							alert("Error Occured");
							console.log(error);
					}
            });
}
