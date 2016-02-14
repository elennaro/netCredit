package art.alex.services;

import art.alex.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NetCreditGeorgiaUsersCreditDataServiceTest {

    private User user;
    private CreditDataService service;

    @Before
    public void setUp() {
        user = new User("alex");
        user.setBirthday(LocalDate.now().minusYears(20));
        user.setCurrentRemainingLiabilities(new BigDecimal(5));
        user.setMonthlySalary(new BigDecimal(100));

        service = new NetCreditGeorgiaUsersCreditDataService();
    }

    @Test
    public void determineCreditLimit() throws Exception {
        Assert.assertTrue("Determined CreditLimit for User", service.determineCreditLimit(user).compareTo(BigDecimal.valueOf(295)) == 0);
    }

    @Test
    public void setCreditLimit() throws Exception {
        Assert.assertTrue("Set CreditLimit in User", service.setCreditLimit(user).getCreditLimit().compareTo(BigDecimal.valueOf(295)) == 0);
    }
}