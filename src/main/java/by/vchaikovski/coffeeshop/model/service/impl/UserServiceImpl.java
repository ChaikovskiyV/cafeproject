package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.DiscountDao;
import by.vchaikovski.coffeeshop.model.dao.UserDao;
import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.DiscountService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import by.vchaikovski.coffeeshop.util.PasswordEncryptor;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.FormValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeeshop.util.validator.impl.FormValidatorImpl;
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
    private final UserDao userDao;

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
    public List<User> findUsersBySeveralParameters(Map<String, String> userParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<User> users = findAllUsers();
        if (userParameters != null && !userParameters.isEmpty()) {
            String firstName = userParameters.get(FIRST_NAME);
            String lastName = userParameters.get(LAST_NAME);
            String discountRate = userParameters.get(DISCOUNT_RATE);
            String discountType = userParameters.get(DISCOUNT_TYPE);
            String userStatus = userParameters.get(USER_STATUS);
            String userRole = userParameters.get(USER_ROLE);
            if (validator.isNameValid(firstName)) {
                users = users.stream().filter(user -> user.getFirstName().equals(firstName)).toList();
            }
            if (validator.isNameValid(lastName)) {
                users = users.stream().filter(user -> user.getLastName().equals(lastName)).toList();
            }
            if (validator.isEnumContains(userStatus, User.Status.class)) {
                User.Status status = User.Status.valueOf(userStatus.toUpperCase());
                users = users.stream().filter(user -> user.getStatus() == status).toList();
            }
            if (validator.isEnumContains(userRole, User.Role.class)) {
                User.Role role = User.Role.valueOf(userRole);
                users = users.stream().filter(user -> user.getRole() == role).toList();
            }
            DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
            users = filterUsersByDiscount(discountService.findDiscountsByRate(discountRate), users);
            users = filterUsersByDiscount(discountService.findDiscountsByType(discountType), users);
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
        boolean result;
        String password = userParameters.get(PASSWORD);
        String passwordRepeat = userParameters.get(PASSWORD_REPEAT);
        if (!validator.isPasswordValid(password)) {
            userParameters.replace(PASSWORD, WRONG_MEANING);
            logger.debug("Password is not correct");
            result = false;
        } else if (!password.equals(passwordRepeat)) {
            userParameters.replace(PASSWORD_REPEAT, WRONG_MEANING);
            logger.debug("Passwords are not the same");
            result = false;
        } else {
            try {
                PasswordEncryptor encryptor = PasswordEncryptor.getInstance();
                String encryptedOldPass = encryptor.encryptPassword(password);
                Optional<User> user = userDao.findByIdAndPassword(id, encryptedOldPass);
                String newPassword = userParameters.get(NEW_PASSWORD);
                if (user.isEmpty()) {
                    userParameters.replace(PASSWORD, WRONG_MEANING);
                    logger.debug("Password is wrong");
                    result = false;
                } else if (!validator.isPasswordValid(newPassword)) {
                    userParameters.replace(NEW_PASSWORD, WRONG_MEANING);
                    logger.debug("New password is not correct");
                    result = false;
                } else {
                    String encryptedNewPass = encryptor.encryptPassword(newPassword);
                    result = userDao.updateUserPassword(id, encryptedNewPass);
                }
            } catch (DaoException e) {
                String message = "User can't be updated by password.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
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
    public boolean updateUserRole(long id, String role) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        boolean result = false;
        if (validator.isEnumContains(role, User.Role.class)) {
            User.Role userRole = User.Role.valueOf(role.toUpperCase());
            try {
                result = userDao.updateUserRole(id, userRole);
            } catch (DaoException e) {
                String message = "User can't be updated by role.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateUserStatus(long id, String status) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        boolean result = false;
        if (validator.isEnumContains(status, User.Status.class)) {
            User.Status userStatus = User.Status.valueOf(status.toUpperCase());
            try {
                result = userDao.updateUserStatus(id, userStatus);
            } catch (DaoException e) {
                String message = "User can't be updated by status.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public long createUser(Map<String, String> userParameters) throws ServiceException {
        long userId = 0;
        FormValidator validator = FormValidatorImpl.getInstance();
        if (validator.isUserParametersValid(userParameters)) {
            try {
                String firstName = userParameters.get(FIRST_NAME);
                String lastName = userParameters.get(LAST_NAME);
                String login = userParameters.get(LOGIN);
                String email = userParameters.get(EMAIL);
                String phoneNumber = userParameters.get(PHONE_NUMBER);
                String password = userParameters.get(PASSWORD);
                if (!isUnique(userParameters)) {
                    return userId;
                }
                String encryptedPassword = PasswordEncryptor.getInstance().encryptPassword(password);
                User user = new User.UserBuilder()
                        .setLogin(login)
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPhoneNumber(phoneNumber)
                        .build();
                long discountId = findZeroDiscountId();
                user.setDiscountId(discountId);
                userId = userDao.create(user, encryptedPassword);
            } catch (DaoException e) {
                String message = "User can't be inserted in data base.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return userId;
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

    private long findZeroDiscountId() throws DaoException {
        long discountId;
        int zeroRate = 0;
        Discount zeroDiscount = new Discount(Discount.DiscountType.ZERO, zeroRate);
        DiscountDao discountDao = DaoProvider.getInstance().getDiscountDao();
        Optional<Discount> optionalDiscount = discountDao.findByTypeAndRate(Discount.DiscountType.ZERO, zeroRate);
        if (optionalDiscount.isPresent()) {
            discountId = optionalDiscount.get().getId();
        } else {
            discountId = discountDao.create(zeroDiscount);
        }
        return discountId;
    }

    private boolean isUnique(Map<String, String> userParameters) {
        boolean result = true;
        try {
            String login = userParameters.get(LOGIN);
            String email = userParameters.get(EMAIL);
            String phoneNumber = userParameters.get(PHONE_NUMBER);
            boolean isUniqueLogin = userDao.findByLogin(login).isEmpty();
            boolean isUniqueEmail = userDao.findByEmail(email).isEmpty();
            boolean isUniquePhoneNumber = userDao.findByPhoneNumber(phoneNumber).isEmpty();
            if (!isUniqueLogin) {
                userParameters.replace(LOGIN, NOT_UNIQUE_MEANING);
                result = false;
            }
            if (!isUniqueEmail) {
                userParameters.replace(EMAIL, NOT_UNIQUE_MEANING);
                result = false;
            }
            if (!isUniquePhoneNumber) {
                userParameters.replace(PHONE_NUMBER, NOT_UNIQUE_MEANING);
                result = false;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<User> filterUsersByDiscount(List<Discount> discountList, List<User> users) {
        if (discountList != null && !discountList.isEmpty()) {
            List<Long> discountsId = discountList.stream()
                    .map(Discount::getId)
                    .toList();
            users = users.stream().filter(user -> discountsId.contains(user.getDiscountId())).toList();
        }
        return users;
    }
}