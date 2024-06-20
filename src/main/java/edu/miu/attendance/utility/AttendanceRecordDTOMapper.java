package edu.miu.attendance.utility;

import edu.miu.attendance.dto.AttendanceRecordExcelDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AttendanceRecordDTOMapper implements RowMapper<AttendanceRecordExcelDTO> {
    @Override
    public AttendanceRecordExcelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        String studentid = rs.getString("studentid");
         String firstName = rs.getString("firstName");
         String lastName = rs.getString("lastName");
         String facultyFirstNam = rs.getString("facultyFirstName");
         String facultyLastName = rs.getString("facultyLastName");
         String CourseCode = rs.getString("CourseCode");
         String CourseName = rs.getString("CourseName");
         String department = rs.getString("department");
        double credits =rs.getDouble("credits");
        Timestamp timestamp =  rs.getTimestamp("scanDateTime");
        LocalDateTime scanDateTime = timestamp.toLocalDateTime();
        String name = rs.getString("name");
        String type = rs.getString("type");


        return new AttendanceRecordExcelDTO(studentid,firstName,lastName,
                facultyFirstNam,facultyLastName,CourseCode,CourseName,department,credits,scanDateTime,name,type);
    }
}
