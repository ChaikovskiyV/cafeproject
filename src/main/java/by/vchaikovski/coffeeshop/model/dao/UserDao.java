package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findByLogin(String login) throws DaoException;

    Optional<User> findByIdAndPassword(long id, String password) throws DaoException;

    Optional<User> findByLoginAndPassword(String login, String password) throws DaoException;

    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    List<User> findByFirstName(String firstName) throws DaoException;

    List<User> findByLastName(String lastName) throws DaoException;

    List<User> findByFirstAndLastName(String firstName, String lastName) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException;

    List<User> findByRole(User.Role userRole) throws DaoException;

    List<User> findByStatus(User.Status userStatus) throws DaoException;

    List<User> findByDiscount(Discount.DiscountType discountType) throws DaoException;

    List<User> findByDiscount(int rate) throws DaoException;

    boolean updateUserLogin(long id, String login) throws DaoException;

    boolean updateUserPassword(long id, String password) throws DaoException;

    boolean updateUserFirstName(long id, String firstName) throws DaoException;

    boolean updateUserLastName(long id, String lastName) throws DaoException;

    boolean updateUserEmail(long id, String email) throws DaoException;

    boolean updateUserPhoneNumber(long id, String phoneNumber) throws DaoException;

    boolean updateUserRole(long id, User.Role role) throws DaoException;

    boolean updateUserStatus(long id, User.Status status) throws DaoException;

    boolean updateUserDiscountId(long userId, long discountId) throws DaoException;

    long create(User user, String password) throws DaoException;
}