package ifpr.delabona.fabiana.tcc.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by wheezy on 19/07/17.
 */

public class Casas extends Drawable{

    private Bitmap mImageResource;
    private int mPosX;
    private int mPosY;
    private int mMaxPosX;
    private int mMaxPosY;
    private String mValue;
    private long mColor;

    private static int mFontSize;

    private Paint paint;


    public Casas(int mPosX, int mPosY,int mMaxPosX,int mMaxPosY, String mValue) {
        this.mPosX = mPosX;
        this.mPosY = mPosY;
        this.mValue = mValue;
        this.mMaxPosX = mMaxPosX;
        this.mMaxPosY = mMaxPosY;


        mColor = 0;
        mImageResource = null;
        paint = new Paint();

        Typeface typeface = Typeface.create(paint.getTypeface(),Typeface.BOLD);
        paint.setTypeface(typeface);
    }

    public static int getFontSize() {
        return mFontSize;
    }

    public static void setFontSize(int fontSize) {
        Casas.mFontSize = fontSize;
    }

    public Bitmap getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(Bitmap mImageResource) {
        this.mImageResource = mImageResource;
    }

    public int getmPosX() {
        return mPosX;
    }

    public void setmPosX(int mPosX) {
        this.mPosX = mPosX;
    }

    public int getmPosY() {
        return mPosY;
    }

    public void setmPosY(int mPosY) {
        this.mPosY = mPosY;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public long getmColor() {
        return mColor;
    }

    public void setmColor(long mColor) {
        this.mColor = mColor;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        paint.setTextSize(mFontSize);
        paint.setStyle(Paint.Style.FILL);

        paint.setStrokeWidth(10);

        float textMeasuredSize = paint.measureText(mValue);

        paint.setColor(Color.WHITE);
        canvas.drawRect(mPosX,mPosY,mMaxPosX,mMaxPosY,paint);

        if (mImageResource != null){
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawRect(mPosX,mPosY,mMaxPosX,mMaxPosY,paint);

            canvas.drawBitmap(mImageResource,
                    mPosX,
                    mPosY+((mMaxPosY-mPosY)/2)-(mImageResource.getHeight()/2),
                    paint);


            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawText(mValue,mPosX+((mMaxPosX-mPosX)/2)-(textMeasuredSize/2),mPosY+((mMaxPosY-mPosY)/2)+mFontSize/2,paint);

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawText(mValue,mPosX+((mMaxPosX-mPosX)/2)-(textMeasuredSize/2),mPosY+((mMaxPosY-mPosY)/2)+mFontSize/2,paint);
        }else{
            paint.setColor(Color.BLACK);
            canvas.drawText(mValue,mPosX+((mMaxPosX-mPosX)/2)-(textMeasuredSize/2),mPosY+((mMaxPosY-mPosY)/2)+mFontSize/2,paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawRect(mPosX,mPosY,mMaxPosX,mMaxPosY,paint);
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
