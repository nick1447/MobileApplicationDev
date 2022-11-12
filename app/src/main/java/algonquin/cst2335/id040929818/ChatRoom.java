package algonquin.cst2335.id040929818;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.id040929818.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.id040929818.databinding.ReceiveMessageBinding;
import algonquin.cst2335.id040929818.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    private ChatRoomViewModel chatModel;
    private ChatMessageDAO messageDAO;
    private ArrayList<ChatMessage> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        MessageDatabase database = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "ChatMessages").build();
        messageDAO = database.getMessageDao();

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText(messages.get(position).getMessage());
                holder.timeText.setText(new Date().toString());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if(messages.get(position).getIsSent()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        if(messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());
            Executor dataThread = Executors.newSingleThreadExecutor();
            dataThread.execute(() -> {
                messages.addAll(messageDAO.getAllMessages());
            });
        }

        binding.sendButton.setOnClickListener(click -> {
            ChatMessage message = new ChatMessage(
                binding.messageText.getText().toString(),
                    new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()),
                true
            );
            messages.add(message);
            Executor dataThread = Executors.newSingleThreadExecutor();
            dataThread.execute(() -> messageDAO.insertMessage(message));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.messageText.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            ChatMessage message = new ChatMessage(
                    binding.messageText.getText().toString(),
                    new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()),
                    false
            );
            messages.add(message);
            Executor dataThread = Executors.newSingleThreadExecutor();
            dataThread.execute(() -> messageDAO.insertMessage(message));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.messageText.setText("");
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        ImageView profileImage;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                new AlertDialog.Builder(ChatRoom.this)
                        .setMessage("Do you want to delete the message: " + messageText.getText().toString())
                        .setTitle("Warning!")
                        .setPositiveButton("yes", (dialog, cl) -> {
                            ChatMessage message = messages.get(position);
                            Executor dataThread = Executors.newSingleThreadExecutor();
                            dataThread.execute(() -> messageDAO.deleteMessage(message));
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);

                            Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", undoClick -> {
                                        messages.add(position, message);
                                        Executor undoDataThread = Executors.newSingleThreadExecutor();
                                        undoDataThread.execute(() -> {
                                            messageDAO.insertMessage(message);
                                        });
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        })
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .create()
                        .show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }
}