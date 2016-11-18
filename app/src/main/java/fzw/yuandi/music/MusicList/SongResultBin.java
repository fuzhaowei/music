package fzw.yuandi.music.MusicList;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SongResultBin {

    private List<SongBin> song;
    private int error_code;
    private String order;

    public List<SongBin> getSong() {
        return song;
    }

    public void setSong(List<SongBin> song) {
        this.song = song;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
