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
    private static final UserDaoImpl USER_DAO = DaoProvider.getInstance().getUserDao();
    private static final DataValidator VALIDATOR = DataValidatorImpl.getInstance();

    private UserServiceImpl() {
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
            users = USER_DAO.findAll();
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
            optionalUser = USER_DAO.findById(id);
        } catch (DaoException e) {
            String message = "User can't be found by id " + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = VALIDATOR.isLoginValid(login) ? USER_DAO.findByLogin(login) : Optional.empty();
        } catch (DaoException e) {
            String message = "User can't be found by login " + login;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        if (VALIDATOR.isLoginValid(login) && VALIDATOR.isPasswordValid(password)) {
            try {
                PasswordEncryptor encryptor = PasswordEncryptor.getInstance();
                String encryptedPassword = encryptor.encryptPassword(password);
                optionalUser = USER_DAO.findByLoginAndPassword(login, encryptedPassword);
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
        List<User> users;
        try {
            users = VALIDATOR.isNameValid(firstName) ? USER_DAO.findByFirstName(firstName) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "Users can't be found by firstName " + firstName;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUserByLastName(String lastName) throws ServiceException {
        List<User> users;
        try {
            users = VALIDATOR.isNameValid(lastName) ? USER_DAO.findByLastName(lastName) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "Users can't be found by lastName " + lastName;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUserByFirstAndLastName(String firstName, String lastName) throws ServiceException {
        List<User> users;
        try {
            users = VALIDATOR.isNameValid(firstName) && VALIDATOR.isNameValid(lastName)
                    ? USER_DAO.findByFirstAndLastName(firstName, lastName)
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
        Optional<User> optionalUser;
        try {
            optionalUser = VALIDATOR.isEmailValid(email) ? USER_DAO.findByEmail(email) : Optional.empty();
        } catch (DaoException e) {
            String message = "User can't be found by email " + email;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = VALIDATOR.isPhoneNumberValid(phoneNumber)
                    ? USER_DAO.findByPhoneNumber(phoneNumber)
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
            users = userRole != null ? USER_DAO.findByRole(userRole) : new ArrayList<>();
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
            users = userStatus != null ? USER_DAO.findByStatus(userStatus) : new ArrayList<>();
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
            users = discountType != null ? USER_DAO.findByDiscount(discountType) : new ArrayList<>();
        } catch (DaoException e) {
            String message = "User can't be found by discountType " + discountType.name();
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return users;
    }

    @Override
    public List<User> findUsersByDiscountRate(String discountRate) throws ServiceException {
        List<User> users = new ArrayList<>();
        if (VALIDATOR.isDiscountRateValid(discountRate)) {
            int rate = Integer.parseInt(discountRate);
            try {
                users = USER_DAO.findByDiscount(rate);
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
        String login = userParameters.get(LOGIN);
        if (VALIDATOR.isLoginValid(login)) {
            try {
                Optional<User> userWithSameLogin = USER_DAO.findByLogin(login);
                return userWithSameLogin.isEmpty() && USER_DAO.updateUserLogin(id, login);
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
        String oldPassword = userParameters.get(OLD_PASSWORD);
        if(!VALIDATOR.isPasswordValid(oldPassword)) {
            userParameters.remove(OLD_PASSWORD);
            return false;
        }
        try {
            PasswordEncryptor encryptor = PasswordEncryptor.getInstance();
            String encryptedOldPass = encryptor.encryptPassword(oldPassword);
            Optional<User> user = USER_DAO.findByIdAndPassword(id, encryptedOldPass);
            String newPassword = userParameters.get(NEW_PASSWORD);
            if (user.isEmpty() || !VALIDATOR.isPasswordValid(newPassword)) {
                userParameters.remove(OLD_PASSWORD);
                userParameters.remove(NEW_PASSWORD);
                logger.debug("Password is wrong or not correct");
                return false;
            }
            String encryptedNewPass = encryptor.encryptPassword(newPassword);
            return USER_DAO.updateUserPassword(id, encryptedNewPass);
        } catch (DaoException e) {
            String message = "User can't be updated by password.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserFirstName(long id, String firstName) throws ServiceException {
        try {
            return VALIDATOR.isNameValid(firstName) && USER_DAO.updateUserFirstName(id, firstName);
        } catch (DaoException e) {
            String message = "User can't be updated by firstName.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserLastName(long id, String lastName) throws ServiceException {
        try {
            return VALIDATOR.isNameValid(lastName) && USER_DAO.updateUserFirstName(id, lastName);
        } catch (DaoException e) {
            String message = "User can't be updated by lastName.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserEmail(long id, Map<String, String> userParameters) throws ServiceException {
        String email = userParameters.get(EMAIL);
        try {
            Optional<User> user = USER_DAO.findByEmail(email);
            return user.isEmpty() && VALIDATOR.isEmailValid(email) && USER_DAO.updateUserEmail(id, email);
        } catch (DaoException e) {
            String message = "User can't be updated by email.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserPhoneNumber(long id, Map<String, String> userParameters) throws ServiceException {
        String phoneNumber = userParameters.get(PHONE_NUMBER);
        try {
            Optional<User> user = USER_DAO.findByPhoneNumber(phoneNumber);
            return user.isEmpty() && VALIDATOR.isPhoneNumberValid(phoneNumber) &&
                    USER_DAO.updateUserPhoneNumber(id, phoneNumber);
        } catch (DaoException e) {
            String message = "User can't be updated by phoneNumber.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserRole(long id, User.Role role) throws ServiceException {
        try {
            return USER_DAO.updateUserRole(id, role);
        } catch (DaoException e) {
            String message = "User can't be updated by role.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean updateUserStatus(long id, User.Status status) throws ServiceException {
        try {
            return USER_DAO.updateUserStatus(id, status);
        } catch (DaoException e) {
            String message = "User can't be updated by status.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean createUser(Map<String, String> userParameters, String password) throws ServiceException {
        String firstName = userParameters.get(FIRST_NAME);
        String lastName = userParameters.get(LAST_NAME);
        String login = userParameters.get(LOGIN);
        String email = userParameters.get(EMAIL);
        String phoneNumber = userParameters.get(PHONE_NUMBER);
        String role = userParameters.get(ROLE);
        String status = userParameters.get(USER_STATUS);
        String discountType = userParameters.get(DISCOUNT_TYPE);
        String discountRate = userParameters.get(DISCOUNT_RATE);
        boolean loginCheck = !VALIDATOR.isLoginValid(login) && userParameters.remove(LOGIN, login);
        boolean firstNameCheck = !VALIDATOR.isNameValid(firstName) &&
                userParameters.remove(FIRST_NAME, firstName);
        boolean lastNameCheck = !VALIDATOR.isNameValid(lastName) &&
                userParameters.remove(LAST_NAME, lastName);
        boolean emailCheck = !VALIDATOR.isEmailValid(email) && userParameters.remove(EMAIL, email);
        boolean phoneNumberCheck = !VALIDATOR.isPhoneNumberValid(phoneNumber) &&
                userParameters.remove(PHONE_NUMBER, phoneNumber);
        boolean passwordCheck = VALIDATOR.isPasswordValid(password);
        userParameters.put(PASSWORD_RESULT_CHECK, passwordCheck ? TRUE : FALSE);
        if (!loginCheck && !firstNameCheck && !lastNameCheck && !emailCheck && !phoneNumberCheck && passwordCheck) {
            String encryptedPassword = PasswordEncryptor.getInstance().encryptPassword(password);
            try {
                User.Status userStatus = status != null ? User.Status.valueOf(status.toUpperCase()) : null;
                User.Role userRole = role != null ? User.Role.valueOf(role.toUpperCase()) : null;
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
                long userId = USER_DAO.create(user, encryptedPassword);
                return userId > 0 && USER_DAO.updateUserDiscountId(userId, discountId);
            } catch (IllegalArgumentException e) {
                logger.warn(() -> "Unknown role=" + role + " or status=" + status, e);
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
            return USER_DAO.deleteById(id);
        } catch (DaoException e) {
            String message = "User can't be deleted by id.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    private long findDiscountId(String discountType, String discountRate) throws DaoException {
        long discountId = 0;
        if (VALIDATOR.isDiscountRateValid(discountRate) && discountType != null) {
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