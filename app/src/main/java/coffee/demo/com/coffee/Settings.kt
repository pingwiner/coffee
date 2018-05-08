package coffee.demo.com.coffee

class Settings private constructor() {
    private object Holder { val INSTANCE = Settings() }

    companion object {
        val instance: Settings by lazy { Settings.Holder.INSTANCE }
    }

    var usersCount: Int = 10

    //from 0 to 100 percents
    var busyness: Int = 20

    var busyHours: Int = 1

    //In minutes
    var timeNeededToMakeCoffee: Long = 3
}

