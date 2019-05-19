package hr.ferit.brunozoric.taskie.ui.fragments


import androidx.fragment.app.Fragment


import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment


class AppInfoFragment : BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_app_info


    companion object {
        fun newInstance(): Fragment{
            return AppInfoFragment()
        }
    }
}

