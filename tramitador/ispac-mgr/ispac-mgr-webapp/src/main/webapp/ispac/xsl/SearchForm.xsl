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
<xsl:param name="propertiesHelper"></xsl:param>
<xsl:param name="values"></xsl:param>
<xsl:param name="operators"></xsl:param>
<xsl:variable name="isEmptyOperators">
      	<xsl:value-of select="java:isEmpty($operators)" />
</xsl:variable>
<!--
===========
OC4J
===========
xmlns:java="http://www.oracle.com/XSL/Transform/java/ieci.tdw.ispac.ispacmgr.search.ValidationTable"
-->

<xsl:template match="/">

<!-- aspecto de formulario-->
<table cellpadding="1" cellspacing="0" border="0" width="98%"> <!-- 1 -->
	<tr>
		<td style="border:1px solid #D3D5DD;">
			<table  width="100%" border="0" cellspacing="0" cellpadding="0"> <!-- 2 box-->
				<!-- FORMULARIO -->
					<tr>
						<td >
							<table  width="100%" border="0" cellspacing="0" cellpadding="2"> <!-- 6 -->
								<tr>
									<td height="5px" colspan="3"> </td>
								</tr>
								<tr>
									<td width="8"> </td>
									<td width="100%" >
										<!-- DEFINICION DE LAS PESTAÑAS -->

										<table border="0" cellspacing="0" cellpadding="0"> <!-- 7 -->
											<tr>
												<td class="select" align="center">
													<nobr>
														<xsl:value-of select="java:getString($resourceBundle, 'search.tab.title')" />
													</nobr>
												</td>
											</tr>
										</table> <!-- 7 -->
										<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0"> <!-- 8 -->
											<tr>
												<td height="10px"> </td>
											</tr>
											<tr>
												<td align="center">
                                                <!-- hasta aqui-->

                                                	<table width="100%" border="0">
                                                		<tr>
                                                			<td>
                                                				<input type="hidden" name="domain" value="2"/>
                                                			</td>
                                                		</tr>
                                                			<xsl:choose>
														    	<xsl:when test="queryform/configuration/expState[@show[.='1']]">
																	<span class="forms"><xsl:value-of select="java:getString($resourceBundle, 'search.filter.title')" /></span>
																	<select name="expState" class="input">
																		<option value="0" selected="true"><xsl:value-of select="java:getString($resourceBundle, 'search.filter.option.all')" /></option>
																		<option value="1"><xsl:value-of select="java:getString($resourceBundle, 'search.filter.option.active')" /></option>
																		<option value="2"><xsl:value-of select="java:getString($resourceBundle, 'search.filter.option.finished')" /></option>
																		<option value="4"><xsl:value-of select="java:getString($resourceBundle, 'search.filter.option.archived')" /></option>
																	</select>
														    	</xsl:when>
																<xsl:when test="queryform/configuration/expState != ''">
																	<input type="hidden" name="expState">
																		<xsl:attribute name="value">
																			<xsl:value-of select="queryform/configuration/expState" />
																		</xsl:attribute>
																	</input>
																</xsl:when>
															</xsl:choose>
                                                	</table>
                                                	<p></p>

                                                	<table width="98%" border="0">
                                                		<!--xsl:apply-templates /-->
														<!--xsl:apply-templates select="queryform/entity/field[@type[.='query']]" /-->														<xsl:for-each select="queryform/entity/field[@type[.='query']]">
														<xsl:sort select="@order" order="ascending"/>
														<xsl:apply-templates select="." />
														</xsl:for-each>
                                                		<tr><td><br></br></td></tr>
                                                		<tr>
                                                			<td colspan="7" align="right">
                                                				<input type="button" class="form_button" onclick="javascript:submitform();">
                                                					<xsl:attribute name="value"><xsl:value-of select="java:getString($resourceBundle, 'search.button.search')" /></xsl:attribute >
                                                				</input>
                                                			</td>
                                                		</tr>
                                                		<tr>
                                                			<input type='hidden' name='displaywidth'>
                                                				<xsl:attribute name="value">
                                                					<xsl:value-of select="queryform/@displaywidth" />
                                                				</xsl:attribute>
                                                			</input>
                                                		</tr>
                                                	</table>

                                                    <!-- aspecto formulario -->
												</td>
											</tr>
											<tr>
												<td height="10px"> </td>
											</tr>
										</table> <!-- 8 -->
									</td>
									<td width="5px"> </td>
								</tr>
								<tr>
									<td height="5px" colspan="3"> </td>
								</tr>
							</table> <!-- 6 -->
						</td>
					</tr>
				</table> <!-- 2 -->
			</td>
		</tr>
	</table> <!-- 1 -->

