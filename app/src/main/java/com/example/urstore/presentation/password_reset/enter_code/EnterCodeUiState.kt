package com.example.urstore.presentation.password_reset.enter_code

import com.example.urstore.util.RequestState

data class EnterCodeUiState(
    val verifyCodeState: RequestState = RequestState.IDLE,
    val resendCodeState: RequestState = RequestState.IDLE,
    var otpSetup: OtpSetup = OtpSetup(),
    var otp: String = "",
    var email : String = "",
    var responseMessage: String? = ""
)

data class OtpSetup(
    var firstDigit: String = "",
    var secondDigit: String = "",
    var thirdDigit: String = "",
    var fourthDigit: String = "",
    var fifthDigit: String = "",
    var sixthDigit: String = ""
)

