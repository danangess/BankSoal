package com.example.banksoal.ui.competences

import android.os.Bundle
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.FragmentCompetencesBinding
import com.example.banksoal.ui.base.BaseFragment
import com.example.banksoal.ui.quiz.QuizActivity
import javax.inject.Inject

class CompetencesFragment : BaseFragment<FragmentCompetencesBinding, CompetencesViewModel>(), CompetencesNavigator {

    companion object {
        const val TAG: String = "Main/Course/Competences"

        fun newFragment(courseId: Long): CompetencesFragment {
            val args = Bundle()
            args.putLong(QuizActivity.COURSE_ID, courseId)
            val fragment = CompetencesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mCompetencesViewModel: CompetencesViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_competences

    override fun getViewModel(): CompetencesViewModel = mCompetencesViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun openCourseActivity() {

    }

    override fun goBack() {
        activity!!.onFragmentDetached(TAG)
    }

    override fun startQuiz() {
        val intent = QuizActivity.newIntent(activity!!.applicationContext)
        intent.putExtra(QuizActivity.COURSE_ID, _getCourseId())
        startActivity(intent)
        activity!!.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompetencesViewModel.setNavigator(this)
        val courseId: Long = _getCourseId()
        mCompetencesViewModel.loadCompetences(courseId)
        Toast.makeText(activity, mCompetencesViewModel.getCompetencesStringify().get(), Toast.LENGTH_SHORT).show()
    }

    private fun _getCourseId(): Long {
        val wrongCourseId: Long = 0
        return arguments!!.getLong(
            QuizActivity.COURSE_ID,
            wrongCourseId
        )
    }
}