package com.example.susan.balldemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susan on 16/6/4.
 */
public class RippleCircle extends View {


    public RippleCircle(Context context) {
        super(context);
        init(null);
    }

    public RippleCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RippleCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RippleCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
        //这里measure为0
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    public int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


    private boolean mHasAnimation;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                setAnimationStatus(AnimStatus.END);
            } else {
                setAnimationStatus(AnimStatus.START);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHasAnimation) {
            setAnimationStatus(AnimStatus.START);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAnimationStatus(AnimStatus.CANCEL);
    }


    void applyAnimation() {
        initAnimation();
    }


    private List<Animator> mAnimators;

    public void initAnimation() {
        mAnimators = createAnimation();
    }


    /**
     * make animation to start or end when target
     * view was be Visible or Gone or Invisible.
     * make animation to cancel when target view
     * be onDetachedFromWindow.
     *
     * @param animStatus
     */
    public void setAnimationStatus(AnimStatus animStatus) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (animStatus) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }


    public enum AnimStatus {
        START, END, CANCEL
    }


    private void init(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView);
        indicatorColor = attr.getColor(R.styleable.AVLoadingIndicatorView_circle_color, indicatorColor);
        centre_text = attr.getString(R.styleable.AVLoadingIndicatorView_centre_text);
        this.name = centre_text;
        textSize = attr.getFloat(R.styleable.AVLoadingIndicatorView_text_size, 10);
        attr.recycle();
    }


    private float StrokeWidth = dp2px(20);//水波的宽度
    private int alpha = 255;
    private int Height;    //整个控件的高度
    private int Width;     //整个控件的宽度
    private Paint mtextPaint;
    private Paint mPaint;

    //Sizes (with defaults in DP)
    private static final int DEFAULT_SIZE = 45;


    private String centre_text;//中间的文字
    private int indicatorColor = 0xEA68A2;//圆形的基础颜色
    private float textSize;


    // The name of the view
    private String name;

    public String getName() {
        return name;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Height = getHeight();
        Width = getWidth();





        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setColor(indicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);//paint抗锯齿功能
        mPaint.setAlpha(255);

        canvas.drawCircle(Width / 2, Height / 2, Width / 2 - StrokeWidth, mPaint);

        mPaint.setStrokeWidth(StrokeWidth); //線寬
        mPaint.setStyle(Paint.Style.STROKE); //空心效果
        mPaint.setAlpha(alpha);
        canvas.drawCircle(Width / 2, Height / 2, Width / 2 - StrokeWidth, mPaint);

        if (mtextPaint == null) {
            mtextPaint = new Paint();
            mtextPaint.setTextSize(dp2px((int) textSize));
            mtextPaint.setColor(Color.WHITE);
            mtextPaint.setAlpha(255);
            mtextPaint.setTextAlign(Paint.Align.CENTER);
        }
        Paint.FontMetricsInt mFontMetricsInt = mPaint.getFontMetricsInt();
        float frontHeight = mFontMetricsInt.bottom - mFontMetricsInt.top;

        float textBaseY = Height - (Height - frontHeight) / 2 - mFontMetricsInt.descent;

        canvas.drawText(centre_text, Width / 2, textBaseY, mtextPaint);

    }


    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        ValueAnimator alphaAnim = ValueAnimator.ofInt(50, 100);
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.setDuration(700);
        alphaAnim.setRepeatCount(-1);
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        alphaAnim.start();
        animators.add(alphaAnim);

        return animators;
    }

}
