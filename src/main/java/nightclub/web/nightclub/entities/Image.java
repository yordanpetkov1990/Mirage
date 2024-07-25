package nightclub.web.nightclub.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pathUrl;
    @ManyToOne()
    private Event event;

    public Long getId() {
        return id;
    }

    public Image setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public Image setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public Image setEvent(Event event) {
        this.event = event;
        return this;
    }
}
