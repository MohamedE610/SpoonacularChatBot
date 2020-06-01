# Architecture
Clean Architecture with MVVM pattern in the presentation layer


# Tech Stack
- Kotlin
- Dagger2
- Rxjava2
- Room
- Retrofit
- Android Architecture Components


# App Flow
- generate QAGraph (Questions and Answers Graph) and cache it in room database (splash module).
- handle chat bot logic through QAGraph in chat bot module.
- chat bot asks user a questions, then user answers the questions.
- chat bot checks user answer to collect the needed information.
- in case answer is not valid, then chat bot asks again and display suggestions to user.
- when chat bot collected the needed information, then display a list of recipes based on user's answers.
- chat bot asks user if he wants to ask about another recipes, if yes, repeat questions cycle.
