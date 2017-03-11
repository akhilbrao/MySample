package com.sutdent.attendance.management;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**This class initializes the application main JFrame.
 * @author Soundarya and sowmya
 *
 */
public class GUI {

	private JFrame frmSams;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPanel panel = new JPanel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmSams.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSams = new JFrame();
		frmSams.setResizable(false);
		frmSams.getContentPane().setBackground(Color.GRAY);
		frmSams.setForeground(Color.BLACK);
		frmSams.setType(Type.NORMAL);
		frmSams.setTitle("STUDENT ATTENDANCE MANAGEMENT SYSTEM");
		//frmSams.getContentPane().setBackground(Color.GRAY);
		frmSams.setBounds(100, 100, 1300, 750);
		frmSams.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSams.getContentPane().setLayout(null);
		
		
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(0, 153, 255));
		panel.setForeground(Color.GRAY);
		panel.setBounds(57, 61, 1173, 558);
		frmSams.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel userNameLable = new JLabel("User Name");
		userNameLable.setFont(new Font("Tahoma", Font.BOLD, 14));
		userNameLable.setBounds(321, 129, 77, 16);
		panel.add(userNameLable);
		
		JLabel unpswLabel = new JLabel("Password");
		unpswLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		unpswLabel.setBounds(321, 185, 77, 16);
		panel.add(unpswLabel);
		
		userNameField = new JTextField();
		userNameField.setBounds(568, 127, 167, 22);
		panel.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(568, 183, 167, 22);
		panel.add(passwordField);
		
		
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"STUDENT", "PROFESSOR"}));
		comboBox.setBounds(318, 307, 105, 22);
		panel.add(comboBox);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(510, 305, 97, 25);
		panel.add(btnNewButton);
		
		JButton btnChangeStudentPassword = new JButton("Change student Password");
		btnChangeStudentPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnChangeStudentPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Panels.showChangePasswordPanel(false);
			}
		});
		btnChangeStudentPassword.setBounds(661, 305, 195, 25);
		panel.add(btnChangeStudentPassword);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String userType = comboBox.getItemAt(comboBox.getSelectedIndex());
				if(userNameField.getText().length()==0 || passwordField.getPassword().length==0)  {
					JOptionPane.showMessageDialog(null, "Enter user name and password");
				} else if(DataSource.authenticateUser(userNameField.getText(),
						String.valueOf(passwordField.getPassword()),userType))
					{
						if("STUDENT".equalsIgnoreCase(userType))
						{
							Panels.clearUNPSWFields(userNameField, passwordField);
							Panels.getStudentAttendance(frmSams , panel);
							panel.setVisible(false);
							frmSams.remove(panel);
						} else {
							Panels.clearUNPSWFields(userNameField, passwordField);
							Panels.showAdminPanel(frmSams , panel);
							panel.setVisible(false);
							frmSams.remove(panel);
						}
					} else {
						//JOptionPane.showMessageDialog(frmSams, "Invalid user name or password");
					}
				} 
		});
	
	}
}
