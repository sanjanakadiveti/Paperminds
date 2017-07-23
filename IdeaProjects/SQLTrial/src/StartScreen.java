import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
public class StartScreen extends StackPane {
	private Image backgroundImage;
	private Button entryButton;
	private VBox vertical;
	public StartScreen() {
		entryButton = new Button("Enter your mind's portal");
		//b.setStyle("-fx-focus-color: -fx-control-inner-background ; -fx-faint-focus-color: -fx-control-inner-background ;");
		entryButton.setStyle("-fx-font: 22 arial; -fx-base: #F19735;");

		backgroundImage = new Image("File:./src/startImageOption.jpg");
		vertical = new VBox(200);
		Text t = new Text(50, 70, "welcome, Sanjana");
		t.setFont(Font.font ("Times New Roman", 40));
		t.setFill(Color.AZURE);
		
		vertical.setPadding(new Insets(50, 0, 100, 0));
		vertical.setAlignment(Pos.BOTTOM_CENTER);
		vertical.getChildren().addAll(t, entryButton);

		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setFitHeight(400);
		backgroundImageView.setFitWidth(400);
		
		this.getChildren().addAll(backgroundImageView, vertical);
	}
	public Button getEntryButton() {
		return entryButton;
	}
}