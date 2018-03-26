package es.ucm.fdi.integration;

import java.util.List;

import es.ucm.fdi.integration.data.ClubPOJO;

public interface ClubDAO {
	public List<ClubPOJO> getClubs();
	public ClubPOJO getClub(String id);
}
