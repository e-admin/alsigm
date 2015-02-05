<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="4.0"
encoding="iso-8859-1" indent="yes"/>

<xsl:template match="/">
  <xsl:apply-templates/>
</xsl:template>


<!-- /Ficha_ISADG -->
<xsl:template match="Ficha_ISADG">
  <xsl:apply-templates select="Areas/Area" />
</xsl:template>


<!-- /Ficha_ISADG/Areas/Area -->
<xsl:template match="Area">
  <xsl:if test="count(.//Valor) &gt; 0 or .//Elemento/@Tipo='Hipervinculo'">
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
        <xsl:for-each select="Elementos/Elemento">
          <xsl:if test="count(.//Valor) &gt; 0 or @Tipo='Hipervinculo'">
            <xsl:apply-templates select="." />
          </xsl:if>
        </xsl:for-each>
      </table>
    </div>
    <div class="separador8">&nbsp;</div>
  </xsl:if>
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
    <td class="tdDatosFicha">
      <xsl:choose>
        <xsl:when test="@Tipo='EtiquetaDato'">
          <xsl:attribute name="class">tdDatosFicha</xsl:attribute>
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
          <xsl:call-template name="MostrarHipervinculo"/>
        </xsl:when>
        <xsl:when test="@Tipo='Cabecera'">
          <xsl:attribute name="class">tdDatosFichaSinBorde</xsl:attribute>
          &nbsp;
        </xsl:when>
        <xsl:when test="@Tipo='Tabla' or @Tipo='TablaTextual'">
          <xsl:attribute name="class">tdDatosFicha</xsl:attribute>
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
    <xsl:for-each select="Elementos_Cabecera/Elemento">
      <xsl:if test="count(.//Valor) &gt; 0 or @Tipo='Hipervinculo'">
        <xsl:apply-templates select=".">
          <xsl:with-param name="inCabecera" select="$varInCabecera"/>
        </xsl:apply-templates>
      </xsl:if>
    </xsl:for-each>
    <tr>
      <td class="tdTituloFicha" colspan="3"><img src="../images/pixel.gif"/></td>
    </tr>
  </xsl:if>
</xsl:template>


<!-- Mostrar los valores de la tabla -->
<xsl:template name="MostrarTabla">
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

  <table>
    <xsl:attribute name="class"><xsl:value-of select="$tableClass"/></xsl:attribute>
    <xsl:if test="not(Mostrar_Cabeceras) or Mostrar_Cabeceras='S'">
    <thead>
      <tr>
        <xsl:for-each select="Elementos_Tabla/Elemento">
        <th>
          <xsl:attribute name="style"><xsl:value-of select="Etiqueta/Estilo"/></xsl:attribute>
          <xsl:value-of select="Etiqueta/Titulo"/>
        </th>
        </xsl:for-each>
      </tr>
    </thead>
    </xsl:if>
    <tbody>
      <xsl:for-each select="Elementos_Tabla/Elemento/Dato/Valores">
        <xsl:sort select="count(Valor)" order="descending"/>
        <xsl:if test="position() = 1">
          <xsl:for-each select="Valor">
              <xsl:call-template name="MostrarFilaTabla">
                <xsl:with-param name="rownum" select="position()"/>
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
  <xsl:param name="tableType"/>

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
    <xsl:for-each select="../../../../Elemento">
    <td>
      <xsl:attribute name="style"><xsl:value-of select="Dato/Estilo"/></xsl:attribute>
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
    <xsl:when test="$inTable='S'">
       <xsl:choose>
	       <xsl:when test="Dato/Valores/Valor[$rownum]/@TieneFichaAsociada='S'">
	       		<a><xsl:attribute name="href">javascript:showFicha('<xsl:value-of select="Dato/Valores/Valor[$rownum]/@IdRef" />','<xsl:value-of select="Dato/Valores/Valor[$rownum]/@TipoRef" />');</xsl:attribute><xsl:apply-templates select="Dato/Valores/Valor[$rownum]" /></a>
	       </xsl:when>
	       <xsl:otherwise>
			    <xsl:if test="@Tipo='Hipervinculo'"><!-- Hipervinculo -->
				    <xsl:call-template name="MostrarHipervinculoEnTabla">
			            <xsl:with-param name="rownum" select="$rownum"/>
			        </xsl:call-template>
			    </xsl:if>
				<xsl:if test="@Tipo!='Hipervinculo'">
					<xsl:apply-templates select="Dato/Valores/Valor[$rownum]" />
				</xsl:if>
	       </xsl:otherwise>
       </xsl:choose>
    </xsl:when>
    <xsl:otherwise>
		<xsl:for-each select="Dato/Valores/Valor">
		<xsl:choose>
			<xsl:when test="@TieneFichaAsociada='S'">
				<a><xsl:attribute name="href">javascript:showFicha('<xsl:value-of select="@IdRef" />','<xsl:value-of select="@TipoRef" />');</xsl:attribute><xsl:value-of select="." /></a><br/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." /><br/>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:for-each>
    </xsl:otherwise>
  </xsl:choose>

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
  </span>
</xsl:template>


<!-- Valor -->
<xsl:template match="Valor">
  <xsl:if test="@Formato = 'S'">
      S.
  </xsl:if>
  <xsl:value-of select="." disable-output-escaping="yes" />
  <xsl:if test="@Calificador != ''">
      &nbsp;<xsl:value-of select="@Calificador"/>
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

</xsl:stylesheet>