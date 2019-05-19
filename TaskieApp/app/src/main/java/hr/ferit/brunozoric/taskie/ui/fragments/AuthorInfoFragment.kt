package hr.ferit.brunozoric.taskie.ui.fragments


import androidx.fragment.app.Fragment


import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment


class AuthorInfoFragment : BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_author_info

    companion object {
        fun newInstance(): Fragment{
            return AuthorInfoFragment()
        }
    }
}
