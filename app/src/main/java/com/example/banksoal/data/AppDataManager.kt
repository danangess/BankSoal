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
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton
import com.example.banksoal.data.model.db.Question

@Singleton
class AppDataManager
@Inject
constructor(
    private val mContext: Context,
    override val preferencesHelper: PreferencesHelper,
    override val courseRepository: CourseRepository,
    override val questionRepository: QuestionRepository,
    override val optionRepository: OptionRepository,
    override val userRepository: UserRepository,
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
            preferencesHelper.loginMode = DataManager.LoggedInMode.LOGOUT
            updateUserInfo(null)
            true
        }
    }

    override fun doLogin(userName: String, password: String): Single<Boolean> {
        return userRepository.validate(userName, password)
    }

    override fun getCourseData(): Observable<List<CourseData>> {
        return courseRepository.getAll()
            .flatMap { courses -> Observable.fromIterable(courses) }
            .flatMap { course  ->
                Observable.zip(
                    questionRepository.getAllByCourseId(courseId = course.id),
                    Observable.just(course),
                    BiFunction<List<Question>, Course, CourseData> { q, c -> CourseData(c) }
                )
            }.toList()
            .toObservable()
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

    override fun seedDatabaseUsers(): Observable<Boolean> {
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, User::class.java)
        val users = CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_USERS)
        val userList = mGson.fromJson<List<User>>(users, type)

        return Observable.fromCallable {
            userRepository.dao.insert(userList)
            true
        }
    }

    override fun seedDatabaseCourses(): Observable<Boolean> {
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Course::class.java)
        val courses = CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_COURSES)
        val courseList = mGson.fromJson<List<Course>>(courses, type)

        return Observable.fromCallable {
            courseRepository.dao.insert(courseList)
            true
        }
    }

    override fun seedDatabaseQuestions(): Observable<Boolean> {
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Question::class.java)
        val questions = CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS)
        val questionList = mGson.fromJson<List<Question>>(questions, type)

        return Observable.fromCallable {
            questionRepository.dao.insert(questionList)
            true
        }
    }

    override fun seedDatabaseOptions(): Observable<Boolean> {
        val options = CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS)
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Option::class.java)
        val optionList = mGson.fromJson<List<Option>>(options, type)

        return Observable.fromCallable {
            optionRepository.dao.insert(optionList)
            true
        }
    }

    override fun setUserAsLoggedOut() {
        preferencesHelper.loginMode = DataManager.LoggedInMode.LOGOUT
        preferencesHelper.currentUserName = null
    }

    override fun updateUserInfo(userName: String?) {
        preferencesHelper.currentUserName = userName
        if (userName != null){
            val user = userRepository.dao.findByUsername(userName)
            val fullName = user!!.firstName + " " + user.lastName
            preferencesHelper.currentFullName = fullName
        }
    }
}