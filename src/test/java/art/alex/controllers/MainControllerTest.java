package art.alex.controllers;

import art.alex.NetCreditTestApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NetCreditTestApplication.class)
public class MainControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "dsa")
                .param("password", "1234")
                .param("confirmPassword", "1234")
                .param("phoneNumber", "593555555")
                .param("birthday", LocalDate.now().minusYears(19).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .param("monthlySalary", "123")
                .param("currentRemainingLiabilities", "12")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void validate() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "a")
                .param("password", "12")
                .param("confirmPassword", "123")
                .param("phoneNumber", "59355555")
                .param("birthday", LocalDate.now().minusYears(17).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .param("monthlySalary", "-5")
                .param("currentRemainingLiabilities", "-10")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest());
    }
}