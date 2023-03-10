package com.stirkaparus.driver.common

object Constants {


    //version
    const val VERSION: Int = 1
    const val SETTING = "setting"
    const val VERSION_CONTROL = "versionControl"
    const val COMPANIES = "companies"

    //Useful
    const val NO_VALUE = ""

    //hints
    const val EMAIL_HINT = "email"
    const val PASSWORD_HINT = "пароль"


    const val SIGN_IN = "Войти"

    //DataStore
    const val LOGIN_SCREEN_TOP_BAR_TEXT = "Вход"
    const val REPORT_SCREEN_TOP_BAR_TEXT = "Отчеты"
    const val ALL_ORDERS_SCREEN_TOP_BAR_TEXT = "Заказы"
    const val DELIVERED_BY_USER = "delivered_by_user"
    const val CANCELED = "canceled_time"
    const val CANCELED_TIME = "canceled"
    const val CANCELED_RU = "Отменен"
    const val ADDRESS_RU = "Адрес"
    const val COMMENT_RU = "Комментарий"
    const val SERIAL: String = "serial"


    //user
    const val ROLE: String = "role"
    const val CITY: String = "city"
    const val EMAIL: String = "email"
    const val PHONE: String = "phone"
    const val NAME: String = "name"
    const val COMPANY_NAME: String = "company_name"
    const val COMPANY_ID: String = "company_id"
    const val ID: String = "id"

    const val TOTAL: String = "total"
    const val WIDTH: String = "width"
    const val LENGTH: String = "length"
    const val TYPE: String = "type"
    const val WASHED_TIME: String = "washed_time"
    const val WASHED: String = "washed"
    const val WASHED_COUNT: String = "washed_count"
    const val WASHED_RU: String = "Постиран"
    const val CARPETS: String = "carpets"
    const val DRIVER: String = "driver"
    const val ADMIN: String = "admin"
    const val USERS: String = "users"
    const val FIRST_NAME: String = "first_name"
    const val LAST_NAME: String = "last_name"
    const val CREATED: String = "created"
    const val CREATED_RU: String = "Создан"
    const val CURR_REPORT_ID: String = "current_report_id"
    const val REPORT_ID: String = "report_id"
    const val REPORTS: String = "reportsT"

    /** FIRE STORE CONSTANTS */
    const val ORDERS: String = "orders"

    const val PARUS_DRIVER_PREFERENCES: String = "ParusDriverPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    const val DELIVERED: String = "delivered"
    const val DELIVERED_RU: String = "Доставлен"

    const val TAKEN: String = "taken"
    const val USER: String = "users"

    const val TAKEN_RU: String = "Забран"
    const val ORDER_ID: String = "orderId"
    const val ADDRESS: String = "address"
    const val TAKEN_TIME: String = "taken_time"
    const val CREATE_TIME: String = "created_time"
    const val DELIVERED_TIME: String = "delivered_time"
    const val STATUS: String = "status"
    const val COUNT: String = "count"
    const val COUNTER: String = "counter"
    const val COMMENT: String = "comment"
    const val SALES: String = "sales"
    const val SALE_TYPE: String = "sale_type"
    const val FINISHED: String = "finished"
    const val FINISHED_RU: String = "Готов"
    const val FB_SETTING: String = "setting"





    const val TAG = "AppTag"

    //Buttons
    const val RESET_PASSWORD = "Reset"
    const val SIGN_UP = "Регистрация"
    const val SIGN_OUT = "Выход"
    const val REVOKE_ACCESS = "Revoke Access"

    //Screens
    const val ADD_ORDER_SCREEN_TOP_BAR_TEXT = "Новый заказ"
    const val FORGOT_PASSWORD_SCREEN = "Forgot password"
    const val SIGN_UP_SCREEN = "Регистрация"
    const val VERIFY_EMAIL_SCREEN = "Проверка email"
    const val PROFILE_SCREEN = "Profile"

    //Hints

    //Useful
    const val VERTICAL_DIVIDER = "|"

    //Texts
    const val PHONE_RU = "Телефон"
    const val FORGOT_PASSWORD = "Забыли пароль?"
    const val NO_ACCOUNT = "Нет аккаунта? Создать."
    const val ALREADY_USER = "Уже зарегистрированы? Войти."
    const val WELCOME_MESSAGE = "Welcome to our app."
    const val ALREADY_VERIFIED = "Уже проверено?"
    const val SPAM_EMAIL = "Если нет, пожалуйста, также проверьте папку со спамом."

    //Messages
    const val VERIFY_EMAIL_MESSAGE = "We've sent you an email with a link to verify the email."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."

    //Error Messages
    const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."

}