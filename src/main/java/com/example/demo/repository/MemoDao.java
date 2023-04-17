package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Memo;

@Repository
// @Repository는 컴포넌트이고 컨테이너가 관리하는 Bean이 된다.
// 이거하나만 붙여주게되면 스프링에서 memoDao에 대한 메모리를 만들어서 인스턴스를 관리한다
// DemoApplication의 패키지가 com.example.demo 이면 demo 하위만 찾게된다.
public class MemoDao {
    // INSERT 를 쉽게 해주는 인터페이스
    private SimpleJdbcInsertOperations insertAction;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate paramJdbcTemplate;
    // 필드를 final로 선언하면 반드시 생성자에서 초기화 해줘야한다.

    // 그래서 아래와 같은 생성자를 생성해주게된다.
    // 생성자에 파라미터를 넣어주면 스프링 부트가 자동으로 주입한다.
    // 생성자 주입이라고 한다.
    // 그래서 MeMoDao() 안에 DataSource dataSource 의 파라미터를 넣어준다.
    public MemoDao(DataSource dataSource) {
        System.out.println("MemoDao 생성자 호출");
        jdbcTemplate = new JdbcTemplate(dataSource);
        // jdbcTemplate는 DataSource가 있어야한다.
        insertAction = new SimpleJdbcInsert(dataSource).withTableName("memo");
        paramJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        // 생성자생성
    }

    // memo 테이블에 한건 저장. 저장을 성공하면 true, 실패하면 falae를 반환한다.
    public boolean addMemo(Memo memo) {
        String sql = "INSERT INTO MEMO(id,content) VALUES(?,?)";
        int result = jdbcTemplate.update(sql, memo.getid(), memo.getContent());
        return result == 1;
    }

    // INSERT 를 쉽게 해주는 Class SimpleJdbcInsert
    public boolean add2Memo(Memo memo) {
        // memo는 id,content의 프로퍼티를 갖고있다.
        // INSERT INTO MEMO(id,content) VALUES(?,?);
        // 위와 같은 SQL을 SimpleJdbcInsert가 내부적으로 만들어준다.
        // MEMO클레스의 프로퍼티이름과 칼럼명이 규칙이 맞아야한다.
        SqlParameterSource params = new BeanPropertySqlParameterSource(memo);
        int result = insertAction.execute(params);
        return result == 1;
    }

    public boolean deleteMemo(int id) {
        String sql = "DELETE FROM memo WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        return result == 1;
    }

    // DELETE 를 쉽게 해주는 NamedParameterJdbcTemplate
    public boolean delete2Memo(int id) {
        String sql = "DELETE FROM memo WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        int result = paramJdbcTemplate.update(sql, params);
        return result == 1;
    }

    // 1건조회
    public Memo getMemo(int id) {
        String sql = "SELECT id, content FROM memo WHERE id = ?";
        // JdbcTemplate 객체를 사용하여 SQL 쿼리를 실행하고,
        // 결과를 Memo 객체로 매핑하여 반환한다.
        try {
            // 람다표현식을 사용하여 rs객체를 처리하는 콜백함수를 정의한다.
            // rs 는 SQL 쿼리 결과를 나타내는 ResultSet 객체이며,
            // rowNum은 행번호를 나타내는 정수이다.
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Memo memo = new Memo();
                memo.setid(rs.getInt("id"));
                memo.setContent(rs.getString("content"));
                return memo;
            }, id);
        } catch (Exception e) {
            return null;
        }
    }

    // NamedParameterJdbcTemplate를 이용해 단건조회 하는방법
    // 람다표현식을 대체하여 조금더 쉽게 코드를 짤수있도록해준다.
    public Memo getMemo3(int id) {
        try {
            String sql = "SELECT id, content FROM memo WHERE id = :id";
            SqlParameterSource params = new MapSqlParameterSource("id", id);
            // rowMapper 인터페이스중 BeanPropertyRowMapper 라는것이 있는데
            // 요걸 사용하게되면 위의 람다표현식을 자동으로 하도록 설정해줄 수 있다.
            RowMapper<Memo> MemoRowMapper = BeanPropertyRowMapper.newInstance(Memo.class, null);
            return paramJdbcTemplate.queryForObject(sql, params, MemoRowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    // NamedParameterJdbcTemplate를 이용해 모든 메모조회 하는방법
    public List<Memo> getMemoList2() {
        String sql = "SELECT id, content FROM memo ";
        RowMapper<Memo> MemoRowMapper = BeanPropertyRowMapper.newInstance(Memo.class, null);
        return paramJdbcTemplate.query(sql, MemoRowMapper);
    }

    // 모든 메모 조회
    public List<Memo> getMemoList() {
        String sql = "SELECT id, content FROM memo";
        // 쿼리메소드는 여러건의 결과를 구할때 사용하는 메소드
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Memo memo = new Memo();
                memo.setid(rs.getInt("id"));
                memo.setContent(rs.getString("content"));
                return memo;
            });
        } catch (Exception e) {
            return null;
        }
    }

    // 람다식을 사용하지 않고 쓰는 조회
    public Memo getMemo2(int id) {
        String sql = "SELECT id, content FROM memo WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MemoRowMapper(), id);
    }
}

// 람다식을 사용하지 않는 조회를 위해 RowMapper 인터페이스 추가
// 이 클래스가 다른 클래스는 전혀 사용할일이 없는경우 클래스를 굳이 만들필요가있을까..?
// 그래서 람다 표현식을 사용한다.
class MemoRowMapper implements RowMapper<Memo> {

    @Override
    public Memo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Memo memo = new Memo();
        memo.setid(rs.getInt("id"));
        memo.setContent(rs.getString("content"));
        return memo;
    }

}