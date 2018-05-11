package coffee.demo.com.coffee.logic

import coffee.demo.com.coffee.events.RefreshEvent
import coffee.demo.com.coffee.model.Settings
import coffee.demo.com.coffee.model.User
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.concurrent.timerTask

class Office private constructor() {
    private object Holder { val INSTANCE = Office() }
    
    companion object {
        val instance: Office by lazy { Holder.INSTANCE }
    }

    private var users = ArrayList<User>()
    private val random = Random()
    private var timer = Timer("office", false)
    private val lock = Object()

    init {
        reset()
        timer.scheduleAtFixedRate(
            timerTask { tick() },0,1000)
    }
    
    
    private fun randomBusyTime() : Long {
        if (random.nextInt(100) < Settings.instance.busyness) {
            return Date().time - random.nextInt(1000 * 60 * Settings.instance.busyHours)
        }
        return 0
    }
    
    private fun randomDrinkingTime() : Long {
        return Date().time - random.nextInt(1000 * 60)
    }
    
    fun reset() {
        synchronized(lock) {
            users.clear();
            for (i in 0..Settings.instance.usersCount - 1) {
                users.add(User(i, randomDrinkingTime(), randomBusyTime()))
            }
        }
    }
    
    private fun tick() {
        synchronized(lock) {
            for (user in users) {
                if (Machine.instance.isUserInQueue(user)) continue

                if (!user.isBusy()) {
                    if ((random.nextInt(100 * 60) < Settings.instance.busyness)) {
                        user.timeBecameBusy = Date().time
                        EventBus.getDefault().post(RefreshEvent("User " + user.id + " became busy"))
                    }
                } else {
                    if (Date().time - user.timeBecameBusy > Settings.instance.busyHours * 1000 * 60) {
                        user.timeBecameBusy = 0
                        EventBus.getDefault().post(RefreshEvent("User " + user.id + " became not busy"))
                    }
                }

                if (user.isThirsty() && !Machine.instance.isUserInQueue(user) && Machine.instance.getQueueSize() < 9) {
                    Machine.instance.makeCoffee(user)
                    EventBus.getDefault().post(RefreshEvent("User " + user.id + (if (user.isBusy()) " (busy)" else "") + " have coffee break"))
                }
            }
        }
    }
    
    fun getUserCount() : Int {
        return users.size
    } 
    
    fun getUser(index: Int) : User {
        return users[index]
    }
    
}