package ums.ejb.domain;

enum RoleEnum {

    UNKNOWN(0, "unknown", "Unknown Role Type"),
    ADMIN(1, "admin", "Admin Role Type"),
    USER(2, "regular", "Regular Role Type");

    private Integer type;

    private String roleName;

    private String roleDesc;

    RoleEnum(Integer type, String roleName, String roleDesc) {
        this.type = type;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public static RoleEnum getRole(Integer type) {
        switch (type) {
        case 0:
            return RoleEnum.UNKNOWN;
        case 1:
            return RoleEnum.ADMIN;
        case 2:
            return RoleEnum.USER;
        default:
            return RoleEnum.UNKNOWN;
        }

    }

    /**
     * Getter for type.
     * 
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Getter for roleDesc.
     * 
     * @return the roleDesc
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * Sets the roleDesc.
     * 
     * @param roleDesc the roleDesc to set
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * Sets the roleName.
     * 
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
