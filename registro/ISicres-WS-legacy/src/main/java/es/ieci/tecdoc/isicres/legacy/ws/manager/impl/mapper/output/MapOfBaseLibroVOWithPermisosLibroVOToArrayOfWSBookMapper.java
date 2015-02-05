package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSBook;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSBook;

/**
 * Instancia de <code>Mapper</code> que transforma un <code>Map</code> de
 * <code>BaseLibroVO</code> como clave y <code>PermisosLibroVO</code> de valor
 * en un objeto de tipo <code>ArrayOfWSBook</code>.
 * 
 * @see BaseLibroVO
 * @see PermisosLibroVO
 * @see ArrayOfWSBook
 * 
 * @author IECISA
 * 
 */
public class MapOfBaseLibroVOWithPermisosLibroVOToArrayOfWSBookMapper implements
		Mapper {

	public MapOfBaseLibroVOWithPermisosLibroVOToArrayOfWSBookMapper(
			Map<BaseLibroVO, PermisosLibroVO> permisos) {
		setPermisos(permisos);
	}

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<BaseLibroVO> libros = (List<BaseLibroVO>) obj;

		ArrayOfWSBook result = new ArrayOfWSBook();

		for (BaseLibroVO libro : libros) {
			result.getWSBook().add(
					(WSBook) new BaseLibroVOToWsBookMapper(
							getPermisosOfLibro(libro)).map(libro));
		}

		return result;
	}

	protected PermisosLibroVO getPermisosOfLibro(BaseLibroVO libro) {
		return permisos.get(libro);
	}

	protected void setPermisos(Map<BaseLibroVO, PermisosLibroVO> permisos) {
		this.permisos = permisos;
	}

	// Members
	protected Map<BaseLibroVO, PermisosLibroVO> permisos;
}
