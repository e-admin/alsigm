package ieci.core.db;

public class InfoOuterJoin {
	private String partnerOuterTableName = null;

	private DbColumnDef partnerOuterColDef = null;

	public DbColumnDef getPartnerOuterColDef() {
		return partnerOuterColDef;
	}

	public void setPartnerOuterColDef(DbColumnDef partnerOuterColDef) {
		this.partnerOuterColDef = partnerOuterColDef;
	}

	public String getPartnerOuterTableName() {
		return partnerOuterTableName;
	}

	public void setPartnerOuterTableName(String partnerOuterTableName) {
		this.partnerOuterTableName = partnerOuterTableName;
	}

}
