package communicator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;


/**Główny program serwera komunikatora internetowego.
 * @author Piotrek
 * @see ClientMain
 * @see Dane
 * @see TypDanych
 * @see Uzytkownik
 * @see Znajomy
 *
 */
public class ServerMain implements WindowListener
{


	private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MYSQL_DB_URL = "jdbc:mysql://localhost/uzytkownicy";
	private static final String MYSQL_USER = "root";
	private static final String MYSQL_PASS = "";
	private final static Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
	private FileHandler fileHandler = null;
	/**
	 * Port połączenia pomiędzy serwerem a klientami.
	 * Wartość stała: 1201.
	 */
	private final int SERVER_PORT = 1201;
	/**
	 *
	 */
	private ServerSocket serverSocket;
	/**
	 * Tymczasowe gniazdko serwera służące do wpisywania gniazdka jako kolejnego elementu HashMapy
	 */
	private Socket tempSocket;
	/**
	 * Tymczasowy strumień wejściowy służący do wpisywania go jako kolejnego elementu HashMapy outStreams.
	 */
	private ObjectInputStream tempStreamIn;
	/**
	 * Tymczasowy strumień wyjściowy służący do wpisywania go jako kolejnego elementu HashMapy inStreams.
	 */
	private ObjectOutputStream tempStreamOut;
	/**
	 * HashMapa gniazdek kolejnych klientów.
	 */
	private HashMap<Integer, Socket> sockets;
	/**
	 * HashMapa strumieni wyjściowych kolejnych klientów.
	 */
	private HashMap <Integer, ObjectOutputStream> outStreams;
	/**
	 * HashMapa strumieni wejściowych kolejnych klientów.
	 */
	private HashMap <Integer, ObjectInputStream> inStreams;
	/**
	 * Główne okno aplikacji serwera.
	 */
	private JFrame ramka;
	private JTextArea users, info;
	private JPanel panelLewy, panelPrawy, panelPolnocny, panelPoludniowy;
	private JButton bCheckIP;
	private JScrollPane scrollCenter, scrollEast;
	private String h = "";
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
	private JSplitPane split1;
	private String usersString;
	private boolean logOK = false;
	private boolean doOnce = false;
	private ServerSysTray serverSysTray;


	public static void main(String[] args)
	{
		new ServerMain();
	}

	/**
	 * Konstruktor głównego programu serwera.
	 */
	public ServerMain()
	{
		// LOGGER
		try {
			fileHandler = new FileHandler("server.log", false);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}

		fileHandler.setFormatter(new SimpleFormatter());
		fileHandler.setLevel(Level.WARNING);
		LOGGER.addHandler(fileHandler);

		usersString = "Użytkownicy:";

		ramka = new JFrame("Gadacz - serwer");
		ramka.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		ramka.setSize(580, 450);
		ramka.setLocationRelativeTo(null);
		ramka.setLayout(new BorderLayout());
		ramka.addWindowListener(this);
		ramka.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/server_program_icon.png")));

		// SYSTRAY
		serverSysTray = new ServerSysTray(ramka);
		serverSysTray.initialize();
		serverSysTray.trayMessage("Serwer", "Serwer został uruchomiony!", TrayIcon.MessageType.INFO);


		info = new JTextArea(h);
		info.setEditable(false);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);

		scrollCenter = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		DefaultCaret caret = (DefaultCaret)info.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		panelLewy = new JPanel(new BorderLayout());
		panelLewy.add(scrollCenter, BorderLayout.CENTER);
		panelPrawy = new JPanel(new BorderLayout());

		users = new JTextArea(usersString);
		users.setEditable(false);
		scrollEast = new JScrollPane(users);
		panelPrawy.add(scrollEast, BorderLayout.CENTER);

