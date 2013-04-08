
function MM_reloadPage(init) {  
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)>=4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function DynLayer(id,nestref,frame) {
	if (!is.ns5 && !DynLayer.set && !frame) DynLayerInit()
	this.frame = frame || self
	if (is.ns) {
		if (is.ns4) {
			if (!frame) {
				if (!nestref) var nestref = DynLayer.nestRefArray[id]
				if (!DynLayerTest(id,nestref)) return
				this.css = (nestref)? eval("document."+nestref+".document."+id) : document.layers[id]
			}
			else this.css = (nestref)? eval("frame.document."+nestref+".document."+id) : frame.document.layers[id]
			this.elm = this.event = this.css
			this.doc = this.css.document
		}
		else if (is.ns5) {
			this.elm = document.getElementById(id)
			this.css = this.elm.style
			this.doc = document
		}
		this.x = this.css.left
		this.y = this.css.top
		this.w = this.css.clip.width
		this.h = this.css.clip.height
	}
	else if (is.ie) {
		this.elm = this.event = this.frame.document.all[id]
		this.css = this.frame.document.all[id].style
		this.doc = document
		this.x = this.elm.offsetLeft
		this.y = this.elm.offsetTop
		this.w = (is.ie4)? this.css.pixelWidth : this.elm.offsetWidth
		this.h = (is.ie4)? this.css.pixelHeight : this.elm.offsetHeight
	}
	this.id = id
	this.nestref = nestref
	this.obj = id + "DynLayer"
	eval(this.obj + "=this")
}
function DynLayerMoveTo(x,y) {
	if (x!=null) {
		this.x = x
		if (is.ns) this.css.left = this.x
		else this.css.pixelLeft = this.x
	}
	if (y!=null) {
		this.y = y
		if (is.ns) this.css.top = this.y
		else this.css.pixelTop = this.y
	}
}
function DynLayerMoveBy(x,y) {
	this.moveTo(this.x+x,this.y+y)
}
function DynLayerShow() {
	this.css.visibility = (is.ns4)? "show" : "visible"
}
function DynLayerHide() {
	this.css.visibility = (is.ns4)? "hide" : "hidden"
}
DynLayer.prototype.moveTo = DynLayerMoveTo
DynLayer.prototype.moveBy = DynLayerMoveBy
DynLayer.prototype.show = DynLayerShow
DynLayer.prototype.hide = DynLayerHide
DynLayerTest = new Function('return true')

// DynLayerInit Function
function DynLayerInit(nestref) {
	if (!DynLayer.set) DynLayer.set = true
	if (is.ns) {
		if (nestref) ref = eval('document.'+nestref+'.document')
		else {nestref = ''; ref = document;}
		for (var i=0; i<ref.layers.length; i++) {
			var divname = ref.layers[i].name
			DynLayer.nestRefArray[divname] = nestref
			var index = divname.indexOf("Div")
			if (index > 0) {
				eval(divname.substr(0,index)+' = new DynLayer("'+divname+'","'+nestref+'")')
			}
			if (ref.layers[i].document.layers.length > 0) {
				DynLayer.refArray[DynLayer.refArray.length] = (nestref=='')? ref.layers[i].name : nestref+'.document.'+ref.layers[i].name
			}
		}
		if (DynLayer.refArray.i < DynLayer.refArray.length) {
			DynLayerInit(DynLayer.refArray[DynLayer.refArray.i++])
		}
	}
	else if (is.ie) {
		for (var i=0; i<document.all.tags("DIV").length; i++) {
			var divname = document.all.tags("DIV")[i].id
			var index = divname.indexOf("Div")
			if (index > 0) {
				eval(divname.substr(0,index)+' = new DynLayer("'+divname+'")')
			}
		}
	}
	return true
}
DynLayer.nestRefArray = new Array()
DynLayer.refArray = new Array()
DynLayer.refArray.i = 0
DynLayer.set = false

