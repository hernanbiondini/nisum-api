package com.nisum.user.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.user.api.dto.PhoneRequestDTO;
import com.nisum.user.api.dto.UserRegistrationRequestDTO;
import com.nisum.user.api.dto.UserResponseDTO;
import com.nisum.user.api.dto.UserUpdateRequest;
import com.nisum.user.api.exceptions.NotFoundException;
import com.nisum.user.api.service.TokenService;
import com.nisum.user.api.service.UserService;

@WebMvcTest(controllers = UserController.class, properties = "spring.profiles.active=local")
@Import(TokenService.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void saveUser_ValidUser_ReturnsCreatedStatus() throws Exception {
		List<PhoneRequestDTO> phoneRequestDTO = new ArrayList<PhoneRequestDTO>();
		PhoneRequestDTO phoneRequestDTO1 = PhoneRequestDTO.builder().cityCode("1").countryCode("1").number("1").build();
		PhoneRequestDTO phoneRequestDTO2 = PhoneRequestDTO.builder().cityCode("2").countryCode("2").number("2").build();
		phoneRequestDTO.add(phoneRequestDTO1);
		phoneRequestDTO.add(phoneRequestDTO2);

		UserRegistrationRequestDTO requestDTO = UserRegistrationRequestDTO.builder().name("John Doe")
				.email("hernan@biondini.com").password("Hernan09").phones(phoneRequestDTO).build();

		UserResponseDTO userRegistrationResponseDTO = new UserResponseDTO();
		when(userService.saveUser(any())).thenReturn(userRegistrationResponseDTO);

		mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void updateUser_ExistingUser_ReturnsUpdatedUser() throws Exception {
		String userId = "1";

		List<PhoneRequestDTO> phoneRequestDTO = new ArrayList<PhoneRequestDTO>();
		PhoneRequestDTO phoneRequestDTO1 = PhoneRequestDTO.builder().cityCode("1").countryCode("1").number("1").build();
		PhoneRequestDTO phoneRequestDTO2 = PhoneRequestDTO.builder().cityCode("2").countryCode("2").number("2").build();
		phoneRequestDTO.add(phoneRequestDTO1);
		phoneRequestDTO.add(phoneRequestDTO2);

		UserRegistrationRequestDTO requestDTO = UserRegistrationRequestDTO.builder().name("John Doe")
				.email("hernan@biondini.com").password("Hernan09").phones(phoneRequestDTO).build();

		UserResponseDTO updatedUser = new UserResponseDTO();

		when(userService.updateUser(eq(userId), any(UserUpdateRequest.class))).thenReturn(updatedUser);

		mockMvc.perform(patch("/api/v1/users/{id}", userId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void findAllUsers_NoUsers_ReturnsEmptyList() throws Exception {
		List<UserResponseDTO> userList = new ArrayList<>();
		when(userService.findAllUsers()).thenReturn(userList);
		mockMvc.perform(get("/api/v1/users")).andExpect(status().isOk()).andExpect(content().json("[]"));
	}

	@Test
	void findUserById_ExistingUser_ReturnsUser() throws Exception {
		String userId = "1";
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		when(userService.findUserById(eq(userId))).thenReturn(userResponseDTO);
		mockMvc.perform(get("/api/v1/users/{id}", userId)).andExpect(status().isOk());
	}

	@Test
	void deleteUserById_ExistingUser_ReturnsNoContent() throws Exception {
		String userId = "1";
		mockMvc.perform(delete("/api/v1/users/{id}", userId)).andExpect(status().isNoContent());
	}

	@Test
	void findUserById_NonExistingUser_ReturnsNotFound() throws Exception {
		String userId = "1";
		when(userService.findUserById(userId)).thenThrow(new NotFoundException("Usuario no encontrado"));
		mockMvc.perform(get("/api/v1/users/{id}", userId)).andExpect(status().isNotFound());
	}

}
