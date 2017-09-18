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
	 * MenuItem wyjście.
	 */
	private MenuItem exitItem;

	/**
	 * MenuItem pokaż.
	 */
	private MenuItem showItem;

	/**
	 * MenuItem informacje.
	 */
	private MenuItem infoItem;


	private CheckboxMenuItem hideToTray;

	/**
	 * True jeżli okno aplikacji serwera jest ukryte.
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
	 * True - kliknięcie w X aplikacji zamyka ją, False - kliknięcie X ukrywa aplikację w systrayu.
	 */
	private boolean hideOnX = true;





	/** Podstawowy konstruktor systemowego zasobnika programu klienta.
	 * @param frame (final JFrame frame) Ramka główna programu.
	 */
	ClientSysTray(final JFrame frame)
	{
		windowIsHidden = false;
		systray = SystemTray.getSystemTray();
		popup = new PopupMenu();

		exitItem = new MenuItem("Zakończ");
		exitItem.setFont(normalFont);
		exitItem.addActionListener((ActionEvent e) -> System.exit(0));

		showItem = new MenuItem("Powrót do programu");
		showItem.setFont(boldFont);
		showItem.addActionListener((ActionEvent e) ->
		{
            setWindowIsHidden(false);
            frame.setVisible(true);
		});

		infoItem = new MenuItem("Informacje o programie");
		infoItem.setFont(normalFont);
		infoItem.addActionListener((ActionEvent e) ->
                JOptionPane.showMessageDialog(frame, infoString, "Gadacz - informacje", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(trayIconImage)));

		hideToTray = new CheckboxMenuItem("Ukrywaj do SysTray'a", hideOnX);
		hideToTray.setFont(normalFont);
		hideToTray.addItemListener(e -> setHideOnX(!isHideOnX()));

		popup.add(showItem);
		popup.addSeparator();
		popup.add(infoItem);
		popup.add(hideToTray);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon = new TrayIcon(trayIconImage, "Aplikacja klienta komunikatora", popup);
		trayIcon.setImageAutoSize(true);

		trayIcon.addActionListener((ActionEvent e) ->
		{
            setWindowIsHidden(false);
            frame.setVisible(true);
		});
	}

    /** Metoda wyświetlająca określony komunikat w zasobniku systemowym.
     * @param msgBold Treść główna komunikatu.
     * @param msgPlain Treść komunikatu.
     * @param msgType Typ komunikatu.
     */
    void trayMessage(String msgBold, String msgPlain, TrayIcon.MessageType msgType)
    {
        trayIcon.displayMessage(msgBold, msgPlain, msgType);
    }

    /**
     * Metoda inicjująca zasobnik systemowy.
     */
    void initialize()
    {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "Zasobnik systemowy nie jest dostępny!", "Błąd zasobnika systemowego.", JOptionPane.ERROR_MESSAGE);
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

    /** Metoda zwracająca wartość true lub false odnośnie ukrytego okna głównego aplikacji.
     * @return windowIsHidden.
     */
    public boolean windowIsHidden()
    {
        return windowIsHidden;
    }

    /** Metoda ustaiwająca wartość true lub false odnośnie ukrytego okna głównego aplikacji.
     * @param b (Boolean) true / false
     */
    void setWindowIsHidden(Boolean b)
    {
        windowIsHidden = b;
    }


    /** Metoda zwracająca wartość hideOnX
     * @return hideOnX
     */
    boolean isHideOnX() {
        return hideOnX;
    }


    /** Metoda ustaiwająca wartość hideOnX
     * @param hideOnX True lub False - czy kliknięcie w X ma zamykać aplikację (true) czy tylko ją ukrywać do systraya (false)
     */
    private void setHideOnX(boolean hideOnX) {
        this.hideOnX = hideOnX;
    }

}