package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentująca zestaw danych przesyłanych od klienta do serwera i odwrotnie.
 * @author Piotrek
 *
 */
public class Dane implements Serializable {
	 
private static final long serialVersionUID = 100L;

/**
 * Numer użytkownika, który wysyła wiadomość.
 */
private int mKto;

/**
 * Typ danych wysyłanych przez użytkownika.
 * @see TypDanych
 */
private TypDanych mTypDanych;  // REGISTER, MESSAGE lub UPDATE

/**
 * Numer użytkownika, do którego wysyłana jest wiadomość.
 */
private int mDoKogo;

/**
 * Nazwa użytkownika wysyłającego wiadomość.
 */
private String mNazwa;

/**
 * Imię użytkownika wysyłającego wiadomość.
 */
private String mImie;
/**
 * Nazwisko użytkownika wysyłającego wiadomość.
 */
private String mNazwisko;
/**
 * E-mail użytkownika wysyłającego wiadomość.
 */
private String mEmail;
/**
 * Hasło użytkownika wysyłającego wiadomość.
 */
private char[] mHaslo;
/**
 * HashMapa znajomych użytkownika wysyłającego wiadomość.
 */
private HashMap<Integer, Znajomy> mZnajomi;
/**
 * Treść wiadomości.
 */
private String mWiadomosc;


/** Konstruktor klasy Dane.
 * @param kto Numer użytkownika, od którego pochodzi wiadomość.
 * @param typDanych Typ wiadmości.
 * @param doKogo Numer użytkownika, do którego wiadomość jest wysyłana.
 * @param nazwa Nazwa użytkownika.
 * @param imie Imię użytkownika.
 * @param nazwisko Nazwisko użytkownika.
 * @param email E-mail użytkownika.
 * @param haslo Hasło użytkownika.
 * @param znajomi HashMapa znajomych użytkownika.
 * @param wiadomosc Treść wiadpmości.
 */
public Dane(int kto, TypDanych typDanych, int doKogo, String nazwa, String imie, String nazwisko, String email, char[] haslo, HashMap<Integer, Znajomy> znajomi, String wiadomosc) {
	mKto = kto;
	mTypDanych = typDanych;
	mDoKogo = doKogo;
	mNazwa = nazwa;
	mImie = imie;
	mNazwisko = nazwisko;
	mEmail = email;
	mHaslo = haslo;
	mZnajomi = znajomi;
	mWiadomosc = wiadomosc;
}


/** Metoda zwracająca numer użytkownika, który wysyła wiadomość.
 * 
 * @return Numer użytkownika
 */
public int getKto()
{
	return mKto;
}


/**Metoda ustawiająca wartość numeru użytkownika, któy wysyła wiadomość.
 * 
 * @param kto Numer użytkownika
 */
public void setKto(int kto)
{
	mKto = kto;
}


/** Metoda zwracająca Typ Danych
 * @see TypDanych
 * @return Typ danych.
 */
public TypDanych getTypDanych() {
	return mTypDanych;
}


/** Metoda ustawiająca wartość typu danych.
 * @param typDanych Typ danych.
 */
public void setTypDanych(TypDanych typDanych) {
	mTypDanych = typDanych;
}


/** Metoda zwracająca numer użytkownika, do którego przekazywana jest wiadomość.
 * @return numer użytkownika.
 */
public int getDoKogo() {
	return mDoKogo;
}


/** Metoda ustawiająca numer użytkownika, do którego przekazywana jest wiadomość.
 * @param doKogo Numer użytkownika.
 */
public void setDoKogo(int doKogo) {
	mDoKogo = doKogo;
}


/** Metoda zwracająca nazwę użytkownika, który wysyła wiadomość.
 * @return nazwa użytkownika.
 */
public String getNazwa() {
	return mNazwa;
}


/** Metoda ustawiająca nazwę użytkownika, do którego przekazywana jest wiadomość.
 * @param  nazwa nazwa użytkownika.
 */
public void setNazwa(String nazwa) {
	mNazwa = nazwa;
}


/** Metoda zwracająca imię użytkownika, który wysyła wiadomość.
 * @return imię użytkownika.
 */
public String getImie() {
	return mImie;
}


/** Metoda ustawiająca imię użytkownika, do którego przekazywana jest wiadomość.
 * @param imie Nazwa użytkownika.
 */
public void setImie(String imie) {
	mImie = imie;
}


/** Metoda zwracająca nazwisko użytkownika, który wysyła wiadomość.
 * @return Nazwisko użytkownika.
 */
public String getNazwisko() {
	return mNazwisko;
}


/** Metoda ustawiająca nazwisko użytkownika, do którego przekazywana jest wiadomość.
 * @param nazwisko Nazwisko użytkownika.
 */
public void setNazwisko(String nazwisko) {
	mNazwisko = nazwisko;
}


/** Metoda zwracająca e-mail użytkownika, który wysyła wiadomość.
 * @return E-mail użytkownika.
 */
public String getEmail() {
	return mEmail;
}


/** Metoda ustawiająca e-mail użytkownika, do którego przekazywana jest wiadomość.
 * @param email E-mail użytkownika.
 */
public void setEmail(String email) {
	mEmail = email;
}


/** Metoda zwracająca hasło użytkownika, który wysyła wiadomość.
 * @return Hasło użytkownika.
 */
public char[] getHaslo() {
	return mHaslo;
}


/** Metoda ustawiająca hasło użytkownika, do którego przekazywana jest wiadomość.
 * @param haslo Hasło użytkownika.
 */
public void setHaslo(char[] haslo) {
	mHaslo = haslo;
}


/** Metoda zwracająca HashMapę znajomych użytkownika, który wysyła wiadomość.
 * @return HashMapa znajomych.
 */
public HashMap<Integer, Znajomy> getZnajomi() {
	return mZnajomi;
}


/** Metoda ustawiająca HashMapę znajomych użytkownika, do którego przekazywana jest wiadomość.
 * @param znajomi HashMapa znajomych użytkownika.
 */
public void setZnajomi(HashMap<Integer, Znajomy> znajomi) {
	mZnajomi = znajomi;
}


/** Metoda zwracająca treść wiadomości użytkownika.
 * @return Wiadomość od użytkownika.
 */
public String getWiadomosc() {
	return mWiadomosc;
}


/** Metoda ustawiająca treść wiadomości wysyłanej przez użytkownika.
 * @param wiadomosc Wiadomość użytkownika.
 */
public void setWiadomosc(String wiadomosc) {
	mWiadomosc = wiadomosc;
}


}