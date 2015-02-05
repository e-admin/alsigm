<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>

<xsl:variable name="lang.relacion" select="'Relacion'"/>
<xsl:variable name="lang.TTitular" select="'Titular de la licencia'"/>
<xsl:variable name="lang.TPropietario" select="'Propietario del negocio'"/>

<xsl:template name="Actividad_Solicitante_Relacion_Select">

	<div class="col">
		<label class="gr">
			<xsl:attribute name="style">position: relative; width:200px;</xsl:attribute>
			<xsl:value-of select="$lang.relacion"/>:<font color="#950000">*</font>
		</label>
		<xsl:variable name="clas" select="Datos_Registro/datos_especificos/Relacion"/>
		<select class="gr">
			<xsl:attribute name="style">position: relative; width:400px; height:20px;</xsl:attribute>
			<xsl:attribute name="name">relacion</xsl:attribute>
			<xsl:attribute name="id">relacion</xsl:attribute>
			<option></option>
			<xsl:choose>
		       <xsl:when test="$clas='INT'">
		           <option value="INT" selected="selected"><xsl:value-of select="$lang.TTitular"/></option>
		       </xsl:when>
		       <xsl:otherwise>
		           <option value="INT"><xsl:value-of select="$lang.TTitular"/></option>
		       </xsl:otherwise>
		    </xsl:choose>
       			<xsl:choose>
       			   <xsl:when test="$clas='PROP'">
         			      <option value="PROP" selected="selected"><xsl:value-of select="$lang.TPropietario"/></option>
       			   </xsl:when>
       		       <xsl:otherwise>
         				  <option value="PROP"><xsl:value-of select="$lang.TPropietario"/></option>
       			   </xsl:otherwise>
       			</xsl:choose>
		</select>
	</div>

</xsl:template>

</xsl:stylesheet>