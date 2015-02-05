package descripcion.model.xml.card;

import gcontrol.model.TipoAcceso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.w3c.dom.Node;

import util.StringOwnTokenizer;

import common.Constants;
import common.Messages;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.util.DateQualifierHelper;
import common.util.ObjectCloner;
import common.util.UrlHelper;
import common.util.XmlFacade;

import deposito.model.GestorEstructuraDepositoBI;
import descripcion.TipoCampoDatoConstants;
import descripcion.model.ValoresFicha;
import descripcion.model.xml.card.utils.ValueManager;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefCampoEspecial;
import descripcion.model.xml.definition.DefCampoTabla;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.definition.DefFormatoFecha;
import descripcion.model.xml.definition.DefInformacionEspecifica;
import descripcion.model.xml.definition.DefTipos;
import descripcion.model.xml.format.DefFmtCampo;
import descripcion.model.xml.format.DefFmtElemento;
import descripcion.model.xml.format.DefFmtElementoCabecera;
import descripcion.model.xml.format.DefFmtElementoEtiquetaDato;
import descripcion.model.xml.format.DefFmtElementoHipervinculo;
import descripcion.model.xml.format.DefFmtElementoSeccion;
import descripcion.model.xml.format.DefFmtElementoTabla;
import descripcion.model.xml.format.DefFmtElementoTablaTextual;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.model.xml.format.DefFmtTiposElemento;
import descripcion.vos.TextoTablaValidacionVO;

/**
 * Factoría para la composición de fichas de descripción.
 */
public class FichaFactory {

    /** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(FichaFactory.class);

    /**
     * Nombre del parámetro que contiene el identificador del objeto descrito en
     * los hipervínculos.
     */
    private static final String OBJECT_ID_PARAM_NAME = "id";

    /**
     * Nombre del parámetro que contiene el identificador del objeto descrito en
     * los hipervínculos para las tablas.
     */
    private static final String OBJECT_ID_TABLE_PARAM_NAME = "idObjeto";

    /** Cliente del servicio. */
    private ServiceSession serviceSession = null;

    /** Locale */
    private Locale locale = null;

    /** Id del elemento del cuadro */
    private String idElementoCF = null;

    /**
     * Constructor.
     *
     * @param serviceSession
     *            Cliente del servicio.
     */
    private FichaFactory(ServiceSession serviceSession, Locale locale,
            String idElementoCF) {
        this.serviceSession = serviceSession;
        this.idElementoCF = idElementoCF;
    }

    /**
     * Instancia un objeto FichaFactory.
     *
     * @param serviceSession
     *            Cliente del servicio.
     * @return Instancia de FichaFactory.
     */
    public static FichaFactory getInstance(ServiceSession serviceSession,
            Locale locale, String idElementoCF) {
        return new FichaFactory(serviceSession, locale, idElementoCF);
    }

    /**
     * Constructor.
     *
     * @param serviceSession
     *            Cliente del servicio.
     */
    private FichaFactory(ServiceSession serviceSession) {
        this.serviceSession = serviceSession;

    }

    /**
     * Instancia un objeto FichaFactory.
     *
     * @param serviceSession
     *            Cliente del servicio.
     * @return Instancia de FichaFactory.
     */
    public static FichaFactory getInstance(ServiceSession serviceSession) {
        return new FichaFactory(serviceSession);
    }

    /**
     * Componer una ficha.
     *
     * @param id
     *            Identificador del objeto descrito.
     * @param idFichaDescr
     *            Identificador de la definición de la ficha de descripción.
     * @param tipoAcceso
     *            Tipo de acceso.
     * @param tipoFicha
     *            Tipo de ficha.
     * @param valoresEspeciales
     *            Valores especiales de la ficha.
     * @param udoc
     *            Unidad documental en la relación
     * @return Ficha.
     */
    public Ficha componerFicha(String id, String idFichaDescr, int tipoAcceso,
            int tipoFicha, Map valoresEspeciales, Object udoc,
            boolean exportacion) {
        // transferencias.vos.UnidadDocumentalVO udoc){
        // Definición de la ficha
        DefFicha defFicha = DefFichaFactory.getInstance(
                serviceSession.getSessionOwner()).getDefFichaById(idFichaDescr);

        // Definición del formato de la ficha
        DefFmtFicha defFmtFicha = DefFmtFichaFactory.getInstance(
                serviceSession.getSessionOwner()).getDefFmtFicha(idFichaDescr,
                tipoAcceso);

        // Valores de la ficha
        ValoresFicha valoresFicha = ValoresFicha.getInstance(serviceSession,
                id, tipoFicha, udoc);
        valoresFicha.addValoresEspeciales(valoresEspeciales);

        // Obtener los posibles formatos con los que mostrar la ficha
        List ltFormatos = DefFmtFichaFactory.getInstance(
                serviceSession.getSessionOwner()).findFmtFichaAccesibles(
                idFichaDescr, tipoAcceso);

        // Componer la ficha
        return componerFicha(defFicha, defFmtFicha, valoresFicha, ltFormatos,
                tipoAcceso == TipoAcceso.EDICION, exportacion);

    }

