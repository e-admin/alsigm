var whitespace = "\t\n\r";
var carriageReturn = "\r\n";

function TextValidator(text)
{
	this.text = text;
	this.isNumeric = function () {
  						for(i=0; i < this.text.length; i++)
  						{
  		  					if(!this.text.charAt(i).match('[0-9]'))
    						{
       							return false;
    						}
  						} 
  						return true;
  					}
	this.isValidLetters = function () {
							for(i=0; i < this.text.length; i++)
    						{
      							if(!this.text.charAt(i).match('[aA-zZ]'))
      							{
         							return false;
      							}
    						}
      						return true; 
  						}
  
}

function trim(cadena){
	var cadena2=rtrim(cadena);
	var cadena3=ltrim(cadena2);
	return cadena3;
}

function rtrim(cadena){
	var i;
	i=cadena.length-1;
	while (cadena.charAt(i) == ' '){
		i--;
	}
	return cadena.substring(0,i+1);
}

function ltrim(cadena){
	var i=0;
	while (cadena.charAt(i) == ' '){
		i++;
	}
	return cadena.substring(i);
}

function isEmpty(cadena) {   
	return ((cadena == null) || (cadena.length == 0))
}

function isEmptyText(cadena) {  
	return ((isWhitespace(cadena)) ||(cadena == null) || (cadena.length == 0))
}

function isValidPwd(pwd)
{
	var validPwd = /^\w{3,}$/;
	var r = false;
	if ( validPwd.test(pwd) )
		r = true;
	return r;
}

function isIpCheck(ip)
{
	var validIpAddress = /^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$/;
	return validIpAddress.test(ip);
}

function isWhitespace(cadena) {
	var i;
	if (isEmpty(cadena)) return true;
	for (i = 0; i < cadena.length; i++)	{   
        var c = cadena.charAt(i);
		if (whitespace.indexOf(c) == -1) return false;
    }
	return true;
}

function isValidText(s)
{
	valid = true;
	if (!isBlank(s) ) {
		if ( s.indexOf('\'') != -1 || s.indexOf('\"') != -1  )
			valid = false;
	}
	return valid;
}

function stripBlanks(fld) {
	var result = "";
	var c = 0;
	for (i=0; i<fld.length; i++) {
	  if (fld.charAt(i) != " " || c > 0) {
	    result += fld.charAt(i);
	    if (fld.charAt(i) != " ") c = result.length;
	    }
	  }
	return result.substr(0,c);
}
function isBlank(fld) {
	fld = stripBlanks(fld);
	if (fld == '') return true;
	return false;
}

function isNumeric(s)
{
	var validNumber = /^[0-9]+$/;
	return validNumber.test(s);
}

function isEqual(cadena, cadena2) {
	if(cadena == cadena2)
		return true;
	else
		return false;
}

function reverse(str) {
	text = "";
	for (i = 0; i <= str.length; i++)
		text = str.substring(i, i+1) + text;
	return text;
}

function encode(str) {
	var result = "";
	var i = 0;
	var sextet = 0;
	var leftovers = 0;
	var octet = 0;
	
	for (i=0; i < str.length; i++) {
		octet = str.charCodeAt(i);
		switch( i % 3 ) {
			case 0:
			{
				sextet = ( octet & 0xFC ) >> 2 ;
				leftovers = octet & 0x03 ;
				// sextet contains first character in quadruple
				break;
			}
			case 1:
			{
				sextet = ( leftovers << 4 ) | ( ( octet & 0xF0 ) >> 4 );
				leftovers = octet & 0x0F ;
				// sextet contains 2nd character in quadruple
				break;
			}
	
			case 2:
			{
				sextet = ( leftovers << 2 ) | ( ( octet & 0xC0 ) >> 6 ) ;
				leftovers = ( octet & 0x3F ) ;
				// sextet contains third character in quadruple
				// leftovers contains fourth character in quadruple
				break;
			}
		}
		
		result = result + base64ToAscii(sextet);
	
		// don't forget about the fourth character if it is there
		if( (i % 3) == 2 )
			result = result + base64ToAscii(leftovers);
	}
	
	// figure out what to do with leftovers and padding
	switch( str.length % 3 ) {
		case 0:
		{
			// an even multiple of 3, nothing left to do
			break ;
		}
		case 1:
		{
			// one 6-bit chars plus 2 leftover bits
			leftovers =  leftovers << 4 ;
			result = result + base64ToAscii(leftovers);
			result = result + "==";
			break ;
		}
		case 2:
		{
			// two 6-bit chars plus 4 leftover bits
			leftovers = leftovers << 2 ;
			result = result + base64ToAscii(leftovers);
			result = result + "=";
			break ;
		}
	}
	
	return result;
}

function base64ToAscii(c) {
	var theChar = 0;
	
	if (0 <= c && c <= 25)
		theChar = String.fromCharCode(c + 65);
	else if (26 <= c && c <= 51)
		theChar = String.fromCharCode(c - 26 + 97);
	else if (52 <= c && c <= 61)
		theChar = String.fromCharCode(c - 52 + 48);
	else if (c == 62)
		theChar = '+';
	else if( c == 63 )
		theChar = '/';
	else
		theChar = String.fromCharCode(0xFF);
	
	return theChar;
}

function mask(obj, value) {
	obj.value = encode(reverse(value));
}

function reset(obj) {
	obj.value = "";
}

function changeCarriageReturn(field) {
	var i;
	var cadena = field.value;
	var cadenaSalida="";
   cadena = trimCR(cadena);
    field.value=cadena;

}
function trimspace(string) {
	//Match spaces at beginning and end of text and replace
	//with null strings
	return string.replace(/^\s+/,'').replace(/\s+$/,'').replace(/"/,'');
}
function trimCR(string) {
	return string.replace(/\r\n/g,';');
}

function chequearSession( baseUrl )
{
	if(window.XMLHttpRequest) {
		try {
			req = new XMLHttpRequest();
		} 
		catch(e) {
			req = false;
		}
	} 
	else if(window.ActiveXObject) {
		try {       		
			req = new ActiveXObject("Msxml2.XMLHTTP");
		} 
		catch(e) {
			try {
				req = new ActiveXObject("Microsoft.XMLHTTP");
			} 
			catch(e) {
				req = false;
			}
		}
	}
	if(req) {		
		var d = new Date();   
        var t = d.getTime();
		req.open("GET", baseUrl + "?" + 'timestamp=' + t, false);
		req.send("");
		xmlDoc = unescape( req.responseText);
		return xmlDoc;
	}  
}
