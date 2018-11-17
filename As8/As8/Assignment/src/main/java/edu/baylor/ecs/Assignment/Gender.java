package edu.baylor.ecs.Assignment;

public enum Gender {
	male("male"),
	female("female");
	
	public final String gender;
	Gender(String gender) {
		this.gender = gender;
	}
	public static String maleToString() {
		return Gender.male.name();
	}
	public static String femaleToString() {
		return Gender.female.name();
	}
}
