package se.groupone.ecommerce.test.webservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CustomerServiceTest.class, ProductServiceTest.class, OrderServiceTest.class })
public class AllTests
{

}