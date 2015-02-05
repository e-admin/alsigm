<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<div class="submenu2">
<ul>
<c:set var="isFirst" value="true" />
<c:set var="imageOn" value="on" />
<c:set var="imageOff" value="off" />

  <logic:equal value="true" scope="session" name="isSuperuser">
    <logic:equal parameter="pestana" value="oficinas">
      <li class="submen1on">

        <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
        <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficinas"/>
        <img src="img/subme2_on_0_bis.gif" />
      </li>
    </logic:equal>
    <logic:notEqual parameter="pestana" value="oficinas">
      <li class="submen1off">
        <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
        <a href="<html:rewrite page='/listadoOficinas.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficinas"/></a>
        <img src="img/subme2_of_0.gif" />
      </li>
    </logic:notEqual>
    <c:if test="${isFirst=='true'}">
      <c:set var="imageOn" value="of_on" />
      <c:set var="imageOff" value="of_of" />
      <c:set var="isFirst" value="false" />
    </c:if>
  </logic:equal>
  <logic:equal value="true" scope="session" name="GenPermsUser"  property="canModifyUsers">
    <logic:equal parameter="pestana" value="usuarios">
      <li class="submen1on">
        <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
        <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuarios"/>
        <img src="img/subme2_on_0_bis.gif" />
      </li>
    </logic:equal>
    <logic:notEqual parameter="pestana" value="usuarios">
      <li class="submen1off">
        <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
        <a href="<html:rewrite page='/listadoUsuarios.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.usuarios"/></a>
        <img src="img/subme2_of_0.gif" />
      </li>
    </logic:notEqual>
   <c:if test="${isFirst=='true'}">
      <c:set var="imageOn" value="of_on" />
      <c:set var="imageOff" value="of_of" />
      <c:set var="isFirst" value="false" />
    </c:if>
  </logic:equal>
   <logic:equal value="true" scope="session" name="isSuperuser">
	  <logic:equal parameter="pestana" value="libros">
	    <li class="submen1on">
	      <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
	      <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.libros"/>
	      <img src="img/subme2_on_0_bis.gif" />
	    </li>
	  </logic:equal>
	  <logic:notEqual parameter="pestana" value="libros">
	    <li class="submen1off">
	      <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
	      <a href="<html:rewrite page='/listadoLibros.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.libros"/></a>
	      <img src="img/subme2_of_0.gif" />
	    </li>
	  </logic:notEqual>
		<c:if test="${isFirst=='true'}">
	      <c:set var="imageOn" value="of_on" />
	      <c:set var="imageOff" value="of_of" />
	      <c:set var="isFirst" value="false" />
	    </c:if>
	</logic:equal>
	<logic:equal value="true" scope="session" name="GenPermsUser"  property="canModifyAdminUnits">
		  <logic:equal parameter="pestana" value="unidades">
		    <li class="submen1on">
		      <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		      <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.unidades"/>
		      <img src="img/subme2_on_0_bis.gif" />
		    </li>
		  </logic:equal>
		  <logic:notEqual parameter="pestana" value="unidades">
		    <li class="submen1off">
		      <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		      <a href="<html:rewrite page='/listadoUnidades.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.unidades"/></a>
		      <img src="img/subme2_of_0.gif" />
		    </li>
		  </logic:notEqual>
		  <c:if test="${isFirst=='true'}">
		      <c:set var="imageOn" value="of_on" />
		      <c:set var="imageOff" value="of_of" />
		      <c:set var="isFirst" value="false" />
	    </c:if>
	</logic:equal>
	<logic:equal value="true" scope="session" name="GenPermsUser"  property="canModifyIssueTypes">
		  <logic:equal parameter="pestana" value="asuntos">
		    <li class="submen1on">
		      <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		      <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asuntos"/>
		      <img src="img/subme2_on_0_bis.gif" />
		    </li>
		  </logic:equal>
		  <logic:notEqual parameter="pestana" value="asuntos">
		    <li class="submen1off">
		      <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		      <a href="<html:rewrite page='/listadoAsuntos.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asuntos"/></a>
		      <img src="img/subme2_of_0.gif" />
		    </li>
		  </logic:notEqual>
			<c:if test="${isFirst=='true'}">
			      <c:set var="imageOn" value="of_on" />
			      <c:set var="imageOff" value="of_of" />
			      <c:set var="isFirst" value="false" />
			 </c:if>
	</logic:equal>
	<logic:equal value="true" scope="session" name="GenPermsUser"  property="canModifyReports">
		  <logic:equal parameter="pestana" value="informes">
		    <li class="submen1on">
		      <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		      <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.informes"/>
		      <img src="img/subme2_on_0_bis.gif" />
		    </li>
		  </logic:equal>
		  <logic:notEqual parameter="pestana" value="informes">
		    <li class="submen1off">
		      <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		      <a href="<html:rewrite page='/listadoInformes.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.informes"/></a>
		      <img src="img/subme2_of_0.gif" />
		    </li>
		  </logic:notEqual>
		  <c:if test="${isFirst=='true'}">
			    <c:set var="imageOn" value="of_on" />
			    <c:set var="imageOff" value="of_of" />
			    <c:set var="isFirst" value="false" />
		  </c:if>
	</logic:equal>
	<logic:equal value="true" scope="session" name="GenPermsUser"  property="canModifyTransportTypes">
		  <logic:equal parameter="pestana" value="transportes">
		    <li class="submen1on">
		      <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		      <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.transportes"/>
		      <img src="img/subme2_on_0_bis.gif" />
		    </li>
		  </logic:equal>
		  <logic:notEqual parameter="pestana" value="transportes">
		    <li class="submen1off">
		      <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		      <a href="<html:rewrite page='/listadoTransportes.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.transportes"/></a>
		      <img src="img/subme2_of_0.gif" />
		    </li>
		  </logic:notEqual>
	</logic:equal>

	<logic:equal value="true" scope="session" name="enabledIntercambioRegistral">
		<logic:equal value="true" scope="session" name="isSuperuser">
		    <logic:equal parameter="pestana" value="configuracionDCO">
		      <li class="submen1on">
		        <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		        <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionDCO"/>
		        <img src="img/subme2_on_0_bis.gif" />
		      </li>
		    </logic:equal>
		    <logic:notEqual parameter="pestana" value="configuracionDCO">
		      <li class="submen1off">
		        <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		        <a href="<html:rewrite page='/configuracionDCO.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionDCO"/></a>
		        <img src="img/subme2_of_0.gif" />
		      </li>
		    </logic:notEqual>
		</logic:equal>
	</logic:equal>


	<logic:equal value="true" scope="session" name="isLdapAuthenticationPolicy">
		<logic:equal value="true" scope="session" name="isSuperuser">
		    <logic:equal parameter="pestana" value="configuracionLdap">
		      <li class="submen1on">
		        <img src="img/subme2_<c:out value="${imageOn}" />.gif" />
		        <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionLdap"/>
		        <img src="img/subme2_on_0_bis.gif" />
		      </li>
		    </logic:equal>
		    <logic:notEqual parameter="pestana" value="configuracionLdap">
		      <li class="submen1off">
		        <img src="img/subme2_<c:out value="${imageOff}" />.gif" />
		        <a href="<html:rewrite page='/editarConfiguracionLdap.do'/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionLdap"/></a>
		        <img src="img/subme2_of_0.gif" />
		      </li>
		    </logic:notEqual>
		</logic:equal>
	</logic:equal>

</ul>
</div>
