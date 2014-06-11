/*
 * This file is part of websocktets-gl - simple WebSocket example Copyright (C) 2012 Burt Parkers
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package ums.reactor.domain;

/**
 * 
 * @author liushuangyi@126.com
 */
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users1")
public class User implements Serializable {

    private static final long serialVersionUID = -7321118073774237777L;

    private String id;

    private String firstName;
    private String lastName;

    private String userName;
    private String password;

    private Role role;

    /**
     * DOC crazyLau User constructor comment.
     * 
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     */
    public User(String userName, String password, String firstName, String lastName, Integer role) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = new Role(role);
    }

    /**
     * DOC crazyLau User constructor comment.
     */
    public User() {
    }

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "USER_ID", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JoinColumn(name = "ROLE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "id:" + this.id + " ,firstName: " + this.firstName + ", lastName: " + this.lastName + ", userName:"
                + this.userName + ", role:" + role.toString();
    }

    public static class Builder {

        private User built;

        /**
         * DOC crazyLau Builder constructor comment.
         * 
         * @param username
         * @param password
         * @param firstName
         * @param lastName
         * @param role
         */
        public Builder(String username, String password, String firstName, String lastName, int role) {
            built = new User(username, password, firstName, lastName, role);
        }

        /**
         * DOC crazyLau Comment method "build".
         * 
         * @return
         */
        public User build() {
            return built;
        }

    }

    /**
     * DOC crazyLau Comment method "getBuilder".
     * 
     * @param username2
     * @param password2
     * @param firstName2
     * @param lastName2
     * @param role2
     * @return
     */
    public static Builder getBuilder(String username, String password, String firstName, String lastName, int role) {
        return new Builder(username, password, firstName, lastName, role);
    }

}
