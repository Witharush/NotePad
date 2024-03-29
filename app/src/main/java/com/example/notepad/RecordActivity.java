package com.example.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.notepad.database.SQLiteHelper;
import com.example.notepad.utils.DBUtils;

public class RecordActivity extends Activity implements View.OnClickListener{
    ImageView note_back;
    TextView note_time;
    EditText content;
    ImageView delete;
    ImageView note_save;
    Button paint;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;
    private MySurfaceView mview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = findViewById(R.id.note_back);
        note_time = findViewById(R.id.tv_time);
        content = findViewById(R.id.note_content);
        delete = findViewById(R.id.delete);
        paint = findViewById(R.id.paint);
        note_save = findViewById(R.id.note_save);
        noteName = findViewById(R.id.note_name);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        paint.setOnClickListener(this);
        initData();
    }

     protected void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("Add record");
        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getStringExtra("id");
            if(id != null){
                noteName.setText("Change Record");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                if(id != null){
                    if(noteContent.length() > 0){
                        if(mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                            showToast("Update Successfully");
                            setResult(2);
                            finish();
                        }else{
                            showToast("Update Failed");
                        }
                    }
                    else{
                        showToast("The update content can't be empty!");
                    }
                }else{
                    if(noteContent.length()>0){
                        if(mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                            showToast("Save Successfully");
                            setResult(2);
                            finish();
                        }else {
                            showToast("Save Failed");
                        }
                    }else {
                        showToast("The update content can't be empty!");
                   }
                }
                break;
        }

    }

    private void showToast(String message) {
        Toast.makeText(RecordActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
