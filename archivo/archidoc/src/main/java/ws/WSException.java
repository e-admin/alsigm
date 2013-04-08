package ws;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;

import transferencias.TransferenciasElectronicasConstants;

import common.exceptions.ConfigException;
import common.exceptions.TransferenciaElectronicaException;

public class WSException extends AxisFault {

	/**
	 *
	 */
	private static final long serialVersionUID = -1301222024805839084L;


	public WSException (String codigo, Exception e){
		if(e != null){
			setFaultReason(e.getStackTrace().toString());
		}
		setFaultCode(new QName(codigo));
		setFaultString(TransferenciasElectronicasConstants.getMensajeError(codigo));
		setFaultDetailString(e.toString());
	}


	public WSException (TransferenciaElectronicaException e){
		if(e.getException() != null){
			setFaultReason(e.getException().toString());
		}
		setFaultDetail(e.getDetalles());
		setFaultCode(new QName(e.getCodigo()));
		setFaultDetailString(e.getMensaje());
		setFaultString(TransferenciasElectronicasConstants.getMensajeError(e.getCodigo()));
	}

	public WSException (Exception e){

		String codigo = TransferenciasElectronicasConstants.ERROR_EXCEPTION;

//		if(e instanceof ConfigException){
//			codigo = TransferenciasElectronicasConstants.ERROR_CONFIGURACION_SISTEMA_ARCHIVO;
//		}

		setFaultCode(new QName(codigo));
		setFaultString(TransferenciasElectronicasConstants.getMensajeError(codigo));
		setFaultDetailString(e.toString());
	}

}
