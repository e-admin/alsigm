/*****************************************************************************
Default browsercheck - Leave this one
******************************************************************************/
function lib_bwcheck(){ //Browsercheck (needed)
	this.ver=navigator.appVersion; this.agent=navigator.userAgent
	this.dom=document.getElementById?1:0
	this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0;
	this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0;
	this.ie4=(document.all && !this.dom)?1:0;
	this.ie=this.ie4||this.ie5||this.ie6
	this.mac=this.agent.indexOf("Mac")>-1
	this.opera5=this.agent.indexOf("Opera 5")>-1
	this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0;
	this.ns4=(document.layers && !this.dom)?1:0;
	this.bw=(this.ie6 || this.ie5 || this.ie4 || this.ns4 || this.ns6 || this.opera5 || this.dom)
	return this;
}
var bw=new lib_bwcheck() //Making browsercheck object

var mDebugging=2 //General debugging variable. Set to 0 for no debugging, 1 for alerts or 2 for status debugging.


oCMenu = new makeCoolMenu("oCMenu") //Making the menu object. Argument: menuname


oCMenu.useframes = 0 //Do you want to use the menus as coolframemenu or not? (in frames or not)-Value:0 || 1
oCMenu.frame="" //The name of your main frame (where the menus should appear). Leave empty if you're not using frames - Value: "main_frame_name"


oCMenu.useclick=0 //If you want the menu to be activated and deactivated onclick only set this to 1. - Value: 0 || 1


/*If you set this to 1 you will get a "hand" cursor when moving over the links in NS4.
NOTE: This does not apply to the submenus if the menu is used in frames due some mayor problems with NS4*/
oCMenu.useNS4links=1


//After adding the "hover effect" for netscape as well, all styles are lost. But if you want padding add it here.
oCMenu.NS4padding=2


//If you have select boxes close to your menu the menu will check for that and hide them if they are in the way of the menu.
//This feature does unfortunatly not work in NS4!
oCMenu.checkselect=1


/*If you choose to have this code inside a linked js, or if your using frames it's important to set these variables.
This will help you get your links to link to the right place even if your files are in different folders.
The offlineUrl variable is the actual path to the directory where you js file are locally.
This is just so you can test it without uploading. Remember to start it with file:/// and only use slashes, no backward slashes!
Also remember to end with a slash*/
oCMenu.offlineUrl="file:///C|/Inetpub/wwwroot/dhtmlcentral/" //Value: "path_to_menu_file_offline/"
//The onlineUrl variable is the online path to your script. Place in the full path to where your js file is. Remember to end with a slash.
oCMenu.onlineUrl="http://www.dhtmlcentral.com/coolmenus/examples/withoutframes/" //Value: "path_to_menu_file_online/"


oCMenu.pagecheck=1 //Do you want the menu to check whether any of the subitems are out of the bouderies of the page and move them in again (this is not perfect but it hould work) - Value: 0 || 1
oCMenu.checkscroll=0 //Do you want the menu to check whether the page have scrolled or not? For frames you should always set this to 1. You can set this to 2 if you want this feature only on explorer since netscape doesn't support the window.onscroll this will make netscape slower (only if not using frames) - Value: 0 || 1 || 2
oCMenu.resizecheck=0 //Do you want the page to reload if it's resized (This should be on or the menu will crash in Netscape4) - Value: 0 || 1
oCMenu.wait=500 //How long to wait before hiding the menu on mouseout. Netscape 6 is a lot slower then Explorer, so to be sure that it works good enough there you should not have this lower then 500 - Value: milliseconds


//Background bar properties
oCMenu.usebar=0 //If you want to use a background-bar for the top items set this on - Value: 1 || 0
oCMenu.barcolor="#F2F2F9" //The color of the background bar - Value: "color"
oCMenu.barwidth="10" //The width of the background bar. Set this to "menu" if you want it to be the same width as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barheight="80%" //The height of the background bar. Set this to "menu" if you want it to be the same height as the menu. (this will change to match the border if you have one) - Value: px || "%" || "menu"
oCMenu.barx=20 //The left position of the bar. Set this to "menu" if you want it be the same as the left position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.bary=2 //The top position of the bar Set this to "menu" if you want it be the same as the top position of the menu. (this will change to match the border if you have one)  - Value: px || "%" || "menu"
oCMenu.barinheritborder=1 //Set this to 1 if you want the bar to have the same border as the top menus - Value: 0 || 1

