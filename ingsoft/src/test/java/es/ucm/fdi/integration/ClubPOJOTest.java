package es.ucm.fdi.integration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.ReviewPOJO;

public class ClubPOJOTest {
	
	private static ClubPOJO createTestClubPOJO(){
		return new ClubPOJO("id", "Kapital", "Calle Atocha, 125, 28012 Madrid", 17.0f, 
				new HashSet<TagPOJO>(Arrays.asList(
									new TagPOJO("Electronica")
									,new TagPOJO("Reggaeton")
									,new TagPOJO("Funky")
									,new TagPOJO("R&B"))));
	}
	
	private static void addOpinions(ClubPOJO c){
		ReviewPOJO op = new ReviewPOJO("", 4.0f);
		for(int i = 0; i < 8; ++i){
			c.addUserReview("anotherUser" + i, op);
		}
	}
	
	private static void removeOpinions(ClubPOJO c){
		for(int i = 0; i < 8; ++i){
			c.removeUserReview("anotherUser" + i);
		}
	}
	
	private boolean anyMatch(Collection<String> c, String s){
		for(String str : c){
			if(str.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void ratingCalculationAndUpdateTest(){
		ClubPOJO testClub = createTestClubPOJO();
		ReviewPOJO op1 = new ReviewPOJO("", 3.0f), op2 = new ReviewPOJO("", 4.0f);
		
		//No delta error margin here
		assertEquals("Club rating not properly initialized.", 0, testClub.getRating(), 0);
		testClub.addUserReview("user1", op1);
		assertEquals("Club rating not properly updated adding one opinion.", 3.0f, 
				testClub.getRating(), 0);
		testClub.addUserReview("user2", op2);
		assertEquals("Club rating not properly updated adding two opinions.", 3.5f, 
				testClub.getRating(), 0.000001);
		
		//Using StringBuilder would slightly improve efficiency (but on 8 elements it is somewhat
		//irrelevant).
		addOpinions(testClub);
		assertEquals("Club rating not properly updated adding multiple opinions.", 3.9f, 
				testClub.getRating(), 0.000001);
		
		
		//Now removal
		removeOpinions(testClub);
		assertEquals("Club rating not properly updated removing multiple opinions.", 3.5f, 
				testClub.getRating(), 0.000001);
		testClub.removeUserReview("user1");
		testClub.removeUserReview("user2");
		assertEquals("Club rating not properly updated when removing all opinions.", 0f,
				testClub.getRating(), 0);
		
	}
	
	@Test
	public void opinionAndReviewersTest(){
		ClubPOJO testClub = createTestClubPOJO();
		Collection<String> col = testClub.getReviewers();
				
		
		assertEquals("Club reviewers not properly initialized", 0, col.size());
		addOpinions(testClub);
		col = testClub.getReviewers();
		assertEquals("Club reviewers not properly initialized", 8, col.size());
		
		for(int i = 0; i<8; ++i){
			assertTrue("Club reviewer not added.", anyMatch(col, "anotherUser" + i));
		}
		removeOpinions(testClub);
		col = testClub.getReviewers();
		assertEquals("Club reviewers not properly removed", 0, col.size());
	}
	
}