    /**
     * Componer una ficha.
     *
     * @param id
     *            Identificador del objeto descrito.
     * @param idFichaDescr
     *            Identificador de la definición de la ficha de descripción.
     * @param tipoAcceso
     *            Tipo de acceso.
     * @param tipoFicha
     *            Tipo de ficha.
     * @param valoresEspeciales
     *            Valores especiales de la ficha.
     * @param udoc
     *            Unidad documental en la relación
     * @return Ficha.
     */
    /*
     * TODO: eliminar cuando se compruebe definitivamente que no se utiliza
     *
     * public Ficha componerFicha(String id, String idFichaDescr, int
     * tipoAcceso, int tipoFicha, int tipoElemento, Map valoresEspeciales){ //
     * Definición de la ficha DefFicha defFicha =
     * DefFichaFactory.getInstance(serviceSession
     * .getSessionOwner()).getDefFichaById(idFichaDescr);
     *
     * // Definición del formato de la ficha DefFmtFicha defFmtFicha =
     * DefFmtFichaFactory
     * .getInstance(serviceSession.getSessionOwner()).getDefFmtFicha
     * (idFichaDescr, tipoAcceso);
     *
     * // Valores de la ficha ValoresFicha valoresFicha =
     * ValoresFicha.getInstance(serviceSession, id, tipoFicha, tipoElemento);
     * valoresFicha.addValoresEspeciales(valoresEspeciales);
     *
     * // Obtener los posibles formatos con los que mostrar la ficha List
     * ltFormatos =
     * DefFmtFichaFactory.getInstance(serviceSession.getSessionOwner
     * ()).findFmtFichaAccesibles(idFichaDescr, tipoAcceso);
     *
     * // Componer la ficha return componerFicha(defFicha, defFmtFicha,
     * valoresFicha, ltFormatos, tipoAcceso == TipoAcceso.EDICION);
     *
     * }
     */

    /**
     * Compone una ficha en función del formato y los valores.
     *
     * @param defFmtFicha
     *            Definición del formato de la ficha.
     * @param valFicha
     *            Valores de la ficha.
     * @param ltFormatos
     *            Lista de posibles formatos para la ficha.
     * @param editable
     *            Si la ficha es editable.
     * @return Ficha de descripción.
     */
    public Ficha componerFicha(DefFicha defFicha, DefFmtFicha defFmtFicha,
            ValoresFicha valFicha, List ltFormatos, boolean editable,
            boolean exportacion) {
        Ficha ficha = new Ficha(valFicha.getTipoFicha());
        Area area;
        DefFmtElementoSeccion seccion;

        // Información general de la ficha
        ficha.setLtFormatos(ltFormatos);
        ficha.setIdFormato(defFmtFicha.getId());
        ficha.setId(valFicha.getId());
        ficha.setIdFicha(defFicha.getId());
        ficha.setEditable(editable);
        ficha.setTipoAcceso(defFmtFicha.getTipoAcceso());
        ficha.setDefFicha(defFicha);
        ficha.setAutomaticos(defFmtFicha.isAutomaticos());

        // Componer la ficha
        for (int i = 0; i < defFmtFicha.getTotalElementos(); i++) {
            seccion = defFmtFicha.getElemento(i);

            // Crear el área
            area = new Area();
            area.setId(new Integer(i + 1).toString());
            area.setDesplegada(seccion.getDesplegada());
            area.setEtiqueta(new Etiqueta(seccion.getEtiqueta().getTitulo()
                    .getPredeterminado(), seccion.getEtiqueta().getEstilo()));

            // Elementos de la sección
            for (int j = 0; j < seccion.getTotalElementos(); j++)
                area.addElemento(createElemento(defFicha,
                        seccion.getElemento(j), valFicha, null,
                        DefFmtTiposElemento.TIPO_ELEMENTO_DESCONOCIDO,
                        editable, false, exportacion));

            // Añadir el área creada
            ficha.addArea(area);
        }

        return ficha;
    }

