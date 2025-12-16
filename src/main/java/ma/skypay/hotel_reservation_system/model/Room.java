package ma.skypay.hotel_reservation_system.model;

public class Room {
    private final int roomNumber;
    private RoomType roomType;
    private int pricePerNight;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void update(RoomType roomType, int pricePerNight) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{number=" + roomNumber +
                ", type=" + roomType +
                ", pricePerNight=" + pricePerNight + '}';
    }
}
