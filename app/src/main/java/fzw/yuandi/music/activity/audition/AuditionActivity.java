package fzw.yuandi.music.activity.audition;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fzw.yuandi.music.activity.MyApplication;
import fzw.yuandi.music.PlaySong.SongDetails;
import fzw.yuandi.music.Tool.Play_Song;
import fzw.yuandi.music.R;
import fzw.yuandi.music.Tool.HTTPVolley;
import fzw.yuandi.music.Tool.ImageUtil;
import fzw.yuandi.music.activity.BaseActivity;

/**
 * 试听列表
 * Created by fuzhaowei on 2016/11/17.
 */

public class AuditionActivity extends BaseActivity {

    private HTTPVolley volley;
    public static final int PALT_KEY = 123;
    @BindView(R.id.public_title_text)
    TextView public_title_text;
    @BindView(R.id.seekbar_progress)
    SeekBar seekbar_progress;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.image_end)
    ImageView image_end;
    @BindView(R.id.image_start)
    ImageView image_start;
    @BindView(R.id.image_stop)
    ImageView image_stop;
    @BindView(R.id.now_time)
    TextView now_time;
    @BindView(R.id.total_time)
    TextView total_time;


    private RotateAnimation rotateAnimation;


    @Override
    protected int onCreateView() {
        return R.layout.activity_audition;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        Steep();
        volley = new HTTPVolley(this, handler);
        volley.getmusic(MyApplication.MUSIC_BASICS + MyApplication.PLAT_SONG + "&songid=" + getIntent("songid"), PALT_KEY);
    }

    @Override
    protected void setListener() {
        seekbar_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                now_time.setText(HTTPVolley.timeParse(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void Rotate() {
        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(20000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        imageView.setAnimation(rotateAnimation);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PALT_KEY) {
                SongDetails detalis = (SongDetails) msg.obj;
                ContentUI(detalis);
            }
            if (msg.what == 22) {
                imageView.setImageBitmap(bitmap);
                Rotate();
            }
        }
    };

    private Play_Song play_song;
    private Bitmap bitmap;
    private SongDetails detalis;

    private void ContentUI(SongDetails detalis) {
        this.detalis = detalis;
        public_title_text.setText(detalis.getSonginfo().getTitle());
        play_song = new Play_Song(seekbar_progress);
        play_song.playUrl(detalis.getBitrate().getShow_link());
        volley.getBitmap(detalis.getSonginfo().getPic_premium(), new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                bitmap = ImageUtil.toRoundBitmap((Bitmap) response);
                handler.obtainMessage(22).sendToTarget();
            }
        });
        total_time.setText(HTTPVolley.timeParse(detalis.getBitrate().getFile_duration()));

//        new Thread(new Myrunn()).start();
    }

    class Myrunn implements Runnable {
        @Override
        public void run() {
            bitmap = ImageUtil.toRoundBitmap(ImageUtil.getBitmap(detalis.getSonginfo().getPic_premium()));
            handler.obtainMessage(22).sendToTarget();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        play_song.stop();
    }

    @OnClick({R.id.image_end, R.id.image_stop, R.id.image_start})
    public void OnClick(View view) {
        int key = view.getId();
        switch (key) {
            case R.id.image_end:
                play_song.stop();
                rotateAnimation.cancel();
                break;
            case R.id.image_start:
                play_song.play();
                rotateAnimation.start();
                break;
            case R.id.image_stop:
                play_song.pause();
                rotateAnimation.cancel();
                break;
        }
    }

}