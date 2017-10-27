package com.rishiqing.qywx.web.util.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
}
