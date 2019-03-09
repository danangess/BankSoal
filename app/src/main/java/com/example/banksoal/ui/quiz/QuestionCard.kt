package com.example.banksoal.ui.quiz

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.example.banksoal.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.NonReusable
import android.widget.Button
import android.widget.TextView
import com.example.banksoal.data.model.QuestionData
import com.example.banksoal.data.model.db.Option
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Resolve

@NonReusable
@Layout(R.layout.card_layout)
class QuestionCard(var questionData: QuestionData) {

    @View(R.id.tvQuestion)
    private lateinit var mQuestionTextView: TextView

    @View(R.id.btnOption1)
    private var mOption1Button: Button? = null

    @View(R.id.btnOption2)
    private var mOption2Button: Button? = null

    @View(R.id.btnOption3)
    private var mOption3Button: Button? = null

    @Click(R.id.btnOption1)
    fun onOption1Click() {
        showCorrectOptions(0)
    }

    @Click(R.id.btnOption2)
    fun onOption2Click() {
        showCorrectOptions(1)
    }

    @Click(R.id.btnOption3)
    fun onOption3Click() {
        showCorrectOptions(2)
    }

    @Resolve
    private fun onResolved() {
        mQuestionTextView.text = questionData.question.text
//        if (questionData.showCorrectOption) {
//            showCorrectOptions()
//        }

        for (i in 0..2) {
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }

            button?.text = questionData.options[i].text

            if (questionData.question.imgUrl != null) {
//                mPicImageView.setImageUrl(mQuestionCardData.question.imgUrl)
            }
        }
    }

    private fun showCorrectOptions(opt: Int) {
        var myCorrect = 0
        val correctCount = questionData.options.count { option: Option -> option.isCorrect }
        questionData.showCorrectOption = true
        for (i in 0..2) {
            val option = questionData.options[i]
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }
            if (button != null) {
                if (option.isCorrect) {
                    if (opt == i) myCorrect++
                    button.setBackgroundColor(Color.GREEN)
                } else {
                    button.setBackgroundColor(Color.RED)
                }
            }
        }

        if (correctCount == myCorrect) QuizViewModel.correctCount++
        else QuizViewModel.inCorrectCount++
    }
}