    /**
     * Crea un elemento de la ficha en función de su formato.
     *
     * @param defFmtElemento
     *            Formato del elemento.
     * @param valFicha
     *            Valores de la ficha.
     * @return Elemento de la ficha.
     */
    public Elemento createElemento(DefFicha defFicha,
            DefFmtElemento defFmtElemento, ValoresFicha valFicha,
            Elemento elementoPadre, short tipoElementoPadre, boolean editable,
            boolean isTabla, boolean exportacion) {
        Elemento elemento = null;

        switch (defFmtElemento.getTipo()) {
        case DefFmtTiposElemento.TIPO_ELEMENTO_HIPERVINCULO: {
            if (isTabla) {
                if (((DefFmtElementoHipervinculo) defFmtElemento).getVinculo() != null
                        && StringUtils
                                .isNotEmpty(((DefFmtElementoHipervinculo) defFmtElemento)
                                        .getVinculo().getParameter())
                        && StringUtils
                                .isNotEmpty(((DefFmtElementoHipervinculo) defFmtElemento)
                                        .getVinculo().getIdCampo())) {

                    elemento = new ElementoHipervinculoEnTabla();

                    // Campo del elemento
                    ((ElementoHipervinculoEnTabla) elemento)
                            .setEstilo(((DefFmtElementoHipervinculo) defFmtElemento)
                                    .getVinculo().getEstilo());

                    String paramName = ((DefFmtElementoHipervinculo) defFmtElemento)
                            .getVinculo().getParameter();
                    String paramValue = ((DefFmtElementoHipervinculo) defFmtElemento)
                            .getVinculo().getIdCampo();

                    // Edicion
                    Valor[] valores = valFicha.getValues(paramValue);

                    // Valores del elemento

                    String saltoLinea = Constants.SALTO_LINEA_HTML;
                    for (int countValores = 0; (valores != null)
                            && (countValores < valores.length); countValores++) {
                        Valor valor = valores[countValores];
                        String strValor = UrlHelper.addUrlParameter(
                                ((DefFmtElementoHipervinculo) defFmtElemento)
                                        .getVinculo().getUrl(), paramName,
                                valor.getValor(), false);
                        strValor = UrlHelper.addUrlParameter(strValor,
                                OBJECT_ID_TABLE_PARAM_NAME, valFicha.getId(),
                                false);

                        if (!exportacion) {
                            strValor = StringUtils.replace(strValor, "\n",
                                    saltoLinea, -1);
                            strValor = StringUtils.replace(strValor, "\r", "",
                                    -1);
                        }

                        valor.setValor(strValor);
                        ((ElementoHipervinculoEnTabla) elemento)
                                .addValor(valor);
                    }

                }
            } else {
                elemento = new ElementoHipervinculo();

                ((ElementoHipervinculo) elemento).setUrl(UrlHelper
                        .addUrlParameter(
                                ((DefFmtElementoHipervinculo) defFmtElemento)
                                        .getVinculo().getUrl(),
                                OBJECT_ID_PARAM_NAME, valFicha.getId(), false));
                ((ElementoHipervinculo) elemento)
                        .setTarget(((DefFmtElementoHipervinculo) defFmtElemento)
                                .getVinculo().getTarget());
                ((ElementoHipervinculo) elemento)
                        .setTexto(((DefFmtElementoHipervinculo) defFmtElemento)
                                .getVinculo().getTitulo().getPredeterminado());
                ((ElementoHipervinculo) elemento)
                        .setEstilo(((DefFmtElementoHipervinculo) defFmtElemento)
                                .getVinculo().getEstilo());
            }
            break;
        }

        case DefFmtTiposElemento.TIPO_ELEMENTO_TABLA: {
            elemento = new ElementoTabla();

            String idTabla = ((DefFmtElementoTabla) defFmtElemento)
                    .getIdCampoTbl();
            ((ElementoTabla) elemento).setEdicion(createEdicionTabla(idTabla,
                    defFicha));
            ((ElementoTabla) elemento)
                    .setMostrarCabeceras(((DefFmtElementoTabla) defFmtElemento)
                            .isMostrarCabeceras());
            ((ElementoTabla) elemento).setSistemaExterno(defFicha.getTabla(
                    idTabla).getSistemaExterno());
            Set ordenes = new TreeSet();
            for (int i = 0; i < ((DefFmtElementoTabla) defFmtElemento)
                    .getTotalElementos(); i++) {
                Elemento elem = createElemento(defFicha,
                        ((DefFmtElementoTabla) defFmtElemento).getElemento(i),
                        valFicha, elemento,
                        DefFmtTiposElemento.TIPO_ELEMENTO_TABLA, editable,
                        true, exportacion);
                ((ElementoTabla) elemento).addElemento(elem);

                if (elem.getTipo() == TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO)
                    ordenes.addAll(((ElementoEtiquetaDato) elem).getOrdenes());
                if (elem.getTipo() == TiposElemento.TIPO_ELEMENTO_HIPERVINCULO)
                    ordenes.addAll(((ElementoHipervinculoEnTabla) elem)
                            .getOrdenes());
            }

            fillTableElementsValues((ElementoTabla) elemento, ordenes);
            ((ElementoTabla) elemento).setNumeroElementos(String
                    .valueOf(((DefFmtElementoTabla) defFmtElemento)
                            .getTotalElementos()));
            break;
        }

        case DefFmtTiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL: {
            elemento = new ElementoTablaTextual();

            String idTabla = ((DefFmtElementoTabla) defFmtElemento)
                    .getIdCampoTbl();
            ((ElementoTabla) elemento).setEdicion(createEdicionTabla(idTabla,
                    defFicha));
            ((ElementoTabla) elemento)
                    .setMostrarCabeceras(((DefFmtElementoTablaTextual) defFmtElemento)
                            .isMostrarCabeceras());
            ((ElementoTabla) elemento).setSistemaExterno(defFicha.getTabla(
                    idTabla).getSistemaExterno());
            Set ordenes = new TreeSet();
            for (int i = 0; i < ((DefFmtElementoTablaTextual) defFmtElemento)
                    .getTotalElementos(); i++) {
                Elemento elem = createElemento(defFicha,
                        ((DefFmtElementoTablaTextual) defFmtElemento)
                                .getElemento(i), valFicha, elemento,
                        DefFmtTiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL,
                        editable, false, exportacion);
                ((ElementoTablaTextual) elemento).addElemento(elem);

                if (elem.getTipo() == TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO)
                    ordenes.addAll(((ElementoEtiquetaDato) elem).getOrdenes());
            }

            fillTableElementsValues((ElementoTabla) elemento, ordenes);
            ((ElementoTabla) elemento).setNumeroElementos(String
                    .valueOf(((DefFmtElementoTabla) defFmtElemento)
                            .getTotalElementos()));
            break;
        }

        case DefFmtTiposElemento.TIPO_ELEMENTO_CABECERA: {
            elemento = new ElementoCabecera();
            for (int i = 0; i < ((DefFmtElementoCabecera) defFmtElemento)
                    .getTotalElementos(); i++)
                ((ElementoCabecera) elemento).addElemento(createElemento(
                        defFicha, ((DefFmtElementoCabecera) defFmtElemento)
                                .getElemento(i), valFicha, null,
                        DefFmtTiposElemento.TIPO_ELEMENTO_CABECERA, editable,
                        false, exportacion));
            break;
        }

        default: // case DefFmtTiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
        {
            elemento = new ElementoEtiquetaDato();

            // Campo del elemento
            DefFmtCampo campo = ((DefFmtElementoEtiquetaDato) defFmtElemento)
                    .getCampo();
            ((ElementoEtiquetaDato) elemento).setEstilo(campo.getEstilo());

            // Edicion
            Valor[] valores = valFicha.getValues(campo.getId());

            ((ElementoEtiquetaDato) elemento)
                    .setEdicion(createEdicionDato(campo, defFicha,
                            elementoPadre, tipoElementoPadre, valores));

            // Valores del elemento

            String saltoLinea = Constants.SALTO_LINEA_HTML;

            for (int countValores = 0; (valores != null)
                    && (countValores < valores.length); countValores++) {
                Valor valor = valores[countValores];
                EdicionDato edicionDato = ((ElementoEtiquetaDato) elemento)
                        .getEdicion();
                if (edicionDato != null) {
                    if ((TipoCampoDatoConstants.TEXTO_LARGO.equals(String
                            .valueOf(edicionDato.getTipo())) || edicionDato
                            .isMultilinea())
                            && (editable == false || !edicionDato.isEditable())) {

                        String strValor = valor.getValor();
                        if (!exportacion) {
                            strValor = StringUtils.replace(strValor, "\n",
                                    saltoLinea, -1);
                            strValor = StringUtils.replace(strValor, "\r", "",
                                    -1);
                        }
                        valor.setValor(strValor);
                        // valor.setIncluirCData(false);
                    }
                }
                ((ElementoEtiquetaDato) elemento).addValor(valor);
            }

            break;
        }
        }
        if (defFmtElemento.getScroll() != null
                && defFmtElemento.getScroll().equals(Constants.TRUE_STRING))
            elemento.setScroll(Constants.TRUE_STRING);
        else
            elemento.setScroll(Constants.FALSE_STRING);
        elemento.setEtiqueta(new Etiqueta(defFmtElemento.getEtiqueta()
                .getTitulo().getPredeterminado(), defFmtElemento.getEtiqueta()
                .getEstilo()));

        return elemento;
    }

