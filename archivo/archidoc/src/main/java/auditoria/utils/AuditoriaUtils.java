package auditoria.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import auditoria.logger.DataLoggingEvent;

import common.Constants;
import common.bi.GestionFondosBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;

import fondos.actions.FondoPO;
import fondos.actions.FondoToPOTransformer;
import fondos.vos.FondoVO;

public class AuditoriaUtils {

	public static String transformOptionsToString(String[] arrayToTransform,
			List options) {
		return transformOptionsToString(arrayToTransform, options, null);
	}

	public static String transformOptionsToString(String[] arrayToTransform,
			List options, String campoDescripcion) {
		if (arrayToTransform == null || arrayToTransform.length == 0)
			return "";
		if (options == null || options.size() == 0)
			return "";

		String atributoDescripcion = campoDescripcion;

		Object obj = options.get(0);
		Field[] campos = obj.getClass().getDeclaredFields();

		if (StringUtils.isBlank(campoDescripcion)) {
			if (campos.length > 2)
				return null;
			atributoDescripcion = "Descripcion";
		}
		String result = "";

		if (campos[0].getName().toLowerCase().equals(Constants.ID)) {
			atributoDescripcion = campos[1].getName();
		} else {
			atributoDescripcion = campos[0].getName();
		}

		// puede ser que el campo id no sea un numero
		HashMap hashOptions = new HashMap();
		for (Iterator it = options.iterator(); it.hasNext();) {
			Object objIt = it.next();
			String clave = null;
			String valor = null;
			try {
				Method metodoGetId = objIt.getClass().getMethod("getId",
						new Class[] {});
				Method metodoGetDescripcion = objIt.getClass().getMethod(
						"get"
								+ atributoDescripcion.substring(0, 1)
										.toUpperCase()
								+ atributoDescripcion.substring(1),
						new Class[] {});
				clave = metodoGetId.invoke(objIt, (Object)null).toString();
				valor = metodoGetDescripcion.invoke(objIt, (Object)null).toString();
			} catch (Exception e) {
				continue;
			}
			hashOptions.put(clave, valor);
		}

		int len = arrayToTransform.length;
		for (int i = 0; i < len; i++) {
			String id = arrayToTransform[i];
			Object o = hashOptions.get(id);
			if (o == null)
				continue;
			String valor = o.toString();

			result = result + valor;
			if (i + 1 < len)
				result = result + ", ";
		}
		return result;
	}

	public static String getDescripcionFondoById(String idFondo,
			ServiceSession session) {
		ServiceRepository services = ServiceRepository.getInstance(session);
		GestionFondosBI fondosBI = services.lookupGestionFondosBI();

		FondoVO fondoVO = fondosBI.getFondoXId(idFondo);
		FondoPO fondoPO = (FondoPO) FondoToPOTransformer.getInstance(services)
				.transform(fondoVO);

		return fondoPO.getTitulo();
	}

	public static void auditaModificacion(Object voOriginal, Object voCambiado,
			DataLoggingEvent detalle, HashMap tablaTraduccionCampos) {

		// nos aseguramos que ambos VOs son del mismo tipo
		if (!(voOriginal.getClass().toString().equals(voCambiado.getClass()
				.toString())))
			return;

		Field[] atributosVOOriginal = voOriginal.getClass().getDeclaredFields();
		Field[] atributosVOCambiado = voCambiado.getClass().getDeclaredFields();

		// recorrer las propiedades de voOrigen
		for (int i = 0; i <= atributosVOOriginal.length; i++) {
			// obtener valores de ese atributo en ambos VOs
			String valorVOOriginal = getValorPropiedad(
					atributosVOOriginal[i].getName(), voOriginal);
			String valorVOCambiado = getValorPropiedad(
					atributosVOCambiado[i].getName(), voCambiado);
			// si son distintas auditarlo, obteniendo la etiqueta para el
			// detalle de la tabla de traduccion
			if (StringUtils.isEmpty(valorVOOriginal)
					&& StringUtils.isEmpty(valorVOCambiado))
				continue;

			if (!StringUtils.isEmpty(valorVOOriginal)) {
				// pendiente de traducir correctamente la etiqueta y el propio
				// campo anterior
			}
			if (!StringUtils.isEmpty(valorVOCambiado)) {
				// pendiente de traducir correctamente la etiqueta y el propio
				// campo actual
			}
		}
	}

	public static String getValorPropiedad(String nombreAtributo, Object vo) {
		String nombreMetodoGet = "get"
				+ nombreAtributo.substring(0, 1).toUpperCase()
				+ nombreAtributo.substring(1);
		String valor = null;
		try {
			Method metodoGet = vo.getClass().getMethod(nombreMetodoGet,
					new Class[] {});
			valor = metodoGet.invoke(vo, (Object)null).toString();
		} catch (Exception e) {
		}
		return valor;
	}
}
