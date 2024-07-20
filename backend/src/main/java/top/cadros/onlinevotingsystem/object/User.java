package top.cadros.onlinevotingsystem.object;

public class User implements java.io.Serializable {
    private String account;
    private String password;
    private String username;

    public User() {
    }

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

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
