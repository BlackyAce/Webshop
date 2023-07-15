package at.technikumwien.webshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;
    
    @NotBlank(message = "Username is a mandatory field")
    private String username;
    
    @NotBlank(message = "Password is a mandatory field")
    private String password;
    
    private boolean admin;
    
    @NotBlank(message = "Salutation is a mandatory field")
    private String salutation;
    
    @NotBlank(message = "First name is a mandatory field")
    @Size(max = 50, message = "First name must be less than or equal to 50 characters")
    private String firstname;
    
    @NotBlank(message = "Last name is a mandatory field")
    @Size(max = 50, message = "Last name must be less than or equal to 50 characters")
    private String lastname;
    
    @NotBlank(message = "Email is a mandatory field")
    @Size(max = 50, message = "Email must be less than or equal to 50 characters")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Street address is a mandatory field")
    @Size(max = 50, message = "Street address must be less than or equal to 50 characters")
    private String streetadress;
    
    @NotBlank(message = "Street number is a mandatory field")
    private String streetnumber;
    
    @NotBlank(message = "City is a mandatory field")
    @Size(max = 50, message = "City must be less than or equal to 50 characters")
    private String city;
    
    @NotBlank(message = "Postal code is a mandatory field")
    @Size(max = 50, message = "Postal code must be less than or equal to 50 characters")
    private String postalcode;
    
    @NotBlank(message = "Country is a mandatory field")
    @Size(max = 50, message = "Country must be less than or equal to 50 characters")
    private String country;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, boolean admin, String salutation, String firstname,
            String lastname, String email, String streetadress, String streetnumber, String city, String postalcode,
            String country) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.salutation = salutation;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.streetadress = streetadress;
        this.streetnumber = streetnumber;
        this.city = city;
        this.postalcode = postalcode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetadress() {
        return streetadress;
    }

    public void setStreetadress(String streetadress) {
        this.streetadress = streetadress;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}