package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;

public class TransporteForm extends RPAdminBaseForm {

	private static final long serialVersionUID = 1L;

	private String[] attrsToUpper = new String[] { "transport" };

	private String id;
	private String transport;
	private String codigoIntercambioRegistral;

	/**
	 * Identificador del tipo de transporte en sicres
	 */
	private Integer idTipoTransporte;

	private List<TipoTransporteEnum> listaTiposTransportesIR;

	public TransporteForm(){
		listaTiposTransportesIR = new LinkedList<TipoTransporteEnum>();
		listaTiposTransportesIR.add(TipoTransporteEnum.SERVICIO_MENSAJEROS);
		listaTiposTransportesIR.add(TipoTransporteEnum.CORREO_POSTAL);
		listaTiposTransportesIR.add(TipoTransporteEnum.CORREO_POSTAL_CERTIFICADO);
		listaTiposTransportesIR.add(TipoTransporteEnum.BUROFAX);
		listaTiposTransportesIR.add(TipoTransporteEnum.EN_MANO);
		listaTiposTransportesIR.add(TipoTransporteEnum.FAX);
		listaTiposTransportesIR.add(TipoTransporteEnum.OTROS);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}



	public String getCodigoIntercambioRegistral() {
		return codigoIntercambioRegistral;
	}

	public void setCodigoIntercambioRegistral(String codigoIntercambioRegistral) {
		this.codigoIntercambioRegistral = codigoIntercambioRegistral;
	}



	public List<TipoTransporteEnum> getListaTiposTransportesIR() {
		return listaTiposTransportesIR;
	}

	public void setListaTiposTransportesIR(
			List<TipoTransporteEnum> listaTiposTransportesIR) {
		this.listaTiposTransportesIR = listaTiposTransportesIR;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}

	public Integer getIdTipoTransporte() {
		return idTipoTransporte;
	}

	public void setIdTipoTransporte(Integer idTipoTransporte) {
		this.idTipoTransporte = idTipoTransporte;
	}





}
