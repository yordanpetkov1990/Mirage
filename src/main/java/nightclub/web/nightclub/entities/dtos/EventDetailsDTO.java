package nightclub.web.nightclub.entities.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nightclub.web.nightclub.entities.Singer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDetailsDTO {

    private Long id;
    @NotBlank
    @Size(min = 5,max = 20)
    private String name;
    @NotBlank()
    @Size(min = 20)
    private String description;
    @FutureOrPresent
    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;


    private BigDecimal entryFee;
    private Integer capacity;
    private Set<SingerDTO> singers;
    private Set<String> singersName;

    private List<String> imageUrls;

    public EventDetailsDTO(){
        this.singers = new HashSet<>();
        this.singersName = new HashSet<>();
        this.imageUrls = new ArrayList<>();
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public EventDetailsDTO setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    public Set<String> getSingersName() {
        return singersName;
    }

    public EventDetailsDTO setSingersName(Set<String> singersName) {
        this.singersName = singersName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EventDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public EventDetailsDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public EventDetailsDTO setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public EventDetailsDTO setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public BigDecimal getEntryFee() {
        return entryFee;
    }

    public EventDetailsDTO setEntryFee(BigDecimal entryFee) {
        this.entryFee = entryFee;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public EventDetailsDTO setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public Set<SingerDTO> getSingers() {
        return singers;
    }

    public EventDetailsDTO setSingers(Set<SingerDTO> singers) {
        this.singers = singers;
        return this;
    }

    @Override
    public String toString() {
        return  name + " - "+ date;

    }


}
