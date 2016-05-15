package communicator;

import java.awt.Component;

import javax.swing.JFrame;

public class MySQLBase extends JFrame{

private String JDBC_Driver;
private String DB_Url;
private String userName;
private String userPass;


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