    /**
     * Obtiene la información de edición de una tabla.
     *
     * @param idCampoTbl
     *            Identificador de la tabla.
     * @param defFicha
     *            Definición de la ficha.
     * @return Información de la edición de la tabla.
     */
    protected Edicion createEdicionTabla(String idCampoTbl, DefFicha defFicha) {
        Edicion edicion = null;

        if (StringUtils.isNotBlank(idCampoTbl)) {
            edicion = new Edicion();
            edicion.setId(idCampoTbl);

            DefCampoTabla tabla = defFicha.getTabla(idCampoTbl);
            if (tabla != null)
                edicion.setEditable(tabla.isEditable());
        }

        return edicion;
    }

    private EdicionDato createEdicionDatoParaCampoEspecial(DefFmtCampo campo,
            DefFicha defFicha) {

        DefCampoEspecial dato = defFicha.getDatoEspecial(campo.getId());
        EdicionDato edicion = null;

        if (dato != null
                && !campo.getId().equals(
                        DefTipos.CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA)) {
            switch (dato.getTipoDato()) {
            case DefTipos.TIPO_DATO_TEXTO_CORTO:

                edicion = new EdicionDatoTextoCorto();
                edicion.setId(campo.getId());
                edicion.setTipo(dato.getTipoDato());
                edicion.setEditable(dato.isEditable());
                edicion.setValorInicial(new ValorInicial(dato.getValorInicial()));
                edicion.setObligatorio(dato.isObligatorio());
                edicion.setMultilinea(campo.isMultilinea());
                break;

            case DefTipos.TIPO_DATO_TEXTO_LARGO:

                edicion = new EdicionDatoTextoLargo();
                edicion.setId(campo.getId());
                edicion.setTipo(dato.getTipoDato());
                edicion.setEditable(dato.isEditable());
                edicion.setValorInicial(new ValorInicial(dato.getValorInicial()));
                edicion.setObligatorio(dato.isObligatorio());
                edicion.setMultilinea(campo.isMultilinea());
                break;
            }

        }

        return edicion;
    }

    private boolean isCampoEspecial(Elemento elemento) {
        if (elemento instanceof ElementoEtiquetaDato) {
            ElementoEtiquetaDato elementoEtiquetaDato = (ElementoEtiquetaDato) elemento;

            return (elementoEtiquetaDato.getEdicion() != null
                    && common.util.StringUtils.isNotEmpty(elementoEtiquetaDato
                            .getEdicion().getId())
                    && NumberUtils.isNumber(elementoEtiquetaDato.getEdicion()
                            .getId()) && Integer.parseInt(elementoEtiquetaDato
                    .getEdicion().getId()) < 0);
        }
        return false;

    }

    private boolean isElementoPadreEditable(Elemento elementoPadre) {

        if (elementoPadre != null) {
            XmlFacade xmlFacade = new XmlFacade(elementoPadre.toXML(3));
            Node node = xmlFacade.getNode("Elemento/Edicion/Editable");
            return Constants.TRUE_STRING.equalsIgnoreCase(node.getFirstChild()
                    .getNodeValue());
        }
        return false;
    }

