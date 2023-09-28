package at.technikumwien.webshop.dto;

public class LoginDTO {

    private String username;
    private String password;


    public LoginDTO(){
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getter
    // /////////////////////////////////////////////////////////////////////////

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
