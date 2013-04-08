<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="4.0"
encoding="iso-8859-1" indent="yes"/>

<xsl:variable name="EDITABLE" select="/Ficha_ISADG/@Editable"/>

<xsl:template match="/">
  <xsl:apply-templates/>
</xsl:template>


<!-- /Ficha_ISADG -->
<xsl:template match="Ficha_ISADG">
  <xsl:apply-templates select="Areas/Area" />
</xsl:template>


<!-- /Ficha_ISADG/Areas/Area -->
<xsl:template match="Area">
  <div class="cabecero_bloque">
    <table class="w98m1" cellpadding="0" cellspacing="0">
      <tr>
        <td class="etiquetaAzul12Bold" align="left">
          <xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/></xsl:attribute>
          <xsl:value-of select="Etiqueta/Titulo"/>&nbsp;
        </td>
        <td align="right">
          <table cellpadding="0" cellspacing="0">
            <tr>
              <td><a><xsl:attribute name="href">javascript:showHideDiv('Area<xsl:value-of select="@Id"/>');</xsl:attribute>
                <img class="imgTextBottom">
                  <xsl:attribute name="id">imgArea<xsl:value-of select="@Id"/></xsl:attribute>
                  <xsl:choose>
                    <xsl:when test="@Desplegada='S'">
                      <xsl:attribute name="src">../images/up.gif</xsl:attribute>
                      <xsl:attribute name="title">Replegar</xsl:attribute>
                      <xsl:attribute name="alt">Replegar</xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:attribute name="src">../images/down.gif</xsl:attribute>
                      <xsl:attribute name="title">Desplegar</xsl:attribute>
                      <xsl:attribute name="alt">Desplegar</xsl:attribute>
                    </xsl:otherwise>
                  </xsl:choose>
                </img></a></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <div class="bloque">
    <xsl:attribute name="id">divArea<xsl:value-of select="@Id"/></xsl:attribute>
    <xsl:choose>
      <xsl:when test="@Desplegada='S'">
        <xsl:attribute name="style">display:block;</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
        <xsl:attribute name="style">display:none;</xsl:attribute>
      </xsl:otherwise>
    </xsl:choose>
    <table class="formulario">
      <tr>
        <td width="50px"></td>
        <td width="200px"></td>
        <td></td>
      </tr>
      <xsl:apply-templates select="Elementos/Elemento" />
    </table>
  </div>
  <div class="separador8">&nbsp;</div>
</xsl:template>


<!-- Elemento -->
<xsl:template match="Elemento">
  <xsl:param name="inCabecera"/>
  <tr>
    <xsl:choose>
      <xsl:when test="$inCabecera='S'">
        <td>&nbsp;</td>
        <td class="tdTituloFicha">
          <xsl:call-template name="MostrarTextoEtiqueta"/>
        </td>
      </xsl:when>
      <xsl:otherwise>
        <td colspan="2" width="250px">
          <xsl:choose>
            <xsl:when test="@Tipo='Cabecera'">
              <xsl:attribute name="class">tdTituloFichaSinBorde</xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
              <xsl:attribute name="class">tdTituloFicha</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>
          <xsl:call-template name="MostrarTextoEtiqueta"/>
        </td>
      </xsl:otherwise>
    </xsl:choose>
    <td>
      <xsl:attribute name="id">TD_<xsl:value-of select="Edicion/Id"/></xsl:attribute>
      <xsl:choose>
        <xsl:when test="@Tipo='EtiquetaDato'">
          <xsl:attribute name="class">tdDatosFicha</xsl:attribute>
          <xsl:call-template name="SaveDataElementInfo"/>
          <xsl:choose>
			  <xsl:when test="@Scroll='S'">
				<div name="divTblFicha" id="divTblFicha">
					<xsl:call-template name="MostrarValoresEtiquetaDato"/>
				</div>
			  </xsl:when>
			  <xsl:otherwise>
				<xsl:call-template name="MostrarValoresEtiquetaDato"/>
			  </xsl:otherwise>
		  </xsl:choose>
        </xsl:when>
        <xsl:when test="@Tipo='Hipervinculo'">
          <xsl:attribute name="class">tdDatosFicha</xsl:attribute>
          <xsl:call-template name="SaveDataElementInfo"/>
          <xsl:call-template name="MostrarHipervinculo"/>
        </xsl:when>
        <xsl:when test="@Tipo='Cabecera'">
          <xsl:attribute name="class">tdDatosFichaSinBorde</xsl:attribute>
          &nbsp;
        </xsl:when>
        <xsl:when test="@Tipo='Tabla' or @Tipo='TablaTextual'">
          <xsl:attribute name="class">tdDatosFicha</xsl:attribute>
          <xsl:call-template name="SaveTableElementInfo"/>
          <xsl:choose>
			  <xsl:when test="@Scroll='S'">
				<div name="divTblFicha" id="divTblFicha">
					<xsl:call-template name="MostrarTabla"/>
				</div>
			  </xsl:when>
			  <xsl:otherwise>
				<xsl:call-template name="MostrarTabla"/>
			  </xsl:otherwise>
		  </xsl:choose>
        </xsl:when>
      </xsl:choose>
    </td>
  </tr>
  <xsl:if test="@Tipo='Cabecera'">
    <xsl:variable name="varInCabecera">S</xsl:variable>
    <xsl:apply-templates select="Elementos_Cabecera/Elemento">
      <xsl:with-param name="inCabecera" select="$varInCabecera"/>
    </xsl:apply-templates>
    <tr>
      <td class="tdTituloFicha" colspan="3"><img src="../images/pixel.gif"/></td>
    </tr>
  </xsl:if>
</xsl:template>


