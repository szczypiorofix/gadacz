package communicator;


import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;


public class ClientMain implements KeyListener, WindowListener
{

private final static Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
private FileHandler fileHandler = null;
private int port = 1201;
private String host = "127.0.0.1";
private Socket socket = null;
private ObjectInputStream ois = null;
private ObjectOutputStream oos = null;
private JFrame ramka;
private JTextArea info;
private JTextField wpis, poleAdresu, polePortu, poleNazwy, poleImienia, poleNazwiska, poleEmail, poleNumeru, numerZnajomego, nazwaZnajomego;
private JPasswordField poleHasla;
private JPanel panelWpisu, panelCentralny, panelPoludniowy, panelWschodni, panelPolDial, panelTechDial, panelPrzycDial, panelDanychZnajomego;
private JScrollPane scroll;
private JButton bPolacz, bWyslij, bOK, bAnuluj, bDodajZnajomego, bDodaj;
private JDialog dialogPolacz, dialogDodajZnajomego;
private JCheckBox logRej;
private String historia = "";
private JList<String> poleListyUserow;
private DefaultListModel<String> modelList;
private Runnable r;
private Thread t;
private String userName = "", userFirstName, userLastName, userEmail;
private int userNumber = 0;
private char[] userPassword = null;
private boolean connected = false, registered = false, doOnce = false;
private HashMap<Integer, Znajomy> znajomi;
private HashMap<Integer, Czat> dialogCzat;
private Znajomy znajomy;
private Dane data;
private Date currentDate;
private SimpleDateFormat sdf;
private AudioInputStream audioInputStream;
private Clip clip;
private final URL audioFile = getClass().getResource("/res/bing.wav");
private Properties prop;
private FileOutputStream propFileOut;
private FileInputStream propFileIn;



public static void main(String[] args)
{

EventQueue.invokeLater(new Runnable()
{
	public void run()
	{
		new ClientMain();
	}
});
}

public ClientMain()
{

	// PRZYGOTOWANIE LOGGERA
	try {
		fileHandler = new FileHandler("client.log", false);
	} catch (SecurityException e1) {
		e1.printStackTrace();
		System.exit(-1);
	} catch (IOException e1) {
		e1.printStackTrace();
		System.exit(-1);
	}
	
	fileHandler.setFormatter(new SimpleFormatter());
	fileHandler.setLevel(Level.ALL);
	LOGGER.addHandler(fileHandler);
	
	znajomi = new HashMap<Integer, Znajomy>();
	
	ramka = new JFrame("Aplikacja klienta");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(520, 300);
	ramka.setLocationRelativeTo(null);
	ramka.setLayout(new BorderLayout());
	
	bOK = new JButton("OK");
	bAnuluj = new JButton("Anuluj");
	bDodajZnajomego = new JButton("Dodaj znajomego");
	//bDodajZnajomego.setEnabled(false);
	bDodaj = new JButton("Dodaj");
	
	panelCentralny = new JPanel(new BorderLayout());
	panelPoludniowy = new JPanel(new FlowLayout());
	panelWschodni = new JPanel(new BorderLayout());
	
	panelWschodni.add(new JLabel("U¿ytkownicy:     "), BorderLayout.NORTH);
	modelList = new DefaultListModel<>();
	poleListyUserow = new JList<String>(modelList);
	
	poleListyUserow.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent m)
			{
				JList<String> list = (JList)m.getSource();
				if (m.getClickCount() == 2)
				{
					int indeks = list.locationToIndex(m.getPoint());
					System.out.println("Indeks: "+indeks);
					dialogCzat.get(indeks).setVisible(true);
					dialogCzat.get(indeks).ustawTytul(znajomi.get(indeks).getNazwa() +" ("+znajomi.get(indeks).getNumer() +")");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

	
	panelWschodni.add(poleListyUserow, BorderLayout.CENTER);
	panelWschodni.add(bDodajZnajomego, BorderLayout.SOUTH);
	
	info = new JTextArea(1,1);
	info.setEditable(false);
	info.setLineWrap(true);
	info.setWrapStyleWord(true);
	info.setText(historia);
	
	scroll = new JScrollPane(info);
	panelCentralny.add(scroll, BorderLayout.CENTER);
	panelWpisu = new JPanel(new FlowLayout());

	bPolacz = new JButton("Po³¹cz z serwerem");
	bPolacz.setEnabled(!connected);
	
	panelPoludniowy.add(bPolacz);
	
	bWyslij = new JButton("Wyœlij");
	bWyslij.setEnabled(connected);
	wpis = new JTextField("", 22);
	wpis.setEnabled(connected);
	wpis.addKeyListener(this);
	
	panelWpisu.add(wpis);
	panelWpisu.add(bWyslij);
	
	panelCentralny.add(panelWpisu, BorderLayout.SOUTH);
	
	ramka.add(panelCentralny, BorderLayout.CENTER);
	ramka.add(panelPoludniowy, BorderLayout.SOUTH);
	ramka.add(panelWschodni, BorderLayout.EAST);
	
	ramka.setResizable(true);
	ramka.addWindowListener(this);
	ramka.setVisible(true);

	dialogCzat = new HashMap<Integer, Czat>();
	
	logRej = new JCheckBox("Rejestracja?", registered);
	logRej.addItemListener(new ItemListener()
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			registered = !registered;
			poleImienia.setEnabled(registered);
			poleNazwiska.setEnabled(registered);
			poleEmail.setEnabled(registered);
			poleNazwy.setEnabled(registered);
			poleNumeru.setEnabled(!registered);
		}
	});
	
