package com.example.demo.repository;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Memo;

@Repository
// @Repository는 컴포넌트 이고 컨테이너가 관리하는 Bean이 된다.
// 이거하나만 붙여줬는데 스프링에서 memoDao에 대한 메모리를 만들어서 인스턴스를 관리한다
// DemoApplication의 패키지가 com.example.demo 이면 demo 하위만 찾게된다.
public class MemoDao {
    private final JdbcTemplate jdbcTemplate; // 필드를 final로 선언하면 반드시 생성자에서 초기화 한다.

    // 생성자에 파라미터를 넣어주면 스프링 부트가 자동으로 주입한다.
    // 생성자 주입이라고 한다.
    public MemoDao(DataSource dataSource) {
        System.out.println("MemoDao 생성자 호출");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // memo 테이블에 한건 저장. 저장을 성공하면 true, 실패하면 falae를 반환한다.
    public boolean addMemo(Memo memo) {
        String sql = "INSERT INTO MEMO(id,content) VALUES(?,?)";
        int result = jdbcTemplate.update(sql, memo.getid(), memo.getContent());
        return result == 1;
    }

    public boolean deleteMemo(int id) {
        String sql = "DELETE FROM memo WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        return result == 1;
    }

    public Memo getMemo(int id) {
        String sql = "SELECT id, content FROM memo WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Memo memo = new Memo();
            memo.setid(rs.getInt("id"));
            memo.setContent(rs.getString("content"));
            return memo;
        }, id);
    }
}