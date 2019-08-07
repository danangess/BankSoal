package com.example.banksoal.ui.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.data.model.QuestionData
import com.example.banksoal.databinding.ActivityQuizBinding
import com.example.banksoal.ui.base.BaseActivity
import com.example.banksoal.ui.main.MainActivity
import com.mindorks.placeholderview.SwipePlaceHolderView
import javax.inject.Inject

class QuizActivity : BaseActivity<ActivityQuizBinding, QuizViewModel>(), QuizNavigator {

    companion object {
        const val COURSE_ID: String = "Quiz/CourseId"
        const val QUESTION_GROUP_ID: String = "Quiz/QuestionGroup"

        fun newIntent(context: Context): Intent {
            return Intent(context, QuizActivity::class.java)
        }
    }

    private var questionIndex: Int = 0

    @Inject
    lateinit var mQuizViewModel: QuizViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_quiz

    override fun getViewModel(): QuizViewModel = mQuizViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun loadQuizData(questionData: QuestionData) {
        mQuizViewModel.isLoading.set(true)
        val swipePlaceHolderView = findViewById<SwipePlaceHolderView>(R.id.quizContainer)
        swipePlaceHolderView.disableTouchSwipe()
        swipePlaceHolderView.addView(QuestionCard(this, questionData))
        mQuizViewModel.isLoading.set(false)
    }

    override fun answer() {
        mQuizViewModel.isLoading.set(true)
        val swipePlaceHolderView = findViewById<SwipePlaceHolderView>(R.id.quizContainer)
        disableViewGroup(swipePlaceHolderView)
        mQuizViewModel.isLoading.set(false)
    }

    private fun disableViewGroup(viewGroup: ViewGroup) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is Button) view.isEnabled = false
            if (view is ViewGroup) {
                disableViewGroup(view)
            }
        }
    }

    override fun next() {
        val swipePlaceHolderView = findViewById<SwipePlaceHolderView>(R.id.quizContainer)
        swipePlaceHolderView.doSwipe(true)
//        mQuizViewModel.updateResult()
        mQuizViewModel.loadNextQuizData()
    }

    override fun onFinish() {
        val intent = MainActivity.newIntent(this@QuizActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mQuizViewModel.setNavigator(this)
        setupQuiz()
        mQuizViewModel.isLoading.set(false)
    }

    override fun onBackPressed() {
        onFinish()
    }

    private fun setupQuiz() {
        val wrongCourseId: Long = 0
        val courseId: Long = intent.getLongExtra(COURSE_ID, wrongCourseId)
//        val questionGroup: String = intent.getStringExtra(QUESTION_GROUP_ID)
        if (courseId != wrongCourseId) {
//            Toast.makeText(this, "Course ID: $courseId loaded", Toast.LENGTH_SHORT).show()
            QuizViewModel.answer = mutableMapOf()
            mQuizViewModel.loadQuizData(courseId, 1)
        } else {
            Toast.makeText(this, "Course ID: $courseId not found", Toast.LENGTH_SHORT).show()
        }
    }
}