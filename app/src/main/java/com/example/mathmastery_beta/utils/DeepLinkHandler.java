package com.example.mathmastery_beta.utils;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

public class DeepLinkHandler {

    public static void handleDeepLink(AppCompatActivity activity) {
        Intent intent = activity.getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri != null) {
                // Извлечение данных из Deep Link
                String path = uri.getPath();
                if ("/home".equals(path)) {
                }
            }
        }
    }
}