<!-- Mostrar tabla -->
<xsl:template name="MostrarTabla">
  <xsl:variable name="numElementos" select="Numero_Elementos" />
  <xsl:variable name="celdWidth" select="100 div $numElementos"/>
  <xsl:variable name="tableType">
    <xsl:choose>
      <xsl:when test="@Tipo='Tabla'">TABLA</xsl:when>
      <xsl:otherwise>TABLA_TEXTUAL</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="tableClass">
    <xsl:choose>
      <xsl:when test="@Tipo='Tabla'">tablaFicha</xsl:when>
      <xsl:otherwise>tablaTextualFicha</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="tableId" select="Edicion/Id" />
  <input type="hidden">
  	<xsl:attribute name="name">deletedRowsTable<xsl:value-of select='Edicion/Id' /></xsl:attribute>
  </input>

  <xsl:if test="$EDITABLE='S' and Edicion/Editable='S'">
    <table cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <a class="etiquetaAzul12Bold">
            <xsl:attribute name="href">javascript:addTableRow('<xsl:value-of select="$tableId"/>','<xsl:value-of select="$tableType"/>');</xsl:attribute>
            <image src="../images/plus.gif"
                  border="0"
                  alt="Añadir"
                  title="Añadir"
                  class="imgTextBottom" />&nbsp;Añadir</a>

            <xsl:if test="count(Sistema_Externo)" >
            	<input type="hidden">
	              <xsl:attribute name="id">tabla_<xsl:value-of select="Edicion/Id"/>_sistemaExterno</xsl:attribute>
	              <xsl:attribute name="value"><xsl:value-of select="Sistema_Externo"/></xsl:attribute>
	          </input>
            </xsl:if>
        </td>
        <td width="10">&nbsp;</td>
        <td>
          <a class="etiquetaAzul12Bold">
            <xsl:attribute name="href">javascript:removeTableRows('<xsl:value-of select="$tableId"/>');</xsl:attribute>
            <image src="../images/minus.gif"
                  border="0"
                  alt="Eliminar"
                  title="Eliminar"
                  class="imgTextBottom" />&nbsp;Eliminar</a>
         </td>
      </tr>
    </table>
  </xsl:if>
  <table>
    <xsl:choose>
      <xsl:when test="@Tipo='Tabla'">
         <xsl:attribute name="width">99%</xsl:attribute>
      </xsl:when>
      <xsl:otherwise>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:attribute name="id">tabla<xsl:value-of select="$tableId"/></xsl:attribute>
    <xsl:attribute name="class"><xsl:value-of select="$tableClass"/></xsl:attribute>
    <thead>
      <xsl:if test="Mostrar_Cabeceras='N'">
        <xsl:attribute name="style">display:none;</xsl:attribute>
      </xsl:if>
      <tr>
        <xsl:if test="$EDITABLE='S' and Edicion/Editable='S'"><th><img src="../images/delete.gif" alt="Eliminar"/></th></xsl:if>
        <xsl:for-each select="Elementos_Tabla/Elemento">
        <xsl:call-template name="SaveDataElementInfo"/>
        <th>

          <!--<xsl:choose>
             <xsl:when test="@Tipo='Tabla'">-->
                <xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/>width:<xsl:value-of select="$celdWidth" />%</xsl:attribute>
             <!--</xsl:when>
             <xsl:otherwise>
                <xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/></xsl:attribute>
             </xsl:otherwise>
          </xsl:choose>-->
          <!--<xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/></xsl:attribute>-->
          <xsl:value-of select="Etiqueta/Titulo"/>
          <xsl:if test="Edicion/Obligatorio='S'">*</xsl:if>
          <input type="hidden">
              <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id"/>_idtabla</xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="$tableId"/></xsl:attribute>
          </input>
        </th>
        </xsl:for-each>
      </tr>
    </thead>
    <tbody>
      <xsl:for-each select="Elementos_Tabla/Elemento/Dato/Valores">
        <xsl:sort select="count(Valor)" order="descending"/>
        <xsl:if test="position() = 1">
          <xsl:for-each select="Valor">
              <xsl:call-template name="MostrarFilaTabla">
                <xsl:with-param name="rownum" select="position()"/>
                <xsl:with-param name="tableId" select="$tableId"/>
                <xsl:with-param name="tableType" select="$tableType"/>
              </xsl:call-template>
          </xsl:for-each>
        </xsl:if>
      </xsl:for-each>
    </tbody>
  </table>
</xsl:template>


<!-- Muestra una línea de la tabla -->
<xsl:template name="MostrarFilaTabla">
  <xsl:param name="rownum"/>
  <xsl:param name="tableId"/>
  <xsl:param name="tableType"/>
  <xsl:variable name="numElementos" select="../../../../../Numero_Elementos" />
  <xsl:variable name="celdWidth" select="100 div $numElementos"/>

  <tr>
    <xsl:if test="$tableType='TABLA'">
      <xsl:choose>
        <xsl:when test="($rownum mod 2) = 0">
          <xsl:attribute name="class">even</xsl:attribute>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="class">odd</xsl:attribute>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:if>
    <xsl:attribute name="id">tr_<xsl:value-of select="$tableId"/>_<xsl:value-of select="$rownum"/></xsl:attribute>
    <xsl:if test="$EDITABLE='S' and ../../../../../Edicion/Editable='S'">
      <!--<td style="width:*">-->
      <td>
        <input type="checkbox" class="checkbox">
          <xsl:attribute name="name">eliminar_tabla_<xsl:value-of select="$tableId"/></xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="$rownum"/></xsl:attribute>
          <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$tableId"/>_<xsl:value-of select="$rownum"/>')</xsl:attribute>
        </input>
      </td>
    </xsl:if>
    <xsl:for-each select="../../../../Elemento">
    <td>
      <xsl:choose>
         <xsl:when test="$tableType='TABLA'">
            <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/>width:<xsl:value-of select="$celdWidth" />%</xsl:attribute>
         </xsl:when>
         <xsl:otherwise>
             <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
         </xsl:otherwise>
      </xsl:choose>
      <xsl:call-template name="MostrarValoresEtiquetaDato">
        <xsl:with-param name="rownum" select="$rownum"/>
        <xsl:with-param name="inTable">S</xsl:with-param>
      </xsl:call-template>
    </td>
    </xsl:for-each>
  </tr>
</xsl:template>


<!-- MostrarValoresEtiquetaDato -->
<xsl:template name="MostrarValoresEtiquetaDato">
  <xsl:param name="rownum"/>
  <xsl:param name="inTable"/>
  <xsl:variable name="type" select="Edicion/Tipo"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>

  <xsl:choose>

    <xsl:when test="$EDITABLE='S' and Edicion/Editable='S'">
      <xsl:if test="$inTable!='S' and $isMultiple='S'">
        <a class="etiquetaAzul12Bold">
          <xsl:attribute name="href">javascript:addField('<xsl:value-of select="Edicion/Id"/>');</xsl:attribute>
          <image src="../images/plus.gif"
                border="0"
                alt="Añadir"
                title="Añadir"
                class="imgTextBottom" />&nbsp;Añadir</a>
        &nbsp;
        <a class="etiquetaAzul12Bold">
          <xsl:attribute name="href">javascript:removeFields('<xsl:value-of select="Edicion/Id"/>');</xsl:attribute>
          <image src="../images/minus.gif"
                border="0"
                alt="Eliminar"
                title="Eliminar"
                class="imgTextBottom" />&nbsp;Eliminar</a>
        <br/>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="$type='1'"><!-- Texto corto -->
          <xsl:choose>
            <xsl:when test="$inTable='S'">
              <xsl:call-template name="MostrarValorTextoCortoEnTabla">
                <xsl:with-param name="rownum" select="$rownum"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="MostrarValorTextoCorto" />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:when test="$type='2'"><!-- Texto largo -->
          <xsl:choose>
            <xsl:when test="$inTable='S'">
              <xsl:call-template name="MostrarValorTextoLargoEnTabla">
                <xsl:with-param name="rownum" select="$rownum"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="MostrarValorTextoLargo" />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:when test="$type='3'"><!-- Fecha -->
          <xsl:choose>
            <xsl:when test="$inTable='S'">
              <xsl:call-template name="MostrarValorFechaEnTabla">
                <xsl:with-param name="rownum" select="$rownum"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="MostrarValorFecha" />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:when test="$type='4'"><!-- Número -->
          <xsl:choose>
            <xsl:when test="$inTable='S'">
              <xsl:call-template name="MostrarValorNumericoEnTabla">
                <xsl:with-param name="rownum" select="$rownum"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="MostrarValorNumerico" />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:when test="$type='5'"><!-- Referencia -->
          <xsl:choose>
            <xsl:when test="$inTable='S'">
              <xsl:call-template name="MostrarValorReferenciaEnTabla">
                <xsl:with-param name="rownum" select="$rownum"/>
              </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
              <xsl:call-template name="MostrarValorReferencia" />
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
      </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
      <xsl:choose>
        <xsl:when test="$inTable='S'">
		  <xsl:if test="@Tipo='Hipervinculo'"><!-- Hipervinculo -->
			 <xsl:call-template name="MostrarHipervinculoEnTabla">
	             <xsl:with-param name="rownum" select="$rownum"/>
	         </xsl:call-template>
		  </xsl:if>
		  <xsl:if test="@Tipo!='Hipervinculo'">
	          <xsl:apply-templates select="Dato/Valores/Valor[$rownum]" />
	          <input type="hidden">
	            <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" /></xsl:attribute>
	            <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]" /></xsl:attribute>
	          </input>

	          <xsl:if test="$type='4'">
	          	<input type="hidden">
		            <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" />_tipoMedida</xsl:attribute>
		            <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Tipo_Medida" /></xsl:attribute>
		        </input>

		        <input type="hidden">
		            <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" />_numero</xsl:attribute>
		            <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]" /></xsl:attribute>
		        </input>
		        <input type="hidden">
		            <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" />_unidadMedida</xsl:attribute>
		            <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Unidad_Medida" /></xsl:attribute>
		        </input>
	          </xsl:if>
		  </xsl:if>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="Dato/Valores/Valor" disable-output-escaping="yes"/>
          <!--<xsl:apply-templates select="Dato/Valores/Valor" />-->
        </xsl:otherwise>
      </xsl:choose>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<!-- MostrarValorTextoCorto -->
