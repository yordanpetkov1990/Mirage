package nightclub.web.nightclub.entities.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EditEventDTO {

    private Long id;
    @NotBlank
    @Size(min = 5,max = 20)
    private String name;
    @NotBlank
    @Size(min = 20,max = 100)
    private String description;
    @FutureOrPresent
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double entryFee;
    private Integer capacity;
    private List<String> singersName;
    public EditEventDTO(){
        this.singersName = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<String> getSingersName() {
        return singersName;
    }

    public void setSingersName(List<String> singersName) {
        this.singersName = singersName;
    }

    public Long getId() {
        return id;
    }

    public EditEventDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
