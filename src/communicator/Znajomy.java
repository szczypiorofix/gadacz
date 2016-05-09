package communicator;

import java.io.Serializable;

public class Znajomy implements Serializable{

private static final long serialVersionUID = 2L;

private int numer;
private String nazwa;
private boolean online;


public Znajomy(int numer, String nazwa, boolean online) {
	this.numer = numer;
	this.nazwa = nazwa;
	this.online = online;
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


public boolean isOnline() {
	return online;
}


public void setOnline(boolean online) {
	this.online = online;
}
}