package coffee.demo.com.coffee.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomPager : ViewPager     {
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null)
        : super(context, attrs)
        
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}