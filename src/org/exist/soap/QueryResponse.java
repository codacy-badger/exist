/**
 * QueryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.exist.soap;

public class QueryResponse  implements java.io.Serializable {
    private org.exist.soap.QueryResponseCollection[] collections;
    private int hits;
    private long queryTime;
    private int resultSetId;

    public QueryResponse() {
    }

    public org.exist.soap.QueryResponseCollection[] getCollections() {
        return collections;
    }

    public void setCollections(org.exist.soap.QueryResponseCollection[] collections) {
        this.collections = collections;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public long getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(long queryTime) {
        this.queryTime = queryTime;
    }

    public int getResultSetId() {
        return resultSetId;
    }

    public void setResultSetId(int resultSetId) {
        this.resultSetId = resultSetId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryResponse)) return false;
        QueryResponse other = (QueryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((collections==null && other.getCollections()==null) || 
             (collections!=null &&
              java.util.Arrays.equals(collections, other.getCollections()))) &&
            hits == other.getHits() &&
            queryTime == other.getQueryTime() &&
            resultSetId == other.getResultSetId();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCollections() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCollections());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCollections(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getHits();
        _hashCode += new Long(getQueryTime()).hashCode();
        _hashCode += getResultSetId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryResponse.class);

    static {
        org.apache.axis.description.FieldDesc field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("collections");
        field.setXmlName(new javax.xml.namespace.QName("", "collections"));
        field.setXmlType(new javax.xml.namespace.QName("urn:exist", "QueryResponseCollection"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("hits");
        field.setXmlName(new javax.xml.namespace.QName("", "hits"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("queryTime");
        field.setXmlName(new javax.xml.namespace.QName("", "queryTime"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(field);
        field = new org.apache.axis.description.ElementDesc();
        field.setFieldName("resultSetId");
        field.setXmlName(new javax.xml.namespace.QName("", "resultSetId"));
        field.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(field);
    };

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
