package com.emaildemo.email.controller;

import com.emaildemo.email.Utility.CommonUtils;
import com.emaildemo.email.model.EmailTemplate;
import com.emaildemo.email.service.EmailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailTemplate emailTemplate){
        JSONObject json = new JSONObject(emailTemplate);
        JSONObject errorlist = CommonUtils.checkvalidinput(json);
        if(errorlist.isEmpty()) {
            Boolean issent = emailService.send(emailTemplate);
            if (!issent) {
                return new ResponseEntity<>("Email not sent..", HttpStatus.BAD_REQUEST);
            }
        }
         return new ResponseEntity<>("Email sent successfulyy.........", HttpStatus.OK);
    }
    @PostMapping("/send-email-with-attach")
    public ResponseEntity<String> uplodafile(@RequestParam("file")MultipartFile file, @RequestParam("emailTemplate") String emailTemplate)  {
        JSONObject json = new JSONObject(emailTemplate);
        JSONObject errorlist = CommonUtils.checkvalidinput(json);
        if(errorlist.isEmpty()) {
            EmailTemplate emaildata = setEmailinfo(json);
            Boolean issent = emailService.sendwithattach(emaildata, file);

            if (!issent) {
                return new ResponseEntity<>("Email not sent..", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>(errorlist+"",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Email sent successfulyy.........",HttpStatus.OK);

    }

    private EmailTemplate setEmailinfo(JSONObject json) {
        EmailTemplate obj = new EmailTemplate();
        obj.setFrom(json.getString("from"));
        obj.setTo(json.getString("to"));
        obj.setMessage(json.getString("message"));
        obj.setSubject(json.getString("subject"));
        return obj;

    }
}
