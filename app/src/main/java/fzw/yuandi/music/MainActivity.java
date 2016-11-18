package fzw.yuandi.music;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fzw.yuandi.music.MusicList.SongBin;

/**
 * 搜索列表
 * Created by fuzhaowei on 2016/11/17.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.edit_text)
    EditText edit_text;
    @BindView(R.id.list_view)
    ListView list_view;
    @BindView(R.id.public_title_text)
    TextView public_title_text;

    public static final int SEARCH_KEY = 597;
    private HTTPVolley httpvolley;
    private String query;
    private MusicAdapter adapter;


    @Override
    protected int onCreateView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        Steep();
        httpvolley = new HTTPVolley(this, handler);
        View view = LayoutInflater.from(this).inflate(R.layout.song_head, null);
        list_view.addHeaderView(view);

    }

    @Override
    protected void initData() {
        public_title_text.setText("搜索歌曲");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SEARCH_KEY) {
                ArrayList<SongBin> songbins = (ArrayList<SongBin>) msg.obj;
                adapter = new MusicAdapter(songbins, MainActivity.this, httpvolley);
                list_view.setAdapter(adapter);
            }
            if (msg.what == 05) {
                LogShow((String) msg.obj, ERROR);
            }
        }
    };

    @OnClick(R.id.btn)
    public void OnClick(View view) {
        int key = view.getId();
        switch (key) {
            case R.id.btn:
                query = edit_text.getText().toString();
                if (query != null && !query.equals("")) {
                    final mHttp mHttp = new mHttp();
//                    new Thread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            handler.obtainMessage(05, mHttp.mHttpUrlConnection(MyApplication.MUSIC_BASICS + MyApplication.SEARCH_SONG + "&query=" + query)).sendToTarget();
//                        }
//                    }).start();
                    httpvolley.getmusic(MyApplication.MUSIC_BASICS + MyApplication.SEARCH_SONG + "&query=" + query, SEARCH_KEY);
                } else {
                    ToastShow("关键字不能为空！");
                }
                break;
        }
    }

}