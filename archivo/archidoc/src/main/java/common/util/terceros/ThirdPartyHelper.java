package common.util.terceros;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import se.NotAvailableException;
import se.terceros.GestorTerceros;
import se.terceros.GestorTercerosFactory;
import se.terceros.InfoTercero;
import se.terceros.TipoAtributo;
import se.terceros.exceptions.GestorTercerosException;

import common.MultiEntityConstants;
import common.util.IFValidator;

/**
 * Utilidad para gestinar las búsquedas de terceros.
 */
public class ThirdPartyHelper {

	/**
	 * Realiza una búsqueda de terceros.
    *
	 * @param form
	 *            Criterios de búsqueda.
	 * @param entity
	 *            Entidad sobre la que se busca
	 * @return Lista de terceros ({@link InfoTercero}).
	 * @throws GestorTercerosException
	 *             si ocurre algún error en la búsqueda de tercero.
	 * @throws NotAvailableException
	 *             si la funcionalidad no está disponible.
	 * @throws IFNotValidException
	 *             si el número de identificación no es válido.
	 */
	public static List searchThirdParty(IThirdPartySearchForm form,
			String entity) throws GestorTercerosException,
			NotAvailableException, IFNotValidException {
		List terceros = null;

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if (StringUtils.isNotEmpty(entity)) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM, entity);
		}

		GestorTerceros bdTerceros = GestorTercerosFactory.getConnector(params);

		if (StringUtils
				.equals(form.getThirdPartySearchType(),
						IThirdPartySearchForm.TIPOS_BUSQUEDA[IThirdPartySearchForm.BUSQUEDA_POR_NOMBRE])) {
			terceros = bdTerceros.recuperarTerceros(form.getNameSearchToken(),
					form.getSurname1SearchToken(),
					form.getSurname2SearchToken());
		} else if (StringUtils
				.equals(form.getThirdPartySearchType(),
						IThirdPartySearchForm.TIPOS_BUSQUEDA[IThirdPartySearchForm.BUSQUEDA_POR_RAZON_SOCIAL])) {
			terceros = bdTerceros.recuperarTerceros(TipoAtributo.RAZON_SOCIAL,
					form.getCompanySearchToken());
		} else if (StringUtils
				.equals(form.getThirdPartySearchType(),
						IThirdPartySearchForm.TIPOS_BUSQUEDA[IThirdPartySearchForm.BUSQUEDA_POR_IF])) {
			if (IFValidator.isValidIF(form.getIfSearchToken())) {
				terceros = bdTerceros.recuperarTerceros(
						(short)form.getTipoNumeroIdentificacion(), form.getIfSearchToken());
			} else
				throw new IFNotValidException(form.getIfSearchToken());
		}

		return terceros;
	}
}
