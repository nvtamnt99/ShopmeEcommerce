package com.shopme.admin.caidat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.DatNuoc;

@SpringBootTest
@AutoConfigureMockMvc
public class QuocGiaReponsitoryTest {
	@Autowired MockMvc mockMvc;
	
	@Autowired ObjectMapper objectMapper;

//	@Autowired private QuocGiaReponsitory repo;
//	
//	@Test
//	public void testfindAllTiente() {
//		List<DatNuoc> dsDatnuoc = repo.findAllByOrderByTenAsc();
//		dsDatnuoc.forEach(tt -> {
//			System.out.println(tt);
//		});
//	}
//	
	@Test
	@WithMockUser(username = "nvtamnt99@gmail.com", password =  "123123123", roles = "Quản trị viên")
	public void testListCountry() throws Exception {
		String url = "/datnuocs/list";
		MvcResult result = mockMvc.perform(get(url))
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		System.out.println(jsonResponse);
		DatNuoc[] datNuocs =  objectMapper.readValue(jsonResponse, DatNuoc[].class);
		for(DatNuoc datNuoc: datNuocs) {
			System.out.println(datNuoc);
		}
	}
	@Test
	@WithMockUser(username = "nvtamnt99@gmail.com", password =  "123123123", roles = "Quản trị viên")
	public void testAddCountry() throws Exception {
		String url = "/datnuocs/save";
		String name = "Canada";
		String code = "+80";
		DatNuoc datNuoc = new DatNuoc(name, code);
		MvcResult result = mockMvc.perform(post(url).contentType("application/json")
				.content(objectMapper.writeValueAsString(datNuoc))
				.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println("Country ID: " + response);
	}
}
