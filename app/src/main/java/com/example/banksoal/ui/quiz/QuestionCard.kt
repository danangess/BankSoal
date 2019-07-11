package com.example.banksoal.ui.quiz

import android.content.Context
import android.graphics.Color
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
class QuestionCard(private val context: Context, private var questionData: QuestionData) {

    @View(R.id.tvQuestion)
    private lateinit var mQuestionTextView: TextView

    @View(R.id.btnOption1)
    private var mOption1Button: Button? = null

    @View(R.id.btnOption2)
    private var mOption2Button: Button? = null

    @View(R.id.btnOption3)
    private var mOption3Button: Button? = null

    @View(R.id.btnOption4)
    private var mOption4Button: Button? = null

    @View(R.id.btnOption5)
    private var mOption5Button: Button? = null

    @Click(R.id.btnOption1)
    fun onOption1Click() {
        setButtonColor(mOption1Button)
        showCorrectOptions(0)
    }

    @Click(R.id.btnOption2)
    fun onOption2Click() {
        setButtonColor(mOption2Button)
        showCorrectOptions(1)
    }

    @Click(R.id.btnOption3)
    fun onOption3Click() {
        setButtonColor(mOption3Button)
        showCorrectOptions(2)
    }

    @Click(R.id.btnOption4)
    fun onOption4Click() {
        setButtonColor(mOption4Button)
        showCorrectOptions(3)
    }

    @Click(R.id.btnOption5)
    fun onOption5Click() {
        setButtonColor(mOption5Button)
        showCorrectOptions(4)
    }

    @Resolve
    private fun onResolved() {
        mQuestionTextView.text = questionData.question.text
//        if (questionData.showCorrectOption) {
//            showCorrectOptions()
//        }

        for (i in 0 until questionData.options.size) {
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
                3 -> button = mOption4Button
                4 -> button = mOption5Button
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
        questionData.showCorrectOption = false
        for (i in 0 until questionData.options.size) {
            val option = questionData.options[i]
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
                3 -> button = mOption4Button
                4 -> button = mOption5Button
            }
            if (button != null) {
                if (option.isCorrect) {
                    if (opt == i) myCorrect++
                    if (questionData.showCorrectOption) button.setBackgroundColor(Color.GREEN)
                } else {
                    if (questionData.showCorrectOption) button.setBackgroundColor(Color.RED)
                }
            }
        }

        QuizViewModel.answer[questionData.question.id] = correctCount == myCorrect
    }

    private fun setButtonColor(button: Button?) {
        setAllButtonDefaultColor()
        button?.setBackgroundColor(Color.LTGRAY)
    }

    private fun setAllButtonDefaultColor() {
        val color = context.resources.getColor(R.color.light_gray)
        mOption1Button?.setBackgroundColor(color)
        mOption2Button?.setBackgroundColor(color)
        mOption3Button?.setBackgroundColor(color)
        mOption4Button?.setBackgroundColor(color)
        mOption5Button?.setBackgroundColor(color)
    }
}