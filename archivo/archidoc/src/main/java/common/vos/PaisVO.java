package common.vos;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.util.MapUtil;

/**
 * Datos de pais. El pais es parte de la información a partir de la que se
 * define un fondo documental.
 */
public class PaisVO {
	String codigo;
	String nombre;
	Map comunidades;

	public int getNumcomunidades() {
		return comunidades.size();
	}

	public PaisVO() {
	}

	public PaisVO(String id, String nombre, Map comunidades) {
		super();
		this.codigo = id;
		this.nombre = nombre;
		this.comunidades = comunidades;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String id) {
		this.codigo = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List getComunidades() {
		List ret = MapUtil.toList(comunidades);
		Collections.sort(ret, new Comparator() {

			public int compare(Object o1, Object o2) {
				ComunidadVO comunidad1 = (ComunidadVO) o1;
				ComunidadVO comunidad2 = (ComunidadVO) o2;
				return comunidad1.getNombre().compareTo(comunidad2.getNombre());
			}

		});
		return ret;
	}

	public void setComunidades(Map comunidades) {
		this.comunidades = comunidades;
	}

	public void addComunidad(ComunidadVO comunidadVO) {
		if (comunidades == null)
			comunidades = new HashMap();

		comunidadVO.setCodigopais(this.getCodigo());
		comunidades.put(comunidadVO.codigocomunidad, comunidadVO);

	}
}
