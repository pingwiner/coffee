package coffee.demo.com.coffee.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import coffee.demo.com.coffee.R
import coffee.demo.com.coffee.events.RefreshEvent
import coffee.demo.com.coffee.logic.Machine
import coffee.demo.com.coffee.logic.Office
import coffee.demo.com.coffee.model.Settings
import kotlinx.android.synthetic.main.fragment_settings.*
import org.greenrobot.eventbus.EventBus

class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    val userCountText : String by lazy {getString(R.string.user_count) + ": "}
    val busynessText: String by lazy {getString(R.string.busyness) + ": "}
    val busyHoursText: String by lazy {getString(R.string.busy_hours) + ": "}
    var settingsChanged = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userCountBar.setOnSeekBarChangeListener(this)
        busynessBar.setOnSeekBarChangeListener(this)
        busyHoursBar.setOnSeekBarChangeListener(this)        
        updateViewState()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onPause() {
        super.onPause()
        if (settingsChanged) {
            EventBus.getDefault().post(RefreshEvent("Settings changed"))
        }
    }

    override fun onResume() {
        super.onResume()
        settingsChanged = false
    }
    
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id) {
            R.id.userCountBar -> {
                Settings.instance.usersCount = progress + 1
                if (fromUser) {
                    Office.instance.reset()
                    Machine.instance.reset()
                }
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
        settingsChanged = true;
        updateViewState()
    }
    
    private fun updateViewState() {
        userCountLabel.setText(userCountText + Settings.instance.usersCount)
        busynessLabel.setText(busynessText + Settings.instance.busyness)
        busyHoursLabel.setText(busyHoursText + Settings.instance.busyHours)
        userCountBar.setProgress(Settings.instance.usersCount - 1)
        busynessBar.setProgress(Settings.instance.busyness)
        busyHoursBar.setProgress(Settings.instance.busyHours - 1)
    }

}
