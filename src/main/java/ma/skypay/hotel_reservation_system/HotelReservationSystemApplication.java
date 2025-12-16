package ma.skypay.hotel_reservation_system;

import ma.skypay.hotel_reservation_system.model.RoomType;
import ma.skypay.hotel_reservation_system.service.BookingService;
import ma.skypay.hotel_reservation_system.exception.BusinessException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class HotelReservationSystemApplication {

	public static void main(String[] args) {
		BookingService service = new BookingService();

		service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
		service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
		service.setRoom(3, RoomType.MASTER_SUITE, 3000);

		service.setUser(1, 5000);
		service.setUser(2, 10000);

		attemptBooking(service, 1, 2,
				LocalDate.of(2026, 6, 30),
				LocalDate.of(2026, 7, 7));

		attemptBooking(service, 1, 2,
				LocalDate.of(2026, 7, 7),
				LocalDate.of(2026, 6, 30));

		attemptBooking(service, 1, 1,
				LocalDate.of(2026, 7, 7),
				LocalDate.of(2026, 7, 8));

		attemptBooking(service, 2, 1,
				LocalDate.of(2026, 7, 7),
				LocalDate.of(2026, 7, 9));

		attemptBooking(service, 2, 3,
				LocalDate.of(2026, 7, 7),
				LocalDate.of(2026, 7, 8));

		service.setRoom(1, RoomType.MASTER_SUITE, 10000);

		service.printAll();
		service.printAllUsers();
	}

	private static void attemptBooking(BookingService service, int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
		try {
			service.bookRoom(userId, roomNumber, checkIn, checkOut);
			System.out.println("Booking succeeded: user=" + userId + " room=" + roomNumber + " " + checkIn + "->" + checkOut);
		} catch (BusinessException be) {
			System.out.println(be.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}
}