package fzw.yuandi.music.PlaySong;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SongDetails {

    private SongInfo songinfo;
    private int error_code;
    private BitRate bitrate;

    public SongInfo getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(SongInfo songinfo) {
        this.songinfo = songinfo;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public BitRate getBitrate() {
        return bitrate;
    }

    public void setBitrate(BitRate bitrate) {
        this.bitrate = bitrate;
    }
}
