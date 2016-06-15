package model;

/**
 * Created by admin on 2016/6/15.
 */
public class User {

    private Long id;
    private Long phone;


    public User(Long phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

}
