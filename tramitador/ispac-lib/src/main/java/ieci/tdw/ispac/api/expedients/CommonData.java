package ieci.tdw.ispac.api.expedients;

import java.util.Date;
import java.util.List;

public class CommonData {
	
	private String organismId;
	private String subjectType;
	private String registerNumber;
	private Date registerDate;
	private List interested;
	
	public CommonData() {
	}
	
	public CommonData(String organismId,
					  String subjectType,
					  String registerNumber,
					  Date registerDate,
					  List interested) {
		
		this.organismId = organismId;
		this.subjectType = subjectType;
		this.registerNumber = registerNumber;
		this.registerDate = registerDate;
		this.interested = interested;
	}
	
	/**
	 * @return Returns the registerDate.
	 */
	public Date getRegisterDate() {
		return registerDate;
	}
	/**
	 * @param registerDate The registerDate to set.
	 */
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	/**
	 * @return Returns the registerNumber.
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}
	/**
	 * @param registerNumber The registerNumber to set.
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}
	
	/**
	 * @return Returns the subjectType.
	 */
	public String getSubjectType() {
		return subjectType;
	}
	/**
	 * @param subjectType The subjectType to set.
	 */
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	/**
	 * @return Returns the interested.
	 */
	public List getInterested() {
		return interested;
	}
	/**
	 * @param interested The interested to set.
	 */
	public void setInterested(List interested) {
		this.interested = interested;
	}

	public String getOrganismId() {
		return organismId;
	}

	public void setOrganismId(String organismId) {
		this.organismId = organismId;
	}
	
}