<xsl:template name="MostrarValorTextoCorto">
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="style" select="Dato/Estilo"/>
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>
  <xsl:variable name="isMultilinea" select="Edicion/Multilinea"/>
  <table style="margin-top:5px;margin-bottom:5px;border-collapse:collapse;width:100%">

    <xsl:attribute name="id">tabla_campo_<xsl:value-of select="$id" /></xsl:attribute>
    <xsl:if test="count(Dato/Valores/Valor)=0">
      <tr>
        <xsl:if test="$isMultiple!='S'">
        <td>
          <xsl:choose>
            <xsl:when test="Edicion/Opciones">
              <select>
                <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                <xsl:attribute name="style"><xsl:value-of select="Estilo"/></xsl:attribute>
                <xsl:if test="$isMandatory='N'">
                  <option value="">&nbsp;</option>
                </xsl:if>
                <xsl:apply-templates select="Edicion/Opciones/Opcion"/>
              </select>
            </xsl:when>
            <xsl:otherwise>
               <xsl:choose>
              	 <xsl:when test="$isMultilinea='S'">
              	 	<textarea rows="6">
                           <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                           <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                        </textarea>
              	 </xsl:when>
              	 <xsl:otherwise>
              	    <input type="text" size="50">
	                <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
	                <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
	              </input>
              	 </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
        </td>
        </xsl:if>
      </tr>
    </xsl:if>
    <xsl:for-each select="Dato/Valores/Valor">
      <tr>
        <xsl:if test="$isMultiple='S'">
          <xsl:attribute name="id">tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/></xsl:attribute>
          <td width="30">
            <input type="checkbox" class="checkbox">
              <xsl:attribute name="name">eliminar_<xsl:value-of select="$id" /></xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="position()" /></xsl:attribute>
              <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/>')</xsl:attribute>
            </input>
          </td>
        </xsl:if>
        <td>
          <xsl:choose>
            <xsl:when test="../../../Edicion/Opciones">
              <xsl:variable name="selectedValue" select="."/>
              <select>
                <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                <xsl:attribute name="style"><xsl:value-of select="../../Estilo"/></xsl:attribute>
                <xsl:if test="$isMandatory='N'">
                  <option value="">&nbsp;</option>
                </xsl:if>
                <xsl:apply-templates select="../../../Edicion/Opciones/Opcion">
                    <xsl:with-param name="selectedValue" select="$selectedValue"/>
                </xsl:apply-templates>
              </select>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
              	 <xsl:when test="$isMultilinea='S'">
              	 	<textarea rows="6">
                           <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                           <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                           <xsl:value-of select="." />
                        </textarea>
              	 </xsl:when>
              	 <xsl:otherwise>
              	    <input type="text" size="50">
                       <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                       <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                       <xsl:attribute name="value"><xsl:value-of select="." /></xsl:attribute>
                    </input>
              	 </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
        </td>
      </tr>
    </xsl:for-each>

  </table>
</xsl:template>


<!-- MostrarValorTextoCortoEnTabla -->
<xsl:template name="MostrarValorTextoCortoEnTabla">
  <xsl:param name="rownum"/>
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>
  <xsl:choose>
    <xsl:when test="Edicion/Opciones">
      <xsl:variable name="selectedValue" select="Dato/Valores/Valor[$rownum]"/>
      <select>
        <xsl:attribute name="id">campo_<xsl:value-of select="Edicion/Id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
        <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" /></xsl:attribute>
        <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
        <xsl:if test="$isMandatory='N'">
          <option value="">&nbsp;</option>
        </xsl:if>
        <xsl:apply-templates select="Edicion/Opciones/Opcion">
            <xsl:with-param name="selectedValue" select="$selectedValue"/>
        </xsl:apply-templates>
      </select>
    </xsl:when>
    <xsl:otherwise>
      <input type="text">
        <xsl:attribute name="id">campo_<xsl:value-of select="Edicion/Id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
        <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" /></xsl:attribute>
        <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
        <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]" /></xsl:attribute>
      </input>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<!-- MostrarValorTextoLargo -->
<xsl:template name="MostrarValorTextoLargo">
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="style" select="Dato/Estilo"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>
  <table style="margin-top:5px;margin-bottom:5px;border-collapse:collapse;width:100%;">

    <xsl:attribute name="id">tabla_campo_<xsl:value-of select="$id" /></xsl:attribute>
    <xsl:if test="count(Dato/Valores/Valor)=0">
      <tr valign="top">
        <xsl:if test="$isMultiple!='S'">
        <td>
          <textarea>
            <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
            <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
          </textarea>
        </td>
        </xsl:if>
      </tr>
    </xsl:if>
    <xsl:for-each select="Dato/Valores/Valor">
      <tr valign="top">
        <xsl:if test="$isMultiple='S'">
          <xsl:attribute name="id">tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/></xsl:attribute>
          <td width="30">
            <input type="checkbox" class="checkbox">
              <xsl:attribute name="name">eliminar_<xsl:value-of select="$id" /></xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="position()" /></xsl:attribute>
              <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/>')</xsl:attribute>
            </input>
          </td>
        </xsl:if>
        <td>
          <textarea rows="6">
            <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
            <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
            <xsl:value-of select="." />
          </textarea>
        </td>
      </tr>
    </xsl:for-each>

  </table>
