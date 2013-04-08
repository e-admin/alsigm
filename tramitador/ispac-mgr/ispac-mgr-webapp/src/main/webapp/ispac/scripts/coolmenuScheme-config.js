/*** 
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/
// CONFIGURACIÓN DEL MENU DEL ESQUEMA (no usado)
// MENU 0 //
//Menu object creation
oCMenu0=new makeCM("oCMenu0") //Making the menu object. Argument: menuname

oCMenu0.frames =0

//Menu properties   
oCMenu0.pxBetween=30
oCMenu0.fromLeft=270
oCMenu0.fromTop=95  
oCMenu0.rows=1 // 1, para menus horizontales y 0 para menus verticales
oCMenu0.menuPlacement=0
                                                             
oCMenu0.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/" 
oCMenu0.onlineRoot="" 
oCMenu0.resizeCheck=1 
oCMenu0.wait=200 
oCMenu0.fillImg="img/degrade.gif"
oCMenu0.zIndex=400
oCMenu0.pagecheck=0
oCMenu0.checkscroll=0

//Background bar properties
oCMenu0.useBar=1
oCMenu0.barWidth="menu"
oCMenu0.barHeight=20
oCMenu0.barClass="clBar"
oCMenu0.barX=270
oCMenu0.barY=95
oCMenu0.barBorderX=0
oCMenu0.barBorderY=0
//oCMenu0.barBorderClass="cmMenuBorder"

//Level properties - ALL properties have to be spesified in level 0
oCMenu0.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu0.level[0].width=160
oCMenu0.level[0].height=20 
oCMenu0.level[0].regClass="cmMenu"
oCMenu0.level[0].overClass="cmMenuOver"
oCMenu0.level[0].borderX=1
oCMenu0.level[0].borderY=1
oCMenu0.level[0].borderClass="cmMenuBorder"
oCMenu0.level[0].offsetX=0
oCMenu0.level[0].offsetY=0
oCMenu0.level[0].rows=0
oCMenu0.level[0].align="bottom" // aparición del menu de nivel 1 con respecto al nivel 0
								// opciones:"top" || "bottom" || "left" || "right"
oCMenu0.level[0].filter=0
//"progid:DXImageTransform.Microsoft.Fade(duration=0.5)"
oCMenu0.level[0].arrow="img/down.gif"
oCMenu0.level[0].arrowWidth=11
oCMenu0.level[0].arrowHeight=9

//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu0.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu0.level[1].width=oCMenu0.level[0].width-2
//oCMenu0.level[1].height=20
oCMenu0.level[1].regClass="cmItem"
oCMenu0.level[1].overClass="cmItemOver"
oCMenu0.level[1].borderX=1
oCMenu0.level[1].borderY=1
oCMenu0.level[1].align="right" // aparición del menu de nivel 2 con respecto al nivel 1
oCMenu0.level[1].offsetX=-5
oCMenu0.level[1].offsetY=0
oCMenu0.level[1].borderClass="cmMenuBorder"


