package com.biorent.miclarov2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _textLoginInit = MutableLiveData<String>().apply {
        value = "Usuario y clave de sucursal virtual Clarochile.cl"
    }
    val textLoginInit: LiveData<String> = _textLoginInit

    private val _hintLoginRUT = MutableLiveData<String>().apply {
        value = "RUT Titular (Sin guión)"
    }
    val hintLoginRUT: LiveData<String> = _hintLoginRUT

    private val _hintLoginPass = MutableLiveData<String>().apply {
        value = "Contraseña (Clave sucursal Virtual)"
    }
    val hintLoginPass: LiveData<String> = _hintLoginPass

    private val _textCheckRemember = MutableLiveData<String>().apply {
        value = "Recordar mi R.U.T."
    }
    val textCheckRemember: LiveData<String> = _textCheckRemember

    private val _textLoginBtn = MutableLiveData<String>().apply {
        value = "Iniciar Sesión"
    }
    val textLoginBtn: LiveData<String> = _textLoginBtn

    private val _textPassForgot = MutableLiveData<String>().apply {
        value = "¿Olvidaste tu contraseña?"
    }
    val textPassForgot: LiveData<String> = _textPassForgot

    private val _textHaveAccount = MutableLiveData<String>().apply {
        value = "¿Aun no tienes una cuenta?"
    }
    val textHaveAccount: LiveData<String> = _textHaveAccount

    private val _textRegisterHere = MutableLiveData<String>().apply {
        value = "Registrate aquí"
    }
    val textRegisterHere: LiveData<String> = _textRegisterHere

    private val _textVisitPortal = MutableLiveData<String>().apply {
        value = "Visita nuestro Portal de autogestión si solo deseas consultar tu linea movil ir al sitio"
    }
    val textVisitPortal: LiveData<String> = _textVisitPortal

    private val _textNeedHelp = MutableLiveData<String>().apply {
        value = "¿Necesitas Ayuda?"
    }
    val textNeedHelp: LiveData<String> = _textNeedHelp

}