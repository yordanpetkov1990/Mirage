package nightclub.web.nightclub.entities.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nightclub.web.nightclub.validation.annotation.UniqueEmail;
import nightclub.web.nightclub.validation.annotation.UniquePhoneNumber;
import nightclub.web.nightclub.validation.annotation.UniqueUsername;

public class RegistrationDTO {

    @NotBlank
    @UniqueUsername
    @Size(min = 5,max = 20,message = "Length must be between 5 and 20")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String password;
    @Email(message = "Please enter a valid email")
    @UniqueEmail
    private String email;
    @Size(min = 2,max = 20,message = "Length must be between 2 and 20")
    private String firstName;
    @Size(min = 2,max = 20,message = "Length must be between 2 and 20")
    private String lastName;
    @NotBlank(message = "Phone number must not be blank")
    @UniquePhoneNumber
    private String phoneNumber;

    public String getUsername() {
        return username;
    }

    public RegistrationDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegistrationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegistrationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RegistrationDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
