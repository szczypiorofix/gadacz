package communicator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ServerMain implements WindowListener
{


private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";  
private static final String MYSQL_DB_URL = "jdbc:mysql://localhost/uzytkownicy";
private static final String MYSQL_USER = "root";
private static final String MYSQL_PASS = "";
private final static Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
private FileHandler fileHandler = null;
private int port = 1201;
private ServerSocket serverSocket;
private Socket tempSocket;
private ObjectInputStream tempStreamIn;
private ObjectOutputStream tempStreamOut;
private HashMap<Integer, Socket> sockets;
private HashMap <Integer, ObjectOutputStream> outStreams;
private HashMap <Integer, ObjectInputStream> inStreams;
private JFrame ramka;
private JTextArea info;
private JPanel panelLewy, panelPrawy, panelPolnocny, panelPoludniowy;
private JButton bPolaczZBaza, bWyswietlRekordy, bCheckIP;
private JScrollPane scrollCenter, scrollEast;
private String h = "               ROBCO INDUSTRIES UNIFIED OPERATING SYSTEM\n                  "
		+ "COPYRIGHT 2075-2077 ROBCO INDUSTRIES\n                               "
		+ "- Server 1 -\n\n"
		+ " - RobCo Communicator Management System -\n"
		+ " ==========================================\n\n";
private int count = 1;
private URL whatismyip;
private BufferedReader readerIP;
private String externalIP;
private Dane dane;
private Thread t;
private ServerThread serverThread;
private HashMap<Integer, Uzytkownik> bazaUzytkownikow;
private HashMap<Integer, Boolean> whoIsOnline;
private Date currentDate;
private SimpleDateFormat sdf;
private JTextArea users;
private JSplitPane split1;
private String usersString;
private boolean logOK = false;
private MySQLBase bazaMySQL;
private final InputStream FALLOUT_FONT = getClass().getResourceAsStream("/res/FalloutFont.ttf");
private final ImageIcon TERMINALBACKGROUND = new ImageIcon(getClass().getResource("/res/terminal_background.jpg"));
private Font falloutFont;


public static void main(String[] args)
{
	new ServerMain();			
}

public ServerMain()
{
	// LOGGER
	try {
		fileHandler = new FileHandler("server.log", false);
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
	
	// CUSTOM FALLOUT FONT
	Boolean fontIsLoaded = false;
	try {
		 falloutFont = Font.createFont(Font.TRUETYPE_FONT, new File("FalloutFont.ttf")).deriveFont(16f); 
		 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("FalloutFont.ttf")));
	     //falloutFont = Font.createFont(Font.TRUETYPE_FONT, FALLOUT_FONT);
	     //falloutFont = new Font("d", Font.PLAIN, 18);
	     fontIsLoaded = true;
	} catch (Exception e) {
	     e.printStackTrace();
	     fontIsLoaded = false;
	     System.exit(-1);
	}
	

	usersString = "Users:";
	
	bazaMySQL = new MySQLBase(MYSQL_JDBC_DRIVER, MYSQL_DB_URL, MYSQL_USER, MYSQL_PASS);
	
	
	ramka = new JFrame("RobCo Industries UOS");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(850, 600);
	ramka.setLocationRelativeTo(null);
	ramka.setLayout(new BorderLayout());
	ramka.addWindowListener(this);

	info = new JTextArea(h);
	
	info.setBackground(new Color(1,25,1));
	info.setForeground(new Color(150,240,150));
	
	if (fontIsLoaded) info.setFont(falloutFont);
	info.setEditable(false);
	info.setLineWrap(true);
	info.setWrapStyleWord(true);
	
	scrollCenter = new JScrollPane(info);
	panelLewy = new JPanel(new BorderLayout());
	panelLewy.add(scrollCenter, BorderLayout.CENTER);
	panelPrawy = new JPanel(new BorderLayout());
	
	users = new JTextArea(usersString);
	users.setEditable(false);
	scrollEast = new JScrollPane(users);
	panelPrawy.add(scrollEast, BorderLayout.CENTER);
	
	split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLewy, panelPrawy);
	split1.setDividerLocation(710);
	split1.setDividerSize(4);
	split1.setBorder(new EmptyBorder(5,5,5,5));
	
	panelPolnocny = new JPanel(new FlowLayout());
	bPolaczZBaza = new JButton("Connect to RobCo MySQL Database");
	bWyswietlRekordy = new JButton("Show RobCo MySQL Database");
	
	panelPolnocny.add(bPolaczZBaza);
	panelPolnocny.add(bWyswietlRekordy);
	
	bPolaczZBaza.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			bazaMySQL.setVisible(true);
			bazaMySQL.connectToBase();
		}
	});
	
	panelPoludniowy = new JPanel(new FlowLayout());
	bCheckIP = new JButton("Check my IP");
	
	bCheckIP.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			// POBIERANIE ZEWNÊTRZNEGO ADRESU IP SERWERA
			Boolean checkIP = true;
			
			try {
				whatismyip = new URL("http://checkip.amazonaws.com");
				readerIP = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				externalIP = readerIP.readLine();
			}
			catch (Exception uhe)
			{
				checkIP = false;
				bCheckIP.setText("Connection Error!");
			}
			finally
			{
				if (readerIP != null) 
					{
						try {
						readerIP.close();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
							System.exit(-1);
						}
					}
			}
			if (checkIP) bCheckIP.setText("My IP: " +externalIP);
		}
	});
	
	panelPoludniowy.add(bCheckIP);
	
	ramka.add(split1, BorderLayout.CENTER);
	ramka.add(panelPolnocny, BorderLayout.NORTH);
	ramka.add(panelPoludniowy, BorderLayout.SOUTH);
	
	ramka.setVisible(true);
	
	// START SERWERA
	message("Server", "Server is starting...");
	message("Server", "Opening socket: " +port);
			
	bazaUzytkownikow = new HashMap<Integer, Uzytkownik>();
	whoIsOnline = new HashMap<Integer, Boolean>();
	sockets = new HashMap<Integer, Socket>();
	outStreams = new HashMap<Integer, ObjectOutputStream>();
	inStreams = new HashMap<Integer, ObjectInputStream>();
			
	try {
		
		serverSocket = new ServerSocket(port);
		message("Server", "Local server address: " +InetAddress.getLocalHost().toString());
		message("Server", "Awaiting for users...");
		while (true)
		{
			tempSocket = serverSocket.accept();
			tempStreamIn = new ObjectInputStream(new BufferedInputStream(tempSocket.getInputStream()));
			tempStreamOut = new ObjectOutputStream(new BufferedOutputStream(tempSocket.getOutputStream()));
			
			dane = (Dane) tempStreamIn.readObject();
			
			if (dane.getTypDanych() == TypDanych.REGISTER)
				{
					bazaUzytkownikow.put(count, new Uzytkownik(count, dane.getNazwa(), dane.getImie(), dane.getNazwisko()
							, dane.getEmail(), dane.getHaslo(), dane.getZnajomi()));
					
					whoIsOnline.put(count, true);
					message("Server", "Registering user: " +dane.getKto() +", " +dane.getNazwa() +" " +dane.getImie()
					 +" " +dane.getNazwisko() +" " +dane.getEmail() +" " +new String(dane.getHaslo()));
					
					
					// DODAWANIE DO BAZY TYLKO ZAREJESTROWANYCH U¯YTKOWNIKÓW
					sockets.put(count, tempSocket);
					outStreams.put(count, tempStreamOut);
					inStreams.put(count, tempStreamIn);
					
					dane.setWiadomosc("Register successful!");
					dane.setKto(count);
					outStreams.get(count).writeObject(dane);
					outStreams.get(count).flush();
					logOK = true;
					
					usersString = "Users:";
					for (int i = 1; i < bazaUzytkownikow.size()+1; i++)
					{
						usersString = usersString + "\n" +bazaUzytkownikow.get(i).getNumer()+"."+bazaUzytkownikow.get(i).getNazwa();
					}
					users.setText(usersString);
					count++;
					
					// ITERATING ...
					//Iterator<Integer> keys = bazaUzytkownikow.keySet().iterator();
					//while (keys.hasNext())
					//{
					//	Integer x = keys.next();
					//	System.out.println("Keys: " +x +" wartoœæ: " +bazaUzytkownikow.get(x).getNumer());
					//}
					
				}
			if (dane.getTypDanych() == TypDanych.LOG)
			{
				if ((bazaUzytkownikow.containsKey(dane.getKto())) && (new String(dane.getHaslo()).equals(new String(bazaUzytkownikow.get(dane.getKto()).getHaslo()))))
				{
					message(bazaUzytkownikow.get(dane.getKto()).getNazwa(), " has logged in.");
					dane.setWiadomosc("Login successfull!");
					dane.setNazwa(bazaUzytkownikow.get(dane.getKto()).getNazwa());
					outStreams.put(dane.getKto(), tempStreamOut);
					inStreams.put(dane.getKto(), tempStreamIn);
					
					outStreams.get(dane.getKto()).writeObject(dane);
					outStreams.get(dane.getKto()).flush();
					logOK = true;
				}
				else {
					message(dane.getKto() +"", "Wrong number/password!");
					dane.setWiadomosc("Wrong password!");
					dane.setTypDanych(TypDanych.WRONG);
					tempStreamOut.writeObject(dane);
					tempStreamOut.flush();
					logOK = false;
				}
			}
			if (logOK)
			{				
				serverThread = new ServerThread(sockets, inStreams, outStreams, dane.getKto());
				t = new Thread(serverThread);
				t.start();
				logOK = false;
			}
		}
		}
		catch (Exception e)
		{
			zrzutLoga(e);
		}
}


