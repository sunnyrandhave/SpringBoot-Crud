package com.crud.springboot_crud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootCrudApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Test
	public void addtest(){
		Assertions.assertEquals(10,(5+5));
	}

}
