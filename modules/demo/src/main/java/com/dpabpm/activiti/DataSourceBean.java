package com.dpabpm.activiti;

public class DataSourceBean {

	
	public DataSourceBean(String url, String dbpassw, String dbuser, String driver, int minPoolSize, int maxPoolSize,
			int maxOpenPreparedStatement) {
		super();
		this.url = url;
		this.dbpassw = dbpassw;
		this.dbuser = dbuser;
		this.driver = driver;
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;
		this.maxOpenPreparedStatement = maxOpenPreparedStatement;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDbpassw() {
		return dbpassw;
	}

	public void setDbpassw(String dbpassw) {
		this.dbpassw = dbpassw;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMaxOpenPreparedStatement() {
		return maxOpenPreparedStatement;
	}

	public void setMaxOpenPreparedStatement(int maxOpenPreparedStatement) {
		this.maxOpenPreparedStatement = maxOpenPreparedStatement;
	}

	private String url;
	private String dbpassw;
	private String dbuser;
	private String driver;
	private int minPoolSize;
	private int maxPoolSize;
	private int maxOpenPreparedStatement;
}