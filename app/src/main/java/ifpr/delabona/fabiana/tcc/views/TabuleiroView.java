package ifpr.delabona.fabiana.tcc.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import ifpr.delabona.fabiana.tcc.R;

/**
 * Created by wheezy on 19/07/17.
 */

public class TabuleiroView extends View {

    private ArrayList<Casas> mCasaItens;
    private int mPosInicial;
    private Casas mCasaAtual;
    private Bitmap mPlayerScaledIcon;

    private int mItemSizeX;
    private int mItemSizeY;
    private int mItemPosX;
    private int mItemPosY;

    private String[] mCasaValues;

    public TabuleiroView(Context context) {
        super(context);
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mCasaItens = new ArrayList<Casas>();

        int scaledSize = getResources().getDimensionPixelSize(R.dimen.font_size);
        Casas.setFontSize(scaledSize);


        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBotton = getPaddingBottom();
        int paddingLeft = getPaddingLeft();

        mItemSizeX = ((right-paddingRight)-(left+paddingLeft))/12;
        mItemSizeY = ((bottom-paddingBotton)-(top+paddingTop))/7;

        mItemPosX =(right-paddingRight)- mItemSizeX;
        mItemPosY =(bottom-paddingBotton)- mItemSizeY;

        for (int i = 0; i<49; i++){
            if (i<=10){
                mItemPosX = mItemPosX - mItemSizeX;
            }else if (i<=16){
                mItemPosY = mItemPosY - mItemSizeY;
            }else if (i<=27){
                mItemPosX = mItemPosX + mItemSizeX;
            }else  if (i <=31){
                mItemPosY = mItemPosY + mItemSizeY;
            }else if (i <= 40){
                mItemPosX = mItemPosX - mItemSizeX;
            }else if (i <= 42){
                mItemPosY = mItemPosY - mItemSizeY;
            }else if (i < 49){
                mItemPosX = mItemPosX + mItemSizeX;
            }


            Casas casas = new Casas(mItemPosX, mItemPosY, mItemPosX + mItemSizeX, mItemPosY + mItemSizeY,mCasaValues[i]);


            mCasaItens.add(casas);
        }

        int imageSize = mItemSizeY;

        mPlayerScaledIcon = Bitmap.createScaledBitmap(mPlayerScaledIcon,imageSize,imageSize,false);

        setPosicao(0);
    }

    public void setValues(@NonNull String[] values){
        mCasaValues = values;
    }

    public void setPosicao(int pos){
        if (pos < mCasaItens.size()){
            if (mCasaAtual != null){
                mCasaAtual.setmImageResource(null);
            }
            mCasaAtual = mCasaItens.get(pos);
            mCasaAtual.setmImageResource(mPlayerScaledIcon);
        }

        invalidate();
    }

    public void setPlayerIcon(@DrawableRes int image){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);
        mPlayerScaledIcon = bitmap;
    }

    public int getPosicao(){
        if (mCasaAtual != null)
            return mCasaItens.indexOf(mCasaAtual);

        return -1;
    }

    public Casas getCasaAtual(){
        return mCasaAtual;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCasaItens != null && mCasaItens.size() > 0){
            for (int i = 0; i< mCasaItens.size(); i++){
                mCasaItens.get(i).draw(canvas);
            }
        }
    }
}
