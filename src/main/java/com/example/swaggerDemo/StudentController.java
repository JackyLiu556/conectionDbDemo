package com.example.swaggerDemo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/jpa/students")
	public String insert(@RequestBody Student student) {
		
		studentRepository.save(student);
		
		return "jpa 執行資料庫 insert 操作";
	}
	
	@PostMapping("/jdbc/students")
	public String insert2(@RequestBody Student student) {
		
		String sql = " INSERT INTO student(name) values(:studentName)";
		
		Map<String,Object> map = new HashMap<>();
		map.put("studentName", student.getName());
		
		namedParameterJdbcTemplate.update(sql, map);
		
		//====================================
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		int id =  keyHolder.getKey().intValue();
		System.out.println("mysql 自動生成key :"+id);
				
		return "jdbc 執行資料庫 insert 操作";
	}
	
	@DeleteMapping("/jdbc/students/{studendId}")
	public String insert2(@PathVariable Integer studendId) {
		
		String sql = " Delete from student where id = :studendId";
		
		Map<String,Object> map = new HashMap<>();
		map.put("studendId", studendId);
		
		namedParameterJdbcTemplate.update(sql, map);

		return "jdbc 執行資料庫 Delete 操作";
	}
	
	@GetMapping("/jdbc/students/{studendId}")
	public Student select(@PathVariable Integer studendId) {
		
		System.out.println("jdbc 執行查詢 並實現 MVC");
		return studentService.getById(studendId);
	}
}
