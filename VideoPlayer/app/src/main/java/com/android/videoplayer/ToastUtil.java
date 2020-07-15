package com.android.videoplayer;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
    //用于替换Toast内容的类 防止多次弹窗
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
