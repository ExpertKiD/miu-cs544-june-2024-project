package edu.miu.attendance.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.domain.Person;
import edu.miu.attendance.dto.MailingDto;
import edu.miu.attendance.exception.ResourceNotFoundException;
import edu.miu.attendance.repository.PersonRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Aspect
@Configuration
@EnableAsync
public class Mailing {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    PersonRepository personRepository;

    @Async
    @AfterReturning("execution(* edu.miu.attendance.service.*.save*(..))")
    public void afterSave(JoinPoint joinPoint) throws JsonProcessingException {

        String user = (String) joinPoint.getArgs()[0];

        Person person=personRepository.findPersonByUsername(user).orElseThrow(() -> new ResourceNotFoundException("Data doesn't exit!"));

        MailingDto dto=new MailingDto("Attendance Record System", "Your data is saving successfully in system.", person.getId().toString(),person.getEmailAddress());
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(dto);
        jmsTemplate.convertAndSend("mail-sending",dtoAsString);
    }

    @Async
    @AfterReturning("execution(* edu.miu.attendance.service.*.delete*(..))")
    public void afterDelete(JoinPoint joinPoint) throws JsonProcessingException {

        String user = (String) joinPoint.getArgs()[0];

        Person person=personRepository.findPersonByUsername(user).orElseThrow(() -> new ResourceNotFoundException("Data doesn't exit!"));


        MailingDto dto=new MailingDto("Attendance Record System", "Your data is successfully deleted in system.", person.getId().toString(),person.getEmailAddress());
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(dto);
        jmsTemplate.convertAndSend("mail-sending",dtoAsString);
    }
}
