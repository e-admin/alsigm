/*
 * Created on 22-jun-2005
 *
 */
package fondos.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;

import common.util.ListUtils;

/**
 * @author luisanve
 * 
 */
public class TipoNivelCF {

	// private static int nextNumOrden = 1;

	private int identificador = 0;
	private String nombre = null;
	private int[] tiposNivelesHijos = null;
	private Boolean puedeSerRaiz = null;
	private Boolean tieneSubtipo = null;
	private String imagen = null;
	private List tiposPermitidos = new ArrayList();

	private static Map listaNiveles = new LinkedHashMap();

	// private static final TipoNivelCF[] tiposNivel = new TipoNivelCF[6];
	public static final String ID_NIVEL_RAIZ = "ID_NIVEL_RAIZ";
	public static final int TIPO_NIVEL_RAIZ = 0;

	private TipoNivelCF(int identificador, String nombre,
			int[] tiposNivelesHijo, boolean puedeSerRaiz, boolean tieneSubtipo,
			String imagen) {
		this.identificador = identificador;
		this.nombre = nombre;
		this.tiposNivelesHijos = tiposNivelesHijo;
		this.puedeSerRaiz = new Boolean(puedeSerRaiz);
		this.tieneSubtipo = new Boolean(tieneSubtipo);
		this.imagen = imagen;
		TipoNivelCF.listaNiveles.put(new Integer(identificador), this);
		// tiposNivel[identificador] = this;
	}

	public static final TipoNivelCF CLASIFICADOR_FONDOS = new TipoNivelCF(1,
			"Clasificador de fondos", new int[] { 1, 2 }, true, false,
			"book-1.0.gif");
	public static final TipoNivelCF FONDO = new TipoNivelCF(2, "Fondo",
			new int[] { 3, 4 }, true, false, "book-2.0.gif");
	public static final TipoNivelCF CLASIFICADOR_SERIE = new TipoNivelCF(3,
			"Clasificador de series", new int[] { 4 }, false, false,
			"book-4.0.gif");
	public static final TipoNivelCF SERIE = new TipoNivelCF(4, "Serie",
			new int[] { 6 }, false, false, "book-5.0.gif");
	// public static final TipoNivelCF CLASIFICADOR_UNIDAD_DOCUMENTAL = new
	// TipoNivelCF(5, "Clasificador unidad documental",new
	// int[]{6},false,false,"book-3.0.gif");
	public static final TipoNivelCF UNIDAD_DOCUMENTAL = new TipoNivelCF(6,
			"Unidad documental", new int[] {}, false, true, "book-0.0.gif");

	public static TipoNivelCF getTipoNivel(int tipoNivel) {
		return (TipoNivelCF) listaNiveles.get(new Integer(tipoNivel));
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getIdentificadorAsString() {
		return new Integer(identificador).toString();
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((TipoNivelCF) otroObjeto).getIdentificador();
	}

	public String getNombre() {
		return this.nombre;
	}

	public static List getListaNiveles() {
		if (CollectionUtils.isEmpty(listaNiveles))
			return new ArrayList();
		return new ArrayList(listaNiveles.values());
	}

	public int[] getTiposNivelesHijos() {
		return tiposNivelesHijos;
	}

	public Boolean getPuedeSerRaiz() {
		return puedeSerRaiz;
	}

	public Boolean getTieneSubtipo() {
		return tieneSubtipo;
	}

	public String getImagen() {
		return this.imagen;
	}

	public List getTiposPermitidos() {
		if (ListUtils.isEmpty(tiposPermitidos)) {
			for (Iterator iterator = listaNiveles.values().iterator(); iterator
					.hasNext();) {
				TipoNivelCF tipoNivel = (TipoNivelCF) iterator.next();
				if (ArrayUtils.contains(tiposNivelesHijos,
						tipoNivel.getIdentificador()))
					tiposPermitidos.add(tipoNivel);
			}
		}
		return tiposPermitidos;
	}

}