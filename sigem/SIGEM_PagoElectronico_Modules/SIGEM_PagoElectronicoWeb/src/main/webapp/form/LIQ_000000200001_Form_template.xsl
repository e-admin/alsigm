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
			<input type="hidden" name="informacionEspecifica" value="" id="idInfo"/>

      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:0px;width:120px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
            <xsl:attribute name="value"><xsl:value-of select="$lang.fechaDevengo"/></xsl:attribute>
      </input>

      <input id="idDevengo" type="text" name="fechaDevengo"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:20px;width:120px;
                   text-align:center;border-style:solid;border-width:1px;background: #c8eee6;"
            		tabIndex="1" maxlength="10">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/DEVENGO"/>         
	      </xsl:attribute>
      </input>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:130px;top:0px;width:70px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.emisora"/></xsl:attribute>            
	</input>            

      <input id="idPeriodo" type="text" name="idEntidad"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:130px;top:20px;width:70px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/ID_ENTIDAD"/>   
	      </xsl:attribute>            
      </input>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:210px;top:0px;width:60px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.modalidad"/></xsl:attribute>                
	</input>

      <input id="idModalidad" type="text" name="modalidad"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:210px;top:20px;width:60px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1"
            value="3">
      </input>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:280px;top:0px;width:50px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.modalidad"/></xsl:attribute>                
	</input>            

      <input id="idPeriodo" type="text" name="idEntidad"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:280px;top:20px;width:40px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1"
            value="1">
      </input>

      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:330px;top:0px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.referencia"/></xsl:attribute>                
	</input>              

      <input id="referencia" type="text" name="referencia"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:330px;top:20px;width:100px;
                   text-align:center;border-style:solid;border-width:1px"
            readOnly="true" tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/REFERENCIA"/>   
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
            tabIndex="8" readOnly="true">
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
            tabIndex="8" value="SAN JOSÉ"  readOnly="true">
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
	         <xsl:value-of select="PAGO/LIQUIDACION/TASA/DATOS_ESPECIFICOS/NOMBRE"/>   
	      </xsl:attribute>            
      </input>
     
     
      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:150px;width:90px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.nif"/></xsl:attribute>                
	</input>              

      <input id="idNif" type="text" name="nif"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:170px;width:90px;
                   text-align:center;border-style:solid;border-width:1px"
            	tabIndex="-1">
	      <xsl:attribute name="value">
	         <xsl:value-of select="PAGO/LIQUIDACION/NIF"/>   
	      </xsl:attribute>            
      </input>



      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:150px;width:510px;
                   text-align:left;border-style:none"
            tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.nombre"/></xsl:attribute>                
	</input>                

      <input id="Ctrl_NombreRem" type="text"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:170px;width:510px;
                   text-align:left;border-style:solid;border-width:1px;padding-left:5px;"
            readOnly="true" tabIndex="-1">
      <xsl:attribute name="value">
         <xsl:value-of select="PAGO/LIQUIDACION/NOMBRE"/>   
      </xsl:attribute>
      </input>
      
      
      
      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:0px;top:200px;width:615px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
		<xsl:attribute name="value"><xsl:value-of select="$lang.especifico"/></xsl:attribute>                
	</input>                

      <input id="idDatoEspecifico" type="text" name="datosEspecificos"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:0px;top:220px;width:615px;
                   text-align:left;border-style:solid;border-width:1px;background: #c8eee6;"
            		tabIndex="2">
      </input>
      
     
      <input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:250px;width:100px;
                   text-align:left;border-style:none"
            		tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.importe"/></xsl:attribute>                
	</input>                 		

      <input id="idImporte" type="text" name="importe"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:270px;width:195px;
                   text-align:center;border-style:solid;border-width:1px;background: #c8eee6;"
            	tabIndex="3" maxlength="13">
            <xsl:attribute name="value">
         		<xsl:value-of select="PAGO/LIQUIDACION/IMPORTE"/>   
      		</xsl:attribute>
      </input>
     
     
     
       	<input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:310px;top:250px;width:100px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.medio"/></xsl:attribute>                
	</input>                  

				<select id="idMedioPago" class="select" name="medioPago" title="medio"
							style="font-size:10pt;font-weight:normal;
                   position:absolute;left:310px;top:270px;width:200px;
                   text-align:center;border-style:solid;border-width:1px;background: #c8eee6;"
                   onchange="javascript:cambioMedioPago();" tabIndex="4">
					<option selected="true" value="1"><xsl:value-of select="$lang.medio1"/></option>
					<option value="2"><xsl:value-of select="$lang.medio2"/></option>
				</select>

<div id="divDatosPagoCuenta">				
       	<input type="text" 
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:300px;width:110px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.entidadFinanciera"/></xsl:attribute>                
		</input>                 

				<select class="select" name="entidadBancaria" title="medio"
							style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:320px;width:200px;
                   text-align:center;border-style:solid;border-width:1px;background: #c8eee6;" tabIndex="4">
					<option selected="true" value="1236">BBVA</option>
					<option value="1236">BSCH</option>
				</select>				
				
      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:350px;width:395px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.ccc"/></xsl:attribute>                
		</input>                 
            

      <input id="idCCC" type="text" name="ccc"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:370px;width:395px;
                   text-align:left;border-style:solid;border-width:1px;background: #c8eee6;"
             tabIndex="-1" maxlength="20">
      </input>
