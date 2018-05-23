package es.ucm.fdi.business.profilemanagement.tools;

import java.time.LocalDate;

import es.ucm.fdi.business.util.ParsingToolHelper;
import es.ucm.fdi.integration.data.UserPOJO;

/**
 * Enum class representing the attributes of a UserPOJO.
 * 
 * @version 08.05.2018
 */
public enum UserModifierHelper {
	USERNAME("USERNAME") {
		@Override
		public void parse(UserPOJO userToManage, UserPOJO userChanges)
				throws IllegalArgumentException {

			if (userChanges.getUsername() != null) {
				// Valid?
				String newUsername = userChanges.getUsername();

				if (!ParsingToolHelper.parseUsername(newUsername)) {
					throw new IllegalArgumentException("In USERNAME modification: "
							+ "not a valid username format -> " + newUsername);
				}
			}
		}

		@Override
		public void modify(UserPOJO userToManage, UserPOJO userChanges) {
			// Modification.
			if (userChanges.getUsername() != null) {
				userToManage.setUsername(userChanges.getUsername());
			}
		}
	},

	PASSWORD("PASSWORD") {
		@Override
		public void parse(UserPOJO userToManage, UserPOJO userChanges)
				throws IllegalArgumentException {

			if (userChanges.getHashedPassword() != null) {
				// Always valid
			}
		}

		@Override
		public void modify(UserPOJO userToManage, UserPOJO userChanges) {
			// Modification.
			if (userChanges.getHashedPassword() != null) {
				userToManage.setHashedPassword(userChanges.getHashedPassword());
			}
		}
	},

	EMAIL("EMAIL") {
		@Override
		public void parse(UserPOJO userToManage, UserPOJO userChanges)
				throws IllegalArgumentException {

			if (userChanges.getEmail() != null) {
				// Valid?
				String newEmail = userChanges.getEmail();

				if (!ParsingToolHelper.parseEmail(newEmail)) {
					throw new IllegalArgumentException("In EMAIL modification: "
							+ "not a valid email format -> " + newEmail);
				}
			}
		}

		@Override
		public void modify(UserPOJO userToManage, UserPOJO userChanges) {
			// Modification.
			if (userChanges.getEmail() != null) {
				userToManage.setEmail(userChanges.getEmail());
			}
		}
	},

	NAME("NAME") {
		@Override
		public void parse(UserPOJO userToManage, UserPOJO userChanges)
				throws IllegalArgumentException {

			if (userChanges.getName() != null) {
				// Valid?
				String newName = userChanges.getName();

				if (!ParsingToolHelper.parseName(newName)) {
					throw new IllegalArgumentException("In NAME modification: "
							+ "not a valid name format -> " + newName);
				}
			}
		}

		@Override
		public void modify(UserPOJO userToManage, UserPOJO userChanges) {
			// Modification.
			if (userChanges.getName() != null) {
				userToManage.setName(userChanges.getName());
			}
		}
	},

	BIRTHDAY("BIRHTDAY") {
		@Override
		public void parse(UserPOJO userToManage, UserPOJO userChanges)
				throws IllegalArgumentException {

			if (userChanges.getBirthday() != null) {
				// Valid?
				LocalDate newBirthday = userChanges.getBirthday();

				if (!ParsingToolHelper.parseBirthday(newBirthday)) {
					throw new IllegalArgumentException("In NAME modification: "
							+ "not a valid name format -> " + newBirthday);
				}
			}
		}

		@Override
		public void modify(UserPOJO userToManage, UserPOJO userChanges) {
			// Modification.
			if (userChanges.getBirthday() != null) {
				userToManage.setBirthday(userChanges.getBirthday());
			}
		}
	};

	private String type;

	private UserModifierHelper(String type) {
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
	 * @param userToManage - user to be modified
	 * @param userChanges  - user new data
	 * 
	 * @throws IllegalArgumentException if {@code userChanges} parsing failed 
	 */
	public abstract void parse(UserPOJO userToManage, UserPOJO userChanges) 
			throws IllegalArgumentException;

	/**
	 * Modifies a specific attribute of a user, asumming the new data is valid.
	 * 
	 * @param userToManage - user to be modified
	 * @param userChanges  - user new data
	 */
	public abstract void modify(UserPOJO userToManage, UserPOJO userChanges);
}