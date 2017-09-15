package com.example.repository;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Dept;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeptRepositoryTest {

	@Inject
	DeptRepository deptrepository;
	
	Dept d10 = new Dept(new BigInteger("10"), "경리부", "서울");
	Dept d20 = new Dept(new BigInteger("20"), "인사부", "인천");
	Dept d30 = new Dept(new BigInteger("30"), "영업부", "용인");
	Dept d40 = new Dept(new BigInteger("40"), "전산부", "수원");
	
	List<Dept> list = Arrays.asList(d10, d20, d30, d40);
	
	@Before
	public void before() {
		
		deptrepository.save(list);
	}
	
	@Test
	public void findAll() {
		System.out.println("deptrepository = " + deptrepository.getClass());
		deptrepository.findAll().forEach(e -> {
			System.out.println(e);
		});
		
//		org.hamcrest.CoreMatchers.*
		assertThat(deptrepository.findAll(), is(list));
		
//		org.assertj.core.api.Assertions.assertThat ==> 더 좋다
		assertThat(deptrepository.findAll()).hasSize(4)
												 .isEqualTo(list);
	}

	@Test
	public void count() {
		long count = deptrepository.count();
		
		assertThat(count, is(4));
		assertThat(count).isEqualTo(4)
						   .isLessThan(10)
						   .isGreaterThan(1);
	}
	
	@Test
	public void save() {
		Dept dept = new Dept(new BigInteger("10"), "xxx", "yyy");
		deptrepository.save(dept);
		
		System.out.println(deptrepository.findOne(new BigInteger("10")));
		
		assertThat(deptrepository.findOne(new BigInteger("10")), is(dept));
		assertThat(deptrepository.findOne(new BigInteger("10"))).isEqualTo(dept)
																		.hasFieldOrPropertyWithValue("deptno", new BigInteger("10"))
																		.hasFieldOrPropertyWithValue("dname", "xxx")
																		.hasFieldOrPropertyWithValue("loc", "yyy");
	}
	
	@Test
	public void delete() {
		deptrepository.delete(new BigInteger("20"));
		
		assertThat(deptrepository.findOne(new BigInteger("20")), is(nullValue()));
		assertThat(deptrepository.findOne(new BigInteger("20"))).isNull();
	}
	
	@Test
	public void deleteAll() {
		deptrepository.deleteAll();
		
		assertThat(deptrepository.findAll()).hasSize(0)
												 .isNotNull()
												 .isNotEmpty();
												 
	}
	
	@Test
	public void findByLoc() {
		Dept dept = deptrepository.findByLoc("서울");  // 쿼리 메소드
		System.out.println(dept);
		
		assertThat(deptrepository.findByLoc("서울"))
									 .hasFieldOrPropertyWithValue("dname", "경리부");
		
	}
	
	@Test
	public void findByDnameOrLoc() {
		deptrepository.findAll().forEach(e -> {
			System.out.println(e);
		});
		
		deptrepository.findByDnameOrLoc("인사부", "용인").forEach(e -> {
			System.out.println(e);
		});
	}
	
	@Test
	public void findByDeptnoGreaterThanOrderByDnameDesc() {
		deptrepository.findByDeptnoGreaterThanOrderByDnameDesc(new BigInteger("20")).forEach(e -> {
			System.out.println(e);
		});
	}
	
	@Test
	public void findByDeptnoBetween() {
		deptrepository.findByDeptnoBetween(new BigInteger("20"), new BigInteger("30")).forEach(e -> {
			System.out.println(e);
		});
	}
}
