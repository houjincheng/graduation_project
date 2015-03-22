package anyviewj.client.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FilefingerPrint {
	MessageDigest md= null;
	static FilefingerPrint fp= new FilefingerPrint();
	
	FilefingerPrint(){
		try {
			md=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getMD5(File file){
		int len = (int) file.length();
		byte[] filein= new byte[len];
		FileInputStream a;
		try {
			a = new FileInputStream(file);
			a.read(filein);
			a.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] md5=null;
		md5=FilefingerPrint.fp.md.digest(filein);
		StringBuffer hexValue = new StringBuffer();
		int amd5;
		for(int i=0; i<md5.length; i++){
			amd5=md5[i] & 0xff;
			hexValue.append(Integer.toHexString(amd5));
		}
		
		return hexValue.toString();
	}
}
