<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="ISO-8859-1" method="html"/>
	<xsl:variable name="lang.datosContratante" select="'Datos del Contratante'"/>
	<xsl:variable name="lang.datosContratado" select="'Datos del Contratado'"/>
	<xsl:variable name="lang.docIdentidad" select="'Documento de identidad'"/>
	<xsl:variable name="lang.nombre" select="'Nombre'"/>
	<xsl:variable name="lang.relacion" select="'Relación'"/>
	<xsl:variable name="lang.domicilio" select="'Domicilio a efectos de notificación'"/>
	<xsl:variable name="lang.localidad" select="'Localidad'"/>
	<xsl:variable name="lang.provincia" select="'Provincia'"/>
	<xsl:variable name="lang.cp" select="'Código postal'"/>
	<xsl:variable name="lang.datosContrato" select="'Datos del Contrato'"/>
	<xsl:variable name="lang.tipoContrato" select="'Tipo de Contrato'"/>
	
	<xsl:variable name="lang.formaAdjudicacion" select="'Forma de Adjudicación'"/>
	
	<xsl:variable name="lang.presupuestoMax" select="'Presupuesto máximo'"/>
	<xsl:variable name="lang.precioAdjudicacion" select="'Precio de la Adjudicación'"/>
	<xsl:variable name="lang.aplicacion" select="'Aplicación presupuestaria'"/>
	<xsl:variable name="lang.plazo" select="'Plazo de Ejecución'"/>
	<xsl:variable name="lang.unidades" select="'Unidades de Plazo'"/>
	<xsl:variable name="lang.garantiaProv" select="'Garantía Provisional'"/>
	<xsl:variable name="lang.garantiaDef" select="'Garantía Definitiva'"/>
	<xsl:variable name="lang.clasificacion" select="'Clasificación'"/>
	<xsl:variable name="lang.fechaFinEjecucion" select="'Fecha final de Ejecución'"/>
	<xsl:variable name="lang.fechaFinGarantia" select="'Fecha final de Garantía'"/>
	
	<xsl:variable name="lang.telefono" select="'Teléfono'"/>
	<xsl:variable name="lang.email" select="'Correo electrónico'"/>	
	
	<xsl:template match="/">
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosContratante"/></h1>
   		</div>
   		<br/>
		<div class="col" style="height:25px;">
			<label class="gr" style="position: relative; width:200px;text-indent:10px;">
				<xsl:value-of select="$lang.docIdentidad"/>:	
			</label>
			<label class="gr">
				<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Documento_Identificacion/Numero"/>
			</label>
			<br/>
		</div>
		<div class="col" style="height:20px">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.nombre"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Genericos/Remitente/Nombre"/>
			</label>
			<br/>
		</div>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descripcion">
			<div class="col" style="height: 20px;">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.relacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Descripcion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion">
			<div class="col" style="height: 20px;">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		
		<xsl:variable name="email" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>		
		<xsl:choose>
        <xsl:when test="$email=''"></xsl:when>
        <xsl:otherwise>
		<div class="col">
			<label class="gr" style="position: relative; width:200px;text-indent:10px">
				<xsl:value-of select="$lang.email"/>:	
			</label>
			<label class="gr" style="position: relative; width:400px;">
				<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Solicitante"/>
			</label>
			<br/>
		</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:variable name="tlfCont" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Contratado"/>		
		<xsl:variable name="emailCont" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Contratado"/>		

		<br/>
		<div class="submenu">
   			<h1><xsl:value-of select="$lang.datosContratado"/></h1>
   		</div>
   		<br/>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Contratado">
			<div class="col" style="height:25px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.docIdentidad"/>:	
				</label>
				<label class="gr">
					<xsl:attribute name="style">position: relative; width:400px;</xsl:attribute>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Documento_Identidad_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Contratado">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.nombre"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Nombre_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Contratado_Descripcion">
			<div class="col" style="height: 20px;">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.relacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Relacion_Contratado_Descripcion"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Contratado">
			<div class="col" style="height: 20px;">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.domicilio"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Domicilio_Notificacion_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Contratado">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.localidad"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Localidad_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Contratado">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.provincia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Provincia_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:if test="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Contratado">
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.cp"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Codigo_Postal_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:if>
		<xsl:choose>
        <xsl:when test="$tlfCont=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.telefono"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Telefono_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$emailCont=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.email"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Email_Contratado"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		<br/>
		
		<xsl:variable name="tipoCont" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tipo_Contrato"/>		
		<xsl:variable name="formAdj" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Forma_Adjudicacion"/>		
		<xsl:variable name="undPlz" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Unidades_Plazo_Descripcion"/>		
		<xsl:variable name="presMax" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Presupuesto_Maximo"/>	
		<xsl:variable name="precAdj" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Precio_Adjudicacion"/>	
		
		<!-- 
		<xsl:variable name="appPres" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Aplicacion_Presupuestaria"/>		
		<xsl:variable name="garProv" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Garantia_Provisional"/>		
		<xsl:variable name="garDef" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Garantia_Definitiva"/>		
		<xsl:variable name="clas" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Clasificacion"/>		
		<xsl:variable name="fchEjec" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Final_Ejecucion"/>		
		<xsl:variable name="fchGar" select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Final_Garantia"/>	
		
		 and $appPres='' and $garProv='' and $garDef='' and $clas='' and $fchEjec='' and $fchGar=''	
		 -->
		<xsl:choose>
        <xsl:when test="$tipoCont='' and $formAdj='' and $presMax='' and $precAdj='' and $undPlz=''"></xsl:when>
        <xsl:otherwise>
			<div class="submenu">
	   			<h1><xsl:value-of select="$lang.datosContrato"/></h1>
	   		</div>
	   		<br/>
   		</xsl:otherwise>
   		</xsl:choose>
   		
		<xsl:choose>
        <xsl:when test="$tipoCont=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:25px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.tipoContrato"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Tipo_Contrato"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$formAdj=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.formaAdjudicacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Forma_Adjudicacion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$presMax=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.presupuestoMax"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Presupuesto_Maximo"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$precAdj=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.precioAdjudicacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Precio_Adjudicacion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<!-- 
		<xsl:choose>
        <xsl:when test="$appPres=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.aplicacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Aplicacion_Presupuestaria"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		 -->
		
		<xsl:choose>
        <xsl:when test="$undPlz=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.plazo"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Plazo_Ejecucion"/>
					<img src="img/nada.gif" width="5" height="1" bgcolor="#ffffff"/>
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Unidades_Plazo_Descripcion"/>
				</label>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<!-- 
		<xsl:choose>
        <xsl:when test="$garProv=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.garantiaProv"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Garantia_Provisional"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$garDef=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.garantiaDef"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Garantia_Definitiva"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$clas=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.clasificacion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Clasificacion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$fchEjec=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.fechaFinEjecucion"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Final_Ejecucion"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		
		<xsl:choose>
        <xsl:when test="$fchGar=''"></xsl:when>
        <xsl:otherwise>
			<div class="col" style="height:20px">
				<label class="gr" style="position: relative; width:200px;text-indent:10px">
					<xsl:value-of select="$lang.fechaFinGarantia"/>:	
				</label>
				<label class="gr" style="position: relative; width:400px;">
					<xsl:value-of select="Solicitud_Registro/Datos_Firmados/Datos_Especificos/Fecha_Final_Garantia"/>
				</label>
				<br/>
			</div>
		</xsl:otherwise>
		</xsl:choose>
		 <br/> -->
		<br/>
	</xsl:template>
</xsl:stylesheet>