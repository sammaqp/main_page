package com.example.dialogfragment;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.concurrent.TimeUnit;


public class myDialogFrag extends DialogFragment {
    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageView btRew,btPlay,btPause,btFf,tc;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public void onDismiss(final DialogInterface dialog) {
        mediaPlayer.stop();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_frag, container, false);

        playerDuration = (TextView) view.findViewById(R.id.player_duration);
        playerPosition = (TextView) view.findViewById(R.id.player_position);
        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        btRew = (ImageView) view.findViewById(R.id.bt_rew);
        btPlay = (ImageView) view.findViewById(R.id.bt_play);
        btPause = (ImageView) view.findViewById(R.id.bt_pause);
        tc = (ImageView) view.findViewById(R.id.bt_close);
        btFf = (ImageView) view.findViewById(R.id.bt_ff);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.music);
        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        int duration = mediaPlayer.getDuration();
        String sDuration = convertFormat(duration);

        playerDuration.setText(sDuration);


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlay.setVisibility(View.GONE);
                btPause.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                handler.postDelayed(runnable, 0);
                // Toast.makeText(getActivity(), "Wellcome" + tname.getText().toString(), Toast.LENGTH_SHORT).show();
                //  getDialog().dismiss();
            }
        });
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlay.setVisibility(View.VISIBLE);
                btPause.setVisibility(View.GONE);
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
            }
        });
        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPostion = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                if (mediaPlayer.isPlaying() && duration != currentPostion) {
                    currentPostion = currentPostion + 5000;
                    playerPosition.setText(convertFormat(currentPostion));
                    mediaPlayer.seekTo(currentPostion);
                }
            }
        });
        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPostion = mediaPlayer.getCurrentPosition();
                if (mediaPlayer.isPlaying() && currentPostion > 5000) {
                    currentPostion = currentPostion - 5000;
                    playerPosition.setText(convertFormat(currentPostion));
                    mediaPlayer.seekTo(currentPostion);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                mediaPlayer.seekTo(0);
            }
        });
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                getDialog().dismiss();
            }
        });
        return view;
    }
    private String convertFormat(int duration) {
        return String.format("%02d:%02d"
                ,TimeUnit.MILLISECONDS.toMinutes(duration)
                ,TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
