package es.ieci.tecdoc.isicres.api.business.vo;

public class ZonaHorariaVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 3548729318629217164L;

	protected int timezone;

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
}
