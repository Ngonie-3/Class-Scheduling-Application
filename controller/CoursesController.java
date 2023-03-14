package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Course;
import model.DatabaseConnection;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {
    @FXML
    private JFXButton coursesLoadDataBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, Integer> courseNumberColumn;

    @FXML
    private TableColumn<Course, String> lecturersColumn;

    @FXML
    private TableColumn<Course, String> maxHeadcount;

    @FXML
    private TableColumn<Course, String> nameColumn;

    ObservableList<Course> courseObservableList = FXCollections.observableArrayList();

    @FXML
    void courseLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String coursesQuery = "SELECT * FROM uctp.courses";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(coursesQuery);
            while(resultSet.next()){
                String courseNo = resultSet.getString("course_num");
                String courseName = resultSet.getString("course_name");
                int maxCapacity = resultSet.getInt("course_capacity");
                String lecturers = resultSet.getString("course_lecturers");
                courseObservableList.add(new Course(courseNo, courseName, maxCapacity, lecturers));
                courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
                lecturersColumn.setCellValueFactory(new PropertyValueFactory<>("lecturers"));
                maxHeadcount.setCellValueFactory(new PropertyValueFactory<>("maxSeatingCapacity"));
                courseTable.setItems(courseObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.courses WHERE course_capacity > ?");
                preparedStatement.setInt(1, 0);
                ResultSet rs = preparedStatement.executeQuery();
                int n = 0;
                if(rs.next()){
                    n = rs.getInt(1);
                }
                if(n>0){
                    if(!(courseTable.getItems().isEmpty())){
                        coursesLoadDataBtn.setOnAction(actionEvent -> {
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
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void search(){
        FilteredList<Course> filteredList = new FilteredList<>(courseObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> filteredList.setPredicate(course -> {
            if(newValue.isEmpty()){
                return true;
            }
            String searchResult = newValue.toLowerCase();
            if(course.getCourseName().toLowerCase().contains(searchResult)){
                return true;
            }
            if(course.getCourseNumber().toLowerCase().contains(searchResult)){
                return true;
            }
            return course.getLecturers().toLowerCase().contains(searchResult);
        }));
        SortedList<Course> searchData = new SortedList<>(filteredList);
        searchData.comparatorProperty().bind(courseTable.comparatorProperty());
        courseTable.setItems(searchData);
    }
}
