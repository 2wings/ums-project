// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.plus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DOC crazyLau class global comment. Detailled comment
 * 
 * @author liushuangyi@126.com
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * 
     */
    private static final String BLANK = " ";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String phone;
    private String email;

    // ///////////////////////////// /////////////////////////////// ///////////////////////////////
    // ///////////////////////////// GETTER AND SETTER /////////////////////////////////////////////
    // ///////////////////////////// /////////////////////////////// ///////////////////////////////
    /**
     * Getter for id.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for firstName.
     * 
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the firstName.
     * 
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName.
     * 
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the lastName.
     * 
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for phone.
     * 
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     * 
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for email.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    // ///////////////////////////// /////////////////////////////// ///////////////////////////////
    // ///////////////////////////// Calculated Attributes /////////////////////////////////////////
    // ///////////////////////////// /////////////////////////////// ///////////////////////////////

    public String getFullName() {
        return this.lastName + BLANK + this.firstName;
    }

    // ///////////////////////////// /////////////////////////////// ///////////////////////////////
    // ///////////////////////////// Business Action ///////////////////////////////////////////////
    // ///////////////////////////// /////////////////////////////// ///////////////////////////////

    public void update(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // ///////////////////////////// /////////////////////////////// ///////////////////////////////
    // ///////////////////////////// Builder Pattern ///////////////////////////////////////////////
    // ///////////////////////////// /////////////////////////////// ///////////////////////////////

    public static Builder getBuilder(String firstName, String lastName) {
        return new Builder(firstName, lastName);
    }

    public static class Builder {

        User built;

        Builder(String firstName, String lastName) {
            built = new User();
            built.firstName = firstName;
            built.lastName = lastName;
        }

        public User build() {
            return this.built;
        }

    }

}
