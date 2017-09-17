package communicator;

/** Oznaczenia typów danych przesyłanych przez użytkowników i serwer.
 * @author Piotrek
 * @see ClientMain
 * @see ServerMain
 * @see Uzytkownik
 * @see Znajomy
 * @see Dane
 */
public enum TypDanych {

	/**
	 *  Typ danych jako zgłoszenie nowego użytkownika do rejestracji.
	 */
	REGISTER,
	/**
	 * Typ danych jako wiadomość od klienta do klienta.
	 */
	MESSAGE,
	/**
	 *  Typ danych jako zgłoszenie zalogowania użytkownika, który już jest zarejestrowany w systemie.
	 */
	LOG,
	/**
	 * Typ danych wskazujących na wprowadzenie błędnych danych podczas logowania użytkownika.
	 */
	WRONG
}
