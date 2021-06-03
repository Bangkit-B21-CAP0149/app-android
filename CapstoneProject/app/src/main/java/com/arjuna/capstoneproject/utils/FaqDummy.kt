package com.arjuna.capstoneproject.utils

import com.arjuna.capstoneproject.data.local.entity.FaqEntity

object FaqDummy {
    fun generateFaqDummy(): List<FaqEntity> {
        val listDataFaq = ArrayList<FaqEntity>()

        listDataFaq.add(
            FaqEntity(
                id = 1,
                questions = "Bagaimana cara lapor pengaduan di aplikasi?",
                answerFaq = "Jawab : Menuju halaman 'Report' yang terdapat pada menu bagian bawah. Kemudian isi formulir yang tersedia. Dan tekan tombol Lapor",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 2,
                questions = "Apakah data identitas saya aman?",
                answerFaq = "Jawab : Kami menjamin keamanan data pelapor di aplikasi ini",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 3,
                questions = "Mengapa pelapor harus mencantumkan nomor NIK atau identitas di formulir pelaporan?",
                answerFaq = "Jawab : Dengan mencantumkan nomor NIK anda, untuk mendukung keaslian dan penanggungjawab pelapor",
            )
        )

        listDataFaq.add(
            FaqEntity(
                id = 4,
                questions = "Apakah data laporan bisa dilihat oleh public atau umum?",
                answerFaq = "Jawab : Data laporan bersifat privat, hanya pihak Kementrian PPPA saja yang bisa melihat untuk keperluan penindak lanjut laporan",
            )
        )
        return listDataFaq
    }
}