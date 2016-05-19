package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentuj�ca zestaw danych przesy�anych od klienta do serwera i odwrotnie.
 * @author Piotrek
 *
 */
public class Dane implements Serializable{
	 
private static final long serialVersionUID = 100L;

/**
 * Numer u�ytkownika, kt�ry wysy�a wiadomo��.
 */
private int mKto;
/**
 * Typ danych wysy�anych przez u�ytkownika.
 * @see TypDanych
 */
private TypDanych mTypDanych;  // REGISTER, MESSAGE lub UPDATE
/**
 * Numer u�ytkownika, do kt�rego wysy�ana jest wiadomo��.
 */
private int mDoKogo;
/**
 * Nazwa u�ytkownika wysy�aj�cego wiadomo��.
 */
private String mNazwa;
/**
 * Imi� u�ytkownika wysy�aj�cego wiadomo��.
 */
private String mImie;
/**
 * Nazwisko u�ytkownika wysy�aj�cego wiadomo��.
 */
private String mNazwisko;
/**
 * E-mail u�ytkownika wysy�aj�cego wiadomo��.
 */
private String mEmail;
/**
 * Has�o u�ytkownika wysy�aj�cego wiadomo��.
 */
private char[] mHaslo;
/**
 * HashMapa znajomych u�ytkownika wysy�aj�cego wiadomo��.
 */
private HashMap<Integer, Znajomy> mZnajomi;
/**
 * Tre�� wiadomo�ci.
 */
private String mWiadomosc;


/** Konstruktor klasy Dane.
 * @param kto Numer u�ytkownika, od kt�rego pochodzi wiadmo��.
 * @param typDanych Typ wiadmo�ci.
 * @param doKogo Numer u�ytkownika, do kt�rego wiadomo�� jest wysy�ana.
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


/** Metoda zwracaj�ca numer u�ytkownika, kt�ry wysy�a wiadomo��.
 * 
 * @return Numer u�ytkownika
 */
public int getKto()
{
	return mKto;
}


/**Metoda ustawiaj�ca warto�� u�ytkownika, kt�y wysy�a wiadomo��.
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


/** Metoda zwracaj�ca numer u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @return numer u�ytkownika.
 */
public int getDoKogo() {
	return mDoKogo;
}


/** Metoda ustawiaj�ca numer u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param doKogo Numer u�ytkownika.
 */
public void setDoKogo(int doKogo) {
	mDoKogo = doKogo;
}


/** Metoda zwracaj�ca nazw� u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return nazwa u�ytkownika.
 */
public String getNazwa() {
	return mNazwa;
}


/** Metoda ustawiaj�ca nazw� u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param  nazwa nazwa u�ytkownika.
 */
public void setNazwa(String nazwa) {
	mNazwa = nazwa;
}


/** Metoda zwracaj�ca imi� u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return imi� u�ytkownika.
 */
public String getImie() {
	return mImie;
}


/** Metoda ustawiaj�ca imi� u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param imie Nazwa u�ytkownika.
 */
public void setImie(String imie) {
	mImie = imie;
}


/** Metoda zwracaj�ca nazwisko u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return Nazwisko u�ytkownika.
 */
public String getNazwisko() {
	return mNazwisko;
}


/** Metoda ustawiaj�ca nazwisko u�ytkownika, do kt�rego przekazywana jest wiadomo��.
 * @param nazwisko Nazwisko u�ytkownika.
 */
public void setNazwisko(String nazwisko) {
	mNazwisko = nazwisko;
}


/** Metoda zwracaj�ca e-mail u�ytkownika, kt�ry wysy�a wiadomo��.
 * @return E-mail u�ytkownika.
 */
public String getEmail() {
	return mEmail;
}


/** Metoda ustawiaj�ca e-mail u�ytkownika, do kt�rego przekazywana jest wiadomo��.
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


/** Metoda zwracaj�ca tre�� wiadomo�ci u�ytkownika.
 * @return Wiadomo�� od u�ytkownika.
 */
public String getWiadomosc() {
	return mWiadomosc;
}


/** Metoda ustawiaj�ca tre�� wiadomo�ci wysy�anej przez u�ytkownika.
 * @param wiadomosc Wiadomo�� u�ytkownika.
 */
public void setWiadomosc(String wiadomosc) {
	mWiadomosc = wiadomosc;
}


}