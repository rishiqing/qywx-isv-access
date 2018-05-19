package com.rishiqing.qywx.web.util.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:web-test-spring-context.xml")
public class XmlUtilTest {
    private static final String SIMPLE_XML = "    <xml>\n" +
            "        <SuiteId><![CDATA[tj4asffe99e54c0f4c]]></SuiteId>\n" +
            "        <InfoType><![CDATA[suite_ticket]]></InfoType>\n" +
            "        <TimeStamp>1403610513</TimeStamp>\n" +
            "        <SuiteTicket><![CDATA[asdfasfdasdfasdf]]></SuiteTicket>\n" +
            "    </xml>";

    private static final String RECURSIVE_XML = "<xml>\n" +
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
    @Test
    public void test_simpleXmlString2Map(){
        try {
            Map m = XmlUtil.simpleXmlString2Map(SIMPLE_XML);
            assertEquals(4, m.entrySet().size());
            assertEquals("tj4asffe99e54c0f4c", m.get("SuiteId"));
            assertEquals("suite_ticket", m.get("InfoType"));
            assertEquals("1403610513", m.get("TimeStamp"));
            assertEquals("asdfasfdasdfasdf", m.get("SuiteTicket"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (SAXException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void test_recursiveXml(){
        try {
            Map m = XmlUtil.xmlString2Map(RECURSIVE_XML);
            System.out.println(m);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
