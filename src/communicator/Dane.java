package communicator;


import java.io.Serializable;
import java.util.HashMap;

/**Klasa reprezentuj¹ca zestaw danych przesy³anych od klienta do serwera i odwrotnie.
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