package iamprogrammer.brian.com.mygym;

/**
 * Created by ADMIN on 6/8/2018.
 */

public class User {

    private String email, password, dob, sex;

    public User() {
        // Empty constructor
    }

    public User(String email, String pass, String dob, String sex) {
        this.email = email;
        this.password = pass;
        this.dob = dob;
        this.sex = sex;
    }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getDob() { return this.dob; }

    public String getSex() { return this.sex; }

    public void setEmail( String email ) { this.email = email; }

    public void setPassword( String pass ) { this.password = pass; }

    public void setDob( String dob ) { this.dob = dob; }

    public void setSex( String sex ) { this.sex = sex; }

}
