package ieci.tdw.ispac.ispaclib.util;

import java.util.Date;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;

/**
 * Attributes implementation.
 */
public class ResourceAttributes implements Attributes {
    
  /**
   * Creation date.
   */
  public static final String CREATION_DATE = "creationdate";

  /**
   * Creation date.
   */
  public static final String ALTERNATE_CREATION_DATE = "creation-date";

  /**
   * Last modification date.
   */
  public static final String LAST_MODIFIED = "getlastmodified";

  /**
   * Last modification date.
   */
  public static final String ALTERNATE_LAST_MODIFIED = "last-modified";

  /**
   * Name.
   */
  public static final String NAME = "displayname";

  /**
   * Type.
   */
  public static final String TYPE = "resourcetype";

  /**
   * Type.
   */
  public static final String ALTERNATE_TYPE = "content-type";

  /**
   * Source.
   */
  public static final String SOURCE = "source";

  /**
   * MIME type of the content.
   */
  public static final String CONTENT_TYPE = "getcontenttype";

  /**
   * Content language.
   */
  public static final String CONTENT_LANGUAGE = "getcontentlanguage";

  /**
   * Content length.
   */
  public static final String CONTENT_LENGTH = "getcontentlength";

  /**
   * Content length.
   */
  public static final String ALTERNATE_CONTENT_LENGTH = "content-length";

  /**
   * ETag.
   */
  public static final String ETAG = "getetag";

  /**
   * Collection type.
   */
  public static final String COLLECTION_TYPE = "<collection/>";

  /**
   * Default constructor.
   */
  public ResourceAttributes() 
  {}

  /**
   * Collection flag.
   */
  protected boolean collection = false;

  /**
   * Content length.
   */
  protected long contentLength = -1;

  /**
   * Creation time.
   */
  protected long creation = -1;

  /**
   * Creation date.
   */
  protected Date creationDate = null;

  /**
   * Last modified time.
   */
  protected long lastModified = -1;

  /**
   * Last modified date.
   */
  protected Date lastModifiedDate = null;

  /**
   * Name.
   */
  protected String name = null;

  /**
   * Weak ETag.
   */
  protected String weakETag = null;

  /**
   * Strong ETag.
   */
  protected String strongETag = null;

  /**
   * Is collection.
   */
  public boolean isCollection() 
  { return (collection);}

  /**
   * Set collection flag.
   * 
   * @return value of the collection flag
   */
  public void setCollection( boolean collection) 
  { this.collection = collection;}

  /**
   * Get content length.
   * 
   * @return content length value
   */
  public long getContentLength() 
  { return contentLength;}

  /**
   * Set content length.
   * 
   * @param contentLength New content length value
   */
  public void setContentLength( long contentLength) 
  { this.contentLength = contentLength;}

  /**
   * Get creation time.
   * 
   * @return creation time value
   */
  public long getCreation() {
    if (creation != -1L)
      return creation;
    if (creationDate != null)
      return creationDate.getTime();
    return creation;
  }

  /**
   * Set creation.
   * 
   * @param creation New creation value
   */
  public void setCreation( long creation) {
    this.creation = creation;
    this.creationDate = null;
  }

  /**
   * Get creation date.
   * 
   * @return Creation date value
   */
  public Date getCreationDate() {
    if (creationDate != null)
      return creationDate;
    if (creation != -1L)
      creationDate = new Date(creation);
    return creationDate;
  }

  /**
   * Creation date mutator.
   * 
   * @param creationDate New creation date
   */
  public void setCreationDate( Date creationDate) {
    this.creation = creationDate.getTime();
    this.creationDate = creationDate;
  }

  /**
   * Get last modified time.
   * 
   * @return lastModified time value
   */
  public long getLastModified() {
    if (lastModified != -1L)
        return lastModified;
    if (lastModifiedDate != null)
        return lastModifiedDate.getTime();
    return lastModified;
  }

  /**
   * Set last modified.
   * 
   * @param lastModified New last modified value
   */
  public void setLastModified( long lastModified) {
    this.lastModified = lastModified;
    this.lastModifiedDate = null;
  }

  /**
   * Set last modified date.
   * 
   * @param lastModified New last modified date value
   * @deprecated
   */
  public void setLastModified( Date lastModified) 
  { setLastModifiedDate(lastModified);}

  /**
   * Get lastModified date.
   * 
   * @return LastModified date value
   */
  public Date getLastModifiedDate() {
    if (lastModifiedDate != null)
      return lastModifiedDate;
    if (lastModified != -1L)
      lastModifiedDate = new Date(lastModified);
    return lastModifiedDate;
  }

  /**
   * Last modified date mutator.
   * 
   * @param lastModifiedDate New last modified date
   */
  public void setLastModifiedDate( Date lastModifiedDate) {
    this.lastModified = lastModifiedDate.getTime();
    this.lastModifiedDate = lastModifiedDate;
  }

