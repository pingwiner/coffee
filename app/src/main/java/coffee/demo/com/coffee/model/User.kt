package coffee.demo.com.coffee.model

import java.util.*

enum class Priority {NORMAL, HIGH}

data class User(val id: Int, var lastTimeDrink: Long, var timeBecameBusy: Long) {
    
    fun isThirsty() : Boolean {
        return Date().time - lastTimeDrink > 60 * 1000 
    }   
    
    fun isBusy() : Boolean {
        return timeBecameBusy > 0
    }

}