		split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLewy, panelPrawy);
		split1.setDividerLocation(460);
		split1.setDividerSize(4);
		split1.setBorder(new EmptyBorder(5,5,5,5));

		panelPolnocny = new JPanel(new FlowLayout());

		panelPoludniowy = new JPanel(new FlowLayout());
		bCheckIP = new JButton("Sprawdź mój IP");

		bCheckIP.addActionListener((ActionEvent e) ->
		{
			// POBIERANIE ZEWNĘTRZNEGO ADRESU IP SERWERA
			Boolean checkIP = true;

			try {
				whatismyip = new URL("http://checkip.amazonaws.com");
				readerIP = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				externalIP = readerIP.readLine();
			}
			catch (Exception uhe)
			{
				checkIP = false;
				bCheckIP.setText("Błąd połączenia!");
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
			if (checkIP) bCheckIP.setText("Moje IP: " +externalIP);
		});

		panelPoludniowy.add(bCheckIP);

		ramka.add(split1, BorderLayout.CENTER);
		ramka.add(panelPolnocny, BorderLayout.NORTH);
		ramka.add(panelPoludniowy, BorderLayout.SOUTH);

		ramka.setVisible(true);

		// START SERWERA
		message("Serwer", "Start serwera");
		message("Serwer", "Otwieranie gniazdka: " +SERVER_PORT);

		bazaUzytkownikow = new HashMap<>();
		whoIsOnline = new HashMap<>();
		sockets = new HashMap<>();
		outStreams = new HashMap<>();
		inStreams = new HashMap<>();

		try {

			serverSocket = new ServerSocket(SERVER_PORT);
			message("Serwer", "Adres lokalny serwera " +InetAddress.getLocalHost().toString());
			message("Serwer", "Oczekiwanie na użytkowników ...");
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
						message("Serwer", "Zarejestrowano użytkownika: " +dane.getKto() +", " +dane.getNazwa() +" " +dane.getImie()
						 +" " +dane.getNazwisko() +" " +dane.getEmail() +" " +new String(dane.getHaslo()));
						if (serverSysTray.windowIsHidden()) serverSysTray.trayMessage("Serwer komunikatora", "Zarejestrowano nowego użytkownika!", TrayIcon.MessageType.INFO);

						// DODAWANIE DO BAZY TYLKO ZAREJESTROWANYCH UŻYTKOWNIKÓW
						sockets.put(count, tempSocket);
						outStreams.put(count, tempStreamOut);
						inStreams.put(count, tempStreamIn);

						dane.setWiadomosc("Rejestracja udana!");
						dane.setKto(count);
						outStreams.get(count).writeObject(dane);
						outStreams.get(count).flush();
						logOK = true;

						usersString = "Użytkownicy:";
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
						//	System.out.println("Keys: " +x +" wartość: " +bazaUzytkownikow.get(x).getNumer());
						//}

					}
				if (dane.getTypDanych() == TypDanych.LOG)
				{
					if ((bazaUzytkownikow.containsKey(dane.getKto())) && (new String(dane.getHaslo()).equals(new String(bazaUzytkownikow.get(dane.getKto()).getHaslo()))))
					{
						message(bazaUzytkownikow.get(dane.getKto()).getNazwa(), " zalogował(a) się.");
						dane.setWiadomosc("Logowanie udane!");
						dane.setNazwa(bazaUzytkownikow.get(dane.getKto()).getNazwa());
						outStreams.put(dane.getKto(), tempStreamOut);
						inStreams.put(dane.getKto(), tempStreamIn);

						outStreams.get(dane.getKto()).writeObject(dane);
						outStreams.get(dane.getKto()).flush();
						logOK = true;
					}
					else {
						message(dane.getKto() +"", "Nieprawidłowy numer/hasło!");
						dane.setWiadomosc("Nieprawidłowy numer/hasło!");
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



	/** Klasa wątku klienta po stronie serwera.
	 * @author Piotrek
	 *
	 */
	public class ServerThread implements Runnable
	{

	private HashMap<Integer, Socket> threadSockets;
	private HashMap<Integer, ObjectOutputStream> streamsOut;
	private HashMap<Integer, ObjectInputStream> streamsIn;
	private int numer = 0;


	/** Konstruktor wątku klienta po stronie serwera.
	 * Tworzy wątek kolejnego klienta z przypisanego mu gniazdna oraz zestawu dwóch strumieni: wejściowego i wyjściowego.
	 * @param s HashMapa (Integer, Socket) bazy gniazdek kolejnych klientów.
	 * @param inMap HashMapa strumieni wejściowych klientów.
	 * @param outMap HashMapa strumieni wyjściowych klientów.
	 * @param c Określony numer konkretnego klienta.
	 */
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

					message(dane.getKto() + " " +dane.getNazwa()+" pisze", dane.getWiadomosc() + " do: " +dane.getDoKogo());

					if (dane.getDoKogo() == 0)
					{
						dane.setWiadomosc("ECHO SERWER: kto " +dane.getKto() +" do " +dane.getDoKogo() +" " +dane.getWiadomosc());
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
							dane.setWiadomosc("Brak użytkownika o numerze "+dane.getDoKogo());
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
			message(bazaUzytkownikow.get(numer).getNazwa(), "rozłączył(a) się. " +e.getMessage());
			if (serverSysTray.windowIsHidden()) serverSysTray.trayMessage("Serwer komunikatora", "Użytkownik "+bazaUzytkownikow.get(numer).getNazwa() +" roz��czy� si�.", TrayIcon.MessageType.INFO);
			//e.printStackTrace();
		}
		catch (Exception e)
		{
			zrzutLoga(e);
		}
	}


	/** Metoda zwracająca HashMapę wątków klientów.
	 * @return HashMapa wątków klientów.
	 */
	public HashMap<Integer, Socket> getSocketsThread() {
		return threadSockets;
	}
	}

	/** Metoda zrzucająca treść wyjątku do pliku Loggera.
	 * @param e Wyjątek.
	 */
	private void zrzutLoga(Exception e)
	{
		LOGGER.log(Level.WARNING, e.getMessage(), e);
		//System.exit(-1);
	}

	/** Metoda formatująca i wyświetlająca treść wiadomości od klienta w oknie serwera.
	 * @param name Nazwa użytkownika.
	 * @param msg Treść wiadomości.
	 */
	private void message(String name, String msg)
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
		if (!doOnce) {
		serverSysTray.trayMessage("Serwer działa w tle!", "Żeby zamknąć kliknij prawym klawiszem myszy i wybierz opcję 'Zamknij'", TrayIcon.MessageType.INFO);
		doOnce = true;
		}
		serverSysTray.setWindowIsHidden(true);
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