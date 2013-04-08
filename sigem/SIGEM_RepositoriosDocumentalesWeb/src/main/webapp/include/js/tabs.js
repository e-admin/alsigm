/* The tab navigation script. Always use in conjunction with docobj.js! */

// Set up array of tab element style property strings 
var arrayCapas = new Array(4);
arrayCapas[0] = "document.getElementById('capaSistema');"
arrayCapas[1] = "document.getElementById('capaAdminUsuarios');"
arrayCapas[2] = "document.getElementById('capaInvesdoc');"
arrayCapas[3] = "document.getElementById('capaAdminVolumen');"


var tabArray = new Array(6);
tabArray[0] = null;
tabArray[1] = getStyleObj('tab1');
tabArray[2] = getStyleObj('tab2');
tabArray[3] = getStyleObj('tab3');
tabArray[4] = getStyleObj('tab4');
tabArray[5] = getStyleObj('tab5');


// Set up array of tabmiddle td element style property strings 

var tabTextArray = new Array(6);
tabTextArray[0] = null;
tabTextArray[1] = getStyleObj('tabmiddle1');
tabTextArray[2] = getStyleObj('tabmiddle2');
tabTextArray[3] = getStyleObj('tabmiddle3');
tabTextArray[4] = getStyleObj('tabmiddle4');
tabTextArray[5] = getStyleObj('tabmiddle5');


// Set up array of text box element style property strings

var boxArray = new Array(6);
boxArray[0] = null;
boxArray[1] = getStyleObj('box1');
boxArray[2] = getStyleObj('box2');
boxArray[3] = getStyleObj('box3');
boxArray[4] = getStyleObj('box4');
boxArray[5] = getStyleObj('box5');

// the global variables for the tab functions 
var active = null;

var activebgcolor = "#639ACE";
var activetextcolor = "#006699";


var inactivebgcolor = "#cccccc";
var inactivetextcolor = "#006699";

var overbgcolor = "#E6E6E6";
var overtextcolor = "#639ACE"; // FFFF33

//var activebgcolor = "#993399";
//var activetextcolor = "black";


//var inactivebgcolor = "#cccccc";
//var inactivetextcolor = "#999999";

//var overbgcolor = "#cc99cc";
//var overtextcolor = "#ffcc99";


// the tab appearance changing function

function tabcolor(tabnum,color1,color2) {
tab = eval(tabArray[tabnum]);
tabtext = eval(tabTextArray[tabnum]);
tab.backgroundColor = color1;
tabtext.color = color2;

if (document.all) {
 tabtext.cursor = 'hand';
 }
else {
 tabtext.cursor = 'pointer';
 }
}

function mostrarCapas(obj)
	{
		var k = obj.selectedIndex;
			
		if (k == 0)
		{ // Si se selecciona el perfil de sistema, paso por el perfil de administrador d usuarios
			for (i = 0; i < 4; i ++ )
			{
				temp = eval(arrayCapas[i]);
				if (i == 1){
					temp.style.visibility = 'visible';		 
				}
				else{
					temp.style.visibility = 'hidden';		 
				}
			}	
		}
		
		for (i = 0; i < 4; i ++ )
		{
			temp = eval(arrayCapas[i]);
	
			if (k == i){
				//arrayCapas[i].style.visibility = 'visible';
				temp.style.visibility = 'visible';		 
			}
			else{
				//arrayCapas[i].style.visibility = 'hidden';
				temp.style.visibility = 'hidden';		 
			}
		}				
	}

// the central tab navigation function 
// perfiles Contiene el numero de la pestaña dnd se encuentra la capa de perfiles
// si no tiene pestaña de perfiles definir a '9'
function choosebox(num, perfiles ) {
if (perfiles == null )
	perfiles = 1;
if (document.all || document.getElementById) { 
		if (active) {
			activetablayer = eval(tabArray[active]);
			activetabtext = eval(tabTextArray[active]);
			activeboxlayer = eval(boxArray[active]);
			activetablayer.zIndex = 0;
			activeboxlayer.zIndex = 0;
			//
			activeboxlayer.visibility='hidden';	
			tabcolor(active,inactivebgcolor, inactivetextcolor);
			
			 // si la pestaña mostrada no es igual la de dnd se encuentran los perfiles
			 // ocultar las capas de perfiles
			 if (perfiles != 9) {
				if (num != perfiles && perfiles != 9 )
				{
					for (i = 0; i < 4; i ++ ){
						temp = eval(arrayCapas[i]);
						temp.style.visibility = 'hidden';		 
						//arrayCapas[i].style.visibility = 'hidden';		 
					}
					document.getElementById('selectContexto').style.visibility='hidden';
				}
				else{
					mostrarCapas(document.getElementById('selectContexto'));
					document.getElementById('selectContexto').style.visibility='visible';
					}
			}
	    }
	
	tablayer = eval(tabArray[num]);
	tabtext = eval(tabTextArray[num]);
	boxlayer = eval(boxArray[num]);
	tablayer.zIndex = 11;
	boxlayer.zIndex = 10;
	boxlayer.visibility='visible';
	tabcolor(num,activebgcolor, activetextcolor);
	
	active = num;
	
  }
} // fin choosebox


// invoked onmouseover the tab 

function tabover(tabnum) {
if (tabnum != active) {
 tabcolor(tabnum,overbgcolor,overtextcolor);
  }
}


//invoked onmouseout of the tab 

function tabout(tabnum) {

if (tabnum != active) {
tabcolor(tabnum,inactivebgcolor,inactivetextcolor);
 }
}

