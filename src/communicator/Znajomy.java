package communicator;

import java.io.Serializable;

/** Podstawowa klasa znajomego użytkownika.
 * @author Piotrek
 * @see ClientMain
 * @see ServerMain
 * @see Dane
 * @see TypDanych
 * @see Uzytkownik
 *
 */
public class Znajomy implements Serializable {

	private static final long serialVersionUID = 2L;

	/**
	 * Numer znajomego.
	 */
	private int numer;

	/**
	 * Nazwa znajomego.
	 */
	private String nazwa;
	/**
	 * Czy znajomy jest online?
	 */
	private boolean online;


	/** Podstawowy konstruktor klasy Znajomy.
	 * @param numer Numer znajomego.
	 * @param nazwa Nazwa znajomego.
	 * @param online Czy znajomy jest online?
	 */
	public Znajomy(int numer, String nazwa, boolean online) {
		this.numer = numer;
		this.nazwa = nazwa;
		this.online = online;
	}


	/** Metoda zwracająca numer znajomego.
	 * @return Numer znajomego.
	 */
	public int getNumer() {
		return numer;
	}


	/** Metoda ustawiająca numer znajomego.
	 * @param numer Numer znajomego.
	 */
	public void setNumer(int numer) {
		this.numer = numer;
	}


	/** Metoda zwracająca nazwę znajomego.
	 * @return Nazwa znajomego.
	 */
	public String getNazwa() {
		return nazwa;
	}


	/** Metoda ustawiająca nazwę znajomego.
	 * @param nazwa Nazwa znajomego.
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	/** Metoda zwracająca wartość true lub false: czy znajomy jest online?
	 * @return 'true' lub 'false'.
	 */
	public boolean isOnline() {
		return online;
	}


	/** Metoda ustawiająca czy znajomy jest online.
	 * @param online 'true' lub 'false'.
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}
}