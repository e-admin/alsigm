package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSBook;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>BaseLibroVO</code> en una de <code>WsBook</code>.
 * 
 * @see BaseLibroVO
 * @see WSBook
 * 
 * @author IECISA
 * 
 */
public class BaseLibroVOToWsBookMapper implements Mapper {

	public BaseLibroVOToWsBookMapper(PermisosLibroVO permisos) {
		setPermisos(permisos);
	}

	public Object map(Object obj) {
		Assert.isInstanceOf(BaseLibroVO.class, obj);

		BaseLibroVO libro = (BaseLibroVO) obj;

		WSBook result = new WSBook();
		result.setId(Integer.parseInt(libro.getId()));
		result.setName(libro.getName());

		int tipoLibro = Libro_Tipo_Estandar;
		if (libro instanceof LibroEntradaVO) {
			tipoLibro = Libro_Tipo_Entrada;
		}
		if (libro instanceof LibroSalidaVO) {
			tipoLibro = Libro_Tipo_Salida;
		}

		result.setType(tipoLibro);

		if (getPermisos() != null) {
			result.setCanCreate(getPermisos().isCreacion());
			result.setCanModify(getPermisos().isModificacion());
			if (!getPermisos().isCreacion() && !getPermisos().isModificacion()) {
				result.setReadOnly(getPermisos().isConsulta());
			} else {
				result.setReadOnly(false);
			}
		}

		return result;
	}

	protected PermisosLibroVO getPermisos() {
		return permisos;
	}

	protected void setPermisos(PermisosLibroVO permisos) {
		this.permisos = permisos;
	}

	// Members
	protected PermisosLibroVO permisos;

	protected static final int Libro_Tipo_Estandar = 0;
	protected static final int Libro_Tipo_Entrada = 1;
	protected static final int Libro_Tipo_Salida = 2;

}
