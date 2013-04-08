/*** 
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/
// CONFIGURACIÓN DEL MENU DEL ESQUEMA (dataLayout)
// MENU 0 // (para el toolbar)
//Menu object creation
oCMenu0=new makeCM("oCMenu0") //Making the menu object. Argument: menuname

oCMenu0.frames =0

//Menu properties   
oCMenu0.pxBetween=30
oCMenu0.fromLeft=280
oCMenu0.fromTop=95  
oCMenu0.rows=1 // 1, para menus horizontales y 0 para menus verticales
oCMenu0.menuPlacement=0
                                                             
oCMenu0.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/" 
oCMenu0.onlineRoot="" 
oCMenu0.resizeCheck=1 
oCMenu0.wait=200 
oCMenu0.fillImg=""
oCMenu0.zIndex=400
oCMenu0.pagecheck=0
oCMenu0.checkscroll=0

//Background bar properties
oCMenu0.useBar=1
oCMenu0.barWidth="menu"
oCMenu0.barHeight=20
oCMenu0.barClass="clBar"
oCMenu0.barX=280
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

// MENU 1 //
//Menu object creation
oCMenu1=new makeCM("oCMenu1") //Making the menu object. Argument: menuname

oCMenu1.frames = 0

//Menu properties   
oCMenu1.pxBetween=30
oCMenu1.fromLeft=700 
oCMenu1.fromTop=140  
oCMenu1.rows=1 
oCMenu1.menuPlacement=0
                                                             
oCMenu1.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/" 
oCMenu1.onlineRoot="" 
oCMenu1.resizeCheck=1 
oCMenu1.wait=200 
oCMenu1.fillImg=""
oCMenu1.zIndex=400
oCMenu1.pagecheck=0
oCMenu1.checkscroll=0

//Background bar properties
oCMenu1.useBar=1
oCMenu1.barWidth="menu"
oCMenu1.barHeight=20 
oCMenu1.barClass="clBar"
oCMenu1.barX=700
oCMenu1.barY=140
oCMenu1.barBorderX=0
oCMenu1.barBorderY=0
//oCMenu1.barBorderClass="cmMenuBorder"

//Level properties - ALL properties have to be spesified in level 0
oCMenu1.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu1.level[0].width=160
oCMenu1.level[0].height=20 
oCMenu1.level[0].regClass="cmMenu"
oCMenu1.level[0].overClass="cmMenuOver"
oCMenu1.level[0].borderX=1
oCMenu1.level[0].borderY=1
oCMenu1.level[0].borderClass="cmMenuBorder"
oCMenu1.level[0].offsetX=0
oCMenu1.level[0].offsetY=0
oCMenu1.level[0].rows=0
oCMenu1.level[0].align="bottom"
oCMenu1.level[0].filter=0
//"progid:DXImageTransform.Microsoft.Fade(duration=0.5)"
oCMenu1.level[0].arrow="img/down.gif"
oCMenu1.level[0].arrowWidth=11
oCMenu1.level[0].arrowHeight=9

//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu1.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu1.level[1].width=oCMenu1.level[0].width-2
//oCMenu1.level[1].height=20
oCMenu1.level[1].regClass="cmItem"
oCMenu1.level[1].overClass="cmItemOver"
oCMenu1.level[1].borderX=1
oCMenu1.level[1].borderY=1
oCMenu1.level[1].align="right" 
oCMenu1.level[1].offsetX=-5
oCMenu1.level[1].offsetY=0
oCMenu1.level[1].borderClass="cmMenuBorder"
