package com.example.starbucks.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    // Temporarily hold user data until OTP is verified
    private var tempFullName: String? = null
    private var tempBirthDate: String? = null
    private var tempGender: String? = null
    private var tempPhoneNumber: String? = null

    fun sendOtpForLogin(phone: String, activity: Activity, onCodeSent: () -> Unit) {
        var tempPhoneNumber = phone  // Optional: only if you want to cache this
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential, onCodeSent)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("OTP", "Login Verification Failed: ${e.message}")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    storedVerificationId = verificationId
                    resendToken = token
                    Log.d("OTP", "Login onCodeSent: $verificationId")
                    onCodeSent()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun sendOtp(
        phone: String,
        fullName: String,
        birthDate: String,
        gender: String,
        activity: Activity,
        onCodeSent: () -> Unit
    ) {
        tempFullName = fullName
        tempBirthDate = birthDate
        tempGender = gender
        tempPhoneNumber = phone

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential, onCodeSent)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("OTP", "Verification Failed: ${e.message}")
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    storedVerificationId = verificationId
                    resendToken = token
                    Log.d("OTP", "onCodeSent: $verificationId")
                    onCodeSent()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(code: String, activity: Activity, onSuccess: () -> Unit) {
        val verificationId = storedVerificationId

        if (verificationId.isNullOrEmpty()) {
            Log.e("OTP", "Verification ID is null. You must send the OTP first.")
            return
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential) {
            saveUserDataToFirestore()
            onSuccess()
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential, onSuccess: () -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("OTP", "Sign in success")
                    onSuccess()
                } else {
                    Log.e("OTP", "Sign in failed: ${task.exception?.message}")
                }
            }
    }

    private fun saveUserDataToFirestore() {
        val uid = auth.currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        val userData = hashMapOf(
            "uid" to uid,
            "fullName" to tempFullName,
            "birthDate" to tempBirthDate,
            "gender" to tempGender,
            "phoneNumber" to tempPhoneNumber
        )

        db.collection("users").document(uid).set(userData)
            .addOnSuccessListener {
                Log.d("Register", "User data saved successfully.")
            }
            .addOnFailureListener { e ->
                Log.e("Register", "Failed to save user data: ${e.message}")
            }
    }
}
