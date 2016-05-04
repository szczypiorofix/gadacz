package communicator;


import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class ClientMain implements KeyListener, WindowListener
{

private int port = 1201;
private String host = "89.70.80.201";
private Socket socket = new Socket();
private ObjectInputStream ois = null;
private ObjectOutputStream oos = null;
private JFrame ramka;
private JPanel panelWpisu, panelDialLog, panelPrzycDialLog, panelDialRej, panelPrzycDialRej;
private JTextArea info;
private JTextField wpis, poleAdresuLog, polePortuLog, poleNazwyULog, poleNazwyURej, poleEmailURej, poleImieniaURej, poleNazwiskaURej, poleAdresuRej, polePortuRej;
private JPasswordField poleHaslaURej, poleHaslaULog;
private JPanel panelCentralny, panelPoludniowy, panelWschodni;
private JScrollPane scroll;
private JButton bRejestracja, bLogowanie, bWyloguj, bWyslij, bOKLog, bAnulujLog, bOKRej, bAnulujRej;
private JDialog dialogLog, dialogRej;
private String historia = "";
private JList<String> poleListyUserow;
private DefaultListModel<String> modelListy;
private Runnable r;
private Thread t;
private String nazwaUsera = "", nazwaRej, imieRej, nazwiskoRej, emailRej;
private char[] haslo = null, hasloRej = null;
private boolean polaczono = false, numError = false;
private HashMap<Integer, Boolean> znajomi;
private Dane dane;


public static void main(String[] args) {
EventQueue.invokeLater(new Runnable()
	{
		@Override
		public void run()
		{
			new ClientMain();
		}
	});
}

public ClientMain()
{
	znajomi = new HashMap<Integer, Boolean>();
	
	ramka = new JFrame("Aplikacja klienta");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(600, 500);
	ramka.setLocationRelativeTo(null);
	ramka.setLayout(new BorderLayout());
	ramka.addWindowListener(this);
	
	bOKRej = new JButton("OK");
	bAnulujRej = new JButton("Anuluj");
	bOKLog = new JButton("OK");
	bAnulujLog = new JButton("Anuluj");
	
	panelCentralny = new JPanel(new BorderLayout());
	panelPoludniowy = new JPanel(new FlowLayout());
	panelWschodni = new JPanel(new BorderLayout());
	
	panelWschodni.add(new JLabel("U¿ytkownicy:     "), BorderLayout.NORTH);
	modelListy = new DefaultListModel<>();
	poleListyUserow = new JList<String>(modelListy);
	
	/**
	poleListyUserow.addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(MouseEvent m)
				{
					JList<String> list = (JList)m.getSource();
					if (m.getClickCount()==2)
					{
						for (int i = 0; i < liczby.size(); i++) System.out.println("Wartoœci: " +liczby.get(i));
						int indeks = list.locationToIndex(m.getPoint());
						wpis.setText("/"+liczby.get(indeks));
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
	**/
	
	panelWschodni.add(poleListyUserow, BorderLayout.CENTER);
	
	info = new JTextArea(1,1);
	info.setEditable(false);
	info.setText(historia);
	
	scroll = new JScrollPane(info);
	panelCentralny.add(scroll, BorderLayout.CENTER);
	panelWpisu = new JPanel(new FlowLayout());

	bRejestracja = new JButton("Zarejestruj siê");
	bLogowanie = new JButton("Logowanie");
	bWyloguj = new JButton("Wyloguj");
	bWyloguj.setEnabled(polaczono);
	bRejestracja.setEnabled(false);
	
	panelPoludniowy.add(bWyloguj);
	panelPoludniowy.add(bLogowanie);
	panelPoludniowy.add(bRejestracja);	
	
	bWyslij = new JButton("Wyœlij");
	bWyslij.setEnabled(polaczono);
	wpis = new JTextField("", 36);
	wpis.setEnabled(polaczono);
	wpis.addKeyListener(this);
	
	panelWpisu.add(wpis);
	panelWpisu.add(bWyslij);
	
	panelCentralny.add(panelWpisu, BorderLayout.SOUTH);
	
	ramka.add(panelCentralny, BorderLayout.CENTER);
	ramka.add(panelPoludniowy, BorderLayout.SOUTH);
	ramka.add(panelWschodni, BorderLayout.EAST);
	
	ramka.setResizable(true);
	ramka.setVisible(true);

	bRejestracja.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				
				dialogRej = new JDialog(ramka, "Zarejestruj u¿ytkownika", true);
				dialogRej.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				dialogRej.setResizable(false);
				dialogRej.setLayout(new BorderLayout());
				
				panelDialRej = new JPanel(new GridLayout(7,2,10,10));
				panelDialRej.setBorder(new EmptyBorder(5,5,5,5));
				
				panelPrzycDialRej = new JPanel(new FlowLayout());
				panelPrzycDialRej.add(bOKRej);
				panelPrzycDialRej.add(bAnulujRej);
				
				poleNazwyURej = new JTextField();
				poleNazwyURej.setText("Nazwa");
				poleEmailURej = new JTextField();
				poleEmailURej.setText("e@mail");
				poleHaslaURej = new JPasswordField();
				poleHaslaURej.setText("haslo");
				poleImieniaURej = new JTextField();
				poleImieniaURej.setText("Imie");
				poleNazwiskaURej = new JTextField();
				poleNazwiskaURej.setText("Nazwisko");
				poleAdresuRej = new JTextField();
				poleAdresuRej.setText("89.70.80.201");
				polePortuRej = new JTextField();
				polePortuRej.setText("1201");
				
				panelDialRej.add(new JLabel("Nazwa: "));
				panelDialRej.add(poleNazwyURej);
				
				panelDialRej.add(new JLabel("Imiê: "));
				panelDialRej.add(poleImieniaURej);
				
				panelDialRej.add(new JLabel("Nazwisko: "));
				panelDialRej.add(poleNazwiskaURej);
				
				panelDialRej.add(new JLabel("E-mail: "));
				panelDialRej.add(poleEmailURej);
				
				panelDialRej.add(new JLabel("Has³o: "));
				panelDialRej.add(poleHaslaURej);
				
				panelDialRej.add(new JLabel("IP serwera: "));
				panelDialRej.add(poleAdresuRej);
				
				panelDialRej.add(new JLabel("Port serwera: "));
				panelDialRej.add(polePortuRej);
				
				panelPrzycDialLog = new JPanel(new FlowLayout());
				
				
				dialogRej.add(panelDialRej, BorderLayout.CENTER);
				dialogRej.add(panelPrzycDialRej, BorderLayout.SOUTH);
				
				dialogRej.setSize(280, 290);
				dialogRej.setLocationRelativeTo(ramka);
				dialogRej.setVisible(true);
				
				nazwaUsera = "temp";
			}
		});
	
	bLogowanie.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent ea)
		{
			dialogLog = new JDialog(ramka, "Podaj dane do logowania:", true);
			dialogLog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			dialogLog.setResizable(false);
			dialogLog.setLayout(new BorderLayout());
				
			panelDialLog = new JPanel(new GridLayout(4,2,10,10));
			panelDialLog.setBorder(new EmptyBorder(5,5,5,5));
				
			poleNazwyULog = new JTextField("Temp");
			poleHaslaULog = new JPasswordField();
			poleAdresuLog = new JTextField(host);
			polePortuLog = new JTextField(port+"");
				
			panelDialLog.add(new JLabel("Nazwa u¿ytkownika: "));
			panelDialLog.add(poleNazwyULog);
				
			panelDialLog.add(new JLabel("Has³o u¿ytkownika: "));
			panelDialLog.add(poleHaslaULog);
				
			panelDialLog.add(new JLabel("Adres hosta: "));
			panelDialLog.add(poleAdresuLog);
			panelDialLog.add(new JLabel("Port po³¹czenia: "));
			panelDialLog.add(polePortuLog);
				
			panelPrzycDialLog = new JPanel(new FlowLayout());
			panelPrzycDialLog.add(bOKLog);
			panelPrzycDialLog.add(bAnulujLog);
						
			dialogLog.add(panelDialLog, BorderLayout.CENTER);
			dialogLog.add(panelPrzycDialLog, BorderLayout.SOUTH);
				
			dialogLog.setSize(280, 200);
			dialogLog.setLocationRelativeTo(ramka);
			dialogLog.setVisible(true);
		}
	});
	
	bAnulujRej.addActionListener((e) -> dialogRej.setVisible(false));
	
	bOKRej.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			dialogRej.setVisible(false);
			numError = false;
			
			nazwaRej = poleNazwyURej.getText().toString();
			imieRej = poleImieniaURej.getText().toString();
			nazwiskoRej = poleNazwiskaURej.getText().toString();
			emailRej = poleEmailURej.getText().toString();
			hasloRej = poleHaslaURej.getPassword();
			host = poleAdresuRej.getText().toString();
			port = Integer.valueOf(polePortuRej.getText().toString());

			boolean emailOK = false;
			
			if (emailRej.length() > 0)    // SPRAWDZENIE CZY ADRES EMAIL ZAWIERA "@"
				for (int i = 0; i < emailRej.length(); i++)
					{
					if (emailRej.charAt(i) == '@' )	emailOK = true;
					if (emailOK) break;
					}
			
			if (!emailOK) JOptionPane.showMessageDialog(ramka, "WprowadŸ poprawny adres email!");
				
			try {
			port = Integer.valueOf(polePortuRej.getText().toString());
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(ramka, "Tylko cyfry s¹ akceptowane!");
				numError = true;
			}
						
			//
			// WYSY£ANIE REJESTRACJI ...
			//
			
		}
	});
	
	bAnulujLog.addActionListener((e) -> dialogLog.setVisible(false));
	
	bOKLog.addActionListener((e) ->
		{
			if (poleNazwyULog.getText().length()>0)
			{
			numError = false;
			dialogLog.setVisible(false);
			
			host = poleAdresuLog.getText();
			try {
			port = Integer.valueOf(polePortuLog.getText());
			}
			catch (NumberFormatException nbr)
			{
				JOptionPane.showMessageDialog(ramka, "Tylko cyfry s¹ akceptowalne!");
				numError = true;
			}
					
			if (!numError)
			{
				historia=historia+"Nawi¹zywanie po³¹czenia z hostem: " +host +":"+ port+" ...\n";
				info.setText(historia);
				
				haslo = poleHaslaULog.getPassword();
				nazwaUsera = poleNazwyULog.getText().toString();				
				ramka.setTitle("Aolikacja klienta: " +nazwaUsera);
								
				r = new WatekKlienta();
				t = new Thread(r);
				t.start();
		
			}
			}
			else JOptionPane.showMessageDialog(ramka, "Podaj jak¹œ ksywkê");
		});
	
	bWyloguj.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			bLogowanie.setEnabled(true);
			bWyloguj.setEnabled(false);
			bWyslij.setEnabled(false);
			wpis.setEnabled(false);
			try {
			socket.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	);
	
	bWyslij.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{					
			wyslij();				
		}
	});	
}

