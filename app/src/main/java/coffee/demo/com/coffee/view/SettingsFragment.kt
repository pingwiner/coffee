package coffee.demo.com.coffee.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import coffee.demo.com.coffee.R
import coffee.demo.com.coffee.logic.Machine
import coffee.demo.com.coffee.logic.Office
import coffee.demo.com.coffee.model.Settings

class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    val userCountText : String by lazy {getString(R.string.user_count) + ": "}
    val busynessText: String by lazy {getString(R.string.busyness) + ": "}
    val busyHoursText: String by lazy {getString(R.string.busy_hours) + ": "}
    
    var userCountLabel : TextView? = null; 
    var busynessLabel : TextView? = null;
    var busyHoursLabel : TextView? = null;
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_settings, container, false)
        v.findViewById<SeekBar>(R.id.userCountBar).setOnSeekBarChangeListener(this)
        v.findViewById<SeekBar>(R.id.busynessBar).setOnSeekBarChangeListener(this)
        v.findViewById<SeekBar>(R.id.busyHoursBar).setOnSeekBarChangeListener(this)
        userCountLabel = v.findViewById(R.id.userCountLabel)
        busynessLabel = v.findViewById(R.id.busynessLabel)
        busyHoursLabel = v.findViewById(R.id.busyHoursLabel)
        updateViewState()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id) {
            R.id.userCountBar -> {
                Settings.instance.usersCount = progress + 1                
                Office.instance.reset()
                Machine.instance.reset()
            }
            R.id.busynessBar -> {
                Settings.instance.busyness = progress                
            }
            R.id.busyHoursBar -> {
                Settings.instance.busyHours = progress + 1                
            }
            else -> {
                Log.e("Bad view state", "seekBar id: " + seekBar?.id);
            }
        }
        updateViewState()
    }
    
    private fun updateViewState() {
        userCountLabel?.setText(userCountText + Settings.instance.usersCount)
        busynessLabel?.setText(busynessText + Settings.instance.busyness)
        busyHoursLabel?.setText(busyHoursText + Settings.instance.busyHours)
    }

}
