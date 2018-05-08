package coffee.demo.com.coffee

enum class Priority {NORMAL, HIGH}

data class User(val id: Int, var priority: Priority)
