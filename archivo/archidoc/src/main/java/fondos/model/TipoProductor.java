package fondos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Posibles tipos de productores de una serie
 */
public class TipoProductor {
	private static int nextNumOrden = 1;
	private final int identificador = nextNumOrden++;
	private String nombre = null;

	private static final TipoProductor[] tiposProductores = new TipoProductor[5];

	public static final TipoProductor INSTITUCION = new TipoProductor(
			"Institucion");
	public static final TipoProductor FAMILIA = new TipoProductor("Familia");
	public static final TipoProductor PERSONA = new TipoProductor("Persona");
	public static final TipoProductor ORGANO = new TipoProductor("Organo");
	public static final TipoProductor BDORGANIZACION = new TipoProductor(
			"Organización");

	protected TipoProductor(String nombre) {
		this.nombre = nombre;
		tiposProductores[identificador - 1] = this;
	}

	public static List getAll() {
		ArrayList ret = new ArrayList();
		ret.add(TipoProductor.getTipoProductor(1));
		ret.add(TipoProductor.getTipoProductor(2));
		ret.add(TipoProductor.getTipoProductor(3));
		ret.add(TipoProductor.getTipoProductor(4));
		ret.add(TipoProductor.getTipoProductor(5));
		return ret;
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public static TipoProductor getTipoProductor(int identificador) {
		return tiposProductores[identificador - 1];
	}

	public boolean equals(Object otroObjeto) {
		return identificador == ((TipoProductor) otroObjeto).getIdentificador();
	}
}