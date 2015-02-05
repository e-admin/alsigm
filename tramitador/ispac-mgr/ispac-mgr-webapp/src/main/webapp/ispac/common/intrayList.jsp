<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

	<table border="0" width="100%">
		<tr>
			<td align="right">
				<ispac:onlinehelp tipoObj="7" image="img/help.gif" titleKey="header.help"/>
			</td>
		</tr>
	</table>

	<table cellpadding="5" cellspacing="0" border="0" width="100%" align="center">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
					<tr>
						<td class="title" height="18px">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
									<td width="100%" class="menuhead">
										<bean:message key="intrays.titulo" />
									</td>
									<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="blank">

							<display:table name="IntraysList" 
										   id="intray" 
							  			   requestURI="/showIntrayList.do"
										   export="true"
								   		   class="tableDisplay"
										   sort="list"
										   pagesize="15"
										   defaultorder="ascending"
										   defaultsort="0">

							  	<display:column titleKey="formatter.intray.numreg"
							  					media="html" 
							  					sortable="true"
							  					sortProperty="registerNumber"
							  					headerClass="sortable">
							  		<html:link 	action="showIntray.do" 
							  					paramId="id" 
							  					paramName="intray"
							  					paramProperty="id"
							  					styleClass="displayLink">
		    							<bean:write name="intray" property="registerNumber" />
							  		</html:link>
							  	</display:column>

							  	<display:column titleKey="formatter.intray.numreg"
							  					property="registerNumber"
							  					media="csv excel xml pdf"/>

							  	<display:column titleKey="formatter.intray.fechareg"
							  					sortable="true"
							  					sortProperty="date"
							  					comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparator"
							  					headerClass="sortable">
							  		<bean:write name="intray" property="registerDate" format="dd/MM/yyyy"/>
							  	</display:column>

							  	<display:column titleKey="formatter.intray.tipoAsunto"
							  					sortable="true"
							  					property="matterTypeName"
							  					sortProperty="matterTypeName"
							  					headerClass="sortable"/>

							  	<display:column titleKey="formatter.intray.resumen"
							  					sortable="true"
							  					property="matter"
							  					sortProperty="matter"
							  					headerClass="sortable"/>

							  	<display:column titleKey="formatter.intray.destino"
							  					sortable="true"
							  					property="destination"
							  					sortProperty="destination"
							  					headerClass="sortable"/>
													
							  	<display:column titleKey="formatter.intray.estado"
							  					sortable="true"
							  					headerClass="sortable">
									<logic:equal name="intray" property="state" value="1">
										<bean:message key="intray.estado.pendiente"/>
									</logic:equal>
									<logic:equal name="intray" property="state" value="2">
										<bean:message key="intray.estado.aceptado"/>
									</logic:equal>
									<logic:equal name="intray" property="state" value="3">
										<bean:message key="intray.estado.archivado"/>
									</logic:equal>
									<logic:equal name="intray" property="state" value="4">
										<bean:message key="intray.estado.devuelto"/>
									</logic:equal>
								</display:column>

							  	<display:column titleKey="formatter.intray.mensaje"
							  					sortable="true"
							  					property="message"
							  					sortProperty="message"
							  					headerClass="sortable"/>

							  	<display:column titleKey="formatter.intray.fechaDistribucion"
							  					sortable="true"
							  					sortProperty="date"
							  					comparator="ieci.tdw.ispac.ispacweb.comparators.DateComparator"
							  					headerClass="sortable">
							  		<bean:write name="intray" property="date" format="dd/MM/yyyy"/>
							  	</display:column>

							</display:table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
