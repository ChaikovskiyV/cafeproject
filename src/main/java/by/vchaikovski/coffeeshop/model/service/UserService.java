package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers() throws ServiceException;

    Optional<User> findUserById(long id) throws ServiceException;
    
    Optional<User> findUserByLogin(String login) throws ServiceException;

    Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException;

    List<User> findUserByFirstName(String firstName) throws ServiceException;

    List<User> findUserByLastName(String lastName) throws ServiceException;

    List<User> findUserByFirstAndLastName(String firstName, String lastName) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    Optional<User> findUserByPhoneNumber(String phoneNumber) throws ServiceException;

    List<User> findUsersByRole(User.Role userRole) throws ServiceException;

    List<User> findUsersByStatus(User.Status userStatus) throws ServiceException;

    List<User> findUsersByDiscountType(Discount.DiscountType discountType) throws ServiceException;

    List<User> findUsersByDiscountRate(String rate) throws ServiceException;

    List<User> findUsersBySeveralParameters(Map<String, String> userParameters) throws ServiceException;

    boolean updateUserLogin(long id, Map<String, String> userParameters) throws ServiceException;

    boolean updateUserPassword(long id, Map<String, String> userParameters) throws ServiceException;

    boolean updateUserFirstName(long id, String firstName) throws ServiceException;

    boolean updateUserLastName(long id, String lastName) throws ServiceException;

    boolean updateUserEmail(long id, Map<String, String> userParameters) throws ServiceException;

    boolean updateUserPhoneNumber(long id, Map<String, String> userParameters) throws ServiceException;

    boolean updateUserRole(long id, String role) throws ServiceException;

    boolean updateUserStatus(long id, String status) throws ServiceException;

    long createUser(Map<String, String> userParameters) throws ServiceException;

    boolean deleteUserById(long id) throws ServiceException;
}