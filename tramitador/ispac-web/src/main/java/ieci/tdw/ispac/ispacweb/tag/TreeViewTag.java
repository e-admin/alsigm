package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 *
 *
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
	isIDNodeTree -> identificador o cojunto de indentificadores de propiedades del ItemBean que formaran el identificador del Node en el Tree
	idTextPropiedad -> Texto a mostrar en el node
	idTagTexto -> nombre del tag en el cual hay que incorporar el idTextPropiedad
 */
public class TreeViewTag extends BodyTagSupport {
    String strIdTree;
    String strNameList;
    String strClassDiv;
    String strIdTagsExp;
    String[] arstrIdTagsExp;
    String strIdTagsImg;
    String[] arstrIdTagsImg;
    String strIdTagImgTipo;
    String strImgsSRCImgTipo;
    String strImgsSRCExpandir;
    String strImgsSRCContraer;
    String[] arstrImgsSRCExpandir;
    String[] arstrImgsSRCContraer;
    boolean bolHome;
    String strImgHome;
    boolean bolDibujaPuntos;
    String strImgSRCPuntosPrimerEle;
    String strImgSRCPuntosContinuaEle;
    String strImgSRCPuntosContinua;
    String strImgSRCPuntosUltimoEle;
    String strIdsIDNodeTree;
    String[] arstrIdsIDNodeTree;
    String strIdTextPropiedad;
    String strIdTagTexto;

    public TreeViewTag() {super();}

    public void otherDoStartTagOperations() {}

    public boolean theBodyShouldBeEvaluated() {return true;}

    public void otherDoEndTagOperations() {}

    public boolean shouldEvaluateRestOfPageAfterEndTag() {return true;}

    public int doStartTag() throws JspException, JspException {
        otherDoStartTagOperations();

        if (theBodyShouldBeEvaluated()) 
            return EVAL_BODY_BUFFERED;
        
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException, JspException {
        otherDoEndTagOperations();

        if (shouldEvaluateRestOfPageAfterEndTag()) 
            return EVAL_PAGE;
        return SKIP_PAGE;
    }

    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        out.println(processingBody(bodyContent.getString()));

        bodyContent.clearBody();
    }

    public void handleBodyContentException(Exception ex) throws JspException {throw new JspException("error in TreeViewTag: " + ex);}

    public int doAfterBody() throws JspException {
        try {
            BodyContent bodyContent = getBodyContent();
            JspWriter out = bodyContent.getEnclosingWriter();
            fillArrays();

            writeTagBodyContent(out, bodyContent);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }

        if (theBodyShouldBeEvaluatedAgain())
            return EVAL_BODY_AGAIN;
        
        return SKIP_BODY;
    }

    public boolean theBodyShouldBeEvaluatedAgain() {return false;}

    public String processingBody(String strBody) {
        int intNivelTree = 0;
        String strRespuesta = null;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        List nodetreelist=(List)request.getAttribute(strNameList);

        if (request.getParameter("nivelTree") == null) intNivelTree = 0;
        else intNivelTree = Integer.parseInt(request.getParameter("nivelTree").toString());

        try {
            //Preparamos el documento XML para recibir el cuerpo del TagTree
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader("<span style='padding-left:" + (intNivelTree * 17) + "px'><div class='" + strClassDiv + "' id=''>" + strBody + "</div></span>")));

            //Preparamos el elemento a replicar
            Element elementDiv = (Element)document.getElementsByTagName("div").item(0).cloneNode(true);

            //Complementamos el diseño establecido con las propiedades definidas
            for (int ind=0; ind<nodetreelist.size(); ind++) {
                if (ind!=0) document.getElementsByTagName("span").item(0).appendChild(elementDiv.cloneNode(true));
                ((Element)document.getElementsByTagName("div").item(ind)).setAttribute("id", getIDDiv((ItemBean)nodetreelist.get(ind)));
                for (int i=0; i<document.getElementsByTagName("div").item(ind).getChildNodes().getLength(); i++) {
                    if (document.getElementsByTagName("div").item(ind).getChildNodes().item(i).getNodeType() == 1) {
                        updateTag(document, (Element)document.getElementsByTagName("div").item(ind).getChildNodes().item(i), (String)((ItemBean)nodetreelist.get(ind)).getProperty(strIdTextPropiedad), intNivelTree);                    }
                }
            }

            if (nodetreelist.size() == 0) {document = null;}
            else if (request.getParameter("numHijos") != null)
                if (Integer.parseInt(request.getParameter("numHijos")) == document.getElementsByTagName("div").getLength()) document = null;


            //Preparamos el documento XML para convertirlo en String
            OutputFormat format = new OutputFormat(document);
            StringWriter stringOut = new StringWriter();
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.serialize(document);

