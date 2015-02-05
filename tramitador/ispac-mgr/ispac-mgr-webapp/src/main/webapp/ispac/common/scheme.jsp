<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<bean:define id="urlExp" name="urlExp" type ="java.lang.String"/>
<table cellpadding="2" cellspacing="0" border="0" width="100%">
  <tr>
  	<td>
			<!-- datos de información-->
			<jsp:include page="./expInfo.jsp" />
  	 	<!-- datos de información-->
  	</td>
  </tr>
  <tr>
		<td>
			<!-- Panel del esquema-->
	  	<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
				<tr>
		  		<!-- Titulo del expediente, p ej: Expediente EXP/199 -->
		  		<td class="title" height="18px" width="100%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
			  			<tr>
								<td height="18px" width="20px" align="center"><img src='<ispac:rewrite href="img/folder.gif"/>' border="0" height="16px" width="16px"/></td>
								<td class="menuhead">
									<html:link styleClass="menuhead" action='<%= urlExp %>' >
              			<bean:message key="scheme.title"/> <bean:write name="Expedient" property="property(NUMEXP)"/>
            			</html:link>
                </td>
			  			</tr>
						</table>
		  		</td>
				</tr>
				<tr>
		  		<td class="blank">
		    		<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<logic:iterate id="entity" name="SchemeList">
								<bean:define id="entityId" name="entity" property="property(ID)"/>
								<bean:size id="elements" name="entity" property="regs"/>
								<!-- Cuando la entidad no sea expediente -->
								<c:if test="${entityId != '1'}">
			  					<!-- panel -->
			  						<bean:define name="entity" property="imgOn" id="entityImgOn" type="java.lang.String"/>
			  						<bean:define name="entity" property="imgOff" id="entityImgOff" type="java.lang.String"/>
      							<tr>
        							<td width="15px" align="center">
        							
        							
        							
        							
                				<img id='imgsch<bean:write name="entityId"/>' src='<ispac:rewrite href='<%= entityImgOn %>'/>' 
                					width="10px" height="11px" border="0" style="cursor:pointer" 
                					onclick="javascript:hide_expand(<bean:write name="entityId"/>, '<%= entityImgOn %>', '<%= entityImgOff %>');"/></td>
        							<td class="schema">
										<c:url value="${urlExp}" var="link">
											<c:param name="entity" value="${entityId}"/>
											<c:if test="${entityId == 2}">
												<c:param name="appId" value="15"/>
											</c:if>
										</c:url>
										<a href='<c:out value="${link}"/>' class="menu"><bean:write name="entity" property="property(DESCRIPCION)"/></a>
        							</td>
  									</tr>
  									<tr>
        							<td colspan="2">
        								<div style="display:block" id="blocksch<bean:write name="entityId"/>">
        									<table border="0" width="100%" cellpadding="1" cellspacing="1">
	              						<logic:iterate id="element" name="entity" property="regs">
	              							<bean:define name="element" property="property(SCHEME_ID)" id="key"/>
	              							<bean:define name="element" property="img" id="elementImg" type="java.lang.String"/>
	              							<bean:define name="element" property="url" id="elementUrl" type="java.lang.String"/>
					                    	<tr>
												<td width="3px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="3px"/></td>
												<td width="3px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="3px"/></td>
								                <td width="3px"><img src='<ispac:rewrite href="img/pixel.gif"/>' width="3px"/></td>
								                <td width="16px" height="16px">
			                  						<img src='<ispac:rewrite href='<%= elementImg %>'/>'/>
			                  					</td>
								           <%--     
								                <c:choose>
								                	<c:when test="${param.entity == entityId && param.key == key}">
														<td height="16px" class="menuSelect">
	              											<bean:write name="element" property="property(SCHEME_EXPR)"/>
	              										</td>
	                  								</c:when>
	                  								<c:otherwise>
	              										<td height="16px" class="menu">
	              											<html:link styleClass="menu" action='<%= elementUrl %>' name="element" property="params">
		                  										<bean:write name="element" property="property(SCHEME_EXPR)"/>
		                  									</html:link>
	                  									</td>
		                  							</c:otherwise>
		                  						</c:choose>
		                  					--%>
		                  						<td height="16px" class="menu">
	       											<html:link styleClass="menu" action='<%= elementUrl %>' name="element" property="params">
														<c:choose>
			       											<c:when test="${param.entity == entityId && param.key == key}">
			            										<b><bean:write name="element" property="property(SCHEME_EXPR)"/></b>
			       											</c:when>
			                  								<c:otherwise>
			            										<bean:write name="element" property="property(SCHEME_EXPR)"/>
			                  								</c:otherwise>
														</c:choose>
	            									</html:link>
	            								</td>
											</tr>
										</logic:iterate>
													</table>
												</div>
											</td>
										</tr>
									<!-- fin panel -->
	              </c:if>
              </logic:iterate>
						</table>
		  		</td>
				</tr>
	  	</table>
	  	<!-- fin panel del esquema -->
		</td>
  </tr>
</table>
