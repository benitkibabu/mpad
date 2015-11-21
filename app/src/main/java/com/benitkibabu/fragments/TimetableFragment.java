package com.benitkibabu.fragments;

import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.ncigomobile.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment {

    public static final String ITEM_NAME = "itemName";

//    PdfRenderer pdf;
//    WebView webview;

    public static TimetableFragment newInstance(String name){
        TimetableFragment fragment = new TimetableFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public TimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);

//        webview = (WebView) view.findViewById(R.id.webview);
//
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webview.getSettings().setBuiltInZoomControls(true);
//
//        openURL();
//
//        webview.setWebViewClient(new MyWebViewClient());
        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private void openURL() {
//        webview.loadUrl("https://docs.google.com/viewer?url=" +AppConfig.TIMETABLE_URL);
//        webview.requestFocus();
    }

    private File getFileFromInputStream(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // read this file into InputStream
            inputStream = getResources().openRawResource(R.raw.mscmt_pt_mad_1);
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory().getPath() + "/pdffile.pdf"));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return new File(Environment.getExternalStorageDirectory().getPath() + "/pdffile.pdf");
    }

}
