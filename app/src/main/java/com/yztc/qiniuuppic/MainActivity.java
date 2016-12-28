package com.yztc.qiniuuppic;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.File;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //上传进度
        final UpProgressHandler handler = new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.i("=========",percent+">>>>");
            }
        };
        UploadOptions options = new UploadOptions(null, null, false, handler, null);


        UploadManager uploadManager =
                QiNiuUpHelper.getUploadManager();

        String uploadToken = QiNiuUpHelper.getUploadToken();

        File f=new File(
                Environment.getExternalStorageDirectory(),
                "smack_4_1_8.zip" );


        uploadManager.put(f, "serverName", uploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    //上传完成
                    Toast.makeText(MainActivity.this, "长传完成", Toast.LENGTH_SHORT).show();
                }
            }
        }, options);

    }


}
