package ma.skypay.hotel_reservation_system.service;

import ma.skypay.hotel_reservation_system.model.Booking;
import ma.skypay.hotel_reservation_system.model.Room;
import ma.skypay.hotel_reservation_system.model.RoomType;
import ma.skypay.hotel_reservation_system.model.User;
import ma.skypay.hotel_reservation_system.exception.BusinessException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingService {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final List<Booking> bookings = new ArrayList<>();

    public void setRoom(int roomNumber, RoomType type, int pricePerNight) {
        rooms.put(roomNumber, new Room(roomNumber, type, pricePerNight));
    }

    public void setUser(int userId, int balance) {
        users.put(userId, new User(userId, balance));
    }

    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new BusinessException("Dates must be provided");
        }
        if (!checkIn.isBefore(checkOut)) {
            throw new BusinessException("Check-in must be before check-out");
        }

        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new BusinessException("Room not found");
        }

        User user = users.get(userId);
        if (user == null) {
            throw new BusinessException("User not found");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) {
            throw new BusinessException("Invalid stay length");
        }

        for (Booking existing : bookings) {
            if (existing.getRoomNumber() != roomNumber) continue;
            if (existing.getCheckIn().isBefore(checkOut) && checkIn.isBefore(existing.getCheckOut())) {
                throw new BusinessException("Room is not available");
            }
        }

        long totalPrice = (long) room.getPricePerNight() * nights;
        if (user.getBalance() < totalPrice) {
            throw new BusinessException("Insufficient balance");
        }

        Booking booking = new Booking(user, room, checkIn, checkOut);
        bookings.add(booking);

        user.setBalance((int) (user.getBalance() - totalPrice));
    }

    public void printAll() {
        System.out.println("Rooms:");
        rooms.values().forEach(System.out::println);

        System.out.println("\nBookings:");
        bookings.forEach(System.out::println);
    }

    public void printAllUsers() {
        System.out.println("Users:");
        users.values().forEach(System.out::println);
    }
}