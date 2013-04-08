package transferencias.actions;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import common.bi.ServiceRepository;

import deposito.actions.TransformerToEspacioEnDepositoPO;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.OcupacionElementoDeposito;

/**
 * Datos de presentación de las ubicaciones del fondo físico
 */
public class EspacioEnUbicacionPO {

	OcupacionElementoDeposito datosOcupacion = null;
	Integer numeroHuecosDisponibles = null;
	List depositosUbicacion = null;

	DepositoVO ubicacion = null;
	String formato = null;

	ServiceRepository services = null;
	transient GestorEstructuraDepositoBI depositoBI = null;

	public EspacioEnUbicacionPO(ServiceRepository services,
			DepositoVO ubicacion, String formato) {
		this.services = services;
		this.ubicacion = ubicacion;
		this.formato = formato;
	}

	private OcupacionElementoDeposito getDatosOcupacion() {
		if (datosOcupacion == null)
			datosOcupacion = getDepositoBI().getDatosOcupacion(
					ubicacion.getId(), ubicacion.getIdTipoElemento(), formato);
		return datosOcupacion;
	}

	public int getNumeroHuecos() {
		return getDatosOcupacion().getNumeroHuecos();
	}

	public int getHuecosLibres() {
		return getDatosOcupacion().getHuecosLibres();
	}

	public int getNumeroHuecosDisponibles() {
		if (numeroHuecosDisponibles == null)
			numeroHuecosDisponibles = new Integer(getDepositoBI()
					.getNumeroHuecosDisponibles(ubicacion.getId(), formato));
		return numeroHuecosDisponibles.intValue();
	}

	public List getDepositosUbicacion() {
		if (depositosUbicacion == null) {
			depositosUbicacion = getDepositoBI().getHijosElemento(
					ubicacion.getId(), ubicacion.getIdTipoElemento());
			CollectionUtils.transform(depositosUbicacion,
					TransformerToEspacioEnDepositoPO.getInstance(services,
							formato));
		}
		return depositosUbicacion;
	}

	public String getNombre() {
		return ubicacion.getNombre();
	}

	public String getId() {
		return ubicacion.getId();
	}

	public String getTipoElemento() {
		return ubicacion.getIdTipoElemento();
	}

	private GestorEstructuraDepositoBI getDepositoBI() {
		if (depositoBI == null)
			depositoBI = services.lookupGestorEstructuraDepositoBI();
		return depositoBI;
	}
}