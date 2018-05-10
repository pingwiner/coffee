package coffee.demo.com.coffee.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import coffee.demo.com.coffee.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var pageAdapter: PageAdapter? = null
    
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                pager.setCurrentItem(0);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                pager.setCurrentItem(1);
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        pageAdapter = PageAdapter(getSupportFragmentManager())        
        pager.setAdapter(pageAdapter)
    }


}