            //Eliminamos la cabecera del XML y los espacios entre etiquetas.
            //Añadimos una capa contendora si es el inicio del tree
            strRespuesta = stringOut.toString().substring(stringOut.toString().indexOf("<span")).replaceAll(">  <", "><");
            if (intNivelTree == 0) strRespuesta = "<div id='" + strIdTree + "'>" + strRespuesta + "</div>";

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return (strRespuesta == null)?"":strRespuesta;
    }

    //Apartir de la propiedad IdsIDNodeTree creamos el ID para el nodo
    private String getIDDiv(ItemBean ibBean) throws ISPACException  {
        String strID = null;

        for (int ind=0; ind<arstrIdsIDNodeTree.length; ind++)
            strID = ibBean.getProperty(arstrIdsIDNodeTree[ind]).toString();

        return strID;
    }

    //Prepara los diferentes elementos del nodo rellenado sus propiedades
    private void updateTag(Document docAux, Element element, String strTexto, int intNivelTree) {
        if (element.getNodeName().equals("img")) {
            for (int i=arstrIdTagsImg.length -1; i>=0; i--) {
                if(arstrIdTagsImg[i].equals(element.getAttribute("id"))) {
                    element.setAttribute("src", arstrImgsSRCExpandir[i]);
                }
            }
        }
        else {
            if(strIdTagTexto.equals(element.getAttribute("id"))) {
                element.appendChild(docAux.createTextNode(strTexto));
            }
        }

        for (int i=arstrIdTagsExp.length -1; i>=0; i--) {
            if(arstrIdTagsExp[i].equals(element.getAttribute("id"))) {
                element.setAttribute("onclick", "javascript:abre(this.parentNode, " + (intNivelTree +1) +");");
            }
        }
    }

    //Rellena los arrays de propiedades apartir de un string delimitado por ;
    private void fillArrays() {
        arstrIdTagsExp = cutterString(strIdTagsExp);
        arstrIdTagsImg = cutterString(strIdTagsImg);
        arstrImgsSRCExpandir = cutterString(strImgsSRCExpandir);
        arstrImgsSRCContraer = cutterString(strImgsSRCContraer);
        arstrIdsIDNodeTree = cutterString(strIdsIDNodeTree);
    }

    //Corta el string delimitado por ;
    private String[] cutterString(String value) {
        String[] strElementos = new String[0];
        String[] strAux = null;
        int intIndice = 0;

        while (value.length() > 0) {
            strAux = strElementos;
            strElementos = new String[strElementos.length +1];
            for (int i=strAux.length -1; i>=0; i--) strElementos[i]=strAux[i];
            strElementos[intIndice] = value.substring(0, value.indexOf(";"));
            value = value.substring(value.indexOf(";") +1);
            intIndice++;
        }

        return strElementos;
    }

    /*private int lengthArray(String value, String busca) {
        int intRepeticiones;

        while (value.length() > 0) {
            strElementos[intIndice] = value.substring(0, value.indexOf(";"));
            value = value.substring(0, value.indexOf(";") +1);
            intIndice++;
        }

        return
    }*/

    /*private String[][] cutterString(String value) {
        String[][] strElementos = null;
        int intIndice = 0, intIndiceSub = 0;

        while (value.length() > 0) {
            if (value.indexOf(",") < value.indexOf(";") && value.indexOf(",") != -1) {
                strElementos[intIndice][intIndiceSub] = value.substring(0, value.indexOf(","));
                value = value.substring(0, value.indexOf(",") +1);
                intIndiceSub++;
            }
            else {
                strElementos[intIndice][intIndiceSub] = value.substring(0, value.indexOf(";"));
                value = value.substring(0, value.indexOf(";") +1);
                intIndice++;
            }
        }

        return strElementos;
    }*/

    public String getIdTree() {return strIdTree;}

    public void setIdTree(String value) {strIdTree = value;}

    public String getNameList() {return strNameList;}

    public void setNameList(String value) {strNameList = value;}

    public String getClassDiv() {return strClassDiv;}

    public void setClassDiv(String value) {strClassDiv = value;}

    public String getIdTagsExp() {return strIdTagsExp;}

    public void setIdTagsExp(String value) {strIdTagsExp = value;}

    public String getIdTagsImg() {return strIdTagsImg;}

    public void setIdTagsImg(String value) {strIdTagsImg = value;}

    public String getIdTagImgTipo() {return strIdTagImgTipo;}

    public void setIdTagImgTipo(String value) {strIdTagImgTipo = value;}

    public String getImgsSRCImgTipo() {return strImgsSRCImgTipo;}

    public void setImgsSRCImgTipo(String value) {strImgsSRCImgTipo = value;}

    public String getImgsSRCExpandir() {return strImgsSRCExpandir;}

    public void setImgsSRCExpandir(String value) {strImgsSRCExpandir = value;}

    public String getImgsSRCContraer() {return strImgsSRCContraer;}

    public void setImgsSRCContraer(String value) {strImgsSRCContraer = value;}

    public boolean getBolHome() {return bolHome;}

    public void setBolHome(boolean value) {bolHome = value;}

    public String getImgHome() {return strImgHome;}

    public void setImgHome(String value) {strImgHome = value;}

    public boolean getDibujaPuntos() {return bolDibujaPuntos;}

    public void setDibujaPuntos(boolean value) {bolDibujaPuntos = value;}

    public String getImgSRCPuntosPrimerEle() {return strImgSRCPuntosPrimerEle;}

    public void setImgSRCPuntosPrimerEle(String value) {strImgSRCPuntosPrimerEle = value;}

    public String getImgSRCPuntosContinuaEle() {return strImgSRCPuntosContinuaEle;}

    public void setImgSRCPuntosContinuaEle(String value) {strImgSRCPuntosContinuaEle = value;}

    public String getImgSRCPuntosContinua() {return strImgSRCPuntosContinua;}

    public void setImgSRCPuntosContinua(String value) {strImgSRCPuntosContinua = value;}

    public String getImgSRCPuntosUltimoEle() {return strImgSRCPuntosUltimoEle;}

    public void setImgSRCPuntosUltimoEle(String value) {strImgSRCPuntosUltimoEle = value;}

    public String getIdsIDNodeTree() {return strIdsIDNodeTree;}

    public void setIdsIDNodeTree(String value) {strIdsIDNodeTree = value;}

    public String getIdTextPropiedad() {return strIdTextPropiedad;}

    public void setIdTextPropiedad(String value) {strIdTextPropiedad = value;}

    public String getIdTagTexto() {return strIdTagTexto;}

    public void setIdTagTexto(String value) {strIdTagTexto = value;}
}