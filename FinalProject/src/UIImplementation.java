
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UIImplementation extends JFrame implements DatabaseUI{
  private JPanel mainPanel;
  private JLabel displayMessage;
  private JTextArea DatabaseResult;
  private JButton buy;// use for buying operation
  private JButton addEmployee;
  private JButton deleteEmployee;
  private JButton showEmployee;
  private JButton showOnlineTransaction;//shows all recorded transaction into the output
  private JButton showOfflineTransaction;//shows all recorded transaction into the output
  private JButton showOnlineTransactionItem;//shows all recorded transaction into the output
  private JButton showOfflineTransactionItem;//shows all recorded transaction into the output
//  private JButton deleteOnlineTransaction;
//  private JButton deleteOfflineTransaction;
  private JButton storage;//shows all item stored in the output
  private JButton updateItem;
  private JButton addItem;
  private JButton addCoupon;
  private JButton deleteCoupon;
  private JButton showAllCoupon;
  private JButton showCurrentCoupon;
  private JButton clearOutput;//button to clear the output.

  private JButton showCard;
  private JButton showCash;
  private JButton showDelivery;
  private JButton manager;
  private JButton SearchOnlineTransacItems;
  private JButton SearchOfflineTransacItems;
 private JLabel curManager;
  private SingleMarket market;
  private Connection connection;

  //The main panel and layout is created when constructing the instance
  public UIImplementation(SingleMarket market, Connection connection,String managerName) {
    super();
    this.market = market;
    this.connection = connection;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setTitle("Store");
    setSize(1000, 1000);
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(10, 10));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //Text Area in the right, can be deleted.
//    JTextArea inputTextArea = new JTextArea(10, 20);
//    inputTextArea.setLineWrap(true); // /n as a sniffer
//    JScrollPane textAreaScroll = new JScrollPane(inputTextArea);
//    textAreaScroll.setBorder(BorderFactory.createTitledBorder("Pleas Type Input"));
    JPanel east = new JPanel();
    east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
    //!!! At this point, get manager name and put inside
    manager  = new JButton("Change Manager");
    curManager = new JLabel(managerName);
    curManager.setFont(new java.awt.Font("Dialog", 1, 20));
    east.add(manager);
    east.add(curManager);
    mainPanel.add(east, BorderLayout.EAST);

    //Error or Success message
//    displayMessage = new JLabel("Message shows up here");
//    displayMessage.setFont(new java.awt.Font("Dialog", 1, 20));
//    mainPanel.add(displayMessage, BorderLayout.SOUTH);

    //Operation panel.
    JPanel operation = new JPanel();
    operation.setBorder(BorderFactory.createTitledBorder("Operations"));
    operation.setLayout(new BoxLayout(operation, BoxLayout.Y_AXIS));
    buy = new JButton("Buy");
    buy.setActionCommand("buy");
    addEmployee = new JButton("Add Employee");
    deleteEmployee = new JButton("Delete Employee");
    showEmployee = new JButton("Show Employee");
    showOnlineTransaction = new JButton("Show Online Transaction");
    showOnlineTransactionItem = new JButton("Show Online Transaction Items");
    showOfflineTransaction = new JButton("Show Offline Transaction");
    showOfflineTransactionItem = new JButton("Show Offline Transaction Items");
//    deleteOnlineTransaction = new JButton("Delete Online Transaction");
//    deleteOfflineTransaction = new JButton("Delete Offline Transaction");
    storage = new JButton("Show Storage");
    updateItem = new JButton("Update Item");
    addItem = new JButton("Add Item");
    deleteCoupon = new JButton("Delete Coupon");
    addCoupon = new JButton("Add Coupon");
    showAllCoupon = new JButton("Show All Coupon");
    showCurrentCoupon = new JButton("Show Current Manager Coupon");
    showCard = new JButton("Show Card Records");
    showCash = new JButton("Show Cash Records");
    showDelivery = new JButton("Show Delievery Records");
    SearchOnlineTransacItems = new JButton("Search Online Transaction Item");
    SearchOfflineTransacItems = new JButton("Search Offline Transaction Item");
    clearOutput = new JButton("Clear Output");

    operation.add(buy);
    operation.add(showOnlineTransaction);
    operation.add(showOnlineTransactionItem);
    operation.add(showOfflineTransaction);
    operation.add(showOfflineTransactionItem);
    operation.add(SearchOnlineTransacItems);
    operation.add(SearchOfflineTransacItems);
    operation.add(showCard);
    operation.add(showCash);
    operation.add(showDelivery);
//    operation.add(deleteOnlineTransaction);
//    operation.add(deleteOfflineTransaction);
    operation.add(addCoupon);
    operation.add(deleteCoupon);
    operation.add(showAllCoupon);
    operation.add(showCurrentCoupon);
    operation.add(storage);
    operation.add(updateItem);
    operation.add(addItem);
    operation.add(addEmployee);
    operation.add(deleteEmployee);
    operation.add(showEmployee);
    operation.add(clearOutput);
    mainPanel.add(operation,BorderLayout.WEST);
    this.buttonActions();

    //Center Result panel
    JPanel resultPanel = new JPanel();
    resultPanel.setBorder(BorderFactory.createTitledBorder("Query Output"));
    resultPanel.setLayout(new FlowLayout());

    mainPanel.add(resultPanel, BorderLayout.CENTER);

    //The output area with scroll bar
    DatabaseResult = new JTextArea();
    DatabaseResult.setEditable(false);
    DatabaseResult.setRows(50);
    DatabaseResult.setColumns(100);
    JScrollPane resultScrollPanel = new JScrollPane(DatabaseResult);
    resultPanel.add(resultScrollPanel);
    pack();

  }


