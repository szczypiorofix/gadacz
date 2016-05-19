package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentuj¹ca zestaw danych przesy³anych od klienta do serwera i odwrotnie.
 * @author Piotrek
 *
 */
public class Dane implements Serializable{
	 
private static final long serialVersionUID = 100L;

/**
 * Numer u¿ytkownika, który wysy³a wiadomoœæ.
 */
private int mKto;
/**
 * Typ danych wysy³anych przez u¿ytkownika.
 * @see TypDanych
 */
private TypDanych mTypDanych;  // REGISTER, MESSAGE lub UPDATE
/**
 * Numer u¿ytkownika, do którego wysy³ana jest wiadomoœæ.
 */
private int mDoKogo;
/**
 * Nazwa u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private String mNazwa;
/**
 * Imiê u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private String mImie;
/**
 * Nazwisko u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private String mNazwisko;
/**
 * E-mail u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private String mEmail;
/**
 * Has³o u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private char[] mHaslo;
/**
 * HashMapa znajomych u¿ytkownika wysy³aj¹cego wiadomoœæ.
 */
private HashMap<Integer, Znajomy> mZnajomi;
/**
 * Treœæ wiadomoœci.
 */
private String mWiadomosc;


/** Konstruktor klasy Dane.
 * @param kto Numer u¿ytkownika, od którego pochodzi wiadmoœæ.
 * @param typDanych Typ wiadmoœci.
 * @param doKogo Numer u¿ytkownika, do którego wiadomoœæ jest wysy³ana.
 * @param nazwa Nazwa u¿ytkownika.
 * @param imie Imiê u¿ytkownika.
 * @param nazwisko Nazwisko u¿ytkownika.
 * @param email E-mail u¿ytkownika.
 * @param haslo Has³o u¿ytkownika.
 * @param znajomi HashMapa znajomych u¿ytkownika.
 * @param wiadomosc Treœæ wiadpmoœci.
 */
public Dane(int kto, TypDanych typDanych, int doKogo, String nazwa, String imie, String nazwisko, String email, char[] haslo, HashMap<Integer, Znajomy> znajomi, String wiadomosc)
{
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


/** Metoda zwracaj¹ca numer u¿ytkownika, który wysy³a wiadomoœæ.
 * 
 * @return Numer u¿ytkownika
 */
public int getKto()
{
	return mKto;
}


/**Metoda ustawiaj¹ca wartoœæ u¿ytkownika, któy wysy³a wiadomoœæ.
 * 
 * @param kto Numer u¿ytkownika
 */
public void setKto(int kto)
{
	mKto = kto;
}


/** Metoda zwracaj¹ca Typ Danych
 * @see TypDanych
 * @return Typ danych.
 */
public TypDanych getTypDanych() {
	return mTypDanych;
}


/** Metoda ustawiaj¹ca wartoœæ typu danych.
 * @param typDanych Typ danych.
 */
public void setTypDanych(TypDanych typDanych) {
	mTypDanych = typDanych;
}


/** Metoda zwracaj¹ca numer u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @return numer u¿ytkownika.
 */
public int getDoKogo() {
	return mDoKogo;
}


/** Metoda ustawiaj¹ca numer u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param doKogo Numer u¿ytkownika.
 */
public void setDoKogo(int doKogo) {
	mDoKogo = doKogo;
}


/** Metoda zwracaj¹ca nazwê u¿ytkownika, który wysy³a wiadomoœæ.
 * @return nazwa u¿ytkownika.
 */
public String getNazwa() {
	return mNazwa;
}


/** Metoda ustawiaj¹ca nazwê u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param  nazwa nazwa u¿ytkownika.
 */
public void setNazwa(String nazwa) {
	mNazwa = nazwa;
}


/** Metoda zwracaj¹ca imiê u¿ytkownika, który wysy³a wiadomoœæ.
 * @return imiê u¿ytkownika.
 */
public String getImie() {
	return mImie;
}


/** Metoda ustawiaj¹ca imiê u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param imie Nazwa u¿ytkownika.
 */
public void setImie(String imie) {
	mImie = imie;
}


/** Metoda zwracaj¹ca nazwisko u¿ytkownika, który wysy³a wiadomoœæ.
 * @return Nazwisko u¿ytkownika.
 */
public String getNazwisko() {
	return mNazwisko;
}


/** Metoda ustawiaj¹ca nazwisko u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param nazwisko Nazwisko u¿ytkownika.
 */
public void setNazwisko(String nazwisko) {
	mNazwisko = nazwisko;
}


/** Metoda zwracaj¹ca e-mail u¿ytkownika, który wysy³a wiadomoœæ.
 * @return E-mail u¿ytkownika.
 */
public String getEmail() {
	return mEmail;
}


/** Metoda ustawiaj¹ca e-mail u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param email E-mail u¿ytkownika.
 */
public void setEmail(String email) {
	mEmail = email;
}


/** Metoda zwracaj¹ca has³o u¿ytkownika, który wysy³a wiadomoœæ.
 * @return Has³o u¿ytkownika.
 */
public char[] getHaslo() {
	return mHaslo;
}


/** Metoda ustawiaj¹ca has³o u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param haslo Has³o u¿ytkownika.
 */
public void setHaslo(char[] haslo) {
	mHaslo = haslo;
}


/** Metoda zwracaj¹ca HashMapê znajomych u¿ytkownika, który wysy³a wiadomoœæ.
 * @return HashMapa znajomych.
 */
public HashMap<Integer, Znajomy> getZnajomi() {
	return mZnajomi;
}


/** Metoda ustawiaj¹ca HashMapê znajomych u¿ytkownika, do którego przekazywana jest wiadomoœæ.
 * @param znajomi HashMapa znajomych u¿ytkownika.
 */
public void setZnajomi(HashMap<Integer, Znajomy> znajomi) {
	mZnajomi = znajomi;
}


/** Metoda zwracaj¹ca treœæ wiadomoœci u¿ytkownika.
 * @return Wiadomoœæ od u¿ytkownika.
 */
public String getWiadomosc() {
	return mWiadomosc;
}


/** Metoda ustawiaj¹ca treœæ wiadomoœci wysy³anej przez u¿ytkownika.
 * @param wiadomosc Wiadomoœæ u¿ytkownika.
 */
public void setWiadomosc(String wiadomosc) {
	mWiadomosc = wiadomosc;
}


}