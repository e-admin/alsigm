<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url);
	}
</script>

<div id="navmenutitle">
	<bean:message key="title.proceduretree" />
</div>
<div id="navSubMenuTitle">
</div>
<div id="navmenu">
	<ispac:hasFunction functions="FUNC_INV_PROCEDURES_EDIT">
	<ul class="actionsMenuList">
		 <li>
       		<ispac:linkframe id="CREATENEWPCD"
					 styleClass=""
				     target="workframerefresh"
					 action="newProcedure.do?action=begin&pcdtype=new"
					 titleKey="catalog.createnewpcd"
					 
					 width="640"
					 height="480"
					 showFrame="true">	
			</ispac:linkframe>
       	</li>
		<li>
       		<ispac:linkframe id="IMPORTPCD"
					 styleClass=""
				     target="workframerefresh"
					 action="importProcedure.do?method=enter"
					 titleKey="catalog.importpcd"
					 width="640"
					 height="480"
					 showFrame="true">	
			</ispac:linkframe>
       	</li>
	</ul>
	</ispac:hasFunction>
</div>


<div class="divTree">
	<ispac:listTree nameList="CTProceduresList" nameFormatter="CTProceduresListFormatter" styleButton="form_button_white" selectedId="idProc"/>
</div>
<br/>
