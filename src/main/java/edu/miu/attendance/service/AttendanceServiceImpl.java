//package edu.miu.attendance.service;
//
//import edu.miu.attendance.domain.AttendanceRecord;
//import edu.miu.attendance.domain.Student;
//import edu.miu.attendance.dto.AttendanceRecordDTO;
//import edu.miu.attendance.repository.AttendanceRecordRepository;
//import edu.miu.attendance.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AttendanceServiceImpl implements AttendanceService {
//
//    @Autowired
//    private AttendanceRecordRepository attendanceRecordRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Override
//    public List<AttendanceRecordDTO> getAttendanceRecordsForStudent(Long studentId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByStudent(student);
//
//        return attendanceRecords.stream().map(this::convertToDTO).toList();
//    }
//    private AttendanceRecordDTO convertToDTO(AttendanceRecord record) {
//        AttendanceRecordDTO dto = new AttendanceRecordDTO();
//        dto.setId(record.getId());
//        dto.setScanDateTime(record.getScanDateTime());
//        dto.setLocationName(record.getLocation().getName());
//        dto.setLocationType(record.getLocation().getLocationType().getType());
//        dto.setStudentId(record.getStudent().getStudentId());
//        return dto;
//    }
//}
