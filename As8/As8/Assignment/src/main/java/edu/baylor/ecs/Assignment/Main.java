package edu.baylor.ecs.Assignment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import edu.baylor.ecs.Assignment.TableFilterDemo.MyTableModel;

public class Main extends JPanel implements ActionListener  {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private ConcreteTableModel tab;
	private JTable table;
	private JComboBox filterBy;
	private JTextField filterText;
	private JFrame frame;
	private boolean tt = false;

	private boolean DEBUG = false;
	private JTextField statusText;
	private TableRowSorter<MyTableModel> sorter;

	public void TableFilter() {
		table.setRowSorter(sorter);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		//For the purposes of this example, better to have a single
		//selection.
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//When selection changes, provide user with row numbers for
		//both view and model.
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
							//Selection got filtered away.
							statusText.setText("");
						} else {
							int modelRow = 
									table.convertRowIndexToModel(viewRow);
							statusText.setText(
									String.format("Selected Row in view: %d. " +
											"Selected Row in model: %d.", 
											viewRow, modelRow));
						}
					}
				}
				);


		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane);

		//Create a separate form for filterText and statusText
		JPanel form = new JPanel(new SpringLayout());
		JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
		form.add(l1);
		filterText = new JTextField();
		//Whenever filterText changes, invoke newFilter.
		filterText.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						newFilter();
					}
					public void insertUpdate(DocumentEvent e) {
						newFilter();
					}
					public void removeUpdate(DocumentEvent e) {
						newFilter();
					}
				});
		l1.setLabelFor(filterText);
		form.add(filterText);
		JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
		form.add(l2);
		statusText = new JTextField();
		l2.setLabelFor(statusText);
		form.add(statusText);
		SpringUtilities.makeCompactGrid(form, 2, 2, 6, 6, 6, 6);
		add(form);
	}

	/** 
	 * Update the row filter regular expression from the expression in
	 * the text box.
	 */
	private void newFilter() {
		RowFilter<MyTableModel, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.regexFilter(filterText.getText(), 0);
			
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}


	public void afterModified(ConcreteTableModel a) {
		frame = new JFrame("Midterm");
		tab = a;
		
		table = new JTable(a);
		TableRowSorter <TableModel> sorter = new TableRowSorter <TableModel> (table.getModel());
		table.setRowSorter(sorter);
        
		sorter.setComparator(5, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		});
		
		sorter.setComparator(3, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String[] str1 = o1.split("/");
				String[] str2 = o2.split("/");
				Date d1 = new Date();
				Date d2 = new Date();
				d1.setYear(Integer.valueOf(str1[2]));
				d1.setMonth(Integer.valueOf(str1[0]));
				d1.setDate(Integer.valueOf(str1[1]));
				d2.setYear(Integer.valueOf(str2[2]));
				d2.setMonth(Integer.valueOf(str2[0]));
				d2.setDate(Integer.valueOf(str2[1]));
				return d1.compareTo(d2);
			}
		});

		
		JPanel control = new JPanel();
        control.setLayout(new GridLayout(2,7));
        JTextField NF = new JTextField("");
        control.add(NF);
        JTextField SF = new JTextField("");
        control.add(SF);
        JTextField EF = new JTextField("");
        control.add(EF);
        JTextField BF = new JTextField("");
        control.add(BF);
        JTextField GF = new JTextField("");
        control.add(GF);
        JTextField IF = new JTextField("");
        control.add(IF);
        JTextField AF = new JTextField("");
        control.add(AF);
        JButton NB = new JButton("Name");
        control.add(NB);
        JButton SB = new JButton("State");
        control.add(SB);
        JButton EB = new JButton("Email");
        control.add(EB);
        JButton BB = new JButton("Birthday");
        control.add(BB);
        JButton GB = new JButton("Gender");
        control.add(GB);
        JButton IB = new JButton("Income");
        control.add(IB);
        JButton AB = new JButton("Age");
        control.add(AB);
        NB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = NF.getText();
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 0));
				}
			}
		});
        
        SB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = SF.getText();
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 1));
				}
			}
		});
        
        EB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = EF.getText();
				System.out.println(str);
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 2));
				}
			}
		});
        
        BB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = BF.getText();
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 3));
				}
			}
		});
        
        GB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				String str = GF.getText();
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 4));
				}
			}
		});
        
        IB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				String str = IF.getText();
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 5));
				}
			}
		});
        
        AB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tt == true) {
					tab.deleteRow(table.getRowCount()-1);
					//tt = false;
				}
				String str = AF.getText();
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 6));
				}
			}
		});
        
   
        frame.add(control, BorderLayout.NORTH);
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.CENTER);
        
        
        JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);

		frame.setJMenuBar(menubar);

		JMenuItem menuItem1 = new JMenuItem("File Chooser");
		menuItem1.addActionListener(this);
		menu.add(menuItem1);

		JMenuItem menuItem2 = new JMenuItem("Delete Row");
		menuItem2.addActionListener(this);
		menu.add(menuItem2);

		JMenuItem menuItem3 = new JMenuItem("Add Row");
		menuItem3.addActionListener(this);
		menu.add(menuItem3);

		JMenuItem menuItem4 = new JMenuItem("Save");
		menuItem4.addActionListener(this);
		menu.add(menuItem4);

		JMenuItem menuItem5 = new JMenuItem("Edit Row");
		menuItem5.addActionListener(this);
		menu.add(menuItem5);

		
		JMenuItem menuItem6 = new JMenuItem("Total Income");
		menuItem6.addActionListener(this);
		menu.add(menuItem6);


		frame.pack();
		frame.setVisible(true);
		tab.fireTableDataChanged();
		
	}
	
	
	public void AfterOpenFile (ConcreteTableModel a) {
		frame = new JFrame("AssignmentVII");
		frame.setPreferredSize(new Dimension(1000,800));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		tab = a;
		table = new JTable(a);

		//table.setTableHeader(new EditableTableHeader(title));
		//table.getTableHeader();
		//table = new JTable(data, columnNames);
		JTableHeader tableHeader =  new JTableHeader();
		JTable header = new JTable();
		//header.
		//JTable header = new JTable(title);
		//tableHeader.setTable();
		//table.setTableHeader(tableHeader);
		//ArrayList <JTextField> JF = new ArrayList <JTextField>();
		//ArrayList <JButton> JB = new ArrayList <JButton>();
		//String[] columnNames = {"Name", "State", "Email", "Birthdate", "Gender", "Annual Income", "Age"};
		
		//for (int i = 0; i < table.getColumnCount(); i++) {
		//	JButton FiltButton = new JButton(columnNames[i]);
		//	FiltButton.setPreferredSize(new Dimension(100,20));
		//	JB.add(FiltButton);
		//}


		TableRowSorter <TableModel> sorter = new TableRowSorter <TableModel> (table.getModel());
		table.setRowSorter(sorter);
		FlowLayout experimentLayout = new FlowLayout();
	
		
		JScrollPane pane = new JScrollPane(table);
		

        JPanel control = new JPanel();
        control.setLayout(new FlowLayout());
        //for (int i = 0; i < table.getColumnCount(); i++) {
		//	control.add(JB.get(i));
		//}
        frame.add(control, BorderLayout.WEST);
        
	
		
		frame.add(pane, BorderLayout.NORTH);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Filter");
		panel.add(label, BorderLayout.NORTH);
		final JTextField filt = new JTextField("");
		panel.add(filt, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.CENTER);
		
		
		
		JButton FiltButton = new JButton("Filter");
		frame.add(FiltButton, BorderLayout.SOUTH);
		FiltButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = filt.getText();
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter(str));
				}
			}
		});

		//TableFilterDemo newContentPane = new TableFilterDemo();
		//newContentPane.setOpaque(true); //content panes must be opaque
		//frame.setContentPane(newContentPane);

		//panel.add(label, BorderLayout.WEST);
		table.getSelectedRow();




		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);

		frame.setJMenuBar(menubar);

		JMenuItem menuItem1 = new JMenuItem("File Chooser");
		menuItem1.addActionListener(this);
		menu.add(menuItem1);

		JMenuItem menuItem2 = new JMenuItem("Delete Row");
		menuItem2.addActionListener(this);
		menu.add(menuItem2);

		JMenuItem menuItem3 = new JMenuItem("Add Row");
		menuItem3.addActionListener(this);
		menu.add(menuItem3);

		JMenuItem menuItem4 = new JMenuItem("Save");
		menuItem4.addActionListener(this);
		menu.add(menuItem4);

		JMenuItem menuItem5 = new JMenuItem("Edit Row");
		menuItem5.addActionListener(this);
		menu.add(menuItem5);



		frame.pack();
		frame.setVisible(true);
		tab.fireTableDataChanged();

	}




	public void createAndShowGUI (ConcreteTableModel a) {
		frame = new JFrame("Midterm");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		tab = a;
		table = new JTable(a);
		String[] columnNames = {"Name", "State", "Email", "Birthdate", "Gender", "Annual Income", "Age"};
		String[][] data = {{"", "", "", "", "", "", ""}};
		JTable table = new JTable(data, columnNames);
		JScrollPane sp=new JScrollPane(table);    
		frame.add(sp,BorderLayout.CENTER);
		JLabel lable = new JLabel("Select file to read from menu.");
		lable.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(lable, BorderLayout.NORTH);

		/*
		tab = a;
		table = new JTable(a);

		TableRowSorter <TableModel> sorter = new TableRowSorter <TableModel> (table.getModel());
		table.setRowSorter(sorter);

		JScrollPane pane = new JScrollPane(table);
		frame.add(pane, BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());


        //panel.add(label, BorderLayout.WEST);
		table.getSelectedRow();*/

		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);

		frame.setJMenuBar(menubar);

		JMenuItem menuItem1 = new JMenuItem("File Chooser");
		menuItem1.addActionListener(this);
		menu.add(menuItem1);

		JMenuItem menuItem2 = new JMenuItem("Delete Row");
		menuItem2.addActionListener(this);
		menu.add(menuItem2);

		JMenuItem menuItem3 = new JMenuItem("Add Row");
		menuItem3.addActionListener(this);
		menu.add(menuItem3);

		frame.pack();
		frame.setVisible(true);

	}



	public static void main(String[] args) {
		final Main guiMaker = new Main();
		final ConcreteTableModel a = new ConcreteTableModel("input.csv");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				guiMaker.createAndShowGUI(a);
			}
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		if (arg0.getActionCommand() == "Delete Row" && table.getSelectedRow() >= 0 && table.getSelectedRow() <= tab.getRowCount()) {
			if (tab.getRowCount() > 0) {
				tab.deleteRow(table.getSelectedRow());
			}
			else {
				System.out.println("No column to delete!");
			}
		}
		if (arg0.getActionCommand() == "Add Row") {
			tab.addRow();
		}
		
		if (arg0.getActionCommand() == "File Chooser") {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());

				final Main guiMaker = new Main();
				final ConcreteTableModel a = new ConcreteTableModel(selectedFile.getAbsolutePath());
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						frame.setVisible(false);
						guiMaker.afterModified(a);
					}

				});
			}
		}
		if (arg0.getActionCommand() == "Save") {
			JFileChooser fileChooser = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("File type", "csv", "xml");
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (file.getName().toLowerCase().endsWith(".csv")) {	
					try {
						FileWriter w = new FileWriter(file);
						PrintWriter p = new PrintWriter(w);
						for (int i = 0; i < table.getRowCount(); i++) {
							for (int j = 0; j < table.getColumnCount(); j++) {
								if (table.getValueAt(i, j) != "") {
									p.write(table.getValueAt(i, j) + ",");
								}
							}
							p.write("\n");
							int t = 0;
							for (int x = 0; x < table.getRowCount()-1; x++) {
								t += Integer.parseInt(table.getValueAt(x, 5).toString());
							}
							p.write("total income: " + t + "\n");
							p.write("total people: " + (table.getRowCount()-1) + "\r\n");
						}
						w.close();
						p.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (arg0.getActionCommand() == "Total Income") {
			if (tt  == false) {
				tab.addRow();
				tab.fireTableDataChanged();
				
				table.setValueAt("0", table.getRowCount()-1, 5);
				int t = 0;
				//System.out.println(t);
				for (int i = 0; i < tab.getRowCount(); i++) {
					if (tab.getValueAt(i, 5) == "") {
						t += 0;
					}
					else {
						t += Integer.parseInt(tab.getValueAt(i, 5).toString());
					}
				}
				String total = String.valueOf(t);
				System.out.println(total + "\ntotal people: " + (table.getRowCount()-1));
				tab.setValueAt("Total income: " + total, table.getRowCount()-1, 5);
				tab.setValueAt("Total number of people: " + String.valueOf(table.getRowCount()), table.getRowCount()-1, 6);
				tab.fireTableDataChanged();
				tt = true;
			}
			
		}

		if (arg0.getActionCommand() == "Edit Row") {
			if (table.getSelectedRow() >= 0 && table.getSelectedRow() <= tab.getRowCount()) {
				JTextField NAME = new JTextField();
				JTextField STATE = new JTextField();
				JTextField EMAIL = new JTextField();
				JTextField BIRTHDAY = new JTextField();
				JTextField GENDER = new JTextField();
				JTextField INCOME = new JTextField();
				Object[] message = { 
						"NAME:", NAME,
						"STATE:", STATE,
						"EMAIL:", EMAIL,
						"BIRTHDAY(MM/DD/YYYY):", BIRTHDAY,
						"GENDER:", GENDER,
						"INCOME:", INCOME
				};
				NAME.setText((String)tab.getValueAt(table.getSelectedRow(), 0));
				STATE.setText((String)tab.getValueAt(table.getSelectedRow(), 1));
				EMAIL.setText((String)tab.getValueAt(table.getSelectedRow(), 2));
				BIRTHDAY.setText((String)tab.getValueAt(table.getSelectedRow(), 3));
				GENDER.setText((String)tab.getValueAt(table.getSelectedRow(), 4));
				INCOME.setText((String)tab.getValueAt(table.getSelectedRow(), 5));
				int option = JOptionPane.showConfirmDialog(null, message, "Edit selected row", JOptionPane.OK_CANCEL_OPTION);
				boolean flag = true;
				String d = BIRTHDAY.getText();
				String[] tmp = d.split("/");

				String birth = "";
				String age = "";
				flag = true;
				Date bd = new Date();
				bd.setMonth(Integer.valueOf(tmp[0]));
				bd.setDate(Integer.valueOf(tmp[1]));
				bd.setYear(Integer.valueOf(tmp[2]));
				Date td = java.util.Calendar.getInstance().getTime();
				birth = bd.getMonth()+"/"+bd.getDate()+"/"+bd.getYear();
				//System.out.println(bd.getMonth()+"/"+bd.getDate()+"/"+bd.getYear());
				age = String.valueOf(2018-bd.getYear());
				
				tab.setValueAt(NAME.getText(), table.getSelectedRow(), 0);
				tab.setValueAt(STATE.getText(), table.getSelectedRow(), 1);
				tab.setValueAt(EMAIL.getText(), table.getSelectedRow(), 2);
				
				tab.setValueAt(GENDER.getText(), table.getSelectedRow(), 4);
				tab.setValueAt(INCOME.getText(), table.getSelectedRow(), 5);
				//if (flag == true) {
				tab.setValueAt(birth, table.getSelectedRow(), 3);
				tab.setValueAt(age, table.getSelectedRow(), 6);
				tab.fireTableDataChanged();
				//}
				//else {
				//tab.setValueAt(birth, table.getSelectedRow(), 3);
				//}
			}
			else {
				JOptionPane.showMessageDialog(frame, "No row selected. Please select a row to edit!");				
			}
		}

	}
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
}

