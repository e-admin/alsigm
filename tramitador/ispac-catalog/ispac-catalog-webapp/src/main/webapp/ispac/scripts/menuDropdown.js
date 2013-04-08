/*
 * menuDropdown.js - implements an dropdown menu based on a HTML list
 * Author: Dave Lindquist (http://www.gazingus.org)
 */

var currentMenu = null;

if (!document.getElementById)
    document.getElementById = function() { return null; }

function initializeMenu(menuId, actuatorId, divId) {
    var menu = document.getElementById(menuId);
    var actuator = document.getElementById(actuatorId);
    var iddiv = document.getElementById(divId);

    if (menu == null || actuator == null) return;

    //if (window.opera) return; // I'm too tired

    actuator.onmouseover = function() {
        if (currentMenu) {
            currentMenu.style.visibility = "hidden";
            this.showMenu();
        }
    }
  
    actuator.onclick = function() {
        if (currentMenu == null) {
            this.showMenu();
        }
        else {
            currentMenu.style.visibility = "hidden";
            currentMenu = null;
        }

        return false;
    }

    actuator.showMenu = function() {
        menu.style.left = iddiv.offsetLeft + this.offsetLeft + "px";
        menu.style.top = iddiv.offsetTop + this.offsetTop + this.offsetHeight + 28 + "px";
        menu.style.visibility = "visible";
        currentMenu = menu;
    }
}
