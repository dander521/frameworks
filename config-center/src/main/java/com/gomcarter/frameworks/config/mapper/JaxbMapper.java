package com.gomcarter.frameworks.config.mapper;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

/**
 * 使用Jaxb2.0实现XML&lt;-&gt;Java Object的Mapper.
 * <p>
 * 在创建时需要设定所有需要序列化的Root对象的Class.
 * 特别支持Root对象是Collection的情形.
 *
 * @author calvin
 */
public class JaxbMapper {
    /**
     * 多线程安全的Context .
     */
    private JAXBContext jaxbContext;

    /**
     * @param rootTypes 所有需要序列化的Root对象的Class.
     */
    public JaxbMapper(Class<?>... rootTypes) {
        try {
            jaxbContext = JAXBContext.newInstance(rootTypes);
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Java Object-&gt;Xml without encoding.
     *
     * @param root object
     * @return xml string
     */
    public String toXml(Object root) {
        return toXml(root, null);
    }

    /**
     * Java Object-&gt;Xml with encoding.
     *
     * @param root     object
     * @param encoding encoding
     * @return xml string
     */
    public String toXml(Object root, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(root, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Java Object-&gt;Xml without encoding, 特别支持Root Element是Collection的情形.
     *
     * @param root     object collection
     * @param rootName which root you wanna from
     * @return xml string
     */
    public String toXml(Collection<?> root, String rootName) {
        return toXml(root, rootName, null);
    }

    /**
     * Java Object-&gt;Xml with encoding, 特别支持Root Element是Collection的情形.
     *
     * @param root     object collection
     * @param rootName which root you wanna from
     * @param encoding encoding
     * @return xml string
     */
    public String toXml(Collection<?> root, String rootName, String encoding) {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;

            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
                    CollectionWrapper.class, wrapper);

            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(wrapperElement, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Xml-&gt;Java Object.
     *
     * @param xml xml string
     * @param <T> object class
     * @return instance of class T
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml) {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 创建Marshaller并设定encoding(可为null).
     *
     * @param encoding encoding
     * @return Marshaller
     */
    public Marshaller createMarshaller(String encoding) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (StringUtils.isNotBlank(encoding)) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }

            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 创建UnMarshaller.
     *
     * @return Unmarshaller
     */
    public Unmarshaller createUnmarshaller() {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {

        @XmlAnyElement
        protected Collection<?> collection;
    }
}