</xsl:template>


<!-- MostrarValorTextoLargoEnTabla -->
<xsl:template name="MostrarValorTextoLargoEnTabla">
  <xsl:param name="rownum"/>
  <textarea rows="3">
    <xsl:attribute name="id">campo_<xsl:value-of select="Edicion/Id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
    <xsl:attribute name="name">campo_<xsl:value-of select="Edicion/Id" /></xsl:attribute>
    <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
    <xsl:value-of select="Dato/Valores/Valor[$rownum]" />
  </textarea>
</xsl:template>


<!-- MostrarValorFecha -->
<xsl:template name="MostrarValorFecha">
  <xsl:variable name="id" select="Edicion/Id" />
  <xsl:variable name="style" select="Dato/Estilo" />
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>



  <table style="margin-top:5px;margin-bottom:5px;border-collapse:collapse;">

    <xsl:attribute name="id">tabla_campo_<xsl:value-of select="$id" /></xsl:attribute>
    <xsl:if test="count(Dato/Valores/Valor)=0">
      <tr valign="top">
        <xsl:if test="$isMultiple!='S'">
        <td>
          <table cellpadding="0" cellspacing="0">
            <tr>
              <td style="border:0;">
                <select onchange="javascript:checkFechaFormato(this)">
                  <xsl:attribute name="id">campo_<xsl:value-of select="$id" />_1</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_formato</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="Estilo"/></xsl:attribute>
                  <xsl:if test="$isMandatory='N'">
                    <option value="">&nbsp;</option>
                  </xsl:if>
                  <xsl:apply-templates select="Edicion/Formatos/Formato"/>
                </select>
              </td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaDValor_<xsl:value-of select="$id" />_1</xsl:attribute>
                <input type="text" size="2" maxlength="2">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaD</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                </input>
              </td>
              <td><xsl:attribute name="id">idFechaG1_<xsl:value-of select="$id" />_1</xsl:attribute>-</td>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaMValor_<xsl:value-of select="$id" />_1</xsl:attribute>
                <input type="text" size="2" maxlength="2">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaM</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                </input>
              </td>
              <td><xsl:attribute name="id">idFechaG2_<xsl:value-of select="$id" />_1</xsl:attribute>-</td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaAValor_<xsl:value-of select="$id" />_1</xsl:attribute>
                <input type="text" size="4" maxlength="4">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaA</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                </input>
              </td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaSValor_<xsl:value-of select="$id" />_1</xsl:attribute>
                <input type="text" size="5" maxlength="5">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaS</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                </input>
              </td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td class="tdDatosFicha" style="border:0;">Calificador:</td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td style="border:0;">
                <xsl:call-template name="MostrarDespegableCalificadores">
                  <xsl:with-param name="name">campo_<xsl:value-of select="$id" />_calificador</xsl:with-param>
                  <xsl:with-param name="selectedValue"></xsl:with-param>
                  <xsl:with-param name="shortedValue">0</xsl:with-param>
                </xsl:call-template>
              </td>
            </tr>
          </table>
          <script>checkFechaFormato(document.getElementById('campo_<xsl:value-of select="$id" />_1'));</script>
        </td>
        </xsl:if>
      </tr>
    </xsl:if>
    <xsl:for-each select="Dato/Valores/Valor">
      <tr valign="top">
        <xsl:if test="$isMultiple='S'">
          <xsl:attribute name="id">tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/></xsl:attribute>
          <td width="30">
            <input type="checkbox" class="checkbox">
              <xsl:attribute name="name">eliminar_<xsl:value-of select="$id" /></xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="position()" /></xsl:attribute>
              <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/>')</xsl:attribute>
            </input>
          </td>
        </xsl:if>

        <xsl:choose>
        <xsl:when test="count(../../../Edicion/Fechas_Extremas_Editables) and ../../../Edicion/Fechas_Extremas_Editables='N' and ../../../Edicion/Editable='S'">
           <td class="tdDatos2">
  	      <xsl:value-of select="." />&nbsp;&nbsp;
              <image src="../images/info.gif"
               border="0"
               class="imgTextBottom"
               onclick="javascript:alert('La unidad documental se encuentra en una unidad de instalación bloqueada. No se puede modificar la fecha.')"/>


               <input type="hidden">
               	  <xsl:attribute name="id">campo_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_formato</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Formato"/></xsl:attribute>
               </input>

               <input type="hidden">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaD</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Dia"/></xsl:attribute>
               </input>

                <input type="hidden">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaM</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Mes"/></xsl:attribute>
                </input>

                <input type="hidden">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaA</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Anio"/></xsl:attribute>
                </input>

                <input type="hidden">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaS</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Siglo"/></xsl:attribute>
                </input>

                <input type="hidden">
                   <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_calificador</xsl:attribute>
                   <xsl:attribute name="value"><xsl:value-of select="@Calificador" /></xsl:attribute>
                </input>

  	   </td>
	</xsl:when>

        <xsl:otherwise>
        <td>
          <table cellpadding="0" cellspacing="0">
            <tr>
              <td style="border:0;">
                <xsl:variable name="selectedValue" select="@Formato"/>
                <select onchange="javascript:checkFechaFormato(this)">
                  <xsl:attribute name="id">campo_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_formato</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="../../Estilo"/></xsl:attribute>
                  <xsl:if test="$isMandatory='N'">
                    <option value="">&nbsp;</option>
                  </xsl:if>
                  <xsl:apply-templates select="../../../Edicion/Formatos/Formato">
                      <xsl:with-param name="selectedValue" select="$selectedValue"/>
                  </xsl:apply-templates>
                </select>
              </td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaDValor_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                <input type="text" size="2" maxlength="2" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaD</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Dia"/></xsl:attribute>
                </input>
              </td>
              <td><xsl:attribute name="id">idFechaG1_<xsl:value-of select="$id" />_1</xsl:attribute>-</td>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaMValor_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                <input type="text" size="2" maxlength="2" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaM</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Mes"/></xsl:attribute>
                </input>
              </td>
              <td><xsl:attribute name="id">idFechaG2_<xsl:value-of select="$id" />_1</xsl:attribute>-</td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaAValor_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                <input type="text" size="4" maxlength="4">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaA</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Anio"/></xsl:attribute>
                </input>
              </td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaSValor_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                <input type="text" size="5" maxlength="5" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaS</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@Siglo"/></xsl:attribute>
                </input>
              </td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td class="tdDatosFicha" style="border:0;">Calificador:</td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td style="border:0;">
                <xsl:call-template name="MostrarDespegableCalificadores">
                  <xsl:with-param name="name">campo_<xsl:value-of select="$id" />_calificador</xsl:with-param>
                  <xsl:with-param name="selectedValue" select="@Calificador" />
                  <xsl:with-param name="shortedValue">0</xsl:with-param>
                </xsl:call-template>
              </td>
            </tr>
          </table>
          <script>checkFechaFormato(document.getElementById('campo_<xsl:value-of select="$id" />_<xsl:value-of select="position()" />'));</script>
        </td>

        </xsl:otherwise>
        </xsl:choose>
      </tr>
    </xsl:for-each>

  </table>
