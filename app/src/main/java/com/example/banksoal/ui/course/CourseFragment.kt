package com.example.banksoal.ui.course

import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.data.model.CourseData
import com.example.banksoal.databinding.FragmentCourseBinding
import com.example.banksoal.ui.base.BaseFragment
import com.example.banksoal.ui.quiz.QuizActivity
import javax.inject.Inject

class CourseFragment: BaseFragment<FragmentCourseBinding, CourseViewModel>(), CourseNavigator {

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
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_course

    override fun getViewModel(): CourseViewModel = mCourseViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun openQuizActivity() {
        val spinner = activity!!.findViewById<Spinner>(R.id.spinnerCourse)
        val selectedCourse = spinner.selectedItem as CourseData
        Toast.makeText(activity, "$selectedCourse dipilih", Toast.LENGTH_SHORT).show()

        val intent = QuizActivity.newIntent(activity!!.applicationContext)
        intent.putExtra(QuizActivity.COURSE_ID, selectedCourse.course.id)
        startActivity(intent)
        activity!!.finish()
    }
}