package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.entity.User;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByLogin(String login) throws DaoException, ConnectionPoolException;

    Optional<User> findByIdAndPassword(long id, String password) throws ConnectionPoolException, DaoException;

    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException, ConnectionPoolException;

    List<User> findByFirstName(String firstName) throws DaoException, ConnectionPoolException;

    List<User> findByLastName(String lastName) throws DaoException, ConnectionPoolException;

    Optional<User> findByEmail(String email) throws DaoException, ConnectionPoolException;

    Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException, ConnectionPoolException;

    List<User> findByRole(User.Role userRole) throws DaoException, ConnectionPoolException;

    List<User> findByStatus(User.Status userStatus) throws DaoException, ConnectionPoolException;

    List<User> findByDiscount(Discount.DiscountType discountType) throws DaoException, ConnectionPoolException;

    List<User> findByDiscount(int rate) throws DaoException, ConnectionPoolException;

    boolean updateUserLogin(long id, String login) throws ConnectionPoolException, DaoException;

    boolean updateUserPassword(long id, String password) throws DaoException, ConnectionPoolException;

    boolean updateUserFirstName(long id, String firstName) throws DaoException, ConnectionPoolException;

    boolean updateUserLastName(long id, String lastName) throws DaoException, ConnectionPoolException;

    boolean updateUserEmail(long id, String email) throws ConnectionPoolException, DaoException;

    boolean updateUserPhoneNumber(long id, String phoneNumber) throws ConnectionPoolException, DaoException;

    boolean updateUserRole(long id, User.Role role) throws ConnectionPoolException, DaoException;

    boolean updateUserStatus(long id, User.Status status) throws DaoException, ConnectionPoolException;
}