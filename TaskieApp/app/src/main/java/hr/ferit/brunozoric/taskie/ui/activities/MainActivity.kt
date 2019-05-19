package hr.ferit.brunozoric.taskie.ui.activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.showFragment
import hr.ferit.brunozoric.taskie.ui.activities.base.BaseActivity
import hr.ferit.brunozoric.taskie.ui.fragments.PagerFragment
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        setFragment()
    }

    private fun setFragment() {
        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId)
            {
                R.id.nav_tasks-> {val fragment= TasksFragment()
                    showFragment(R.id.fragmentContainer,fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_about-> {val fragment = PagerFragment()
                    showFragment(R.id.fragmentContainer,fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}