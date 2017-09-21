package me.mauricee.contentSwitcher;

import android.support.annotation.StringRes;
import android.widget.TextView;


/**
 * ContentSwitcher changing text from text views.
 */
public class TextSwitcher extends ContentSwitcher<TextView> {

    private CharSequence toText;
    private SwitchAction<TextView> alsoAction;

    private TextSwitcher(TextView target) {
        super(target);
        this.target = target;
    }

    public static TextSwitcher with(TextView target) {
        return new TextSwitcher(target);
    }

    public TextSwitcher to(CharSequence text) {
        toText = text;
        return this;
    }

    public TextSwitcher to(@StringRes int id) {
        toText = target.getResources().getText(id);
        return this;
    }

    public TextSwitcher also(SwitchAction<TextView> action) {
        alsoAction = action;
        return this;
    }

    @Override
    protected void onSwitch() {
        target.setText(toText);
        if (alsoAction != null)
            alsoAction.onSwitch(target);
        super.onSwitch();
    }
}