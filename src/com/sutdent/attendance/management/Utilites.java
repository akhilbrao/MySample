package com.sutdent.attendance.management;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Utilites {
	
	private static Map<String, String[]> columnMap = new HashMap<String, String[]>();
	private static final String ALGORITHM = "AES";
	private static final String KEY = "HiNZ!P1*oQStYc_u";

	static {
		columnMap.put("CSE-1", new String[] { "DATE", "ROLLNO", "CP", "M1", "PHY", "CHEM", "MM", "ED" });
		columnMap.put("CSE-2.1", new String[] { "DATE", "ROLLNO", "DS", "MFCS", "DLD", "BEE", "EDC", "PS" });
		columnMap.put("CSE-2.2", new String[] { "DATE", "ROLLNO", "JAVA", "DBMS", "DAA", "FLAT", "CO", "ES" });
		columnMap.put("CSE-3.1", new String[] { "DATE", "ROLLNO", "PPL", "DM", "SE", "CD", "OS", "CN" });
		columnMap.put("CSE-3.2", new String[] { "DATE", "ROLLNO", "MPI", "IS", "OOAD", "STM", "MEFA", "WT" });
		columnMap.put("CSE-4.1", new String[] { "DATE", "ROLLNO", "LP", "DP", "DM", "CC", "ELECTIVE-1", "ELECTIVE-2" });
		columnMap.put("CSE-4.2", new String[] { "DATE", "ROLLNO", "MS", "ELECTIVE-3", "ELECTIVE-4" });

		columnMap.put("ECE-1", new String[] { "DATE", "ROLLNO", "CP", "M1", "PHY", "CHEM", "MM", "ED" });
		columnMap.put("ECE-2.1", new String[] { "DATE", "ROLLNO", "M3", "PTSP", "STLD", "EC", "EDC", "SS" });
		columnMap.put("ECE-2.2", new String[] { "DATE", "ROLLNO", "PEE", "ECA", "PDC", "EScience", "ETTL", "VHDL" });
		columnMap.put("ECE-3.1", new String[] { "DATE", "ROLLNO", "CS", "CO", "AWP", "EMI", "AC", "LDIA" });
		columnMap.put("ECE-3.2", new String[] { "DATE", "ROLLNO", "MEFA", "HVPE", "DM", "IPR", "VLSID", "MM" });
		columnMap.put("ECE-4.1", new String[] { "DATE", "ROLLNO", "MS", "ME", "CN","CMC","ELECTIVE1","ELECTIVE2" });
		columnMap.put("ECE-4.2", new String[] { "DATE", "ROLLNO", "ELECTIVE3", "ELECTIVE4", "ELECTIVE5" });

		columnMap.put("EEE-1", new String[] { "DATE", "ROLLNO", "CP", "M1", "PHY", "CHEM", "MM", "ED" });
		columnMap.put("EEE-2.1", new String[] {"DATE","ROLLNO","M3","FMHM","EDC","EC","EF","EM1"});
		columnMap.put("EEE-2.2", new String[] {"DATE","ROLLNO","MEFA","PowerSys1","CS","STLD","NT","EM2	"});
		columnMap.put("EEE-3.1", new String[] {"DATE","ROLLNO","ICApplication","MS","PowerSys2","CS","PE","EM3"});
		columnMap.put("EEE-3.2", new String[] {"DATE","ROLLNO","EEI","SD","CMPS","MPI","DM","ES"});
		columnMap.put("EEE-4.1", new String[] {"DATE","ROLLNO","SGP","UEE","DSP","PSOC","ELECTIVE1","ELECTIVE2"});
		columnMap.put("EEE-4.2", new String[] {"DATE","ROLLNO","FHFD","ELECTIVE3","ELECTIVE4","ELECTIVE5"});

		columnMap.put("IT-1", new String[] { "DATE", "ROLLNO", "CP", "M1", "PHY", "CHEM", "MM", "ED" });
		columnMap.put("IT-2.1", new String[] { "DATE", "ROLLNO", "DS", "MFCS", "DLD", "BEE", "EDC", "PS" });
		columnMap.put("IT-2.2", new String[] { "DATE", "ROLLNO", "PPL", "DBMS", "JAVA", "DC", "DAA", "ES" });
		columnMap.put("IT-3.1", new String[] { "DATE", "ROLLNO", "ACD", "LP", "SE", "MEFA", "OS", "CN" });
		columnMap.put("IT-3.2", new String[] { "DATE", "ROLLNO", "WT", "IPS", "OOAD", "STM", "DWDM", "STM" });
		columnMap.put("IT-4.1", new String[] { "DATE", "ROLLNO", "IS", "DP", "MAD", "IRS", "ELECTIVE-1", "ELECTIVE-2" });
		columnMap.put("IT-4.2", new String[] { "DATE", "ROLLNO", "MS", "ELECTIVE-3", "ELECTIVE-4" });
	}


	public static Map<String, String[]> getColumnMap() {
		return columnMap;
	}

	public static String encrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(Utilites.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		String encryptedValue64 = new Base64().encodeAsString(encryptedByteValue);
		return encryptedValue64;

	}

	public static String decrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(Utilites.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedValue64 = new Base64().decode(value);
		byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
		String decryptedValue = new String(decryptedByteValue, "utf-8");
		return decryptedValue;

	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(Utilites.KEY.getBytes(), Utilites.ALGORITHM);
		return key;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(decrypt("eJTF0rM9eto6LKp7VcoQVg=="));
	}

}
