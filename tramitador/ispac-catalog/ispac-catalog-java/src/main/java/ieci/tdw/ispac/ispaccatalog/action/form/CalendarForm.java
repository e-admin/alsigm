package ieci.tdw.ispac.ispaccatalog.action.form;

public class CalendarForm extends EntityForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] weekDaysSelect;
	private String[] selectFixedHolidays;
	private String year;
	
	
	public CalendarForm () {
		super();
		weekDaysSelect = null;	
	}

	public String[] getWeekDaysSelect() {
		return weekDaysSelect;
	}

	public void setWeekDaysSelect(String[] weekDaysSelect) {
		this.weekDaysSelect = weekDaysSelect;
	}

	public String[] getSelectFixedHolidays() {
		return selectFixedHolidays;
	}

	public void setSelectFixedHolidays(String[] selectFixedHolidays) {
		this.selectFixedHolidays = selectFixedHolidays;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
