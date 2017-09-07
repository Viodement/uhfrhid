package com.senter.demo.uhf.Contants;

/**
 * Created by Lenovo on 2017/6/13.
 */

public class UserMessage {
    private String username;
    private String userjob;
    private String usernumber;

    public UserMessage(String username, String userjob, String usernumber) {
        super();
        this.username = username;
        this.userjob = userjob;
        this.usernumber = usernumber;
    }

    public String getUserjob() {
        return userjob;
    }

    public void setUserjob(String userjob) {
        this.userjob = userjob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }
}
