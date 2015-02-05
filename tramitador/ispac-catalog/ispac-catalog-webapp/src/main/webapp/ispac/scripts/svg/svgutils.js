
var SVGNS = "http://www.w3.org/2000/svg";
var DRAGNS = "http://www.ieci.es/tecdoc/ispac";
var DRAGSTART = "dragstart";
var DRAGMOVE = "dragmove";
var DRAGDROP = "dragdrop";

var SVGUtilsClass = function(){
	this.UPDATEFRAME_COUNTER = 0;
	this.NODE_WIDTH = 40;
	this.NODE_HEIGHT = 40;
	this.GROUP_FLOWS = "flows";
};

SVGUtilsClass.prototype.isIE =  function()
{
    var agent = navigator.appName.toLowerCase();
    return ((agent.indexOf("adobe") != -1) && (agent.indexOf("opera") == -1));
}

SVGUtilsClass.prototype.update = function(obj) {
    var grpflows=document.getElementById(this.GROUP_FLOWS);
    var allChildren = grpflows.childNodes;

    for(var child = 0; child < allChildren.length; ++child) {
        // Adobe doesn't allow array access
        var ele = allChildren.item(child);
	//if (ele.getAttributeNS != null)
	if (typeof(ele.getAttributeNS) != "undefined")
	{
		if (ele.getAttributeNS(DRAGNS, "orig") == obj.id ||
		ele.getAttributeNS(DRAGNS, "end") == obj.id )
		{
			this.connect(ele);
		}
	}
    }
};
SVGUtilsClass.prototype.moveUpdate = function(evt) {

    if (++this.UPDATEFRAME_COUNTER<3)
	return;

    this.UPDATEFRAME_COUNTER=0;
    SVGUtils.update(evt.dragEnt);
};

SVGUtilsClass.prototype.dropUpdate = function(evt) {
    SVGUtils.update(evt.dragEnt);
};


SVGUtilsClass.prototype.defaultTranslate = function(obj) {
	var x=obj.getAttributeNS(DRAGNS, "x");
        var y=obj.getAttributeNS(DRAGNS, "y");
	obj.setAttributeNS(null, "transform", "translate(" + x + "," + y + ")");
};


SVGUtilsClass.prototype.translate = function(obj,x,y) {
	obj.setAttributeNS(DRAGNS, "x", x);
	obj.setAttributeNS(DRAGNS, "y", y);
	obj.setAttributeNS(null, "transform", "translate(" + x + "," + y + ")");
};


SVGUtilsClass.prototype.connect = function(obj) {

	var origname = obj.getAttributeNS(DRAGNS, "orig");
	var destname = obj.getAttributeNS(DRAGNS, "end");

	var origele = document.getElementById(origname);
	var destele = document.getElementById(destname);


	var origx=parseInt(origele.getAttributeNS(DRAGNS, "x"));
	var origy=parseInt(origele.getAttributeNS(DRAGNS, "y"));
	var width=parseInt(origele.getAttributeNS(DRAGNS, "width"));
	var height=parseInt(origele.getAttributeNS(DRAGNS, "height"));

	origx=origx+width/2;
	origy=origy+height/2;

	if (origname==destname)
	{
		//El flujo es un ciclo. Se pinta de forma distinta
		var path="M"+(origx+width/2)+","+(origy)+"A"+(width/2+6)+","+(height/2+6)+" 0 0,0 "+(origx+width)+","+(origy-width)+" A"+(width/2+3)+","+(height/2+3)+" 0 0,0 "+origx+","+(origy-height/2);
		obj.setAttributeNS(null, "d", path);
		return;
	}


	var destx=parseInt(destele.getAttributeNS(DRAGNS, "x"));
	var desty=parseInt(destele.getAttributeNS(DRAGNS, "y"));

	width=parseInt(destele.getAttributeNS(DRAGNS, "width"));
	height=parseInt(destele.getAttributeNS(DRAGNS, "height"));


	var rectbound= new SVGRectBoundClass(destx,desty,width,height);
	rectbound.calcAnchorPoint(origx,origy);
	var path="M"+origx+","+origy+" L"+rectbound.sx+","+rectbound.sy;
/*
	midx=origx+(rectbound.sx-origx)/2;
	midy=origy+(rectbound.sy-origy)/2;

	ctrpx=origx+(midx-origx)/2+10;
	ctrpy=origy+(midy-origy)/2+10;

	var path="M"+origx+","+origy+" Q"+ctrpx+","+ctrpy+" "+midx+","+midy+" T"+rectbound.sx+","+rectbound.sy;
*/
	//var sid = obj.ownerSVGElement.suspendRedraw(1000);
	obj.setAttributeNS(null, "d", path);
        //obj.ownerSVGElement.unsuspendRedraw(sid);
};

SVGUtilsClass.prototype.addNode = function(itemid,x,y) {

	var item = document.getElementById(itemid);
	if (item==null)
		alert("Warning: item ["+itemid+"] is null");
	SVGUtils.translate(item,x,y);

	//item.addEventListener("click", mouseclick_listener, false);
};

SVGUtilsClass.prototype.addFlow = function(itemid) {

	var item = document.getElementById(itemid);
	if (item==null)
		alert("Warning: flow ["+itemid+"] is null");
	SVGUtils.connect(item);
	//item.addEventListener("click", mouseclick_listener, false);
};

SVGUtilsClass.prototype.init = function(mainid,bkgid,consoleid) {

	var allChildren = document.documentElement.getElementsByTagNameNS(SVGNS, "*");
	for(var child = 0; child < allChildren.length; ++child) {
		var item = allChildren.item(child);
		var itemtype=item.getAttributeNS(DRAGNS, "type");
		switch (itemtype){
		case "node":
			SVGUtils.defaultTranslate(item);
			break;
		case "flow":
			SVGUtils.connect(item);
			break;
		};
	}

	//Decidir entre mainid(Firefox) y bkgid(IE);
	var bkg=mainid;
	if (SVGUtils.isIE())
	{
		bkg=bkgid;
		var item = document.getElementById(bkgid);
		item.setAttributeNS(null,"class","bkgenabled");
	}

	SVGUtils.dragInstall(bkg,consoleid);
};



SVGUtilsClass.prototype.dragInstall = function(bkgid,consoleid) {
	initializeDraggableElements(bkgid,consoleid);
	addDragEventListener(DRAGMOVE,SVGUtils.moveUpdate);
	addDragEventListener(DRAGDROP,SVGUtils.dropUpdate);
};

var SVGUtils= new SVGUtilsClass();


var SVGRectBoundClass = function(x,y,width,height){
	this.tx = x;
	this.ty = y;
	this.w = width;
	this.h = height;

	this.sx= 0;
	this.sy= 0;
};

SVGRectBoundClass.prototype.calcAnchorPoint= function(x,y) {
	var px=(this.tx+this.w/2);
	var py=(this.ty+this.h/2);

	this.sx=x-px;
	this.sy=y-py;

	if (Math.abs(this.sy)>Math.abs(this.sx))
	{
		//TOP/BOTTOM

		this.sx=px;
		if (this.sy>0)
		{

			this.sy=this.ty+this.h;
		}
		else
		{
			this.sy=this.ty;
		}
	}
	else   	//LEFT/RIGHT
	{
		this.sy=py;
		if (this.sx>0)
		{
			this.sx=this.tx+this.w;

		}
		else
		{
			this.sx=this.tx;
		}
	}
};