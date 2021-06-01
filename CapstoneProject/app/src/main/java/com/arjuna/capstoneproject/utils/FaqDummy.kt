package com.arjuna.capstoneproject.utils

import com.arjuna.capstoneproject.data.local.entity.FaqEntity

object FaqDummy {
    fun generateFaqDummy(): List<FaqEntity> {
        val listDataFaq = ArrayList<FaqEntity>()

        listDataFaq.add(
            FaqEntity(
                id = 1,
                questions = "Pertanyaan 1",
                answerFaq = "Jawaban 1",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 2,
                questions = "Pertanyaan 2",
                answerFaq = "Jawaban 2",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 3,
                questions = "Pertanyaan 3",
                answerFaq = "Jawaban 3",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 4,
                questions = "Pertanyaan 4",
                answerFaq = "Jawaban 4",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 5,
                questions = "Pertanyaan 5",
                answerFaq = "Jawaban 5",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 6,
                questions = "Pertanyaan 6",
                answerFaq = "Jawaban 6",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 7,
                questions = "Pertanyaan 7",
                answerFaq = "Jawaban 7",
            )
        )
        return listDataFaq
    }
}