<!-- hasta aqui -->

	<xsl:apply-templates select="queryform/entity/tablename" />
</xsl:template>
<xsl:template match="entity">
	<tr>
		<td colspan="7" class="forms"><b><xsl:value-of select="description"/></b></td>
		<xsl:apply-templates select="field[@type[.='query']]" />
	</tr>
</xsl:template>

<xsl:template match="field">
	<tr>
		<xsl:apply-templates select="description" />
		<td width="15px" valign="top"></td>
		<xsl:apply-templates select="datatype" />
		<td width="15px" valign="top"></td>
		<xsl:apply-templates select="binding" />
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
                        <xsl:variable name="defaultvalue"><xsl:if test="$isEmptyOperators='true'"><xsl:value-of select="controltype/@defaultvalue"/></xsl:if></xsl:variable>
						 <!-- Indica si se al seleccionar una opcion se ha de recargar el formulario -->
						<xsl:variable name="reload"><xsl:value-of select="controltype/@reload"/></xsl:variable>
						 <!-- Indica si el rol que juega el campo en una relación jerarquica (en caso de pertenecer) -->
						<xsl:variable name="role"><xsl:value-of select="controltype/@role"/></xsl:variable>
						 <!-- Nombre de la jerarquía  padre-->
						<xsl:variable name="parentHierarchicalName"><xsl:value-of select="controltype/@parentHierarchicalName"/></xsl:variable>
						 <!-- Campo  de la jerarquía padre -->
						<xsl:variable name="parentFieldHierarchical"><xsl:value-of select="controltype/@parentFieldHierarchical"/></xsl:variable>
						<!-- Nombre de la jerarquía  hija-->
						<xsl:variable name="childHierarchicalName"><xsl:value-of select="controltype/@childHierarchicalName"/></xsl:variable>
						 <!-- Campo de la jerarquía hija -->
						<xsl:variable name="childFieldHierarchical"><xsl:value-of select="controltype/@childFieldHierarchical"/></xsl:variable>

                        <!-- Llamada al metodo que renderiza el control HTML.-->
						<xsl:value-of select="java:format($valid, $field, $label, $value, $clause, $defaultvalue, $reload , $orderBy , $values , $role, $parentHierarchicalName , $parentFieldHierarchical, $childHierarchicalName , $childFieldHierarchical)" disable-output-escaping="yes"/>
			      </xsl:when>


			<xsl:when test="controltype/@tipo[.='list']">



                        <!-- Nombre de la tabla de validacion-->
                        <xsl:variable name="table"><xsl:value-of select="controltype/@table"/></xsl:variable>
                        <!-- Llamada al constructor de la clase que generara el control con los valores de la tabla de validacion -->
                        <!--
                        ===========
                        OC4J
                        ===========
                        <xsl:variable name="valid" select="java:new($table)"/>
                        -->
                        <xsl:variable name="valid" select="java:ieci.tdw.ispac.ispacmgr.search.MultiListTable.new($table)"/>

                        <!-- Nombre del campo de la tabla a mostrar como etiqueta -->
                        <xsl:variable name="label"><xsl:value-of select="controltype/@label"/></xsl:variable>
                        <!-- Nombre del campo de la tabla a utilizar como valor -->
                        <xsl:variable name="value"><xsl:value-of select="controltype/@value"/></xsl:variable>
						<xsl:variable name="height"><xsl:value-of select="controltype/@height"/></xsl:variable>
                        <!-- Nombre del control HTML -->
                        <xsl:variable name="field"><xsl:value-of select="controltype/@field"/></xsl:variable>
                        <!-- Clausula where de la consulta a realizar -->
                        <xsl:variable name="clause"><xsl:value-of select="controltype/@clause"/></xsl:variable>
                        <!-- Indica si se selecciona un valor por defecto -->
                       <xsl:variable name="defaultvalue"><xsl:if test="$isEmptyOperators='true'"><xsl:value-of select="controltype/@defaultvalue"/></xsl:if></xsl:variable>
						<!-- Si queremos ordenar por algun campo -->
                        <xsl:variable name="orderBy"><xsl:value-of select="controltype/@orderBy"/></xsl:variable>
						<!--Indica si depende de algun otro campo y el nombre que tiene en la aplicacion -->
						<xsl:variable name="dependantColumn"><xsl:value-of select="controltype/@dependantColumn"/></xsl:variable>
						<!--Indica contra que campo de su tabla es la dependencia -->
						<xsl:variable name="dependantField"><xsl:value-of select="controltype/@dependantField"/></xsl:variable>
						<!--Indica nombre de la tabla y campo en la bbdd-->
						<xsl:variable name="dependantColumnBbdd"><xsl:value-of select="controltype/@dependantColumnBbdd"/></xsl:variable>


                        <!-- Llamada al metodo que renderiza el control HTML.-->

						<xsl:value-of select="java:format($valid, $field, $label, $value, $clause, $defaultvalue, $height, $mapParams, $dependantColumn, $dependantField, $dependantColumnBbdd , $orderBy , $values)" disable-output-escaping="yes"/>

					<input type='hidden'>
						<xsl:attribute name="name">
							<xsl:text>types(</xsl:text>
							<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
							<xsl:text>)</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="datatype" />
						</xsl:attribute>
					</input>

			 </xsl:when>

			 <xsl:when test="controltype/@tipo[.='validateByCode']">
                        <!-- Nombre de la tabla de validacion-->
                        <xsl:variable name="table"><xsl:value-of select="controltype/@table"/></xsl:variable>
                        <!-- Llamada al constructor de la clase que generara el control con los valores de la tabla de validacion -->
                        <!--
                        ===========
                        OC4J
                        ===========
                        <xsl:variable name="valid" select="java:new($table)"/>
                        -->
                        <xsl:variable name="valid" select="java:ieci.tdw.ispac.ispacmgr.search.ValidatedFieldByCode.new($table)"/>

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
                        <xsl:variable name="defaultvalue">
                        	<xsl:if test="$isEmptyOperators='true'"><xsl:value-of select="controltype/@defaultvalue"/></xsl:if>
                        </xsl:variable>
						 <!-- Indica si se al seleccionar una opcion se ha de recargar el formulario -->
						<xsl:variable name="reload"><xsl:value-of select="controltype/@reload"/></xsl:variable>
						 <!-- Indica el tamaño del texto con el código-->
						<xsl:variable name="sizeCode"><xsl:value-of select="controltype/@sizeCode"/></xsl:variable>
						 <!-- Indica el tamaño del texto con el valor , si rowsValue=1 se pintará una caja de texto en caso contrario un textarea-->
						<xsl:variable name="rowsValue"><xsl:value-of select="controltype/@rowsValue"/></xsl:variable>
						<!-- Indica el numero de columnas , en caso que el numero de filas sea 1 entonces sera el tamaño del control-->
						<xsl:variable name="colsValue"><xsl:value-of select="controltype/@colsValue"/></xsl:variable>
						 <!-- Indica si el rol que juega el campo en una relación jerarquica (en caso de pertenecer) -->
						<xsl:variable name="role"><xsl:value-of select="controltype/@role"/></xsl:variable>
						 <!-- Nombre de la jerarquía  padre-->
						<xsl:variable name="parentHierarchicalName"><xsl:value-of select="controltype/@parentHierarchicalName"/></xsl:variable>
						 <!-- Campo de la jerarquía padre -->
						<xsl:variable name="parentFieldHierarchical"><xsl:value-of select="controltype/@parentFieldHierarchical"/></xsl:variable>
						<!-- Nombre de la jerarquía  hija-->
						<xsl:variable name="childHierarchicalName"><xsl:value-of select="controltype/@childHierarchicalName"/></xsl:variable>
						 <!-- Campo  de la jerarquía hija -->
						<xsl:variable name="childFieldHierarchical"><xsl:value-of select="controltype/@childFieldHierarchical"/></xsl:variable>
	    				 <!-- Ancho del frame buscador -->
						<xsl:variable name="width"><xsl:value-of select="controltype/@width"/></xsl:variable>
						 <!-- Alto del frame buscador -->
						<xsl:variable name="height"><xsl:value-of select="controltype/@height"/></xsl:variable>
                        <!-- Llamada al metodo que renderiza el control HTML.-->
						<xsl:value-of select="java:format($valid, $field, $label, $value, $clause, $defaultvalue, $reload , $orderBy , $sizeCode , $rowsValue , $colsValue, $values ,$role, $parentHierarchicalName ,$parentFieldHierarchical, $childHierarchicalName , $childFieldHierarchical , $width ,$height,$resourceBundle)" disable-output-escaping="yes"/>
			      </xsl:when>

				  <xsl:otherwise>

      				<xsl:variable name="type">
	      				<xsl:choose>
	      					<xsl:when test="controltype/@visible[.='false']">hidden</xsl:when>
	      					<xsl:otherwise>text</xsl:otherwise>
	      				</xsl:choose>
      				</xsl:variable>
      				<xsl:variable name="nameField" >
      						<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
      				</xsl:variable>
      				  <xsl:variable name="key" select="java:java.lang.String.new($nameField)"/>
      					<xsl:variable name="userValue">
      						<xsl:value-of select="java:get($values, $key)" />
      					</xsl:variable>
      				<xsl:variable name="userOperator">
      					<xsl:value-of select="java:get($operators, $key)" />
      				</xsl:variable>
      				<input class="input">
      					<xsl:attribute name="type"><xsl:value-of select="$type"/></xsl:attribute>
      					<xsl:attribute name="name">
      						<xsl:text>values(</xsl:text>
      						<xsl:value-of select="$nameField"/>
      						<xsl:text>)</xsl:text>
      					</xsl:attribute>
      					<xsl:attribute name="size">
      						<xsl:value-of select="controltype/@size" />
      					</xsl:attribute>
      					<xsl:attribute name="maxlength">
      						<xsl:value-of select="controltype/@maxlength" />
      					</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="../tablename" />__<xsl:value-of select="columnname" />
						</xsl:attribute>
						<xsl:choose>
							<xsl:when test="$userValue!= ''">
							<xsl:attribute name="value">
	      							<xsl:value-of select="$userValue"/>
	      						</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<!-- Solo la primera vez se pondrá el valor por defecto,
								en el resto de ocasiones el valor estará en values-->
								<xsl:if test="$isEmptyOperators='true'">
									<xsl:attribute name="value">
		      							<xsl:value-of select="controltype/@value" />
		      						</xsl:attribute>
	      						</xsl:if>
	      					</xsl:otherwise>
						</xsl:choose>

	      				<xsl:choose>
	      					<xsl:when test="controltype/@readonly[.='true']">
		      					<xsl:attribute name="readonly">
		      						<xsl:value-of select="controltype/@readonly"/>
		      					</xsl:attribute>
	      					</xsl:when>
	      				</xsl:choose>
					</input>
					<!--  mostramos el componente del calendario si el tipo es fecha y no esta en solo lectura -->
					<xsl:if test="datatype[.='date']">
					<span>
						<xsl:attribute name="id">
							<xsl:text>desc_</xsl:text>
							<xsl:value-of select="../tablename"/>
							<xsl:text>_</xsl:text>
							<xsl:value-of select="columnname" />
						</xsl:attribute>
					<xsl:choose>
						<xsl:when test="$userOperator = '..'">
							dd/mm/aaaa;dd/mm/aaaa
						</xsl:when>
						<xsl:otherwise>
							dd/mm/aaaa
						</xsl:otherwise>
					</xsl:choose>

					</span>
