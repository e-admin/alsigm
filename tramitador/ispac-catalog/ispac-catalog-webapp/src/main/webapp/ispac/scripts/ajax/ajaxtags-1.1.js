/**
 * Copyright 2005 Darren L. Spurgeon
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var AjaxJspTag = {
  Version: '1.1-beta1'
}

var isSafari = false;
var isMoz = false;
var isIE = false;

if (navigator.userAgent.indexOf("Safari") > 0) {
  isSafari = true;
  isMoz = false;
  isIE = false;
}
else if (navigator.product == "Gecko") {
  isSafari = false;
  isMoz = true;
  isIE = false;
} else {
  isSafari = false;
  isMoz = false;
  isIE = true;
}

AJAX_METHOD_UPDATER = "updater";
AJAX_METHOD_REQUEST = "request";

AJAX_DEFAULT_PARAMETER = "ajaxParameter";

AJAX_PORTLET_MAX = 1;
AJAX_PORTLET_MIN = 2;
AJAX_PORTLET_CLOSE = 3;


/* ---------------------------------------------------------------------- */
/* Example File From "_JavaScript and DHTML Cookbook"
   Published by O'Reilly & Associates
   Copyright 2003 Danny Goodman
*/

// utility function to retrieve a future expiration date in proper format;
// pass three integer parameters for the number of days, hours,
// and minutes from now you want the cookie to expire; all three
// parameters required, so use zeros where appropriate
function getExpDate(days, hours, minutes) {
  var expDate = new Date();
  if (typeof days == "number" && typeof hours == "number" && typeof hours == "number") {
    expDate.setDate(expDate.getDate() + parseInt(days));
    expDate.setHours(expDate.getHours() + parseInt(hours));
    expDate.setMinutes(expDate.getMinutes() + parseInt(minutes));
    return expDate.toGMTString();
  }
}

// utility function called by getCookie()
function getCookieVal(offset) {
  var endstr = document.cookie.indexOf (";", offset);
  if (endstr == -1) {
    endstr = document.cookie.length;
  }
  return unescape(document.cookie.substring(offset, endstr));
}

// primary function to retrieve cookie by name
function getCookie(name) {
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  while (i < clen) {
    var j = i + alen;
    if (document.cookie.substring(i, j) == arg) {
      return getCookieVal(j);
    }
    i = document.cookie.indexOf(" ", i) + 1;
    if (i == 0) break;
  }
  return null;
}

// store cookie value with optional details as needed
function setCookie(name, value, expires, path, domain, secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}

// remove the cookie by setting ancient expiration date
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}
/* ---------------------------------------------------------------------- */
/* End Copyright 2003 Danny Goodman */


/* ---------------------------------------------------------------------- */
/* UTILITY FUNCTIONS
 */

/**
 * Type Detection
 */
function isAlien(a) {
  return isObject(a) && typeof a.constructor != 'function';
}

function isArray(a) {
  return isObject(a) && a.constructor == Array;
}

function isBoolean(a) {
  return typeof a == 'boolean';
}

function isEmpty(o) {
  var i, v;
  if (isObject(o)) {
    for (i in o) {
      v = o[i];
      if (isUndefined(v) && isFunction(v)) {
        return false;
      }
    }
  }
  return true;
}

function isFunction(a) {
  return typeof a == 'function';
}

function isNull(a) {
  return typeof a == 'object' && !a;
}

function isNumber(a) {
  return typeof a == 'number' && isFinite(a);
}

function isObject(a) {
  return (a && typeof a == 'object') || isFunction(a);
}

function isString(a) {
  return typeof a == 'string';
}

function isUndefined(a) {
  return typeof a == 'undefined';
}

function getPropertyByName(node, nodeName) {
  var arr = new Array();
  var items = node.getElementsByTagName("name");
  for (var i=0; i<items.length; i++) {
    if (nodeName == items[i].firstChild.nodeValue) {
      arr[0] = items[i].firstChild.nodeValue;
      arr[1] = items[i].parentNode.getElementsByTagName("value")[0].firstChild.nodeValue;
      return arr;
    }
  }
  return null;
}

function getValueForNode(node, nodeName) {
  var arr = getPropertyByName(node, nodeName);
  return arr != null ? arr[1] : null;
}

