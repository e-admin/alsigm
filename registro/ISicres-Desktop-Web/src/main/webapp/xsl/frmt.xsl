<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<HTML>
		<HEAD>
		</HEAD>
		<BODY ondragstart="return false;" tabIndex="-1">
			<xsl:apply-templates select="Error"/>
			<xsl:apply-templates select="FrmTree/Session"/>
			<xsl:apply-templates select="FrmTree/PARAMS"/>
			<xsl:apply-templates select="FrmTree/Properties"/>
			<xsl:apply-templates select="FrmTree/UserConfig/Parameters/Parameter"/>
			<xsl:apply-templates select="FrmTree/UserConfig/Fields"/>
			<div id="divDocs" class="contenido_tree">
				<div id="botonera">
					<p id="imgTree" class="imgTree"><a href="#" onclick="Maximized();" id="img_menu_on" class="img_menu_on">&#160;</a></p>
				</div>
				<nobr>
					<UL style="margin-left:0px">
						<LI id="tree" class="tree" >
							<IMG src="images/datblank.gif" border="0"></IMG>

							<IMG border="0" style="margin-right: 5px">
								<xsl:choose>
									<xsl:when test="FrmTree/Properties/BookType[.='1']">
										<!-- Libro de entrada -->
										<xsl:attribute name="src">images/book-open-in.gif</xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<!-- Libro de salida -->
										<xsl:attribute name="src">images/book-open-out.gif</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
							</IMG>

							<A><xsl:value-of select="FrmTree/PARAMS/FolderName"/></A>
							<ul id="UL0" style="display:list-item;margin-left:20px">
								<xsl:apply-templates select="FrmTree/Node"/>
							</ul>
						</LI>
					</UL>
				</nobr>
			</div>
			<div id="MenuClf" style="visibility:hidden; position:absolute; top:10; left:10" onclick="window.IsClickMenu=true;" onMouseOut="window.IsClickMenu=false;">
			</div>
		</BODY>
	</HTML>
</xsl:template>

<xsl:template match="Node">
   <xsl:choose>
      <xsl:when test="@Type[.='Pag']">
         <LI class="CL3">
            <xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
            <IMG src="images/datblank.gif" border="0"></IMG>
            <IMG src="images/page.png" style="cursor:pointer"
               onclick="SelElem( 3, this.parentNode.id );OpenPage(event, this.parentNode.id, top.GetInnerText(this.parentNode));"
               oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;"/>
            <A onclick="SelElem( 3, this.parentNode.id );OpenPage(event, this.parentNode.id, top.GetInnerText(this));"
               style="cursor:pointer" onmouseover="CursorOver(this);" onmouseout="CursorOut(this);"
               onfocus="CursorOver( this );" onblur="CursorOut(this);"
               oncontextmenu="ShowMenu(event, 3, this.parentNode.id); return false;" tabIndex="1">
               <xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){SelElem( 3, this.parentNode.id );OpenPage(event, this.parentNode.id, top.GetInnerText(this));}</xsl:attribute>
               <xsl:value-of select="Title"/>
            </A>
         </LI>
      </xsl:when>
      <xsl:when test="@Type[.='Doc']">
         <LI class="CL2">
            <xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
            <IMG src="images/datplus.gif" style="cursor:pointer"  onclick="arbol( this.parentNode.id, 2 );" tabIndex="1">
               <xsl:attribute name="onkeydown">if(top.GetKeyCode(event)==13){arbol( this.parentNode.id, 2 );}</xsl:attribute>
            </IMG>
            <IMG src="images/folder_database.png" onmouseover="CursorOver( this );"
               oncontextmenu="ShowMenu(event, 2, this.parentNode.id); return false;"/>
            <A onfocus="CursorOver( this );" onblur="CursorOut(this);"
               onmouseover="CursorOver( this );" onmouseout="CursorOut(this);"
               oncontextmenu="ShowMenu(event, 2, this.parentNode.id); return false;" tabIndex="1"><xsl:value-of select="Title"/>
            </A>
            <UL style="display:none"><xsl:apply-templates select="Node"/></UL>
         </LI>
      </xsl:when>
      <xsl:when test="@Type[.='InfDoc']">
         <LI id="R0" class="CL0">
            <IMG src="images/datminus.gif" onclick="arbol( this.parentNode.id, 0 );" style="cursor:pointer" tabIndex="1">
               <xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){arbol( this.parentNode.id, 0 );}</xsl:attribute>
            </IMG>
            <IMG src="images/attach2.png" onmouseover="CursorOver( this );" oncontextmenu="ShowMenu(event, 0, this.parentNode.id); return false;"/>
            <A id="lbAnexos" onfocus="CursorOver( this );" onblur="CursorOut(this);"
               onmouseover="CursorOver( this );" onmouseout="CursorOut(this);"
               oncontextmenu="ShowMenu(event, 0, this.parentNode.id); return false;" tabIndex="1"></A>
            <UL style="display:list-item"><xsl:apply-templates select="Node"/></UL>
         </LI>
      </xsl:when>
      <xsl:otherwise>
         <LI class="CL5">
            <xsl:attribute name="id"><xsl:value-of select="Id"/></xsl:attribute>
            <xsl:if test="Id[.=0]">
            	<IMG src="images/datblank.gif" border="0"/>
            	<IMG src="images/file_doc.png" border="0" style="cursor:pointer; margin-right: 2px"
               		onclick="SelElem( 5, this.parentNode.id );OpenPageData(event)"/>
            </xsl:if>
            <xsl:if test="Id[.=1 or .=2]">
            	<IMG src="images/datblank.gif" border="0"/>
            	<IMG src="images/comment.png" border="0" style="cursor:pointer; margin-right: 2px"
               		onclick="SelElem( 5, this.parentNode.id );OpenPageData(event)"/>
            </xsl:if>
            <A class="Item" onmouseover="OverArchive()" onmouseout="OutArchive();"
             	 	onclick="SelElem( 5, this.parentNode.id );OpenPageData(event);" tabIndex="1">
            		<xsl:attribute name="onkeydown">if (top.GetKeyCode(event)==13){SelElem( 5, this.parentNode.id );OpenPageData(event);}</xsl:attribute>
            		<xsl:value-of select="Title"/>
          	</A>
          	<xsl:if test="Id[.=0]">
				<xsl:if test="../Properties/RegNumber[.!='']">
					<br/>
					<b style="margin-left:35px">n&#176;&#58;&#160;<xsl:value-of select="../Properties/RegNumber"/></b>
				</xsl:if>
          	</xsl:if>

         </LI>
      </xsl:otherwise>
   </xsl:choose>
