package transferencias.electronicas.documentos;

public interface IRepositorio {
	public boolean isDepositoElectronico();
	public boolean isEcm();
	public String getIdRepositorio();
	public String getLocalizador();
}