function evalBoolean(value, defaultValue) {
  if (!isNull(value) && isString(value)) {
    return ("true" == value.toLowerCase() || "yes" == value.toLowerCase()) ? "true" : "false";
  } else {
    return defaultValue == true ? "true" : "false";
  }
}

/*
 * Extract querystring from a URL
 */
function extractQueryString(url) {
  var ret = (url.indexOf('?') >= 0 && url.indexOf('?') < (url.length-1))
    ? url.substr(url.indexOf('?')+1)
    : '';
  return ret;
}

/*
 * Trim the querystring from a URL
 */
function trimQueryString(url) {
  var ret = url.indexOf('?') >= 0
    ? url.substring(0, url.indexOf('?'))
    : url;
  return ret;
}

/*
 *
 */
function delimitQueryString(qs) {
  var ret = '';
  if (qs.length > 0) {
    var params = qs.split('&');
    for (var i=0; i<params.length; i++) {
      if (i > 0) ret += ',';
      ret += params[i];
    }
  }
  return ret;
}

function getElementsByClassName(node, className) {
  var children = node.getElementsByTagName('*');
  var elements = new Array();

  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    var classNames = child.className.split(' ');
    for (var j = 0; j < classNames.length; j++) {
      if (classNames[j] == className) {
        elements.push(child);
        break;
      }
    }
  }
  return elements;
}

/*
 * Add multiple onLoad Events
 */
function addOnLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}

function getElementY(element){
  var targetTop = 0;
  if (element.offsetParent) {
    while (element.offsetParent) {
      targetTop += element.offsetTop;
      element = element.offsetParent;
    }
  } else if (element.y) {
    targetTop += element.y;
  }
  return targetTop;
}

function getElementX(element){
  var targetLeft = 0;
  if (element.offsetParent) {
    while (element.offsetParent) {
      targetLeft += element.offsetLeft;
      element = element.offsetParent;
    }
  } else if (element.x) {
    targetLeft += element.yx;
  }
  return targetLeft;
}

function findNextElementByTagName(element, tagName) {
  var children = element.getElementsByTagName('*');
  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    if (child.tagName.toLowerCase() == tagName.toLowerCase())
      return child;
  }
  return null;
}


/**
 * Returns true if an element has a specified class name
 */
function hasClass(node, className) {
  if (node.className == className) {
    return true;
  }
  var reg = new RegExp('(^| )'+ className +'($| )')
  if (reg.test(node.className)) {
    return true;
  }
  return false;
}

/**
 * Emulate PHP's ereg_replace function in javascript
 */
function eregReplace(search, replace, subject) {
  return subject.replace(new RegExp(search,'g'), replace);
}

function arrayToParameterString(array) {
  var str = '';
  for (var i=0; i<array.length; i++) {
    str += (i >= 0 ? '&' : '') + array[i];
  }
  return str;
}

function replaceWithValue(sourceString, regExp, element) {
  var retString = "";
  switch (element.type) {
    case 'checkbox':
    case 'radio':
    case 'text':
    case 'textarea':
    case 'password':
    case 'select-one':
    case 'select-multiple':
      retString = sourceString.replace(regExp, $F(element));
      break;
    default:
      retString = sourceString.replace(regExp, element.innerHTML);
      break;
  }
  return retString;
}

function decodeHtml(sourceString) {
  var retString = sourceString.replace(/&amp;/, "&");
  retString = retString.replace(/&lt;/, "<");
  retString = retString.replace(/&gt;/, ">");
  return retString;
}


/* ---------------------------------------------------------------------- */
/* AJAXJSPTAG.BASE
 */

