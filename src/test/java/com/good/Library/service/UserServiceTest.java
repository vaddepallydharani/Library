package com.good.Library.service;

import com.good.Library.exception.UserAlreadyExistException;
import com.good.Library.entity.User;
import com.good.Library.entity.UserDetails;
import com.good.Library.entity.UserRole;
import com.good.Library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void registrationTest_Success() throws UserAlreadyExistException {

        User user = new User();
        user.setUserName("junnu26280");
        user.setUserDOB(LocalDate.parse("2017-06-17"));
        user.setUserRole(UserRole.ADMIN);

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("Hyderabad");
        userDetails.setPhoneNumber("9999999999");

        user.setUserDetails(userDetails);

        doReturn(Optional.empty()).when(userRepository).findByUserName(user.getUserName());

        when(userRepository.save(user)).thenReturn(user);
        User expecteduserdetails= userService.registration(user);

        assertEquals(user,expecteduserdetails);
    }

    @Test
    public void registrationTest_UserExists() throws UserAlreadyExistException{
        User user = new User();
        user.setUserName("junnu26280");
        user.setUserDOB(LocalDate.parse("2017-06-17"));
        user.setUserRole(UserRole.ADMIN);

        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("Hyderabad");
        userDetails.setPhoneNumber("9999999999");

        user.setUserDetails(userDetails);

        doReturn(Optional.of(user)).when(userRepository).findByUserName(Mockito.matches("junnu26280"));

        when(userRepository.save(user)).thenReturn(user);
        UserAlreadyExistException userAlreadyExistException = assertThrows(UserAlreadyExistException.class,
                () -> userService.registration(user));

        assertEquals("User is already registered",userAlreadyExistException.getMessage());

    }
}