// Slide Methods
function DynLayerSlideTo(endx,endy,inc,speed,fn) {
	if (endx==null) endx = this.x
	if (endy==null) endy = this.y
	var distx = endx-this.x
	var disty = endy-this.y
	this.slideStart(endx,endy,distx,disty,inc,speed,fn)
}
function DynLayerSlideBy(distx,disty,inc,speed,fn) {
	var endx = this.x + distx
	var endy = this.y + disty
	this.slideStart(endx,endy,distx,disty,inc,speed,fn)
}
function DynLayerSlideStart(endx,endy,distx,disty,inc,speed,fn) {
	if (this.slideActive) return
	if (!inc) inc = 10
	if (!speed) speed = 20
	var num = Math.sqrt(Math.pow(distx,2) + Math.pow(disty,2))/inc
	if (num==0) return
	var dx = distx/num
	var dy = disty/num
	if (!fn) fn = null
	this.slideActive = true
	this.slide(dx,dy,endx,endy,num,1,speed,fn)
}
function DynLayerSlide(dx,dy,endx,endy,num,i,speed,fn) {
	if (!this.slideActive) return
	if (i++ < num) {
		this.moveBy(dx,dy)
		this.onSlide()
		if (this.slideActive) setTimeout(this.obj+".slide("+dx+","+dy+","+endx+","+endy+","+num+","+i+","+speed+",\""+fn+"\")",speed)
		else this.onSlideEnd()
	}
	else {
		this.slideActive = false
		this.moveTo(endx,endy)
		this.onSlide()
		this.onSlideEnd()
		eval(fn)
	}
}
function DynLayerSlideInit() {}
DynLayer.prototype.slideInit = DynLayerSlideInit
DynLayer.prototype.slideTo = DynLayerSlideTo
DynLayer.prototype.slideBy = DynLayerSlideBy
DynLayer.prototype.slideStart = DynLayerSlideStart
DynLayer.prototype.slide = DynLayerSlide
DynLayer.prototype.onSlide = new Function()
DynLayer.prototype.onSlideEnd = new Function()

// Clip Methods
function DynLayerClipInit(clipTop,clipRight,clipBottom,clipLeft) {
	if (is.ie) {
		if (arguments.length==4) this.clipTo(clipTop,clipRight,clipBottom,clipLeft)
		else if (is.ie4) this.clipTo(0,this.css.pixelWidth,this.css.pixelHeight,0)
	}
}
function DynLayerClipTo(t,r,b,l) {
	if (t==null) t = this.clipValues('t')
	if (r==null) r = this.clipValues('r')
	if (b==null) b = this.clipValues('b')
	if (l==null) l = this.clipValues('l')
	if (is.ns) {
		this.css.clip.top = t
		this.css.clip.right = r
		this.css.clip.bottom = b
		this.css.clip.left = l
	}
	else if (is.ie) this.css.clip = "rect("+t+"px "+r+"px "+b+"px "+l+"px)"
}
function DynLayerClipBy(t,r,b,l) {
	this.clipTo(this.clipValues('t')+t,this.clipValues('r')+r,this.clipValues('b')+b,this.clipValues('l')+l)
}
function DynLayerClipValues(which) {
	if (is.ie) var clipv = this.css.clip.split("rect(")[1].split(")")[0].split("px")
	if (which=="t") return (is.ns)? this.css.clip.top : Number(clipv[0])
	if (which=="r") return (is.ns)? this.css.clip.right : Number(clipv[1])
	if (which=="b") return (is.ns)? this.css.clip.bottom : Number(clipv[2])
	if (which=="l") return (is.ns)? this.css.clip.left : Number(clipv[3])
}
DynLayer.prototype.clipInit = DynLayerClipInit
DynLayer.prototype.clipTo = DynLayerClipTo
DynLayer.prototype.clipBy = DynLayerClipBy
DynLayer.prototype.clipValues = DynLayerClipValues

// Write Method
function DynLayerWrite(html) {
	if (is.ns) {
		this.doc.open()
		this.doc.write(html)
		this.doc.close()
	}
	else if (is.ie) {
		this.event.innerHTML = html
	}
}
DynLayer.prototype.write = DynLayerWrite

// BrowserCheck Object
function BrowserCheck() {
	var b = navigator.appName
	if (b=="Netscape") this.b = "ns"
	else if (b=="Microsoft Internet Explorer") this.b = "ie"
	else this.b = b
	this.version = navigator.appVersion
	this.v = parseInt(this.version)
	this.ns = (this.b=="ns" && this.v>=4)
	this.ns4 = (this.b=="ns" && this.v==4)
	this.ns5 = (this.b=="ns" && this.v==5)
	this.ie = (this.b=="ie" && this.v>=4)
	this.ie4 = (this.version.indexOf('MSIE 4')>0)
	this.ie5 = (this.version.indexOf('MSIE 5')>0)
	this.min = (this.ns||this.ie)
}
is = new BrowserCheck()

