package Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.solit.timeit.dao.UserDAO;

public class Crypto
{
	private static MessageDigest	digester;
	private static final Logger		LOGGER	= LoggerFactory.getLogger(UserDAO.class);

	static
	{
		try
		{
			digester = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e)
		{
			LOGGER.error("Failed to create encryptor", e);
		}
	}

	public static String encrypt(String str)
	{
		if (str == null || str.length() == 0)
		{
			throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
		}

		digester.update(str.getBytes());
		byte[] hash = digester.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++)
		{
			if ((0xff & hash[i]) < 0x10)
			{
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			}
			else
			{
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}
}
