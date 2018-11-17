package edu.baylor.ecs.Assignment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tester {
	private ConcreteTableModel tab;
	private JTable table;

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

	public boolean isThisDateValid(String dateToValidate, String dateFromat){

		if(dateToValidate == null){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	//public boolean isGenderValid(String gender){
	//	if ()
	//}

	@BeforeEach
	void init() throws FileNotFoundException {
		tab = new ConcreteTableModel("input.csv");
		table = new JTable(tab);
	}
	
	@Test
	void testAddRowSuccess() {
		int total = tab.getRowCount() + 1;
		tab.addRow();
		assertEquals(tab.getRowCount(), total);
	}
	
	@Test
	void testDeleteRowSuccess() {
		int total = tab.getRowCount() - 1;
		tab.deleteRow(0);
		assertEquals(tab.getRowCount(), total);
	}
	
	@Test
	void testValidEmail() {
		String email = "bo_fu2@baylor.edu";
		assertTrue(validate(email));
	}
	
	@Test
	void testInValidEmail() {
		String email = "bo fu2@baylor.edu";
		assertFalse(validate(email));
	}
	
	@Test
	void testEditRowValid() {
		String name = "Bo Fu";
		String state = "TX";
		String email = "bo_fu2@baylor.edu";
		String birthday = "05/19/1997";
		String gender = "MALE";
		String income = "10000";
		assertTrue(isThisDateValid(birthday, "MM/dd/yyyy") && (gender.equalsIgnoreCase(Gender.maleToString()) || gender.equalsIgnoreCase(Gender.femaleToString())));
	}
	
	@Test
	void testEditRowInvalid() {
		String name = "Bo Fu";
		String state = "TX";
		String email = "bo_fu2@baylor.edu";
		String birthday = "null";
		String gender = "null";
		String income = "10000";
		assertFalse(isThisDateValid(birthday, "MM/dd/yyyy") && (gender.equalsIgnoreCase(Gender.maleToString()) || gender.equalsIgnoreCase(Gender.femaleToString())));
	}
	
	@Test
	void testDateValid() {	
		String birthday = "05/19/1997";
		assertTrue(isThisDateValid(birthday, "MM/dd/yyyy"));
	}
	
	@Test
	void testDateInvalid() {	
		String birthday = "null";
		assertFalse(isThisDateValid(birthday, "MM/dd/yyyy"));
	}
	
	@Test
	void testEnumValid() {
		String gender = "MALE";
		assertTrue((gender.equalsIgnoreCase(Gender.maleToString()) || gender.equalsIgnoreCase(Gender.femaleToString())));
	}
	
	@Test
	void testEnumInValid() {
		String gender = "null";
		assertFalse((gender.equalsIgnoreCase(Gender.maleToString()) || gender.equalsIgnoreCase(Gender.femaleToString())));
	}
}
