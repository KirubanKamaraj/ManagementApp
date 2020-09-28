package com.kirubankamaraj.managementapp2;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.util.Date;
import java.util.Properties;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSend extends Service {

    /**
     * Utility method to send simple HTML email
     *
     * @param toEmail
     * @param subject
     * @param body
     */
    private String toEmail;
    private String subject;
    private String body;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toEmail = intent.getStringExtra("mail");
        subject = intent.getStringExtra("subject");
        body = intent.getStringExtra("body");

        new Send().execute();
        return START_STICKY;
    }


/*
    public EmailSend(String toEmail, String subject, String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }
*/

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class Send extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                final String fromEmail = "nallurkiruban@gmail.com"; //requires valid gmail id
                final String password = "igaeicfch0"; // correct password for gmail id

                Authenticator auth = new Authenticator() {
                    //override the getPasswordAuthentication method
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                };

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
                props.put("mail.smtp.port", "587"); //TLS Port
                props.put("mail.smtp.auth", "true"); //enable authentication
                props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

                Session session = Session.getInstance(props, auth);
                MimeMessage msg = new MimeMessage(session);

                //set message headers
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8bit");

                msg.setFrom(new InternetAddress("nallurkiruban@gmail.com", "NoReply-JD"));

                msg.setReplyTo(InternetAddress.parse("nallurkiruban@gmail.com", false));

                msg.setSubject(subject, "UTF-8");

                msg.setText(body, "UTF-8");

                msg.setSentDate(new Date());

                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}