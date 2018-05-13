package es.ucm.fdi.business.searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.ucm.fdi.business.data.TagPOJO;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.ClubDAOImp;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.UserDAOImp;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public class CustomDataSAImp implements CustomDataSA{
	
	private UserDAO user;
	private ClubDAO club;
	
	private class ObjectValue implements Comparable<ObjectValue>{
		private ClubPOJO club;
		private Integer value;
		
		public ObjectValue(ClubPOJO club, Integer value) {
			this.club = club;
			this.value = value;
		}

		public int compareTo(ObjectValue ov) {
			if(value > ov.value) return -1;
			else if(value == ov.value) return 0;
			else return 1;
		}
		
	}
	

	public CustomDataSAImp(UserDAO user, ClubDAO club) {
		this.user = user;
		this.club = club;
	}


	public void updateValues() {
		List<ObjectValue> clubsWithValue = new ArrayList<ObjectValue>();
		int clubValue;
		for (UserPOJO u: user.getUsers()){
			for (ClubPOJO c: club.getClubs()){
				clubValue = assignValue(u, c);
				clubsWithValue.add( new ObjectValue(c, clubValue));
			}
			Collections.sort(clubsWithValue);
			List<ClubPOJO> orderedListOfClubs = new ArrayList<ClubPOJO>();
			for (ObjectValue ob: clubsWithValue){
				orderedListOfClubs.add(ob.club);
			}
			u.setPreferencesList(orderedListOfClubs);
			
		}
	}
	

	public int assignValue(UserPOJO user, ClubPOJO club) {
		int valueOfClub = 0;
		for (TagPOJO tp: club.getTags()){
			valueOfClub += user.getValueTags().get(tp.getTag());
		}
		return valueOfClub;
	}

}