// CSS Function
function css(id,left,top,width,height,color,vis,z,other) {
	color = "ffffff";
	if (id=="START") return '<STYLE TYPE="text/css">\n'
	else if (id=="END") return '</STYLE>'
	var str = (left!=null && top!=null)? '#'+id+' {position:absolute; left:'+left+'px; top:'+top+'px;' : '#'+id+' {position:relative;'
	if (arguments.length>=4 && width!=null) str += ' width:'+width+'px;'
	if (arguments.length>=5 && height!=null) {
		str += ' height:'+height+'px;'
		if (arguments.length<9 || other.indexOf('clip')==-1) str += ' clip:rect(0px '+width+'px '+height+'px 0px);'
	}
	if (arguments.length>=6 && color!=null) str += (is.ns)? ' layer-background-color:'+color+';' : ' background-color:'+color+';'
	if (arguments.length>=7 && vis!=null) str += ' visibility:'+vis+';'
	if (arguments.length>=8 && z!=null) str += ' z-index:'+z+';'
	if (arguments.length==9 && other!=null) str += ' '+other
	str += '}\n'
	return str
	
}
function writeCSS(str,showAlert) {
    	str = css('START')+str+css('END')
	document.write(str)
	if (showAlert) alert(str)
}

function DynLayerGlideTo(startSpeed,endSpeed,endx,endy,angleinc,speed,fn) {
	if (endx==null) endx = this.x
	if (endy==null) endy = this.y
	var distx = endx-this.x
	var disty = endy-this.y
	this.glideStart(startSpeed,endSpeed,endx,endy,distx,disty,angleinc,speed,fn)
}
function DynLayerGlideBy(startSpeed,endSpeed,distx,disty,angleinc,speed,fn) {
	var endx = this.x + distx
	var endy = this.y + disty
	this.glideStart(startSpeed,endSpeed,endx,endy,distx,disty,angleinc,speed,fn)
}
function DynLayerGlideStart(startSpeed,endSpeed,endx,endy,distx,disty,angleinc,speed,fn) {
	if (this.glideActive) return
	if (endx==this.x) var slantangle = 90
	else if (endy==this.y) var slantangle = 0
	else var slantangle = Math.abs(Math.atan(disty/distx)*180/Math.PI)
	if (endx>=this.x) {
		if (endy>this.y) slantangle = 360-slantangle
	}
	else {
		if (endy>this.y) slantangle = 180+slantangle
		else slantangle = 180-slantangle
	}
	slantangle *= Math.PI/180
	var amplitude = Math.sqrt(Math.pow(distx,2) + Math.pow(disty,2))
	if (!fn) fn = null
	this.glideActive = true
	if (startSpeed == "fast") {
		if (endSpeed=="fast") this.glide(1,amplitude/2,0,90,this.x,this.y,slantangle,endx,endy,distx,disty,angleinc,speed,fn)
		else this.glide(0,amplitude,0,90,this.x,this.y,slantangle,endx,endy,distx,disty,angleinc,speed,fn)
	}
	else {
		if (endSpeed=="fast") this.glide(0,amplitude,-90,0,this.x+distx,this.y+disty,slantangle,endx,endy,distx,disty,angleinc,speed,fn)
		else this.glide(0,amplitude/2,-90,90,this.x+distx/2,this.y+disty/2,slantangle,endx,endy,distx,disty,angleinc,speed,fn)
	}
}
function DynLayerGlide(type,amplitude,angle,endangle,centerX,centerY,slantangle,endx,endy,distx,disty,angleinc,speed,fn) {
	if (angle < endangle && this.glideActive) {
		angle += angleinc
		var u = amplitude*Math.sin(angle*Math.PI/180)
		var x = centerX + u*Math.cos(slantangle)
		var y = centerY - u*Math.sin(slantangle)
		this.moveTo(x,y)
		this.onGlide()
		if (this.glideActive) setTimeout(this.obj+'.glide('+type+','+amplitude+','+angle+','+endangle+','+centerX+','+centerY+','+slantangle+','+endx+','+endy+','+distx+','+disty+','+angleinc+','+speed+',\''+fn+'\')',speed)
		else this.onGlideEnd()
	}
	else {
		if (type==1) this.glide(0,amplitude,-90,0,this.x+distx/2,this.y+disty/2,slantangle,endx,endy,distx,disty,angleinc,speed,fn)
		else {
			this.glideActive = false
			this.moveTo(endx,endy)
			this.onGlide()
			this.onGlideEnd()
			eval(fn)
		}
	}
}
DynLayerGlideInit = new Function()
DynLayer.prototype.glideInit = new Function()
DynLayer.prototype.glideTo = DynLayerGlideTo
DynLayer.prototype.glideBy = DynLayerGlideBy
DynLayer.prototype.glideStart = DynLayerGlideStart
DynLayer.prototype.glide = DynLayerGlide
DynLayer.prototype.onGlide = new Function()
DynLayer.prototype.onGlideEnd = new Function()


