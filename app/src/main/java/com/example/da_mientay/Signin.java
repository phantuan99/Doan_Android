package com.example.da_mientay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da_mientay.Common.Common;
import com.example.da_mientay.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    //Khai báo control
    EditText edtPhone,edtPass;
    Button btnSignin;

    /// Khởi tạo Firebase
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        AddControl();
        AddEvent();



    }

    void AddControl(){
    edtPhone=(EditText)findViewById(R.id.edtPhone);
    edtPass=(EditText)findViewById(R.id.edtPass);
    btnSignin = (Button)findViewById(R.id.btnSignin);
    }

    void AddEvent()
    {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gán event
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Kiểm tra số phone khi get text có tồn tại trong database không
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            //Đi vào node User và gán cho Model
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            // So sánh Password được lưu với password của text có giống nhau ko
                            if(user.getPassword().equals(edtPass.getText().toString()))
                            {
//                                // In thông báo thành công
//                                Toast toast =  Toast.makeText(Signin.this,"Đăng nhập thành công!",Toast.LENGTH_SHORT);
//                                toast.show();
                                // Mở activity Home
                                Common.currentUser = user;
                                Intent homeIntent = new Intent(Signin.this,Home.class);
                                startActivity(homeIntent);
                                finish();
                            }
                            // Không giống nhau
                            else
                            {
                                // In thông báo failed
                                Toast.makeText(Signin.this,"Vui lòng kiểm tra lại mật khẩu",Toast.LENGTH_SHORT).show();
                            }
                        }
                        //Không tồn tại
                        else
                        {
                            Toast.makeText(Signin.this,"User không tồn tại",Toast.LENGTH_SHORT).show();
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