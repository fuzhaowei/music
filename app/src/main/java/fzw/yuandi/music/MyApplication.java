package fzw.yuandi.music;

import android.app.Application;

/**
 * Created by fuzhaowei on 2016/11/16.
 */

public class MyApplication extends Application {

    /*请求音乐基础网址*/
    public final static String MUSIC_BASICS = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    /*搜索歌曲*/
    public final static String SEARCH_SONG = "?method=baidu.ting.search.catalogSug";
    /*播放歌曲*/
    public final static String PLAT_SONG = "?method=baidu.ting.song.play";
    /*下载歌曲*/
    public final static String PLAT_DOWNLOAD = "?method=baidu.ting.song.downWeb";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
