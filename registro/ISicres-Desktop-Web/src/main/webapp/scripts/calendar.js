	var language = 'ES';
	var enablePast = 0;
	var fixedX = -1;
	var fixedY = -1;
	var startAt = 1;
	var showWeekNumber = 0;
	var showToday = 1;
	var imgDir = 'images/';
	var dayName = '';

	var gotoString = {
		ES : 'Ir al mes actual',
		CT_ : 'Ves al mes Actual',
		EU_ : 'Gaur egungo hilarengan joatea',
		GL_ : 'Ir ao mes actual'
	};
	var todayString = {
		ES : 'Hoy es',
		CT_ : 'Avui és',
		EU_ : 'Gaur da',
		GL_ : 'Hoxe é'
	};
	var weekString = {
		ES : 'Sem',
		CT_ : 'Sem',
		EU_ : 'Ast',
		GL_ : 'Sem'
	};
	var scrollLeftMessage = {
		ES : 'Presione para pasar al mes anterior. Deje presionado para pasar varios meses.',
		CT_ : 'Pressioni per a passar al mes anterior. Deixi pressionat per a passar diversos mesos.',
		EU_ : 'Pasatzeko presioa egin dezan aurreko hilarengan. Presioa eginda utz beza zenbait hilengan pasatzeko.',
		GL_ : 'Presione para pasar ao mes anterior. Deixe presionado para pasar varios meses'
	};
	var scrollRightMessage = {
		ES : 'Presione para pasar al siguiente mes. Deje presionado para pasar varios meses.',
		CT_ : 'Pressioni per a passar al següent mes. Deixi pressionat per a passar diversos mesos.',
		EU_ : 'Pasatzeko presioa egin dezan hurrengo hilarengan. Presioa eginda utz beza zenbait hilengan pasatzeko',
		GL_ : 'Presione para pasar ao seguinte mes. Deixe presionado para pasar varios meses.'
	};
	var selectMonthMessage = {
		ES : 'Presione para seleccionar un mes',
		CT_ : 'Pressioni per a seleccionar un mes.',
		EU_ : 'Hil batengan aukeratzeko presioa egin dezan',
		GL_ : 'Presione para seleccionar un mes'
	};
	var selectYearMessage = {
		ES : 'Presione para seleccionar un año',
		CT_ : 'Pressioni per a seleccionar un any.',
		EU_ : 'Urte batean aukeratzeko presioa egin dezan',
		GL_ : 'Presione para seleccionar un ano'
	};
	var selectDateMessage = {		// do not replace [date], it will be replaced by date.
		ES : 'Seleccione [date] como fecha',
		CT_ : 'Seleccioni [date] com data.',
		EU_ : '[date] aukera dezan egunean.',
		GL_ : 'Seleccione [date] como data'
	};
	var	monthName = {
		ES : new Array('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'),
		CT_ : new Array('Gener','Febrer','Març','Abril','Maig','Juny','Julio','Agost','Setembre','Octubre','Novembre','Desembre'),
		EU_ : new Array('Urtarrilean','Otsailean','Martxoan','Apirilean','Maiatz','Ekainean','Uztailan','Abuztuan','Irailean','Urrian','Azaroan','Abenduan'),
		GL_ : new Array('Xaneiro','Febreiro','Marzo','Abril','Maio','Xuño','Xullo','Agosto','Setembro','Outubro','Novembro','Decembro')
	};
	var	monthName2 = {
		ES : new Array('ENE','FEB','MAR','ABR','MAY','JUN','JUL','AGO','SEP','OCT','NOV','DIC'),
		CT_ : new Array('GEN','FEB','MAR','ABR','MAI','JUN','JUL','AGO','SET','OCT','NOV','DES'),
		EU_ : new Array('URT','OTS','MAR','API','MAI','EKA','UZT','ABU','IRA','URR','AZA','ABE'),
		GL_ : new Array('XAN','FEB','MAR','ABR','MAI','XUÑ','XUL','AGO','SET','OUT','NOV','DIC')
	};

	if (startAt==0) {
		dayName = {
			ES : new Array('Dom','Lun','Mar','Mie','Jue','Vie','Sab'),
			CT_ : new Array('Dg','Dl','Dm','Dc','Dj','Dv','Ds'),
			EU_ : new Array('Iga','Leh','Art','Azk','Gun','Ira','Lar'),
			GL_ : new Array('Dom','Lun','Mar','Mér','Xov','Ven','Sab')
		};
	} else {
		dayName = {
			ES : new Array('Lun','Mar','Mie','Jue','Vie','Sab','Dom'),
			CT_ : new Array('Dl','Dm','Dc','Dj','Dv','Ds','Dg'),
			EU_ : new Array('Leh','Art','Azk','Gun','Ira','Lar','Iga'),
			GL_ : new Array('Lun','Mar','Mér','Xov','Ven','Sab','Dom')
		};
	}

	var crossobj, crossMonthObj, crossYearObj, monthSelected, yearSelected, dateSelected, omonthSelected, oyearSelected, odateSelected, monthConstructed, yearConstructed, intervalID1, intervalID2, timeoutID1, timeoutID2, ctlToPlaceValue, ctlNow, dateFormat, nStartingYear, selDayAction, isPast;
	var visYear  = 0;
	var visMonth = 0;
	var bPageLoaded = false;
	var ie  = document.all;
	var dom = document.getElementById;
	var ns4 = document.layers;
	var today    = new Date();
	var dateNow  = today.getDate();
	var monthNow = today.getMonth();
	var yearNow  = today.getFullYear();
	var timeNow  = 	today.getHours() + ":" + today.getMinutes() +":" + today.getSeconds();
	var imgsrc   = new Array('pcaldrop1.gif','pcaldrop2.gif','pcalleft1.gif','pcalleft2.gif','pcalright1.gif','pcalright2.gif');
	var img      = new Array();
	var bShow    = false;


	function hideCombosNexo (overDiv) {
		var contadorreg = document.getElementById("contadorreg").value;
		for (i = 0; i < contadorreg; i++) {
			var nexo = document.getElementById("nexo_" + i);
			// Find the element's offsetTop and offsetLeft relative to the BODY tag.
			objLeft   = nexo.offsetLeft;
			objTop    = nexo.offsetTop;
			objParent = nexo.offsetParent;

			while(objParent.tagName.toUpperCase() != 'TABLE') {
				objLeft  += objParent.offsetLeft;
				objTop   += objParent.offsetTop;
				objParent = objParent.offsetParent;
			}

			objHeight = nexo.offsetHeight;
			objWidth  = nexo.offsetWidth;

			if ((objLeft < (overDiv.offsetLeft + overDiv.offsetWidth))
				&&
				((objLeft + objWidth) > overDiv.offsetLeft)
				&&
				(overDiv.offsetTop < (objTop + objHeight)))
				//&&
				//((overDiv.offsetTop + overDiv.offsetHeight) > objTop))
				{
					nexo.style.visibility = 'hidden';
				}
		}
	}

	/* hides <select> and <applet> objects (for IE only) */
	function hideElement( elmID, overDiv ) {

		if (navigator.appVersion.indexOf("MSIE 6")>0 && top.g_typeSearchAdvanced) {
			hideCombosNexo(overDiv);
		}
		else {
			if (ie && top.g_typeSearchAdvanced!=true) {
				for(i = 0; i < document.all.tags( elmID ).length; i++) {
					obj = document.all.tags( elmID )[i];
					if(!obj || !obj.offsetParent) continue;

					// Find the element's offsetTop and offsetLeft relative to the BODY tag.
					objLeft   = obj.offsetLeft;
					objTop    = obj.offsetTop;
					objParent = obj.offsetParent;

					while(objParent.tagName.toUpperCase() != 'BODY') {
						objLeft  += objParent.offsetLeft;
						objTop   += objParent.offsetTop;
						objParent = objParent.offsetParent;
					}

					objHeight = obj.offsetHeight;
					objWidth  = obj.offsetWidth;

					if((overDiv.offsetLeft + overDiv.offsetWidth) <= objLeft);
					else if((overDiv.offsetTop + overDiv.offsetHeight) <= objTop);
					else if(overDiv.offsetTop >= (objTop + objHeight + obj.height));
					else if(overDiv.offsetLeft >= (objLeft + objWidth));
					else {
						obj.style.visibility = 'hidden';
					}
				}
			}
		}
	}

	function showElement(elmID) {
		if(ie) {
			for(i = 0; i < document.all.tags( elmID ).length; i++) {
				obj = document.all.tags(elmID)[i];
				if(!obj || !obj.offsetParent) continue;
				obj.style.visibility = '';
			}
		}
	}

	function HolidayRec (d, m, y, desc) {
		this.d = d;
		this.m = m;
		this.y = y;
		this.desc = desc;
	}

	var HolidaysCounter = 0;
	var Holidays = new Array();

	function addHoliday (d, m, y, desc) {
		Holidays[HolidaysCounter++] = new HolidayRec (d, m, y, desc);
	}

	var	styleAnchor = 'text-decoration:none;color:black;';
	var	styleLightBorder = 'border:1px solid #a0a0a0;';

	function swapImage(srcImg, destImg) {
		if (ie) document.getElementById(srcImg).setAttribute('src',imgDir + destImg);
	}

	function initCalendar() {
		if (dom) {
			for	(i=0;i<imgsrc.length;i++) {
				img[i] = new Image;
				img[i].src = imgDir + imgsrc[i];
			}

			var divCal = document.createElement("DIV");
			var HTMLtext = "";

			divCal.setAttribute("id", "calendar");
			divCal.style.visibility = "hidden";
			divCal.style.position = "absolute";
			divCal.style.zIndex = "+999";
			divCal["onclick"] = "bShow=true";

			document.body.appendChild(divCal);

			HTMLtext = '<table width="'+((showWeekNumber==1)?250:220)+'" style="font-family:Arial;font-size:11px;border: 1px solid #A0A0A0;" bgcolor="#ffffff"><tr class="td_encabezado_azul"><td><table width="'+((showWeekNumber==1)?248:218)+'"><tr><td style="padding:2px;font-family:Arial;font-size:11px;"><font color="#000000' + '' /*C9D3E9*/ +'"><b><span id="caption"></span></b></font></td><td align="right"><a href="javascript:hideCalendar()"><img src="'+imgDir+'pcalclose.gif" width="15" height="13" border="0" /></a></td></tr></table></td></tr><tr><td style="padding:5px" bgcolor="#ffffff"><span id="content"></span></td></tr>';

			if (showToday == 1) {
				HTMLtext += '<tr bgcolor="#f0f0f0"><td style="padding:5px" align="center"><span id="lblToday"></span></td></tr>';
			}

			HTMLtext += '</table></div><div id="selectMonth" style="z-index:+999;position:absolute;visibility:hidden;"></div><div id="selectYear" style="z-index:+999;position:absolute;visibility:hidden;">';

			divCal.innerHTML = HTMLtext;

			document.onkeypress = function hidecal1 () {
				if (top.GetKeyCode(event) == 27) hideCalendar();
			}

			document.onclick = function hidecal2 () {
				if (!bShow) {hideCalendar();}
				bShow = false;
			}
		}
		if (!ns4)
		{
			crossobj=(dom)?document.getElementById('calendar').style : ie? document.all.calendar : document.calendar;
			hideCalendar();

			crossMonthObj = (dom) ? document.getElementById('selectMonth').style : ie ? document.all.selectMonth : document.selectMonth;

			crossYearObj = (dom) ? document.getElementById('selectYear').style : ie ? document.all.selectYear : document.selectYear;

			monthConstructed = false;
			yearConstructed = false;

			if (showToday == 1) {
				document.getElementById('lblToday').innerHTML =	'<font color="#000066">' + todayString[language] + ' <a onmousemove="window.status=\''+gotoString[language]+'\'" onmouseout="window.status=\'\'" title="'+gotoString[language]+'" style="'+styleAnchor+'" href="javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();">'+dayName[language][(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+', ' + dateNow + ' ' + monthName[language][monthNow].substring(0,3) + ' ' + yearNow + '</a></font>';
			}

			sHTML1 = '<span id="spanLeft" style="border:1px solid #36f;cursor:pointer;background-color: #EEEEEE" onmouseover="swapImage(\'changeLeft\',\'pcalleft2.gif\');this.style.borderColor=\'#8af\';window.status=\''+scrollLeftMessage[language]+'\'" onclick="decMonth(event);" onmouseout="clearInterval(intervalID1);swapImage(\'changeLeft\',\'pcalleft1.gif\');this.style.borderColor=\'#36f\';window.status=\'\'" onmousedown="clearTimeout(timeoutID1);timeoutID1=setTimeout(\'StartDecMonth(event)\',500)" onmouseup="clearTimeout(timeoutID1);clearInterval(intervalID1)">&nbsp<img id="changeLeft" src="'+imgDir+'pcalleft1.gif" width="10" height="11" border="0">&nbsp</span>&nbsp;';
			sHTML1 += '<span id="spanRight" style="border:1px solid #36f;cursor:pointer;background-color: #EEEEEE" onmouseover="swapImage(\'changeRight\',\'pcalright2.gif\');this.style.borderColor=\'#8af\';window.status=\''+scrollRightMessage[language]+'\'" onmouseout="clearInterval(intervalID1);swapImage(\'changeRight\',\'pcalright1.gif\');this.style.borderColor=\'#36f\';window.status=\'\'" onclick="incMonth(event);" onmousedown="clearTimeout(timeoutID1);timeoutID1=setTimeout(\'StartIncMonth(event)\',500)" onmouseup="clearTimeout(timeoutID1);clearInterval(intervalID1)">&nbsp<img id="changeRight" src="'+imgDir+'pcalright1.gif" width="10" height="11" border="0">&nbsp</span>&nbsp;';
			sHTML1 += '<span id="spanMonth" style="border:1px solid #36f;cursor:pointer;background-color: #EEEEEE" onmouseover="swapImage(\'changeMonth\',\'pcaldrop2.gif\');this.style.borderColor=\'#8af\';window.status=\''+selectMonthMessage[language]+'\'" onmouseout="swapImage(\'changeMonth\',\'pcaldrop1.gif\');this.style.borderColor=\'#36f\';window.status=\'\'" onclick="popUpMonth(event);"></span>&nbsp;';
			sHTML1 += '<span id="spanYear" style="border:1px solid #36f;cursor:pointer;background-color: #EEEEEE" onmouseover="swapImage(\'changeYear\',\'pcaldrop2.gif\');this.style.borderColor=\'#8af\';window.status=\''+selectYearMessage[language]+'\'" onmouseout="swapImage(\'changeYear\',\'pcaldrop1.gif\');this.style.borderColor=\'#36f\';window.status=\'\'" onclick="popUpYear(event)"></span>&nbsp;';

			document.getElementById('caption').innerHTML = sHTML1;

			bPageLoaded=true;
		}
	}

	function hideCalendar() {
		crossobj.visibility = 'hidden';
		if (crossMonthObj != null) crossMonthObj.visibility = 'hidden';
		if (crossYearObj  != null) crossYearObj.visibility = 'hidden';
		showElement('SELECT');
		showElement('APPLET');
	}

	function padZero(num) {
		return (num	< 10) ? '0' + num : num;
	}

	function constructDate(d,m,y, t) {
		sTmp = dateFormat;
		sTmp = sTmp.replace ('dd','<e>');
		sTmp = sTmp.replace ('d','<d>');
		sTmp = sTmp.replace ('<e>',padZero(d));
		sTmp = sTmp.replace ('<d>',d);
		sTmp = sTmp.replace ('mmmm','<p>');
		sTmp = sTmp.replace ('mmm','<o>');
		sTmp = sTmp.replace ('mm','<n>');
		sTmp = sTmp.replace ('m','<m>');
		sTmp = sTmp.replace ('<m>',m+1);
		sTmp = sTmp.replace ('<n>',padZero(m+1));
		sTmp = sTmp.replace ('<o>',monthName[language][m]);
		sTmp = sTmp.replace ('<p>',monthName2[language][m]);
		sTmp = sTmp.replace ('yyyy',y);
		sTmp = sTmp.replace ('yy',padZero(y%100));
		return sTmp.replace ('tt',t);

	}

	function closeCalendar() {
		var timed = new Date();
		var tt = timed.getHours() + ":" + timed.getMinutes() + ":" +timed.getSeconds() + "";
		hideCalendar();

		ctlToPlaceValue.value = constructDate(dateSelected,monthSelected,yearSelected, tt );

		try {
			cambioValor(ctlToPlaceValue);
			top.Main.Folder.FolderBar.ActivateSave();
		}
		catch(e){}
	}

	function StartDecMonth(aEvent) {
		intervalID1 = setInterval("decMonth(aEvent)",80);
	}

	function StartIncMonth(aEvent) {
		intervalID1 = setInterval("incMonth(aEvent)",80);
	}

	function incMonth (aEvent) {
		top.StopPropagation(aEvent);
		monthSelected++;
		if (monthSelected > 11) {
			monthSelected = 0;
			yearSelected++;
		}
		constructCalendar();
	}

	function decMonth (aEvent) {
		top.StopPropagation(aEvent);
		monthSelected--;
		if (monthSelected < 0) {
			monthSelected = 11;
			yearSelected--;
		}
		constructCalendar();
	}

	function constructMonth(aEvent) {
		popDownYear(aEvent)
		if (!monthConstructed) {
			sHTML = "";
			for (i=0; i<12; i++) {
				sName = monthName[language][i];
				if (i == monthSelected){
					sName = '<b>' + sName + '</b>';
				}
				sHTML += '<tr><td id="m' + i + '" onmouseover="this.style.backgroundColor=\'#909090\'" onmouseout="this.style.backgroundColor=\'\'" style="cursor:pointer" onclick="monthConstructed=false;monthSelected=' + i + ';constructCalendar();popDownMonth(event);top.StopPropagation(event);"><font color="#000066">&nbsp;' + sName + '&nbsp;</font></td></tr>';
			}

			document.getElementById('selectMonth').innerHTML = '<table width="70" style="font-family:Arial;font-size:11px;border:1px solid #a0a0a0;" bgcolor="#f0f0f0" cellspacing="0" onmouseover="clearTimeout(timeoutID1)" onmouseout="clearTimeout(timeoutID1);timeoutID1=setTimeout(\'popDownMonth(event)\',100);top.StopPropagation(event);">' + sHTML + '</table>';

			monthConstructed = true;
		}
	}

	function popUpMonth(aEvent) {
		var leftOffset;
		var heightOffset;
		var topOffset;

		top.StopPropagation(aEvent);

		if (visMonth == 1) {
			popDownMonth();
			visMonth--;
		} else {
			constructMonth(aEvent);
			crossMonthObj.visibility = (dom||ie) ? 'visible' : 'show';
			//crossMonthObj.left = parseInt(crossobj.left) + 50;
			leftOffset = document.getElementById('spanMonth').offsetLeft;
			if (ie) leftOffset += 6;
			crossMonthObj.left = leftOffset;
			//crossMonthObj.top =	parseInt(crossobj.top) + 26;
			//crossMonthObj.top =	parseInt(crossobj.top) - 35;
			heightOffset = document.getElementById('spanMonth').offsetHeight;
			topOffset = document.getElementById('spanMonth').offsetTop;
			if (ie) topOffset += 6;
			crossMonthObj.top =	heightOffset + topOffset;
			hideElement('SELECT', document.getElementById('selectMonth'));
			hideElement('APPLET', document.getElementById('selectMonth'));
			visMonth++;
		}
	}

	function popDownMonth() {
		crossMonthObj.visibility = 'hidden';
		visMonth = 0;
	}

	/*** Year Pulldown ***/
	function incYear(aEvent) {
		top.StopPropagation(aEvent);
		for	(i=0; i<7; i++) {
			newYear	= (i + nStartingYear) + 1;
			if (newYear == yearSelected)
				txtYear = '<span style="color:#006;font-weight:bold;">&nbsp;' + newYear + '&nbsp;</span>';
			else
				txtYear = '<span style="color:#006;">&nbsp;' + newYear + '&nbsp;</span>';
			document.getElementById('y'+i).innerHTML = txtYear;
		}
		nStartingYear++;
		bShow=true;
	}

	function decYear(aEvent) {
		top.StopPropagation(aEvent);
		for	(i=0; i<7; i++) {
			newYear = (i + nStartingYear) - 1;
			if (newYear == yearSelected)
				txtYear = '<span style="color:#006;font-weight:bold">&nbsp;' + newYear + '&nbsp;</span>';
			else
				txtYear = '<span style="color:#006;">&nbsp;' + newYear + '&nbsp;</span>';
			document.getElementById('y'+i).innerHTML = txtYear;
		}
		nStartingYear--;
		bShow=true;
	}

	function selectYear(aEvent, nYear) {
		yearSelected = parseInt(nYear + nStartingYear);
		yearConstructed = false;
		constructCalendar();
		popDownYear(aEvent);
	}

	function constructYear() {
		popDownMonth();
		sHTML = '';
		if (!yearConstructed) {
			sHTML = '<tr><td align="center" onmouseover="this.style.backgroundColor=\'#909090\'" onmouseout="clearInterval(intervalID1);this.style.backgroundColor=\'\'" style="cursor:pointer" onmousedown="clearInterval(intervalID1);intervalID1=setInterval(\'decYear(event)\',30)" onmouseup="clearInterval(intervalID1)"><font color="#000066">-</font></td></tr>';

			j = 0;
			nStartingYear =	yearSelected - 3;
			for ( i = (yearSelected-3); i <= (yearSelected+3); i++ ) {
				sName = i;
				if (i == yearSelected) sName = '<b>' + sName + '</b>';
				sHTML += '<tr><td id="y' + j + '" onmouseover="this.style.backgroundColor=\'#909090\'" onmouseout="this.style.backgroundColor=\'\'" style="cursor:pointer" onclick="selectYear(event, '+j+');top.StopPropagation(event);"><font color="#000066">&nbsp;' + sName + '&nbsp;</font></td></tr>';
				j++;
			}

			sHTML += '<tr><td align="center" onmouseover="this.style.backgroundColor=\'#909090\'" onmouseout="clearInterval(intervalID2);this.style.backgroundColor=\'\'" style="cursor:pointer" onmousedown="clearInterval(intervalID2);intervalID2=setInterval(\'incYear(event)\',30)" onmouseup="clearInterval(intervalID2)"><font color="#000066">+</font></td></tr>';

			document.getElementById('selectYear').innerHTML = '<table width="44" cellspacing="0" bgcolor="#f0f0f0" style="font-family:Arial;font-size:11px;border:1px solid #a0a0a0;" onmouseover="clearTimeout(timeoutID2)" onmouseout="clearTimeout(timeoutID2);timeoutID2=setTimeout(\'popDownYear(event)\',100)">' + sHTML + '</table>';

			yearConstructed = true;
		}
	}

	function popDownYear(aEvent) {
		top.StopPropagation(aEvent);
		clearInterval(intervalID1);
		clearTimeout(timeoutID1);
		clearInterval(intervalID2);
		clearTimeout(timeoutID2);
		crossYearObj.visibility= 'hidden';
		visYear = 0;
	}

	function popUpYear(aEvent) {
		var leftOffset;
		var heightOffset;
		var topOffset;

		top.StopPropagation(aEvent);

		if (visYear==1) {
			popDownYear(aEvent);
			visYear--;
		} else {
			constructYear();
			crossYearObj.visibility	= (dom||ie) ? 'visible' : 'show';
			//leftOffset = parseInt(crossobj.left) + document.getElementById('spanYear').offsetLeft;
			leftOffset = document.getElementById('spanYear').offsetLeft;
			if (ie) leftOffset += 6;
			crossYearObj.left = leftOffset;
			//crossYearObj.top = parseInt(crossobj.top) + 26;
			//crossYearObj.top = parseInt(crossobj.top) - 35;
			heightOffset = document.getElementById('spanYear').offsetHeight;
			topOffset = document.getElementById('spanYear').offsetTop;
			if (ie) topOffset += 6;
			crossYearObj.top = heightOffset + topOffset;
			visYear++;
		}
	}

	function WeekNbr(n) {
		// Algorithm used:
		// From Klaus Tondering's Calendar document (The Authority/Guru)
		// http://www.tondering.dk/claus/calendar.html
		// a = (14-month) / 12
		// y = year + 4800 - a
		// m = month + 12a - 3
		// J = day + (153m + 2) / 5 + 365y + y / 4 - y / 100 + y / 400 - 32045
		// d4 = (J + 31741 - (J mod 7)) mod 146097 mod 36524 mod 1461
		// L = d4 / 1460
		// d1 = ((d4 - L) mod 365) + L
		// WeekNumber = d1 / 7 + 1

		year = n.getFullYear();
		month = n.getMonth() + 1;
		if (startAt == 0) {
			day = n.getDate() + 1;
		} else {
			day = n.getDate();
		}

		a = Math.floor((14-month) / 12);
		y = year + 4800 - a;
		m = month + 12 * a - 3;
		b = Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400);
		J = day + Math.floor((153 * m + 2) / 5) + 365 * y + b - 32045;
		d4 = (((J + 31741 - (J % 7)) % 146097) % 36524) % 1461;
		L = Math.floor(d4 / 1460);
		d1 = ((d4 - L) % 365) + L;
		week = Math.floor(d1/7) + 1;

		return week;
	}

	function constructCalendar () {
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);
		var dateMessage;
		var startDate = new Date (yearSelected,monthSelected,1);
		var endDate;

		if (monthSelected==1) {
			endDate = new Date (yearSelected,monthSelected+1,1);
			endDate = new Date (endDate - (24*60*60*1000));
			numDaysInMonth = endDate.getDate();
		} else {
			numDaysInMonth = aNumDays[monthSelected];
		}

		datePointer = 0;
		dayPointer = startDate.getDay() - startAt;

		if (dayPointer<0) dayPointer = 6;

		sHTML = '<table border="0" style="font-family:verdana;font-size:10px;"><tr>';

		if (showWeekNumber == 1) {
			sHTML += '<td width="27"><b>' + weekString[language] + '</b></td><td width="1" rowspan="7" bgcolor="#d0d0d0" style="padding:0px"><img src="'+imgDir+'divider.gif" width="1"></td>';
		}

		for (i = 0; i<7; i++) {
			sHTML += '<td width="27" align="right"><b><font color="#000066">' + dayName[language][i] + '</font></b></td>';
		}

		sHTML += '</tr><tr>';

		if (showWeekNumber == 1) {
			sHTML += '<td align="right">' + WeekNbr(startDate) + '&nbsp;</td>';
		}

		for	( var i=1; i<=dayPointer;i++ ) {
			sHTML += '<td>&nbsp;</td>';
		}

		for	( datePointer=1; datePointer <= numDaysInMonth; datePointer++ ) {
			dayPointer++;
			sHTML += '<td align="right">';
			sStyle=styleAnchor;
			if ((datePointer == odateSelected) && (monthSelected == omonthSelected) && (yearSelected == oyearSelected))
			{ sStyle+=styleLightBorder }

			sHint = '';
			for (k = 0;k < HolidaysCounter; k++) {
				if ((parseInt(Holidays[k].d) == datePointer)&&(parseInt(Holidays[k].m) == (monthSelected+1))) {
					if ((parseInt(Holidays[k].y)==0)||((parseInt(Holidays[k].y)==yearSelected)&&(parseInt(Holidays[k].y)!=0))) {
						sStyle+= 'background-color:#fdd;';
						sHint += sHint=="" ? Holidays[k].desc : "\n"+Holidays[k].desc;
					}
				}
			}

			sHint = sHint.replace('/\"/g', '&quot;');

			dateMessage = 'onmousemove="window.status=\''+selectDateMessage[language].replace('[date]',constructDate(datePointer,monthSelected,yearSelected, timeNow))+'\'" onmouseout="window.status=\'\'" ';

			if (enablePast == 0 && ((yearSelected < yearNow) || (monthSelected < monthNow) && (yearSelected == yearNow) || (datePointer < dateNow) && (monthSelected == monthNow) && (yearSelected == yearNow))) {
				selDayAction = '';
				isPast = 1;
			} else {
				selDayAction = 'href="javascript:dateSelected=' + datePointer + ';closeCalendar();"';
				isPast = 0;
			}

			if ((datePointer == dateNow) && (monthSelected == monthNow) && (yearSelected == yearNow)) {	///// today
				sHTML += "<b><a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+"><font color=#ff0000>&nbsp;" + datePointer + "</font>&nbsp;</a></b>";
			} else if (dayPointer % 7 == (startAt * -1)+1) {									///// SI ES DOMINGO
				if (isPast==1)
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#909090>" + datePointer + "</font>&nbsp;</a>";
				else
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#54A6E2>" + datePointer + "</font>&nbsp;</a>";
			} else if ((dayPointer % 7 == (startAt * -1)+7 && startAt==1) || (dayPointer % 7 == startAt && startAt==0)) {	///// SI ES SABADO
				if (isPast==1)
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#909090>" + datePointer + "</font>&nbsp;</a>";
				else
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#54A6E2>" + datePointer + "</font>&nbsp;</a>";
			} else {																			///// CUALQUIER OTRO DIA
				if (isPast==1)
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#909090>" + datePointer + "</font>&nbsp;</a>";
				else
					sHTML += "<a "+dateMessage+" title=\"" + sHint + "\" style='"+sStyle+"' "+selDayAction+">&nbsp;<font color=#000066>" + datePointer + "</font>&nbsp;</a>";
			}

			sHTML += '';

			if ((dayPointer+startAt) % 7 == startAt) {
				sHTML += '</tr><tr>';
				if ((showWeekNumber == 1) && (datePointer < numDaysInMonth)) {
					sHTML += '<td align="right">' + (WeekNbr(new Date(yearSelected,monthSelected,datePointer+1))) + '&nbsp;</td>';
				}
			}
		}

		document.getElementById('content').innerHTML   = sHTML
		document.getElementById('spanMonth').innerHTML = '&nbsp;' +	monthName[language][monthSelected] + '&nbsp;<img id="changeMonth" src="'+imgDir+'pcaldrop1.gif" width="12" height="10" border="0">'
		document.getElementById('spanYear').innerHTML  = '&nbsp;' + yearSelected	+ '&nbsp;<img id="changeYear" src="'+imgDir+'pcaldrop1.gif" width="12" height="10" border="0">';
	}

	function showCalendarEx(ctl, ctl2, lang)
	{
		initCalendar();
		showCalendar(ctl, ctl2, "dd-mm-yyyy", lang, -1, -1, -1);
	}

	function showCalendarExDateLong(ctl, ctl2, lang)
	{
		initCalendar();
		//hora actual
		var oT=lZ(tS().getHours())+':'+lZ(tS().getMinutes())+':'+lZ(tS().getSeconds());
		showCalendar(ctl, ctl2, "dd-mm-yyyy "+ oT, lang, -1, -1, -1);
	}

	//funciones para obtener la hora
	function tS(){ x=new Date(); x.setTime(x.getTime()); return x; }
	function lZ(x){ return (x>9)?x:'0'+x; }
	//


	function showCalendar(ctl, ctl2, format, lang, past, fx, fy) {
		if (lang != null && lang != '') language = lang;
		if (past != null) enablePast = past;
		else enablePast = 0;
		if (fx != null) fixedX = fx;
		else fixedX = -1;
		if (fy != null) fixedY = fy;
		else fixedY = -1;

		if (showToday == 1) {
			document.getElementById('lblToday').innerHTML = '<font color="#000066">' + todayString[language] + ' <a onmousemove="window.status=\''+gotoString[language]+'\'" onmouseout="window.status=\'\'" title="'+gotoString[language]+'" style="'+styleAnchor+'" href="javascript:monthSelected=monthNow;yearSelected=yearNow;constructCalendar();">'+dayName[language][(today.getDay()-startAt==-1)?6:(today.getDay()-startAt)]+', ' + dateNow + ' ' + monthName[language][monthNow].substring(0,3) + ' ' + yearNow + '</a></font>';
		}

		popUpCalendar(ctl, ctl2, format);
	}

	function popUpCalendar(ctl, ctl2, format) {
		var leftpos = 0;
		var toppos  = 0;

		if (bPageLoaded) {
			if (crossobj.visibility == 'hidden') {
				ctlToPlaceValue = ctl2;
				dateFormat = format;
				formatChar = ' ';
				aFormat = dateFormat.split(formatChar);
				if (aFormat.length < 3) {
					formatChar = '/';
					aFormat = dateFormat.split(formatChar);
					if (aFormat.length < 3) {
						formatChar = '.';
						aFormat = dateFormat.split(formatChar);
						if (aFormat.length < 3) {
							formatChar = '-';
							aFormat = dateFormat.split(formatChar);
							if (aFormat.length < 3) {
								formatChar = '';					// invalid date format

							}
						}
					}
				}

				tokensChanged = 0;
				if (formatChar != "") {
					aData =	ctl2.value.split(formatChar);			// use user's date

					for (i=0; i<3; i++) {
						if ((aFormat[i] == "d") || (aFormat[i] == "dd")) {
							dateSelected = parseInt(aData[i], 10);
							tokensChanged++;
						} else if ((aFormat[i] == "m") || (aFormat[i] == "mm")) {
							monthSelected = parseInt(aData[i], 10) - 1;
							tokensChanged++;
						} else if (aFormat[i] == "yyyy") {
							yearSelected = parseInt(aData[i], 10);
							tokensChanged++;
						} else if (aFormat[i] == "mmm") {
							for (j=0; j<12; j++) {
								if (aData[i] == monthName[language][j]) {
									monthSelected=j;
									tokensChanged++;
								}
							}
						} else if (aFormat[i] == "mmmm") {
							for (j=0; j<12; j++) {
								if (aData[i] == monthName2[language][j]) {
									monthSelected = j;
									tokensChanged++;
								}
							}
						}
					}
				}

				if ((tokensChanged != 3) || isNaN(dateSelected) || isNaN(monthSelected) || isNaN(yearSelected)) {
					dateSelected  = dateNow;
					monthSelected = monthNow;
					yearSelected  = yearNow;
				}

				odateSelected  = dateSelected;
				omonthSelected = monthSelected;
				oyearSelected  = yearSelected;

				aTag = ctl;

				do {
					aTag     = aTag.offsetParent;
					if (aTag == null){break;}
					leftpos += aTag.offsetLeft;
				} while (aTag.tagName != 'TD');

				constructCalendar (1, monthSelected, yearSelected);

				if (ctl.offsetLeft + document.getElementById('calendar').offsetWidth > document.body.clientWidth) {
					leftpos = ctl.offsetWidth - document.getElementById('calendar').offsetWidth;
				}

				if (ctl.offsetTop + document.getElementById('calendar').offsetHeight > document.body.clientHeight) {
					toppos = - document.getElementById('calendar').offsetHeight;
				}

				if(toppos<0){
					toppos = ctl.offsetHeight - 13;
				}

				crossobj.left = ((fixedX == -1) ? ctl.offsetLeft + leftpos : fixedX)+"px";
				crossobj.top = ((fixedY == -1) ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 : fixedY)+"px";

				crossobj.visibility = (dom||ie) ? "visible" : "show";


				hideElement('SELECT', document.getElementById('calendar'));
				hideElement('APPLET', document.getElementById('calendar'));

				bShow = true;
			} else {
				hideCalendar();
				if (ctlNow!=ctl) popUpCalendar(ctl, ctl2, format);
			}
			ctlNow = ctl;
		}
	}

	function showCalendarQueryAdvan(ctl, inputId, lang){

		var ctl2 = document.getElementById(inputId);

		showCalendarEx(ctl, ctl2, lang);

	}
	