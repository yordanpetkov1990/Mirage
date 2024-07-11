package nightclub.web.nightclub.entities.dtos;

public class ReservationDTO {
    public String phoneNumber;
    public int guests;
    private EventDetailsDTO eventDetailsDTO;
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

    public EventDetailsDTO getEventDetailsDTO() {
        return eventDetailsDTO;
    }

    public ReservationDTO setEventDetailsDTO(EventDetailsDTO eventDetailsDTO) {
        this.eventDetailsDTO = eventDetailsDTO;
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
