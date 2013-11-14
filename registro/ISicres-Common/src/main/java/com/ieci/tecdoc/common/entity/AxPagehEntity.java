//
//FileName: AxPagehEEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author 79426599
 *
 */

public class AxPagehEntity implements ServerKeys {

 private static final Logger log = Logger.getLogger(AxPagehEntity.class);

 protected static String FINDBY_SENTENCE = "SELECT id FROM {0} WHERE fdrId=? and id=?";
 protected static String DELETE_SENTENCE = "DELETE FROM {0} WHERE fdrId=? and id=?";
 protected static String INSERT_SENTENCE = "INSERT INTO {0} (id,fdrid,name,sortorder,docid,fileid,volid,loc,annid,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 protected static String SELECT_SENTENCE = "SELECT id,fdrid,name,sortorder,docid,fileid,volid,loc,annid,stat,refcount,remarks,accesstype,acsid,crtrid,crtndate,updrid,upddate,accrid,accdate,acccount FROM {0} WHERE fdrid=? and id=?";
 protected static String UPDATE_SENTENCE = "UPDATE {0} SET name=?,sortorder=?,docid=?,fileid=?,volid=?,loc=?,annid=?,stat=?,refcount=?,remarks=?,accesstype=?,acsid=?,crtrid=?,crtndate=?,updrid=?,upddate=?,accrid=?,accdate=?,acccount=? WHERE fdrid=? and id=?";
 protected static String SELECT_SENTENCE_FDRID_DOCID = "SELECT id FROM {0} WHERE fdrid=? and docid=? ORDER BY sortorder";
 protected static String SELECT_SENTENCE_FILEID = "SELECT fdrid,id FROM {0} WHERE fileid=?";

 private String type = null;
 private int id;
 private int fdrId;
 private String name;
 private int sortOrder;
 private int docId;
 private int fileId;
 private int volId = (Integer.MAX_VALUE) - 1;
 private String loc;
 private int annId= (Integer.MAX_VALUE) - 1;
 private int stat = 0;
 private int refCount = 0;
 private String remarks = null;
 private int accessType = 1;
 private int acsId = (Integer.MAX_VALUE) - 1;
 private int crtrId;
 private Date crtnDate;
 private int updrId;
 private Date updDate;
 private int accrId;
 private Date accDate;
 private int acccount = 0;

 public AxPagehEntity(){
 }

 protected String getFinalTableName() {
     return "A" + type + "PAGEH";
 }

 public AxPKById getPrimaryKey() {
     return new AxPKById(type, fdrId, id);
 }

 protected void loadEntityPrimaryKey(AxPKById axPKById) {
     this.type = axPKById.getType();
     setFdrId(axPKById.getFdrId());
     setId(axPKById.getId());
 }

