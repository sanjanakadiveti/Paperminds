import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteTest {
    private static Connection c;
    private static boolean hasData = false;
    public Connection returnConn() {
        return c;
    }
    
    public ResultSet displayTitles() throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        Statement state = c.createStatement();
        ResultSet res = state.executeQuery("SELECT title FROM notes");
        return res;
    }
    public ResultSet displayEntries() throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        Statement state = c.createStatement();
        ResultSet res = state.executeQuery("SELECT entry FROM notes");
        return res;
    }
    
    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
        initialise();
    }
    
    private void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;
            Statement state = c.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='notes'");
            if (!res.next() ) {
                System.out.println("Building the notes table with prepopulated values");
                //need to build a table
                Statement state2 = c.createStatement();
                state2.executeQuery("CREATE TABLE notes(id integer,"
                                    + "title varchar(60),"
                                    + "entry varchar(1000),"
                                    + "primary key(id));");
                //inserting some data
                PreparedStatement prep = c.prepareStatement("INSERT INTO notes values(?,?,?);");
                prep.setString(2, "HarryPotter");
                prep.setString(3, "random entry one");
                prep.execute();
                
                PreparedStatement prep2 = c.prepareStatement("INSERT INTO notes values(?,?,?);");
                prep2.setString(2, "East");
                prep2.setString(3, "random entry two");
                prep2.execute();
            }
        }
    }
    
    public void addNote(String title) throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        PreparedStatement prep = c.prepareStatement("INSERT INTO notes values(?,?,?);");
        prep.setString(2, title);
        prep.setString(3, "");
        prep.execute();
    }
    public void addNote(String title, String entry) throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        PreparedStatement prep = c.prepareStatement("INSERT INTO notes values(?,?,?);");
        prep.setString(2, title);
        prep.setString(3, entry);
        prep.execute();
    }
    public void deleteNote(String hhhh) throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        PreparedStatement prep = c.prepareStatement("DELETE FROM notes");
        prep.execute();
    }
    public String getEntry(String t) throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        String sql = "SELECT entry FROM notes WHERE title = ?";
        PreparedStatement prep = c.prepareStatement(sql);
        prep.setString(1, t);
        ResultSet res = prep.executeQuery();
        //Statement state = c.createStatement();
        //ResultSet res = state.executeQuery("SELECT entry FROM notes WHERE title = 'happy'");
        return res.getString("entry");
    }
    public void update(String t, String n, String m) throws ClassNotFoundException, SQLException {
        if (c == null) {
            getConnection();
        }
        String sql = "UPDATE notes SET title = ?, entry = ? WHERE title = ?";
        PreparedStatement prep = c.prepareStatement(sql);
        prep.setString(1, n);
        prep.setString(2, m);
        prep.setString(3, t);
        prep.execute();
    }
}
