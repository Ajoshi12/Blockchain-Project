import java.util.ArrayList;
import java.util.Date;

public class Block
{
  private String Hash; // holds digital signiture
  private String PrevHash; // holds previous blocks hash
  private String Data; // holds data
  private String Sender; //holds name of Sender
  private String Reciever; //holds name of reciever
  private String TimeStamp; // time in system
  private int nonce;

  // constructor
  public Block(String Data, String Sender, String Reciever, String PrevHash)
  {
    this.Data = Data;
    this.PrevHash = PrevHash;
    this.Sender = Sender;
    this.Reciever = Reciever;
    this.TimeStamp = setTimeStamp();
    this.Hash = CalculateHash();
  }

  public String CalculateHash()
  {
    String CalcH = DigitalSign.SHA256(getPrevHash() + getTimeStamp() + Integer.toString(nonce) + getData()
    + getSender() + getReciever());
    return CalcH;
  }
  // set TimeStamp
  public String setTimeStamp()
  {
    Date timestamp_date = new Date();
    return timestamp_date.toString();
  }

// get methods
 public String getHash()
 {
   return Hash;
 }

 public String getData()
 {
   return Data;
 }

 public String getPrevHash()
 {
   return PrevHash;
 }

public String getSender()
{
  return Sender;
}

public String getReciever()
{
  return Reciever;
}

 public String getTimeStamp()
 {
   return TimeStamp;
 }

  public void MineBlock(int difficult)
  {
    // Create a string with difficulty * "0"
    String target = new String(new char[difficult]).replace('\0','0');
    while (!Hash.substring(0, difficult).equals(target))
    {
      nonce++;
      Hash = CalculateHash();
    }
    System.out.println("Block Mined: " + Hash);
  }

}
