package nightclub.web.nightclub.entities.dtos;

import java.time.LocalDate;

public class ShowReservationDTO {
    private String eventName;

    private LocalDate date;

    private int numberOfGuests;

    private String status;

    public String getEventName() {
        return eventName;
    }

    public ShowReservationDTO setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public ShowReservationDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public ShowReservationDTO setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ShowReservationDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
