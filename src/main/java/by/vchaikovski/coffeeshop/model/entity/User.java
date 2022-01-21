package by.vchaikovski.coffeeshop.model.entity;

public class User extends AbstractEntity {
    public enum Role {ADMIN, CLIENT, GUEST, BARISTA}

    public enum Status {BANNED, UNBANNED}

    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private Status status;
    private long discountId;

    public User(UserBuilder builder) {
        if (builder == null || !builder.isValid()) {
            String message = "The builder " + builder + " is not valid.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDiscountId() {
        return discountId;
    }

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

        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public UserBuilder setDiscountId(long discountId) {
            this.discountId = discountId;
            return this;
        }

        public boolean isValid() {
            return login != null && firstName != null && lastName != null &&
                    email != null && phoneNumber != null;
        }

        public User build() {
            return new User(this);
        }
    }
}