package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.domain.Memo;
import com.example.demo.repository.MemoDao;

//@Componenent 가 붙어있는 객체는 스프링 컨테이너가 관리하는 객체 (BEAN)

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// DataSource 에 대한 BEAN을 주입한다(메모리에 올라간다)
	@Autowired
	MemoDao memoDao;

	@Override
	public void run(String... args) throws Exception {
		// 스프링부트는 커맨드라인러너라는 구현하고있으면 이 run을 시작점으로 변경한다.
		// 인서트
		// Memo memo = new Memo();
		// memo.setid(5);
		// memo.setContent("메모 네번째ㅐ");
		// memoDao.addMemo(memo);

		// 딜리트
		// boolean flag = memoDao.deleteMemo(3);
		// System.out.println("flag : " + flag);

		// 조회
		Memo memo = memoDao.getMemo(1);
		System.out.println(memo.getid() + ", " + memo.getContent());
	}

}