//Placement properties
oCMenu.rows=0 //This controls whether the top items is supposed to be laid out in rows or columns. Set to 0 for columns and 1 for row - Value 0 || 1
//oCMenu.fromleft=120 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
//oCMenu.fromtop=125 //This is the top position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
oCMenu.fromleft=2 //This is the left position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
oCMenu.fromtop=5 //This is the top position of the menu. (Only in use if menuplacement below is 0 or aligned) (will change to adapt any borders) - Value: px || "%"
oCMenu.pxbetween=6 //How much space you want between each of the top items. - Value: px || "%"

/*You have several different ways to place the top items.
You can have them right beside eachother (only adding the pxbetween variable)
oCMenu.menuplacement=0

You can have them aligned to one of the sides - This is mostly when not using frames, but can be used in both conditions
Values: (If you get strange results check the fromleft,fromtop and pxbetween variables above)
For menus that are placed in columns (align=left or align=right (se below)) you can align them to the "right" or "center"
For menus that are placed in rows (align=top or align=bottom (se below)) you can align them to the "bottom", "center" or "bottomcenter"
oCMenu.menuplacement="center"

You can also set them directly in pixels: (Remember to have as many array members as you have top items)
oCMenu.menuplacement=new Array(10,200,400,600)

Or you can place in percentage: (remember to use the ' ' around the numbers)


Choose one of those options to get the desired results.
*/
oCMenu.menuplacement=0

/*
Now we are ready for the properties of each level. For those of that have used the old
coolmenus for coolframemenu I will try and explain how this works like this:
level[0] = top items
level[1] = sub items
level[2] = sub2 items
level[3] = sub3 items and so on....
All menus will inherit the properties, and all properties does only HAVE to be spesifed on the top level.
If a level doesn't have on property spesified it will look for it on the last level that was spesified,
if it still doesn't exist it will get the properties from level[0]

Which means that if you set the background color on level[0] to "black" and doesn't spesify any more levels or doesn't
spesify the background color on the last level you spesified ALL menus will get the color from level[0]

Did that make sense at all? This can be a little hard to understand, look at the different examples on my site
and play with and I am sure you'll get what I mean.
*/

//TOP LEVEL PROPERTIES - ALL OF THESE MUST BE SPESIFIED FOR LEVEL[0]
oCMenu.level[0]=new Array() //Add this for each new level
oCMenu.level[0].width=130 //The default width for each level[0] (top) items. You can override this on each item by spesifying the width when making the item. - Value: px || "%"
oCMenu.level[0].height=17 //The default height for each level[0] (top) items. You can override this on each item by spesifying the height when making the item. - Value: px || "%"
oCMenu.level[0].bgcoloroff="white" //The default background color for each level[0] (top) items. You can override this on each item by spesifying the backgroundcolor when making the item. - Value: "color"
oCMenu.level[0].bgcoloron="red" //The default "on" background color for each level[0] (top) items. You can override this on each item by spesifying the "on" background color when making the item. - Value: "color"
oCMenu.level[0].textcolor="#0099ff" //The default text color for each level[0] (top) items. You can override this on each item by spesifying the text color when making the item. - Value: "color"
oCMenu.level[0].hovercolor="#0099ff" //The default "on" text color for each level[0] (top) items. You can override this on each item by spesifying the "on" text color when making the item. - Value: "color"
oCMenu.level[0].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:12px; font-weight:bold;" //The style for all level[0] (top) items. - Value: "style_settings"
oCMenu.level[0].border=1 //The border size for all level[0] (top) items. - Value: px
oCMenu.level[0].bordercolor="#798AB2" //"#999999" //'#003399' //The border color for all level[0] (top) items. - Value: "color"
oCMenu.level[0].offsetX=-8 //The X offset of the submenus of this item. This does not affect the first submenus, but you need it here so it can be the default value for all levels. - Value: px
oCMenu.level[0].offsetY=-1 //The Y offset of the submenus of this item. This does not affect the first submenus, but you need it here so it can be the default value for all levels. - Value: px
oCMenu.level[0].NS4font="tahoma,arial,helvetica"
oCMenu.level[0].NS4fontSize="2"
oCMenu.level[0].rows=0


