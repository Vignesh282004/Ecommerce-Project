package com.myecom.gallery.gallery.Services;

public interface MailService {
    public void sendmail(String name,String email,String subject,String msg);
    public boolean sendmail(String email,String subject,String msg);
}
