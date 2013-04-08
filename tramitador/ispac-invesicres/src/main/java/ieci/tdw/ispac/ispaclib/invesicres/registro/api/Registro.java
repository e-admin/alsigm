package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DocumentoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.InteresadoVO;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterBook;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOffice;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author RAULHC
 *
 */
public class Registro {
	
	protected static final int INVESICRES_BOOK_TYPE_STANDARD = 0;
	protected static final int INVESICRES_BOOK_TYPE_INPUT = 1;
	protected static final int INVESICRES_BOOK_TYPE_OUTPUT = 2;
	
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Registro.class);
	
	private InveSicresConfiguration mConfig;
	private TercerosImpl mTerceros = null;
			
	InvesDocConnection invesDocConnection = null;
	
	public Registro(InvesDocConnection invesDocConnection) throws ISPACException {
		this.invesDocConnection = invesDocConnection;
		mConfig = InveSicresConfiguration.getInstance();
		mTerceros = new TercerosImpl(invesDocConnection);
	}
	
	public Date getDateTime() {
		Calendar c = new GregorianCalendar();
		return c.getTime();
	}
	
	public Date getDate() throws ISPACException {
		Calendar calendary = GregorianCalendar.getInstance();
		calendary.clear(GregorianCalendar.HOUR);
		calendary.clear(GregorianCalendar.HOUR_OF_DAY);
		calendary.clear(GregorianCalendar.HOUR_OF_DAY);
		calendary.clear(GregorianCalendar.MINUTE);
		calendary.clear(GregorianCalendar.SECOND);
		calendary.clear(GregorianCalendar.MILLISECOND);
		Date actual = calendary.getTime();
		return actual;
	}
	
	/**
	 * Obtener la lista de interesados de un registro.
	 * @param archiveId Identificador del Archivador del Registro
	 * @param folderId Identificador de la carpeta del Registro
	 * @return Retorna una lista de InteresadoVO
	 * @throws ISPACException
	 */
	public LinkedList getInteresados(int archiveId, int folderId) throws ISPACException {
		LinkedList interesados = new LinkedList();
		LinkedList rows = invesDocConnection.DbSelectNCNR("ID,NAME,ID_PERSON,ID_ADDRESS,ORD",
				mConfig.get(InveSicresConfiguration.TBL_REGINT),"WHERE ID_ARCH = " + archiveId + " AND ID_FDR = " + folderId +
				" ORDER BY ORD");
		Iterator it = rows.iterator();
		while (it.hasNext()) {
			LinkedHashMap row = (LinkedHashMap)it.next();
			InteresadoVO interesadoVO = new InteresadoVO();
			interesadoVO.setId((String)row.get("ID"));
			interesadoVO.setIdDireccion((String)row.get("ID_ADDRESS"));
			interesadoVO.setIdTercero((String)row.get("ID_PERSON"));
			interesadoVO.setNombre((String)row.get("NAME"));
			interesadoVO.setOrden((String)row.get("ORD"));
			interesados.add(interesadoVO);
		}
		return interesados;
	}
	
	public String getScrIdFromCode(String tableId, String code) throws ISPACException {
		String id = null;
		if (tableId != null && tableId.length() > 0 && code != null && code.length() > 0) {
			String qual = "WHERE CODE = '" + code + "'";
			id = invesDocConnection.DbSelect1C1R("ID", tableId, qual);
		}
		return id;
	}

	public String getScrCodeFromId(String tableId, String id) throws ISPACException {
		String code = null;
		if (tableId != null && tableId.length() > 0 && id != null && id.length() > 0) {
			String qual = "WHERE ID = " + id;
			code = invesDocConnection.DbSelect1C1R("CODE", tableId, qual);
		}
		return code;
	}

	public String getScrNameFromId(String tableId, String id) throws ISPACException {
		String name = null;
		if (tableId != null && tableId.length() > 0 && id != null && id.length() > 0) {
			String qual = "WHERE ID = " + id;
			name = invesDocConnection.DbSelect1C1R("NAME", tableId, qual);
		}
		return name;
	}

	public String getScrNameFromCode(String tableId, String code) throws ISPACException {
		String name = null;
		if (tableId != null && tableId.length() > 0 && code != null && code.length() > 0) {
			String qual = "WHERE CODE = '" + code + "'";
			name = invesDocConnection.DbSelect1C1R("NAME", tableId, qual);
		}
		return name;
	}

	public String getScrPropertyById(String tableId, String id, String nameProperty) throws ISPACException {
		String name = null;
		if (tableId != null && tableId.length() > 0 && id != null && id.length() > 0) {
			String qual = "WHERE ID = " + id;
			name = invesDocConnection.DbSelect1C1R(nameProperty, tableId, qual);
		}
		return name;
	}

	public String getScrPropertyByPropertyCode(String table, String namePropertyCode,
            String propertyCode, String nameScrProperty) throws ISPACException {
		String name = null;
		if (table != null && table.length() > 0 && namePropertyCode != null && namePropertyCode.length() > 0) {
			String qual = "WHERE "  + namePropertyCode + " = '" + DBUtil.replaceQuotes(propertyCode) + "' ";
			name = invesDocConnection.DbSelect1C1R(nameScrProperty, table, qual);
		}
		return name;
	}
	
	public Integer getOrgIdFromCif(String cif) throws ISPACException {
		Integer result = null;
		if (cif != null && cif.length() > 0) {
			String qual = "WHERE CIF = '" + cif + "'";
			String id = invesDocConnection.DbSelect1C1R("ID", mConfig.get(InveSicresConfiguration.TBL_ORGS), qual);
			if (id == null && id.length() > 0) result = new Integer(id);
		}
		return result;
	}
	
	/**
	 * Obtiene la descripción de un asunto dado su identificador
	 * @param strId Identificador del asunto
	 * @return
	 * @throws ISPACException
	 */
	public String getTipoAsuntoById(String strId) throws ISPACException {
		String tipoAsunto = null;
		if (strId != null && strId.length() > 0) {
			String qual = "WHERE ID = " + strId;
			tipoAsunto = invesDocConnection.DbSelect1C1R("MATTER", mConfig.get(InveSicresConfiguration.TBL_CA), qual);
		}
		return tipoAsunto;
	}
	
	
	/**
	 * Obtiene un identificador único
	 * @return Identificador único
	 * @throws ISPACException si ocurre algún error.
	 */
	private int getContadorScr(String tablaID) throws ISPACException {

		int id = -1;

		String commandSelect = "SELECT CONTADOR FROM " + mConfig.get(InveSicresConfiguration.TBL_CONTADOR) +
		" WHERE TABLAID=?"; // FOR UPDATE";
		String commandUpdate = "UPDATE " + mConfig.get(InveSicresConfiguration.TBL_CONTADOR) + 
		" SET CONTADOR=CONTADOR+1 WHERE TABLAID=?";

		PreparedStatement prepstmt = null;
		ResultSet rs = null;
		
		try {           
			if (invesDocConnection.isConnected()) {                                     
				prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
				prepstmt.setString(1, tablaID);                            
				rs = prepstmt.executeQuery();

				if (rs.next()) {
					id = rs.getInt("CONTADOR") + 1;

					rs.close();
					rs = null;
					
					prepstmt.close();
					prepstmt = null;

					prepstmt = invesDocConnection.getConnection().prepareStatement(commandUpdate);
					prepstmt.setString(1, tablaID); 
					prepstmt.executeUpdate();
				}                        
			}            
		}
		catch (SQLException se) {
			throw new ISPACException("Error al obtener contador para [" + tablaID + "]", se);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
		}

		return id;
	}	

	/**
	 * Obtiene un identificador único
	 * @return Identificador único
	 * @throws ISPACException si ocurre algún error.
	 */
	private int getNextVal(String tablaID) throws ISPACException {

		int id = -1;
		String commandSelect = null;
		
		if (invesDocConnection.isSqlServerDbEngine()) {
			return getContadorScr(tablaID);
		} else if (invesDocConnection.isPostgreSQLDbEngine()) {
			commandSelect = "SELECT NEXTVAL('" + mConfig.get(InveSicresConfiguration.SEQCNT) + "')";
		} else if (invesDocConnection.isOracleDbEngine()) {
			commandSelect = "SELECT " + mConfig.get(InveSicresConfiguration.SEQCNT) + ".NEXTVAL FROM DUAL";
		} else if (invesDocConnection.isDB2DbEngine()) {
			commandSelect = "VALUES NEXTVAL FOR " + mConfig.get(InveSicresConfiguration.SEQCNT);
		}

		if (commandSelect != null) {
			
			PreparedStatement prepstmt = null;
			ResultSet rs = null;
			
			try {
				if (invesDocConnection.isConnected()) {
					prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
					rs = prepstmt.executeQuery();
	
					if (rs.next()) {
						id = rs.getInt(1);
					}
				}
			} catch (SQLException se) {
				throw new ISPACException("Error al obtener identificador único", se);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						logger.warn("Error al cerrar el resultSet", e);
					}
				}
				if (prepstmt != null) {
					try {
						prepstmt.close();
					} catch (SQLException e) {
						logger.warn("Error al cerrar el statement", e);
					}
				}
			}
		}

		return id;
	}
    
    public InteresadoVO insertInteresado(int archiveId, int folderId, InteresadoVO interesado) throws ISPACException {
    	String tblRegInt = mConfig.get(InveSicresConfiguration.TBL_REGINT);
    	String qual = "WHERE ORD = (SELECT MAX(ORD) FROM SCR_REGINT WHERE ID_ARCH="+
    	              Integer.toString(archiveId)+ " AND ID_FDR="+Integer.toString(folderId)+
    	              ") AND ID_ARCH="+Integer.toString(archiveId)+" AND ID_FDR="+Integer.toString(folderId);
    	//Obtener Orden del Interesado
    	String orden = invesDocConnection.DbSelect1C1R("ORD", 
    			tblRegInt, qual);
    	if (orden == null) 
    	    orden = "1";
    	else
    	    orden = Integer.toString(Integer.parseInt(orden)+1);
    	
    	//Obtener siguiente id para la tabla SCR_REGINT
    	//int id = getContadorScr(tblRegInt);
    	int id = getNextVal(tblRegInt);
    	
    	//Insertar el interesado en la tabla SCR_REGINT
    	String commandInsert = 
    		"INSERT INTO " + tblRegInt +
			"(ID, ID_ARCH, ID_FDR, NAME, ID_PERSON, ID_ADDRESS, ORD) " +
			"VALUES(?, ?, ?, ?, ?, ?, ?)";
    	/*
    	System.out.println("INSERT INTO " + tblRegInt + "(ID, ID_ARCH, ID_FDR, NAME, ID_PERSON, ID_ADDRESS, ORD) " +
    			"VALUES(" + id + "," +
    			archiveId + "," +
    			folderId +"," +
    			interesado.getNombre() + "," +
    			interesado.getIdTercero() + "," +
    			interesado.getIdDireccion() + "," + 
    			orden + ")");
    			*/
    	if (invesDocConnection.isConnected()) {
    		try {
				PreparedStatement prepstmt = invesDocConnection.getConnection().prepareStatement(commandInsert);
				prepstmt.setInt(1, id); //Id unico del interesado
				prepstmt.setInt(2, archiveId); //Identificador del Archivador del Registro
				prepstmt.setInt(3, folderId);  //Identificador de la carpeta del Registro
				prepstmt.setString(4, interesado.getNombre()); //Nombre del Interesado
				String personId = interesado.getIdTercero(); //Identificador de la persona
				if (StringUtils.isBlank(personId)) {
					//prepstmt.setInt(5, Integer.parseInt(personId));
				    personId = "0";
				}// else {
					//prepstmt.setNull(5, Types.INTEGER);
				//}
				prepstmt.setInt(5, Integer.parseInt(personId));
				
				String addressId = interesado.getIdDireccion(); //Identificador de la direccion de notificacion
				if (addressId != null && !addressId.equalsIgnoreCase("0")) {
					prepstmt.setInt(6, Integer.parseInt(addressId));
				} else {
					prepstmt.setNull(6, Types.INTEGER);
				}
				prepstmt.setInt(7, Integer.parseInt(orden)); //Orden del Interesado en el Registro
				prepstmt.executeUpdate();
				interesado.setId(Integer.toString(id));
				interesado.setOrden(orden);
			} catch (Exception e) {
				throw new ISPACException("Error al insertar interesado.", e);
			}
    	}
    	return interesado;
    }
    
    public String getNumRegistroOficina(int ofic, int arch, int tipoRegistro) throws ISPACException{
    	String tblCntOficina = mConfig.get(InveSicresConfiguration.TBL_CNTOFICINA);
    	Date now = getDateTime();
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(now);
    	int year = gc.get(Calendar.YEAR);
    	
        String commandSelect = "SELECT NUM_REG FROM " + tblCntOficina + " WHERE AN=? AND OFICINA=? AND ID_ARCH=?"; // FOR UPDATE";
        String commandUpdate = "UPDATE " + tblCntOficina + " SET NUM_REG=NUM_REG+1 WHERE AN=? AND OFICINA=? AND ID_ARCH=?";
        String commandInsert = "INSERT INTO " + tblCntOficina + "(AN, OFICINA, NUM_REG, ID_ARCH) VALUES(?, ?, ?, ?)";
        
        int id = -1;
        
        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {           
            if (invesDocConnection.isConnected()) {                                     
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, year);
                prepstmt.setInt(2, ofic);
                //prepstmt.setInt(3, arch);
                prepstmt.setInt(3, tipoRegistro);
                rs = prepstmt.executeQuery();
                            
                if (rs.next())  {
                    id = rs.getInt("NUM_REG") + 1;
                                        
                    rs.close();
                    rs = null;
                    
                    prepstmt.close();
                    prepstmt = null;
                
                    prepstmt = invesDocConnection.getConnection().prepareStatement(commandUpdate);
                    prepstmt.setInt(1, year);
                    prepstmt.setInt(2, ofic);
                    //prepstmt.setInt(3, arch);
                    prepstmt.setInt(3, tipoRegistro);
                    prepstmt.executeUpdate();
                }
                else {                
                    id = 1;
                    
                    rs.close();
                    rs = null;

                    prepstmt.close();
                    prepstmt = null;
                
                    prepstmt = invesDocConnection.getConnection().prepareStatement(commandInsert);
                    prepstmt.setInt(1, year);
                    prepstmt.setInt(2, ofic);
                    prepstmt.setInt(3, 1);
                    //prepstmt.setInt(4, arch);
                    prepstmt.setInt(4, tipoRegistro);
                    prepstmt.executeUpdate();                    
                }
            }            
        } catch (SQLException se) {
        	throw new ISPACException("Error al obtener número de regitro para el archivador con id = " + Integer.toString(arch), se);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
		
        return composeNumRegistroOficina(year, ofic, id);    
    }    
        
    private String composeNumRegistroOficina(int year, int ofic, int numReg) throws ISPACException {
    	NumberFormat formatter = new DecimalFormat("00000000");
    	String oficCode = getScrCodeFromId(mConfig.get(InveSicresConfiguration.TBL_OFIC), Integer.toString(ofic));
    	return Integer.toString(year) + oficCode + formatter.format(numReg);
    }
    
    public boolean existNumRegistroCentral(int arch, String numRegistroCentral) throws ISPACException {
    	String tblCntCentral = mConfig.get(InveSicresConfiguration.TBL_CNTCENTRAL);
    	Date now = getDateTime();
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(now);
    	int year = gc.get(Calendar.YEAR);
    	boolean ret = false;
    	
        String commandSelect = "SELECT NUM_REG FROM " + tblCntCentral + " WHERE AN=? AND ID_ARCH=?"; // FOR UPDATE";
        
        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {           
            if (invesDocConnection.isConnected()) {                                     
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, year);
                prepstmt.setInt(2, arch);
                rs = prepstmt.executeQuery();
                if (rs.next()) {
                    ret = true;
                } else {
                    ret = false;
                }
            }            
            
        } catch (SQLException se) {
        	throw new ISPACException(
                    "Error al obtener número de regitro para el archivador con id = "
                            + Integer.toString(arch), se);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
        
        return ret;
            
    }
    
    public String getNumRegistroCentral(int arch, int tipoRegistro) throws ISPACException{
    	String tblCntCentral = mConfig.get(InveSicresConfiguration.TBL_CNTCENTRAL);
    	Date now = getDateTime();
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(now);
    	int year = gc.get(Calendar.YEAR);
    	
        String commandSelect = "SELECT NUM_REG FROM " + tblCntCentral + " WHERE AN=? AND ID_ARCH=?"; // FOR UPDATE";
        String commandUpdate = "UPDATE " + tblCntCentral + " SET NUM_REG=NUM_REG+1 WHERE AN=? AND ID_ARCH=?";
        String commandInsert = "INSERT INTO " + tblCntCentral + "(AN, NUM_REG, ID_ARCH) VALUES(?, ?, ?)";
        
        int id = -1;
        
        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {           
            if (invesDocConnection.isConnected()) {                                     
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, year);
                //prepstmt.setInt(2, arch);
                prepstmt.setInt(2, tipoRegistro);
                rs = prepstmt.executeQuery();
                            
                if (rs.next())  {
                    id = rs.getInt("NUM_REG") + 1;
                                        
                    rs.close();
                    rs = null;
                    
                    prepstmt.close();
                    prepstmt = null;
                
                    prepstmt = invesDocConnection.getConnection().prepareStatement(commandUpdate);
                    prepstmt.setInt(1, year);
                    //prepstmt.setInt(2, arch);
                    prepstmt.setInt(2, tipoRegistro);
                    prepstmt.executeUpdate();
                }
                else {                
                    id = 1;
                    
                    rs.close();
                    rs = null;
                    
                    prepstmt.close();
                    prepstmt = null;
                
                    prepstmt = invesDocConnection.getConnection().prepareStatement(commandInsert);
                    prepstmt.setInt(1, year);
                    prepstmt.setInt(2, 1);
                    //prepstmt.setInt(3, arch);
                    prepstmt.setInt(3, tipoRegistro);
                    prepstmt.executeUpdate();                    
                }
            }            
        } catch (SQLException se) {
        	throw new ISPACException("Error al obtener número de regitro para el archivador con id = " + Integer.toString(arch), se);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
		
        return composeNumRegistroCentral(year, id);    
    }    
        
    private String composeNumRegistroCentral(int year, int numReg) throws ISPACException {
    	NumberFormat formatter = new DecimalFormat("00000000");
    	return Integer.toString(year) + formatter.format(numReg);
    }
    
    
    public LinkedList getDocumentos(String archiveId, String folderId) throws ISPACException {
    	String columns = "P.ID AS PAGEID, P.DOCID AS DOCID, D.NAME AS DOCNAME, P.NAME AS PAGENAME, P.LOC AS LOC";
    	String tables = "A" + archiveId + "PAGEH P, A" + archiveId + "DOCH D";
    	String qual = "WHERE P.FDRID = " + folderId + " AND D.ID = P.DOCID";
    	LinkedList documentos = null;
    	
    	LinkedList documentosDb = invesDocConnection.DbSelectNCNR(columns, tables, qual);
		if (documentosDb != null && documentosDb.size() > 0) {
			Iterator it = documentosDb.iterator();
			documentos = new LinkedList();
			while (it.hasNext()) {
				LinkedHashMap row = (LinkedHashMap)it.next();
				DocumentoVO documentoVO = new DocumentoVO();
				documentoVO.setDocId((String)row.get("DOCID"));
				documentoVO.setDocName((String)row.get(("DOCNAME")));
				documentoVO.setLoc((String)row.get("LOC"));
				documentoVO.setPageId((String)row.get("PAGEID"));
				documentoVO.setPageName((String)row.get("PAGENAME"));
				documentos.add(documentoVO);
			}
		}
    	return documentos;
    }
    
    private DocumentoVO getDocumento(String archiveId, String folderId, String pageName) throws ISPACException {
    	String columns = "P.ID AS PAGEID, P.DOCID AS DOCID, D.NAME AS DOCNAME, P.NAME AS PAGENAME, P.LOC AS LOC";
    	String tables = "A" + archiveId + "PAGEH P, A" + archiveId + "DOCH D";
    	String qual = "WHERE P.FDRID = " + folderId + " AND D.ID = P.DOCID AND P.NAME = '" + DBUtil.replaceQuotes(pageName) + "'";
  
    	DocumentoVO documentoVO = null;
    	
    	LinkedHashMap documento = invesDocConnection.DbSelectNC1R(columns, tables, qual);
    	if (documento != null && documento.size() > 0) {
    		documentoVO = new DocumentoVO();
    		documentoVO.setDocId((String)documento.get("DOCID"));
    		documentoVO.setDocName((String)documento.get("DOCNAME"));
    		documentoVO.setLoc((String)documento.get("LOC"));
    		documentoVO.setPageId((String)documento.get("PAGEID"));
    		documentoVO.setPageName((String)documento.get(("PAGENAME")));
    	}
    	
    	return documentoVO;
    }
    
    private String getExtension(String fileName) {
        String extension = "";
        int whereDot = fileName.lastIndexOf( '.' );
        
        if ( 0 < whereDot && whereDot <= fileName.length()-2 )    
            extension = fileName.substring(whereDot + 1);   

        return extension;
    }	
    
	public DocumentoVO addDocumento(String archiveId, String folderId, DocumentoVO documentoVO) throws ISPACException {
		FolderObject registroFdr = null;
		File f = new File(documentoVO.getPath());
		if (f.exists()) {
			try {
				registroFdr = invesDocConnection.LoadFolderFromId(
						Integer.parseInt(archiveId), Integer.parseInt(folderId));
			} catch (NumberFormatException e) {
				throw new ISPACException("Parámetros incorrectos.", e);
			}
			String ext = getExtension(f.getName());
			if (ext.length() == 0) ext = "-";
			try {
				//invesDocConnection.StartTrans();
				registroFdr.addRootDocument(documentoVO.getPageName(), ext, f.getAbsolutePath());
				invesDocConnection.storeFolder(Integer.parseInt(archiveId), registroFdr);
				updateSortOrder(archiveId, folderId);
				
				//invesDocConnection.CommitTrans();
			} catch (Exception e1) {
				//invesDocConnection.RollbackTrans();
				throw new ISPACException("Error al anexar el fichero al registro.", e1);
			}
		} else {
			throw new ISPACException("El fichero no existe en la ruta indicada: " + documentoVO.getPath());
		}
		return getDocumento(archiveId, folderId, documentoVO.getPageName());
	}
	
    
	/**
     * PEAZO ÑAPA PARA QUE NO CASQUE SICRES
	 * @param archiveId
	 * @param folderId
	 */
	private void updateSortOrder(String archiveId, String folderId)
			throws ISPACException {
		String commandUpdate = "UPDATE A" + archiveId + "PAGEH SET A"
				+ archiveId + "PAGEH.SORTORDER=1 WHERE A" + archiveId
				+ "PAGEH.FDRID=" + folderId + " ";
		try {
			if (invesDocConnection.isConnected()) {
				PreparedStatement prepstmt = invesDocConnection.getConnection()
						.prepareStatement(commandUpdate);
				prepstmt.execute();
				prepstmt.close();
			}
		} catch (SQLException se) {
			throw new ISPACException("Error al actualizar SORTORDER ");
		}
	}

	public TercerosImpl getTerceros() {
    	return mTerceros;
    }
    
    public String getNumRegistro(int ofic, int arch, int tipoRegistro) throws ISPACException{
        String tblRegState = mConfig.get(InveSicresConfiguration.TBL_REGSTATE);
        String commandSelect = "SELECT NUMERATION_TYPE FROM " + tblRegState + " WHERE IDARCHREG = ?";
        int typeCnt = -1;
        
        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {
            if (invesDocConnection.isConnected()) {
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, arch);
                rs = prepstmt.executeQuery();
                if (rs.next()) {
                    typeCnt = rs.getInt("NUMERATION_TYPE");
                }
            } 
        } catch (SQLException se) {
         throw new ISPACException("Error al obtener número de regitro para el archivador con id = " + Integer.toString(arch), se);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
		
        if (typeCnt == 0) {
            return getNumRegistroCentral(arch, tipoRegistro);
        } else{
            return getNumRegistroOficina(ofic, arch, tipoRegistro);
        }
    }

    public List getBooks(int bookType) throws ISPACException {
    	
    	List books = new ArrayList();
    	
        String booksTbl = mConfig.get(InveSicresConfiguration.TBL_BOOKS);
        String regStateTbl = mConfig.get(InveSicresConfiguration.TBL_REGSTATE);
        
        String commandSelect = new StringBuffer("SELECT BOOKS.ID, BOOKS.NAME FROM ")
        	.append(booksTbl).append(" BOOKS, ")
        	.append(regStateTbl).append(" REGSTATE")
        	.append(" WHERE BOOKS.TYPE = ? AND BOOKS.ID=REGSTATE.IDARCHREG AND REGSTATE.STATE=0 ORDER BY BOOKS.NAME")
        	.toString();

        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {
            if (invesDocConnection.isConnected()) {
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, (bookType == IRegisterAPI.BOOK_TYPE_OUTPUT ? INVESICRES_BOOK_TYPE_OUTPUT : INVESICRES_BOOK_TYPE_INPUT));
                rs = prepstmt.executeQuery();
                while (rs.next()) {
                	RegisterBook book = new RegisterBook();
        			book.setId(rs.getString("ID"));
        			book.setName(rs.getString("NAME"));
        			book.setType(bookType);
        			
        			books.add(book);
                }
            } 
        } catch (Exception e) {
        	logger.error("Error al obtener los libros de registro de tipo [" + bookType + "]", e);
        	throw new ISPACException("Error al obtener los libros de registro", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
        
        return books; 
    }
    
    public List getOffices(String bookId) throws ISPACException {
    	
    	List offices = new ArrayList();
    	
        String officesTbl = mConfig.get(InveSicresConfiguration.TBL_OFIC);
        String bookoficTbl = mConfig.get(InveSicresConfiguration.TBL_BOOKOFIC);
        String commandSelect = "SELECT * FROM " + officesTbl + ", " + bookoficTbl 
        	+ " WHERE " + officesTbl + ".ID=" + bookoficTbl + ".ID_OFIC"
        	+ " AND " + bookoficTbl + ".ID_BOOK=?"
        	+ " ORDER BY " + officesTbl + ".NAME";
        
        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {
            if (invesDocConnection.isConnected()) {
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setInt(1, TypeConverter.parseInt(bookId, 0));
                rs = prepstmt.executeQuery();
                while (rs.next()) {
                	RegisterOffice office = new RegisterOffice();
        			office.setCode(rs.getString("CODE"));
        			office.setName(rs.getString("NAME"));
        			
        			offices.add(office);
                }
            } 
        } catch (Exception e) {
        	logger.error("Error al obtener las oficinas de registro del libro [" + bookId + "]", e);
        	throw new ISPACException("Error al obtener las oficinas de registro", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
        
        return offices; 
    }
    
    public Organization getOrganizationByCode(String code) throws ISPACException {
    	
    	Organization organization = null;
    	
        String orgsTbl = mConfig.get(InveSicresConfiguration.TBL_ORGS);
        String commandSelect = "SELECT * FROM " + orgsTbl + " WHERE CODE = ?";

        PreparedStatement prepstmt = null;
        ResultSet rs = null;
        
        try {
            if (invesDocConnection.isConnected()) {
                prepstmt = invesDocConnection.getConnection().prepareStatement(commandSelect);
                prepstmt.setString(1, code);
                rs = prepstmt.executeQuery();
                if (rs.next()) {
                	organization = new Organization();
                	organization.setId(rs.getString("ID"));
        			organization.setCode(rs.getString("CODE"));
        			organization.setName(rs.getString("NAME"));
        			organization.setAcronym(rs.getString("ACRON"));
        			organization.setCreationDate(TypeConverter.toCalendar(rs.getDate("CREATION_DATE")));
        			organization.setDisabledDate(TypeConverter.toCalendar(rs.getDate("DISABLE_DATE")));
                }
            } 
        } catch (Exception e) {
        	logger.error("Error al obtener la organización por código [" + code + "]", e);
        	throw new ISPACException("Error al obtener la organización", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (prepstmt != null) {
				try {
					prepstmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
        }
        
        return organization; 
    }

}
