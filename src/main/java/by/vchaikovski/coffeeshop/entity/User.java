package by.vchaikovski.coffeeshop.entity;

public class User {
    public enum UserRole {ADMINISTRATOR, VISITOR, COURIER, BARISTA}

    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;

    public User(String login, String password, String firstName, String lastName, String email, String phone, UserRole role) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && (login != null ? login.equals(user.login) : user.login == null) &&
                (password != null ? password.equals(user.password) : user.password == null) &&
                (firstName != null ? firstName.equals(user.firstName) : user.firstName == null) &&
                (lastName != null ? lastName.equals(user.lastName) : user.lastName == null) &&
                (email != null ? email.equalsIgnoreCase(user.email) : user.email == null) &&
                (phone != null ? phone.equals(user.phone) : user.phone == null) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        int first = 31;
        int result = 1;
        result = result * first + (int) id;
        result = result * first + (login != null ? login.hashCode() : 0);
        result = result * first + (password != null ? password.hashCode() : 0);
        result = result * first + (firstName != null ? firstName.hashCode() : 0);
        result = result * first + (lastName != null ? lastName.hashCode() : 0);
        result = result * first + (email != null ? email.hashCode() : 0);
        result = result * first + (phone != null ? phone.hashCode() : 0);
        result = result * first + (role != null ? role.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("id=")
                .append(id)
                .append(", firstName='")
                .append(firstName)
                .append(", lastName='")
                .append(lastName)
                .append(", login='")
                .append(login)
                .append(", password='")
                .append("******")
                .append(", email='")
                .append(email)
                .append(", phone='")
                .append(phone)
                .append(", role=")
                .append(role)
                .append('}')
                .toString();
    }
}