</xsl:template>


<!-- MostrarValorFechaEnTabla -->
<xsl:template name="MostrarValorFechaEnTabla">
  <xsl:param name="rownum"/>
  <xsl:variable name="id" select="Edicion/Id" />
  <xsl:variable name="style" select="Dato/Estilo" />
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td style="border:0;">
          <xsl:variable name="selectedValue" select="Dato/Valores/Valor[$rownum]/@Formato"/>
          <select>
            <xsl:attribute name="id">campo_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
            <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_formato</xsl:attribute>
            <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
            <xsl:attribute name="onchange">javascript:checkFechaFormato(this)</xsl:attribute>
            <xsl:if test="$isMandatory='N'">
              <option value="">&nbsp;</option>
            </xsl:if>
            <xsl:apply-templates select="Edicion/Formatos/Formato">
                <xsl:with-param name="selectedValue" select="$selectedValue"/>
            </xsl:apply-templates>
          </select>
      </td>
    </tr>
    <tr>
      <td style="border:0;">
        <table cellpadding="0" cellspacing="0">
          <tr>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaDValor_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
                <input type="text" size="2" maxlength="2" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaD</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Dia"/></xsl:attribute>
                </input>
              </td>
              <td style="border:0;"><xsl:attribute name="id">idFechaG1_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>-</td>
              <td style="display:none;border:0;" nowrap="true">
                <xsl:attribute name="id">idFechaMValor_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
                <input type="text" size="2" maxlength="2" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaM</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Mes"/></xsl:attribute>
                </input>
              </td>
              <td style="border:0;"><xsl:attribute name="id">idFechaG2_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>-</td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaAValor_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
                <input type="text" size="4" maxlength="4">
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaA</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Anio"/></xsl:attribute>
                </input>
              </td>
              <td style="display:none;border:0;">
                <xsl:attribute name="id">idFechaSValor_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
                <input type="text" size="5" maxlength="5" >
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_fechaS</xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style"/></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@Siglo"/></xsl:attribute>
                </input>
              </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td style="border:0;">
        <table cellpadding="0" cellspacing="0">
          <tr>
              <td style="border:0;">Calificador:</td>
              <td width="10" style="border:0;">&nbsp;</td>
              <td style="border:0;">
                <xsl:call-template name="MostrarDespegableCalificadores">
                  <xsl:with-param name="name">campo_<xsl:value-of select="$id" />_calificador</xsl:with-param>
                  <xsl:with-param name="selectedValue" select="Dato/Valores/Valor[$rownum]/@Calificador" />
                  <xsl:with-param name="shortedValue">1</xsl:with-param>
                </xsl:call-template>
              </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <script>checkFechaFormato(document.getElementById('campo_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" />'));</script>
</xsl:template>


<!-- MostrarValorNumerico -->
<xsl:template name="MostrarValorNumerico">
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="style" select="Dato/Estilo"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>
  <xsl:variable name="defaultTipoMedida" select="Edicion/Medida/@Tipo"/>

  <xsl:variable name="defaultUnidadMedida" select="Edicion/Medida/@Unidad"/>
  <xsl:variable name="mostrarTipoMedida" select="Edicion/Mostrar_Tipo_Numero"/>
  <xsl:variable name="mostrarUnidadMedida" select="Edicion/Mostrar_Unidad_Numero"/>
  <xsl:variable name="validationTable" select="Edicion/Id_TblVld"/>
  <xsl:variable name="editable" select="Edicion/Editable"/>
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>

  <table style="margin-top:5px;margin-bottom:5px;border-collapse:collapse;">
    <tbody>
    <xsl:attribute name="id">tabla_campo_<xsl:value-of select="$id" /></xsl:attribute>
    <xsl:if test="count(Dato/Valores/Valor)=0 and $isMultiple!='S'">
      <tr valign="top">
        <td class="tdDatosFicha" style="border:0;">
          <input type="text" size="20">
            <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_numero</xsl:attribute>
            <xsl:attribute name="style"><xsl:value-of select="$style" /></xsl:attribute>
          </input>
        </td>
      </tr>
    </xsl:if>

    <xsl:for-each select="Dato/Valores/Valor">

      <tr valign="top">
        <xsl:if test="$isMultiple='S'">
          <xsl:attribute name="id">tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/></xsl:attribute>
          <td width="20">
            <input type="checkbox" class="checkbox">
              <xsl:attribute name="name">eliminar_<xsl:value-of select="$id" /></xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="position()" /></xsl:attribute>
              <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/>')</xsl:attribute>
            </input>
          </td>
        </xsl:if>
        <td>
          <table cellpadding="0" cellspacing="0">

            <tr>
                <td class="tdDatosFicha" style="border:0;">
                   <input type="text" size="20">
                      <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_numero</xsl:attribute>
                      <xsl:attribute name="style"><xsl:value-of select="$style" /></xsl:attribute>
                      <xsl:attribute name="value"><xsl:value-of select="." /></xsl:attribute>
                   </input>
          	   <xsl:choose>
          	  	 <xsl:when test="$EDITABLE='S' and $editable='S' and $validationTable!=''">
			       Unidad:
	                       <select>
		                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_unidadMedida</xsl:attribute>
		                  <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
		                  <xsl:if test="$isMandatory='N'">
		                     <option value="">&nbsp;</option>
		                  </xsl:if>
		                  <xsl:variable name="selectedValue" select="@Unidad_Medida"/>
			          <xsl:apply-templates select="../../../Edicion/Opciones/Opcion">
	                             <xsl:with-param name="selectedValue" select="$selectedValue"/>
	                          </xsl:apply-templates>
		               </select>

		               <input type="hidden">
		                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tipoMedida</xsl:attribute>
		                    <xsl:attribute name="value"><xsl:value-of select="@Tipo_Medida" /></xsl:attribute>
		               </input>

	                 </xsl:when>
	                 <xsl:otherwise>
	                 	<input type="hidden">
		                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tipoMedida</xsl:attribute>
		                    <xsl:attribute name="value"><xsl:value-of select="@Tipo_Medida" /></xsl:attribute>
		               </input>
		               <input type="hidden">
		                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_unidadMedida</xsl:attribute>
		                    <xsl:attribute name="value"><xsl:value-of select="@Unidad_Medida" /></xsl:attribute>
		               </input>
	                 </xsl:otherwise>
                   </xsl:choose>
                </td>
             </tr>

          </table>
        </td>
      </tr>

    </xsl:for-each>
  </tbody>
  </table>
</xsl:template>