AjaxJspTag.Base = function() {};
AjaxJspTag.Base.prototype = {

  resolveParameters: function() {
    // Strip URL of querystring and append it to parameters
    var qs = delimitQueryString(extractQueryString(this.baseUrl));
    if (this.options.parameters) {
      this.options.parameters += ',' + qs;
    } else {
      this.options.parameters = qs;
    }
    this.baseUrl = trimQueryString(this.baseUrl);
  },

  setAjaxOptions: function(ajaxOptions) {
    this.ajaxOptions = {
      asynchronous: true,
      method: 'get',
      evalScripts: true,
      onComplete: this.onRequestComplete.bind(this)
    }.extend(ajaxOptions || {});
  },

  sendRequest: function() {
    new Ajax.Request(this.baseUrl, this.ajaxOptions);
  },

  sendUpdateRequest: function(target) {
    this.ajaxUpdater = new Ajax.Updater(target, this.baseUrl, this.ajaxOptions);
  },

  sendPeriodicalUpdateRequest: function(target) {
    this.ajaxPeriodicalUpdater =
      new Ajax.PeriodicalUpdater(target, this.baseUrl, this.ajaxOptions);
  },

  onRequestComplete: function(request) {
    if (request != null && request.status == 200) {
      var xmlDoc = request.responseXML;
      if (this.options.ajaxMethod != AJAX_METHOD_UPDATER
          && this.isEmptyResponse(request.responseXML)) {
        if (this.options.emptyFunction) {
          this.options.emptyFunction(request);
        }
      } else if (this.options.ajaxMethod != AJAX_METHOD_UPDATER
                 && this.isErrorResponse(request.responseXML)) {
        this.options.errorFunction(request.responseXML);
      } else {
        this.handlerFunction(request.responseXML);
      }
    } else {
      if (this.options.errorFunction) {
        this.options.errorFunction(request);
      }
    }
  },

  isEmptyResponse: function(xml) {
    var root = xml.documentElement;
    if (root.getElementsByTagName("response").length == 0
        || root.getElementsByTagName("response")[0].getElementsByTagName("item").length == 0) {
      return true;
    }
    return false;
  },

  isErrorResponse: function(xml) {
    var root = xml.documentElement;
    if (root.nodeName == "parsererror"
        || root.getElementsByTagName("error").length == 1
        || root.getElementsByTagName("response").length == 0) {
      return true;
    }
    return false;
  },

  buildParameterString: function(parameterList) {
    var ajaxParameters = parameterList || '';
    var re = new RegExp("(\\{[^,]*\\})", 'g'); // should retrieve each {} group
    var results = ajaxParameters.match(re);
    if (results != null) {
      for (var r=0; r<results.length; r++) {
        var nre = new RegExp(results[r], 'g');
        var field = $(results[r].substring(1, results[r].length-1));
        ajaxParameters = replaceWithValue(ajaxParameters, nre, field);
      }
    }
    return ajaxParameters;
  },

  attachBehaviors: function(element, event, listener, obj) {
    if (isArray(element)) {
      for (var i=0; i<element.length; i++) {
        eval("element[i].on"+event+" = listener.bindAsEventListener(obj)");
      }
    } else {
      eval("element.on"+event+" = listener.bindAsEventListener(obj)");
    }
  }

}


/* ---------------------------------------------------------------------- */
/* SELECT TAG
 */

