package me.mauricee.contentSwitcher.support.v7;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.mauricee.contentSwitcher.TextSwitcher;

import android.widget.TextView;


public class ToolbarSwitcher {

    /**
     * @return {@link me.mauricee.contentSwitcher.TextSwitcher} with the toolbar's title.
     * Will throw {@link MissingTitleException} if no title is found.
     */
    public static TextSwitcher switchTitle(@NonNull Toolbar toolbar) {
        TextView toolbarTitle = null;
        CharSequence title = toolbar.getTitle();
        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView && ((TextView) child).getText().equals(title)) {
                toolbarTitle = (TextView) child;
                break;
            }

        }

        if (toolbarTitle == null) throw new MissingTitleException();
        return TextSwitcher.with(toolbarTitle);
    }

    /**
     * @return {@link me.mauricee.contentSwitcher.TextSwitcher} with the toolbar's subtitle.
     * Will throw {@link MissingSubtitleException} if no subtitle is found.
     */
    public static TextSwitcher switchSubstitle(@NonNull Toolbar toolbar) {
        TextView toolbarTitle = null;
        CharSequence subtitle = toolbar.getSubtitle();
        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView && ((TextView) child).getText().equals(subtitle)) {
                toolbarTitle = (TextView) child;
                break;
            }

        }
        if (toolbarTitle == null) throw new MissingSubtitleException();
        return TextSwitcher.with(toolbarTitle);
    }

    private static class MissingTitleException extends RuntimeException {
        MissingTitleException() {
            super("There's no title TextView inside of The provided Toolbar. " +
                    "You may need to set the title first.");
        }
    }

    private static class MissingSubtitleException extends RuntimeException {
        MissingSubtitleException() {
            super("There's no subtitle TextView inside of The provided Toolbar. " +
                    "You may need to set the subtitle first.");
        }
    }
}