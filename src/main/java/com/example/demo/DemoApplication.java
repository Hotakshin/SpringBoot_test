package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	//main메소드는 Spring이 관리하지 않는다.
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}
	// DataSource Bean(Spring이 관리하는 객체)
	@Autowired //자동으로 주입
	DataSource dataSource;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("스프링 부트가 관리하는 빈을 사용할 수 있다.");

		Connection conn = dataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement("select id, content from memo");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int roleId = rs.getInt("id");
			String name = rs.getString("content");
			System.out.println(roleId + ", " + name);
		}
		rs.close();
		ps.close();
		conn.close();
	}

}
