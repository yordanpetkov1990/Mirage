package nightclub.web.nightclub.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddJobDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100,min = 3,message = "Title must be between 3 and 100 symbols")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 500,min = 20,message = "Description must be between 20 and 500 symbols")
    private String description;

    @NotBlank(message = "Requirements are required")
    @Size(max = 500,min = 10,message = "Requirements must be between 10 and 500 symbols")
    private String requirements;

    public AddJobDTO(){

    }

    public AddJobDTO(String title, String description, String requirements) {
        this.title = title;
        this.description = description;
        this.requirements = requirements;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}