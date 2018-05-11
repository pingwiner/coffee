package coffee.demo.com.coffee.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coffee.demo.com.coffee.R
import coffee.demo.com.coffee.events.RefreshEvent
import coffee.demo.com.coffee.logic.Machine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlinx.android.synthetic.main.fragment_office.*
import java.util.*

class OfficeFragment : Fragment() {
    var adapter: UserGridAdapter? = null
    private val TAG = "OfficeFragment"
    var queueImages : List<QueueUserView>? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_office, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        queueImages = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)
        adapter = UserGridAdapter(context)
        userGridView.adapter = adapter      
        updateUserQueue()
    }   

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)        
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }    
   
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshEvent(event: RefreshEvent) {
        Log.d(TAG, event.message);
        adapter?.notifyDataSetChanged()
        updateUserQueue()
    }
    
    fun updateUserQueue() {        
        val count = Machine.instance.getQueueSize()
        for (it in count..9) {
            queueImages?.get(it)?.hide()
        }
        var i = 0
        for (user in Machine.instance.getUsersInQueue()) {
            queueImages?.get(i)?.setAvatarByIndex(context, user.id)
            queueImages?.get(i)?.setBusy(user.isBusy())
            if (++i > 9) break
        }        
    }
    
}