AjaxJspTag.Select = Class.create();
AjaxJspTag.Select.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElem, "change", this.sourceElemChanged, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      targetElem: $(options.target),
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  populateSelect: function(xml) {
    var root = xml.documentElement;

    // reset field
    this.options.targetElem.options.length = 0;
    this.options.targetElem.disabled = false;

    // grab list of options
    var respNode = root.getElementsByTagName("response")[0];
    var items = respNode.getElementsByTagName("item");
    for (var i=0; i<items.length; i++) {
      var name = items[i].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[i].getElementsByTagName("value")[0].firstChild.nodeValue;
      this.options.targetElem.options[i] = new Option(name, value);
    }
  },

  sourceElemChanged: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.populateSelect(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* TOGGLE TAG
 */

AjaxJspTag.Toggle = Class.create();
AjaxJspTag.Toggle.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.imageElem, "click", this.imageElemClicked, this);
  },

  setOptions: function(options) {
    this.options = {
      imageElem: $(options.image),
      stateElem: $(options.state),
      stateXmlName: options.stateXmlName ? options.stateXmlName : "toggleState",
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  toggleImage: function(xml) {
    var root = xml.documentElement;

    // set state
    var respNode = root.getElementsByTagName("response")[0];
    var toggleState = getValueForNode(respNode, this.options.stateXmlName);
    if (this.options.stateElem) {
      this.options.stateElem.value = toggleState;
    }

    // set image
    var patternRegExp = /\{0\}/;
    this.options.imageElem.src = this.options.imagePattern.replace(patternRegExp, toggleState);
  },

  imageElemClicked: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: this.options.parameters ?
        arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
        : ''
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.toggleImage(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* UPDATE FIELD TAG
 */

AjaxJspTag.UpdateField = Class.create();
AjaxJspTag.UpdateField.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.actionElem, "click", this.actionElemClicked, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      actionElem: $(options.action),
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  updateField: function(xml) {
    var root = xml.documentElement;
    var respNode = root.getElementsByTagName("response")[0];

    var items = respNode.getElementsByTagName("item");
    var targetArray = this.options.target.split(",");
    if (items.length > 0) {
      for (var i=0; i<targetArray.length; i++) {
        var value = getValueForNode(respNode, targetArray[i]);
        var field = $(targetArray[i]);
        if (value != null && field != null && field.type == "text") {
          field.value = value;
         }
      }
    }
  },

  actionElemClicked: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.updateField(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* AUTOCOMPLETE TAG
 */

AjaxJspTag.Autocomplete = Class.create();
AjaxJspTag.Autocomplete.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.suggestList = new Array();
    this.currentIndex = 0;
    this.createPopup();
    this.createIframe();
    this.options.sourceElem.setAttribute("autocomplete", "off");
    this.attachBehaviors(this.options.sourceElem, "keyup", this.sourceElemChanged, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      targetElem: $(options.target),
      eventType: options.eventType ? options.eventType : "click",
      appendValue: evalBoolean(options.appendValue),
      appendSeparator: options.appendSeparator || " ",
      forceSelection: evalBoolean(options.forceSelection)
    }.extend(options || {});

    // Autocomplete is unique in that we may have a progress icon
    // So, we need to hook in the resetProgressStyle function on an empty result
    var self = this;
    this.options.emptyFunction = function() {
      self.handleEmptyResult();
      if (options.emptyFunction) options.emptyFunction();
    }

    this.popupElem = "ajaxAutocompletePopup";
  },

  autocomplete: function() {
    var root = this.xml.documentElement;

    // clear contents of container (i.e., DIV tag)
    $(this.popupElem).innerHTML = "";

    var ul = document.createElement("ul");
    items = root.getElementsByTagName("item");
    for (var i=0; i<items.length; i++) {
      var name = items[i].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[i].getElementsByTagName("value")[0].firstChild.nodeValue;
      var li = document.createElement("li");
      var liIdAttr = document.createAttribute("id");
      li.setAttribute("id", value);
      var liText = document.createTextNode(name);
      li.appendChild(liText);
      ul.appendChild(li);
    }
    $(this.popupElem).appendChild(ul);
    $(this.popupElem).style.visibility = 'visible';

    this.setSelected();
  },

  sourceElemChanged: function(e) {
    var key = 0;
    if (e.keyCode) { key = e.keyCode; }
    else if (typeof(e.which)!= 'undefined') { key = e.which; }
    var fieldLength = $F(this.options.sourceElem).length;

    //up arrow
    if (key == 38) {
      if (this.currentIndex > 0) {
        this.suggestList[this.currentIndex].className = '';
        this.currentIndex--;
        this.suggestList[this.currentIndex].className = 'selected';
        this.suggestList[this.currentIndex].scrollIntoView(false);
      }

    //down arrow
    } else if (key == 40) {
      if(this.currentIndex < this.suggestList.length - 1) {
        this.suggestList[this.currentIndex].className = '';
        this.currentIndex++;
        this.suggestList[this.currentIndex].className = 'selected';
        this.suggestList[this.currentIndex].scrollIntoView(false);
      }

    //enter
    } else if (key == 13 && $(this.popupElem).style.visibility == 'visible') {
      this.fillField(this.suggestList[this.currentIndex]);
      Event.stop(e);
      this.executePostFunction();

    //escape
    } else if (key == 27) {
      this.hidePopup();
      Event.stop(e);

    } else {
      if (fieldLength < this.options.minimumCharacters) {
        this.hidePopup();
      } else if (key != 37 && key != 39 && key != 17 && key != 18) {
        this.resolveParameters();
        this.setAjaxOptions({
          parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
        });
        this.setProgressStyle();
        this.sendRequest();
      }
    }
  },

  fillField: function(selection) {
    this.options.sourceElem.value = decodeHtml(selection.innerHTML);
    if (this.options.appendValue == "false") {
      this.options.targetElem.value = selection.getAttribute("id");
    } else {
      if (this.options.targetElem.value.length > 0)
        this.options.targetElem.value += this.options.appendSeparator;
      this.options.targetElem.value += selection.getAttribute("id");
    }
    $(this.popupElem).style.visibility = 'hidden';
    this.hidePopup();
  },

  handleEmptyResult: function(e) {
    this.resetProgressStyle();
    if (this.options.forceSelection == "true") {
      // remove last character typed
      this.options.sourceElem.value = this.options.sourceElem.value.substr(0, this.options.sourceElem.value.length-1);
    } else {
      // simply hide the popup
      this.hidePopup();
    }
  },

  handlerFunction: function(xml) {
    this.xml = xml;
    this.resetProgressStyle();
    this.autocomplete();
  },

  executePostFunction: function() {
    if (this.options.postFunction) {
      this.options.postFunction(this.xml);
    }
  },

  createPopup: function() {
    new Insertion.Top(
      document.getElementsByTagName("body")[0],
        "<div id=\""+this.popupElem+"\" class=\""+this.options.className+"\"></div>");
  },

  hidePopup: function() {
    $(this.popupElem).style.visibility = 'hidden';
    $('layerCover').style.display = "none";
  },

  setProgressStyle: function() {
    if (this.options.progressStyle != null) {
      Element.addClassName(this.options.sourceElem, this.options.progressStyle);
    }
  },

  resetProgressStyle: function() {
    if (this.options.progressStyle != null) {
      Element.removeClassName(this.options.sourceElem, this.options.progressStyle);
    }
  },

  setSelected: function() {
    this.currentIndex = 0;
    this.suggestList = $(this.popupElem).getElementsByTagName("li");
    if ((this.suggestList.length > 1)
       || (this.suggestList.length == 1
           && this.suggestList[0].innerHTML != $F(this.options.sourceElem))) {
      this.setPopupStyles();
      for (var i = 0; i < this.suggestList.length; i++) {
        this.suggestList[i].index = i;
        this.addOptionHandlers(this.suggestList[i]);
      }
      this.suggestList[0].className = 'selected';
    } else {
      $(this.popupElem).style.visibility = 'hidden';
    }
    return null;
  },

  setPopupStyles: function() {
    var maxHeight;
    if (isIE) {
      maxHeight = 200;
    } else {
      maxHeight = window.outerHeight/3;
    }

    if ($(this.popupElem).offsetHeight < maxHeight) {
      $(this.popupElem).style.overflow = 'hidden';
    } else if (isMoz) {
      $(this.popupElem).style.maxHeight = maxHeight + 'px';
      $(this.popupElem).style.overflow = '-moz-scrollbars-vertical';
    } else {
      $(this.popupElem).style.height = maxHeight + 'px';
      $(this.popupElem).style.overflowY = 'auto';
    }
    $(this.popupElem).scrollTop = 0;
    $(this.popupElem).style.visibility = 'visible';

    $(this.popupElem).style.top = (getElementY(this.options.sourceElem)+this.options.sourceElem.offsetHeight+2) + "px";
    $(this.popupElem).style.left = getElementX(this.options.sourceElem) + "px";
    if (isIE) {
      $(this.popupElem).style.width = this.options.sourceElem.offsetWidth + "px";
    } else {
      $(this.popupElem).style.minWidth = this.options.sourceElem.offsetWidth + "px";
    }
    $(this.popupElem).style.overflow = "visible";

    // do iframe
    $('layerCover').style.top = (getElementY(this.options.sourceElem)+this.options.sourceElem.offsetHeight+2) + "px";
    $('layerCover').style.left = getElementX(this.options.sourceElem) + "px";
    $('layerCover').style.width = $(this.popupElem).offsetWidth;
    $('layerCover').style.height = $(this.popupElem).offsetHeight;
    $('layerCover').style.display = "block";
    $('layerCover').style.zIndex = 10;
    $(this.popupElem).style.zIndex = 20;
  },

  createIframe: function() {
    new Insertion.Before($(this.popupElem), "<iframe id='layerCover' style='display: none; position: absolute; top: 0; left: 0;' src='about:blank' frameborder='0' scrolling='no'></iframe>");
  },

  handleClick: function(e) {
    this.fillField(Event.element(e));
    this.executePostFunction();
  },

  handleOver: function(e) {
    this.suggestList[this.currentIndex].className = '';
    this.currentIndex = Event.element(e).index;
    this.suggestList[this.currentIndex].className = 'selected';
  },

  addOptionHandlers: function(option) {
    option.onclick = this.handleClick.bindAsEventListener(this);
    option.onmouseover = this.handleOver.bindAsEventListener(this);
  }

});


/* ---------------------------------------------------------------------- */
/* CALLOUT TAG
 */

AjaxJspTag.Callout = Class.create();
AjaxJspTag.Callout.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElementList,
                         this.options.eventType,
                         this.sourceElemClicked,
                         this);
    this.createContainer();
    this.targetElem = this.constructBox();
    this.activeElem = null;
  },

  setOptions: function(options) {
    var list;
    if (options.sourceClass) {
      list = document.getElementsByClassName(options.sourceClass);
    } else {
      list = new Array();
      list.push($(options.source));
    }
    this.options = {
      sourceElementList: list,
      classNamePrefix: options.classNamePrefix ? options.classNamePrefix : "callout",
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});

    if (options.timeout) {
      if (Number(options.timeout) > 250)
        this.options.timeout = Number(options.timeout)
      else
        this.options.timeout = 250;
    }

    if (options.title) {
      this.options.useTitleBar = "true";
    } else if (options.useTitleBar) {
      this.options.useTitleBar = evalBoolean(options.useTitleBar);
    } else {
      this.options.useTitleBar = "false";
    }

    if (!options.boxPosition) this.options.boxPosition = "top right";
    this.calloutContainer = "calloutContainer";
    this.calloutParameter = AJAX_DEFAULT_PARAMETER;
  },

  callout: function(xml) {
    var root = xml.documentElement;

    var items = root.getElementsByTagName("item");
    if (items.length > 0) {
      var name = items[0].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[0].getElementsByTagName("value")[0].firstChild.nodeValue;
      // fill text (if present)
      if (this.options.useTitleBar == "true") {
        if (!this.options.title) {
          this.targetElem.childNodes[1].innerHTML = name;
        } else {
          this.targetElem.childNodes[1].innerHTML = this.options.title;
        }
        this.targetElem.childNodes[2].innerHTML = value;
      } else {
        this.targetElem.childNodes[1].innerHTML = value;
      }

      // size box
      if (isIE) {
        this.targetElem.style.width = "250px";
      } else {
        this.targetElem.style.minWidth = "250px";
      }
      this.targetElem.style.overflow = "visible";

      // bring box to front
      this.targetElem.style.display = "block";
      this.targetElem.style.visibility = "visible";

      // move box to new location
      this.moveBox(this.activeElem, this.targetElem);

      // hook events
      this.targetElem.childNodes[0].onclick = this.handleCloseClick.bindAsEventListener(this);
      window.onclick = this.checkBoxPosition.bindAsEventListener(this); // when clicked outside callout
      if (this.options.timeout) {
        if (this.timer)
          clearTimeout(this.timer);
        this.timer = setTimeout(this.handleHover.bind(this), this.options.timeout);
      }
    }
  },

  sourceElemClicked: function(e) {
    // replace unique parameter 'ajaxCallout' with inner content of containing element
    // ONLY IF we are using the class as the identifier; otherwise, treat it like a field
    this.activeElem = Event.element(e);
    this.resolveParameters();
    var ajaxParameters = this.options.parameters || '';
    var re = new RegExp("(\\{"+this.calloutParameter+"\\})", 'g');
    ajaxParameters = replaceWithValue(ajaxParameters, re, this.activeElem);
    // set the rest of the parameters generically
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(ajaxParameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.callout(xml);
    if (this.options.postFunction) {
      this.options.postFunction(this.activeElem);
    }
  },

  createContainer: function() {
    new Insertion.Top(
      document.getElementsByTagName("body")[0],
        "<div id=\""+this.calloutContainer+"\" style=\"position: absolute; top: 0; left: 0\"></div>");
  },

  constructBox: function() {
    // create base
    var eBox = document.createElement("div");
    eBox.className = this.options.classNamePrefix+"Box";
    eBox.setAttribute("style", "position: absolute; top: 0; left: 0");
    document.documentElement.appendChild(eBox);

    // add elements
    var eClose = document.createElement("div");
    eClose.className = this.options.classNamePrefix+"Close";
    eClose.appendChild(document.createTextNode("X"));
    eBox.appendChild(eClose);

    if (this.options.useTitleBar == "true") {
      var eTitle = document.createElement("div");
      eTitle.className = this.options.classNamePrefix+"Title";
      eBox.appendChild(eTitle);
    }

    var eContent = document.createElement("div");
    eContent.className = this.options.classNamePrefix+"Content";
    eBox.appendChild(eContent);

    eBox.style.display = "none";
    $(this.calloutContainer).appendChild(eBox);
    return eBox;
  },

  moveBox: function(anchor, box) {
    box.style.position = "absolute";
    var posXY = Position.cumulativeOffset(anchor);

    if (this.options.boxPosition.indexOf("top") >= 0) {
      box.style.top = (posXY[1] - (box.offsetHeight) - 10) + "px";
    } else {
      box.style.top = (posXY[1] + (anchor.offsetHeight) + 10) + "px";
    }
    if (this.options.boxPosition.indexOf("right") >= 0) {
      box.style.left = (posXY[0] + 10) + "px";
    } else {
      box.style.left = (posXY[0] - (box.offsetWidth) - 10) + "px";
    }

    // Check for off-screen position
    if (box.offsetLeft < 0) {
      box.style.left = 0;
    }
    if (box.offsetTop < 0) {
      box.style.top = 0;
    }
  },

  handleCloseClick: function(e) {
    clearTimeout(this.timer);
    if (window.captureEvents) {
      window.releaseEvents(Event.MOUSEMOVE);
    }
    window.onmousemove = null;
    this.targetElem.style.display = "none";
  },

  checkBoxPosition: function(e) {
    var outsideContainer = false;
    var outsideSource = false;

    // evaluate if cursor is over box
    var clickX = e.clientX; // cursor X position
    var clickY = e.clientY; // cursor Y position
    var boundX1 = this.targetElem.offsetLeft;
    var boundX2 = boundX1 + this.targetElem.offsetWidth;
    var boundY1 = this.targetElem.offsetTop;
    var boundY2 = boundY1 + this.targetElem.offsetHeight;
    if (clickX < boundX1 || clickX > boundX2 || clickY < boundY1 || clickY > boundY2) {
      outsideContainer = true;
    }

    // evaluate if cursor is over source
    boundX1 = this.activeElem.offsetLeft;
    boundX2 = boundX1 + this.activeElem.offsetWidth;
    boundY1 = this.activeElem.offsetTop;
    boundY2 = boundY1 + this.activeElem.offsetHeight;

    if (clickX < boundX1 || clickX > boundX2 || clickY < boundY1 || clickY > boundY2) {
      outsideSource = true;
    }
    if (outsideContainer && outsideSource) {
      this.handleCloseClick();
    }
  },

  handleHover: function(e) {
    if (window.captureEvents) {
      window.captureEvents(Event.MOUSEMOVE);
    }
    window.onmousemove = this.checkBoxPosition.bindAsEventListener(this);
  }

});


