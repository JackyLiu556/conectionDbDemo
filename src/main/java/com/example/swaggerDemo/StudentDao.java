package com.example.swaggerDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public Student getById(Integer studentId) {
		
		String sql ="select id ,name from student where id = :studentId";
		Map<String,Object> map = new HashMap<>();
		map.put("studentId", studentId);
		
		List<Student> list = namedParameterJdbcTemplate.query(sql, map,new StudentRowMapper());
		
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;	
		}
	}
}
