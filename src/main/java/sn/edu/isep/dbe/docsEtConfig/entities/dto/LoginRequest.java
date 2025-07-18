package sn.edu.isep.dbe.docsEtConfig.entities.dto;


public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
        System.out.println("creation loginRequest");
    }

    public String getEmail() {
        System.out.println("get email");
        return email;
    }

    public void setEmail(String email) {
        System.out.println("set login"+" "+email);
        this.email = email;
    }

    public String getPassword() {
        System.out.println("get password");
        return password;
    }

    public void setPassword(String password) {
        System.out.println("set password"+" "+password);
        this.password = password;
    }
}
