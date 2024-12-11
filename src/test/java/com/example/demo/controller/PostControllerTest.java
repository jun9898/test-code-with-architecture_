package com.example.demo.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.UserStatus;
import com.example.demo.model.dto.PostUpdateDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	@Sql("/sql/post-controller-test-data.sql"),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void 사용자는_게시물을_단건_조회할수_있다 () throws Exception {
		//given
		//when
		//then
		mockMvc.perform(get("/api/posts/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("helloworld"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("kok202@naver.com"))
			.andExpect(jsonPath("$.writer.nickname").value("kok202"));
	}

	@Test
	void 사용자는_게시물을_수정할_수_있다() throws Exception {
		//given
		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.content("test content")
			.build();
		//when
		//then
		mockMvc.perform(put("/api/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postUpdateDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("test content"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("kok202@naver.com"))
			.andExpect(jsonPath("$.writer.nickname").value("kok202"));
	}
}