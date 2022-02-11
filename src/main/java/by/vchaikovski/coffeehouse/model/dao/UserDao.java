package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface User dao.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Find by id and password optional.
     *
     * @param id       the id
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByIdAndPassword(long id, String password) throws DaoException;

    /**
     * Find by login and password optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Find by email and password optional.
     *
     * @param email    the email
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * Find by first name list.
     *
     * @param firstName the first name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByFirstName(String firstName) throws DaoException;

    /**
     * Find by last name list.
     *
     * @param lastName the last name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByLastName(String lastName) throws DaoException;

    /**
     * Find by first and last name list.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByFirstAndLastName(String firstName, String lastName) throws DaoException;

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find by phone number optional.
     *
     * @param phoneNumber the phone number
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException;

    /**
     * Find by role list.
     *
     * @param userRole the user role
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByRole(User.Role userRole) throws DaoException;

    /**
     * Find by status list.
     *
     * @param userStatus the user status
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByStatus(User.Status userStatus) throws DaoException;

    /**
     * Find by discount list.
     *
     * @param discountType the discount type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByDiscount(Discount.DiscountType discountType) throws DaoException;

    /**
     * Find by discount list.
     *
     * @param rate the rate
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByDiscount(int rate) throws DaoException;

    /**
     * Update user login boolean.
     *
     * @param id    the id
     * @param login the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserLogin(long id, String login) throws DaoException;

    /**
     * Update user password boolean.
     *
     * @param id       the id
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserPassword(long id, String password) throws DaoException;

    /**
     * Update user first name boolean.
     *
     * @param id        the id
     * @param firstName the first name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserFirstName(long id, String firstName) throws DaoException;

    /**
     * Update user last name boolean.
     *
     * @param id       the id
     * @param lastName the last name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserLastName(long id, String lastName) throws DaoException;

    /**
     * Update user email boolean.
     *
     * @param id    the id
     * @param email the email
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserEmail(long id, String email) throws DaoException;

    /**
     * Update user phone number boolean.
     *
     * @param id          the id
     * @param phoneNumber the phone number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserPhoneNumber(long id, String phoneNumber) throws DaoException;

    /**
     * Update user role boolean.
     *
     * @param id   the id
     * @param role the role
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserRole(long id, User.Role role) throws DaoException;

    /**
     * Update user status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserStatus(long id, User.Status status) throws DaoException;

    /**
     * Update user discount id boolean.
     *
     * @param userId     the user id
     * @param discountId the discount id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserDiscountId(long userId, long discountId) throws DaoException;

    /**
     * Create long.
     *
     * @param user     the user
     * @param password the password
     * @return the long
     * @throws DaoException the dao exception
     */
    long create(User user, String password) throws DaoException;
}