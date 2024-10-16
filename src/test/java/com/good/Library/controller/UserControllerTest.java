package com.good.Library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.good.Library.entity.BookDetailsEntity;
import com.good.Library.entity.User;
import com.good.Library.entity.UserDetails;
import com.good.Library.entity.UserRole;
import com.good.Library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static com.good.Library.entity.UserRole.ADMIN;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    UserController userController;
    private MockMvc mockMvc;
    @Mock
    UserService userService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

     User userEntity() {
        User user = new User();
        user.setId(123);
        user.setUserName("junnu26280");
        user.setUserDOB(LocalDate.of(2017,06,17));
        user.setUserRole(UserRole.ADMIN);

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("Hyderabad");
        userDetails.setPhoneNumber("9999999999");

        user.setUserDetails(userDetails);

        return user;
    }

    @Test
    public void testRegistration() throws Exception {

        //Mockito.when(userService.registration(Mockito.any())).thenReturn(userEntity());

        doReturn(userEntity()).when(userService).registration(Mockito.any());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(userEntity()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        System.out.println("actions: "+result);

        assertNotNull(result);
        assertNotNull(result.andReturn().getResponse().getContentAsString());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
