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
import model.MeetingTime;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MeetingTimeController implements Initializable {
    @FXML
    private JFXButton meetingTimeLoadDataBtn;

    @FXML
    private TableColumn<MeetingTime, String> meetingTimeColumn;

    @FXML
    private TableColumn<MeetingTime, String> meetingTimeIDColumn;

    @FXML
    private TableView<MeetingTime> meetingTimeTable;

    @FXML
    private TextField searchTextField;

    ObservableList<MeetingTime> meetingTimeObservableList = FXCollections.observableArrayList();

    @FXML
    void meetingTimeLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String metingTimeQuery = "SELECT * FROM uctp.meeting_time";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(metingTimeQuery);
            while(resultSet.next()){
                String meetingID = resultSet.getString("time_ID");
                String meetingTime = resultSet.getString("meeting_time");
                meetingTimeObservableList.add(new MeetingTime(meetingID, meetingTime));
                meetingTimeIDColumn.setCellValueFactory(new PropertyValueFactory<>("meetingTimeID"));
                meetingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("meetingTime"));
                meetingTimeTable.setItems(meetingTimeObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.meeting_time WHERE time_ID = ?");
                preparedStatement.setString(1, "");
                ResultSet rs = preparedStatement.executeQuery();
                int value = 0;
                if(rs.next()){
                    value = rs.getInt(1);
                }
                if(value > 0){
                    if(!(meetingTimeTable.getItems().isEmpty())){
                        meetingTimeLoadDataBtn.setOnAction(actionEvent -> {
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
        FilteredList<MeetingTime> meetingTimeFilteredList = new FilteredList<>(meetingTimeObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> meetingTimeFilteredList.setPredicate(meetingTime -> {
            if(newValue.isEmpty()){
                return true;
            }
            String searchResult = newValue.toLowerCase();
            if(meetingTime.getMeetingTimeID().toLowerCase().contains(searchResult)){
                return true;
            }
            return meetingTime.getMeetingTime().toLowerCase().contains(searchResult);
        }));
        SortedList<MeetingTime> meetingTimes = new SortedList<>(meetingTimeFilteredList);
        meetingTimes.comparatorProperty().bind(meetingTimeTable.comparatorProperty());
        meetingTimeTable.setItems(meetingTimes);
    }
}
