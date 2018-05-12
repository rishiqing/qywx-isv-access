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
     * 检查ToUserName是否是suiteKey
     * @param body
     * @return
     */
    public static Boolean checkUserName(String suiteKey, String body) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(body);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("ToUserName");

        return nodeList.getLength() != 0 && suiteKey.equals(nodeList.item(0).getTextContent());
    }
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

    /**
     * 检查corpId是否为isv服务商的corpId，如果不是，那么就不做处理
     * @param corpId
     * @param body
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Boolean checkIsvCorpId(String corpId, String body) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(body);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("ServiceCorpId");

        return nodeList.getLength() != 0 && corpId.equals(nodeList.item(0).getTextContent());
    }

    /**
     * 将xml解析成Map
     * @param xmlText
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Map xmlString2Map(String xmlText) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(xmlText);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        Element root = document.getDocumentElement();
        Object result = xmlNode2Map(root);
        return (Map)result;
    }

    private static Object xmlNode2Map(Node node){
        HashMap<String, Object> map = new HashMap<>();
        NodeList list = node.getChildNodes();
        for(int i = 0; i < list.getLength(); i ++){
            Node tempNode = list.item(i);
            if(tempNode.getNodeType() == Node.ELEMENT_NODE){
                map.put(tempNode.getNodeName(), xmlNode2Map(tempNode));
            }
        }
        if(map.isEmpty()){
            return node.getTextContent();
        }else{
            return map;
        }
    }

    public static void main(String[] args) {
        String xmlText = "<xml>\n" +
                "    <ServiceCorpId><![CDATA[wxfjdkasfasjkfdsa]]></ServiceCorpId>\n" +
                "    <InfoType><![CDATA[register_corp]]></InfoType>\n" +
                "    <TimeStamp>1502682173</TimeStamp>\n" +
                "    <RegisterCode><![CDATA[pIKi3wRPNWCGF-pyP-YU5KWjDDD]]></RegisterCode>\n" +
                "    <AuthCorpId><![CDATA[wxf8b4f85f3a794e77]]></AuthCorpId>\n" +
                "    <ContactSync>\n" +
                "        <AccessToken><![CDATA[accesstoken000001]]></AccessToken>\n" +
                "        <ExpiresIn>1800</ExpiresIn>\n" +
                "    </ContactSync>\n" +
                "    <AuthUserInfo>\n" +
                "        <Email><![CDATA[zhangshan@qq.com]]></Email>\n" +
                "        <Mobile><![CDATA[12345678901]]></Mobile>\n" +
                "        <UserId><![CDATA[zhangshan]]></UserId>\n" +
                "    </AuthUserInfo>\n" +
                "    <State>TestState123</State>\n" +
                "</xml>";
        try {
            Map m = XmlUtil.xmlString2Map(xmlText);
            System.out.println(m);

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = null;
//            db = dbf.newDocumentBuilder();
//            StringReader sr = new StringReader(xmlText);
//            InputSource is = new InputSource(sr);
//            Document document = db.parse(is);
//            Element root = document.getDocumentElement();
//            NodeList list = root.getChildNodes();
//
//            Node contactSync = list.item(11);
//            System.out.println("----node 11: " + contactSync.getNodeName());
//            System.out.println("----node 11: " + contactSync.getNodeType());
//            System.out.println("----node 11: " + contactSync.getTextContent());
//
//            Node childNode = contactSync.getChildNodes().item(1);
//            NodeList childList = contactSync.getChildNodes();
//            System.out.println("====child node: " + childNode.getNodeName());
//            System.out.println("====child getNodeType: " + childNode.getNodeType());
//
//            for(int i = 0; i < childList.getLength(); i ++){
//                Node tempNode = childList.item(i);
//                System.out.println("-----tempNode: " + tempNode.getNodeName());
//                System.out.println("-----tempNode: " + tempNode.getNodeType());
//            }
//
//            Node grandChildNode = childList.item(1);
//            NodeList grandChildList = grandChildNode.getChildNodes();
//            System.out.println("!!====child node: " + grandChildNode.getNodeName());
//            System.out.println("!!====child getNodeType: " + grandChildNode.getNodeType());
//
//            for(int i = 0; i < grandChildList.getLength(); i ++){
//                Node tempNode = grandChildList.item(i);
//                System.out.println("!!-----tempNode: " + tempNode.getNodeName());
//                System.out.println("!!-----tempNode: " + tempNode.getNodeType());
//            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
