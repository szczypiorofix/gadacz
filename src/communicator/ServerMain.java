package communicator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


public class ServerMain implements WindowListener
{

private final static Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
private FileHandler fileHandler = null;
private int port = 1201;
private ServerSocket serverSocket;
private ArrayList<Socket> sockets;
private JFrame ramka;
private JTextArea info;
private JPanel panelCentralny, panelWschodni;
private JScrollPane scrollCenter, scrollEast;
private String h = "Serwer 1.0\n";
private int count = 0;
private URL whatismyip;
private BufferedReader buffreader;
private String externalIP;
private Dane dane;
private Thread t;
private ServerThread serverThread;
private ArrayList<Uzytkownik> bazaUzytkownikow;
private Date currentDate;
private SimpleDateFormat sdf;
private FileOutputStream fos;
private ObjectOutputStream oos;
private BufferedOutputStream bos;
private FileInputStream fis;
private ObjectInputStream ois;
private BufferedInputStream bis;
private JTextArea users;
private JSplitPane split1;
private String usersString;
private final File file = new File("dane.txt");




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
	
	bazaUzytkownikow = new ArrayList<Uzytkownik>(20);
	
	if ((file.exists()) && (!file.isDirectory()))
	{
	try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			
			bazaUzytkownikow = (ArrayList<Uzytkownik>) ois.readObject(); 
			
			/**
			Object odczyt = ois.readObject();
			if (odczyt instanceof ArrayList<?>)
			{
				ArrayList<?> a1 = (ArrayList<?>) odczyt;
				if (a1.size() > 0)
				{
					for (int i = 0; i < a1.size(); i++)
					{
						Object o = a1.get(i);
						if (o instanceof Uzytkownik)
						{
							bazaUzytkownikow.add((Uzytkownik) o);
						}
					}
				}
			}
			**/
			
			usersString = "Users:";
			for (int i = 0; i < bazaUzytkownikow.size(); i++)
			{
				usersString = usersString + "\n" +bazaUzytkownikow.get(i).getNumer()+"."+bazaUzytkownikow.get(i).getNazwa();
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try {
			ois.close();
			bis.close();
			fis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	else {
		usersString = "Users:";
	}
	
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

	users = new JTextArea(usersString);
	users.setEditable(false);
	scrollEast = new JScrollPane(users);
	panelWschodni.add(scrollEast, BorderLayout.CENTER);
	
	
	//ramka.add(panelCentralny, BorderLayout.CENTER);
	//ramka.add(panelWschodni, BorderLayout.EAST);
	
	split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCentralny, panelWschodni);
	split1.setDividerLocation(450);
	split1.setDividerSize(3);
	ramka.add(split1, BorderLayout.CENTER);
	
	ramka.setVisible(true);
	
	message("Serwer", "Start serwera.");
	message("Serwer", "Otwieranie gniazdka serwera. Port: " +port);
		
	try {
		
		serverSocket = new ServerSocket(port);
		message("Serwer", "Lokalny adres serwera: " +InetAddress.getLocalHost().toString());
		
		whatismyip = new URL("http://checkip.amazonaws.com");
		buffreader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		externalIP = buffreader.readLine();
		message("Serwer", "Zewnêtrzny adres serwera: " +externalIP);
		
		sockets = new ArrayList<Socket>(20);
		
		while (true)
		{
			sockets.add(serverSocket.accept());
			message("Serwer", "Po³¹czenie z klientem: " +count +" " +sockets.get(count).getInetAddress());
			serverThread = new ServerThread(sockets, count);
			t = new Thread(serverThread);
			t.start();
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

private ArrayList<Socket> sockets;
private Socket socket;
private ObjectInputStream ois;
private ObjectOutputStream oos;
private int numer = 0;
private int liczbaUzytkownikow = 0;

public ServerThread(ArrayList<Socket> s, int c)
{
	sockets = s;
	numer = c;
	this.socket = s.get(numer);
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
				
				if (dane.getDoKogo() == 0)  {
					dane.setWiadomosc("ECHO MSG: " +dane.getWiadomosc());
					message(dane.getNazwa(), dane.getWiadomosc());
				}
				
				if (oos == null) 
					oos = new ObjectOutputStream(this.socket.getOutputStream());
				
				oos.writeObject(dane);
				oos.flush();
				break;
			}
			case REGISTER: {
				message("Zarejestrowano nowego u¿ytkownika: ", dane.getNazwa() +" " +dane.getImie() +" " +dane.getNazwisko() +" " +dane.getEmail() +" " +(new String(dane.getHaslo())) +" '" +dane.getWiadomosc() +"'");
				
				bazaUzytkownikow.add(new Uzytkownik(bazaUzytkownikow.size(), dane.getNazwa(), dane.getImie(), dane.getNazwisko(), dane.getEmail(), dane.getHaslo(), dane.getZnajomi()));
				liczbaUzytkownikow=bazaUzytkownikow.size()-1;
				
				usersString = "Users:";
				for (int i = 0; i < bazaUzytkownikow.size(); i++)
				{
					usersString = usersString + "\n" +bazaUzytkownikow.get(i).getNumer()+"."+bazaUzytkownikow.get(i).getNazwa();
				}
				users.setText(usersString);
				
				dane.setDoKogo(bazaUzytkownikow.get(liczbaUzytkownikow).getNumer());
				dane.setWiadomosc("ECHO REGISTER: " +dane.getWiadomosc());
				
				if (oos == null) 
					oos = new ObjectOutputStream(this.socket.getOutputStream());
				
				oos.writeObject(dane);
				oos.flush();
				break;
			}
			case LOG: {
				for (int i = 0; i < bazaUzytkownikow.size(); i++)
				{
					if ((dane.getEmail().equals(bazaUzytkownikow.get(i).getEmail())) && (dane.getDoKogo() == bazaUzytkownikow.get(i).getNumer()))
					{
						message(dane.getNazwa(), dane.getWiadomosc());
					}
					else {
						users.setText(usersString);
						dane.setDoKogo(bazaUzytkownikow.get(bazaUzytkownikow.size()-1).getNumer());
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
		}
	}
	catch (SocketException e)
	{
		//zrzutLoga(e);
		message(dane.getNazwa(), "roz³¹czy³ siê. " +e.getMessage());
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

public void message(String name, String msg)
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