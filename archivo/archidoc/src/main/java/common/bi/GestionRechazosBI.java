package common.bi;

import java.util.List;

import solicitudes.vos.MotivoRechazoVO;

public interface GestionRechazosBI {

	public List getMotivosRechazo();

	public MotivoRechazoVO getMotivoRechazo(final MotivoRechazoVO motivoVO);

	public MotivoRechazoVO getMotivoRechazoById(final String idMotivo);

	public void insertMotivoRechazo(final MotivoRechazoVO motivoVO);

	public void deleteMotivoRechazo(final MotivoRechazoVO motivoVO);

	public void updateMotivoRechazo(final MotivoRechazoVO motivoVO);

	public boolean isReferenciado(final MotivoRechazoVO motivoVO);
}
