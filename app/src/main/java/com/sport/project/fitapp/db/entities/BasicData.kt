package com.sport.project.fitapp.db.entities

object BasicData {

    val abs = Exercise(
        0, "abs", listOf(
            "Crunches",
            "Decline crunch",
            "Dumbell Side Bends",
            "Hanging Leg Raises",
            "Incline Leg Raises",
            "Kneeling Cable Crunch",
            "Legs Raises",
            "Flat Bench Lying Leg Raises",
            "Seated Jack Knife",
            "Twisting Hip Raise",
            "Weighted Crunch",
            "Plank",
            "Side plank",
            "Superman",
            "Twist crunch"
        )
    )

    val chest = Exercise(
        1, "chest", listOf(
            "Bench Press",
            "Close Grip Bench Press",
            "Decline Press",
            "Dumbbell Flys",
            "Dumbbell Pullover",
            "Incline Dumbell Flys",
            "Incline Press",
            "Parallel Bar Dips",
            "Push-ups",
            "Mid Cable Crossover",
            "Dumbbell Press",
            "Weighted Pushups",
            "Incline Dumbbell Press"
        )
    )

    val shoulder = Exercise(
        2, "shoulder", listOf(
            "Two Bar Shoulder press",
            "Barbell Front Raise",
            "Dumbbell Front Raises",
            "Barbell Indy Press",
            "Dumbbell Side Lateral Raises",
            "Weight Plate Front Raise",
            "Machine Shoulder Press",
            "Cable Rope Upright Rows",
            "Back Press",
            "Seated Front Press",
            "Overhead Lift",
            "Seated Dumbbell Press",
            "Shoulder Shrugs",
            "Barbell Shrugs",
            "Upright Rows",
            "Dumbbell Rear Deltoid Raises",
            "Dumbbell Side Raises"
        )
    )

    val biceps = Exercise(
        3, "biceps", listOf(
            "Barbell Preacher Curl",
            "Dumbbell Concentration Curl",
            "Barbell Bicep Curl",
            "Inclined Dumbbell Curl",
            "Reverse Dumbbell Curl",
            "Seated Dumbbell Curl",
            "Cable Biceps Curl",
            "Dumbbell Biceps Curl",
            "Dumbbell Hammer Curl",
            "Low Pully Curl",
            "Low Cable Crossover"
        )
    )

    val triceps = Exercise(
        4, "triceps", listOf(
            "Bentover Single Arm Cable Kickback",
            "Close grip Smith Push-ups",
            "Hyperbench Rope Pushdown",
            "Reverse Single Cable Extention",
            "Seated Overhead Rope Extention",
            "Standing Overhead Dumbbell Extention",
            "V-Bar Pushdown",
            "Weighted Bench Dips",
            "Close Grip Bench Press",
            "Lying Barbell Tricep Extention",
            "Tricep Kickback",
            "One Arm Dumbbell Tricep Extensions",
            "Dumbbell Tricep Extentions",
            "Diamond Push-up",
            "Diamond Push-up on Bench",
            "Dumbbell Row Kickback"
        )
    )

    val legs = Exercise(
        5, "legs", listOf(
            "Barbell Lunges",
            "Barbell Squat",
            "Leg Extension",
            "Leg Press",
            "Bulgarian Split Squat",
            "Dumbbell Side Lunges",
            "One Arm Side DeadLift",
            "Dumbbell Squat"
        )
    )

    val calf = Exercise(
        6, "calf", listOf(
            "Calf Raises",
            "One Leg Toe Raises",
            "Seated Dumbbell Calf Raises",
            "Standing Barbell Calf Raises",
            "Calf Jump"
        )
    )

    val forearm = Exercise(
        7, "forearm", listOf(
            "Barbell Wrist Curl",
            "One Arm Palmdown Wrist Curl",
            "Hammer Curl",
            "Alternating Hammer Curl",
            "Standing Barbell Behind the Back Wrist Curl",
            "Dumbbell Curl"
        )
    )

    val back = Exercise(
        8, "back", listOf(
            "Two Bar Pullups",
            "Two Handle High Pulley Row",
            "Ab Focussed Chin Ups",
            "Curl Bar One Arm Rows",
            "Hanging Dumbbell Rows",
            "Isometric Pullups with Lateralshift",
            "Rope Chinups",
            "DeadLift",
            "Chinups",
            "Seated Rows",
            "Barbell Bent Over Row",
            "T-Bar Bent Over Row"
        )
    )
}