public void message(String s)
{
	info.append(s+"\n");
}

public void wyslij()
{
	try {
		oos = new ObjectOutputStream(socket.getOutputStream());
		dane = new Dane(TypDanych.MESSAGE, 0, nazwaUsera, "", "", "", haslo, znajomi, wpis.getText().toString()); // 0 - wiadomoœc echo
		oos.writeObject(dane);
		oos.flush();
		message(nazwaUsera+": "+wpis.getText().toString());
	}
	catch (IOException ioe)
	{
		ioe.printStackTrace();
	}
	
	wpis.setText("");
	wpis.requestFocus();
}


public class WatekKlienta implements Runnable
{
	@Override
	public void run()
	{
		try {
			
			socket.connect(new InetSocketAddress(host, port), 3000); // 3 sek. timeout
			
			message("Po³¹czono z serwerem zosta³o ustanowione!");
			
			polaczono = true;
			wpis.setEnabled(polaczono);
			bWyslij.setEnabled(polaczono);
			bLogowanie.setEnabled(!polaczono);
			bWyloguj.setEnabled(polaczono);
			
			ois = new ObjectInputStream(socket.getInputStream());

			dane = null;
			
			while (dane == null)
			{			
				dane = (Dane) ois.readObject();
			}
			message(dane.getWiadomosc());

			}
		catch (SocketTimeoutException ste)
		{
			System.out.println("Min¹³ czas ...");
			ste.printStackTrace();
			System.exit(-1);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(-1);
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
			System.exit(-1);
		}
	}
}

@Override
public void keyPressed(KeyEvent arg0) {
	if ((arg0.getKeyCode() == KeyEvent.VK_ENTER) && (bWyslij.isEnabled()))
	{
		wyslij();
	}
}

@Override
public void keyReleased(KeyEvent arg0) {}

@Override
public void keyTyped(KeyEvent arg0) {}


@Override
public void windowActivated(WindowEvent arg0) {}

@Override
public void windowClosed(WindowEvent arg0) {}

@Override
public void windowClosing(WindowEvent arg0) {
	try {
	
	//if ((!socket.isClosed()) && (socket != null)) socket.close();
	
	if (socket.isConnected()) socket.close();
		
	if (ois != null) ois.close();
	if (oos != null) oos.close();
	}
	catch (IOException ioe)
	{
		ioe.printStackTrace();
		System.exit(-1);
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