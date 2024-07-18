package top.cadros.onlinevotingsystem.object;

public class User {
    private String account;
    private String password;
    private String username;

    public User(String account, String password, String username) {
        this.account = account;
        this.password = password;
        this.username = username;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
