package coffee.demo.com.coffee

import android.app.ActivityManager
import java.util.*
import kotlin.concurrent.timerTask

data class Job(val user: User, val startTime: Date)

class CompareJobs {

    companion object : Comparator<Job> {

        override fun compare(a: Job, b: Job): Int = when {
            a.user.priority == Priority.HIGH && b.user.priority == Priority.NORMAL  -> 1
            a.user.priority == Priority.NORMAL && b.user.priority == Priority.HIGH  -> -1
            else -> a.startTime.compareTo(b.startTime)
        }
    }
}

class Machine {
    private var queue : PriorityQueue<Job> = PriorityQueue(10, CompareJobs)
    private var currentJob: Job? = null
    val timer = Timer("schedule", true)

    fun makeCoffee(user: User) {
        queue.add(Job(user, Date()))
        makeNextCup()
    }

    private fun makeNextCup() {
        currentJob = queue.poll()
        if (currentJob == null) return
        timer.schedule(
                timerTask { cupIsReady() },
                Settings.instance.timeNeededToMakeCoffee)
    }

    private fun cupIsReady() {
        notifyCupIsReady(currentJob.user, queue.size)
        makeNextCup()
    }

    private fun notifyCupIsReady(user: User, queueSize: Int) {

    }
}
