package nightclub.web.nightclub.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private User owner;
    @ManyToOne()
    private Event event;
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany()
    private Set<TableEntity> tables;

    public Reservation() {
        tables = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<TableEntity> getTables() {
        return tables;
    }

    public void setTables(Set<TableEntity> tables) {
        this.tables = tables;
    }
}
