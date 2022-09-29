package MySQLDemo;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignIn extends LoginPage  {
	/*
	 * Sachin Test Two - Files System
	 */

	public JFrame loginFrame;
	private JTextField netIDInput;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn window = new SignIn();
					window.loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignIn() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginFrame = new JFrame();
		loginFrame.setResizable(false);
		loginFrame.setBounds(100, 100, 450, 300);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon(this.getClass().getResource("/covid.png"));
		
		JLabel lblNewLabel_1 = new JLabel("COVID-19 Data Visualizer");
		lblNewLabel_1.setFont(new Font("Krungthep", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(17, 18, 312, 42);
		loginFrame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Log In Portal:");
		lblNewLabel_1_1.setFont(new Font("Krungthep", Font.PLAIN, 10));
		lblNewLabel_1_1.setBounds(17, 40, 312, 42);
		loginFrame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Username");
		lblNewLabel_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblNewLabel_2_1.setBounds(38, 104, 109, 16);
		loginFrame.getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Password:");
		lblNewLabel_2_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblNewLabel_2_1_1.setBounds(38, 133, 109, 16);
		loginFrame.getContentPane().add(lblNewLabel_2_1_1);
		
		netIDInput = new JTextField();
		netIDInput.setColumns(10);
		netIDInput.setBounds(125, 100, 130, 26);
		loginFrame.getContentPane().add(netIDInput);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125, 127, 130, 26);
		loginFrame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(img);
		lblNewLabel.setBounds(144, 0, 306, 451);
		loginFrame.getContentPane().add(lblNewLabel);
		
		JButton logIn = new JButton("Log In");
		logIn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String passText = new String(passwordField.getPassword());
				String ID = netIDInput.getText();
				System.out.println(ID + " " +  passText);

				
				// System.out.println(passText + " " + dataP + " " + ID);
				
				// netID = ID;
				// databasePrefix = dataP;
				// password = passText;
				
				// Boolean checkConnection = false;
				
				if (ID.equals("username") && passText.equals("password") && Connection() == true)
				{

					JOptionPane.showMessageDialog(loginFrame, "Log In Success!");

					LoginPage demoObj = new LoginPage();
					
					demoObj.screen();
					loginFrame.dispose();
				}
				else if (ID.equals("username") == false || passText.equals("password") == false && Connection() == true)
				{
					JOptionPane.showMessageDialog(loginFrame, "Log In Failed, Incorrect Username Or Password");
				} 
				else
				{
					JOptionPane.showMessageDialog(loginFrame, "Log In Failure, Database Connection Not Available");
				}
			}
		});
		logIn.setBounds(102, 198, 71, 29);
		loginFrame.getContentPane().add(logIn);
	}
}
