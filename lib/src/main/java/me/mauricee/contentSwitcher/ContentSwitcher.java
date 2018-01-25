package me.mauricee.contentSwitcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class ContentSwitcher<V extends View> {

    private static final AnimationModifier DEFAULT_START_ANIMATOR = animator -> animator.alpha(0f);
    private static final AnimationModifier DEFAULT_END_ANIMATOR = animator -> animator.alpha(1f);

    V target;
    private AnimationModifier startAnimation;
    private AnimationModifier endAnimation;

    @Nullable
    private SwitchAction<V> startAction;
    @Nullable
    private SwitchAction<V> thenAction;
    @Nullable
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

    /**
     * Creates a ContentSwitcher with corresponding view as target
     */
    final public static <V extends View> ContentSwitcher<V> with(V target) {
        return new ContentSwitcher<>(target);
    }

    /**
     * Sets the duration of the start and end animation
     */
    final public ContentSwitcher<V> duration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * sets the {@link SwitchAction} before the first animation starts
     */
    final public ContentSwitcher<V> first(SwitchAction<V> action) {
        this.startAction = action;
        return this;
    }

    /**
     * sets the {@link SwitchAction} after the first animation ends
     * and before the last animation
     */
    final public ContentSwitcher<V> then(SwitchAction<V> action) {
        this.thenAction = action;
        return this;
    }

    /**
     * sets the {@link SwitchAction} after both animations complete
     */
    final public ContentSwitcher<V> after(SwitchAction<V> action) {
        this.afterAction = action;
        return this;
    }

    final public ContentSwitcher<V> setStartAnimation(AnimationModifier startAnimation) {
        this.startAnimation = startAnimation;
        return this;
    }

    final public ContentSwitcher<V> setEndAnimation(AnimationModifier endAnimation) {
        this.endAnimation = endAnimation;
        return this;
    }

    final public long getDuration() {
        return duration;
    }

    /**
     * begins content switch
     */
    final public void switchContent() {
        animator.cancel();
        startAnimation.getAnimation(animator)
                .setDuration(duration)
                .withStartAction(this::doStart)
                .withEndAction(this::doSwitch)
                .start();
    }

    //TODO return to original state when canceled.

    /**
     * Cancels switch. content may still have been switched depending on when animation was stopped
     */
    final public void cancel() {
        animator.cancel();
        onCancel();
    }

    private void doStart() {
        if (startAction != null && target != null)
            startAction.onSwitch(target);

        onStart();
    }

    /**
     * Internal event for {@link ContentSwitcher#first(SwitchAction)}
     */
    protected void onStart() {
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

    /**
     * Internal event for {@link ContentSwitcher#then(SwitchAction)}
     */
    protected void onSwitch() {
    }

    private void doAfter() {
        onAfter();
    }

    /**
     * Internal event for {@link ContentSwitcher#after(SwitchAction)}
     */
    protected void onAfter() {
        if (afterAction != null && target != null) {
            afterAction.onSwitch(target);
        }
    }

    /**
     * Internal event for when {@link ContentSwitcher} is cancelled
     */
    protected void onCancel() {

    }

    public interface SwitchAction<V> {
        void onSwitch(@NonNull V target);
    }

    public interface AnimationModifier {
        ViewPropertyAnimator getAnimation(ViewPropertyAnimator animator);
    }
}