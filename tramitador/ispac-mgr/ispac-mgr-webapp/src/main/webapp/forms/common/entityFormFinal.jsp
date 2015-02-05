<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

									</td>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
								</tr>
								<tr>
									<td height="5px" colspan="3"><img src='<ispac:rewrite href="img/pixel.gif"/>' height="5px"/></td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
		
	</table>
	
</html:form>

<%-- Manejador de block para resituarse en la pestaña en la que nos encontrabamos --%>
<tiles:insert template="/forms/common/manageBlock.jsp"/>

<%-- Para informar si se intenta salir del formulario sin guardar --%>
<tiles:insert template="/forms/common/observer.jsp"/>
