package art.alex.entities;

import art.alex.validators.CorrectPassword;
import art.alex.validators.PasswordMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.malkusch.validation.constraints.age.Age;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@PasswordMatch(password = "password", confirmPassword = "confirmPassword",
        message = "Passwords do not match!",
        groups = {User.ValidateOnCreate.class, User.ValidateOnUpdatePassword.class})
public class User {

    public interface ValidateOnCreate {}
    public interface ValidateOnUpdate {}
    public interface ValidateOnUpdatePassword {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String avatar;

    @NotEmpty(groups = {ValidateOnCreate.class})
    @Size(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, min = 2, max = 30)
    private String username;

    @NotEmpty(groups = {ValidateOnCreate.class})
    @Pattern(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, regexp = "^(5\\d{2}|2)\\d{6}$",
            message = "Phone must be in format: 5xxxxxxxx or 2xxxxxx")
    private String phoneNumber;

    @JsonIgnore
    @Age(value = 18, message = "Bye bye, kiddo, you should be 18!", groups = {User.ValidateOnCreate.class})
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(groups = {ValidateOnCreate.class})
    private Date birthday;

    @NotNull(groups = {ValidateOnCreate.class})
    @Min(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 0)
    @Max(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 9007199254740991L)
    private BigDecimal monthlySalary;

    @NotNull(groups = {ValidateOnCreate.class})
    @Min(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 0)
    @Max(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 9007199254740991L)
    private BigDecimal currentRemainingLiabilities;

    @JsonIgnore
    @NotEmpty(groups = { ValidateOnCreate.class, ValidateOnUpdatePassword.class })
    @Size(groups = { ValidateOnCreate.class, ValidateOnUpdatePassword.class }, min = 4, max = 100)
    private String password;

    @JsonIgnore
    @Transient
    @NotEmpty(groups = { ValidateOnUpdatePassword.class })
    @CorrectPassword(message = "Wrong password!", groups = { User.ValidateOnUpdatePassword.class })
    private String oldPassword;

    @JsonIgnore
    @Transient
    @NotEmpty(groups = { ValidateOnCreate.class, ValidateOnUpdatePassword.class })
    private String confirmPassword;

    @Transient
    private BigDecimal creditLimit;

    //Constructors
    protected User() {}

    public User(String username) {
        this.username = username;
    }

    public User(User user) {
        this.id = user.getId();
        this.birthday = user.getBirthday();
        this.currentRemainingLiabilities = user.getCurrentRemainingLiabilities();
        this.monthlySalary = user.getMonthlySalary();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.username = user.getUsername();
    }

    //Getters And setters
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getCurrentRemainingLiabilities() {
        return currentRemainingLiabilities;
    }

    public void setCurrentRemainingLiabilities(BigDecimal currentRemainingLiabilities) {
        this.currentRemainingLiabilities = currentRemainingLiabilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @JsonProperty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @JsonIgnore
    public String getOldPassword() {
        return oldPassword;
    }

    @JsonProperty
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public long getAge(){
        return ChronoUnit.YEARS.between(this.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
    }

}
