package MySQLDemo;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.sql.*;
import java.util.*;  

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class LoginPage extends FacultyStoredPrecedure 
{
	/*
	 * Sachin Test Two - Files System
	 */

	JFrame dataFrame;
	private JTextField patientID;

	/**
	 * Launch the application.
	 */
	
	public static void screen()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					LoginPage demoObj = new LoginPage();
					demoObj.Connection();
					demoObj.dataFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dataFrame = new JFrame();
		dataFrame.setResizable(false);
		dataFrame.setBounds(100, 100, 450, 366);
		dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataFrame.getContentPane().setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("COVID Data Visualizer");
		lblNewLabel.setFont(new Font("Krungthep", Font.BOLD, 10));
		lblNewLabel.setBounds(268, 6, 118, 29);
		dataFrame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 16, 191, 185);
		dataFrame.getContentPane().add(scrollPane);
		
		final JTextArea textAreaOne = new JTextArea();
		scrollPane.setViewportView(textAreaOne);
		textAreaOne.setEditable(false);
		
		String[] queries = {"Select a Query... ", "Patients By Country", "Common Symptoms Ranked", "Total Patient Count", "Seniors (60+) With No Symptoms", "Seniors (60+) With Symptoms", "All Reported Symptoms", "Total Patients with Positive COVID Contact", "Total Patients Aged 10-19 Experiencing Diarrhea", "Total Patients Aged 60+ Experiencing Diarrhea", "Total Women Patients from Italy Aged 20-24 Experiencing Diarrhea", "Total Patients Having No Symptoms And Also Contacting a COVID Positive Individual", "Total Patients Aged 60+ And Experiencing Severe Symptoms", "Total Male Patients Experiencing Moderate Symptoms", "All Countries Reported", "Total Patients Experiencing No Symptoms"};
		final JComboBox comboBox = new JComboBox(queries);
		comboBox.setMaximumRowCount(40);
		comboBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String chosen = (String)comboBox.getSelectedItem();
				
				if (chosen == "Patients By Country")
				{
					textAreaOne.setText("");
					try 
					{
						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT COUNT(PatientID), Country FROM Patients GROUP BY Country ASC;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients By Country: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next())
						{
				             String pID = s.getString("COUNT(PatientID)");
				             String country = s.getString("Country");

				             //Printing Results
				             textAreaOne.append(country + ": " + pID + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Common Symptoms Ranked")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT COUNT(S.SymptomID), P.SymptomDescription FROM PatientSymptoms S, Symptoms P WHERE P.SymptomID = S.SymptomID GROUP BY SymptomDescription ORDER BY COUNT(S.SymptomID) DESC;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Most Common Symptoms Reported: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String countsymptomid = s.getString("COUNT(S.SymptomID)");
				             String symptomd = s.getString("SymptomDescription");

				             //Printing Results
				             System.out.println(symptomd + ": " + countsymptomid);
				             textAreaOne.append(symptomd + ": " + countsymptomid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patient Count")
				{
					textAreaOne.setText("");
					
					FacultyStoredPrecedure demoObj = new FacultyStoredPrecedure();
					demoObj.Connection();
					
					int result = demoObj.patientsProcedure();
					
					textAreaOne.append("------------------------------ " + "\n");
					textAreaOne.append("Total Current Patients: " + "\n");
					textAreaOne.append("------------------------------ " + "\n");
					textAreaOne.append(result + " ");
				}
				else if (chosen == "All Countries Reported")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT DISTINCT Country FROM Patients;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("All Countries Reported:" + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String country = s.getString("Country");

				             //Printing Results
				             textAreaOne.append(country + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Seniors (60+) With No Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT DISTINCT COUNT(S.PatientID) FROM Patients P, PatientSymptoms S WHERE P.AgeDescription = \"60+\" AND P.PatientID = S.PatientID AND S.SymptomID = 5;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Seniors with No Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String countpatientid = s.getString("COUNT(S.PatientID)");

				             //Printing Results
				             textAreaOne.append(countpatientid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Seniors (60+) With Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT DISTINCT COUNT(S.PatientID) FROM Patients P, PatientSymptoms S WHERE P.AgeDescription = \"60+\" AND P.PatientID = S.PatientID AND S.SymptomID <> 5;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Seniors with Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String countpatientid = s.getString("COUNT(S.PatientID)");

				             //Printing Results
				             textAreaOne.append(countpatientid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "All Reported Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("select SymptomDescription from Symptoms;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("All Reported Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String SymptomDes = s.getString("SymptomDescription");

				             //Printing Results
				             textAreaOne.append(SymptomDes + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients Experiencing No Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT DISTINCT COUNT(PatientID) FROM PatientSymptoms WHERE SymptomID = 5;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients Experiencing No Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("COUNT(PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients with Positive COVID Contact")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT DISTINCT COUNT(PatientID) FROM Patients WHERE ContactID = 0;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients with Positive COVID Contact: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("COUNT(PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients Aged 10-19 Experiencing Diarrhea")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("select count(p.PatientID) from Patients p, PatientSymptoms z, Symptoms s, Severities x where p.PatientID = z.PatientID and s.SymptomID = z.SymptomID and x.SeverityID = p.SeverityID and s.SymptomDescription = \"Diarrhea\" and p.AgeDescription = \"10->19\" and x.SeverityDescription != \"None\";");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients Aged 10-19 Experiencing Diarrhea: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("count(p.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients Aged 60+ Experiencing Diarrhea")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("select count(p.PatientID) from Patients p, PatientSymptoms z, Symptoms s, Severities x where p.PatientID = z.PatientID and s.SymptomID = z.SymptomID and x.SeverityID = p.SeverityID and s.SymptomDescription = \"Diarrhea\" and p.AgeDescription = \"60+\" and p.GenderDescription = \"Male\" and x.SeverityDescription = \"Severe\";");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients Aged 60+ Experiencing Diarrhea: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("count(p.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Women Patients from Italy Aged 20-24 Experiencing Diarrhea")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("select count(p.PatientID) from Patients p, PatientSymptoms z, Symptoms s, Severities x where p.PatientID = z.PatientID and s.SymptomID = z.SymptomID and x.SeverityID = p.SeverityID and s.SymptomDescription = \"Diarrhea\" and p.AgeDescription = \"20->24\" and p.GenderDescription = \"Female\" and x.SeverityDescription = \"Severe\" and p.Country = \"Italy\";");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Women Patients from Italy Aged 20-24 Experiencing Diarrhea: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("count(p.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients Having No Symptoms And Also Contacting a COVID Positive Individual")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT COUNT(P.PatientID) FROM Patients P, PatientSymptoms S WHERE P.PatientID = S.PatientID AND S.SymptomID = 5 AND P.ContactID = 0;");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients Having No Symptoms And Also Contacting a COVID Positive Individual: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("COUNT(P.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Patients Aged 60+ And Experiencing Severe Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT COUNT(p.PatientID) FROM Patients p, Severities s where p.SeverityID = s.SeverityID and p.AgeDescription = \"60+\"and s.SeverityDescription = \"Severe\";");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Patients Aged 60+ And Experiencing Severe Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("COUNT(p.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
				else if (chosen == "Total Male Patients Experiencing Moderate Symptoms")
				{
					try 
					{
						textAreaOne.setText("");

						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("SELECT COUNT(p.PatientID) FROM Patients p, Severities s where s.SeverityID = p.SeverityID and p.GenderDescription = \"Male\" and s.SeverityDescription = \"Moderate\";\n"
								+ "");
						ResultSet s = pst.executeQuery();
						textAreaOne.append("------------------------------ " + "\n");
						textAreaOne.append("Total Male Patients Experiencing Moderate Symptoms: " + "\n");
						textAreaOne.append("------------------------------ " + "\n");
						while(s.next()){
				             String contactid = s.getString("COUNT(p.PatientID)");

				             //Printing Results
				             textAreaOne.append(contactid + "\n");
				        }
						
						pst.close();
						s.close();
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		comboBox.setBounds(28, 254, 160, 29);
		dataFrame.getContentPane().add(comboBox);
		
		patientID = new JTextField();
		patientID.setBounds(290, 32, 145, 26);
		dataFrame.getContentPane().add(patientID);
		patientID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("PatientID:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(230, 37, 55, 16);
		dataFrame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Quick Searches:");
		lblNewLabel_2.setFont(new Font("Krungthep", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(54, 229, 105, 16);
		dataFrame.getContentPane().add(lblNewLabel_2);
		
		final String[] optionsContacts = {"Contacts..", "Yes", "No", "DontKnow"};
		final JComboBox contactCombo = new JComboBox(optionsContacts);
		contactCombo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			}
		});
		contactCombo.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		contactCombo.setBounds(268, 120, 126, 27);
		dataFrame.getContentPane().add(contactCombo);
		
		final String[] optionsSeverity = {"Severity..", "None", "Mild", "Moderate", "Severe"};
		final JComboBox severityCombo = new JComboBox(optionsSeverity);
		severityCombo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		severityCombo.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		severityCombo.setBounds(318, 91, 126, 27);
		dataFrame.getContentPane().add(severityCombo);
		
		final String[] optionsGender = {"Gender..", "Male", "Female", "Transgender"};
		final JComboBox genderCombo = new JComboBox(optionsGender);
		genderCombo.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		genderCombo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			}
		});
		genderCombo.setBounds(214, 91, 105, 27);
		dataFrame.getContentPane().add(genderCombo);
		
		final String[] options = {"Country..", "China", "Italy"};
		final JComboBox countryCombo = new JComboBox(options);
		countryCombo.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		countryCombo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		countryCombo.setBounds(214, 62, 105, 27);
		dataFrame.getContentPane().add(countryCombo);
		
		final String[] optionsAge = {"Age Range..", "0->9", "10->19", "20->24", "25->59", "60+"};
		final JComboBox ageCombo = new JComboBox(optionsAge);
		ageCombo.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		ageCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ageCombo.setToolTipText("Age Range");
		ageCombo.setBounds(318, 62, 126, 27);
		dataFrame.getContentPane().add(ageCombo);
		
		final JCheckBox none = new JCheckBox("No Symptoms");
		
		final JCheckBox fever = new JCheckBox("Fever");
		fever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		fever.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		fever.setBounds(230, 154, 71, 23);
		dataFrame.getContentPane().add(fever);
		
		final JCheckBox tiredness = new JCheckBox("Tiredness");
		tiredness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		tiredness.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		tiredness.setBounds(230, 194, 85, 23);
		dataFrame.getContentPane().add(tiredness);
		
		final JCheckBox cough = new JCheckBox("Dry Cough");
		cough.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		cough.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		cough.setBounds(334, 174, 90, 23);
		dataFrame.getContentPane().add(cough);
		
		final JCheckBox breathing = new JCheckBox("Difficulty Breathing");
		breathing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		breathing.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		breathing.setBounds(294, 154, 135, 23);
		dataFrame.getContentPane().add(breathing);
		
		final JCheckBox sorethroat = new JCheckBox("Sore Throat");
		sorethroat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		sorethroat.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		sorethroat.setBounds(230, 174, 105, 23);
		dataFrame.getContentPane().add(sorethroat);
		
		final JCheckBox pains = new JCheckBox("Pains");
		pains.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		pains.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		pains.setBounds(230, 214, 75, 23);
		dataFrame.getContentPane().add(pains);
		
		final JCheckBox nasal = new JCheckBox("Congestion");
		nasal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		nasal.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		nasal.setBounds(334, 194, 99, 23);
		dataFrame.getContentPane().add(nasal);
		
		final JCheckBox runny = new JCheckBox("Runny Nose");
		runny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		runny.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		runny.setBounds(334, 214, 99, 23);
		dataFrame.getContentPane().add(runny);
		
		final JCheckBox diarrhea = new JCheckBox("Diarrhea");
		diarrhea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				none.setSelected(false);
			}
		});
		diarrhea.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		diarrhea.setBounds(230, 240, 99, 23);
		dataFrame.getContentPane().add(diarrhea);
		
		none.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				fever.setSelected(false);
				tiredness.setSelected(false);
				cough.setSelected(false);
				breathing.setSelected(false);
				sorethroat.setSelected(false);
				pains.setSelected(false);
				nasal.setSelected(false);
				runny.setSelected(false);
				diarrhea.setSelected(false);
			}
		});
		none.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		none.setBounds(325, 240, 119, 23);
		dataFrame.getContentPane().add(none);
				
		JButton searchCustom = new JButton("Search");
		searchCustom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textAreaOne.setText("");
				try 
				{
					Connection testC = DriverManager.getConnection(databaseURL, netID, password);
					PreparedStatement pst = testC.prepareStatement("SELECT * FROM Patients WHERE PatientID = ?;");
					PreparedStatement symptom = testC.prepareStatement("SELECT SymptomID FROM PatientSymptoms WHERE PatientID = ?;");
					pst.setString(1, patientID.getText());
					symptom.setString(1, patientID.getText());
					ResultSet s = pst.executeQuery();
					ResultSet symptoms = symptom.executeQuery();
					fever.setSelected(false);
					tiredness.setSelected(false);
					cough.setSelected(false);
					breathing.setSelected(false);
					sorethroat.setSelected(false);
					none.setSelected(false);
					pains.setSelected(false);
					nasal.setSelected(false);
					runny.setSelected(false);
					diarrhea.setSelected(false);
					
					while(symptoms.next())
					{
						
						String sID = symptoms.getString("SymptomID");
						
						if (sID.toString().equals("0"))
						{
							fever.setSelected(true);
						}
						else if (sID.toString().equals("1"))
						{
							tiredness.setSelected(true);
						}
						else if (sID.toString().equals("2"))
						{
							cough.setSelected(true);
						}
						else if (sID.toString().equals("3"))
						{
							breathing.setSelected(true);
						}
						else if (sID.toString().equals("4"))
						{
							sorethroat.setSelected(true);
						}
						else if (sID.toString().equals("5"))
						{
							none.setSelected(true);
						}
						else if (sID.toString().equals("6"))
						{
							pains.setSelected(true);
						}
						else if (sID.toString().equals("7"))
						{
							nasal.setSelected(true);
						}
						else if (sID.toString().equals("8"))
						{
							runny.setSelected(true);
						}
						else if (sID.toString().equals("9"))
						{
							diarrhea.setSelected(true);
						}
						

						
					}
					
					symptom.close();
					symptoms.close();
					
					while(s.next())
					{
			             String pID = s.getString("PatientID");
			             String country = s.getString("Country");
			             String age = s.getString("AgeDescription");
			             String gender = s.getString("GenderDescription");
			             String severity = s.getString("SeverityID");
			             String contact = s.getString("ContactID");


			             //Printing Results
			             textAreaOne.append("------------------------------ " + "\n");
			             textAreaOne.append("PatientID: " + pID + "\n");
			             textAreaOne.append("------------------------------ " + "\n");
			             textAreaOne.append("Country:   " + country + "\n");
			             textAreaOne.append("Age Range: " + age + "\n");
			             textAreaOne.append("Gender: " + gender + "\n");
			             textAreaOne.append("Disease Severity Diagnosis: " + severity + "\n");
			             textAreaOne.append("Confirmed Contact Status: " + contact + "\n");
			           	
			             // updating Country 
			             if (country.toString().equals("China"))
			             {
			            	 countryCombo.setSelectedItem(options[1]);
			             }
			             if (country.toString().equals("Italy"))
			             {
			            	 countryCombo.setSelectedItem(options[2]);
			             }
			             
			             // updating Age Range
			             if (age.toString().equals("0->9"))
			             {
				             System.out.println(optionsAge[1]);

				             ageCombo.setSelectedItem(optionsAge[1]);
			             }
			             else if (age.toString().equals("10->19"))
			             {
			            	 System.out.println(optionsAge[2]);
			            	 
			            	 ageCombo.setSelectedItem(optionsAge[2]);
			             }
			             else if (age.toString().equals("20->24"))
			             {
			            	 System.out.println(optionsAge[3]);
			            	 
			            	 ageCombo.setSelectedItem(optionsAge[3]);
			             }	
			             else if (age.toString().equals("25->59"))
			             {
			            	 System.out.println(optionsAge[4]);
			            	 
			            	 ageCombo.setSelectedItem(optionsAge[4]);
			             }
			             else if (age.toString().equals("60+"))
			             {
			            	 System.out.println(optionsAge[5]);
			            	 
			            	 ageCombo.setSelectedItem(optionsAge[5]);
			             }

			             // updating Gender 
			             if (gender.toString().equals(optionsGender[1]))
			             {
			            	 genderCombo.setSelectedItem(optionsGender[1]);
			             }
			             else if (gender.toString().equals(optionsGender[2]))
			             {
			            	 genderCombo.setSelectedItem(optionsGender[2]);
			             }
			             else if (gender.toString().equals(optionsGender[3]))
			             {
			            	 genderCombo.setSelectedItem(optionsGender[3]);
			             }
			             
			             // updating Severities
			             if (Integer.parseInt(severity) == 0)
			             {
			            	 severityCombo.setSelectedItem(optionsSeverity[1]);
			             }
			             else if (Integer.parseInt(severity) == 1)
			             {
			            	 severityCombo.setSelectedItem(optionsSeverity[2]);
			             }
			             else if (Integer.parseInt(severity) == 2)
			             {
			            	 severityCombo.setSelectedItem(optionsSeverity[3]);
			             }
			             else if (Integer.parseInt(severity) == 3)
			             {
			            	 severityCombo.setSelectedItem(optionsSeverity[4]);
			             }
			             
			             
			             // updating Contacts
			             if (Integer.parseInt(contact) == 0)
			             {
				             System.out.println(optionsContacts[1]);

			            	 contactCombo.setSelectedItem(optionsContacts[1]);
			             }
			             else if (Integer.parseInt(contact) == 1)
			             {
			            	 System.out.println(optionsContacts[2]);
			            	 
			            	 contactCombo.setSelectedItem(optionsContacts[2]);
			             }
			             else if (Integer.parseInt(contact) == 2)
			             {
			            	 System.out.println(optionsContacts[3]);
			            	 
			            	 contactCombo.setSelectedItem(optionsContacts[3]);
			             }	

			        }
					
					pst.close();
					s.close();
				}
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		searchCustom.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		searchCustom.setBounds(214, 275, 55, 29);
		dataFrame.getContentPane().add(searchCustom);
		
		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (countryCombo.getSelectedItem().toString().equals(options[0]) || ageCombo.getSelectedItem().toString().equals(optionsAge[0]) || genderCombo.getSelectedItem().toString().equals(optionsGender[0]) || severityCombo.getSelectedItem().toString().equals(optionsSeverity[0]) || contactCombo.getSelectedItem().toString().equals(optionsContacts[0]))
				{
					System.out.println("NO");
					JOptionPane.showMessageDialog(dataFrame, "Please Make Sure To Select Values For Every Option!! No Default Values ('Country..', 'Age Range..')");
				}
				else
				{
					System.out.println(genderCombo.getSelectedItem().toString());
					
					// 		final String[] optionsSeverity = {"Severity..", "None", "Mild", "Moderate", "Severe"};

					int severityid = 0;
					int contactid = 0;
					
					if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[1]))
					{
						severityid = 0;
					}
					else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[2]))
					{
						severityid = 1;
					}
					else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[3]))
					{
						severityid = 2;
					}
					else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[4]))
					{
						severityid = 3;
					}
					
					// 				final String[] optionsContacts = {"Contacts..", "Yes", "No", "DontKnow"};


					if (contactCombo.getSelectedItem().toString().equals(optionsContacts[1]))
					{
						contactid = 0;
					}
					else if (contactCombo.getSelectedItem().toString().equals(optionsContacts[2]))
					{
						contactid = 1;
					}
					else if (contactCombo.getSelectedItem().toString().equals(optionsContacts[3]))
					{
						contactid = 2;
					}
					
					try
					{
						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement pst = testC.prepareStatement("UPDATE Patients SET PatientID = ?, Country = ?, AgeDescription = ?, GenderDescription = ?, SeverityID = ?, ContactID = ? WHERE PatientID = ?");
						
						pst.setInt(1, Integer.parseInt(patientID.getText()));
						
						pst.setString(2, countryCombo.getSelectedItem().toString());
						
						pst.setString(3, ageCombo.getSelectedItem().toString());
						
						pst.setString(4, genderCombo.getSelectedItem().toString());
						
						pst.setInt(5, severityid);
	
						pst.setInt(6, contactid);
						
						pst.setInt(7, Integer.parseInt(patientID.getText()));
						
						ResultSet s = pst.executeQuery();
						
						pst.close();
						
						// updating checkbox values
						
						// basic idea: if its checked: i have to delete first, then insert. if its unchecked, I have to delete, and insert (INSERT INTO, DELETE FROM)
						
						// deletes all existing symptom data from the database for this patientID
						PreparedStatement deleteExisting = testC.prepareStatement("DELETE FROM PatientSymptoms WHERE PatientID = ?;");
						deleteExisting.setInt(1, Integer.parseInt(patientID.getText()));
						deleteExisting.executeQuery();
						deleteExisting.close();
						
						if (none.isSelected() == true)
						{
							PreparedStatement insertNone = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 5);");
							insertNone.setInt(1, Integer.parseInt(patientID.getText()));
							insertNone.executeQuery();
							insertNone.close();
						}
						else
						{
							if (fever.isSelected() == true)
							{
								PreparedStatement insertFever = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 0);");
								insertFever.setInt(1, Integer.parseInt(patientID.getText()));
								insertFever.executeQuery();
								insertFever.close();
							}
							
							if (tiredness.isSelected() == true)
							{
								PreparedStatement insertTiredness = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 1);");
								insertTiredness.setInt(1, Integer.parseInt(patientID.getText()));
								insertTiredness.executeQuery();
								insertTiredness.close();
							}
							
							if (cough.isSelected() == true)
							{
								PreparedStatement insertCough = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 2);");
								insertCough.setInt(1, Integer.parseInt(patientID.getText()));
								insertCough.executeQuery();
								insertCough.close();
							}
							
							if (breathing.isSelected() == true)
							{
								PreparedStatement insertBreathing = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 3);");
								insertBreathing.setInt(1, Integer.parseInt(patientID.getText()));
								insertBreathing.executeQuery();
								insertBreathing.close();
							}
							
							if (sorethroat.isSelected() == true)
							{
								PreparedStatement insertSore = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 4);");
								insertSore.setInt(1, Integer.parseInt(patientID.getText()));
								insertSore.executeQuery();
								insertSore.close();
							}
							
							if (pains.isSelected() == true)
							{
								PreparedStatement insertPains = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 6);");
								insertPains.setInt(1, Integer.parseInt(patientID.getText()));
								insertPains.executeQuery();
								insertPains.close();
							}
							
							if (nasal.isSelected() == true)
							{
								PreparedStatement insertNasal = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 7);");
								insertNasal.setInt(1, Integer.parseInt(patientID.getText()));
								insertNasal.executeQuery();
								insertNasal.close();
							}
							
							if (runny.isSelected() == true)
							{
								PreparedStatement insertRunny = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 8);");
								insertRunny.setInt(1, Integer.parseInt(patientID.getText()));
								insertRunny.executeQuery();
								insertRunny.close();
							}
							
							if (diarrhea.isSelected() == true)
							{
								PreparedStatement insertDiarrhea = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 9);");
								insertDiarrhea.setInt(1, Integer.parseInt(patientID.getText()));
								insertDiarrhea.executeQuery();
								insertDiarrhea.close();
							}
						
						}
						
						
						
						JOptionPane.showMessageDialog(dataFrame, "Update Successful!!");

					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						JOptionPane.showMessageDialog(dataFrame, "Unknown Error, Check Console Log");

					}
					
				}
			}
		});
		
		update.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		update.setBounds(268, 275, 55, 29);
		dataFrame.getContentPane().add(update);
		
		JButton insert = new JButton("Insert");
		insert.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					
					Connection testC = DriverManager.getConnection(databaseURL, netID, password);

					PreparedStatement checkID = testC.prepareStatement("SELECT PatientID FROM Patients WHERE PatientID = ?;");

					checkID.setInt(1, Integer.parseInt(patientID.getText()));

					ResultSet ID = checkID.executeQuery();

					checkID.close();
					
					String idInsert = "";
					
					while (ID.next())
					{
						idInsert = ID.getString("PatientID");
					}
										
					PreparedStatement getNewID = testC.prepareStatement("SELECT MAX(PatientID) + 1 FROM Patients;");
					ResultSet newID = getNewID.executeQuery();
					getNewID.close();
					
					String freshID = "";

					while (newID.next())
					{
						freshID = newID.getString("MAX(PatientID) + 1");
					}
										
					if (idInsert.equals(patientID.getText().toString()))
					{
						JOptionPane.showMessageDialog(dataFrame, "This ID is already taken! Try this ID instead: " + freshID);
					}
					else
					{

						if (countryCombo.getSelectedItem().toString().equals(options[0]) || ageCombo.getSelectedItem().toString().equals(optionsAge[0]) || genderCombo.getSelectedItem().toString().equals(optionsGender[0]) || severityCombo.getSelectedItem().toString().equals(optionsSeverity[0]) || contactCombo.getSelectedItem().toString().equals(optionsContacts[0]))
						{
							System.out.println("NO");
							JOptionPane.showMessageDialog(dataFrame, "Please Make Sure To Select Values For Every Option!! No Default Values ('Country..', 'Age Range..')");
						}
						else
						{
							System.out.println(genderCombo.getSelectedItem().toString());
							
							// 		final String[] optionsSeverity = {"Severity..", "None", "Mild", "Moderate", "Severe"};

							int severityid = 0;
							int contactid = 0;
							
							if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[1]))
							{
								severityid = 0;
							}
							else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[2]))
							{
								severityid = 1;
							}
							else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[3]))
							{
								severityid = 2;
							}
							else if (severityCombo.getSelectedItem().toString().equals(optionsSeverity[4]))
							{
								severityid = 3;
							}
							
							// 				final String[] optionsContacts = {"Contacts..", "Yes", "No", "DontKnow"};


							if (contactCombo.getSelectedItem().toString().equals(optionsContacts[1]))
							{
								contactid = 0;
							}
							else if (contactCombo.getSelectedItem().toString().equals(optionsContacts[2]))
							{
								contactid = 1;
							}
							else if (contactCombo.getSelectedItem().toString().equals(optionsContacts[3]))
							{
								contactid = 2;
							}
							
							try
							{
								testC = DriverManager.getConnection(databaseURL, netID, password);
								PreparedStatement pst = testC.prepareStatement("INSERT INTO Patients (PatientID, Country, AgeDescription, GenderDescription, SeverityID, ContactID) VALUES (?, ?, ?, ?, ?, ?);");
								
								pst.setInt(1, Integer.parseInt(patientID.getText()));
								
								pst.setString(2, countryCombo.getSelectedItem().toString());
								
								pst.setString(3, ageCombo.getSelectedItem().toString());
								
								pst.setString(4, genderCombo.getSelectedItem().toString());
								
								pst.setInt(5, severityid);
			
								pst.setInt(6, contactid);
																
								ResultSet s = pst.executeQuery();
								
								pst.close();
								
								// updating checkbox values
								
								// basic idea: if its checked: i have to delete first, then insert. if its unchecked, I have to delete, and insert (INSERT INTO, DELETE FROM)
								
								// deletes all existing symptom data from the database for this patientID
								PreparedStatement deleteExisting = testC.prepareStatement("DELETE FROM PatientSymptoms WHERE PatientID = ?;");
								deleteExisting.setInt(1, Integer.parseInt(patientID.getText()));
								deleteExisting.executeQuery();
								deleteExisting.close();
								
								if (none.isSelected() == true)
								{
									PreparedStatement insertNone = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 5);");
									insertNone.setInt(1, Integer.parseInt(patientID.getText()));
									insertNone.executeQuery();
									insertNone.close();
								}
								else
								{
									if (fever.isSelected() == true)
									{
										PreparedStatement insertFever = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 0);");
										insertFever.setInt(1, Integer.parseInt(patientID.getText()));
										insertFever.executeQuery();
										insertFever.close();
									}
									
									if (tiredness.isSelected() == true)
									{
										PreparedStatement insertTiredness = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 1);");
										insertTiredness.setInt(1, Integer.parseInt(patientID.getText()));
										insertTiredness.executeQuery();
										insertTiredness.close();
									}
									
									if (cough.isSelected() == true)
									{
										PreparedStatement insertCough = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 2);");
										insertCough.setInt(1, Integer.parseInt(patientID.getText()));
										insertCough.executeQuery();
										insertCough.close();
									}
									
									if (breathing.isSelected() == true)
									{
										PreparedStatement insertBreathing = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 3);");
										insertBreathing.setInt(1, Integer.parseInt(patientID.getText()));
										insertBreathing.executeQuery();
										insertBreathing.close();
									}
									
									if (sorethroat.isSelected() == true)
									{
										PreparedStatement insertSore = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 4);");
										insertSore.setInt(1, Integer.parseInt(patientID.getText()));
										insertSore.executeQuery();
										insertSore.close();
									}
									
									if (pains.isSelected() == true)
									{
										PreparedStatement insertPains = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 6);");
										insertPains.setInt(1, Integer.parseInt(patientID.getText()));
										insertPains.executeQuery();
										insertPains.close();
									}
									
									if (nasal.isSelected() == true)
									{
										PreparedStatement insertNasal = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 7);");
										insertNasal.setInt(1, Integer.parseInt(patientID.getText()));
										insertNasal.executeQuery();
										insertNasal.close();
									}
									
									if (runny.isSelected() == true)
									{
										PreparedStatement insertRunny = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 8);");
										insertRunny.setInt(1, Integer.parseInt(patientID.getText()));
										insertRunny.executeQuery();
										insertRunny.close();
									}
									
									if (diarrhea.isSelected() == true)
									{
										PreparedStatement insertDiarrhea = testC.prepareStatement("INSERT INTO PatientSymptoms (PatientID, SymptomID) VALUES (?, 9);");
										insertDiarrhea.setInt(1, Integer.parseInt(patientID.getText()));
										insertDiarrhea.executeQuery();
										insertDiarrhea.close();
									}
								
								}
								
								
								
								JOptionPane.showMessageDialog(dataFrame, "Insertion Successful!!");

							}
							catch (SQLException e1) 
							{
								e1.printStackTrace();
								JOptionPane.showMessageDialog(dataFrame, "Unknown Error, Check Console Log");

							}
							
						}
					
					}
				}
				catch (SQLException e1) 
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(dataFrame, "Unknown Error, Check Console Log");

				}
			}
		});
		insert.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		insert.setBounds(326, 275, 55, 29);
		dataFrame.getContentPane().add(insert);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Object[] options = {"Yes, please", "No way!"};
				int n = JOptionPane.showOptionDialog(dataFrame,
				                "You are about to delete this record permanently. Are you ok with this?",
				                "WARNING",
				                JOptionPane.YES_NO_OPTION,
				                JOptionPane.QUESTION_MESSAGE,
				                null,
				                options,
				                options[0]);
				if (n == JOptionPane.YES_OPTION) 
				{
					try
					{
						Connection testC = DriverManager.getConnection(databaseURL, netID, password);
						PreparedStatement deleteExisting = testC.prepareStatement("DELETE FROM PatientSymptoms WHERE PatientID = ?;");
						deleteExisting.setInt(1, Integer.parseInt(patientID.getText()));
						deleteExisting.executeQuery();
						deleteExisting.close();
						
						PreparedStatement deleteRec = testC.prepareStatement("DELETE FROM Patients WHERE PatientID = ?;");
						deleteRec.setInt(1, Integer.parseInt(patientID.getText()));
						deleteRec.executeQuery();
						deleteRec.close();
						
						fever.setSelected(false);
						tiredness.setSelected(false);
						cough.setSelected(false);
						breathing.setSelected(false);
						sorethroat.setSelected(false);
						none.setSelected(false);
						pains.setSelected(false);
						nasal.setSelected(false);
						runny.setSelected(false);
						diarrhea.setSelected(false);
						
						countryCombo.setSelectedItem(options[0]);
						ageCombo.setSelectedItem(optionsAge[0]);
						genderCombo.setSelectedItem(optionsGender[0]);
						severityCombo.setSelectedItem(optionsSeverity[0]);
						contactCombo.setSelectedItem(optionsContacts[0]);
						
						patientID.setText("");
						
						textAreaOne.setText("");;
						
						JOptionPane.showMessageDialog(dataFrame, "Deletion Successful");
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
						JOptionPane.showMessageDialog(dataFrame, "Unknown Error, Check Console Log");
					}
				} 
				else if (n == JOptionPane.NO_OPTION) 
				{
					JOptionPane.showMessageDialog(dataFrame, "Deletion Unsuccessful");
				} 
				else 
				{
					JOptionPane.showMessageDialog(dataFrame, "Deletion Unsuccessful");
				}

				
			}
		});
		delete.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		delete.setBounds(380, 275, 55, 29);
		dataFrame.getContentPane().add(delete);
		
		JButton clear = new JButton("CLEAR");
		clear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				fever.setSelected(false);
				tiredness.setSelected(false);
				cough.setSelected(false);
				breathing.setSelected(false);
				sorethroat.setSelected(false);
				none.setSelected(false);
				pains.setSelected(false);
				nasal.setSelected(false);
				runny.setSelected(false);
				diarrhea.setSelected(false);
				
				countryCombo.setSelectedItem(options[0]);
				ageCombo.setSelectedItem(optionsAge[0]);
				genderCombo.setSelectedItem(optionsGender[0]);
				severityCombo.setSelectedItem(optionsSeverity[0]);
				contactCombo.setSelectedItem(optionsContacts[0]);
				
				patientID.setText("");
				
				textAreaOne.setText("");
				comboBox.setSelectedIndex(0);
			}
		});
		clear.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		clear.setBounds(257, 305, 129, 30);
		dataFrame.getContentPane().add(clear);
		
		
		
	}
}
