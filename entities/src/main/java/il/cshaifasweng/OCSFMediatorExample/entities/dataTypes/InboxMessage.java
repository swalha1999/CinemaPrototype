package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InboxMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;                 // Unique ID for the message
    private String sender;          // Who sent the message (e.g., System or user)
    private String recipient;       // Recipient user
    private String subject;         // Subject of the message
    private String content;         // Main content/body of the message
    private LocalDateTime timestamp;// When the message was sent
    private boolean isRead;         // Status if the message has been read or not

    // Constructor
    public InboxMessage(String sender, String recipient, String subject, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Override toString to display message details
    @Override
    public String toString() {
        return "InboxMessage{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}
