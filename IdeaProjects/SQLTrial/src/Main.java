
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.text.Font;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SQLiteTest test = new SQLiteTest();
		ResultSet res;
		
		try {
			//System.out.println(test.getEntry("happy"));
			test.deleteNote("f");
			test.deleteNote("fff hahahaha");
			test.deleteNote("why");
			test.deleteNote("dddd");
			test.deleteNote("does it work");
			test.deleteNote("my");
			test.deleteNote("stillwierd?");
			test.deleteNote("submit");
			test.deleteNote("bottom");
			test.deleteNote("again");
			//test.addNote("MyTitle");

			res = test.displayTitles();
			while(res.next()) {
				//System.out.println(Font.getFamilies());
				//System.out.println(res.getString("title"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
