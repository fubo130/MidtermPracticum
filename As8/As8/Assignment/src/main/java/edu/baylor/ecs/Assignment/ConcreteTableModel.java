package edu.baylor.ecs.Assignment;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ConcreteTableModel extends AbstractTableModel {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private String fileName;
	private static final long serialVersionUID = 1L;
	private String[] write;
	private ArrayList<String[]> data = new ArrayList<String[]>();
	
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
	
	
	public ConcreteTableModel (String fileName) {
		Date day = java.util.Calendar.getInstance().getTime();
		System.out.println(day.toString());
		
		File file = new File(fileName);
		this.fileName = fileName;
		try {
			Scanner input = new Scanner(file);
			ArrayList<String> arr = new ArrayList<String>();
			String string = input.nextLine();
			int count = 0;
			boolean flag = true;
			while (input.hasNext()) {
				count += 1;
				string = input.nextLine();
				String[] str = string.split(",");
				String[] temp = new String[7];
				for (int i = 0; i < str.length; i++) {
					temp[i] = str[i];
				}
				String[] time = temp[3].split("/");
				if (validate(temp[2])) {
					flag = true;
				}
				else {
					flag = false;
					System.out.println(temp[2]);
				}
				if (count >= 2) {
				
					Date birthday = new Date();
					birthday.setMonth(Integer.valueOf(time[0]));
					birthday.setDate(Integer.valueOf(time[1]));
					birthday.setYear(Integer.valueOf(time[2]));
					
					Integer x = (day.getYear()-birthday.getYear());
					temp[6] = x.toString();
					//Calendar birthday = java.util.Calendar.getInstance();
					if (Integer.valueOf(temp[5]) > 25000 && (day.getYear()-birthday.getYear() < 35) && (day.getYear()-birthday.getYear()>18) && !temp[2].contains(" ") && flag == true) {
						data.add(data.size(), temp);
					}
					else {
						data.add(data.size(), temp);
						data.remove(data.get(data.size()-1));
					}
				}
				
				for (int i = 0; i < this.getRowCount(); i ++) {
					for (int j = i+1; j < this.getRowCount(); j++) {
						if (this.getValueAt(i, 2).equals(this.getValueAt(j, 2))) {
							this.deleteRow(i);
						}
					}
					
				}
				
			}
			input.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return data.get(0).length;
	}

	//@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	//@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data.get(row)[col];
	}
	
	public String getColumnName(int col) {
		String[] title = {"Name", "State", "Email", "Birthdate", "Gender", "Annual Income", "Age"};
		return title[col];
	}
	
	public boolean isCellEditable (int row, int col) {
		return false;
	}
	
	public void setValueAt(Object value, int row, int col) {
		data.get(row)[col] = value.toString();
	}

	/*
	public void fileSave() throws IOException {
		FileWriter writer = new FileWriter(fileName);
		for(int i = 0; i < this.getRowCount(); i++) {
			for(int j = 0; j < this.getColumnCount(); j++) {
				writer.write(data.get(i)[j]);
				System.out.print(data.get(i)[j]);
				if (j < data.get(0).length - 1) {
					writer.write(",");
				}
			}
			writer.write("\n");
		}
		writer.close();
	}*/
	
	public void deleteRow(int row) {
		data.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void addRow() {
		String[] temp = {"","","","","","","", ""};
		data.add(data.size(), temp);
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}
	
}
