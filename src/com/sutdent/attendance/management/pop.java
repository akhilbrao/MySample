package com.sutdent.attendance.management;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;

@SuppressWarnings("unused")
public class pop {

	private JFrame frmChangePassword;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pop window = new pop();
					window.frmChangePassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public pop() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame frmChangePassword = new JFrame();
		frmChangePassword.setTitle("Reset Password");
		frmChangePassword.setBounds(100, 100, 512, 289);
		frmChangePassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChangePassword.getContentPane().setLayout(null);
		
		JLabel lblRollNo = new JLabel("Roll No");
		lblRollNo.setBounds(34, 60, 56, 16);
		frmChangePassword.getContentPane().add(lblRollNo);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(29, 109, 99, 16);
		frmChangePassword.getContentPane().add(lblNewPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(230, 106, 150, 22);
		frmChangePassword.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(171, 176, 97, 25);
		frmChangePassword.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(230, 57, 150, 22);
		frmChangePassword.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
