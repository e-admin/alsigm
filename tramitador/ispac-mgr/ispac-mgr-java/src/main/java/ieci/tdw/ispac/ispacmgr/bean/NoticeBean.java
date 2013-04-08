/*
 * invesflow Java - ISPAC
 * Fuente: NoticeBean.java
 * Creado el 25-may-2005 por juanin
 *
 */
package ieci.tdw.ispac.ispacmgr.bean;

import java.util.HashMap;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.notices.Notices;

/**
 * @author juanin
 *
 * Añade dos propiedades nuevas resultado de la traducción de propiedades
 * númericas a su representación textual:
 * <p>
 * <code>ESTADO_AVISO</code> en <code>ESTADO_AVISO_TEXT</code>
 * <p>
 * <code>TIPO_AVISO</code> en <code>TIPO_AVISO_TEXT</code>
 *
 */
public class NoticeBean extends ItemBean
{
	public static HashMap ESTADOAVISOMAP = new HashMap();
	public static HashMap TIPOAVISOMAP = new HashMap();

    static
    {
        ESTADOAVISOMAP.put(null,"Desconocido");
        ESTADOAVISOMAP.put(""+Notices.STATE_PENDIENTE,"Pendiente");
        ESTADOAVISOMAP.put(""+Notices.STATE_RECIBIDO,"EnCurso");
        ESTADOAVISOMAP.put(""+Notices.STATE_FINALIZADO,"Tratado");

        TIPOAVISOMAP.put(null,"Desconocido");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_SIGEM,"SIGEM");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_RT,"RT");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_EXTERNO,"EXTERNO");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_TRAMITE_DELEGADO,"TRAMITE_DELEGADO");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_EXPEDIENTE_INICIADO_WS,"EXPEDIENTE_INICIADO_WS");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_DOCS_ANEXADOS_WS,"DOCS_ANEXADOS_WS");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_EXP_REUBICADO_WS,"EXP_REUBICADO_WS");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_EXP_CAMBIO_ESTADO_ADM_WS,"EXP_CAMBIO_ESTADO_ADM_WS");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_FASE_DELEGADA, "FASE_DELEGADA");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_ACTIVIDAD_DELEGADA, "ACTIVIDAD_DELEGADA");
        TIPOAVISOMAP.put(""+Notices.TIPO_AVISO_TRAMITE_FINALIZADO,"TRAMITE_FINALIZADO");

    }

	public IItem processItem(IItem item)
	throws ISPACException
	{
	    String estadoAviso="noticias.estado."+ (String)ESTADOAVISOMAP.get(item.getString("SPAC_AVISOS_ELECTRONICOS:ESTADO_AVISO"));
	    String tipoAviso="noticias.tipo." + (String)TIPOAVISOMAP.get(item.getString("SPAC_AVISOS_ELECTRONICOS:TIPO_AVISO"));
	    setProperty("ESTADO_AVISO_TEXT",estadoAviso);
	    setProperty("TIPO_AVISO_TEXT",tipoAviso);
		return item;
	}
}
