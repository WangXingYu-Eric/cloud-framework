package com.sinosoft.newstandard.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class XsteamUtils {

    private static XStream xstream = new XStream(new DomDriver());

    @SuppressWarnings("unchecked")
    public static <T> T toBean(Class<T> clazz, String xml) {
        String alias = clazz.getAnnotation(XStreamAlias.class).value();
        xstream.alias(alias, clazz);
        xstream.processAnnotations(clazz);
        xstream.autodetectAnnotations(true);
        Object xmlObject = xstream.fromXML(xml);
        return (T) xmlObject;
    }

    public static <T> String toXml(T object) {
        String alias = object.getClass().getAnnotation(XStreamAlias.class).value();
        xstream.alias(alias, object.getClass());
        return xstream.toXML(object);
    }

}
