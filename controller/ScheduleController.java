package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.DatabaseConnection;
import model.Schedule;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    @FXML
    private JFXButton scheduleLoadDataBtn;

    @FXML
    private TableColumn<Schedule, Integer> classNumColumn;

    @FXML
    private TableColumn<Schedule, String> courseColumn;

    @FXML
    private TableColumn<Schedule, String> departmentColumn;

    @FXML
    private TableColumn<Schedule, String> lecturerColumn;

    @FXML
    private TableColumn<Schedule, String> meetingTimeColumn;

    @FXML
    private TableColumn<Schedule, CheckBox> registerColumn;

    @FXML
    private TableColumn<Schedule, String> roomColumn;

    @FXML
    private TableView<Schedule> scheduleTable;

    @FXML
    private TextField searchTextField;

    ObservableList<Schedule> scheduleObservableList = FXCollections.observableArrayList();

    @FXML
    void scheduleLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String scheduleQuery = "SELECT * FROM uctp.schedule";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(scheduleQuery);
            while(resultSet.next()){
                int classNumber = resultSet.getInt("class_num");
                String department = resultSet.getString("department");
                String course = resultSet.getString("course");
                String room = resultSet.getString("room");
                String lecturer = resultSet.getString("lecturer");
                String meetingTime = resultSet.getString("meeting_time");
                CheckBox checkBox = new CheckBox("");
                checkBox.setFocusTraversable(false);
                checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                    if(newValue){
                        Notifications notifications =  Notifications.create()
                                .text("Registration Successful!")
                                .position(Pos.TOP_RIGHT)
                                .hideAfter(Duration.minutes(5));
                        notifications.darkStyle();
                        notifications.showInformation();
                        checkBox.setDisable(true);
                    }
                });
                scheduleObservableList.add(new Schedule(classNumber, department, course, room, lecturer, meetingTime, checkBox));
                classNumColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));
                departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
                courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
                roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
                lecturerColumn.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
                meetingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("meetingTime"));
                registerColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
                scheduleTable.setItems(scheduleObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.schedule WHERE class_num > ?");
                preparedStatement.setInt(1, 0);
                ResultSet rs = preparedStatement.executeQuery();
                int num = 0;
                if(rs.next()){
                    num = rs.getInt(1);
                }
                if(num > 0){
                    if(!(scheduleTable.getItems().isEmpty())){
                        scheduleLoadDataBtn.setOnAction(actionEvent -> {
                            Notifications notifications = Notifications.create()
                                    .text("Data is up to date")
                                    .position(Pos.TOP_RIGHT)
                                    .hideCloseButton()
                                    .hideAfter(Duration.seconds(3));
                            notifications.darkStyle();
                            notifications.showInformation();
                        });
                    }
                }
                search();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void search(){
        FilteredList<Schedule> scheduleFilteredList = new FilteredList<>(scheduleObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> scheduleFilteredList.setPredicate(schedule -> {
            if(newValue.isEmpty()){
                return true;
            }
            String searchResult = newValue.toLowerCase();
            if(schedule.getDepartment().toLowerCase().contains(searchResult)){
                return true;
            }
            if(schedule.getCourse().toLowerCase().contains(searchResult)){
                return true;
            }
            if(schedule.getRoom().toLowerCase().contains(searchResult)){
                return true;
            }
            if(schedule.getLecturer().toLowerCase().contains(searchResult)){
                return true;
            }
            if(schedule.getMeetingTime().toLowerCase().contains(searchResult)){
                return true;
            }
            String classNumber = String.valueOf(schedule.getClassNumber());
            return classNumber.contains(searchResult);
        }));
        SortedList<Schedule> scheduleSortedList = new SortedList<>(scheduleFilteredList);
        scheduleSortedList.comparatorProperty().bind(scheduleTable.comparatorProperty());
        scheduleTable.setItems(scheduleSortedList);
    }
}
