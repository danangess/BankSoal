package com.example.banksoal.data

import android.content.Context
import com.example.banksoal.data.local.db.*
import com.example.banksoal.data.local.prefs.PreferencesHelper
import com.example.banksoal.data.model.*
import com.example.banksoal.data.model.db.*
import com.example.banksoal.utils.AppConstants
import com.example.banksoal.utils.CommonUtils
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager
@Inject
constructor(
    private val mContext: Context,
    override val courseRepository: CourseRepository,
    override val questionRepository: QuestionRepository,
    override val optionRepository: OptionRepository,
    override val userRepository: UserRepository,
    private val mPreferencesHelper: PreferencesHelper,
    private val mGson: Gson
) : DataManager {
    override fun doSignUp(firstName: String, lastName: String, userName: String, password: String): Single<Boolean> {
        val user = User()
        user.username = userName
        user.password = password
        user.firstName = firstName
        user.lastName = lastName
        return userRepository.add(user)
    }

    override fun doLogout(): Single<Boolean> {
        return Single.fromCallable {
            mPreferencesHelper.setLoginMode(DataManager.LoggedInMode.LOGOUT)
            updateUserInfo(null)
            true
        }
    }

    override fun doLogin(userName: String, password: String): Single<Boolean> {
        return userRepository.validate(userName, password)
    }

    override fun setLoginMode(loggedInMode: DataManager.LoggedInMode) {
        mPreferencesHelper.setLoginMode(loggedInMode)
    }

    override fun getLoginMode(): DataManager.LoggedInMode {
        return mPreferencesHelper.getLoginMode()
    }

    override fun getCourseData(): Observable<List<CourseData>> {
        return courseRepository.getAll()
            .flatMap { courses -> Observable.fromIterable(courses) }
            .flatMap { course  ->
                Observable.zip(
                    questionRepository.getAllByCourseId(courseId = course.id),
                    Observable.just(course),
                    BiFunction<List<Question>, Course, CourseData> { q, c -> CourseData(c, q) }
                )
            }.toList()
            .toObservable()
    }

    override fun seedDatabaseCourses(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUserName(): String? {
        return mPreferencesHelper.getCurrentUserName()
    }

    override fun setCurrentUserName(userName: String?) {
        mPreferencesHelper.setCurrentUserName(userName)
    }

    override fun getQuestionData(courseId: Long): Observable<List<QuestionData>> {
        return questionRepository.getAllByCourseId(courseId = courseId)
            .flatMap { questions -> Observable.fromIterable(questions) }
            .flatMap { question ->
                Observable.zip(
                    optionRepository.getAllByQuestionId(questionId = question.id),
                    Observable.just(question),
                    BiFunction<List<Option>, Question, QuestionData> { o, q -> QuestionData(q, o) }
                )
            }.toList()
            .toObservable()
    }

    override fun seedDatabaseOptions(): Observable<Boolean> {
        return Observable.fromCallable {
            optionRepository.getAll().isEmpty.blockingGet()
        }.concatMap { isEmpty ->
            if (isEmpty){
                val type: Type = object : TypeToken<List<Option>>() {

                }.type
                val optionList = mGson.fromJson<List<Option>>(
                    CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS),
                    type
                )
                optionRepository.add(optionList)
                Observable.just(true)
            }
            Observable.just(false)
        }
    }

    override fun seedDatabaseQuestions(): Observable<Boolean> {
        return Observable.fromCallable {
            questionRepository.getAll().isEmpty.blockingGet()
        }.concatMap { isEmpty ->
            if (isEmpty){
                val type =
                    `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Question::class.java)
                val questionList = mGson
                    .fromJson<List<Question>>(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS), type)
                questionRepository.add(questionList)
                Observable.just(true)
            }
            Observable.just(false)
        }
    }

    override fun setUserAsLoggedOut() {
        setLoginMode(DataManager.LoggedInMode.LOGOUT)
        setCurrentUserName(null)
    }

    override fun updateUserInfo(userName: String?) {
        setCurrentUserName(userName)
    }
}