/* ---------------------------------------------------------------------- */
/* HTML CONTENT TAG
 */

AjaxJspTag.HtmlContent = Class.create();
AjaxJspTag.HtmlContent.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElementList,
                         this.options.eventType,
                         this.sourceElemClicked,
                         this);
  },

  setOptions: function(options) {
    var list;
    if (options.sourceClass) {
      list = document.getElementsByClassName(options.sourceClass);
    } else {
      list = new Array();
      list.push($(options.source));
    }
    this.options = {
      sourceElementList: list,
      targetElem: $(options.target),
      contentXmlName: options.contentXmlName ? options.contentXmlName : "ajaxContent",
      eventType: options.eventType ? options.eventType : "click",
      ajaxMethod: AJAX_METHOD_UPDATER
    }.extend(options || {});

    this.contentParameter = AJAX_DEFAULT_PARAMETER;
  },

  sourceElemClicked: function(e) {
    this.resolveParameters();

    var ajaxParameters = this.options.parameters || '';
    if (this.options.sourceClass) {
      this.activeElem = Event.element(e);
      var re = new RegExp("(\\{"+this.contentParameter+"\\})", 'g');
      ajaxParameters = replaceWithValue(ajaxParameters, re, this.activeElem);
    }
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(ajaxParameters).split(','))
    });

    this.sendUpdateRequest(this.options.target);
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* TAB PANEL TAG
 */

