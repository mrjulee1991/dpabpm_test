
package com.dpabpm.activiti;

import java.io.IOException;
import java.io.StringReader;

import javax.sql.DataSource;
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

import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.util.ContentUtil;

public class DataSourceUtils {
	
	/**
	 * @param actionRequest
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static BasicDataSource getDataSource() throws ParserConfigurationException, SAXException, IOException {

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
		ds.setMaxOpenPreparedStatements(dataSourceBean.getMaxOpenPreparedStatement());

		return ds;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public static DataSource getLiferayDataSource() throws Exception {

		DataSourceBean dataSourceBean = getDataSourceBeanFromGlobalProperties();
		
		return DataSourceFactoryUtil.initDataSource(dataSourceBean.getDriver(), dataSourceBean.getUrl(),
				dataSourceBean.getDbuser(), dataSourceBean.getDbpassw(), null);
	}

	private static DataSourceBean getDataSourceBeanFromGlobalProperties() {
		String driver = PrefsPropsUtil.getString("jdbc.default.driverClassName");
		String url = PrefsPropsUtil.getString("jdbc.default.url");
		String dbuser = PrefsPropsUtil.getString("jdbc.default.username");
		String dbpassw = PrefsPropsUtil.getString("jdbc.default.password");
		if(_log.isInfoEnabled()) {
			_log.info("dbpassw ==== " + dbpassw);
		}
		DataSourceBean dataSourceBean = new DataSourceBean(url, dbpassw, dbuser, driver, 0, 0, 0);

		return dataSourceBean;
	}

	/**
	 * @param actionRequest
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static DataSourceBean getBeanDataSource() throws ParserConfigurationException, SAXException, IOException {

		DataSourceBean dataSourceBean = null;

		String xmlDatabaseContent = ContentUtil.get("/com/dpabpm/resources/databases/database.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(new InputSource(new StringReader(xmlDatabaseContent)));

		NodeList nList = doc.getElementsByTagName("database");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String driver = eElement.getElementsByTagName("driver").item(0).getTextContent();
				String url = eElement.getElementsByTagName("url").item(0).getTextContent();
				String dbuser = eElement.getElementsByTagName("dbpassw").item(0).getTextContent();
				String dbpassw = eElement.getElementsByTagName("dbpassw").item(0).getTextContent();

				int minpoolsize = Integer
						.valueOf(eElement.getElementsByTagName("minpoolsize").item(0).getTextContent());
				int maxpoolsize = Integer
						.valueOf(eElement.getElementsByTagName("maxpoolsize").item(0).getTextContent());
				int maxOpenPreparedStatement = Integer
						.valueOf(eElement.getElementsByTagName("MaxOpenPreparedStatement").item(0).getTextContent());

				dataSourceBean = new DataSourceBean(url, dbpassw, dbuser, driver, minpoolsize, maxpoolsize,
						maxOpenPreparedStatement);

			}
		}

		return dataSourceBean;
	}
	
	private static Log _log = LogFactoryUtil.getLog(DataSourceUtils.class);
}