    /**
     * Obtiene la información de edición de un campo de tipo dato.
     *
     * @param campo
     *            Definición del formato del campo.
     * @param defFicha
     *            Definición de la ficha.
     * @return Información de la edición del campo de tipo dato.
     */
    protected EdicionDato createEdicionDato(DefFmtCampo campo,
            DefFicha defFicha, Elemento elementoPadre, short tipoElementoPadre,
            Valor[] valores) {
        EdicionDato edicion = null;

        if (StringUtils.isNotBlank(campo.getId())
                && ((campo.getId().charAt(0) == '-'))) {
            edicion = createEdicionDatoParaCampoEspecial(campo, defFicha);
        }

        else if (StringUtils.isNotBlank(campo.getId())
                && ((campo.getId().charAt(0) != '-'))) {

            DefCampoDato dato = defFicha.getDato(campo.getId());
            if (dato != null) {
                DefInformacionEspecifica infoEspec = dato
                        .getInformacionEspecifica();
                switch (dato.getTipoDato()) {
                case DefTipos.TIPO_DATO_TEXTO_CORTO:

                    edicion = new EdicionDatoTextoCorto();

                    // Cargar los valores de validación
                    if (StringUtils.isNotBlank(infoEspec.getIdTblVld())) {
                        ((EdicionDatoTextoCorto) edicion).setIdTblVld(infoEspec
                                .getIdTblVld());

                        // Interfaz de acceso a la lógica de descripción
                        GestionDescripcionBI descripcionBI = ServiceRepository
                                .getInstance(serviceSession)
                                .lookupGestionDescripcionBI();

                        List values = descripcionBI
                                .getValoresValidacion(infoEspec.getIdTblVld());
                        Map mapProcessed = new HashMap();
                        if (values != null) {
                            TextoTablaValidacionVO tblVld;
                            for (int i = 0; i < values.size(); i++) {
                                tblVld = (TextoTablaValidacionVO) values.get(i);
                                ((EdicionDatoTextoCorto) edicion)
                                        .addOpcion(new Opcion(
                                                tblVld.getValor(),
                                                tblVld.getValor()
                                                        .equals(campo
                                                                .getValorSeleccionPorDefecto())));

                                mapProcessed.put(tblVld.getValor(),
                                        tblVld.getValor());
                            }
                        }

                        if (valores != null) {
                            for (int i = 0; i < valores.length; i++) {
                                if (mapProcessed.get(valores[i].getValor()) == null) {
                                    ((EdicionDatoTextoCorto) edicion)
                                            .addOpcion(new Opcion(
                                                    valores[i].getValor(),
                                                    valores[i]
                                                            .getValor()
                                                            .equals(campo
                                                                    .getValorSeleccionPorDefecto())));
                                }
                            }
                        }

                    }
                    break;

                case DefTipos.TIPO_DATO_TEXTO_LARGO:

                    edicion = new EdicionDatoTextoLargo();
                    break;

                case DefTipos.TIPO_DATO_FECHA:

                    edicion = new EdicionDatoFecha();

                    // Cargar los formatos válidos de las fechas
                    for (int i = 0; i < infoEspec.getTotalFormatos(); i++) {
                        DefFormatoFecha formato = infoEspec.getFormato(i);
                        ((EdicionDatoFecha) edicion).addFormato(new Formato(
                                Messages.getString(Constants.ETIQUETA_FORMATO
                                        + "." + formato.getTipo(), locale),
                                formato.getTipo(), formato.getSeparador(),
                                formato.getTipo().equals(
                                        campo.getValorSeleccionPorDefecto())));
                    }

                    GestorEstructuraDepositoBI depositoBI = ServiceRepository
                            .getInstance(serviceSession)
                            .lookupGestorEstructuraDepositoBI();

                    if (!common.util.StringUtils.isEmpty(idElementoCF)
                            && depositoBI.isBloqueada(idElementoCF)) {

                        ((EdicionDatoFecha) edicion)
                                .setFechasExtremasEditables(false);
                    }

                    break;

                case DefTipos.TIPO_DATO_NUMERICO:

                    edicion = new EdicionDatoNumerico();
                    ((EdicionDatoNumerico) edicion).setTipoNumerico(infoEspec
                            .getTipoNumerico());

                    // Cargar la información del número
                    if (infoEspec.getTipoNumerico() != DefTipos.TIPO_NUMERICO_DESCONOCIDO) {
                        ((EdicionDatoNumerico) edicion).setTipoMedida(infoEspec
                                .getTipoMedida());
                        ((EdicionDatoNumerico) edicion)
                                .setUnidadMedida(infoEspec.getUnidadMedida());
                        ((EdicionDatoNumerico) edicion)
                                .setRangoMinimo(infoEspec.getRangoMinimo());
                        ((EdicionDatoNumerico) edicion)
                                .setRangoMaximo(infoEspec.getRangoMaximo());
                        ((EdicionDatoNumerico) edicion)
                                .setMostrarTipoNumero(campo
                                        .isMostrarTipoNumero());
                        ((EdicionDatoNumerico) edicion)
                                .setMostrarUnidadNumero(campo
                                        .isMostrarUnidadNumero());
                    }

                    // Cargar los valores de validación
                    if (StringUtils.isNotBlank(infoEspec.getIdTblVld())) {
                        ((EdicionDatoNumerico) edicion).setIdTblVld(infoEspec
                                .getIdTblVld());

                        // Interfaz de acceso a la lógica de descripción
                        GestionDescripcionBI descripcionBI = ServiceRepository
                                .getInstance(serviceSession)
                                .lookupGestionDescripcionBI();

                        List values = descripcionBI
                                .getValoresValidacion(infoEspec.getIdTblVld());
                        if (values != null) {
                            TextoTablaValidacionVO tblVld;
                            for (int i = 0; i < values.size(); i++) {
                                tblVld = (TextoTablaValidacionVO) values.get(i);
                                ((EdicionDatoNumerico) edicion)
                                        .addOpcion(new Opcion(
                                                tblVld.getValor(),
                                                tblVld.getValor()
                                                        .equals(campo
                                                                .getValorSeleccionPorDefecto())));
                            }
                        }
                    }
                    break;

                case DefTipos.TIPO_DATO_REFERENCIA:

                    edicion = new EdicionDatoReferencia();
                    ((EdicionDatoReferencia) edicion)
                            .setTipoReferencia(infoEspec.getTipoReferencia());

                    // Cargar la info de la referencia
                    if (infoEspec.getTipoReferencia() == DefTipos.TIPO_REFERENCIA_DESCRIPTOR) {
                        if (StringUtils.isNotBlank(infoEspec
                                .getIdsListasDescriptoras())) {
                            StringOwnTokenizer tok = new StringOwnTokenizer(
                                    infoEspec.getIdsListasDescriptoras(), ",");
                            while (tok.hasMoreTokens())
                                ((EdicionDatoReferencia) edicion)
                                        .addIdentificador(tok.nextToken());
                        }
                    }
                    break;
                }

                if (edicion != null) {
                    edicion.setId(campo.getId());
                    edicion.setTipo(dato.getTipoDato());
                    edicion.setEditable(dato.isEditable());
                    edicion.setMultivalor(dato.isMultivalor());
                    edicion.setObligatorio(dato.isObligatorio());
                    edicion.setValorInicial(new ValorInicial(dato
                            .getValorInicial()));
                    edicion.setPadreEditable(isElementoPadreEditable(elementoPadre));
                    edicion.setMultilinea(campo.isMultilinea());
                    if (tipoElementoPadre > 0) {
                        edicion.setTipoPadre(tipoElementoPadre);
                    }
                }
            }
        }

        return edicion;
    }

