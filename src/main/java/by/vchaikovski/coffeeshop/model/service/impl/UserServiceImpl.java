package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.DiscountDao;
import by.vchaikovski.coffeeshop.model.dao.impl.UserDaoImpl;
import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.UserService;
import by.vchaikovski.coffeeshop.util.PasswordEncryptor;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance;
    private final UserDaoImpl userDao;

    private UserServiceImpl() {
        userDao = DaoProvider.getInstance().getUserDao();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            String message = "Impossible find all users.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserById(long id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(id);
        } catch (DaoException e) {
            String message = "User can't be found by id " + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<User> optionalUser;
        try {
            optionalUser = validator.isLoginValid(login) ? userDao.findByLogin(login) : Optional.empty();
        } catch (DaoException e) {
            String message = "User can't be found by login " + login;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<User> optionalUser = Optional.empty();
        if (validator.isLoginValid(login) && validator.isPasswordValid(password)) {
            try {
                PasswordEncryptor encryptor = PasswordEncryptor.getInstance();
                String encryptedPassword = encryptor.encryptPassword(password);
                optionalUser = userDao.findByLoginAndPassword(login, encryptedPassword);
            } catch (DaoException e) {
                String message = "User can't be found by login and password";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return optionalUser;
    }

    @Override
    public List<User> findUserByFirstName(String firstName) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<User> users;
        try {
            users = validator.isNameValid(firstName) ? userDao.findByFirstName(firstName) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "Users can't be found by firstName " + firstName;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUserByLastName(String lastName) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<User> users;
        try {
            users = validator.isNameValid(lastName) ? userDao.findByLastName(lastName) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "Users can't be found by lastName " + lastName;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUserByFirstAndLastName(String firstName, String lastName) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<User> users;
        try {
            users = validator.isNameValid(firstName) && validator.isNameValid(lastName)
                    ? userDao.findByFirstAndLastName(firstName, lastName)
                    : new ArrayList<>();
        } catch (DaoException e) {
            String message = "Users can't be found by  firstName " + firstName + " and lastName " + lastName;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<User> optionalUser;
        try {
            optionalUser = validator.isEmailValid(email) ? userDao.findByEmail(email) : Optional.empty();
        } catch (DaoException e) {
            String message = "User can't be found by email " + email;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<User> optionalUser;
        try {
            optionalUser = validator.isPhoneNumberValid(phoneNumber)
                    ? userDao.findByPhoneNumber(phoneNumber)
                    : Optional.empty();
        } catch (DaoException e) {
            String message = "User can't be found by phoneNumber " + phoneNumber;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public List<User> findUsersByRole(User.Role userRole) throws ServiceException {
        List<User> users;
        try {
            users = userRole != null ? userDao.findByRole(userRole) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "User can't be found by role " + userRole.name();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUsersByStatus(User.Status userStatus) throws ServiceException {
        List<User> users;
        try {
            users = userStatus != null ? userDao.findByStatus(userStatus) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "User can't be found by status " + userStatus.name();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUsersByDiscountType(Discount.DiscountType discountType) throws ServiceException {
        List<User> users;
        try {
            users = discountType != null ? userDao.findByDiscount(discountType) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "User can't be found by discountType " + discountType.name();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUsersByDiscountRate(String discountRate) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<User> users = new ArrayList<>();
        if (validator.isDiscountRateValid(discountRate)) {
            int rate = Integer.parseInt(discountRate);
            try {
                users = userDao.findByDiscount(rate);
            } catch (DaoException e) {
                String message = "User can't be found by discountRate " + discountRate;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return users;
    }

    @Override
    public boolean updateUserLogin(long id, Map<String, String> userParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String login = userParameters.get(LOGIN);
        if (validator.isLoginValid(login)) {
            try {
                Optional<User> userWithSameLogin = userDao.findByLogin(login);
                return userWithSameLogin.isEmpty() && userDao.updateUserLogin(id, login);
            } catch (DaoException e) {
                String message = "User can't be updated by login.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }

    @Override
    public boolean updateUserPassword(long id, Map<String, String> userParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String oldPassword = userParameters.get(OLD_PASSWORD);
        if (!validator.isPasswordValid(oldPassword)) {
            userParameters.remove(OLD_PASSWORD);
            return false;
        }
        try {
            PasswordEncryptor encryptor = PasswordEncryptor.getInstance();
            String encryptedOldPass = encryptor.encryptPassword(oldPassword);
            Optional<User> user = userDao.findByIdAndPassword(id, encryptedOldPass);
            String newPassword = userParameters.get(NEW_PASSWORD);
            if (user.isEmpty() || !validator.isPasswordValid(newPassword)) {
                userParameters.remove(OLD_PASSWORD);
                userParameters.remove(NEW_PASSWORD);
                logger.debug("Password is wrong or not correct");
                return false;
            }
            String encryptedNewPass = encryptor.encryptPassword(newPassword);
            return userDao.updateUserPassword(id, encryptedNewPass);
        } catch (DaoException e) {
            String message = "User can't be updated by password.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserFirstName(long id, String firstName) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        try {
            return validator.isNameValid(firstName) && userDao.updateUserFirstName(id, firstName);
        } catch (DaoException e) {
            String message = "User can't be updated by firstName.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserLastName(long id, String lastName) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        try {
            return validator.isNameValid(lastName) && userDao.updateUserFirstName(id, lastName);
        } catch (DaoException e) {
            String message = "User can't be updated by lastName.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserEmail(long id, Map<String, String> userParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String email = userParameters.get(EMAIL);
        try {
            Optional<User> user = userDao.findByEmail(email);
            return user.isEmpty() && validator.isEmailValid(email) && userDao.updateUserEmail(id, email);
        } catch (DaoException e) {
            String message = "User can't be updated by email.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserPhoneNumber(long id, Map<String, String> userParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String phoneNumber = userParameters.get(PHONE_NUMBER);
        try {
            Optional<User> user = userDao.findByPhoneNumber(phoneNumber);
            return user.isEmpty() && validator.isPhoneNumberValid(phoneNumber) &&
                    userDao.updateUserPhoneNumber(id, phoneNumber);
        } catch (DaoException e) {
            String message = "User can't be updated by phoneNumber.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserRole(long id, User.Role role) throws ServiceException {
        try {
            return userDao.updateUserRole(id, role);
        } catch (DaoException e) {
            String message = "User can't be updated by role.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserStatus(long id, User.Status status) throws ServiceException {
        try {
            return userDao.updateUserStatus(id, status);
        } catch (DaoException e) {
            String message = "User can't be updated by status.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean createUser(Map<String, String> userParameters, String password) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        String firstName = userParameters.get(FIRST_NAME);
        String lastName = userParameters.get(LAST_NAME);
        String login = userParameters.get(LOGIN);
        String email = userParameters.get(EMAIL);
        String phoneNumber = userParameters.get(PHONE_NUMBER);
        String role = userParameters.get(ROLE);
        String status = userParameters.get(USER_STATUS);
        String discountType = userParameters.get(DISCOUNT_TYPE);
        String discountRate = userParameters.get(DISCOUNT_RATE);
        boolean loginCheck = !validator.isLoginValid(login) && userParameters.remove(LOGIN, login);
        boolean firstNameCheck = !validator.isNameValid(firstName) &&
                userParameters.remove(FIRST_NAME, firstName);
        boolean lastNameCheck = !validator.isNameValid(lastName) &&
                userParameters.remove(LAST_NAME, lastName);
        boolean emailCheck = !validator.isEmailValid(email) && userParameters.remove(EMAIL, email);
        boolean phoneNumberCheck = !validator.isPhoneNumberValid(phoneNumber) &&
                userParameters.remove(PHONE_NUMBER, phoneNumber);
        boolean passwordCheck = validator.isPasswordValid(password);
        userParameters.put(PASSWORD_RESULT_CHECK, String.valueOf(passwordCheck));
        if (!loginCheck && !firstNameCheck && !lastNameCheck && !emailCheck && !phoneNumberCheck && passwordCheck) {
            String encryptedPassword = PasswordEncryptor.getInstance().encryptPassword(password);
            try {
                User.Status userStatus = validator.isEnumContains(status, User.Status.class)
                        ? User.Status.valueOf(status.toUpperCase()) : null;
                User.Role userRole = validator.isEnumContains(role, User.Role.class)
                        ? User.Role.valueOf(role.toUpperCase()) : null;
                long discountId = findDiscountId(discountType, discountRate);
                User user = new User.UserBuilder()
                        .setLogin(login)
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPhoneNumber(phoneNumber)
                        .setRole(userRole)
                        .setStatus(userStatus)
                        .build();
                long userId = userDao.create(user, encryptedPassword);
                return userId > 0 && userDao.updateUserDiscountId(userId, discountId);
            } catch (DaoException e) {
                String message = "User can't be inserted in data base.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteUserById(long id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            String message = "User can't be deleted by id.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    private long findDiscountId(String discountType, String discountRate) throws DaoException {
        DataValidator validator = DataValidatorImpl.getInstance();
        long discountId = 0;
        if (validator.isDiscountRateValid(discountRate) && discountType != null) {
            int rate = Integer.parseInt(discountRate);
            try {
                DiscountDao discountDao = DaoProvider.getInstance().getDiscountDao();
                Discount.DiscountType type = Discount.DiscountType.valueOf(discountType);
                Optional<Discount> optionalDiscount = discountDao.findByTypeAndRate(type, rate);
                if (optionalDiscount.isPresent()) {
                    Discount discount = optionalDiscount.get();
                    discountId = discount.getId();
                } else {
                    Discount discount = new Discount.DiscountBuilder().setRate(rate).setType(type).build();
                    discountId = discountDao.create(discount);
                }
            } catch (IllegalArgumentException e) {
                logger.warn(() -> "Unknown discount type: " + discountType, e);
            }
        } else {
            discountId = findZeroDiscountId();
        }
        return discountId;
    }

    private long findZeroDiscountId() throws DaoException {
        long zeroDiscountId;
        Discount zeroDiscount = new Discount.DiscountBuilder().build();
        DiscountDao discountDao = DaoProvider.getInstance().getDiscountDao();
        Optional<Discount> optionalDiscount = discountDao
                .findByTypeAndRate(zeroDiscount.getType(), zeroDiscount.getRate());
        zeroDiscountId = optionalDiscount.isPresent() ? optionalDiscount.get().getId()
                : discountDao.create(zeroDiscount);
        return zeroDiscountId;
    }
}