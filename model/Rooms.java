package model;

public class Rooms {
    String roomNumber;
    int seatingCapacity;

    public Rooms(String roomNumber, int seatingCapacity) {
        this.roomNumber = roomNumber;
        this.seatingCapacity = seatingCapacity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }
}
