package me.mauricee.contentSwitcher.support.v7;

import android.support.v7.widget.Toolbar;
import android.view.View;

import me.mauricee.contentSwitcher.TextSwitcher;

import android.widget.TextView;


public class ToolbarSwitcher {

    public static TextSwitcher switchTitle(Toolbar toolbar) {
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

    public static TextSwitcher switchSubstitle(Toolbar toolbar) {
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