  /**
   * Get name.
   * 
   * @return Name value
   */
  public String getName() 
  { return name;}


  /**
   * Set name.
   * 
   * @param name New name value
   */
  public void setName( String name)
  { this.name = name;}

  /**
   * Get resource type.
   * 
   * @return String resource type
   */
  public String getResourceType() {
    String result = null;

    if (collection)
        result = COLLECTION_TYPE;
    else
        result = "";
    return result;
  }

  /**
   * Type mutator.
   * 
   * @param resourceType New resource type
   */
  public void setResourceType( String resourceType) 
  { collection = resourceType.equals(COLLECTION_TYPE);}

  /**
   * Get ETag.
   * 
   * @return Weak ETag
   */
  public String getETag() 
  { return getETag(false);}

  /**
   * Get ETag.
   * 
   * @param strong If true, the strong ETag will be returned
   * @return ETag
   */
  public String getETag( boolean strong) {
    String result = null;
    if (strong) {
      // The strong ETag must always be calculated by the resources
      result = strongETag;
    } 
    else {
        // The weakETag is contentLenght + lastModified
      if (weakETag == null) {
        weakETag = "W/\"" 
                 + getContentLength() 
                 + "-" 
                 + getLastModified() 
                 + "\"";
      }
      result = weakETag;
    }
    return result;
  }

  /**
   * Set strong ETag.
   */
  public void setETag( String eTag) 
  { this.strongETag = eTag;}

  /**
   * Get attribute.
   */
  public Attribute get( String attrID) {

    if (attrID.equals(CREATION_DATE)) {
        return new BasicAttribute(CREATION_DATE, getCreationDate());
    } else if (attrID.equals(ALTERNATE_CREATION_DATE)) {
        return new BasicAttribute(ALTERNATE_CREATION_DATE, getCreationDate());
    } else if (attrID.equals(LAST_MODIFIED)) {
        return new BasicAttribute(LAST_MODIFIED, getLastModifiedDate());
    } else if (attrID.equals(ALTERNATE_LAST_MODIFIED)) {
        return new BasicAttribute(ALTERNATE_LAST_MODIFIED, getLastModifiedDate());
    } else if (attrID.equals(NAME)) {
        return new BasicAttribute(NAME, getName());
    } else if (attrID.equals(TYPE)) {
        return new BasicAttribute(TYPE, getResourceType());
    } else if (attrID.equals(ALTERNATE_TYPE)) {
        return new BasicAttribute(ALTERNATE_TYPE, getResourceType());
    } else if (attrID.equals(CONTENT_LENGTH)) {
        return new BasicAttribute(CONTENT_LENGTH, new Long(getContentLength()));
    } else if (attrID.equals(ALTERNATE_CONTENT_LENGTH)) {
        return new BasicAttribute(ALTERNATE_CONTENT_LENGTH, new Long(getContentLength()));
    }
    return null;
  }

  /**
   * Put attribute.
   */
  public Attribute put( Attribute attribute) {
    try 
    { return put(attribute.getID(), attribute.get());} 
    catch (NamingException e) 
    {}
     return null; 
  }

  /**
   * Put attribute.
   */
  public Attribute put( String attrID, Object val) 
  { return null;}

  /**
   * Remove attribute.
   */
  public Attribute remove( String attrID)
  { return null;}

  /**
   * Get all attributes.
   */
  public NamingEnumeration getAll() {
    Vector attributes = new Vector();
    attributes.addElement(new BasicAttribute(CREATION_DATE, getCreationDate()));
    attributes.addElement(new BasicAttribute(LAST_MODIFIED, getLastModifiedDate()));
    attributes.addElement(new BasicAttribute(NAME, getName()));
    attributes.addElement(new BasicAttribute(TYPE, getResourceType()));
    attributes.addElement(new BasicAttribute(CONTENT_LENGTH, new Long(getContentLength())));
    return (NamingEnumeration) attributes.elements();
  }

  /**
   * Get all attribute IDs.
   */
  public NamingEnumeration getIDs() {
    Vector attributeIDs = new Vector();
    attributeIDs.addElement(CREATION_DATE);
    attributeIDs.addElement(LAST_MODIFIED);
    attributeIDs.addElement(NAME);
    attributeIDs.addElement(TYPE);
    attributeIDs.addElement(CONTENT_LENGTH);
    return (NamingEnumeration) attributeIDs.elements();
  }

  /**
   * Retrieves the number of attributes in the attribute set.
   */
  public int size() 
  { return 5;}

  /**
   * Clone the attributes object (WARNING: fake cloning).
   */
  public Object clone() 
  { return this;}

  /**
   * Case sensitivity.
   */
  public boolean isCaseIgnored() 
  { return false;}
}
