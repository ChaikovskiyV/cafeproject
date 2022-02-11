package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface User service.
 */
public interface UserService {
    /**
     * Find all users list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAllUsers() throws ServiceException;

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserById(long id) throws ServiceException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByLogin(String login) throws ServiceException;

    /**
     * Find user by login and password optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Find user by first name list.
     *
     * @param firstName the first name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUserByFirstName(String firstName) throws ServiceException;

    /**
     * Find user by last name list.
     *
     * @param lastName the last name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUserByLastName(String lastName) throws ServiceException;

    /**
     * Find user by first and last name list.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUserByFirstAndLastName(String firstName, String lastName) throws ServiceException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * Find user by phone number optional.
     *
     * @param phoneNumber the phone number
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByPhoneNumber(String phoneNumber) throws ServiceException;

    /**
     * Find users by role list.
     *
     * @param userRole the user role
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersByRole(User.Role userRole) throws ServiceException;

    /**
     * Find users by status list.
     *
     * @param userStatus the user status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersByStatus(User.Status userStatus) throws ServiceException;

    /**
     * Find users by discount type list.
     *
     * @param discountType the discount type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersByDiscountType(Discount.DiscountType discountType) throws ServiceException;

    /**
     * Find users by discount rate list.
     *
     * @param rate the rate
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersByDiscountRate(String rate) throws ServiceException;

    /**
     * Find users by several parameters list.
     *
     * @param userParameters the user parameters
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersBySeveralParameters(Map<String, String> userParameters) throws ServiceException;

    /**
     * Update user login boolean.
     *
     * @param id             the id
     * @param userParameters the user parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserLogin(long id, Map<String, String> userParameters) throws ServiceException;

    /**
     * Update user password boolean.
     *
     * @param id             the id
     * @param userParameters the user parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserPassword(long id, Map<String, String> userParameters) throws ServiceException;

    /**
     * Update user first name boolean.
     *
     * @param id        the id
     * @param firstName the first name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserFirstName(long id, String firstName) throws ServiceException;

    /**
     * Update user last name boolean.
     *
     * @param id       the id
     * @param lastName the last name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserLastName(long id, String lastName) throws ServiceException;

    /**
     * Update user email boolean.
     *
     * @param id             the id
     * @param userParameters the user parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserEmail(long id, Map<String, String> userParameters) throws ServiceException;

    /**
     * Update user phone number boolean.
     *
     * @param id             the id
     * @param userParameters the user parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserPhoneNumber(long id, Map<String, String> userParameters) throws ServiceException;

    /**
     * Update user role boolean.
     *
     * @param id   the id
     * @param role the role
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserRole(long id, String role) throws ServiceException;

    /**
     * Update user status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserStatus(long id, String status) throws ServiceException;

    /**
     * Create user long.
     *
     * @param userParameters the user parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createUser(Map<String, String> userParameters) throws ServiceException;

    /**
     * Delete user by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteUserById(long id) throws ServiceException;
}