    /**
     * Rellena los valores de los elementos de una tabla.
     *
     * @param tabla
     *            Elemento de tipo tabla.
     * @param ordenes
     *            Lista de órdenes de los valores de los elementos de la tabla.
     */
    protected void fillTableElementsValues(ElementoTabla tabla, Set ordenes) {
        for (int i = 0; i < tabla.getTotalElementos(); i++) {
            Elemento elemTabla = tabla.getElemento(i);
            if (elemTabla.getTipo() == TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO) {
                ElementoEtiquetaDato elemDato = (ElementoEtiquetaDato) elemTabla;

                Iterator it = ordenes.iterator();
                List valores = new LinkedList();
                int contValores = 0;
                Integer orden;

                // Añadir los valores que se correspondan
                while (it.hasNext() && contValores < elemDato.getTotalValores()) {
                    orden = (Integer) it.next();
                    if (orden.intValue() == elemDato.getValor(contValores)
                            .getOrden()) {
                        valores.add(elemDato.getValor(contValores));
                        contValores++;
                    } else {
                        // Añadir valor vacío
                        valores.add(new Valor(orden.intValue()));
                    }
                }

                // Añadir el resto de valores vacíos
                while (it.hasNext()) {
                    orden = (Integer) it.next();
                    valores.add(new Valor(orden.intValue()));
                }

                // Actualizar la lista de valores
                elemDato.setValores(valores);
            } else if (elemTabla.getTipo() == TiposElemento.TIPO_ELEMENTO_HIPERVINCULO) {
                ElementoHipervinculoEnTabla elemDato = (ElementoHipervinculoEnTabla) elemTabla;

                Iterator it = ordenes.iterator();
                List valores = new LinkedList();
                int contValores = 0;
                Integer orden;

                // Añadir los valores que se correspondan
                while (it.hasNext() && contValores < elemDato.getTotalValores()) {
                    orden = (Integer) it.next();
                    if (orden.intValue() == elemDato.getValor(contValores)
                            .getOrden()) {
                        valores.add(elemDato.getValor(contValores));
                        contValores++;
                    } else {
                        // Añadir valor vacío
                        valores.add(new Valor(orden.intValue()));
                    }
                }

                // Añadir el resto de valores vacíos
                while (it.hasNext()) {
                    orden = (Integer) it.next();
                    valores.add(new Valor(orden.intValue()));
                }

                // Actualizar la lista de valores
                elemDato.setValores(valores);
            }
        }
    }

    /**
     * Modifica los valores de la ficha.
     *
     * @param ficha
     *            Ficha a modificar.
     * @param parameters
     *            Parámetros de la ficha a modificar.
     * @param exportar
     * @return Errores en la validación.
     */
    public ActionErrors updateValores(Ficha ficha, Map parameters,
            boolean exportar) {
        ActionErrors errors = new ActionErrors();

        for (int contAreas = 0; contAreas < ficha.getTotalAreas(); contAreas++)
            updateContenedorElementos(ficha, ficha.getArea(contAreas),
                    parameters, errors, exportar);

        return errors;
    }

    /**
     * Modifica los elementos del contenedor de elementos.
     *
     * @param contenedor
     *            Contenedor de elementos.
     * @param parameters
     *            Parámetros de la ficha a modificar.
     * @param errors
     *            Lista de errores en la validación.
     * @param exportar
     */
    protected void updateContenedorElementos(Ficha ficha,
            ContenedorElementos contenedor, Map parameters,
            ActionErrors errors, boolean exportar) {
        for (int contElementos = 0; contElementos < contenedor
                .getTotalElementos(); contElementos++) {
            Elemento elem = contenedor.getElemento(contElementos);

            switch (elem.getTipo()) {
            case TiposElemento.TIPO_ELEMENTO_AREA:
            case TiposElemento.TIPO_ELEMENTO_CABECERA:
            case TiposElemento.TIPO_ELEMENTO_TABLA:
            case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:

                if (elem.getTipo() == TiposElemento.TIPO_ELEMENTO_TABLA
                        || elem.getTipo() == TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL) {

                    ElementoTabla elementoTabla = (ElementoTabla) elem;
                    String[] deletedRowsTable = (String[]) parameters
                            .get("deletedRowsTable"
                                    + elementoTabla.getEdicion().getId());
                    if (!common.util.StringUtils
                            .isContenidoVacio(deletedRowsTable))
                        elementoTabla.setFilasABorrar(deletedRowsTable[0]);
                }

                updateContenedorElementos(ficha, (ContenedorElementos) elem,
                        parameters, errors, exportar);
                break;

            case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
                updateElemento((ElementoEtiquetaDato) elem, parameters, errors,
                        contenedor, exportar);
                break;
            }

        }
    }

