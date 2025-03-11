package com.ndev.storyGeneratorBackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column()
    private boolean enabled;

    @Column()
    private String verificationCode;

    // Default constructor
    public User() {
    }

    // All-args constructor
    public User(UUID id, String password, String email, String firstName, String lastName, boolean enabled, String verificationCode) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    // Builder pattern replacement
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private UUID id;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private boolean enabled;
        private String verificationCode;

        public UserBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder verificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
            return this;
        }

        public User build() {
            return new User(id, password, email, firstName, lastName, enabled, verificationCode);
        }
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(verificationCode, user.verificationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email, firstName, lastName, enabled, verificationCode);
    }

    // toString
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='[PROTECTED]'" +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
