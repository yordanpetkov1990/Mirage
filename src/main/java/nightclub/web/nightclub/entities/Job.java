package nightclub.web.nightclub.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String requirements;

   public Job(){

   }

   public Job(String title,String description,String requirements){
       this.title = title;
       this.description = description;
       this.requirements = requirements;
   }

    public Long getId() {
        return id;
    }

    public Job setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Job setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Job setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRequirements() {
        return requirements;
    }

    public Job setRequirements(String requirements) {
        this.requirements = requirements;
        return this;
    }
}