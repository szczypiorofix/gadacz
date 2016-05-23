package communicator;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientSysTray {

private Image trayIconImage = new ImageIcon(getClass().getResource("/res/client_program_icon.png")).getImage();
private TrayIcon trayIcon;
private SystemTray systray;
private PopupMenu popup;
private MenuItem exitItem, showItem, infoItem;
private boolean windowIsHidden; 	

public ClientSysTray(final JFrame frame)
{
	windowIsHidden = false;
	systray = SystemTray.getSystemTray();
	popup = new PopupMenu();
	
	exitItem = new MenuItem("Zakoñcz");
	exitItem.setFont(new Font("Verdana", Font.PLAIN, 12));
	exitItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	});
	
	showItem = new MenuItem("Powróæ do programu");
	showItem.setFont(new Font("Verdana", Font.BOLD, 12));
	showItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setWindowIsHidden(false);
			frame.setVisible(true);
		}
	});
	
	infoItem = new MenuItem("Informacje o programie");
	infoItem.setFont(new Font("Verdana", Font.PLAIN, 12));
	infoItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String infoString = "<html><h3>Gadacz ... czyli kolejny program do internetowych pogaduszek"
					+ "<br><h4> Wersja 0.8a (build 777)"
					+ "<br><h5>Copyright (c) 2016, PoopingDog Studio. All rights reserved \u00AE";
			JOptionPane.showMessageDialog(frame, infoString, "Gadacz - informacje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(trayIconImage));
		}
	});
	
	popup.add(showItem);
	popup.addSeparator();
	popup.add(infoItem);
	popup.addSeparator();
	popup.add(exitItem);
	
	trayIcon = new TrayIcon(trayIconImage, "Aplikacja klienta komunikatora", popup);
	trayIcon.setImageAutoSize(true);
	
	trayIcon.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setWindowIsHidden(false);
			frame.setVisible(true);
		}
	});
}
/** Metoda wyœwietlaj¹ca okreœlony komunikat w zasobniku systemowym.
 * @param msgBold Treœæ g³ówna komunikatu.
 * @param msgPlain Treœæ komunikatu.
 * @param msgType Typ komunikatu.
 */
public void trayMessage(String msgBold, String msgPlain, TrayIcon.MessageType msgType)
{
	trayIcon.displayMessage(msgBold, msgPlain, msgType);
}

/**
 * Metoda inicjuj¹ca zasobnik systemowy.
 */
public void initialize()
{
	if (!SystemTray.isSupported()) {
		JOptionPane.showMessageDialog(null, "Zasobnik systemowy nie jest dostêpny!", "B³¹d zasobnika systemowego.", JOptionPane.ERROR_MESSAGE);
		return;
	}
	
	try {
		systray.add(trayIcon);
	}
	catch (AWTException awte)
	{
		awte.printStackTrace();
		System.exit(-1);
	}
}

public boolean windowIsHidden()
{
	return windowIsHidden;
}

public void setWindowIsHidden(Boolean b)
{
	windowIsHidden = b;
}

}