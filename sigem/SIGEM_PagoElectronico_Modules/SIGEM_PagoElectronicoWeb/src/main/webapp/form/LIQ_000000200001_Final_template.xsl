<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<!-- ********** Raíz -->
<xsl:template name="Raíz" match="/">      
   
	<div style="position:relative;left:50px;top:0px;width:690px;height:300px;">      
			<div class="submenu3" style="position:relative;left:0px;top:00px;width:687px;">
       	<input class="submenu3" type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:0px;width:200px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
            <xsl:attribute name="value"><xsl:value-of select="$lang.justificante"/></xsl:attribute>
      </input>                        				
      </div>

       <div class="cuadro" style="position:relative;left:0px;top:0px;width:655px;height:200px;">
       <div style="position:relative;left:50px;top:20px;">
	       <xsl:value-of select="$lang.texto01"/>
       </div>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:260px;top:70px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
            <xsl:attribute name="value"><xsl:value-of select="$lang.referencia"/></xsl:attribute>
      </input>            

      <input id="referencia" type="text" name="referencia"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:260px;top:90px;width:100px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/REFERENCIA"/>   
	      </xsl:attribute>            
      </input>

       <div style="position:relative;left:50px;top:100px;">
	       <xsl:value-of select="$lang.texto02"/>
       </div>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:170px;top:160px;width:300px;
                   text-align:center;border-style:none"
            readOnly="true" tabIndex="-1">
            <xsl:attribute name="value"><xsl:value-of select="$lang.nrc"/></xsl:attribute>
      </input>
      <input id="referencia" type="text" name="referencia"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:170px;top:180px;width:300px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/NRC"/>   
	      </xsl:attribute>            
      </input>

       <div style="position:relative;left:0px;top:200px;">
			<xsl:value-of select="$lang.texto03"/>
       </div>

       </div>
</div>
 
</xsl:template> <!-- Raíz -->
</xsl:stylesheet>