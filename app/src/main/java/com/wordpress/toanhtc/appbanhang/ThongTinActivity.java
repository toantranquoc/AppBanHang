package com.wordpress.toanhtc.appbanhang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongTinActivity extends AppCompatActivity {
    Button save, cancel;
    EditText namekh, phonekh, emailkh;
    ProgressBar load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            CancelEvent();
            ButtonSaveEvent();
        }
        else {
            CheckConnection.showToast_Connect(getApplicationContext(),"Check your Internet");
        }
    }

    private void ButtonSaveEvent() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEvent();
            }
        });
    }
    public boolean isValidEmail(String target) {
        if (target.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        return false;
    }
    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else
        if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                if (number.substring(0, 2).equals("09")) {
                    return true;
                } else {
                    return false;
                }
            } else
            if (number.substring(0, 2).equals("01")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private void SaveEvent() {
        load.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        String name, phone, email;
        name = namekh.getText().toString().trim();
        phone = phonekh.getText().toString().trim();
        email = emailkh.getText().toString().trim();
        boolean error = false;
        if (TextUtils.isEmpty(name))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            namekh.requestFocus();
            namekh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }

        if (TextUtils.isEmpty(phone))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            phonekh.requestFocus();
            phonekh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }
        if (checkNumberPhone(phone) == false)
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            phonekh.requestFocus();
            phonekh.setError("Số điện thoại không đúng!");
            error = true;
        }
        if (TextUtils.isEmpty(email))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            emailkh.requestFocus();
            emailkh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }
        if (isValidEmail(email) == false)
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            emailkh.requestFocus();
            emailkh.setError("Email không hợp lệ");
            error = true;
        }
        if(error == false)
        {
            CheckConnection.showToast_Connect(getApplicationContext(),"Thành công");
        }
    }

    private void CancelEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        namekh = (EditText) findViewById(R.id.edtTenKh);
        phonekh = (EditText) findViewById(R.id.edtPhoneKh);
        emailkh = (EditText) findViewById(R.id.edtEmailKh);
        save = (Button) findViewById(R.id.btnSaveKh);
        cancel = (Button) findViewById(R.id.btnCancelKh);
        load = (ProgressBar) findViewById(R.id.loadingKh);
    }
}
