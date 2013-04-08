package transferencias.vos;

import org.apache.commons.lang.StringUtils;

public class SoporteDocumentacionVO {
	int volumen;
	String formato;
	String soporte = null;

	public SoporteDocumentacionVO() {
	}

	public SoporteDocumentacionVO(int volumen, String formato, String soporte) {
		this.volumen = volumen;
		this.formato = formato;
		this.soporte = soporte;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public boolean equals(Object obj) {
		boolean returnValue = false;
		if (obj == this)
			returnValue = true;
		else if (obj instanceof SoporteDocumentacionVO) {
			SoporteDocumentacionVO objAsInfoVolumen = (SoporteDocumentacionVO) obj;
			returnValue = StringUtils.equals(soporte,
					objAsInfoVolumen.getSoporte())
					&& StringUtils.equals(formato,
							objAsInfoVolumen.getFormato());
		}
		return returnValue;
	}
}