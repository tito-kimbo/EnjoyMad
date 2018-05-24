package es.ucm.fdi.business.profilemanagement.tools;

import java.util.Set;

import es.ucm.fdi.business.tagmanagement.TagManagerSA;
import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.data.ClubPOJO;
import es.ucm.fdi.integration.data.TagPOJO;


/**
 * Enum class representing the attributes of a ClubPOJO.
 * 
 * @version 08.05.2018
 */
public enum ClubModifierHelper {

	COMMERCIAL_NAME("COMMERCIAL_NAME") {
		@Override
		public void parse(ClubPOJO clubToManage, TagManagerSA tagManager, 
				ClubPOJO clubChanges) throws IllegalArgumentException {
			
			if (clubChanges.getCommercialName() != null) {
				// Valid?
				String newCommercialName = clubChanges.getCommercialName();

				if (!ParsingToolHelper.parseCommercialName(newCommercialName)) {
					throw new IllegalArgumentException("In COMMERCIAL NAME modification: "
							+ "not a valid commercial name format -> " + newCommercialName);
				}

			}
		}

		@Override
		public void modify(ClubPOJO clubToManage, ClubPOJO clubChanges) {
			// Modification.
			if (clubChanges.getCommercialName() != null) {
				clubToManage.setCommercialName(clubChanges.getCommercialName());
			}
		}
	},

	LOCATION("LOCATION") {
		@Override
		public void parse(ClubPOJO clubToManage, TagManagerSA tagManager, 
				ClubPOJO clubChanges) throws IllegalArgumentException {
			
			if (clubChanges.getLocation() != null) {
				// XXX ¿Qué es valido para la localización?
				// Location newLocation = clubChanges.getLocation();	
			}
		}

		@Override
		public void modify(ClubPOJO clubToManage, ClubPOJO clubChanges) {
			// Modification.
			if (clubChanges.getLocation() != null) {
				clubToManage.setLocation(clubChanges.getLocation());
			}
		}
	},

	ADDRESS("ADDRESS") {
		@Override
		public void parse(ClubPOJO clubToManage, TagManagerSA tagManager, 
				ClubPOJO clubChanges) throws IllegalArgumentException {

			if (clubChanges.getAddress() != null) {
				// Valid?
				String newAddress = clubChanges.getAddress();

				if (!ParsingToolHelper.parseAddress(newAddress)) {
					throw new IllegalArgumentException("In ADDRESS modification: " + 
						"not a valid address format -> " + newAddress);
				}
			}
		}

		@Override
		public void modify(ClubPOJO clubToManage, ClubPOJO clubChanges) {
			// Modification.
			if (clubChanges.getAddress() != null) {
				clubToManage.setAddress(clubChanges.getAddress());
			}
		}
	},

	PRICE("PRICE") {
		@Override
		public void parse(ClubPOJO clubToManage, TagManagerSA tagManager, 
				ClubPOJO clubChanges) throws IllegalArgumentException {

			if (new Float(clubChanges.getPrice()) != null) {
				// Valid?
				float newPrice = clubChanges.getPrice();

				if (!ParsingToolHelper.parsePrice(newPrice)) {
					throw new IllegalArgumentException("In PRICE modification: " + 
							"not a valid price -> " + newPrice);
				}
			}
		}

		@Override
		public void modify(ClubPOJO clubToManage, ClubPOJO clubChanges) {
			// Modification.
			if (new Float(clubChanges.getPrice()) != null) {
				clubToManage.setPrice(clubChanges.getPrice());
			}
		}
	},

	TAGS("TAGS") {
		@Override
		public void parse(ClubPOJO clubToManage, TagManagerSA tagManager, 
				ClubPOJO clubChanges) throws IllegalArgumentException {

			if (clubChanges.getTags() != null) {
				// Checking if is active tag.
				Set<TagPOJO> newTags = clubChanges.getTags();

				for (TagPOJO tp : newTags) {
					if (!tagManager.hasTag(tp)) {
						throw new IllegalArgumentException("In CLUB creation: " + 
								"tag not found in valid tags list -> " + tp.getTag());
					}
				}
			}
		}

		@Override
		public void modify(ClubPOJO clubToManage, ClubPOJO clubChanges) {
			// Modification.
			if (clubChanges.getTags() != null) {
				clubToManage.setTags(clubChanges.getTags());
			}			
		}
	};


	private String type;

	private ClubModifierHelper(String type) {
		this.type = type;
	}

	/**
	 * Returns the enum type in {@code String} form.
	 * 
	 * @return type as {@code String}
	 */
	@Override
	public String toString() {
		return type;
	}

	/**
	 * Parses if the data to be modified is valid. If it is not, it throws
	 * an exception, does nothing otherwise.
	 * 
	 * @param clubToManage - club to be modified
	 * @param tagManager   - manager for tags parsing
	 * @param clubChanges  - club new data
	 * 
	 * @throws IllegalArgumentException if {@code userChanges} parsing failed 
	 */
	public abstract void parse(ClubPOJO clubToManage, TagManagerSA tagManager,
			ClubPOJO clubChanges) throws IllegalArgumentException;

	/**
	 * Modifies a specific attribute of a club, asumming the new data is valid.
	 * 
	 * @param clubToManage - club to be modified
	 * @param clubChanges      - club new data
	 */
	public abstract void modify(ClubPOJO clubToManage, ClubPOJO clubChanges);
}