<!--
						<xsl:choose>
							<xsl:when test="controltype/@readonly[.='true']"></xsl:when>
							<xsl:otherwise>
						   		<img id='calgif' >
						   			<xsl:attribute name="src">
						   				<xsl:text>./ispac/skin2/img/calendar/calendarM.gif</xsl:text>
						   			</xsl:attribute>
						   			<xsl:attribute name="onclick">
						   				<xsl:text>showCalendar(this, document.queryform.</xsl:text>
						   				<xsl:value-of select="../tablename" />__<xsl:value-of select="columnname" />
						   				<xsl:text>, 'dd/mm/yyyy','es',1,-1,-1)</xsl:text>
						   			</xsl:attribute>
						   		</img>
							</xsl:otherwise>
						</xsl:choose>
 -->
					</xsl:if>

			      </xsl:otherwise>
			    </xsl:choose>
					<input type='hidden'>
						<xsl:attribute name="name">
							<xsl:text>types(</xsl:text>
							<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
							<xsl:text>)</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="value">
							<xsl:value-of select="datatype" />
						</xsl:attribute>
					</input>
				</td>
			 </xsl:when>



	<xsl:when test="controltype[.='textarea']">
		 	 <td>
				<xsl:variable name="name">
						<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
				</xsl:variable>

                 <!-- Llamada al constructor de la clase que generara el control con los valores de la tabla de validacion -->
                 <!--
                 ===========
                 OC4J
                 ===========
                 <xsl:variable name="valid" select="java:new($table)"/>
                 -->
                 <xsl:variable name="valid" select="java:ieci.tdw.ispac.ispacmgr.search.TextArea.new($name)"/>
                 	<!-- Número de filas-->
				<xsl:variable name="rows"><xsl:value-of select="controltype/@rows"/></xsl:variable>
				<!-- Número de columnas-->
				<xsl:variable name="cols"><xsl:value-of select="controltype/@cols"/></xsl:variable>
				 <!-- Valor por defecto-->
				<xsl:variable name="defaultValue"><xsl:if test="$isEmptyOperators='true'"><xsl:value-of select="controltype/@defaultValue"/></xsl:if></xsl:variable>

                  <!-- Llamada al metodo que renderiza el control HTML.-->
				<xsl:value-of select="java:format($valid, $rows, $cols, $defaultValue, $values)" disable-output-escaping="yes"/>

				<input type='hidden'>
					<xsl:attribute name="name">
				 		<xsl:text>types(</xsl:text>
						<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
						<xsl:text>)</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="datatype" />
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
		<!-- si el campo visible indica que no se muestre el campo de entrada no mostramos tampoco el texto -->
		<xsl:choose>
			<xsl:when test="../controltype/@visible[.='false']"></xsl:when>
			<xsl:otherwise>
                <xsl:variable name="description"><xsl:value-of select="."/></xsl:variable>
                <!--
                <xsl:variable name="descriptionResource"><xsl:value-of select="java:handleGetObject($resourceBundle, $description)" /></xsl:variable>
                -->
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

