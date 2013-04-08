<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<!-- ********** Raíz -->
<xsl:template name="Raíz" match="/">      
   
   <xsl:call-template name="Funciones"/>
   
			<input type="hidden" name="idEntidadEmisora">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/ID_ENTIDAD"/>   
	      </xsl:attribute>            				
			</input>
			<input type="hidden" name="idTasa">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/TASA/ID_TASA"/>   
	      </xsl:attribute>            				
			</input>			
			<input type="hidden" name="ejercicio">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/EJERCICIO"/>   
	      </xsl:attribute>            				
			</input>	

      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:0px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
            <xsl:attribute name="value"><xsl:value-of select="$lang.periodo"/></xsl:attribute>
      </input>
      <input id="idPeriodo" type="text" name="periodo"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:20px;width:150px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/INICIO_PERIODO"/> - <xsl:value-of select="PAGO/LIQUIDACION/FIN_PERIODO"/>   	         
	      </xsl:attribute>            
      </input>


      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:160px;top:0px;width:70px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.emisora"/></xsl:attribute>            
	</input>            
      <input id="idPeriodo" type="text" name="idEntidad"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:160px;top:20px;width:70px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/ID_ENTIDAD"/>   
	      </xsl:attribute>            
      </input>


      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:240px;top:0px;width:50px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.modelo"/></xsl:attribute>            
	</input>            
      <input id="idPeriodo" type="text" name="idEntidad"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:240px;top:20px;width:40px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1"
            value="1">
      </input>


      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:290px;top:0px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.referencia"/></xsl:attribute>                
	</input>              
      <input id="referencia" type="text" name="referencia"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:290px;top:20px;width:100px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/REFERENCIA"/>   
	      </xsl:attribute>            
      </input>


      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:400px;top:0px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.identificacion"/></xsl:attribute>                
	</input>              
      <input id="referencia" type="text" name="identificacion"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:400px;top:20px;width:100px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/IDENTIFICADOR2"/>   
	      </xsl:attribute>            
      </input>


      <input type="text" 
            style="font-family:Verdana;font-size:8pt;font-weight:bold;
                  position:absolute;left:510px;top:0px;width:100px;
                  text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.importe"/></xsl:attribute>                
	</input>                 		            
      <input id="Ctrl_Provincia" type="text"
            style="font-family:Verdana;font-size:10pt;font-weight:normal;
                  position:absolute;left:510px;top:20px;width:100px;
                  text-align:right;border-style:solid;border-width:1px;padding-left:5px;"
            tabIndex="9" readOnly="true" >
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/IMPORTE"/>   
	      </xsl:attribute>            
      </input>


      <input type="text" 
            style="font-family:Verdana;font-size:8pt;font-weight:bold;
                  position:absolute;left:210px;top:50px;width:400px;
                  text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.concepto"/></xsl:attribute>                
	</input>               
      <input id="idConcepto" type="text"
            style="font-family:Verdana;font-size:10pt;font-weight:normal;
                  position:absolute;left:210px;top:70px;width:400px;
                  text-align:left;border-style:solid;border-width:1px;padding-left:5px;"
            tabIndex="8" readOnly="true" >
      <xsl:attribute name="value">
         <xsl:value-of select="PAGO/LIQUIDACION/TASA/NOMBRE_TASA"/>   
      </xsl:attribute>
     </input> 


      <input type="text" 
            style="font-family:Verdana;font-size:8pt;font-weight:bold;
                  position:absolute;left:0px;top:50px;width:200px;
                  text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.ayuntamiento"/></xsl:attribute>                
	</input>               
      <input id="idConcepto" type="text"
            style="font-family:Verdana;font-size:10pt;font-weight:normal;
                  position:absolute;left:0px;top:70px;width:200px;
                  text-align:left;border-style:solid;border-width:1px;padding-left:5px;"
            tabIndex="8" value="SAN JOSÉ" readOnly="true" >
     </input> 
     
     
      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:100px;width:615px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.desc"/></xsl:attribute>                
	</input>                   
      <input id="Ctrl_NIFRem" type="text" name="datosEspecificos"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:120px;width:615px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/DATOS_ESPECIFICOS/DETALLE"/>   
	      </xsl:attribute>            
      </input>
      
      
      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:150px;width:90px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.nif"/></xsl:attribute>                
	</input>              
      <input id="nif" type="text" name="nif"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:170px;width:90px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/NIF"/>   
	      </xsl:attribute>            
      </input>


      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:150px;width:500px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.nombre"/></xsl:attribute>                
	</input>                
      <input id="Ctrl_NombreRem" type="text"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:170px;width:500px;
                   text-align:left;border-style:solid;border-width:1px;padding-left:5px;"
            readOnly="true" tabIndex="-1">
      <xsl:attribute name="value">
         <xsl:value-of select="PAGO/LIQUIDACION/NOMBRE"/>   
      </xsl:attribute>
      </input>
      
      <xsl:choose>
	  		<xsl:when test="/PAGO/USAR_PASARELA = '0'">					
				<input type="text" 
		            style="font-size:8pt;font-weight:bold;
		                   position:absolute;left:100px;top:200px;width:100px;
		                   text-align:left;border-style:none"
		            readOnly="true" tabIndex="-1">
					<xsl:attribute name="value"><xsl:value-of select="$lang.medio"/></xsl:attribute>                
				</input>                  
						<select id="idMedioPago" class="select" name="medioPago" title="medio"
									style="font-size:10pt;font-weight:normal;
		                   position:absolute;left:100px;top:220px;width:200px;
		                   text-align:center;border-style:solid;border-width:1px"
		                   onchange="javascript:cambioMedioPago();" disabled="true">
						    <xsl:choose>
								<xsl:when test="/PAGO/MEDIO_PAGO = '1'">					
										<option selected="true" value="1"><xsl:value-of select="$lang.medio1"/></option>
										<option value="2"><xsl:value-of select="$lang.medio2"/></option>
								</xsl:when>
								<xsl:when test="/PAGO/MEDIO_PAGO = '2'">					
										<option value="1"><xsl:value-of select="$lang.medio1"/></option>
										<option  selected="true" value="2"><xsl:value-of select="$lang.medio2"/></option>
								</xsl:when>	
								</xsl:choose>							
						</select>		
			</xsl:when>
			<xsl:when test="/PAGO/USAR_PASARELA = '1'">
			</xsl:when>
    </xsl:choose>
    
    <xsl:choose>
		<xsl:when test="/PAGO/MEDIO_PAGO = '1'">

       	<input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:250px;width:110px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.entidadFinanciera"/></xsl:attribute>                
		</input>                 
				<select class="select" name="entidadBancaria" title="medio"
							style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:270px;width:200px;
                   text-align:center;border-style:solid;border-width:1px" disabled="true">                   
					<option selected="true" value="1236">BBVA</option>
					<option value="1236">BSCH</option>
				</select>
				
       	<input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:310px;top:250px;width:110px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.domiciliacion"/></xsl:attribute>                
		</input>                 

				<select class="select" name="domiciliacion" title="medio"
							style="font-size:10pt;font-weight:normal;
                   position:absolute;left:310px;top:270px;width:200px;
                   text-align:center;border-style:solid;border-width:1px" disabled="true">
				    <xsl:choose>
						<xsl:when test="/PAGO/COD_DOMICILIACION = '1'">				
							<option selected="true" value="1"><xsl:value-of select="$lang.domiciliacion01"/></option>
							<option value="2"><xsl:value-of select="$lang.domiciliacion02"/></option>
						</xsl:when>
						<xsl:when test="/PAGO/COD_DOMICILIACION = '2'">					
							<option value="1"><xsl:value-of select="$lang.domiciliacion01"/></option>
							<option selected="true" value="2"><xsl:value-of select="$lang.domiciliacion02"/></option>
						</xsl:when>	
						</xsl:choose>							                   
				</select>
				
				
      <input type="text" id="idCCC"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:300px;width:395px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.ccc"/></xsl:attribute>                
		</input>                 
      <input id="ccc" type="text" name="ccc"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:320px;width:395px;
                   text-align:left;border-style:solid;border-width:1px"
             tabIndex="-1" readOnly="true">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/CCC"/>   
	      </xsl:attribute>             
      </input>
