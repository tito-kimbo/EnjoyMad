package es.ucm.fdi.business.SearchEngine;


import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.UserPOJO;

public interface CustomDataSA {
public void updateValues();
public int assignValues(UserPOJO user, ClubPOJO club);
}
