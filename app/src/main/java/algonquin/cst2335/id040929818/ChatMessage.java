package algonquin.cst2335.id040929818;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="timeSent")
    protected String timeSent;

    @ColumnInfo(name="isSend")
    protected boolean isSent;

    public ChatMessage(String message, String timeSent, boolean isSent) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSent = isSent;
    }

    public String getMessage() {return this.message;}
    public String getTimeSent() {return this.timeSent;}
    public boolean getIsSent() {return this.isSent;}
}
