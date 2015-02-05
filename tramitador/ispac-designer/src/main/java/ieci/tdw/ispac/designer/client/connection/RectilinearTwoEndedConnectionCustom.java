package ieci.tdw.ispac.designer.client.connection;

import ieci.tdw.ispac.designer.client.Designer;
import ieci.tdw.ispac.designer.client.diagrama.AbstractConnectionsExample;
import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;
import ieci.tdw.ispac.designer.client.diagrama.DiagramReadOnly;
import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.helper.Html;
import ieci.tdw.ispac.designer.client.helper.Util;
import ieci.tdw.ispac.designer.client.helper.Validates;
import ieci.tdw.ispac.designer.client.helper.varDefs;
import ieci.tdw.ispac.designer.client.menuPopUp.MenuPopUpPanel;
import ieci.tdw.ispac.designer.client.objetos.CampoEntidad;
import ieci.tdw.ispac.designer.client.objetos.ComparadorCampos;
import ieci.tdw.ispac.designer.client.objetos.ComparadorEntidades;
import ieci.tdw.ispac.designer.client.objetos.Condition;
import ieci.tdw.ispac.designer.client.objetos.ConditionSimple;
import ieci.tdw.ispac.designer.client.objetos.DrawFlow;
import ieci.tdw.ispac.designer.client.objetos.Entidad;
import ieci.tdw.ispac.designer.client.objetos.Evento;
import ieci.tdw.ispac.designer.client.objetos.Operando;
import ieci.tdw.ispac.designer.client.objetos.Regla;
import ieci.tdw.ispac.designer.client.service.GwtIService;
import ieci.tdw.ispac.designer.client.service.GwtIServiceAsync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.balon.gwt.diagrams.client.connection.AbstractConnection;
import pl.balon.gwt.diagrams.client.connection.calculator.ConnectionDataCalculator;
import pl.balon.gwt.diagrams.client.connection.calculator.FullRectilinearTwoEndedCalculator;
import pl.balon.gwt.diagrams.client.connection.data.ConnectionData;
import pl.balon.gwt.diagrams.client.connection.data.Point;
import pl.balon.gwt.diagrams.client.connector.Connector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RectilinearTwoEndedConnectionCustom extends AbstractConnection {

	/**
	 * DOM elements representing connection in browser dom tree
	 */
	private List elements = new ArrayList();

	static List tipoCampoFila = new ArrayList();

	static int codeEvento = -1;

	// Creo el popUpDeLosConectores
	static MenuPopUpPanel popUpConectorSinCond;

	// Creo el popUpDeLosConectores
	static MenuPopUpPanel popUpConectorCond;

	// Para las conexiones con el servlet
	static GwtIServiceAsync servicio;

	static MenuPopUpPanel popUpConectorTramitador;
	// Almacena la lista de entidades disponibles con sus campos
	static Map entidades = new HashMap();

	// Mapa <String , Rule> clave y valor , identificador de la regla y la
	// informacion del objeto regla
	static Map mapaReglas = new HashMap();

	/**
	 * Constructs connection
	 */

	public RectilinearTwoEndedConnectionCustom(Connector[] toConnect) {
		super(toConnect);
		if (toConnect.length != 2) {
			throw new IllegalArgumentException(
					"Need exactly two connectors to connect");
		}
		Element e = DOM.createDiv();

		setElement(e);
		addStyleName("gwt-diagrams-connection");

		// 1)Creo el proxy del cliente
		servicio = (GwtIServiceAsync) GWT.create(GwtIService.class);
		// servicio = (GwtIServiceAsync)GWT.create(GwtIService.class);

		// 2)Especificar la url que en la que nuestra implementaciÃ³n de
		// servicio esta corriendo

		ServiceDefTarget endpoint = (ServiceDefTarget) servicio;
		String URLmodulo = GWT.getModuleBaseURL() + "GwtIService.gwt";
		endpoint.setServiceEntryPoint(URLmodulo);
	}

	public static void deleteImgArrowStage(String flujo)
	{
		
	}
	public static void reordenarIdFila(Element tr)
	{
		if(tr!=null){
		Element tbody= DOM.getParent(tr);
		int numHijos=DOM.getChildCount(tbody);
		//La cero es para la cabecera
		for(int i=1; i< numHijos; i++)
		{
			Element tr1=DOM.getChild(tbody, i);
			DOM.setElementAttribute(tr1, "id", ""+(i-1));
		}
		}
		
	}
	
	/**
	 * Constructs connection
	 * 
	 * @throws DesignerException
	 */
	public RectilinearTwoEndedConnectionCustom(int idProcedimiento,
			Connector c1, Connector c2) throws DesignerException {

		this(new Connector[] { c1, c2 });

		construirPopUpConector(idProcedimiento);

	}

	/**
	 * 
	 * @param c1
	 *            Conector 1 del flujo
	 * @param c2
	 *            Conector 2 del flujo
	 */
	public RectilinearTwoEndedConnectionCustom(Connector c1, Connector c2) {

		this(new Connector[] { c1, c2 });

	}

	/**
	 * 
	 * @param idProcedimiento
	 *            Identificador del procedimiento para el que vamos a construir
	 *            el pop up
	 */
	static public boolean hayCondicion = false;
	static public boolean sinCondicion=false;
	protected void construirPopUpConector(final int idProcedimiento)
			throws DesignerException {

		popUpConectorCond = new MenuPopUpPanel();
		popUpConectorSinCond = new MenuPopUpPanel();
		popUpConectorTramitador = new MenuPopUpPanel();
		HTML eliminarConector;

		eliminarConector = new HTML(Html.label(DiagramBuilderExample.mensajes
				.delete()));
		eliminarConector.addStyleName("seleccionable");
		popUpConectorCond.addMenuItem(eliminarConector);
		eliminarConector.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpConectorCond.hide();

				if (Window.confirm(DiagramBuilderExample.mensajes
						.confirmDeleteFlow())) {

					eliminarFlujo(idProcedimiento);

				}
			}

		});

		HTML eliminarConector2;

		eliminarConector2 = new HTML(Html.label(DiagramBuilderExample.mensajes
				.delete()));
		eliminarConector2.addStyleName("seleccionable");
		popUpConectorSinCond.addMenuItem(eliminarConector2);
		eliminarConector2.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpConectorSinCond.hide();

				if (Window.confirm(DiagramBuilderExample.mensajes
						.confirmDeleteFlow())) {

					eliminarFlujo(idProcedimiento);

				}
			}

		});
		popUpConectorCond.addSeparator();
		popUpConectorSinCond.addSeparator();

		HTML addCond = new HTML(Html.label(DiagramBuilderExample.mensajes
				.addCondition()));
		addCond.addStyleName("seleccionable");

		popUpConectorSinCond.addMenuItem(addCond);
		addCond.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpConectorSinCond.hide();
				// Llamar para conseguir un listado de entidades sobre las que
				// el usuario puede
				try {
                     sinCondicion=true;
                     hayCondicion=false;
					obtenerListadoEventos();
				} catch (DesignerException e) {
					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100,

							"construirListadoCondiciones", e
									.getLocalizedMessage()));
				}

			}

		});

		HTML condicion = new HTML(Html.label(DiagramBuilderExample.mensajes
				.condition()));
		condicion.addStyleName("seleccionable");

		popUpConectorCond.addMenuItem(condicion);
		condicion.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				 sinCondicion=false;
				popUpConectorCond.hide();
				hayCondicion = true;
				getCTRules();

			}

		});

		HTML condicion2 = new HTML(Html.label(DiagramBuilderExample.mensajes
				.condition()));
		condicion.addStyleName("seleccionable");

		popUpConectorTramitador.addMenuItem(condicion2);
		condicion2.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {

				popUpConectorTramitador.hide();
				hayCondicion = true;
				getCTRules();

			}

		});

	}

	public static void getCTRules() {
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				try {

					mapaReglas = new HashMap();
					mapaReglas = (Map) result;
					
				
					obtenerListadoEventos();
				} catch (DesignerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			public void onFailure(Throwable caught) {

				Window.alert(DiagramBuilderExample.mensajeError
						.accionNoRealizada(100, "getCTRules", caught
								.getLocalizedMessage()));

			}
		};

		try {
			servicio.getCTRules(callback);
		} catch (DesignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static boolean deleteLastEvent = false;

	protected static void obtenerListadoEventos() throws DesignerException {
		indexFilasChecked.clear();
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				flujoSel.setEvents(new ArrayList());
				flujoSel.setEvents((List) result);
				if (flujoSel.getEvents().size() > 0)
					codeEvento = ((Evento) flujoSel.getEvents().get(0))
							.getCode();
				try {
					obtenerListadoEntidades();
				} catch (DesignerException e) {
					Window
							.alert(DiagramBuilderExample.mensajeError
									.accionNoRealizada(100,

									"obtenerListadoEntidades", e
											.getLocalizedMessage()));
				}

			}

			public void onFailure(Throwable caught) {

				Window.alert(DiagramBuilderExample.mensajeError
						.accionNoRealizada(100,

						"getEventRules", caught.getLocalizedMessage()));

			}
		};

		servicio.getEventRules(varDefs.EVENT_OBJ_FLOW, flujoSel.getId(),
				callback);

	}

	/**
	 * 
	 * @param lb
	 *            ListBox del que queremos obtener el indice
	 * @param literal
	 *            literal del lb del que queremos obtener el indice
	 * @return
	 */
	protected static int getIndex(ListBox lb, String literal) {
		int indice = -1;
		for (int i = 0; i < lb.getItemCount(); i++) {
			if (lb.getValue(i).compareTo(literal) == 0)
				return i;
		}
		return indice;

	}

	static boolean checked = false;

	/**
	 * 
	 * @param td1
	 *            Celda en la que se guardara el lb y su txt
	 * @param op
	 *            Objeto Operando
	 * @param nuevo
	 *            Indica si es cargado de bbdd o es nuevo
	 * @param idCond
	 *            Identificador de la condición a la qu esta asociado el
	 *            operando
	 * @param operando
	 *            Indica si es OP2 u OP3 no puede ser OP1 xq OP1 no tiene la
	 *            opcion de txt del valor
	 * @param vacio
	 *            Indica si han de estar vacio los lb
	 * @return
	 */

	public static Element construirOpEntidadValor(Element td1, Operando op,
			boolean nuevo, String operando, boolean vacio) {

		String entidad = op.getNombreEntidad();
		String campo = op.getNombreCampo();
		
		Element table = DOM.createTable();

		Element tbody = DOM.createTBody();

		Element tr = DOM.createTR();
		Element td = DOM.createTD();
		DOM.appendChild(tr, td);

		td = DOM.createTD();

		Element td2 = DOM.createTD();

		Operando opAux = new Operando();
		if (vacio) {
			opAux.setNombreEntidad("");
			opAux.setNombreCampo("");
			opAux.setValor(op.getValor());
		} else {
			opAux.setNombreEntidad(entidad);
			opAux.setNombreCampo(campo);
			opAux.setValor(op.getValor());
		}

		td2 = construirOpEntidad(td2, opAux, nuevo, operando, true);

		DOM.setElementAttribute(td2, "id", "entidad"
				+ operando.substring(0, operando.length() - 5));
		DOM.appendChild(td, td2);

		DOM.appendChild(tr, td);

		DOM.appendChild(tbody, tr);
		DOM.appendChild(table, tbody);
		DOM.appendChild(td1, table);

		return td1;
	}

	static String idCb = "";

	public static void addEntidadesSort(ListBox lbEntidades) {
		Object[] Acampos = (Object[]) entidades.values().toArray();
		List entidades = new ArrayList();
		for (int i = 0; i < Acampos.length; i++)
			entidades.add(Acampos[i]);
		Collections.sort(entidades, new ComparadorEntidades());
		int numEntidades = entidades.size();

		for (int i = 0; i < numEntidades; i++) {
			Entidad e = ((Entidad) entidades.get(i));
			String clave = e.getNombre();
			String valor = null;

			if (e.getDescripcion() != null
					&& e.getDescripcion().containsKey(
							DiagramBuilderExample.locale))
				valor = (String) e.getDescripcion().get(
						DiagramBuilderExample.locale);
			if (valor == null)
				valor = (String) e.getDescripcion().get(
						DiagramBuilderExample.localePorDefecto);
			lbEntidades.addItem(valor, clave);

		}
		lbEntidades.setSelectedIndex(0);
	}

	public static void addCamposSort(String nombreEntidad, ListBox lbCampos,
			String nombreCampo, int fila, boolean cargar) {
		Entidad ent = ((Entidad) entidades.get(nombreEntidad));
		Element e= getFilaActual(fila);
		String idaux= DOM.getElementAttribute(e, "id");
		if(idaux!=null && idaux.compareTo("")!=0)
		fila=DOM.getElementPropertyInt(e, "id");
		if (ent != null) {
			Object[] Acampos = (Object[]) ent.getCampos().values().toArray();
			String id = DOM.getElementAttribute(lbCampos.getElement(), "id");
			List campos = new ArrayList();
			for (int i = 0; i < Acampos.length; i++)
				campos.add(Acampos[i]);

			Collections.sort(campos, new ComparadorCampos());

			int numCampos = campos.size();

			for (int i = 0; i < numCampos; i++) {
				CampoEntidad c = ((CampoEntidad) campos.get(i));
				String clave = c.getNombre();
				String valor = c.getNombre();
				Integer tipoSel = null;
				if (tipoCampoFila.size() > fila)
					tipoSel = (Integer) tipoCampoFila.get(fila);

				if (id != null && ( Html.contains(id, "OP1")
						|| Util.esCompatible(tipoSel, c.getTipo()) || cargar)) {

					if (c.getDescripcion() != null
							&& c.getDescripcion().containsKey(
									DiagramBuilderExample.locale)) {

						valor = (String) c.getDescripcion().get(
								DiagramBuilderExample.locale);

					}
					lbCampos.addItem(valor, clave);
					if (clave != null && clave.compareTo(nombreCampo) == 0) {

						lbCampos.setSelectedIndex(lbCampos.getItemCount() - 1);

						tipoCampoFila.add(fila, new Integer(c.getTipo()));

					}
				}

			}
		}
	}

	/**
	 * 
	 * @param td1
	 *            Celda sobre la que guardaremos el lb construido
	 * @param op
	 *            Operando con la informacion
	 * @param nuevo
	 *            Indica si es nuevo o viene de bbdd
	 * @param ope
	 *            "OP1" "OP2" "OP3"
	 * @return
	 */

	public int anchoEntidad = 0;

	public native static void showCalendar(Object ethis, Object e,
			String lenguaje, String valorActual, int x, int y)/*-{
		if(lenguaje!=null)
		new $wnd.showCalendar(ethis, e, "dd/mm/yyyy", lenguaje ,null ,  x,y  );
		
		}-*/;

	public static Element construirOpEntidad(Element td1, Operando op,
			boolean nuevo, String ope, boolean tieneValor) {

		String[] auxi = ope.split("_");
		int idFila = Integer.parseInt(auxi[0]);
		String entidad = op.getNombreEntidad();
		TextBox tx = new TextBox();

		DOM.setStyleAttribute(tx.getElement(), "display", "none");
		Element table = DOM.createTable();
		Element tbody = DOM.createTBody();

		Element tr = DOM.createTR();
		Element td = DOM.createTD();

		DOM.appendChild(tr, td);

		td = DOM.createTD();
		// DOM.setInnerText(td,DiagramBuilderExample.mensajes.campo());
		DOM.appendChild(tr, td);

		DOM.appendChild(tbody, tr);

		tr = DOM.createTR();
		td = DOM.createTD();

		ListBox lbEntidad = new ListBox();
		lbEntidad.addStyleName("entidadValor");
		DOM.setElementAttribute(lbEntidad.getElement(), "id", ope + "Entidad");
		ListBox lbCampos = new ListBox();
		lbCampos.addStyleName("entidadValor");
		DOM.setElementAttribute(lbCampos.getElement(), "id", ope + "Campo");

		String idTxt = ope + "Valor";
		DOM.setElementAttribute(tx.getElement(), "id", idTxt);

		Image image = new Image();
		DOM.setElementAttribute(image.getElement(), "id", ope + "VImage");
		image.setUrl(".//img//calendarM.gif");
		if (!nuevo && Integer.parseInt(tipoCampoFila.get(idFila).toString()) == varDefs.DATETIME)
			DOM.setStyleAttribute(image.getElement(), "display", "block");
		else
			DOM.setStyleAttribute(image.getElement(), "display", "none");
		image.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {

				String id = DOM.getElementAttribute(sender.getElement(), "id");
				String idRes = id.substring(0, id.length() - 6);
				idRes += "Valor";
                
				showCalendar(sender.getElement(), DOM.getElementById(idRes),
						 AbstractConnectionsExample.locale,"", sender
								.getAbsoluteLeft(), sender.getAbsoluteTop());

			}

		});

		lbEntidad.addItem("", "-1");
		addEntidadesSort(lbEntidad);

		lbEntidad.setSelectedIndex(0);
		lbCampos.addItem("", "-1");
		if (!nuevo) {
			if (entidad != null && entidad.compareTo("") != 0) {
				int index = getIndex(lbEntidad, entidad);

				if (index != -1) {
					lbEntidad.setSelectedIndex(index);
					// Cargo los campos asociados a la entidad actualmente
					// seleccionada
					String nombreEntidad = lbEntidad.getValue(index);

					addCamposSort(nombreEntidad, lbCampos, op.getNombreCampo(),
							idFila, true);

					DOM.setStyleAttribute(tx.getElement(), "display", "none");

					DOM
							.setStyleAttribute(image.getElement(), "display",
									"none");

					DOM.setStyleAttribute(lbCampos.getElement(), "display", "");

				} else {

					DOM.setStyleAttribute(tx.getElement(), "display", "block");
					if (!nuevo && Integer.parseInt(tipoCampoFila.get(idFila).toString()) == varDefs.DATETIME)
						DOM.setStyleAttribute(image.getElement(), "display",
								"block");
					DOM.setStyleAttribute(lbCampos.getElement(), "display",
							"none");

				}
			} else {

				DOM.setStyleAttribute(tx.getElement(), "display", "block");
				if (!nuevo && Integer.parseInt(tipoCampoFila.get(idFila).toString()) == varDefs.DATETIME)
					DOM.setStyleAttribute(image.getElement(), "display",
							"block");
				DOM.setStyleAttribute(lbCampos.getElement(), "display", "none");
			}

		}

		lbEntidad.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// Identificar el operador seleccionado actualmente

				ListBox lbEntidad = ((ListBox) sender);

				// Obtengo su id
				String id = lbEntidad.getValue(lbEntidad.getSelectedIndex());

				// Obtengo el objeto Entidad asociado a la entidad seleccionada
				
				String idLb = DOM.getElementAttribute(lbEntidad.getElement(),
						"id");
				idCb = (String) idLb.subSequence(0, 5);
				String idTxt = idCb + "Valor";
				idCb = idCb + "Campo";

				// Busco el lb
				int numChild = RootPanel.get().getWidgetCount();
				boolean encontrado = false;
				int i;
				Widget txt = null;
				Element imageCalendar = null;
				ListBox lbC = null;
				for (i = 0; i < numChild && !encontrado; i++) {
					Widget hijo = RootPanel.get().getWidget(i);
					String idCampo = DOM.getElementAttribute(hijo.getElement(),
							"id");

					if (idCampo != null && idCampo.compareTo(idTxt) == 0) {
						txt = hijo;
						String operando = idTxt
								.substring(0, idTxt.length() - 5);
						imageCalendar = DOM.getElementById(operando + "VImage");

					} else if (idCampo != null && idCampo.compareTo(idCb) == 0) {

						encontrado = true;
						// Elimino los items que hubiera y añado los nuevo
						lbC = (ListBox) hijo;

						lbC.clear();
						String[] auxi = idCampo.split("_");
						int idFila = Integer.parseInt(auxi[0]);
						addCamposSort(id, lbC, "", idFila, false);
						lbC.addItem("", "-1");
						// lbC.setVisibleItemCount(lbC.getItemCount());
						lbC.setSelectedIndex(lbC.getItemCount() - 1);

						// Muestro el lb y oculto el txto

						DOM.setStyleAttribute(lbC.getElement(), "display",
								"block");

					}

				}
				if (!Html.contains(idCb, "OP1")) {
					if (encontrado) {
						if (txt == null) {
							encontrado = false;
							for (; i < numChild && !encontrado; i++) {
								Widget hijo = RootPanel.get().getWidget(i);
								String idCampo = DOM.getElementAttribute(hijo
										.getElement(), "id");
								if (idCampo != null
										&& idCampo.compareTo(idTxt) == 0) {
									txt = hijo;
									String operando = idTxt.substring(0, idTxt
											.length() - 5);
									imageCalendar = DOM.getElementById(operando
											+ "VImage");
									encontrado = true;

								}
							}
						}

					}

					// Ponemos a visible invisible
					if (id == null || id.compareTo("-1") == 0) {
						DOM.setStyleAttribute(txt.getElement(), "display", "");
						String[] auxi = DOM.getElementAttribute(txt.getElement(), "id").split("_");
						int idFila = Integer.parseInt(auxi[0]);
						Element trPadre= getFilaActual(idFila);
						auxi=DOM.getElementAttribute(trPadre, "id").split("_");
						idFila=Integer.parseInt(auxi[0]);
						if(Integer.parseInt(tipoCampoFila.get(idFila).toString())==varDefs.DATETIME)
						DOM.setStyleAttribute(imageCalendar, "display", "");
						DOM.setStyleAttribute(lbC.getElement(), "display",
								"none");

					} else if (txt != null) {
						TextBox tx = (TextBox) txt;
						tx.setText("");
						DOM.setStyleAttribute(txt.getElement(), "display",
								"none");
						DOM.setStyleAttribute(imageCalendar, "display", "none");
						DOM.setStyleAttribute(lbC.getElement(), "display", "block");
					}
				}

			}
		});
		lbCampos.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {

				// TODO Auto-generated method stub
				// oBTENER EL LISTBOX CON EL OPERADOR
				ListBox lbCampos = ((ListBox) sender);
				if (lbCampos.getSelectedIndex() != -1) {
					int tipoCampo = -1;
					String idCampo = DOM.getElementAttribute(lbCampos
							.getElement(), "id");
					// Obtengo su nombre en bbdd
					String nombreCampo = lbCampos.getValue(lbCampos
							.getSelectedIndex());
				
					Element lb= DOM.getElementById(idCampo);
					int idFila=-1;
					if(Html.contains(idCampo, "OP1"))
						
					idFila=idFila(lb,  7);
					else 
						idFila=idFila(lb,  16);
				
					// Obtengo el tipo que tiene asociado

					// Iteramos sobre todas las entidades
					Iterator itr = entidades.values().iterator();
					boolean encontrado = false;
					while (itr.hasNext() && !encontrado) {

						Entidad e = (Entidad) itr.next();
						Iterator itr2 = e.getCampos().values().iterator();
						while (itr2.hasNext() && !encontrado) {
							CampoEntidad c = (CampoEntidad) itr2.next();
							if (c.getNombre().compareTo(nombreCampo) == 0) {
								encontrado = true;
								tipoCampo = c.getTipo();

							}

						}

					}

					// Obtengo el listbox del operador
					ListBox lbOperador = new ListBox();

					int numHijos = RootPanel.get().getWidgetCount();
					encontrado = false;
					int cont = 0;
					String idOperador = idCampo.substring(0, 1) + "_Operador";

					while (cont < numHijos && !encontrado) {
						Widget aux = RootPanel.get().getWidget(cont);
						String idAux = DOM.getElementAttribute(
								aux.getElement(), "id");
						if (idAux != null && Html.contains(idAux, idOperador)) {
							lbOperador = (ListBox) aux;
							encontrado = true;
						}
						cont++;
					}

					if (idCampo != null && Html.contains(idCampo, "OP1")) {

						if (tipoCampoFila.size() > idFila) {
							if (((Integer) tipoCampoFila.get(idFila))
									.intValue() != tipoCampo) {
								resetearLBFila(idFila, tipoCampo);
								loadOperadorByType(lbOperador, tipoCampo);
							}
						}

						tipoCampoFila.add(idFila, new Integer(tipoCampo));
					}

				}

			}

		});

		// Cuando seleccione un campo u otro el lb de los operadores se ha de
		// cargar con los valores permitidos
		lbCampos.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {

			}

		});

		RootPanel.get().add(lbCampos);
		RootPanel.get().add(lbEntidad);
		/*
		 * RootPanel.get().add(tx); RootPanel.get().add(image);
		 */

		Element div = DOM.createDiv();
		DOM.appendChild(div, lbEntidad.getElement());
		DOM.appendChild(td, div);

		div = DOM.createDiv();
		DOM.appendChild(div, lbCampos.getElement());
		if (tieneValor) {

			tx.setText(op.getValor());
			DOM.setElementAttribute(tx.getElement(), "id", ope + "Valor");
			if (nuevo) {
				DOM.setStyleAttribute(tx.getElement(), "display", "block");
				DOM.setStyleAttribute(lbCampos.getElement(), "display", "none");
			}

			RootPanel.get().add(tx);
			RootPanel.get().add(image);

			Element table2 = DOM.createTable();
			DOM.setElementAttribute(table2, "id", "tablaEntidadValor");
			Element tbody2 = DOM.createTBody();
			Element tr2 = DOM.createTR();
			Element td2 = DOM.createTD();
			DOM.appendChild(td2, tx.getElement());
			DOM.appendChild(tr2, td2);
			td2 = DOM.createTD();
			DOM.appendChild(td2, image.getElement());
			DOM.appendChild(tr2, td2);
			DOM.appendChild(tbody2, tr2);
			DOM.appendChild(table2, tbody2);

			DOM.appendChild(div, table2);

			/*
			 * DOM.appendChild(div, tx.getElement()); DOM.appendChild(div,
			 * image.getElement());
			 */
		}
		DOM.appendChild(td, div);

		DOM.appendChild(tr, td);
		DOM.appendChild(tbody, tr);

		DOM.appendChild(table, tbody);
		DOM.appendChild(td1, table);

		return td1;

	}

	public static void resetearLBFila(int idFila, int tipo) {

		int numHijos = RootPanel.get().getWidgetCount();
		Image imagenCalendarop2 = null;
	
		Image imagenCalendariop3 = null;
		boolean ponerCalendar2 = false;
		boolean ponerCalendar3 = false;
		for (int i = 0; i < numHijos; i++) {
			Widget hijo = RootPanel.get().getWidget(i);
			String id = DOM.getElementAttribute(hijo.getElement(), "id");
			if(Html.contains(id, "_OP"))
			{
				String []auxi= id.split("_");
				int idFilaElemento=-1;
				if(auxi.length>1)
				idFilaElemento=Integer.parseInt(auxi[0]);
				Element e =getFilaActual(idFilaElemento);
				if(e!=null){
				String ide= DOM.getElementAttribute(e, "id");
				auxi= ide.split("_");
		
				if(auxi.length==1)
				idFilaElemento=Integer.parseInt(auxi[0]);
				}
				if(idFilaElemento==idFila && e!=null){
				
			if (id != null
					&& (Html.contains(id, "_OP2Entidad") || Html
							.contains(id, "_OP3Entidad"))) {
				ListBox lb = (ListBox) hijo;
				lb.setSelectedIndex(0);
				
			} else if (id != null
					&& (Html.contains(id, "_OP2Campo") || Html
							.contains(id, "_OP3Campo"))) {
				ListBox lb = (ListBox) hijo;
				DOM.setStyleAttribute(lb.getElement(),
						"display", "none");
				lb.clear();
			}

			else if (id != null && (Html.contains(id, "_OP2Valor"))) {
				TextBox txt = (TextBox) hijo;
				txt.setText("");
				DOM.setStyleAttribute(txt.getElement(),
						"display", "block");
				if (tipo == varDefs.DATETIME )
					ponerCalendar2 = true;

			} else if (id != null && (Html.contains(id, "_OP3Valor"))) {
				TextBox txt = (TextBox) hijo;
				txt.setText("");
				DOM.setStyleAttribute(txt.getElement(),
						"display","block");
				if (tipo == varDefs.DATETIME )
					ponerCalendar3 = true;

			} else if (id != null && (Html.contains(id, "_OP2VImage"))) {

				imagenCalendarop2 = (Image) hijo;

			} else if (id != null && (Html.contains(id, "_OP3VImage"))) {

				imagenCalendariop3 = (Image) hijo;

			}
			}
			}
		}

		if (ponerCalendar2) {
			DOM.setStyleAttribute(imagenCalendarop2.getElement(), "display",
					"block");
		} else
			DOM.setStyleAttribute(imagenCalendarop2.getElement(), "display",
					"none");
		if (ponerCalendar3) {
			DOM.setStyleAttribute(imagenCalendariop3.getElement(), "display",
					"block");
		} else
			DOM.setStyleAttribute(imagenCalendariop3.getElement(), "display",
					"none");

	}

	/**
	 * 
	 * @param lbOperador:
	 *            ListBox con los operadores que el usuario puede seleccionar en
	 *            función del tipo del operando
	 * @param tipo
	 *            Tipo del operando si es -1 es que es una nueva condicion en
	 *            blanco , luego apareceran todos.
	 */

	public static void loadOperadorByType(ListBox lbOperador, int tipo) {

		// Almaceno el que tuvieramos antes
		int indexSelected = lbOperador.getSelectedIndex();
		String valor = null;
		if (indexSelected != -1)
			valor = lbOperador.getItemText(indexSelected);

		// Supongamos que el tipo string , varchar etc sea el 1
		lbOperador.clear();

		lbOperador.addItem(DiagramBuilderExample.mensajes.equal());
		lbOperador.addItem(DiagramBuilderExample.mensajes.distinct());
		lbOperador.addItem(DiagramBuilderExample.mensajes.notNull());
		lbOperador.addItem(DiagramBuilderExample.mensajes.nulo());
		// lbOperador.addItem(DiagramBuilderExample.mensajes.like());

		if (!(tipo == varDefs.LONGTEXT || tipo == varDefs.SHORTTEXT)) {
			lbOperador.addItem(DiagramBuilderExample.mensajes.greaterThan());
			lbOperador.addItem(DiagramBuilderExample.mensajes.greaterEqual());
			lbOperador.addItem(DiagramBuilderExample.mensajes.lessThan());
			lbOperador.addItem(DiagramBuilderExample.mensajes.lessEqual());

			lbOperador.addItem(DiagramBuilderExample.mensajes.between());
		}

		if (indexSelected != -1) {
			boolean encontrado = false;
			for (int i = 0; i < lbOperador.getItemCount() && !encontrado; i++) {
				if (lbOperador.getItemText(i).compareTo(valor) == 0) {
					encontrado = true;
					lbOperador.setSelectedIndex(i);
				}
			}
		}
		
	
		int indice = lbOperador.getSelectedIndex();
		String text = lbOperador.getItemText(indice);

		Element td = DOM.getParent(lbOperador.getElement());
		if(td!=null && text!=null && text.compareTo("")!=0){
		Element tr = DOM.getParent(td);
		Element op2 = DOM.getChild(tr, 5);
		Element op3 = DOM.getChild(tr, 6);
		setVisibilidadOperandos(op2, op3, text);
		}

	}

	static void setVisibilidadOperandos(Element op2, Element op3, String text) {
		
		// Hay que ocultar el operador 3 si es cualquier operador salvo el
		// beteween
		if (text.compareTo(DiagramBuilderExample.mensajes.between()) != 0) {

			DOM.setStyleAttribute(op3, "visibility", "hidden");
		} else {
			// Hay que visualizarlo

			DOM.setStyleAttribute(op3, "visibility", "visible");
		}
		// Hay que ocultar el operador 2 si no es ni null ni not null
		if (text.compareTo(DiagramBuilderExample.mensajes.notNull()) != 0
				&& text.compareTo(DiagramBuilderExample.mensajes.nulo()) != 0) {

			// El operando 2 esta en la columna 6, indice=5

			DOM.setStyleAttribute(op2, "visibility", "visible");

		} else {

			DOM.setStyleAttribute(op2, "visibility", "hidden");

		}

	}

	/**
	 * 
	 * @param i
	 *            Indica la fila a construir
	 * @param c
	 *            Condicion simple a representar
	 * @param nuevo
	 *            Indica si es nuevo o viene de bbdd
	 * @return
	 */
	public static Element construirFila(int i, ConditionSimple c, boolean nuevo) {
		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		CheckBox cb = new CheckBox();
		RootPanel.get().add(cb);
		String nombreEntidad = c.getOp1().getNombreEntidad();

		// Obtengo el tipo de la fila, que sera el tipo del campo del operando1
		if (i != -1 && nombreEntidad != null) {

			String nombreCampo = c.getOp1().getNombreCampo();
			Entidad ent = (Entidad) entidades.get(nombreEntidad);

			Map campos = ent.getCampos();
			CampoEntidad c1 = ((CampoEntidad) campos.get(nombreCampo));
			int tipo = c1.getTipo();
			tipoCampoFila.add(i, new Integer(tipo));
		}

		cb.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				checked = ((CheckBox) sender).isChecked();
				Element cb = sender.getElement();
				int id = DOM.getElementPropertyInt(cb, "id");
				
				id=idFila(cb, 2);
				// indexFilasChecked.contains(o)
				if (checked) {
					if (!indexFilasChecked.contains(new Integer(id)))
						indexFilasChecked.add(new Integer(id));
				} else if (indexFilasChecked.contains(new Integer(id)))
					indexFilasChecked.remove(new Integer(id));
			}
		});

		DOM.setElementAttribute(cb.getElement(), "id", "" + i);
		DOM.appendChild(td1, cb.getElement());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		ListBox lbAndOr = new ListBox();
		lbAndOr.addItem(" ");
		if (i == 0) {
			lbAndOr.setVisible(false);
		}

		lbAndOr.addItem(DiagramBuilderExample.mensajes.And());
		lbAndOr.addItem(DiagramBuilderExample.mensajes.Or());

		// lbAndOr.getItemText(lbAndOr.getSelectedIndex());
		DOM.setElementAttribute(lbAndOr.getElement(), "id", i + "_AndOr");
		RootPanel.get().add(lbAndOr);
		DOM.appendChild(td1, lbAndOr.getElement());
		DOM.appendChild(tr1, td1);
		td1 = DOM.createTD();
		ListBox lbParentesisOpen = new ListBox();
		DOM.setElementAttribute(lbParentesisOpen.getElement(), "id", i + "_)");
		lbParentesisOpen.addItem(" ");
		lbParentesisOpen.addItem("(");
		DOM.setElementAttribute(lbParentesisOpen.getElement(), "id", i
				+ "_Open");
		RootPanel.get().add(lbParentesisOpen);
		if (c.getParentesisOpen() == 1)
			lbParentesisOpen.setSelectedIndex(1);

		DOM.appendChild(td1, lbParentesisOpen.getElement());
		DOM.appendChild(tr1, td1);
		td1 = DOM.createTD();
		DOM.appendChild(tr1, td1);

		td1 = construirOpEntidad(td1, c.getOp1(), nuevo, i + "_OP1", false);

		td1 = DOM.createTD();
		ListBox lbOperador = new ListBox();
		loadOperadorByType(lbOperador, -1);

		DOM.setElementAttribute(lbOperador.getElement(), "id", i + "_Operador");
		RootPanel.get().add(lbOperador);

		// DiagramBuilderExample.vpA.add(lbOperador);
		DOM.appendChild(td1, lbOperador.getElement());
		DOM.appendChild(tr1, td1);

		/**
		 * Cuando el operando se modifique se han de cargar los op2 y/o op2
		 * segun operador seleccionado*
		 */

		lbOperador.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// Identificar el operador seleccionado actualmente

				ListBox lbOpe = ((ListBox) sender);
				int indice = lbOpe.getSelectedIndex();
				String text = lbOpe.getItemText(indice);

				Element td = DOM.getParent(lbOpe.getElement());
				Element tr = DOM.getParent(td);
			

				Element op2 = DOM.getChild(tr, 5);
				Element op3 = DOM.getChild(tr, 6);

				setVisibilidadOperandos(op2, op3, text);

			}

		});

		td1 = DOM.createTD();
		if (c.getOp2() != null
				&& (c.getOp2().getValor() != null || (c.getOp2()
						.getNombreEntidad() != null && c.getOp2()
						.getNombreEntidad().compareTo("") != 0))) {

			td1 = construirOpEntidadValor(td1, c.getOp2(), nuevo, i + "_OP2",
					false);

			if (c.getOperador().compareTo(
					DiagramBuilderExample.mensajes.notNull()) == 0
					||

					c.getOperador().compareTo(
							DiagramBuilderExample.mensajes.nulo()) == 0) {

				DOM.setStyleAttribute(td1, "visibility", "hidden");

			}
		}

		else // construirVacio
		{

			td1 = construirOpEntidadValor(td1, c.getOp2(), nuevo, i + "_OP2",
					true);

			if (!nuevo)
				DOM.setStyleAttribute(td1, "visibility", "hidden");

		}

		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();

		if (c.getOp3() != null
				&& ((c.getOp3().getNombreEntidad() != null && c.getOp3()
						.getNombreEntidad().compareTo("") != 0) || c.getOp3()
						.getValor() != null)) {
			td1 = construirOpEntidadValor(td1, c.getOp3(), nuevo, i + "_OP3",
					false);
			if (c.getOperador().compareTo(
					DiagramBuilderExample.mensajes.between()) != 0) {
				DOM.setStyleAttribute(td1, "visibility", "hidden");

			}
		} else {
			td1 = construirOpEntidadValor(td1, c.getOp3(), nuevo, i + "_OP3",
					true);
			DOM.setStyleAttribute(td1, "visibility", "hidden");
		}
		DOM.appendChild(tr1, td1);

		if (!nuevo) {

			int indice = getIndex(lbOperador, c.getOperador());
			lbOperador.setSelectedIndex(indice);

			indice = getIndex(lbAndOr, c.getAndOr());
			if (indice != -1)
				lbAndOr.setSelectedIndex(indice);
		}

		td1 = DOM.createTD();
		ListBox lbParentesisClose = new ListBox();
		DOM.setElementAttribute(lbParentesisClose.getElement(), "id", i + "_(");
		lbParentesisClose.addItem(" ");
		lbParentesisClose.addItem(")");

		DOM.setElementAttribute(lbParentesisClose.getElement(), "id", i
				+ "_Close");
		RootPanel.get().add(lbParentesisClose);
		if (c.getParentesisClose() == 1)
			lbParentesisClose.setSelectedIndex(1);
		DOM.appendChild(td1, lbParentesisClose.getElement());
		DOM.appendChild(tr1, td1);

		return tr1;
	}

	/**
	 * Realiza las acciones necesarias para que el usuario vuelva a la pantalla
	 * normal del diseñador
	 */

	public void salirCondicion(DrawFlow aux) {
		Element listado = DOM.getElementById(varDefs.divListado);
		indexFilasChecked.clear();
		condsSel = new Condition();
		indexFilasChecked = new ArrayList();
		condsSel = null;
		Html.removeAllChildren(listado);
		Designer.listadoOculto();

		if (aux != null) {
			HTML imageC = DiagramBuilderExample.addImgCondicion(aux);
			if(imageC!=null)
			RootPanel.get().add(imageC);
			update();
		}

	}

	public static String idSel;
