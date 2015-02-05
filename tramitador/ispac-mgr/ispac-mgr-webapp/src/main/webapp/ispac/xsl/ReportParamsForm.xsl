<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet 
	version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xalan/java"
	exclude-result-prefixes="java"
>

<!-- Recursos del idioma en el que se visualiza el formulario -->
<xsl:param name="resourceBundle"></xsl:param>
<xsl:param name="mapParams"></xsl:param>
<xsl:param name="filasColumnas"></xsl:param>


<xsl:param name="propertiesHelper"></xsl:param>
<xsl:param name="request"></xsl:param>
<xsl:param name="parameterUtils"></xsl:param>
<xsl:param name="userUID"></xsl:param>
<xsl:param name="userName"></xsl:param>
<xsl:param name="deptUID"></xsl:param>
<xsl:param name="deptName"></xsl:param>


<!--
===========
OC4J
===========                        
xmlns:java="http://www.oracle.com/XSL/Transform/java/ieci.tdw.ispac.ispacmgr.search.ValidationTable"
-->

<xsl:template match="/">

<table cellpadding="1" cellspacing="0" border="0" width="99%"> 
	<tr>
		<td>
		
			<table  width="100%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td height="5px" colspan="3"> </td>
				</tr>
				<tr>
					<td width="8px"></td>
					<td width="100%">

						<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0"> <!-- 8 -->
							<tr>
								<td height="10px"> </td>
							</tr>
							<tr>
								<td align="center">
                                            	
                                   	<table width="90%" border="0">

										<xsl:for-each select="reportform/fields/field">
										
											<xsl:sort select="@order" order="ascending"/>
											<xsl:apply-templates select="." />
											
										</xsl:for-each>
                                            		
                                        <tr>
                                        	<td><br/></td>
                                        </tr>
                                        <tr>
                                        	<td colspan="4" align="right">
                                        	 <xsl:if test="$filasColumnas != 1">
                                   				<input type="button" class="form_button" onclick="javascript:submitform();">
                                   					<xsl:attribute name="value"><xsl:value-of select="java:getString($resourceBundle, 'forms.button.generate')" /></xsl:attribute > 
                                   				</input>
                                   			</xsl:if>
                                   			<xsl:if test="$filasColumnas = 1">
                                   				<input type="button" class="form_button" onclick="javascript:submitformFilasColumnas();">
                                   					<xsl:attribute name="value"><xsl:value-of select="java:getString($resourceBundle, 'forms.button.generate')" /></xsl:attribute > 
                                   				</input>
                                   			</xsl:if>
                                   					
                                   			
                                   			</td>
                                   		</tr>
                                   		
                                	</table>
	
								</td>
							</tr>
							<tr>
								<td height="10px"> </td>
							</tr>
						</table>
						
					</td>
					<td width="8px"> </td>
				</tr>
				<tr>
					<td height="5px" colspan="3"> </td>
				</tr>
			</table>
			
		</td>
	</tr>
</table>

</xsl:template>

