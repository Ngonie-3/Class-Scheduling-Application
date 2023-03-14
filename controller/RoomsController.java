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
import model.Rooms;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RoomsController implements Initializable {

    @FXML
    private JFXButton roomsLoadDataBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Rooms> roomsTable;

    @FXML
    private TableColumn<Rooms, Integer> seatingCapacityColumn;

    @FXML
    private TableColumn<Rooms, String> roomNumberColumn;

    ObservableList<Rooms> roomsObservableList = FXCollections.observableArrayList();

    @FXML
    void roomsLoadDataBtnPressed() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String roomsQuery = "SELECT * FROM uctp.rooms";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(roomsQuery);
            while(resultSet.next()){
                String roomNum = resultSet.getString("room_number");
                int seatCapacity = resultSet.getInt("max_capacity");
                roomsObservableList.add(new Rooms(roomNum, seatCapacity));
                roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
                seatingCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("seatingCapacity"));
                roomsTable.setItems(roomsObservableList);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM uctp.rooms WHERE room_ID > ?");
                preparedStatement.setInt(1, 0);
                ResultSet rs = preparedStatement.executeQuery();
                int num = 0;
                if(rs.next()){
                    num = rs.getInt(1);
                }
                if(num > 0){
                    if(!(roomsTable.getItems().isEmpty())){
                        roomsLoadDataBtn.setOnAction(actionEvent -> {
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
        FilteredList<Rooms> roomsFilteredList = new FilteredList<>(roomsObservableList, b -> true);
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> roomsFilteredList.setPredicate(rooms -> {
            if(newValue.isEmpty()){
                return true;
            }
            String searchResult = newValue.toLowerCase();
            if(rooms.getRoomNumber().toLowerCase().contains(searchResult)){
                return true;
            }
            String maxSeatingCapacity = String.valueOf(rooms.getSeatingCapacity());
            return maxSeatingCapacity.toLowerCase().contains(searchResult);
        }));
        SortedList<Rooms> roomsSortedList = new SortedList<>(roomsFilteredList);
        roomsSortedList.comparatorProperty().bind(roomsTable.comparatorProperty());
        roomsTable.setItems(roomsSortedList);
    }
}
