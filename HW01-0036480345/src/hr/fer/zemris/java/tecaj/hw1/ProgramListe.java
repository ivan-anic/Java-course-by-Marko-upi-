package hr.fer.zemris.java.tecaj.hw1;

/**
 * A program which executes various operations on a linked array.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class ProgramListe {
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * the main method, used for testing
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");

		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);

		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);

		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);

		return;
	}

	/**
	 * prints out the array size; number of elements
	 * 
	 * @param cvor
	 *            - the array head
	 * @return - number of elements
	 */
	private static int velicinaListe(CvorListe cvor) {
		int size = 0;
		while (cvor != null) {
			size++;
			cvor = (cvor.sljedeci);
		}
		return size;
	}

	/**
	 * adds a node to the end of the list.
	 * 
	 * @param prvi
	 *            - the first node of the list
	 * @param podatak
	 *            - the data to be stored
	 * @return - the (new) first node of the list
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe pom, novi = new CvorListe();
		novi.podatak = podatak;
		novi.sljedeci = null;
		pom = prvi;
		if (prvi == null) {
			return novi;
		}
		while (pom.sljedeci != null) {
			pom = pom.sljedeci;
		}
		pom.sljedeci = novi;
		return prvi;
	}

	/**
	 * prints out the list elements in order
	 * 
	 * @param cvor
	 *            - the array head
	 */
	private static void ispisiListu(CvorListe cvor) {
		if (cvor == null)
			System.out.println("Lista je prazna.");
		do {
			System.out.println(cvor.podatak + ", ");
			cvor = cvor.sljedeci;
		} while (cvor != null);
	}

	/**
	 * sorts the list using the insertion sort algorithm
	 * 
	 * @param cvor
	 *            - the array head
	 * @return - the new sorted array head
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if (cvor == null || cvor.sljedeci == null)
			return cvor;

		CvorListe novi = new CvorListe();
		novi.podatak = cvor.podatak;
		CvorListe pointer = cvor.sljedeci;

		while (pointer != null) {

			CvorListe sred = novi;
			CvorListe sljedeci = pointer.sljedeci;

			if ((pointer.podatak.compareTo(novi.podatak)) <= 0) {
				CvorListe pom = novi;
				novi = pointer;
				novi.sljedeci = pom;
			} else {
				while (sred.sljedeci != null) {

					if (((pointer.podatak.compareTo(sred.podatak)) > 0)
							&& (pointer.podatak.compareTo(sred.sljedeci.podatak) <= 0)) {
						CvorListe stariSljed = sred.sljedeci;
						sred.sljedeci = pointer;
						pointer.sljedeci = stariSljed;
					}
					sred = sred.sljedeci;
				}
				if (sred.sljedeci == null && (pointer.podatak.compareTo(sred.podatak) > 0)) {
					sred.sljedeci = pointer;
					pointer.sljedeci = null;
				}
			}
			pointer = sljedeci;
		}
		return novi;
	}
}
