package deposito.forms;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.ListUtils;

import deposito.vos.BusquedaHistUInstDepositoVO;

public class BusquedaUISEliminadasForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String formato;
	private String signatura;
	private String signatura_like;
	private String[] motivos;
	private String idArchivo;
	private String nombreArchivo;

	/* Fecha */
	private String fechaComp = null;
	private String fechaFormato = null;
	private String fechaA = null;
	private String fechaM = null;
	private String fechaD = null;
	private String fechaS = null;
	private String fechaIniFormato = null;
	private String fechaIniA = null;
	private String fechaIniM = null;
	private String fechaIniD = null;
	private String fechaIniS = null;
	private String fechaFinFormato = null;
	private String fechaFinA = null;
	private String fechaFinM = null;
	private String fechaFinD = null;
	private String fechaFinS = null;

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String[] getMotivos() {
		return motivos;
	}

	public void setMotivos(String[] motivos) {
		this.motivos = motivos;
	}

	public String getFechaComp() {
		return fechaComp;
	}

	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	public String getFechaFormato() {
		return fechaFormato;
	}

	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	public String getFechaA() {
		return fechaA;
	}

	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	public String getFechaM() {
		return fechaM;
	}

	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	public String getFechaD() {
		return fechaD;
	}

	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	public String getFechaS() {
		return fechaS;
	}

	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	public String getFechaIniA() {
		return fechaIniA;
	}

	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	public String getFechaIniM() {
		return fechaIniM;
	}

	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	public String getFechaIniD() {
		return fechaIniD;
	}

	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	public String getFechaIniS() {
		return fechaIniS;
	}

	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	public String getFechaFinA() {
		return fechaFinA;
	}

	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	public String getFechaFinM() {
		return fechaFinM;
	}

	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	public String getFechaFinD() {
		return fechaFinD;
	}

	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura_like(String signatura_like) {
		this.signatura_like = signatura_like;
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public BusquedaHistUInstDepositoVO getBusquedaHistUInstDepositoVO(
			List idsArchivo) {
		BusquedaHistUInstDepositoVO busquedaVO = new BusquedaHistUInstDepositoVO();

		busquedaVO.setIdFormato(formato);
		busquedaVO.setSignaturaUI(signatura);
		busquedaVO.setSignatura_like(signatura_like);
		busquedaVO.setMotivos(motivos);

		if (StringUtils.isNotBlank(idArchivo)) {
			busquedaVO.setArchivos(new String[] { idArchivo });
		} else {
			if (ListUtils.isNotEmpty(idsArchivo)) {
				String[] ids = null;

				if (idsArchivo.size() == 1)
					ids = new String[] { (String) idsArchivo.get(0) };
				else {
					ids = new String[idsArchivo.size()];

					for (int i = 0; i < idsArchivo.size(); i++) {
						ids[i] = (String) idsArchivo.get(i);
					}

					busquedaVO.setArchivos(ids);
				}
			}
		}

		// Obtener el rango de fechas para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(this.fechaComp,
				new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
						this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		busquedaVO.setFechaIni(range.getInitialDate());
		busquedaVO.setFechaFin(range.getFinalDate());

		return busquedaVO;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}
}
