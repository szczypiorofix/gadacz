package communicator;


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

private String JDBC_Driver;
private String DB_Url;
private String userName;
private String userPass;


/** Podstawowy konstruktor klasy MySQLBase.
 * @param jDBC_Driver Nazwa sterownika JDBC.
 * @param dB_Url Adres bazy MySQL.
 * @param userName Nazwa u¿ytkownika bazy danych MySQL.
 * @param userPass Has³o u¿ytkownika bazy danych MySQL.
 */
public MySQLBase(String jDBC_Driver, String dB_Url, String userName, String userPass)
{
	super("Baza MySQL");
	setSize(500,350);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setLocationRelativeTo(null);
	JDBC_Driver = jDBC_Driver;
	DB_Url = dB_Url;
	this.userName = userName;
	this.userPass = userPass;
}

public void connectToBase()
{
	
}

}
