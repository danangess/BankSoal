package com.example.banksoal.ui.course

import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.data.model.CourseData
import com.example.banksoal.databinding.FragmentCourseBinding
import com.example.banksoal.ui.base.BaseFragment
import com.example.banksoal.ui.competences.CompetencesFragment
import javax.inject.Inject

class CourseFragment : BaseFragment<FragmentCourseBinding, CourseViewModel>(), CourseNavigator {

    companion object {
        const val TAG: String = "Main/Course"

        fun newFragment(): CourseFragment {
            val args = Bundle()
            val fragment = CourseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mCourseViewModel: CourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCourseViewModel.setNavigator(this)
        mCourseViewModel.loadCourseDataList()
        mCourseViewModel.isLoading.set(false)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_course

    override fun getViewModel(): CourseViewModel = mCourseViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun openQuizActivity() {

    }

    override fun openCompetencesFragment() {
//        unlockDrawer()
        mCourseViewModel.setIsActive(false)
        val spinner = activity!!.findViewById<Spinner>(R.id.spinnerCourse)
        val selectedCourse = spinner.selectedItem as CourseData
        activity!!.supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
//            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(
                R.id.clRootView,
                CompetencesFragment.newFragment(selectedCourse.course.id),
                CompetencesFragment.TAG
            )
            .commit()
    }

    override fun onCourseSelected() {

    }

    override fun onQuestionGroupSelected() {

    }
}