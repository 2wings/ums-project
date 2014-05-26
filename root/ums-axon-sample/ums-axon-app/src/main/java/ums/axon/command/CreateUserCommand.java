package ums.axon.command;

import ums.axon.domain.UserId;
import ums.axon.query.RoleEntry;
import ums.axon.query.UserEntry;

/**
 * Command to create a new user.
 *
 * @author liushuangyi
 */
public class CreateUserCommand {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    private RoleEntry role;

    /**
     * DOC crazyLau CreateUserCommand constructor comment.
     * 
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     */
    public CreateUserCommand(String id, String firstName, String lastName, String userName, String password, RoleEntry role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;

    }

    /**
     * DOC crazyLau Comment method "getId".
     * 
     * @return
     */
    public String getId() {
        return this.id;
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
     * Getter for lastName.
     * 
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
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
     * Getter for role.
     * 
     * @return the role
     */
    public RoleEntry getRole() {
        return role;
    }

}
