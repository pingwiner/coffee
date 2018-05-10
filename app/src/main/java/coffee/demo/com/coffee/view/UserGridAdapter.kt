package coffee.demo.com.coffee.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coffee.demo.com.coffee.logic.Office

class UserGridAdapter : BaseAdapter {
    var context: Context? = null

    constructor(context: Context?) : super() {
        this.context = context
    }

    override fun getCount(): Int {
        return Office.instance.getUserCount()
    }

    override fun getItem(position: Int): Any {
        return Office.instance.getUser(position)
    }

    override fun getItemId(position: Int): Long {
        return Office.instance.getUser(position).id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val user = Office.instance.getUser(position)
        val userView: UserView = (convertView ?: UserView(context)) as UserView
        userView.setAvatarByIndex(context, position)
        userView.setBusy(user.isBusy())        
        return userView
    }   
    

}