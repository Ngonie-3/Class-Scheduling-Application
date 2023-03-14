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
import model.Lecturers;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LecturersController implements Initializable {
    @FXML
    private JFXButton lecturerLoadDataBtn;
    @FXML
    private TextField lecturerSearchTextField;
    @FXML
    private TableColumn<Lecturers, String> lecturerNameColumn;

    @FXML
    private TableColumn<Lecturers, String> lecturerIDColumn;

    @FXML
    private TableView<Lecturers> lecturersTable;

    ObservableList<Lecturers> lecturersObservableList = FXCollections.observableArrayList();
    @FXML
    void lecturerLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String lecturersQuery = "SELECT * FROM uctp.lecturers";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(lecturersQuery);
            while(resultSet.next()){
                String lecturerID = resultSet.getString("lecturer_ID");
                String lecturerName = resultSet.getString("lecturer_name");
                lecturersObservableList.add(new Lecturers(lecturerID, lecturerName));
                lecturerIDColumn.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));
                lecturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
                lecturersTable.setItems(lecturersObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.lecturers WHERE lecturer_ID = ?");
                preparedStatement.setString(1, "");
                ResultSet rs = preparedStatement.executeQuery();
                int num = 0;
                if(rs.next()){
                    num = rs.getInt(1);
                }
                if(num > 0){
                    if(!(lecturersTable.getItems().isEmpty())){
                        lecturerLoadDataBtn.setOnAction(actionEvent -> {
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
        FilteredList<Lecturers> lecturersFilteredList = new FilteredList<>(lecturersObservableList, b -> true);
        lecturerSearchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> lecturersFilteredList.setPredicate(lecturers -> {
           if(newValue.isEmpty()){
               return true;
           }
           String searchResult = newValue.toLowerCase();
           if(lecturers.getLecturerID().toLowerCase().contains(searchResult)){
               return true;
           }
           return lecturers.getLecturerName().toLowerCase().contains(searchResult);
        }));
        SortedList<Lecturers> lecturers = new SortedList<>(lecturersFilteredList);
        lecturers.comparatorProperty().bind(lecturersTable.comparatorProperty());
        lecturersTable.setItems(lecturers);
    }
}
