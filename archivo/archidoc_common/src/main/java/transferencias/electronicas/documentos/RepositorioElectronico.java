package transferencias.electronicas.documentos;


public class RepositorioElectronico extends Repositorio{

	/**
	 *
	 */
	private static final long serialVersionUID = 7336978840741300443L;

	/**
	 * {@inheritDoc}
	 * @see transferencias.electronicas.documentos.Repositorio#isDepositoElectronico()
	 */
	@Override
	public boolean isDepositoElectronico() {
		return true;
	}

	public boolean isEcm() {
		return false;
	}

}
