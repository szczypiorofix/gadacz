package communicator;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ServerMain
{

private int port = 1201;
private String nrPortu = null;
private ServerSocket serverSocket;
private Socket socket;
private ObjectInputStream ois;
private ObjectOutputStream oos;
private JFrame ramka;
private JTextArea historia;
private JScrollPane scroll;
private String h = "Serwer 1.0\n";
private int count = 0;
private boolean numberError;
private URL whatismyip;
private BufferedReader buffreader;
private String externalIP;
private Dane dane;
private ServerThread r;
private Thread t;


public static void main(String[] args)
{
	new ServerMain();
}

public ServerMain()
{
	ramka = new JFrame("Serwer komunikatora");
	ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ramka.setSize(400, 400);
	ramka.setLocationRelativeTo(null);

	historia = new JTextArea(h);
	historia.setEditable(false);
	historia.setLineWrap(true);
	historia.setWrapStyleWord(true);
	scroll = new JScrollPane(historia);
	ramka.add(scroll);
	ramka.setVisible(true);

	do {
		numberError = false;
		nrPortu = JOptionPane.showInputDialog(ramka, "Podaj numer portu serwera (np.1201)", port);
		try {
			port = Integer.valueOf(nrPortu);
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(ramka, "Numer portu nie moøe zawieraÊ liter!");
			numberError = true;
		}
	}
	while (numberError);

	
	try {
		message("Start serwera.");
		message("Otwieranie gniazdka serwera. Port: " +port);
		serverSocket = new ServerSocket(port);
		message("Lokalny adres serwera: " +InetAddress.getLocalHost().toString());
		
		//whatismyip = new URL("http://checkip.amazonaws.com");
		//buffreader = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		//externalIP = buffreader.readLine();
		//message("ZewnÍtrzny adres serwera: " +externalIP);
				
		try {
			while (true)
			{
			socket = serverSocket.accept();
			message("Po≥πczenie z klientem: " +count +" " +socket.getInetAddress());
			r = new ServerThread(socket);
			t = new Thread(r);
			t.start();
			count++;
			}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
				System.exit(-1);
			}

		}
		catch (SocketException se)
		{
			se.printStackTrace();
			System.exit(-1);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(-1);
		}

}

public class ServerThread implements Runnable
{

private Socket socket;
private boolean connected;

public ServerThread(Socket s)
{
	this.socket = s;
	connected = true;
}
	
@Override
public void run()
{
	try {
		
		while (connected)
		{
		ois = new ObjectInputStream(this.socket.getInputStream());
		oos = new ObjectOutputStream(this.socket.getOutputStream());
		
		// ODBIERANIE WIADOMOåCI OD KLIENTA

		if (connected) dane = (Dane) ois.readObject();		
		
		if (dane.getTypDanych() == TypDanych.MESSAGE) message("Klient: "+dane.getNazwa() +" (pass: " +new String(dane.getHaslo()) +") : " +dane.getWiadomosc());
		if (dane.getTypDanych() == TypDanych.REGISTER) {
			message("Rejestracja nowego klienta: "+dane.getNazwa() +", " +dane.getImie() +", " +dane.getNazwisko() +", " +dane.getEmail() +", " +new String(dane.getHaslo()) +".");
			ois.close();
			oos.close();
			connected = false;
		}
		
		// ODPOWIEDè SERWERA DO KLIENTA
		
		if (dane.getTypDanych() == TypDanych.MESSAGE) {
		dane.setWiadomosc("Serwer echo: " +dane.getWiadomosc());
		oos.writeObject(dane);
		oos.flush();
		}
		}
	}
	catch (ClassNotFoundException cnfe)
	{
		cnfe.printStackTrace();
		message("Klient roz≥πczy≥ siÍ.");
		//System.exit(-1);
	}
	catch (IOException ioe)
	{
		ioe.printStackTrace();
		System.exit(-1);
	}
	}
}


public void message(String s)
{
	historia.append(s+"\n");
}
}