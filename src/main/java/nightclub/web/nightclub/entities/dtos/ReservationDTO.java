package nightclub.web.nightclub.entities.dtos;

public class ReservationDTO {
    public String phoneNumber;
    public int guests;
    private Long eventId;

    private String eventName;


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

    public Long getEventId() {
        return eventId;
    }

    public ReservationDTO setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    public ReservationDTO setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }
}
