package ses;

// these are the imports for SDK v1
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;
import java.util.List;

public class EmailSender {
    public EmailResult handleRequest(EmailRequest request, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Entering send_email\n");
        EmailResult emailRes;

        try {
            logger.log("Before initializing client\n");

            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()

                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();

            logger.log("After initializing client!\n");

            // TODO:
            // Use the AmazonSimpleEmailService object to send an email message
            // using the values in the EmailRequest parameter object
            List<String> toAddresses = new ArrayList<>();
            toAddresses.add(request.to);
            Destination destination = new Destination(toAddresses);

            Content subject = new Content(request.subject);

            Content html = new Content(request.htmlBody);
            Content text = new Content(request.textBody);
            Body body = new Body();
            body.setHtml(html);
            body.setText(text);

            Message message = new Message(subject, body);

            SendEmailRequest sendReq = new SendEmailRequest(request.from, destination, message);

            logger.log("Sending email...");
            SendEmailResult sendRes = client.sendEmail(sendReq);
            emailRes = new EmailResult("Sent message: "+sendRes.getMessageId());

            logger.log("Sent message: "+sendRes.getMessageId());
        } catch (Exception ex) {
            logger.log("The email was not sent. Error message: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
        finally {
            logger.log("Leaving send_email");
        }

        // TODO:
        // Return EmailResult
        return emailRes;
    }

}