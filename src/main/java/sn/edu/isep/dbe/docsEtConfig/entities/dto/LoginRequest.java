package sn.edu.isep.dbe.docsEtConfig.entities.dto;


public class LoginRequest {
    private String login;
    private String password;

    public LoginRequest() {
        System.out.println("creation loginRequest");
    }

    public String getLogin() {
        System.out.println("get login");
        return login;
    }

    public void setLogin(String login) {
        System.out.println("set login"+" "+login);
        this.login = login;
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
