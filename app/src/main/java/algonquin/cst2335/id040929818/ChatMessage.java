package algonquin.cst2335.id040929818;public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean isSent;

    public ChatMessage(String message, String timeSent, boolean isSent) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSent = isSent;
    }

    public String getMessage() {return this.message;}
    public String getTimeSent() {return this.timeSent;}
    public boolean getIsSent() {return this.isSent;}
}
