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
import model.DatabaseConnection;
import model.Department;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DepartmentsController implements Initializable {
    @FXML
    private JFXButton departmentLoadDataBtn;
    @FXML
    private TextField searchDepartment;
    @FXML
    private TableColumn<Department, String> departmentNameColumn;
    @FXML
    private TableColumn<Department, String> departmentCoursesColumn;
    @FXML
    private TableView<Department> departmentTable;

    ObservableList<Department> departmentObservableList = FXCollections.observableArrayList();
    @FXML
    void departmentLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String departmentQuery = "SELECT * FROM uctp.departments";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(departmentQuery);
            while(resultSet.next()){
                String departmentName = resultSet.getString("department_name");
                String departmentCourses = resultSet.getString("department_courses");
                departmentObservableList.add(new Department(departmentName, departmentCourses));
                departmentCoursesColumn.setCellValueFactory(new PropertyValueFactory<>("departmentCourses"));
                departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
                departmentTable.setItems(departmentObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.departments WHERE department_ID > ?");
                preparedStatement.setInt(1, 0);
                ResultSet rs = preparedStatement.executeQuery();
                int value = 0;
                if(rs.next()){
                    value = rs.getInt(1);
                }
                if(value > 0){
                    if(!(departmentTable.getItems().isEmpty())){
                        departmentLoadDataBtn.setOnAction(actionEvent -> {
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
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void search(){
        FilteredList<Department> departmentFilteredList = new FilteredList<>(departmentObservableList);
        searchDepartment.textProperty().addListener((observableValue, oldValue, newValue) -> departmentFilteredList.setPredicate(department -> {
            if(newValue.isEmpty()){
                return true;
            }
            String searchResult = newValue.toLowerCase();
            if(department.getDepartmentName().toLowerCase().contains(searchResult)){
                return true;
            }
            return department.getDepartmentCourses().toLowerCase().contains(searchResult);
        }));
        SortedList<Department> sortedList = new SortedList<>(departmentFilteredList);
        sortedList.comparatorProperty().bind(departmentTable.comparatorProperty());
        departmentTable.setItems(sortedList);
    }
}
