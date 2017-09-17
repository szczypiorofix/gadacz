package communicator;

import java.io.Serializable;
import java.util.HashMap;

/** Podstawowa klasa użytkownika, wpisywanego w bazę użytkowników.
 * @author Piotrek
 * @see ClientMain
 * @see ServerMain
 * @see Dane
 * @see Znajomy
 * @see TypDanych
 */
public class Uzytkownik implements Serializable{

	private static final long serialVersionUID = 1L;

	private int numer;
	private String nazwa, imie, nazwisko, email;
	private char[] haslo;
	private HashMap<Integer, Znajomy> znajomi;



	/** Podstawowy konstruktor klasy Użytkownik.
	 * @param numer Numer użytkownika.
	 * @param nazwa Nazwa.
	 * @param imie Imię.
	 * @param nazwisko Nazwisko.
	 * @param email E-mail.
	 * @param haslo Hasło użytkownika.
	 * @param hashMap HashMapa zawierająca wykaz znajomych użytkownika.
	 */
	public Uzytkownik(int numer, String nazwa, String imie, String nazwisko, String email, char[] haslo, HashMap<Integer, Znajomy> hashMap)
	{
		this.numer = numer;
		this.nazwa = nazwa;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.email = email;
		this.haslo = haslo;
		this.znajomi = hashMap;
	}

	public int getNumer() {
		return numer;
	}


	public void setNumer(int numer) {
		this.numer = numer;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	public String getImie() {
		return imie;
	}


	public void setImie(String imie) {
		this.imie = imie;
	}


	public String getNazwisko() {
		return nazwisko;
	}


	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public char[] getHaslo() {
		return haslo;
	}


	public void setHaslo(char[] haslo) {
		this.haslo = haslo;
	}


	public HashMap<Integer, Znajomy> getZnajomi() {
		return znajomi;
	}


	public void setZnajomi(HashMap<Integer, Znajomy> znajomi) {
		this.znajomi = znajomi;
	}

}