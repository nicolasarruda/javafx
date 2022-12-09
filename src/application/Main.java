package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private static String title = "Projeto JavaFX";
    private static Stage stage;
    private static Scene loginViewScene;
    private static Scene mainViewScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
	stage = primaryStage;

	Parent loginParent = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
	loginViewScene = new Scene(loginParent);

	Parent mainParent = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
	mainViewScene = new Scene(mainParent);
	mainViewScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	primaryStage.setScene(loginViewScene);
	primaryStage.setTitle(title);
	primaryStage.setResizable(false);
	primaryStage.show();
    }

    public static void setSceneLoginView() {
	stage.setScene(loginViewScene);
    }

    public static void setSceneMainView(FXMLLoader loader) throws IOException {
	Pane pane = loader.load();
	Scene scene = new Scene(pane);
	scene.getStylesheets().add("application/application.css");
	stage.setScene(scene);
    }

    public static void setSceneMainView() {
	stage.setScene(mainViewScene);
    }

    public static void setSceneFormView(FXMLLoader loader) throws IOException {
	Pane pane = loader.load();
	stage.setScene(new Scene(pane));
    }

    public static void main(String[] args) {
	launch(args);
    }
}
