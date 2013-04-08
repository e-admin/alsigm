package ieci.tecdoc.sgm.pe.pasarela;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ConectorPasarelaPago {
	public void redireccionarAPasarela(HttpServletRequest request,HttpServletResponse response);
}
