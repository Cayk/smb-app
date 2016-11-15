package com.smb.util;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Cayk Lima on 12/11/16.
 */

public class Validador {

    public static boolean validateNotNull(View view, String mssg) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            Editable text = editText.getText();

            if (text != null) {
                String strText = text.toString();
                Log.i("Len", strText.trim().length()+"");
                if (!TextUtils.isEmpty(strText) && strText.trim().length() > 0)
                    return true;
            }

            editText.setError(mssg);
            editText.setFocusable(true);
            editText.requestFocus();
        }
        return false;
    }

    public static String validate(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            Editable text = editText.getText();

            if (text != null) {
                String strText = text.toString();
                Log.i("Len", strText.trim().length() + "");
                if (!TextUtils.isEmpty(strText) && strText.trim().length() > 0)
                    return strText;
            }
        }
        return null;
    }

    public static boolean validadeEmail(View view, String mssg) {
        String email = validate(view);
        if(email != null){
            if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return true;
            }
        }

        if(view instanceof EditText){
            EditText editText = (EditText) view;

            editText.setError(mssg);
            editText.setFocusable(true);
            editText.requestFocus();
        }
        return false;
    }

    public static boolean validateInteger(View view) {
        if (view instanceof EditText) {

            EditText editText = (EditText) view;
            Editable text = editText.getText();
            String txt = text.toString();

            try {

                int valor = Integer.parseInt(txt);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static boolean validateSenha(View view, View view1, String mssg) {
        if (view instanceof EditText && view1 instanceof EditText) {
            EditText editText = (EditText) view;
            EditText editText1 = (EditText) view1;
            Editable text = editText.getText();
            Editable text1 = editText1.getText();

            if (text != null && text1 != null) {
                String strText = text.toString();
                String strText1 = text1.toString();
                if (strText.equals(strText1)) {
                    return true;
                }

                editText1.setError(mssg);
                editText1.setFocusable(true);
                editText1.requestFocus();
                return false;
            }
        }
        return false;
    }
}
