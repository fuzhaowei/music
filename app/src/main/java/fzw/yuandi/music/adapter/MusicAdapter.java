package fzw.yuandi.music.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fzw.yuandi.music.MusicList.SongBin;
import fzw.yuandi.music.R;
import fzw.yuandi.music.Tool.HTTPVolley;
import fzw.yuandi.music.activity.audition.AuditionActivity;

/**
 * Created by fuzhaowei on 2016/11/17.
 */

public class MusicAdapter extends ConcreteAdapter<SongBin> {

    private HTTPVolley volley;

    public MusicAdapter(List list, Context context) {
        super(list, context);
    }

    public MusicAdapter(List<SongBin> list, Context context, HTTPVolley volley) {
        super(list, context);
        this.volley = volley;
    }

    class Holder {
        TextView song_name;
        TextView singer_name;
        ImageView download;
        ImageView play;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (holder == null) {
            holder = new Holder();
            view = getInflater().inflate(R.layout.song_item, null);
            holder.song_name = (TextView) view.findViewById(R.id.song_name);
            holder.singer_name = (TextView) view.findViewById(R.id.singer_name);
            holder.download = (ImageView) view.findViewById(R.id.download);
            holder.play = (ImageView) view.findViewById(R.id.play);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.song_name.setText(getList().get(position).getSongname());
        holder.singer_name.setText(getList().get(position).getArtistname());
        holder.download.setImageResource(R.drawable.download);
        holder.play.setImageResource(R.drawable.paly);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downorplay(0, position);
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downorplay(1, position);
            }
        });
        return view;
    }

    private void downorplay(int key, int position) {
        switch (key) {
            case 0:
                Intent intent = new Intent(getContext(), AuditionActivity.class);
                intent.putExtra("songid", getList().get(position).getSongid());
                getContext().startActivity(intent);
                break;
            case 1:

                break;
        }
    }


}