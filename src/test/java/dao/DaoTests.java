package dao;

import dto.TransactionDto;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DaoTests {

  @Test(singleThreaded = true)
  public void testDao(){
    TransactionDao.reset();
    TransactionDao transactionDao = TransactionDao.getInstance();
    Assert.assertEquals(transactionDao, TransactionDao.getInstance());

    // test that constructor is private
    List<Constructor> constructors = Arrays.asList(TransactionDao.class.getConstructors());
    Assert.assertEquals(constructors.size(), 0);
    constructors = Arrays.asList(TransactionDao.class.getDeclaredConstructors());
    Assert.assertEquals(constructors.size(), 1);
    Assert.assertTrue(Modifier.isPrivate(constructors.get(0).getModifiers()));
  }

  @Test(singleThreaded = true)
  public void testStore(){
    TransactionDao.reset();
    TransactionDao transactionDao = TransactionDao.getInstance();
    String id = String.valueOf(Math.random());
    TransactionDto transactionDto = new TransactionDto(id);
    transactionDao.put(transactionDto);
    // id's that do not exist should be empty
    Assert.assertNull(transactionDao.get(String.valueOf(Math.random())));
    // ids that do exist should return the object
    Assert.assertEquals(transactionDao.get(id), transactionDto);
    Assert.assertNull(transactionDao.get(String.valueOf(Math.random())));
  }
}