function DynLayerLoad(url,fn) {
	this.loadFinish = DynLayerLoadFinish
	if (is.ns) this.css.load(url,this.w)
	else if (is.ie) parent.bufferFrame.document.location = url
	this.evalfn = fn
}
function DynLayerLoadFinish() {
	if (is.ie) this.event.innerHTML = parent.bufferFrame.document.body.innerHTML
	eval(this.evalfn)
}
DynLayer.prototype.load = DynLayerLoad

function DynLayerSetbg(color) {
	if (is.ns) this.doc.bgColor = color
	else this.css.backgroundColor = color
}
DynLayer.prototype.setbg = DynLayerSetbg

function DynLayerImg(imgName,imgObj) {
	this.doc.images[imgName].src = eval(imgObj+'.src')
}
DynLayer.prototype.img = DynLayerImg

function DynLayerGetRelativeX() {
	return (is.ns)? this.css.pageX : this.elm.offsetLeft
}
function DynLayerGetRelativeY() {
	return (is.ns)? this.css.pageY : this.elm.offsetTop
}
DynLayer.prototype.getRelativeX = DynLayerGetRelativeX
DynLayer.prototype.getRelativeY = DynLayerGetRelativeY

function DynLayerGetContentWidth() {
	return (is.ns)? this.doc.width : this.elm.scrollWidth
}
function DynLayerGetContentHeight() {
	return (is.ns)? this.doc.height : this.elm.scrollHeight
}
DynLayer.prototype.getContentWidth = DynLayerGetContentWidth
DynLayer.prototype.getContentHeight = DynLayerGetContentHeight


