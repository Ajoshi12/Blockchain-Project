
import java.security.MessageDigest;

public class DigitalSign
{
  public static String SHA256(String input)
  {
    try
    {

      MessageDigest D = MessageDigest.getInstance("SHA-256");
      byte[] hash = D.digest(input.getBytes("UTF-8"));
      // to get hex value as a hash

      StringBuffer hex = new StringBuffer();
      for(int i = 0; i < hash.length ; i++)
      {
        String h = Integer.toHexString(0xff & hash[i]);
        if (h.length() == 1)
        {
          hex.append('0');
        }
        hex.append(h);
      }
      return hex.toString();
    }
    catch(Exception e)
     {
       throw new RuntimeException(e);
     }

  }

  public static String getdifficulty(int difficult)
  {
    // returns a string by replacing null values in a char array
    // with the length difficult with 0's
    return new String(new char[difficult]).replace('\0','0');
  }

}
