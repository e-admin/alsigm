package ieci.tdw.ispac.ispaclib.sign.portafirmas.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * Propiedades específicas de un proceso de firma
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ProcessSignProperties {

	String subject;

	Calendar fstart;

	Calendar fexpiration;

	String content;

	/**
	 * Nivel de Importancia. Sólo admite cuatro valores
	 *  NONE : No se asigna valor al nivel de importancia
	 *  A: Alta
	 *  M: Media
	 *  B: Baja
	 */
	String levelOfImportance;

	public ProcessSignProperties() {
		super();
	}

	public ProcessSignProperties(String subject, String fstart,
			String fexpiration, String content, String levelOfImportance) throws ParseException {
		super();
		this.subject = subject;
		this.content = content;
		this.levelOfImportance = levelOfImportance;
		// Si las fechas vienen informadas lo harán en formato dd/mm/YYYY

		this.fstart = GregorianCalendar.getInstance();
		this.fexpiration = GregorianCalendar.getInstance();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (StringUtils.isNotBlank(fstart)) {
			Date fstartD = dateFormat.parse(fstart);
			this.fstart.setTime(fstartD);
		}
		if (StringUtils.isNotBlank(fexpiration)) {
			Date fexpirationD = dateFormat.parse(fexpiration);
			this.fexpiration.setTime(fexpirationD);
		}

	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Calendar getFstart() {
		return fstart;
	}

	public void setFstart(Calendar fstart) {
		this.fstart = fstart;
	}

	public Calendar getFexpiration() {
		return fexpiration;
	}

	public void setFexpiration(Calendar fexpiration) {
		this.fexpiration = fexpiration;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLevelOfImportance() {
		return levelOfImportance;
	}

	public void setLevelOfImportance(String levelOfImportance) {
		this.levelOfImportance = levelOfImportance;
	}



}
