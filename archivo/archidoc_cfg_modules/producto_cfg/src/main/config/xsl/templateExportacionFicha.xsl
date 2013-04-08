<?xml version="1.0" encoding="ISO-8859-1"?>

<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">

<xsl:variable name="EDITABLE" select="/Ficha_ISADG/@Editable"/>

<!-- Ficha_ISADG -->
<xsl:template match="Ficha_ISADG">
	<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
		<fo:layout-master-set>
			<fo:simple-page-master master-name="simple"
					page-height="29.7cm" 
					page-width="21cm"
					margin="1cm">
				
				<fo:region-body margin="1cm"/>
				<fo:region-before extent="2cm"/>
				<fo:region-after extent="2cm"/>
			</fo:simple-page-master>
		</fo:layout-master-set>
		
		<fo:page-sequence master-reference="simple" initial-page-number="1">
			<fo:static-content flow-name="xsl-region-before">
				<fo:block text-align="center" line-height="12pt" text-decoration="underline"
						  font-size="16pt" keep-together="always" font-weight="bold">
					DESCRIPCIÓN <xsl:value-of select="@Nombre_Ficha"/>				
				</fo:block>
			</fo:static-content>
			<fo:static-content flow-name="xsl-region-after">
				<fo:block text-align="center" line-height="12pt" font-size="10pt" margin-top="1.5cm" keep-together="always">
					Página <fo:page-number/>
				</fo:block>
			</fo:static-content>
			<fo:flow flow-name="xsl-region-body">
				<!--<fo:table text-align="left" table-layout="fixed" margin-top="0.5cm">
					<fo:table-column column-width="6cm"/>
					<fo:table-column column-width="12cm"/>
					<fo:table-body>
						<xsl:apply-templates select="Cabecera"/>
					</fo:table-body>
				</fo:table>-->
				<fo:table text-align="center" table-layout="fixed" margin-top="0.5cm">
					<fo:table-column column-width="6cm"/>
					<fo:table-column column-width="13cm"/>
					<fo:table-body>
						<xsl:apply-templates select="Areas/Area"/>
					</fo:table-body>
				</fo:table>
			</fo:flow>		
		</fo:page-sequence>
	</fo:root>	
</xsl:template>

<!--<xsl:template match="Cabecera">
	<fo:table-row>
		<fo:table-cell number-columns-spanned="2">
			<fo:block font-size="14pt" font-family="sans-serif" color="blue" 
					  text-align="left" font-weight="bold" margin-bottom="0.3cm" text-decoration="underline">
		        Datos del objeto descrito
		  	</fo:block>
	  	</fo:table-cell>
  	</fo:table-row>
  	<fo:table-row>   				
		<fo:table-cell border-style="solid" border-width="thin" margin-left="0.3cm">
			<fo:block font-size="9pt" font-family="sans-serif" text-align="left" font-weight="bold" margin-top="0.1cm">
				Tipo
			</fo:block>
		</fo:table-cell>
		<fo:table-cell border-style="solid" border-width="thin" margin-left="0.3cm">
			<fo:block font-size="9pt" font-family="sans-serif" text-align="left" margin-top="0.1cm">
				<xsl:value-of select="@Tipo"/>
			</fo:block>
		</fo:table-cell>
	</fo:table-row> 				
	<xsl:for-each select="Elemento_Cabecera">
		<fo:table-row>   				
			<fo:table-cell border-style="solid" border-width="thin" margin-left="0.3cm">
				<fo:block font-size="9pt" font-family="sans-serif" text-align="left" font-weight="bold" margin-top="0.1cm">
					<xsl:value-of select="Nombre"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell border-style="solid" border-width="thin" margin-left="0.3cm">
				<fo:block font-size="9pt" font-family="sans-serif" text-align="left" margin-top="0.1cm">
					<xsl:value-of select="Valor"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>						
	</xsl:for-each>       	
</xsl:template>-->

<!-- Areas -->
<xsl:template match="Areas/Area">
	<xsl:if test="count(.//Valor) &gt; 0 or .//Elemento/@Tipo='Hipervinculo'">
		<fo:table-row>
			<fo:table-cell number-columns-spanned="2">
				<fo:block font-size="14pt" 
			            font-family="sans-serif"
						color="blue"
			            line-height="20pt"			            
			            text-align="left"
			            font-weight="bold"
						text-decoration="underline"
						margin-top="0.5cm"
						margin-bottom="0.5cm">
			        <xsl:value-of select="Etiqueta/Titulo"/>
			  	</fo:block>
		  	</fo:table-cell>
	  	</fo:table-row> 				
		<xsl:for-each select="Elementos/Elemento">
   			<xsl:if test="count(.//Valor) &gt; 0 or @Tipo='Hipervinculo'">
   				<fo:table-row>   				
					<xsl:apply-templates select="." />
				</fo:table-row>				
				<xsl:if test="@Tipo='Cabecera'">		  				
					<xsl:for-each select="Elementos_Cabecera/Elemento">
						<xsl:if test="count(.//Valor) &gt; 0 or @Tipo='Hipervinculo'">
							<fo:table-row>
								<xsl:apply-templates select=".">
									<xsl:with-param name="cabecera">S</xsl:with-param>
								</xsl:apply-templates>
							</fo:table-row>
							<xsl:if test="@Tipo='Tabla' or @Tipo='TablaTextual'">																					
								<xsl:call-template name="CrearTabla">
									<xsl:with-param name="pos">1</xsl:with-param>
								</xsl:call-template>
							</xsl:if>
						</xsl:if>
					</xsl:for-each>		
				</xsl:if>
				<xsl:if test="@Tipo='Tabla' or @Tipo='TablaTextual'">																	
					<xsl:call-template name="CrearTabla">
						<xsl:with-param name="pos">1</xsl:with-param>
					</xsl:call-template>
				</xsl:if>		           			
    		</xsl:if>
   		</xsl:for-each>        	
	</xsl:if>	      	  	
