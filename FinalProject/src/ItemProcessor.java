

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.xml.transform.Result;

public class ItemProcessor {
  private HashMap<Integer, Integer> items;
  private String onlineFlag;
  private int totalCost;

  public ItemProcessor(String isOnline) {
    this.items = new HashMap<>();
    this.onlineFlag = isOnline;
  }

  public boolean insertItem(int itemID, int amount, Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    String sql = "CALL GETSTORAGE(" + itemID + ")";
    ResultSet rs = statement.executeQuery(sql);
    int storage = 0;
    int price = -1;
    while (rs.next()) {
      storage = rs.getInt("itemCount");
      price = rs.getInt("itemPrice");
    }

    if (storage >= amount) {
      this.items.put(itemID, this.items.getOrDefault(itemID,0) + amount);
      this.totalCost += price * amount;
      return true;
    } else {
      throw new IllegalArgumentException();
    }
  }


  public int getTotalCost() {
    return this.totalCost;
  }

  public boolean push(int CardPayment, int CardAmount, int CashAmount, int couponID, String bank,
                      String packageAddress, String packageReceiverName, String ShippingCompany,
      Connection connection) throws SQLException {
    if (this.onlineFlag.equals("Online")) {
      String sql0 = "CALL VALIDCOUPON(?)";
      CallableStatement cs0 = connection.prepareCall(sql0);
      cs0.setInt(1, couponID);
      cs0.execute();
      ResultSet rs0 = cs0.getResultSet();
      int validflagAmount = -1;
      while(rs0.next()) {
        validflagAmount = rs0.getInt("couponAmount");
      }
      rs0.close();
      cs0.close();
      if (validflagAmount != -1) {
        this.totalCost -= validflagAmount;
      }

      if (CardAmount < this.totalCost ) {
        throw new SQLException();
      }

      String sql = "CALL CARDPAYMENT(?, ?, ?)";
      CallableStatement cs = connection.prepareCall(sql);
      cs.setInt(1, CardPayment);
      cs.setInt(2, CardAmount);
      cs.setString(3, bank);
      cs.execute();
      cs.close();

      Statement statement0 = connection.createStatement();
      String sql2 = "SELECT MAX(cardID) AS cardID FROM card";
      ResultSet rs2 = statement0.executeQuery(sql2);
      int cardID = -1;
      while(rs2.next()) {
        cardID = rs2.getInt("cardID");
      }
      rs2.close();

      String sql5 = "CALL CREATETRANSPORTATION(?, ?, ?)";
      CallableStatement cs5 = connection.prepareCall(sql5);
      cs5.setString(1, packageAddress);
      cs5.setString(2, packageReceiverName);
      cs5.setString(3, ShippingCompany);
      cs5.execute();
      cs5.close();

      String sql6 = "SELECT MAX(packageID) AS packageID FROM transportation";
      Statement statement6 = connection.createStatement();
      ResultSet rs6 = statement6.executeQuery(sql6);
      int transportationID = -1;
      while (rs6.next()) {
        transportationID = rs6.getInt("packageID");
      }
      rs6.close();

      String sql3 = "CALL CREATEOnlineTransac(?, ?)";
      CallableStatement cs3 = connection.prepareCall(sql3);
      cs3.setInt(1, cardID);
      cs3.setInt(2, transportationID);
      cs3.execute();
      cs3.close();

      Statement statement1 = connection.createStatement();
      String sql1 = "SELECT MAX(transacID) AS transacID FROM onlineTransaction";
      ResultSet rs1 = statement1.executeQuery(sql1);
      int transacID = -1;
      while (rs1.next()) {
        transacID = rs1.getInt("transacID");
      }
      rs1.close();
      if (transacID == -1) {
        throw new SQLException();
      }

      for (int itemID : this.items.keySet()) {
        String sqlLoop = "CALL CREATEOnlineTransacItems(?, ?, ?)";
        CallableStatement csLoop = connection.prepareCall(sqlLoop);
        csLoop.setInt(1, transacID);
        csLoop.setInt(2, itemID);
        csLoop.setInt(3, this.items.get(itemID));
        csLoop.execute();
        csLoop.close();
      }

    } else {
      String sql0 = "CALL VALIDCOUPON(?)";
      CallableStatement cs0 = connection.prepareCall(sql0);
      cs0.setInt(1, couponID);
      cs0.execute();
      ResultSet rs0 = cs0.getResultSet();
      int validflagAmount = -1;
      while(rs0.next()) {
        validflagAmount = rs0.getInt("couponAmount");
      }
      if (validflagAmount != -1) {
        this.totalCost -= validflagAmount;
      }

      if (CardAmount + CashAmount < this.totalCost ) {
        throw new SQLException();
      }
      rs0.close();
      cs0.close();

      String sql = "CALL CARDPAYMENT(?, ?, ?)";
      CallableStatement cs = connection.prepareCall(sql);
      cs.setInt(1, CardPayment);
      cs.setInt(2, CardAmount);
      cs.setString(3, bank);
      cs.execute();
      cs.close();

      Statement statement0 = connection.createStatement();
      String sql2 = "SELECT MAX(cardID) AS cardID FROM card";
      ResultSet rs2 = statement0.executeQuery(sql2);
      int cardID = -1;
      while(rs2.next()) {
        cardID = rs2.getInt("cardID");
      }
      rs2.close();

      String sql4 = "CALL CASHPAYMENT(?)";
      CallableStatement cs4 = connection.prepareCall(sql4);
      cs4.setInt(1, CashAmount);
      cs4.execute();
      cs4.close();

      Statement statement5 = connection.createStatement();
      String sql5 = "SELECT MAX(cashID) AS cashID FROM cash";
      ResultSet rs5 = statement5.executeQuery(sql5);
      int cashID = -1;
      while (rs5.next()) {
        cashID = rs5.getInt("cashID");
      }
      rs5.close();

      String sql3 = "CALL CREATEPhysicalTransac(?, ?)";
      CallableStatement cs3 = connection.prepareCall(sql3);
      cs3.setInt(1, cardID);
      cs3.setInt(2, cashID);
      cs3.execute();
      cs3.close();

      Statement statement1 = connection.createStatement();
      String sql1 = "SELECT MAX(transacID) AS transacID FROM physicalTransaction";
      ResultSet rs1 = statement1.executeQuery(sql1);
      int transacID = -1;
      while (rs1.next()) {
        transacID = rs1.getInt("transacID");
      }
      rs1.close();
      if (transacID == -1) {
        throw new SQLException();
      }

      for (int itemID : this.items.keySet()) {
        String sqlLoop = "CALL CREATEOfflineTransacItems(?, ?, ?)";
        CallableStatement csLoop = connection.prepareCall(sqlLoop);
        csLoop.setInt(1, transacID);
        csLoop.setInt(2, itemID);
        csLoop.setInt(3, this.items.get(itemID));
        csLoop.execute();
        csLoop.close();
      }
    }
    return true;
  }
}
