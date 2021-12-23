package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.UserDao;
import by.vchaikovski.coffeeshop.dao.mapper.UserMapper;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.entity.User;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_USERS = "SELECT user_id, login, first_name, last_name, email, phone_number, role, " +
            "status, fk_discount_id FROM users" +
            "JOIN discount ON fk_discount_id=discount_id";
    private static final String FIND_USER_BY_ID = " WHERE user_id=?";
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
    private static final String FIND_USER_BY_DISCOUNT_RATE = " WHERE rate=?";
    private static final String CREATE_NEW_USER = "INSERT INTO users (login, password, first_name, last_name, email, phone_number, " +
            "role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    public List<User> findAll() throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findAll method. DataBase connection error.", e);
            throw new DaoException("Exception from findAll method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(long id) throws ConnectionPoolException, DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_ID)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public long create(User user) throws ConnectionPoolException, DaoException {
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
        } catch (SQLException e) {
            logger.error(() -> "User " + user + " can't be added in dataBase. DataBase connection error.", e);
            throw new DaoException("User " + user + " can't be added in dataBase. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean update(long id, User user) {
        throw new UnsupportedOperationException("This operation is unsupported.");
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_USER_BY_ID + id);
        } catch (SQLException e) {
            logger.error("Exception from deleteById method. DataBase connection error.", e);
            throw new DaoException("Exception from deleteById method. DataBase connection error.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException, ConnectionPoolException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_LOGIN)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByLogin method. DataBase connection error.", e);
            throw new DaoException("Exception from findByLogin method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByIdAndPassword(long id, String password) throws ConnectionPoolException, DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_ID_AND_PASSWORD)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            statement.setString(SECOND_PARAMETER_INDEX, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByIdAndPassword method. DataBase connection error.", e);
            throw new DaoException("Exception from findByIdAndPassword method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException, ConnectionPoolException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            statement.setString(SECOND_PARAMETER_INDEX, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByLoginAndPassword method. DataBase connection error.", e);
            throw new DaoException("Exception from findByLoginAndPassword method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> findByFirstName(String firstName) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_FIRST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, firstName);
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByFirstName method. DataBase connection error.", e);
            throw new DaoException("Exception from findByFirstName method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public List<User> findByLastName(String lastName) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LAST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, lastName);
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByLastName method. DataBase connection error.", e);
            throw new DaoException("Exception from findByLastName method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException, ConnectionPoolException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_EMAIL)) {
            statement.setString(FIRST_PARAMETER_INDEX, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByEmail method. DataBase connection error.", e);
            throw new DaoException("Exception from findByEmail method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) throws DaoException, ConnectionPoolException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS + FIND_USER_BY_PHONE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.createUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByPhoneNumber method. DataBase connection error.", e);
            throw new DaoException("Exception from findByPhoneNumber method. DataBase connection error.", e);
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> findByRole(User.Role userRole) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ROLE)) {
            statement.setString(FIRST_PARAMETER_INDEX, userRole.name());
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByRole method. DataBase connection error.", e);
            throw new DaoException("Exception from findByRole method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public List<User> findByStatus(User.Status userStatus) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, userStatus.name());
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByStatus method. DataBase connection error.", e);
            throw new DaoException("Exception from findByStatus method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public List<User> findByDiscount(Discount.DiscountType discountType) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_DISCOUNT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByDiscountType method. DataBase connection error.", e);
            throw new DaoException("Exception from findByDiscountType method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public List<User> findByDiscount(int rate) throws DaoException, ConnectionPoolException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_DISCOUNT_RATE)) {
            statement.setInt(FIRST_PARAMETER_INDEX, rate);
            ResultSet resultSet = statement.executeQuery();
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserMapper.createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByDiscountRate method. DataBase connection error.", e);
            throw new DaoException("Exception from findByDiscountRate method. DataBase connection error.", e);
        }
        return users;
    }

    @Override
    public boolean updateUserLogin(long id, String login) throws ConnectionPoolException, DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LOGIN)) {
            statement.setString(FIRST_PARAMETER_INDEX, login);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by login " + login + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by login " + login + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserPassword(long id, String password) throws DaoException, ConnectionPoolException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(FIRST_PARAMETER_INDEX, password);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by password " + password + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by password " + password + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserFirstName(long id, String firstName) throws DaoException, ConnectionPoolException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_FIRST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, firstName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by firstName " + firstName + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by firstName " + firstName + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserLastName(long id, String lastName) throws DaoException, ConnectionPoolException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_LAST_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, lastName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by lastName " + lastName + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by lastName " + lastName + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserEmail(long id, String email) throws ConnectionPoolException, DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_EMAIL)) {
            statement.setString(FIRST_PARAMETER_INDEX, email);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by email " + email + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by email " + email + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserPhoneNumber(long id, String phoneNumber) throws ConnectionPoolException, DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PHONE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, phoneNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by phoneNumber " + phoneNumber + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by phoneNumber " + phoneNumber + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserRole(long id, User.Role role) throws ConnectionPoolException, DaoException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ROLE)) {
            statement.setString(FIRST_PARAMETER_INDEX, role.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by role " + role + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by role " + role + " can't be executed.", e);
        }
        return result != 0;
    }

    @Override
    public boolean updateUserStatus(long id, User.Status status) throws DaoException, ConnectionPoolException {
        int result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, status.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating user with id " + id + " by status " + status + " can't be executed.", e);
            throw new DaoException("Updating user with id " + id + " by status " + status + " can't be executed.", e);
        }
        return result != 0;
    }
}