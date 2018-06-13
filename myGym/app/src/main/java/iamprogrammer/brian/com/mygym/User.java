package iamprogrammer.brian.com.mygym;

/**
 * Created by ADMIN on 6/8/2018.
 */

public class User {

    private String username, email, password, dob, sex, home, init_weight, target_weight;

    public User() {
        // Empty constructor
    }

    public User(String username, String email, String pass, String dob, String sex, String home, String init_weight, String target_weight) {
        this.username = username;
        this.email = email;
        this.password = pass;
        this.dob = dob;
        this.sex = sex;
        this.home = home;
        this.init_weight = init_weight;
        this.target_weight = target_weight;
    }

    public String getUsername() { return this.username; }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getDob() { return this.dob; }

    public String getSex() { return this.sex; }

    public String getHome() { return this.home; }

    public String getInit_weight() { return this.init_weight; }

    public String getTarget_weight() { return this.target_weight; }

    public void setUsername( String username ) { this.username = username; }

    public void setEmail( String email ) { this.email = email; }

    public void setPassword( String pass ) { this.password = pass; }

    public void setDob( String dob ) { this.dob = dob; }

    public void setSex( String sex ) { this.sex = sex; }

    public void setHome( String home ) { this.home = home; }

    public void setInit_weight( String init_weight ) { this.init_weight = init_weight; }

    public void setTarget_weight( String target_weight ) { this.target_weight = target_weight; }

}
