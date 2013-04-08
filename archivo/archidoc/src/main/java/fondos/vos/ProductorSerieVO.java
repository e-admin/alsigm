package fondos.vos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;

import common.Constants;
import common.util.DateUtils;
import common.util.StringUtils;
import common.vos.UniqueGuid;

import descripcion.vos.DescriptorVO;
import fondos.FondosConstants;
import fondos.utils.ProductoresUtils;

public class ProductorSerieVO extends DescriptorVO implements UniqueGuid {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger
			.getLogger(ProductorSerieVO.class);

	public static String NAME_PROPERTY_IDPRODUCTOR = Constants.ID;
	public static String NAME_PROPERTY_TIPOPRODUCTOR = "tipo";
	public static String NAME_PROPERTY_LISTAACCESO = "idLCAPref";

	public final static int TIPO_ENTIDAD = 1;
	public final static int TIPO_ORG_DEP = 2;

	String idserie = null;
	String idprocedimiento = null;
	String idProductor = null;
	int tipoProductor;
	Date fechaInicial;
	Date fechaFinal;
	String idLCAPref = null;
	String sustituidoVigente;
	int marcas;
	private Boolean pasadoAVigente;
	// private int marcasAnterior;

	/**
	 * Identificador unico del productor.
	 */
	private String guid;

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.FORMATO_FECHA);
		try {
			if (fechaInicial != null && fechaInicial.length() > 0)
				this.fechaInicial = f.parse(fechaInicial);
		} catch (ParseException e) {
			logger.error("formato de fecha inicial incorrecto");
		}
	}

	public void setFechaFinal(String fechaFinal) {
		if (fechaFinal == null)
			this.fechaFinal = null;
		else {
			SimpleDateFormat f = new SimpleDateFormat(Constants.FORMATO_FECHA);
			try {
				if (fechaFinal != null && fechaFinal.length() > 0)
					this.fechaFinal = f.parse(fechaFinal);
			} catch (ParseException e) {
				logger.error("formato de fecha inicial incorrecto");
				this.fechaFinal = null;
			}
		}
	}

	public boolean isTipoEntidad() {
		return tipoProductor == TIPO_ENTIDAD;
	}

	public boolean isTipoOrganismo() {
		return tipoProductor == TIPO_ORG_DEP;
	}

	public String getNombreTipo() {
		if (isTipoEntidad())
			return FondosConstants.MSG_RESOURCES_TIPO_ENTIDAD;
		else
			return FondosConstants.MSG_RESOURCES_TIPO_ORGANISMO;
	}

	public String getIdprocedimiento() {
		return this.idprocedimiento;
	}

	public void setIdprocedimiento(String idprocedimiento) {
		this.idprocedimiento = idprocedimiento;
	}

	public String getIdserie() {
		return this.idserie;
	}

	public void setIdserie(String idserie) {
		this.idserie = idserie;
	}

	public String getIdLCAPref() {
		return idLCAPref;
	}

	public void setIdLCAPref(String idLCAPref) {
		this.idLCAPref = idLCAPref;
	}

	public String getIdProductor() {
		return idProductor;
	}

	public void setIdProductor(String idProductor) {
		this.idProductor = idProductor;
	}

	public int getTipoProductor() {
		return tipoProductor;
	}

	public void setTipoProductor(int tipoProductor) {
		this.tipoProductor = tipoProductor;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof ProductorSerieVO))
			return false;
		ProductorSerieVO castOther = (ProductorSerieVO) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	public String getSustituidoVigente() {
		return sustituidoVigente;
	}

	public void setSustituidoVigente(String sustituidoVigente) {
		this.sustituidoVigente = sustituidoVigente;
	}

	public int getMarcas() {
		return marcas;
	}

	public void setMarcas(int marcas) {
		this.marcas = marcas;
	}

	public void setPasadoAVigente(Boolean pasadoAVigente) {
		this.pasadoAVigente = pasadoAVigente;
	}

	public Boolean getPasadoAVigente() {
		return pasadoAVigente;
	}

	public boolean isPermitidoEliminar() {
		return ProductoresUtils.isPermitidoEliminar(this.marcas);
	}

	public boolean isPermitidoPasarAVigente() {
		boolean retorno = ProductoresUtils
				.isPermitidoPasarAVigente(this.marcas);

		if (retorno || StringUtils.isNotEmpty(this.sustituidoVigente)) {
			return true;
		}

		return false;
	}

	public boolean isMostrarRadio() {
		if (isPermitidoEliminar() || isPermitidoPasarAVigente()) {
			return true;
		}
		return false;
	}

	public boolean isSinGuardar() {
		return ProductoresUtils.isSinGuardar(this.marcas);
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		if (StringUtils.isNotEmpty(this.getId())) {
			return this.getId();
		}
		return guid;
	}

	public boolean isVinculado() {
		return ProductoresUtils.isVinculado(this.marcas);
	}

	public boolean isNuevo() {
		return ProductoresUtils.isNuevo(this.marcas);
	}

	public boolean isEliminado() {
		return ProductoresUtils.isEliminado(marcas);
	}

	// public void setMarcasAnterior(int marcasAnterior) {
	// this.marcasAnterior = marcasAnterior;
	// }
	//
	// public int getMarcasAnterior() {
	// return marcasAnterior;
	// }

	public boolean isPermitidoPasarAHistorico() {
		return ProductoresUtils.isPermitidoPasarAHistorico(this.marcas);
	}

	public boolean isPermitidoReemplazar() {
		return ProductoresUtils.isPermitidoReemplazar(this.marcas,
				this.getIdLCA());
	}

	public String getTipoObjeto() {
		return ProductoresUtils.getTipoObjeto(this);
	}

	public boolean isPasadoAHistorico() {
		return ProductoresUtils.isPermitidoReemplazar(this.marcas,
				this.getIdLCA());
	}

	public boolean isIncorporadoComoHistorico() {
		return ProductoresUtils.isIncorporadoComoHistorico(this.marcas);
	}

	public boolean isSustituidoPorVigente() {
		return ProductoresUtils.isSustituidoPorVigente(this.marcas);
	}

	public boolean isVigente() {
		return ProductoresUtils.isVigente(marcas, this.fechaFinal);
	}

	public boolean isVigenteSeleccionable() {
		return ProductoresUtils.isOriginalVigente(this.marcas, this.fechaFinal);
	}

	public String getDebug() {
		return getTipoObjeto() + "<br/>" + getGuid() + "<br/>" + getNombre()
				+ "<br/>MARCAS:" + this.marcas + "<br/>LCAPREF:"
				+ this.idLCAPref + "<br/>FECHAS:"
				+ DateUtils.formatDate(this.fechaInicial) + "-"
				+ DateUtils.formatDate(this.fechaFinal);
	}

	public String getTextoMarcas() {
		return ProductoresUtils.getTextoMarcas(this.marcas);
	}
}