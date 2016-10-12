
package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.Record;

import hr.fer.zemris.java.hw15.model.BlogEntry;

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
public class BlogEntryForm {

	/**
	 * String verzija identifikatora.
	 */
	private String id;

	/** The title. */
	private String title;

	/** The text. */
	private String text;

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
	public BlogEntryForm() {
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
		this.id = pripremi(req.getParameter("id"));
		this.title = pripremi(req.getParameter("title"));
		this.text = pripremi(req.getParameter("text"));
	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r
	 *            objekt koji čuva originalne podatke
	 */
	public void popuniIzRecorda(BlogEntry r) {
		if (r.getId() == null) {
			this.id = "";
		} else {
			this.id = r.getId().toString();
		}

		this.title = r.getTitle();
		this.text = r.getText();

	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r
	 *            domenski objekt koji treba napuniti
	 */
	public void popuniURecord(BlogEntry r) {
		r.setTitle(this.title);
		r.setText(this.text);
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka
	 * te po potrebi registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();

		if (!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch (NumberFormatException ex) {
				greske.put("id",
						"Vrijednost identifikatora nije valjana.");
			}
		}

		if (this.title.isEmpty()) {
			greske.put("title", "Name is mandatory!");
		}

		if (this.text.isEmpty()) {
			greske.put("lastName", "Text is mandatory!");
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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}
}
