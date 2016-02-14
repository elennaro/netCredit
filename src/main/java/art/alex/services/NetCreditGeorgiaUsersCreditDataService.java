package art.alex.services;

import art.alex.entities.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NetCreditGeorgiaUsersCreditDataService implements CreditDataService {


    @Override
    public BigDecimal determineCreditLimit(User user) {
        long age = user.getAge();
        if(age < 18 || age > 65)
            return new BigDecimal(0);
        return new BigDecimal(age * 10).add(user.getMonthlySalary()).subtract(user.getCurrentRemainingLiabilities());
    }

    @Override public User setCreditLimit(User user) {
        user.setCreditLimit(determineCreditLimit(user));
        return user;
    }
}