package communicator;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ServerMain implements WindowListener
{

private final static Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
private FileHandler fileHandler = null;
private int port = 1201;
private ServerSocket serverSocket;
private Socket socket;
private JFrame ramka;
private JTextArea info;
private JPanel panelCentralny, panelWschodni;
private JScrollPane scrollCenter;
private String h = "Serwer 1.0\n";
private int count = 0;
private URL whatismyip;
private BufferedReader buffreader;
private String externalIP;
private Dane dane;
private ServerThread serverThread;
private ArrayList<Thread> watki;
private ArrayList<Uzytkownik> bazaUzytkownikow;
private Date currentDate;
private SimpleDateFormat sdf;
private FileOutputStream fos;
private ObjectOutputStream oos;
private BufferedOutputStream bos;
private JTextArea users;
private String usersString;




public static void main(String[] args)
{
	new ServerMain();
}

public ServerMain()
{
	try {
		fileHandler = new FileHandler("server.log", true);
	} catch (SecurityException e1) {
		e1.printStackTrace();
		System.exit(-1);
	} catch (IOException e1) {
		e1.printStackTrace();
		System.exit(-1);
	}
	
	fileHandler.setFormatter(new SimpleFormatter());
	fileHandler.setLevel(Level.WARNING);
	LOGGER.addHandler(fileHandler);
	
	ramka = new JFrame("Serwer komunikatora");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(600, 450);
	ramka.setLocationRelativeTo(null);
	ramka.setLayout(new BorderLayout());
	ramka.addWindowListener(this);

	info = new JTextArea(h);
	info.setEditable(false);
	info.setLineWrap(true);
	info.setWrapStyleWord(true);
	
	scrollCenter = new JScrollPane(info);
	panelCentralny = new JPanel(new BorderLayout());
	panelCentralny.add(scrollCenter, BorderLayout.CENTER);
	panelWschodni = new JPanel(new BorderLayout());
	usersString = "Users:";
	users = new JTextArea(usersString);
	users.setEditable(false);
	panelWschodni.add(users, BorderLayout.CENTER);
	
	
	ramka.add(panelCentralny, BorderLayout.CENTER);
	ramka.add(panelWschodni, BorderLayout.EAST);
	ramka.setVisible(true);
	
	message("Start serwera.", "Serwer");
	message("Otwieranie gniazdka serwera. Port: " +port, "Serwer");
	
	watki = new ArrayList<Thread>(20);
	bazaUzytkownikow = new ArrayList<Uzytkownik>(20);
	
	
	try {
		
		serverSocket = new ServerSocket(port);
		message("Lokalny adres serwera: " +InetAddress.getLocalHost().toString(), "Serwer");
		
		whatismyip = new URL("http://checkip.amazonaws.com");
		buffreader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		externalIP = buffreader.readLine();
		message("Zewnêtrzny adres serwera: " +externalIP, "Serwer");
				
		while (true)
		{
			socket = serverSocket.accept();
			message("Po³¹czenie z klientem: " +watki.size() +" " +socket.getInetAddress(), "Serwer");
			serverThread = new ServerThread(socket);
			watki.add(new Thread(serverThread));
			watki.get(count).start();
			count++;
		}
		}
		catch (Exception e)
		{
			zrzutLoga(e);
		}
}

public class ServerThread implements Runnable
{

private Socket socket;
private ObjectInputStream ois;
private ObjectOutputStream oos;

public ServerThread(Socket s)
{
	this.socket = s;
}
	
@Override
public void run()
{
	try {
		
		while (!this.socket.isClosed())
		{
		
			if (ois == null) 
				ois = new ObjectInputStream(this.socket.getInputStream());
			
			if (!socket.isClosed()) dane = (Dane) ois.readObject();		
		
			switch (dane.getTypDanych())
			{
			case MESSAGE: {
				message(dane.getWiadomosc(), dane.getNazwa());
				break;
			}
			case REGISTER: {
				message(dane.getNazwa() +" " +dane.getImie() +" " +dane.getNazwisko() +" " +dane.getEmail() +" " +(new String(dane.getHaslo())) +" : " +dane.getWiadomosc(), "Zarejestrowano nowego u¿ytkownika: ");
				bazaUzytkownikow.add(new Uzytkownik(bazaUzytkownikow.size(), dane.getNazwa(), dane.getImie(), dane.getNazwisko(), dane.getEmail(), dane.getHaslo(), dane.getZnajomi()));
				
				usersString = "Users:";
				for (int i = 0; i < bazaUzytkownikow.size(); i++)
				{
					usersString = usersString + "\n" +bazaUzytkownikow.get(i).getNumer()+"."+bazaUzytkownikow.get(i).getNazwa();
				}
				
				users.setText(usersString);
				dane.setDoKogo(bazaUzytkownikow.get(bazaUzytkownikow.size()-1).getNumer());
				break;
			}
			case LOG: {
				for (int i = 0; i < bazaUzytkownikow.size(); i++)
				{
					if ((dane.getEmail().equals(bazaUzytkownikow.get(i).getEmail())) && (dane.getDoKogo() == bazaUzytkownikow.get(i).getNumer()))
					{
						message("Zalogowany!", dane.getNazwa());
					}
				}
				
				break;
			}
			case UPDATE: {
				break;
			}
			case PING: {
				break;
			}
			}

			dane.setWiadomosc("ECHO: " +dane.getWiadomosc());
			
			if (oos == null) 
				oos = new ObjectOutputStream(this.socket.getOutputStream());
			
			oos.writeObject(dane);
			oos.flush();
		}
	}
	catch (SocketException e)
	{
		//zrzutLoga(e);
		message("roz³¹czy³ siê.", dane.getNazwa());
		//e.printStackTrace();
	}
	catch (Exception e)
	{
		zrzutLoga(e);
	}
	}
}

public void zrzutLoga(Exception e)
{
	LOGGER.log(Level.WARNING, e.getMessage(), e);
	//System.exit(-1);
}

public void message(String msg, String name)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("HH:mm:ss");
	info.append(sdf.format(currentDate) +" "+name +": " +msg +"\n");
}


@Override
public void windowActivated(WindowEvent arg0) {}

@Override
public void windowClosed(WindowEvent arg0) {}

@Override
public void windowClosing(WindowEvent arg0) {
	try {
	fos = new FileOutputStream("dane.txt");
	bos = new BufferedOutputStream(fos);
	oos = new ObjectOutputStream(bos);
	oos.writeObject(bazaUzytkownikow);
	oos.flush();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	try {
	oos.close();
	bos.close();
	fos.close();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}

@Override
public void windowDeactivated(WindowEvent arg0) {}

@Override
public void windowDeiconified(WindowEvent arg0) {}

@Override
public void windowIconified(WindowEvent arg0) {}

@Override
public void windowOpened(WindowEvent arg0) {}
}