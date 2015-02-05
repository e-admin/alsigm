/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddress;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddress;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);
		List<DireccionFisicaVO> listaDirecciones = (List<DireccionFisicaVO>) obj;
		
		ArrayOfWSPostalAddress arrayOfWSPostalAddress = new ArrayOfWSPostalAddress();
		
		for (DireccionFisicaVO direccion : listaDirecciones) {
			WSPostalAddress wsPostalAddress = new WSPostalAddress();

			
			wsPostalAddress.setAddress(direccion.getDireccion());

			wsPostalAddress.setCountry(direccion.getPais().getNombre());
			wsPostalAddress.setId(Integer.valueOf(direccion.getId()));
			wsPostalAddress.setProvince(direccion.getProvincia().getNombre());
			// Preferred es el atributo correspondiente a si es la dirección principal
			wsPostalAddress.setPreferred(String.valueOf(direccion.isPrincipal()));
			wsPostalAddress.setZipCode(direccion.getCodigoPostal());
			if (direccion.getCiudad()!=null){
				wsPostalAddress.setCity(direccion.getCiudad().getNombre());
			}
			arrayOfWSPostalAddress.getArrayOfWSCity().add(wsPostalAddress);
		}
		return arrayOfWSPostalAddress;
	}

}
