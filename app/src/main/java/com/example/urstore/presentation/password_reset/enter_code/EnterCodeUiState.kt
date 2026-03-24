package com.example.urstore.presentation.password_reset.enter_code

import com.example.urstore.util.RequestState

data class EnterCodeUiState(
    val enterCodeState: RequestState = RequestState.IDLE,
    var otpSetup: OtpSetup = OtpSetup(),
    var otp : String = ""
)

data class OtpSetup(
    var firstDigit: String = "",
    var secondDigit: String = "",
    var thirdDigit: String = "",
    var fourthDigit: String = "",
    var fifthDigit: String = "",
    var sixthDigit: String = ""
)

