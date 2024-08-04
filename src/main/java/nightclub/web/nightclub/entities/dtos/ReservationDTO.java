package nightclub.web.nightclub.entities.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class ReservationDTO {
    public String phoneNumber;
    @Min(value = 8,message = "If you want to make a reservation you need to be at least 8 people")
    public int guests;
    private String  eventName;
    private Long eventId;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ReservationDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public int getGuests() {
        return guests;
    }

    public ReservationDTO setGuests(int guests) {
        this.guests = guests;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    public ReservationDTO setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public Long getEventId() {
        return eventId;
    }

    public ReservationDTO setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }
}
