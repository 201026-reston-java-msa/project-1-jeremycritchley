function sendAjaxGet(url, func) {
	var xhr = new XMLHttpRequest()
			|| new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			func(this);
		}
	};
	xhr.open("GET", url, true);
	xhr.send();
};

function populateTable(xhr) {
	if(xhr.responseText) {
		var res = JSON.parse(xhr.responseText);
		console.log(res);
		if (res === undefined || res.length === 0) {

		} else {
            let table = document.getElementById("reim-table");
            table.innerHTML = "";
			let thead = table.createTHead();
			let row = thead.insertRow();
			console.log("type of res[0]" + typeof(res[0]) +  res[0]);
			for (let [key, val] of Object.entries(res[0])) {
				let th = document.createElement("th");
				let text = document.createTextNode(key);
				th.appendChild(text);
				row.appendChild(th);
            }
            let app = document.createElement("th");
            let apptext = document.createTextNode("APPROVE");
			app.appendChild(apptext);
            row.appendChild(app);

            let deny = document.createElement("th");
            let denytext = document.createTextNode("DENY");
			deny.appendChild(denytext);
            row.appendChild(deny);
            


            let i = 0;
			for (let element of res) {
				let row =  table.insertRow();
				for(key in element) {
					let cell = row.insertCell();
      				let text = document.createTextNode(element[key]);
      				cell.appendChild(text);
                }
                let cell1 = row.insertCell();
                let appButton = document.createElement("button");
                appButton.style = "background-color: #31c6e8;";
                appButton.id = i;
                appButton.class = "btn btn-primary";
                cell1.appendChild(appButton);

                let cell2 = row.insertCell();
                let denyButton = document.createElement("button");
                denyButton.style = "background-color: #31c6e8;";
                denyButton.id = i;
                denyButton.class = "btn btn-primary"
                cell2.appendChild(denyButton);

				i++;
			}

		}
	} 
}

function doPopulateTable() {
    sendAjaxGet(`http://localhost:8080/project-1/portal/reims?status=pending`, populateTable);
}

$(document).ready(function() {
    $("#nav-bar").load("/project-1/nav.html");
    doPopulateTable();
})