</xsl:when>

<xsl:when test="/PAGO/MEDIO_PAGO = '2'">
		<div id="divDatosPagoTarjeta">			
		      <input type="text" 
		            style="font-size:8pt;font-weight:bold;
		                   position:absolute;left:100px;top:250px;width:200px;
		                   text-align:left;border-style:none"
		            readOnly="true" tabIndex="-1">
					<xsl:attribute name="value"><xsl:value-of select="$lang.numeroTarjeta"/></xsl:attribute>                
		</input>                 
		      <input id="idTarjeta" type="text" name="numeroTarjetaCredito"
		            style="font-size:10pt;font-weight:normal;
		                   position:absolute;left:100px;top:270px;width:200px;
		                   text-align:left;border-style:solid;border-width:1px"
		             tabIndex="-1" readOnly="true">
			      <xsl:attribute name="value">
			         <xsl:value-of select="PAGO/PAN"/>   
			      </xsl:attribute>             		             
		      </input>
		
		
		      <input type="text" 
		            style="font-size:8pt;font-weight:bold;
		                   position:absolute;left:330px;top:250px;width:200px;
		                   text-align:left;border-style:none"
		            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.fechaTarjeta"/></xsl:attribute>                
		</input>                 
		      <input id="idFechaTarjeta" type="text" name="fechaCaducidadTarjetaCredito"
		            style="font-size:10pt;font-weight:normal;
		                   position:absolute;left:330px;top:270px;width:50px;
		                   text-align:left;border-style:solid;border-width:1px"
		             tabIndex="-1" readOnly="true">
			      <xsl:attribute name="value">
			         <xsl:value-of select="PAGO/FECHA_CADUCIDAD"/>   
			      </xsl:attribute>             		             
		      </input>
			
		</div>
</xsl:when>
</xsl:choose>
</xsl:template> <!-- Raíz -->

<!-- ********** Funciones -->
<xsl:template name="Funciones">
<script language="javascript">

function cambioMedioPago(){
				var divPagoCuenta = document.getElementById("divDatosPagoCuenta");
				var divPagoTarjeta = document.getElementById("divDatosPagoTarjeta");				
				var combo = document.getElementById("idMedioPago");
				var value = combo.value;
				if(value == 1){
					divPagoCuenta.style.visibility = "visible";
					divPagoTarjeta.style.visibility = "hidden";
				}else{
					divPagoCuenta.style.visibility = "hidden";
					divPagoTarjeta.style.visibility = "visible";
				}				
}
</script>
</xsl:template> <!-- Funciones -->

</xsl:stylesheet>