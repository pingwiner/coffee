package coffee.demo.com.coffee.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import coffee.demo.com.coffee.R
import coffee.demo.com.coffee.events.QueueSizeChangedEvent
import coffee.demo.com.coffee.events.UserStatusChangedEvent
import coffee.demo.com.coffee.logic.Machine
import coffee.demo.com.coffee.logic.Office
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.EventBus
import android.support.v4.view.ViewPager





class MainActivity : AppCompatActivity() {
    var pageAdapter: PageAdapter? = null
    
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                pager.setCurrentItem(0);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                pager.setCurrentItem(1);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                pager.setCurrentItem(2);
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        pageAdapter = PageAdapter(getSupportFragmentManager())        
        pager.setAdapter(pageAdapter);
        
        Machine.instance.reset()
        Office.instance.reset()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onQueueSizeChanged(event: QueueSizeChangedEvent) {
        
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserStatusChanged(event: UserStatusChangedEvent) {
        
    }
    

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
