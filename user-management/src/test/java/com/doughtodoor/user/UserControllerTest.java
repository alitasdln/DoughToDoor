package com.doughtodoor.user;

import com.doughtodoor.user.model.User;
import com.doughtodoor.user.controller.UserController;
import com.doughtodoor.user.service.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JpaUserDetailsService userDetailsService;

    @Test
    @WithMockUser(roles = "CUSTOMER")
    public void testRegisterUser() throws Exception {
        // Mock your service response
        User mockUser = new User(1L, "testUser", "testPassword", "test@example.com", Collections.emptySet());
        Mockito.when(userDetailsService.registerUser(Mockito.any(User.class))).thenReturn(mockUser);

        // Prepare request data
        User newUser = new User(null, "testUser", "testPassword", "test@example.com", Collections.emptySet());

        // Perform the request and check the status
        ResultActions resultActions = mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"testPassword\",\"email\":\"test@example.com\"}")
                .with(csrf()));

        // Validate the response
        resultActions.andExpect(status().isOk());
        // You can add more assertions based on your requirements
    }
}