package nightclub.web.nightclub.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tables")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String number;
    @Column(nullable = false)
    private Integer capacity;
    @Column(name = "is_available",nullable = false)
    private boolean isAvailable;
    @ManyToMany(mappedBy = "tables")
    private Set<Reservation> reservations;

    public TableEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public TableEntity setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }
}