<xsl:template match="binding">

</xsl:template>

<xsl:template match="datatype">
			<xsl:variable name="nameField" >
      				<xsl:value-of select="../../tablename"/>:<xsl:value-of select="../columnname" />
    	 	</xsl:variable>
     		<xsl:variable name="key" select="java:java.lang.String.new($nameField)"/>
    		<xsl:variable name="userOperator">
      			<xsl:value-of select="java:get($operators, $key)" />
      		</xsl:variable>
		<xsl:choose>

<!--
	  	<xsl:when test="../controltype/@tipo[.='validate' or .='list']">


			<xsl:if test="../controltype/@tipo[.='validate' or .='list']">
		  		<xsl:choose>
					<xsl:when test="../controltype/@visible[.='false']">
					</xsl:when>
						<xsl:otherwise>
							<b><xsl:text>&#160;&#160;=</xsl:text></b>
						</xsl:otherwise>
					</xsl:choose>

			</xsl:if>

	    </xsl:when>
-->

	  	<xsl:when test="../controltype/@tipo[.='list']">
		  		<xsl:choose>
					<xsl:when test="../controltype/@visible[.='false']">
					</xsl:when>
						<xsl:otherwise>
							<b><xsl:text>&#160;&#160;=</xsl:text></b>
						</xsl:otherwise>
					</xsl:choose>
	    </xsl:when>

		<xsl:when test="../controltype/@tipo[.='validate' or .='list']">


			<xsl:choose>
					<xsl:when test="../controltype/@visible[.='false']">
					</xsl:when>
					<xsl:when test="../operators">
						<select class="input">
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="$nameField"/>
								<xsl:text>)</xsl:text>
							</xsl:attribute>
							<xsl:for-each select="../operators/operator">
							<xsl:variable name="operator"><xsl:value-of select="name"/></xsl:variable>
							<xsl:choose>
								<xsl:when test="$userOperator = $operator">
									<option selected="yes"><xsl:value-of select="name"/></option>
								</xsl:when>
								<xsl:otherwise>
								 	<option><xsl:value-of select="name"/></option>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
						</select>
					 </xsl:when>
					<xsl:otherwise>
						<select class="input">
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="../../tablename"/>:<xsl:value-of select="../columnname" />
								<xsl:text>)</xsl:text>
							</xsl:attribute>
							<xsl:choose>
								<xsl:when test="$userOperator!= ''">
									<option>
										<xsl:attribute name="value">
											<xsl:text>=</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = '='">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>=</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>&lt;&gt;</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = '&lt;&gt;'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>&lt;&gt;</xsl:text>
									</option>

								</xsl:when>
								<xsl:otherwise>
									<option value="=" selected="yes">=</option>
									<option value="&lt;&gt;">&lt;&gt;</option>
								</xsl:otherwise>
							</xsl:choose>

						</select>
					</xsl:otherwise>
			</xsl:choose>
