package com.example.spoonacularchatbot.features.splash.presentation.viewmodel

import android.content.Context
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.data.local.model.Answer
import com.example.spoonacularchatbot.core.data.local.model.QUESTION_ENUM
import com.example.spoonacularchatbot.core.data.local.model.QuestionEntity
import com.example.spoonacularchatbot.core.presentation.viewmodel.BaseViewModel
import javax.inject.Inject

class QAGraphViewModel @Inject constructor(private val context: Context) : BaseViewModel() {

    fun generateQAGraph(): QuestionEntity {
        var id = 0

        val doYouLikeIt = generateDoYouLikeRecipeQuestion(id)
        id++

        val intoleranceIngredient = generateIntoleranceIngredientQuestion(id, doYouLikeIt)
        id++

        val excludeIngredients = generateExcludeIngredientsQuestion(id, intoleranceIngredient)
        id++

        val cuisineQuestion = generateCuisineQuestion(id, excludeIngredients)
        id++

        val mainIngredientsQuestion = generateMainIngredientsQuestion(id, cuisineQuestion)
        id++

        val vegetarianMainIngredientsQuestion =
            generateVegetarianMainIngredientsQuestion(id, cuisineQuestion)
        id++

        val vegetarianTypeQuestion =
            generateVegetarianTypeQuestion(id, vegetarianMainIngredientsQuestion)
        id++

        val areYouVegetarianQuestion =
            generateAreYouVegetarianQuestion(
                id = id,
                yesRelatedQuestion = vegetarianTypeQuestion,
                noRelatedQuestion = mainIngredientsQuestion
            )
        id++

        return generateWhatsUrNameQuestion(id, areYouVegetarianQuestion)
    }

    private fun generateDoYouLikeRecipeQuestion(id: Int): QuestionEntity {
        val questionsText = context.getString(R.string.do_you_like_it)
        val questionType = QUESTION_ENUM.DO_YOU_LIKE_IT
        val yesNoRelatedQuestion =
            QuestionEntity(
                -1,
                context.getString(R.string.no_more_questions),
                QUESTION_ENUM.NONE,
                arrayListOf()
            )
        val yesAnswer = Answer(id, context.getString(R.string.lbl_yes), yesNoRelatedQuestion)
        val noAnswer = Answer(id + 1, context.getString(R.string.lbl_no), yesNoRelatedQuestion)
        val doYouLikeItAnswers = arrayListOf(yesAnswer, noAnswer)
        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = doYouLikeItAnswers
        )
    }

    private fun generateIntoleranceIngredientQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.intolerance_ingredient_question)
        val questionType = QUESTION_ENUM.INTOLERANCE_INGREDIENTS
        val yesAnswer = Answer(id, context.getString(R.string.lbl_yes), relatedQuestion)
        val noAnswer = Answer(id + 1, context.getString(R.string.lbl_no), relatedQuestion)
        val answers = arrayListOf(yesAnswer, noAnswer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }

    private fun generateExcludeIngredientsQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.exclude_ingredient_question)
        val questionType = QUESTION_ENUM.EXCLUDE_INGREDIENTS
        val yesAnswer = Answer(id, context.getString(R.string.lbl_yes), relatedQuestion)
        val noAnswer = Answer(id + 1, context.getString(R.string.lbl_no), relatedQuestion)
        val answers = arrayListOf(yesAnswer, noAnswer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }

    private fun generateCuisineQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.cuisine_question)
        val questionType = QUESTION_ENUM.CUISINE
        val answer = Answer(id, context.getString(R.string.lbl_any), relatedQuestion)
        val answers = arrayListOf(answer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }

    private fun generateMainIngredientsQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.main_ingredients_question)
        val questionType = QUESTION_ENUM.MAIN_INGREDIENTS
        val answer = Answer(id, context.getString(R.string.lbl_any), relatedQuestion)
        val answers = arrayListOf(answer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }

    private fun generateVegetarianMainIngredientsQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.vegetarian_main_ingredients_question)
        val questionType = QUESTION_ENUM.VEGETARIAN_MAIN_INGREDIENTS
        val answer = Answer(id, context.getString(R.string.lbl_any), relatedQuestion)
        val answers = arrayListOf(answer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }

    private fun generateVegetarianTypeQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.vegetarian_type_question)
        val questionType = QUESTION_ENUM.VEGETARIAN_TYPE
        val answer = Answer(id, context.getString(R.string.lbl_any), relatedQuestion)
        val answers = arrayListOf(answer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }


    private fun generateAreYouVegetarianQuestion(
        id: Int,
        yesRelatedQuestion: QuestionEntity,
        noRelatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.are_you_vegetarian)
        val questionType = QUESTION_ENUM.R_U_VEGETARIAN
        val yesAnswer = Answer(id, context.getString(R.string.lbl_yes), yesRelatedQuestion)
        val noAnswer = Answer(id, context.getString(R.string.lbl_no), noRelatedQuestion)
        val answers = arrayListOf(yesAnswer, noAnswer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )
    }


    private fun generateWhatsUrNameQuestion(
        id: Int,
        relatedQuestion: QuestionEntity
    ): QuestionEntity {
        val questionsText = context.getString(R.string.whats_ur_name)
        val questionType = QUESTION_ENUM.WHATS_UR_NAME
        val answer = Answer(id, context.getString(R.string.lbl_any), relatedQuestion)
        val answers = arrayListOf(answer)

        return QuestionEntity(
            id = id,
            text = questionsText,
            type = questionType,
            expectedAnswers = answers
        )

    }
}