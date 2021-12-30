package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.UserDao;
import by.vchaikovski.coffeeshop.dao.mapper.impl.UserMapperImpl;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.entity.User;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String UPDATE_MESSAGE = "The query \"update user with id=";
    private static final String FIND_ALL_USERS = "SELECT user_id, login, first_name, last_name, email, phone_number, role, " +
            "status FROM users" +
            "JOIN discount ON fk_discount_id=discount_id";
    private static final String FIND_USER_BY_ID = " WHERE user_id=";
    private static final String FIND_USER_BY_LOGIN = " WHERE login=?";
    private static final String FIND_USER_BY_ID_AND_PASSWORD = " WHERE id=? AND password=?";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = " WHERE login=? AND password=?";
    private static final String FIND_USER_BY_FIRST_NAME = " WHERE first_name=?";
    private static final String FIND_USER_BY_LAST_NAME = " WHERE last_name=?";
    private static final String FIND_USER_BY_EMAIL = " WHERE email=?";
    private static final String FIND_USER_BY_PHONE_NUMBER = " WHERE phone_number=?";
    private static final String FIND_USER_BY_ROLE = " WHERE role=?";
    private static final String FIND_USER_BY_STATUS = " WHERE status=?";
    private static final String FIND_USER_BY_DISCOUNT_TYPE = " WHERE discount_type=?";
    private static final String FIND_USER_BY_DISCOUNT_RATE = " WHERE rate=";
    private static final String CREATE_NEW_USER = "INSERT INTO users (login, password, first_name, last_name, email, " +
            "phone_number, role, status, fk_discount_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_LOGIN = "UPDATE users SET login=? WHERE user_id=?";
    private static final String UPDATE_USER_FIRST_NAME = "UPDATE users SET first_name=? WHERE user_id=?";
    private static final String UPDATE_USER_LAST_NAME = "UPDATE users SET last_name=? WHERE user_id=?";
    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE user_id=?";
    private static final String UPDATE_USER_EMAIL = "UPDATE users SET email=? WHERE user_id=?";
    private static final String UPDATE_USER_PHONE_NUMBER = "UPDATE users SET phone_number=? WHERE user_id=?";
    private static final String UPDATE_USER_ROLE = "UPDATE users SET role=? WHERE user_id=?";
    private static final String UPDATE_USER_STATUS = "UPDATE users SET status=? WHERE user_id=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all users\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS + FIND_USER_BY_ID + id)) {
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_LOGIN)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by login=" + login + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByIdAndPassword(long id, String password) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_ID_AND_PASSWORD)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            statement.setString(SECOND_PARAMETER_INDEX, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by password and id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            statement.setString(SECOND_PARAMETER_INDEX, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by password and login=" + login + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findByFirstName(String firstName) throws DaoException {
        List<User> users;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_FIRST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, firstName);
            resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by firstName=" + firstName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return users;
    }

    @Override
    public List<User> findByLastName(String lastName) throws DaoException {
        List<User> users;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_LAST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, lastName);
            resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by lastName=" + lastName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_EMAIL)) {
            statement.setString(FIRST_PARAMETER_INDEX, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by email=" + email + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_PHONE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, phoneNumber);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new UserMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find user by phoneNumber=" + phoneNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findByRole(User.Role userRole) throws DaoException {
        List<User> users;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_ROLE)) {
            statement.setString(FIRST_PARAMETER_INDEX, userRole.name());
            resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find users by userRole=" + userRole + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return users;
    }

    @Override
    public List<User> findByStatus(User.Status userStatus) throws DaoException {
        List<User> users;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, userStatus.name());
            resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find users by userStatus=" + userStatus + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return users;
    }

    @Override
    public List<User> findByDiscount(Discount.DiscountType discountType) throws DaoException {
        List<User> users;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_DISCOUNT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find users by discountType=" + discountType + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
        return users;
    }

    @Override
    public List<User> findByDiscount(int rate) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS + FIND_USER_BY_DISCOUNT_RATE + rate)) {
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new UserMapperImpl().createEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find users by discount=" + rate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return users;
    }

    @Override
    public boolean update(long id, User user) {
        throw new UnsupportedOperationException(" The update(long id, User user) method is unsupported.");
    }

    @Override
    public boolean updateUserLogin(long id, String login) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LOGIN)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by login " + login + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserPassword(long id, String password) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(FIRST_PARAMETER_INDEX, password);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by password\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserFirstName(long id, String firstName) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_FIRST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, firstName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by firstName=" + firstName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserLastName(long id, String lastName) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LAST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, lastName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by lastName=" + lastName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserEmail(long id, String email) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_EMAIL)) {
            statement.setString(FIRST_PARAMETER_INDEX, email);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by email=" + email + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserPhoneNumber(long id, String phoneNumber) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PHONE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, phoneNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by phoneNumber=" + phoneNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserRole(long id, User.Role role) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ROLE)) {
            statement.setString(FIRST_PARAMETER_INDEX, role.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by role=" + role + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateUserStatus(long id, User.Status status) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, status.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by status=" + status + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(User user) throws DaoException { //TODO add fk_discount_id
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, user.getLogin());
            statement.setString(SECOND_PARAMETER_INDEX, user.getPassword());
            statement.setString(THIRD_PARAMETER_INDEX, user.getFirstName());
            statement.setString(FOURTH_PARAMETER_INDEX, user.getLastName());
            statement.setString(FIFTH_PARAMETER_INDEX, user.getEmail());
            statement.setString(SIXTH_PARAMETER_INDEX, user.getPhoneNumber());
            statement.setString(SEVENTH_PARAMETER_INDEX, user.getRole().name());
            statement.setString(EIGHTH_PARAMETER_INDEX, user.getStatus().name());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            long userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getLong(FIRST_PARAMETER_INDEX);
            }
            return userId;
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create user " + user + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_USER_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete user with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}