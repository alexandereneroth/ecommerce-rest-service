//package se.groupone.ecommerce.test;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.HashMap;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runners.MethodSorters;
//
//import se.groupone.ecommerce.exception.RepositoryException;
//import se.groupone.ecommerce.model.Product;
//import se.groupone.ecommerce.model.ProductParameters;
//import se.groupone.ecommerce.repository.sql.SQLProduct;
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class SQLProductTest
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
//	Product p1 = new Product(pID,
//			     new ProductParameters(pName,
//									   pCategory,
//									   pManufacturer,
//									   pDescription,
//									   pImg,
//									   pPrice,
//								       pQuantity));
//	
//	Product p2 = new Product(pID2,
//			     new ProductParameters(pName2,
//							    	   pCategory2,
//									   pManufacturer2,
//									   pDescription2,
//									   pImg2,
//									   pPrice2,
//									   pQuantity2));
//	
//	final int SLEEP = 500;
//	
//	@Test
//	public void a_addProducts() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		sqlp.addProduct(p1);
//		sqlp.addProduct(p2);
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test
//	public void b_getProduct() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		assertEquals(p1, sqlp.getProduct(p1.getId()));
//		assertEquals(p2, sqlp.getProduct(p2.getId()));
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test
//	public void c_getProducts() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		List<Product> allProducts = new ArrayList<>();
//		allProducts.add(p1);
//		allProducts.add(p2);
//		
//		assertEquals(allProducts, sqlp.getProducts());
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test
//	public void c_updateProduct() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		
//		Product tp = new Product(p1.getId(),
//					new ProductParameters(p2.getTitle(),
//							              p2.getCategory(),
//							              p2.getManufacturer(),
//							              p2.getDescription(),
//							              p2.getImg(),
//							              p2.getPrice(),
//							              p2.getQuantity())
//					);
//		Product tp2 = new Product(p2.getId(),
//				new ProductParameters(p1.getTitle(),
//						              p1.getCategory(),
//						              p1.getManufacturer(),
//						              p1.getDescription(),
//						              p1.getImg(),
//						              p1.getPrice(),
//						              p1.getQuantity())
//				);
//		sqlp.updateProduct(tp);
//		sqlp.updateProduct(tp2);
//		assertEquals(tp, sqlp.getProduct(p1.getId()));
//		assertEquals(tp2, sqlp.getProduct(p2.getId()));
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test
//	public void d_removeProduct() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		sqlp.removeProduct(p1.getId());
//		sqlp.removeProduct(p2.getId());
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test(expected=Exception.class)
//	public void e_getProductThatDoesNotExist() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		sqlp.getProduct(p2.getId());
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test
//	public void f_getMAXID() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		sqlp.addProduct(p1);
//		sqlp.addProduct(p2);
//		Thread.sleep(SLEEP);
//		assertEquals(p2.getId(), sqlp.getHighestId());
//	}
//	
//	@Test
//	public void g_productQuantityChange() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		List<Integer> ids = new ArrayList<>();
//		ids.add(p1.getId());
//		ids.add(p2.getId());
//		
//		sqlp.increaseQuantityOfProductsByOne(ids);
//		assertEquals(p1.getQuantity()+1, sqlp.getProduct(p1.getId()).getQuantity());
//		assertEquals(p2.getQuantity()+1, sqlp.getProduct(p2.getId()).getQuantity());
//		
//		sqlp.decreaseQuantityOfProductsByOne(ids);
//		assertEquals(p1.getQuantity(), sqlp.getProduct(p1.getId()).getQuantity());
//		assertEquals(p2.getQuantity(), sqlp.getProduct(p2.getId()).getQuantity());
//		Thread.sleep(SLEEP);
//	}
//	
//	@Test(expected=Exception.class)
//	public void h_cleanIp() throws RepositoryException, InterruptedException
//	{
//		SQLProduct sqlp = new SQLProduct();
//		sqlp.removeProduct(p1.getId());
//		sqlp.removeProduct(p2.getId());
//		sqlp.getProduct(p1.getId());
//	}
//
//	
//}
