package com.github.library.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.bubbleview.R;
/**
 * Created by lgp on 2015/3/24.
 */
public class BubbleTextVew extends TextView {
    private BubbleDrawable bubbleDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
    private int bubbleColor;
    private BubbleDrawable.ArrowLocation mArrowLocation;

    public BubbleTextVew(Context context)
    {
        super(context);
        initView(null);
    }

    public BubbleTextVew(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(attrs);
    }

    public BubbleTextVew(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs)
    {
        if (attrs != null)
        {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle,
                    BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            bubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            array.recycle();
        }
        setUpPadding();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0)
        {
            setUp(w, h);
        }
    }

    @Override
    public void layout(int l, int t, int r, int b)
    {
        super.layout(l, t, r, b);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (bubbleDrawable != null)
            bubbleDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    private void setUp(int width, int height)
    {
        setUp(0, width, 0, height);
    }

    private void setUp()
    {
        setUp(getWidth(), getHeight());
    }

    private void setUp(int left, int right, int top, int bottom)
    {
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new BubbleDrawable.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .bubbleType(BubbleDrawable.BubbleType.COLOR)
                .angle(mAngle)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .bubbleColor(bubbleColor)
                .arrowPosition(mArrowPosition)
                .build();
    }

    private void setUpPadding()
    {
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        switch (mArrowLocation)
        {
            case LEFT:
                left += mArrowWidth;
                break;
            case RIGHT:
                right += mArrowWidth;
                break;
            case TOP:
                top += mArrowHeight;
                break;
            case BOTTOM:
                bottom += mArrowHeight;
                break;
        }
        setPadding(left, top, right, bottom);
    }

    public void setBubbleColor(int color)
    {
        bubbleColor = color;

        if (bubbleDrawable != null)
        {
            bubbleDrawable.setBubbleColor(color);

            invalidate();
        }
    }


    // # State manager

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();

        bundle.putParcelable(State.STATE, new State(super.onSaveInstanceState(), bubbleColor));

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        // my bundle
        if (state instanceof Bundle)
        {
            State s = ((Bundle) state).getParcelable(State.STATE);
            assert (s != null);

            bubbleColor = s.getBubbleColor();

            super.onRestoreInstanceState(s.getSuperState());
        }

        // some extraordinary error to not be my bundle
        else
        {
            super.onRestoreInstanceState(state);
        }
    }


    // Class to hold state

    static class State extends BaseSavedState {

        protected static final String STATE = "BubbleTextView.Chatuba.STATE";

        private int bubbleColor;

        private State(Parcel in)
        {
            super(in);

            bubbleColor = in.readInt();
        }

        public State(Parcelable parcelable, int bubbleColor)
        {
            super(parcelable);

            this.bubbleColor = bubbleColor;
        }

        @Override
        public void writeToParcel(Parcel out, int flags)
        {
            super.writeToParcel(out, flags);

            out.writeInt(bubbleColor);
        }

        public static final Parcelable.Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in)
            {
                return new State(in);
            }

            @Override
            public State[] newArray(int size)
            {
                return new State[size];
            }
        };

        public int getBubbleColor()
        {
            return bubbleColor;
        }
    }
}