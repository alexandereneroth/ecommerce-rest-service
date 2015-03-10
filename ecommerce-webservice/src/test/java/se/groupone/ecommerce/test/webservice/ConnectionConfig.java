package se.groupone.ecommerce.test.webservice;

public final class ConnectionConfig
{
	public static final String HOST_NAME = "localhost";
	public static final int HOST_IP = 8080;
	public static final String PROJECT_NAME = "ecommerce-webservice";
	public static final String URL_BASE = "http://" + HOST_NAME + ":" + HOST_IP + "/"
			+ PROJECT_NAME;
	public static final String CUSTOMERS_URL = URL_BASE + "/customers";
	public static final String PRODUCTS_URL = URL_BASE + "/products";
	public static final String ORDERS_URL = URL_BASE + "/orders";
}