AjaxJspTag.TabPanel = Class.create();
AjaxJspTag.TabPanel.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.execute();
  },

  setOptions: function(options) {
    this.options = {
      ajaxMethod: AJAX_METHOD_UPDATER
    }.extend(options || {});
  },

  execute: function() {
    this.resolveParameters();

    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });

    this.sendUpdateRequest(this.options.target);

    $(this.options.currentStyleId).id = '';
    this.options.source.id = this.options.currentStyleId;
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* PORTLET TAG
 */

AjaxJspTag.Portlet = Class.create();
AjaxJspTag.Portlet.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    if (this.options.executeOnLoad == "true") {
      this.execute();
    }
    if (this.preserveState) this.checkCookie();

    // Attach events to icons if defined
    if (this.options.imageClose) {
      this.attachBehaviors(this.options.closeElement, "click", this.closePortlet, this);
    }
    if (this.options.imageRefresh) {
      this.attachBehaviors(this.options.refreshElement, "click", this.refreshPortlet, this);
    }
    if (this.options.imageMaximize && this.options.imageMinimize) {
      this.attachBehaviors(this.options.toggleElement, "click", this.togglePortlet, this);
    }
  },

  checkCookie: function() {
    // Check cookie for save state
    var cVal = getCookie("AjaxJspTag.Portlet."+this.options.source);
    if (cVal != null) {
      if (cVal == AJAX_PORTLET_MIN) {
        this.togglePortlet();
      } else if (cVal == AJAX_PORTLET_CLOSE) {
        this.closePortlet();
      }
    }
  },

  setOptions: function(options) {
    this.options = {
      ajaxMethod: AJAX_METHOD_UPDATER,
      targetElement: options.classNamePrefix+"Content",
      closeElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Close"))[0],
      refreshElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Refresh"))[0],
      toggleElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Size"))[0],
      executeOnLoad: evalBoolean(options.executeOnLoad, true),
      preserveState: evalBoolean(options.preserveState),
      expireDays: options.expireDays || "0",
      expireHours: options.expireHours || "0",
      expireMinutes: options.expireMinutes || "0",
      isMaximized: true
    }.extend(options || {});

    if (parseInt(this.options.expireDays) > 0
        || parseInt(this.options.expireHours) > 0
        || parseInt(this.options.expireMinutes) > 0) {
      this.preserveState = true;
      this.options.expireDate = getExpDate(
        parseInt(this.options.expireDays),
        parseInt(this.options.expireHours),
        parseInt(this.options.expireMinutes));
    }

    this.autoRefreshSet = false;
  },

  execute: function() {
    this.resolveParameters();

    this.setAjaxOptions({
      frequency: this.options.refreshPeriod ? (this.options.refreshPeriod) : null,
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });

    if (this.options.refreshPeriod && this.autoRefreshSet == false) {
      this.sendPeriodicalUpdateRequest(
          getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
      this.autoRefreshSet = true;
    } else {
      this.sendUpdateRequest(
          getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
    }
  },

  stopAutoRefresh: function() {
    // stop auto-update if present
    if (this.ajaxPeriodicalUpdater != null
        && this.options.refreshPeriod
        && this.autoRefreshSet == true) {
      this.ajaxPeriodicalUpdater.stop();
    }
  },

  startAutoRefresh: function() {
    // stop auto-update if present
    if (this.ajaxPeriodicalUpdater != null && this.options.refreshPeriod) {
      this.ajaxPeriodicalUpdater.start();
    }
  },

  refreshPortlet: function(e) {
    // clear existing updater
    this.stopAutoRefresh();
    if (this.ajaxPeriodicalUpdater != null) {
      this.startAutoRefresh();
    } else {
      this.execute();
    }
  },

  closePortlet: function(e) {
    this.stopAutoRefresh();
    Element.remove(this.options.source);
    // Save state in cookie
    if (this.preserveState) {
      setCookie("AjaxJspTag.Portlet."+this.options.source,
        AJAX_PORTLET_CLOSE,
        this.options.expireDate);
    }
  },

  togglePortlet: function(e) {
    Element.toggle(getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
    var image = this.options.toggleElement;
    if (this.options.isMaximized) {
      image.src = this.options.imageMaximize;
      this.stopAutoRefresh();
    } else {
      image.src = this.options.imageMinimize;
      this.startAutoRefresh();
    }
    this.options.isMaximized = !this.options.isMaximized;
    // Save state in cookie
    if (this.preserveState) {
      setCookie("AjaxJspTag.Portlet."+this.options.source,
        (this.options.isMaximized == true ? AJAX_PORTLET_MAX : AJAX_PORTLET_MIN),
        this.options.expireDate);
    }
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});
