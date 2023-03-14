package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class TimeTableLoginController implements Initializable {

    @FXML
    private Button logInBtn;
    @FXML
    private Label loginSuccess;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField userNameTextField;
    @FXML
    private ImageView offButton;
    @FXML
    private Parent root;
    @FXML
    private HBox imageHBox;
    @FXML
    private Scene scene;

    @FXML
    private Stage stage;


    @FXML
    void logInBtnPressed(ActionEvent event) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String verifyLogin = "SELECT count(1) FROM uctp.user_login WHERE user_name = '"+userNameTextField.getText()+"' AND user_password = '"+passwordField.getText()+"'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);
            while(resultSet.next()){
                if(resultSet.getInt(1)==1){
                    loginSuccess.setText("Login Successful!");
                    loginSuccess.setStyle("-fx-text-fill: #3AC666; -fx-font-size: 18px; -fx-font-family: Calibri");
                    userNameTextField.getStyleClass().add("loginSuccess");
                    passwordField.getStyleClass().add("loginSuccess");
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UniversityData.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root, 1318, 601);
                    stage.setTitle("Home");
                    FadeTransition fadeTransition = new FadeTransition();
                    fadeTransition.setDelay(Duration.seconds(1));
                    fadeTransition.setNode(anchorPane);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.setOnFinished(actionEvent -> {
                        stage.setScene(scene);
                        stage.show();
                        stage.centerOnScreen();
                    });
                    fadeTransition.play();
                }
                else{
                    loginSuccess.setText("Login Failed!");
                    loginSuccess.setStyle("-fx-text-fill: #EB3A0B; -fx-font-size: 18px; -fx-font-family: Calibri");
                    userNameTextField.getStyleClass().add("loginFailed");
                    passwordField.getStyleClass().add("loginFailed");
                    Alert failAlert = new Alert(Alert.AlertType.ERROR);
                    failAlert.setHeaderText("Error trying to Login");
                    failAlert.setContentText("Wrong username or password!");
                    failAlert.showAndWait();
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setOpacity(0.0);
        FadeTransition fadeInTransition = new FadeTransition();
        fadeInTransition.setDuration(Duration.seconds(1));
        fadeInTransition.setDelay(Duration.seconds(1));
        fadeInTransition.setNode(anchorPane);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();

        offButton.setOnMouseClicked(clickEvent -> System.exit(0));

        offButton.setOnMouseEntered(mouseEvent -> {
            offButton.setStyle("-fx-background-color: #EE250C; -fx-cursor: hand");
            imageHBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#EE250C"), null, null)));
        });

        offButton.setOnMouseExited(mouseEvent1 -> {
            offButton.setStyle("-fx-background-color: #ffffff");
            imageHBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#ffffff"), null, null)));
        });
    }

}
