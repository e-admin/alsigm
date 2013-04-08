package fondos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tipos de entidad productora
 */
public class TipoEntidad {
	private static int nextNumOrden = 1;
	private final int identificador = nextNumOrden++;
	private String nombre = null;

	private static final TipoEntidad[] tiposInstitucion = new TipoEntidad[3];
	public static final TipoEntidad INSTITUCION = new TipoEntidad("Institucion");
	public static final TipoEntidad FAMILIA = new TipoEntidad("Familia");
	public static final TipoEntidad PERSONA = new TipoEntidad("Persona");

	protected TipoEntidad(String nombre) {
		this.nombre = nombre;
		tiposInstitucion[identificador - 1] = this;
	}

	public static List getAll() {
		ArrayList ret = new ArrayList();
		ret.add(TipoEntidad.getTipoEntidad(1));
		ret.add(TipoEntidad.getTipoEntidad(2));
		ret.add(TipoEntidad.getTipoEntidad(3));
		return ret;
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public static TipoEntidad getTipoEntidad(int identificador) {
		return tiposInstitucion[identificador - 1];
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((TipoEntidad) otroObjeto).getIdentificador();
	}
}