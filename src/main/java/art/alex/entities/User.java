package art.alex.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class User {

    public interface ValidateOnCreate {}
    public interface ValidateOnUpdate {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Size(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, min = 2, max = 30)
    private String username;

    @NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Pattern(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, regexp = "^(5\\d{2}|2)\\d{6}$",
            message = "Phone must be in format: 5xxxxxxxx or 2xxxxxx")
    private String phoneNumber;

    @JsonIgnore
    @NotNull(groups = {ValidateOnCreate.class})
    private LocalDate birthday;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Min(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 0)
    @Max(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 9007199254740991L)
    private Long monthlySalary;

    @NotNull(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})
    @Min(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 0)
    @Max(groups = {ValidateOnCreate.class, ValidateOnUpdate.class}, value = 9007199254740991L)
    private Long currentRemainingLiabilities;

    @JsonIgnore
    @NotEmpty(groups = { ValidateOnCreate.class })
    @NotNull(groups = { ValidateOnCreate.class })
    @Size(groups = { ValidateOnCreate.class }, min = 4, max = 100)
    private String password;

    @Transient
    @JsonIgnore
    @NotEmpty(groups = { ValidateOnCreate.class })
    @NotNull(groups = { ValidateOnCreate.class })
    private String confirmPassword;

    //Constructors
    protected User() {}

    public User(String username) {
        this.username = username;
    }

    //Getters And setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getCurrentRemainingLiabilities() {
        return currentRemainingLiabilities;
    }

    public void setCurrentRemainingLiabilities(Long currentRemainingLiabilities) {
        this.currentRemainingLiabilities = currentRemainingLiabilities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Long monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //TODO: move to a king of decorator
    //Unpresented in model
    public long getAge(){
        return ChronoUnit.YEARS.between(this.getBirthday(), LocalDate.now());
    }
}
