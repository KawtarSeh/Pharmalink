package com.example.pharmalink

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "UserSession"
    private const val IS_LOGGED_IN = "isLoggedIn"
    private const val USER_EMAIL = "userEmail"
    private const val FULL_NAME = "fullName"

    private lateinit var sharedPreferences: SharedPreferences
    //Objet pour éditer et sauvegarder les données dans SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    // Initialiser SharedPreferences utilisé cette fonction dans l'activité d'app
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    // Enregistrer les informations de session
    fun saveSession(isLoggedIn: Boolean, email: String?, fullName: String?) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putString(USER_EMAIL, email)
        editor.putString(FULL_NAME, fullName)
        editor.apply()
    }

    // Vérifier si l'utilisateur est connecté
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    // Récupérer l'email de l'utilisateur
    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, null)
    }

    // Récupérer le nom complet de l'utilisateur
    fun getFullName(): String? {
        return sharedPreferences.getString(FULL_NAME, null)
    }

    // Effacer la session
    fun logout() {
        editor.clear()
        editor.apply()
    }
}
