package es.ucm.fdi.business.searchengine;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

import es.ucm.fdi.business.data.FilterPOJO;
import es.ucm.fdi.integration.data.TagPOJO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;
import es.ucm.fdi.business.searchengine.FilterMapper;
import es.ucm.fdi.business.searchengine.SearchEngineSAImp;
import es.ucm.fdi.business.util.ElementHelper;;


public class SearchEngineSAImpTest {
	private static ClubDAOImp cd;
	private static SearchEngineSAImp searchMotor;
	private static UserPOJO user;
	
	static
	{
		/* We set the allowed tags we are going to use*/
		
		TagPOJO pop  = new TagPOJO("pop");
		TagPOJO rock = new TagPOJO("rock");
		TagPOJO edm  = new TagPOJO("edm");
		TagPOJO rgt  = new TagPOJO("rgt");

		/* Initialize the DAO whith all the clubs */
		cd = new ClubDAOImp();

		//Club1
		Set<TagPOJO> l1 = new HashSet<TagPOJO>(); 	
		l1.add(pop);
		l1.add(rock); 
		l1.add(edm); 
		l1.add(rgt);
		
		ClubPOJO c1 = new ClubPOJO("Club1", "Teatro Kapital", "C/Falsa 1", 20.00F, l1);
		c1.setRating(9.5F);
		c1.setLatitude(40.40977609999999);
		c1.setLongitude(-3.6931690999999773);
		//Distance from maths faculty: 7km
		
		//Club2
		Set<TagPOJO> l2 = new HashSet<TagPOJO>(); // Club2's tags (empty)
		l2.add(rgt);
		l2.add(pop);
		
		ClubPOJO c2 = new ClubPOJO("Club2", "Mitty", "C/Falsa 2", 15.00F, l2);
		c2.setRating(8.37F);
		c2.setLatitude(40.4427815);
		c2.setLongitude(-3.7133863000000247);
		//Distance from maths faculty: 2km
		
		//Club3
		Set<TagPOJO> l3 = new HashSet<TagPOJO>();
		l3.add(rgt);
		l3.add(pop);
		
		ClubPOJO c3 = new ClubPOJO("Club3", "Pachá", "C/Falsa 3", 17.00F, l3);
		c3.setRating(7.4F);
		c3.setLatitude(40.42692900000001);
		c3.setLongitude(-3.69982200000004);
		//Distance from maths faculty: 4,6km
		
		//Club4
		Set<TagPOJO> l4 = new HashSet<TagPOJO>();
		l4.add(edm);
		l4.add(rgt);
		l4.add(pop);
		
		ClubPOJO c4 = new ClubPOJO("Club4", "Fabrik", "C/Falsa 4", 10.00F, l4);
		c4.setRating(6.4F);
		c4.setLatitude(40.265579);
		c4.setLongitude(-3.840562099999943);
		//Distance from maths faculty: 27,4km
		
		//Add the clubs to the system
		cd.addClub(c1);
		cd.addClub(c2);
		cd.addClub(c3);
		cd.addClub(c4);
		
		/* Initialize the searchMotor, responsible of searching through the clubs */
		searchMotor = new SearchEngineSAImp(cd);
		
		/* Initialize the user who is going to search whith concrete preferences */
		user = readyToSearchUser("id", "frblazqu", "Francis");
	}
	
	/**
	 * @param id New user's id
	 * @param user New user's application name
	 * @param name New user's name
	 * 
	 * @return An user ready to be used for searching clubs.
	 */
	private static UserPOJO readyToSearchUser(String id, String user, String name) 
	{
		LocalDate birthday = LocalDate.of(2018, Month.JANUARY, 1);
		UserPOJO usr = new UserPOJO(id, user, "password", "email@mail.com", name, birthday);
		
		usr.setPreferencesList(cd.getClubs());

		return usr;
	}

	
	/**
	 * Useful for checking search result.
	 * 
	 * @return true Iff element with the id is visible in searchResult list
	 * @throws InvalidParameterException Iff there is no element whith that id in
	 * searchResult list.
	 */
	private boolean isVisibleElemWithId(String id, List<ElementHelper<ClubPOJO>> searchResult)
	{
		for(ElementHelper<ClubPOJO> elem: searchResult)
		{
			if(elem.getElement().getID().equals(id))
				return elem.isVisible();
		}
		
		//There is no element whith that id in the search result
		throw new InvalidParameterException("There is no element whith id " + id + " in the search result.");
	}
	
	
	/**
	 * TEST 1: Search without filters.
	 */
	@Test
	public void noFilterSearchTest() 
	{
		//Search filters (empty)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);
		
		//We check if the result is what we expected!
		assertTrue("No filterSearch failed", searchResult.size()== 4);
		
		for(int i = 1; i <= 4; i++)
			assertTrue("No filterSearch failed", searchResult.get(i-1).isVisible());
	}
	
	/**
	 * TEST 2: One filter search. (price)
	 */
	@Test
	public void oneFilterTest1()
	{
		//Search filters (price filter)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		
		List<String> filterParams = new ArrayList<String>(); 
		filterParams.add("15");
		FilterPOJO priceFilter = new FilterPOJO(FilterMapper.PRICE_FILTER, filterParams);
		
		FilterMapper.addAll();
		filters.add(priceFilter);
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);