    /**
     * Modifica el elemento.
     *
     * @param elemento
     *            Elemento de tipo etiqueta-dato.
     * @param parameters
     *            Parámetros de la ficha a modificar.
     * @param errors
     *            Lista de errores en la validación.
     * @param exportar
     */
    protected void updateElemento(ElementoEtiquetaDato elemento,
            Map parameters, ActionErrors errors,
            ContenedorElementos contenedor, boolean exportar) {
        if (logger.isDebugEnabled())
            logger.debug("Comprobando campo: "
                    + elemento.getEtiqueta().getTitulo());

        // Comprobar que sea editable
        if (elemento.getEdicion() != null
                && (elemento.getEdicion().isEditable() || elemento.getEdicion()
                        .isPadreEditable())) {
            ValueManager vmgr = new ValueManager(elemento.getEtiqueta()
                    .getTitulo(), elemento.getEdicion(), errors, serviceSession);
            Valor[] valoresFormulario = vmgr.getValoresFormulario(parameters,
                    contenedor, elemento);

            // Validar si el campo es obligatorio
            if (elemento.getEdicion().isObligatorio()) {
                boolean isEmpty = true;
                for (int i = 0; isEmpty && (i < valoresFormulario.length); i++) {
                    if (StringUtils.isNotBlank(valoresFormulario[i].getValor())
                            || DateQualifierHelper.CALIFICADOR_SIN_FECHA
                                    .equals(valoresFormulario[i]
                                            .getCalificador()))
                        isEmpty = false;
                }

                if (isEmpty) {
                    if (exportar)
                        elemento.clearValores();
                    else
                        errors.add(ActionErrors.GLOBAL_MESSAGE,
                                new ActionError(Constants.ERROR_REQUIRED,
                                        elemento.getEtiqueta().getTitulo()));
                }
            } else if (!elemento.getEdicion().isMultivalor()
                    && (valoresFormulario.length > 1)) {
                // Campo no es multivalor
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                        Constants.ERRORS_NO_MULTIVALOR, elemento.getEtiqueta()
                                .getTitulo()));
            }