/*New: Added animation features that can be controlled on each level.*/
oCMenu.level[0].clip=0 //Set this to 1 if you want the submenus of this level to "slide" open in a animated clip effect. - Value: 0 || 1
oCMenu.level[0].clippx=0 //If you have clip spesified you can set how many pixels it will clip each timer in here to control the speed of the animation. - Value: px
oCMenu.level[0].cliptim=0 //This is the speed of the timer for the clip effect. Play with this and the clippx to get the desired speed for the clip effect (be carefull though and try and keep this value as high or possible or you can get problems with NS4). - Value: milliseconds
//Filters - This can be used to get some very nice effect like fade, slide, stars and so on. EXPLORER5.5+ ONLY - If you set this to a value it will override the clip on the supported browsers
oCMenu.level[0].filter=0 //VALUE: 0 || "filter specs"
//oCMenu.level[0].filter="progid:DXImageTransform.Microsoft.Fade(duration=1)"
/* NOTA Osmar: .el anterior filtro funciona bien en IE6
               .no funciona en Opera 7.0 (los submenús no se despliegan)
			   .No tiene efecto en Mozilla 1.6 (no hay efecto de fade) pero se despliegan los submenus
*/


/*And last but not least the align variable.
This spesifies how the submenus of this level comes out.
Values:
"bottom": The sub menus of this level will come out on the top of this item
"top": The sub menus of this level will come out on the bottom of this item
"left": The sub menus of this level will come out on the right of this item
"right": The sub menus of this level will come out on the left of this item

In generally "left" and "right" works best for menus in columns and "top" and "bottom" works best for menus in rows.
But by all means feel free to play with it.

If you have set pagecheck to 1 above this is what the pagecheck will change when reaching the bounderies of the page.
If it reaches the right boundery and it's aligned left it will change the align to right and so on.
*/
oCMenu.level[0].align="right" //Value: "top" || "bottom" || "left" || "right"


//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to spesify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[1]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[1].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[1].align="bottom"
oCMenu.level[1].offsetX=-5
oCMenu.level[1].offsetY=-1
oCMenu.level[1].width=130


//EXAMPLE SUB LEVEL[2] PROPERTIES - You have to spesify the properties you want different from LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[2]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[2].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[2].align="bottom"
oCMenu.level[2].offsetX=-20
oCMenu.level[2].offsetY=-1
oCMenu.level[2].width=130


//EXAMPLE SUB LEVEL[3] PROPERTIES - You have to spesify the properties you want different from LEVEL[2] OR LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[3]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[3].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[3].align="bottom"
oCMenu.level[3].offsetX=-1
oCMenu.level[3].offsetY=-1
oCMenu.level[3].width=115


//EXAMPLE SUB LEVEL[4] PROPERTIES - You have to spesify the properties you want different from LEVEL[3] OR LEVEL[2] OR LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[4]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[4].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[4].align="bottom"
oCMenu.level[4].offsetX=0
oCMenu.level[4].offsetY=0
oCMenu.level[4].width=140

//EXAMPLE SUB LEVEL[5] PROPERTIES - You have to spesify the properties you want different from LEVEL[3] OR LEVEL[2] OR LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[5]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[5].style="padding-top:1px; padding-left:5px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[5].align="bottom"
oCMenu.level[5].offsetX=-10
oCMenu.level[5].offsetY=2
oCMenu.level[5].width=100

//EXAMPLE SUB LEVEL[5] PROPERTIES - You have to spesify the properties you want different from LEVEL[3] OR LEVEL[2] OR LEVEL[1] OR LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[6]=new Array() //Add this for each new level (adding one to the number)
oCMenu.level[6].style="padding-top:1px; padding:1px; font-family:Verdana,Arial,Helvetica,sans-serif; font-size:10px; font-weight:normal;"
oCMenu.level[6].align="bottom"
oCMenu.level[6].offsetX=-10
oCMenu.level[6].offsetY=2
oCMenu.level[6].width=100


// Struts Menu specific javascript variables 

// menu arrows
cmTopMenuImage='&nbsp;&nbsp;<img src="../images/menu/arrow_r.gif">'
cmSubMenuImage='&nbsp;&nbsp;<img src="../images/menu/arrow_r.gif">'

// normal menu colors
// this is required!

//grises
cmBGColorOff='#DADAE4'
//cmBGColorOn='#B2B2C4'
cmBGColorOn='#FFFFFF'
cmTxtColor='#003b73'
cmHoverColor='#003b73'

cmDisBGColorOn='red'
cmDisBGColorOff='red'
cmDisTxtColor='#000000'
cmDisHoverColor='#000000'

