package com.ndev.storyGeneratorBackend.dtos;

public class SignupDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String email) {
        this.password = email;
    }
}
