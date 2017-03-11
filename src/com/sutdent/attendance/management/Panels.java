package com.sutdent.attendance.management;

import static com.sutdent.attendance.management.DataSource.getConnection;
import static com.sutdent.attendance.management.DataSource.getStudentObject;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**This class is responsible to change the UI visibility.
 * @author Soundarya and Sowmya
 *
 */
public class Panels {
	
	private static  Vector<String> columnName = new Vector<String>();
	private static  DefaultTableModel defaultTableModel = null;
	private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
	static String[] coulmnNames = {"DATE","SUBJECT","TOTAL CLASSES ATTENDEND"};
	
	/**This method is used to set Student Panel Visibility.
	 * @param frame
	 * @param mainPanel
	 * @return
	 */
	public static JPanel getStudentAttendance(final JFrame frame , final JPanel mainPanel)
	{
		final JTextField textField;
		final JPanel studentMainPanel = new JPanel();
		studentMainPanel.setBounds(210, 100, 900, 500);
		studentMainPanel.setLayout(null);
		
		final JLabel lblNewLabel = new JLabel("Enter Date in DD-MM-YYYY");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(120, 350, 160, 16);
		studentMainPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(320, 350, 116, 22);
		studentMainPanel.add(textField);
		textField.setColumns(10);
		
		JButton dateSubmitButton = new JButton("Submit");
		dateSubmitButton.setBounds(550, 350, 97, 25);
		studentMainPanel.add(dateSubmitButton);
		//action is performed to get report by date.
		dateSubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				
				DefaultTableModel defaultTableModel = new DefaultTableModel(coulmnNames,0);
				if(DataSource.getDateWiseAttendance(defaultTableModel, textField.getText()))
				{
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 797, 438);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.getContentPane().setLayout(null);
					
					JPanel panel = new JPanel();
					panel.setBounds(24, 35, 715, 307);
					panel.setLayout(null);
					frame.getContentPane().add(panel);
					
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBounds(49, 24, 599, 179);
					panel.add(scrollPane);
					
					JTable table = new JTable();
					scrollPane.setViewportView(table);
					//table.setFillsViewportHeight(true);
					table.setModel(defaultTableModel);
					panel.setVisible(true);
					frame.setVisible(true);
					frame.getContentPane().add(panel);
				} else if(lblNewLabel.getText().length()==0){
					JOptionPane.showMessageDialog(frame, "Enter the date !");
				} else {
					JOptionPane.showMessageDialog(frame, "No Records found !");
				}
			}
		});
		
		JButton fullReportButton = new JButton("Submit");
		fullReportButton.setBounds(550, 200, 97, 25);
		studentMainPanel.add(fullReportButton);
		//Perform action to remove visibility of student main panel and turn on full report visibility.
		fullReportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				studentMainPanel.setVisible(false);
				getFullReportPane(frame,studentMainPanel);
			}
		});
		
		JLabel lblFullReport = new JLabel("Full Report");
		lblFullReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblFullReport.setBounds(150, 200, 97, 16);
		studentMainPanel.add(lblFullReport);
		
		JLabel lblWelcome = new JLabel("Welcome " + DataSource.getStudentObject().getFirstName() + 
				" "+ DataSource.getStudentObject().getLastName());
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(320, 9, 137, 90);
		studentMainPanel.add(lblWelcome);
		
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(700, 15, 83, 25);
		studentMainPanel.add(logoutButton);
		//This button is added to student main panel which makes login panel visibility to true.
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				//System.out.println("hello world");
				studentMainPanel.setVisible(false);
				mainPanel.setVisible(true);
				frame.add(mainPanel);
			}
		});
		//Add student to main frame.
		studentMainPanel.setVisible(true);
		frame.add(studentMainPanel);
		return studentMainPanel;
		
	}
	
	/**This panel shows student change password panel.
	 * @param isResetPassword
	 */
	public static void showChangePasswordPanel(final boolean isResetPassword)
	{
		final JPasswordField passwordField_1 = new JPasswordField();		
		JFrame 	frmChangePassword = new JFrame();
		frmChangePassword.setTitle("Change password");
		frmChangePassword.setBounds(100, 100, 512, 289);
		frmChangePassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChangePassword.getContentPane().setLayout(null);
		
		JLabel lblRollNo = new JLabel("Roll No");
		lblRollNo.setBounds(29, 35, 56, 16);
		frmChangePassword.getContentPane().add(lblRollNo);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(29, 109, 99, 16);
		frmChangePassword.getContentPane().add(lblNewPassword);
		
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(230, 106, 150, 22);
		frmChangePassword.getContentPane().add(passwordField);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(161, 176, 97, 25);
		frmChangePassword.getContentPane().add(submit);
		
		final JTextField textField = new JTextField();
		textField.setBounds(230, 32, 150, 22);
		textField.setColumns(10);
		frmChangePassword.getContentPane().add(textField);
		JLabel lblNewLabel = null;
		
		if(!isResetPassword)
		{
			lblNewLabel = new JLabel("Old Password");
			lblNewLabel.setBounds(29, 70, 99, 16);
			frmChangePassword.getContentPane().add(lblNewLabel);
			passwordField_1.setBounds(230, 67, 150, 22);
			frmChangePassword.getContentPane().add(passwordField_1);
		}
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isResetPassword && DataSource.
						changePassword(textField.getText(), String.valueOf(passwordField.getPassword()), String.valueOf(passwordField_1.getPassword())))
				{
					JOptionPane.showMessageDialog(null, "Sucessfully");
					passwordField_1.setText("");
				} else if(isResetPassword && DataSource.changePassword(textField.getText(), String.valueOf(passwordField.getPassword())))
				{
					JOptionPane.showMessageDialog(null, "Sucessfully");
				} else {
					JOptionPane.showMessageDialog(null, "Password not changed");
				}
				passwordField.setText("");
			}
		});
		
		frmChangePassword.setVisible(true);
	}
	
	//############# Creating full report panel ##############################
	
	/**This method is used to make student full report panel visibility true add turn off student main panel.
	 * @param frame
	 * @param studentMainPanel
	 * @return
	 */
	public static JPanel getFullReportPane(JFrame frame , final JPanel studentMainPanel)
	{
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(210, 100, 900, 500);
		ResultSet rs = null;
		Connection con = null;
		Statement st = null;
		try
		{
			JLabel label = new JLabel("Welcome " + DataSource.getStudentObject().getFirstName() + " " + DataSource.getStudentObject().getLastName());
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(300, 29, 137, 16);
			panel.add(label);
			
			JButton backButton = new JButton("Back");
			backButton.setBounds(300, 350, 97, 25);
			panel.add(backButton);
			
			backButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					studentMainPanel.setVisible(true);
					panel.setVisible(false);
					//columnName.clear();
					
				}
			});
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(60, 59, 710, 250);
			panel.add(scrollPane);
			System.out.println(DataSource.getStudentObject()
					.getBranch());
			
			defaultTableModel = new DefaultTableModel(coulmnNames, 0);
			//creating jtable.
			JTable table = new JTable(){
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					 super.isCellEditable(row, column);
					return false;
				}
			};
			scrollPane.setViewportView(table);
			table.setModel(defaultTableModel);
			table.setFont(new Font("Tahoma", Font.PLAIN, 14));
			
			//Insert total class data in text area.
			JTextArea textArea = new JTextArea();
			textArea.setBounds(450, 320, 150, 100);
			JButton btnNewButton = new JButton("New button");
			panel.add(btnNewButton);
			
			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent paramActionEvent) {
					studentMainPanel.setVisible(true);
					panel.setVisible(false);
					columnName.clear();
				}
			});
		
			String query = "SELECT dated, ATTENDANCE as att ,subject FROM CSE where rollno='"+ getStudentObject().getRollNo()+"' order by dated ASC";
			
			System.out.println(query);
			con	= getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if(!rs.isBeforeFirst())
			{
				frame.getContentPane().add(panel);
				JOptionPane.showMessageDialog(frame, "No Records Found");
				
			} else {
				while(rs.next())
				{
					defaultTableModel.addRow(new String[]{rs.getString("dated"),rs.getString("subject"),rs.getString("att")});
				}
				int t[] = DataSource.getTotalClasses();
				float per = ((float)t[1]/t[0])*100;
				textArea.setText("Attendance percentage \n\nTotal Attended : "+t[1]+"\nTotal Classees : "+t[0]+"\nPercentage : "+decimalFormat.format(per) );
				textArea.setEditable(false);
				panel.add(textArea);
			}
	
			frame.getContentPane().add(panel);
		} catch(Exception e)
		{
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
		finally {
			DataSource.closeResources(con, st, rs);
		}
		return panel;
	}
	
	
	/**This method makes administrator panel visible.
	 * @param frame
	 * @param jPanel
	 */
	public static void showAdminPanel(final JFrame frame , final JPanel jPanel)
	{
		final JComboBox<String> comboBox = new JComboBox<String>();
		final JComboBox<String> regulation = new JComboBox<String>();
		defaultTableModel = new DefaultTableModel(columnName,0);
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(49, 35, 1200, 600);
		
		JLabel label = new JLabel("Welcome");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(500, 29, 137, 16);
		panel.add(label);
		
		JButton button = new JButton("Logout");
		button.setBounds(1050, 39, 83, 25);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				columnName.clear();
				panel.setVisible(false);
				jPanel.setVisible(true);
				frame.add(jPanel);
				frame.validate();
			}
		});
		
		JButton button_1 = new JButton("Update Student Info");
		button_1.setBounds(200, 530, 150, 25);
		panel.add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				showUpdateStudentInfoPanel(frame, panel);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 130, 850, 320);
		panel.add(scrollPane);
		final JTable	table = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				super.isCellEditable(row, column);
				if(column==1)
					return 	false;
				return true;
			}
		};
		
		table.setModel(defaultTableModel);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setToolTipText("Insert Selected Record");
		btnNewButton.setBounds(400, 530, 97, 25);
		panel.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				if(table.getSelectedRows().length>0)
				{
					if(JOptionPane.showConfirmDialog(frame, "Submit select records ?","Confirmation Dialog ",JOptionPane.YES_NO_OPTION)==0)
					{
						String branchyearSem [] = comboBox.getItemAt(comboBox.getSelectedIndex()).split("-");
						for(int i : table.getSelectedRows())
						{
							for(int j=2;j<table.getColumnCount();j++)
							{
								try {
									DataSource.insert(branchyearSem[0],defaultTableModel.getValueAt(i,0).toString(),(String)defaultTableModel.getValueAt(i, 1),defaultTableModel.getColumnName(j),(String)defaultTableModel.getValueAt(i, j),regulation.getItemAt(regulation.getSelectedIndex()),branchyearSem[1]);
								} catch (SQLException e) {
									e.printStackTrace();
									JOptionPane.showMessageDialog(frame, e.getMessage());
									break;
								}
							}
						}
					}
				}
				
			}
		});
			
		JButton btnNewButton_1 = new JButton("Reset Psw");
		btnNewButton_1.setBounds(550, 530, 97, 25);
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showChangePasswordPanel(true);
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Invalidate");
		btnNewButton_2.setToolTipText("Invalidate Student Account");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				String rollNo = JOptionPane.showInputDialog("Invalidate");
				if(!"".equals(rollNo))
				{
					if(JOptionPane.showConfirmDialog(frame, 
							"Do you want to deactivate ?", "Deactivate",JOptionPane.YES_NO_OPTION)==0)
					{
						if(DataSource.deactivateUser(rollNo))
						{
							JOptionPane.showMessageDialog(frame, "Deactivated !");
						}  else {
							JOptionPane.showMessageDialog(frame, "Incorrect !");
						}
								
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Enter user name !");
				}
			}
		});
		btnNewButton_2.setBounds(700, 530, 97, 25);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Export Report");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramActionEvent) {
				
				final JTextField yearSemester  = new JTextField();		
				final JFrame 	exportReport = new JFrame();
				exportReport.setTitle("Export Report");
				exportReport.setBounds(100, 100, 512, 289);
				exportReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				exportReport.getContentPane().setLayout(null);
				
				JLabel lblRollNo = new JLabel("Branch");
				lblRollNo.setBounds(29, 35, 56, 16);
				exportReport.getContentPane().add(lblRollNo);
				
				JLabel batch = new JLabel("Batch (2013-2017,2014-2019))");
				batch.setBounds(29, 109, 200, 16);
				exportReport.getContentPane().add(batch);
				
				final JTextField batchJtextFiled = new JTextField();
				batchJtextFiled.setBounds(230, 106, 150, 22);
				batchJtextFiled.setColumns(5);
				
				exportReport.getContentPane().add(batchJtextFiled);
				
				JButton submit = new JButton("Submit");
				submit.setBounds(161, 176, 97, 25);
				exportReport.getContentPane().add(submit);
				
				final JTextField brachJTextFiled = new JTextField();
				brachJTextFiled.setBounds(230, 32, 150, 22);
				brachJTextFiled.setColumns(10);
				exportReport.getContentPane().add(brachJTextFiled);
				
				JLabel lblNewLabel = new JLabel("Year-Sem");
				lblNewLabel.setBounds(29, 70, 99, 16);
				exportReport.getContentPane().add(lblNewLabel);
				
				yearSemester.setBounds(230, 67, 150, 22);
				exportReport.getContentPane().add(yearSemester);
				
				submit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String branch  = brachJTextFiled.getText();
						String yearSem = yearSemester.getText();
						String batch = batchJtextFiled.getText();
						System.out.println(branch + " " + yearSem + " " + batch);
						
					if(branch!=null && yearSem!=null && yearSem.contains("-") && batch!=null && batch.contains("-"))
					{
							Connection con = null;
							Statement st = null;
							ResultSet rs = null;
							FileWriter fr = null;
							String temp[] = yearSem.split("-");
							try {
								String query = "select dated,rollno,subject,attendance as att from "+branch+" where year=\'"+temp[0]+"\' and sem=\'"+temp[1]+"\'and batch=\'"+batch+"\' order by dated asc";
								System.out.println(query);
								con = getConnection();
								st = con.createStatement();
								rs = st.executeQuery(query);
								if(rs.isBeforeFirst())
								{
									File f = new File("C:/Reports/"+branch+"-"+yearSem+ "-" + new SimpleDateFormat("MM-dd-yyyy-HHMMss").format(new Date()) +".csv");
									f.getParentFile().mkdir();
									f.createNewFile();
									fr = new FileWriter(f);
									fr.write("DATE,ROLL NO,SUBJECT,ATTENDANCE");
									fr.write("\n");
									while(rs.next())
									{
										fr.write(rs.getString("dated")+","+rs.getString("rollno")+","+rs.getString("subject")+","+rs.getString("att"));
										fr.write("\n");
									}
									fr.flush();
									fr.close();
									DataSource.closeResources(con, st, rs);
									JOptionPane.showMessageDialog(null, "Reort exported at : " + f.getAbsolutePath());
;								} else if(fr==null)
								{
									JOptionPane.showMessageDialog(frame, "No Records Found !");
								}
								
							} catch (Exception ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(frame, ex.getMessage());
							}
							
						} else {
							JOptionPane.showMessageDialog(frame, "Incorrect Information provided !");
						}
					}
				});
				
				exportReport.setVisible(true);
			}
		});
		
	
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				columnName.clear();
				columnName.addAll(Arrays.asList(Utilites.getColumnMap().get(comboBox.getSelectedItem())));
				defaultTableModel = new DefaultTableModel(columnName, 0);
				table.setModel(defaultTableModel);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"CSE-1", "CSE-2.1", "CSE-2.2", "CSE-3.1", "CSE-3.2", "CSE-4.1", "CSE-4.2", "ECE-1", "ECE-2.1", "ECE-2.2", "ECE-3.1", "ECE-3.2", "ECE-4.1", "ECE-4.2", "EEE-1", "EEE-2.1", "EEE-2.2", "EEE-3.1", "EEE-3.2", "EEE-4.1", "EEE-4.2", "IT-1", "IT-2.1", "IT-2.2", "IT-3.1", "IT-3.2", "IT-4.1", "IT-4.2"}));
		comboBox.setBounds(1050, 165, 75, 22);
		panel.add(comboBox);
	
		regulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				defaultTableModel.setRowCount(0);
				/*columnName.clear();
				columnName.addAll(Arrays.asList(Utilites.getColumnMap().get(comboBox.getSelectedItem())));
				defaultTableModel = new DefaultTableModel(columnName, 0);
				table.setModel(defaultTableModel);*/
				String yearSem = comboBox.getItemAt(comboBox.getSelectedIndex());
				String batch = regulation.getItemAt(regulation.getSelectedIndex()).split("-")[0].substring(2);
				ArrayList<String> rollNoList = DataSource.getRollNos(batch,yearSem);
				for(String l : rollNoList)
				{
					defaultTableModel.addRow(new String[]{"",l});
				}
					
			}
		});
		regulation.setModel(new DefaultComboBoxModel<String>(new String[] {"2013-2017", "2014-2018", "2015-2019", "2016-2020"}));
		regulation.setBounds(1050, 285, 80, 22);
		panel.add(regulation);
		
		JLabel label_1 = new JLabel("Year-Sem");
		label_1.setBounds(1050, 130, 56, 16);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Batch");
		label_2.setBounds(1050, 250, 60, 16);
		panel.add(label_2);
		
		btnNewButton_3.setBounds(850, 530, 120, 25);
		panel.add(btnNewButton_3);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				defaultTableModel.setRowCount(0);
			}
		});
		frame.getContentPane().add(panel);
	}
	
	public static void clearUNPSWFields(Object o,Object o2)
	{
		((JTextField)o).setText("");
		((JPasswordField)o2).setText("");
	}
	
	/**This method shows student information panel visibilty.
	 * @param mainFrame
	 * @param adminPanle
	 */
	public static void showUpdateStudentInfoPanel(final JFrame mainFrame , final JPanel adminPanle)
	{
		final JPanel panel = new JPanel();
		panel.setBounds(300, 150, 715, 307);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ROLL NO");
		lblNewLabel.setBounds(116, 75, 56, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Year");
		lblNewLabel_1.setBounds(116, 104, 74, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Semester");
		lblNewLabel_2.setBounds(116, 133, 74, 16);
		panel.add(lblNewLabel_2);
		
		
		final JTextField textField = new JTextField();
		textField.setBounds(336, 72, 180, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		final JTextField textField_1 = new JTextField();
		textField_1.setBounds(336, 101, 180, 22);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		final JTextField	textField_2 = new JTextField();
		textField_2.setBounds(336, 130, 180, 22);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Update Student Information");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(224, 13, 216, 29);
		panel.add(lblNewLabel_5);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(274, 269, 97, 25);
		panel.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(DataSource.updateSudentInfo(textField.getText(),textField_1.getText(),
						textField_2.getText()))
				{
					JOptionPane.showMessageDialog(null, "Succesfully updated !");
				} else {
					JOptionPane.showMessageDialog(null, "Error updating record !");			
				}
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBounds(581, 16, 97, 25);
		panel.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				panel.setVisible(false);;
				adminPanle.setVisible(true);
				mainFrame.remove(panel);
				mainFrame.add(adminPanle);
				return;
			}
		});
		
		adminPanle.setVisible(false);
		panel.setVisible(true);
		mainFrame.add(panel);
		
	}
}
