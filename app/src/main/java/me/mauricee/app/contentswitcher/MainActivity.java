package me.mauricee.app.contentswitcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import me.mauricee.contentSwitcher.TextSwitcher;
import me.mauricee.contentSwitcher.support.v7.ToolbarSwitcher;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener {
    private int counter = 0;

    private final String[] text = {
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
    TextSwitcher toolbarSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchBtn = findViewById(R.id.switch_btn);
        transitionText = findViewById(R.id.transition_text);
        seekBar = findViewById(R.id.seekbar);
        switchText = findViewById(R.id.switch_text);

        textSwitcher = TextSwitcher.with(switchText);

        seekBar.setMax(1000);
        seekBar.setProgress((int) textSwitcher.getDuration());

        seekBar.setOnSeekBarChangeListener(this);
        switchBtn.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarSwitcher = ToolbarSwitcher.switchTitle(toolbar);
        transitionText.setText(getString(R.string.transition_speed, textSwitcher.getDuration()));
        toolbar.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        if (fromUser)
            textSwitcher.duration(i);
        transitionText.setText(getString(R.string.transition_speed, i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        counter = counter < text.length - 1 ? counter + 1 : 0;
        if (view.equals(switchBtn))
            textSwitcher.to(text[counter]).switchContent();
        else
            toolbarSwitcher.to(text[counter]).switchContent();
    }
}
