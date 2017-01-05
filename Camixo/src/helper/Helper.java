package helper;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import dbJPA.Uporabnik;

public class Helper {
	
	public static String vrniLepoOblikoDatuma(Timestamp d)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("dd. MMMM yyyy, HH:mm");
		return sdf.format(d);
	}
	
	public static String sumniki(String x)
	{
		char[] sumniki={'è','È','š','Š','ž','Ž','æ','Æ'};
		String[] replace={"&#269;","&#268;","&#353;","&#352;","&#382;","&#381;","&#263;","&#262;"};
		String tmp="";
		boolean found;
		for(int i=0;i<x.length();i++)
		{
			found=false;
			for(int j=0;j<sumniki.length;j++)
			{
				if(x.charAt(i)==sumniki[j])
				{
					tmp+=replace[j];
					found=true;
					break;
				}
			}
			if(!found)
				tmp+=x.charAt(i);
		}
		return tmp;
	}
	
	public static String kodirajGeslo(String password, String salt) 
	{
		return DigestUtils.sha512Hex(salt+password);
	}
	
	public static String generirajSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return Hex.encodeHex(salt).toString();
    }
	
	public static boolean logged(HttpServletRequest req)
	{
		HttpSession session=req.getSession();
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
		if(u==null) return false;
		else return true;
	}
	
	public static boolean loggedAdmin(HttpServletRequest req)
	{
		HttpSession session=req.getSession();
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
		if(u==null) 
			return false;
		if(u.getTipUporabnika()!=1)
			return false;
		
		return true;
	}

}
