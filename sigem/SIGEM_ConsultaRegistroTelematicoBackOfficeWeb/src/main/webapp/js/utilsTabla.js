function changeColor(tabla, color){
	if (color == 'true'){
		document.getElementById('tabla0'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla1'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla2'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla3'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla4'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla5'+tabla).style.backgroundColor = "#FFCC80";
		document.getElementById('tabla6'+tabla).style.backgroundColor = "#FFCC80";
	}else {
		document.getElementById('tabla0'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla1'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla2'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla3'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla4'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla5'+tabla).style.backgroundColor = "transparent";
		document.getElementById('tabla6'+tabla).style.backgroundColor = "transparent";
	}
}


