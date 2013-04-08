var w ;
var win;

function working(path,width,height){
   w = new modalWindow(width,height)
   w.onclose = function( returnValue ){
}
   var dialogArguments = "I was Here";
   w.open( ""+path, dialogArguments );
}

function workingInternal(path,action,width,height){
   w = new modalWindow(width,height)
   w.onclose = function( returnValue ){}
   var dialogArguments = "I was Here";
   w.open( ""+path+action, dialogArguments );

}


function closewindow(){
	if(win)
		win.close();
}

function modalWindow(width,height){
	var id = modalWindow.stack.push(this);
	this._onclose = function(value){ 
		window.onfocus = function(){
		} 
		modalWindow.current = false ;
	} 
	this.onclose = function(value){} 
	this.open = function(url,override){ 
		if(!window.showModalDialog || override){
			var LeftPosition=(screen.width-width)/2;
			var TopPosition=(screen.height-height)/2;
			modalWindow.current = win = window.open(url,'ModalWindow_'+id,'top='+TopPosition+',left='+LeftPosition+',width='+(width || 300)+',height='+ (height||180) +',modal,dialog');
			
			// window.onfocus = function(){ modalWindow.current.focus() } Para evitar el pete, si se activa la ventana  padre, y el pop ha sido cerrado
		}else{ 
			win = window.showModalDialog( url , "" , "dialogWidth:"+(width||300)+"px; dialogHeight: "+(height||180)+"px; status:no; help:no");
		} 
	} 
} 
modalWindow.stack = []; 
modalWindow.current = false;
if(window.opener && window.name.split('_')[0] == 'ModalWindow'){ 
	if(!window.returnValue) window.returnValue = false; 
	var _creator = window.opener.modalWindow.stack[ window.name.split('_')[1] ] 
	window.onunload = function(){ modalWindow.current.close() } 
	window.onblur = function(){ window.focus() } }    









//OTRA VERSION
var winModalWindow;

function IgnoreEvents(e)
{
  return false
}

function ShowWindow(path,width,height)
{

  if (window.showModalDialog)
  {
  	var sFeatures="dialogHeight: " + height + "px;" + "dialogWidth: " + width + "px;"  ;
  	window.showModalDialog(path, window.document, sFeatures);
  	
    // window.showModalDialog(path,null, "dialogWidth="+width+";dialogHeight="+height)
  }
  else
  {
    window.top.captureEvents (Event.CLICK|Event.FOCUS)
    window.top.onclick=IgnoreEvents
    window.top.onfocus=HandleFocus
    winModalWindow =
    window.open (path,"ModalChild",
       "dependent=yes,width="+width+",height="+height)
    winModalWindow.focus()
  }
}


function HandleFocus()
{
  if (winModalWindow)
  {
    if (!winModalWindow.closed)
    {
      winModalWindow.focus()
    }
    else
    {
      window.top.releaseEvents (Event.CLICK|Event.FOCUS)
      window.top.onclick = ""
    }
  }
  return false
}
