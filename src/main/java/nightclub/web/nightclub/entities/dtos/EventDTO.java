package nightclub.web.nightclub.entities.dtos;

import java.util.ArrayList;
import java.util.List;



public class EventDTO {
    private Long id;
    private String name;

    private String eventDate;

    private List<String> singers;

    public EventDTO(){
        singers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public EventDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEventDate() {
        return eventDate;
    }

    public EventDTO setEventDate(String eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public List<String> getSingers() {
        return singers;
    }

    public EventDTO setSingers(List<String> singers) {
        this.singers = singers;
        return this;
    }
}
