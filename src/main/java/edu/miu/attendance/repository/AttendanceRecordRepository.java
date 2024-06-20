package edu.miu.attendance.repository;

import edu.miu.attendance.domain.AttendanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    @Query("SELECT ar FROM AttendanceRecord ar JOIN FETCH ar.student s WHERE s.studentId =:studentId")
    Page<AttendanceRecord> findAttendanceRecordsByStudentId(@Param("studentId") String studentId, Pageable pageable);
}

