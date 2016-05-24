package communicator;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** Klasa systemowego zasobnika programu klienta.
 * @author Piotrek
 */
public class ClientSysTray {

/**
 *  Obraz ikony zasobnika systemowego aplikacji klienta.
 */
private Image trayIconImage = new ImageIcon(getClass().getResource("/res/client_program_icon.png")).getImage();

/**
 * Ikona zasobnika systemowego aplikacji klienta.
 */
private TrayIcon trayIcon;

/**
 * Zasobnik systemowy aplikacji klienta.
 */
private SystemTray systray;

/**
 * Popup Menu zasobnika systemowego.
 */
private PopupMenu popup;

/**
 * MenuItem wyj�cie.
 */
private MenuItem exitItem;

/**
 * MenuItem poka�.
 */
private MenuItem showItem;

/**
 * MenuItem informacje.
 */
private MenuItem infoItem;


private CheckboxMenuItem hideToTray;

/**
 * True je�li okno aplikacji serwera jest ukryte.
 */
private boolean windowIsHidden;

/**
 * Informacje "O aplikacji"
 */
private final String infoString = "<html><h3>Gadacz ... czyli kolejny program do internetowych pogaduszek"
		+ "<br><h4> Wersja 0.8a (build 777)"
		+ "<br><h5>Copyright (c) 2016, PoopingDog Studio. All rights reserved \u00AE";

/**
 * Standardowa czcionka.
 */
private final Font normalFont = new Font("Verdana", Font.PLAIN, 12);

/**
 * Standardowa czcionka, pogrubiona.
 */
private final Font boldFont = new Font("Verdana", Font.BOLD, 12);


/**
 * True - klikni�cie w X aplikacji zamyk� j�, False - klikni�cie X ukrywa aplikacj� w systrayu.
 */
private boolean hideOnX = true;



/** Podstawowy konstruktor systemowego zasobnika programu klienta.
 * @param frame (final JFrame frame) Ramka g��wna programu.
 */
public ClientSysTray(final JFrame frame)
{
	windowIsHidden = false;
	systray = SystemTray.getSystemTray();
	popup = new PopupMenu();
	
	exitItem = new MenuItem("Zako�cz");
	exitItem.setFont(normalFont);
	exitItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	});
	
	showItem = new MenuItem("Powr�� do programu");
	showItem.setFont(boldFont);
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
	infoItem.setFont(normalFont);
	infoItem.addActionListener(new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(frame, infoString, "Gadacz - informacje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(trayIconImage));
		}
	});
	
	hideToTray = new CheckboxMenuItem("Ukrywaj do SysTray'a", hideOnX);
	hideToTray.setFont(normalFont);
	hideToTray.addItemListener(new ItemListener()
	{
		@Override
		public void itemStateChanged(ItemEvent arg0) {
			setHideOnX(!isHideOnX());
		}				
	});
	
	popup.add(showItem);
	popup.addSeparator();
	popup.add(infoItem);
	popup.add(hideToTray);
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
/** Metoda wy�wietlaj�ca okre�lony komunikat w zasobniku systemowym.
 * @param msgBold Tre�� g��wna komunikatu.
 * @param msgPlain Tre�� komunikatu.
 * @param msgType Typ komunikatu.
 */
public void trayMessage(String msgBold, String msgPlain, TrayIcon.MessageType msgType)
{
	trayIcon.displayMessage(msgBold, msgPlain, msgType);
}

/**
 * Metoda inicjuj�ca zasobnik systemowy.
 */
public void initialize()
{
	if (!SystemTray.isSupported()) {
		JOptionPane.showMessageDialog(null, "Zasobnik systemowy nie jest dost�pny!", "B��d zasobnika systemowego.", JOptionPane.ERROR_MESSAGE);
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

/** Metoda zwracaj�ca warto�� true lub false odno�nie ukrytego okna g��wnego aplikacji.
 * @return windowIsHidden.
 */
public boolean windowIsHidden()
{
	return windowIsHidden;
}

/** Metoda ustaiwaj�ca warto�� true lub false odno�nie ukrytego okna g��wnego aplikacji.
 * @param b (Boolean) true / false
 */
public void setWindowIsHidden(Boolean b)
{
	windowIsHidden = b;
}


/** Metoda zwracaj�ca warto�� hideOnX
 * @return hideOnX
 */
public boolean isHideOnX() {
	return hideOnX;
}


/** Metoda ustaiwaj�ca warto�� hideOnX
 * @param hideOnX True lub False - czy klikni�cie w X ma zamyka� aplikacj� (true) czy tylko j� ukrywa� do systraya (false)
 */
public void setHideOnX(boolean hideOnX) {
	this.hideOnX = hideOnX;
}



}