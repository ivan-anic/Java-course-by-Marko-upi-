package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.Record;

import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * <p>
 * Model formulara koji odgovara web-reprezentaciji domenskog objekta
 * {@link Record}. U domenskom modelu, različita svojstva su različitih tipova;
 * primjerice, {@link Record#getId()} je tipa {@link Long}. U formularu, sva su
 * svojstva stringovi jer se tako prenose preko HTTP-a i čuvaju u web-stranici u
 * formularima.
 * </p>
 * 
 * <p>
 * Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji
 * (metoda {@link #validiraj()}) upiše je li došlo do pogreške u podatcima.
 * Formular nudi sljedeće funkcionalnosti.
 * </p>
 * 
 * <ol>
 * <li>Punjenje iz trenutnog zahtjeva metodom
 * {@link #popuniIzHttpRequesta(HttpServletRequest)}. Čita parametre i upisuje
 * odgovarajuća svojstva u formular.</li>
 * <li>Punjenje iz domenskog objekta metodom {@link #popuniIzRecorda(Record)}.
 * Prima {@link Record} kao argument i temeljem toga što je u njemu upisano
 * popunjava ovaj formular.</li>
 * <li>Punjenje domenskog objekta temeljem upisanog sadržaja u formularu metodom
 * {@link #popuniURecord(Record)}. Ideja je da se ovo radi tek ako su podatci u
 * formularu prošli validaciju. Pogledajte pojedine servlete koji su
 * pripremljeni uz ovaj primjer za demonstraciju kako se to radi.</li>
 * </ol>
 * 
 * @author marcupic
 */
/**
 * @author Ivan Anić
 * @version 1.0
 */
public class BlogUserForm {

	/**
	 * String verzija identifikatora.
	 */
	private String id;
	/**
	 * Prezime osobe.
	 */
	private String firstName;
	/**
	 * Ime osobe.
	 */
	private String lastName;
	/**
	 * E-mail osobe.
	 */
	private String email;

	/** The nick. */
	private String nick;

	/** The password hash. */
	private String passwordHash;

	/**
	 * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a
	 * vrijednosti tekstovi pogrešaka.
	 */
	Map<String, String> greske = new HashMap<>();

	/**
	 * Gets the greske.
	 *
	 * @return the greske
	 */
	public Map<String, String> getGreske() {
		return greske;
	}

	/**
	 * Konstruktor.
	 */
	public BlogUserForm() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime
	 *            naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema
	 *         pridruženu pogrešku
	 */
	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Dodaj gresku.
	 *
	 * @param key
	 *            the key
	 * @param message
	 *            the message
	 */
	public void dodajGresku(String key, String message) {
		greske.put(key, message);
		return;
	}

	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime
	 *            naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest}
	 * popunjava svojstva ovog formulara.
	 * 
	 * @param req
	 *            objekt s parametrima
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.id = pripremi(req.getParameter("ID"));
		this.lastName = pripremi(req.getParameter("firstName"));
		this.firstName = pripremi(req.getParameter("lastName"));
		this.email = pripremi(req.getParameter("email"));
		this.nick = pripremi(req.getParameter("nick"));
		this.passwordHash = pripremi(req.getParameter("password"));

	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r
	 *            objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(BlogUser r) {
		if (r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}

		this.lastName = r.getFirstName();
		this.firstName = r.getLastName();
		this.email = r.getEmail();
		this.nick = r.getNick();
		this.passwordHash = r.getPasswordHash();
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r
	 *            domenski objekt koji treba napuniti
	 */
	public void popuniURecord(BlogUser r) {
		if (this.id.isEmpty()) {
			r.setId(null);
		} else {
			r.setId(Long.valueOf(this.id));
		}

		r.setFirstName(this.lastName);
		r.setLastName(this.firstName);
		r.setEmail(this.email);
		r.setNick(this.nick);
		r.setPasswordHash(this.passwordHash);
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka
	 * te po potrebi registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();
		if (this.lastName.isEmpty()) {
			greske.put("firstName", "Ime je obavezno!");
		}

		if (this.firstName.isEmpty()) {
			greske.put("lastName", "Prezime je obavezno!");
		}

		if (this.nick.isEmpty()) {
			greske.put("nick", "Nick je obavezan!");
		}

		if (this.email.isEmpty()) {
			greske.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				greske.put("email", "EMail nije ispravnog formata.");
			}
		}

		if (this.nick.isEmpty()) {
			greske.put("password", "Pass je obavezan!");
		}

	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne
	 * stringove, što je puno pogodnije za uporabu na webu.
	 * 
	 * @param s
	 *            string
	 * @return primljeni string ako je različit od <code>null</code>, prazan
	 *         string inače.
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return passwordHash;
	}

	/**
	 * Sets the password.
	 *
	 * @param passwordHash
	 *            the new password
	 */
	public void setPassword(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the nick.
	 *
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick.
	 *
	 * @param nick
	 *            the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Dohvat id-a.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter za id.
	 * 
	 * @param id
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Dohvat prezimena.
	 * 
	 * @return prezime
	 */
	public String getLastName() {
		return firstName;
	}

	/**
	 * Setter za prezime.
	 * 
	 * @param prezime
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setLastName(String prezime) {
		this.firstName = prezime;
	}

	/**
	 * Dohvat imena.
	 * 
	 * @return ime
	 */
	public String getFirstName() {
		return lastName;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param ime
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setFirstName(String ime) {
		this.lastName = ime;
	}

	/**
	 * Dohvat emaila.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za email.
	 * 
	 * @param email
	 *            vrijednost na koju ga treba postaviti.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
