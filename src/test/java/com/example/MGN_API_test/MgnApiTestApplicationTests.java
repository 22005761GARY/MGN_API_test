package com.example.MGN_API_test;

import com.example.MGN_API_test.controller.TransController;
import com.example.MGN_API_test.controller.dto.request.MgniRequest;
import com.example.MGN_API_test.model.MgniRepository;
import com.example.MGN_API_test.model.entity.Mgni;
import com.example.MGN_API_test.service.MgniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MgnApiTestApplication.class)
@WebAppConfiguration
class MgnApiTestApplicationTests {

	@Autowired
	private MgniRepository mgniRepository;
	@Autowired
	private MgniService mgniService;
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private MgniRequest mgniRequest;
	@Before
	public void setup(){
		mgniRequest = new MgniRequest("MGI20220928174618929", null, null);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void contextLoads() throws Exception {
		String uri = "/api/v1/mgni/spec";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).content(objectMapper.writeValueAsString(mgniRequest)).accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		System.out.println(status);
		Assert.assertEquals("錯誤",200,status);
	}

//	@Test
//	public void testGetDataBySpec() throws Exception {
//		when(this.mgniService.getDataBySpec(new MgniRequest("MGI20220928174618929", null, null))).thenReturn(mgniList);
//		ResultActions resultActions = mockMvc.perform(get("/api/v1/mgni/spec?id=MGI20220928174618929").contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("id",is("MGI20220928174618929")));
//	}
//	@Test
//	public void getMgniBySpec() throws Exception {
////		Optional<Mgni> mgni;
//		when(mgniRepository.findById("MGI20220928174618929")).thenReturn(new Mgni("MGI20220928174618929", LocalDateTime.parse("2023 - 02 - 10 16:43:33"), "1", "5", "3", "222", "TWD", "2", "0921229414", "1", "tt", new BigDecimal(10000), "Bryan", "0956110658", "1", LocalDateTime.parse("2023-02-10 16:43:33"), null));
//
//		List<Mgni> targetMgni = mgniService.getDataBySpec(new MgniRequest("MGI20220928174618929",null,null));
//
//		assertEquals("MGI20220928174618929",targetMgni.get(0).getId());
//
//	}
}
