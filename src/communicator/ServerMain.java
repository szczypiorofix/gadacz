package communicator;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
private Socket s;
private ArrayList<Socket> sockets;
private ArrayList <ObjectOutputStream> outStreams;
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
private ObjectInputStream ois;
private BufferedOutputStream bos;
private FileInputStream fis;
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
	
	bazaUzytkownikow = new ArrayList<Uzytkownik>(20);
	
	/**
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
	**/
	usersString = "Users:";
	
	ramka = new JFrame("Serwer komunikatora");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(600, 400);
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
	
	split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCentralny, panelWschodni);
	split1.setDividerLocation(450);
	split1.setDividerSize(4);
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
		
		sockets = new ArrayList<Socket>();
		outStreams = new ArrayList<ObjectOutputStream>();
		
		while (true)
		{
			s = serverSocket.accept();
			sockets.add(s);
			outStreams.add(new ObjectOutputStream(s.getOutputStream()));
			
			message("Serwer", "Po³¹czenie z klientem: " +count +" " +sockets.get(count).getInetAddress());
			serverThread = new ServerThread(sockets, outStreams, count);
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

private ArrayList<Socket> socketsThread;
private ArrayList<ObjectOutputStream> streamsOut;
private Socket socketThread;
private InputStream streamIn;
private ObjectInputStream objectStreamIn;
private int numer = 0;
private int liczbaUzytkownikow = 0;

public ServerThread(ArrayList<Socket> s, ArrayList<ObjectOutputStream> outMap, int c)
{
	socketsThread = s;
	streamsOut = outMap;
	numer = c;
	socketThread = s.get(c);
}
	
@Override
public void run()
{
	try {
		
		streamIn = socketThread.getInputStream();
		objectStreamIn = new ObjectInputStream(streamIn);
				
		while (true)
		{
			
			dane = (Dane) objectStreamIn.readObject();
			
			switch (dane.getTypDanych())
			{
			case MESSAGE: {
				
				message(dane.getNazwa()+" pisze: ", dane.getWiadomosc() + " do: " +dane.getDoKogo());
				
				if ((dane.getDoKogo() < streamsOut.size()) && (dane.getDoKogo() >= 0))  // Gdy "DoKogo" znajduje siê w obrêbie bazy u¿ytkowników
				{
					streamsOut.get(dane.getDoKogo()).writeObject(dane);
					streamsOut.get(dane.getDoKogo()).flush();
				}
				else { // GDY WIADOMOŒÆ JEST WYSY£ANA DO NIEPOPRAWNEGO NUMERU
					dane.setWiadomosc("BRAK U¯YTKOWNIKA O TAKIM NUMERZE !!!");
					streamsOut.get(numer).writeObject(dane);
					streamsOut.get(numer).flush();
				}
				break;
			}
			case REGISTER: {
				message("Zarejestrowano nowego u¿ytkownika: ",dane.getKto() +" " +dane.getNazwa() +" " +dane.getImie() +" " +dane.getNazwisko() +" " +dane.getEmail() +" " +(new String(dane.getHaslo())) +" '" +dane.getWiadomosc() +"'");
				
				bazaUzytkownikow.add(new Uzytkownik(bazaUzytkownikow.size(), dane.getNazwa(), dane.getImie(), dane.getNazwisko(), dane.getEmail(), dane.getHaslo(), dane.getZnajomi()));
				liczbaUzytkownikow=bazaUzytkownikow.size()-1;
				
				usersString = "Users:";
				for (int i = 0; i < bazaUzytkownikow.size(); i++)
					{
						usersString = usersString + "\n" +bazaUzytkownikow.get(i).getNumer()+"."+bazaUzytkownikow.get(i).getNazwa();
					}
				users.setText(usersString);
				dane.setKto(bazaUzytkownikow.get(liczbaUzytkownikow).getNumer()); // NADAWANIE NASTÊPNEGO NUMERU
				dane.setDoKogo(bazaUzytkownikow.get(liczbaUzytkownikow).getNumer()); // WYSY£ANIE WIADOMOŒCI ZWROTNEJ DO TEGO SAMEGO U¯YTKOWNIKA
				dane.setWiadomosc("Serwer: Zarejestrowano pomyœlnie!");

				streamsOut.get(dane.getDoKogo()).writeObject(dane);
				streamsOut.get(dane.getDoKogo()).flush();
				break;
			}
			case LOG: {
				
					if ((dane.getEmail().equals(bazaUzytkownikow.get(dane.getKto()).getEmail())) && (new String(dane.getHaslo()).equals(new String(bazaUzytkownikow.get(dane.getKto()).getHaslo()))))
					{
						message(dane.getNazwa(), " Udane zalogowanie! " +dane.getWiadomosc());
						dane.setWiadomosc("Zalogowano pomyœlnie!");
						streamsOut.get(numer).writeObject(dane);
						streamsOut.get(numer).flush();
					}
					else {
						message(dane.getNazwa(), " Logowanie nieudane!");
						dane.setWiadomosc("B³êdny adres email lub has³o!");
						streamsOut.get(numer).writeObject(dane);
						streamsOut.get(numer).flush();
						
						//???
						streamsOut.remove(numer);
						socketsThread.remove(numer);
						
						
						//users.setText(usersString);
						//dane.setDoKogo(bazaUzytkownikow.get(bazaUzytkownikow.size()-1).getNumer());
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


public ArrayList<Socket> getSocketsThread() {
	return socketsThread;
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