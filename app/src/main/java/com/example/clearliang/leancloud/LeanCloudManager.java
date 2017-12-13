package com.example.clearliang.leancloud;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.Arrays;


public class LeanCloudManager {
    private static LeanCloudManager sLeanCloudManager;
    private String currentUserId;
    private String toUserId;
    private AVIMClient mClient;
    private Context mContext;

    public LeanCloudManager() {
    }



    public static synchronized LeanCloudManager getInstance() {
        if (null == sLeanCloudManager) {
            sLeanCloudManager = new LeanCloudManager();
        }
        return sLeanCloudManager;
    }

    /**
     * 初始化 ，此函数要在 Application 的 onCreate 中调用
     * @param context   上下文
     * @param APP_ID    LeanCloud中项目的Id
     * @param APP_KEY   LeanCloud中项目的Key
     */
    public static void initIM(Context context, String APP_ID, String APP_KEY){
        if (TextUtils.isEmpty(APP_ID)) {
            throw new IllegalArgumentException("appId can not be empty!");
        }
        if (TextUtils.isEmpty(APP_KEY)) {
            throw new IllegalArgumentException("appKey can not be empty!");
        }
        AVOSCloud.initialize(context.getApplicationContext(),APP_ID,APP_KEY);
        //todo 在正式运行时，去掉logo日志
        AVOSCloud.setDebugLogEnabled(true);
        // 消息处理 handler
        //AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new CustomMessageHandler(context));
    }
    /**
     * 初始化会话
     * @param context   上下文
     * @param myName    发送者id
     * @param toName    接收者id
     * */
    public void initMessage(final Context context,final String myName, final String toName,AVIMClient name){
        currentUserId = myName;
        mContext = context;
        toUserId = toName;
        mClient = name;
    }


    /**
     * 发送信息
     * @param text      发送的内容
     * */
    public void sendMessage(final String text) {
        // 与服务器连接
        mClient.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(Arrays.asList(toUserId), currentUserId+" & "+toUserId, null,
                            new AVIMConversationCreatedCallback() {

                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText(text);
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {

                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    Log.d(currentUserId+" & "+toUserId+"：", "发送成功！");
                                                    Toast.makeText(mContext.getApplicationContext(),"发送成功",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });

    }
    /**
     * 关闭会话
     * */
    public void closeClient(){
        mClient.close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                Log.d("信息", "关闭成功！");
            }
        });
    }
}
