package com.example.da_mientay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da_mientay.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    //Khai báo control
    Button btnSignUp;
    EditText edtName,edtPass,edtPhone;

    ///Khởi tạo Firebase
    final FirebaseDatabase database  = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControl();
        addEvent();
    }


    void addControl()
    {
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        edtName = (EditText)findViewById(R.id.edtName);
        edtPass = (EditText)findViewById(R.id.edtPass);
        edtPhone = (EditText)findViewById(R.id.edtPhone);
    }

    void addEvent()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Kiểm tra số điện thoại đã tồn tại
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            // In thông báo đã tồn tại
                            Toast toast = Toast.makeText(SignUp.this,"Số điện thoại đã được đăng ký",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        // Không tồn tại thì cho phép đăng ký
                        else
                        {
                            // Truyền 2 giá trị Name và Pass được get vào Model User
                            User user = new User(edtName.getText().toString(),edtPass.getText().toString());
                            //Đổ dữ liệu vào database dựa trên phone đã get
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            //Thông báo đăng ký thành công
                            Toast toast =  Toast.makeText(SignUp.this,"Đăng ký thành công ",Toast.LENGTH_SHORT);
                            toast.show();
                            // Kết thúc  activity
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }

    
}