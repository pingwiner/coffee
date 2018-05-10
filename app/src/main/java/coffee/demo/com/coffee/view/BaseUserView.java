package coffee.demo.com.coffee.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import coffee.demo.com.coffee.R;
        
public abstract class BaseUserView extends FrameLayout {
    private static LruCache<Integer, Bitmap> memoryCache;
    
    static {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);    
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<Integer, Bitmap>(cacheSize) {        
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    } 
    
    void addBitmapToCache(Integer key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    Bitmap getBitmapFromCache(Integer key) {
        return memoryCache.get(key);
    }

    public BaseUserView(Context context) {
        super(context);
        initViews(context);
    }

    public BaseUserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public BaseUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }
    
    abstract int getLayoutResId(); 
    
    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutResId(), this);   
    }

   public void setBusy(boolean busy) {
        ImageView busySign = this.findViewById(R.id.busySign);
        if (busy) {
            busySign.setVisibility(View.VISIBLE);
        } else {
            busySign.setVisibility(View.INVISIBLE);
        }
   }
   
   Bitmap loadFromAssets(Context context, int index) {
        Bitmap bitmap = null;
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        try {
            istr = assetManager.open("user_" + index + ".png");
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            Log.e("IOException", "Can't load avatar for user " + index);
        }
        return bitmap;
   }
   
   public abstract void setAvatarByIndex(Context context, int index); 
}
