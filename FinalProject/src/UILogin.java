import com.sun.jdi.NativeMethodException;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UILogin {
  static final String JDBC = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost:3306/mard";

//  static String USER ="root";
//  static String PWD = "Su20020319";
  JPanel panel1;
  JFrame frame1;
  JLabel USERLabel;
  JLabel PWDLabel;
  JTextField USERText, PWDText;
  JButton connect;

  String USER, PWD;
  private Connection connection;
  private static JLabel password1, label, managerNameLanel,managerGenderLabel;
  private static JTextField username, managerName,managerGender;
  private static JButton login,create;
  private static JPasswordField Password;
  JPanel panel = new JPanel();
  JFrame frame = new JFrame();
  private String UserName = "";

  public UILogin(Connection connection){
    this.connection = connection;
    panel.setLayout(null);
    frame.setTitle("LOGIN PAGE");
    frame.setLocation(new Point(500, 300));
    frame.add(panel);
    frame.setSize(new Dimension(400, 200));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.sqlConnectionSetUp();


  }

  private void beginLogin(){
    try {
      String sql = "SELECT managerName FROM manager";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sql);
      while (rs.next()) {
        UserName = rs.getString("managerName");
      }
      rs.close();
    }
    catch (SQLException e){
      this.UserName = "";
    }

    if(this.UserName.equals("")){
      createManagerPage();
    }
    else{
      loginPage();
    }

    frame.setVisible(true);
  }


  private void loginPage(){
    label = new JLabel("Username");
    label.setBounds(100, 8, 70, 20);
    panel.add(label);
    //constructing user name
    username = new JTextField();
    username.setBounds(100, 27, 193, 28);
    panel.add(username);

    // Button constructor
    login = new JButton("Login");
    login.setBounds(100, 90, 90, 25);
    login.setForeground(Color.WHITE);
    login.setBackground(Color.BLACK);
    login.addActionListener((evt-> this.loginActions()));
    panel.add(login);
  }

  private void createManagerPage(){
    //Creating a manager
    // manager Name, Gender
    managerNameLanel = new JLabel("Manager Name");
    managerNameLanel.setBounds(100, 8, 100, 20);
    panel.add(managerNameLanel);
    //constructing user name
    managerName = new JTextField();
    managerName.setBounds(100, 27, 193, 28);
    panel.add(managerName);

    managerGenderLabel = new JLabel("Gender");
    managerGenderLabel.setBounds(100, 55, 70, 20);
    panel.add(managerGenderLabel);

    //constructing user name
    managerGender = new JTextField();
    managerGender.setBounds(100, 75, 193, 28);
    panel.add(managerGender);

    // Button constructor
    create = new JButton("Login");
    create.setBounds(100, 110, 90, 25);
    create.setForeground(Color.WHITE);
    create.setBackground(Color.BLACK);
    create.addActionListener((evt-> this.createMangagerAction()));
    panel.add(create);
  }

  //There is already a manager in the database, ask the user about the name.
  public void loginActions() {
    String Username = username.getText();

    //Ask database for correctness of password and usernaeme
    if (Username.equals(this.UserName) ){
      JOptionPane.showMessageDialog(null, "Login Successful");
      this.frame.setVisible(false);
      SingleMarket market = new SingleMarket(Username);
      this.loginSucceeded(market,connection,Username);
    }
    else
      JOptionPane.showMessageDialog(null, "Username mismatch ");
  }

  //If there is no manager in the database, then the user must be the new manager
  public void createMangagerAction(){
    String name, gender;
    SingleMarket market;
    while(true) {
      name = managerName.getText();
      gender = managerGender.getText();
      //continue creating manager when not successfully created.
      if(name.equals("") || gender.equals("")){
        JOptionPane.showMessageDialog(null, "Unable to create manager, please retry");
        return;
      }

      JOptionPane.showMessageDialog(null, "Manager assigned with name:" + name
          + ", gender:" + gender);
      try {
        market = new SingleMarket(name, gender, connection);
        break;
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Unable to create manager, please retry");
        return;
      }
    }

    this.frame.setVisible(false);
    this.loginSucceeded(market,connection,name);
  }

  public void loginSucceeded(SingleMarket market, Connection connection,String managerName){
    UIImplementation ui = new UIImplementation(market,connection,managerName);
    ui.setVisible(true);

  }

  public void sqlConnectionSetUp(){
    panel1 = new JPanel();
    frame1 = new JFrame();
    panel1.setLayout(null);
    frame1.setTitle("LOGIN PAGE");
    frame1.setLocation(new Point(500, 300));
    frame1.add(panel1);
    frame1.setSize(new Dimension(400, 200));
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    USERLabel = new JLabel("SQL User Name");
    USERLabel.setBounds(100, 8, 100, 20);
    panel1.add(USERLabel);
    //constructing user name
    USERText = new JTextField();
    USERText.setBounds(100, 27, 193, 28);
    panel1.add(USERText);

    PWDLabel = new JLabel("SQL Password");
    PWDLabel.setBounds(100, 55, 100, 20);
    panel1.add(PWDLabel);

    //constructing user name
    PWDText = new JTextField();
    PWDText.setBounds(100, 75, 193, 28);
    panel1.add(PWDText);

    // Button constructor
    connect = new JButton("Connect");
    connect.setBounds(100, 110, 90, 25);
    connect.setForeground(Color.WHITE);
    connect.setBackground(Color.BLACK);
    connect.addActionListener((evt-> this.setUpConnection()));
    panel1.add(connect);
    frame1.setVisible(true);
  }

  private void setUpConnection(){
    while(true) {
      USER = USERText.getText();
      PWD = PWDText.getText();
      //continue creating manager when not successfully created.
      if(USER.equals("") || PWD.equals("")){
        JOptionPane.showMessageDialog(null, "Empty input, please retry");
        return;
      }
      try {
        connection = DriverManager.getConnection(DB_URL, USER, PWD);
        this.frame1.setVisible(false);
        this.beginLogin();
        break;
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Unable to connect, please retry");
        return;
      }
    }
  }


  public static void main(String args[]) throws ClassNotFoundException, SQLException  //static method
  {


    Scanner scan = new Scanner(System.in);
    Connection connection = null;
    Statement statement = null;
    Class.forName(JDBC);


    UILogin login = new UILogin(connection);
  }


}
