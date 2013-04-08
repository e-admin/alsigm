/* this is a variation on the basic document object constructor we have used up until now. 
Two functions are defined: getDocObj() and getStyleObj. The getDocObj() function creates the document object for the browser in use depending on whether it recognizes document.layers, document.all or document.getElementById(). The getStyleObj() function gets the style object of a document object. 

To invoke each, use like this:

variablename = eval(getDocObj(elementidvalue)); 

or 

variablename = eval(getStyleObj(elementidvalue)); 

Use this in conjunction with any cross-browser script where you must account for Netscape 4.x, IE 4.x and W3C DOM compliant browsers.

*/


function getDocObj(elem,parent) {
	if (document.layers) {
	    if (parent) {
	     return "document."+parent+".document."+elem;
	      }
	    else {
		return "document."+elem;
		     }
		
	  } 
	    else if (document.all) {
		return "document.all."+elem;
	} 
	    else if (document.getElementById) {
		return "document.getElementById('"+elem+"')";
	}
}


function getStyleObj(elem,parent) {
	if (document.layers) {
	    if (parent) {
	     return "document."+parent+".document."+elem;
	      }
	    else {
		return "document."+elem + ".style";
		     }
		
	  } 
	    else if (document.all) {
		return "document.all."+elem + ".style";
	  } 
	    else if (document.getElementById) {
		return "document.getElementById('"+elem+"').style";
		
	}
}


