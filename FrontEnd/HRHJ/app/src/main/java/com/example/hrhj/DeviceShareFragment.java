package com.example.hrhj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hrhj.domain.DeviceCode;
import com.example.hrhj.httpConnect.HttpConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DeviceShareFragment extends Fragment {

    public DeviceShareFragment(){

    }

    private Button issueCodeButton;
    private Button shareAccountButton;
    private HttpConnection httpConnection = HttpConnection.getInstance();
    private DeviceCode code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_devicechange, container, false);

        issueCodeButton = (Button)view.findViewById(R.id.DCF_issueCode_Button);
        shareAccountButton = (Button)view.findViewById(R.id.DCF_shareAccount_Button);

        issueCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpConnection.createDeviceCode(MainActivity.USER_ID,createDeviceCodeCallback);
            }
        });

        shareAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder inputBuilder = new AlertDialog.Builder(getActivity());
                inputBuilder.setTitle("공유코드를 입력하세요");
                inputBuilder.setMessage("계정 공유시 현재 기기의 데이터는 삭제됩니다.");
                final EditText et = new EditText(getActivity());
                et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                inputBuilder.setView(et);
                inputBuilder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeviceCode inputCode = new DeviceCode();
                        inputCode.setUid(MainActivity.USER_ID);
                        inputCode.setCode(et.getText().toString());
                        httpConnection.shareAccount(inputCode, shareAccountCodeCallback);
                    }
                });
                inputBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                inputBuilder.show();


            }
        });

        return view;
    }

    public final Callback createDeviceCodeCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            code = objectMapper.readValue(responseBytes, DeviceCode.class);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("공유코드 : " + code.getCode());
                            builder.setMessage("발급된 코드를 새로운 기기에 입력하시면\n계정공유가 완료됩니다.");
                            builder.show();
                        }
                    });
                }
            }).start();


        }
    };

    public final Callback shareAccountCodeCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            final int returnId = objectMapper.readValue(responseBytes, Integer.TYPE);

            if(returnId==MainActivity.USER_ID)
            {
                //기기변경 실패
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("공유실패");
                                builder.setMessage("공유코드를 다시 확인 해 주세요.");
                                builder.show();
                            }
                        });
                    }
                }).start();

            }
            else
            {
                //기기변경 성공
                //TODO: SharedPreference의 ID returnID로 변경
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData",getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("USER_ID",returnId);
                editor.commit();
                //TODO: 어플 재시작
                getActivity().finishAffinity();
            }


        }
    };
}
