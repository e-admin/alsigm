package fondos.vos;

public interface FondoVO extends ElementoCuadroClasificacionVO {

	public static final int PUBLICO = 1;
	public static final int PRIVADO = 2;

	public String getCodPais();

	public void setCodPais(String codPais);

	public String getCodComunidad();

	public void setCodComunidad(String codComunidad);

	public String getCodArchivo();

	public void setCodArchivo(String codArchivo);

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public int getTipofondo();

	public void setTipofondo(int tipoFondo);

	public String getIdEntProductora();

	public void setIdEntProductora(String idEntProductora);

	public String getIdElementoCF();

	public void setIdElementoCF(String id);

	public boolean getPermitidoModificarCodigo();

	public void setPermitidoModificarCodigo(boolean value);

	public boolean getPermitidoModificarEntidadProductora();

	public void setPermitidoModificarEntidadProductora(boolean value);

	public boolean getPuedeSerEliminado();

	public void setPuedeSerEliminado(boolean value);

	public boolean getPuedeSerMovido();

	public void setPuedeSerMovido(boolean puedeSerMovido);

	public boolean getPermitidoModificarArchivo();

	public void setPermitidoModificarArchivo(boolean value);

}
