package fzw.yuandi.music.Tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sunshine.view.library.data.ResponseInfo;
import com.sunshine.view.library.download.Downloader;

import fzw.yuandi.music.MusicList.SongResultBin;
import fzw.yuandi.music.PlaySong.SongDetails;
import fzw.yuandi.music.activity.audition.AuditionActivity;
import fzw.yuandi.music.activity.main.MainActivity;

/**
 * 网络请求类
 * Created by fuzhaowei on 2016/11/16.
 */

public class HTTPVolley implements Downloader {

    private RequestQueue queue;
    private Context context;
    private Handler handler;
    private Toast toast;
    private Gson gson;

    public HTTPVolley(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        queue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    private void ToastShow(String content) {
        if (toast != null) {
            toast.setText(content);
        } else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static String timeParse(long duration) {
        String time = "";

        long minute = duration / 60;
        long seconds = duration % 60;

        long second = Math.round((float) seconds / 1);

        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";

        if (second < 10) {
            time += "0";
        }
        time += second;

        return time;
    }

    private void getlist(int key, String response) {
        switch (key) {
            case MainActivity.SEARCH_KEY:
                if (JsonUtil.getInt(response, "error_code") == 22000) {
                    SongResultBin songResultBin = gson.fromJson(response, SongResultBin.class);
                    if (songResultBin.getSong().size() != 0) {
                        handler.obtainMessage(key, songResultBin.getSong()).sendToTarget();
                    } else {
                        ToastShow("查找不到该歌曲");
                    }
                } else {
                    ToastShow("查找不到该歌曲");
                }
                break;
            case AuditionActivity.PALT_KEY:
                if (JsonUtil.getInt(response, "error_code") == 22000) {
                    SongDetails songDetails = gson.fromJson(response, SongDetails.class);
                    if (songDetails != null) {
                        handler.obtainMessage(key, songDetails).sendToTarget();
                    } else {
                        ToastShow("暂无该歌曲的资源");
                    }
                } else {
                    ToastShow("暂无该歌曲的资源");
                }
                break;
        }
    }

    public void getmusic(String url, final int key) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getlist(key, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastShow("请检查网络是否连接，或者联系后台");
            }
        });
        queue.add(request);
    }

    public void getBitmap(String url, Response.Listener listener) {
        ImageRequest request = new ImageRequest(url, listener, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public String TimeToString(int number) {
        String time = "秒";
        if (number >= 60) {
            time = "分";
            number = number / 60;
            if (number >= 60) {
                time = "时";
                number = number / 24;
            }
        }
        return number + time;
    }

    @Override
    public ResponseInfo downloadImgByUrl(String urlStr) {
        RequestFuture future = RequestFuture.newFuture();
        Bitmap bm = null;
        ResponseInfo responseInfo = new ResponseInfo(false);
        ImageRequest request = new ImageRequest(urlStr, future, 0, 0, Bitmap.Config.RGB_565, future);
        queue.add(request);
        try {
            bm = (Bitmap) future.get();
            if (bm != null) {
                responseInfo.success = true;
                responseInfo.bitmap = bm;
            }
        } catch (Exception e) {
        }
        return responseInfo;

    }
}