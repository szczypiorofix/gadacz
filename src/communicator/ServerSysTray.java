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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/** Klasa systemowego zasobnika programu serwera.
 * @author Piotrek
 *
 */
public class ServerSysTray {

private Image trayIconImage = new ImageIcon(getClass().getResource("/res/systrayicon.png")).getImage();
private TrayIcon trayIcon;
private SystemTray systray;
private PopupMenu popup;
private MenuItem exitItem, showItem, infoItem;
private boolean windowIsHidden; 

/** Podstawowy konstruktor systemowego zasobnika programu serwera.
 * @param r (JFrame r) Ramka g³ówna programu.
 */
public ServerSysTray(JFrame r)
{
	windowIsHidden = false;
	systray = SystemTray.getSystemTray();
	popup = new PopupMenu();
	
	exitItem = new MenuItem("Wyjœcie");
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
			r.setVisible(true);
		}
	});
	
	infoItem = new MenuItem("Informacje o programie");
	infoItem.setFont(new Font("Verdana", Font.PLAIN, 12));
	infoItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(r, "Wersja programu 0.8", "Serwer komunikatora - informacje", JOptionPane.INFORMATION_MESSAGE);
		}
	});
	
	popup.add(showItem);
	popup.addSeparator();
	popup.add(infoItem);
	popup.addSeparator();
	popup.add(exitItem);
	
	trayIcon = new TrayIcon(trayIconImage, "Serwer komunikatora", popup);
	trayIcon.setImageAutoSize(true);
	
	trayIcon.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setWindowIsHidden(false);
			r.setVisible(true);
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
		JOptionPane.showMessageDialog(null, "System tray is not supported!", "System tray error!", JOptionPane.ERROR_MESSAGE);
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
