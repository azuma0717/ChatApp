package com.gmail.yuki.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private DatabaseReference mDatabase;
    private RecyclerView mMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editMessege);

        //親配下にMessagesを作成
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");

        /////////////////////////RecyclerViewの表示方法(デフォルト)///////////////////////
        mMessageList = findViewById(R.id.messageRec);

        //リストのコンテンツの大きさがデータによって変わらないならtrueを渡す。
        //これをRecyclerViewに知らせることで，パフォーマンスが良くなるらしい。
        mMessageList.setHasFixedSize(true);

        //表示方法はリニアで
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //↓Linearのときに使うと思うが、、効力がわからん。
        linearLayoutManager.setStackFromEnd(true);

        //LinerとかGridとかの表示方法を↑で定義して、下で教えてあげる
        mMessageList.setLayoutManager(linearLayoutManager);
        ///////////////////////////////////////////////////////////////////////////////

    }

    //sendボタンを押したら、発動
    public void sendButtonClicked(View view){

        final String messageValue = editText.getText().toString().trim();

        //空じゃなかったら発動
        if(!TextUtils.isEmpty(messageValue)){

            //Messages - "ID" - content：メッセージ
            //push()をすると、勝手に"ID"を生成する。
            final DatabaseReference newPost = mDatabase.push();
            newPost.child("content").setValue(messageValue);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ///////////////////////////////Adapterをここで定義//////////////////////////

        //FirebaseRecyclerAdapterはあらかじめ、Gradleでuiをcompileしておく。
        //最初の引数は、Messageクラス(ゲッターセッター)、次はViewHolderクラス。

        FirebaseRecyclerAdapter<Message,MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(

                //Messageクラスは、予め作っておいた、レイアウトファイルをあてがう。
                Message.class,R.layout.singlemessagelayout,
                //ViewHolderクラスは、DBから引っ張ってくるデータを指定する。("Messages"直下の"content"のvalueを取ってくる。"content"以外は取ってこない)
                MessageViewHolder.class,mDatabase

        ) {

            //このオーバーライドは絶対。
            //引数３つ、1つ目：自分で作った「MessageViewHolder」クラス、2つ目：自分で作った「Message」クラス、3つ目はposition(これはデフォルト)(メッセージの順番)
            //viewHolderとMessageクラスの絡みはここで書くのかな？
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message message, int position) {

                ///ここはViewHolder内で定義したメソッドを呼び出している。
                ///渡している値は、MessageクラスのgetContent
                viewHolder.setContent(message.getContent());
            }
        };

        mMessageList.setAdapter(FBRA);
        //////////////////////////////////////////////////////////////////////////
    }


    ///////////////////////////////ViewHolderをここで定義//////////////////////////

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        //contentは、入力した文章
        public void setContent(String content){
            TextView message_content = mView.findViewById(R.id.messageText);
            message_content.setText(content);
        }
    }
    //////////////////////////////////////////////////////////////////////////////

}
