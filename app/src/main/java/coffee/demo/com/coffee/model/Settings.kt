package coffee.demo.com.coffee.model

class Settings private constructor() {
    private object Holder { val INSTANCE = Settings() }

    companion object {
        val instance: Settings by lazy { Holder.INSTANCE }
    }

    var usersCount: Int = 10

    //from 0 to 100 percents
    var busyness: Int = 20

    var busyHours: Int = 1

    //Simulated time goes 60 times faster, so this is minutes in simulated time, but seconds in real time
    var timeNeededToMakeCoffee: Long = 3
}

