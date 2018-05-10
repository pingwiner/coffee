package coffee.demo.com.coffee.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import coffee.demo.com.coffee.R

class QueueUserView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseUserView(context, attrs, defStyleAttr) {

    private val TAG = "QueueUserView"
    
    override fun getLayoutResId() : Int {
        return R.layout.queue_user_view
    }

    fun hide() {
        findViewById<ImageView>(R.id.avatar).setImageBitmap(null)
        setBusy(false)
    }
    
    override fun setAvatarByIndex(context: Context?, index: Int) {
        var bitmap: Bitmap? = getBitmapFromCache(index + 100)
        
        if (bitmap == null) {
            bitmap = loadFromAssets(context, index)            
            addBitmapToCache(index + 100, bitmap)
        }        
        
        findViewById<ImageView>(R.id.avatar).setImageBitmap(bitmap)
       
    }

}