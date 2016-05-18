package communicator;

import java.io.Serializable;

/** Podstawowa klasa znajomego u¿ytkownika.
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


/** Metoda zwracaj¹ca numer znajomego.
 * @return Numer znajomego.
 */
public int getNumer() {
	return numer;
}


/** Metoda ustawiaj¹ca numer znajomego.
 * @param numer Numer znajomego.
 */
public void setNumer(int numer) {
	this.numer = numer;
}


/** Metoda zwracaj¹ca nazwê znajomego.
 * @return Nazwa znajomego.
 */
public String getNazwa() {
	return nazwa;
}


/** Metoda ustawiaj¹ca nazwê znajomego.
 * @param nazwa Nazwa znajomego.
 */
public void setNazwa(String nazwa) {
	this.nazwa = nazwa;
}


/** Metoda zwracaj¹ca wartoœæ true lub false: czy znajomy jest online?
 * @return Prawda lub Fa³sz.
 */
public boolean isOnline() {
	return online;
}


/** Metoda ustawiaj¹ca czy znajomy jest online.
 * @param online Prawda lub Fa³sz.
 */
public void setOnline(boolean online) {
	this.online = online;
}
}