public static boolean add=true;
	public static void construirListadoCondiciones() throws DesignerException {

		Element tramites = DOM.getElementById(varDefs.divTramites);
		Element listado = DOM.getElementById(varDefs.divListado);
		Element divTram = DOM.getElementById(varDefs.divTramites);
		Button eliminar = new Button(DiagramBuilderExample.mensajes
				.buttonDelete());
		Button add = new Button(DiagramBuilderExample.mensajes.buttonAdd());
		Button subir = new Button(DiagramBuilderExample.mensajes
				.buttonSubir());
		Button bajar = new Button(DiagramBuilderExample.mensajes
				.buttonBajar());
		condsSel = null;
	
		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();
		Element e = DOM.createDiv();
		
		if (DiagramBuilderExample.imgTram == null) {
			DiagramBuilderExample.imgTram = new Image();

			DiagramBuilderExample.imgTram.setUrl(".//img//close1.png");
		}
		if(AbstractConnectionsExample.vez==0){
		Html.removeAllChildren(listado);
		Html.removeAllChildren(divTram);
		Designer.listadoOculto();
		Designer.tramitesOculto();

		if (DOM.getChildCount(tramites) > 0)
			Html.removeAllChildren(tramites);

		DOM.setStyleAttribute(tramites, "display", "none");
		Html.removeAllChildren(listado);
		Designer.listadoOculto();

		
	

		Element divAzul = DOM.createDiv();
		DOM.setElementAttribute(divAzul, "id", "divAzul");
		DOM.setElementAttribute(divAzul, "align", "right");
		

		DOM.sinkEvents(DiagramBuilderExample.imgTram.getElement(),
				Event.ONCLICK);

		DOM.setEventListener(DiagramBuilderExample.imgTram.getElement(),
				new EventListener() {
					public void onBrowserEvent(Event event) {

						try {
							
							hayCondicion = false;
							closeEventConditionType();
							
						} catch (DesignerException e) {
							// TODO Auto-generated catch block
							Window
									.alert(DiagramBuilderExample.mensajeError
											.accionNoRealizada(
													100,
													"construirWindowAddEventoConditionType",
													e.getLocalizedMessage()));
						}
						Designer.tramitesOculto();
						Element tramites = DOM
								.getElementById(varDefs.divTramites);
						Html.removeAllChildren(tramites);
						DOM.setStyleAttribute(tramites, "display", "none");

					}
				});

		DOM.appendChild(divAzul, DiagramBuilderExample.imgTram.getElement());
		DOM.appendChild(divTram, divAzul);

	
		DOM.setElementAttribute(e, "id", "divModal");
		DOM.setStyleAttribute(e, "width", "960px");

		DOM.appendChild(divTram, e);

		// Contenido de lapagina
		DiagramBuilderExample.addBr(e);

		Element titulo = DOM.createDiv();
		DOM.setElementAttribute(titulo, "id", "tituloTramites");
		DOM.setInnerText(titulo, DiagramBuilderExample.mensajes
				.listadoCondiciones());

		DiagramBuilderExample.addBr(e);
		DOM.appendChild(e, titulo);
		DiagramBuilderExample.addBr(e);
	
		
		HorizontalPanel botonera1 = new HorizontalPanel();
		botonera1.setVerticalAlignment(botonera1.ALIGN_MIDDLE);
		botonera1.setHorizontalAlignment(botonera1.ALIGN_CENTER);
		if (!AbstractConnectionsExample.readOnly ) {
			botonera1.add(add);
			botonera1.add(eliminar);
		}
		RootPanel.get().add(botonera1);
		DOM.appendChild(e, botonera1.getElement());
		DiagramBuilderExample.addBr(e);
		// Construimos la tabla
	      table1 = DOM.createTable();

		DOM.setElementAttribute(table1, "cellpadding", "5");
		DOM.setElementAttribute(body1, "cellpadding", "6");
		DOM.setElementAttribute(table1, "id", "disponibles");
		DOM.setElementAttribute(table1, "id", "bodyDisponibles");
		}
		else{
			
		e=DOM.getElementById("divModal");
		table1 = DOM.getElementById("bodyDisponibles");
	
			
		}
		
		// Obtengo el listado de condiciones
		if (flujoSel.getEvents().size() == 0 && !deleteLastEvent) {

			Designer.tramitesOculto();
			tramites = DOM.getElementById(varDefs.divTramites);
		
			DOM.setStyleAttribute(tramites, "display", "none");

			AsyncCallback callback = new AsyncCallback() {
				public void onSuccess(Object result) {

					try {

						if (codeEvento == -1) {
							AsyncCallback callback = new AsyncCallback() {

								public void onFailure(Throwable caught) {

									Window
											.alert(DiagramBuilderExample.mensajeError
													.accionNoRealizada(
															100,
															"getEvents",
															caught
																	.getLocalizedMessage()));

								}

								public void onSuccess(Object result) {
									
									Map eventos = (Map) result;
									if (eventos.isEmpty()) {
										Window
												.alert(DiagramBuilderExample.mensajeError
														.noExisteEvento(200));
									}
									Iterator itr = eventos.keySet().iterator();
									codeEvento = Integer.parseInt(itr.next()
											.toString());
								}
							};

							try {
								servicio.getEvents(varDefs.EVENT_OBJ_FLOW,
										callback);
							} catch (DesignerException e) {
							
								Window.alert(DiagramBuilderExample.mensajeError
										.accionNoRealizada(100, "getEvents", e
												.getLocalizedMessage()));
							}
						}
						Map aux = (Map) result;
						List rules = new ArrayList();
						Iterator itr = aux.values().iterator();
						while (itr.hasNext()) {
							rules.add(itr.next());
						}

						construirWindowAddEventoConditionType(rules);

					} catch (DesignerException e) {
						// TODO Auto-generated catch block
						Window
								.alert(DiagramBuilderExample.mensajeError
										.accionNoRealizada(
												100,
												"construirWindowAddEventoConditionType",
												e.getLocalizedMessage()));
					}
				}

				public void onFailure(Throwable caught) {

					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100, "getCTRules", caught
									.getLocalizedMessage()));

				}
			};

			servicio.getCTRules(callback);

		}

		else {
			Designer.tramitesVisible();
			DiagramBuilderExample.imgTram.setVisible(true);

			tr1 = DOM.createTR();
			td1 = DOM.createTD();

			DOM.appendChild(tr1, td1);
			td1 = DOM.createTD();
			DOM.setInnerText(td1, DiagramBuilderExample.mensajes
					.nombreCondicion());
			DOM.appendChild(tr1, td1);
			td1 = DOM.createTD();
			DOM.setInnerText(td1, DiagramBuilderExample.mensajes
					.tipoCondicion());
			DOM.appendChild(tr1, td1);
			DOM.setElementAttribute(tr1, "id", "cabeceraTabla");
			DOM.appendChild(body1, tr1);

			for (int i = 0; i < flujoSel.getEvents().size(); i++) {
				Evento evento = (Evento) flujoSel.getEvents().get(i);

				tr1 = DOM.createTR();
				td1 = DOM.createTD();
				// CheckBox
				CheckBox cb = new CheckBox();
				DOM.setElementAttribute(cb.getElement(), "id", ""
						+ evento.getId());
				RootPanel.get().add(cb);
				cb.addClickListener(new ClickListener() {

					public void onClick(Widget sender) {

						checked = ((CheckBox) sender).isChecked();
						Element cb = sender.getElement();
						String id = DOM.getElementProperty(cb, "id");
						if (checked) {

							if (!indexFilasChecked.contains(id))
								indexFilasChecked.add(id);
						} else {
							if (indexFilasChecked.contains(id))
								indexFilasChecked.remove(id);
						}

					}

				});
				DOM.appendChild(td1, cb.getElement());
				DOM.appendChild(tr1, td1);
				Element tdTipo = DOM.createTD();
				// Nombre de la regla o condicion
				td1 = DOM.createTD();
				if (evento.getRuleId() != -1) {
					Regla regla = (Regla) mapaReglas.get(String.valueOf(evento
							.getRuleId()));
					DOM.setInnerText(td1, regla.getNombre());
					DOM.setInnerText(tdTipo, DiagramBuilderExample.mensajes
							.java());
				} else {
					// Es una condicion

					Element link = DOM.createElement("A");
					DOM.setStyleAttribute(link, "text-decoration", "none");

					DOM.setElementAttribute(link, "class", "displayLink");
					DOM.setElementAttribute(link, "id", "" + evento.getId());
					DOM.setInnerText(link, evento.getCondition().getNombre());
					DOM.setElementAttribute(link, "href", "javascript:;");
					DOM.appendChild(td1, link);
				  	DOM.sinkEvents(link, Event.ONCLICK);
					DOM.setEventListener(link, new EventListener() {
						public void onBrowserEvent(Event event) {
							try {

								// Busco la condicion seleccionada por el id
			
								Element link = DOM.eventGetCurrentTarget(event);
								String id = DOM.getElementAttribute(link, "id");
								boolean encontrado = false;
								for (int i = 0; i < flujoSel.getEvents().size()
										&& !encontrado; i++) {
									if (((Evento) flujoSel.getEvents().get(i))
											.getId().compareTo(id) == 0) {
										
										idSel = id;
										condsSel = ((Evento) flujoSel
												.getEvents().get(i))
												.getCondition();

									}
								}

								construirWindowCond(true);

							} catch (DesignerException e) {
								// TODO Auto-generated catch block
								Window
										.alert(DiagramBuilderExample.mensajeError
												.accionNoRealizada(100,
														"construirWindowCond",
														e.getLocalizedMessage()));
							}
						}
					});
					DOM.setInnerText(tdTipo, DiagramBuilderExample.mensajes
							.sql());
				}

				DOM.appendChild(tr1, td1);
				DOM.appendChild(tr1, tdTipo);
				DOM.appendChild(body1, tr1);

			}

			DOM.appendChild(table1, body1);
			if(AbstractConnectionsExample.vez==0)
			DOM.appendChild(e, table1);

			// Añado los botones Añadir Eliminar

			if (!AbstractConnectionsExample.readOnly && AbstractConnectionsExample.vez==0) {

				

				HorizontalPanel botonera = new HorizontalPanel();
				botonera.setVerticalAlignment(botonera.ALIGN_MIDDLE);
				botonera.setHorizontalAlignment(botonera.ALIGN_CENTER);
				botonera.add(bajar);
				botonera.add(subir);

				botonera.addStyleName("botonera");
			
			   
				RootPanel.get().add(botonera);

				subir.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {

						if (indexFilasChecked.size() == 0) {
							Window.alert(DiagramBuilderExample.mensajes
									.seleccionObligatoria());
						} else {
							AsyncCallback callback = new AsyncCallback() {
								public void onSuccess(Object result) {

									try {
                                      
										Element disponibles= DOM.getElementById("bodyDisponibles");
										int numChild=DOM.getChildCount(disponibles);
										int cont=0;
										for(int i=numChild-1; i>=cont; i--)
										{
											Element hijo=DOM.getChild(disponibles, i);
											String id= DOM.getElementAttribute(hijo, "id");
											if(Html.contains(id, "check")){
												DOM.setElementAttribute(hijo, "id", "--");
												cont+=1;
											}
												
											else{
												DOM.removeChild(disponibles, hijo);
											}	
										}
									/*	numChild=RootPanel.get().getWidgetCount();
									    boolean encontrado=false;
										for(int i=0 ; i<numChild && !encontrado; i++)
										{
											Widget auxi=RootPanel.get().getWidget(i);
											String id= DOM.getElementAttribute(auxi.getElement(), "id");
											if(Html.contains(id, "botoneraJAVA")){
												RootPanel.get().getWidget(i).removeFromParent();
												encontrado=true;
											
											}
											
										}
										
									    Element listadoReglas=DOM.getElementById("listadoReglas");
								        Html.removeAllChildren(listadoReglas);*/
								        
										obtenerListadoEventos();
									} catch (DesignerException e) {
										
										Window
												.alert(DiagramBuilderExample.mensajeError
														.accionNoRealizada(
																100,
																"obtenerListadoEventos",
																e
																		.getLocalizedMessage()));
									}
									indexFilasChecked.clear();

								}

								public void onFailure(Throwable caught) {
									Window
											.alert(DiagramBuilderExample.mensajeError
													.accionNoRealizada(
															100,
															"incOrderEventRules",
															caught
																	.getLocalizedMessage()));

								}
							};

							try {

								servicio
										.incOrderEventRules(
												Util
														.ordenarCondiciones(
																flujoSel
																		.getEvents(),
																(String[]) (indexFilasChecked
																		.toArray(new String[indexFilasChecked
																				.size()]))),
												callback);
							} catch (DesignerException e) {
								// TODO Auto-generated catch block
								Window
										.alert(DiagramBuilderExample.mensajeError
												.accionNoRealizada(100,
														"incOrderEventRules",
														e.getLocalizedMessage()));
							}
						}

					}
				});

				eliminar.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {
					
						if (indexFilasChecked.size() == 0) {
							Window.alert(DiagramBuilderExample.mensajes
									.seleccionObligatoria());
						} else {
							AsyncCallback callback = new AsyncCallback() {
								public void onSuccess(Object result) {

									try {
										if (flujoSel.getEvents().size() ==indexFilasChecked.size() ) {
											deleteLastEvent = true;
											Element eleCondFlow = DOM
													.getElementById(flujoSel
															.getIdC());
											if (eleCondFlow != null) {
												Element padreCond = DOM
														.getParent(eleCondFlow);
												DOM.removeChild(padreCond,
														eleCondFlow);
											
											}
										}
									
										Element disponibles= DOM.getElementById("bodyDisponibles");
										int numChild=DOM.getChildCount(disponibles);
										int cont=0;
										for(int i=numChild-1; i>=cont; i--)
										{
											Element hijo=DOM.getChild(disponibles, i);
											String id= DOM.getElementAttribute(hijo, "id");
											if(Html.contains(id, "check")){
												DOM.setElementAttribute(hijo, "id", "--");
												cont+=1;
											}
												
											else{
												DOM.removeChild(disponibles, hijo);
											}	
										}
										/*numChild=RootPanel.get().getWidgetCount();
									    boolean encontrado=false;
										for(int i=0 ; i<numChild && !encontrado; i++)
										{
											Widget auxi=RootPanel.get().getWidget(i);
											String id= DOM.getElementAttribute(auxi.getElement(), "id");
											if(Html.contains(id, "botoneraJAVA")){
												RootPanel.get().getWidget(i).removeFromParent();
												encontrado=true;
											
											}
											
										}
										
									    Element listadoReglas=DOM.getElementById("listadoReglas");
								        Html.removeAllChildren(listadoReglas);*/
										obtenerListadoEventos();
									} catch (DesignerException e) {
										// TODO Auto-generated catch block
										Window
												.alert(DiagramBuilderExample.mensajeError
														.accionNoRealizada(
																100,
																"obtenerListadoEventos",
																e
																		.getLocalizedMessage()));
									}
									indexFilasChecked.clear();

								}

								public void onFailure(Throwable caught) {
									Window
											.alert(DiagramBuilderExample.mensajeError
													.accionNoRealizada(
															100,
															"removeEventsRules",
															caught
																	.getLocalizedMessage()));

								}
							};
							try {
								servicio.removeEventsRules(indexFilasChecked,
										callback);
							} catch (DesignerException e) {

								Window
										.alert(DiagramBuilderExample.mensajeError
												.accionNoRealizada(100,
														"removeEventsRules",
														e.getLocalizedMessage()));
							}
						}
					}
				});

				bajar.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {

						if (indexFilasChecked.size() == 0) {
							Window.alert(DiagramBuilderExample.mensajes
									.seleccionObligatoria());
						} else {
							AsyncCallback callback = new AsyncCallback() {
								public void onSuccess(Object result) {

									try {

										Element disponibles= DOM.getElementById("bodyDisponibles");
										int numChild=DOM.getChildCount(disponibles);
										int cont=0;
										for(int i=numChild-1; i>=cont; i--)
										{
											Element hijo=DOM.getChild(disponibles, i);
											String id= DOM.getElementAttribute(hijo, "id");
											if(Html.contains(id, "check")){
												DOM.setElementAttribute(hijo, "id", "--");
												cont+=1;
											}
												
											else{
												DOM.removeChild(disponibles, hijo);
											}	
										}
										
										/*numChild=RootPanel.get().getWidgetCount();
										boolean encontrado=false;
										for(int i=0 ; i<numChild && !encontrado; i++)
										{
											Widget auxi=RootPanel.get().getWidget(i);
											String style= auxi.getStyleName();
											if(Html.contains(style, "botoneraJAVA")){
												RootPanel.get().getWidget(i).removeFromParent();
												encontrado=true;
											
											}
											
										}
										
									    Element listadoReglas=DOM.getElementById("listadoReglas");
								        Html.removeAllChildren(listadoReglas);*/
										obtenerListadoEventos();
									} catch (DesignerException e) {
										// TODO Auto-generated catch block
										Window
												.alert(DiagramBuilderExample.mensajeError
														.accionNoRealizada(
																100,
																"obtenerListadoEventos",
																e
																		.getLocalizedMessage()));
									}
									indexFilasChecked.clear();

								}

								public void onFailure(Throwable caught) {
									Window
											.alert(DiagramBuilderExample.mensajeError
													.accionNoRealizada(
															100,
															"decOrderEventRules",
															caught
																	.getLocalizedMessage()));

								}
							};

							try {
							
								servicio.decOrderEventRules(Util.ordenarCondiciones(flujoSel
																		.getEvents(),
																(String[]) (indexFilasChecked
																		.toArray(new String[indexFilasChecked
																				.size()]))),
												callback);
							} catch (DesignerException e) {
							
								Window
										.alert(DiagramBuilderExample.mensajeError
												.accionNoRealizada(100,
														"decOrderEventRules",
														e.getLocalizedMessage()));
							}
						}

					}
				});

				add.addClickListener(new ClickListener() {
					public void onClick(Widget sender) {
						AsyncCallback callback = new AsyncCallback() {
							public void onSuccess(Object result) {

								// Lista de reglas obtenidas
								List reglasDisponibles = (List) result;
								if (reglasDisponibles.size() == 0) {
									AsyncCallback callback = new AsyncCallback() {

										public void onFailure(Throwable caught) {

											Window
													.alert(DiagramBuilderExample.mensajeError
															.accionNoRealizada(
																	100,
																	"getEvents",
																	caught
																			.getLocalizedMessage()));

										}

										public void onSuccess(Object result) {
											// TODO Auto-generated method stub
											Map eventos = (Map) result;
											if (eventos.isEmpty()) {
												Window
														.alert(DiagramBuilderExample.mensajeError
																.noExisteEvento(200));
											}
											Iterator itr = eventos.keySet()
													.iterator();
											codeEvento = Integer.parseInt(itr
													.next().toString());
										}
									};

									try {
										servicio.getEvents(
												varDefs.EVENT_OBJ_FLOW,
												callback);
									} catch (DesignerException e) {
										// TODO Auto-generated catch block
										Window
												.alert(DiagramBuilderExample.mensajeError
														.accionNoRealizada(
																100,
																"getEvents",
																e
																		.getLocalizedMessage()));
									}

								}
								try {
									construirWindowAddEventoConditionType(reglasDisponibles);
								} catch (DesignerException e) {
									// TODO Auto-generated catch block
									Window
											.alert(DiagramBuilderExample.mensajeError
													.accionNoRealizada(
															100,
															"construirWindowAddEventoConditionType",
															e
																	.getLocalizedMessage()));
								}

							}

							public void onFailure(Throwable caught) {

								Window
										.alert(DiagramBuilderExample.mensajeError
												.accionNoRealizada(
														100,
														"getAvailableCTRules",
														caught
																.getLocalizedMessage()));

							}
						};

						try {
						
							
							servicio.getAvailableCTRules(
									varDefs.EVENT_OBJ_FLOW, flujoSel.getId(),
									codeEvento, callback);
							// servicio.getEvents(varDefs.EVENT_OBJ_FLOW,
							// callback);
						} catch (DesignerException e) {
							// TODO Auto-generated catch block
							Window.alert(DiagramBuilderExample.mensajeError
									.accionNoRealizada(100, "getEvents", e
											.getLocalizedMessage()));
						}

					}
				});

				DOM.appendChild(e, botonera.getElement());
			}
		}
		AbstractConnectionsExample.vez=1;
	}

	public void eliminarLb() {

	}

	public static void deleteElements() {
	}

	public static List hijosABorrar = new ArrayList();

	public static void closeEventConditionType() throws DesignerException {
		// Mostrar la ventana de la lista de cond -eventos , para ello primero
		// obtenemos los eventos y
		// sus condiciones
		 String id="";
       if(!sinCondicion){
    	   try{
    		   int numChild = RootPanel.get().getWidgetCount();
    		   int cont=0;
    		   while(numChild!=cont )
    		   {
    			   Widget aux= RootPanel.get().getWidget(cont);
    			  id=DOM.getElementAttribute(aux.getElement(), "id");
    			   if(id==null || (varDefs.divDiagrama.compareTo(id)!=0 &&
    					   varDefs.divListado.compareTo(id)!=0 &&
    					   varDefs.divSubProceso.compareTo(id)!=0 &&
    					   varDefs.divTramites.compareTo(id)!=0))
    			   {
    				   
    				if(DOM.getParent(aux.getElement())!=null)
    					 RootPanel.get().getWidget(cont).removeFromParent();
    				 else{
    					 cont+=1;
    				 }
    		 		
    	    }
    	else{
    		cont+=1;
    	}
    	
    	numChild = RootPanel.get().getWidgetCount();
    }

    	   }catch(Exception t) 
    	   {
    		
    		  
    		   System.out.println("Error"+ t);
    	   };}
		if (hayCondicion)
			getCTRules();
		else {
			Element tramites = DOM.getElementById(varDefs.divTramites);
			Element listado = DOM.getElementById(varDefs.divListado);
			Html.removeAllChildren(listado);
			Designer.listadoOculto();
			Designer.tramitesOculto();
			
		
			DOM.setStyleAttribute(tramites, "display", "none");
		}

		indexFilasChecked.clear();
		indexRulesChecked.clear();
		AbstractConnectionsExample.vez = 0;

	}

	public static void addIconHelp(Element div) {

		Label lhelp = new Label();
		lhelp.setWidth("100%");
		
		
		lhelp.addStyleName("nodo-sinc");

		HTML help = new HTML();
		help.addStyleName("nodo-sinc");
		help.addStyleName("help");

		help
				.setHTML("<img class=\"help\" src=\"./img/help.gif\" class=\"seleccionable\" onclick=\"window.parent.showHelp();\"  style=\"align:right;\">"
						+ AbstractConnectionsExample.mensajes.help() + "</img>");
		DOM.setStyleAttribute(lhelp.getElement(), "ALIGN", "RIGHT");

		DOM.appendChild(lhelp.getElement(), help.getElement());

		DOM.appendChild(div, lhelp.getElement());

	}

	public static void construirWindowAddEventoConditionType(
			List reglasDisponibles) throws DesignerException {

		// Ocultar la ventana que tiene el listado de eventos asociados
		Designer.tramitesOculto();

		Element tramites = DOM.getElementById(varDefs.divTramites);
		if(!sinCondicion){
			try{
		//RootPanel.get().clear();
		int numChild = RootPanel.get().getWidgetCount();
		/*for(int i=numChild-1; i>=0; i--)
			   RootPanel.get().remove(i);*/
		int cont=0;
	    while(numChild!=cont && DiagramBuilderExample.vez==0)
	    {
	    	Widget aux= RootPanel.get().getWidget(cont);
	    	String id=DOM.getElementAttribute(aux.getElement(), "id");
	    	if(id==null || (varDefs.divDiagrama.compareTo(id)!=0 &&
	    			varDefs.divListado.compareTo(id)!=0 &&
	    			varDefs.divSubProceso.compareTo(id)!=0 &&
	    			varDefs.divTramites.compareTo(id)!=0))
	    	{
	    		RootPanel.get().getWidget(cont).removeFromParent();
	    		//DOM.removeChild(((Widget)RootPanel.get().getWidget(cont).getParent()).getElement(), ((Widget)RootPanel.get().getWidget(cont)).getElement());
	    		
	    		numChild = RootPanel.get().getWidgetCount();
	    	}
	    	else{
	    		cont+=1;
	    	}	
	    }

	      }catch(Exception t) {
	    	  
	    	  System.out.println("error"+ t);
	    	  
	      };
		
		}
		Html.removeAllChildren(tramites);

		Element divListadoEventos = DOM.getElementById(varDefs.divListado);
		Designer.listadoVisible();

		Element divAzul = DOM.createDiv();
		DOM.setElementAttribute(divAzul, "id", "divAzul");
		DOM.setElementAttribute(divAzul, "align", "right");
		if (DiagramBuilderExample.imgTram == null) {
			DiagramBuilderExample.imgTram = new Image();

			DiagramBuilderExample.imgTram.setUrl(".//img//close1.png");
		}

		DOM.sinkEvents(DiagramBuilderExample.imgTram.getElement(),
				Event.ONCLICK);

		DOM.setEventListener(DiagramBuilderExample.imgTram.getElement(),
				new EventListener() {
					public void onBrowserEvent(Event event) {

						// Destruye esta ventana y muestra la anterior
						// actualziando la regla o condicion añadida
						try {
							
							DiagramBuilderExample.vez=0;
							hayCondicion = false;
							closeEventConditionType();

						} catch (DesignerException e) {
							// TODO Auto-generated catch block
							Window.alert(DiagramBuilderExample.mensajeError
									.accionNoRealizada(100,
											"closeEventConditionType", e
													.getLocalizedMessage()));
						}

					}
				});

		DiagramBuilderExample.imgTram.setVisible(true);
		DOM.appendChild(divAzul, DiagramBuilderExample.imgTram.getElement());
		DOM.appendChild(divListadoEventos, divAzul);

		Element e = DOM.createDiv();
		DOM.setElementAttribute(e, "id", "divModal");
		DOM.setStyleAttribute(e, "width", "960");

		DOM.appendChild(divListadoEventos, e);

		Element divListadoCondiciones = construirWindowCond(false);

		DOM.setStyleAttribute(divListadoCondiciones, "display", "none");
		Element divListadoReglas = DOM.createDiv();

		DOM.setElementAttribute(divListadoReglas, "id", "listadoReglas");
		Element cabecera = DOM.createDiv();
		DOM.setInnerText(cabecera, DiagramBuilderExample.mensajes
				.addCondition());
		DOM.setElementAttribute(cabecera, "id", "tituloTramites");
		Element selTipoEvento = DOM.createDiv();
		DiagramBuilderExample.addBr(e);
		DOM.appendChild(e, cabecera);
		DOM.appendChild(e, selTipoEvento);
		DiagramBuilderExample.addBr(e);
		DOM.appendChild(e, divListadoReglas);
		DOM.appendChild(e, divListadoCondiciones);

		HorizontalPanel panelTipo = new HorizontalPanel();
		panelTipo.setVerticalAlignment(panelTipo.ALIGN_MIDDLE);
		panelTipo.setHorizontalAlignment(panelTipo.ALIGN_CENTER);

		// DiagramBuilderExample.addBr(selTipoEvento);
		Label label2 = new Label();
		label2.setText(DiagramBuilderExample.mensajes.tipoCondicion() + " ");
		panelTipo.add(label2);
		// Construyo la seleccion del tipo de objeto

		ListBox lb = new ListBox();
		DOM.setInnerText(lb.getElement(), DiagramBuilderExample.mensajes
				.addCondition());
		RootPanel.get().add(lb);
		lb.addItem(DiagramBuilderExample.mensajes.sql());
		lb.addItem(DiagramBuilderExample.mensajes.java());
		lb.setSelectedIndex(1);
		lb.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {

				ListBox lb = (ListBox) sender;
				if (lb.getValue(lb.getSelectedIndex()).compareTo(
						DiagramBuilderExample.mensajes.sql()) == 0) {

					Element divListadoCondiciones = DOM
							.getElementById("divCondiciones");
					DOM.setStyleAttribute(divListadoCondiciones, "display",
							"block");
					Element divListadoReglas = DOM
							.getElementById("listadoReglas");
					DOM.setStyleAttribute(divListadoReglas, "display", "none");

				} else {

					Element divListadoCondiciones = DOM
							.getElementById("divCondiciones");
					DOM.setStyleAttribute(divListadoCondiciones, "display",
							"none");
					Element divListadoReglas = DOM
							.getElementById("listadoReglas");
					DOM.setStyleAttribute(divListadoReglas, "display", "block");
				}

			}

		});

		DOM.appendChild(selTipoEvento, lb.getElement());
		panelTipo.add(lb);
		DiagramBuilderExample.addBr(selTipoEvento);
		RootPanel.get().add(panelTipo);
		DOM.appendChild(selTipoEvento, panelTipo.getElement());

		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();

		DOM.setElementAttribute(table1, "cellpadding", "5");
		DOM.setElementAttribute(body1, "cellpadding", "6");
		DOM.setElementAttribute(table1, "id", "disponibles");
		DOM.setElementAttribute(table1, "id", "bodyDisponiblesReglas");

		DOM.setElementAttribute(tr1, "id", "cabeceraTabla");

		td1 = DOM.createTD();
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, "Id");
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.reglas());
		DOM.appendChild(tr1, td1);

		DOM.appendChild(body1, tr1);

		// Construyo el div con el listado de reglas recibido
		for (int i = 0; i < reglasDisponibles.size(); i++) {

			Regla regla = (Regla) reglasDisponibles.get(i);
			td1 = DOM.createTD();
			tr1 = DOM.createTR();
			CheckBox cb = new CheckBox();
			DOM.setElementAttribute(cb.getElement(), "id", "" + regla.getId());

			RootPanel.get().add(cb);
			DOM.appendChild(td1, cb.getElement());
			cb.addClickListener(new ClickListener() {

				public void onClick(Widget sender) {

					checked = ((CheckBox) sender).isChecked();
					if (checked) {

						Element cb = sender.getElement();
						int id = DOM.getElementPropertyInt(cb, "id");
						// indexFilasChecked.contains(o)
						if (checked) {

							if (!indexRulesChecked.contains(new Integer(id)))
								indexRulesChecked.add(new Integer(id));
						} else if (indexRulesChecked.contains(new Integer(id)))
							indexRulesChecked.remove(new Integer(id));
					}

				}

			});

			DOM.appendChild(tr1, td1);

			// iD
			td1 = DOM.createTD();
			DOM.setInnerText(td1, "" + regla.getId());
			DOM.appendChild(tr1, td1);
			// nOMBRE
			td1 = DOM.createTD();
			DOM.appendChild(tr1, td1);
			DOM.setInnerText(td1, regla.getNombre());
			DOM.appendChild(body1, tr1);

		}

		DOM.appendChild(table1, body1);
		Element tituloReglas = DOM.createDiv();
		DOM.setInnerText(tituloReglas, DiagramBuilderExample.mensajes
				.listRules());
		DOM.setElementAttribute(tituloReglas, "id", "tituloTramites");
		DOM.appendChild(divListadoReglas, tituloReglas);
		DiagramBuilderExample.addBr(divListadoReglas);

		Button aceptar = new Button(DiagramBuilderExample.mensajes
				.buttonAccept());
		Button cancelar = new Button(DiagramBuilderExample.mensajes
				.buttonCancel());
       
		
		HorizontalPanel botonera = new HorizontalPanel();
		botonera.setVerticalAlignment(botonera.ALIGN_MIDDLE);
		botonera.setHorizontalAlignment(botonera.ALIGN_CENTER);
		botonera.add(aceptar);
		botonera.add(cancelar);
		botonera.addStyleName("botonera");
		botonera.addStyleName("botoneraJava");
	
		RootPanel.get().add(botonera);
		DOM.appendChild(divListadoReglas, botonera.getElement());
		DiagramBuilderExample.addBr(divListadoReglas);
		DOM.appendChild(divListadoReglas, table1);
		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
			
				if (indexRulesChecked.size() == 0)
					Window.alert(DiagramBuilderExample.mensajes
							.seleccionObligatoria());
				else
					try {
					
						addRulesEvent();
						

					} catch (DesignerException e) {
						
						Window.alert(DiagramBuilderExample.mensajeError
								.accionNoRealizada(100, "addRulesEvent", e
										.getLocalizedMessage()));
					}

			}
		});

		cancelar.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {

				try {
					
					closeEventConditionType();
				} catch (DesignerException e) {

					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100, "closeEventConditionType",
									e.getLocalizedMessage()));
				}

			}

		});

	}

	protected static void addRulesEvent() throws DesignerException {
			
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				indexRulesChecked.clear();
				try {
				
					closeEventConditionType();
					
					if (!hayCondicion ) {
						
						hayCondicion = true;
						sinCondicion=false;
						HTML imgC = DiagramBuilderExample
								.addImgCondicion(flujoSel);
						if(imgC!=null){
						Element area = DOM.getElementById("area");
						DOM.appendChild(area, imgC.getElement());}
						Designer.update();
					}
					
				} catch (DesignerException e) {
					
					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100, "closeEventConditionType",
									e.getLocalizedMessage()));
				}

			}

			public void onFailure(Throwable caught) {
                 
				Window.alert(DiagramBuilderExample.mensajeError
						.accionNoRealizada(100, "addRulesEvent", caught
								.getLocalizedMessage()));

			}
		};

		
		servicio.addEventRule(varDefs.EVENT_OBJ_FLOW, flujoSel.getId(),
				codeEvento, indexRulesChecked, null, callback);

	}

	protected static void obtenerListadoEntidades() throws DesignerException {

		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				entidades = new HashMap();
				entidades = (Map) result;
				try {
					for (int i = 0; i < hijosABorrar.size(); i++)
						try {
							((Widget) hijosABorrar.get(i)).removeFromParent();
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block

						}
						hijosABorrar.clear();
					construirListadoCondiciones();
				} catch (DesignerException e) {
					// TODO Auto-generated catch block
					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100,

							"construirListadoCondiciones", e
									.getLocalizedMessage()));
				}
			}

			public void onFailure(Throwable caught) {

				Window.alert(DiagramBuilderExample.mensajeError
						.accionNoRealizada(100, "getListEntities", caught
								.getLocalizedMessage()));

			}
		};

		servicio.getEntities(DiagramBuilderExample.procedimiento.getIdProc(),
				callback);

	}

	/**
	 * Construye la ventana modal con la información de la condición
	 */
	
	static int childCount=-1;
	public static Element construirWindowCond(boolean esWindow)
			throws DesignerException {

	tipoCampoFila.clear();
		boolean nuevo = true;
		if (condsSel != null)
			nuevo = false;

		Condition condicion = condsSel;

		// CONSTRUYO EL DIV CON FONDO AZULADO Y la imagen de cerrar
		Element e = DOM.createDiv();
		DOM.setElementAttribute(e, "id", "divCondiciones");

		if (esWindow) {

			Designer.tramitesVisible();
			Element tramites = DOM.getElementById(varDefs.divTramites);
			Designer.listadoVisible();
			if(!sinCondicion){
				try{
					int numChild = RootPanel.get().getWidgetCount();
				    /*for(int i=numChild-1; i>=0; i--)
				    	RootPanel.get().remove(i);*/
					int cont=0;
					
					
				    while(numChild!=cont && AbstractConnectionsExample.vez==0)
				    {
				    	
				    	Widget aux= RootPanel.get().getWidget(cont);
				    	String id=DOM.getElementAttribute(aux.getElement(), "id");
				    	if(id==null || (varDefs.divDiagrama.compareTo(id)!=0 &&
				    			varDefs.divListado.compareTo(id)!=0 &&
				    			varDefs.divSubProceso.compareTo(id)!=0 &&
				    			varDefs.divTramites.compareTo(id)!=0))
				    	{
				    		RootPanel.get().getWidget(cont).removeFromParent();
				    		//DOM.removeChild(((Widget)RootPanel.get().getWidget(cont).getParent()).getElement(), ((Widget)RootPanel.get().getWidget(cont)).getElement());
				    		
				    		numChild = RootPanel.get().getWidgetCount();
				    	}
				    	else{
				    		cont+=1;
				    	}
				    	
				    }

				     }catch(Exception t) {};
			}
			Html.removeAllChildren(tramites);

			
			Element divListado = DOM.getElementById(varDefs.divListado);
			Element divAzul = DOM.createDiv();
			DOM.setElementAttribute(divAzul, "id", "divAzul");
			DOM.setElementAttribute(divAzul, "align", "right");
			if (DiagramBuilderExample.imgTram == null) {
				DiagramBuilderExample.imgTram = new Image();

				DiagramBuilderExample.imgTram.setUrl(".//img//close1.png");
			}

			DOM.sinkEvents(DiagramBuilderExample.imgTram.getElement(),
					Event.ONCLICK);

			DOM.setEventListener(DiagramBuilderExample.imgTram.getElement(),
					new EventListener() {
						public void onBrowserEvent(Event event) {

							try {
								
								DiagramBuilderExample.vez=0;
								hayCondicion = false;
								closeEventConditionType();
								

							} catch (DesignerException e) {
								
								Window.alert(DiagramBuilderExample.mensajeError
										.accionNoRealizada(100,

										"closeEventConditionType", e
												.getLocalizedMessage()));
							}

						}
					});

			DiagramBuilderExample.imgTram.setVisible(true);
			DOM
					.appendChild(divAzul, DiagramBuilderExample.imgTram
							.getElement());
			DOM.appendChild(divListado, divAzul);

			DOM.setElementAttribute(e, "id", "divModal");
			DOM.setStyleAttribute(e, "width", "960px");

			DOM.appendChild(divListado, e);
		}

		// Contenido de lapagina

		Element div = DOM.createDiv();

		DOM.setElementAttribute(div, "id", "divCondicion");
		// DiagramBuilderExample.addBr(div);
		VerticalPanel vp = new VerticalPanel();
		vp.setVerticalAlignment(vp.ALIGN_MIDDLE);
		vp.setHorizontalAlignment(vp.ALIGN_LEFT);

		vp.setWidth("100%");
		addIconHelp(div);

		Label titulo = new Label();
		DOM.setElementAttribute(titulo.getElement(), "id", "tituloTramites");

		titulo.setText(DiagramBuilderExample.mensajes.condition());

		DOM.appendChild(div, titulo.getElement());
		DiagramBuilderExample.addBr(div);
		HorizontalPanel botonera1 = new HorizontalPanel();
		botonera1.setVerticalAlignment(botonera1.ALIGN_MIDDLE);
		botonera1.setHorizontalAlignment(botonera1.ALIGN_CENTER);
		
		HorizontalPanel botonera2 = new HorizontalPanel();
		botonera2.setVerticalAlignment(botonera2.ALIGN_MIDDLE);
		botonera2.setHorizontalAlignment(botonera2.ALIGN_CENTER);

		
		Button aceptar = new Button(DiagramBuilderExample.mensajes
				.buttonAccept());
		Button cancelar = new Button(DiagramBuilderExample.mensajes
				.buttonCancel());
		Button eliminar = new Button(DiagramBuilderExample.mensajes
				.buttonDelete());
		Button add = new Button(DiagramBuilderExample.mensajes.buttonAdd());
		Button insert = new Button(DiagramBuilderExample.mensajes
				.buttonInsertar());
		
		
		botonera1.add(aceptar);
		
		
		if (!AbstractConnectionsExample.readOnly){
			botonera2.add(eliminar);

		    botonera2.add(add);
		   botonera2.add(insert);
	       botonera1.add(cancelar);
		}
		
		else {

			aceptar.setText(DiagramReadOnly.mensajes.buttonReturn());
		}

		RootPanel.get().add(botonera1);
		DOM.appendChild(div, botonera1.getElement());
		
		DiagramBuilderExample.addBr(div);

		Element divNombre = DOM.createDiv();
		DOM.setElementAttribute(divNombre, "id", "punteado");
		Element tableNombre = DOM.createTable();
		DOM.setElementAttribute(tableNombre, "id", "tablaNombreCond");

		Element tbody = DOM.createTBody();

		Element tr = DOM.createTR();
		Element td = DOM.createTD();
		DOM.setInnerText(td, DiagramBuilderExample.mensajes.nombreCondicion()
				+ " :");
		DOM.setElementAttribute(td, "id", "literalModal");
		DOM.appendChild(tr, td);

		td = DOM.createTD();
		TextBox textBox = new TextBox();
		textBox.setText("");
		RootPanel.get().add(textBox);
		DOM.setElementAttribute(textBox.getElement(), "id", "nombreCond");
		if (!nuevo)
			textBox.setText(condsSel.getNombre());
		DOM.appendChild(td, textBox.getElement());

		DOM.appendChild(tr, td);

		DOM.appendChild(tbody, tr);
		DOM.appendChild(tableNombre, tbody);
		DOM.appendChild(divNombre, tableNombre);

		DOM.appendChild(div, divNombre);

		Element detalle = DOM.createDiv();
		DOM.setElementAttribute(detalle, "id", "tituloNivel2");
		DOM.setInnerText(detalle, DiagramBuilderExample.mensajes
				.detalleCondicion());
		DiagramBuilderExample.addBr(div);
		DOM.appendChild(div, detalle);

		Element tr1 = DOM.createTR();
		Element td1 = DOM.createTD();
		Element body1 = DOM.createTBody();
		Element table1 = DOM.createTable();
		DOM.appendChild(table1, body1);
		DOM.appendChild(div, table1);
		DOM.setElementAttribute(table1, "cellpadding", "5");
		DOM.setElementAttribute(body1, "cellpadding", "6");
		DOM.setElementAttribute(table1, "id", "disponibles");
	
		DOM.setElementAttribute(table1, "id", "bodyDisponiblesCondi");

		DOM.setElementAttribute(tr1, "id", "cabeceraTablaCentrada");

		td1 = DOM.createTD();
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.andOr());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, "(");
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.op1());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.operando());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.op2());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, DiagramBuilderExample.mensajes.op3());
		DOM.appendChild(tr1, td1);

		td1 = DOM.createTD();
		DOM.setInnerText(td1, ")");
		DOM.appendChild(tr1, td1);

		DOM.appendChild(body1, tr1);

		// Recorro la lista de condiciones
		if (!nuevo) {

			condicion = condsSel;
			for (int i = 0; i < condsSel.getCondicionesSimples().size(); i++) {
				tipoCampoFila.add(new Integer(-1));
				ConditionSimple c = (ConditionSimple) condsSel
						.getCondicionesSimples().get(i);
				tr1 = DOM.createTR();

				tr1 = construirFila(i, c, nuevo);

				DOM.setElementAttribute(tr1, "id", i + "");
				
				DOM.appendChild(body1, tr1);
				reordenarIdFila(tr1);
			}
			if(childCount==-1)
			childCount=condsSel.getCondicionesSimples().size()+1;
		}

		// Construimos una unica fila
		else {
			if(childCount==-1)
			childCount=2;
			tipoCampoFila.add( new Integer(-1));
			ConditionSimple c = new ConditionSimple();
			tr1 = DOM.createTR();
			tr1 = construirFila(childCount, c, nuevo);

			DOM.setElementAttribute(tr1, "id", 0 + "");
			DOM.appendChild(body1, tr1);
			childCount+=1;
			reordenarIdFila(tr1);

		}

		DiagramBuilderExample.addBr(div);
		DiagramBuilderExample.addBr(div);

		// Construyo la botonera
		
		if (!AbstractConnectionsExample.readOnly) {
			

			
			//DOM.setElementAttribute(botonera.getElement(), "id", "botonera");

			//RootPanel.get().add(botonera);

			eliminar.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					if (indexFilasChecked.size() == 0)
						Window.alert(DiagramBuilderExample.mensajes
								.seleccionObligatoria());
					else {

						// Borra todas las lineas que tenga seleccionadas
						deleteLineas();
					}

				}
			});
			cancelar.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {

					try {
						hayCondicion = true;
						closeEventConditionType();
						
					} catch (DesignerException e) {
						// TODO Auto-generated catch block
						Window.alert(DiagramBuilderExample.mensajeError
								.accionNoRealizada(100,

								"closeEventConditionType", e
										.getLocalizedMessage()));
					}

				}
			});

			add.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					addLinea(-1);

				}
			});

			insert.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					// Comprobar que ha seleccionado una fila donde quiere
					// seleccionarlo, si hay dos se inserta en el primero que
					// esta seleccionado

					if (indexFilasChecked.size() == 0)
						Window.alert(DiagramBuilderExample.mensajes
								.seleccionObligatoria());
					else {
						Integer id = new Integer(indexFilasChecked.get(0)
								.toString());
						addLinea(id.intValue());
					}

				}
			});

			RootPanel.get().add(botonera2);
			DOM.appendChild(div, botonera2.getElement());

		}

		aceptar.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				hayCondicion = true;

				
				
				try {
				
					if (AbstractConnectionsExample.isReadOnly())

						closeEventConditionType();

					else
						saveCondition();
				} catch (DesignerException e) {
					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100,

							"save Condition", e.getLocalizedMessage()));
				}

			}
		});

		DOM.appendChild(e, div);
		return e;
	}

	public static int idFila(Element e, int veces) {
		if (e != null) {
			Element padre = null;
			padre = e;
			for (int i = 0; i < veces && padre != null; i++)
				padre = DOM.getParent(padre);

			return DOM.getElementPropertyInt(padre, "id");
		} else
			return -1;

	}

	/**
	 * Recoge los valores del formulario y llama al metodo del servlet para que
	 * guarde la información
	 * 
	 * @throws DesignerException
	 */
	public static void saveCondition() throws DesignerException {

		boolean nuevo = true;
		if (condsSel != null)
			nuevo = false;
		else
			condsSel = new Condition();

		if (condsSel.getNombre() == null
				|| condsSel.getNombre().compareTo("") == 0)
			DiagramBuilderExample.mensajes
					.campoObligatorio(DiagramBuilderExample.mensajes
							.nombreCondicion());

		// Recorremos el listado de las condiciones Simples
		Element table = DOM.getElementById("bodyDisponiblesCondi");
		Element tbody = DOM.getChild(table, 0);

		condsSel.setCondicionesSimples(new ArrayList());
		int numChild = DOM.getChildCount(tbody);
		List listCondSimple = new ArrayList();
		for (int i = 1; i < numChild; i++) {
			Element fila_actual=DOM.getElementById((i-1)+"");
			String visible="";
			if(fila_actual!=null)
			visible= DOM.getStyleAttribute(fila_actual, "display");
		
			
	     if(!Html.contains(visible, "none")){	
			
			
			ConditionSimple cs = new ConditionSimple();
			Operando op2 = cs.getOp2();
			Element valor = DOM.getElementById((i - 1) + "_OP2Valor");

			if (valor != null) {
				String texto = DOM.getElementAttribute(valor, "value");
				op2.setValor(texto);
			}

			Operando op3 = cs.getOp3();
			valor = DOM.getElementById((i - 1) + "_OP3Valor");
			if (valor != null) {
				String texto = DOM.getElementAttribute(valor, "value");
				op3.setValor(texto);
			}
			cs.setOp2(op2);
			cs.setOp3(op3);
			listCondSimple.add(cs);
		}
		}
		int numChild2 = RootPanel.get().getWidgetCount();
		// boolean [] valorEntidad= new boolean
		// [RootPanel.get().getWidgetCount()];
		for (int i = 0; i < numChild2; i++) {
			Widget hijo = RootPanel.get().getWidget(i);
			Element tr, td;
			int idFila2 = -1;
			
			tr = null;
			td = null;
			if (hijo != null) {
				td = DOM.getParent(hijo.getElement());
				if (td != null) {
					tr = DOM.getParent(td);
					if (tr != null) {
						String fila = DOM.getElementProperty(tr, "id");
						if (fila==null ||fila.compareTo("") == 0
								|| fila.compareTo("divModal") == 0  || fila.compareTo("divCondiciones")==0)
							idFila2 = -1;
						else

							idFila2 = Integer.parseInt(fila);
						String name = hijo.getStyleName();
						if (name.compareTo("gwt-TextBox") == 0) {

							TextBox txt = (TextBox) hijo;

							String nombreTxt = DOM.getElementAttribute(txt
									.getElement(), "id");

							if (nombreTxt != null
									&& nombreTxt.compareTo("nombreCond") == 0)
								condsSel.setNombre(txt.getText());
							else if (Html.contains(nombreTxt, "OP2")
									|| Html.contains(nombreTxt, "OP3")) {

								if (idFila2 == -1
										&& Html.contains(nombreTxt, "Valor"))
									idFila2 = idFila(txt.getElement(), 16);
								else if (idFila2 == -1)

									idFila2 = idFila(txt.getElement(), 7);

								ConditionSimple cs = new ConditionSimple();
								if (idFila2 < listCondSimple.size()) {
									cs = (ConditionSimple) listCondSimple
											.get(idFila2);

									if (Html.contains(nombreTxt, "OP2")) {

										Operando op2 = cs.getOp2();
										op2.setValor(txt.getText());
										cs.setOp2(op2);
									} else {
										Operando op3 = cs.getOp3();
										op3.setValor(txt.getText());
										cs.setOp3(op3);
									}
									listCondSimple.set(idFila2, cs);
								}
							}

						}

						if (name.compareTo("gwt-CheckBox") == 0) {

							CheckBox cb = (CheckBox) hijo;
							String id = DOM.getElementAttribute(hijo
									.getElement(), "id");
							if (id != null) {
								if (Html.contains(id, "OP")) {
									
									if (idFila2 == -1)
										idFila2 = idFila(cb.getElement(), 2);
								}

							}
						}

						if (Html.contains(name, "gwt-ListBox")) {

							String id = DOM.getElementAttribute(hijo
									.getElement(), "id");
							if (id != null) {
								if (id.compareTo("") != 0) {
								
									if (idFila2 == -1
											&& (Html.contains(id, "OP2") || Html
													.contains(id, "OP3"))) {
										// if(Html.contains(id, "Campo"))
										idFila2 = idFila(hijo.getElement(), 12);
										// else

										// idFila2=idFila(hijo.getElement(),8);
									} else if (idFila2 == -1
											&& (Html.contains(id, "OP1")))
										idFila2 = idFila(hijo.getElement(), 7);
									else if (idFila2 == -1)
										idFila2 = idFila(hijo.getElement(), 2);
									// idFila=Integer.parseInt(auxi[0]);

									String idObj = DOM.getElementAttribute(hijo
											.getElement(), "id");
									// Me dice si es un gwt-checkBox gwt-ListBox

									ConditionSimple cs = new ConditionSimple();

									if (listCondSimple.size() > idFila2) {
										cs = (ConditionSimple) listCondSimple
												.get(idFila2);
										Operando ope = new Operando();
										ListBox lb = ((ListBox) hijo);

										if (Html.contains(idObj, "AndOr"))
											cs.setAndOr(lb.getItemText(lb
													.getSelectedIndex()));
										else if (Html.contains(idObj, "Open")) {
											cs.setParentesisOpen(lb
													.getSelectedIndex());
										} else if (Html
												.contains(idObj, "Close")) {
											cs.setParentesisClose(lb
													.getSelectedIndex());
										} else if (Html.contains(idObj,
												"Operador")) {
											cs.setOperador(lb.getItemText(lb
													.getSelectedIndex()));
										}

										// Operando1
										else if (Html.contains(idObj,
												"OP1Entidad")) {
											ope = cs.getOp1();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {
												String nombreEntidad = lb
														.getValue(lb
																.getSelectedIndex());
												if (nombreEntidad
														.compareTo("-1") != 0)
													// ope.setIdEntidad(((Entidad)entidades.get(nombreEntidad)).getId());
													ope
															.setNombreEntidad(nombreEntidad);
											}
											cs.setOp1(ope);

										} else if (Html.contains(idObj,
												"OP1Campo")) {
											ope = cs.getOp1();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {

												ope
														.setNombreCampo(lb
																.getValue(lb
																		.getSelectedIndex()));

											}
											cs.setOp1(ope);
										}

										// Operando2
										else if (Html.contains(idObj,
												"OP2Entidad")) {
											ope = cs.getOp2();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {
												String nombreEntidad = lb
														.getValue(lb
																.getSelectedIndex());
												if (nombreEntidad
														.compareTo("-1") != 0) {
													// ope.setIdEntidad(((Entidad)entidades.get(nombreEntidad)).getId());
													ope
															.setNombreEntidad(nombreEntidad);
												}
											}
											cs.setOp2(ope);
										} else if (Html.contains(idObj,
												"OP2Campo")) {
											ope = cs.getOp2();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {
												ope
														.setNombreCampo(lb
																.getValue(lb
																		.getSelectedIndex()));
											}
											cs.setOp2(ope);
										}

										// Operando3
										else if (Html.contains(idObj,
												"OP3Entidad")) {
											ope = cs.getOp3();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {

												String nombreEntidad = lb
														.getValue(lb
																.getSelectedIndex());
												if (nombreEntidad
														.compareTo("-1") != 0
														&& entidades
																.containsKey(nombreEntidad)) {
													// ope.setIdEntidad(((Entidad)entidades.get(nombreEntidad)).getId());
													ope
															.setNombreEntidad(nombreEntidad);
												}
											}
											cs.setOp3(ope);
										} else if (Html.contains(idObj,
												"OP3Campo")) {
											ope = cs.getOp3();
											int ind = lb.getSelectedIndex();
											if (ind != -1) {
												ope
														.setNombreCampo(lb
																.getValue(lb
																		.getSelectedIndex()));
											}
											cs.setOp3(ope);

										}

										listCondSimple.set(idFila2, cs);
									}
								}
								}
							}
						}

					}
				
			}

			}

		condsSel.setCondicionesSimples(listCondSimple);

		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {

				try {
					closeEventConditionType();
					if (!hayCondicion || sinCondicion) {
						hayCondicion = true;
						sinCondicion=false;
						HTML imgC = DiagramBuilderExample
								.addImgCondicion(flujoSel);
						if(imgC!=null){
						Element area = DOM.getElementById("area");
						DOM.appendChild(area, imgC.getElement());}
						Designer.update();
					}
				
					
				} catch (DesignerException e) {
				
					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100, "closeEventConditionType",
									e.getLocalizedMessage()));
				}

			}

			public void onFailure(Throwable caught) {

				Window.alert(DiagramBuilderExample.mensajeError
						.accionNoRealizada(100, "saveCondition/addCondition",
								caught.getLocalizedMessage()));

			}
		};
		;
		Validates val = new Validates();
		if (val.validarCondicion(condsSel, (ArrayList) tipoCampoFila)) {

			if (nuevo) {

				servicio.addEventRule(varDefs.EVENT_OBJ_FLOW, flujoSel.getId(),
						codeEvento, null, condsSel, callback);

			} else {
			
				
				servicio.updateEventRuleCondition(idSel, condsSel, callback);

			}

		}
		if (nuevo)
			condsSel = null;

	}

	public static Widget getWidget(String id) {

		boolean encontrado = false;
		int numChild = RootPanel.get().getWidgetCount();
		for (int i = 0; i < numChild && !encontrado; i++) {
			Widget hijo = RootPanel.get().getWidget(i);
			String idH = DOM.getElementAttribute(hijo.getElement(), "id");
			if (idH != null && Html.contains(idH, id)
					&& hijo.getStyleName().compareTo("gwt-CheckBox") != 0) {
				encontrado = true;
				return hijo;
			}
		}

		return null;
	}

	public static void deleteRootPanel(String id) {

		String[] ids = id.split("_");

		if (ids.length == 0 || ids[0].compareTo("")==0)
			return;
		Widget aux = RootPanel.get(ids[0] + "_OP1Entidad");

		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP1Campo");

		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP2Entidad");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP2Campo");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP3Entidad");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP3Campo");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP3Valor");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_OP2Valor");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_AndOr");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_Open");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_Close");
		if (aux != null)
			RootPanel.get().remove(aux);

		aux = RootPanel.get(ids[0] + "_Operador");
		if (aux != null)
			RootPanel.get().remove(aux);

	}

	/**
	 * Se borran todas las lineas que sten chequeadas
	 */
	public static void deleteLineas() {
		List trAborrar = new ArrayList();
		List trAborra2r = new ArrayList();
		Element tabla = DOM.getElementById("bodyDisponiblesCondi");
		Element tbody = DOM.getChild(tabla, 0);
		int numFilas = DOM.getChildCount(tbody);
		for (int i = 0; i < indexFilasChecked.size(); i++) {
			Integer index = (Integer) indexFilasChecked.get(i);
			// Le sumamos uno xq la cabecera no cuenta
			int fila = index.intValue() + 1;
			Element tr1 = DOM.getChild(tbody, fila);
			trAborra2r.add(tr1);
			tipoCampoFila.remove(fila);
			Element trtd = DOM.getChild(tr1, 1);
			// Eliminar de rootPanel los widget que sean idFila_ que se
			// corresponda con el ide del hijo
			if (trtd != null){
				Element trtdhijo = DOM.getChild(trtd, 0);
				if(trtdhijo!=null)
				deleteRootPanel(DOM.getElementAttribute(trtdhijo, "id"));
			}

			// Decremento los posteriores en una unidad
			for (int j = fila + 1; j < numFilas; j++) {
				Element tr = DOM.getChild(tbody, j);
				int numFil = DOM.getElementPropertyInt(tr, "id");
				Element td = DOM.getChild(tr, 0);
				Element cb = DOM.getChild(td, 0);

				// cambiarId(numFil-1, numFil );
				DOM.setElementPropertyInt(cb, "id", numFil - 1);
				DOM.setElementPropertyInt(tr, "id", numFil - 1);

			}

			Element hijo = DOM.getChild(tbody, fila);
			trAborrar.add(hijo);

		}

		/*
		 * for(int i=0; i<trAborrar.size(); i++) { DOM.removeChild(tbody,
		 * (Element) trAborrar.get(i)); }
		 */

		for (int i = 0; i < trAborra2r.size(); i++) {
			//DOM.removeChild(tbody, (Element) trAborra2r.get(i));
			DOM.setStyleAttribute((Element) trAborra2r.get(i),"display", "none");
		}
		indexFilasChecked.clear();
		indexFilasChecked = new ArrayList();

	}

	public static List indexFilasChecked = new ArrayList();
	public static List indexRulesChecked = new ArrayList();

	/**
	 * 
	 * @param index
	 *            Posicion en la que voy a añadir la linea
	 */
	public static void addLinea(int index) {

		tipoCampoFila.add(new Integer(-1));
		Element table = DOM.getElementById("bodyDisponiblesCondi");
		Element tbody = DOM.getChild(table, 0);
		childCount +=1;

		// Construyo el td con toda la informacion
		Element tr1 = DOM.createTR();
		ConditionSimple c = new ConditionSimple();
		tr1 = construirFila(childCount - 1, c, true);
		
		Element td = DOM.getChild(tr1, 0);
		Element cb = DOM.getChild(td, 0);
		if (index == -1) {
			DOM.setElementAttribute(cb, "id", "" + (childCount - 1));
			DOM.setElementAttribute(tr1, "id", "" + (childCount - 1));
			DOM.appendChild(tbody, tr1);
		} else {
			DOM.setElementAttribute(cb, "id", "" + index);
			DOM.setElementAttribute(tr1, "id", "" + index);
			index = index + 1; // Sumo uno por la cabecera
			List elementos = new ArrayList();
			int childCount2= DOM.getChildCount(tbody);
			for (int i = childCount2 - 1; i >= index; i--) {

				cambiarId(i - 1, i);
				Element tr = DOM.getChild(tbody, i);
				// Incrementear en 1 el id
				td = DOM.getChild(tr, 0);
				cb = DOM.getChild(td, 0);
				// cambiarIdRootPanel(i, i-1);
				DOM.setElementAttribute(cb, "id", "" + i);
				DOM.setElementAttribute(tr, "id", "" + i);
				elementos.add(0, tr);
				DOM.removeChild(tbody, tr);

			}
			DOM.appendChild(tbody, tr1);
			for (int i = 0; i < elementos.size(); i++) {
				DOM.appendChild(tbody, (Element) elementos.get(i));
			}
		}
		reordenarIdFila(tr1);

		indexFilasChecked.clear();
		indexFilasChecked = new ArrayList();
	}

	/**
	 * 
	 * @param nuevo
	 *            Id nuevo
	 * @param anterior
	 *            Id anterior
	 */
	public static void cambiarId(int nuevo, int anterior) {

		if (idFila(DOM.getElementById(anterior + "_OP1Entidad"), 7) != anterior)
			return;
		Element aux = DOM.getElementById(anterior + "_OP1Entidad");

		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP1Entidad");

		aux = DOM.getElementById(anterior + "_OP1Campo");

		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP1Campo");

		aux = DOM.getElementById(anterior + "_OP2Entidad");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP2Entidad");

		aux = DOM.getElementById(anterior + "_OP2Campo");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP2Campo");

		aux = DOM.getElementById(anterior + "_OP3Entidad");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP3Entidad");
		aux = DOM.getElementById(anterior + "_OP3Campo");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP3Campo");

		aux = DOM.getElementById(anterior + "_OP3Valor");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP3Valor");

		aux = DOM.getElementById(anterior + "_OP2Valor");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_OP2Valor");

		aux = DOM.getElementById(anterior + "_AndOr");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_AndOr");
		aux = DOM.getElementById(anterior + "_Open");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_Open");
		aux = DOM.getElementById(anterior + "_Close");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_Close");
		aux = DOM.getElementById(anterior + "_Operador");
		if (aux != null)
			DOM.setElementAttribute(aux, "id", nuevo + "_Operador");

	}

	public static Element getFilaActual(int fila) {
		int numChild = RootPanel.get().getWidgetCount();

		for (int i = 0; i < numChild; i++) {
			Widget hijo = RootPanel.get().getWidget(i);
			String id = DOM.getElementAttribute(hijo.getElement(), "id");

			if (id != null && Html.contains(id, fila + "_And")) {
				String[] auxi = id.split("_");
				if (auxi.length == 2) {
					Element td = DOM.getParent(hijo.getElement());
					if(td!=null){
					Element tr = DOM.getParent(td);

					return tr;
					}
				}

			}

		}
		return null;

	}

	/**
	 * Elimina el flujo seleccionado del procedimiento que estamos tratando,
	 * esta operación solo se podrá realizar en el catalogo siendo el estado del
	 * procedimiento borrador
	 * 
	 * @param idProcedimiento
	 *            Identificador del procedimiento
	 */
	public static void eliminarFlujo(final int idProcedimiento) {
		if (imagenSel != null) {
			
			
			Element padre = DOM
					.getParent(RectilinearTwoEndedConnectionCustom.imagenSel);
			Element abuelo = DOM.getParent(padre);
			String id = DOM.getElementAttribute(padre, "id");
			DOM.setStyleAttribute(padre, "display", "none");
			//DOM.removeChild(abuelo, padre);
			DiagramBuilderExample.addConexionesBorrar(id);
			RectilinearTwoEndedConnectionCustom.imagenSel = null;
			// 3) Creamos un objeto asincrono para manejar el resultado
			AsyncCallback callback = new AsyncCallback() {
				public void onSuccess(Object result) {

				}

				public void onFailure(Throwable caught) {

					Window.alert(DiagramBuilderExample.mensajeError
							.accionNoRealizada(100, "removeFlow", caught
									.getLocalizedMessage()));

				}
			};

			DrawFlow flu = (DrawFlow) DiagramBuilderExample.flujos
					.get(new String(id));
			DiagramBuilderExample.flujos.remove(new String(id));
			Element eleCondFlow = DOM.getElementById(flu.getIdC());
			if (eleCondFlow != null) {
				Element padreCond = DOM.getParent(eleCondFlow);
				DOM.removeChild(padreCond, eleCondFlow);

			}
			Element elePFlow = DOM.getElementById(flu.getIdP());
			if (elePFlow != null) {
				Element padreCond = DOM.getParent(elePFlow);
				DOM.setStyleAttribute(elePFlow, "display", "none");
				//DOM.removeChild(padreCond, elePFlow);
			}

			servicio.removeFlow(idProcedimiento, flu.getId(), callback);
		}
		
	}

	/**
	 * 
	 * @param origen
	 *            Elemento html del que queremos obtener la informacion del
	 *            flujo
	 * @return
	 */
	public static DrawFlow getArrowSel(Element origen) {

		String id = DOM.getElementAttribute(origen, "id");

		if (id == null || id.compareTo("") == 0) {
			Element padre = DOM.getParent(origen);
			id = DOM.getElementAttribute(padre, "id");
		} else {

			id = "f" + id.substring(1);
		}

		DrawFlow flujo = (DrawFlow) AbstractConnectionsExample.flujos.get(id);

		return flujo;
	}

	public static Condition condsSel = null;
	public static int idFlujo = -1;
	public static DrawFlow flujoSel = new DrawFlow();

	/**
	 * Muestra el opo up según el evento seleccionado
	 * 
	 * @param event
	 */

	public static void showPopUp(Event event) {

		int clientX = DOM.eventGetClientX(event);
		int clientY = DOM.eventGetClientY(event);
		DrawFlow flujo = getArrowSel(DOM.eventGetCurrentTarget(event));
		idFlujo = flujo.getId();
		flujoSel = flujo;
		deleteLastEvent = false;

		if (AbstractConnectionsExample.isReadOnly()
				&& flujo.getEvents() != null && flujo.getEvents().size() > 0) {
			AbstractConnectionsExample.vez=0;
			popUpConectorTramitador.setVisible(true);
			popUpConectorTramitador.show();
			popUpConectorTramitador.setPopupPosition(clientX, clientY);

		}

		else if ((!AbstractConnectionsExample.isReadOnly())
				&& flujo.getEvents() != null && flujo.getEvents().size() > 0) {

			// condsSel=flujo.getCondition();

			hayCondicion = false;
			AbstractConnectionsExample.vez=0;
			popUpConectorCond.setVisible(true);
			
			popUpConectorCond.show();
			popUpConectorCond.setPopupPosition(clientX, clientY);

		} else if (!AbstractConnectionsExample.isReadOnly()) {

			popUpConectorSinCond.setVisible(true);
			AbstractConnectionsExample.vez=0;
			
			popUpConectorSinCond.show();
			popUpConectorSinCond.setPopupPosition(clientX, clientY);
		}

	}

	/**
	 * Calcula las nuevas posciones
	 */
	protected ConnectionDataCalculator createCalculator() {

		return new FullRectilinearTwoEndedCalculator();
	}

	/**
	 * 
	 * @param direccion
	 *            Dirección visible
	 * @param idPadre
	 *            Identificador del padre
	 * @param left
	 *            Posicion left
	 * @param top
	 *            Posicion top
	 */
	protected void setArrowVisible(int direccion, String idPadre, String left,
			String top) {

		/*
		 * <div id=pidFlow <img id=LEFT visible=none <img id=RIGHT
		 * visible=display <img id=UP visible=none; <img id=DOWN visible =
		 * display
		 */

		Element divp = null;
		if (idPadre!=null && idPadre.compareTo("") != 0) {
			String idP = "p" + idPadre.substring(1);

			divp = DOM.getElementById(idP);
		}

		if (divp == null)
			divp = DOM.getElementById(varDefs.pIdFlowTemp);
		if (divp == null){
			DrawFlow flu = (DrawFlow) DiagramBuilderExample.flujos
			.get(new String(idPadre));
			if(flu!=null)
			divp = AbstractConnectionsExample.createArrow(varDefs.pIdFlowTemp);
		}

		if (divp != null) {

			int numChild = DOM.getChildCount(divp);
			int id, i;
			for (i = 0; i < numChild; i++) {

				Element hijo = DOM.getChild(divp, i);
				id = DOM.getElementPropertyInt(hijo, "id");
				if (id == direccion) {

					DOM.setStyleAttribute(hijo, "display", "block");
				} else {
					
					DOM.setStyleAttribute(hijo, "display", "none");
				}
			}
			// Posicionamos el div
			DOM.setStyleAttribute(divp, "left", left);
			DOM.setStyleAttribute(divp, "top", top);
		}

		

	}

	public static Element imagen;
	public static Element imagenSel=null;

	/**
	 * Actualiza las conexiones tras haber realizado un movimiento
	 */
	protected void update(ConnectionData data) {
		if (data.getPoints().size() <= 1) {
			throw new IllegalArgumentException("gwt");
		}

		
		boolean down = false;
		prepareElements(data.getPoints().size() - 1);
		int mitad = data.getPoints().size() / 2 - 1;
		String leftC, topC;
		leftC = topC = "0px";
		Element padre = null;
		for (int i = 0; i < elements.size(); i++) {

			Element div = (Element) elements.get(i);

			padre = DOM.getParent(div);

			Point start = (Point) data.getPoints().get(i);
			if (i == 0)
				start = new Point(start.getLeft() - 10, start.getTop());

			Point end = (Point) data.getPoints().get(i + 1);

			String style = "gwt-diagrams-line";

			DOM.setElementAttribute(div, "style", "");

			DOM.setStyleAttribute(div, "height", "1");
			DOM.setStyleAttribute(div, "width", "1");

			imagen = DOM.createElement("img");

			if (start.left == end.left) {
				style += " gwt-diagrams-line-vertical";
				DOM.setStyleAttribute(div, "height", Integer.toString(Math
						.abs(start.top - end.top)

				));
				if (i == elements.size() - 1) {

					DOM.setStyleAttribute(div, "height", "20px");
					DOM.setStyleAttribute(div, "width", "10px");

					if (DOM.getChildCount(div) == 1) {
						Element e = DOM.getChild(div, 0);
						DOM.removeChild(div, e);
					}

					if (start.top > end.top) {
						style = "gwt-diagrams-line";
						leftC = Html.addPx(leftC, -15);
						String left = Integer.toString(Math.min(start.left,
								end.left) - 9);
						String top = Integer.toString(Math.min(start.top,
								end.top) + 2);
						// DOM.setElementProperty(imagen, "src", varDefs.fUp);
						setArrowVisible(varDefs.UP, DOM.getElementAttribute(
								padre, "id"), left, top);

					} else {
						// DOM.setElementProperty(imagen, "src", varDefs.fDown);
						topC = Html.addPx(topC, 10);
						style = "gwt-diagrams-line imagenConector";
						DOM.removeElementAttribute(div, "style");

						DOM.setStyleAttribute(div, "WIDTH", "" + 0);
						DOM.setStyleAttribute(div, "HEIGHT", "" + 0);
						String left = Integer.toString(Math.min(start.left,
								end.left) - 10);
						String top = Integer.toString(Math.min(start.top,
								end.top) - 18);
						down = true;
						setArrowVisible(varDefs.DOWN, DOM.getElementAttribute(
								padre, "id"),

						left, top);
					}

				}
			} else if (start.top == end.top) {
				DOM.setStyleAttribute(div, "width", Integer.toString(Math
						.abs(start.left -

						end.left)));
				style += " gwt-diagrams-line-horizontal";
				if (i == elements.size() - 1) {

					DOM.setStyleAttribute(div, "height", "20px");
					DOM.setStyleAttribute(div, "width", "10px");

					if (DOM.getChildCount(div) == 1) {
						Element e = DOM.getChild(div, 0);
						DOM.removeChild(div, e);
					}

					if (start.left > end.left) {
						style = "gwt-diagrams-line";
						topC = Html.addPx(topC, 10);
						
						String left = Integer.toString(Math.min(start.left,
								end.left) + 4);
						String top = Integer.toString(Math.min(start.top,
								end.top) - 8);
						setArrowVisible(varDefs.LEFT, DOM.getElementAttribute(
								padre, "id"),

						left, top);
					} else {
						leftC = Html.addPx(leftC, -15);
						style = "gwt-diagrams-line";
						// DOM.setElementProperty(imagen, "src",
						// varDefs.fRight);
						String left = Integer.toString(Math.min(start.left,

						end.left) - 15);
						String top = Integer.toString(Math.min(start.top,
								end.top)

						- 8);
						setArrowVisible(varDefs.RIGHT,

						DOM.getElementAttribute(padre, "id"), left, top);
					}

				

				}
			}

			DOM.setElementProperty(imagen, "className", "imagenConector");
			DOM.setElementProperty(div, "className", style);

			if (i != elements.size() - 1)

			{
				DOM.setStyleAttribute(div, "left", Integer.toString(Math.min(
						start.left, end.left)));
				DOM.setStyleAttribute(div, "top", Integer.toString(Math.min(
						start.top, end.top)));
			} else {
				DOM.setStyleAttribute(div, "height", "0px");
				DOM.setStyleAttribute(div, "widht", "0px");

			}

			if (i == mitad) {
				leftC = DOM.getStyleAttribute(div, "left");
				topC = DOM.getStyleAttribute(div, "top");
			}

			

		}

		// Actualizamos la condicion si existiera
		String idFlow = DOM.getElementAttribute(padre, "id");
		Element imgc = null;
		if (idFlow!=null && idFlow.compareTo("") != 0) {
			String idC = "c" + idFlow.substring(1);

			imgc = DOM.getElementById(idC);
			if (imgc == null)
				imgc = DOM.getElementById("idCTemp");
			if (imgc != null) {

				DOM.setStyleAttribute(imgc, "left", leftC);
				if (down)
					DOM.setStyleAttribute(imgc, "left", Html.addPx(leftC, -13));
				DOM.setStyleAttribute(imgc, "top", Html.addPx(topC, -5));

			}
		} else {

			HTML imageC = new HTML();
			imageC
					.setHTML("<img id=idCTemp src=\"./img/query.gif\"  style=\"position:absolute; top:"
							+ topC
							+ "; left:"
							+ leftC
							+ "; z-index:10000 \"></img>");

			Element area = DOM.getElementById("area");
			imgc = imageC.getElement();
			DOM.appendChild(area, imgc);

		}

	}

	/**
	 * Cambia el estilo segun si esta seleccionado, o no 
	 */
	public static void cambiarStilo() {

		Element padre = DOM.getParent(imagenSel);
		// Element abuelo= DOM.getParent(padre);
		String id = DOM.getElementProperty(padre, "id");

		//a todos los hijos le pondre el id como el estilo definido
		int numhijos = DOM.getChildCount(padre);
		for (int i = 0; i < numhijos; i++)
			DOM.removeElementAttribute(DOM.getChild(padre, i), "id");

	
		DOM.setElementAttribute(padre, "id", id);
	}

	/**
	 * 
	 * @param event Evento realizado
	 * @param e Elemento sobre el que ser realizo la accion
	 */
	public static void accionesEventosFlechas(Event event, Element e) {

		//Hay que mostrar el pop up y pasar el objeto que ha sido seleccionado
		if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {

			if (DiagramBuilderExample.procedimiento.getEstado() == varDefs.borrador
					|| AbstractConnectionsExample.readOnly) {
				//Element e= DOM.eventGetCurrentTarget(event);

				if (imagenSel != null && !AbstractConnectionsExample.readOnly) {
					cambiarStilo();
				}
				if (DiagramBuilderExample.seleccionado != null) {
					DiagramBuilderExample.seleccionado
							.removeStyleName(varDefs.seleccionado);
					DiagramBuilderExample.seleccionado =null;
				}
				
				if(e!=null){
				imagenSel = e;
				}
				
					

				showPopUp(event);
			}

		}

		if (!DiagramBuilderExample.conectar
				&& DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
				
			if (DiagramBuilderExample.procedimiento.getEstado() == varDefs.borrador) {

				//Element e= DOM.eventGetCurrentTarget(event);
				if (AbstractConnectionsExample.condSel != null) {
					String idCond = DOM.getElementProperty(
							AbstractConnectionsExample.condSel, "id");
					AbstractConnectionsExample.deseleccionarCondicion(idCond);
				}
				if (DiagramBuilderExample.seleccionado!=null )
					DiagramBuilderExample.seleccionado
							.removeStyleName(varDefs.seleccionado);
			
				
				DiagramBuilderExample.seleccionado = null;
				DiagramBuilderExample.sel = true;
				if (imagenSel != null)
					cambiarStilo();

				Element padre = DOM.getParent(e);
                String id=DOM.getElementAttribute(e, "id");
               
               
                if(id!=null && id.compareTo("")!=0){
				imagenSel = e;
                }
                else{ 
                	imagenSel=e;
                	cambiarStilo();	
                }
			  
				padre = DOM.getParent(e);

				//a todos los hijos le pondre el id como el estilo definido
				int numhijos = DOM.getChildCount(padre);
				for (int i = 0; i < numhijos; i++)
					DOM.setElementAttribute(DOM.getChild(padre, i), "id",

					varDefs.transicionSel);
			}

		}

		/*if (KeyboardListener.KEY_DELETE == DOM.eventGetKeyCode(event)) {

		}*/

	}

	/**
	 * 
	 * @param div Div que originó el evento
	 */
	public static void gestionEventos(Element div) {
		imagen = div;

		DOM.sinkEvents(imagen, Event.BUTTON_RIGHT);
		DOM.sinkEvents(imagen, Event.ONCLICK);
		DOM.sinkEvents(imagen, KeyboardListener.KEY_DELETE);

		DOM.setEventListener(imagen, new EventListener() {
			public void onBrowserEvent(Event event) {

				accionesEventosFlechas(event, DOM.eventGetCurrentTarget(event));

			}
		});

	}

	private void prepareElements(int count) {

		// New lines if too few
		for (int i = elements.size(); i < count; i++) {
			Element div = DOM.createDiv();
			gestionEventos(div);
			elements.add(div);
			DOM.appendChild(getElement(), div);
			DOM.setInnerHTML(div, "&nbsp;");
		}

		// Remove some lines if too many
		while (elements.size() > count) {
			Element div = (Element) elements.get(0);
			elements.remove(div);
			DOM.removeChild(getElement(), div);
		}

	}

}
