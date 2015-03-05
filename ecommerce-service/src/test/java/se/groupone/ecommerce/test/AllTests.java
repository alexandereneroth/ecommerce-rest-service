package se.groupone.ecommerce.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ShopServiceTest.class, SQLCustomerTest.class, SQLProductTest.class })
public class AllTests
{

}
