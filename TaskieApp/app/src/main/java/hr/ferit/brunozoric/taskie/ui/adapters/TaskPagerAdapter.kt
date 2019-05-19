package hr.ferit.brunozoric.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.AppInfoFragment
import hr.ferit.brunozoric.taskie.ui.fragments.AuthorInfoFragment
import hr.ferit.brunozoric.taskie.ui.fragments.TasksFragment


class TaskPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AppInfoFragment.newInstance()
            1 -> AuthorInfoFragment.newInstance()
            else -> AppInfoFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> APP_INFO
            1 -> AUTHOR_INFO
            else -> APP_INFO
        }
    }

    override fun getCount() = 2

    companion object{
        const val APP_INFO = "App info"
        const val AUTHOR_INFO = "Author info"
    }
}