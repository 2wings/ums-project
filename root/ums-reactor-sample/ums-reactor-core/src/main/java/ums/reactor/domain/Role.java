package ums.reactor.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role1" )
public class Role implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7379605425278283239L;

    private String id;

    private String roleEnum;

    public Role() {

    }

    public Role(Integer type) {
        setType(type);
        this.id = UUID.randomUUID().toString();
    }

    /**
     * DOC crazyLau Comment method "setType".
     * 
     * @param type
     */
    private void setType(Integer type) {
        setRoleEnum(RoleEnum.getRole(type).getRoleName());
    }

    
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ROLE_ID", nullable = false)
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "ROLE_NAME", nullable = false, length = 20)
    public String getRoleEnum() {
        return this.roleEnum;
    }

    /**
     * DOC crazyLau Comment method "setRoleEnum".
     * 
     * @param type2
     */
    private void setRoleEnum(String roleEnum) {
        this.roleEnum = roleEnum;
    }
    
    @Override
    public String toString(){
        return "id:"+this.id+", roleName:" +this.roleEnum;
    }

}
