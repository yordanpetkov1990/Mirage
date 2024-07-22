package nightclub.web.nightclub.entities.dtos;

import jakarta.persistence.Column;

public class SingerDTO {

    private Long id;
    private String name;

    private String imageUrl;
    private String genre;

    public SingerDTO(){

    }

    public Long getId() {
        return id;
    }

    public SingerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SingerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SingerDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public SingerDTO setGenre(String genre) {
        this.genre = genre;
        return this;
    }
}
