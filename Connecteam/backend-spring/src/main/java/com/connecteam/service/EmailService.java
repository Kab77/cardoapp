package com.connecteam.service;

import com.connecteam.model.DailyReport;
import com.connecteam.model.EmailConfig;
import com.connecteam.repository.EmailConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    private EmailConfigRepository emailConfigRepository;

    private JavaMailSender getMailSender(EmailConfig config) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getSmtpHost());
        mailSender.setPort(config.getSmtpPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public void sendReportEmail(DailyReport report) {
        EmailConfig config = emailConfigRepository.findByIsActiveTrue()
            .orElseThrow(() -> new RuntimeException("Aucune configuration email active trouvée"));

        try {
            JavaMailSender mailSender = getMailSender(config);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(config.getFromEmail());
            helper.setTo(config.getToEmail());
            helper.setSubject("Rapport journalier validé - " + report.getReportDate());

            // Construction du contenu du mail
            StringBuilder content = new StringBuilder();
            content.append("Rapport journalier validé\n\n");
            content.append("Employé: ").append(report.getEmployee().getFirstName())
                   .append(" ").append(report.getEmployee().getLastName()).append("\n");
            content.append("Date: ").append(report.getReportDate()).append("\n");
            content.append("Service: ").append(report.getPlanning().getService().getName()).append("\n");
            content.append("Heures prévues: ").append(report.getPlannedStartTime())
                   .append(" - ").append(report.getPlannedEndTime()).append("\n");
            content.append("Heures réelles: ").append(report.getActualStartTime())
                   .append(" - ").append(report.getActualEndTime()).append("\n\n");

            if (report.getEmployeeRemarks() != null && !report.getEmployeeRemarks().isEmpty()) {
                content.append("Remarques de l'employé:\n")
                       .append(report.getEmployeeRemarks()).append("\n\n");
            }

            if (report.getOtherRemarks() != null && !report.getOtherRemarks().isEmpty()) {
                content.append("Autres remarques:\n")
                       .append(report.getOtherRemarks()).append("\n\n");
            }

            helper.setText(content.toString());

            // Ajout des pièces jointes
            if (report.getEmployeeAttachments() != null) {
                for (var attachment : report.getEmployeeAttachments()) {
                    helper.addAttachment(attachment.getFileName(), 
                        new java.io.File(attachment.getFilePath()));
                }
            }

            if (report.getOtherAttachments() != null) {
                for (var attachment : report.getOtherAttachments()) {
                    helper.addAttachment(attachment.getFileName(), 
                        new java.io.File(attachment.getFilePath()));
                }
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
} 