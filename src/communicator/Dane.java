package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentująca zestaw danych przesyłanych od klienta do serwera i odwrotnie.
 * @author Piotrek
 *
 */
public class Dane implements Serializable{
	 
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
 * E-mail u�ytkownika wysy�aj�cego wiadomość.
 */
private String mEmail;
/**
 * Has�o u�ytkownika wysy�aj�cego wiadomość.
 */
private char[] mHaslo;
/**
 * HashMapa znajomych u�ytkownika wysy�aj�cego wiadomość.
 */
private HashMap<Integer, Znajomy> mZnajomi;
/**
 * Tre�� wiadomo�ci.
 */
private String mWiadomosc;


/** Konstruktor klasy Dane.
 * @param kto Numer u�ytkownika, od kt�rego pochodzi wiadomość.
 * @param typDanych Typ wiadmo�ci.
 * @param doKogo Numer u�ytkownika, do kt�rego wiadomość jest wysy�ana.
 * @param nazwa Nazwa u�ytkownika.
 * @param imie Imi� u�ytkownika.
 * @param nazwisko Nazwisko u�ytkownika.
 * @param email E-mail u�ytkownika.
 * @param haslo Has�o u�ytkownika.
 * @param znajomi HashMapa znajomych u�ytkownika.
 * @param wiadomosc Tre�� wiadpmo�ci.
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


/** Metoda zwracaj�ca numer u�ytkownika, kt�ry wysy�a wiadomość.
 * 
 * @return Numer u�ytkownika
 */
public int getKto()
{
	return mKto;
}


/**Metoda ustawiaj�ca warto�� u�ytkownika, kt�y wysy�a wiadomość.
 * 
 * @param kto Numer u�ytkownika
 */
public void setKto(int kto)
{
	mKto = kto;
}


/** Metoda zwracaj�ca Typ Danych
 * @see TypDanych
 * @return Typ danych.
 */
public TypDanych getTypDanych() {
	return mTypDanych;
}


/** Metoda ustawiaj�ca warto�� typu danych.
 * @param typDanych Typ danych.
 */
public void setTypDanych(TypDanych typDanych) {
	mTypDanych = typDanych;
}


/** Metoda zwracaj�ca numer u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @return numer u�ytkownika.
 */
public int getDoKogo() {
	return mDoKogo;
}


/** Metoda ustawiaj�ca numer u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @param doKogo Numer u�ytkownika.
 */
public void setDoKogo(int doKogo) {
	mDoKogo = doKogo;
}


/** Metoda zwracaj�ca nazw� u�ytkownika, kt�ry wysy�a wiadomość.
 * @return nazwa u�ytkownika.
 */
public String getNazwa() {
	return mNazwa;
}


/** Metoda ustawiaj�ca nazw� u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @param  nazwa nazwa u�ytkownika.
 */
public void setNazwa(String nazwa) {
	mNazwa = nazwa;
}


/** Metoda zwracaj�ca imi� u�ytkownika, kt�ry wysy�a wiadomość.
 * @return imi� u�ytkownika.
 */
public String getImie() {
	return mImie;
}


/** Metoda ustawiaj�ca imi� u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @param imie Nazwa u�ytkownika.
 */
public void setImie(String imie) {
	mImie = imie;
}


/** Metoda zwracaj�ca nazwisko u�ytkownika, kt�ry wysy�a wiadomość.
 * @return Nazwisko u�ytkownika.
 */
public String getNazwisko() {
	return mNazwisko;
}


/** Metoda ustawiaj�ca nazwisko u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @param nazwisko Nazwisko u�ytkownika.
 */
public void setNazwisko(String nazwisko) {
	mNazwisko = nazwisko;
}


/** Metoda zwracaj�ca e-mail u�ytkownika, kt�ry wysy�a wiadomość.
 * @return E-mail u�ytkownika.
 */
public String getEmail() {
	return mEmail;
}


/** Metoda ustawiaj�ca e-mail u�ytkownika, do kt�rego przekazywana jest wiadomość.
 * @param email E-mail u�ytkownika.
 */
public void setEmail(String email) {
	mEmail = email;
}


/** Metoda zwracaj�ca has�o u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return Has�o u�ytkownika.
 */
public char[] getHaslo() {
	return mHaslo;
}


/** Metoda ustawiaj�ca has�o u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param haslo Has�o u�ytkownika.
 */
public void setHaslo(char[] haslo) {
	mHaslo = haslo;
}


/** Metoda zwracaj�ca HashMap� znajomych u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return HashMapa znajomych.
 */
public HashMap<Integer, Znajomy> getZnajomi() {
	return mZnajomi;
}


/** Metoda ustawiaj�ca HashMap� znajomych u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param znajomi HashMapa znajomych u�ytkownika.
 */
public void setZnajomi(HashMap<Integer, Znajomy> znajomi) {
	mZnajomi = znajomi;
}


/** Metoda zwracaj�ca tre�� wiadomości u�ytkownika.
 * @return Wiadomo�� od u�ytkownika.
 */
public String getWiadomosc() {
	return mWiadomosc;
}


/** Metoda ustawiaj�ca tre�� wiadomości wysy�anej przez u�ytkownika.
 * @param wiadomosc Wiadomo�� u�ytkownika.
 */
public void setWiadomosc(String wiadomosc) {
	mWiadomosc = wiadomosc;
}


}