	bDodajZnajomego.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dialogDodajZnajomego = new JDialog(ramka, "Dodaj znajomego", true);
			dialogDodajZnajomego.setResizable(false);
			dialogDodajZnajomego.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			dialogDodajZnajomego.setSize(220,110);
			dialogDodajZnajomego.setLayout(new BorderLayout());
			dialogDodajZnajomego.setLocationRelativeTo(ramka);
			panelDanychZnajomego = new JPanel(new GridLayout(2,2,5,5));
			panelDanychZnajomego.add(new JLabel("Nazwa: "));
			nazwaZnajomego = new JTextField("");
			panelDanychZnajomego.add(nazwaZnajomego);
			panelDanychZnajomego.add(new JLabel("Numer: "));
			numerZnajomego = new JTextField("");
			panelDanychZnajomego.add(numerZnajomego);
			dialogDodajZnajomego.add(bDodaj, BorderLayout.SOUTH);
			dialogDodajZnajomego.add(panelDanychZnajomego, BorderLayout.CENTER);
			dialogDodajZnajomego.setVisible(true);
		}
	});
	
	bDodaj.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if ((nazwaZnajomego.getText().length() > 0) && (numerZnajomego.getText().length() > 0)) {
						znajomy = new Znajomy(Integer.valueOf(numerZnajomego.getText().toString()), nazwaZnajomego.getText().toString(), false);
						
						dialogCzat.put(znajomi.size(), new Czat(Integer.valueOf(numerZnajomego.getText().toString())));
						znajomi.put(znajomi.size(), znajomy);
						
						modelList.addElement(nazwaZnajomego.getText()+"("+znajomy.getNumer()+")");
					}
					
					dialogDodajZnajomego.setVisible(false);
					poleListyUserow.revalidate();
					poleListyUserow.repaint();
				}
			}
			);
	
	bPolacz.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			dialogPolacz = new JDialog(ramka, "Logowanie/Rejestracja", true);
			dialogPolacz.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			dialogPolacz.setResizable(false);
			dialogPolacz.setSize(350, 400);
			dialogPolacz.setLayout(new BorderLayout());
						
			panelPolDial = new JPanel(new GridLayout(8, 2, 5, 5));
			panelPolDial.setBorder(new EmptyBorder(10,10,10,10));
			
			poleNazwy = new JTextField("Kowal77");
			poleEmail = new JTextField("kowal77@o2.pl");
			poleNumeru = new JTextField("0");
			poleHasla = new JPasswordField("has³o");
			poleImienia = new JTextField("Jan");
			poleNazwiska = new JTextField("Kowalski");
			poleAdresu = new JTextField(host);
			polePortu = new JTextField(port +"");
			poleImienia.setEnabled(registered);
			poleNazwiska.setEnabled(registered);
			poleEmail.setEnabled(registered);
			poleNazwy.setEnabled(registered);
			
			panelPolDial.add(new JLabel("Nazwa: "));
			panelPolDial.add(poleNazwy);
			
			panelPolDial.add(new JLabel("Imiê: "));
			panelPolDial.add(poleImienia);
			
			panelPolDial.add(new JLabel("Nazwisko: "));
			panelPolDial.add(poleNazwiska);
			
			panelPolDial.add(new JLabel("Email: "));
			panelPolDial.add(poleEmail);
			
			panelPolDial.add(new JLabel("Numer: "));
			panelPolDial.add(poleNumeru);
			
			panelPolDial.add(new JLabel("Has³o: "));
			panelPolDial.add(poleHasla);
			panelPolDial.add(logRej);
			
			panelTechDial = new JPanel(new GridLayout(2, 2, 5, 5));
			panelTechDial.setBorder(new EmptyBorder(10,10,10,10));
			
			panelTechDial.add(new JLabel("Adres serwera: "));
			panelTechDial.add(poleAdresu);
			panelTechDial.add(new JLabel("Adres portu: "));
			panelTechDial.add(polePortu);			
			
			panelPrzycDial = new JPanel(new FlowLayout());
			panelPrzycDial.setBorder(new EmptyBorder(10,10,10,10));
			
			panelPrzycDial.add(bOK);
			panelPrzycDial.add(bAnuluj);
			
			dialogPolacz.add(panelTechDial, BorderLayout.NORTH);
			dialogPolacz.add(panelPolDial, BorderLayout.CENTER);
			dialogPolacz.add(panelPrzycDial, BorderLayout.SOUTH);
			
			dialogPolacz.setLocationRelativeTo(ramka);
			dialogPolacz.setVisible(true);
		}
	});
	
	bOK.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dialogPolacz.setVisible(false);
			
			if (registered) userName = poleNazwy.getText().toString(); else userName = "";
			if (registered) userFirstName = poleImienia.getText().toString(); else userFirstName = "";
			if (registered) userLastName = poleNazwiska.getText().toString(); else userLastName = "";
			if (registered) userEmail = poleEmail.getText().toString(); else userEmail = "";
			userPassword = poleHasla.getPassword();
			userNumber = Integer.valueOf(poleNumeru.getText());
			
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(host, port), 3000); // 3 sek. timeout
				// TODO Obs³uga wyj¹tko - co jeœli nie mo¿na po³¹czyæ siê z serwerem ?
			}
			catch (IOException ioe)
			{
				zrzutLoga(ioe);
			}
			ramka.setTitle("Aplikacja klienta: " +userName);
			message(info, "Po³¹czono z serwerem!" +host +" : " +port);
			
			if (registered) {
				wyslij(TypDanych.REGISTER, "Chcê siê zajestrowaæ!", 0);
				registered = false;
			}
			else wyslij(TypDanych.LOG, "Chcê siê zalogowaæ!", userNumber);
			
			connected = true;
			wpis.setEnabled(connected);
			bWyslij.setEnabled(connected);
			bPolacz.setEnabled(!connected);

			if (!doOnce)
			{
				doOnce = true;
				r = new WatekKlienta();
				t = new Thread(r);
				t.start();
			}
		}
	});
	
	bAnuluj.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dialogPolacz.setVisible(false);	
		}
	});
	
	bWyslij.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{					
			wyslij(TypDanych.MESSAGE, wpis.getText().toString(), 0);				
		}
	});	
}

