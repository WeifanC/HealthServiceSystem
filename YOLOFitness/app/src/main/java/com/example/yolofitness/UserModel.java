package com.example.yolofitness;

public class UserModel {
        private int id;
        private String username;
        private String password;
        private String identity;

    public UserModel(int id, String username, String password, String identity) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }

    /**
     * get userid
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * set user id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get user password when sign up
     * @return
     */

    public String getPassword() {
        return password;
    }

    /**
     * set user password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ger user identity (member or insructor
     * @return
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * set indentify
     * @param identity
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
