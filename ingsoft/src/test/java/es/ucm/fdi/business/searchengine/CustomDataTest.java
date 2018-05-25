package es.ucm.fdi.business.searchengine;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import es.ucm.fdi.integration.data.TagPOJO;
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
	public void assignValuesTest(){
	UserPOJO uPOJO = new UserPOJO("Pepe", "pepe1", "constrasena", "pepe@gamil.com", "Pepe Dominguez", null);
		
		
		Map<TagPOJO, Integer> valueTags = new HashMap<TagPOJO, Integer>();
		valueTags.put(new TagPOJO("barato"), 10);
		valueTags.put(new TagPOJO("buena musica"), 20);
		valueTags.put(new TagPOJO("marchoso"), 35);
		valueTags.put(new TagPOJO("divertido"), 5);
		
		Set<TagPOJO> tags1 = new HashSet<TagPOJO>();		
		
		Set<TagPOJO> tags2 = new HashSet<TagPOJO>();
		tags2.add(new TagPOJO("barato"));
		tags2.add(new TagPOJO("buena musica"));
		tags2.add(new TagPOJO("marchoso"));
		tags2.add(new TagPOJO("divertido"));
		
		
		// Deberia tener 0 puntos con el mapa establecido
		ClubPOJO cPOJO1 = new ClubPOJO("Disco 1", "PachaDisco"," C/ Falsa 123", 7.98F, tags1);
		
		// Deberia tener 70 puntos con el mapa establecido
		ClubPOJO cPOJO2 = new ClubPOJO("Disco 2", "MittyDisco"," C/ Falsa 124", 10.98F, tags2);
		
		
		uPOJO.setValueTags(valueTags);
		
		UserDAO uDAO = new UserDAOImp();
		uDAO.addUser(uPOJO);
		
		uPOJO.setValueTags(valueTags);
		
		ClubDAO cDAO = new ClubDAOImp();
		cDAO.addClub(cPOJO1);
		cDAO.addClub(cPOJO2);
		
		CustomDataSA cd = new CustomDataSAImp(uDAO, cDAO);
		
		int valor1 = cd.assignValue(uPOJO, cPOJO1);
		assertEquals("Expected 0 points but we have " + valor1, 0, valor1);
		
		int valor2 = cd.assignValue(uPOJO, cPOJO2);
		assertEquals("Expected 70 points but we have " + valor2, 70, valor2);
		
	}
	
	
	@Test
	public void updateValuesTest() {
		UserPOJO uPOJO = new UserPOJO("Pepe", "pepe1", "constrasena", "pepe@gamil.com", "Pepe Dominguez", null);
		
		
		Map<TagPOJO, Integer> valueTags = new HashMap<TagPOJO, Integer>();
		valueTags.put(new TagPOJO("barato"), 10);
		valueTags.put(new TagPOJO("buena musica"), 20);
		valueTags.put(new TagPOJO("marchoso"), 35);
		valueTags.put(new TagPOJO("divertido"), 5);
		
		Set<TagPOJO> tags1 = new HashSet<TagPOJO>();
		tags1.add(new TagPOJO("marchoso"));
		tags1.add(new TagPOJO("buena musica"));
		
		
		Set<TagPOJO> tags2 = new HashSet<TagPOJO>();
		tags2.add(new TagPOJO("barato"));
		tags2.add(new TagPOJO("buena musica"));
		tags2.add(new TagPOJO("marchoso"));
		tags2.add(new TagPOJO("divertido"));
		
		Set<TagPOJO> tags3 = new HashSet<TagPOJO>();
		tags3.add(new TagPOJO("divertido"));
		tags3.add(new TagPOJO("barato"));
		tags3.add(new TagPOJO("marchoso"));
		
		// Must have 55 with this map
		ClubPOJO cPOJO1 = new ClubPOJO("Pacha", "PachaDisco","C/ Falsa 123", 7.98F, tags1);
		
		// Must have 70 with this map
		ClubPOJO cPOJO2 = new ClubPOJO("Mitty", "MittyDisco","C/ Falsa 124", 10.98F, tags2);
		
		// Must have 50 with this map
		ClubPOJO cPOJO3 = new ClubPOJO("Kapital", "Kapi","C/ Falsa 125", 15.98F, tags3);
		
		UserDAO uDAO = new UserDAOImp();
		uPOJO.setValueTags(valueTags);
		uDAO.addUser(uPOJO);
		
		ClubDAO cDAO = new ClubDAOImp();
		cDAO.addClub(cPOJO1);
		cDAO.addClub(cPOJO2);
		cDAO.addClub(cPOJO3);
		
		CustomDataSA cd = new CustomDataSAImp(uDAO, cDAO);
		cd.updateValues();
		uPOJO = uDAO.getUser(uPOJO.getID());
		
		List <ClubPOJO> expPreferences = new ArrayList<ClubPOJO>();
		expPreferences.add(cPOJO2);
		expPreferences.add(cPOJO1);
		expPreferences.add(cPOJO3);

		assertEquals(expPreferences, uPOJO.getPreferencesList());
		
		// Now we want to know what appends if there are two clubs with the same tags valoration
		// This disco has the same punctuation than cPOJO1
		ClubPOJO cPOJO4 = new ClubPOJO("Coco Loco", "CocoLoco"," C/ Falsa 125", 8.14F, tags1);
		cDAO.addClub(cPOJO4);
		
		expPreferences.add(1, cPOJO4); // We want opposite entry order if has the same points
		cd.updateValues();
		uPOJO = uDAO.getUser(uPOJO.getID());
		
		assertEquals(expPreferences, uPOJO.getPreferencesList());
	}
}
