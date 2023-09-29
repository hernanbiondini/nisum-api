package com.nisum.user.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.nisum.user.api.service.TokenService;

@WebMvcTest(controllers = TokenController.class)
@Import(TokenService.class)
class TokenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void givenSubject_whenGetToken_thenReturnToken() throws Exception {
		mockMvc.perform(get("/token").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}