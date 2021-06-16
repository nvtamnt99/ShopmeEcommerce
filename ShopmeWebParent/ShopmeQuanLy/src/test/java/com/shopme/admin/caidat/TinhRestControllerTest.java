package com.shopme.admin.caidat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.admin.caidat.quocgia.QuocGiaReponsitory;
import com.shopme.admin.caidat.tinh.TinhReponsitory;
import com.shopme.common.entity.Tinh;

@SpringBootTest
@AutoConfigureMockMvc
public class TinhRestControllerTest {

	@Autowired MockMvc mockMvc;
	
	@Autowired ObjectMapper objectMapper;
	
	@Autowired QuocGiaReponsitory countryRepo;
	
	@Autowired TinhReponsitory stateRepo;
	@Test
	@WithMockUser(username = "nvtamnt99@gmail.com", password =  "123123123", roles = "Quản trị viên")
	public void testUpdateState() throws Exception {
		String url = "/tinhs/save";
		Integer stateId = 2;
		String stateName = "Alaska";
		
		Tinh state = stateRepo.findById(stateId).get();
		state.setTen(stateName);
		
		mockMvc.perform(post(url).contentType("application/json")
			.content(objectMapper.writeValueAsString(state))
			.with(csrf()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect((ResultMatcher) content().string(String.valueOf(stateId)));
		
		Optional<Tinh> findById = stateRepo.findById(stateId);
		assertThat(findById.isPresent());
		
		Tinh updatedState = findById.get();
		assertThat(updatedState.getTen()).isEqualTo(stateName);
		
	}
	
//	@Test
//	@WithMockUser(username = "nam", password = "something", roles = "Admin")
//	public void testDeleteState() throws Exception {
//		Integer stateId = 6;
//		String uri = "/states/delete/" + stateId;
//		
//		mockMvc.perform(get(uri)).andExpect(status().isOk());
//		
//		Optional<State> findById = stateRepo.findById(stateId);
//		
//		assertThat(findById).isNotPresent();
//	}
}
