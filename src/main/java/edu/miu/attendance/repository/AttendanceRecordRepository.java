package edu.miu.attendance.repository;

import edu.miu.attendance.domain.AttendanceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Page<AttendanceRecord> findByStudentId(Long studentId, Pageable pageable);
}
