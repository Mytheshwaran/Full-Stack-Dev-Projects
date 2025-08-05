package com.dhyan.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Application {
	public static void main(String[] args) {

		Application application = new Application();
		Connection connection = application.getDatabaseInfo();
		application.chooseLoginOption(connection);
	}
	
	/**
	 * Getting Database information by reading XML file
	 * @return
	 */
	public Connection getDatabaseInfo() {
		Connection connection = null;
		try {
			File newFile = new File("UserDetails.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(newFile);
			document.getDocumentElement().normalize();
			String url = " ", username = " ", password = " ";
			NodeList nodeList = document.getElementsByTagName("jdbc");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element nodeElement = (Element) node;
				Node node1 = nodeElement.getElementsByTagName("url").item(0);
				url = node1.getTextContent();
				Node node2 = nodeElement.getElementsByTagName("username").item(0);
				username = node2.getTextContent();
				Node node3 = nodeElement.getElementsByTagName("password").item(0);
				password = node3.getTextContent();
			}
			connection = getDatabaseConnection(url, username, password);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}
	
	/**
	 * Getting Database connection
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public Connection getDatabaseConnection(String url, String username, String password) {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				System.out.println("Connected to the PostgreSQL server successfully.");
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	/**
	 * Choose Login option
	 * @param conn
	 */
	public void chooseLoginOption(Connection conn) {
		System.out.println("######  HOME PAGE  ######");
		System.out.println("Enter \n1.Login \n2.Register");
		String opt = selectOption();
		if (opt.equals("Yes")) {
			login(conn);
		} else {
			System.out.println("~~~~~Create new Account~~~~");
			register(conn);
		}
	}

	/**
	 * Register user
	 * @param conn
	 */
	public void register(Connection conn) {

		System.out.print("Enter name: ");
		String name = IOUtility.getString();

		try {

			boolean isUserExist = checkUser(conn, name);
			if (!isUserExist) {
				adduser(conn, name);
				System.out.println("Login to account");
				login(conn);
			} else {
				System.out.println("User Already Exist");
				register(conn);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Login with user details
	 * @param conn
	 */
	public void login(Connection conn) {
		String query = "select * from userdetails;";
		PreparedStatement statement = null;
		try {
			System.out.print("Enter Name: ");
			String newUser = IOUtility.getString();

			statement = conn.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			boolean isUserExist = checkUser(conn, newUser);
			
			if (isUserExist) {
				System.out.print("Enter password: ");
				String newPassword = IOUtility.getString();
				while (result.next()) {
					String name = result.getString("name");
					String password = result.getString("password");
					if (newUser.equals(name) && newPassword.equals(password)) {
						dropUser(conn, result.getInt("id"));
						break;
					} else if (newUser.equals(name)) {
						System.out.println("Password Wrong!");
						checkPassword(result, conn, password);
						break;
					}					
				}
			}
			else
			{
				System.out.println("Your User name may be wrong!");
				System.out.println("Enter \n1.Register \\n2.relogin ");
				String option = selectOption();
				if (option.equals("Yes"))
				{
					System.out.println("~~~~~Create new Account~~~~");
					register(conn);
				}
				else
				{
					login(conn);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Drop user from database
	 * @param conn
	 * @param id
	 */
	public void dropUser(Connection conn,int id) {
		System.out.println("!!!!  Logged in successfully  !!!!");
		System.out.println("1.Remove Account \n2.Log Out");
		String opt = selectOption();
		if (opt == "Yes") {
			String dropAccount = "Delete from userdetails where id=?;";

			try {
				PreparedStatement statement = conn.prepareStatement(dropAccount);
				statement.setInt(1, id);
				statement.executeUpdate();
				System.out.println("====Account Deleted Successfully====");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else
		{
			chooseLoginOption(conn);
		}
	}

	/**
	 * Check user exist in database or not
	 * @param conn
	 * @param name
	 * @return
	 */
	public boolean checkUser(Connection conn, String name) {
		String checkDetails = "select name from userdetails where name=?;";
		boolean isUserExist = false;
		try {
			PreparedStatement queryStatement = conn.prepareStatement(checkDetails);
			queryStatement.setString(1, name);
			ResultSet result = queryStatement.executeQuery();
			isUserExist = result.next();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isUserExist;
	}
	
	/**
	 * Check password correct or not
	 * @param result
	 * @param conn
	 * @param correctPassword
	 */
	public void checkPassword(ResultSet result, Connection conn, String correctPassword) {
		int maxReEnterPassword = 3;
		boolean isPasswordCorrect = false;
		do {
			System.out.print("Re-Enter Password: ");
			String newPassword = IOUtility.getString();
			if (newPassword.equals(correctPassword)) {
				try {
					dropUser(conn, result.getInt("id"));
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				isPasswordCorrect = true;
				break;
			}
			maxReEnterPassword--;
		} while (maxReEnterPassword > 0);
		if (!isPasswordCorrect)
		{
			System.out.println("Try after sometime...");
			System.out.println("Else Enter \n1.Create new account"
					+ "\n2.exit");
			String option =selectOption();
			if (option.equals("Yes"))
			{
				System.out.println("~~~~~Create new Account~~~~");
				register(conn);
			}
		}
	}
	
	/**
	 * Add User to database
	 * @param conn
	 * @param name
	 */
	public void adduser(Connection conn, String name) {

		String insert = "insert into userdetails(name,password) values(?,?);";
		try {
			System.out.print("Enter password: ");
			String password = IOUtility.getString();

			PreparedStatement statement = conn.prepareStatement(insert);
			statement.setString(1, name);
			statement.setString(2, password);
			statement.executeUpdate();

			System.out.println("Account registered Successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Select option
	 * @return
	 */
	public String selectOption()
	{
		int opt=IOUtility.getInteger();
		String option=" ";
		switch(opt)
		{
		case 1:
			option="Yes";
			break;
		case 2:
			option="No";
			break;
		default:
			System.out.println("Enter correct option");
			selectOption();
			break;
		}
		return option;
	}
}
