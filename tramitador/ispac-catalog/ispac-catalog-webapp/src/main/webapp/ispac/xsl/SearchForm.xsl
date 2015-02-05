<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

<xsl:template match="/">
	<table>
		<tr>
			<td>
				<nobr>
					<input  type="radio" name='domain' checked="1" value="1"></input>
				  	<span class="forms"> <b>Todos los expedientes </b></span>
				  </nobr>		
	  	</td>	  			
		</tr>
		<tr>
			<td>
				<nobr>
					<input type="radio" name='domain' value="2"></input>
					<span class="forms"> <b>Sólo los expedientes de mi responsabilidad</b></span>
				</nobr>		
			</td>
		</tr>
	</table>
	<p></p>
	<table>	
		<xsl:apply-templates />
		<tr><td><br></br></td></tr>
		<tr>
			<td colspan="7" align="center">
			<input type="button" value="Buscar" class="form_button" onclick="javascript:submitform();"/>
			<!--<a href="javascript:submitform();" class="forms">Buscar</a>--></td>
		</tr>
		<tr>
			<input type='hidden' name='displaywidth'>
				<xsl:attribute name="value">
					<xsl:value-of select="queryform/@displaywidth" />
				</xsl:attribute>																							
			</input>		
		</tr>
	</table>
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
		<td width="15px"></td>
		<xsl:apply-templates select="datatype" />
		<td width="15px"></td>
		<xsl:choose>
			<xsl:when test="controltype[.='text']">
				<td>
					<input type="text" class="input"> 
						<xsl:attribute name="name">
							<xsl:text>values(</xsl:text>
							<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
							<xsl:text>)</xsl:text>
						</xsl:attribute>									
						<xsl:attribute name="size">
							<xsl:value-of select="controltype/@size" />
						</xsl:attribute>
						<xsl:attribute name="maxlength">
							<xsl:value-of select="controltype/@maxlength" />
						</xsl:attribute>
					</input>
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
			 	<textarea class="input">
					<xsl:attribute name="name">
						<xsl:text>values(</xsl:text>				 		
						<xsl:value-of select="../tablename" />:<xsl:value-of select="columnname" />
						<xsl:text>)</xsl:text>							
					</xsl:attribute>									  
					<xsl:attribute name="cols">
				 	  <xsl:value-of select="controltype/@cols" />
					</xsl:attribute>
				 	<xsl:attribute name="rows">
				 		<xsl:value-of select="controltype/@rows" />
				 	</xsl:attribute>
				 	<xsl:text>&#032;</xsl:text>
				</textarea>
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
	<td class="forms" align="right">
		<xsl:value-of select="."/>
	</td>
</xsl:template>

<xsl:template match="datatype">
		<xsl:choose>
	  	<xsl:when test=".='string'">
	  		<td align="left">
	    		<b>	<xsl:text>&#160;&#160;=</xsl:text></b>
	    	</td>
	    </xsl:when>
	    <xsl:otherwise>
	  		<td align="center">
					<select>
						<xsl:attribute name="name">
							<xsl:text>operators(</xsl:text>
							<xsl:value-of select="../../tablename"/>:<xsl:value-of select="../columnname" />
							<xsl:text>)</xsl:text>
						</xsl:attribute>
						<option value="=" selected="yes">=</option>
						<option value="&gt;">&gt;</option>
						<option value="&gt;=">&gt;=</option>
						<option value="&lt;">&lt;</option>
						<option value="&lt;=">&lt;=</option>
					</select>
				</td>
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