 public void remove(String entidad) throws Exception {
     //loadEntityPrimaryKey();

     Connection con = null;
     PreparedStatement ps = null;
     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(DELETE_SENTENCE, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         ps.setInt(1, getFdrId());
         ps.setInt(2, getId());
         ps.executeUpdate();
     } catch (SQLException ex) {
         log.error("Error en método remover.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método remover.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }
 }

 public void load(AxPKById axPKById, String entidad) throws Exception {
     loadEntityPrimaryKey(axPKById);

     Connection con = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(SELECT_SENTENCE, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         ps.setInt(1, getFdrId());
         ps.setInt(2, getId());
         rs = ps.executeQuery();
         if (!rs.next()) {
             throw new Exception();
         }
         int index = 1;
         setId(rs.getInt(index++));
         setFdrId(rs.getInt(index++));
         setName(rs.getString(index++));
         setSortOrder(rs.getInt(index++));
         setDocId(rs.getInt(index++));
         setFileId(rs.getInt(index++));
         setVolId(rs.getInt(index++));
         setLoc(rs.getString(index++));
         setAnnId(rs.getInt(index++));
         setStat(rs.getInt(index++));
         setRefCount(rs.getInt(index++));
         setRemarks(rs.getString(index++));
         setAccessType(rs.getInt(index++));
         setAcsId(rs.getInt(index++));
         setCrtrId(rs.getInt(index++));
         setCrtnDate(rs.getDate(index++));
         setUpdrId(rs.getInt(index++));
         setUpdDate(rs.getDate(index++));
         setAccrId(rs.getInt(index++));
         setAccDate(rs.getDate(index++));
         setAcccount(rs.getInt(index++));
     } catch (SQLException ex) {
         log.error("Error en método load.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método load.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(rs);
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }
 }

 public void store(String entidad) throws Exception {
     //loadEntityPrimaryKey();

     Connection con = null;
     PreparedStatement ps = null;
     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(UPDATE_SENTENCE, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         int index = 1;
         ps.setString(index++, getName());
         ps.setInt(index++, getSortOrder());
         ps.setInt(index++, getDocId());
         ps.setInt(index++, getFileId());
         ps.setInt(index++, getVolId());
         ps.setString(index++, getLoc());
         ps.setInt(index++, getAnnId());
         ps.setInt(index++, getStat());
         ps.setInt(index++, getRefCount());
         ps.setString(index++, getRemarks());
         ps.setInt(index++, getAccessType());
         ps.setInt(index++, getAcsId());
         ps.setInt(index++, getCrtrId());
         ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
         ps.setInt(index++, getUpdrId());
         ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
         ps.setInt(index++, getAccrId());
         ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
         ps.setInt(index++, getAcccount());

         ps.setInt(index++, getFdrId());
         ps.setInt(index++, getId());

         ps.executeUpdate();
     } catch (SQLException ex) {
         log.error("Error en método store.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método storeP.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }
 }

 public AxPKById create(AxPKById pk, int user, String name, int sortOrder, int docId, int fileId, String loc, Timestamp timestamp, String entidad) throws Exception {
     this.type = pk.getType();
     this.fdrId = pk.getFdrId();
     this.id = pk.getId();

     this.name = name;
     this.sortOrder = sortOrder;
     this.docId = docId;
     this.fileId = fileId;
     this.loc = loc;
     this.crtrId = user;
     this.crtnDate = new Date(timestamp.getTime());
     this.updrId = user;
     this.updDate = new Date(timestamp.getTime());
     this.accrId = user;
     this.accDate = new Date(timestamp.getTime());

     Connection con = null;
     PreparedStatement ps = null;
     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(INSERT_SENTENCE, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         int index = 1;
         ps.setInt(index++, getId());
         ps.setInt(index++, getFdrId());
         ps.setString(index++, getName());
         ps.setInt(index++, getSortOrder());
         ps.setInt(index++, getDocId());
         ps.setInt(index++, getFileId());
         ps.setInt(index++, getVolId());
         ps.setString(index++, getLoc());
         ps.setInt(index++, getAnnId());
         ps.setInt(index++, getStat());
         ps.setInt(index++, getRefCount());
         ps.setString(index++, getRemarks());
         ps.setInt(index++, getAccessType());
         ps.setInt(index++, getAcsId());
         ps.setInt(index++, getCrtrId());
         ps.setDate(index++, BBDDUtils.getDate(getCrtnDate()));
         ps.setInt(index++, getUpdrId());
         ps.setDate(index++, BBDDUtils.getDate(getUpdDate()));
         ps.setInt(index++, getAccrId());
         ps.setDate(index++, BBDDUtils.getDate(getAccDate()));
         ps.setInt(index++, getAcccount());

         ps.executeUpdate();
         return getPrimaryKey();
     } catch (SQLException ex) {
         log.error("Error en método create.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método create.PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }
 }

 public AxPKById findByPrimaryKey(AxPKById pk, String entidad) throws Exception {
     this.type = pk.getType();
     this.fdrId = pk.getFdrId();
     this.id = pk.getId();

     Connection con = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(FINDBY_SENTENCE, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         ps.setInt(1, pk.getFdrId());
         ps.setInt(2, pk.getId());
         rs = ps.executeQuery();
         if (!rs.next()) {
             throw new Exception();
         }
     } catch (SQLException ex) {
         log.error("Error en método findByPrimaryKey. PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método findByPrimaryKey. PK [" + getPrimaryKey() + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(rs);
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }

     return new AxPKById(pk.getType(), pk.getFdrId(), pk.getId());
 }

 public Collection findByFdridDocid(Integer bookID, int fdrid, int docid, String entidad) throws Exception {
     this.type = bookID.toString();

     Connection con = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     List result = new ArrayList();

     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(SELECT_SENTENCE_FDRID_DOCID, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         ps.setInt(1, fdrid);
         ps.setInt(2, docid);
         rs = ps.executeQuery();
         while (rs.next()) {
             result.add(new AxPKById(this.type, fdrid, rs.getInt("id")));
         }
     } catch (SQLException ex) {
         log.error("Error en método findByFdridDocid. PK [" + bookID + "] fdrid [" + fdrid
                 + "] docid [" + docid + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método findByFdridDocid. PK [" + bookID + "] fdrid [" + fdrid
                 + "] docid [" + docid + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(rs);
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }

     return result;
 }

 public Collection findByFileID(Integer bookID, int fileid, String entidad) throws Exception {
     this.type = bookID.toString();

     Connection con = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     List result = new ArrayList();

     try {
         con = BBDDUtils.getConnection(entidad);
         String sentence = MessageFormat.format(SELECT_SENTENCE_FILEID, new String[] { getFinalTableName()});
         ps = con.prepareStatement(sentence);
         ps.setInt(1, fileid);
         rs = ps.executeQuery();
         while (rs.next()) {
             result.add(new AxPKById(this.type, rs.getInt("fdrid"), rs.getInt("id")));
         }
     } catch (SQLException ex) {
         log.error("Error en método findByFileID. PK [" + bookID + "] fileid [" + fileid
                 + "]", ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.error("Error en método findByFileID. PK [" + bookID + "] fileid [" + fileid
                 + "]", ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(rs);
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }

     return result;
 }

 public int getOrderID(Integer bookID, int fdrid, int docID, String entidad) throws Exception {
     Connection con = null;
     PreparedStatement ps = null;
     ResultSet rs = null;
     int result = 1;

     String select = "SELECT count(*) FROM A"+bookID.toString()+"PAGEH WHERE FDRID=? AND DOCID=?";

     try {
         con = BBDDUtils.getConnection(entidad);

         ps = con.prepareStatement(select);
         ps.setInt(1, fdrid);
         ps.setInt(2, docID);
         rs = ps.executeQuery();

         while (rs.next()) {
             result += rs.getInt(1);
         }

     } catch (SQLException ex) {
         log.fatal("getOrderID. Sentence [" + select + "] fdrid " + fdrid + " docid " + docID, ex);
         throw new Exception(ex);
     } catch (NamingException ex) {
         log.fatal("getOrderID. Sentence [" + select + "] fdrid " + fdrid + " docid " + docID, ex);
         throw new Exception(ex);
     } finally {
         BBDDUtils.close(rs);
         BBDDUtils.close(ps);
         BBDDUtils.close(con);
     }

     return result++;
 }

 public int getId() {
     return id;
 }

 public void setId(int id) {
     this.id = id;
 }

 public int getFdrId() {
     return fdrId;
 }

 public void setFdrId(int fdrId) {
     this.fdrId = fdrId;
 }

 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name;
 }

 public int getSortOrder() {
     return sortOrder;
 }

 public void setSortOrder(int sortOrder) {
     this.sortOrder = sortOrder;
 }

 public int getDocId() {
     return docId;
 }

 public void setDocId(int docId) {
     this.docId = docId;
 }

 public int getFileId() {
     return fileId;
 }

 public void setFileId(int fileId) {
     this.fileId = fileId;
 }

 public int getVolId() {
     return volId;
 }

 public void setVolId(int volId) {
     this.volId = volId;
 }

 public String getLoc() {
     return loc;
 }

 public void setLoc(String loc) {
     this.loc = loc;
 }

 public int getAnnId() {
     return annId;
 }

 public void setAnnId(int annId) {
     this.annId = annId;
 }

 public int getStat() {
     return stat;
 }

 public void setStat(int stat) {
     this.stat = stat;
 }

 public int getRefCount() {
     return refCount;
 }

 public void setRefCount(int refCount) {
     this.refCount = refCount;
 }

 public String getRemarks() {
     return remarks;
 }

 public void setRemarks(String remarks) {
     this.remarks = remarks;
 }

 public int getAccessType() {
     return accessType;
 }

 public void setAccessType(int accessType) {
     this.accessType = accessType;
 }

 public int getAcsId() {
     return acsId;
 }

 public void setAcsId(int acsId) {
     this.acsId = acsId;
 }

 public int getCrtrId() {
     return crtrId;
 }

 public void setCrtrId(int crtrId) {
     this.crtrId = crtrId;
 }

 public Date getCrtnDate() {
     return crtnDate;
 }

 public void setCrtnDate(Date crtnDate) {
     this.crtnDate = crtnDate;
 }

 public int getUpdrId() {
     return updrId;
 }

 public void setUpdrId(int updrId) {
     this.updrId = updrId;
 }

 public Date getUpdDate() {
     return updDate;
 }

 public void setUpdDate(Date updDate) {
     this.updDate = updDate;
 }

 public int getAccrId() {
     return accrId;
 }

 public void setAccrId(int accrId) {
     this.accrId = accrId;
 }

 public Date getAccDate() {
     return accDate;
 }

 public void setAccDate(Date accDate) {
     this.accDate = accDate;
 }

 public int getAcccount() {
     return acccount;
 }

 public void setAcccount(int acccount) {
     this.acccount = acccount;
 }


public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

 public AxPageh getAxPageh() {
     AxPageh axPageh = new AxPageh();
     axPageh.setId(getId());
     axPageh.setFdrId(getFdrId());
     axPageh.setName(getName());
     axPageh.setSortOrder(getSortOrder());
     axPageh.setDocId(getDocId());
     axPageh.setFileId(getFileId());
     axPageh.setVolId(getVolId());
     axPageh.setLoc(getLoc());
     axPageh.setAnnId(getAnnId());
     axPageh.setStat(getStat());
     axPageh.setRefCount(getRefCount());
     axPageh.setRemarks(getRemarks());
     axPageh.setAccessType(getAccessType());
     axPageh.setAcsId(getAcsId());
     axPageh.setCrtrId(getCrtrId());
     axPageh.setCrtnDate(getCrtnDate());
     axPageh.setUpdrId(getUpdrId());
     axPageh.setUpdDate(getUpdDate());
     axPageh.setAccrId(getAccrId());
     axPageh.setAccDate(getAccDate());
     axPageh.setAcccount(getAcccount());
     return axPageh;
 }

}