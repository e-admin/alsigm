<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!--
	idTree -> identificador del tree, por ahora no lo huso pues con la accion devemos identificar que tree usar
	nameList -> nombre del List que tiene el Atributo del request
	classDiv -> clase CSS para usar en el div de cada node
	idTagsExp -> tags que expanden o contraen
	idTagsImg -> tags con imagenes que deben de cambiar , las fijas se espacifiacan en el body del tag
	idTagImgTipo -> identificador del IMG que depende del tipo de node
	imgsSRCImgTipo -> relacion entre identificador y foto asociada al tipo de node
	imgsSRCExpandir -> ruta de las fotos que representan un nodo que se puede expandir
	imgsSRCContraer -> ruta de las fotos que representan un nodo que se puede contraer
	bolHome -> true si el tree debe de tener un node
	imgHome -> ruta de la foto del home
	dibujaPuntos -> se dibujan puntos para ver los niveles
	imgSRCPuntosPrimerEle -> imagen para el primer elemento del tree
	imgSRCPuntosContinuaEle -> imagen para un elemento intermedio
	imgSRCPuntosContinua -> imagen para agrupar subniveles
	imgSRCPuntosUltimoEle -> imagen para el ultimo elemento de un nivel o subnivel
	isIDNodeTree -> identificador o cojunto de indentificadores de propiedades del ItemBean que formaran el
		identificador del Node en el Tree
	idTextPropiedad -> Texto a mostrar en el node
	idTagTexto -> nombre del tag en el cual hay que incorporar el idTextPropiedad
-->

<%--
<ispac:tree idTree="ProcedureTree"
			nameList="TreeViewList"
			classDiv="botones"
			idTagsExp="imgExp;"
			idTagsImg="imgExp;imgEle;"
			idTagImgTipo="imgEle"
			imgsSRCImgTipo="1,ispac/skin1/img/treeview/doct.gif,false;2,ispac/skin1/img/treeview/docselt.gif,false;"
			imgsSRCExpandir="ispac/skin1/img/treeview/mast.gif;ispac/skin1/img/treeview/carpcerradat.gif;"
			imgsSRCContraer="ispac/skin1/img/treeview/menost.gif;ispac/skin1/img/treeview/carpabiertat.gif;"
			bolHome="true"
			imgHome="ispac/skin1/img/treeview/hometree.gif"
			dibujaPuntos="true"
			imgSRCPuntosPrimerEle="ispac/skin1/img/treeview/puntosh.gif"
			imgSRCPuntosContinuaEle="ispac/skin1/img/treeview/puntost.gif"
			imgSRCPuntosContinua="ispac/skin1/img/treeview/puntosut.gif"
			imgSRCPuntosUltimoEle="ispac/skin1/img/treeview/puntosvt.gif"
			idsIDNodeTree="ID;"
			idTextPropiedad="NOMBRE"
			idTagTexto="aEle">

	<img id='imgExp' style='margin-right:0px; vertical-align:middle;'></img>
	<img id='imgEle' style='margin-right:3px; vertical-align:middle;'></img>
	<a id='aEle'></a>
	
</ispac:tree>

<ispac:tree	name="TreeViewList"
			id="treebean"
			class="botones"
			idTagsImg="imgExp;imgEle;"
			idTagImgTipo="imgEle"
			imgsSRCImgTipo="1,ispac/skin1/img/treeview/doct.gif,false;2,ispac/skin1/img/treeview/docselt.gif,false;"
			imgsSRCExpandir="ispac/skin1/img/treeview/mast.gif;ispac/skin1/img/treeview/carpcerradat.gif;"
			imgsSRCContraer="ispac/skin1/img/treeview/menost.gif;ispac/skin1/img/treeview/carpabiertat.gif;"
			showHome="true"
			imgHome="ispac/skin1/img/treeview/hometree.gif"
			showTreeLines="true"
			imgSRCPuntosPrimerEle="ispac/skin1/img/treeview/puntosh.gif"
			imgSRCPuntosContinuaEle="ispac/skin1/img/treeview/puntost.gif"
			imgSRCPuntosContinua="ispac/skin1/img/treeview/puntosut.gif"
			imgSRCPuntosUltimoEle="ispac/skin1/img/treeview/puntosvt.gif"
			idsIDNodeTree="ID;"
>
	<treeExpand style="class" name="imgExp" action='url' paramId='expId' paramName="treebean" paramProperty='property(ID_EXP)'>
		<treeImage>
			<img id='imgEle' style='margin-right:0px; vertical-align:middle;'></img>
		</treeImage>
	<treeExpand>

	<table>	
		<table >
			<tr>
				<td> </td>
			</tr>
		</table>
	</table>

	<bean:define id="treebeanURL" name="treebean" property="property(URL)" />
	
	<html:link action='url' paramId='expId' paramName="treebean" paramProperty='property(ID_EXP)'>
		<bean:write name="treebean" property="property(NAME)" />
	</html:link>
	
</ispac:tree>
--%>

