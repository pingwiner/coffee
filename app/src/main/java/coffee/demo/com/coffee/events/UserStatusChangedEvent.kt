package coffee.demo.com.coffee.events

import coffee.demo.com.coffee.model.User

data class UserStatusChangedEvent(val user: User) 