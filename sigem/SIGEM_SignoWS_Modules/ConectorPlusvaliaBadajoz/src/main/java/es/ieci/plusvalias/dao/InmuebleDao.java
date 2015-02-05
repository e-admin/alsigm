package es.ieci.plusvalias.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.domain.InmuebleDTO;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;

public class InmuebleDao extends HibernateDaoSupport
{
	protected final Log logger = LogFactory.getLog(getClass());

	public InmuebleDTO findByTableOLD(final String tabla, final String refcatastral)
	{
		logger.debug("getting InmuebleDTO");
		
		try
		{
			InmuebleDTO inmuebleDto = (InmuebleDTO) getHibernateTemplate().execute(new HibernateCallback()
			{
				public Object doInHibernate(Session session) throws HibernateException, SQLException
				{

					String tablaNew = tabla.toUpperCase();
					String refCatastralNew = refcatastral.trim() + "%";

					SQLQuery sql = session.createSQLQuery("select * from " + tablaNew + " where refcatastral like '" + refCatastralNew + "'");
					Object object[] = (Object[]) sql.uniqueResult();

					if (object == null)
					{
						return null;
					}
					
					InmuebleDTO inmuebleDto = new InmuebleDTO();
					BigDecimal valorSuelo = (BigDecimal) object[13];
					BigDecimal valorConstr = (BigDecimal) object[14];
					BigDecimal supSolar = (BigDecimal) object[10];

					inmuebleDto.setValsuelo(new Double(valorSuelo.doubleValue()));
					inmuebleDto.setValconstruido(new Double(valorConstr.doubleValue()));
					inmuebleDto.setSupsolar(new Double(supSolar.doubleValue()));

					return inmuebleDto;
				}
			});

			if (inmuebleDto == null)
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instances found");
			}
			
			return inmuebleDto;
		}
		catch (RuntimeException re)
		{
			logger.error("get failed", re);
			throw re;
		}
	}

	public Inmueble findByRefCatastral(final String refCatastral, final Map propiedades)
	{
		Session sesion = null;
		Inmueble inmueble = null;
		List lista = null;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("getting InmuebleDTO:" + refCatastral);
		}
		
		String referencia = refCatastral;
		
		if (referencia.length() > 18)
		{
			referencia = referencia.substring(0, 18);
		}
		
		try
		{
			sesion = getSession();
			
			SQLQuery sql = sesion.createSQLQuery("select REFCATASTRAL, FIJOCATASTRAL, NIFSUJETO, NOMSUJETO, SIGVIA, NOMVIA,"
					+ " NUMERO, ESCALERA, PLANTA, PUERTA, SUPSOLAR, COEPRO, SUPCONSTRUIDA, VALSUELO, VALCONSTRUIDO, VALCATASTRAL "
					+ "from " + (String)propiedades.get("tabla") + " where refcatastral like '" + referencia.trim() + "%" + "'")
					.addScalar("REFCATASTRAL", Hibernate.STRING)
					.addScalar("FIJOCATASTRAL", Hibernate.STRING)
					.addScalar("NIFSUJETO", Hibernate.STRING)
					.addScalar("NOMSUJETO", Hibernate.STRING)
					.addScalar("SIGVIA", Hibernate.STRING)
					.addScalar("NOMVIA", Hibernate.STRING)
					.addScalar("NUMERO", Hibernate.STRING)
					.addScalar("ESCALERA", Hibernate.STRING)
					.addScalar("PLANTA", Hibernate.STRING)
					.addScalar("PUERTA", Hibernate.STRING)
					.addScalar("SUPSOLAR", Hibernate.STRING)
					.addScalar("COEPRO", Hibernate.STRING)
					.addScalar("SUPCONSTRUIDA", Hibernate.STRING)
					.addScalar("VALSUELO", Hibernate.STRING)
					.addScalar("VALCONSTRUIDO", Hibernate.STRING)
					.addScalar("VALCATASTRAL", Hibernate.STRING);
			
			lista = sql.list();

			if (lista.isEmpty() || lista.size() > 1)
			{
				logger.debug("get successful, no instance found");
			}
			else
			{
				logger.debug("get successful, instances found");
			}

			Object object[] = (Object[])lista.get(0);
			inmueble = new Inmueble();

			BigDecimal valorSuelo = new BigDecimal((String)object[13]);
			BigDecimal valorConstr = new BigDecimal((String)object[14]);
			BigDecimal supSolar = new BigDecimal((String)object[10]);
			String escalera = (String)object[7];
			String nombrevia = (String)object[5];
			String planta = (String)object[8];
			String numero = (String)object[6];
			String puerta = (String)object[9];
			String tipoVia = (String)object[4];
			BigDecimal valorCatastral = new BigDecimal((String)object[15]);

			inmueble.setRefCatastral(refCatastral);
			inmueble.setEscalera(escalera);
			inmueble.setNombrevia(nombrevia);
			inmueble.setNumero(numero);
			inmueble.setPlanta(planta);
			inmueble.setPuerta(puerta);
			inmueble.setSupsolar(supSolar.doubleValue());
			inmueble.setTipovia(tipoVia);
			inmueble.setValcatastral(valorCatastral.doubleValue());
			inmueble.setValsuelo(valorSuelo.doubleValue());
			inmueble.setValconstruido(valorConstr.doubleValue());
		}
		catch (Exception e)
		{
			if (e instanceof PlusvaliaException)
			
			logger.error(e.getMessage(), e);
			
			return null;
		}
		finally
		{
			lista = null;
			sesion.close();
			sesion = null;
		}

		return inmueble;
	}
}
