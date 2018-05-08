package coffee.demo.com.coffee

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import coffee.demo.com.coffee.events.QueueSizeChangedEvent
import coffee.demo.com.coffee.events.UserStatusChangedEvent
import coffee.demo.com.coffee.logic.Machine
import coffee.demo.com.coffee.logic.Office
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.EventBus



class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