<!-- MostrarValorNumericoEnTabla -->
<xsl:template name="MostrarValorNumericoEnTabla">
  <xsl:param name="rownum"/>
  <xsl:variable name="isMandatory" select="Edicion/Obligatorio"/>
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="style" select="Dato/Estilo"/>
  <xsl:variable name="defaultTipoMedida" select="Edicion/Medida/@Tipo"/>
  <xsl:variable name="defaultUnidadMedida" select="Edicion/Medida/@Unidad"/>
  <xsl:variable name="tipoMedida" select="Dato/Valores/Valor[$rownum]/@Tipo_Medida"/>
  <xsl:variable name="unidadMedida" select="Dato/Valores/Valor[$rownum]/@Unidad_Medida"/>
  <xsl:variable name="mostrarTipoMedida" select="Edicion/Mostrar_Tipo_Numero"/>
  <xsl:variable name="mostrarUnidadMedida" select="Edicion/Mostrar_Unidad_Numero"/>
  <xsl:variable name="validationTable" select="Edicion/Id_TblVld"/>
  <xsl:variable name="editable" select="Edicion/Editable"/>

  <table cellpadding="0" cellspacing="0">
    <tr>
      <td style="border:0;width:99%">

        <input type="text">
          <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_numero</xsl:attribute>
          <xsl:attribute name="style"><xsl:value-of select="$style" /></xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]" /></xsl:attribute>
        </input>
      	    <xsl:choose>
	    	<xsl:when test="$EDITABLE='S' and $editable='S' and $validationTable!=''">
      	   	       Unidad:
	               <select>
	                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_unidadMedida</xsl:attribute>
	                  <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
	                  <xsl:if test="$isMandatory='N'">
	                     <option value="">&nbsp;</option>
	                  </xsl:if>
	                  <xsl:variable name="selectedValue" select="$unidadMedida"/>
		          <xsl:apply-templates select="Edicion/Opciones/Opcion">
                             <xsl:with-param name="selectedValue" select="$selectedValue"/>
                          </xsl:apply-templates>
	               </select>
	               <input type="hidden">
	                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tipoMedida</xsl:attribute>
	                    <xsl:choose>
	                    	<xsl:when test="$tipoMedida!=''">
	                    	    <xsl:attribute name="value"><xsl:value-of select="$tipoMedida" /></xsl:attribute>
	                        </xsl:when>
	                        <xsl:otherwise>
	                            <xsl:attribute name="value"><xsl:value-of select="$defaultTipoMedida" /></xsl:attribute>
	                        </xsl:otherwise>
	                    </xsl:choose>
	               </input>
	        </xsl:when>
	        <xsl:otherwise>
	        	<input type="hidden">
	                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tipoMedida</xsl:attribute>
	                    <xsl:choose>
	                    	<xsl:when test="$tipoMedida!=''">
	                    	    <xsl:attribute name="value"><xsl:value-of select="$tipoMedida" /></xsl:attribute>
	                        </xsl:when>
	                        <xsl:otherwise>
	                            <xsl:attribute name="value"><xsl:value-of select="$defaultTipoMedida" /></xsl:attribute>
	                        </xsl:otherwise>
	                    </xsl:choose>
	               </input>
	               <input type="hidden">
	                    <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_unidadMedida</xsl:attribute>
	                    <xsl:attribute name="value"><xsl:value-of select="$unidadMedida" /></xsl:attribute>
	               </input>
	        </xsl:otherwise>
	    </xsl:choose>
     </td>
   </tr>
  </table>

</xsl:template>


<!-- MostrarValorReferencia -->
<xsl:template name="MostrarValorReferencia">
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="style" select="Dato/Estilo"/>
  <xsl:variable name="isMultiple" select="Edicion/Multivalor"/>
  <xsl:variable name="refType" select="Edicion/Tipo_Referencia"/>
  <xsl:variable name="refIdsList">
    <xsl:for-each select="Edicion/Validacion/Lista_Descriptora">
        <xsl:if test="position() > 1 ">,</xsl:if>
        <xsl:value-of select="@id" />
    </xsl:for-each>
  </xsl:variable>

  <table style="margin-top:5px;margin-bottom:5px;border-collapse:collapse;">

    <xsl:attribute name="id">tabla_campo_<xsl:value-of select="$id" /></xsl:attribute>
    <xsl:if test="count(Dato/Valores/Valor)=0">
      <tr valign="top">
        <xsl:if test="$isMultiple!='S'">
        <td>
          <table cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <input type="text" size="50">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_1</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style" /></xsl:attribute>
                  <xsl:attribute name="onchange">javascript:changeReferenceInfo("ref_<xsl:value-of select="$id" />_1");</xsl:attribute>
                </input>
                <input type="hidden">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_1_tiporef</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tiporef</xsl:attribute>
                </input>
                <input type="hidden">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_1_idref</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_idref</xsl:attribute>
                </input>
              </td>
              <td width="5">&nbsp;</td>
              <td>
                <a><xsl:attribute name="href">javascript:popupReferencePage('ref_<xsl:value-of select="$id" />_1','<xsl:value-of select="$refType" />','<xsl:value-of select="$refIdsList" />');</xsl:attribute>
                  <image src="../images/buscar.gif" alt="Buscar" title="Buscar" class="imgTextTop" /></a>
              </td>
              <td>

	      	  <xsl:if test="Dato/Valores/Valor/@TieneFichaAsociada='S'">
		      	  <a><xsl:attribute name="href">javascript:showFicha('<xsl:value-of select="Dato/Valores/Valor/@IdRef" />','<xsl:value-of select="Dato/Valores/Valor/@TipoRef" />');</xsl:attribute>
		            <image src="../images/searchDoc.gif" alt="Ver Descripcion" title="Ver Descripcion" class="imgTextTop" /></a>
		  </xsl:if>
	      </td>
            </tr>
          </table>
        </td>
        </xsl:if>
      </tr>
    </xsl:if>
    <xsl:for-each select="Dato/Valores/Valor">

      <tr valign="top">
        <xsl:if test="$isMultiple='S'">
          <xsl:attribute name="id">tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/></xsl:attribute>
          <td width="30">
            <input type="checkbox" class="checkbox">
              <xsl:attribute name="name">eliminar_<xsl:value-of select="$id" /></xsl:attribute>
              <xsl:attribute name="value"><xsl:value-of select="position()" /></xsl:attribute>
              <xsl:attribute name="onclick">javascript:checkIfSelectedToBeRemoved(this,'tr_<xsl:value-of select="$id"/>_<xsl:value-of select="position()"/>')</xsl:attribute>
            </input>
          </td>
        </xsl:if>
        <td>
          <table cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <input type="text" size="50">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="position()" /></xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
                  <xsl:attribute name="style"><xsl:value-of select="$style" /></xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="." /></xsl:attribute>
                  <xsl:attribute name="onchange">javascript:changeReferenceInfo("ref_<xsl:value-of select="$id" />_<xsl:value-of select="position()" />");</xsl:attribute>
                </input>
                <input type="hidden">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="position()" />_tiporef</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tiporef</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@TipoRef" /></xsl:attribute>
                </input>
                <input type="hidden">
                  <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="position()" />_idref</xsl:attribute>
                  <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_idref</xsl:attribute>
                  <xsl:attribute name="value"><xsl:value-of select="@IdRef" /></xsl:attribute>
                </input>
              </td>
              <td width="5">&nbsp;</td>
              <td>
	         <a><xsl:attribute name="href">javascript:popupReferencePage('ref_<xsl:value-of select="$id" />_<xsl:value-of select="position()" />','<xsl:value-of select="$refType" />','<xsl:value-of select="$refIdsList" />');</xsl:attribute>
	            <image src="../images/buscar.gif" alt="Buscar" title="Buscar" class="imgTextTop" /></a>
	      </td>
	      <td width="5">&nbsp;</td>
	      <td>
	      	  <xsl:if test="@TieneFichaAsociada='S'">
		      	  <a><xsl:attribute name="href">javascript:showFicha('<xsl:value-of select="@IdRef" />','<xsl:value-of select="@TipoRef" />');</xsl:attribute>
		            <image src="../images/searchDoc.gif" alt="Ver Descripcion" title="Ver Descripcion" class="imgTextTop" /></a>
		  </xsl:if>
	      </td>

	    </tr>
          </table>
        </td>
      </tr>
    </xsl:for-each>

  </table>