//the button actions initiation
  private void buttonActions(){
    buy.addActionListener((evt->this.buyAction()));
    addEmployee.addActionListener((evt->this.addEmployeeAction()));
    deleteEmployee.addActionListener((evt->this.deleteEmployeeAction()));
    showEmployee.addActionListener((evt->this.showEmployeeAction()));
    showOnlineTransaction.addActionListener((evt->this.showOnlineTransactionAction()));
    showOfflineTransaction.addActionListener((evt->this.showOfflineTransactionAction()));
    showOnlineTransactionItem.addActionListener((evt->this.showOnlineTransactionItemAction()));
    showOfflineTransactionItem.addActionListener((evt->this.showOfflineTransactionItemAction()));
    SearchOfflineTransacItems.addActionListener((evt->this.searchOfflineTransacItemsAction()));
    SearchOnlineTransacItems.addActionListener((evt->this.searchOnlineTransacItemsAction()));
    addItem.addActionListener((evt->this.addItemAction()));
    updateItem.addActionListener((evt->this.updateItemAction()));
    clearOutput.addActionListener((evt->this.clear()));
    storage.addActionListener((evt->showItemAction()));
    addCoupon.addActionListener((evt->this.addCouponAction()));
    deleteCoupon.addActionListener((evt->this.deleteCouponAction()));
    showAllCoupon.addActionListener((evt->this.showAllCouponAction()));
    showCurrentCoupon.addActionListener((evt->this.showCurrentCouponAction()));
    manager.addActionListener((evt->this.changeManagerAction()));
    showCard.addActionListener((evt->this.showCardAction()));
    showCash.addActionListener((evt->this.showCashAction()));
    showDelivery.addActionListener((evt->this.showDeliveryAction()));
//    create.addActionListener((evt->this.renderWarning("Warning")));
//    update.addActionListener(((evt->this.optionChooser(new String[]{"1","2","3"},"Message"))));
  }

  //Defines the action of button Buy(creating new transaction)
  //Coupon can be applied with given coupon number
  private void buyAction(){
    //choose online or offline
    ItemProcessor itemProc;
    String method;
    this.queryOutput("Transaction type");
    try {
      method = this
          .optionChooser(new String[]{"Online", "Offline"}, "Please Choose Transaction Category");
      this.queryOutput(method);
      itemProc = new ItemProcessor(method);

    }
    catch (IllegalArgumentException e){
      this.renderWarning("Transaction closed!");
      return;
    }


    this.queryOutput("Purchased Item");
    //continue purchasing item until finish
    while(true){
      try {
        String[] purchaseItem = this.jOptionPaneMultiInput("Item ID", "Quantity",
            "Please Enter the item and number you wish to purchase");
        this.queryOutput(purchaseItem);
        itemProc.insertItem(Integer.valueOf(purchaseItem[0]),Integer.valueOf(purchaseItem[1]),connection);
        String result = this
            .optionChooser(new String[]{"End", "Continue"}, "Are there more items?");
        //add to database at here
        if (result.equals("End")) {
          break;
        }
      }
      catch (IllegalArgumentException e){
        this.renderWarning("Illegal operation, please retry");
        continue;
      }
      //user click cancel button
      catch (IllegalStateException e){
        this.renderWarning("Transaction closed!");
        return;
      }
      catch (Exception e){
        this.renderWarning("Invalid item, please retry");
        return;
      }
    }

    int totalCost = itemProc.getTotalCost();
    this.renderMessage(Integer.toString(totalCost),"The Total Cost of Items.");

    int couponPWD;
    //applying coupon to payment
    this.queryOutput("Coupon");
    String couponInfo = this.jOptionPaneSingleInput("Coupon PWD", "Please enter coupon"
        + "you wish to apply", "Unable to find coupon");
    if(couponInfo.equals("")){
      this.renderWarning("Coupon not entered, continue");
      this.queryOutput("No coupon");
      couponPWD = -1;
    }
    else{
      this.queryOutput(couponInfo);
      couponPWD = Integer.valueOf(couponInfo);
    }


    this.queryOutput("Purchase Method");
    //purchase method, card or cash
    String purchaseMethod,shippingCompany;
      try {
        //if it is offline transaction, provide choice of card or cash payment.
        if(method.equals("Offline")){
          purchaseMethod = this
              .optionChooser(new String[]{"Card", "Cash"}, "Please Choose Purchase Method");
          shippingCompany = "";
        }
        else{
          //online purchase only accept card
          purchaseMethod = "Card";
        }

        String cashAmount = "";
        String cardAmount = "";
        String[] cardInfo = new String[2];
        //user choose to pay by cash
    if(purchaseMethod.equals("Cash")){
      //ask how many has user paid
      this.queryOutput("Cash");
      cashAmount = this.jOptionPaneSingleInput("Cash amount","Total payment","");
      this.queryOutput("Cash paid");
      if(cashAmount.equals("")){
        this.renderWarning("No cash paid, transaction end");
        return;
      }
      this.queryOutput(cashAmount);

    }
    //user choose to use card as payment
    else{
      this.queryOutput("Card");
      cardInfo = this.jOptionPaneMultiInput("Number","Bank","Please Enter the card number and bank name of your card");
      this.queryOutput(cardInfo);
      this.queryOutput("Card paid");
      cardAmount = this.jOptionPaneSingleInput("Card payment","Total paid","");
      if(cardAmount.equals("")){
        this.renderWarning("Card payment failed, transaction end");
        return;
      }
      this.queryOutput(cardAmount);

    }

    //address receiver name, shipping company.

    //if it is online, choose deliever method, rush or no rush and transportation method.
    if(method.equals("Online")){
      this.queryOutput("Shipping Information");
      String[] res = this.jOptionPaneMultiInput("Address","Receiver","Shipping Information");
      this.queryOutput(res);
      this.queryOutput("Shipping Company");
      shippingCompany = this.jOptionPaneSingleInput("Shipping Company", "Please Choose Shipping Company",
          "");
      this.queryOutput(shippingCompany);
      itemProc.push(Integer.valueOf(cardInfo[0]),Integer.valueOf(cardAmount),0,couponPWD,cardInfo[1],res[0],res[1],shippingCompany,connection);
    }
    else{
      itemProc.push(0,0,Integer.valueOf(cashAmount),couponPWD,"","","","",connection);
    }
      } catch (NumberFormatException e){
        this.renderWarning("Incorrect field input, expecting number");
        return;
      } catch (IllegalArgumentException e){
      this.renderWarning("Invalid Input, transaction closed.");
      return;
    }
      catch (IllegalStateException e){
        this.renderWarning("Transaction closed!");
        return;
      }
      catch (SQLException e){
        this.renderWarning("Not enough money, purchase failed");
        return;
      }
      catch (Exception e){
        this.renderWarning("Incorrect purchase, transaction closed");
        return;
      }
  }


  //Employee adding: needs name, job description, gender
  private void addEmployeeAction(){
    JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(50);
    JTextField zField = new JTextField(5);

    String[] answer = new String[]{"","",""};
    JPanel myPanel = new JPanel();

    myPanel.add(new JLabel("Employee Name"));
    myPanel.add(xField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("Job Description"));
    myPanel.add(yField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("Gender"));
    myPanel.add(zField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Please Enter the information of new Employee", JOptionPane.OK_CANCEL_OPTION);
    if(result == JOptionPane.CANCEL_OPTION){
      this.renderWarning("Adding canceled");
      return;
    }

    if (result == JOptionPane.OK_OPTION) {
      try {
        answer[0] = xField.getText();
        answer[1] = yField.getText();
        answer[2] = zField.getText();
      }
      catch (NullPointerException e){
        this.renderWarning("Invalid Input, adding failed");
        return;
      }
    }

    if(answer[0].equals("") || answer[1].equals("") || answer[2].equals("")){
      this.renderWarning("Invalid Input, adding failed");
      return;
    }

    try {
      market.addEmployee(answer[0], answer[2], answer[1], connection);
    }
    catch (SQLException e){
      this.renderWarning("Unsuccessful Add, aborted");
      return;
    }
    this.queryOutput("Added Employee:");
    this.queryOutput(answer);
  }

  //Employer deleting: needs name and id
  private void deleteEmployeeAction(){

    try {
      String[] result = this.jOptionPaneMultiInput("Employee Name", "Employee ID", "Please enter"
          + "the employee information you wish to delete");
      market.deleteEmployee(result[0],Integer.valueOf(result[1]),connection);
      this.queryOutput("Deleted Employee:");
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unable to delete the employee, aborted");
      return;
    }

  }

  //showing employee returned by database
  private void showEmployeeAction(){
    try {
      String[] result = market.showEmployees(connection);
      this.queryOutput(result);
    } catch (SQLException e) {
      this.renderWarning("Show Unsuccessful");
      return;
    }

  };

  //showing transaction returned by database
  private void showOnlineTransactionAction(){
    try {
      String[] result = market.showOnlineTransactions(connection);
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Failed to show online transaction");
      return;
    }
  }

  private void showOfflineTransactionAction(){
    try {
      String[] result = market.showOfflineTransactions(connection);
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Failed to show online transaction");
      return;
    }
  }

  //showing transaction returned by database
  private void showOnlineTransactionItemAction(){
    try {
      String[] res = market.showOnlineSaleItems(connection);
      this.queryOutput("Showing Items in Online Transaction:");
      this.queryOutput(res);
    }
    catch (Exception e){
      this.renderWarning("Failed to show online transaction items");
      return;
    }
  }

  private void showOfflineTransactionItemAction(){
    try {
      String[] res = market.showOfflineSaleItems(connection);
      this.queryOutput("Showing Items in Offline Transaction:");
      this.queryOutput(res);
    }
    catch (Exception e){
      this.renderWarning("Failed to show offline transaction items");
      return;
    }
  }


  //delete item: id.
  private void searchOnlineTransacItemsAction(){
    try {
      String result = jOptionPaneSingleInput("Transaction ID",
          "Search item in a given Transaction", "");

      if (result.equals("")) {
        this.renderWarning("Unsuccessful Search");
        return;
      }

      String[] output = market.showOnlineSaleItemsWithID(Integer.valueOf(result),connection);
      this.queryOutput("Search result:");
      this.queryOutput(output);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful Search,canceled.");
      return;
    }
  }

  private void searchOfflineTransacItemsAction(){
    try {
      String result = jOptionPaneSingleInput("Transaction ID",
          "Search item in a given Transaction", "");

      if (result.equals("")) {
        this.renderWarning("Unsuccessful Search");
        return;
      }

      String[] output = market.showOfflineSaleItemsWithID(Integer.valueOf(result),connection);
      this.queryOutput("Search result:");
      this.queryOutput(output);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful Search,canceled.");
      return;
    }
  }

  private void showCardAction(){
    try {
      String[] result = market.showAllCards(connection);
      this.queryOutput("Showing all cards");
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Failed to show cards records");
      return;
    }
  }

  private void showCashAction(){
    try {
      String[] result = market.showAllCash(connection);
      this.queryOutput("Showing all cash");
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Failed to show cash records");
      return;
    }
  }

  private void showDeliveryAction(){
    try {
      String[] result = market.showAllTransportations(connection);
      this.queryOutput("Showing all transportation");
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Failed to show delivery records");
      return;
    }
  }

  //Insert item: name, amount, price
  private void addItemAction(){
    JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(5);
    JTextField zField = new JTextField(5);

    String[] answer = new String[]{"","",""};
    JPanel myPanel = new JPanel();

    myPanel.add(new JLabel("Item name"));
    myPanel.add(xField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("Item amount"));
    myPanel.add(yField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("Price"));
    myPanel.add(zField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Please Enter the information of new Item", JOptionPane.OK_CANCEL_OPTION);
    if(result == JOptionPane.CANCEL_OPTION){
      this.renderWarning("Adding canceled");
      return;
    }

    if (result == JOptionPane.OK_OPTION) {
      try {
        answer[0] = xField.getText();
        answer[1] = yField.getText();
        answer[2] = zField.getText();
      }
      catch (NullPointerException e){
        this.renderWarning("Invalid Input, adding failed");
        return;
      }
    }

    if(answer[0].equals("") || answer[1].equals("") || answer[2].equals("")){
      this.renderWarning("Invalid Input, adding failed");
      return;
    }
    try {
      market.storageAdd(answer[0], Integer.valueOf(answer[1]), Integer.valueOf(answer[2]),
          connection);
    }
    catch (Exception e){
      this.renderWarning("Unable to add item, please check your input");
      return;
    }
    this.queryOutput("Added Item");
    this.queryOutput(answer);
  }

  //delete item: id.
  private void updateItemAction(){
    try {
      String[] result = jOptionPaneMultiInput("Item ID", "Amount decreased",
          "Please Enter Update Information");

      if (result[0].equals("") || result[1].equals("")) {
        this.renderWarning("Deletion canceled");
        return;
      }

      market.storageChange(Integer.valueOf(result[0]),Integer.valueOf(result[1]),connection);
      this.queryOutput("Delete Item");
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful Update,canceled.");
      return;
    }
  }

  //showing item returned by database
  private void showItemAction(){
    try {
      String[] result = market.showItems(connection);
      this.queryOutput(result);
    }
    catch (SQLException e){
      this.renderWarning("Unable to fetch Items.");
      return;
    }
  };

  //Adding coupon
  private void addCouponAction(){
    try {
      String[] result = jOptionPaneMultiInput("Coupon PWD", "Discount",
          "Please Enter Coupon Information");
      if (result[0].equals("") || result[1].equals("")) {
        this.renderWarning("Adding canceled");
        return;
      }
      market.addCoupon(Integer.valueOf(result[0]), Integer.valueOf(result[1]),connection);
      this.queryOutput("Add Coupon");
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful add, canceled");
      return;
    }
  }


  //deleting coupon
  private void deleteCouponAction(){
    try {
      String result = jOptionPaneSingleInput("Coupon ID", "Please Enter Coupon ID",
          "Unable to delete the Coupon, aborted");

      if (result.equals("")) {
        this.renderWarning("Deletion canceled");
        return;
      }
      market.delCounpon(Integer.valueOf(result), connection);
      this.queryOutput("Delete Coupon");
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful delete, canceled");
      return;
    }
  }

  //showing coupon
  private void showAllCouponAction(){
    try {
      this.queryOutput("Displaying all coupons:");
      String[] result = market.showAllCoupons(connection);
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful show, canceled");
      return;
    }
  }

  //showing coupon
  private void showCurrentCouponAction(){
    try {
      this.queryOutput("Displaying all coupons:");
      String[] result = market.showCoupons(connection);
      this.queryOutput(result);
    }
    catch (Exception e){
      this.renderWarning("Unsuccessful show, canceled");
      return;
    }
  }


  private String  jOptionPaneSingleInput(String label, String title, String warning){
    JTextField xField = new JTextField(5);
    String answer = "";

    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel(label));
    myPanel.add(xField);
    int result = JOptionPane.showConfirmDialog(null, myPanel,
        title, JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      answer = xField.getText();
    }

    return answer;
  }

  //Presents an option panel that allows user to choose one of them.
  private String optionChooser(String[] options, String show) {
    if (options.length == 0) {
      return "";
    }
    int retvalue = JOptionPane
        .showOptionDialog(UIImplementation.this, show, "Options",
            JOptionPane.YES_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    if (retvalue == JFileChooser.ERROR_OPTION) {
      throw new IllegalArgumentException();
    }
    return options[retvalue];
  }

  //change current manager by typing
  private void changeManagerAction(){
    try {
      String[] result = this.jOptionPaneMultiInput("Manager name", "Gender",
          "Please enter new manager information");

      market.changeManager(result[0],result[1],connection);
      this.queryOutput("New Manager");
      this.queryOutput(result);
      this.updateCurManager(result[0]);
    }
    catch(IllegalStateException e){
      return;
    }
    catch(Exception e){
      this.renderWarning("Failed to create new manager, aborted");
      return;
    }

  }

  //Present user a input menu with information needed for input

  private String[] jOptionPaneMultiInput(String first, String second,String message) {
    JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(5);

    String[] answer = new String[]{"",""};
    JPanel myPanel = new JPanel();

    myPanel.add(new JLabel(first));
    myPanel.add(xField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel(second));
    myPanel.add(yField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        message, JOptionPane.OK_CANCEL_OPTION);
    if(result == JOptionPane.CANCEL_OPTION){
      throw new IllegalStateException();
    }

      if (result == JOptionPane.OK_OPTION) {
        try {
        answer[0] = xField.getText();
        answer[1] = yField.getText();

      }
        catch (NullPointerException e){
          throw new IllegalStateException();
        }
    }

    if(answer[0].equals("") || answer[1].equals("")){
      throw new IllegalArgumentException();
    }

    return answer;
  }

  //render a warning message
  public void renderWarning(String warn) {
    JOptionPane.showMessageDialog(UIImplementation.this, warn, "Warning", JOptionPane.WARNING_MESSAGE);
  }


  //clear the message in the output.
  public void clear() {
    this.DatabaseResult.setText("");
  }

  //render the message below as a guide.
  public void renderMessage(String message,String title) {
    JOptionPane.showMessageDialog(UIImplementation.this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  //outprint the query output to UI
  public void queryOutput(String[] message) {
    Objects.requireNonNull(message);
    for(String s : message)
      DatabaseResult.append(s + "\n");
  }

  //overloading method for output
  public void queryOutput(String message) {
    Objects.requireNonNull(message);
    DatabaseResult.append(message + "\n");
  }

  private void updateCurManager(String manager){
    this.curManager.setText(manager);
  }
  public static void main(String args[])  //static method
  {
//    UIImplementation ui = new UIImplementation();
//    ui.setVisible(true);
  }
}
