package com.lectomundo.logic;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class CorreoService {

    private final String remitente = "edwardcornegomez@gmail.com";
    private final String contrasena = "cjfs wesb tbsa nvuw";

    private Session crearSession() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasena);
            }
        });
    }

    public boolean enviarCorreo(String destinatario, String asunto, String mensaje) throws MessagingException {

        Message message = new MimeMessage(crearSession());
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);
        return true;
    }

    public static String generarCodigoDeVerificacion() {

        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public static boolean enviarCodigoPorCorreo(String correo, String codigo) {

        try {

            String asunto = "Código de verificación";

            CorreoService correoService = new CorreoService();
            return correoService.enviarCorreo(correo, asunto, "Tu código es: " + codigo);

        } catch (MessagingException e) {

            e.printStackTrace();
            return false;
        }
    }
}