</xsl:template>

<!-- Elementos -->
<xsl:template match="Elemento">
	<xsl:param name="cabecera"/>
	<fo:table-cell>	
		<xsl:choose>
			<xsl:when test="$cabecera='S'">
				<fo:block font-size="11pt" font-family="sans-serif" line-height="20pt"
           				  space-after.optimum="15pt" text-align="left" font-weight="bold" margin-left="1cm">						
					<xsl:value-of select="Etiqueta/Titulo"/>
				</fo:block>
			</xsl:when>
			<xsl:otherwise>
				<fo:block font-size="12pt" font-family="sans-serif" line-height="20pt"
           				  space-after.optimum="15pt" text-align="left" font-weight="bold">						
					<xsl:value-of select="Etiqueta/Titulo"/>
				</fo:block>
			</xsl:otherwise>
		</xsl:choose>
	</fo:table-cell>
	<fo:table-cell>
		<fo:block font-size="10pt" font-family="sans-serif" line-height="20pt"
         				  space-after.optimum="10pt" text-align="left">
			<xsl:choose>
				<xsl:when test="@Tipo='EtiquetaDato'">
					<xsl:call-template name="MostrarValoresEtiquetaDato"/>
			    </xsl:when>
			    <xsl:when test="@Tipo='Hipervinculo'">
			    	<xsl:value-of select="Dato/Texto"/>
			    </xsl:when>	
			</xsl:choose>
		</fo:block>							
	</fo:table-cell>		
</xsl:template>
        
<!-- Mostrar los valores de la tabla -->
<xsl:template name="CrearTabla">
	<xsl:param name="pos"/>
	<xsl:variable name="elementos" select="count(Elementos_Tabla/Elemento)"/>
	<xsl:variable name="valores" select="count(Elementos_Tabla/Elemento/Dato/Valores/Valor)"/>
	<xsl:variable name="num_filas" select="$valores div $elementos"/>
	<xsl:if test="$pos &lt;= $num_filas">
		<fo:table-row>
			<fo:table-cell margin-left="1.5cm">
				<fo:block font-size="9pt" font-family="sans-serif" text-align="left" font-weight="bold">
					<xsl:value-of select="$pos"/>:
				</fo:block>
			</fo:table-cell>
		</fo:table-row>						
		<fo:table-row margin-left="2cm">
			<fo:table-cell number-columns-spanned="2">			
				<fo:table text-align="left" table-layout="fixed" border-style="solid" border-width="thin" margin-bottom="0.5cm">				
					<fo:table-column column-width="4cm"/>
					<fo:table-column column-width="12cm"/>				
					<fo:table-body>														
						<xsl:for-each select="Elementos_Tabla/Elemento">							
							<xsl:choose>		
								<xsl:when test="Dato/Valores/Valor[@Orden=$pos] != ''">											
									<fo:table-row>							
										<fo:table-cell border-style="solid" border-width="thin" margin-left="0.5cm">								
											<fo:block font-size="9pt" font-family="sans-serif" text-align="left" font-weight="bold" margin-top="0.1cm">
												<xsl:value-of select="Etiqueta/Titulo"/>																																																																
											</fo:block>
										</fo:table-cell>
										<fo:table-cell border-style="solid" border-width="thin" margin-left="0.5cm">								
											<fo:block font-size="9pt" font-family="sans-serif" text-align="left" margin-top="0.1cm">										
												<xsl:apply-templates select="Dato/Valores/Valor[@Orden=$pos]"/>																																																					
											</fo:block>
										</fo:table-cell>
									</fo:table-row>	
								</xsl:when>								
							</xsl:choose>										
						</xsl:for-each>					
					</fo:table-body>
				</fo:table>
			</fo:table-cell>
		</fo:table-row>
		<xsl:call-template name="CrearTabla">
			<xsl:with-param name="pos"><xsl:value-of select="$pos+1"/></xsl:with-param>
		</xsl:call-template>
	</xsl:if>	
</xsl:template>

<!-- MostrarValoresEtiquetaDato -->
<xsl:template name="MostrarValoresEtiquetaDato">
	<fo:list-block space-before="0.1cm">
		<xsl:for-each select="Dato/Valores/Valor">
			<fo:list-item space-after="0.1cm">
				<fo:list-item-label>
					<fo:block/>
				</fo:list-item-label>
				<fo:list-item-body font-size="10pt">
					<fo:block>
						<xsl:apply-templates select="." />
					</fo:block>
				</fo:list-item-body>
			</fo:list-item>
		</xsl:for-each>
	</fo:list-block>    		
</xsl:template>

<!-- Valor -->
<xsl:template match="Valor">
	<xsl:if test="@Formato = 'S'">S.</xsl:if>
	<xsl:value-of select="." disable-output-escaping="yes" /><xsl:text> </xsl:text>
  	<xsl:if test="@Calificador != ''">
  		<xsl:value-of select="@Calificador"/><xsl:text> </xsl:text>
  	</xsl:if>  
  	<xsl:if test="Edicion/Mostrar_Unidad_Numero!='N'">
		<xsl:if test="@Unidad_Medida != ''">     
    		<xsl:value-of select="@Unidad_Medida"/><xsl:text> </xsl:text>
  		</xsl:if>
  	</xsl:if>
</xsl:template>

</xsl:stylesheet>