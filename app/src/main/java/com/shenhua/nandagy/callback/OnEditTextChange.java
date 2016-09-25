package com.shenhua.nandagy.callback;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * EditText 文字改变监听
 * Created by Shenhua on 9/8/2016.
 */
public class OnEditTextChange implements TextWatcher {

    private TextInputLayout textInputLayout;

    public OnEditTextChange(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        textInputLayout.setError("");
    }
}
