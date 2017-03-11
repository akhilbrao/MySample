package com.sutdent.attendance.management;

import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import java.awt.Font;

public class Temp {

	private JFrame frame;
	static String[] coulmnNames = {"DATE","SUBJECT","TOTAL CLASSES ATTENDEND"};
	JPanel panel2 = new JPanel();
	Object[] column = new Object[]{"X","Y","Z"};
	Vector columnName = new Vector<>();
	{
		columnName.add("Date");
		columnName.add("Java");
		columnName.add("OS");
		columnName.add("DBMS");
		columnName.add("FLAT");
		columnName.add("CO");
		columnName.add("DW");
		
	}
	
	DefaultTableModel defaultTableModel = new DefaultTableModel(coulmnNames,1);
	Object[][] o = new Object[][]{{null,null,null,null,null,null}} ;
	private JTable table;
	private JScrollPane scrollPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Temp window = new Temp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Temp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DefaultTableModel defaultTableModel = new DefaultTableModel(coulmnNames,0);
		frame = new JFrame();
		frame.setBounds(100, 100, 797, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(24, 35, 715, 307);
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(265, 229, 121, 25);
		panel.add(btnUpdate);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 24, 599, 179);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setToolTipText("");
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"DATE", "SUBJECT", "TOTAL CLASSES ATTENDEND"
			}
		));
		
	}
}
