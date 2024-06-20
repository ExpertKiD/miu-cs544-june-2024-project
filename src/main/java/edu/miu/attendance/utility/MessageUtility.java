package edu.miu.attendance.utility;



public class MessageUtility {


    public MessageResponse validationMessage (String name) {
        MessageResponse message = new MessageResponse();
        message.setStatus("failed");
        message.setMessage(name+" "+ "Name Already Exists." );
        return message;
    }

    public MessageResponse idNotFoundMessage () {
        MessageResponse message = new MessageResponse();
        message.setStatus("failed");
        message.setMessage("Id Not Found" );
        return message;
    }

    public MessageResponse addExpMessage (String exception) {
        MessageResponse message = new MessageResponse();
        message.setStatus("Failed");
        message.setType("Exception");
        message.setMessage("Exception Occured: "+ exception );
        return message;
    }

    public MessageResponse deleteMessage (String name) {
        MessageResponse message = new MessageResponse();
        message.setStatus("ok");
        message.setType("delete");
        message.setMessage(name+" "+ "Delete "+ "Successfully." );
        return message;
    }


    public MessageResponse codeValidationMessage (String courseCode) {
        MessageResponse message = new MessageResponse();
        message.setStatus("failed");
        message.setMessage(courseCode+" "+ "Course Code Already Exists." );
        return message;
    }
}
