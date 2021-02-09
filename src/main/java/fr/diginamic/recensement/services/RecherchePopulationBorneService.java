package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.NotANumberException;
import fr.diginamic.recensement.exceptions.NumberNegatifException;
import fr.diginamic.recensement.exceptions.UnknownArgException;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws NotANumberException, UnknownArgException, NumberNegatifException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();
		
		List<Ville> villes = rec.getVilles();
		boolean bool = false;
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				bool = !bool;
			}
		}
		if (!bool) {
	        throw new UnknownArgException("Aucune département ne correspond à votre recherche");
		}

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();

		if ( !NumberUtils.isCreatable(saisieMin) || saisieMin.contains(".") ) {
	        throw new NotANumberException("Saisir des chiffres pas des lettres");
	    } else if (Integer.parseInt(saisieMin) < 0 ) {
	        throw new NumberNegatifException("Saisir un nombre positif");
	    }
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();

		if ( !NumberUtils.isCreatable(saisieMax) || saisieMax.contains(".") ) {
	        throw new NotANumberException("Saisir des chiffres pas des lettres");
	    } else if (Integer.parseInt(saisieMax) < 0 ) {
	        throw new NumberNegatifException("Saisir un nombre positif");
	    }

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		if (min > max) {
	        throw new NumberNegatifException("Le résultat est négatif");
		}

		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}

}
