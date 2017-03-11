package com.sutdent.attendance.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * This class is responsible for Database connectivity.It contains helper methods to fetch information from the database
 * @author <b><i>Soundarya and sowmya</i></b>
 *
 */
public class DataSource {
	private static String DRIVERS = "oracle.jdbc.driver.OracleDriver";
	private static String CONNECTION_URL = "CONNECTION_URL";
	private static String DB_USER_NAME = "DB_USER_NAME";
	private static String DB_PSW = "DB_PSW";
	
	private static Properties dbProp = new Properties();
	private static Student student;
	static 
	{
		try {
			Class.forName(DRIVERS);
			dbProp.load(DataSource.class.getResourceAsStream("DBInfo.properties"));
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(dbProp.getProperty(CONNECTION_URL),dbProp.getProperty(DB_USER_NAME),dbProp.getProperty(DB_PSW));
	}
	
	public static void closeResources(Connection con,Statement st ,ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
			if(st!=null)
			{
				st.close();
			}
			if(con!=null)
			{
				con.close();
			}
			System.out.println("Resourcers cosed");
		}catch(Exception e)
		{
			System.out.println("Troubel in closing rsources : " + e);
		}
	}
	
	public static boolean changePassword(String userName , String psw, String oldPsw)
	{
		System.out.println("jkajlj");
		int i=0;
		try {
		String updateQuery = "update studentinfo set psw = '" + Utilites.encrypt(psw) + "' where rollno = '"+ userName + "' and psw ='"+ Utilites.encrypt(oldPsw) + "'";
		Connection con = DataSource.getConnection();
		con.prepareStatement("", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Statement st = con.createStatement();
		i = st.executeUpdate(updateQuery);
		DataSource.closeResources(con, st, null);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace());
			e.printStackTrace();
		}
		return i==1;
	}
	
	
	public static boolean changePassword(String userName , String psw)
	{
		int i=0;
		try {
		String updateQuery = "update studentinfo set psw = '" + Utilites.encrypt(psw) + "' where rollno = '"+ userName + "'";
		System.out.println(updateQuery);
		Connection con = DataSource.getConnection();
		Statement st = con.createStatement();
		i = st.executeUpdate(updateQuery);
		DataSource.closeResources(con, st, null);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace());
			e.printStackTrace();
		}
		return i==1;
	}
	
	
	public static boolean authenticateUser(String userName, String psw, String userType) {
		boolean isAuthenticated = false;
		try {
			Connection con = DataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = null;
			
			if ("STUDENT".equals(userType)) {
			rs = st.executeQuery("select * from studentinfo where rollno ='" + userName + "'");
				String retrievedPsw = "";
				student = new Student();
				while (rs.next()) {
					retrievedPsw = rs.getString("psw");
					student.setBranch(rs.getString("branch"));
					student.setFirstName(rs.getString("FirstName"));
					student.setLastName(rs.getString("LastName"));
					student.setYear(rs.getString("year"));
					student.setSem(rs.getString("sem"));
					student.setRegulation(rs.getString("Regulation"));
					student.setRollNo(rs.getString("rollno"));
				}
				System.out.println(retrievedPsw);
				isAuthenticated = Utilites.decrypt(retrievedPsw).equals(psw);
				closeResources(con, st, rs);
			} else {
				 rs = st.executeQuery("select psw from admin where username ='" + userName + "'");
				String retrievedPsw = "";
				while (rs.next()) {
					retrievedPsw = rs.getString("psw");
				}
				closeResources(con, st, rs);
				isAuthenticated = Utilites.decrypt(retrievedPsw).equals(psw);
			}
		} catch (Exception e) {
			JTextArea ja = new JTextArea(20,30);
			ja.setText(e.getMessage());
			JScrollPane bar = new JScrollPane();
			bar.getViewport().setView(ja);
			JOptionPane.showMessageDialog(null, bar);
		}
		return isAuthenticated;
	}
	
	public static boolean updateSudentInfo(String ...args)
	{
		int i=0;
		try {
		String updateQuery = "update studentinfo set YEAR ='"+ args[1]+"',SEM ='"+ args[2]+"'"
				+ " where rollno = '"+ args[0] + "'";
		System.out.println(updateQuery);	
		
		Connection con = DataSource.getConnection();
		Statement st = con.createStatement();
		i = st.executeUpdate(updateQuery);
		DataSource.closeResources(con, st, null);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i==1;
	}
	
	
	
	public static Student getStudentObject() {
		return student;
	}
	
	public static void insert(String ...args) throws SQLException {
		String yearSem[] = new String[2];
		if(args[6].contains("."))
		{
			yearSem = args[6].split("\\.");
		} else {
			yearSem[0] = args[6];
			yearSem[1] = "";
		}
		Connection con =getConnection();
		//dated-roll-subject-att-batch-year-sem
		String s = "insert into "+args[0]+"  VALUES (?,?,?,?,?,?,?)";
		System.out.println(s);
		PreparedStatement st = con.prepareStatement(s);
		
		st.setString(1,args[1]);
		st.setString(2,args[2]);
		st.setString(3, args[3]);
		st.setString(4,args[4]==null?"na":args[4]);
		st.setString(5, args[5]);
		st.setString(6, yearSem[0]);
		st.setString(7, yearSem[1]);
		st.execute();
	}
	
	public static ArrayList<String> getRollNos(String batch , String yearSem)
	{
		String branch  = yearSem.substring(0, 3);
		System.out.println(branch);
		String temp[]  = new String[2];
		if(yearSem.contains("."))
		{
			temp = yearSem.substring(yearSem.indexOf("-")+1).split("\\.");
		} else {
			temp[0] = yearSem.substring(yearSem.indexOf("-")+1);
			temp[1] = "";
		}
		System.out.println(temp[0]);
		ArrayList<String> rollNoList = null;
		try {
			String query = "select rollno from studentinfo where"
					+ " rollno like \'" + batch + "%\' and year=\'"+ temp[0] +"\'and sem=\'"+ temp[1] +"\' and branch='"+branch+"\'";
			System.out.println(query);
			
			Connection con	= getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.isBeforeFirst())
			{
				rollNoList = new ArrayList<>();
				while(rs.next())
				{
					rollNoList.add(rs.getString("rollno"));
				}
			} else {
				JOptionPane.showMessageDialog(null, "No Records Found");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rollNoList==null?new ArrayList<String>() : rollNoList;
	}
	
	public static boolean getDateWiseAttendance(DefaultTableModel defaultTableModel,String date)
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			String query = "SELECT dated,COUNT(ATTENDANCE) as att ,subject FROM "+ getStudentObject().getBranch()+" where rollno='"+ getStudentObject().getRollNo()+"' and dated =\'"+date+"\'group by dated,subject order by dated ASC";
			System.out.println(query);
			con	= getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			if(!rs.isBeforeFirst())
			{
				return false;
			} else {
				while(rs.next())
				{
					defaultTableModel.addRow(new String[]{rs.getString("dated"),rs.getString("subject"),rs.getString("att")});
				}
			}
			return true;
		}catch(Exception e)
		{
			return false;
		}
		
	}
	
	public static boolean deactivateUser(String rollNo)
	{
		System.out.println("jkajlj");
		int i=0;
		try {
		String updateQuery = "update studentinfo set psw = '.' where rollno = '"+ rollNo + "'";
		Connection con = DataSource.getConnection();
		Statement st = con.createStatement();
		i = st.executeUpdate(updateQuery);
		DataSource.closeResources(con, st, null);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getStackTrace());
			e.printStackTrace();
		}
		return i==1;
	}
	
	public static int[] getTotalClasses()
	{
		String totalClassQuery = "select count(*) as tot from "+ getStudentObject().getBranch()+" where rollno=\'"+getStudentObject().getRollNo()+"\' and year=\'"+getStudentObject().getYear()+"\' and sem=\'"+getStudentObject().getSem()+"\' and (attendance='P' OR attendance='A')";
		String totalAttenQuery = "select count(*) as tot from "+ getStudentObject().getBranch()+" where rollno=\'"+getStudentObject().getRollNo()+"\' and year=\'"+getStudentObject().getYear()+"\' and sem=\'"+getStudentObject().getSem()+"\' and attendance='P'";		
		int totClassArray[] = new int[2];
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			System.out.println(totalClassQuery);
			con	= getConnection();
			st = con.createStatement();
			rs = st.executeQuery(totalClassQuery);
			while(rs.next())
			{
				totClassArray[0]= Integer.parseInt((rs.getString("tot")));
			}
			closeResources(null, null, rs);
			rs = st.executeQuery(totalAttenQuery);
			while(rs.next())
			{
				totClassArray[1]= Integer.parseInt(rs.getString("tot"));
			}
		}catch(Exception e)
		{
		}
		finally
		{
			closeResources(con, st, rs);
		}
		return totClassArray;
		
	}
	public static void main(String[] args) {
		
	}
	

}
