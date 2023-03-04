package com.emaildemo.email.Utility;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class CommonUtils {

    public static JSONObject checkvalidinput(JSONObject jsonObject) {
        JSONObject errorobj = new JSONObject();
        if(jsonObject.has("from")){
            String senderEmail = jsonObject.optString("from");
            if(!isvalidemail(senderEmail)){
                errorobj.put("from","please enter a valid email eg : abc@gmail.com");
            }
        }else{
            errorobj.put("fromAddress","Missing parameter");
        }
        if(jsonObject.has("to")){
            String receivermail = jsonObject.optString("to");
            if(!isvalidemail(receivermail)){
                errorobj.put("to","please enter a valid email eg : abc@gmail.com");
            }
        }else{
            errorobj.put("fromAddress","Missing parameter");
        }
        if(jsonObject.has("subject")){
            String sub = jsonObject.getString("subject");
            if(sub.isEmpty()){
                errorobj.put("subject","Please enter subject");
            }
            }else{
            errorobj.put("subject","Missing parameter");
        }
        if(jsonObject.has("message")){
            String msg = jsonObject.getString("message");
            if(msg.isEmpty()){
                errorobj.put("message","Please enter message body");
            }
        }else {
            errorobj.put("message","Missing parameter");

        }
        return errorobj;
    }

    private static boolean isvalidemail(String senderEmail) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (senderEmail == null)
            return false;
        return pat.matcher(senderEmail).matches();
    }
}
