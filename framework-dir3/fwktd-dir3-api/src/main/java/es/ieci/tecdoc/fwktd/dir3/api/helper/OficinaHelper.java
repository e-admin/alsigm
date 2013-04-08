package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaDatosLocalizacionVO;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;

/**
 * Clase de utilidad para la transformación de objetos con la información de
 * oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class OficinaHelper {

	private static final String COMA_AND_ESPACIO = ", ";
	private static final String EMPTY = "";
	private static final String BLANK = " ";

	public static List<DatosBasicosOficina> getDatosBasicosOficinas(
			List<DatosBasicosOficinaVO> listaDatosBasicosOficinaVO) {

		List<DatosBasicosOficina> listaDatosBasicosOficinas = new ArrayList<DatosBasicosOficina>();

		if (listaDatosBasicosOficinaVO != null) {
			for (DatosBasicosOficinaVO datosBasicosOficinaVO : listaDatosBasicosOficinaVO) {
				DatosBasicosOficina datosBasicosOficina = getDatosBasicosOficina(datosBasicosOficinaVO);
				if (datosBasicosOficina != null) {
					listaDatosBasicosOficinas.add(datosBasicosOficina);
				}
			}
		}

		return listaDatosBasicosOficinas;
	}

	public static DatosBasicosOficina getDatosBasicosOficina(
			DatosBasicosOficinaVO datosBasicosOficinaVO) {

		DatosBasicosOficina datosBasicosOficina = null;

		if (datosBasicosOficinaVO != null) {

			datosBasicosOficina = new DatosBasicosOficina();

			datosBasicosOficina.setId(datosBasicosOficinaVO.getId());
			datosBasicosOficina.setNombre(datosBasicosOficinaVO.getNombre());
			datosBasicosOficina.setIdExternoFuente(datosBasicosOficinaVO
					.getIdExternoFuente());

			datosBasicosOficina.setIdUnidadResponsable(datosBasicosOficinaVO
					.getIdUnidadResponsable());
			datosBasicosOficina
					.setNombreUnidadResponsable(datosBasicosOficinaVO
							.getNombreUnidadResponsable());
			datosBasicosOficina.setNivelAdministracion(datosBasicosOficinaVO
					.getNivelAdministracion());
			datosBasicosOficina
					.setDescripcionNivelAdministracion(datosBasicosOficinaVO
							.getDescripcionNivelAdministracion());

			datosBasicosOficina.setIndicadorAdhesionSIR(datosBasicosOficinaVO
					.getIndicadorAdhesionSIR());
			datosBasicosOficina
					.setIndicadorOficinaRegistro(datosBasicosOficinaVO
							.getIndicadorOficinaRegistro());
			datosBasicosOficina
					.setIndicadorOficinaInformacion(datosBasicosOficinaVO
							.getIndicadorOficinaInformacion());
			datosBasicosOficina
					.setIndicadorOficinaTramitacion(datosBasicosOficinaVO
							.getIndicadorOficinaTramitacion());
			datosBasicosOficina
					.setIndicadorRegistroElectronico(datosBasicosOficinaVO
							.getIndicadorRegistroElectronico());
			datosBasicosOficina
					.setIndicadorIntercambioSinRestriccion(datosBasicosOficinaVO
							.getIndicadorIntercambioSinRestriccion());
			datosBasicosOficina
					.setIndicadorIntercambioLocalEstatal(datosBasicosOficinaVO
							.getIndicadorIntercambioLocalEstatal());
			datosBasicosOficina
					.setIndicadorIntercambioLocalAutonomicoRestringido(datosBasicosOficinaVO
							.getIndicadorIntercambioLocalAutonomicoRestringido());
			datosBasicosOficina
					.setIndicadorIntercambioLocalAutonomicoGeneral(datosBasicosOficinaVO
							.getIndicadorIntercambioLocalAutonomicoGeneral());
			datosBasicosOficina
					.setIndicadorIntercambioLocalLocalRestringido(datosBasicosOficinaVO
							.getIndicadorIntercambioLocalLocalRestringido());
			datosBasicosOficina
					.setIndicadorIntercambioLocalLocalGeneral(datosBasicosOficinaVO
							.getIndicadorIntercambioLocalLocalGeneral());
			datosBasicosOficina
					.setIndicadorIntercambioAytoAytoRestringido(datosBasicosOficinaVO
							.getIndicadorIntercambioAytoAytoRestringido());

			datosBasicosOficina.setEstado(datosBasicosOficinaVO.getEstado());
			datosBasicosOficina.setDescripcionEstado(datosBasicosOficinaVO
					.getDescripcionEstado());
			datosBasicosOficina.setFechaCreacion(datosBasicosOficinaVO
					.getFechaCreacion());
			datosBasicosOficina.setFechaExtincion(datosBasicosOficinaVO
					.getFechaExtincion());
			datosBasicosOficina.setFechaAnulacion(datosBasicosOficinaVO
					.getFechaAnulacion());
		}

		return datosBasicosOficina;
	}

	/**
	 * Devuelve la dirección a partir de los datos de Localización de una Oficina.
	 * La dirección se devolvera en el siguiente formato:
	 * [Tipo de Via][ ][Nombre de Via][, ][Numero de Via][, ][Resto Direccion] | [Direccion Extranjera]
	 * @param data - {@link OficinaDatosLocalizacionVO}
	 * @return Cadena con la direccion de la oficina
	 */
	public static String getOficinaAddress(OficinaDatosLocalizacionVO data)
	{
		StringBuilder address = new StringBuilder();

		//Tip de Via (Calle, Avenida, etc..)
		if (data.getTipoVia().length() > 0)
		{
			address.append(data.getTipoVia());
		}

		//Nombre de la Via
		if (data.getNombreVia().length() > 0)
		{
			address.append(address.length() > 0 ? BLANK : EMPTY);
			address.append(data.getNombreVia());
		}

		//Numero
		if (data.getNumeroVia().length() > 0)
		{
			address.append(address.length() > 0 ? COMA_AND_ESPACIO : EMPTY);
			address.append(data.getNumeroVia());
		}

		//Resto de dirección si tuviera
		if (data.getRestoDireccion().length() > 0)
		{
			address.append(address.length()> 0 ? COMA_AND_ESPACIO : EMPTY);
			address.append(data.getRestoDireccion());
		}

		//Direccion extranjera, solo se incluye si no hay el resto de datos
		if (address.length() == 0 && data.getDireccionExtranjera().length() > 0)
		{
			address.append(data.getDireccionExtranjera());
		}

		return address.toString();
	}

}