package com.vass.coursevass.network.service

interface AlertService {
    fun createAlert(messages: String, title: String)
    fun  displayLoadingWithText(text: String?, cancelable: Boolean)
    fun hideLoading()
}