public void wyslij(TypDanych td, String s, int doKogo)
{
	try {
		
		if (oos == null)
			oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		
		data = new Dane(userNumber, td, doKogo, userName, userFirstName, userLastName, userEmail, userPassword, znajomi, s); // 0 - ka¿dy pocz¹tkowo ma numer 0, dopiero serwer zwraca w³aœciwy numer
		oos.writeObject(data);
		oos.flush();
		message(info, s);
	}
	catch (Exception e)
	{
		zrzutLoga(e);
	}
	wpis.setText("");
	//wpis.requestFocus();
}

public class WatekKlienta implements Runnable
{
	
@Override
public void run()
{
	try {		
		if (ois == null)
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			 
		while (true)
		{
			data = (Dane) ois.readObject();

			message(info, data.getNazwa() +" do " +data.getDoKogo() +" : " +data.getWiadomosc());
			
			bing();
			
			if (data.getTypDanych() == TypDanych.LOG) {
				message(info, "Udane logowanie!");
				userNumber = data.getKto();
				userName = data.getNazwa();
				ramka.setTitle("Aplikacja klienta: " +userName);
				friendsLoad();				
			}
			
			if (data.getTypDanych() == TypDanych.REGISTER) {
				message(info, "Nadano nowy numer: "+data.getKto() +" !");
				userNumber = data.getKto();
			}
			if (data.getTypDanych() == TypDanych.WRONG) {
				message(info, "Odrzucono po³¹czenie !");
				data.setTypDanych(TypDanych.MESSAGE);
					
				// TODO Reconnect w przypadku wprowadzenia z³ego numeru / has³a u¿ytkownika
				//socket.close();	
			}
		}		
	}
	catch (Exception e)
	{
		zrzutLoga(e);
	}		
}

public void friendsLoad()
{
	try {
	prop = new Properties();
	propFileIn = new FileInputStream("friends"+userNumber+".txt");
	prop.load(propFileIn);
	
    Enumeration<?> e = prop.propertyNames();

    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      znajomy = new Znajomy(Integer.valueOf(key), prop.getProperty(key), false);
      znajomi.put(znajomi.size(), znajomy);
      modelList.addElement(prop.getProperty(key)+"("+znajomy.getNumer()+")");
      dialogCzat.put(znajomi.size(), new Czat(Integer.valueOf(key)));
    }
	
	poleListyUserow.revalidate();
	poleListyUserow.repaint();
    
	}
	catch (IOException ioe)
	{
		ioe.printStackTrace();
		System.exit(-1);
	}
	finally
	{
		try {
		propFileIn.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

}

public class Czat extends JDialog
{

private static final long serialVersionUID = 3L;
private int doUzytkownika;
private JTextArea historiaCzat;
private JTextField wpisCzat;

public void ustawTytul(String s)
{
	setTitle(s);
}

public Czat(int doKogo)
{
	super();
	setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	setSize(500,350);
	//setResizable(false);
	setLocationRelativeTo(ramka);
	setLayout(new BorderLayout());
	
	historiaCzat = new JTextArea();
	historiaCzat.setEditable(false);
	historiaCzat.setLineWrap(true);
	historiaCzat.setWrapStyleWord(true);
	wpisCzat = new JTextField(35);
	JButton bW = new JButton("Wyœlij");
	JPanel panelHistorii = new JPanel(new BorderLayout());
	JScrollPane sp = new JScrollPane(historiaCzat);
	panelHistorii.setBorder(new EmptyBorder(5,5,5,5));
	JPanel panelWpis = new JPanel(new FlowLayout());
	
	panelHistorii.add(sp, BorderLayout.CENTER);
	panelWpis.add(wpisCzat);
	panelWpis.add(bW);
	
	add(panelHistorii, BorderLayout.CENTER);
	add(panelWpis, BorderLayout.SOUTH);
	wpisCzat.requestFocus();
	//setVisible(true);
	
	bW.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			wyslij(TypDanych.MESSAGE, wpisCzat.getText().toString() , getDoUzytkownika());
			message(historiaCzat, "Do: " +getDoUzytkownika()+" "+wpisCzat.getText().toString()+"\n");
			//historiaCzat.append("Do: " +getDoUzytkownika()+" "+wpisCzat.getText().toString()+"\n");
			wpisCzat.setText("");
			wpisCzat.requestFocus();
		}
	});
}

