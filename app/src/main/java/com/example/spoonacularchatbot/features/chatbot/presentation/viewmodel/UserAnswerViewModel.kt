package com.example.spoonacularchatbot.features.chatbot.presentation.viewmodel

import android.content.Context
import com.example.spoonacularchatbot.R
import com.example.spoonacularchatbot.core.data.local.model.Cuisine_Enum
import com.example.spoonacularchatbot.core.data.local.model.Vegetarian_Enum
import com.example.spoonacularchatbot.core.presentation.viewmodel.BaseViewModel
import com.example.spoonacularchatbot.features.chatbot.presentation.model.YES_NO
import java.util.*
import javax.inject.Inject

// to collect info i need to perform chat bot task
class UserAnswerViewModel @Inject constructor(private val context: Context) : BaseViewModel() {

    fun getUserNameFromMessage(message: String): String {
        return when {
            message.toLowerCase(Locale.getDefault()).contains(MY_NAME_IS) -> message.replace(
                MY_NAME_IS, ""
            )
            message.toLowerCase(Locale.getDefault())
                .contains(IM) -> message.replace(IM, "")
            message.toLowerCase(Locale.getDefault())
                .contains(IM) -> message.replace(I_M, "")
            message.toLowerCase(Locale.getDefault())
                .contains(IAM) -> message.replace(I_AM, "")
            message.toLowerCase(Locale.getDefault())
                .contains(IAM) -> message.replace(IAM, "")
            else -> message
        }
    }

    fun checkAreYouVegetarianAnswer(message: String): YES_NO {
        return when {
            message.toLowerCase(Locale.getDefault())
                .contains(context.getString(R.string.lbl_no)) -> YES_NO.NO
            message.toLowerCase(Locale.getDefault())
                .contains(context.getString(R.string.lbl_yes)) -> {
                YES_NO.YES
            }
            else -> YES_NO.NONE
        }
    }

    fun checkVegetarianTypeAnswer(message: String): Vegetarian_Enum {
        return when {
            message.toLowerCase(Locale.getDefault())
                .contains(Vegetarian_Enum.Vegan.name.toLowerCase(Locale.getDefault())) -> Vegetarian_Enum.Vegan
            message.toLowerCase(Locale.getDefault())
                .contains(Vegetarian_Enum.Vegetarian.name.toLowerCase(Locale.getDefault())) -> Vegetarian_Enum.Vegetarian
            message.toLowerCase(Locale.getDefault())
                .contains(Vegetarian_Enum.Lacto_Vegetaria.name.toLowerCase(Locale.getDefault())) -> Vegetarian_Enum.Lacto_Vegetaria
            message.toLowerCase(Locale.getDefault())
                .contains(Vegetarian_Enum.Ovo_Vegetarian.name.toLowerCase(Locale.getDefault())) -> Vegetarian_Enum.Ovo_Vegetarian
            message.toLowerCase(Locale.getDefault())
                .contains(Vegetarian_Enum.Pescetarian.name.toLowerCase(Locale.getDefault())) -> Vegetarian_Enum.Pescetarian

            else -> Vegetarian_Enum.NONE
        }

    }

    fun checkCuisineAnswer(message: String): String {
        val answer = arrayListOf<Cuisine_Enum>()

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.american.name))
            answer.add(Cuisine_Enum.american)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.french.name))
            answer.add(Cuisine_Enum.french)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.indian.name))
            answer.add(Cuisine_Enum.indian)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.italian.name))
            answer.add(Cuisine_Enum.italian)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.japanese.name))
            answer.add(Cuisine_Enum.japanese)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.mexican.name))
            answer.add(Cuisine_Enum.mexican)

        if (message.toLowerCase(Locale.getDefault()).contains(Cuisine_Enum.middle_eastern.name))
            answer.add(Cuisine_Enum.middle_eastern)

        return answer.joinToString(separator = ",") { cuisineEnum -> cuisineEnum.name }
    }

    fun checkDoYouLikeItAnswer(message: String): Boolean {
        val answer = when {
            message.toLowerCase(Locale.getDefault())
                .contains(YES_NO.YES.name.toLowerCase(Locale.getDefault())) -> {
                YES_NO.YES
            }

            message.toLowerCase(Locale.getDefault())
                .contains(YES_NO.NO.name.toLowerCase(Locale.getDefault())) -> {
                YES_NO.NO
            }

            message.toLowerCase(Locale.getDefault())
                .contains(YES_NO.NONE.name.toLowerCase(Locale.getDefault())) -> {
                YES_NO.NONE
            }

            else -> YES_NO.NONE
        }


        when (answer) {
            YES_NO.NO -> return false
            YES_NO.YES -> return true
            else -> {
                val approvalExpressions =
                    context.resources.getStringArray(R.array.approval_expressions)
                for (expression in approvalExpressions)
                    if (message.toLowerCase(Locale.getDefault()).contains(expression))
                        return true

                return false
            }
        }
    }

    companion object {
        const val MY_NAME_IS = "my name is"
        const val IM = "im"
        const val I_M = "i'm"
        const val IAM = "iam"
        const val I_AM = "i am"
    }
}