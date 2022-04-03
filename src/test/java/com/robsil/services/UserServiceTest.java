package com.robsil.services;

import com.robsil.models.User;
import com.robsil.repos.UserRepository;
import com.robsil.utils.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findAllUsers() {
        //given

        List<User> users = List.of(
                User.builder().id("asd").name("test").email("test@gmail.com").build(),
                User.builder().id("fgh").name("testerino").email("testerino@gmail.com").build(),
                User.builder().id("jkl").name("foo").email("foo@gmail.com").build(),
                User.builder().id("qwe").name("bar").email("bar@gmail.com").build()
        );

        given(userRepository.findAll()).willReturn(users);

        //when

        List<User> actual = underTest.findAllUsers();

        //then

        assertThat(actual).isEqualTo(users);
    }

    @Test
    void findUserById() {
        //given

        User user = User.builder().id("asd").name("test").email("test@gmail.com").build();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        //when

        User actual = underTest.findUserById(user.getId());

        //then

        assertThat(actual).isEqualTo(user);
    }

    @Test
    void saveUser() {

        //given

        User user = User.builder().id("asd").name("test").email("test@gmail.com").build();

        given(userRepository.insert(user)).willReturn(user);

        //when

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        underTest.saveUser(user);

        //then
        verify(userRepository).insert(userArgumentCaptor.capture());
        User actual = userArgumentCaptor.getValue();
        assertThat(actual).isEqualTo(user);
    }

    @Test
    void successfullyEditUser() {
        //given

        User user = User.builder().id("asd").name("test").email("test@gmail.com").build();
        given(userRepository.existsById(user.getId())).willReturn(true);
        given(userRepository.save(user)).willReturn(user);
        //when

        User actual = underTest.editUser(user);
        //then

        assertThat(actual).isEqualTo(user);
    }

    @Test
    void unsuccessfullyEditUser() {
        //given

        User user = User.builder().id("asd").name("test").email("test@gmail.com").build();
        given(userRepository.existsById(user.getId())).willReturn(false);
        //when


        //then

        assertThatThrownBy(() -> underTest.editUser(user))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserById() {

        //given
        User user = User.builder().id("asd").name("test").email("test@gmail.com").build();

        //when

        underTest.deleteUserById(user.getId());
        //then

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).deleteById(stringArgumentCaptor.capture());

        String actual = stringArgumentCaptor.getValue();

        assertThat(actual).isEqualTo(user.getId());
    }


}