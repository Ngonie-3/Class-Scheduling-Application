package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UniversityDataController implements Initializable {
    @FXML
    private ImageView menuButton;
    @FXML
    private AnchorPane anchorPane, pane1;
    @FXML
    private JFXButton departmentsButton, lecturersButton, meetingTimeButton, coursesButton, courseRegistrationButton;

    @FXML
    private JFXButton examRegistrationButton, roomsButton;

    @FXML
    private Parent root;

    @FXML
    private Scene scene;

    @FXML
    private Stage stage;

    @FXML
    private Label goBack;

    @FXML
    private VBox myVBox, buttonsVBox;

    @FXML
    void roomsButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Rooms.fxml")));
            myVBox.getChildren().add(root);
            roomsButton.setStyle("-fx-text-fill: #FFFFFF;");
            lecturersButton.setStyle("/stylesheets.Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets/Dashboard.css");
            coursesButton.setStyle("/stylesheets.Dashboard.css");
            departmentsButton.setStyle("/stylesheets.Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets.Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void lecturersButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Lecturers.fxml")));
            myVBox.getChildren().add(root);
            lecturersButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets.Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets.Dashboard.css");
            coursesButton.setStyle("/stylesheets.Dashboard.css");
            departmentsButton.setStyle("/stylesheets.Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets.Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void meetingTimeButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MeetingTime.fxml")));
            myVBox.getChildren().add(root);
            meetingTimeButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets/Dashboard.css");
            lecturersButton.setStyle("/stylesheets/Dashboard.css");
            coursesButton.setStyle("/stylesheets/Dashboard.css");
            departmentsButton.setStyle("/stylesheets/Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets/Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void coursesButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Courses.fxml")));
            myVBox.getChildren().add(root);
            coursesButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets/Dashboard.css");
            lecturersButton.setStyle("/stylesheets/Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets/Dashboard.css");
            departmentsButton.setStyle("/stylesheets.Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets.Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void departmentsButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Departments.fxml")));
            myVBox.getChildren().add(root);
            departmentsButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets/Dashboard.css");
            lecturersButton.setStyle("/stylesheets.Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets.Dashboard.css");
            coursesButton.setStyle("/stylesheets.Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets.Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void courseRegistrationButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Schedule.fxml")));
            myVBox.getChildren().add(root);
            courseRegistrationButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets.Dashboard.css");
            lecturersButton.setStyle("/stylesheets.Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets.Dashboard.css");
            coursesButton.setStyle("/stylesheets.Dashboard.css");
            departmentsButton.setStyle("/stylesheets.Dashboard.css");
            examRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void examRegistrationButtonPressed() {
        try {
            myVBox.getChildren().removeAll(myVBox.getChildren());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ExamRegistration.fxml")));
            myVBox.getChildren().add(root);
            examRegistrationButton.setStyle("-fx-text-fill: #FFFFFF;");
            roomsButton.setStyle("/stylesheets.Dashboard.css");
            lecturersButton.setStyle("/stylesheets.Dashboard.css");
            meetingTimeButton.setStyle("/stylesheets.Dashboard.css");
            coursesButton.setStyle("/stylesheets.Dashboard.css");
            departmentsButton.setStyle("/stylesheets.Dashboard.css");
            courseRegistrationButton.setStyle("/stylesheets.Dashboard.css");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void goToLogin(MouseEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TimeTableLogin.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 707, 463);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setOpacity(0.0);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setDelay(Duration.seconds(1));
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setNode(anchorPane);
        fadeTransition.play();

        pane1.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane1);
        translateTransition.setByX(-600);
        translateTransition.play();

        menuButton.setOnMouseClicked(mouseEvent -> {
            pane1.setVisible(true);
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), pane1);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

            TranslateTransition translate = new TranslateTransition(Duration.seconds(1.5), pane1);
            translate.setByX(600);
            translate.play();

            translate.setOnFinished(event ->{
                menuButton.setOnMouseClicked(actionEvent ->{
                    TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), pane1);
                    transition.setByX(-600);
                    transition.play();
                    transition.setOnFinished(finishEvent -> menuButton.setOnMouseClicked(event1->{
                        if(!buttonsVBox.getChildren().isEmpty()){
                            TranslateTransition bringBack = new TranslateTransition(Duration.seconds(1.5), pane1);
                            bringBack.setByX(600);
                            bringBack.play();
                            bringBack.setOnFinished(mouseEvent1-> menuButton.setOnMouseClicked(clickedEvent->{
                                TranslateTransition hide = new TranslateTransition(Duration.seconds(1.5), pane1);
                                hide.setByX(-600);
                                hide.play();
                            }));
                        }
                    }));
                });
            });
        });
    }
}
