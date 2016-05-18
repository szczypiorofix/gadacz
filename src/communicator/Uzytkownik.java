package communicator;

import java.io.Serializable;
import java.util.HashMap;

/** Podstawowa klasa u¿ytkownika, wpisywanego w bazê u¿ytkowników.
 * @author Piotrek
 *
 */
public class Uzytkownik implements Serializable{

private static final long serialVersionUID = 1L;

private int numer;
private String nazwa, imie, nazwisko, email;
private char[] haslo;
private HashMap<Integer, Znajomy> znajomi;



/** Podstawowy konstruktor klasy U¿ytkownik.
 * @param numer Numer u¿ytkownika.
 * @param nazwa Nazwa.
 * @param imie Imiê.
 * @param nazwisko Nazwisko.
 * @param email E-mail.
 * @param haslo Has³o u¿ytkownika.
 * @param hashMap HashMapa zawieraj¹ca wykaz znajomych u¿ytkownika.
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