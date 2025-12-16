package ma.skypay.hotel_reservation_system.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class Booking {
    private final int userId;
    private final int roomNumber;
    private final int pricePerNightAtBooking;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final long nights;
    private final long totalPrice;

    public Booking(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(room, "room must not be null");
        Objects.requireNonNull(checkIn, "checkIn must not be null");
        Objects.requireNonNull(checkOut, "checkOut must not be null");

        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("checkIn must be before checkOut");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) {
            throw new IllegalArgumentException("stay length must be positive");
        }

        this.userId = user.getId();
        this.roomNumber = room.getRoomNumber();
        this.pricePerNightAtBooking = room.getPricePerNight();
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.totalPrice = (long) pricePerNightAtBooking * nights;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getPricePerNightAtBooking() {
        return pricePerNightAtBooking;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public long getNights() {
        return nights;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Booking{userId=" + userId +
                ", room=" + roomNumber +
                ", pricePerNight=" + pricePerNightAtBooking +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", nights=" + nights +
                ", total=" + totalPrice + '}';
    }
}