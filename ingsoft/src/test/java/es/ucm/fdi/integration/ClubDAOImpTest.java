package es.ucm.fdi.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;

/**
 * This class tests the functionality of ClubDAOImp.
 */

public class ClubDAOImpTest{
	private static ClubPOJO club;
	private static ClubDAO clubDao;
	private static ArrayList<ClubPOJO> list = new ArrayList<ClubPOJO>();
	
	private static void createTestClubDAOImp(){
		club = new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid", 
				17.0f,  new HashSet<TagPOJO>(Arrays.asList
						( new TagPOJO("Electronica")
						, new TagPOJO("Reggaeton")
						, new TagPOJO("Funky")
						, new TagPOJO("R&B"))));
		
		clubDao = new ClubDAOImp();
		
		list = new ArrayList<ClubPOJO>(Arrays.asList(club));
		
		clubDao.addClub(club);
	}
	
	
	@Test
	public void testExist(){
		createTestClubDAOImp();
		assertEquals(clubDao.exists("id"),true);
	}

	@Test
	public void testGetClub() {
		createTestClubDAOImp();
		assertEquals(clubDao.getClub("id"), club);
	}
	
	@Test
	public void testGetClubs() {
		createTestClubDAOImp();
		assertEquals(clubDao.getClubs(), list);
	}
	
	@Test
	public void testRemoveClub(){
		clubDao.removeClub("id");
		assertEquals(clubDao.exists("id"),false);
	}
}