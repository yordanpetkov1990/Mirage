package nightclub.web.nightclub.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDate date;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "entry_fee")

    private BigDecimal entryFee;
    private Integer capacity;
    @ManyToMany
    @JoinTable(
            name = "event_singer",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    private Set<Singer> singers;
    @OneToMany(mappedBy = "event")
    private List<Image> images;

    public Event() {
        this.singers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Event setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public Event setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Event setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Event setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Event setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public Event setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Event setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public Set<Singer> getSingers() {
        return singers;
    }

    public Event setSingers(Set<Singer> singers) {
        this.singers = singers;
        return this;
    }
}