</xsl:when>

		<xsl:when test=".='date'">
				<script>
	  			function modifyDesc_<xsl:value-of select="../../tablename"/>_<xsl:value-of select="../columnname" />(obj){
	  					if (obj.value == '..')
	  						document.getElementById('desc_<xsl:value-of select="../../tablename"/>_<xsl:value-of select="../columnname" />').innerHTML="dd/mm/aaaa;dd/mm/aaaa";
	  					else
	  						document.getElementById('desc_<xsl:value-of select="../../tablename"/>_<xsl:value-of select="../columnname" />').innerHTML="dd/mm/aaaa";
	  			}
	  		</script>
			<xsl:choose>
					<xsl:when test="../controltype/@visible[.='false']">
					</xsl:when>

					<xsl:when test="../operators">
						<select class="input">
							<xsl:attribute name="onChange">
								<xsl:text>modifyDesc_</xsl:text>
								<xsl:value-of select="../../tablename"/>_<xsl:value-of select="../columnname" />
								<xsl:text>(this);</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="$nameField"/>
								<xsl:text>)</xsl:text>
							</xsl:attribute>

						<xsl:for-each select="../operators/operator">
							<xsl:variable name="operator"><xsl:value-of select="name"/></xsl:variable>
							<xsl:choose>

								<xsl:when test="$userOperator = $operator">
									<option selected="yes"><xsl:value-of select="name"/></option>
								</xsl:when>
								<xsl:otherwise>
								 	<option><xsl:value-of select="name"/></option>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
						</select>
					 </xsl:when>
					<xsl:otherwise>
					<select class="input">
							<xsl:attribute name="onChange">
								<xsl:text>modifyDesc_</xsl:text>
								<xsl:value-of select="../../tablename"/>_<xsl:value-of select="../columnname" />
								<xsl:text>(this);</xsl:text>
							</xsl:attribute>
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="$nameField"/>
								<xsl:text>)</xsl:text>
							</xsl:attribute>
							<option>
								<xsl:attribute name="value">
									<xsl:text>=</xsl:text>
								</xsl:attribute>
								<xsl:if test="$userOperator = '='">
									<xsl:attribute name="selected">
										<xsl:text>yes</xsl:text>
									</xsl:attribute>
								</xsl:if>
								<xsl:text>=</xsl:text>
							</option>
							<option>
							<xsl:attribute name="value">
								<xsl:text>&gt;</xsl:text>
							</xsl:attribute>
							<xsl:if test="$userOperator = '&gt;'">
								<xsl:attribute name="selected">
									<xsl:text>yes</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:text>&gt;</xsl:text>
						</option>
						<option>
							<xsl:attribute name="value">
								<xsl:text>&gt;=</xsl:text>
							</xsl:attribute>
							<xsl:if test="$userOperator = '&gt;='">
								<xsl:attribute name="selected">
									<xsl:text>yes</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:text>&gt;=</xsl:text>
						</option>
						<option>
							<xsl:attribute name="value">
								<xsl:text>&lt;</xsl:text>
							</xsl:attribute>
							<xsl:if test="$userOperator = '&lt;'">
								<xsl:attribute name="selected">
									<xsl:text>yes</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:text>&lt;</xsl:text>
						</option>
						<option>
							<xsl:attribute name="value">
								<xsl:text>&lt;=</xsl:text>
							</xsl:attribute>
							<xsl:if test="$userOperator = '&lt;='">
								<xsl:attribute name="selected">
									<xsl:text>yes</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:text>&lt;=</xsl:text>
						</option>
						<option>
							<xsl:attribute name="value">
								<xsl:text>..</xsl:text>
							</xsl:attribute>
							<xsl:if test="$userOperator = '..'">
								<xsl:attribute name="selected">
									<xsl:text>yes</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<xsl:text>[...]</xsl:text>
						</option>
						</select>
					</xsl:otherwise>
			</xsl:choose>

		</xsl:when>

	<xsl:otherwise >


		<xsl:choose>
					<xsl:when test="../controltype/@visible[.='false']">
					</xsl:when>

					<xsl:when test="../operators">
						<select class="input">
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="../../tablename"/>:<xsl:value-of select="../columnname" />
								<xsl:text>)</xsl:text>
							</xsl:attribute>
						<xsl:for-each select="../operators/operator">
							<xsl:variable name="operator"><xsl:value-of select="name"/></xsl:variable>
							<xsl:choose>
								<xsl:when test="$userOperator = $operator">
									<option selected="yes"><xsl:value-of select="name"/></option>
								</xsl:when>
								<xsl:otherwise>
								 	<option><xsl:value-of select="name"/></option>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
						</select>
					 </xsl:when>
					<xsl:otherwise>

				<select class="input">
							<xsl:attribute name="name">
								<xsl:text>operators(</xsl:text>
								<xsl:value-of select="$nameField"/>
								<xsl:text>)</xsl:text>
							</xsl:attribute>
							<xsl:choose>
								<xsl:when test="$userOperator!= ''">
									<option>
										<xsl:attribute name="value">
											<xsl:text>=</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = '='">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>=</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>&gt;</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'gt'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>&gt;</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>&gt;=</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'ge'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>&gt;=</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>&lt;</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'lt'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>&lt;</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>&lt;=</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'le'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>&lt;=</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>contieneLike</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'contieneLike'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>Contiene (Like)</xsl:text>
									</option>
									<option>
										<xsl:attribute name="value">
											<xsl:text>contieneIndex</xsl:text>
										</xsl:attribute>
										<xsl:if test="$userOperator = 'contieneIndex'">
											<xsl:attribute name="selected">
												<xsl:text>yes</xsl:text>
											</xsl:attribute>
										</xsl:if>
										<xsl:text>Contiene (Index)</xsl:text>
									</option>
								</xsl:when>
								<xsl:otherwise>
									<option value="=" selected="yes">=</option>
									<option value="&gt;">&gt;</option>
									<option value="&gt;=">&gt;=</option>
									<option value="&lt;">&lt;</option>
									<option value="&lt;=">&lt;=</option>
									<option value="contieneLike">Contiene (Like)</option>
								</xsl:otherwise>
							</xsl:choose>
						</select>
					</xsl:otherwise>
				</xsl:choose>
		</xsl:otherwise>



	  </xsl:choose>
</xsl:template>

<!--
<xsl:template match="entity">
	<tr>
		<td colspan="7" class="forms"><b><xsl:value-of select="description"/></b></td>
		<xsl:apply-templates select="field[@type[.='query']]" />
	</tr>
</xsl:template>
-->

<xsl:template match="tablename">
	<input type='hidden' name='tables'>
		<xsl:attribute name="value">
			<xsl:value-of select="." />
		</xsl:attribute>
	</input>
</xsl:template>

</xsl:stylesheet>