<xsl:template match="field">
	<tr>
	
		<xsl:apply-templates select="description" />
		<td width="15px" valign="top"></td>
		
		<xsl:choose>

			 <xsl:when test="controltype[.='text']">
			 
				<td>

		  			<xsl:choose>
		  			
			      		<xsl:when test="controltype/@tipo[.='validate']">
			      		
	                        <!-- Nombre de la tabla de validacion-->
	                        <xsl:variable name="table"><xsl:value-of select="controltype/@table"/></xsl:variable>
	                        <!-- Llamada al constructor de la clase que generara el control con los valores de la tabla de validacion -->
	                        <!--
	                        ===========
	                        OC4J
	                        ===========                        
	                        <xsl:variable name="valid" select="java:new($table)"/>
	                        -->
	                        <xsl:variable name="valid" select="java:ieci.tdw.ispac.ispacmgr.search.ValidationTable.new($table)"/>
	                        
	                        <!-- Nombre del campo de la tabla a mostrar como etiqueta -->
	                        <xsl:variable name="label"><xsl:value-of select="controltype/@label"/></xsl:variable>
	                        <!-- Nombre del campo de la tabla a utilizar como valor -->
	                        <xsl:variable name="value"><xsl:value-of select="controltype/@value"/></xsl:variable>
	                        <!-- Nombre del control HTML -->
	                        <xsl:variable name="field"><xsl:value-of select="controltype/@field"/></xsl:variable>
	                        <!-- Clausula where de la consulta a realizar -->
	                        <xsl:variable name="clause"><xsl:value-of select="controltype/@clause"/></xsl:variable>
							<!-- Si queremos ordenar por algun campo -->
	                        <xsl:variable name="orderBy"><xsl:value-of select="controltype/@orderBy"/></xsl:variable>
	                        <!-- Indica si se selecciona un valor por defecto -->
	                        <xsl:variable name="defaultvalue"><xsl:value-of select="controltype/@defaultvalue"/></xsl:variable>
							 <!-- Indica si se al seleccionar una opcion se ha de recargar el formulario -->
							<xsl:variable name="reload"><xsl:value-of select="controltype/@reload"/></xsl:variable>
							
	                        <!-- Llamada al metodo que renderiza el control HTML.-->
							<xsl:value-of select="java:format($valid, $field, $label, $value, $clause, $defaultvalue, $reload , $orderBy)" disable-output-escaping="yes"/>
			      		</xsl:when>
				  
						<xsl:otherwise>                
	
	      					<xsl:variable name="type">
		      					<xsl:choose>
		      						<xsl:when test="controltype/@visible[.='false']">hidden</xsl:when>
		      						<xsl:otherwise>text</xsl:otherwise>
		      					</xsl:choose>
	      					</xsl:variable>
	
	      					<xsl:if test="datatype[.!='objectDirectory']">
		      					<input class="input"> 
			      					<xsl:attribute name="type"><xsl:value-of select="$type"/></xsl:attribute>
			      					<xsl:attribute name="name">
			      						<xsl:text>values(</xsl:text>
			      						<xsl:value-of select="columnname" />
			      						<xsl:text>)</xsl:text>
			      					</xsl:attribute>									
			      					<xsl:attribute name="size">
			      						<xsl:value-of select="controltype/@size" />
			      					</xsl:attribute>
			      					<xsl:attribute name="maxlength">
			      						<xsl:value-of select="controltype/@maxlength" />
			      					</xsl:attribute>
									<xsl:attribute name="id">
										<xsl:value-of select="columnname" />
									</xsl:attribute>
			      					<xsl:attribute name="value">
			      						<xsl:value-of select="controltype/@value" />
			      					</xsl:attribute>
				      				<xsl:choose>
				      					<xsl:when test="controltype/@readonly[.='true']">
					      					<xsl:attribute name="readonly">
					      						<xsl:value-of select="controltype/@readonly"/>
					      					</xsl:attribute>
				      					</xsl:when>
				      				</xsl:choose>
								</input>
							</xsl:if>
							
							<!--  mostramos el componente del calendario si el tipo es fecha y no esta en solo lectura -->
							<xsl:if test="datatype[.='interval']">
								<span>
									dd/mm/aaaa;dd/mm/aaaa
								</span>
								<br/><a>
		      					<xsl:attribute name="href">
		      						<xsl:text>javascript:printWeek('</xsl:text>
		      						<xsl:value-of select="columnname" />
		      						<xsl:text>')</xsl:text>
		      					</xsl:attribute><xsl:value-of select="java:getString($resourceBundle, 'report.thisweek')" /></a> | <a>
		      					<xsl:attribute name="href">
		      						<xsl:text>javascript:printMonth('</xsl:text>
		      						<xsl:value-of select="columnname" />
		      						<xsl:text>')</xsl:text>
		      					</xsl:attribute><xsl:value-of select="java:getString($resourceBundle, 'report.thismonth')" /></a> | <a>
		      					<xsl:attribute name="href">
		      						<xsl:text>javascript:printYear('</xsl:text>
		      						<xsl:value-of select="columnname" />
		      						<xsl:text>')</xsl:text>
		      					</xsl:attribute><xsl:value-of select="java:getString($resourceBundle, 'report.thisyear')" /></a>
							</xsl:if>

							<xsl:if test="datatype[.='objectDirectory']">
								
		      					<input class="input"> 
			      					<xsl:attribute name="type"><xsl:text>hidden</xsl:text></xsl:attribute>
			      					<xsl:attribute name="name">
			      						<xsl:text>values(</xsl:text>
			      						<xsl:value-of select="columnname" />
			      						<xsl:text>)</xsl:text>
			      					</xsl:attribute>									
									<xsl:attribute name="id">
										<xsl:value-of select="columnname" />
									</xsl:attribute>
			      					<xsl:attribute name="value">
			      						<xsl:value-of select="controltype/@value" />
			      					</xsl:attribute>
								</input>
								
								<xsl:variable name="property"><xsl:value-of select="columnname" /></xsl:variable>
								<xsl:value-of select="java:includeParameterResponsible($parameterUtils, $request, $property)"></xsl:value-of>
								
								<input class="input" type="text">
									<xsl:attribute name="id"><xsl:text>NAME_</xsl:text><xsl:value-of select="columnname" /></xsl:attribute>
									<xsl:attribute name="name"><xsl:text>values(NAME_</xsl:text><xsl:value-of select="columnname" /><xsl:text>)</xsl:text></xsl:attribute>
									<xsl:attribute name="readonly">true</xsl:attribute>
									<xsl:attribute name="class">inputReadOnly</xsl:attribute>
								</input>
								<br/><a>
		      					<xsl:attribute name="href">
		      						<xsl:text>javascript:setObjDirectory('</xsl:text><xsl:value-of select="columnname" /><xsl:text>', '</xsl:text>NAME_<xsl:value-of select="columnname" /><xsl:text>', '</xsl:text><xsl:value-of select="$userUID"/><xsl:text>', '</xsl:text><xsl:value-of select="$userName"/>
		      						<xsl:text>')</xsl:text>
		      					</xsl:attribute><xsl:value-of select="java:getString($resourceBundle, 'report.myuser')" /></a> | <a>
		      					<xsl:attribute name="href">
		      						<xsl:text>javascript:setObjDirectory('</xsl:text><xsl:value-of select="columnname" /><xsl:text>', '</xsl:text>NAME_<xsl:value-of select="columnname" /><xsl:text>', '</xsl:text><xsl:value-of select="$deptUID"/><xsl:text>', '</xsl:text><xsl:value-of select="$deptName"/>
		      						<xsl:text>')</xsl:text>
		      					</xsl:attribute><xsl:value-of select="java:getString($resourceBundle, 'report.mydept')" /></a>
		      					 | <a>
			      					<xsl:attribute name="href">
			      					<xsl:text>javascript:showFramePosition('workframeDirectory','</xsl:text><xsl:value-of select="java:getContextPath($request)"/><xsl:text>/viewUsersManager.do?parameters=</xsl:text><xsl:value-of select="columnname" /><xsl:text>&amp;workframe=workframeDirectory&amp;formName=reportForm')</xsl:text>
			      				</xsl:attribute>
			      				<xsl:value-of select="java:getString($resourceBundle, 'report.other')" />
								</a>
		      													
							</xsl:if>
			      		</xsl:otherwise>
			      		
					</xsl:choose>
				
					<input type='hidden'>
						<xsl:attribute name="name">
						<xsl:text>types(</xsl:text>
						<xsl:value-of select="columnname" />
						<xsl:text>)</xsl:text>
						</xsl:attribute>															
						<xsl:attribute name="value">
							<xsl:value-of select="datatype" />
						</xsl:attribute>																							
					</input>
					
					<input type='hidden'>
						<xsl:attribute name="name">
						<xsl:text>sqlqueries(</xsl:text>
						<xsl:value-of select="columnname" />
						<xsl:text>)</xsl:text>
						</xsl:attribute>															
						<xsl:attribute name="value">
							<xsl:value-of select="sqlquery" />
						</xsl:attribute>																							
					</input>
					
				</td>
				
			 </xsl:when>		
	
		</xsl:choose>
		
	</tr>	
</xsl:template>
<xsl:template match="description">
	<td></td>
	<td class="forms" align="left" valign="top">
		<xsl:choose>
			<xsl:when test="../controltype/@visible[.='false']"></xsl:when>
			<xsl:otherwise>

                <xsl:variable name="description"><xsl:value-of select="."/></xsl:variable>
                <xsl:variable name="descriptionResource"><xsl:value-of select="java:getMessage($propertiesHelper, $description, 'false')" /></xsl:variable>
                <xsl:choose>
                    <xsl:when test="$descriptionResource">
                        <xsl:value-of select="$descriptionResource"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$description"/>
                    </xsl:otherwise>
                </xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</td>
</xsl:template>
</xsl:stylesheet>