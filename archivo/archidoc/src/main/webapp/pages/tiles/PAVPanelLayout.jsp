<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>

<div id="panel" style="position:relative;height:100%">
<div id="rightPanel" style="position:absolute;top:0px;left:0px;width:100%;height:100%;padding-left:262px;padding-right:16px;-moz-box-sizing: border-box">
	<div style="width:100%;height:100%;overflow:auto">
	<tiles:insert attribute="rightPanelContent" ignore="true" />
	</div>
</div>

<div id="leftPanel" style="position:relative;left:12px;float:left;width:240px;height:100%;margin:0px">
	<div id="leftContentHider" style="position:absolute;height:100%;width:100%;background-color:#0000ff;opacity:0;FILTER:Alpha( opacity=0);display:none;z-index:10"></div>
	<tiles:insert attribute="leftPanelContent" ignore="true" />
</div>
<div style="position:relative;left:12px;float:left;height:100%;width:8px">
<table border="0" height="100%" cellpadding="0" cellspacing="0"><tr><td valign="middle"><img id="resizer" src="../images/resizer.gif"></td></tr></table>
</div>

</div>

<script>
	xAddEventListener('resizer', 'mousedown', startTreePanelResizing, true);
	function startTreePanelResizing(oEvent) {
		var evt = new xEvent(oEvent);
		  xPreventDefault(oEvent);
		  xGetElementById('leftContentHider').style.display = "block";
		  mouseX = evt.pageX;
		  xAddEventListener(document, 'mousemove', resizingTreePanel, true);
		  xAddEventListener(document, 'mouseup', endTreePanelResizing, true);
		  MAX_LEFT_WIDTH = xWidth('rightPanel') - 80;
		  dragging = 1;
	}
	function resizingTreePanel(oEvent) {
		xStopPropagation(oEvent)
		xPreventDefault(oEvent);
		var evt = new xEvent(oEvent);
		if (dragging) {
		  var dx = evt.pageX - mouseX;
		  var newLeftWidth = xWidth('leftPanel')+dx;
		  //var newRightWidth = xWidth('rightPanel')-dx;
		  if (newLeftWidth > 80 && newLeftWidth < MAX_LEFT_WIDTH) {
			xWidth('leftPanel', newLeftWidth);
			xGetElementById('rightPanel').style.paddingLeft = newLeftWidth + 22;
		    mouseX = evt.pageX;
		  }
		}
	}
	function stopDragging() {
		  mouseX = dragging = 0;
		  xRemoveEventListener(document, 'mouseup', endTreePanelResizing, true);
		  xRemoveEventListener(document, 'mousemove', resizingTreePanel, true);
		  xGetElementById('leftContentHider').style.display = "none";
	}
	function endTreePanelResizing(oEvent) {
		if (dragging) {
		  xPreventDefault(oEvent);
		  stopDragging();
		}
	}
	var dragging = 0;
	var mouseX = 0;
	var MAX_LEFT_WIDTH = 0;
</script>