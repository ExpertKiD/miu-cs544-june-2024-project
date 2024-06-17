//package edu.miu.attendance.repository;
//
//import edu.miu.attendance.domain.AttendanceRecord;
//import edu.miu.attendance.domain.Student;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
//    List<AttendanceRecord> findByStudent(Student student);
//}