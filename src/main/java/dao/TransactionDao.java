package dao;

import dto.TransactionDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDao implements BaseDao<TransactionDto> {

  private static TransactionDao instance = new TransactionDao();

  public static TransactionDao getInstance() {
    return instance;
  }

  private Map<String, TransactionDto> transactionStorage = new HashMap<>();
  private TransactionDao() {

  }

  @Override
  public void put(TransactionDto transactionDto) {
    // TODO fill this out
    transactionStorage.put(transactionDto.getUniqueId(), transactionDto);
  }

  @Override
  public TransactionDto get(String uniqueId) {
    // TODO fill this out
    return transactionStorage.get(uniqueId);
  }

  @Override
  public List<TransactionDto> getAll() {
    // TODO fill this out
    return new ArrayList<>(transactionStorage.values());
  }

  // only for testing, do not call this method
  public static void reset(){
    instance = new TransactionDao();
  }
}
