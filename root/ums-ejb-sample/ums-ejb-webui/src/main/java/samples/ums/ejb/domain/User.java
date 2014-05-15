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

package samples.ums.ejb.domain;

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

import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users")
public class User implements Serializable {

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
    public User(String firstName, String lastName, String userName, String password, Integer role) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
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
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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

    @JoinColumn(name = "ROLE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
