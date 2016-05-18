package communicator;

/** Oznaczenia typ�w danych przesy�anych przez u�ytkownik�w i serwer.
 * @author Piotrek
 * @see ClientMain
 * @see ServerMain
 * @see MySQLBase
 * @see Uzytkownik
 * @see Znajomy
 * @see Dane
 */
public enum TypDanych {

	/**
	 *  Typ danych jako zg�oszenie nowego u�ytkownika do rejestracji.
	 */
	REGISTER,
	/**
	 * Typ danych jako wiadomo�� od klienta do klienta.
	 */
	MESSAGE,
	/**
	 *  Typ danych jako zg�oszenie zalogowania u�ytkownika, kt�ry ju� jest zarejestrowany w systemie.
	 */
	LOG,
	/**
	 * Typ danych wskazuj�cych na wprowadzenie b��dnych danych podczas logowania u�ytkownika.
	 */
	WRONG
}
