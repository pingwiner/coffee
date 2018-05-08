package coffee.demo.com.coffee.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log


class PageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> OfficeFragment()
            1 -> MachineFragment()
            2 -> SettingsFragment()
            else -> {
                Log.e("Bad view state", "position: " + position)
                OfficeFragment()
            }
        }        
    }
}