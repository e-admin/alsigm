var menuArray = new Array();
var menuSelect = "";

function addMenu(menuId) {
	var menu = document.getElementById(menuId);
	menuArray[menuArray.length] = menu;
}

function showMenu(menuId) {
	if(menuSelect != menuId)
	{
		var menu = document.getElementById(menuId);
		menu.style.visibility = "visible";
		menuSelect = menuId;
	}
}

function hiddenMenu() {
	if (menuSelect != "")
	{
		var menu = document.getElementById(menuSelect);
		menu.style.visibility = "hidden";
		menuSelect = "";
	}
}