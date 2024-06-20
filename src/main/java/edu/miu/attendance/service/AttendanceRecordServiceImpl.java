package edu.miu.attendance.service;

import edu.miu.attendance.domain.AttendanceRecord;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import edu.miu.attendance.dto.AttendanceRecordExcelDTO;
import edu.miu.attendance.repository.AttendanceRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<AttendanceRecordDTO> getAttendanceRecordsForStudent(Long studentId, Pageable pageable) {
        Page<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByStudentId(studentId, pageable);

        return attendanceRecords.map(attendanceRecord -> {
            AttendanceRecordDTO dto = modelMapper.map(attendanceRecord, AttendanceRecordDTO.class);
            dto.setLocationName(attendanceRecord.getLocation().getName());
            dto.setLocationType(attendanceRecord.getLocation().getLocationType().getType());
            dto.setCourseOfferingName(attendanceRecord.getCourseOffering().getCourse().getName());
            return dto;
        });
    }
}