function CollapseMenu(x,y,width,numBlocks,name) {
 	this.name = (name!=null)? name : "CollapseMenu"+(CollapseMenu.count++)
	this.x = x
	this.y = y
	this.w = width
	this.numBlocks = numBlocks
	this.bgColor = '#004080'
 	this.openStyle = "slide"  // or 'glide' or 'move'
	this.contentIndent = 0
	this.inc = 20
	this.speed = 20
	this.active = false
	this.obj = this.name + "Object"
	eval(this.obj + "=this")
	this.build = CollapseMenuBuild
	this.activate = CollapseMenuActivate
	this.toggle = CollapseMenuToggle
	this.open = CollapseMenuOpen
	this.close = CollapseMenuClose
	this.finish = CollapseMenuFinish
	this.onToggle = new Function()
}
function CollapseMenuBuild() {
	this.css = css(this.name,this.x,this.y,this.w,0)
	for (var i=0;i<this.numBlocks;i++) {
		this.css += css(this.name+'Block'+i,0,0,this.w)
		this.css += css(this.name+'Block'+i+'Item',0,0,this.w,null,this.bgColor)
		this.css += css(this.name+'Block'+i+'Content',0,0,this.w,null,this.bgColor,null,null,'margin-left:'+this.contentIndent)
	}
	this.css += css(this.name+'Block'+this.numBlocks,0,0,this.w,0,this.bgColor)
	this.divStart = '<div id="'+this.name+'">'
	this.divEnd = ''
	this.divEnd += '<div id="'+this.name+'Block'+this.numBlocks+'"></div>'
	for (var i=0;i<this.numBlocks;i++) {
		this.divEnd += '</div>'
	}
	this.divEnd += '</div>'
}
function CollapseMenuActivate() {
	this.lyr = new DynLayer(this.name)
	this.blocks = new Array()
	this.itemTotal = 0
	this.contentTotal = 0
	for (var i=0;i<this.numBlocks;i++) {
          	this.blocks[i] = new Object()
		this.blocks[i].open = false
		this.blocks[i].lyr = new DynLayer(this.name+'Block'+i)
		this.blocks[i].itemlyr = new DynLayer(this.name+'Block'+i+'Item')
		this.blocks[i].itemHeight = this.blocks[i].itemlyr.getContentHeight()
		this.itemTotal += this.blocks[i].itemHeight
            this.blocks[i].itemlyr.clipTo(0,this.w,this.blocks[i].itemHeight,0)
		
		this.blocks[i].contentlyr = new DynLayer(this.name+'Block'+i+'Content')
		this.blocks[i].contentHeight = this.blocks[i].contentlyr.getContentHeight()
		this.contentTotal += this.blocks[i].contentHeight
	      this.blocks[i].contentlyr.clipTo(0,this.w,this.blocks[i].contentHeight,0)
		this.blocks[i].contentlyr.moveTo(null,this.blocks[i].itemHeight)
		if (i!=0) this.blocks[i].lyr.moveTo(null,this.blocks[i-1].itemHeight)
		this.blocks[i].h = this.blocks[i].itemHeight + this.blocks[i].contentHeight

	}
	this.h = this.contentTotal + this.itemTotal
	
	for (var i=this.numBlocks-1;i>=0;i--) {
		this.blocks[i].lyr.clipInit()
		this.blocks[i].lyr.clipTo(0,this.w,this.h-this.blocks[i].lyr.y,0)
	}
	this.blocks[this.numBlocks] = new Object()
	this.blocks[this.numBlocks].lyr = new DynLayer(this.name+'Block'+this.numBlocks)
	this.blocks[this.numBlocks].lyr.clipTo(0,this.w,this.h-this.itemTotal,0)
	this.blocks[this.numBlocks].lyr.css.height = this.h-this.itemTotal
	this.blocks[this.numBlocks].lyr.moveTo(null,this.blocks[this.numBlocks-1].itemHeight)
	this.lyr.clipTo(0,this.w,this.h,0);
	var ImgObjName;
	var estado = document.f1.estado.value;
	
	var est = new Array("F","C","O","G");//"C","I","N","S","P","E","R","A");
	for( i = 0 ; i < est.length; i++ )
	{
		if( estado == est[i])	{ 
			collapse.toggle(i);
			cambiarImg( "Imgmas"+est[i],estado);
		}
	}
}

function CollapseMenuToggle(i) {
   for (j=0;j<this.numBlocks;j++){
      if (j==i){
        if (this.active) return
        this.active = true
     	  if (!this.blocks[i].open) this.open(i)
	  else this.close(i)
      }else{
         this.close(j)
      }
    }
}

function CollapseMenuOpen(i) {
	if (!this.blocks[i].open) {
		var h = this.blocks[i].contentHeight + this.blocks[i].itemHeight
		this.blocks[i].open = true
		if (this.openStyle == 'slide') this.blocks[i+1].lyr.slideTo(null,h,this.inc,this.speed,this.obj+'.finish()')
		else if (this.openStyle == 'glide') this.blocks[i+1].lyr.glideTo('slow','slow',null,h,this.inc,this.speed,this.obj+'.finish()')
		else if (this.openStyle == 'move') {this.blocks[i+1].lyr.moveTo(null,h); this.finish();}
	}
}

function CollapseMenuClose(i) {
	if (this.blocks[i].open) {
		var h = this.blocks[i].itemHeight
		this.blocks[i].open = false
		if (this.openStyle == 'slide') this.blocks[i+1].lyr.slideTo(null,h,this.inc,this.speed,this.obj+'.finish()')
		else if (this.openStyle == 'glide') this.blocks[i+1].lyr.glideTo('slow','slow',null,h,this.inc,this.speed,this.obj+'.finish()')
		else if (this.openStyle == 'move') {this.blocks[i+1].lyr.moveTo(null,h); this.finish();}
	}
}

function CollapseMenuFinish() {
	this.active = false
	this.onToggle()
}

CollapseMenu.count = 0

