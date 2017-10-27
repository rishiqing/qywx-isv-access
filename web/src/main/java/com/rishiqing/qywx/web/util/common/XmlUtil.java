package com.rishiqing.qywx.web.util.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class XmlUtil {
    /**
     * 暂时简单处理，只处理第一层
     * @param xmlText
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Map simpleXmlString2Map(String xmlText) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(xmlText);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        Element root = document.getDocumentElement();
        NodeList list = root.getChildNodes();

        Map<String, String> map = new HashMap<String ,String>();
        for(int i = 0; i < list.getLength(); i ++){
            Node tempNode = list.item(i);
            if(tempNode.getNodeType() == Node.ELEMENT_NODE){
                map.put(tempNode.getNodeName(), tempNode.getTextContent());
            }
        }
        return map;
    }
}
