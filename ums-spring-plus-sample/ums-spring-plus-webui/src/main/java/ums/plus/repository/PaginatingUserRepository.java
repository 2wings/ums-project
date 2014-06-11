package ums.plus.repository;

import java.util.List;

import ums.plus.domain.User;

public interface PaginatingUserRepository {

    /**
     * Finds all Users stored in the database.
     * @return
     */
    public List<User> findAllUsers();

    /**
     * Finds the count of Users stored in the database.
     * @param searchTerm
     * @return
     */
    public long findUserCount(String searchTerm);

    /**
     * Finds Users for the requested page whose last name starts with the given search term.
     * @param searchTerm    The used search term.
     * @param page  The number of the requested page.
     * @return  A list of Users belonging to the requested page.
     */
    public List<User> findUsersForPage(String searchTerm, int page);
}
