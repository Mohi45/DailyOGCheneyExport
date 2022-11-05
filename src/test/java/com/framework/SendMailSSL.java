package com.framework;


import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

import static com.framework.Constant.GmailPassword;
import static com.framework.Constant.GmailUser;

public class SendMailSSL {
   private final static Logger logger = Logger.getLogger(SendMailSSL.class);

   public static Session createConnection() throws MessagingException {
      // Create IMAPSSLStore object
      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.socketFactory.port", "465");
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.port", "587"); 
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.ssl.protocols", "TLSv1.2");
      // All sysout statements are used for testing, have to remove them
      // while implementation
      System.out.println("Connecting to gmail...");

      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(GmailUser, GmailPassword);// change
            // accordingly
         }
      });
      return session;
   }

   /*
    * public static IMAPSSLStore createConnection() throws MessagingException { //
    * Create IMAPSSLStore object Properties props = System.getProperties();
    * props.setProperty("mail.store.protocol", "imaps");
    *
    * Session session = Session.getDefaultInstance(props, new
    * javax.mail.Authenticator() { protected PasswordAuthentication
    * getPasswordAuthentication() { return new
    * PasswordAuthentication(Constant.GmailUser, Constant.GmailPassword);// change
    * // accordingly } }); URLName urlName = new URLName("imap.gmail.com");
    * IMAPSSLStore store = new IMAPSSLStore(session, urlName);
    *
    * // All sysout statements are used for testing, have to remove them // while
    * implementation System.out.println("Connecting to gmail...");
    *
    * // Connect to GMail, enter user name and password here
    * store.connect("imap.gmail.com", Constant.GmailUser, Constant.GmailPassword);
    *
    * System.out.println("Connected to - " + store);
    *
    * sendMailAction(session, Constant.GmailUser, Constant.GmailPassword);
    *
    * System.out.println("send mail success"); return store; }
    */

   public static void sendMailActionXlsx(String PurveyorName, String Restaurantname) {
      String to = Constant.sendMailToDE;
      String user = Constant.sendMailFrom;// change
      // accordingly
      try {
         // get connection
         Session session = createConnection();
         // String filepath = RandomAction.setdownloadDir();
         File GFS_OG = RandomAction.getLatestFilefromDirxlsx("/var/jenkins_home/workspace/DailyCheneyOGExport/");
         String filename = GFS_OG.getAbsolutePath();
         System.out.println(filename);

         MimeMessage message = new MimeMessage(session);
         // message.setFrom(new InternetAddress(user));
         // message.addRecipient(Message.RecipientType.TO, new
         // InternetAddress(to));
         // message.setSubject("Message Alert");

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly
         messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // smessageBodyPart1.addRecipient(Message.RecipientType.CC, new
         // InternetAddress("teamesave@gmail.com"));

         // Subject of mails
         message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + Restaurantname);
         // Body of mails
         // message.setContent("Cheney OG export- date & time : " +
         // RandomAction.getDate(), "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();

         DataSource source = new FileDataSource(filename);
         messageBodyPart2.setDataHandler(new DataHandler(source));
         messageBodyPart2.setFileName(filename);

         // 5) create Multipart object and add MimeBodyPart objects to this
         // object
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         // 6) set the multiplart object to the message object
         message.setContent(multipart);

         // send message
         /*
          * Transport transport = session.getTransport("smtp");
          * transport.connect(Constant.GmailURL,Constant.GmailUser ,
          * Constant.GmailPassword); transport.sendMessage(message,
          * messageBodyPart1.getAllRecipients());
          */
         Transport.send(message, messageBodyPart1.getAllRecipients());

         System.out.println("Message send success for " + Restaurantname);

      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
         // TODO: handle exception
      }

   }

   public static void sendMailActionXls(String PurveyorName, String Restaurantname) {
      String to = Constant.sendMailToDE;
      String user = Constant.sendMailFrom;// change
      // accordingly
      try {
         // get connection
         Session session = createConnection();
         // String filepath = RandomAction.setdownloadDir();
         File GFS_OG = RandomAction.getLatestFilefromDirxls(System.getProperty("user.home") + "\\Downloads\\");
         String filename = GFS_OG.getAbsolutePath();
         System.out.println(filename);

         MimeMessage message = new MimeMessage(session);
         // message.setFrom(new InternetAddress(user));
         // message.addRecipient(Message.RecipientType.TO, new
         // InternetAddress(to));
         // message.setSubject("Message Alert");

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly
         messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // smessageBodyPart1.addRecipient(Message.RecipientType.CC, new
         // InternetAddress("teamesave@gmail.com"));

         // Subject of mails
         message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + Restaurantname);
         // Body of mails
         // message.setContent("Cheney OG export- date & time : " +
         // RandomAction.getDate(), "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();

         DataSource source = new FileDataSource(filename);
         messageBodyPart2.setDataHandler(new DataHandler(source));
         messageBodyPart2.setFileName(filename);

         // 5) create Multipart object and add MimeBodyPart objects to this
         // object
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         // 6) set the multiplart object to the message object
         message.setContent(multipart);

         // send message
         /*
          * Transport transport = session.getTransport("smtp");
          * transport.connect(Constant.GmailURL,Constant.GmailUser ,
          * Constant.GmailPassword); transport.sendMessage(message,
          * messageBodyPart1.getAllRecipients());
          */
         Transport.send(message, messageBodyPart1.getAllRecipients());

         System.out.println("Message send success for " + Restaurantname);

      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
         // TODO: handle exception
      }

   }

   public static void sendMailActionPDF(String PurveyorName, String Restaurantname) {
      String to = Constant.sendMailToDE;
      String user = Constant.sendMailFrom;// change
      // accordingly
      try {
         // get connection
         Session session = createConnection();
         // String filepath = RandomAction.setdownloadDir();
         File GFS_OG = RandomAction.getLatestFilefromDirPDF(System.getProperty("user.home") + "\\Downloads\\");
         String filename = GFS_OG.getAbsolutePath();
         System.out.println(filename);

         MimeMessage message = new MimeMessage(session);
         // message.setFrom(new InternetAddress(user));
         // message.addRecipient(Message.RecipientType.TO, new
         // InternetAddress(to));
         // message.setSubject("Message Alert");

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly
         messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // smessageBodyPart1.addRecipient(Message.RecipientType.CC, new
         // InternetAddress("teamesave@gmail.com"));

         // Subject of mails
         message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + Restaurantname);
         // Body of mails
         // message.setContent("Cheney OG export- date & time : " +
         // RandomAction.getDate(), "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();

         DataSource source = new FileDataSource(filename);
         messageBodyPart2.setDataHandler(new DataHandler(source));
         messageBodyPart2.setFileName(filename);

         // 5) create Multipart object and add MimeBodyPart objects to this
         // object
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         // 6) set the multiplart object to the message object
         message.setContent(multipart);

         // send message
         /*
          * Transport transport = session.getTransport("smtp");
          * transport.connect(Constant.GmailURL,Constant.GmailUser ,
          * Constant.GmailPassword); transport.sendMessage(message,
          * messageBodyPart1.getAllRecipients());
          */
         Transport.send(message, messageBodyPart1.getAllRecipients());

         System.out.println("Message send success for " + Restaurantname);

      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
         // TODO: handle exception
      }

   }

   public static void sendMailActionCsvDE(String PurveyorName, String Restaurantname) {
      String to = Constant.sendMailToDE;
      String user = Constant.sendMailFrom;// change
      // accordingly
      try {
         // get connection
         Session session = createConnection();
         // String filepath = RandomAction.setdownloadDir();
         File GFS_OG = RandomAction.getLatestFilefromDirCsv(System.getProperty("user.home") + "\\Downloads\\");
         String filename = GFS_OG.getAbsolutePath();
         System.out.println(filename);

         MimeMessage message = new MimeMessage(session);
         // message.setFrom(new InternetAddress(user));
         // message.addRecipient(Message.RecipientType.TO, new
         // InternetAddress(to));
         // message.setSubject("Message Alert");

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly
         messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // smessageBodyPart1.addRecipient(Message.RecipientType.CC, new
         // InternetAddress("teamesave@gmail.com"));

         // Subject of mails
         message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + Restaurantname);
         // Body of mails
         // message.setContent("Cheney OG export- date & time : " +
         // RandomAction.getDate(), "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();

         DataSource source = new FileDataSource(filename);
         messageBodyPart2.setDataHandler(new DataHandler(source));
         messageBodyPart2.setFileName(filename);

         // 5) create Multipart object and add MimeBodyPart objects to this
         // object
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         // 6) set the multiplart object to the message object
         message.setContent(multipart);

         // send message
         /*
          * Transport transport = session.getTransport("smtp");
          * transport.connect(Constant.GmailURL,Constant.GmailUser ,
          * Constant.GmailPassword); transport.sendMessage(message,
          * messageBodyPart1.getAllRecipients());
          */
         Transport.send(message, messageBodyPart1.getAllRecipients());

         System.out.println("Message send success for " + Restaurantname);

      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
         // TODO: handle exception
      }

   }

   public static void sendMailActionCsvES(String PurveyorName, String Restaurantname) {
      String to = Constant.sendMailToEsave;
      String user = Constant.sendMailFrom;// change
      // accordingly
      try {
         // get connection
         Session session = createConnection();
         if(isFileDownloaded_Ext(System.getProperty("user.home") + "\\Downloads\\",".xlsx")) {
 			System.out.println("File is Found at location");
 		}else {
 			System.out.println("File not found at loaction");
 		}
         // String filepath = RandomAction.setdownloadDir();
         File GFS_OG = RandomAction.getLatestFilefromDirCsv(System.getProperty("user.home") + "\\Downloads\\");
         String filename = GFS_OG.getAbsolutePath();
         System.out.println(filename);

		
         MimeMessage message = new MimeMessage(session);
         // message.setFrom(new InternetAddress(user));
         // message.addRecipient(Message.RecipientType.TO, new
         // InternetAddress(to));
         // message.setSubject("Message Alert");

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly
         messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // smessageBodyPart1.addRecipient(Message.RecipientType.CC, new
         // InternetAddress("teamesave@gmail.com"));

         // Subject of mails
         message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + Restaurantname);
         // Body of mails
         // message.setContent("Cheney OG export- date & time : " +
         // RandomAction.getDate(), "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();

         DataSource source = new FileDataSource(filename);
         messageBodyPart2.setDataHandler(new DataHandler(source));
         messageBodyPart2.setFileName(filename);

         // 5) create Multipart object and add MimeBodyPart objects to this
         // object
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         // 6) set the multiplart object to the message object
         message.setContent(multipart);

         // send message
         /*
          * Transport transport = session.getTransport("smtp");
          * transport.connect(Constant.GmailURL,Constant.GmailUser ,
          * Constant.GmailPassword); transport.sendMessage(message,
          * messageBodyPart1.getAllRecipients());
          */
         Transport.send(message, messageBodyPart1.getAllRecipients());

         System.out.println("Message send success for " + Restaurantname);

      } catch (AddressException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e.getMessage());
         // TODO: handle exception
      }

   }

   /*
   public static void sendReport(String Subject, String filename) {
        try {
            // Properties Constant = new Properties();
            // Constant.load(new FileInputStream("Config.properties"));
            // String to = Constant.getProperty("reportTo");
            // String user = Constant.getProperty("sendMailFrom");// change
            // // accordingly
            String user = Constant.sendMailFrom;
            String[] to = Constant.reportTo;
            String cc = Constant.cc;
            // get connection
            Session session = createConnection();

            MimeMessage message = new MimeMessage(session);

            MimeMessage messageBodyPart1 = new MimeMessage(session);
            messageBodyPart1.setFrom(new InternetAddress(user));// change
            // accordingly

            InternetAddress[] recipientAddress = new InternetAddress[to.length];
            int counter = 0;
            for (String recipient : to) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }

            messageBodyPart1.addRecipients(Message.RecipientType.TO, recipientAddress);
            messageBodyPart1.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));

            // Subject of mails
            message.setSubject(Subject);
            // Body of mails
            String date = RandomAction.getDate();
            message.setContent("Attached is the report for the OG export on : " + date, "text");

            // message.setText();

            // 4) create new MimeBodyPart object and set DataHandler object to
            // this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            logger.info("Attached file - " + filename);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            Transport.send(message, messageBodyPart1.getAllRecipients());

            logger.info("Message send success");

        } catch (AddressException e) {
            e.printStackTrace();
            logger.info("Technical issue in sending reporting");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.info("Technical issue in sending reporting");
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("Technical issue in sending reporting");
        }

    }
    */

   public static void sendReports(String Subject, String... filenames) {
      try {
         // Properties Constant = new Properties();
         // Constant.load(new FileInputStream("Config.properties"));
         // String to = Constant.getProperty("reportTo");
         // String user = Constant.getProperty("sendMailFrom");// change
         // // accordingly
         String user = Constant.sendMailFrom;
         String[] to = Constant.reportTo;
         String cc = Constant.cc;
         // get connection
         Session session = createConnection();

         MimeMessage message = new MimeMessage(session);

         MimeMessage messageBodyPart1 = new MimeMessage(session);
         messageBodyPart1.setFrom(new InternetAddress(user));// change
         // accordingly

         InternetAddress[] recipientAddress = new InternetAddress[to.length];
         int counter = 0;
         for (String recipient : to) {
            recipientAddress[counter] = new InternetAddress(recipient.trim());
            counter++;
         }

         messageBodyPart1.addRecipients(Message.RecipientType.TO, recipientAddress);
         messageBodyPart1.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));

         // Subject of mails
         message.setSubject(Subject);
         // Body of mails
         String date = RandomAction.getDate();
         message.setContent("Attached is the report for the OG export on : " + date, "text");

         // message.setText();

         // 4) create new MimeBodyPart object and set DataHandler object to
         // this object
         MimeBodyPart messageBodyPart2 = new MimeBodyPart();
         for (String filename : filenames) {
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);
            logger.info("Attached file - " + filename);

         }
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart2);

         message.setContent(multipart);

         Transport.send(message, messageBodyPart1.getAllRecipients());

         logger.info("Message send success");

      } catch (Exception e) {
         logger.error(e.getMessage());
         logger.info("Technical issue in sending reporting");
      }

   }
   private static boolean isFileDownloaded_Ext(String dirPath, String ext){
		boolean flag=false;
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        flag = false;
	    }
	    
	    for (int i = 1; i < files.length; i++) {
	    	if(files[i].getName().contains(ext)) {
	    		flag=true;
	    	}
	    }
	    return flag;
	} 
}