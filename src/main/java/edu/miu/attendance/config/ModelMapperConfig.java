package edu.miu.attendance.config;

import edu.miu.attendance.domain.AttendanceRecord;
import edu.miu.attendance.dto.AttendanceRecordDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean("modelMapper")
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AttendanceRecord, AttendanceRecordDTO>() {
            @Override
            protected void configure() {
                map().setLocationName(source.getLocation().getName());
                map().setLocationType(source.getLocation().getLocationType().getType());
                map().setCourseOfferingName(source.getCourseOffering().getCourse().getName());
            }
        });
        return modelMapper;
    }
}