            // Comprobar los valores existentes
            for (int i = 0; i < elemento.getTotalValores(); i++) {
                Valor valor = elemento.getValor(i);

                if (i < valoresFormulario.length) {
                    Valor valorFormulario = valoresFormulario[i];

                    if (logger.isDebugEnabled())
                        logger.debug("Valor existente #" + i + ":"
                                + Constants.NEWLINE + "Valor original  : "
                                + valor + Constants.NEWLINE
                                + "Valor formulario: " + valorFormulario);

                    boolean cambioFormatoSeparador = false;
					if (elemento.getEdicion().getTipo() == DefTipos.TIPO_DATO_FECHA && elemento.getEdicion().isEditable()) {

                        EdicionDatoFecha edicionDatoFecha = (EdicionDatoFecha) elemento
                                .getEdicion();

                        Formato nuevoFormato = edicionDatoFecha
                                .getFormato(valorFormulario.getFormato());

                        // Comprobar si ha cambiado el separador
                        if ((valor != null)
                                && (valorFormulario != null)
                                && (valor.getFormato() != null)
                                && (valorFormulario.getFormato() != null)
                                && (valor.getFormato().equals(valorFormulario
                                        .getFormato()))
                                && (valor.getSeparador() != null)
                                && (valorFormulario.getSeparador() != null)
                                && (!valor.getSeparador().equals(
                                        nuevoFormato.getSeparador()))) {
                            cambioFormatoSeparador = true;
                            if ((valorFormulario.getValor() != null)
                                    && (nuevoFormato.getSeparador() != null)) {
                                valorFormulario.setValor(StringUtils.replace(
                                        valorFormulario.getValor(),
                                        valorFormulario.getSeparador(),
                                        nuevoFormato.getSeparador()));
                                valorFormulario.setSeparador(nuevoFormato
                                        .getSeparador());
                            }
                        }
                    }

					if (!valor.equals(valorFormulario)
							|| (cambioFormatoSeparador)) {
                        if ((valor.getValor() == null)
                                && !DateQualifierHelper.CALIFICADOR_SIN_FECHA
                                        .equals(valor.getCalificador())
                                && !isCampoEspecial(elemento))
                            valor.setAccion(TipoAccion.CREAR);
                        else if (StringUtils
                                .isBlank(valorFormulario.getValor())
                                && !DateQualifierHelper.CALIFICADOR_SIN_FECHA
                                        .equals(valor.getCalificador())
                                && !isCampoEspecial(elemento))
                            valor.setAccion(TipoAccion.ELIMINAR);
                        else {
                            valor.setValorAnterior((Valor) ObjectCloner
                                    .deepCopy(valor));
                            valor.setAccion(TipoAccion.MODIFICAR);
                        }

                        if (exportar
                                && TipoAccion.ELIMINAR
                                        .equals(valor.getAccion())
                                && elemento.getEdicion().getTipoPadre() != TiposElemento.TIPO_ELEMENTO_TABLA
                                && elemento.getEdicion().getTipoPadre() != TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL)
                            elemento.removeValor(i);
                        else {
                            if(elemento.getEdicion().isEditable()){
                                if (elemento.getEdicion().getTipo() == DefTipos.TIPO_DATO_FECHA) {
                                    EdicionDatoFecha edicionDatoFecha = (EdicionDatoFecha) elemento
                                            .getEdicion();
                                    Formato nuevoFormato = edicionDatoFecha
                                            .getFormato(valorFormulario
                                                    .getFormato());
                                    if ((valorFormulario.getValor() != null)
                                            && (nuevoFormato.getSeparador() != null)) {
                                        valor.setValor(StringUtils.replace(
                                                valorFormulario.getValor(),
                                                valorFormulario.getSeparador(),
                                                nuevoFormato.getSeparador()));
                                        valor.setSeparador(nuevoFormato
                                                .getSeparador());
                                    }
                                } else {
                                    valor.setValor(valorFormulario.getValor());
                                    valor.setSeparador(valorFormulario
                                            .getSeparador());
                                }

                                valor.setIdRef(valorFormulario.getIdRef());
                                valor.setTipoRef(valorFormulario.getTipoRef());
                                valor.setAnio(valorFormulario.getAnio());
                                valor.setMes(valorFormulario.getMes());
                                valor.setDia(valorFormulario.getDia());
                                valor.setHoras(valorFormulario.getHoras());
                                valor.setMinutos(valorFormulario.getMinutos());
                                valor.setSegundos(valorFormulario.getSegundos());
                                valor.setSiglo(valorFormulario.getSiglo());
                                valor.setFormato(valorFormulario.getFormato());

                                valor.setCalificador(valorFormulario
                                        .getCalificador());
                                valor.setTipoMedida(valorFormulario.getTipoMedida());
                                valor.setUnidadMedida(valorFormulario
                                        .getUnidadMedida());
                           }
                        }
                    }
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Eliminar valor existente #" + i + ":"
                                + Constants.NEWLINE + "Valor original  : "
                                + valor);
                    if(elemento.getEdicion().isEditable()){
                       valor.setAccion(TipoAccion.ELIMINAR);

                       if (exportar) {
                           if (elemento.getEdicion().getTipoPadre() != TiposElemento.TIPO_ELEMENTO_TABLA
                                   && elemento.getEdicion().getTipoPadre() != TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL)
                               elemento.clearValores();
                           else
                               elemento.removeValor(i);
                       }
                    }
                }
            }

            // Obtener el último número de orden
            int lastOrderNumber = (elemento.getTotalValores() > 0 ? elemento
                    .getValor(elemento.getTotalValores() - 1).getOrden() : 0);

            // Añadir los demás valores
            for (int i = elemento.getTotalValores(); i < valoresFormulario.length; i++) {
                if (logger.isDebugEnabled())
                    logger.debug("Valor nuevo #" + i + ": "
                            + valoresFormulario[i]);

                lastOrderNumber++;

                if (exportar) {
                    if ((StringUtils
                            .isNotBlank(valoresFormulario[i].getValor()))
                            || ((StringUtils.isBlank(valoresFormulario[i]
                                    .getValor())) && (elemento.getEdicion()
                                    .getTipoPadre() == TiposElemento.TIPO_ELEMENTO_TABLA || elemento
                                    .getEdicion().getTipoPadre() == TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL))) {

                        Valor valor = (Valor) ObjectCloner
                                .deepCopy(valoresFormulario[i]);
                        valor.setOrden(lastOrderNumber);
                        valor.setAccion(TipoAccion.CREAR);
                        elemento.addValor(valor);
                    }
                } else if (elemento.getEdicion().getTipo() != DefTipos.TIPO_DATO_FECHA
                        || StringUtils.isNotBlank(valoresFormulario[i]
                                .getValor())
                        || (elemento.getEdicion().getTipo() == DefTipos.TIPO_DATO_FECHA
                                && StringUtils.isBlank(valoresFormulario[i]
                                        .getValor()) && DateQualifierHelper.CALIFICADOR_SIN_FECHA
                                .equals(valoresFormulario[i].getCalificador()))) {
                    Valor valor = (Valor) ObjectCloner
                            .deepCopy(valoresFormulario[i]);
                    valor.setOrden(lastOrderNumber);
                    valor.setAccion(TipoAccion.CREAR);

                    if (elemento.getEdicion().getTipo() == DefTipos.TIPO_DATO_FECHA
                            && !DateQualifierHelper.CALIFICADOR_SIN_FECHA
                                    .equals(valoresFormulario[i]
                                            .getCalificador())

                    ) {
                        EdicionDatoFecha edicionDatoFecha = (EdicionDatoFecha) elemento
                                .getEdicion();
                        Formato nuevoFormato = edicionDatoFecha
                                .getFormato(valor.getFormato());
                        valor.setValor(StringUtils.replace(valor.getValor(),
                                valor.getSeparador(),
                                nuevoFormato.getSeparador()));
                        valor.setSeparador(nuevoFormato.getSeparador());
                    }

                    elemento.addValor(valor);
                }
            }
        }
    }

    /**
     * Inicializa los valores de la ficha.
     *
     * @param ficha
     *            Ficha a inicializar.
     */
    public void actualizarValoresIniciales(Ficha ficha) {
        for (int contAreas = 0; contAreas < ficha.getTotalAreas(); contAreas++)
            inicializaContenedorElementos(ficha.getArea(contAreas));
    }

    /**
     * Inicializa los valores de los elementos del contenedor.
     *
     * @param contenedor
     *            Contenedor de elementos.
     */
    protected void inicializaContenedorElementos(ContenedorElementos contenedor) {
        for (int contElementos = 0; contElementos < contenedor
                .getTotalElementos(); contElementos++) {
            Elemento elem = contenedor.getElemento(contElementos);

            switch (elem.getTipo()) {
            case TiposElemento.TIPO_ELEMENTO_AREA:
            case TiposElemento.TIPO_ELEMENTO_CABECERA:
            case TiposElemento.TIPO_ELEMENTO_TABLA:
            case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
                inicializaContenedorElementos((ContenedorElementos) elem);
                break;

            case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
                inicializaElemento((ElementoEtiquetaDato) elem);
                break;
            }

        }
    }

    /**
     * Inicializa el elemento.
     *
     * @param elemento
     *            Elemento de tipo etiqueta-dato.
     */
    protected void inicializaElemento(ElementoEtiquetaDato elemento) {
        // Comprobar que sea editable
        if ((elemento.getEdicion() != null)
                && (elemento.getEdicion().getValorInicial() != null)
                && StringUtils.isNotBlank(elemento.getEdicion()
                        .getValorInicial().getValor())
                && (elemento.getTotalValores() == 0)) {
            Valor valor = new Valor(elemento.getEdicion().getValorInicial());
            valor.setAccion(TipoAccion.CREAR);

            elemento.addValor(valor);
        }
    }

}
