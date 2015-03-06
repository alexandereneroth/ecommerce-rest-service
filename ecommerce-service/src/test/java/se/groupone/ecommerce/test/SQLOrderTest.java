//package se.groupone.ecommerce.test;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import se.groupone.ecommerce.exception.RepositoryException;
//import se.groupone.ecommerce.model.Customer;
//import se.groupone.ecommerce.model.Order;
//import se.groupone.ecommerce.model.Product;
//import se.groupone.ecommerce.model.ProductParameters;
//import se.groupone.ecommerce.repository.sql.SQLOrder;
//
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class SQLOrderTest
//{
//	final int pID = 1;
//	final String pName = "Voffla";
//	final String pCategory = "Bakverk";
//	final String pManufacturer = "Steffe";
//	final String pDescription = "En våffa gjord av steffe!";
//	final String pImg = "delicious.png";
//	final double pPrice = 10;
//	final int pQuantity = 100;
//	
//	final int pID2 = 2;
//	final String pName2 = "Pannkaka";
//	final String pCategory2 = "Bakverk";
//	final String pManufacturer2 = "SteffeKeff";
//	final String pDescription2 = "En pannkaka gjord av steffe!";
//	final String pImg2 = "delicious2.png";
//	final double pPrice2 = 5;
//	final int pQuantity2 = 200;
//	
//	final int pID3 = 3;
//	final String pName3 = "Paj";
//	final String pCategory3 = "Bakverk";
//	final String pManufacturer3 = "Kira";
//	final String pDescription3 = "En paj gjord av Kira!";
//	final String pImg3 = "delicious3.png";
//	final double pPrice3 = 20;
//	final int pQuantity3 = 50;
//	
//	final int pID4 = 4;
//	final String pName4 = "Ammerikansk kaka";
//	final String pCategory4 = "Bakverk";
//	final String pManufacturer4 = "Erik Kira Welander";
//	final String pDescription4 = "En kaka gjord av Kira!";
//	final String pImg4 = "delicious4.png";
//	final double pPrice4 = 10;
//	final int pQuantity4 = 90;
//	
//	
//	final String cuUsername = "kira";
//	final String cuPassword = "Elf";
//	final String cuEmail = "erik.welander@hotmailcom";
//	final String cuFirstName = "Erik";
//	final String cuLastName = "Welander";
//	final String cuAddress = "Ter";
//	final String cuMobile = "073";
//	
//	final String cuUsername2 = "rednalew";
//	final String cuPassword2 = "Albin1";
//	final String cuEmail2 = "viktor.welander@hotmail.com";
//	final String cuFirstName2 = "Viktor";
//	final String cuLastName2 = "Welander";
//	final String cuAddress2 = "Ter2";
//	final String cuMobile2 = "0732";
//	
//	final String cuUsername3 = "Osama";
//	final String cuPassword3 = "MA1";
//	final String cuEmail3 = "@something";
//	final String cuFirstName3 = "Osama";
//	final String cuLastName3 = "Embarki";
//	final String cuAddress3 = "aaa";
//	final String cuMobile3 = "0732333";
//	
//	final Product p1 = new Product(pID,
//			     new ProductParameters(pName,
//									   pCategory,
//									   pManufacturer,
//									   pDescription,
//									   pImg,
//									   pPrice,
//								       pQuantity));
//	
//	final Product p2 = new Product(pID2,
//			     new ProductParameters(pName2,
//							    	   pCategory2,
//									   pManufacturer2,
//									   pDescription2,
//									   pImg2,
//									   pPrice2,
//									   pQuantity2));
//	final Product p3 = new Product(pID3,
//		     new ProductParameters(pName3,
//						    	   pCategory3,
//								   pManufacturer3,
//								   pDescription3,
//								   pImg3,
//								   pPrice3,
//								   pQuantity3));
//	final Product p4 = new Product(pID4,
//		     new ProductParameters(pName4,
//						    	   pCategory,
//								   pManufacturer4,
//								   pDescription4,
//								   pImg4,
//								   pPrice4,
//								   pQuantity4));
//	ArrayList<Integer> prodIDs = new ArrayList<>(Arrays.asList(p1.getId(), p2.getId()));
//	ArrayList<Integer> prodIDs2 = new ArrayList<>(Arrays.asList(p3.getId(), p4.getId()));
//	ArrayList<Integer> prodIDs3 = new ArrayList<>(Arrays.asList(p1.getId(), p2.getId(), p3.getId(), p4.getId()));
//	
//	Customer cu = new Customer(cuUsername, cuPassword, cuEmail, cuFirstName, cuLastName, cuAddress, cuMobile);
//	Customer cu2 = new Customer(cuUsername2, cuPassword2, cuEmail2, cuFirstName2, cuLastName2, cuAddress2, cuMobile2);
//	Customer cu3 = new Customer(cuUsername3, cuPassword3, cuEmail3, cuFirstName3, cuLastName3, cuAddress3, cuMobile3); 
//	
//	final Order order = new Order(1, cu.getUsername(), prodIDs);
//	final Order order2 = new Order(2, cu2.getUsername(), prodIDs2);
//	final Order order3 = new Order(3, cu3.getUsername(), prodIDs3);
//	
//	List<Order> orderList = new ArrayList<>(Arrays.asList(order));
//	List<Order> orderList2 = new ArrayList<>(Arrays.asList(order2));
//	List<Order> orderList3 = new ArrayList<>(Arrays.asList(order3));
//	@Test
//	public void a_addOrders() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		sqlo.addOrder(order);
//		sqlo.addOrder(order2);
//		sqlo.addOrder(order3);
//	}
//	
//	@Test
//	public void b_getOrder() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		assertEquals(order, sqlo.getOrder(order.getId()));
//		assertEquals(order2, sqlo.getOrder(order2.getId()));
//		assertEquals(order3, sqlo.getOrder(order3.getId()));
//	}
//	
//	@Test
//	public void c_getOrdersForConsumer() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		assertEquals(orderList, sqlo.getOrders(cu.getUsername()));
//	}
//	
//	@Test
//	public void d_getHighestID() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		assertEquals(3, sqlo.getHighestId());
//	}
//	
//	@Test
//	public void e_removeOrder() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		sqlo.removeOrder(1);
//		sqlo.removeOrder(2);
//		sqlo.removeOrder(3);
//	}
//	
//	@Test (expected=Exception.class)
//	public void f_getOrderThatDoesNotExist() throws RepositoryException
//	{
//		SQLOrder sqlo = new SQLOrder();
//		sqlo.getOrder(2);
//	}
//	
//}
