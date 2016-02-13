package art.alex.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^(5\\d{2}|2)\\d{6}$",
            message = "Phone must be in format: 5xxxxxxxx or 2xxxxxx")
    private String phoneNumber;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    @NotNull
    private Date birthday;

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

    //Getters And setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
        Date now = new Date();
        return ChronoUnit.YEARS.between(this.getBirthday().toInstant(), now.toInstant());
    }
}
