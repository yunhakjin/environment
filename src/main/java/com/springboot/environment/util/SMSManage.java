package com.springboot.environment.util;

/**
 * Created by sts on 2018/12/30.
 */


import com.springboot.environment.util.SmsClientSend;

public class SMSManage {

    public static SMSManage smsManage = null;
    private SMSManage(){
        super();
    }
    public static SMSManage getInstance(){
        if(smsManage==null){
            smsManage = new SMSManage();
        }
        return smsManage;
    }
    /**
     * 发送短信
     * @param content 短信内容：必须以【xxxx】结尾
     * @param mobile 接收手机号码； 多个以逗号分隔
     * @return
     */
    public boolean send(String content,String mobile){
        boolean flag = false;
        String url = "http://www.uehyt.com/sms.aspx";
        String userid = "613";
        String account = "17317788510";
        String password = "123456qwe";

        String send = SmsClientSend.sendSms(url, userid, account, password, mobile, content);
        System.out.println(send);
        if(send.indexOf("<returnstatus>Success</returnstatus>")>0){
            flag = true;
        }
        return flag;
    }
//    public static void main(String[] args){
//        String string1 = "13700719173";
//        String tel = "ceshi【xx】";
//        SMSManage.getInstance().send("ceshi【xx】", string1);
//    }
}
