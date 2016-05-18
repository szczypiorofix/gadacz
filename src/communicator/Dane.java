package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentuj�ca zestaw danych przesy�anych od klienta do serwera i odwrotnie.
 * @author Piotrek
 *
 */
public class Dane implements Serializable{
	 
private static final long serialVersionUID = 100L;

private int mKto;
private TypDanych mTypDanych;  // REGISTER, MESSAGE lub UPDATE
private int mDoKogo;
private String mNazwa;
private String mImie;
private String mNazwisko;
private String mEmail;
private char[] mHaslo;
private HashMap<Integer, Znajomy> mZnajomi;
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


public int getDoKogo() {
	return mDoKogo;
}


public void setDoKogo(int doKogo) {
	mDoKogo = doKogo;
}


public String getNazwa() {
	return mNazwa;
}


public void setNazwa(String nazwa) {
	mNazwa = nazwa;
}


public String getImie() {
	return mImie;
}


public void setImie(String imie) {
	mImie = imie;
}


public String getNazwisko() {
	return mNazwisko;
}


public void setNazwisko(String nazwisko) {
	mNazwisko = nazwisko;
}


public String getEmail() {
	return mEmail;
}


public void setEmail(String email) {
	mEmail = email;
}


public char[] getHaslo() {
	return mHaslo;
}


public void setHaslo(char[] haslo) {
	mHaslo = haslo;
}


public HashMap<Integer, Znajomy> getZnajomi() {
	return mZnajomi;
}


public void setZnajomi(HashMap<Integer, Znajomy> znajomi) {
	mZnajomi = znajomi;
}


public String getWiadomosc() {
	return mWiadomosc;
}


public void setWiadomosc(String wiadomosc) {
	mWiadomosc = wiadomosc;
}


}