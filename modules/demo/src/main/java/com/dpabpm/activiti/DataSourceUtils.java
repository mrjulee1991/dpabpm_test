
package com.dpabpm.activiti;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.liferay.util.ContentUtil;

public class DataSourceUtils {

	/**
	 * @param actionRequest
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static DataSourceBean getBeanDataSource()
		throws ParserConfigurationException, SAXException, IOException {

		DataSourceBean dataSourceBean = new DataSourceBean();

		 String xmlDatabaseContent = ContentUtil.get(
		 "/com/dpabpm/resources/databases/database.xml");
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(
			new InputSource(new StringReader(xmlDatabaseContent)));

		NodeList nList = doc.getElementsByTagName("database");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				dataSourceBean.setDriver(
					eElement.getElementsByTagName("driver").item(
						0).getTextContent());

				dataSourceBean.setUrl(
					eElement.getElementsByTagName("url").item(
						0).getTextContent());

				dataSourceBean.setDbuser(
					eElement.getElementsByTagName("dbuser").item(
						0).getTextContent());

				dataSourceBean.setDbpassw(
					eElement.getElementsByTagName("dbpassw").item(
						0).getTextContent());

				dataSourceBean.setMinPoolSize(
					Integer.valueOf(
						eElement.getElementsByTagName("minpoolsize").item(
							0).getTextContent()));

				dataSourceBean.setMaxPoolSize(
					Integer.valueOf(
						eElement.getElementsByTagName("maxpoolsize").item(
							0).getTextContent()));

				dataSourceBean.setMaxOpenPreparedStatement(
					Integer.valueOf(
						eElement.getElementsByTagName(
							"MaxOpenPreparedStatement").item(
								0).getTextContent()));

			}
		}

		return dataSourceBean;
	}

	/**
	 * @param actionRequest
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static BasicDataSource getDataSource()
		throws ParserConfigurationException, SAXException, IOException {

		BasicDataSource ds = null;

		DataSourceBean dataSourceBean = getBeanDataSource();

		ds = new BasicDataSource();

		ds.setDriverClassName(dataSourceBean.getDriver());
		ds.setUsername(dataSourceBean.getDbuser());
		ds.setPassword(dataSourceBean.getDbpassw());
		ds.setUrl(dataSourceBean.getUrl());

		// the settings below are optional -- dbcp can work with defaults
		ds.setMinIdle(dataSourceBean.getMinPoolSize());
		ds.setMaxIdle(dataSourceBean.getMaxPoolSize());
		ds.setMaxOpenPreparedStatements(
			dataSourceBean.getMaxOpenPreparedStatement());

		return ds;
	}
}
