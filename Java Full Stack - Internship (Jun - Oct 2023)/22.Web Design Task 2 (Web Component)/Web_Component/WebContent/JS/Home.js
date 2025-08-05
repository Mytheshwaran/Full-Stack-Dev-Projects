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
    refreshButton.setAttribute("onclick","refreshCells()");
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
	
	let removedRow=selectedData[0].ID;
	
	$.ajax({
    	         type: "DELETE",
   	 	         url: "display",
   	 	         data:{ID:removedRow},
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
		$.ajax({
    	         type: "GET",
   	 	         url: "display",
                 success: function(response){
					 displayContent(response);
				 },
                 error:function(error)
				{
					alert("Error Occured");
					console.log(error);
				}
            });
});

var columnDefs = [{
		headerName : 'ID',
		field : 'ID',
		sortable:true,
		filter:true
	},{
		headerName : 'NAME',
		field : 'NAME',
		sortable:true,
		filter:true
	}, {
		headerName : 'AGE',
		field : 'AGE',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'D.O.B',
		field : 'D_O_B',
		sortable:true
	},{
		headerName : 'BLOOD_GROUP',
		field : 'BLOOD_GROUP',
		sortable:true,
		filter:true
	},{
		headerName : 'DESIGNATION',
		field : 'DESIGNATION',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'DOOR_NO',
		field : 'DOOR_NO',
		sortable:true,
		editable: true
	},{
		headerName : 'STREET',
		field : 'STREET',
		sortable:true,
		editable: true
	},{
		headerName : 'DISTRICT',
		field : 'DISTRICT',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'STATE',
		field : 'STATE',
		sortable:true,
		editable: true,
		filter:true
	},{
		headerName : 'CONTACT',
		field : 'PHONE_NUMBER',
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
	json_data = JSON.parse(response);
	data=json_data.data;
	gridOptions = {
		columnDefs : columnDefs,
		rowData: data,
		rowSelection: 'multiple',
		pagination : true,
		onCellValueChanged:(event)=>{updateChanges(event)},
		onCellClicked:(event)=>{
			message();
			}
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
	rows.forEach(x=>data.push(x.ID));
	
	$.ajax({
    	         type: 'DELETE',
   	 	         url: "goto",
        	     data: {ID:data},
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
		let message="ID: "+rowData[0].ID+
		"<br>NAME: "+rowData[0].NAME+
		"<br>AGE: "+rowData[0].AGE+
		"<br>D.O.B: "+rowData[0].D_O_B+
		"<br>BLOOD_GROUP: "+rowData[0].BLOOD_GROUP+
		"<br>DESIGNATION: "+rowData[0].DESIGNATION+
		"<br>DOOR_NO: "+rowData[0].DOOR_NO+
		"<br>STREET: "+rowData[0].STREET+
		"<br>DISTRICT: "+rowData[0].DISTRICT+
		"<br>STATE: "+rowData[0].STATE+
		"<br>CONTACT: "+rowData[0].PHONE_NUMBER;
		
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
   	 	         url: "goto",
        	     data: rowData,
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

function refreshCells(){
	gridOptions.api.redrawRows();
}