</div>

<div id="divDatosPagoTarjeta" style="visibility:hidden;">			
      <input type="text"  
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:100px;top:300px;width:200px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1">
			<xsl:attribute name="value"><xsl:value-of select="$lang.numeroTarjeta"/></xsl:attribute>                
		</input>                 
      <input id="idTarjeta" type="text" name="numeroTarjetaCredito"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:100px;top:320px;width:200px;
                   text-align:left;border-style:solid;border-width:1px;background: #c8eee6;"
             tabIndex="-1" maxlength="16">
      </input>


      <input type="text"
            style="font-size:8pt;font-weight:bold;
                   position:absolute;left:330px;top:300px;width:200px;
                   text-align:left;border-style:none"
            readOnly="true" tabIndex="-1" >
			<xsl:attribute name="value"><xsl:value-of select="$lang.fechaTarjeta"/></xsl:attribute>                
		</input>                 
            

      <input type="text" name="fechaCaducidadTarjetaCredito" id="idFechaTarjeta"
            style="font-size:10pt;font-weight:normal;
                   position:absolute;left:330px;top:320px;width:50px;
                   text-align:left;border-style:solid;border-width:1px;;background: #c8eee6;"
             tabIndex="-1" maxlength="4">
      </input>
	
</div>
 
</xsl:template> <!-- Raíz -->

<!-- ********** Funciones -->
<xsl:template name="Funciones">
<script language="javascript">

function validarFecha(input){
	var validformat=/^\d{2}\/\d{2}\/\d{4}$/ //Basic check for format validity
	var returnval=false
	if (!validformat.test(input.value))
		alert("<xsl:value-of select="$lang.mensaje01"/>")
	else{ //Detailed check for valid date ranges
		var monthfield=input.value.split("/")[1]
		var dayfield=input.value.split("/")[0]
		var yearfield=input.value.split("/")[2]
		var dayobj = new Date(yearfield, monthfield-1, dayfield)
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
			alert("Fecha incorrecta.")
		else
			returnval=true
	}
	if (returnval==false) input.select()
		return returnval
		
}

function validarImporte(input){
	var validformat = /^\d*(\,\d{1,2})?$/

	if (!validformat.test(input.value)){
		alert("<xsl:value-of select="$lang.mensaje02"/>")
		return false;
	}
	return true;
}
function completarInformacionEspecifica(){
	var xml= "<DATO_ESPECIFICO>";
	var campo = document.getElementById("idDatoEspecifico");
	xml += campo.value;
	xml += "</DATO_ESPECIFICO>";
	
	var campoInfo = document.getElementById("idInfo");
	campoInfo.value = xml;
}

function validarNumerico(input, nombrecampo){
	var validformat = /^\d+$/
	if (!validformat.test(input.value)){
		alert("<xsl:value-of select="$lang.mensaje03"/> " + nombrecampo + " <xsl:value-of select="$lang.mensaje04"/>")
		return false;
	}
	return true;
}

function validarEnvio(){
				completarInformacionEspecifica();
				var campo = document.getElementById("idDevengo");
				if( (campo.value == null) || (campo.value == "")){
					alert("<xsl:value-of select="$lang.mensaje05"/>");
					return false;
				}				
				if( !validarFecha(campo)){
					return false;
				}
				campo = document.getElementById("idDatoEspecifico");
				if( (campo.value == null) || (campo.value == "")){
					alert("<xsl:value-of select="$lang.mensaje06"/>");
					return false;
				}
				campo = document.getElementById("idImporte");
				if( (campo.value == null) || (campo.value == "")){
					alert("<xsl:value-of select="$lang.mensaje07"/>");
					return false;
				}
				if( !validarImporte(campo)){
					return false;
				}
				var formulario = document.getElementById("formulario");
				var combo = document.getElementById("idMedioPago");
				var value = combo.value;
				if(value == 1){
					var aux = document.getElementById("idCCC");
					if( (aux.value == null) || (aux.value == "")){
						alert("<xsl:value-of select="$lang.mensaje08"/>");
						return false;
					}else{
						if(aux.value.length != 20 ){
							alert("<xsl:value-of select="$lang.mensaje09"/>");
							return false;
						}else{
							if(!validarNumerico(aux, "<xsl:value-of select="$lang.ccc"/>")){
								return false;
							}
						}
					}
				}else{
					var aux = document.getElementById("idTarjeta");
					if( (aux.value == null) || (aux.value == "")){
						alert("<xsl:value-of select="$lang.mensaje13"/>");
						return false;
					}else{
						if(aux.value.length != 16 ){
							alert("<xsl:value-of select="$lang.mensaje10"/>");
							return false;
						}else{
							if(!validarNumerico(aux, "<xsl:value-of select="$lang.numeroTarjeta"/>")){
								return false;
							}
						}
					}
					aux = document.getElementById("idFechaTarjeta");
					if( (aux.value == null) || (aux.value == "")){
						alert("<xsl:value-of select="$lang.mensaje11"/>");
						return false;
					}else{
						if(aux.value.length != 4 ){
							alert("<xsl:value-of select="$lang.mensaje12"/>");
							return false;
						}else{
							if(!validarNumerico(aux, "<xsl:value-of select="$lang.fechaTarjeta"/>")){
								return false;
							}
						}
					}				
				}				
				var form = document.getElementById("formulario");
				form.submit();
}

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