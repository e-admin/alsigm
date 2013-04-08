package deposito.actions.reubicacion;

public class ReubicacionWizardAction {

	public final static String LISTA_CONFIRMACION = "listaconfirmacion";
	public final static String ERROR_NO_ASIGNABLE_SELECCIONADO = "Es necesario seleccionar un elemento de tipo asignable";

	// public ReubicacionWizardAction(){
	// super(5);
	// }
	//
	// /* (non-Javadoc)
	// * @see deposito.actions.WizardAction#prepareNextState(java.lang.String,
	// org.apache.struts.action.ActionForm,
	// javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse)
	// */
	// public void prepareNextState(int nextState, int actualState,ActionForm
	// form, HttpServletRequest req, HttpServletResponse resp) {
	// try{
	// if (nextState==1){
	// prepareState1(req,form);
	//
	// }else if (nextState==2){
	// prepareState2(req,form);
	//
	// }else if(nextState==3){
	// ReubicacionWizardForm frm = ((ReubicacionWizardForm)form);
	// boolean destinoSeleccionadoEsAsignable =
	// TipoElementoOLD.getTipoElemento(frm.getIdtipoasignabledestino()).isAsignable();
	// int numHuecosSeleccionados=frm.getHuecosorigen().size();
	// if(!pulsoAtras(actualState,nextState)){
	// if ((numHuecosSeleccionados>1)||(!destinoSeleccionadoEsAsignable)){
	// //salta a estado 4
	// ((ReubicacionWizardForm)form).setWizardNextState("4");
	// //preparacion de estado 4
	// prepareState4(req,form);
	// }else{
	// prepareState3(req,form);
	// }
	// }else{
	// if ((numHuecosSeleccionados>1)||(!destinoSeleccionadoEsAsignable)){
	// ((ReubicacionWizardForm)form).setWizardNextState("2");
	// prepareState2(req,form);
	// }else{
	// prepareState3(req,form);
	// }
	// }
	// }else if(nextState==4){
	// prepareState4(req,form);
	// }else if(nextState==5){
	// prepareState5(req,form);
	// }else if(nextState==6){
	// prepareState6(req,form);
	// }
	//
	// }catch(Exception e){
	//
	// //TODO GESTION DE ERRORES
	// logger.error(e);
	//
	// }
	// }
	//
	// /**
	// * Preparacion de datos para el estado 1
	// * @param req
	// * @param form
	// * @throws Exception
	// */
	// private void prepareState1(HttpServletRequest req,ActionForm form) throws
	// Exception{
	// //esto es para cuando vuelve hacia atras que los huecos ya estan en
	// request
	// //(para q no los machaque)
	//
	// ReubicacionWizardForm frm = ((ReubicacionWizardForm)form);
	//
	// if (req.getAttribute(Constants.LISTA_HUECOS_ORIGEN_KEY)==null){
	// //carga de huecos
	// //GestorEstructuraDepositoBI service=getGestorEstructuraDeposito();
	// GestorEstructuraDepositoBI service = getGestorEstructuraDepositoBI(req);
	// List listaHuecos=service.getHuecos(frm.getAsignableorigen());
	//
	// //transformar a huecoForm(por el integer de numorden y por el idhueco)
	// List listaHuecosToView=getListHuecoForm(listaHuecos);
	//
	// //aprovechando q tengo los huecos=>cargar las posibles uins en el form
	// para evitar mas tarde llamar a la BD para huecos seleccionado
	// Iterator it=listaHuecosToView.iterator();
	// String idHueco=null,idUins=null;
	// HuecoForm hueco;
	// while(it.hasNext()){
	// hueco=((HuecoForm)it.next());
	// idHueco=hueco.getIdhueco();
	// idUins=hueco.getIduinstalacion();
	// ((ReubicacionWizardForm)form).setPosiblesuinsorigen(idHueco,idUins);
	// }
	//
	// frm.setPathorigen(service.getInfoElemento(frm.getAsignableorigen(),frm.getTipoasignableorigen()).getPathName());
	//
	// req.setAttribute(Constants.LISTA_HUECOS_ORIGEN_KEY,listaHuecosToView);
	// }
	// }
	//
	// /**
	// * Preparacion de datos para el estado 2
	// * @param req
	// * @param form
	// * @throws Exception
	// */
	// private void prepareState2(HttpServletRequest req,ActionForm form)throws
	// Exception{
	// /*
	// GestorEstructuraDepositoBI service=getGestorEstructuraDeposito();
	// List listaAsignables;
	//
	// listaAsignables = service.getElementosAsignables();
	// req.setAttribute(Constants.LISTA_ASIGNABLES_KEY,listaAsignables);
	// */
	// //este paso actualemnte no se usa solo redirecciona al navegador
	//
	// }
	//
	// /** Preparacion de datos para el estado 3
	// * @param req
	// * @param form
	// * @throws Exception
	// */
	// private void prepareState3(HttpServletRequest req,ActionForm form)throws
	// Exception{
	//
	// //esto es para cuando vuelve hacia atras que los huecos ya estan en
	// request
	// //(para q no los machaque)
	// if (req.getAttribute(Constants.LISTA_HUECOS_DESTINO_KEY)==null){
	// //comporbar que se selecciono un elemento asignable!!
	// ReubicacionWizardForm frm = ((ReubicacionWizardForm)form);
	//
	// //carga de huecos
	// //GestorEstructuraDepositoBI service=getGestorEstructuraDeposito();
	// GestorEstructuraDepositoBI service = getGestorEstructuraDepositoBI(req);
	// List listaHuecos=service.getHuecos(frm.getIdasignabledestino());
	//
	// //transformar a huecoForm(por el integer de numorden y por el idhueco)
	// List listaHuecosToView=getListHuecoForm(listaHuecos);
	//
	// //path de destino
	// frm.setPathdestino(service.getInfoElemento(frm.getIdasignabledestino(),
	// frm.getIdtipoasignabledestino()).getPathName());
	//
	// req.setAttribute(Constants.LISTA_HUECOS_DESTINO_KEY,listaHuecosToView);
	// }
	//
	// }
	//
	// /**
	// *
	// * @param req
	// * @param form
	// * @throws Exception
	// */
	// private void prepareState4(HttpServletRequest req,ActionForm form) throws
	// Exception {
	// ReubicacionWizardForm frm=((ReubicacionWizardForm)form);
	// //GestorEstructuraDepositoBI service=getGestorEstructuraDeposito();
	// GestorEstructuraDepositoBI service = getGestorEstructuraDepositoBI(req);
	// List listDetalle=new LinkedList();
	// int
	// numHuecosSeleccionados=((ReubicacionWizardForm)form).getHuecosorigen().size();
	// boolean soloUnHuecoDeOrigenSeleccionado=(numHuecosSeleccionados==1);
	// boolean destinoSeleccionadoEsAsignable =
	// TipoElementoOLD.getTipoElemento(frm.getIdtipoasignabledestino()).isAsignable();
	// try {
	// if ((!soloUnHuecoDeOrigenSeleccionado) ||
	// !destinoSeleccionadoEsAsignable)
	// listDetalle= getListDetalleAsignableDest(frm, service);
	//
	// else
	// listDetalle=getListDetalleOneHuecoDest( frm, service);
	//
	// req.getSession().setAttribute(LISTA_CONFIRMACION,listDetalle);
	//
	// } catch (NoAvailableSpaceException naee) {
	// ActionErrors errors = new ActionErrors();
	// errors.add(Constants.ERROR_NOT_AVAILABLE_SPACE,new
	// ActionError(Constants.ERROR_NOT_AVAILABLE_SPACE));
	// saveErrors(req,errors);
	// }
	// }
	//
	// /** Generacion de listado de reubicacion para la vista en el caso de la
	// reubicacion sea a un hueco
	// * @param listDetalle
	// * @param frm
	// * @param service
	// * @throws Exception
	// */
	// private List getListDetalleOneHuecoDest(ReubicacionWizardForm frm,
	// GestorEstructuraDepositoBI service) throws Exception {
	// List listDetalle=new LinkedList();
	// //la lista solo tiene un detalle(origen y destino)
	// DetalleInformeReubicacionForm detalle =
	// new DetalleInformeReubicacionForm();
	//
	// Iterator itIdsHuecosOrigen=frm.getHuecosorigen().iterator();
	// Iterator itIdsHuecosDestino=frm.getHuecosdestino().iterator();
	// //Iterator itUinsorigen=frm.getUinsdsorigen().iterator();
	// //Iterator itUinsdestino=frm.getUinsdsorigen().iterator();
	// String idPadreHuecoOrigen,numordenHuecoOrigen;
	// String idPadreHuecoDestino,numordenHuecoDestino;
	//
	// String idHuecoOrigen=(String)itIdsHuecosOrigen.next();
	// String idsHuecoOrigen[]=getIdsHuecoForm(idHuecoOrigen);
	// idPadreHuecoOrigen=idsHuecoOrigen[0];
	// numordenHuecoOrigen=idsHuecoOrigen[1];
	//
	// detalle.setPathorigen(service.getInfoHueco(
	// new HuecoID(idPadreHuecoOrigen,new
	// Integer(numordenHuecoOrigen).intValue())).getPath());
	//
	// //detalle.setPathorigen(service.getPathHueco(new
	// HuecoID(idPadreHuecoOrigen,new
	// Integer(numordenHuecoOrigen).intValue())));
	//
	// String idHuecoDestino=(String)itIdsHuecosDestino.next();
	// String idsHuecoDestino[]=getIdsHuecoForm(idHuecoDestino);
	// idPadreHuecoDestino=idsHuecoDestino[0];
	// numordenHuecoDestino=idsHuecoDestino[1];
	//
	// //detalle.setPathdestino(service.getPathHueco(new
	// HuecoID(idPadreHuecoDestino,new
	// Integer(numordenHuecoDestino).intValue())));
	// detalle.setPathdestino(service.getInfoHueco(
	// new HuecoID(idPadreHuecoDestino,new
	// Integer(numordenHuecoDestino).intValue())).getPath());
	//
	// UInsDepositoVO uInsEnDeposito = null;
	// if (frm.getPosibleuinsorigen(idHuecoOrigen)!=null){
	// uInsEnDeposito =
	// service.getUinsEnDeposito(frm.getPosibleuinsorigen(idHuecoOrigen));
	// detalle.setSignaturauinsorigen(uInsEnDeposito.getSignaturaui());
	// }
	//
	// detalle.setUinsorigen(frm.getPosibleuinsorigen(idHuecoOrigen));
	//
	// detalle.setIdHuecoOrigen(new HuecoID(idPadreHuecoOrigen,new
	// Integer(numordenHuecoOrigen).intValue()));
	// detalle.setIdHuecoDestino(new HuecoID(idPadreHuecoDestino,new
	// Integer(numordenHuecoDestino).intValue()));
	//
	// listDetalle.add(detalle);
	//
	// return listDetalle;
	// }
	//
	// /** Generacion de listado de reubicacion para la vista en el caso de la
	// reubicacion sea a un Asignable
	// * @param frm
	// * @param service
	// * @param listDetalle
	// * @throws Exception
	// */
	// private List getListDetalleAsignableDest(ReubicacionWizardForm frm,
	// GestorEstructuraDepositoBI service) throws Exception {
	// List listDetalle=new LinkedList();
	// List listaToService=new ArrayList(frm.getHuecosorigen());
	// List listaFromService;
	//
	// //Nos puede llegar una asignable , no asignable o deposito
	// //if
	// (TipoElemento.getTipoElemento(frm.getIdtipoasignabledestino()).isAsignable()){
	// //
	// listaFromService=service.getHuecosLibresAsignable(frm.getIdasignabledestino()
	// // , listaToService.size());
	// //}else{
	//
	// String idFormatoOrigen =
	// ((ElementoAsignableVO)service.getInfoElemento(frm.getAsignableorigen(),
	// frm.getTipoasignableorigen()))
	// .getIdFormato();
	//
	// listaFromService=service.searchNHuecosLibres(frm.getIdasignabledestino(),
	// frm.getIdtipoasignabledestino(), listaToService.size(),
	// frm.getAsignableorigen(), frm.getTipoasignableorigen(),idFormatoOrigen);
	// //}
	//
	// Iterator itDestino=listaFromService.iterator();
	// Iterator itOrigen=listaToService.iterator();
	// //Iterator itUinsorigen=frm.getUinsdsorigen().iterator();
	// String idHuecoOrigen;
	// HuecoVO huecoDestino;
	// UInsDepositoVO uInsEnDeposito = null;
	// String idPadreHuecoOrigen,numordenHuecoOrigen,iduinsorigen,iduinsdestino;
	// //la longitud de ambos iteradores es la misma
	// while(itOrigen.hasNext()){
	// DetalleInformeReubicacionForm detalle=new
	// DetalleInformeReubicacionForm();
	//
	// idHuecoOrigen=((String)itOrigen.next());
	// String idsHuecoOrigen[]=getIdsHuecoForm(idHuecoOrigen);
	// idPadreHuecoOrigen=idsHuecoOrigen[0];
	// numordenHuecoOrigen=idsHuecoOrigen[1];
	//
	// detalle.setPathorigen(service.getInfoHueco(
	// new HuecoID(idPadreHuecoOrigen,new
	// Integer(numordenHuecoOrigen).intValue())).getPath());
	//
	// huecoDestino=((HuecoVO)itDestino.next());
	// detalle.setPathdestino(huecoDestino.getPath());
	//
	// String id = frm.getPosibleuinsorigen(idHuecoOrigen);
	// if (frm.getPosibleuinsorigen(idHuecoOrigen)!=null){
	// uInsEnDeposito =
	// service.getUinsEnDeposito(frm.getPosibleuinsorigen(idHuecoOrigen));
	// detalle.setSignaturauinsorigen(uInsEnDeposito.getSignaturaui());
	// }
	//
	// detalle.setUinsorigen(frm.getPosibleuinsorigen(idHuecoOrigen));
	//
	// detalle.setIdHuecoOrigen(new HuecoID(idPadreHuecoOrigen,new
	// Integer(numordenHuecoOrigen).intValue()));
	// detalle.setIdHuecoDestino(new
	// HuecoID(huecoDestino.getIdElemAPadre(),huecoDestino.getNumorden().intValue()));
	//
	// listDetalle.add(detalle);
	// }
	//
	// return listDetalle;
	// }
	//
	// /**
	// *
	// * @param idHuecoForm
	// * @return
	// */
	// private String[] getIdsHuecoForm(String idHuecoForm){
	// return idHuecoForm.split(Constants.DELIMITER_IDS);
	// }
	//
	// /**
	// *
	// * @param huecosVO
	// * @return
	// */
	// private List getListHuecoForm(List huecosVO){
	// ArrayList huecosToView=new ArrayList();
	// Iterator it=huecosVO.iterator();
	// HuecoVO huecoFromService;
	// while(it.hasNext()){
	// huecoFromService=(HuecoVO)it.next();
	// HuecoForm huecoToView=new HuecoForm();
	// String
	// idHueco=huecoFromService.getIdElemAPadre()+":"+huecoFromService.getNumorden().toString();
	// huecoToView.setNumorden(huecoFromService.getNumorden().toString());
	// huecoToView.setEstado(huecoFromService.getEstado());
	// huecoToView.setIdpadre(huecoFromService.getIdElemAPadre());
	// huecoToView.setIdhueco(idHueco);
	// huecoToView.setIduinstalacion(huecoFromService.getIduinstalacion());
	// huecosToView.add(huecoToView);
	// }
	//
	// return huecosToView;
	//
	// }
	//
	// /** Preparacion del estado 5: Grabacion de datos
	// * @param req
	// * @param form
	// */
	// private void prepareState5(HttpServletRequest req, ActionForm form)
	// throws Exception{
	// //GestorEstructuraDepositoBI service=getGestorEstructuraDeposito();
	// GestorEstructuraDepositoBI service = getGestorEstructuraDepositoBI(req);
	// List listDetalle=(List)
	// req.getSession().getAttribute(LISTA_CONFIRMACION);
	// Iterator it=listDetalle.iterator();
	// DetalleInformeReubicacionForm detalle;
	// List listaorigen=new ArrayList(),listadestino=new ArrayList();
	// while(it.hasNext()){
	// detalle=(DetalleInformeReubicacionForm)it.next();
	// listaorigen.add(detalle.getIdHuecoOrigen());
	// listadestino.add(detalle.getIdHuecoDestino());
	// }
	//
	// service.reubicarUnidadesInstalacion(listaorigen,listadestino);
	//
	// }
	//
	// /**
	// * eliminacion de recursos
	// * @param req
	// * @param form
	// */
	//
	// private void prepareState6(HttpServletRequest req, ActionForm form) {
	// req.getSession().removeAttribute(LISTA_CONFIRMACION);
	// }
	//
	//
	//
	//
	// /* (non-Javadoc)
	// * @see
	// es.archigest.framework.web.action.ArchigestAction#findSuccess(org.apache.struts.action.ActionMapping,
	// org.apache.struts.action.ActionForm,
	// javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse)
	// */
	// protected ActionForward findSuccess(ActionMapping mapping, ActionForm
	// form,
	// HttpServletRequest req, HttpServletResponse resp) {
	//
	// // ActionForward ret= super.findSuccess(mapping, form, req, resp);
	// // ReubicacionWizardForm frm=((ReubicacionWizardForm)form);
	// //
	// // //hacer vuelva a la pagina de inicio
	// // if ((ret == null) || (ret.getName().equalsIgnoreCase(FORWARD_END))
	// // || (frm.isCancel())) {
	// //
	// // ret=new ActionForward();
	// //
	// // String idAsignable=frm.getAsignableorigen();
	// // String idTipoCreacionMultiple=frm.getTipoasignableorigen();
	// // String path="/action/gestionTipoAsignableAction?"+
	// // GestionTipoAsignableAction.CALL_TYPE_KEY+"="+
	// // GestionTipoAsignableAction.LOAD_CALL+"&"+
	// // GestionForm.ID_TO_GEST_KEY+"="+idAsignable+"&"+
	// // GestionForm.MODE_KEY+"="+GestionForm.MODE_VIEW_KEY+
	// // "&idtipoelementocomun="+idTipoCreacionMultiple;
	// // ret.setPath(path);
	// // ret.setRedirect(true);
	// // }
	// //
	// // return ret;
	//
	// return null;
	// }
}
