package fondos.actions;

import fondos.FondosConstants;
import fondos.forms.NivelesCuadroForm;
import fondos.model.JerarquiaNivelCF;
import fondos.model.NivelCF;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoNivelCF;
import fondos.vos.INivelCFVO;
import fondos.vos.JerarquiaNivelCFVO;
import gcontrol.ControlAccesoConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.vos.FichaVO;
import docelectronicos.vos.IRepositorioEcmVO;

public class GestionNivelesCuadroAction extends BaseAction {

    public void initExecuteLogic(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        ClientInvocation invocation = saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES, request);
        invocation.setAsReturnPoint(true);

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();
        List listaNivelesCuadro = cuadroBI.getAllNivelesCF();
        if (ListUtils.isEmpty(listaNivelesCuadro))
            listaNivelesCuadro = new ArrayList();

        setInTemporalSession(request,
                ControlAccesoConstants.LISTA_NIVELES_CUADRO, listaNivelesCuadro);
        setInTemporalSession(request, FondosConstants.LISTA_SUBTIPOS_NIVEL,
                SubtipoNivelCF.getListaSubtipos());

        setReturnActionFordward(request, mappings.findForward("init"));
    }

    public void nuevoNivelExecuteLogic(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES_ALTA,
                request);

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        int tipoNivel = nivelCuadroForm.getTipoNivel();

        // Para indicar si puede ser o no raiz (al producirse algun error y
        // recargar la pagina)
        if (tipoNivel > 0)
            cargarDatos(request, tipoNivel);

        cargarListas(request, tipoNivel, false);

        setInTemporalSession(request,
                FondosConstants.TIPO_NIVEL_CUADRO_EDITABLE, new Boolean(true));

        setReturnActionFordward(request,
                mappings.findForward("alta_nivel_cuadro"));
    }

    public void addNivelCuadroExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;

        ActionErrors errors = validateForm(nivelCuadroForm, request);
        if (errors.size() > 0) {
            ErrorsTag.saveErrors(request, errors);
            setReturnActionFordward(request,
                    mappings.findForward("alta_nivel_cuadro"));
        } else {
            JerarquiaNivelCF jerarquiaNivel = null;
            NivelCF nivelCFVO = new NivelCF();
            nivelCuadroForm.populate(nivelCFVO);

            if (StringUtils.isEmpty(nivelCFVO.getId())) {
                // Si se creo como nivel inicial hay que insertar el valor en la
                // estructura jerarquica
                if (nivelCuadroForm.isNivelInicial()) {
                    jerarquiaNivel = new JerarquiaNivelCF();
                    jerarquiaNivel.setIdNivelPadre(TipoNivelCF.ID_NIVEL_RAIZ);
                    jerarquiaNivel
                            .setTipoNivelPadre(TipoNivelCF.TIPO_NIVEL_RAIZ);
                } else if (StringUtils.isNotEmpty(nivelCuadroForm
                        .getIdNivelPadre())) {
                    jerarquiaNivel = new JerarquiaNivelCF();
                    jerarquiaNivel.setIdNivelPadre(nivelCuadroForm
                            .getIdNivelPadre());
                    jerarquiaNivel.setTipoNivelPadre(nivelCuadroForm
                            .getTipoNivelPadre());
                }
                cuadroBI.insertarNivelJerarquiaCF(nivelCFVO, jerarquiaNivel);

                if (nivelCuadroForm.isNivelInicial())
                    setReturnActionFordward(request,
                            mappings.findForward("list"));
                else
                    goBackExecuteLogic(mappings, form, request, response);
            } else {
                boolean cambioInitialYesCheck = false;
                if ((nivelCuadroForm.isCheckInicial() && nivelCuadroForm
                        .isNivelInicial())
                        || !nivelCuadroForm.isCheckInicial()
                        && !nivelCuadroForm.isNivelInicial())
                    cuadroBI.actualizarNivelJerarquiaCF(nivelCFVO, null,
                            cambioInitialYesCheck);
                else {
                    if (nivelCuadroForm.isCheckInicial()
                            && !nivelCuadroForm.isNivelInicial())
                        cambioInitialYesCheck = true;

                    jerarquiaNivel = new JerarquiaNivelCF();
                    jerarquiaNivel.setIdNivelPadre(TipoNivelCF.ID_NIVEL_RAIZ);
                    jerarquiaNivel
                            .setTipoNivelPadre(TipoNivelCF.TIPO_NIVEL_RAIZ);
                    jerarquiaNivel.setIdNivelHijo(nivelCFVO.getId());
                    jerarquiaNivel.setTipoNivelHijo(nivelCFVO.getTipo());
                    cuadroBI.actualizarNivelJerarquiaCF(nivelCFVO,
                            jerarquiaNivel, cambioInitialYesCheck);
                }
                goBackExecuteLogic(mappings, form, request, response);
            }
        }
    }

    public void cargarDatosExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        ServiceRepository service = ServiceRepository.getInstance(ServiceClient
                .create(getAppUser(request)));
        GestionDescripcionBI descripcionService = service
                .lookupGestionDescripcionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        int tipoNivel = nivelCuadroForm.getTipoNivel();

        // Se cargan las fichas correspondientes al tipo de nivel seleccionado
        if (tipoNivel > 0) {
            TipoNivelCF tipoNivelCF = TipoNivelCF.getTipoNivel(tipoNivel);
            if (StringUtils.isNotEmpty(nivelCuadroForm.getIdNivelPadre())) {
                removeInTemporalSession(request,
                        FondosConstants.MOSTRAR_SUBTIPOS);

                // Si el tipo es unidad documental mostraremos los subtipos
                if (tipoNivelCF.getIdentificador() == TipoNivelCF.UNIDAD_DOCUMENTAL
                        .getIdentificador())
                    setInTemporalSession(request,
                            FondosConstants.MOSTRAR_SUBTIPOS, new Boolean(true));
            } else
                cargarDatos(request, tipoNivel);

            List fichas = descripcionService
                    .getFichasByTiposNivel(new int[] { tipoNivel });
            setInTemporalSession(request, ControlAccesoConstants.LISTA_FICHAS,
                    fichas);
        } else {
            setInTemporalSession(request, ControlAccesoConstants.LISTA_FICHAS,
                    new ArrayList());

            removeInTemporalSession(request,
                    FondosConstants.PUEDE_SER_NIVEL_INICIAL);
            removeInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS);
        }

        setReturnActionFordward(request,
                mappings.findForward("alta_nivel_cuadro"));
    }

    public void verNivelCuadroExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES_DATOS,
                request);

        removeInTemporalSession(request,
                FondosConstants.PUEDE_SER_NIVEL_INICIAL);
        removeInTemporalSession(request, FondosConstants.LISTA_NIVELES_HIJO);
        removeInTemporalSession(request, FondosConstants.NIVEL_CF_KEY);

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        String idNivel = request.getParameter("idNivel");
        if (StringUtils.isNotEmpty(idNivel)) {
            INivelCFVO nivelCF = cuadroBI.getNivelCFById(idNivel);

            // Comprobar si es nivel inicial o no para chequearlo
            if (cuadroBI.isNivelInicial(nivelCF.getId()))
                setInTemporalSession(request,
                        FondosConstants.PUEDE_SER_NIVEL_INICIAL, new Boolean(
                                true));

            // Fichas de descripción
            ServiceRepository service = ServiceRepository
                    .getInstance(ServiceClient.create(getAppUser(request)));
            GestionDescripcionBI descripcionService = service
                    .lookupGestionDescripcionBI();

            FichaVO fichaDescPref = descripcionService.getFicha(nivelCF
                    .getIdFichaDescrPref());
            request.setAttribute(FondosConstants.FICHA_DESCRIPCION_NIVEL,
                    fichaDescPref);

            // Repositorio ECM
            IRepositorioEcmVO repositorioEcmPref = getGestionDocumentosElectronicosBI(
                    request).getRepositorioEcm(nivelCF.getIdRepEcmPref());
            request.setAttribute(FondosConstants.REPOSITORIO_ECM_NIVEL,
                    repositorioEcmPref);

            // Obtener los niveles hijos
            List listaNivelesHijo = cuadroBI.getNivelesCF(idNivel);
            setInTemporalSession(request, FondosConstants.LISTA_NIVELES_HIJO,
                    listaNivelesHijo);

            // Comprobar si puede tener hijos
            if (nivelCF.getTipoNivel().getTiposNivelesHijos().length > 0)
                request.setAttribute(FondosConstants.PUEDE_TENER_HIJOS,
                        new Boolean(true));

            // Comprobar si tiene hijos o alguna referencia en ASGFELEMENTOCF
            // O si aparece alguna referencia en la tabla ASGFESTRUCTJNIVCF en
            // el campo IDNIVELHIJO
            // En ese caso no dejar editar el tipo, ni el subtipo.
            int elementos = cuadroBI.getElementosCFByNivel(idNivel);

            boolean tipoEditable = true;
            if (listaNivelesHijo.size() > 0 || elementos > 0
                    || !cuadroBI.isNivelHijoNoRaiz(idNivel))
                tipoEditable = false;
            request.setAttribute(FondosConstants.TIPO_NIVEL_CUADRO_EDITABLE,
                    new Boolean(tipoEditable));

            int[] tiposHijo = nivelCF.getTipoNivel().getTiposNivelesHijos();
            List posiblesHijos = new ArrayList();
            if (tiposHijo != null && tiposHijo.length > 0)
                posiblesHijos = cuadroBI.getNivelesCFByTipo(tiposHijo,
                        listaNivelesHijo);
            request.setAttribute(FondosConstants.LISTA_POSIBLES_NIVELES_HIJO,
                    posiblesHijos);

            cargarListas(request, nivelCF.getTipo(), true);

            setInTemporalSession(request, FondosConstants.NIVEL_CF_KEY, nivelCF);
        }

        setReturnActionFordward(request,
                mappings.findForward("datos_nivel_cuadro"));
    }

    /**
     * Carga la página para la edición del nivel del cuadro de clasificacion
     * seleccionado.
     *
     * @param mappings
     * @param form
     * @param request
     * @param response
     */
    public void editarNivelCuadroExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES_EDICION,
                request);

        removeInTemporalSession(request,
                FondosConstants.TIPO_NIVEL_CUADRO_EDITABLE);

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        String idNivel = nivelCuadroForm.getIdNivel();
        INivelCFVO nivelCF = null;
        if (StringUtils.isNotEmpty(idNivel)) {
            nivelCF = cuadroBI.getNivelCFById(idNivel);
            nivelCuadroForm.set(nivelCF);

            // Comprobar si es nivel inicial o no para chequearlo
            if (cuadroBI.isNivelInicial(idNivel)) {
                nivelCuadroForm.setNivelInicial(true);
                nivelCuadroForm.setCheckInicial(true);
            } else
                nivelCuadroForm.setCheckInicial(false);

            // Comprobar si tiene hijos o alguna referencia en ASGFELEMENTOCF
            // O si aparece alguna referencia en la tabla ASGFESTRUCTJNIVCF en
            // el campo IDNIVELHIJO
            // En ese caso no dejar editar el tipo, ni el subtipo.
            List listaNivelesHijo = cuadroBI.getNivelesCF(idNivel);
            int elementos = cuadroBI.getElementosCFByNivel(nivelCuadroForm
                    .getIdNivel());
            boolean tipoEditable = true;
            if ((listaNivelesHijo != null && listaNivelesHijo.size() > 0)
                    || elementos > 0 || !cuadroBI.isNivelHijoNoRaiz(idNivel))
                tipoEditable = false;

            setInTemporalSession(request,
                    FondosConstants.TIPO_NIVEL_CUADRO_EDITABLE, new Boolean(
                            tipoEditable));

            if (nivelCF.getTipo() > 0)
                cargarDatos(request, nivelCF.getTipo());

            cargarListas(request, nivelCF.getTipo(), true);
        }
        setReturnActionFordward(request,
                mappings.findForward("alta_nivel_cuadro"));
    }

    public void agregarNivelHijoExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES_AGREGAR_NIVEL,
                request);

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        List descendientes = (List) getFromTemporalSession(request,
                FondosConstants.LISTA_NIVELES_HIJO);

        String idNivel = request.getParameter("idNivel");
        if (StringUtils.isNotEmpty(idNivel)) {
            INivelCFVO nivel = cuadroBI.getNivelCFById(idNivel);
            if (nivel != null) {
                TipoNivelCF tipoNivelCF = nivel.getTipoNivel();
                List posiblesHijos = new ArrayList();
                int[] tiposHijo = tipoNivelCF.getTiposNivelesHijos();
                if (tiposHijo != null && tiposHijo.length > 0)
                    posiblesHijos = cuadroBI.getNivelesCFByTipo(tiposHijo,
                            descendientes);
                request.setAttribute(
                        FondosConstants.LISTA_POSIBLES_NIVELES_HIJO,
                        posiblesHijos);
            }
            request.setAttribute(FondosConstants.NIVEL_CF_KEY, nivel);
        }

        setReturnActionFordward(request,
                mappings.findForward("agregar_nivel_hijo"));
    }

    public void eliminarNivelCuadroExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        String idNivel = request.getParameter("idNivel");
        if (StringUtils.isNotEmpty(idNivel)) {
            INivelCFVO nivel = cuadroBI.getNivelCFById(idNivel);
            if (nivel != null) {

                ActionErrors errors = new ActionErrors();

                // Obtengo los hijos de dicho nivel
                List listaNivelesHijo = cuadroBI.getNivelesCF(nivel.getId());

                // Comprobar que no sea hijo de otro nivel (excepto del
                // NIVEL_RAIZ)
                boolean isHijoNoRaiz = cuadroBI.isNivelHijoNoRaiz(idNivel);

                // Comprobar si tiene alguna referencia en ASGFELEMENTOCF
                int elementos = cuadroBI.getElementosCFByNivel(nivel.getId());

                if (listaNivelesHijo != null && listaNivelesHijo.size() > 0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                            Constants.ERROR_ELIMINAR_NIVEL_CON_HIJOS));
                else if (!isHijoNoRaiz)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                            Constants.ERROR_ELIMINAR_NIVEL_HIJO));
                else if (elementos > 0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                            Constants.ERROR_ELIMINAR_NIVEL_REFERENCIADO));

                if (!errors.isEmpty()) {
                    ErrorsTag.saveErrors(request, errors);
                    goLastClientExecuteLogic(mappings, form, request, response);
                } else {
                    JerarquiaNivelCFVO jerarquiaNivelCF = new JerarquiaNivelCF();
                    jerarquiaNivelCF.setIdNivelHijo(nivel.getId());
                    // Solamente se podra eliminar si es hijo de un nivel RAIZ o
                    // no tiene padres
                    if (cuadroBI.isNivelInicial(idNivel))
                        jerarquiaNivelCF
                                .setIdNivelPadre(TipoNivelCF.ID_NIVEL_RAIZ);

                    cuadroBI.eliminarNivelJerarquiaCF(nivel, jerarquiaNivelCF);

                    setReturnActionFordward(request,
                            mappings.findForward("list"));
                }
            }
        }
    }

    public void eliminarNivelHijoExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        String[] nivelesSeleccionados = nivelCuadroForm.getDescendientes();
        if (nivelesSeleccionados != null && nivelesSeleccionados.length > 0) {
            String idNivel = nivelCuadroForm.getIdNivel();
            if (StringUtils.isNotEmpty(idNivel)) {
                INivelCFVO nivel = cuadroBI.getNivelCFById(idNivel);
                if (nivel != null) {
                    JerarquiaNivelCFVO jerarquiaNivelCF = null;
                    for (int i = 0; i < nivelesSeleccionados.length; i++) {
                        jerarquiaNivelCF = new JerarquiaNivelCF();
                        jerarquiaNivelCF.setIdNivelPadre(nivel.getId());
                        jerarquiaNivelCF
                                .setIdNivelHijo(nivelesSeleccionados[i]);

                        cuadroBI.deleteJerarquiaNivelCF(jerarquiaNivelCF);
                    }
                    int tipoNivel = nivel.getTipoNivel().getIdentificador();
                    if (tipoNivel > 0)
                        cargarDatos(request, tipoNivel);
                    cargarListas(request, tipoNivel, false);
                }

                List listaNivelesHijo = cuadroBI.getNivelesCF(nivel.getId());
                setInTemporalSession(request,
                        FondosConstants.LISTA_NIVELES_HIJO, listaNivelesHijo);
            }
        } else {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                    Constants.ERROR_NIVEL_HIJO_ELIMINAR_REQUERIDO));
            ErrorsTag.saveErrors(request, errors);
        }
        goLastClientExecuteLogic(mappings, form, request, response);
    }

    public void addNivelDescendienteExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        String[] nivelesSeleccionados = nivelCuadroForm.getDescendientes();
        if (nivelesSeleccionados != null && nivelesSeleccionados.length > 0) {
            JerarquiaNivelCFVO jerarquiaNivelCF = null;
            for (int i = 0; i < nivelesSeleccionados.length; i++) {
                INivelCFVO nivelCF = cuadroBI
                        .getNivelCFById(nivelesSeleccionados[i]);

                jerarquiaNivelCF = new JerarquiaNivelCF();
                jerarquiaNivelCF.setIdNivelPadre(nivelCuadroForm.getIdNivel());
                jerarquiaNivelCF.setTipoNivelPadre(nivelCuadroForm
                        .getTipoNivel());
                jerarquiaNivelCF.setIdNivelHijo(nivelCF.getId());
                jerarquiaNivelCF.setTipoNivelHijo(nivelCF.getTipo());

                cuadroBI.insertarNivelJerarquiaCF(null, jerarquiaNivelCF);
            }

            List listaNivelesHijo = cuadroBI.getNivelesCF(nivelCuadroForm
                    .getIdNivel());
            setInTemporalSession(request, FondosConstants.LISTA_NIVELES_HIJO,
                    listaNivelesHijo);

            goBackExecuteLogic(mappings, form, request, response);
        } else {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
                    Constants.ERROR_NIVEL_CUADRO_REQUERIDO));
            ErrorsTag.saveErrors(request, errors);
            goLastClientExecuteLogic(mappings, form, request, response);
        }
    }

    public void crearNuevoNivelHijoExecuteLogic(ActionMapping mappings,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        saveCurrentInvocation(
                KeysClientsInvocations.CUADRO_CLASIFICACION_NIVELES_NUEVO_HIJO,
                request);

        removeInTemporalSession(request, FondosConstants.LISTA_TIPOS_NIVEL_KEY);
        removeInTemporalSession(request,
                FondosConstants.PUEDE_SER_NIVEL_INICIAL);
        removeInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS);

        ServiceRepository services = ServiceRepository
                .getInstance(ServiceClient.create(getAppUser(request)));
        GestionCuadroClasificacionBI cuadroBI = services
                .lookupGestionCuadroClasificacionBI();

        NivelesCuadroForm nivelCuadroForm = (NivelesCuadroForm) form;
        String idNivel = nivelCuadroForm.getIdNivel();
        if (StringUtils.isNotEmpty(idNivel)) {
            INivelCFVO nivel = cuadroBI.getNivelCF(idNivel);
            if (nivel != null) {
                int tipoNivel = nivelCuadroForm.getTipoNivel();
                nivelCuadroForm.reset();
                nivelCuadroForm.setIdNivelPadre(nivel.getId());
                nivelCuadroForm.setTipoNivelPadre(tipoNivel);

                // En este caso nunca podra ser nivel raiz
                if (tipoNivel > 0) {
                    if (nivel.getTipoNivel().getIdentificador() == TipoNivelCF.UNIDAD_DOCUMENTAL
                            .getIdentificador())
                        setInTemporalSession(request,
                                FondosConstants.MOSTRAR_SUBTIPOS, new Boolean(
                                        true));
                }
                cargarListas(request, tipoNivel, false);

                // Tipos de NivelCF permitidos
                nivelCuadroForm.setTipoNivel(0);
                List listaTiposNivelPermitidos = nivel.getTipoNivel()
                        .getTiposPermitidos();
                setInTemporalSession(request,
                        FondosConstants.LISTA_TIPOS_NIVEL_KEY,
                        listaTiposNivelPermitidos);

                setInTemporalSession(request,
                        FondosConstants.TIPO_NIVEL_CUADRO_EDITABLE,
                        new Boolean(true));
            }
        }
        setReturnActionFordward(request,
                mappings.findForward("alta_nivel_cuadro"));
    }

    private void cargarDatos(HttpServletRequest request, int tipoNivel) {

        removeInTemporalSession(request,
                FondosConstants.PUEDE_SER_NIVEL_INICIAL);
        removeInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS);

        // Para mostrar el check de nivel inicial si el tipo es clasificador de
        // fondos o fondo
        TipoNivelCF tipoNivelCF = TipoNivelCF.getTipoNivel(tipoNivel);
        setInTemporalSession(request, FondosConstants.PUEDE_SER_NIVEL_INICIAL,
                tipoNivelCF.getPuedeSerRaiz());

        // Si el tipo es unidad documental mostraremos los subtipos
        if (tipoNivelCF.getIdentificador() == TipoNivelCF.UNIDAD_DOCUMENTAL
                .getIdentificador())
            setInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS,
                    new Boolean(true));
    }

    private void cargarListas(HttpServletRequest request, int tipoNivel,
            boolean editando) {

        // Tipos de NivelCF
        List listaTiposNivel = TipoNivelCF.getListaNiveles();
        setInTemporalSession(request, FondosConstants.LISTA_TIPOS_NIVEL_KEY,
                listaTiposNivel);

        cargarListaRepositoriosECM(request);

        // Fichas de descripción
        if (editando) {
            ServiceRepository service = ServiceRepository
                    .getInstance(ServiceClient.create(getAppUser(request)));
            GestionDescripcionBI descripcionService = service
                    .lookupGestionDescripcionBI();
            List fichas = descripcionService
                    .getFichasByTiposNivel(new int[] { tipoNivel });
            setInTemporalSession(request, ControlAccesoConstants.LISTA_FICHAS,
                    fichas);
        } else{
            setInTemporalSession(request, ControlAccesoConstants.LISTA_FICHAS,
                    new ArrayList());
        }



    }

    /**
     * Comprueba que los datos introducidos en el formulario sean correctos. -
     * Nombre Obligatorio - TipoNivel Obligatorio
     *
     * @return
     */
    public ActionErrors validateForm(NivelesCuadroForm nivelCuadroForm,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        // Comprobar que se ha introducido el nombre
        if (StringUtils.isEmpty(nivelCuadroForm.getNombre())) {
            errors.add(
                    ActionErrors.GLOBAL_MESSAGE,
                    new ActionError(Constants.ERROR_REQUIRED, Messages
                            .getString(Constants.ETIQUETA_NOMBRE,
                                    request.getLocale())));
        }

        // Comprobar que se ha seleccionado el tipo de nivel.
        if (nivelCuadroForm.getTipoNivel() == 0) {
            errors.add(
                    ActionErrors.GLOBAL_MESSAGE,
                    new ActionError(Constants.ERROR_REQUIRED, Messages
                            .getString(Constants.ETIQUETA_TIPO_NIVEL,
                                    request.getLocale())));
        }

        return errors;
    }
}