</xsl:template>

<xsl:template match="PARAMS">
	<input type="hidden" id="FolderPId">
		<xsl:attribute name="value"><xsl:value-of select="FolderPId"/></xsl:attribute>
	</input>
	<input type="hidden" id="FdrReadOnly">
		<xsl:attribute name="value"><xsl:value-of select="FdrReadOnly"/></xsl:attribute>
	</input>
	<input type="hidden" id="FolderId">
		<xsl:attribute name="value"><xsl:value-of select="FolderId"/></xsl:attribute>
	</input>
	<input type="hidden" id="VldSave">
		<xsl:attribute name="value"><xsl:value-of select="VldSave"/></xsl:attribute>
	</input>
   <xsl:apply-templates select="IsBookAdm"/>
   <xsl:apply-templates select="CanDist"/>
</xsl:template>

<xsl:template match="Session">
	<input type="hidden" id="User">
		<xsl:attribute name="value"><xsl:value-of select="User"/></xsl:attribute>
	</input>
	<input type="hidden" id="UserName">
		<xsl:attribute name="value"><xsl:value-of select="UserName"/></xsl:attribute>
	</input>
	<input type="hidden" id="OfficeCode">
		<xsl:attribute name="value"><xsl:value-of select="OfficeCode"/></xsl:attribute>
	</input>
	<input type="hidden" id="OfficeName">
		<xsl:attribute name="value"><xsl:value-of select="OfficeName"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Properties">
	<input type="hidden" id="BookType">
		<xsl:attribute name="value"><xsl:value-of select="BookType"/></xsl:attribute>
	</input>
	<input type="hidden" id="RegNumber">
		<xsl:attribute name="value"><xsl:value-of select="RegNumber"/></xsl:attribute>
	</input>
	<input type="hidden" id="RegDate">
		<xsl:attribute name="value"><xsl:value-of select="RegDate"/></xsl:attribute>
	</input>
	<input type="hidden" id="StampOfficeCode">
		<xsl:attribute name="value"><xsl:value-of select="StampOfficeCode"/></xsl:attribute>
	</input>
	<input type="hidden" id="StampOfficeDesc">
		<xsl:attribute name="value"><xsl:value-of select="StampOfficeDesc"/></xsl:attribute>
	</input>
	<input type="hidden" id="UnitCode">
		<xsl:attribute name="value"><xsl:value-of select="UnitCode"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="IsBookAdm">
	<input type="hidden" id="IsBookAdm">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Error">
	<input type="hidden" id="ErrorMessage">
		<xsl:attribute name="value"><xsl:value-of select="Message"/></xsl:attribute>
	</input>
	<xsl:apply-templates select="CtrlId"/>
</xsl:template>

<xsl:template match="CtrlId">
	<input type="hidden" name="CtrlId">
		<xsl:attribute name="value"><xsl:value-of select="@Id"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Parameter[@Id='1']">
	<input type="hidden" id="PersonValidation">
		<xsl:attribute name="value"><xsl:value-of select="@Checked"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Parameter[@Id='2']">
	<input type="hidden" id="ShowScannerUI">
		<xsl:attribute name="value"><xsl:value-of select="@Checked"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Fields">
	<input type="hidden" id="Fields">
		<xsl:attribute name="value"><xsl:apply-templates select="Field"/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="CanDist">
	<input id="CanDist" type="hidden">
		<xsl:attribute name="value"><xsl:value-of select="."/></xsl:attribute>
	</input>
</xsl:template>

<xsl:template match="Field"><xsl:value-of select="@Id"/>;</xsl:template>

</xsl:stylesheet>