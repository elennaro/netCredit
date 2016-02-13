package art.alex.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30)
    private String username;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^(5\\d{2}|2)\\d{6}$",
            message = "Phone must be in format: 5xxxxxxxx or 2xxxxxx")
    private String phoneNumber;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    @NotNull
    private LocalDate birthday;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long monthlySalary;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long currentRemainingLiabilities;

    @NotEmpty
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Transient
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

    private LocalDate getBirthday() {
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
