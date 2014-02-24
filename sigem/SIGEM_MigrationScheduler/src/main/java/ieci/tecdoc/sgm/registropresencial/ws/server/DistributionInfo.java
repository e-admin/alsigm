/**
 * DistributionInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.registropresencial.ws.server;

public class DistributionInfo  extends ieci.tecdoc.sgm.core.services.dto.RetornoServicio  implements java.io.Serializable {
    private java.lang.String bookId;

    private java.lang.String bookName;

    private java.lang.String bookType;

    private java.lang.String destinationId;

    private java.lang.String destinationName;

    private java.lang.String destinationType;

    private java.lang.String distributionDate;

    private java.lang.String dtrId;

    private java.lang.String folderId;

    private java.lang.String message;

    private java.lang.String registerDate;

    private java.lang.String registerDestinationName;

    private java.lang.String registerMatter;

    private java.lang.String registerMatterTypeName;

    private java.lang.String registerNumber;

    private java.lang.String registerOffice;

    private java.lang.String registerSenderName;

    private java.lang.String senderId;

    private java.lang.String senderName;

    private java.lang.String senderType;

    private ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] sendersOrReceivers;

    private java.lang.String state;

    private java.lang.String stateDate;

    private java.lang.String stateDescription;

    private java.lang.String user;

    public DistributionInfo() {
    }

    public DistributionInfo(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String bookId,
           java.lang.String bookName,
           java.lang.String bookType,
           java.lang.String destinationId,
           java.lang.String destinationName,
           java.lang.String destinationType,
           java.lang.String distributionDate,
           java.lang.String dtrId,
           java.lang.String folderId,
           java.lang.String message,
           java.lang.String registerDate,
           java.lang.String registerDestinationName,
           java.lang.String registerMatter,
           java.lang.String registerMatterTypeName,
           java.lang.String registerNumber,
           java.lang.String registerOffice,
           java.lang.String registerSenderName,
           java.lang.String senderId,
           java.lang.String senderName,
           java.lang.String senderType,
           ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] sendersOrReceivers,
           java.lang.String state,
           java.lang.String stateDate,
           java.lang.String stateDescription,
           java.lang.String user) {
        super(
            errorCode,
            returnCode);
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookType = bookType;
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.destinationType = destinationType;
        this.distributionDate = distributionDate;
        this.dtrId = dtrId;
        this.folderId = folderId;
        this.message = message;
        this.registerDate = registerDate;
        this.registerDestinationName = registerDestinationName;
        this.registerMatter = registerMatter;
        this.registerMatterTypeName = registerMatterTypeName;
        this.registerNumber = registerNumber;
        this.registerOffice = registerOffice;
        this.registerSenderName = registerSenderName;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderType = senderType;
        this.sendersOrReceivers = sendersOrReceivers;
        this.state = state;
        this.stateDate = stateDate;
        this.stateDescription = stateDescription;
        this.user = user;
    }


    /**
     * Gets the bookId value for this DistributionInfo.
     * 
     * @return bookId
     */
    public java.lang.String getBookId() {
        return bookId;
    }


    /**
     * Sets the bookId value for this DistributionInfo.
     * 
     * @param bookId
     */
    public void setBookId(java.lang.String bookId) {
        this.bookId = bookId;
    }


    /**
     * Gets the bookName value for this DistributionInfo.
     * 
     * @return bookName
     */
    public java.lang.String getBookName() {
        return bookName;
    }


    /**
     * Sets the bookName value for this DistributionInfo.
     * 
     * @param bookName
     */
    public void setBookName(java.lang.String bookName) {
        this.bookName = bookName;
    }


    /**
     * Gets the bookType value for this DistributionInfo.
     * 
     * @return bookType
     */
    public java.lang.String getBookType() {
        return bookType;
    }


    /**
     * Sets the bookType value for this DistributionInfo.
     * 
     * @param bookType
     */
    public void setBookType(java.lang.String bookType) {
        this.bookType = bookType;
    }


    /**
     * Gets the destinationId value for this DistributionInfo.
     * 
     * @return destinationId
     */
    public java.lang.String getDestinationId() {
        return destinationId;
    }


    /**
     * Sets the destinationId value for this DistributionInfo.
     * 
     * @param destinationId
     */
    public void setDestinationId(java.lang.String destinationId) {
        this.destinationId = destinationId;
    }


    /**
     * Gets the destinationName value for this DistributionInfo.
     * 
     * @return destinationName
     */
    public java.lang.String getDestinationName() {
        return destinationName;
    }


    /**
     * Sets the destinationName value for this DistributionInfo.
     * 
     * @param destinationName
     */
    public void setDestinationName(java.lang.String destinationName) {
        this.destinationName = destinationName;
    }


    /**
     * Gets the destinationType value for this DistributionInfo.
     * 
     * @return destinationType
     */
    public java.lang.String getDestinationType() {
        return destinationType;
    }


    /**
     * Sets the destinationType value for this DistributionInfo.
     * 
     * @param destinationType
     */
    public void setDestinationType(java.lang.String destinationType) {
        this.destinationType = destinationType;
    }


    /**
     * Gets the distributionDate value for this DistributionInfo.
     * 
     * @return distributionDate
     */
    public java.lang.String getDistributionDate() {
        return distributionDate;
    }


    /**
     * Sets the distributionDate value for this DistributionInfo.
     * 
     * @param distributionDate
     */
    public void setDistributionDate(java.lang.String distributionDate) {
        this.distributionDate = distributionDate;
    }


    /**
     * Gets the dtrId value for this DistributionInfo.
     * 
     * @return dtrId
     */
    public java.lang.String getDtrId() {
        return dtrId;
    }


    /**
     * Sets the dtrId value for this DistributionInfo.
     * 
     * @param dtrId
     */
    public void setDtrId(java.lang.String dtrId) {
        this.dtrId = dtrId;
    }


    /**
     * Gets the folderId value for this DistributionInfo.
     * 
     * @return folderId
     */
    public java.lang.String getFolderId() {
        return folderId;
    }


    /**
     * Sets the folderId value for this DistributionInfo.
     * 
     * @param folderId
     */
    public void setFolderId(java.lang.String folderId) {
        this.folderId = folderId;
    }


    /**
     * Gets the message value for this DistributionInfo.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this DistributionInfo.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the registerDate value for this DistributionInfo.
     * 
     * @return registerDate
     */
    public java.lang.String getRegisterDate() {
        return registerDate;
    }


    /**
     * Sets the registerDate value for this DistributionInfo.
     * 
     * @param registerDate
     */
    public void setRegisterDate(java.lang.String registerDate) {
        this.registerDate = registerDate;
    }


    /**
     * Gets the registerDestinationName value for this DistributionInfo.
     * 
     * @return registerDestinationName
     */
    public java.lang.String getRegisterDestinationName() {
        return registerDestinationName;
    }


    /**
     * Sets the registerDestinationName value for this DistributionInfo.
     * 
     * @param registerDestinationName
     */
    public void setRegisterDestinationName(java.lang.String registerDestinationName) {
        this.registerDestinationName = registerDestinationName;
    }


    /**
     * Gets the registerMatter value for this DistributionInfo.
     * 
     * @return registerMatter
     */
    public java.lang.String getRegisterMatter() {
        return registerMatter;
    }


    /**
     * Sets the registerMatter value for this DistributionInfo.
     * 
     * @param registerMatter
     */
    public void setRegisterMatter(java.lang.String registerMatter) {
        this.registerMatter = registerMatter;
    }


    /**
     * Gets the registerMatterTypeName value for this DistributionInfo.
     * 
     * @return registerMatterTypeName
     */
    public java.lang.String getRegisterMatterTypeName() {
        return registerMatterTypeName;
    }


    /**
     * Sets the registerMatterTypeName value for this DistributionInfo.
     * 
     * @param registerMatterTypeName
     */
    public void setRegisterMatterTypeName(java.lang.String registerMatterTypeName) {
        this.registerMatterTypeName = registerMatterTypeName;
    }


    /**
     * Gets the registerNumber value for this DistributionInfo.
     * 
     * @return registerNumber
     */
    public java.lang.String getRegisterNumber() {
        return registerNumber;
    }


    /**
     * Sets the registerNumber value for this DistributionInfo.
     * 
     * @param registerNumber
     */
    public void setRegisterNumber(java.lang.String registerNumber) {
        this.registerNumber = registerNumber;
    }


    /**
     * Gets the registerOffice value for this DistributionInfo.
     * 
     * @return registerOffice
     */
    public java.lang.String getRegisterOffice() {
        return registerOffice;
    }


    /**
     * Sets the registerOffice value for this DistributionInfo.
     * 
     * @param registerOffice
     */
    public void setRegisterOffice(java.lang.String registerOffice) {
        this.registerOffice = registerOffice;
    }


    /**
     * Gets the registerSenderName value for this DistributionInfo.
     * 
     * @return registerSenderName
     */
    public java.lang.String getRegisterSenderName() {
        return registerSenderName;
    }


    /**
     * Sets the registerSenderName value for this DistributionInfo.
     * 
     * @param registerSenderName
     */
    public void setRegisterSenderName(java.lang.String registerSenderName) {
        this.registerSenderName = registerSenderName;
    }


    /**
     * Gets the senderId value for this DistributionInfo.
     * 
     * @return senderId
     */
    public java.lang.String getSenderId() {
        return senderId;
    }


    /**
     * Sets the senderId value for this DistributionInfo.
     * 
     * @param senderId
     */
    public void setSenderId(java.lang.String senderId) {
        this.senderId = senderId;
    }


    /**
     * Gets the senderName value for this DistributionInfo.
     * 
     * @return senderName
     */
    public java.lang.String getSenderName() {
        return senderName;
    }


    /**
     * Sets the senderName value for this DistributionInfo.
     * 
     * @param senderName
     */
    public void setSenderName(java.lang.String senderName) {
        this.senderName = senderName;
    }


    /**
     * Gets the senderType value for this DistributionInfo.
     * 
     * @return senderType
     */
    public java.lang.String getSenderType() {
        return senderType;
    }


    /**
     * Sets the senderType value for this DistributionInfo.
     * 
     * @param senderType
     */
    public void setSenderType(java.lang.String senderType) {
        this.senderType = senderType;
    }


    /**
     * Gets the sendersOrReceivers value for this DistributionInfo.
     * 
     * @return sendersOrReceivers
     */
    public ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] getSendersOrReceivers() {
        return sendersOrReceivers;
    }


    /**
     * Sets the sendersOrReceivers value for this DistributionInfo.
     * 
     * @param sendersOrReceivers
     */
    public void setSendersOrReceivers(ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] sendersOrReceivers) {
        this.sendersOrReceivers = sendersOrReceivers;
    }


    /**
     * Gets the state value for this DistributionInfo.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this DistributionInfo.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the stateDate value for this DistributionInfo.
     * 
     * @return stateDate
     */
    public java.lang.String getStateDate() {
        return stateDate;
    }


    /**
     * Sets the stateDate value for this DistributionInfo.
     * 
     * @param stateDate
     */
    public void setStateDate(java.lang.String stateDate) {
        this.stateDate = stateDate;
    }


    /**
     * Gets the stateDescription value for this DistributionInfo.
     * 
     * @return stateDescription
     */
    public java.lang.String getStateDescription() {
        return stateDescription;
    }


    /**
     * Sets the stateDescription value for this DistributionInfo.
     * 
     * @param stateDescription
     */
    public void setStateDescription(java.lang.String stateDescription) {
        this.stateDescription = stateDescription;
    }


    /**
     * Gets the user value for this DistributionInfo.
     * 
     * @return user
     */
    public java.lang.String getUser() {
        return user;
    }


    /**
     * Sets the user value for this DistributionInfo.
     * 
     * @param user
     */
    public void setUser(java.lang.String user) {
        this.user = user;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DistributionInfo)) return false;
        DistributionInfo other = (DistributionInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bookId==null && other.getBookId()==null) || 
             (this.bookId!=null &&
              this.bookId.equals(other.getBookId()))) &&
            ((this.bookName==null && other.getBookName()==null) || 
             (this.bookName!=null &&
              this.bookName.equals(other.getBookName()))) &&
            ((this.bookType==null && other.getBookType()==null) || 
             (this.bookType!=null &&
              this.bookType.equals(other.getBookType()))) &&
            ((this.destinationId==null && other.getDestinationId()==null) || 
             (this.destinationId!=null &&
              this.destinationId.equals(other.getDestinationId()))) &&
            ((this.destinationName==null && other.getDestinationName()==null) || 
             (this.destinationName!=null &&
              this.destinationName.equals(other.getDestinationName()))) &&
            ((this.destinationType==null && other.getDestinationType()==null) || 
             (this.destinationType!=null &&
              this.destinationType.equals(other.getDestinationType()))) &&
            ((this.distributionDate==null && other.getDistributionDate()==null) || 
             (this.distributionDate!=null &&
              this.distributionDate.equals(other.getDistributionDate()))) &&
            ((this.dtrId==null && other.getDtrId()==null) || 
             (this.dtrId!=null &&
              this.dtrId.equals(other.getDtrId()))) &&
            ((this.folderId==null && other.getFolderId()==null) || 
             (this.folderId!=null &&
              this.folderId.equals(other.getFolderId()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.registerDate==null && other.getRegisterDate()==null) || 
             (this.registerDate!=null &&
              this.registerDate.equals(other.getRegisterDate()))) &&
            ((this.registerDestinationName==null && other.getRegisterDestinationName()==null) || 
             (this.registerDestinationName!=null &&
              this.registerDestinationName.equals(other.getRegisterDestinationName()))) &&
            ((this.registerMatter==null && other.getRegisterMatter()==null) || 
             (this.registerMatter!=null &&
              this.registerMatter.equals(other.getRegisterMatter()))) &&
            ((this.registerMatterTypeName==null && other.getRegisterMatterTypeName()==null) || 
             (this.registerMatterTypeName!=null &&
              this.registerMatterTypeName.equals(other.getRegisterMatterTypeName()))) &&
            ((this.registerNumber==null && other.getRegisterNumber()==null) || 
             (this.registerNumber!=null &&
              this.registerNumber.equals(other.getRegisterNumber()))) &&
            ((this.registerOffice==null && other.getRegisterOffice()==null) || 
             (this.registerOffice!=null &&
              this.registerOffice.equals(other.getRegisterOffice()))) &&
            ((this.registerSenderName==null && other.getRegisterSenderName()==null) || 
             (this.registerSenderName!=null &&
              this.registerSenderName.equals(other.getRegisterSenderName()))) &&
            ((this.senderId==null && other.getSenderId()==null) || 
             (this.senderId!=null &&
              this.senderId.equals(other.getSenderId()))) &&
            ((this.senderName==null && other.getSenderName()==null) || 
             (this.senderName!=null &&
              this.senderName.equals(other.getSenderName()))) &&
            ((this.senderType==null && other.getSenderType()==null) || 
             (this.senderType!=null &&
              this.senderType.equals(other.getSenderType()))) &&
            ((this.sendersOrReceivers==null && other.getSendersOrReceivers()==null) || 
             (this.sendersOrReceivers!=null &&
              java.util.Arrays.equals(this.sendersOrReceivers, other.getSendersOrReceivers()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.stateDate==null && other.getStateDate()==null) || 
             (this.stateDate!=null &&
              this.stateDate.equals(other.getStateDate()))) &&
            ((this.stateDescription==null && other.getStateDescription()==null) || 
             (this.stateDescription!=null &&
              this.stateDescription.equals(other.getStateDescription()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getBookId() != null) {
            _hashCode += getBookId().hashCode();
        }
        if (getBookName() != null) {
            _hashCode += getBookName().hashCode();
        }
        if (getBookType() != null) {
            _hashCode += getBookType().hashCode();
        }
        if (getDestinationId() != null) {
            _hashCode += getDestinationId().hashCode();
        }
        if (getDestinationName() != null) {
            _hashCode += getDestinationName().hashCode();
        }
        if (getDestinationType() != null) {
            _hashCode += getDestinationType().hashCode();
        }
        if (getDistributionDate() != null) {
            _hashCode += getDistributionDate().hashCode();
        }
        if (getDtrId() != null) {
            _hashCode += getDtrId().hashCode();
        }
        if (getFolderId() != null) {
            _hashCode += getFolderId().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getRegisterDate() != null) {
            _hashCode += getRegisterDate().hashCode();
        }
        if (getRegisterDestinationName() != null) {
            _hashCode += getRegisterDestinationName().hashCode();
        }
        if (getRegisterMatter() != null) {
            _hashCode += getRegisterMatter().hashCode();
        }
        if (getRegisterMatterTypeName() != null) {
            _hashCode += getRegisterMatterTypeName().hashCode();
        }
        if (getRegisterNumber() != null) {
            _hashCode += getRegisterNumber().hashCode();
        }
        if (getRegisterOffice() != null) {
            _hashCode += getRegisterOffice().hashCode();
        }
        if (getRegisterSenderName() != null) {
            _hashCode += getRegisterSenderName().hashCode();
        }
        if (getSenderId() != null) {
            _hashCode += getSenderId().hashCode();
        }
        if (getSenderName() != null) {
            _hashCode += getSenderName().hashCode();
        }
        if (getSenderType() != null) {
            _hashCode += getSenderType().hashCode();
        }
        if (getSendersOrReceivers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSendersOrReceivers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSendersOrReceivers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getStateDate() != null) {
            _hashCode += getStateDate().hashCode();
        }
        if (getStateDescription() != null) {
            _hashCode += getStateDescription().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DistributionInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "DistributionInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "bookId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "bookName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "bookType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "destinationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "destinationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "destinationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distributionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "distributionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtrId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "dtrId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "folderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerDestinationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerDestinationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerMatter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerMatter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerMatterTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerMatterTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSenderName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "registerSenderName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "senderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "senderName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("senderType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "senderType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendersOrReceivers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "sendersOrReceivers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "PersonInfo"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "stateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "stateDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.registropresencial.sgm.tecdoc.ieci", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
