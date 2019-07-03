package com.example.banksoal.ui.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
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

    @Inject
    lateinit var mQuizViewModel: QuizViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_quiz

    override fun getViewModel(): QuizViewModel = mQuizViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun loadQuizData() {
        val swipePlaceHolderView = findViewById<SwipePlaceHolderView>(R.id.quizContainer)
        swipePlaceHolderView.disableTouchSwipe()
        swipePlaceHolderView.addItemRemoveListener {
            mQuizViewModel.updateResult()
        }
        mQuizViewModel.getQuestionCardData().value!!.forEach { question ->
            swipePlaceHolderView.addView(
                QuestionCard(
                    question
                )
            )
        }
    }

    override fun answer() {
        val swipePlaceHolderView = findViewById<SwipePlaceHolderView>(R.id.quizContainer)
        swipePlaceHolderView.doSwipe(true)
        mQuizViewModel.updateResult()
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
    }

    override fun onBackPressed() {
        onFinish()
    }

    private fun setupQuiz() {
        val wrongCourseId: Long = 0
        val courseId: Long = intent.getLongExtra(COURSE_ID, wrongCourseId)
        val questionGroup: String = intent.getStringExtra(QUESTION_GROUP_ID)
        if (courseId != wrongCourseId) {
            Toast.makeText(this, "Course ID: $courseId loaded", Toast.LENGTH_SHORT).show()
            mQuizViewModel.loadQuizData(courseId, questionGroup)
        } else {
            Toast.makeText(this, "Course ID: $courseId not found", Toast.LENGTH_SHORT).show()
        }
    }
}