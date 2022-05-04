package br.com.kafka_ses.services_ses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.kafka_ses.auth_ses.AWSCredentials;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

public class SESService {
    public static void sendMessage(String message) {
        Region region = Region.US_EAST_1;
        SesClient client = SesClient.builder()
                .credentialsProvider(AWSCredentials.getCredentials())
                .region(region)
                .build();

        String bodyText = "Olá! Tudo bem?";

        String bodyHTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h3>Este é um teste!</h3>"
                + "<p> Enviado pelo Java - " + message + ".</p>"
                + "</body>"
                + "</html>";

        try {
            send(client, "bianca.andrade@ifood.com.br", "bianca.andrade@ifood.com.br", "E-mail Teste", bodyText, bodyHTML);
            client.close();

            System.out.println("E-mail enviado com sucesso!");

        } catch (IOException | MessagingException e) {
            e.getStackTrace();
        }
    }

    public static void send(SesClient client,
            String sender,
            String recipient,
            String subject,
            String bodyText,
            String bodyHTML) throws AddressException, MessagingException, IOException {

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        message.setSubject(subject, "UTF-8");
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(bodyText, "text/plain; charset=UTF-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

        MimeMultipart msgBody = new MimeMultipart();
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        message.setContent(msgBody);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            client.sendRawEmail(rawEmailRequest);

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}