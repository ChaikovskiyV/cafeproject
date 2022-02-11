package by.vchaikovski.coffeehouse.model.entity;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type User.
 */
public class User extends AbstractEntity {
    /**
     * The enum Role.
     */
    public enum Role {
        /**
         * Admin role.
         */
        ADMIN,
        /**
         * Client role.
         */
        CLIENT,
        /**
         * Guest role.
         */
        GUEST,
        /**
         * Barista role.
         */
        BARISTA
    }

    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Banned status.
         */
        BANNED,
        /**
         * Unbanned status.
         */
        UNBANNED
    }

    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private Status status;
    private long discountId;

    /**
     * Instantiates a new User.
     *
     * @param builder the builder
     */
    public User(UserBuilder builder) {
        super.setId(builder.id);
        login = builder.login;
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        phoneNumber = builder.phoneNumber;
        role = builder.role != null ? builder.role : Role.CLIENT;
        status = builder.status != null ? builder.status : Status.UNBANNED;
        discountId = builder.discountId;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets discount id.
     *
     * @return the discount id
     */
    public long getDiscountId() {
        return discountId;
    }

    /**
     * Sets discount id.
     *
     * @param discountId the discount id
     */
    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return super.equals(user) && (login != null ? login.equals(user.login) : user.login == null) &&
                (firstName != null ? firstName.equals(user.firstName) : user.firstName == null) &&
                (lastName != null ? lastName.equals(user.lastName) : user.lastName == null) &&
                (email != null ? email.equalsIgnoreCase(user.email) : user.email == null) &&
                (phoneNumber != null ? phoneNumber.equals(user.phoneNumber) : user.phoneNumber == null) &&
                role == user.role && status == user.status && discountId == user.discountId;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + super.hashCode();
        result = result * first + (login != null ? login.hashCode() : 0);
        result = result * first + (firstName != null ? firstName.hashCode() : 0);
        result = result * first + (lastName != null ? lastName.hashCode() : 0);
        result = result * first + (email != null ? email.hashCode() : 0);
        result = result * first + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = result * first + (role != null ? role.hashCode() : 0);
        result = result * first + (status != null ? status.hashCode() : 0);
        result = result * first + (int) discountId;

        return result;
    }

    @Override
    public String toString() {
        return new StringBuffer(super.toString())
                .append(", firstName='")
                .append(firstName)
                .append(", lastName='")
                .append(lastName)
                .append(", login='")
                .append(login)
                .append(", email='")
                .append(email)
                .append(", phoneNumber='")
                .append(phoneNumber)
                .append(", role=")
                .append(role)
                .append(", status=")
                .append(status)
                .append(", discountId=")
                .append(discountId)
                .append('}')
                .toString();
    }

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private long id;
        private String login;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private Role role;
        private Status status;
        private long discountId;

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets login.
         *
         * @param login the login
         * @return the login
         */
        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        /**
         * Sets first name.
         *
         * @param firstName the first name
         * @return the first name
         */
        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets last name.
         *
         * @param lastName the last name
         * @return the last name
         */
        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets email.
         *
         * @param email the email
         * @return the email
         */
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets phone number.
         *
         * @param phoneNumber the phone number
         * @return the phone number
         */
        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * Sets role.
         *
         * @param role the role
         * @return the role
         */
        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        /**
         * Sets status.
         *
         * @param status the status
         * @return the status
         */
        public UserBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }

        /**
         * Sets discount id.
         *
         * @param discountId the discount id
         * @return the discount id
         */
        public UserBuilder setDiscountId(long discountId) {
            this.discountId = discountId;
            return this;
        }

        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return new User(this);
        }
    }
}