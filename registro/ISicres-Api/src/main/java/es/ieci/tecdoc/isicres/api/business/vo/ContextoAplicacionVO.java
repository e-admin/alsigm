package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * Clase para almacenar la información actual con la que está funcionando
 * sicres acerca de usuario actual, libroActual y oficina actual
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 * 
 */
public class ContextoAplicacionVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -7642705322203410749L;

	protected UsuarioVO usuarioActual;

	protected BaseLibroVO libroActual;

	protected BaseOficinaVO oficinaActual;
	
	protected BaseRegistroVO registroActual;

	public UsuarioVO getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(UsuarioVO usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public BaseLibroVO getLibroActual() {
		if (null == libroActual) {
			libroActual = new BaseLibroVO();
		}
		return libroActual;
	}

	public void setLibroActual(BaseLibroVO libroActual) {
		this.libroActual = libroActual;
	}

	public BaseOficinaVO getOficinaActual() {
		return oficinaActual;
	}

	public void setOficinaActual(BaseOficinaVO oficinaActual) {
		this.oficinaActual = oficinaActual;
	}

	public BaseRegistroVO getRegistroActual() {
		return registroActual;
	}

	public void setRegistroActual(BaseRegistroVO registroActual) {
		this.registroActual = registroActual;
	}

}