		//We check if the result is what we expected!
		assertTrue("One filter search failed", searchResult.size()== 4);
		assertTrue("One filter search failed", isVisibleElemWithId("Club2",searchResult));
		assertTrue("One filter search failed", isVisibleElemWithId("Club4",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club1",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club3",searchResult));
	}
	
	/**
	 * TEST 3: One filter search. (tags)
	 */
	@Test
	public void oneFilterTest2()
	{
		//Search filters (tag filter)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		
		List<String> filterParams = new ArrayList<String>(); 
		filterParams.add("pop"); //Discotecas de música pop
		filterParams.add("rgt"); //Discotecas de música regeton
		filterParams.add("edm"); //Discotecas de electronic dance music
		FilterPOJO priceFilter = new FilterPOJO(FilterMapper.TAG_FILTER, filterParams);
		
		FilterMapper.addAll();
		filters.add(priceFilter);
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);

		//We check if the result is what we expected!
		assertTrue("One filter search failed", searchResult.size()== 4);
		assertTrue("One filter search failed", isVisibleElemWithId("Club1",searchResult));
		assertTrue("One filter search failed", isVisibleElemWithId("Club4",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club3",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club2",searchResult));
	}
	
	/**
	 * TEST 3: Two filter search. (price and rating)
	 */
	@Test
	public void twoFilterTest1()
	{
		//Search filters (price filter)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		List<String> filter1Params = new ArrayList<String>(); 
		List<String> filter2Params = new ArrayList<String>(); 
		
		filter1Params.add("19.35");
		FilterPOJO priceFilter = new FilterPOJO(FilterMapper.PRICE_FILTER, filter1Params);
		
		filter2Params.add("7.5");
		FilterPOJO ratingFilter = new FilterPOJO(FilterMapper.RATING_FILTER, filter2Params);
		
		FilterMapper.addAll();
		filters.add(priceFilter);
		filters.add(ratingFilter);
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);

		//We check if the result is what we expected!
		assertTrue("One filter search failed", searchResult.size()== 4);
		assertTrue("One filter search failed", isVisibleElemWithId("Club2",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club4",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club3",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club1",searchResult));
	}
	
	/**
	 * TEST 4: Two filter search. (tags and location)
	 * 
	 * Checks which of the clubs that were initialized upper are nearer than 7km from the
	 * Universidad Complutense's Mathematics faculty and plays pop and reggeton music.
	 * 
	 * @Test commented to avoid abusive use of the api key.
	 */
	//@Test
	public void twoFilterTest2()
	{
		//Search filters (tag filter)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		
		List<String> filter1Params = new ArrayList<String>(); 
		filter1Params.add("pop"); //Pop music clubs
		filter1Params.add("rgt"); //Electronic dance music clubs
		FilterPOJO tagFilter = new FilterPOJO(FilterMapper.TAG_FILTER, filter1Params);
		
		List<String> filter2Params = new ArrayList<String>();
		filter2Params.add("7");						//Maximum distance 7km
		filter2Params.add("40.4494588");			//Maths faculty latitude
		filter2Params.add("-3.725856799999974");	//Maths faculty longitude
		FilterPOJO locationFilter = new FilterPOJO(FilterMapper.LOCATION_FILTER, filter2Params);
		
		FilterMapper.addAll();
		filters.add(tagFilter);
		filters.add(locationFilter);
		
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);

		//We check if the result is what we expected!
		assertTrue("One filter search failed", searchResult.size()== 4);
		assertTrue("One filter search failed", isVisibleElemWithId("Club2",searchResult));
		assertTrue("One filter search failed", isVisibleElemWithId("Club3",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club4",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club1",searchResult));
	}
	
	/**
	 * TEST 5: All filter search.
	 * 
	 * @Test commented to avoid abusive use of the api key.
	 */
	//@Test
	public void allFilterTest2()
	{
		//Search filters (tag filter)
		List<FilterPOJO> filters = new ArrayList<FilterPOJO>();
		
		List<String> filter1Params = new ArrayList<String>(); 
		filter1Params.add("pop"); //Pop music clubs
		filter1Params.add("rgt"); //Electronic dance music clubs
		FilterPOJO tagFilter = new FilterPOJO(FilterMapper.TAG_FILTER, filter1Params);
		//All clubs verifies this two tags
		
		List<String> filter2Params = new ArrayList<String>(); 
		filter2Params.add("7"); //Higher rating than 7 points
		FilterPOJO ratingFilter = new FilterPOJO(FilterMapper.RATING_FILTER, filter2Params);
		//Fabrik doesn't verifies this condition
		
		List<String> filter3Params = new ArrayList<String>(); 
		filter3Params.add("19"); //Lower price than 19€
		FilterPOJO priceFilter = new FilterPOJO(FilterMapper.PRICE_FILTER, filter3Params);
		//Kapital is excluded too
		
		List<String> filter4Params = new ArrayList<String>();
		filter4Params.add("5");						//Maximum distance 5km
		filter4Params.add("40.4494588");			//Maths faculty latitude
		filter4Params.add("-3.725856799999974");	//Maths faculty longitude
		FilterPOJO locationFilter = new FilterPOJO(FilterMapper.LOCATION_FILTER, filter4Params);
		//Pachá is excluded, so Mitty is the only one that verifies all the filters
		
		FilterMapper.addAll();
		filters.add(tagFilter);
		filters.add(ratingFilter);
		filters.add(priceFilter);
		filters.add(locationFilter);
		
		
		//Search result (visible clubs are which verifies all conditions)
		List<ElementHelper<ClubPOJO>> searchResult = searchMotor.search("", filters, user);

		//We check if the result is what we expected!
		assertTrue("One filter search failed", searchResult.size()== 4);
		assertTrue("One filter search failed", isVisibleElemWithId("Club2",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club3",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club4",searchResult));
		assertFalse("One filter search failed", isVisibleElemWithId("Club1",searchResult));
	}
}
