import java.util.ArrayList;
import java.security.Security;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chain
{
public static ArrayList<Block> blockchain = new ArrayList<Block>();
public static int difficult = 4;

  public static void main(String[] args)
  {
    String userAns;
    String input_user;
    String Name_Sender;
    String Name_Reciever;
    int index = 0;
    int count =1;

    Scanner in = new Scanner(System.in);
    // do-while loop to process the blockchain
    do
    {
      System.out.println();
      System.out.println("Enter Amount: ");
      input_user = in.nextLine();

      System.out.println("Enter name of Sender: ");
      Name_Sender = in.nextLine();

      System.out.println("Enter name of Reciever: ");
      Name_Reciever = in.nextLine();

      if (index == 0)
      {
        blockchain.add(new Block(input_user, Name_Sender, Name_Reciever, "0"));
      }
      else
       {
        blockchain.add(new Block(input_user,Name_Sender, Name_Reciever,blockchain.get(blockchain.size()-1).getHash()));
      }
      System.out.println();
      System.out.println("Trying to Mine block " + count + "...");
      blockchain.get(index).MineBlock(difficult);
      System.out.println("Do you wish to continue? (type yes if you want to continue): ");
      userAns = in.nextLine();
      index++;
      count++;
    } while (userAns.equals("yes"));
    System.out.println();
    System.out.println();
    System.out.println("Now printing out the Blockchain:");
    System.out.println("================================");
    // after the loop print out data and check validation
    displayChain();
    System.out.println("Is the BlockChain valid? : " + IsValid() );

}

  public static void displayChain()
  {
    int num = 1;
    for(int i = 0; i < blockchain.size(); i++)
    {
      System.out.println("Block: " + num);
      System.out.println("Amount: " + blockchain.get(i).getData());
      System.out.println("Sender: " + blockchain.get(i).getSender());
      System.out.println("Reciever: " + blockchain.get(i).getReciever());
      System.out.println("Timestamp: " + blockchain.get(i).getTimeStamp());
      System.out.println("Previous Hash: " + blockchain.get(i).getPrevHash());
      System.out.println("Hash: " + blockchain.get(i).getHash());
      System.out.println();
      num++;
    }

  }

// method loops through all blocks in the chain and compare the hashes
  public static Boolean IsValid()
  {
    Block Current;
    Block Previous;
    String hashT = new String(new char[difficult]).replace('\0','0');
    // check hashes
    for (int i = 1; i < blockchain.size(); i++ )
    {
      Current = blockchain.get(i);
      Previous = blockchain.get(i-1);
      //compare registered hash and calculated hash:
      if (!Current.getHash().equals(Current.CalculateHash()) )
      {
        return false;
      }

      //compare previous hash and  previous hash of the current block
      if ( !Previous.getHash().equals(Current.getPrevHash()) )
      {
        return false;
      }

      // check if hash is solved by mining hash
      if (!Current.getHash().substring(0,difficult).equals(hashT))
      {
        return false;
      }
    }
    return true;
  }

  public static void myDB()
  {
     String driver = "com.mysql.jdbc.Driver";
     String connection = "jdbc:mysql://localhost:3306/User_Transactions"; //User_Transactions is the database name
     String user = "TransactionAdmin";
     String password = "password";   // using the safest password in the history of humanity!!!!!
     Connection con = null;
     Statement state = null;
     ResultSet result;
     PreparedStatement pstate;
     // for the connection to the database
     try
    {
        Class.forName(driver);
        con = DriverManager.getConnection(connection, user, password);
        System.out.println("Successfully connected to database.");
    }
    catch(ClassNotFoundException e)
    {
        System.err.println("Couldn't load driver.");
    }
    catch(SQLException e)
    {
        System.err.println("Couldn't connect to database.");
    }

     // now time to insert all the data from one session into the database
     try
     {
       for(int i = 0; i < blockchain.size(); i++)
        {
           pstate = con.prepareStatement("insert into AllTransactionHistory(Block, Amount, Reviever, Timestamp, Previous Hash, Hash)"+
                                           "values(?,?,?,?,?,?)");
           pstate.setString(1, blockchain.get(i).getData());
           pstate.setString(2, blockchain.get(i).getSender());
           pstate.setString(3, blockchain.get(i).getReciever());
           pstate.setString(4, blockchain.get(i).getTimeStamp());
           pstate.setString(5, blockchain.get(i).getPrevHash());
           pstate.setString(6, blockchain.get(i).getHash());
        }
           //int value = pstate.executeUpdate();
           }
       catch(SQLException e)
       {
           System.err.println("Insertion error");
       }

       // in order to close the connection
            try
            {
               if(!con.isClosed())
              {
               con.close();
               System.out.println("Database closed successfully.");
               }
           }
       catch(NullPointerException e)
       {
           System.err.println("Couldn't load driver.");
       }
       catch(SQLException e)
       {
           System.err.println("Couldn't close database.");
       }
  }


}
