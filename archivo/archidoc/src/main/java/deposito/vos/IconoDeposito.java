/**
 *
 */
package deposito.vos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class IconoDeposito {
	private String id;
	private String icono1;
	private String icono2;
	private String icono3;
	private String icono4;
	private String icono5;
	private String icono6;

	private List listaIconos = new ArrayList();

	public IconoDeposito(String id, String icono1, String icono2,
			String icono3, String icono4, String icono5, String icono6) {
		super();
		this.id = id;
		this.icono1 = icono1;
		this.icono2 = icono2;
		this.icono3 = icono3;
		this.icono4 = icono4;
		this.icono5 = icono5;
		this.icono6 = icono6;

		listaIconos.add(icono1);
		listaIconos.add(icono2);
		listaIconos.add(icono3);
		listaIconos.add(icono4);
		listaIconos.add(icono5);
		listaIconos.add(icono6);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcono1() {
		return icono1;
	}

	public void setIcono1(String icono1) {
		this.icono1 = icono1;
	}

	public String getIcono2() {
		return icono2;
	}

	public void setIcono2(String icono2) {
		this.icono2 = icono2;
	}

	public String getIcono3() {
		return icono3;
	}

	public void setIcono3(String icono3) {
		this.icono3 = icono3;
	}

	public String getIcono4() {
		return icono4;
	}

	public void setIcono4(String icono4) {
		this.icono4 = icono4;
	}

	public String getIcono5() {
		return icono5;
	}

	public void setIcono5(String icono5) {
		this.icono5 = icono5;
	}

	public String getIcono6() {
		return icono6;
	}

	public void setIcono6(String icono6) {
		this.icono6 = icono6;
	}

	public String getIconoDefecto() {
		return this.icono1;
	}

	public List getListaIconos() {
		return listaIconos;
	}

	public String getCadena(String cadena) {
		return "'" + cadena + "'";
	}

	public String getJavascript() {
		StringBuffer js = new StringBuffer(getCadena(this.id)).append(":")
				.append("[").append(getCadena(icono1)).append(",")
				.append(getCadena(icono2)).append(",")
				.append(getCadena(icono3)).append(",")
				.append(getCadena(icono4)).append(",")
				.append(getCadena(icono5)).append(",")
				.append(getCadena(icono6)).append("]");

		return js.toString();
	}

}