public int getDoUzytkownika()
{
	return doUzytkownika;
}

}

@Override
public void keyPressed(KeyEvent arg0) {
	if ((arg0.getKeyCode() == KeyEvent.VK_ENTER) && (bWyslij.isEnabled()))
	{
		wyslij(TypDanych.MESSAGE, wpis.getText().toString(), 0);
	}
}

@Override
public void keyReleased(KeyEvent arg0) {}

@Override
public void keyTyped(KeyEvent arg0) {}

public void zrzutLoga(Exception e)
{
	LOGGER.log(Level.INFO, "User number:" +userNumber);
	LOGGER.log(Level.WARNING, e.getMessage(), e);
	//System.exit(-1);
}

public void message(JTextArea a, String s)
{
	currentDate = new Date();
	sdf = new SimpleDateFormat("HH:mm:ss");
	a.append(sdf.format(currentDate) +" (" +userNumber +") " +": " +s +"\n");
}

public void bing()
{
	 try {
		 	audioInputStream = AudioSystem.getAudioInputStream((audioFile));
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        JOptionPane.showMessageDialog(ramka, "B³¹d odtwarzania pliku dŸwiêkowego!");
	        ex.printStackTrace();
	        System.exit(-1);
	    }
}

/// WINDOWLISTENER INFEFACE METHODS
@Override
public void windowActivated(WindowEvent arg0) {}

@Override
public void windowClosed(WindowEvent arg0) {}

@Override
public void windowClosing(WindowEvent arg0) {
	
	try {
	prop = new Properties();
	propFileOut = new FileOutputStream("friends"+userNumber+".txt");
		
	if (znajomi.size() > 0)
	{
		for (int i = 0; i < znajomi.size(); i++) prop.setProperty(znajomi.get(i).getNumer()+"", znajomi.get(i).getNazwa());
	}
	
	prop.store(propFileOut, "Lista znajomych klienta o numerze: "+userNumber);
	
	}
	catch (IOException e)
	{
		e.printStackTrace();
		System.exit(-1);
	}
	finally
	{
		if (propFileOut != null)
		{
			try {
				propFileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
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