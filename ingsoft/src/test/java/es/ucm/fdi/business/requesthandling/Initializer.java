package es.ucm.fdi.business.requesthandling;

import java.util.List;

import es.ucm.fdi.business.ProductionConfig;
import es.ucm.fdi.integration.ClubDAO;
import es.ucm.fdi.integration.UserDAO;
import es.ucm.fdi.integration.data.TagPOJO;

public class Initializer extends ProductionConfig {

	public static void initialize() {
		// Without SQL
		ProductionConfig.init(false);
	}

	// We assume this list is valid
	public static void addTags(List<String> tags) {
		for (String s : tags) {
			ProductionConfig.getFrontController().getTagManagerSA()
					.newTag(new TagPOJO(s));
		}
	}

	public static UserDAO getUserDAO() {
		return ProductionConfig.getUserDAO();
	}

	public static ClubDAO getClubDAO() {
		return ProductionConfig.getClubDAO();
	}
}
