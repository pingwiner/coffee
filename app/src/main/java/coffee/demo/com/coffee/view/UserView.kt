package coffee.demo.com.coffee.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.widget.ImageView
import coffee.demo.com.coffee.R

class UserView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseUserView(context, attrs, defStyleAttr) {

    override fun getLayoutResId() : Int {
        return R.layout.user_view
    }

    override fun setAvatarByIndex(context: Context?, index: Int) {
        var bitmap: Bitmap? = getBitmapFromCache(index)
        
        if (bitmap == null) {
            bitmap = loadFromAssets(context, index)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, 40, 40);
            addBitmapToCache(index, bitmap)
        }        
        
        findViewById<ImageView>(R.id.avatar).setImageBitmap(bitmap)       
    }

    fun goAway() {
        findViewById<ImageView>(R.id.avatar).setImageResource(R.drawable.coffee_time)
        setBusy(false)
    }
    
}