package ieci.tecdoc.sgm.consulta_telematico.manager;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;

public interface ObtenerDetalleManager {

	public XmlDocument getDetalleRegistro(String numRegistro, ieci.tecdoc.sgm.core.services.dto.Entidad idEntidad) throws Exception;
}
