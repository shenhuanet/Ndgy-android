package com.shenhua.nandagy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ListView;

import com.shenhua.lib.keyboard.XhsEmoticonsKeyBoard;
import com.shenhua.lib.keyboard.interfaces.EmoticonClickListener;
import com.shenhua.lib.keyboard.utils.EmoticonsKeyboardUtils;
import com.shenhua.lib.keyboard.widget.FuncLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleTranslucentChatActivity extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_chat)
    ListView lvChat;
    @BindView(R.id.ek_bar)
    XhsEmoticonsKeyBoard ekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chat_translucent);
        ButterKnife.bind(this);
        toolbar.setTitle("Simple Chat Keyboard");
        initView();
    }

    private void initView() {
        initEmoticonsKeyBoardBar();
    }

    private void initEmoticonsKeyBoardBar() {
//        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
//        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
//        ekBar.addOnFuncKeyBoardListener(this);
//        ekBar.addFuncView(new SimpleAppsGridView(this));
//
//        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
//            @Override
//            public void onSizeChanged(int w, int h, int oldw, int oldh) {
//                scrollToBottom();
//            }
//        });
//        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OnSendBtnClick(ekBar.getEtChat().getText().toString());
//                ekBar.getEtChat().setText("");
//            }
//        });
//        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SimpleTranslucentChatActivity.this, "ADD", Toast.LENGTH_SHORT).show();
//            }
//        });
//        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SimpleTranslucentChatActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
//                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
//                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
//                    if (o instanceof EmoticonEntity) {
//                        OnSendImage(((EmoticonEntity) o).getIconUri());
//                    }
//                } else {
//                    String content = null;
//                    if (o instanceof EmojiBean) {
//                        content = ((EmojiBean) o).emoji;
//                    } else if (o instanceof EmoticonEntity) {
//                        content = ((EmoticonEntity) o).getContent();
//                    }
//
//                    if (TextUtils.isEmpty(content)) {
//                        return;
//                    }
//                    int index = ekBar.getEtChat().getSelectionStart();
//                    Editable editable = ekBar.getEtChat().getText();
//                    editable.insert(index, content);
//                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (EmoticonsKeyboardUtils.isFullScreen(this)) {
            boolean isConsum = ekBar.dispatchKeyEventInFullScreen(event);
            return isConsum || super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
//            ImMsgBean bean = new ImMsgBean();
//            bean.setContent(msg);
//            chattingListAdapter.addData(bean, true, false);
            scrollToBottom();
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
        lvChat.requestLayout();
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom());
            }
        });
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }
}
