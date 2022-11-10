package algonquin.cst2335.id040929818;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.id040929818.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.id040929818.databinding.ReceiveMessageBinding;
import algonquin.cst2335.id040929818.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    private ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding.sendButton.setOnClickListener(click -> {
            ChatMessage message = new ChatMessage(
                binding.messageText.getText().toString(),
                    new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a").format(new Date()),
                true
            );
            messages.add(message);
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
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.messageText.setText("");
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
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
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        ImageView profileImage;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }
}