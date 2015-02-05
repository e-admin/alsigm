package fondos.vos;

import java.util.List;

public class InfoProdVigenteHistorico {

	// InfoProductorSerie productor;
	// boolean originalVigente;
	InfoProductorSerie sustituidor;
	ProductorSerieVO productorSerieSustituido;
	InfoProductorSerie infoProductorSerieSustituido;
	List listaPermisosDelSustituido;

	public InfoProductorSerie getInfoProductorSerieSustituido() {
		return infoProductorSerieSustituido;
	}

	public void setInfoProductorSerieSustituido(
			InfoProductorSerie infoProductorSerieSustituido) {
		this.infoProductorSerieSustituido = infoProductorSerieSustituido;
	}

	public ProductorSerieVO getProductorSerieSustituido() {
		return productorSerieSustituido;
	}

	public void setProductorSerieSustituido(
			ProductorSerieVO productorSerieSustituido) {
		this.productorSerieSustituido = productorSerieSustituido;
	}

	public List getListaPermisosDelSustituido() {
		return listaPermisosDelSustituido;
	}

	public void setListaPermisosDelSustituido(List listaPermisosDelSustituido) {
		this.listaPermisosDelSustituido = listaPermisosDelSustituido;
	}

	public InfoProductorSerie getSustituidor() {
		return sustituidor;
	}

	public void setSustituidor(InfoProductorSerie sustituidor) {
		this.sustituidor = sustituidor;
	}

	// private String getId(){
	// String id = Constants.STRING_EMPTY;
	// if (productor instanceof InfoProductorSerie)
	// id = ((InfoProductorSerie) productor).getIdDescriptor();
	// else if (productor instanceof InfoOrganoProductorSerie)
	// id = ((InfoOrganoProductorSerie) productor).getIdEnSistemaExterno();
	// return id;
	// }
	//
	// public boolean equals(Object other) {
	// if ( (this == other ) ) return true;
	// if ( !(other instanceof InfoProdVigenteHistorico) ) return false;
	// Object productor = this.getProductor();
	// if (productor == null) return false;
	// if ( !(productor instanceof InfoProductorSerie) ) return false;
	// if ( !(productor instanceof InfoOrganoProductorSerie) ) return false;
	// InfoProdVigenteHistorico castOther = (InfoProdVigenteHistorico) other;
	// return new EqualsBuilder()
	// .append(this.getId(), castOther.getId())
	// .isEquals();
	// }

}
