<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="4.0" encoding="iso-8859-1" indent="yes"/>

<xsl:template match="/">
  	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="transferencia/validacion">
	<xsl:call-template name="MostrarValidacion"/>
</xsl:template>

<xsl:template match="transferencia/carga">
	<xsl:call-template name="MostrarCarga"/>
</xsl:template>

<xsl:template match="transferencia/actualizar_rel_entrega">
	<xsl:call-template name="MostrarActualizarRelEntrega"/>
</xsl:template>

<xsl:template match="transferencia/error">
	<xsl:call-template name="MostrarErrorGeneral" />
</xsl:template>

<xsl:template name="MostrarValidacion">
	<xsl:param name="id">Validacion</xsl:param>
	<xsl:call-template name="MostrarCabecera">
		<xsl:with-param name="titulo">Proceso de Validación</xsl:with-param>
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
	<xsl:call-template name="MostrarFormulario">
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="MostrarCarga">
	<xsl:param name="id">Carga</xsl:param>
	<xsl:call-template name="MostrarCabecera">
		<xsl:with-param name="titulo">Proceso de Carga</xsl:with-param>
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
	<xsl:call-template name="MostrarFormulario">
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="MostrarActualizarRelEntrega">
	<xsl:param name="id">RelEntrega</xsl:param>
	<xsl:call-template name="MostrarCabecera">
		<xsl:with-param name="titulo">Relación de Entrega</xsl:with-param>
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
	<xsl:call-template name="MostrarFormulario">
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="MostrarErrorGeneral">
	<xsl:param name="id">Error</xsl:param>
	<xsl:call-template name="MostrarCabecera">
		<xsl:with-param name="titulo">Errores generales</xsl:with-param>
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
	<xsl:call-template name="MostrarError">
		<xsl:with-param name="id" select="$id"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="MostrarCabecera">
		<xsl:param name="titulo"/>
		<xsl:param name="id"/>
		<div class="separador8">&nbsp;</div>
		<div class="cabecero_bloque">
			<table cellspacing="0" cellpadding="0" class="w98m1">
				<tr>
					<td align="left" class="etiquetaAzul12Bold"><xsl:value-of select="$titulo"/>&nbsp;</td>
					<td align="right">
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<a>
										<xsl:attribute name="href">javascript:showHideDiv('<xsl:value-of select="$id"/>');</xsl:attribute>
										<img class="imgTextBottom" src="../images/up.gif" title="Replegar" alt="Replegar">
											<xsl:attribute name="id">img<xsl:value-of select="$id"/></xsl:attribute>	
										</img>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
</xsl:template>
	
<xsl:template name="MostrarFormulario">
	<xsl:param name="id"/>
	<div class="bloque" style="display:block;">
		<xsl:attribute name="id">div<xsl:value-of select="$id"/></xsl:attribute>	
		<table cellspacing="0" cellpadding="0" class="formulario">
			<tr>
				<td width="50px"></td><td width="200px"></td><td></td>
			</tr>
			<xsl:call-template name="MostrarExpedientesErrores"/>
			<xsl:call-template name="MostrarErrores"/>
			<xsl:call-template name="MostrarAvisos"/>			
		</table>
	</div>
</xsl:template>

<xsl:template name="MostrarExpedientesErrores">
		<xsl:if test="count(num_expedientes_totales)>0" >
			<tr>
				<td width="250px" colspan="2" class="tdTituloFicha"><span>Número de expedientes:</span></td>
				<td class="tdDatosFicha"><xsl:value-of select="num_expedientes_totales"/></td>
			</tr>
		</xsl:if>
		<xsl:if test="count(errores_en_expedientes)>0" >
				<tr>
					<td width="250px" colspan="2" class="tdTituloFicha"><span>Errores en expedientes:</span></td>
					<td class="tdDatosFicha"><xsl:value-of select="errores_en_expedientes"/></td>
				</tr>
		</xsl:if>		
</xsl:template>
			
<xsl:template name="MostrarErrores">
	<xsl:if test="count(error)>0" >
		<tr>
			<td>&nbsp;</td>
			<td class="tdTituloFicha"><span>Errores:</span></td>
			<td class="tdDatosFicha">
				<table width="99%" class="tablaFicha">
					<thead>
						<tr>
							<th style="width:5%">Orden</th>
							<th style="width:35%">Número de Expediente</th>
							<th style="width:10%">Código</th>
							<th style="width:50%">Descripción</th>
						</tr>
					</thead>
					<xsl:for-each select="error">
						<tr>
							<td style="color: Red; font-weight: bold;"><xsl:value-of select="orden"/></td>
							<td style="color: Red; font-weight: bold;"><xsl:value-of select="numeroExpediente"/></td>
							<td style="color: Red; font-weight: bold;"><xsl:value-of select="codigo"/></td>
							<td style="color: Red; font-weight: bold;"><xsl:value-of select="msg"/></td>
						</tr>
					</xsl:for-each>
				</table>
			</td>
		</tr>
	</xsl:if>
</xsl:template>

<xsl:template name="MostrarAvisos">
		<xsl:if test="count(aviso)>0" >
			<tr>
				<td>&nbsp;</td>
				<td class="tdTituloFicha"><span>Avisos:</span></td>
				<td class="tdDatosFicha">
					<table width="99%" class="tablaFicha">
						<thead>
							<tr>
								<th style="width:50%">Descripción</th>
							</tr>
						</thead>
						<xsl:for-each select="aviso">
							<tr>
								<td><xsl:value-of select="."/></td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</xsl:if>
</xsl:template>

<xsl:template name="MostrarError">
	<xsl:param name="id"/>
	<div class="bloque" style="display:block;">
		<xsl:attribute name="id">div<xsl:value-of select="$id"/></xsl:attribute>	
		<table cellspacing="0" cellpadding="0" class="formulario">
			<tr>
				<td width="50px"></td><td width="200px"></td><td></td>
			</tr>
			<tr>
					<td>&nbsp;</td>
					<td class="tdTituloFicha"><span>Error:</span></td>
					<td class="tdDatosFicha">
						<table width="99%" class="tablaFicha">
							<thead>
								<tr>
									<th style="width:35%">Número de Expediente</th>
									<th style="width:10%">Código</th>
									<th style="width:50%">Descripción</th>
								</tr>
							</thead>
								<tr>
									<td style="color: Red; font-weight: bold;"><xsl:value-of select="numeroExpediente"/></td>
									<td style="color: Red; font-weight: bold;"><xsl:value-of select="codigo"/></td>
									<td style="color: Red; font-weight: bold;"><xsl:value-of select="msg"/></td>
								</tr>
						</table>
					</td>
				</tr>
		</table>
	</div>				
</xsl:template>

</xsl:stylesheet>