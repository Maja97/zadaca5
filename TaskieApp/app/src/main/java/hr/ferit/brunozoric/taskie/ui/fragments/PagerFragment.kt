package hr.ferit.brunozoric.taskie.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.adapters.TaskPagerAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment


class PagerFragment : BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_pager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)
        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout= view.findViewById(R.id.tabLayout)

        viewPager.adapter = TaskPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

}
