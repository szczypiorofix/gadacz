package communicator;

/** Oznaczenia typów danych przesy³anych przez u¿ytkowników i serwer.
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
	 *  Typ danych jako zg³oszenie nowego u¿ytkownika do rejestracji.
	 */
	REGISTER,
	/**
	 * Typ danych jako wiadomoœæ od klienta do klienta.
	 */
	MESSAGE,
	/**
	 *  Typ danych jako zg³oszenie zalogowania u¿ytkownika, który ju¿ jest zarejestrowany w systemie.
	 */
	LOG,
	/**
	 * Typ danych wskazuj¹cych na wprowadzenie b³êdnych danych podczas logowania u¿ytkownika.
	 */
	WRONG
}