public class ServerThread implements Runnable
{

private HashMap<Integer, Socket> threadSockets;
private HashMap<Integer, ObjectOutputStream> streamsOut;
private HashMap<Integer, ObjectInputStream> streamsIn;
private int numer = 0;


public ServerThread(HashMap<Integer, Socket> s, HashMap<Integer, ObjectInputStream> inMap, HashMap<Integer, ObjectOutputStream> outMap, int c)
{
	threadSockets = s;
	streamsOut = outMap;
	streamsIn = inMap;
	numer = c;
}
	
@Override
public void run()
{
	try {
		
		while (true)
		{
			
			dane = (Dane) streamsIn.get(numer).readObject();
			
			switch (dane.getTypDanych())
			{
			case MESSAGE: {
				
				message(dane.getKto() + " " +dane.getNazwa()+" is writing", dane.getWiadomosc() + " to: " +dane.getDoKogo());
				
				if (dane.getDoKogo() == 0)
				{
					dane.setWiadomosc("ECHO SERVER: who " +dane.getKto() +" to " +dane.getDoKogo() +" " +dane.getWiadomosc());
					streamsOut.get(dane.getKto()).writeObject(dane);
					streamsOut.get(dane.getKto()).flush();
				}
				else {
					if (dane.getDoKogo() <= bazaUzytkownikow.size())
					{
						streamsOut.get(dane.getDoKogo()).writeObject(dane);
						streamsOut.get(dane.getDoKogo()).flush();
					}
					else {
						dane.setWiadomosc("There is no user with the number "+dane.getDoKogo());
						streamsOut.get(dane.getKto()).writeObject(dane);
						streamsOut.get(dane.getKto()).flush();
					}
				}
				break;
			}
			case REGISTER: {
				break;
			}
			case LOG: {				
				break;
			}
			case WRONG: {				
				break;
			}
			}
		}
	}
	catch (SocketException e)
	{
		//zrzutLoga(e);
		message(bazaUzytkownikow.get(numer).getNazwa(), "has disconnected. " +e.getMessage());
		//e.printStackTrace();
	}
	catch (Exception e)
	{
		zrzutLoga(e);
	}
	}


public HashMap<Integer, Socket> getSocketsThread() {
	return threadSockets;
}
}

public void zrzutLoga(Exception e)
{
	LOGGER.log(Level.WARNING, e.getMessage(), e);
	//System.exit(-1);
}

public void message(String name, String msg)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("HH:mm:ss");
	info.append(" "+sdf.format(currentDate) +" "+name +": " +msg +"\n");
}


@Override
public void windowActivated(WindowEvent arg0) {}

@Override
public void windowClosed(WindowEvent arg0) {}

@Override
public void windowClosing(WindowEvent arg0) {
	// ZAMYKANIE GNIAZDEK I ZAPIS DO PLIKU
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