</xsl:template>


<!-- MostrarValorReferenciaEnTabla -->
<xsl:template name="MostrarValorReferenciaEnTabla">
  <xsl:param name="rownum"/>
  <xsl:variable name="id" select="Edicion/Id"/>
  <xsl:variable name="editable" select="Edicion/Editable"/>
  <xsl:variable name="sistemaExterno" select="../../Sistema_Externo" />
  <xsl:variable name="refType" select="Edicion/Tipo_Referencia"/>
  <xsl:variable name="refIdsList">
    <xsl:for-each select="Edicion/Validacion/Lista_Descriptora">
        <xsl:if test="position() > 1 ">,</xsl:if>
        <xsl:value-of select="@id" />
    </xsl:for-each>
  </xsl:variable>
  <xsl:variable name="tieneFichaAsociada" select="Dato/Valores/Valor[$rownum]/@TieneFichaAsociada" />
  <table cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td style="border:0;width:99%">
        <input type="text">
          <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" /></xsl:attribute>
          <xsl:attribute name="name">campo_<xsl:value-of select="$id" /></xsl:attribute>
          <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo" /></xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]" /></xsl:attribute>
          <xsl:attribute name="onchange">javascript:changeReferenceInfo("ref_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" />");</xsl:attribute>
        </input>
        <input type="hidden">
          <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" />_tiporef</xsl:attribute>
          <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_tiporef</xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@TipoRef" /></xsl:attribute>
        </input>
        <input type="hidden">
          <xsl:attribute name="id">ref_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" />_idref</xsl:attribute>
          <xsl:attribute name="name">campo_<xsl:value-of select="$id" />_idref</xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="Dato/Valores/Valor[$rownum]/@IdRef" /></xsl:attribute>
        </input>
      </td>

      <td style="border:0;">
        <a><xsl:attribute name="href">javascript:popupReferencePage('ref_<xsl:value-of select="$id" />_<xsl:value-of select="$rownum" />','<xsl:value-of select="$refType" />','<xsl:value-of select="$refIdsList" />');</xsl:attribute>
        <image src="../images/buscar.gif" alt="Buscar" title="Buscar" class="imgTextTop" /></a>
      </td>
      <td style="border:0;">
      	  <xsl:if test="$tieneFichaAsociada='S'">
	      	  <a><xsl:attribute name="href">javascript:showFicha('<xsl:value-of select="Dato/Valores/Valor[$rownum]/@IdRef" />', '<xsl:value-of select="Dato/Valores/Valor[$rownum]/@TipoRef" />');</xsl:attribute>
	            <image src="../images/searchDoc.gif" alt="Ver Descripcion" title="Ver Descripcion" class="imgTextTop" /></a>
	  </xsl:if>
      </td>

    </tr>
  </table>
</xsl:template>

<!-- MostrarHipervínculoEnTabla -->
<xsl:template name="MostrarHipervinculoEnTabla">
  <xsl:param name="rownum"/>
  <xsl:if test="Dato/Valores/Valor[$rownum] != ''">
	  <a>
	    <xsl:if test="Dato/Estilo!=''">
	        <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
	    </xsl:if>
	    <xsl:attribute name="href"><xsl:value-of select="Dato/Valores/Valor[$rownum]"/></xsl:attribute>
		<image src="../images/searchDoc.gif" alt="Ver Enlace" title="Ver Enlace" class="imgTextTop" />
	  </a>
  </xsl:if>
</xsl:template>

<!-- MostrarHipervínculo -->
<xsl:template name="MostrarHipervinculo">
  <a>
    <xsl:if test="Dato/Estilo!=''">
        <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
    </xsl:if>
    <xsl:attribute name="href"><xsl:value-of select="Dato/Url"/></xsl:attribute>
    <xsl:if test="Dato/Target!=''">
        <xsl:attribute name="target"><xsl:value-of select="Dato/Target"/></xsl:attribute>
    </xsl:if>
    <xsl:value-of select="Dato/Texto"/>
  </a>
</xsl:template>


<!-- MostrarTextoEtiqueta -->
<xsl:template name="MostrarTextoEtiqueta">
  <span>
    <xsl:if test="Etiqueta/Estilo!=''">
        <xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/></xsl:attribute>
    </xsl:if>
    <xsl:value-of select="Etiqueta/Titulo"/>
    <xsl:if test="$EDITABLE='S' and Edicion/Obligatorio='S'">*</xsl:if>
  </span>
</xsl:template>


<!-- Valor -->
<xsl:template match="Valor">
  <xsl:if test="@Formato = 'S'">
      S.
  </xsl:if>
  <xsl:value-of select="."/>
  <xsl:if test="@Calificador != ''">
      (<xsl:value-of select="@Calificador"/>)
  </xsl:if>
  <xsl:choose>
      <xsl:when test="../../../Edicion/Mostrar_Unidad_Numero='N'">
      </xsl:when>
      <xsl:otherwise>
          <xsl:if test="@Unidad_Medida != ''">
              &nbsp;<xsl:value-of select="@Unidad_Medida"/>
          </xsl:if>
      </xsl:otherwise>
  </xsl:choose>
  <xsl:if test="@Descripcion != ''">
      &nbsp;-&nbsp;<xsl:value-of select="@Descripcion"/>
  </xsl:if>
  <xsl:if test="last() &gt; 1"><br/></xsl:if>
</xsl:template>

<!-- Formato -->
<xsl:template match="Formato">
  <xsl:param name="selectedValue"/>
  <option>
    <xsl:attribute name="value"><xsl:value-of select="@Tipo"/></xsl:attribute>
    <xsl:if test="@Tipo=$selectedValue">
      <xsl:attribute name="selected">true</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="."/>
  </option>
</xsl:template>

<!-- Opcion -->
<xsl:template match="Opcion">
  <xsl:param name="selectedValue"/>
  <option>
    <xsl:attribute name="value"><xsl:value-of select="@Titulo"/></xsl:attribute>
    <xsl:if test="@Titulo=$selectedValue">
      <xsl:attribute name="selected">true</xsl:attribute>
    </xsl:if>
    <xsl:value-of select="@Titulo"/>
  </option>
</xsl:template>

<!-- Guardar informacion del elemento -->
<xsl:template name="SaveDataElementInfo">
  <script>
    elemento = new ElementInfo();
    elemento.id = "<xsl:value-of select="Edicion/Id"/>";
    elemento.type = "<xsl:value-of select="Edicion/Tipo"/>";
    elemento.style = "<xsl:value-of select="Dato/Estilo"/>";
    elemento.mandatory = "<xsl:value-of select="Edicion/Obligatorio"/>";
    elemento.initialValue = "<xsl:value-of select="Edicion/Valor_Inicial"/>";
    elemento.defaultOptionValue = "<xsl:value-of select="Edicion/Opciones/Opcion[@ValorPorDefecto='true']/@Titulo"/>";
    <xsl:for-each select="Edicion/Opciones/Opcion">
      elemento.options[elemento.options.length] = "<xsl:value-of select="@Titulo"/>";
    </xsl:for-each>
    elemento.defaultFormatValue = "<xsl:value-of select="Edicion/Formatos/Formato[@ValorPorDefecto='true']/@Tipo"/>";
    <xsl:for-each select="Edicion/Formatos/Formato">
      elemento.formats[elemento.formats.length] = new ElementDateFormatInfo("<xsl:value-of select="@Tipo"/>", "<xsl:value-of select="@Sep"/>", "<xsl:value-of select="."/>");
    </xsl:for-each>
    elemento.refType = "<xsl:value-of select="Edicion/Tipo_Referencia"/>";
    <xsl:for-each select="Edicion/Validacion/Lista_Descriptora">
      elemento.refIdsLists[elemento.refIdsLists.length] = "<xsl:value-of select="@id"/>";
    </xsl:for-each>

    elemento.defaultTipoMedida = "<xsl:value-of select="Edicion/Medida/@Tipo"/>";

    elemento.defaultUnidadMedida = "<xsl:value-of select="Edicion/Medida/@Unidad"/>";
    elemento.mostrarTipoMedida = "<xsl:value-of select="Edicion/Mostrar_Tipo_Numero"/>";
    elemento.mostrarUnidadMedida = "<xsl:value-of select="Edicion/Mostrar_Unidad_Numero"/>";
    elemento.validationTable= "<xsl:value-of select="Edicion/Id_TblVld"/>";
    elemento.editable="<xsl:value-of select="Edicion/Editable"/>";
    <xsl:if test="count(Edicion/Tipo_Padre)>0">
       elemento.tipoPadre="<xsl:value-of select="Edicion/Tipo_Padre"/>";
    </xsl:if>
    elementsInfo.addFieldElementInfo(elemento);
  </script>
</xsl:template>

<!-- Guardar informacion de la tabla -->
<xsl:template name="SaveTableElementInfo">
  <script>
    elemento = new ElementInfo();
    elemento.id = "<xsl:value-of select="Edicion/Id"/>";
    <xsl:for-each select="Elementos_Tabla/Elemento">
      elemento.nestedElements[elemento.nestedElements.length] = "<xsl:value-of select="Edicion/Id"/>";
    </xsl:for-each>
    elementsInfo.addTableElementInfo(elemento);
  </script>
</xsl:template>

<!-- Guardar informacion de la tabla -->
<xsl:template name="MostrarDespegableCalificadores">
  <xsl:param name="name"/>
  <xsl:param name="selectedValue"/>
  <xsl:param name="shortedValue"/>
  <select>
    <xsl:attribute name="name"><xsl:value-of select="$name" /></xsl:attribute>
    <option value="">&nbsp;</option>
    <option value="ant">
      <xsl:if test="$selectedValue='ant'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">ant</xsl:when><xsl:otherwise>Anterior a</xsl:otherwise></xsl:choose>
    </option>
    <option value="pos">
      <xsl:if test="$selectedValue='pos'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">pos</xsl:when><xsl:otherwise>Posterior a</xsl:otherwise></xsl:choose>
    </option>
    <option value="apr">
      <xsl:if test="$selectedValue='apr'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">apr</xsl:when><xsl:otherwise>Aproximada</xsl:otherwise></xsl:choose>
    </option>
    <option value="con">
      <xsl:if test="$selectedValue='con'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">con</xsl:when><xsl:otherwise>Conocida</xsl:otherwise></xsl:choose>
    </option>
    <option value="sup">
      <xsl:if test="$selectedValue='sup'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">sup</xsl:when><xsl:otherwise>Supuesta</xsl:otherwise></xsl:choose>
    </option>
    <option value="sic">
      <xsl:if test="$selectedValue='sic'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">sic</xsl:when><xsl:otherwise>Tal y como se ha escrito</xsl:otherwise></xsl:choose>
    </option>
    <option value="a.C.">
      <xsl:if test="$selectedValue='a.C.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">a.C.</xsl:when><xsl:otherwise>Antes de Cristo</xsl:otherwise></xsl:choose>
    </option>
    <option value="p.m.">
      <xsl:if test="$selectedValue='p.m.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">p.m.</xsl:when><xsl:otherwise>Primera mitad</xsl:otherwise></xsl:choose>
    </option>
    <option value="s.m.">
      <xsl:if test="$selectedValue='s.m.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">s.m.</xsl:when><xsl:otherwise>Segunda mitad</xsl:otherwise></xsl:choose>
    </option>
    <option value="p.t.">
      <xsl:if test="$selectedValue='p.t.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">p.t.</xsl:when><xsl:otherwise>Primer tercio</xsl:otherwise></xsl:choose>
    </option>
    <option value="s.t.">
      <xsl:if test="$selectedValue='s.t.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">s.t.</xsl:when><xsl:otherwise>Segundo tercio</xsl:otherwise></xsl:choose>
    </option>
    <option value="u.t.">
      <xsl:if test="$selectedValue='u.t.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">u.t.</xsl:when><xsl:otherwise>Ultimo tercio</xsl:otherwise></xsl:choose>
    </option>
    <option value="c.">
      <xsl:if test="$selectedValue='c.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">c.</xsl:when><xsl:otherwise>Cerca</xsl:otherwise></xsl:choose>
    </option>
    <option value="p.">
      <xsl:if test="$selectedValue='p.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">p.</xsl:when><xsl:otherwise>Principios</xsl:otherwise></xsl:choose>
    </option>
    <option value="m.">
      <xsl:if test="$selectedValue='m.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">m.</xsl:when><xsl:otherwise>Mediados</xsl:otherwise></xsl:choose>
    </option>
    <option value="f.">
      <xsl:if test="$selectedValue='f.'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">f.</xsl:when><xsl:otherwise>Finales</xsl:otherwise></xsl:choose>
    </option>
    <option value="sf">
      <xsl:if test="$selectedValue='sf'"><xsl:attribute name="selected">true</xsl:attribute></xsl:if>
      <xsl:choose><xsl:when test="$shortedValue=1">sf</xsl:when><xsl:otherwise>Sin fecha</xsl:otherwise></xsl:choose>
    </option>
  </select>
</xsl:template>

</xsl:stylesheet>