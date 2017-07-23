import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
public class ListScreen extends BorderPane {
    private ListView<String> listView;
    private VBox listBox;
    private VBox writeBox;
    private HBox currentBottom;
    private Text t;
    private HBox topWrite;
    private HBox topRead;
    private TextArea currentDisplayed;
    private Button newThought;

	public ListScreen() throws ClassNotFoundException, SQLException {
        topRead = new HBox();
        topRead.setPadding(new Insets(0, 12, 0, 12));
        topRead.setSpacing(10);
        topRead.setStyle("-fx-background-color: darkorange;");
        topRead.setAlignment(Pos.CENTER_LEFT);
        t = new Text("read");
        t.setFont(Font.font ("Apple Chancery", 20));
        t.setFill(Color.WHITE);
        topRead.getChildren().clear();
        topRead.getChildren().addAll(t);
        listBox = new VBox();
        listBox.getChildren().addAll(topRead, getListView());
        this.setLeft(listBox);

        writeBox = new VBox();
        topWrite = new HBox();
        topWrite.setPadding(new Insets(0, 12, 0, 0));
        topWrite.setSpacing(5);
        topWrite.setStyle("-fx-background-color: darkorange;");
        Text t = new Text("write");
        t.setFont(Font.font ("Apple Chancery", 20));
        t.setFill(Color.WHITE);
        topWrite.getChildren().addAll(addNewButton(), t);
        writeBox.getChildren().addAll(topWrite);
        this.setCenter(getWriteVbox());
	}

	public Button addNewButton() {
        newThought = new Button("+");
        newThought.setOnMouseClicked(ev -> {
            try {
                writeBox.getChildren().removeAll(currentDisplayed, currentBottom);
                this.setCenter(getWriteVbox());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        return newThought;
    }

    public VBox getWriteVbox() throws ClassNotFoundException, SQLException {
        TextArea thoughtWritten = new TextArea();
        thoughtWritten.setPrefColumnCount(41);
        thoughtWritten.setPrefRowCount(25);

        writeBox.getChildren().addAll(thoughtWritten, addThought(thoughtWritten));
        currentDisplayed = thoughtWritten;
        return writeBox;
    }

	public HBox addThought(TextArea t) {
	    HBox bottomAdd = new HBox();
        bottomAdd.setPadding(new Insets(5, 12, 5, 12));
        bottomAdd.setSpacing(10);
        bottomAdd.setStyle("-fx-background-color: orange;");
	    Button addNewThought = new Button("submit thought");
	    addNewThought.setOnMouseClicked(e -> {
            SQLiteTest test = new SQLiteTest();
            try {
                test.addNote(t.getText().substring(0, t.getText().indexOf(" ")), t.getText());
                updateListView();
                writeBox.getChildren().removeAll(currentDisplayed, currentBottom);
                this.setCenter(getWriteVbox());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
	    currentBottom = bottomAdd;
	    bottomAdd.getChildren().addAll(addNewThought);
	    return bottomAdd;
    }
    public void updateListView() throws ClassNotFoundException, SQLException {
	    listBox.getChildren().removeAll(listView);
        listBox.getChildren().addAll(getListView());
        this.setLeft(listBox);
    }
	public ListView<String> getListView() throws ClassNotFoundException, SQLException {
        ObservableList titles = FXCollections.observableArrayList();

        SQLiteTest test = new SQLiteTest();
        ResultSet res;
        res = test.displayTitles();
        while (res.next()) {
            titles.add(res.getString("title"));
        }
        listView = new ListView();
        listView.setPrefSize(200, 450);
        listView.setEditable(true);
        listView.setItems(titles);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new MouseClickListCell();
            }

        });
        return listView;
	}
    public void displayThought(String title) throws ClassNotFoundException, SQLException {
        listView.getSelectionModel().clearSelection();
        SQLiteTest test = new SQLiteTest();
        String thought = test.getEntry(title);
        TextArea toShow = new TextArea(thought);
        toShow.setPrefColumnCount(41);
        toShow.setPrefRowCount(25);
        writeBox.getChildren().removeAll(currentDisplayed, currentBottom);
        currentDisplayed = toShow;
        writeBox.getChildren().addAll(toShow, updateThought(title, toShow));
    }

    public HBox updateThought(String title, TextArea t) {
        HBox bottomUpdate = new HBox();
        bottomUpdate.setPadding(new Insets(5, 12, 5, 12));
        bottomUpdate.setSpacing(10);
        bottomUpdate.setStyle("-fx-background-color: orange;");
        Button updateThought = new Button("update thought");
        updateThought.setOnMouseClicked(e -> {
            SQLiteTest test = new SQLiteTest();
            try {
                test.update(title, t.getText().substring(0, t.getText().indexOf(" ")), t.getText());
                updateListView();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        bottomUpdate.getChildren().addAll(updateThought);
        currentBottom = bottomUpdate;
        return bottomUpdate;
    }
    public class MouseClickListCell<T> extends ListCell<T>
    {
        @Override
        protected void updateItem(T item, boolean empty)  {
            super.updateItem(item, empty);

            if(empty) {
                setText(null);
                setOnMouseClicked(null);
            }
            else {
                setText(item.toString());
                setOnMouseClicked(ev -> {
                    try {
                        displayThought(listView.getSelectionModel().getSelectedItem());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }
}
