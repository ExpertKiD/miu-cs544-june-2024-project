package edu.miu.attendance.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.attendance.dto.MailingDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;

@Aspect
@Configuration
@Async
public class Mailing {

    @Autowired
    JmsTemplate jmsTemplate;

    @AfterReturning("execution(* edu.miu.attendance.service.*.save*(..))")
    public void afterSave(JoinPoint joinPoint) throws JsonProcessingException {
        MailingDto dto=new MailingDto("Saving data", joinPoint.getSignature().getName(), "123","aaa");
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(dto);
        jmsTemplate.convertAndSend("mail-sending",dtoAsString);
    }

    @AfterReturning("execution(* edu.miu.attendance.service.*.delete*(..))")
    public void afterDelete(JoinPoint joinPoint) throws JsonProcessingException {
        MailingDto dto=new MailingDto("Deleting data", joinPoint.getSignature().getName(), "123","aaa");
        ObjectMapper objectMapper = new ObjectMapper();
        String dtoAsString = objectMapper.writeValueAsString(dto);
        jmsTemplate.convertAndSend("mail-sending",dtoAsString);
    }
}
