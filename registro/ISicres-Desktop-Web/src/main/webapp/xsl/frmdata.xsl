<?xml version="1.0" encoding="UTF-8"?>
	<!--
		Pinta el formulario de registro
	-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<HTML>
			<HEAD>
			</HEAD>
			<BODY ondragstart="return false;" onfocus="AsigFocus();" tabIndex="-1">

				<div id="cargando" class="cargarSaveRegisterOculto">
					<img src="./images/loading.gif"/>
					<br/>
				</div>

				<xsl:apply-templates select="FrmData/PARAMS"/>
				<xsl:apply-templates select="FrmData/PageData"/>
				<input type="hidden" id="HabilitarValidationInt"/>
				<img id="imgValInter" scr="" style="display:none" onclick="activarDesactivarTerceros();"/>
				<form style="visibility:hidden" name="FrmData" id="FrmData">
					<xsl:apply-templates select="FrmData/Control/Class"/>
				</form>
				<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
					style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
				</iframe>
			</BODY>
		</HTML>
	</xsl:template>

	<xsl:template match="Class[.='Static']">
		<div>
			<xsl:attribute name="class">label</xsl:attribute>
			<xsl:choose>
	            <xsl:when test="../IsVisible[.='0']">
	               <xsl:attribute name="style">position:absolute;display:none;<xsl:value-of select="../Style"/></xsl:attribute>
	            </xsl:when>
	            <xsl:otherwise>
	               <xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
	            </xsl:otherwise>
         	</xsl:choose>
			<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="../Text"/></xsl:attribute>
			<label class="label"><xsl:value-of select="../Text"/></label>
		</div>
	</xsl:template>

	<xsl:template match="Class[.='Edit' and ../TblVal!='6']">
		<xsl:choose>
			<!--
				Para generar los campos input comprobamos si dichos campos corresponden a los FLDID:
					503 - Involucrado en Intercambio Registral
					504 - Acompanya documentacion fisica requerida
					505 - Acompanya documentacion fisica complementaria
					506 - No acompanya  documentación fisica ni otros soportes
				Si corresponden con cualquiera de esos FLDId, entonces el campo input se genara oculto y en su lugar se mostrara un radiobuton,
				con los valores SI/NO/"".
			-->
			<xsl:when test="../FldId[.!='503' and ../FldId!='504' and ../FldId!='505' and ../FldId!='506']">
		  		<input type="Text" onfocus="ChkModifFocus( this );">
					<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
					<xsl:attribute name="maxlength"><xsl:value-of select="../MaxLength"/></xsl:attribute>
					<xsl:choose>
						<xsl:when test="../ReadOnly[.='1']">
							<xsl:attribute name="class">inputRO</xsl:attribute>
					   		<xsl:attribute name="readOnly">true</xsl:attribute>
					   		<xsl:attribute name="tabIndex">-1</xsl:attribute>
					   	</xsl:when>
					   	<xsl:otherwise>
							<xsl:attribute name="class">input</xsl:attribute>
					   		<xsl:attribute name="tabIndex">1</xsl:attribute>
					   	</xsl:otherwise>
					</xsl:choose>
					<xsl:if test="../IsVisible[.='0']">
		            	<xsl:attribute name="type">hidden</xsl:attribute>
		         	</xsl:if>
					<xsl:choose>
						<xsl:when test="../CaseSensitive[.='CI']">
							<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
							<xsl:attribute name="onblur">FireOnBlur(this);</xsl:attribute>
					   	</xsl:when>
					   	<xsl:otherwise>
							<xsl:attribute name="style">text-transform:uppercase;position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
							<xsl:attribute name="onblur">this.value=this.value.toUpperCase();FireOnBlur(this);</xsl:attribute>
					   	</xsl:otherwise>
					</xsl:choose>
					<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
		         	<xsl:attribute name="tblvalidated"><xsl:value-of select="../TblVal"/></xsl:attribute>
		         	<xsl:attribute name="TblFldId"><xsl:value-of select="../TblFldId"/></xsl:attribute>
			        <xsl:attribute name="value"><xsl:value-of select="../Text"/></xsl:attribute>
			        <xsl:attribute name="onhelp">VldHelp(event, this)</xsl:attribute>
			        <xsl:attribute name="IsResum"><xsl:value-of select="../IsResum"/></xsl:attribute>
			        <xsl:attribute name="onkeydown">EnabledSave(false, event);</xsl:attribute>
			        <xsl:attribute name="onpaste">top.Main.Folder.FolderBar.ActivateSave();</xsl:attribute>
					<xsl:apply-templates select="../IsSust"/>
				</input>
			</xsl:when>
			<xsl:otherwise>
			<!-- Generamos el input oculto -->
			  		<input type="Text" onfocus="ChkModifFocus( this );">
						<xsl:attribute name="name"><xsl:value-of select="../FldId"/></xsl:attribute>
						<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
						<xsl:attribute name="maxlength"><xsl:value-of select="../MaxLength"/></xsl:attribute>
			            <xsl:attribute name="type">hidden</xsl:attribute>
						<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
			         	<xsl:attribute name="tblvalidated"><xsl:value-of select="../TblVal"/></xsl:attribute>
			         	<xsl:attribute name="TblFldId"><xsl:value-of select="../TblFldId"/></xsl:attribute>
				        <xsl:attribute name="value"><xsl:value-of select="../Text"/></xsl:attribute>
				        <xsl:attribute name="onhelp">VldHelp(event, this)</xsl:attribute>
				        <xsl:attribute name="IsResum"><xsl:value-of select="../IsResum"/></xsl:attribute>
				        <xsl:attribute name="onkeydown">EnabledSave(false, event);</xsl:attribute>
						<xsl:apply-templates select="../IsSust"/>
					</input>
					<xsl:choose>
					<xsl:when test="../FldId[.='503']">
						<!--  Generamos un check con los mismos estilos que para el input anterior: posicion, tamanyo... -->
						<input type="checkbox">
							<xsl:attribute name="name">checkbox_<xsl:value-of select="../Id"/></xsl:attribute>
							<xsl:attribute name="onclick">changeValueSelect(this, <xsl:value-of select="../Id"/>);</xsl:attribute>
							<xsl:attribute name="style">position:absolute;cursor:pointer;<xsl:value-of select="../Style"/></xsl:attribute>
							<xsl:if test="../Text[.='1']">
								<xsl:attribute name="checked">true</xsl:attribute>
							</xsl:if>
							<xsl:attribute name="disabled">disabled</xsl:attribute>
						</input>
					</xsl:when>
					<xsl:otherwise>
						<!--  Generamos un check con los mismos estilos que para el input anterior: posicion, tamanyo... -->
						<input type="radio">
							<xsl:attribute name="name">radio_doc_fisica</xsl:attribute>
							<xsl:attribute name="onclick">changeValueRadioDocFisica(this, '<xsl:value-of select="../FldId"/>');</xsl:attribute>
							<xsl:attribute name="style">position:absolute;cursor:pointer;<xsl:value-of select="../Style"/></xsl:attribute>
							<xsl:if test="../Text[.='1']">
								<xsl:attribute name="checked">true</xsl:attribute>
							</xsl:if>
						</input>
					</xsl:otherwise>
			    </xsl:choose>

			</xsl:otherwise>
		</xsl:choose>
      	<xsl:apply-templates select="../TblVal[.>'0']"/>
      	<xsl:apply-templates select="../DataType[.='2']"/>
      	<xsl:apply-templates select="../DataType[.='3']"/>
      	<!--
      	 	<IMG src="images/invalid.gif">
        		<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
        		<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
        		<xsl:attribute name="style">display:none; position:absolute;<xsl:value-of select="../Style_Val_Img"/></xsl:attribute>
      		</IMG>
      	-->
      	<xsl:apply-templates select="../Obli[.>'0']"/>
	</xsl:template>

	<xsl:template match="Class[.='TextArea']">
		<TEXTAREA onfocus="ChkModifFocus( this );" >
			<xsl:choose>
         		<xsl:when test="../ReadOnly[.='1']">
					<xsl:attribute name="class">textareaRO</xsl:attribute>
	  	   			<xsl:attribute name="readOnly"><xsl:value-of select="../ReadOnly"/></xsl:attribute>
					<xsl:attribute name="tabIndex">-1</xsl:attribute>
			    </xsl:when>
			    <xsl:otherwise>
					<xsl:attribute name="class">textarea</xsl:attribute>
			        <xsl:attribute name="tabIndex">1</xsl:attribute>
			        <xsl:if test="../MaxLength">
			        	<xsl:attribute name="onkeydown">LimitText(this, <xsl:value-of select="../MaxLength"/>);EnabledSave(false, event);</xsl:attribute>
						<xsl:attribute name="onpaste">LimitText(this, <xsl:value-of select="../MaxLength"/>);top.Main.Folder.FolderBar.ActivateSave();</xsl:attribute>
						<xsl:attribute name="onkeyup">LimitText(this, <xsl:value-of select="../MaxLength"/>);</xsl:attribute>
			        </xsl:if>
			    </xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="../CaseSensitive[.='CI']">
					<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/>overflow:auto</xsl:attribute>
					<xsl:attribute name="onblur">ChkModifBlur(this);</xsl:attribute>
			   	</xsl:when>
			   	<xsl:otherwise>
					<xsl:attribute name="style">text-transform:uppercase;position:absolute;<xsl:value-of select="../Style"/>overflow:auto</xsl:attribute>
					<xsl:attribute name="onblur">this.value=this.value.toUpperCase();ChkModifBlur(this);</xsl:attribute>
			   	</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="../IsVisible[.='0']">
            	<xsl:attribute name="style">display:none;</xsl:attribute>
         	</xsl:if>
			<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
			<xsl:attribute name="tblvalidated"><xsl:value-of select="../TblVal"/></xsl:attribute>
			<xsl:attribute name="IsResum"><xsl:value-of select="../IsResum"/></xsl:attribute>
			<xsl:apply-templates select="../IsSust"/>
			<xsl:value-of select="../Text"/>
		</TEXTAREA>
		<xsl:apply-templates select="../TblVal[.>'0']"/>
		<!--
			<IMG src="images/invalid.gif">
				<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
				<xsl:attribute name="style">display:none; position:absolute;<xsl:value-of select="../Style_Val_Img"/></xsl:attribute>
			</IMG>
		-->
		<xsl:apply-templates select="../Obli[.>'0']"/>
	</xsl:template>

	<xsl:template match="Class[(.='Edit' or .='TextArea') and ../TblVal='6']">
		<iframe id="Interesados" SRC="blank.htm" frameborder="0" scrolling="no">
			<xsl:attribute name="style">display:block;position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="CtrlId"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
			<xsl:attribute name="tabIndex">1</xsl:attribute>
			<xsl:attribute name="readOnly"><xsl:value-of select="../ReadOnly"/></xsl:attribute>
         	<xsl:attribute name="tblvalidated"><xsl:value-of select="../TblVal"/></xsl:attribute>
         	<xsl:attribute name="TblFldId"><xsl:value-of select="../TblFldId"/></xsl:attribute>
	        <xsl:attribute name="value"><xsl:value-of select="../Cadena"/></xsl:attribute>
	        <xsl:attribute name="caseSensitive"><xsl:value-of select="../CaseSensitive"/></xsl:attribute>
	        <xsl:attribute name="valueSust"></xsl:attribute>
		</iframe>
	</xsl:template>

	<xsl:template match="Class[.='CBox']">
		<xsl:attribute name="class">combo</xsl:attribute>
		<select size="1">
			<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="FldId"><xsl:value-of select="../FldId"/></xsl:attribute>
			<xsl:apply-templates select="../Text"/>
		</select>
	</xsl:template>

	<xsl:template match="Class[.='Img' and ../Text!='']">
		<TABLE border="0">
			<xsl:attribute name="class">table</xsl:attribute>
			<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			<TBODY>
				<TR>
					<TD>
						<IMG name="thumbnail" tabIndex="-1">
       						<xsl:attribute name="src"><xsl:value-of select="../Text"/></xsl:attribute>
       						<xsl:attribute name="style"><xsl:value-of select="../Style"/></xsl:attribute>
						</IMG>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</xsl:template>

	<xsl:template match="Control/IsSust[.='1']">
		<xsl:attribute name="Sustituto">true</xsl:attribute>
	</xsl:template>

	<xsl:template match="Control/IsSust[.!='1']">
		<xsl:attribute name="valueSust"></xsl:attribute>
	</xsl:template>

	<xsl:template match="Control/TblVal[.>'0' and .!='6']">
		<IMG src="images/buscar2.gif" tabIndex="1">
			<xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){imgHelp(event,'<xsl:value-of select="../CaseSensitive"/>')}</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="style">cursor:pointer; display:none;position:absolute;<xsl:value-of select="../Style"/></xsl:attribute>
			<xsl:attribute name="tabIndex">-1</xsl:attribute>
			<xsl:attribute name="onclick">imgHelp(event,'<xsl:value-of select="../CaseSensitive"/>');</xsl:attribute>
			<xsl:if test="../ReadOnly[.!='0']">
				<xsl:attribute name="isReadOnly">1</xsl:attribute>
			</xsl:if>
		</IMG>
	</xsl:template>

	<xsl:template match="Control/DataType[.='2']">
		<IMG src="./images/calendarM.gif" tabIndex="1">
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="onkeydown">EnabledSave(false, event);</xsl:attribute>
			<xsl:attribute name="onclick">showCalendarEx(this, document.getElementsByName('<xsl:value-of select="../Id"/>')[0], top.Idioma);</xsl:attribute>
			<xsl:attribute name="style">position:absolute;cursor:pointer;display:none;<xsl:value-of select="../Style"/></xsl:attribute>
			<xsl:if test="../ReadOnly[.!='0']">
				<xsl:attribute name="isReadOnly">1</xsl:attribute>
			</xsl:if>
		</IMG>
	</xsl:template>

	<xsl:template match="Control/DataType[.='3']">
		<IMG src="./images/calendarM.gif" tabIndex="1">
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="onkeydown">EnabledSave(false, event);</xsl:attribute>
			<xsl:attribute name="onclick">showCalendarExDateLong(this, document.getElementsByName('<xsl:value-of select="../Id"/>')[0], top.Idioma);</xsl:attribute>
			<xsl:attribute name="style">position:absolute;cursor:pointer;display:none;<xsl:value-of select="../Style"/></xsl:attribute>
			<xsl:if test="../ReadOnly[.!='0']">
				<xsl:attribute name="isReadOnly">1</xsl:attribute>
			</xsl:if>
		</IMG>
	</xsl:template>

	<xsl:template match="Control/Obli[.>'0']">
		<IMG src="images/fldobli.gif">
			<xsl:attribute name="id"><xsl:value-of select="../Id"/></xsl:attribute>
			<xsl:attribute name="style">position:absolute;<xsl:value-of select="../Style_Val_Img"/></xsl:attribute>
			<xsl:attribute name="isObli">true</xsl:attribute>
		</IMG>
	</xsl:template>

	<xsl:template match="PARAMS">
		<input type="hidden" id="CanUpdatePer">
			<xsl:attribute name="value"><xsl:value-of select="CanUpdatePer"/></xsl:attribute>
		</input>
		<input type="hidden" id="CanAddPer">
			<xsl:attribute name="value"><xsl:value-of select="CanAddPer"/></xsl:attribute>
		</input>
	</xsl:template>

	<xsl:template match="PageData">
		<input type="hidden" id="Width">
			<xsl:attribute name="value"><xsl:value-of select="Width"/></xsl:attribute>
		</input>
		<input type="hidden" id="Height">
			<xsl:attribute name="value"><xsl:value-of select="Height"/></xsl:attribute>
		</input>
	</xsl:template>

</xsl:stylesheet>
