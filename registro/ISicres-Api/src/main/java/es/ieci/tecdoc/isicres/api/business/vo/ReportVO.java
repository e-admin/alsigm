package es.ieci.tecdoc.isicres.api.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;

public class ReportVO extends BaseIsicresApiVO {

	/**
	 * Constructor por defecto.
	 */
	public ReportVO() {
		// nothing to do
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TipoPlantillaInformeEnum getReportType() {
		return reportType;
	}

	public void setReportType(TipoPlantillaInformeEnum reportType) {
		this.reportType = reportType;
	}

	public TipoLibroEnum getBookType() {
		return bookType;
	}

	public void setBookType(TipoLibroEnum bookType) {
		this.bookType = bookType;
	}

	public boolean isCanBeUsedByAllBooks() {
		return canBeUsedByAllBooks;
	}

	public void setCanBeUsedByAllBooks(boolean canBeUsedByAllBooks) {
		this.canBeUsedByAllBooks = canBeUsedByAllBooks;
	}

	public boolean isCanBeUsedByAllOffices() {
		return canBeUsedByAllOffices;
	}

	public void setCanBeUsedByAllOffices(boolean canBeUsedByAllOffices) {
		this.canBeUsedByAllOffices = canBeUsedByAllOffices;
	}

	public boolean isCanBeUsedByAllProfiles() {
		return canBeUsedByAllProfiles;
	}

	public void setCanBeUsedByAllProfiles(boolean canBeUsedByAllProfiles) {
		this.canBeUsedByAllProfiles = canBeUsedByAllProfiles;
	}

	// Members
	protected static final long serialVersionUID = -7601586942482361260L;

	protected String id;

	protected String name;

	protected String description;

	protected TipoPlantillaInformeEnum reportType;

	protected TipoLibroEnum bookType;

	protected boolean canBeUsedByAllBooks;

	protected boolean canBeUsedByAllOffices;

	protected boolean canBeUsedByAllProfiles;
}
