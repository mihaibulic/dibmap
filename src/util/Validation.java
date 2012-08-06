package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Validation
{
	private boolean verify(String pw, byte[] correctHash, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		try
		{
			Thread.sleep(1000); //slow down
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} 
		
		byte[] hash = getHash(pw,salt);
		boolean equal = (hash.length == correctHash.length);
		
		for(int x = 0; equal && x < hash.length; x++)
		{
			equal = (hash[x] == correctHash[x]);
		}
		
		return equal;
	}

	public static byte[] getHash(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return getHash(pw, getRandomKey(64));
	}
	
	public static byte[] getHash(String pw, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		int itterations = 5000;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        digest.reset();
        digest.update(salt.getBytes("UTF-8"));
        digest.update(pw.getBytes("UTF-8"));
        byte input[] = digest.digest();
        for (int i = 1; i < itterations; i++) 
        {
            input = digest.digest(input);
        }
        
        return input;
	}

	public static String getRandomKey(int length)
	{
		StringBuilder sb = new StringBuilder(System.currentTimeMillis() + "");
		SecureRandom random = new SecureRandom();
		
		if(length < sb.length())
		{
			throw new RuntimeException("The length for this key was set to " + length + 
					" while the minimum is " + sb.length());
		}
		
		while(sb.length() < length)
		{
			sb.append((char)(random.nextInt(26) + 'a'));
		}
		
		if(sb.length() > length)
		{
			sb.substring(0, length);
		}

		return sb.toString();
	}	
}
