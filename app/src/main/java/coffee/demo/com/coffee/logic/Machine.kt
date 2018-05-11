package coffee.demo.com.coffee.logic

import android.util.Log
import coffee.demo.com.coffee.model.Settings
import coffee.demo.com.coffee.model.User
import coffee.demo.com.coffee.events.RefreshEvent
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.concurrent.timerTask

data class Job(val user: User, val startTime: Date)

class CompareJobs {

    companion object : Comparator<Job> {

        override fun compare(a: Job, b: Job): Int = when {
            a.user.isBusy() && !b.user.isBusy() -> -1
            !a.user.isBusy() && b.user.isBusy() -> 1
            else -> a.startTime.compareTo(b.startTime)
        }
        
    }
}

class Machine private constructor() {
    private object Holder { val INSTANCE = Machine() }
    
    companion object {
        val instance: Machine by lazy { Holder.INSTANCE }
        val STATE_READY = -1
    }

    private var queue : LinkedList<Job> = LinkedList()
    private var currentJob: Job? = null
    private var timer: Timer? = null    

    fun makeCoffee(user: User) {
        queue.add(Job(user, Date()))
        queue.sortWith(CompareJobs)
        if (currentJob == null) makeNextCup()
    }

    private fun makeNextCup() {
        currentJob = queue.poll()
        
        if (currentJob == null) {
            EventBus.getDefault().post(RefreshEvent("No more people in queue"))
            return
        } else {
            EventBus.getDefault().post(RefreshEvent((queue.size + 1).toString() + " people in queue"))            
        }
        timer = Timer("machine", false)
        timer?.schedule(
                timerTask { cupIsReady() },
                Settings.instance.timeNeededToMakeCoffee * 1000)
    }

    private fun cupIsReady() {
        val user = currentJob?.user
        if (user == null) {
            Log.e("Bad machine state", "currentJob is null")             
        } else {
            user.lastTimeDrink = Date().time
            EventBus.getDefault().post(RefreshEvent("User " + user.id + " returned to workplace"))            
        }
        makeNextCup()
    }
    
    fun getCurrentJobState() : Int {
        val startTime = currentJob?.startTime
        return when (startTime) {
            null -> STATE_READY
            else -> ((Date().time - startTime.time) / Settings.instance.timeNeededToMakeCoffee / 10).toInt() 
        }
    }
    
    fun reset() {
        timer?.cancel()
        currentJob = null      
        queue.clear()                
    }
    
    fun isUserInQueue(user: User) : Boolean {
        val j = currentJob
        if (j != null) {
            if (j.user.equals(user)) return true
        }

        for(job in queue) {
            if (job.user.id == user.id) return true
        }
        return false
    }
    
    fun getUsersInQueue() : List<User> {
        val users = ArrayList<User>();
        val j = currentJob

        //First guy is waiting for the cup that is currently prepared
        //Nobody can stand before him because his order already processed
        if (j != null)  {
            users.add(j.user)    
        }

        //Other people waiting in queue
        for(job in queue) {
            users.add(job.user)    
        }       
        return users
    }
    
    fun getQueueSize() : Int {
        return queue.size
    }

}
