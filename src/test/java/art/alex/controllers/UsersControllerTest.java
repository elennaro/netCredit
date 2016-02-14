package art.alex.controllers;

import art.alex.NetCreditTestApplication;
import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NetCreditTestApplication.class)
public class UsersControllerTest {

    private static final LocalDate DEFAULT_BIRTHSDAY = LocalDate.now().minusYears(19);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UsersRepository repository;


    @Autowired
    private UserDetailsService userDetailsService;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        this.repository.deleteAll();
        User joe = new User("joe");
        joe.setPassword("1234");
        joe.setConfirmPassword("1234");
        joe.setPhoneNumber("593555555");
        joe.setBirthday(DEFAULT_BIRTHSDAY);
        joe.setCurrentRemainingLiabilities(new BigDecimal(0));
        joe.setMonthlySalary(new BigDecimal(5000));
        // save a joe
        this.repository.registerUser(joe);
    }

    @Test
    public void getCurrentUser() throws Exception {
        mockMvc.perform(get("/api/users/me")
                .with(user(userDetailsService.loadUserByUsername("joe")))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username").value("joe"));
    }

    @Test
    public void saveCurrentUser() throws Exception {
        mockMvc.perform(put("/api/users/me")
                .with(user(userDetailsService.loadUserByUsername("joe")))
                .with(csrf())
                .content("{\"username\":\"joe\",\"phoneNumber\":\"593555556\",\"monthlySalary\":5000,\"currentRemainingLiabilities\":0}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("593555556"));
    }

    @Test
    public void saveCurrentUserChangeBirthdayShouldBeImpossible() throws Exception {
        mockMvc.perform(put("/api/users/me")
                .with(user(userDetailsService.loadUserByUsername("joe")))
                .with(csrf())
                .content("{\"phoneNumber\":\"593555556\",\"birthday\":\""+LocalDate.now().minusYears(45).format(DateTimeFormatter.ISO_LOCAL_DATE)+"\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("593555556"))
                .andExpect(jsonPath("$.age").value(19));
    }
}