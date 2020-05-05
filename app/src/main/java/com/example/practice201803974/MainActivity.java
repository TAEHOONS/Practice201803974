package com.example.practice201803974;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave,btnClear,btnAdd,btnFinish;
    EditText edit01;


    private static final String LOCAL_FILE="raw_data.txt"; //파일 받아오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit01 = (EditText)findViewById(R.id.editText);
        btnSave = (Button)findViewById(R.id.btn_save);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnClear = (Button)findViewById(R.id.btn_clear);
        btnFinish =(Button)findViewById(R.id.btn_finish);

        btnSave.setOnClickListener(this);  //버튼들에게 온클릭리스너 달아주기
        btnAdd.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        InputStream in;  //입력스트림클래스들의 최고클래스
        BufferedReader reader;  //reader스트림에 버퍼링기능추가한 입력스트림클래스

        try{
            in = openFileInput(LOCAL_FILE);  // 입력스트림에 raw_data가 들어오게하고
        } catch (FileNotFoundException e){
            in = getResources().openRawResource(R.raw.raw_data);
        }

        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String s;
            while((s = reader.readLine())!=null){  //null이 아니면 한줄씩 읽어서 처리
                edit01.append(s);
                edit01.append("\n");
            }
        }catch (IOException e){
            Log.e("LOCAL_FILE",e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {
       // String txt01;
       // txt01 = edit01.getText().toString();

        if(v.getId()== R.id.btn_save){
            String s = edit01.getText().toString();
            if(s.length()==0){
                deleteFile(LOCAL_FILE); //만약 에디트텍스트가 비어있으면 파일삭제
                return;
            }
            try{
                OutputStream out = openFileOutput(LOCAL_FILE, MODE_PRIVATE); //raw_data 꺼내오고
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.append(s); //s 내용 뿌리기
                writer.close(); //종료
            }catch (IOException e){
                Log.e("메세지",e.getMessage());
            }
        }
        if(v.getId()==R.id.btn_finish){
            finish();
        }
        if(v.getId()==R.id.btn_clear){
            edit01.setText(""); // 텍스트를 비우기 (띄어쓰기 넣으면 안됨(위에서 입력값있는거로 처리))
        }
        if(v.getId()==R.id.btn_add){
            edit01.append("\n"+"201803974 _ 신태훈"); // 엔터+ㄴ학번이름 뿌리기

        }
    }
}
