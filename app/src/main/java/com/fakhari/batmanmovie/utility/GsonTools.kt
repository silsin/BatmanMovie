package com.fakhari.batmanmovie.utility

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonTools {

    companion object{
        private var gsonTools: Gson? = null
        private var gsonExposeAnnotationTools: Gson? = null

        // static method to create instance of Singleton class
        fun getInstance(): Gson? {
            when (gsonTools) {
                null -> gsonTools = Gson()
            }
            return gsonTools
        }

        fun getInstanceExposeAnnotation(): Gson? {
            when (gsonExposeAnnotationTools) {
                null -> gsonExposeAnnotationTools =
                    GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            }
            return gsonExposeAnnotationTools
        }
    }
}