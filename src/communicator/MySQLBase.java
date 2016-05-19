package communicator;


import java.awt.HeadlessException;

import javax.swing.JFrame;

/** Podstawowa klasa s³u¿¹ca do komunikowania siê z baz¹ danych MySQL.
 * @author Piotrek
 * @see ClientMain
 * @see ServerMain
 * @see Dane
 * @see TypDanych
 * @see Uzytkownik
 * @see Znajomy
 */
public class MySQLBase extends JFrame{

private static final long serialVersionUID = 1L;

/**
 * Nazwa sterownika JDBC.
 */
private String JDBC_Driver;
/**
 * URL strony z baz¹ danych MySQL.
 */
private String DB_Url;
/**
 * Nazwa u¿ytkownika, potrzebna do zalogowania w bazie MySQL.
 */
private String userName;
/**
 * Has³o u¿ytkownika, potrzebne do zalogowania w bazie MySQL.
 */
private String userPass;





/** Podstawowy konstruktor klasy MySQLBase.
 * @param jDBC_Driver   - Sterownik JDBC.
 * @param dB_Url        - Adres URL bazy daych.
 * @param userName      - Nazwa u¿ytkownika.
 * @param userPass      - Has³o u¿ytkownika.
 * @throws HeadlessException Jakiœtam wyj¹tek.
 */
public MySQLBase(String jDBC_Driver, String dB_Url, String userName, String userPass) throws HeadlessException {
	
	super("Baza MySQL");
	JDBC_Driver = jDBC_Driver;
	DB_Url = dB_Url;
	this.userName = userName;
	this.userPass = userPass;
	setSize(300, 300);
	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	setLocationRelativeTo(null);
	setResizable(false);
}


public void connectToBase()
{
	
}


public String getJDBC_Driver() {
	return JDBC_Driver;
}


public void setJDBC_Driver(String jDBC_Driver) {
	JDBC_Driver = jDBC_Driver;
}


public String getDB_Url() {
	return DB_Url;
}


public void setDB_Url(String dB_Url) {
	DB_Url = dB_Url;
}


public String getUserName() {
	return userName;
}


public void setUserName(String userName) {
	this.userName = userName;
}


public String getUserPass() {
	return userPass;
}


public void setUserPass(String userPass) {
	this.userPass = userPass;
}



}
