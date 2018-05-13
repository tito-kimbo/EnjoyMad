package es.ucm.fdi.bussines.searchengine;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import es.ucm.fdi.business.searchengine.CustomDataSA;
import es.ucm.fdi.business.searchengine.CustomDataSAImp;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class CustomDataTest {
	@Test
	public void updateValuesTest() {
		UserPOJO uPOJO = new UserPOJO("Pepe", "pepe1", "constrasena", "pepe@gamil.com", "Pepe Dominguez", null);
		
		
		Map<String, Integer> valueTags = new HashMap<String, Integer>();
		valueTags.put("barato", 10);
		valueTags.put("buena musica", 20);
		valueTags.put("marchoso", 35);
		valueTags.put("divertido", 5);
		
		Set<String> tags1 = new HashSet<String>();
		tags1.add("marchoso");
		tags1.add("buena musica");
		
		
		Set<String> tags2 = new HashSet<String>();
		tags2.add("barato");
		tags2.add("buena musica");
		tags2.add("marchoso");
		tags2.add("divertido");
		
		Set<String> tags3 = new HashSet<String>();
		tags3.add("divertido");
		tags3.add("barato");
		tags3.add("marchoso");
		
		// Deberia tener 55 puntos con el mapa establecido
		ClubPOJO cPOJO1 = new ClubPOJO("Pacha", "Pacha Disco"," C/ Falsa 123", null, 7.98F, tags1);
		
		// Deberia tener 70 puntos con el mapa establecido
		ClubPOJO cPOJO2 = new ClubPOJO("Mitty", "Mitty Disco"," C/ Falsa 124", null, 10.98F, tags2);
		
		// Deberia tener 50 puntos con el mapa establecido
		ClubPOJO cPOJO3 = new ClubPOJO("Kapital", "Teatro Kapi"," C/ Falsa 125", null, 15.98F, tags3);
		
		UserDAO uDAO = new UserDAOImp();
		uDAO.addUser(uPOJO);
		
		uPOJO.setValueTags(valueTags);
		
		ClubDAO cDAO = new ClubDAOImp();
		cDAO.addClub(cPOJO1);
		cDAO.addClub(cPOJO2);
		cDAO.addClub(cPOJO3);
		
		CustomDataSA cd = new CustomDataSAImp(uDAO, cDAO);
		cd.updateValues();
		
		List <ClubPOJO> expPreferences = new ArrayList<ClubPOJO>();
		expPreferences.add(cPOJO2);
		expPreferences.add(cPOJO1);
		expPreferences.add(cPOJO3);
		
		assertEquals(expPreferences, uPOJO.getPreferencesList());
		
		
	}
}
