package edu.miu.attendance.utility;

import edu.miu.attendance.dto.StudentCourseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseDTOMapper implements RowMapper<StudentCourseDTO> {
    @Override
    public StudentCourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
         String code = rs.getString("CourseCode");
         String name = rs.getString("CourseName");
         String description = rs.getString("CourseDescription");
         double credits = rs.getDouble("CourseCredit");
         String grade = rs.getString("grade");

        return new StudentCourseDTO(code,name,description,credits,grade);
    }
}
