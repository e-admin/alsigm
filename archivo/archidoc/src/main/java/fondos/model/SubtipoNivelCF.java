/*
 * Created on 22-jun-2005
 *
 */
package fondos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubtipoNivelCF {

	private static final Map subtiposNivel = new HashMap();

	private int identificador = 0;
	private String nombre = null;
	private int codigo = 0;
	private static List listaSubtipos = new ArrayList();

	private SubtipoNivelCF(int key, String value, int codigo) {
		subtiposNivel.put(new Integer(key).toString(), value);
		this.identificador = key;
		this.nombre = value;
		this.codigo = codigo;
		listaSubtipos.add(this);
	}

	public static final SubtipoNivelCF UDOCSIMPLE = new SubtipoNivelCF(
			ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE,
			"Unidad Documental Simple",
			ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE);
	public static final SubtipoNivelCF CAJA = new SubtipoNivelCF(
			ElementoCuadroClasificacion.SUBTIPO_CAJA, "Caja",
			ElementoCuadroClasificacion.SUBTIPO_CAJA);

	public static SubtipoNivelCF getSubtipoNivel(int subtipoNivel) {

		SubtipoNivelCF ret = UDOCSIMPLE;
		Object obj = subtiposNivel.get(new Integer(subtipoNivel).toString());
		if (obj != null)
			ret = (SubtipoNivelCF) obj;

		return ret;
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((SubtipoNivelCF) otroObjeto)
				.getIdentificador();
	}

	public int getCodigo() {
		return codigo;
	}

	public static List getListaSubtipos() {
		return listaSubtipos;
	}
}