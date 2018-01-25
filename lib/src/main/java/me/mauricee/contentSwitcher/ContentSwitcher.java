package me.mauricee.contentSwitcher;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class ContentSwitcher<V extends View> {

    private static final AnimationModifier DEFAULT_START_ANIMATOR = animator -> animator.alpha(0f);
    private static final AnimationModifier DEFAULT_END_ANIMATOR = animator -> animator.alpha(1f);

    V target;
    private AnimationModifier startAnimation;
    private AnimationModifier endAnimation;
    private SwitchAction<V> thenAction;
    private SwitchAction<V> afterAction;
    private long duration;
    private ViewPropertyAnimator animator;


    protected ContentSwitcher(@NonNull V target) {
        this.target = target;
        this.animator = target.animate();
        duration = 250;
        startAnimation = DEFAULT_START_ANIMATOR;
        endAnimation = DEFAULT_END_ANIMATOR;
    }

    final public static <V extends View> ContentSwitcher<V> with(V target) {
        return new ContentSwitcher<>(target);
    }

    final public ContentSwitcher<V> duration(long duration) {
        this.duration = duration;
        return this;
    }

    final public ContentSwitcher<V> then(SwitchAction<V> action) {
        this.thenAction = action;
        return this;
    }

    final public ContentSwitcher<V> after(SwitchAction<V> action) {
        this.afterAction = action;
        return this;
    }

    final public void switchContent() {
        animator.cancel();
        startAnimation.getAnimation(animator)
                .setDuration(duration)
                .withEndAction(this::doSwitch)
                .start();
    }

    final public void cancel() {
        animator.cancel();
        onCancel();
    }

    private void doSwitch() {
        if (thenAction != null && target != null)
            thenAction.onSwitch(target);

        onSwitch();

        endAnimation.getAnimation(animator)
                .setDuration(duration)
                .withEndAction(this::doAfter)
                .start();
    }

    protected void onSwitch() {
    }

    private void doAfter() {
        onAfter();
    }

    protected void onAfter() {
        if (afterAction != null && target != null) {
            afterAction.onSwitch(target);
        }
    }

    protected void onCancel() {

    }

    final public long getDuration() {
        return duration;
    }

    final public AnimationModifier getStartAnimation() {
        return startAnimation;
    }

    final public void setStartAnimation(AnimationModifier startAnimation) {
        this.startAnimation = startAnimation;
    }

    final public AnimationModifier getEndAnimation() {
        return endAnimation;
    }

    final public ContentSwitcher<V> setEndAnimation(AnimationModifier endAnimation) {
        this.endAnimation = endAnimation;
        return this;
    }

    public interface SwitchAction<V> {
        void onSwitch(@NonNull V target);
    }

    public interface AnimationModifier {
        ViewPropertyAnimator getAnimation(ViewPropertyAnimator animator);
    }
}