package me.mauricee.contentswitcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import me.mauricee.contentSwitcher.TextSwitcher;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private int counter = 0;
    public final String[] text = {
            "Text 1",
            "Text 2",
            "Text 3",
            "Text 4",
            "Hello World!"};

    Button switchBtn;
    TextView transitionText;
    SeekBar seekBar;
    TextView switchText;
    TextSwitcher textSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchBtn = findViewById(R.id.switch_btn);
        transitionText = findViewById(R.id.transition_text);
        seekBar = findViewById(R.id.seekbar);
        switchText = findViewById(R.id.switch_text);

        textSwitcher = TextSwitcher.with(switchText);
        textSwitcher.after(ignored -> counter = counter < text.length ? counter + 1 : 0);

        seekBar.setMax(1000);
        seekBar.setProgress((int) textSwitcher.getDuration());

        seekBar.setOnSeekBarChangeListener(this);
        switchBtn.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b)
            textSwitcher.duration(i);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        textSwitcher.to(text[counter])
                .switchContent();
    }
}
